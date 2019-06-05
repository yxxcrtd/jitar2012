from base_action import BaseAction
from base_blog_page import *
from com.alibaba.fastjson import JSONObject

# 本模块全局变量.
user_svc = __jitar__.userService

# 显示一个用户的用户档案页面.
class show_user_profile(BaseAction, RequestMixiner, ResponseMixiner, PageCheckMixiner):
  # 页面地址.
  PAGE_FTL = "/WEB-INF/user/default/show_user_profile.ftl"
  
  def execute(self):
    self.loginName = request.getAttribute("loginName")
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
      customSkin = JSONObject.parse(self.page.customSkin)
      request.setAttribute("customSkin", customSkin)
      
    # 显示该页面.
    return self.getWidgetsAndReturn(self.page, self.PAGE_FTL)