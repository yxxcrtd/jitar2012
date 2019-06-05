#encoding=utf-8
from common_data import CommonData
from plugintopic_query import *

class admin_topic_list_by_object2(CommonData):
    def execute(self):
        if self.parentGuid == "" or self.parentType == "":
            self.addActionError(u"无效的对象标识。")
            return self.ERROR
        
        if self.loginUser == None:
            self.addActionError(u"请先登录。")
            return self.LOGIN
        
        if self.pageFrameService == None:
            self.addActionError(u"没有配置 pageFrameService ，请检查配置文件。")
            return self.ERROR
        
        if self.pluginAuthorityCheckService == None:
            self.addActionError(u"没有配置 pluginAuthorityCheckService ，请检查配置文件。")
            return self.ERROR
        
        canManage = self.pluginAuthorityCheckService.canManagePluginInstance(self.commonObject,self.loginUser)
        if canManage == False:
            self.addActionError(u"权限被拒绝。")
            return self.ACCESS_DENIED
        
        plugInTopicId = self.params.safeGetIntParam("topicId")
        if plugInTopicId == 0:
            self.addActionError(u"缺少讨论标识。")
            return self.ERROR
        
        if request.getMethod() == "POST":            
            topic_svc = __spring__.getBean("plugInTopicService")
            guids = self.params.safeGetIntValues("replyId")
            for g in guids:
                topic_svc.deletePlugInTopicReplyById(g)       
        
        
        qry = PlugInTopicReplyQuery(""" ptr.plugInTopicReplyId, ptr.plugInTopicId, ptr.title, ptr.createDate,ptr.replyContent,ptr.createUserId, ptr.createUserName, pt.title as ttitle,pt.parentGuid,pt.parentObjectType """)
        qry.plugInTopicId = plugInTopicId
        pager = self.params.createPager()
        pager.itemName = u"回复"
        pager.itemUnit = u"个"
        pager.pageSize = 20
        pager.totalRows = qry.count()
        topic_reply_list = qry.query_map(pager)
        request.setAttribute("topic_reply_list", topic_reply_list)
        request.setAttribute("pager", pager)

        return "/WEB-INF/mod/topic/admin_topic_list_by_object2.ftl"