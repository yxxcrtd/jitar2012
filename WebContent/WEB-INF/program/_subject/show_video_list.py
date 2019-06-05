from subject_page import *
from video_query import VideoQuery

class show_video_list(BaseSubject):
    def __init__(self):
        BaseSubject.__init__(self)
        self.specialSubjectService = __spring__.getBean("specialSubjectService")
        self.specialSubject = None
        
    def execute(self):
        if self.subject == None:
            self.addActionError(u"无法加载指定的学科。")
            return self.ERROR
        
        self.templateName = "template1"
        if self.subject.templateName != None:
            self.templateName = self.subject.templateName
        
        specialSubjectId = self.params.safeGetIntParam("specialSubjectId")
        if specialSubjectId == 0:
            self.specialSubject = self.specialSubjectService.getNewestSpecialSubjectByType("subject")
            if self.specialSubject != None:
                specialSubjectId = self.specialSubject.specialSubjectId
                                
        if specialSubjectId > 0:
            if self.specialSubject == None:
                self.specialSubject = self.specialSubjectService.getSpecialSubject(specialSubjectId)
            if self.specialSubject == None:
                self.addActionError(u"无法加载指定的专题。")
                return self.ERROR
            
            return self.show_specialsubject_video_list()
        else:
            request.setAttribute("subject", self.subject)
            request.setAttribute("head_nav", "specialsubject")
            return "/WEB-INF/subjectpage/" + self.templateName + "/specialsubject_page_error.ftl"
        
    def show_specialsubject_video_list(self):
        qry = VideoQuery(" v.videoId, v.flvHref, v.flvThumbNailHref, v.title,v.userId,v.createDate")
        qry.specialSubjectId = self.specialSubject.specialSubjectId
        pager = self.params.createPager()
        pager.itemName = u"视频"
        pager.itemUnit = u"个"
        pager.pageSize = 20
        pager.totalRows = qry.count()
        video_list = qry.query_map(pager)
        request.setAttribute("video_list", video_list)
        request.setAttribute("pager", pager)        
        request.setAttribute("specialSubject", self.specialSubject)        
        request.setAttribute("subject", self.subject)
        request.setAttribute("head_nav", "specialsubject")
        return "/WEB-INF/subjectpage/" + self.templateName + "/show_video_list.ftl"
