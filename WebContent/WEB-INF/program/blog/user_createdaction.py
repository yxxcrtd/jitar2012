from cn.edustar.jitar.util import ParamUtil
from cn.edustar.jitar.data import Command
from base_blog_page import *
from base_action import BaseAction
from action_query import ActionQuery
from java.util import HashMap
from cn.edustar.jitar.util import FileCache
from cn.edustar.jitar.model import SiteUrlModel

user_svc = __jitar__.userService

class user_createdaction(BaseAction, RequestMixiner, ResponseMixiner):
    
    def __init__(self):
        self.params = ParamUtil(request)
        
    def execute(self):
        self.loginName = request.getAttribute("loginName")
          
        writer = response.getWriter()

        # 加载当前用户对象.
        self.user = user_svc.getUserByLoginName(self.loginName)
        if self.user == None:
            writer.write(u"无法加载当前用户。")
            return
        
        self.userName = self.user.loginName
        fc = FileCache()
        content = fc.getUserFileCacheContent(self.userName, "user_createdaction.html",14400)
        if content != "":
            response.getWriter().write(content)
            fc = None
            return
        
        #得到当前用户的创建的活动
        qry = ActionQuery(""" act.title, act.createDate, act.actionId, act.ownerId, act.ownerType, act.createUserId, act.actionType,
                              act.description, act.userLimit, act.startDateTime,act.finishDateTime, act.attendLimitDateTime, act.place,
                              act.status, act.visibility, act.attendCount
                            """)
        #qry.ownerType = "user"
        qry.status = 0
        qry.createUserId = self.user.userId        
        action_list = qry.query_map(10)
        
        templateProcessor = __spring__.getBean("templateProcessor")
        map = HashMap()
        map.put("action_list", action_list)
        map.put("user", self.user)
        map.put("UserSiteUrl", self.getUserSiteUrl())
        content = templateProcessor.processTemplate(map, "/WEB-INF/user/default/user_createdaction.ftl", "utf-8")
        
        fc.writeUserFileCacheContent(self.userName, "user_createdaction.html",content)        
        response.getWriter().write(content)
        fc = None
        
    def getUserSiteUrl(self):
        siteUrl = SiteUrlModel.getSiteUrl()
        userSiteUrl = request.getSession().getServletContext().getInitParameter("userUrlPattern");
        if userSiteUrl == None or userSiteUrl == "":
            userSiteUrl = siteUrl + "u/" + self.userName + "/"
        else:
            userSiteUrl = userSiteUrl.replace("{loginName}", self.userName)            
        return userSiteUrl