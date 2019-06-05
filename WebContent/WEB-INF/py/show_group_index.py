# script for show group index page.

from base_action import ActionExecutor
from base_blog_page import *
from cn.edustar.jitar.pojos import Group, GroupMember, User
from cn.edustar.jitar.util import CommonUtil
from cn.edustar.jitar.action import ActionLink
from cn.edustar.jitar.service import CategoryService;
# 本模块全局变量.
group_svc = __jitar__.groupService
stat_svc = __jitar__.statService
 
# 显示协作组首页.
class show_group_index(ActionExecutor, ShowGroupBase, RequestMixiner, ResponseMixiner, PageCheckMixiner):
  def execute(self):
    # 解析 uri.
    if self.parseUri() == False:
      return self.sendNotFound(self.uri)
  
    # 得到要访问的协作组, 访问者角色, 并验证其可访问状态.
    if self.getGroupInfo(self.groupName) == False:
      actionLinks = [ActionLink(u"返回协作组列表",CommonUtil.getSiteUrl(request) + "groups.action")]
      request.setAttribute("actionLinks",actionLinks);
      return self.ACCESS_ERROR
    #print "group = ", self.group, ", visitor = ", self.visitor, ", gm = ", self.group_member
  
    uuid=group_svc.getGroupCateUuid(self.group)
    if uuid==CategoryService.GROUP_CATEGORY_GUID_KTYJ :
        #课题
        request.setAttribute("isKtGroup", "1")
    elif uuid==CategoryService.GROUP_CATEGORY_GUID_JTBK :
        #备课 
        request.setAttribute("isKtGroup", "2")
    else:
        request.setAttribute("isKtGroup", "0")
  
    # 得到协作组首页.
    page = self.getGroupIndexPage(self.group)
    #print "page =", page
    if page == None:
      return self.sendNotFound()
    request.setAttribute("page", page)

    # 得到页面功能块.
    self.widget_list = self.getPageWidgets(page)
    #print "self.widget_list = ", self.widget_list
    request.setAttribute("widget_list", self.widget_list)
    request.setAttribute("widgets", self.widget_list)

    # 增加访问计数.
    self.incGroupVisitCount(self.group)

    return "/WEB-INF/group/default/index.ftl"

  
  # 解析 uri, 从中获取要访问的 groupName.
  def parseUri(self):
    self.uri = self.getRequestURI()
    #print "self.uri =", self.uri
    if self.uri == None or self.uri == "":
      return False

    # 例子: /Groups/g/manager -> ['', 'Groups', 'g', 'manager']
    # 其中最后一个是协作组名字.
    arr = self.uri.split('/')
    arr_len = len(arr)
    if arr_len < 1:
      return False

    # 得到协作组名字并验证其合法性.
    self.groupName = arr[arr_len - 1]
    if isValidName(self.groupName) == False:
      return False

    return True
  
  
  # 增加协作组访问计数.
  def incGroupVisitCount(self, group):
    group = group._getGroupObject()
    stat_svc.incGroupVisitCount(group)

    # 这里更新也许有并发问题, 但不严重, 因为隔一段时间总会重新加载.    
    group.visitCount += 1


