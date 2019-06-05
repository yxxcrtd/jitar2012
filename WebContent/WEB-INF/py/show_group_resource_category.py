# script
from cn.edustar.jitar.util import ParamUtil
from cn.edustar.jitar.pojos import Category
from base_action import ActionExecutor
from base_blog_page import *
from resource_query import GroupResourceQuery
from cn.edustar.jitar.service import CategoryService;

# 本模块全局变量.
group_svc = __jitar__.groupService
cate_svc = __jitar__.categoryService

# 显示一个协作组的指定资源分类页面.
class show_group_resource_category(ActionExecutor, ShowGroupBase, RequestMixiner, ResponseMixiner, PageCheckMixiner, CategoryMixiner):
  def __init__(self):
    self.params = ParamUtil(request)
    
  # 主执行入口.  
  def execute(self):
    # 解析 uri.
    if self.parseUri() == False:
      return self.sendNotFound(self.uri)
    
    # 得到要访问的协作组, 访问者角色, 并验证其可访问状态.
    if self.getGroupInfo(self.groupName) == False:
      return self.ACCESS_ERROR
    
    # 得到要访问的分类, 要验证分类的确是协作组资源分类.
    self.category = self.getCategory()
    #print "self.category = ", self.category
    if self.category == None: 
      return self.sendNotFound()
    request.setAttribute("category", self.category)
    
    self.isKtGroup=0
    uuid=group_svc.getGroupCateUuid(self.group)
    if uuid==CategoryService.GROUP_CATEGORY_GUID_KTYJ :
      #课题
      self.isKtGroup=1
      request.setAttribute("isKtGroup", "1")
    elif uuid==CategoryService.GROUP_CATEGORY_GUID_JTBK :
      #备课 
      self.isKtGroup=2
      request.setAttribute("isKtGroup", "2")
    else:
      self.isKtGroup=0
      request.setAttribute("isKtGroup", "0")
          
    # 得到资源.
    self.getResourceList(self.visitor_role)

    # 得到资源分类显示页面.
    page = self.getGroupResourceCategoryPage(self.group)
    #print "page = ", page
    if page == None:
      return self.sendNotFound()
    request.setAttribute("page", page)
    
    # 得到页面功能块.
    widget_list = self.getPageWidgets(page)
    #print "widget_list = ", widget_list
    request.setAttribute("widget_list", widget_list)
    request.setAttribute("widgets", widget_list)
    
    return "/WEB-INF/group/default/show_group_resource_category.ftl"  
  
  # 解析 uri, 从中获取要访问的 groupName, categoryId.
  def parseUri(self):
    self.uri = self.getRequestURI()
    #print "self.uri =", self.uri
    if self.uri == None or self.uri == "":
      return False
  
    # 例子: '/Groups/g/manager/rescate/22.html'
    #   ['', 'Groups', 'g', 'manager', 'rescate', '22.html']
    # 其中最后一个是分类标识+'.html', 倒数第3个是协作组名字.
    arr = self.uri.split('/')
    arr_len = len(arr)
    if arr_len < 3:
      return False
    
    # 得到分类标识部分.
    category_part = self.removeHtmlExt(arr[arr_len - 1])    # 153.html -> 153
    if isIntegerStrong(category_part) == False: 
      return False
    self.categoryId = int(category_part)
    #print "self.categoryId = ", self.categoryId
    
    # 得到访问的协作组名字, 并初步验证其合法性.
    self.groupName = arr[arr_len - 3]   # 'manager'
    #print "self.groupName = ", self.groupName
    if isValidName(self.groupName) == False:
      return False
    
    return True


  # 得到用户要访问的分类对象.
  def getCategory(self):
    grcItemType = self.toGroupResourceCategoryItemType(self.group)
    #print "grcItemType = ", grcItemType
    # 0 表示所有文章.
    if self.categoryId == 0:
      category = Category()
      category.categoryId = 0
      category.itemType = grcItemType
      category.name = u"所有"
      return category
    
    # 得到分类.
    category = cate_svc.getCategory(self.categoryId)
    #print "category = ", category
    if category == None: return None
    
    # 如果分类不是协作组的资源分类则返回 null
    if category.itemType != grcItemType: return None
    
    return category

  # 得到协作组资源列表.
  def getResourceList(self, visitor_role):
    qry = GroupResourceQuery(""" r.resourceId, r.title, r.href, gr.isGroupBest, 
        u.loginName, u.nickName, r.createDate """)
    if self.isKtGroup==1:
      qry.includeChildGroup=True    
    qry.groupId = self.group.groupId
    qry.shareMode = 500
    if self.category != None and self.category.categoryId != 0:
      if self.isKtGroup==1:
        #需要汇聚下级组的文章，只能汇聚同名分类的文章
        qry.gartCateName = self.category.name
      else:  
        qry.gresCateId = self.category.categoryId
        #print "qry.gresCateId = ", qry.gresCateId
      
    pager = self.createPager()
    
    pager.totalRows = qry.count()
    resource_list = qry.query_map(pager)
    
    #print "resource_list = ", resource_list
    request.setAttribute("resource_list", resource_list) 
    request.setAttribute("pager", pager)    

  # 创建资源查询所用分页.
  def createPager(self):
    pager = self.params.createPager()
    pager.itemName = u'资源'
    pager.itemUnit = u'个'
    pager.pageSize = 20
    return pager
