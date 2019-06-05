from cn.edustar.jitar.util import ParamUtil, CommonUtil
from cn.edustar.jitar.jython import JythonBaseAction
from base_action import *

class crash_user(JythonBaseAction, ActionResult):
    def __init__(self):
        self.params = ParamUtil(request)
        
    def execute(self):
        user = self.loginUser
        if user == None:
            return ActionResult.LOGIN
        accessControlService = __spring__.getBean("accessControlService")
        if accessControlService.isSystemAdmin(user) or accessControlService.isSystemUserAdmin(user):
            userId = self.params.safeGetIntParam("userId")
            userService = __jitar__.userService
            deleteUser = userService.getUserById(userId)
            if deleteUser == None:
                self.addActionError(u"该用户不存在。")
                return ActionResult.ERROR
            
            if deleteUser.userStatus != 2:
                self.addActionError(u"该用户不属于待删除状态，不能进行彻底删除。")
                return ActionResult.ERROR
            
            #检查该用户是否是群组的创建者，需要先解除群组的创建者
            groupService = __jitar__.groupService
            glist = groupService.getAllUserCreatedGroupByUserId(deleteUser.userId)
            if glist != None and len(glist) > 0:
                request.setAttribute("glist", glist)
                return "/WEB-INF/ftl/admin/crash_user.ftl"
            
            deleteComplexObjectService = __spring__.getBean("deleteComplexObjectService")
            deleteComplexObjectService.deleteUser(deleteUser)
            self.addActionLink(u"返回", request.getHeader("Referer"))
            return ActionResult.SUCCESS
            
        else:
            self.addActionError(u"只有系统用户管理员才能进行删除用户。")
            return ActionResult.ERROR
