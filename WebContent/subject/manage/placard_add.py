from subject_page import *
from cn.edustar.jitar.pojos import Placard

class placard_add(BaseSubject):
    def __init__(self):
        BaseSubject.__init__(self)
        self.placard = None
        self.placradService = __spring__.getBean("placardService")
        
    def execute(self):
        if self.loginUser == None:
            return self.LOGIN
        
        if self.isAdmin() == False:
            self.addActionError(u"您你没有管理的权限！")
            return self.ERROR
        placardId = self.params.safeGetIntParam("placardId")
        if placardId > 0:
            self.placard = self.placradService.getPlacard(placardId)  
                      
        if request.getMethod() == "POST":
            self.clear_subject_cache()
            self.save_post()
        
        return self.news_edit()
    
    def news_edit(self):
        request.setAttribute("placard", self.placard)
        return "/WEB-INF/subjectmanage/placard_add.ftl"
    
    def save_post(self):
        placard_title = self.params.safeGetStringParam("placard_title")
        content = self.params.safeGetStringParam("content")       
        if placard_title == "":
            self.addActionError(u"请输入标题")
            return self.ERROR
        if content == "":
            self.addActionError(u"请输入内容")
            return self.ERROR
        if self.placard == None:
            self.placard = Placard()
            self.placard.setObjId(self.subject.subjectId)
            self.placard.setObjType(14)
            self.placard.setHide(False)
        self.placard.setTitle(placard_title)
        self.placard.setUserId(self.loginUser.userId)
        self.placard.setContent(content)
        self.placradService.savePlacard(self.placard)
        response.sendRedirect("placard.py?id=" + str(self.subject.subjectId))