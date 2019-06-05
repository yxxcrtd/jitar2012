from cn.edustar.jitar.util import ParamUtil
from base_action import *
from cn.edustar.jitar.pojos import Feedback
from java.util import Date

class admin_feed(ActionExecutor, SubjectMixiner):
    def __init__(self):
        self.params = ParamUtil(request)
        
    def execute(self):
        if self.loginUser == None:
            return ActionResult.LOGIN
        
        if request.getMethod() == "POST":
            return self.saveData()
        return "/WEB-INF/ftl/admin/admin_feed.ftl"
        
    def saveData(self):
        feedType = self.params.safeGetStringParam("feedType")
        feedContent = self.params.safeGetStringParam("feedContent")
        if feedContent != "":
            f = Feedback()
            f.setLoginName(self.loginUser.loginName)            
            f.setTrueName(self.loginUser.trueName)
            f.setCreateDate(Date())
            f.setFeedbackType(feedType)
            f.setContent(feedContent)
            feedbackService = __spring__.getBean("feedbackService")
            feedbackService.saveOrUpdateFeedback(f)
        return ActionResult.SUCCESS
            