#encoding=utf-8
from common_data import CommonData
from plugintopic_query import PlugInTopicQuery

class listview(CommonData):
    def __init__(self):        
        CommonData.__init__(self) 
        self.topic_svc = __spring__.getBean("plugInTopicService")
        self.AuthorityCheck_svc = __spring__.getBean("pluginAuthorityCheckService")
        
    def execute(self):
        self.pluginService = __spring__.getBean("pluginService")
        if self.pluginService.checkPluginEnabled("topic") == False:
            request.setAttribute("message", u"该插件已经被管理员禁用。")
            return "/WEB-INF/mod/show_text.ftl"
        
        if self.parentGuid == "" or self.parentType == "":
            self.addActionError(u"无效的标识。")
            return self.ERROR        
        qry = PlugInTopicQuery(""" pt.plugInTopicId, pt.title, pt.createDate, pt.createUserId, pt.createUserName """)
        qry.parentGuid = self.parentGuid
        qry.parentObjectType = self.parentType
        topic_list = qry.query_map(10)

        canManage = "false"
        if self.AuthorityCheck_svc.canManagePluginInstance(self.commonObject, self.loginUser) == True:
            canManage = "true" 
        returl = self.params.safeGetStringParam("returl")
        
        request.setAttribute("topic_list", topic_list)
        request.setAttribute("canManage", canManage)
        request.setAttribute("loginUser", self.loginUser)
        request.setAttribute("returl", returl)
        request.setAttribute("unitId", self.params.getIntParamZeroAsNull("unitId"))
        return "/WEB-INF/mod/topic/listview.ftl"
