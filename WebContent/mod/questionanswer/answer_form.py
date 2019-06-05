#encoding=utf-8
from common_data import CommonData
from cn.edustar.jitar.pojos import QuestionAnswer
from java.util import Date

class answer_form(CommonData):
    def __init__(self):        
        CommonData.__init__(self)  # some share variable can get
        self.questionAnswerService = __spring__.getBean("questionAnswerService")
        self.questionId = 0
    def execute(self):
        if self.loginUser == None:
            return "/WEB-INF/mod/questionanswer/not_logined.ftl"
        self.questionId = self.params.safeGetIntParam("qid")
        # print "self.questionId = ", self.questionId
        if self.questionId == 0:
            return "/WEB-INF/mod/questionanswer/not_found.ftl"
        question = self.questionAnswerService.getQuestionById(self.questionId)
        if question == None:
            return "/WEB-INF/mod/questionanswer/not_found.ftl"
        content = self.params.getStringParam("content")
        if content == None or content == "":
            return "/WEB-INF/mod/questionanswer/not_found.ftl"
            
        questionAnswer = QuestionAnswer()
        questionAnswer.setQuestionId(question.questionId)
        questionAnswer.setCreateDate(Date())
        questionAnswer.setAnswerUserId(self.loginUser.userId)
        questionAnswer.setAnswerUserName(self.loginUser.trueName)
        questionAnswer.setAnswerContent(content)
        questionAnswer.setAddIp(self.get_client_ip())
        
        self.questionAnswerService.saveAnswer(questionAnswer)        
        response.sendRedirect(request.getParameter("return_url"))
        