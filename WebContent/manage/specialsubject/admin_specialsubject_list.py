from base_specialsubject_page import SpecialSubjectQuery
from base_action import *
from cn.edustar.jitar.util import ParamUtil
from cn.edustar.jitar.jython import BaseAdminAction

class admin_specialsubject_list(ActionExecutor):
    def __init__(self):
        self.params = ParamUtil(request)
    
    def execute(self):
        if self.loginUser == None:
            self.addActionError(u"请先登录。")
            return self.LOGIN
        
        accessControlService = __spring__.getBean("accessControlService")
        if False == accessControlService.isSystemContentAdmin(self.loginUser):
            self.addActionError(u"管理专题需要管理员权限。")
            return self.ERROR
        
        if request.getMethod() == "POST":            
            ss_svc = __spring__.getBean("specialSubjectService")
            guids = self.params.safeGetIntValues("guid")
            for g in guids:
                ss_svc.deleteSpecialSubjectById(g)
        
        qry = SpecialSubjectQuery(""" ss.specialSubjectId, ss.logo, ss.title,ss.createUserId, ss.createDate,ss.expiresDate """)
        pager = self.params.createPager()
        pager.itemName = u"专题"
        pager.itemUnit = u"个"
        pager.pageSize = 20
        pager.totalRows = qry.count()
        ss_list = qry.query_map(pager)
        request.setAttribute("ss_list", ss_list)
        request.setAttribute("pager", pager)
        return "/WEB-INF/ftl/specialsubject/admin_specialsubject_list.ftl"
