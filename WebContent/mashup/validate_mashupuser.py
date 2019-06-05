from cn.edustar.jitar.util import ParamUtil
from java.util import Date

class validate_mashupuser:
    def __init__(self):
        self.params = ParamUtil(request)
        
    def execute(self):
        userGuid = self.params.safeGetStringParam("g")
        fromType = self.params.safeGetStringParam("from")
        if userGuid == "":
            response.getWriter().write("no user assigned")
            return
        mashupService = __spring__.getBean("mashupService")
        if fromType == "1":
            #本平台用户
            userService = __spring__.getBean("userService")
            u = userService.getUserByGuid(userGuid)
            if u == None:
                response.getWriter().write("NOT LOGIN")
            else:
                onlineService = __spring__.getBean("onlineService")
                if None == onlineService.findUserOnLineByUserName(u.loginName):
                    response.getWriter().write("NOT LOGIN")
                else:            
                    response.getWriter().write("OK")
            return
            
        
        # 先删除过期的登录
        mashupService.deleteAllInValidMashupUser() 
        mashupUser = mashupService.getMashupUserByGuid(userGuid)
        if mashupUser == None:
            response.getWriter().write("NOT LOGIN")
        else:            
            mashupUser.setLastUpdated(Date())
            response.getWriter().write("OK")
        return