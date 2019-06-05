# coding=utf-8
from base_action import *
from user_query import UserQuery
from article_query import ArticleQuery
from resource_query import ResourceQuery
from photo_query import PhotoQuery
from group_query import GroupQuery
from base_action import ActionResult
from cn.edustar.jitar.pojos import User, Article, Resource, Photo, Group, AccessControl, TimerCount
from cn.edustar.jitar.model import Configure
from cn.edustar.jitar.util import CommonUtil
from base_manage import *

# 后台管理框架的页面.
class admin (BaseManage):
  # 定义要返回的页面常量.
  ADMIN_MAIN = "/WEB-INF/ftl/admin/main.ftl"
    
  def execute(self):
    # 验证用户必须具有管理权限.
    # 本页面的后台管理需要的权限有：系统管理员，系统用户管理员，系统内容管理员    

    canManage = False
    if self.isSystemAdmin():
        canManage = True
        request.setAttribute("isSystemAdmin", "1")
    
    if self.isSystemUserAdmin():
        canManage = True
        request.setAttribute("isSystemUserAdmin", "1")
        
    if self.isSystemContentAdmin():
        canManage = True
        request.setAttribute("isSystemContentAdmin", "1")      
        
   
    if canManage == False:
        self.addActionError(u"没有管理权限，需要的权限为系统管理员、系统用户管理员和系统内容管理员.")
        return ActionResult.ERROR
    
    cmd = request.getParameter("cmd")
    if cmd == "menu":
      autoHtml = __jitar__.configService.getConfigure().getBoolValue(Configure.SITE_AUTO_HTML, True)
      request.setAttribute("autoHtml", autoHtml)      
      plugin_svc = __spring__.getBean("pluginService")
      plugin_list = plugin_svc.getPluginList()
      request.setAttribute("plugin_list", plugin_list)
      # 判断是否有频道可以管理
      channelPageService = __spring__.getBean("channelPageService")
      hasChennels = channelPageService.getChannelList()  
      request.setAttribute("hasChennels", hasChennels)
      webSiteManageService = __spring__.getBean("webSiteManageService")
      bklist = webSiteManageService.getBackYearList("article")
      if bklist != None and len(bklist) > 0:
        request.setAttribute("bklist", bklist)
      
      jitarColumnService = __spring__.getBean("jitarColumnService")
      columnlist = jitarColumnService.getJitarColumnList()
      if columnlist != None and len(columnlist) > 0:
        request.setAttribute("columnlist", columnlist)
      userService = __jitar__.userService
      userTypeList = userService.getAllUserType()
      if userTypeList != None and len(userTypeList) > 0:
        request.setAttribute("userTypeList", userTypeList)
      return "/WEB-INF/ftl/admin/menu.ftl"
    elif cmd == "main":
      return self.main()
    else:
      url = self.params.getStringParam("url")
      if url == None or url == "": url = "?cmd=main"
      request.setAttribute("url", url)
      return "/WEB-INF/ftl/admin/index.ftl"


  # 后台管理的统计数据
  def main(self):
    request.setAttribute("site_stat", __spring__.getBean("timerCountService").getTimerCountById(TimerCount.COUNT_TYPE_SITE))
    return self.ADMIN_MAIN
