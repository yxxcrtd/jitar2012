from subject_page import *

class admin_index(BaseSubject):
    def __init__(self):
        BaseSubject.__init__(self)

    def execute(self):
        #权限判定
        if self.loginUser == None:
            return self.LOGIN

        if self.isContentAdmin() == False and self.isAdmin() == False and self.isUserAdmin() == False:
            self.addActionError(u"你没有管理的权限。")
            return self.ERROR
            
        cmd = self.params.safeGetStringParam("cmd")
        request.setAttribute("subject", self.subject)
        if self.isUserAdmin():
            request.setAttribute("isUserAdmin", "1")
        else:
            request.setAttribute("isUserAdmin", "0")
        
        if self.isContentAdmin():
            request.setAttribute("isContentAdmin", "1")
        else:
            request.setAttribute("isContentAdmin", "0")
            
        if self.isAdmin():
            request.setAttribute("isAdmin", "1")
        else:
            request.setAttribute("isAdmin", "0")    
        
        if cmd == "menu":
            webSiteManageService = __spring__.getBean("webSiteManageService")
            bklist = webSiteManageService.getBackYearList("article")
            if bklist != None and len(bklist) > 0:
                request.setAttribute("bklist", bklist)
            return "/WEB-INF/subjectmanage/menu.ftl"
        elif cmd == "main":
            return "/WEB-INF/subjectmanage/main.ftl"
        elif cmd == "head":
            return "/WEB-INF/subjectmanage/head.ftl"
        else:
            return "/WEB-INF/subjectmanage/index.ftl"
