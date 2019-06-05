from subject_page import *
from photo_query import PhotoQuery

class specialsubject_photo_list(BaseSubject):
    def __init__(self):
        BaseSubject.__init__(self)
        self.specialSubject_svc = __spring__.getBean("specialSubjectService")
        self.specialSubject = None
        
    def execute(self):
        if self.loginUser == None:
            return self.LOGIN
        
        if self.isContentAdmin() == False and self.isAdmin() == False:
            self.addActionError(u"您你没有管理的权限！")
            return self.ERROR
        
        specialSubjectId = self.params.safeGetIntParam("specialSubjectId")
        
        if specialSubjectId > 0:
            self.specialSubject = self.specialSubject_svc.getSpecialSubject(specialSubjectId)
        
        if self.specialSubject == None:
            self.addActionError(u"不能加载专题对象。")
            return self.ERROR
        
        if request.getMethod() == "POST":
            self.clear_subject_cache()
            self.save_post()
        return self.get_method()
    
    def save_post(self):
        cmd = self.params.safeGetStringParam("cmd")
        guids = self.params.safeGetIntValues("guid")
        photo_svc =  __spring__.getBean("photoService")
        if cmd == "remove":
            for g in guids:
                photo = photo_svc.findById(g)
                if photo != None:
                    photo.setSpecialSubjectId(0)
                    photo_svc.updatePhoto(photo)
                    
    def get_method(self):
        qry = PhotoQuery(""" p.title, p.photoId, p.userId, p.createDate, p.href """)
        qry.specialSubjectId = self.specialSubject.specialSubjectId
        pager = self.params.createPager()
        pager.itemName = u"图片"
        pager.itemUnit = u"个"
        pager.pageSize = 20
        pager.totalRows = qry.count()
        photo_list = qry.query_map(pager)
        request.setAttribute("photo_list", photo_list)
        request.setAttribute("pager", pager)
        request.setAttribute("specialSubject", self.specialSubject)
        return "/WEB-INF/subjectmanage/specialsubject_photo_list.ftl"