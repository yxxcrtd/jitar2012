from java.util import HashMap
from cn.edustar.jitar.util import FileCache
from cn.edustar.jitar.model import SiteUrlModel
from video_query import VideoQuery
from cn.edustar.jitar.util import ParamUtil

class user_video:

    def execute(self):
        self.params = ParamUtil(request)     
        userId = self.params.safeGetIntParam("userId", 0)
        listCount = self.params.safeGetIntParam("count", 2)
        if(userId == 0) : 
            request.setAttribute("MessageText", "没有找到所查询的视频！")
            return "/WEB-INF/ftl/show_message.ftl";
        user = __jitar__.userService.getUserById(userId)
        if user == None: 
            request.setAttribute("MessageText", "没有找到所查询的图片")
            return "/WEB-INF/ftl/show_message.ftl";
        
        self.userName = user.loginName
        
        fc = FileCache()
        # 14400 为10 天
        content = fc.getUserFileCacheContent(self.userName, "user_video.html",30)
        if content != "":
            response.getWriter().write(content)
            fc = None
            return
        
        qry = VideoQuery(""" v.videoId, v.title, v.createDate, v.lastModified, v.flvThumbNailHref, v.href, u.nickName, u.loginName, u.userIcon """)
        qry.userId = userId
        qry.orderType = 0
        qry.isPrivateShow = None
        video_list = qry.query_map(int(listCount))
        
        templateProcessor = __spring__.getBean("templateProcessor")
        map = HashMap()
        map.put("video_list", video_list)
        map.put("UserSiteUrl", self.getUserSiteUrl())
        content = templateProcessor.processTemplate(map, "/WEB-INF/user/default/user_video.ftl", "utf-8")
        
        fc.writeUserFileCacheContent(self.userName, "user_video.html",content)        
        response.getWriter().write(content)
        fc = None
    
    def getUserSiteUrl(self):
        siteUrl = SiteUrlModel.getSiteUrl()
        userSiteUrl = request.getSession().getServletContext().getInitParameter("userUrlPattern");
        if userSiteUrl == None or userSiteUrl == "":
            userSiteUrl = siteUrl + "u/" + self.userName + "/"
        else:
            userSiteUrl = userSiteUrl.replace("{loginName}", self.userName)            
        return userSiteUrl