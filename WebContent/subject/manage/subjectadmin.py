from cn.edustar.jitar.pojos import User, AccessControl
from subject_page import *
from accesscontrol_query import AccessControlQuery

class subjectadmin(BaseSubject):
    def __init__(self):
        BaseSubject.__init__(self)
        self.userService = __spring__.getBean("userService")
        
    def execute(self):
        if self.loginUser == None:
            return self.LOGIN
        
        if self.isAdmin() == False:
            self.addActionError(u"您你没有管理的权限！")
            return self.ERROR
        
        if request.getMethod() == "POST":
            guids = self.params.safeGetIntValues("guid")
            for g in guids:
                accessControl = self.accessControlService.getAccessControlById(g)
                if accessControl != None:
                    #print accessControl
                    self.accessControlService.deleteAccessControl(accessControl)
        
        self.get_admin_list()
        ObjectType = [u"未知的权限", u"系统超级管理员", u"系统用户管理员", u"系统内容管理员", u"机构系统管理员", u"机构用户管理员", u"机构内容管理员", u"学科系统管理员", u"学科用户管理员", u"学科内容管理员", u"元学科内容管理员"]
        request.setAttribute("ObjectType", ObjectType)
        request.setAttribute("subject", self.subject)

        return "/WEB-INF/subjectmanage/subjectadmin.ftl"
        
    def get_admin_list(self):
        objectType = self.params.safeGetIntParam("objectType")
        qry = AccessControlQuery("ac.accessControlId, ac.userId, ac.objectType, ac.objectId, ac.objectTitle, u.loginName,u.trueName,u.unitId")
        if objectType != 0:
            qry.objectType = objectType
        else:
            qry.custormAndWhere = "(ac.objectType = 7 or ac.objectType = 8 or ac.objectType = 9)"            
        qry.orderType = 1
        
        # print objectType
        if self.subject != None:
            qry.objectId = self.subject.subjectId
        pager = self.createPager()
        pager.totalRows = qry.count() 
        admin_list = qry.query_map(pager)
        request.setAttribute("pager", pager)
        request.setAttribute("objectType", objectType)
        request.setAttribute("admin_list", admin_list)
        
    def createPager(self):
        # pager.
        pager = self.params.createPager()
        pager.itemName = u"管理员"
        pager.itemUnit = u"名"
        pager.pageSize = 30
        return pager
