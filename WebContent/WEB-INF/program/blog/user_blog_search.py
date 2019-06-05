from base_action import BaseAction
from java.util import HashMap
from cn.edustar.jitar.util import FileCache

class user_blog_search(BaseAction):
    def execute(self):
        self.userName = request.getAttribute("loginName")
        userService = __jitar__.userService
        user = userService.getUserByLoginName(self.userName)
        if user == None:
            response.writer.println(u"不能加载该用户的信息")
            return
        if self.canVisitUser(user) == False:
            response.writer.println(u"用户  %s 无法访问." % self.userName)
            return
        fc = FileCache()
        # 14400 为  10 天
        content = fc.getUserFileCacheContent(self.userName, "user_blog_search.html",144000)
        if content != "":
            response.getWriter().write(content)
            fc = None
            return
        
        templateProcessor = __spring__.getBean("templateProcessor")
        map = HashMap()
        map.put("user", user)
        content = templateProcessor.processTemplate(map, "/WEB-INF/user/default/blog_search.ftl", "utf-8")
        
        fc.writeUserFileCacheContent(self.userName, "user_blog_search.html",content)        
        response.getWriter().write(content)
        fc = None