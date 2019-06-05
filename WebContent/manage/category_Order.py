# 系统后台的分类排序管理.
from base_action import *
from unit_query import UnitQuery
from cn.edustar.jitar.data import Command
from cn.edustar.jitar.util import ParamUtil
from cn.edustar.jitar.pojos import Category
from cn.edustar.jitar.jython import BaseAdminAction
from cn.edustar.jitar.service import CategoryService
from category_query import CategoryQuery
from group_member_query import GroupMemberQuery

# 需要类名和文件名一致.
class category_Order (ActionExecutor):
  CATEGORY_LIST = "/WEB-INF/ftl/admin/category_Order.ftl"
  
  # 构造.
  def __init__(self):
    self.params = ParamUtil(request)
    self.cate_svc = __jitar__.categoryService
    self.itemType = "user"
    return
 
  
  # 根据'cmd'进行派发.
  def dispatcher(self, cmd):
    
    # 以下必须要求是登录用户
    if self.loginUser == None: return ActionResult.LOGIN
    if self.loginUser == None:
      self.addActionError(u"请重新登陆.")
      return ActionResult.ERROR
    
    # 根据cmd参数，执行相应的方法.
    if cmd == "" or cmd == None or cmd == "list":
      return self.list()          # 列表显示.
    elif cmd == "save":
      return self.save()          # 保存.
    else :
      self.addActionError(u"未知的命令参数：" + cmd)
      return ActionResult.ERROR    


  # 系统所有机构的列表显示.
  def list(self):
    # 构造查询.根据传递的参数与权限结合来判断显示什么分类
    # default 缺省为系统分类, 也就是文章分类
    # group 群组分类
    # resource 资源分类
    # photo 照片分类
    # blog 工作组分类
    # user_1 用户１的分类
    # gres_1 协助组１的资源分类　
    # gart_1 协助组１的文章分类
    # user_res_1 用户1的资源分类
    # user_video_1 用户1的视频分类
    
    if self.loginUser == None: return ActionResult.LOGIN
    
    query = AdminCategoryQuery(""" cat.categoryId, cat.name, cat.itemType, cat.parentId, cat.parentPath,cat.childNum, cat.orderNum, cat.description """)
    self.localTypeName = "";
    self.parentId = self.params.getIntParamZeroAsNull("parentId")
    self.itemType = self.params.getStringParam("type")
    #print 'parentId='+str(self.parentId)      
    #print 'itemType='+self.itemType
    #print 'split0-4='+self.itemType[0:4]
    if self.itemType == None or self.itemType == '': 
      self.itemType = 'default'  
    query.itemType = self.itemType
    query.parentId = self.parentId
    if self.itemType == 'default' or self.itemType == 'group' or self.itemType == 'resource' or self.itemType == 'blog' or self.itemType == 'photo' or self.itemType == 'video':
      # TODO: 权限检查.
      # print self.itemType
      self.localTypeName = self.itemType
      if self.canAdmin() == False:
        self.addActionError(u"没有管理系统分类的权限.")
        return ActionResult.ERROR
      
    elif self.itemType[0:9] == 'user_res_': 
      #TODO: 检查当前用户是否是本分类的用户
      # print self.itemType
      cateUserId = self.itemType[9:]
      if str(self.loginUser.userId) == cateUserId:
        self.localTypeName = "user_res"
        self.cateUserId = cateUserId
      else:
        self.addActionError(u"您不是id=" + str(cateUserId) + u"的用户")
        return ActionResult.ERROR

    elif self.itemType[0:11] == 'user_video_': 
      #TODO: 检查当前用户是否是本分类的用户
      # print self.itemType
      cateUserId = self.itemType[11:]
      if str(self.loginUser.userId) == cateUserId:
        self.localTypeName = "user_video"
        self.cateUserId = cateUserId
      else:
        self.addActionError(u"您不是id=" + str(cateUserId) + u"的用户")
        return ActionResult.ERROR
              
    elif self.itemType[0:5] == 'user_':
      #TODO: 检查当前用户是否是本分类的用户
      # print self.itemType  
      cateUserId = self.itemType[5:]
      # print "cateUserId=" + str(cateUserId)
      # print "myID=" + str(self.loginUser.userId)
      if str(self.loginUser.userId) == cateUserId:
        self.localTypeName = "user" 
        self.cateUserId = cateUserId
      else:
        self.addActionError(u"您不是id=" + str(cateUserId) + u"的用户")
        return ActionResult.ERROR
    
    elif self.itemType[0:5] == 'gres_':
      #TODO: 检查当前用户是否是协作组的管理员
      #print self.itemType
      groupId = self.itemType[5:]
      #print "groupId=" + str(groupId)
      gmQuery = GroupMemberQuery(""" gm.groupId, gm.groupRole """)
      gmQuery.userId = self.loginUser.userId
      gmQuery.groupId = int(groupId)
      group_list = gmQuery.query_map(1)
      if group_list.size > 0:
        self.localTypeName = "gres"
        self.cateGroupId = int(groupId)
      else:
        self.addActionError(u"您不是id=" + str(cateUserId) + u"的群组管理员")
        return ActionResult.ERROR
    
    elif self.itemType[0:5] == 'gart_':
      #TODO: 检查当前用户是否是协作组的管理员
      #print self.itemType
      groupId = self.itemType[5:]
      #print "groupId=" + str(groupId)
      gmQuery = GroupMemberQuery(""" gm.groupId, gm.groupRole """)
      gmQuery.userId = self.loginUser.userId
      gmQuery.groupId = int(groupId)
      group_list = gmQuery.query_map(1)
      if group_list.size > 0:
        self.localTypeName = "gart"
        self.cateGroupId = int(groupId)
      else:
        self.addActionError(u"您不是id=" + str(cateUserId) + u"的群组管理员")
        return ActionResult.ERROR
    
    elif self.itemType[0:5] == 'gvid_':
      #TODO: 检查当前用户是否是协作组的管理员
      #print self.itemType
      groupId = self.itemType[5:]
      #print "groupId=" + str(groupId)
      gmQuery = GroupMemberQuery(""" gm.groupId, gm.groupRole """)
      gmQuery.userId = self.loginUser.userId
      gmQuery.groupId = int(groupId)
      group_list = gmQuery.query_map(1)
      if group_list.size > 0:
        self.localTypeName = "gvid"
        self.cateGroupId = int(groupId)
      else:
        self.addActionError(u"您不是id=" + str(cateUserId) + u"的群组管理员")
        return ActionResult.ERROR
    elif self.itemType[0:5] == 'gpho_':
      #TODO: 检查当前用户是否是协作组的管理员
      #print self.itemType
      groupId = self.itemType[5:]
      #print "groupId=" + str(groupId)
      gmQuery = GroupMemberQuery(""" gm.groupId, gm.groupRole """)
      gmQuery.userId = self.loginUser.userId
      gmQuery.groupId = int(groupId)
      group_list = gmQuery.query_map(1)
      if group_list.size > 0:
        self.localTypeName = "gpho"
        self.cateGroupId = int(groupId)
      else:
        self.addActionError(u"您不是id=" + str(cateUserId) + u"的群组管理员")
        return ActionResult.ERROR
    else:
      # print self.itemType
      self.addActionError(u"无效的参数")
      return ActionResult.ERROR
        
    # 调用分页函数.
    pager = self.createPager()
    pager.totalRows = query.count()
        
    # 得到所有分类的列表.
    cateList = query.query_map(pager)
        
    # 传给页面.
    request.setAttribute("category_list", cateList)
    request.setAttribute("pager", pager)
    
    # 将参数放到查询的页面.
    request.setAttribute("parentId", query.parentId)
    request.setAttribute("itemType", query.itemType)
    request.setAttribute("type", self.localTypeName)
    
    if query.parentId == '' or query.parentId == None:
      request.setAttribute("parentparentId", "")
    else:
      pcategory = self.cate_svc.getCategory(int(query.parentId))
      if pcategory == None:
        request.setAttribute("parentparentId", "")
      else:    
        request.setAttribute("parentparentId", pcategory.parentId)
      
    return self.CATEGORY_LIST
    
  
  # 保存分类排序结果
  def save(self):
    cate_ids = self.params.getIdList("cateid")
    nos = self.params.safeGetStringParam("orderNo").split(",")
    if cate_ids == None or cate_ids.size() == 0:
      self.addActionError(u"没有选择要操作的分类.")
      return ActionResult.ERROR
  
    if nos == None or len(nos) == 0:
      self.addActionError(u"没有选择要操作的分类序号.")
      return ActionResult.ERROR
    iIndex = -1
    for id in cate_ids:
      iIndex = iIndex + 1
      no = nos[iIndex]
      if no.isdigit() == False :
        no = 0
      else:
        no = int(no)
      #print "id=" + str(id) + "  no=" + str(no)
      category = self.cate_svc.getCategory(id)
      if category == None:
        self.addActionError(u"未能找到标识为 " + id + u" 的分类")
        return ActionResult.ERROR
      else:  
        self.cate_svc.setCategoryOrder(id, no)
    self.parentId = self.params.getIntParamZeroAsNull("parentId")
    self.itemType = self.params.getStringParam("type")
    link = "?cmd=list&amp;type=" + str(self.itemType) + "&amp;parentId=" 
    if self.parentId != None:
      link += str(self.parentId)
    self.addActionLink(u"返回", link)
    return ActionResult.SUCCESS


  # 创建并返回一个分页对象.
  def createPager(self):
    # 调用Java的函数.
    pager = self.params.createPager()
    pager.itemName = u"分类"
    pager.itemUnit = u"个"
    pager.pageSize = 20
    return pager
    


# 资源带高级过滤条件的搜索.
class AdminCategoryQuery (CategoryQuery):
  def __init__(self, selectFields):
    CategoryQuery.__init__(self, selectFields)
    self.parentId = None              # 那个分类下的
    
  def applyWhereCondition(self, qctx):
    CategoryQuery.applyWhereCondition(self, qctx)
