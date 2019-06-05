#encoding=utf-8
from cn.edustar.jitar.pojos import Question
from java.util import Date
from java.util import UUID
from java.util import HashMap
from java.lang import String
from common_data import CommonData

class editform(CommonData):
    def __init__(self):        
        CommonData.__init__(self)  # some share variable can get
        self.question = None
        
        
    def execute(self):
        if self.parentGuid == "" or self.parentType == "":
            return "/WEB-INF/mod/questionanswer/not_found.ftl"
        
        if self.loginUser == None:
             return "/WEB-INF/mod/questionanswer/not_logined.ftl"         
        
        
        self.questionId = self.params.safeGetIntParam("qid")
        if self.questionId != None and self.questionId != 0:
            self.question = self.questionAnswerService.getQuestionById(self.questionId)
        
        
        if request.getMethod() == "POST":
            q_topic = self.params.safeGetStringParam("quesition_title")
            q_content = self.params.safeGetStringParam("quesition_content")    
            if q_topic == "":
                self.addActionError(u"请输入问题。")
                return self.ERROR
            if q_content == "":
                self.addActionError(u"请输入问题描述。")
                return self.ERROR
        
            self.save_or_update()
            response.sendRedirect(self.pageFrameService.getSiteUrl() + "mod/questionanswer/question_getcontent.action?guid=" + self.parentGuid + "&type=" + self.parentType + "&qid=" + str(self.question.questionId))
        
        map = HashMap()
        map.put("question",self.question)
        map.put("SiteUrl",self.pageFrameService.getSiteUrl())
        map.put("UserMgrUrl",self.pageFrameService.getUserMgrUrl())
                
        pagedata = self.pageFrameService.transformTemplate(map, "/WEB-INF/mod/questionanswer/editform.ftl")      
        
        page_frame = self.pageFrameService.getFramePage(self.parentGuid,self.parentType)
        page_frame = page_frame.replace("[placeholder_content]",pagedata)
        page_frame = page_frame.replace("[placeholder_title]",u"提出问题")
        
        self.writeToResponse(page_frame)        
    
    def save_or_update(self):        
        q_topic = self.params.safeGetStringParam("quesition_title")
        q_content = self.params.safeGetStringParam("quesition_content") 
        if self.question == None:
            #objectGuid = UUID.randomUUID().toString().upper()
            self.question = Question()
            self.question.setParentGuid(self.parentGuid)
            #self.question.setObjectGuid(str(objectGuid))
            self.question.setCreateDate(Date())
            self.question.setParentObjectType(self.parentType)
            self.question.setAddIp(self.get_client_ip())
            self.question.setCreateUserName(self.loginUser.trueName)
            self.question.setCreateUserId(self.loginUser.userId)
            
        self.question.setTopic(q_topic)
        self.question.setQuestionContent(q_content)
        self.questionAnswerService.saveOrUpdate(self.question)