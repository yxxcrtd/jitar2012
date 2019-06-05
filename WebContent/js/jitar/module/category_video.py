from cn.edustar.jitar.util import ParamUtil
from video_query import VideoQuery
from cn.edustar.jitar.util import FileCache
from cn.edustar.jitar.model import SiteUrlModel

class category_video:
    def execute(self):
        params = ParamUtil(request)
        self.userName = params.safeGetStringParam("loginName")
        title = params.safeGetStringParam("title")
        userCateId = params.safeGetIntParam("userCateId")
        #print "-----------------------------category_video.py-----------------------------"
        #print "self.userName="+self.userName
        #print "title="+title
        #print "userCateId="+str(userCateId)
        
        if userCateId == 0:
            response.getWriter().write(u"<div style='text-align:center;'>请选择一个个人视频分类。</div>")
            return
        
        fc = FileCache()
        content = fc.getUserFileCacheContent(self.userName, "category_video_" + str(userCateId) + ".html",30)
        if content != "":
            response.getWriter().write(content)
            fc = None
            return
        
        count = params.safeGetIntParam("count")
        if count == 0 : count = 9
        #print "count="+str(count)
        user = __jitar__.userService.getUserByLoginName(self.userName)
        if user == None:
            response.getWriter().write(u"不能加载当前用户。")
            return
        qry = VideoQuery(""" v.videoId, v.title, v.createDate, v.href, v.flvHref, v.userId, u.loginName, u.nickName, v.summary,v.flvThumbNailHref """)
        qry.userCateId = userCateId
        qry.userId = user.userId
        video_list = qry.query_map(count)
        templateProcessor = __spring__.getBean("templateProcessor")
        map = HashMap()
        map.put("video_list", video_list)
        map.put("UserSiteUrl", self.getUserSiteUrl())
        map.put("userCateId", userCateId)
        map.put("title", title)
        map.put("user", user)       
        content = templateProcessor.processTemplate(map, "/WEB-INF/user/default/category_video.ftl", "utf-8")
        
        fc.writeUserFileCacheContent(self.userName, "category_video_" + str(userCateId) + ".html",content)        
        response.getWriter().write(content)
        fc = None
    
    def getUserSiteUrl(self):
        siteUrl = SiteUrlModel.getSiteUrl()
        userSiteUrl = request.getSession().getServletContext().getInitParameter("userUrlPattern")
        if userSiteUrl == None or userSiteUrl == "":
            userSiteUrl = siteUrl + "u/" + self.userName + "/"
        else:
            userSiteUrl = userSiteUrl.replace("{loginName}", self.userName)            
        return userSiteUrl