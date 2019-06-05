#encoding=utf-8
from java.util import Date
from java.util import HashMap
from java.lang import String
from common_data import CommonData
from cn.edustar.jitar.pojos import PlugInTopic

class new_topic(CommonData):
    def __init__(self):        
        CommonData.__init__(self)
        
    def execute(self):
        if self.parentGuid == "" or self.parentType == "":
            self.addActionError(u"无效的访问。")
            return self.ERROR
        if self.loginUser == None:
            self.addActionError(u"请先登录。")
            return self.LOGIN
        self.returl = self.params.safeGetStringParam("returl")
        
        if request.getMethod() == "POST":
            return self.save_or_update()
        map = HashMap()
        map.put("SiteUrl", self.pageFrameService.getSiteUrl())
        map.put("UserMgrUrl", self.pageFrameService.getUserMgrUrl())
        map.put("loginUser", self.loginUser)
        map.put("head_nav", "special_subject")
        map.put("returl", self.returl)        
        
        pagedata = self.pageFrameService.transformTemplate(map, "/WEB-INF/mod/topic/new_topic.ftl")      
        
        page_frame = self.pageFrameService.getFramePage(self.parentGuid, self.parentType)
        page_frame = page_frame.replace("[placeholder_content]", pagedata)
        page_frame = page_frame.replace("[placeholder_title]", u"发起讨论")        
        self.writeToResponse(page_frame)
        
    def save_or_update(self):
        t_title = self.params.safeGetStringParam("ttitle")
        t_content = self.params.safeGetStringParam("tcontent")
        if t_title == "" or t_content == "":
            self.addActionError(u"请输入讨论标题或者讨论内容。")
            return self.ERROR
        plugInTopic = PlugInTopic()
        plugInTopic.setTitle(t_title)
        plugInTopic.setCreateDate(Date())
        plugInTopic.setCreateUserId(self.loginUser.userId)
        plugInTopic.setCreateUserName(self.loginUser.trueName)
        plugInTopic.setTopicContent(t_content)
        plugInTopic.setAddIp(self.get_client_ip())
        plugInTopic.setParentGuid(self.parentGuid)
        plugInTopic.setParentObjectType(self.parentType)
        self.topic_svc = __spring__.getBean("plugInTopicService")
        self.topic_svc.addPluginTopic(plugInTopic)
        if self.returl == "":
            self.addActionMessage(u"发布成功。")
            return self.SUCCESS
        else:
            response.sendRedirect(self.returl)
