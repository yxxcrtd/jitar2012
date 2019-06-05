from subject_page import *

class sysmodule(BaseSubject):
    def __init__(self):
        BaseSubject.__init__(self)

    def execute(self):
        if self.loginUser == None:
            return self.LOGIN
        
        if self.isAdmin() == False:
            self.addActionError(u"您你没有管理的权限！")
            return self.ERROR
        
        if request.getMethod() == "POST":
            self.save_module()
        self.list_sysmodule()
        
        return "/WEB-INF/subjectmanage/sysmodule.ftl"
    
    def list_sysmodule(self):
        webpart_list = self.subjectService.getWebpartList(self.subject.subjectId,True)
        request.setAttribute("subject",self.subject)
        request.setAttribute("webpart_list",webpart_list)
    
    def save_module(self):
        cmd = self.params.safeGetStringParam("cmd")
        if cmd == "displayname":
            subjectWebpartIds = self.params.safeGetIntValues("subjectWebpartId")
            for id in subjectWebpartIds:
                webpartDisplayName = self.params.safeGetStringParam("displayName" + str(id))
                webpart = self.subjectService.getSubjectWebpartById(id)
                if webpart != None:
                    if webpartDisplayName != webpart.getDisplayName():
                        webpart.setDisplayName(webpartDisplayName)
                        self.subjectService.saveOrUpdateSubjectWebpart(webpart)
            #更新缓存
            cache = __jitar__.cacheProvider.getCache('subject')
            cache_list = cache.getAllKeys()
            cache_key_head = "sbj" + str(self.subject.subjectId)
            for c in cache_list:
                if c.split("_")[0] == cache_key_head:
                    cache.remove(c)
        else:            
            guids = self.params.safeGetIntValues("guid")
            for g in guids:
                webpart = self.subjectService.getSubjectWebpartById(g)
                if webpart != None:
                    if cmd == "visible":
                        webpart.setVisible(True)
                    elif cmd == "hidden":
                        webpart.setVisible(False)
                    self.subjectService.saveOrUpdateSubjectWebpart(webpart)