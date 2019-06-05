# coding=utf-8
from java.net import URLDecoder
from cn.edustar.jitar.pojos import User, Group, GroupMember
from cn.edustar.jitar.model import ObjectType
from cn.edustar.jitar.service import PageKey
from cn.edustar.jitar.util import CommonUtil
from com.alibaba.fastjson import JSONObject

# 此文件的全局变量.
page_svc = __jitar__.pageService
group_svc = __jitar__.groupService


# 强判定一个指定字符串是否是一个数字.
def isIntegerStrong(val):
  # 转换为数字, 如果发生异常则不是数字.
  try:
    i = int(val)
  except ValueError:
    return False
  
  # 数字再转回字符串, 和字符串完全比较相同才是数字.
  if str(i) != val: return False
  return True


# 强判定一个指定字符串是否是一个合法的名字, 可用于判断 user.loginName, group.groupName.
# 合法名字必须由字母、数字、下划线构成, 第一个字符必须是字母，长度不得小于1.
def isValidName(name):
  if name == None or name == '':
    return False
  if name.strip() != name:  # 不可在前后带空白字符.
    return False
  if len(name) == 1:       # 强制要求不允许出现单字符.
    return False
  
  i = 0
  for c in name:
    i += 1
    if ('A' <= c and 'Z' >= c) or ('a' <= c and 'z' >= c): continue
    # 首字符必须是字母.
    if i == 1: return False
    if ('0' <= c and '9' >= c) or '_' == c: continue
    return False
    
  return True



# request 辅助类, 用于获取及解析 uri.
class RequestMixiner:
  # 可靠的得到客户端原来请求的 uri 地址, 当请求被 forward 过来的时候不能通过简单的 #   
  #   request.getRequestURI 得到该地址, 其可能返回为: /Groups/WEB-INF/py/show_article.py
  #   而不是原始的请求 uri.
  # 注意: 此返回地址包括了 context_path 部分, 为去掉 context_path TODO:
  def getRequestURI(self):
    # 先尝试从 attribute['javax.servlet.forward.request_uri'] 获取, 如果有则解码返回.
    uri = request.getAttribute('javax.servlet.forward.request_uri')
    if uri != None and uri != "":
      return URLDecoder.decode(uri, 'UTF-8')
    
    # 否则直接用 request 对象里面的.
    uri = request.requestURI
    if uri != None:
      return URLDecoder.decode(uri, 'UTF-8')
    raise Error, "Can't get requestURI"



  # 从指定的 uri 参数中去掉 '.html', '.htm' 后缀.
  # 如果后缀不是 '.html', '.htm' 则返回 ''.
  def removeHtmlExt(self, uri):
    if uri == None or uri == '': return ''
    if uri.endswith('.html'):
      return uri[ : len(uri) - len('.html')]
    if uri.endswith('.htm'):
      return uri[ : len(uri) - len('.htm')]
    # 也许应该支持更多后缀类型?? 例如 '.shtml' ??
    return ''


  def debug_output(self):
    out = response.writer
    print >>out, "<h2>Hello World!</h2>"
    print >>out, "<li>requestURI = ", request.requestURI
    print >>out, "<li>pathInfo = ", request.pathInfo
    print >>out, "<li>requestURL = ", request.requestURL
    print >>out, "<li>servletPath = ", request.servletPath
    
    print >>out, "<h3>request.Attributes dump</h3>"
    names = request.getAttributeNames()
    while names.hasMoreElements():
      elem = names.nextElement()
      print >>out, "<li>elem = ", elem, ", value=", request.getAttribute(elem)


# response 辅助类.
class ResponseMixiner:
  ACCESS_ERROR = "/WEB-INF/ftl/mengv1/access_error.ftl"
  
  # 报告客户端指定 uri 未找到.
  # 函数返回 None
  def sendNotFound(self, uri = None):
    if uri == None:
      response.sendError(404, self.getRequestURI())
    else:
      response.sendError(404, uri)
    return None


# page 检测辅助类, 可帮助检测用户页, 协作组页中对象状态并返回合适的信息.
class PageCheckMixiner:

  # 得到指定页面 page 的所有 widget 放到 request 环境中, 然后返回指定的 ftl 模板名.
  def getWidgetsAndReturn(self, page, ftl):
    self.widget_list = self.getPageWidgets(page)
    # print "self.widget_list = ", self.widget_list
    request.setAttribute("widget_list", self.widget_list)
    request.setAttribute("widgets", self.widget_list)
    
    return ftl



  # 得到指定用户的首页, 如果不存在则从母版复制一份.
  def getUserIndexPage(self, user):
    index_pk = PageKey(ObjectType.OBJECT_TYPE_USER, user.userId, "index")
    page = page_svc.getPageByKey(index_pk)
    if page != None: return page
    
    return self._createUserIndexPage(user)   


  # 得到指定用户文章显示所用的系统页, 其中 skin 被设置为该用户的.
  def getUserEntryPage(self, user):
    return self.getSystemPageWithUserSkin(PageKey.SYSTEM_USER_ENTRY, user)
  
  
  # 得到指定用户文章分类显示所用的系统页, 其中 skin 被设置为该用户的.
  def getUserArticleCategoryPage(self, user):
    return self.getSystemPageWithUserSkin(PageKey.SYSTEM_USER_CATEGORY, user)
  
  
  # 得到指定用户资源分类显示所用的系统页, 其中 skin 被设置为该用户的.
  def getUserResourceCategoryPage(self, user):
    return self.getSystemPageWithUserSkin(PageKey.SYSTEM_USER_RESCATE, user)
  
  # 得到指定用户资源分类显示所用的系统页, 其中 skin 被设置为该用户的.
  def getUserVideoCategoryPage(self, user):
    return self.getSystemPageWithUserSkin(PageKey.SYSTEM_USER_VIDEOCATE, user)

  # 得到指定用户的用户档案显示系统页, 其中 skin 被设置为该用户的.
  def getUserProfilePage(self, user):
    return self.getSystemPageWithUserSkin(PageKey.SYSTEM_USER_PROFILE, user)
  
  
  # 得到指定协作组的首页, 如果不存在, 则立刻从母版复制一份.
  def getGroupIndexPage(self, group):
    # 协作组类型 + 协作组标识 + 'index'
    index_pk = PageKey(ObjectType.OBJECT_TYPE_GROUP, group.groupId, 'index')
    page = page_svc.getPageByKey(index_pk)
    if page != None: return page
    
    # 创建出来, 并返回. 
    return self._createGroupIndexPage(group)
  
  def getPrepareCourseIndexPage(self, prepareCourse):
    # 协作组类型 + 协作组标识 + 'index'
    index_pk = PageKey(ObjectType.OBJECT_TYPE_PREPARECOURSE, prepareCourse.prepareCourseId, 'index')
    page = page_svc.getPageByKey(index_pk)
    return page
    
    # 创建出来, 并返回. 
    return self._createPrepareCourseIndexPage(prepareCourse)

  # 得到指定协作组文章分类显示所用的系统页, 其中 skin 被设置为该协作组的.
  def getGroupArticleCategoryPage(self, group):
    return self.getSystemPageWithGroupSkin(PageKey.SYSTEM_GROUP_ARTICLE_CATEGORY, group)


  # 得到指定协作组资源分类显示所用的系统页, 其中 skin 被设置为该协作组的.
  def getGroupResourceCategoryPage(self, group):
    return self.getSystemPageWithGroupSkin(PageKey.SYSTEM_GROUP_RESOURCE_CATEGORY, group)
  
    
  # 得到指定的系统页, 并附加上指定用户的首页的皮肤属性.
  def getSystemPageWithUserSkin(self, pk, user):
    # 得到此系统页面.
    sys_page = page_svc.getPageByKey(pk)
    if sys_page == None: return None;

    # 得到用户个人主页.
    index_page = page_svc.getUserIndexPage(user._getUserObject());

    # 创建新页面.
    new_page = sys_page._getPageObject().clone()
    if index_page != None:
      new_page.skin = index_page.skin
      new_page.customSkin = index_page.customSkin
      if index_page.customSkin != None:
        customSkin = JSONObject.parse(index_page.customSkin)
        request.setAttribute("customSkin", customSkin)
    return new_page
  
  
  # 得到指定的系统页, 并附加上指定协作组的首页的皮肤属性.
  def getSystemPageWithGroupSkin(self, pk, group):
    # 得到此系统页面.
    sys_page = page_svc.getPageByKey(pk)
    if sys_page == None: return None;

    # 得到协作组主页.
    index_page = self.getGroupIndexPage(group)

    # 创建新页面.
    new_page = sys_page._getPageObject().clone()
    if index_page != None:
      new_page.skin = index_page.skin
    
    return new_page
  
  
  # 得到指定页面的所有功能块.
  def getPageWidgets(self, page):
    return page_svc.getPageWidgets(page.pageId)


  # 得到协作组服务.
  def _getGroupService(self):
    return __jitar__.groupService
  

  # 创建/复制用户首页, 并返回复制的页面.
  def _createUserIndexPage(self, user):
    src_pk = PageKey.SYSTEM_USER_INDEX
    dest_pk = PageKey(ObjectType.OBJECT_TYPE_USER, user.userId, 'index')
    title = user.blogName
    if title == None or title == '' : title = user.nickName + u' 的工作室'
    page_svc.duplicatePage(src_pk, dest_pk, title)
    return page_svc.getPageByKey(dest_pk)    

  
  # 创建/复制协作组首页, 并返回复制的页面.
  def _createGroupIndexPage(self, group):
    src_pk = PageKey.SYSTEM_GROUP_INDEX
    dest_pk = PageKey(ObjectType.OBJECT_TYPE_GROUP, group.groupId, 'index')
    title = group.getGroupTitle()
    page_svc.duplicatePage(src_pk, dest_pk, title)
    return page_svc.getPageByKey(dest_pk)

  
    
# 得到协作组必要信息的辅助类.
class ShowGroupBase:
  def getGroupInfo(self, groupName):
    # 得到当前登录用户.
    self.visitor = self.loginUser
    request.setAttribute("visitor", self.visitor)
    self.visitor_role = "guest"
    
    # 得到协作组信息, 并验证协作组是否能被访问.
    self.group = group_svc.getGroupMayCached(groupName)
    request.setAttribute("group", self.group)
    if self.canVisitGroup(self.group) == False:
      return False
    
    # 计算当前登录用户身份, 以及其在协作组中的角色.
    if self.visitor != None:
      self.group_member = self.__get_group_member()
      request.setAttribute("group_member", self.group_member)
      self.visitor_role = self.__calcVisitorRole()
    request.setAttribute("visitor_role", self.visitor_role)
    
    # TODO: 隐藏的协作组处理问题.
    
    return True
    
  # 得到 self.visitor 在 self.group 中的身份信息
  def __get_group_member(self):
    if self.visitor == None: return None
    self.group_member = self._getGroupService().getGroupMemberByGroupIdAndUserId(self.group.groupId, self.visitor.userId)
    return self.group_member


  # 计算指定访客 self.group_member 在协作组 self.group 的角色 .
  # 可能返回 'guest', 'member', 'admin'.
  def __calcVisitorRole(self):
    role = "guest"      # 缺省为访客.
    # 如果未登录或者访客被锁定等则身份为访客, 如果用户被删除按理说不能登录.
    if self.visitor == None or self.group_member == None: return role
    if self.visitor.userStatus != User.USER_STATUS_NORMAL:
      return role

    # 如果协作组非正常, 则任何人都是访客.
    if self.group.groupState != Group.GROUP_STATE_NORMAL:
      return role
    
    # 得到访客在协作组的身份.
    gm = self.group_member
    # 如果不是组员则返回访客身份.
    if gm == None: return role
    
    # 组员状态不正常则返回访客身份.
    if gm.status != GroupMember.STATUS_NORMAL: return role

    # 副管理员及以上身份返回为管理员, 否则返回组员身份.
    if gm.groupRole >= GroupMember.GROUP_ROLE_VICE_MANAGER:
      role = "admin"
    else:
      role = "member"
    return role
  

# 其提供分类知识.
class CategoryMixiner:
  # 得到用户文章分类对应的分类 itemType.
  def toUserArticleCategoryItemType(self, user):
    return CommonUtil.toUserArticleCategoryItemType(user.userId)
  
  # 得到用户资源分类对应的分类 itemType.
  def toUserResourceCategoryItemType(self, user):
    return CommonUtil.toUserResourceCategoryItemType(user.userId)

  # 得到用户视频分类对应的分类 itemType.
  def toUserVideoCategoryItemType(self, user):
    return CommonUtil.toUserVideoCategoryItemType(user.userId)
  
  # 得到协作组文章分类对应的分类 itemType.
  def toGroupArticleCategoryItemType(self, group):
    return CommonUtil.toGroupArticleCategoryItemType(group.groupId)
  
  # 得到协作组资源分类对应的分类 itemType.
  def toGroupResourceCategoryItemType(self, group):
    return CommonUtil.toGroupResourceCategoryItemType(group.groupId)
  
  # 得到协作组图片分类对应的分类 itemType.
  def toGroupPhotoCategoryItemType(self, group):
    return CommonUtil.toGroupPhotoCategoryItemType(group.groupId)

  # 得到协作组视频分类对应的分类 itemType.
  def toGroupVideoCategoryItemType(self, group):
    return CommonUtil.toGroupVideoCategoryItemType(group.groupId)
  
  