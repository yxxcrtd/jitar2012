from subject_page import *
from cn.edustar.jitar.pojos import SpecialSubject
from java.util import Date
from java.text import SimpleDateFormat

class createspecialsubject(BaseSubject):
    def __init__(self):
        BaseSubject.__init__(self)
        self.specialSubject_svc = __spring__.getBean("specialSubjectService")
        self.specialSubject = None
        
    def execute(self):
        if self.loginUser == None:
            return self.LOGIN
        
        if self.isAdmin() == False and self.isContentAdmin() == False:
            self.addActionError(u"您你没有管理的权限！")
            return self.ERROR
        
        specialSubjectId = self.params.safeGetIntParam("specialSubjectId")
        
        if specialSubjectId > 0:
            self.specialSubject = self.specialSubject_svc.getSpecialSubject(specialSubjectId)
        
        if request.getMethod() == "POST":
            return self.save_post()
        return self.get_method()
    
    def save_post(self):
        st = self.params.safeGetStringParam("st")
        if st == "":
            self.addActionError(u"请输入专题标题。")
            return self.ERROR
        
        so = self.params.safeGetStringParam("so")
        sd = self.params.safeGetStringParam("sd")
        se = self.params.safeGetStringParam("se")
        expire_date = Date()
        try:
            expire_date = SimpleDateFormat("yyyy-MM-dd").parse(se)
        except:
            actionErrors = [u"输入的日期格式不正确，应当是: '年年年年-月月-日日' 格式"]
            request.setAttribute("actionErrors", actionErrors)
            return self.ERROR        
        if self.specialSubject == None:
            self.specialSubject = SpecialSubject()
            self.specialSubject.setObjectType("subject")
            self.specialSubject.setObjectId(self.subject.subjectId) 
            self.specialSubject.setCreateDate(Date())
            self.specialSubject.setCreateUserId(self.loginUser.userId)
            
        self.specialSubject.setTitle(st)
        if so != "":
            self.specialSubject.setLogo(so)
        else:
            self.specialSubject.setLogo(None)
        if sd != "":
            self.specialSubject.setDescription(sd)
        else:
            self.specialSubject.setDescription(None)
        self.specialSubject.setExpiresDate(expire_date)     
  
        self.specialSubject_svc.saveOrUpdateSpecialSubject(self.specialSubject)
        return self.SUCCESS
    
    def get_method(self):
        request.setAttribute("specialSubject", self.specialSubject)        
        return "/WEB-INF/subjectmanage/createspecialsubject.ftl"
