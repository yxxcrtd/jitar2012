from cn.edustar.jitar.util import ParamUtil
from java.util import HashMap
from cn.edustar.jitar.util import FileCache
from cn.edustar.jitar.model import SiteUrlModel

class lastest_comments:
    def __init__(self):
        self.commentService = __jitar__.commentService
        self.params = ParamUtil(request)
    
    def execute(self):
        aboutUserId = request.getParameter("userId")
        count = self.params.safeGetIntParam("count")
        self.userName = self.params.safeGetStringParam("userName")
        if aboutUserId == None or aboutUserId == "":
            return self.notFound() 
        try:
            aboutUserId = int(aboutUserId)
        except ValueError:
            return self.notFound()
        
        fc = FileCache()
        content = fc.getUserFileCacheContent(self.userName, "user_lastest_comments.html",30)
        if content != "":
            response.getWriter().write(content)
            fc = None
            return
        
        comment_list = self.commentService.getRecentCommentAboutUser(aboutUserId, int(count))
        templateProcessor = __spring__.getBean("templateProcessor")
        map = HashMap()
        map.put("comment_list", comment_list)
        content = templateProcessor.processTemplate(map, "/WEB-INF/user/default/user_lastest_comments.ftl", "utf-8")
        fc.writeUserFileCacheContent(self.userName, "user_lastest_comments.html",content)        
        response.getWriter().write(content)
        fc = None
  
    def notFound(self):
        return '/WEB-INF/ftl/Error.ftl'