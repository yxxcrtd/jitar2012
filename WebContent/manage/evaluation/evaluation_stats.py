from cn.edustar.jitar.util import ParamUtil
from evaluation_query import *
from base_action import *

class evaluation_stats(ActionResult, SubjectMixiner, EvaluationBase):
    def __init__(self):
        self.params = ParamUtil(request)
        
    def execute(self):
        if self.canManage() == False:
            self.addActionError(u"需要系统内容管理员进行管理。")
            return self.ERROR
        
        cmd = self.params.safeGetStringParam("cmd")
        if cmd == "":cmd = "list"
        if cmd == "list":
            self.list()
        
        return "/WEB-INF/ftl/evaluation/evaluation_stats.ftl"
    
    def list(self):
        qry = EvaluationPlanQuery("ev.evaluationPlanId, ev.evaluationYear, ev.evaluationSemester, ev.evaluationTimes, ev.metaSubjectId, ev.metaGradeId, ev.startDate, ev.endDate, ev.userCount, ev.enabled, ev.attendUserCount, ev.evaluationCount")
        pager = self.params.createPager()
        pager.itemName = u"评课活动"
        pager.itemUnit = u"个"
        pager.totalRows = qry.count()
        pager.pageSize = 20
        evaluation_list = qry.query_map(pager)
        request.setAttribute("pager", pager)
        request.setAttribute("evaluation_list", evaluation_list)
        self.putSubjectList()
        self.putGradeList()
