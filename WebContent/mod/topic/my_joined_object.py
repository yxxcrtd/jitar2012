#encoding=utf-8
from common_data import CommonData
from plugintopic_query import *

class my_joined_object(CommonData):
    def execute(self):
        if self.loginUser == None:
            self.addActionError(u"请先登录。")
            return self.LOGIN
        if request.getMethod() == "POST":            
            topic_svc = __spring__.getBean("plugInTopicService")
            guids = self.params.safeGetIntValues("guid")
            for g in guids:
                topic_svc.deletePlugInTopicReplyById(g)       
        
        qry = PlugInTopicReplyQuery(""" ptr.plugInTopicReplyId, ptr.plugInTopicId, ptr.title, ptr.createDate,ptr.replyContent, pt.title as ttitle,pt.parentGuid,pt.parentObjectType """)
        qry.createUserId = self.loginUser.userId
        pager = self.params.createPager()
        pager.itemName = u"回复"
        pager.itemUnit = u"个"
        pager.pageSize = 20
        pager.totalRows = qry.count()
        topic_reply_list = qry.query_map(pager)
        request.setAttribute("topic_reply_list", topic_reply_list)
        request.setAttribute("pager", pager)        

        return "/WEB-INF/mod/topic/my_joined_object.ftl"