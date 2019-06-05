from cn.edustar.jitar.util import ParamUtil
from cn.edustar.jitar.jython import BaseAdminAction
from base_action import ActionResult
from base_mashup import *

class admin_mashup_resource_list(BaseAdminAction, ActionResult):
    
    ERROR = "/WEB-INF/ftl/Error.ftl"
    ACCESS_DENIED = "/WEB-INF/ftl/Error.ftl"
    ACCESS_ERROR = "/WEB-INF/ftl/mengv1/access_error.ftl"
    SUCCESS = "/WEB-INF/ftl/success.ftl"
    LOGIN = "/login.jsp"
    
    def __init__(self):
        self.params = ParamUtil(request)
        self.mashupService = __spring__.getBean("mashupService")
        
    def execute(self):
        if self.loginUser == None:
            return ActionResult.LOGIN
        if self.loginUser.loginName != "admin":
            self.addActionError(u"目前只有系统管理员才可以进行管理.")
            return ActionResult.ERROR
        
        cmd = self.params.safeGetStringParam("cmd")
        if cmd == "approve":
            self.approve()
        elif cmd == "unapprove":
            self.unapprove()
        elif cmd == "delete":
            self.delete()
        elif cmd == "todelete":
            self.todelete()
        
        self.show_list()        
        return "/WEB-INF/mashup/admin_mashup_resource_list.ftl"
    
    def approve(self):
        guids = self.params.safeGetIntValues("guid")
        for g in guids:
            mashupContent = self.mashupService.getMashupContent(g)
            if mashupContent != None:
                mashupContent.setMashupContentState(0)
                self.mashupService.updateMashupContent(mashupContent)
    
    def unapprove(self):
        guids = self.params.safeGetIntValues("guid")
        for g in guids:
            mashupContent = self.mashupService.getMashupContent(g)
            if mashupContent != None:
                mashupContent.setMashupContentState(1)
                self.mashupService.updateMashupContent(mashupContent)
    
    def todelete(self):
        guids = self.params.safeGetIntValues("guid")
        for g in guids:
            mashupContent = self.mashupService.getMashupContent(g)
            if mashupContent != None:
                mashupContent.setMashupContentState(2)
                self.mashupService.updateMashupContent(mashupContent)
                
    def delete(self):
        guids = self.params.safeGetIntValues("guid")
        for g in guids:
            mashupContent = self.mashupService.getMashupContent(g)
            if mashupContent != None:
                self.mashupService.deleteMashupContent(mashupContent)
    
    def show_list(self):
        qry = MashupContentQuery("""
                mc.mashupContentId,mc.pushUserName,mc.mashupContentState,mc.platformName,
                mc.title,mc.unitName,mc.unitTitle,mc.unitTitle,mc.author,mc.orginId,mc.href,mc.pushDate
                """)
        qry.documentType = "resource"
        qry.mashupContentState = None
        qry.orderType = 0
        pager = self.params.createPager()
        pager.itemName = u"资源"
        pager.itemUnit = u"个"
        pager.pageSize = 25
        pager.totalRows = qry.count()
        content_list = qry.query_map(pager)
        request.setAttribute("content_list", content_list)
        request.setAttribute("pager", pager)
