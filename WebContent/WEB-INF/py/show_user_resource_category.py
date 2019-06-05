# script by ljx

from cn.edustar.jitar.pojos import Category
from base_blog_page import *
from base_action import BaseAction
# 本模块全局变量.
user_svc = __jitar__.userService
cate_svc = __jitar__.categoryService

# 显示一个用户的指定资源分类页面.
class show_user_resource_category(BaseAction, RequestMixiner, ResponseMixiner, PageCheckMixiner, CategoryMixiner):
  def execute(self):
    # 解析 uri.
    if self.parseUri() == False:
      return self.sendNotFound(self.uri)
    
    # 得到要工作室主人, 并验证用户状态.
    self.user = user_svc.getUserByLoginName(self.loginName)
    request.setAttribute("user", self.user)
    #print "self.user = ", self.user
    if self.canVisitUser(self.user) == False:
      return self.ACCESS_ERROR
    
    
    # 得到要访问的分类, 要验证分类的确属于该用户.
    self.category = self.getCategory()
    #print "self.category = ", self.category
    if self.category == None: 
      return self.sendNotFound()
    request.setAttribute("category", self.category)

    # 得到资源分类显示页面.
    page = self.getUserResourceCategoryPage(self.user)
    #print "page = ", page
    if page == None:
      return self.sendNotFound()
    if page.customSkin != None:
      customSkin = JSONValue.parse(page.customSkin)
      request.setAttribute("customSkin", customSkin)
    request.setAttribute("page", page)

    # 得到页面功能块.
    widget_list = self.getPageWidgets(page)
    #print "widget_list = ", widget_list
    request.setAttribute("widget_list", widget_list)
    request.setAttribute("widgets", widget_list)
    
    return "/WEB-INF/user/default/resource_category_index.ftl"
    
    
  # 解析 uri, 从中获取要访问的 loginName, categoryId.
  def parseUri(self):
    self.uri = self.getRequestURI()
    #print "self.uri =", self.uri
    if self.uri == None or self.uri == "":
      return False
    
    # 例子: /Groups/liujunxing/rescate/0.html -> 
    #   ['', 'Groups', 'liujunxing', 'rescate', '0.html']
    # 其中最后一个是分类标识+'.html', 倒数第3个是用户登录名.
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
    
    # 得到访问的用户名, 并初步验证其合法性.
    self.loginName = arr[arr_len - 3]   # 'admin'
    # print "self.loginName = ", self.loginName
    if isValidName(self.loginName) == False:
      return False
    
    return True
    
    
  # 得到用户要访问的资源分类对象.
  def getCategory(self):
    uacItemType = self.toUserResourceCategoryItemType(self.user)
    # 0 表示所有资源.
    if self.categoryId == 0:
      category = Category()
      category.categoryId = 0
      category.itemType = uacItemType
      category.name = u"所有资源"
      return category
    
    # 得到分类.
    category = cate_svc.getCategory(self.categoryId)
    if category == None: return None
    
    # 如果分类不是用户的资源分类则返回 null
    if category.itemType != uacItemType: return None
    
    return category
  
    