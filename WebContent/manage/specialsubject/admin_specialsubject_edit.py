from base_action import *
from cn.edustar.jitar.pojos import SpecialSubject
from java.util import Date
from java.text import SimpleDateFormat


class admin_specialsubject_edit(ActionExecutor, SubjectMixiner):
    def __init__(self):
        ActionExecutor.__init__(self)
        self.specialSubject_svc = __spring__.getBean("specialSubjectService")
        self.specialSubject = None
        
    def execute(self):
        # 必须要求登录和具有管理权限.
        if self.loginUser == None: return ActionResult.LOGIN
        
        accessControlService = __spring__.getBean("accessControlService")
        if False == accessControlService.isSystemContentAdmin(self.loginUser):
            self.addActionError(u"管理专题需要 管理员 权限。")
            return self.ERROR
        
        specialSubjectId = self.params.safeGetIntParam("specialSubjectId")
        self.specialSubject = self.specialSubject_svc.getSpecialSubject(specialSubjectId)
        if self.specialSubject == None:
            self.addActionError(u"不能加载专题对象。")
            return self.ERROR        
        
        if request.getMethod() == "POST":
            return self.save_post()
        else:
            request.setAttribute("specialSubject", self.specialSubject)
            return "/WEB-INF/ftl/specialsubject/admin_specialsubject_edit.ftl"
    
    def save_post(self):
        st = self.params.safeGetStringParam("st")
        if st == "":
            self.addActionError(u"请输入专题标题。")
            return ActionResult.ERROR
        
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
       
        self.specialSubject.setTitle(st)
        if so == "":
            self.specialSubject.setLogo(None)
        else:
            self.specialSubject.setLogo(so)
            
        if sd == "":
            self.specialSubject.setDescription(None)
        else:
            self.specialSubject.setDescription(sd)
            
        self.specialSubject.setExpiresDate(expire_date)        
        self.specialSubject_svc.saveOrUpdateSpecialSubject(self.specialSubject)
        return ActionResult.SUCCESS
