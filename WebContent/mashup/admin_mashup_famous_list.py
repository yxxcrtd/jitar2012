from cn.edustar.jitar.util import ParamUtil
from cn.edustar.jitar.jython import BaseAdminAction
from base_action import ActionResult
from base_mashup import *

class admin_mashup_famous_list(BaseAdminAction, ActionResult):
    
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
        return "/WEB-INF/mashup/admin_mashup_famous_list.ftl"
    
    def approve(self):
        guids = self.params.safeGetIntValues("guid")
        for g in guids:
            mashupBlogGroup = self.mashupService.getMashupBlogGroupById(g)
            if mashupBlogGroup != None:
                mashupBlogGroup.setMashupBlogGroupState(0)
                self.mashupService.updateMashupBlogGroup(mashupBlogGroup)
    
    def unapprove(self):
        guids = self.params.safeGetIntValues("guid")
        for g in guids:
            mashupBlogGroup = self.mashupService.getMashupBlogGroupById(g)
            if mashupBlogGroup != None:
                mashupBlogGroup.setMashupBlogGroupState(1)
                self.mashupService.updateMashupBlogGroup(mashupBlogGroup)
                
    def delete(self):
        guids = self.params.safeGetIntValues("guid")
        for g in guids:
            mashupBlogGroup = self.mashupService.getMashupBlogGroupById(g)
            if mashupBlogGroup != None:
                self.mashupService.deleteMashupBlogGroup(mashupBlogGroup)
                
    def todelete(self):
        guids = self.params.safeGetIntValues("guid")
        for g in guids:
            mashupBlogGroup = self.mashupService.getMashupBlogGroupById(g)
            if mashupBlogGroup != None:
                mashupBlogGroup.setMashupBlogGroupState(2)
                self.mashupService.updateMashupBlogGroup(mashupBlogGroup)
                
    def show_list(self):
        qry = MashupBlogGroupQuery("""
                mbg.mashupBlogGroupId,mbg.pushUserName,mbg.mashupBlogGroupState,mbg.platformName,mbg.description,
                mbg.trueName,mbg.unitName,mbg.unitTitle,mbg.subjectName,mbg.gradeName,mbg.orginId,mbg.href,mbg.pushDate,mbg.icon
                """)
        qry.contentType = "blog"
        qry.mashupBlogGroupState = None
        qry.orderType = 0
        pager = self.params.createPager()
        pager.itemName = u"名师"
        pager.itemUnit = u"篇位"
        pager.pageSize = 25
        pager.totalRows = qry.count()
        content_list = qry.query_map(pager)
        request.setAttribute("content_list", content_list)
        request.setAttribute("pager", pager)
