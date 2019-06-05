from subject_page import *
from placard_query import PlacardQuery

class placardList(BaseSubject):
    def __init__(self):
        BaseSubject.__init__(self)
    
    def execute(self):
        if self.subject == None:
            self.addActionError(u"无法加载指定的学科。")
            return self.ERROR
        
        self.templateName = "template1"
        if self.subject.templateName != None:
            self.templateName = self.subject.templateName
            
        qry = PlacardQuery(""" pld.id, pld.title, pld.createDate """)
        qry.objType = 14  
        qry.objId = self.subject.subjectId
        pager = self.createPager()
        pager.totalRows = qry.count()
        placard_list = qry.query_map(pager)
        Page_Title = self.params.safeGetStringParam("title")
        if Page_Title == "":
            Page_Title = u"学科公告"
        request.setAttribute("Page_Title", Page_Title)
        request.setAttribute("placard_list", placard_list)
        request.setAttribute("pager", pager)
        request.setAttribute("subject", self.subject)
        
        return "/WEB-INF/subjectpage/" + self.templateName + "/placardList.ftl"   
        
            
    def createPager(self):
        pager = self.params.createPager()
        pager.pageSize = 10
        pager.itemName = u"公告"
        pager.itemUnit = u"个"
        return pager