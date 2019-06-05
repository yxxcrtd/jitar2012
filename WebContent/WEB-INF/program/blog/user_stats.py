from base_action import BaseAction
from cn.edustar.jitar.util import ParamUtil
from java.util import HashMap
from cn.edustar.jitar.util import FileCache
from cn.edustar.jitar.model import SiteUrlModel
from cn.edustar.jitar.model import ObjectType

userService = __jitar__.userService
pun_svc = __jitar__.UPunishScoreService

class user_stats(BaseAction):
    def execute(self):
        self.userName = request.getAttribute("loginName")
        self.user = userService.getUserByLoginName(self.userName)
        if self.user == None:
            response.getWriter().write(u"无法加载当前用户")
            return
        
        self.userName = request.getAttribute("UserName")        
        fc = FileCache()
        content = fc.getUserFileCacheContent(self.userName, "user_stats.html", 300)
        if content != "":
            response.getWriter().write(content)
            fc = None
            return
        
        userService.statForUser(self.user)    #这句容易出现死锁！！！！
        self.user = userService.getUserByLoginName(self.userName, False)
        templateProcessor = __spring__.getBean("templateProcessor")
        map = HashMap()
        map.put("user", self.user)
        
        #True 是罚分  False 是加分
        addscore=pun_svc.getScore(False,self.user.userId)
        map.put("addscore", addscore)

        minusscore=pun_svc.getScore(True,self.user.userId)
        map.put("minusscore", minusscore)
        
        content = templateProcessor.processTemplate(map, "/WEB-INF/user/default/user_stats.ftl", "utf-8")
        
        fc.writeUserFileCacheContent(self.userName, "user_stats.html", content)        
        response.getWriter().write(content)
        fc = None