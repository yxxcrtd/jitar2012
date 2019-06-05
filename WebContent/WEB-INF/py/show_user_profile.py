# script by ljx

from base_action import BaseAction
from base_blog_page import *
from cn.edustar.jitar.util.json import *

# 本模块全局变量.
user_svc = __jitar__.userService


# 显示一个用户的用户档案页面.
class show_user_profile(BaseAction, RequestMixiner, ResponseMixiner, PageCheckMixiner):
  # 页面地址.
  PAGE_FTL = "/WEB-INF/user/default/full_profile_page.ftl"
  
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
    
    # 得到用户档案显示页面.
    self.page = self.getUserProfilePage(self.user)
    # print "self.page = ", self.page
    if self.page == None:
      return self.sendNotFound()
    request.setAttribute("page", self.page)
    if self.page.customSkin != None:
      customSkin = JSONValue.parse(self.page.customSkin)
      request.setAttribute("customSkin", customSkin)
      
    # 显示该页面.
    return self.getWidgetsAndReturn(self.page, self.PAGE_FTL)
    
    
  # 解析 uri, 从中获取要访问的 loginName.
  def parseUri(self):
    self.uri = self.getRequestURI()
    #print "self.uri =", self.uri
    if self.uri == None or self.uri == "":
      return False
    
    # 例子: /Groups/admin/profile -> 
    #   ['', 'Groups', 'admin', 'profile']
    # 其中最后一个 profile, 倒数第二个是用户名.
    arr = self.uri.split('/')
    arr_len = len(arr)
    if arr_len < 2:
      return False
    
    # 得到访问的用户名, 并初步验证其合法性.
    self.loginName = arr[arr_len - 2]   # 'admin'
    # print "self.loginName = ", self.loginName
    if isValidName(self.loginName) == False:
      return False
    
    return True
    
    