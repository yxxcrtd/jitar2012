from evaluation_query import *
from base_action import *
class evaluationList(EvaluationBase,SubjectMixiner):
    def __init__(self):                
        self.params = ParamUtil(request)
    
    def execute(self):
        type = self.params.getStringParam("listType")
        ispage = self.params.getStringParam("ispage")
        if ispage==None:
            ispage=""
        userId = self.params.getIntParamZeroAsNull("userId")
        request.setAttribute("ispage", ispage)
        if ispage=="1":
            pager = self.params.createPager()
            pager.itemName = u"评课"
            pager.itemUnit = u"个"
            pager.pageSize = 20
        
        qry = EvaluationPlanQuery("ev.evaluationPlanId, ev.evaluationCaption,ev.metaSubjectId, ev.metaGradeId, ev.startDate, ev.endDate, ev.teacherId, ev.teacherName,ev.teachDate,ev.createrId,subj.msubjName, grad.gradeName")
        qry.userId=userId
        qry.enabled=True
        if type == "1":
            qry.listType=1
        elif type == "0":
            qry.listType=0
        else:
            qry.listType=1
        
        if ispage=="1":
            pager.totalRows = qry.count()
            plan_list = qry.query_map(pager)
            request.setAttribute("pager", pager)
        else:
            plan_list = qry.query_map()
        request.setAttribute("plan_list", plan_list)
        return "/WEB-INF/ftl/evaluation/userevaluationlist.ftl"