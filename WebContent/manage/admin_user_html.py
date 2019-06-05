from cn.edustar.jitar.jython import BaseAdminAction
from cn.edustar.jitar.util import ParamUtil
from cn.edustar.jitar.pojos import Config, User
from base_action import ActionResult

class admin_user_html(BaseAdminAction, ActionResult):
    def __init__(self):
        self.cfg_svc = __spring__.getBean('configService')
        self.accessControlService = __spring__.getBean('accessControlService')

    def execute(self):
        userService = __jitar__.userService
        userHtmlService = __spring__.getBean('userHtmlService')
        params = ParamUtil(request)
        if params.existParam("userId"):
            userId = params.safeGetIntParam("userId")
            user = userService.getUserById(userId)
            if user != None:
                userHtmlService.GenUserIndex(user)
                if params.existParam("from"):                        
                    userHtmlService.GenUserArticleList(user)
                    userHtmlService.GenUserProfile(user)
                    userHtmlService.GenUserResourceList(user)
                    userHtmlService.GenUserPhotoList(user)
                    userHtmlService.GenUserCreateActionList(user)
                    userHtmlService.GenUserJoinedActionList(user)
                    userHtmlService.GenUserCreatePrepareCourseList(user)
                    userHtmlService.GenUserJoinedPrepareCourseList(user)
                    userHtmlService.GenUserQuestionList(user)
                    userHtmlService.GenUserVoteList(user)
                    userHtmlService.GenUserGroupList(user)
                    userHtmlService.GenUserLeaveWordList(user)
                    userHtmlService.GenUserFriendList(user)
                    userHtmlService.GenUserSpecialTopic(user)
                userHtmlService = None
                userService = None
                user = None
            self.addActionMessage(u"生成成功。")
            return ActionResult.SUCCESS        
        
        if self.loginUser == None: 
            return ActionResult.LOGIN
        
        if self.accessControlService.isSystemAdmin(self.loginUser) == False:
            self.addActionError(u"没有管理站点配置的权限，只有超级管理员才能进行管理。")
            return ActionResult.ERROR
        maxId = userService.getMaxUserId()
        minId = userService.getMinUserId()
        request.setAttribute("maxId",maxId)
        request.setAttribute("minId",minId)
        return "/WEB-INF/ftl/admin/admin_user_html.ftl"        