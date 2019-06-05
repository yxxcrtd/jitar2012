#encoding=utf-8
from common_data import CommonData
from question_query import QuestionQuery

class my_received_object(CommonData):
    def execute(self):
        if self.loginUser == None:
            self.addActionError(u"请先登录。")
            return self.LOGIN
        
        # 列出当前用户的所有问答实例
        q_svc = __spring__.getBean("questionAnswerService")
        
        if request.getMethod() == "POST":            
            guid = self.params.safeGetIntValues("guid")
            for g in guid:
                q_svc.deleteQuestionByQuestionId(g)        
        
        qry = QuestionQuery(""" q.questionId, q.parentGuid, q.parentObjectType, q.topic, q.createDate, q.createUserId, q.createUserName, q.questionContent, q.addIp """)
        qry.parentGuid = self.loginUser.userGuid
        pager = self.params.createPager()
        pager.itemName = u"问题"
        pager.itemUnit = u"个"
        pager.pageSize = 20
        pager.totalRows = qry.count()
        q_list = qry.query_map(pager)
        request.setAttribute("q_list", q_list)
        request.setAttribute("pager", pager)
        
        return "/WEB-INF/mod/questionanswer/my_received_object.ftl"        