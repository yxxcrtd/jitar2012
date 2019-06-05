from subject_page import *
from cn.edustar.jitar.data import *
from base_action import BaseAction, ActionResult, SubjectMixiner
from action_query import ActionQuery

class activity(BaseSubject,SubjectMixiner):
    def __init__(self):
        BaseSubject.__init__(self)
        self.cate_svc = __jitar__.categoryService
    
    def execute(self):
        cache = __jitar__.cacheProvider.getCache('page')
        
        self.get_action_list()
        
        self.templateName = "template1"
        if self.subject.templateName != None:
            self.templateName = self.subject.templateName
            
        request.setAttribute("subject",self.subject)
        request.setAttribute("head_nav","activity")
        return "/WEB-INF/subjectpage/" + self.templateName + "/activity_page.ftl"
    
    def get_action_list(self):
        showtype = self.params.getStringParam("type")
        request.setAttribute("type", showtype)
        
        self.pager = self.params.createPager()
        self.pager.itemName = u"活动"
        self.pager.itemUnit = u"个"
        self.pager.pageSize = 20
        qry = ActionQuery(""" act.title, act.createDate, act.actionId, act.ownerId, act.ownerType, act.createUserId, act.actionType,
                              act.description, act.userLimit, act.startDateTime,act.finishDateTime, act.attendLimitDateTime, act.place,
                              act.status, act.visibility, act.attendCount,u.loginName,u.trueName
                          """)
        
        qry.ownerType = "subject"
        qry.ownerId = self.subject.subjectId
        if showtype == "running":
            qry.qryDate = 1 #正在进行
        elif showtype == "finish":
            qry.qryDate = 2 #已经完成的活动
        elif showtype == "new":
            qry.qryDate = 0 #正在报名的活动            

        self.pager.totalRows = qry.count()
        action_list = qry.query_map(self.pager)
        request.setAttribute("action_list", action_list)
        request.setAttribute("pager", self.pager)