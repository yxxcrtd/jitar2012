from cn.edustar.jitar.pojos import Video
from video_query import *
from base_action import *
from cn.edustar.jitar.util import ParamUtil
from cn.edustar.jitar.jython import BaseAdminAction

class admin_specialsubject_video_list(ActionExecutor):
    def __init__(self):
        self.params = ParamUtil(request)
        self.specialSubjectId = 0
    
    def execute(self):
        if self.loginUser == None:
            self.addActionError(u"请先登录。")
            return self.LOGIN    
        
        accessControlService = __spring__.getBean("accessControlService")
        if False == accessControlService.isSystemContentAdmin(self.loginUser):
            self.addActionError(u"管理专题需要管理员权限。")
            return self.ERROR
        
        self.specialSubjectId = self.params.safeGetIntParam("specialSubjectId")
        
        if self.specialSubjectId == 0:
            self.addActionError(u"无效的专题标识。")
            return self.ERROR
        
        if request.getMethod() == "POST":
            videoService = __spring__.getBean("videoService")
            
            guid = self.params.safeGetIntValues("guid")
            for g in guid:
                video = videoService.findById(g)
                if video != None:
                    video.setSpecialSubjectId(None)
                    videoService.updateVideo(video)
                
        
        qry = VideoQuery(""" v.title, v.videoId, v.userId, v.createDate, v.flvHref, v.flvThumbNailHref """)
        qry.specialSubjectId = self.specialSubjectId
        pager = self.params.createPager()
        pager.itemName = u"视频"
        pager.itemUnit = u"个"
        pager.pageSize = 20
        pager.totalRows = qry.count()
        video_list = qry.query_map(pager)
        request.setAttribute("video_list", video_list)
        request.setAttribute("pager", pager)
        
        userUrlPattern = request.getSession().getServletContext().getInitParameter("userUrlPattern")
        if userUrlPattern != None and userUrlPattern != "":
            request.setAttribute("userUrlPattern", userUrlPattern)
        return "/WEB-INF/ftl/specialsubject/admin_specialsubject_video_list.ftl"
