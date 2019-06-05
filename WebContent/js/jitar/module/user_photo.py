from java.util import HashMap
from cn.edustar.jitar.util import FileCache
from cn.edustar.jitar.model import SiteUrlModel
from photo_query import PhotoQuery
from cn.edustar.jitar.util import ParamUtil

class user_photo:

    def execute(self):
        self.params = ParamUtil(request)     
        self.type = self.params.getIntParam("type")
        userId = self.params.safeGetIntParam("userId", 0)
        listCount = self.params.safeGetIntParam("count", 4)
        user = __jitar__.userService.getUserById(userId)
        if user == None: 
            request.setAttribute("MessageText", "没有找到所查询的图片")
            return "/WEB-INF/ftl/show_message.ftl";
        else:
            self.userName = user.loginName
            fc = FileCache()
            if self.type == 2:
                content = fc.getUserFileCacheContent(self.userName, "user_photo2.html", 60)
            else:
                content = fc.getUserFileCacheContent(self.userName, "user_photo.html", 60)
            if content != "":
                response.getWriter().write(content)
                fc = None
                return
            
            qry = PhotoQuery(""" p.photoId, p.title, p.createDate, p.lastModified, p.href,
                           u.nickName, u.loginName, u.userIcon, sc.name """)
            qry.userId = userId
            qry.orderType = 0
            qry.isPrivateShow = None
            photo_list = qry.query_map(int(listCount))
            
            templateProcessor = __spring__.getBean("templateProcessor")
            map = HashMap()
            map.put("photo_list", photo_list)
            map.put("UserSiteUrl", self.getUserSiteUrl())
            if self.type == 2:
                content = templateProcessor.processTemplate(map, "/WEB-INF/user/default/user_photo2.ftl", "utf-8")
                fc.writeUserFileCacheContent(self.userName, "user_photo2.html", content)
            else:
                content = templateProcessor.processTemplate(map, "/WEB-INF/user/default/user_photo.ftl", "utf-8")
                fc.writeUserFileCacheContent(self.userName, "user_photo.html", content)
            response.getWriter().write(content)
            fc = None
            
            #if self.type == 2:
            #    return "WEB-INF/user/default/user_photo2.ftl"
            #else:
            #    return "WEB-INF/user/default/user_photo.ftl"
            
    def getUserSiteUrl(self):
        siteUrl = SiteUrlModel.getSiteUrl()
        userSiteUrl = request.getSession().getServletContext().getInitParameter("userUrlPattern");
        if userSiteUrl == None or userSiteUrl == "":
            userSiteUrl = siteUrl + "u/" + self.userName + "/"
        else:
            userSiteUrl = userSiteUrl.replace("{loginName}", self.userName)            
        return userSiteUrl
