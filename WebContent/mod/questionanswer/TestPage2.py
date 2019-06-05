#encoding=utf-8
from common_data import CommonData
from java.lang import StringBuilder
from java.util import HashMap
from cn.edustar.jitar.model import SiteUrlModel

class TestPage2(CommonData):
    def __init__(self):        
        CommonData.__init__(self)
        self.pfService = __spring__.getBean("pageFrameService")
        self.questionAnswerService = __spring__.getBean("questionAnswerService")
        self.pageFrameService = __spring__.getBean("pageFrameService")
        self.questionId = 0
        #self.parentGuid = "77ad996b-e0ae-4da6-8c4e-8a42fe584948"
        
    def execute(self):
        
        # print "debug: self.parentGuid = ", self.parentGuid
        # print "debug: self.parentType = ", self.parentType
        if self.parentGuid == "" or self.parentType == "":
            return "/WEB-INF/mod/questionanswer/not_found.ftl"        
        
        self.questionId = self.params.safeGetIntParam("qid")
        
        if self.questionId == 0:
            return "/WEB-INF/mod/questionanswer/not_found.ftl"
        question = self.questionAnswerService.getQuestionById(self.questionId)
        if question == None:
            return "/WEB-INF/mod/questionanswer/not_found.ftl"
        
        map = HashMap()
        map.put("question",question)
        map.put("SiteUrl",self.pageFrameService.getSiteUrl())
        map.put("UserMgrUrl",self.pageFrameService.getUserMgrUrl())
        map.put("return_url","")
        
        answer_list = self.questionAnswerService.getAnswerListByQuestionId(question.questionId)
        if answer_list != None:
            map.put("answer_list",answer_list)
        
        content = self.pageFrameService.transformTemplate(map, "/WEB-INF/mod/questionanswer/getcontent.ftl")      
        
        pagedata = self.pfService.getFramePage(self.parentGuid,self.parentType)
        pagedata = pagedata.replace("[placeholder_content]",content)
        pagedata = pagedata.replace("[placeholder_title]",u"问题与解答")
        
        self.writeToResponse(pagedata)