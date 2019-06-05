from cn.edustar.jitar.util import ParamUtil

class validate_user:
    def __init__(self):
        self.params = ParamUtil(request)
        
    def execute(self):
        userEncGuid = self.params.safeGetStringParam("g")
        if userEncGuid == "":
            response.getWriter().write("no user assigned")
            return
        userService = __spring__.getBean("userService")
        user = userService.getUserByGuid(userEncGuid)
        if user == None:
            response.getWriter().write("cann't load user")
            return
        onlineService = __spring__.getBean("onlineService")
        
        if None == onlineService.findUserOnLineByUserName(user.loginName):
            response.getWriter().write("NOT LOGIN")
        else:            
            response.getWriter().write("OK")
        return