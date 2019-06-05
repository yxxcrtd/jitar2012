# script by ljx

from cn.edustar.jitar.pojos import User
from base_action import BaseAction
from base_blog_page import *
from cn.edustar.jitar.data import Command
from cn.edustar.jitar.util.json import *

user_svc = __jitar__.userService 

# 显示用户工作室首页.
class show_user_index(BaseAction, RequestMixiner, ResponseMixiner, PageCheckMixiner):
  # 页面地址.
  PAGE_FTL = "/WEB-INF/user/default/index.ftl"

  def execute(self):
    #print "show_user_index : uri = ", self.getRequestURI()
    
    #print "url = ",request.getParameter("base")
    # 解析 uri.
    if self.parseUri() == False:
      return self.sendNotFound(self.uri)
    
    # 得到要工作室主人, 并验证用户状态.
    self.user = user_svc.getUserByLoginName(self.loginName)
    request.setAttribute("user", self.user)
    #print "self.user = ", self.user
    if self.canVisitUser(self.user) == False:
      return self.ACCESS_ERROR
   
    # 计数器
    try:
      sql = """ UPDATE User Set visitCount = visitCount + 1 Where userId = """ + str(self.user.userId)
      Command(sql).update()
    except:
      pass  
  
    # 更新用户统计表
    try:
      sql1 = """ UPDATE UserStat Set visitCount = visitCount + 1 Where userId = """ + str(self.user.userId)
      Command(sql1).update()
    except:
      pass
    
    # 得到访问者对象, 并计算其身份.
    self.visitor = self.getLoginUser()
    visitor_role = self.calcVisitorRole(self.visitor, self.user)
    request.setAttribute("visitor", self.visitor)
    request.setAttribute("visitor_role", visitor_role)
    
    # 得到用户的首页.
    self.page = self.getUserIndexPage(self.user)
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
    
    # 例子: /Groups/admin, /Groups/u/admin -> 
    #   ['', 'Groups', 'admin']   ['', 'Groups', 'u', 'admin']
    # 其中最后一个是用户名.
    arr = self.uri.split('/')
    arr_len = len(arr)
    if arr_len < 1:
      return False
    
    # 得到访问的用户名, 并初步验证其合法性.
    self.loginName = arr[arr_len - 1]   # 'admin'
    # print "self.loginName = ", self.loginName
    if isValidName(self.loginName) == False:
      return False
    
    return True
    

  # 计算访客在用户首页的权限.
  def calcVisitorRole(self, visitor, user):
    role = "guest"      # 缺省为访客.
    # 如果未登录或者访客被锁定等则身份为访客, 如果用户被删除按理说不能登录.
    if visitor == None : return role
    if visitor.userStatus != User.USER_STATUS_NORMAL:
      return role
    
    # 如果工作室主人状态不正常(如锁定), 则任何人都是访客, 包括工作室主人自己.
    if user.userStatus != User.USER_STATUS_NORMAL:
      return role
    
    # 如果是自己访问自己则角色是 'admin'
    if user.userId == visitor.userId:
      return "admin"
    
    # TODO: 好友以后支持!.
    
    # 否则都是访客.
    return role