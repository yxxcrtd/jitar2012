#encoding=utf-8
from subject_page import *
from cn.edustar.jitar.data import *
from article_query import ArticleQuery
from photo_query import PhotoQuery
from base_specialsubject_page import SpecialSubjectQuery
from video_query import VideoQuery
from specialSubjectArticle_query import SpecialSubjectArticleQuery

class specialsubject(BaseSubject):
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
            self.specialSubject = self.specialSubjectService.getNewestSpecialSubjectByTypeId(self.subject.subjectId)
            if self.specialSubject != None:
                specialSubjectId = self.specialSubject.specialSubjectId
                                
        if specialSubjectId > 0:
            if self.specialSubject == None:
                self.specialSubject = self.specialSubjectService.getSpecialSubject(specialSubjectId)
            if self.specialSubject == None:
                self.addActionError(u"无法加载指定的专题。")
                return self.ERROR
            
            return self.show_specialSubject()
        else:
            request.setAttribute("subject", self.subject)
            request.setAttribute("head_nav", "specialsubject")
            return "/WEB-INF/subjectpage/" + self.templateName + "/specialsubject_page_error.ftl"
        
    def show_specialSubject(self):
        qry = PhotoQuery(""" p.photoId, p.title, p.userId,p.href, p.userTrueName,u.loginName """)
        qry.specialSubjectId = self.specialSubject.specialSubjectId
        qry.extName = ".jpg"
        ssp_list = qry.query_map(6)
        
        qry = SpecialSubjectArticleQuery(""" ssa.articleId, ssa.title, ssa.userId, ssa.userTrueName, ssa.createDate, ssa.loginName, ssa.typeState """)
        qry.specialSubjectId = self.specialSubject.specialSubjectId
        ssa_list = qry.query_map(10)
        
        qry = SpecialSubjectQuery(""" ss.specialSubjectId, ss.objectGuid, ss.title,ss.logo,ss.description,ss.createDate,ss.expiresDate """)
        qry.objectId = self.subject.subjectId
        qry.objectType = "subject"
        ss_list = qry.query_map(10)
        if ssp_list != None and len(ssp_list) > 0:
            request.setAttribute("specialSubjectPhotoList", ssp_list)
        request.setAttribute("specialSubjectArticleList", ssa_list)
        request.setAttribute("specialSubjectList", ss_list)
        request.setAttribute("specialSubject", self.specialSubject)
        
        request.setAttribute("subject", self.subject)
        request.setAttribute("head_nav", "specialsubject")
        
        if self.unitId != None and self.unitId != 0:
            request.setAttribute("unitId", self.unitId)
        self.video_list()
        return "/WEB-INF/subjectpage/" + self.templateName + "/specialsubject_page.ftl"
    
    def video_list(self):
        qry = VideoQuery(" v.videoId, v.flvHref, v.flvThumbNailHref, v.title,v.userId,v.createDate")
        qry.specialSubjectId = self.specialSubject.specialSubjectId
        v_list = qry.query_map(5)
        if len(v_list) > 0:
            request.setAttribute("video_list", v_list)
