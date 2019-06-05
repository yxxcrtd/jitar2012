#encoding=utf-8
from cn.edustar.jitar.pojos import Question
from java.util import Date
from java.util import UUID
from common_data import CommonData

class qa_editform(CommonData):
    def __init__(self):        
        CommonData.__init__(self)  # some share variable can get
        self.questionAnswerService = __spring__.getBean("questionAnswerService")
        self.question = None
        self.parentGuid = ""
        
    def execute(self):
        if self.loginUser == None:
            return "/WEB-INF/mod/questionanswer/not_logined.ftl"
        
        # print "调式：self.parentGuid = ", self.parentGuid
        # print "调式：self.parentType = ", self.parentType
        
        
        if self.parentGuid == "":
            return "/WEB-INF/mod/questionanswer/not_found.ftl"
        self.questionId = self.params.safeGetIntParam("questionId")        
        self.question = self.questionAnswerService.getQuestionById(self.questionId)
        self.redUrl = self.params.safeGetStringParam("redUrl")        
        if request.getMethod() == "POST":
            return self.save_or_update()
        
        request.setAttribute("question",self.question)
        request.setAttribute("parentGuid",self.parentGuid)
        request.setAttribute("redUrl",self.redUrl)
        request.setAttribute("refer",request.getHeader("referer"))
        
        return "/WEB-INF/mod/questionanswer/editform.ftl"
    
    def save_or_update(self):
        q_topic = self.params.safeGetStringParam("quesition_title")
        q_content = self.params.safeGetStringParam("quesition_content")    
 
        if self.question == None:
            objectGuid = UUID.randomUUID()            
            self.question = Question()
            self.question.setParentGuid(self.parentGuid)
            self.question.setObjectGuid(str(objectGuid))
            self.question.setCreateDate(Date())
            self.question.setAddIp(self.get_client_ip())
            self.question.setCreateUserName(self.loginUser.trueName)
            self.question.setCreateUserId(self.loginUser.userId)
            
        self.question.setTopic(q_topic)
        self.question.setQuestionContent(q_content)
        self.questionAnswerService.saveOrUpdate(self.question)
        response.sendRedirect(self.redUrl)
        return
        return "/WEB-INF/mod/questionanswer/success.ftl"    