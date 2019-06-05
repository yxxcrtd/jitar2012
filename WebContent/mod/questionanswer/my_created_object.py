#encoding=utf-8
from common_data import CommonData
from question_query import QuestionQuery

"""
    
    本程序实现问与答模块的管理功能，操作的时候需要一定按照登录进行过滤

"""

class my_created_object(CommonData):
    def execute(self):
        if self.loginUser == None:
            self.addActionError(u"请先登录。")
            return self.LOGIN
        
        # 列出当前用户的所有问答实例
        q_svc = __spring__.getBean("questionAnswerService")
        
        # 自己可以删除自己创建的？
        guid = self.params.safeGetIntValues("guid")
        for g in guid:
            q_svc.deleteQuestionByQuestionIdAndCreateUserId(g,self.loginUser.userId)        
        
        qry = QuestionQuery(""" q.questionId, q.parentGuid, q.parentObjectType, q.topic, q.createDate, q.createUserId, q.createUserName, q.questionContent, q.addIp """)
        qry.createUserId = self.loginUser.userId
        pager = self.params.createPager()
        pager.itemName = u"问题"
        pager.itemUnit = u"个"
        pager.pageSize = 20
        pager.totalRows = qry.count()
        q_list = qry.query_map(pager)
        request.setAttribute("q_list", q_list)
        request.setAttribute("pager", pager)
        
        return "/WEB-INF/mod/questionanswer/my_created_object.ftl"        