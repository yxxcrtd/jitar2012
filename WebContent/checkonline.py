from cn.edustar.jitar.util import ParamUtil
from java.lang import System

class checkonline:
    def __init__(self):
        self.params = ParamUtil(request)
        self.onlineService = __spring__.getBean("onlineService")
        self.configService = __spring__.getBean("configService")
        
    def execute(self):
        optTime = self.getTime()
        onlineUserNum = self.onlineService.getOnLineUesrNum(optTime)      
        onlineGuestNum = self.onlineService.getOnLineGuestNum(optTime)
        response.getWriter().write(str(onlineUserNum) + "|" + str(onlineGuestNum))
                
    def getTime(self):
        config = self.configService.getConfigure()
        onlineTime = config.getValue("site.user.online.time")
        return System.currentTimeMillis() - int(onlineTime) * 60 * 1000