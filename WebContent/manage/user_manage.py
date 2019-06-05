from base_action import ActionExecutor, ActionResult
from user_query import UserQuery
from cn.edustar.jitar.data import Command
from cn.edustar.jitar.pojos import AccessControl

# 用户管理入口.
class user_manage(ActionExecutor):
    def dispatcher(self, cmd):
        if self.loginUser == None:
            response.sendRedirect(request.getServletContext().getContextPath() + "/login.jsp")
            return None
    
        if self.canVisitUser(self.loginUser) == False:
            return ActionResult.ERROR
    
        # 判断是否有整站内容管理权限    
        canManage = False
        user = self.loginUser
        accessControlService = __spring__.getBean("accessControlService")
        if accessControlService.isSystemAdmin(self.loginUser) == False:
            control_list = accessControlService.getAllAccessControlByUser(user)
            if not(control_list == None or len(control_list) < 1):
                for ac in control_list:
                    if ac.objectType == AccessControl.OBJECTTYPE_SUPERADMIN or ac.objectType == AccessControl.OBJECTTYPE_SYSTEMUSERADMIN or ac.objectType == AccessControl.OBJECTTYPE_SYSTEMCONTENTADMIN :
                        canManage = True
        else:
            canManage = True
            
        if canManage == True:
            request.setAttribute("canManage", "true")
        else:
            request.setAttribute("canManage", "false")
            
        if cmd == "nav":
            return self.nav()
        elif cmd == "main":
            return self.main()
        else:
            return self.frame()
  
    # 显示框架页.
    def frame(self):
        url = self.params.getStringParam("url")
        if url == None or url == "": url = "?cmd=main"
        specialSubjectId = self.params.getIntParam("specialSubjectId")
        if specialSubjectId == None or specialSubjectId == "": specialSubjectId = "0"
        request.setAttribute("specialSubjectId", specialSubjectId)
        if url.find('?') >= 0:
            url = url + "&specialSubjectId=" + str(specialSubjectId)
        else:
            url = url + "?specialSubjectId=" + str(specialSubjectId)
        request.setAttribute("url", url)
        return "/WEB-INF/ftl/user/manage_index.ftl"
  
    # 显示导航菜单.
    def nav(self):
        plugin_svc = __spring__.getBean("pluginService")
        plugin_list = plugin_svc.getPluginList()
        accessControlService = __spring__.getBean("accessControlService")
        aclist = accessControlService.getAllAccessControlByUserAndObjectType(self.loginUser, AccessControl.OBJECTTYPE_CUSTORMCOLUMNADMIN)
        request.setAttribute("aclist", aclist)
        request.setAttribute("plugin_list", plugin_list)
        request.setAttribute("passportURL", "")
        if request.getServletContext().getServletRegistration("passportClientInit")!=None:
            self.passportURL = PassportClient.getInstance().getPassportURL()
            if self.passportURL == None:
                self.passportURL = ""
            if self.passportURL == "http://":
                self.passportURL = ""
            if self.passportURL != "" :
                if self.passportURL[len(self.passportURL) - 1] != "/":
                    self.passportURL += "/"
            request.setAttribute("passportURL", self.passportURL)
        
        return "/WEB-INF/ftl/user/manage_left.ftl"
  
    # 显示欢迎页.
    def main(self):
        qry = UserQuery(""" u.userId, u.loginName, u.userIcon, u.gender, u.trueName, u.email, u.blogName, u.blogIntroduce, 
                        u.myArticleCount, u.otherArticleCount, u.historyMyArticleCount, u.historyOtherArticleCount, u.visitCount,u.resourceCount,
                        u.commentCount,u.photoCount,u.subjectId, u.gradeId, u.positionId,
                        subj.subjectName, grad.gradeName, unit.unitTitle, sc.name
             """)
        qry.userStatus = None
        qry.loginName = self.loginUser.loginName
        users = qry.query_map()
        if users.size() == 0:
            response.sendRedirect("../login.jsp")
        
        user = users[0]
        request.setAttribute("user", user)
    
        # TODO: 下面的查询用 XxxQuery 改写, 现在统计不对.
        hql = """ select count(*) as groupCount
                          FROM GroupMember gm
                          where gm.userId = """ + str(self.loginUser.userId) + """
                         """
        cmd = Command(hql)    
        groupCount = cmd.int_scalar()
        request.setAttribute("groupCount", groupCount)
        
        hql = """ select count(*) as messageCount
                          FROM Message m
                          where m.receiveId = """ + str(self.loginUser.userId) + """
                         """
        cmd = Command(hql)    
        messageCount = cmd.int_scalar()
        request.setAttribute("messageCount", messageCount)
        
        hql = """ select count(*) as friendCount
                          FROM Friend f
                          where f.userId = """ + str(self.loginUser.userId) + """
                         """
        cmd = Command(hql)    
        friendCount = cmd.int_scalar()
        request.setAttribute("friendCount", friendCount)
        
        return "/WEB-INF/ftl/user/manage_main.ftl" 
