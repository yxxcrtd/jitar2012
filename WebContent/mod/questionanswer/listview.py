#encoding=utf-8
from common_data import CommonData

class listview(CommonData):
    def __init__(self):
        CommonData.__init__(self)  # some share variable can get
        self.questionAnswerService = __spring__.getBean("questionAnswerService")
        self.pluginAuthorityCheckService = __spring__.getBean("pluginAuthorityCheckService")
        
    def execute(self):
        self.pluginService = __spring__.getBean("pluginService")
        if self.pluginService.checkPluginEnabled("questionanswer") == False:
            request.setAttribute("message", u"该插件已经被管理员禁用。")
            return "/WEB-INF/mod/show_text.ftl"
        
        if self.parentGuid == "":
            return "/WEB-INF/mod/questionanswer/not_found.ftl"
        q_list = self.questionAnswerService.getQuestionList(self.parentGuid, "new", 5)
        canManage = "false"
        if self.pluginAuthorityCheckService.canManagePluginInstance(self.commonObject, self.loginUser) == True:
            canManage = "true"
                
        request.setAttribute("canManage", canManage)
        request.setAttribute("q_list", q_list)
        request.setAttribute("unitId", self.params.getIntParamZeroAsNull("unitId"))
        
        if self.parentType == "user":
            user = __spring__.getBean("userService").getUserByGuid(self.parentGuid)
            #if user != None:
                #request.setAttribute("user",user)
        return "/WEB-INF/mod/questionanswer/listview.ftl"