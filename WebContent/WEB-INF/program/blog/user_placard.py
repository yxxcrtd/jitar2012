from cn.edustar.jitar.util import ParamUtil
from base_action import BaseAction
from java.util import HashMap
from cn.edustar.jitar.util import FileCache
from placard_query import PlacardQuery

class user_placard(BaseAction):
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
        content = fc.getUserFileCacheContent(self.userName, "user_placard.html",30)
        if content != "":
            response.getWriter().write(content)
            fc = None
            return
        qry = PlacardQuery("pld.title, pld.createDate, pld.content")
        qry.objType = 1
        qry.objId = user.getUserId()
        placard_list = qry.query_map(1)
        
        templateProcessor = __spring__.getBean("templateProcessor")
        map = HashMap()
        map.put("user", user)
        map.put("placard_list", placard_list)
        content = templateProcessor.processTemplate(map, "/WEB-INF/user/default/user_placard.ftl", "utf-8")
        
        fc.writeUserFileCacheContent(self.userName, "user_placard.html",content)        
        response.getWriter().write(content)
        fc = None