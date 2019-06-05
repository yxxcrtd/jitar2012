from site_config import SiteConfig
from base_action import *
from cn.edustar.jitar.util import ParamUtil

class showNewSpecialSubject(ActionExecutor):
    def execute(self):
        site_config = SiteConfig()
        site_config.get_config()
        response.contentType = "text/html; charset=UTF-8"
        special_subj_svc = __spring__.getBean("specialSubjectService")
        
        # 本页面需要管理员权限
        if self.loginUser == None:
            self.addActionError(u"请先登录。")
            return self.ACCESS_DENIED
        accessControlService = __spring__.getBean("accessControlService")
        if accessControlService.isSystemAdmin(self.loginUser) == False:            
            la = accessControlService.getAllAccessControlByUser(self.loginUser)
            if la == None or len(la) < 1:
                self.addActionError(u"您没有管理的权限。")
                return self.ACCESS_DENIED        
        
        if request.getMethod() == "POST":
            self.params = ParamUtil(request)
            guids = self.params.safeGetIntValues("guid")
            for guid in guids:
                special_subj_svc.deleteNewSpecialSubjectById(guid)
        
        request.setAttribute("newSpecialSubjectList", special_subj_svc.getAllNewSpecialSubject())
        request.setAttribute("head_nav", "special_subject")
        if self.params.safeGetIntParam("id") > 0:
            return "/WEB-INF/ftl/specialsubject/showNewSpecialSubject_nologo.ftl"
        else:
            #return "/WEB-INF/ftl/specialsubject/showNewSpecialSubject.ftl"
            return "/WEB-INF/ftl2/special/special_show_new.ftl"
