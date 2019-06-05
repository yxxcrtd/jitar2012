from subject_page import *
from group_query import GroupQuery

class groupList(BaseSubject):
    def __init__(self):
        BaseSubject.__init__(self)
    
    def execute(self):
        if self.subject == None:
            self.addActionError(u"无法加载指定的学科。")
            return self.ERROR
        
        self.templateName = "template1"
        if self.subject.templateName != None:
            self.templateName = self.subject.templateName
        
        type = self.params.safeGetStringParam("type")
        qry = GroupQuery("""  subj.subjectId, g.createUserId, g.groupId, g.groupIcon, g.groupTitle, g.userCount, 
                              g.createDate, g.visitCount, g.articleCount, g.topicCount, g.resourceCount,g.actionCount,
                          u.loginName, u.nickName """)
        qry.subjectId = self.get_current_subjectId()
        qry.gradeId = self.get_current_gradeId()
        Page_Title = self.params.safeGetStringParam("title")
        request.setAttribute("Page_Title",Page_Title)
        if "new" == type:
            if Page_Title == "":
                request.setAttribute("Page_Title",u"最新协作组")
        elif "hot" == type:
            qry.orderType = 8
            if Page_Title == "":
                request.setAttribute("Page_Title",u"热门协作组")
        elif "rcmd" == type:
            qry.isRecommend = True
            if Page_Title == "":
                request.setAttribute("Page_Title",u"推荐协作组")      
        else:
            if Page_Title == "":
                request.setAttribute("Page_Title",u"协作组")
        pager = self.createPager()
        pager.totalRows = qry.count()
        group_list = qry.query_map(pager)
        request.setAttribute("group_list", group_list)
        request.setAttribute("pager", pager)
        request.setAttribute("subject", self.subject)
        
        return "/WEB-INF/subjectpage/" + self.templateName + "/groupList.ftl"
            
            
    def createPager(self):
        pager = self.params.createPager()
        pager.pageSize = 10
        pager.itemName = u"工作室"
        pager.itemUnit = u"个"
        return pager
    
    def get_current_subjectId(self):
        subjectId = self.subject.metaSubject.msubjId
        request.setAttribute("subjectId" ,subjectId)
        return subjectId
    
    
    def get_current_gradeId(self):
        gradeId = self.subject.metaGrade.gradeId
        request.setAttribute("gradeId",gradeId)
        return gradeId