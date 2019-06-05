from evaluation_query import *

class evaluation_show_user(EvaluationBase):
    
    def execute(self):
        pager = self.params.createPager()
        pager.itemName = u"评课"
        pager.itemUnit = u"个"
        pager.pageSize = 20        
        qry = EvaluationContentQuery("ec.evaluationContentId, ec.title, ec.courseTeacherName, ec.publishUserName, ec.metaSubjectId, ec.metaGradeId, subj.msubjName, grad.gradeName, ec.createDate")
        qry.publishUserId = self.loginUser.userId
        pager.totalRows = qry.count()
        content_list = qry.query_map(pager)
        request.setAttribute("pager", pager)
        request.setAttribute("content_list", content_list)
        return "/WEB-INF/ftl/evaluation/evaluation_show_user.ftl"