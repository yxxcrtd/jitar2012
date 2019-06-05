from evaluation_query import *
from cn.edustar.jitar.util import ParamUtil

class evaluations_middle(EvaluationBase):
    def __init__(self):                
        self.params = ParamUtil(request)
        
    def execute(self):
        #user = self.loginUser
        #showAll = (user.subjectId == None and user.gradeId == None and self.params.existParam("show") == False)
        pager = self.params.createPager()
        pager.itemName = u"评课"
        pager.itemUnit = u"个"
        pager.pageSize = 20
        
        qry = EvaluationContentQuery("ec.evaluationContentId, ec.title, ec.courseTeacherName, ec.publishUserName, ec.metaSubjectId, ec.metaGradeId, subj.msubjName, grad.gradeName, ec.createDate")
        #if showAll == False:
        #    qry.metaSubjectId = user.subjectId
        #    qry.metaGradeId = user.gradeId
        pager.totalRows = qry.count()
        content_list = qry.query_map(pager)
        request.setAttribute("pager", pager)
        request.setAttribute("content_list", content_list)
        return "/WEB-INF/ftl/evaluation/evaluations_middle.ftl"