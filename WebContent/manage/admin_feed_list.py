from javax.servlet.http import Cookie
from cn.edustar.jitar.util import ParamUtil

class admin_feed_list:
    def __init__(self):
        self.params = ParamUtil(request)
    
    def execute(self):
        pwd = self.params.safeGetStringParam("pwd")
        self.feedbackService = __spring__.getBean("feedbackService")        
        if pwd != "":
            if self.feedbackService.getUserData() == pwd:
                self.saveToCookie()

        if self.getCookie() == "":
            return "/WEB-INF/ftl/admin/admin_feed_nothing.ftl"
        if request.getMethod() == "POST":
            self.delete()
        feedlist = self.feedbackService.getAllFeedbackList()
        request.setAttribute("feedlist",feedlist)
        return "/WEB-INF/ftl/admin/admin_feed_list.ftl"        
    
    def delete(self):
        guids = self.params.safeGetIntValues("guid")
        for g in guids:
            f = self.feedbackService.getFeedbackById(g)
            if f != None:
                self.feedbackService.deleteFeedback(f)
    
    def saveToCookie(self):
        namecookie = Cookie("m","m")
        namecookie.setMaxAge(300)
        namecookie.setPath("/")
        response.addCookie(namecookie)
    
    def getCookie(self):
        cookie = ""
        cookies = request.getCookies()
        if cookies == None:
            return cookie
        for c in cookies:
            if c.getName() == "m":
                 cookie = c.getValue()
        return cookie