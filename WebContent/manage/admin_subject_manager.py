from accesscontrol_query import AccessControlQuery
from cn.edustar.jitar.util import ParamUtil
from cn.edustar.jitar.pojos import AccessControl

class admin_subject_manager:
    def execute(self):
        self.params = ParamUtil(request)
        
        if request.getMethod() == "POST":
            self.accessControlService = __spring__.getBean("accessControlService")
            guids = self.params.safeGetIntValues("guid")
            for g in guids:
                accessControl = self.accessControlService.getAccessControlById(g)
                if accessControl != None:
                    self.accessControlService.deleteAccessControl(accessControl)
            
        self.get_admin_list()
        ObjectType = [u"未知的权限", u"系统超级管理员", u"系统用户管理员", u"系统内容管理员", u"机构系统管理员", u"机构用户管理员", u"机构内容管理员", u"学科系统管理员", u"学科用户管理员", u"学科内容管理员", u"元学科内容管理员"]
        request.setAttribute("ObjectType", ObjectType)
        
        return "/WEB-INF/ftl/admin/admin_subject_manager.ftl"

    def get_admin_list(self):        
        qry = AccessControlQuery("ac.accessControlId, ac.userId, ac.objectType, ac.objectId, u.loginName,u.trueName,u.unitId")
        qry.custormAndWhere = "(ac.objectType = 7 or ac.objectType = 8 or ac.objectType = 8)"
        qry.orderType = 1
        pager = self.createPager()
        pager.totalRows = qry.count() 
        admin_list = qry.query_map(pager)
        request.setAttribute("pager", pager)
        request.setAttribute("admin_list", admin_list)
        
    def createPager(self):
        # pager.
        pager = self.params.createPager()
        pager.itemName = u"管理员"
        pager.itemUnit = u"名"
        pager.pageSize = 30
        return pager
