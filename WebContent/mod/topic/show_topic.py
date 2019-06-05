#encoding=utf-8
from common_data import CommonData
from java.util import Date
from java.util import HashMap
from plugintopic_query import PlugInTopicReplyQuery
from cn.edustar.jitar.pojos import PlugInTopicReply

class show_topic(CommonData):
    def __init__(self):        
        CommonData.__init__(self) 
        self.topic_svc = __spring__.getBean("plugInTopicService")
        self.AuthorityCheck_svc = __spring__.getBean("pluginAuthorityCheckService")
        self.plugInTopicId = 0
        self.plugInTopic = None
        
    def execute(self):
        if self.parentGuid == "" or self.parentType == "":
            self.addActionError(u"无效的标识。")
            return self.ERROR
        
        self.plugInTopicId = self.params.safeGetIntParam("topicId")
        if self.plugInTopicId == 0:
            self.addActionError(u"无效的讨论标识。")
            return self.ERROR
        self.plugInTopic = self.topic_svc.getPlugInTopicById(self.plugInTopicId)
        if self.plugInTopic == None:
            self.addActionError(u"无法加载讨论对象。")
            return self.ERROR
        
        if request.getMethod() == "POST":
            ret = self.save_reply()
            if ret != "":
                return ret
        
        qry = PlugInTopicReplyQuery(""" ptr.plugInTopicReplyId, ptr.title, ptr.createDate, ptr.createUserId, ptr.createUserName, ptr.replyContent """)
        qry.plugInTopicId = self.plugInTopicId
        pager = self.params.createPager()
        pager.itemName = u"讨论话题"
        pager.itemUnit = u"个"
        pager.pageSize = 20
        pager.totalRows = qry.count()
        topic_reply_list = qry.query_map(pager)

        canManage = "false"
        if self.AuthorityCheck_svc.canManagePluginInstance(self.commonObject, self.loginUser) == True:
            canManage = "true" 
        self.returl = self.params.safeGetStringParam("returl")
        
        map = HashMap()
        map.put("plugInTopic",self.plugInTopic)
        map.put("pager",pager)
        map.put("topic_reply_list",topic_reply_list)        
        
        map.put("SiteUrl",self.pageFrameService.getSiteUrl())
        map.put("UserMgrUrl",self.pageFrameService.getUserMgrUrl())
        map.put("loginUser",self.loginUser)
        map.put("head_nav","special_subject")
        map.put("returl",self.returl)
        map.put("parentGuid",self.parentGuid)
        map.put("parentType",self.parentType)

        pagedata = self.pageFrameService.transformTemplate(map, "/WEB-INF/mod/topic/show_topic.ftl")      
        
        page_frame = self.pageFrameService.getFramePage(self.parentGuid,self.parentType)
        page_frame = page_frame.replace("[placeholder_content]",pagedata)
        page_frame = page_frame.replace("[placeholder_title]",self.plugInTopic.title + u" 的讨论")        
        self.writeToResponse(page_frame)
        
    def save_reply(self):
        if self.loginUser == None:
            self.addActionError(u"请先登录参与。")
            return self.LOGIN
        
        t = self.params.safeGetStringParam("commentTitle")
        c = self.params.safeGetStringParam("content")
        if t == "" or c == "":
            self.addActionError(u"请输入必要的内容。")
            return self.ERROR
        
        plugInTopicReply = PlugInTopicReply()
        plugInTopicReply.setPlugInTopicId(self.plugInTopicId)
        plugInTopicReply.setTitle(t)
        plugInTopicReply.setCreateDate(Date())
        plugInTopicReply.setCreateUserId(self.loginUser.userId)
        plugInTopicReply.setCreateUserName(self.loginUser.trueName)
        plugInTopicReply.setReplyContent(c)
        plugInTopicReply.setAddIp(self.get_client_ip())
        self.topic_svc.addPlugInTopicReply(plugInTopicReply)
        refUrl = request.getHeader("Referer")
        if refUrl == None or refUrl == "":
            return ""
        else:
            response.sendRedirect(refUrl)
        