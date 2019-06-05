#encoding=utf-8
from common_data import CommonData
from plugintopic_query import *

class admin_list2(CommonData):
    def execute(self):
        if self.loginUser == None:
            self.addActionError(u"请先登录。")
            return self.LOGIN
        
        accessControlService = __spring__.getBean("accessControlService")
        if accessControlService.isSystemAdmin(self.loginUser) == False:
            self.addActionError(u"需要超级管理员才能进行管理。")
            return self.ERROR
        
        if request.getMethod() == "POST":            
            topic_svc = __spring__.getBean("plugInTopicService")
            guids = self.params.safeGetIntValues("guid")
            for g in guids:
                topic_svc.deletePlugInTopicReplyById(g)       
        
        qry = PlugInTopicReplyQuery(""" ptr.plugInTopicReplyId, ptr.plugInTopicId, ptr.title, ptr.createDate,ptr.replyContent,ptr.createUserId, ptr.createUserName, pt.title as ttitle,pt.parentGuid,pt.parentObjectType """)
        pager = self.params.createPager()
        pager.itemName = u"回复"
        pager.itemUnit = u"个"
        pager.pageSize = 20
        pager.totalRows = qry.count()
        topic_reply_list = qry.query_map(pager)
        request.setAttribute("topic_reply_list", topic_reply_list)
        request.setAttribute("pager", pager)

        return "/WEB-INF/mod/topic/admin_list2.ftl"