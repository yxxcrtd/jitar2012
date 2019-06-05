from cn.edustar.jitar.util import ParamUtil
from cn.edustar.jitar.jython import JythonBaseAction

class getAccessControl(JythonBaseAction):
    def execute(self):
        if self.loginUser == None:
            return
        params = ParamUtil(request)
        userId = params.safeGetIntParam("userId")
        user = __spring__.getBean("userService").getUserById(userId)
        if user == None:
            return
        accessControlService = __spring__.getBean("accessControlService")
        aclist = accessControlService.getAllAccessControlByUser(user)
        if aclist == None or len(aclist) < 1:
            return
        #print "aclist", aclist
        request.setAttribute("aclist", aclist)
        return "/WEB-INF/ftl/admin/getAccessControl.ftl"
