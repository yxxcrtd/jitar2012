from base_preparecourse_page import *
from base_action import BaseAction

class VideoToPC(BaseAction):
    def __init__(self):
        self.params = ParamUtil(request)
        
    def execute(self):
        if self.loginUser == None:
            response.getWriter().println(u"请先登录。")
            return
        pcId = self.params.safeGetIntParam("pcid")
        videoId = self.params.safeGetIntParam("videoId")
        state = self.params.safeGetStringParam("state")            
        prepareCourseService = __spring__.getBean("prepareCourseService")
        videoService = __spring__.getBean("videoService")
        video = videoService.findById(videoId)
        if video == None or video.userId == None:
            response.getWriter().println(u"不能加载视频")
            return
        
        if prepareCourseService.checkUserExistsInPrepareCourse(pcId,video.userId) == False:
            response.getWriter().println(u"不是集备成员")
            return        
        
        if state == "Add":
            if prepareCourseService.checkVideoInPrepareCourse(pcId,videoId) == False:                
                prepareCourseService.insertVideoToPrepareCourse(pcId, videoId,video.userId,video.title)
            response.getWriter().println("Added")
        else:
            prepareCourseService.removePrepareCourseVideo(pcId,videoId)
            response.getWriter().println("Removed")
        return