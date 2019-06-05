#encoding=utf-8
from common_data import CommonData
from java.util import HashMap
from java.util import Date
from cn.edustar.jitar.pojos import QuestionAnswer


class getcontent(CommonData):
    def __init__(self):        
        CommonData.__init__(self)
        self.questionId = 0
        self.question = None
        
    def execute(self):
        self.pluginService =  __spring__.getBean("pluginService")
        if self.pluginService.checkPluginEnabled("questionanswer") == False:
            request.setAttribute("message",u"该插件已经被管理员禁用。")
            return "/WEB-INF/mod/show_text.ftl"
        

        if self.parentGuid == "" or self.parentType == "":
            return "/WEB-INF/mod/questionanswer/not_found.ftl"        
        
        self.questionId = self.params.safeGetIntParam("qid")
        
        if self.questionId == 0:
            return "/WEB-INF/mod/questionanswer/not_found.ftl"
        
        self.question = self.questionAnswerService.getQuestionById(self.questionId)
        if self.question == None:
            return "/WEB-INF/mod/questionanswer/not_found.ftl"
        
        if request.getMethod() == "POST":
            if self.loginUser == None:
                return "/WEB-INF/mod/questionanswer/not_logined.ftl"
            content = self.params.getStringParam("content")
            if content == "":
                self.addActionError(u"请输入解答内容。")
                return self.ERROR
            self.answer_question()
        
        
        map = HashMap()
        map.put("question",self.question)
        map.put("SiteUrl",self.pageFrameService.getSiteUrl())
        map.put("UserMgrUrl",self.pageFrameService.getUserMgrUrl())
        
        answer_list = self.questionAnswerService.getAnswerListByQuestionId(self.question.questionId)
        if answer_list != None:
            map.put("answer_list",answer_list)
        
        content = self.pageFrameService.transformTemplate(map, "/WEB-INF/mod/questionanswer/getcontent.ftl")      
        
        pagedata = self.pageFrameService.getFramePage(self.parentGuid,self.parentType)
        pagedata = pagedata.replace("[placeholder_content]",content)
        pagedata = pagedata.replace("[placeholder_title]",u"问题与解答")
        
        self.writeToResponse(pagedata)
        
    def answer_question(self):
        content = self.params.getStringParam("content")
        questionAnswer = QuestionAnswer()
        questionAnswer.setQuestionId(self.question.questionId)
        questionAnswer.setCreateDate(Date())
        questionAnswer.setAnswerUserId(self.loginUser.userId)
        questionAnswer.setAnswerUserName(self.loginUser.trueName)
        questionAnswer.setAnswerContent(content)
        questionAnswer.setAddIp(self.get_client_ip())
        
        self.questionAnswerService.saveAnswer(questionAnswer)