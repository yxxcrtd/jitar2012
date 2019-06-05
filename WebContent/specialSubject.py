from cn.edustar.jitar.util import ParamUtil
from base_specialsubject_page import *
from base_action import *
from specialSubjectArticle_query import SpecialSubjectArticleQuery
from photo_query import PhotoQuery
from video_query import VideoQuery

class specialSubject(ActionExecutor):
    def __init__(self):
        ActionExecutor.__init__(self)        
        self.specialSubject_svc = __spring__.getBean("specialSubjectService")
        self.specialSubject = None
        
    def execute(self):
        response.contentType = "text/html; charset=UTF-8"
        self.specialSubjectId = self.params.safeGetIntParam("specialSubjectId")
        if self.specialSubjectId > 0:
            self.specialSubject = self.specialSubject_svc.getSpecialSubject(self.specialSubjectId)
            if self.specialSubject == None:
                self.addActionError(u"未能正确加载专题对象。")
                return self.ERROR
            else:
                if self.specialSubject.getObjectType() == "subject":
                    subjectUrlPattern = request.getAttribute("SubjectUrlPattern")
                    subjectService = __spring__.getBean("subjectService")
                    subject = subjectService.getSubjectById(self.specialSubject.getObjectId())
                    if subject == None:
                        self.addActionError(u"未能正确加载学科对象。")
                        return self.ERROR        
                    if subjectUrlPattern != None:
                        subjectUrlPattern = subjectUrlPattern.replace("{subjectCode}", subject.subjectCode)
                        subjectUrlPattern = subjectUrlPattern + "py/specialsubject.py?specialSubjectId=" + str(self.specialSubject.specialSubjectId)
                    else:
                        subjectUrlPattern = "k/" + subject.subjectCode + "/py/specialsubject.py?specialSubjectId=" + str(self.specialSubject.specialSubjectId)
                    response.sendRedirect(subjectUrlPattern)
                    return
      
        qry = SpecialSubjectQuery(""" ss.specialSubjectId, ss.objectGuid, ss.title,ss.logo,ss.description,ss.createDate,ss.expiresDate""")
        qry.objectType = "system"
        ss_list = qry.query_map(20)  
        if self.specialSubject == None:
            if ss_list.size() > 0:
                self.specialSubject = ss_list.get(0)
                self.specialSubjectId = self.specialSubject["specialSubjectId"]
        # 有点小问题，暂时先再加载一次。
        self.specialSubject = self.specialSubject_svc.getSpecialSubject(self.specialSubjectId)
        if self.specialSubject == None:
            return "/WEB-INF/ftl/specialsubject/no_specialsubject_page.ftl"
            
        if self.specialSubject != None:                
            qry = PhotoQuery(""" p.photoId, p.title, p.userId,p.href, p.userTrueName,u.loginName """)
            qry.specialSubjectId = self.specialSubjectId
            qry.extName = ".jpg"
            ssp_list = qry.query_map(6)
            
            qry = SpecialSubjectArticleQuery("ssa.articleId,ssa.title,ssa.userId, ssa.userTrueName,ssa.createDate,ssa.typeState")
            qry.specialSubjectId = self.specialSubjectId
            ssa_list = qry.query_map(20)
            if ssp_list != None and len(ssp_list) > 0:
                request.setAttribute("specialSubjectPhotoList", ssp_list)
            request.setAttribute("specialSubjectArticleList", ssa_list)                 
            request.setAttribute("specialSubject", self.specialSubject)
            
        sp_type = self.params.safeGetStringParam("type")
        sp_type_id = self.params.safeGetIntParam("id")
        
        if sp_type != "":
            request.setAttribute("specialSubjectType", sp_type)
            request.setAttribute("specialSubjectTypeId", sp_type_id)
        
        request.setAttribute("specialSubjectList", ss_list)        
        request.setAttribute("head_nav", "special_subject")
        if self.loginUser != None:
            request.setAttribute("loginUser", self.loginUser)
        
        self.video_list()
        return "/WEB-INF/ftl/site_specialsubject.ftl"
    
    def video_list(self):
        qry = VideoQuery(" v.videoId, v.flvHref, v.flvThumbNailHref, v.title,v.userId,v.createDate")
        qry.specialSubjectId = self.specialSubject.specialSubjectId
        v_list = qry.query_map(5)
        if len(v_list) > 0:
            request.setAttribute("video_list", v_list)