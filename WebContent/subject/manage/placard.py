from subject_page import *
from placard_query import PlacardQuery

class placard(BaseSubject):
    def __init__(self):
        BaseSubject.__init__(self)
        self.placradService = __spring__.getBean("placardService")
        
    def execute(self):
        if self.loginUser == None:
            return self.LOGIN
        
        if self.isAdmin() == False:
            self.addActionError(u"您你没有管理的权限！")
            return self.ERROR
        
        if request.getMethod() == "POST":
            self.clear_subject_cache()
            self.post_action()
        
        return self.news_list()
        
    def news_list(self):
        qry = PlacardQuery(""" pld.userId, pld.id, pld.hide, pld.title, pld.createDate """)
        qry.objType = 14 #参见 model.ObjectType 的定义
        qry.objId = self.subject.subjectId
        qry.hideState = None
        pager = self.params.createPager()
        pager.itemName = u"公告"
        pager.itemUnit = u"个"
        pager.pageSize = 25
        pager.totalRows = qry.count()
        subject_placard_list = qry.query_map(pager)
        request.setAttribute("subject",self.subject)
        request.setAttribute("subject_placard_list",subject_placard_list)
        return "/WEB-INF/subjectmanage/placard_list.ftl"
    
    def post_action(self):
        cmd = self.params.safeGetStringParam("cmd")
        guids = self.params.safeGetIntValues("guid")
        for id in guids:
            news = self.placradService.getPlacard(id)
            if news != None:
                if cmd == "delete":                
                    self.placradService.deletePlacard(news)
                elif cmd == "approve":
                    self.placradService.showPlacard(news)
                elif cmd == "unapprove":
                    self.placradService.hidePlacard(news)