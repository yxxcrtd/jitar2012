#encoding=utf-8
from common_data import CommonData
from plugintopic_query import *

class my_created_object(CommonData):
    def execute(self):
        if self.loginUser == None:
            self.addActionError(u"请先登录。")
            return self.LOGIN
        if request.getMethod() == "POST":            
            topic_svc = __spring__.getBean("plugInTopicService")
            guids = self.params.safeGetIntValues("guid")
            for g in guids:
                topic_svc.deletePluginTopicById(g)
       
        
        qry = PlugInTopicQuery(""" pt.plugInTopicId,pt.title,pt.createDate,pt.parentGuid,pt.parentObjectType """)
        qry.createUserId = self.loginUser.userId
        pager = self.params.createPager()
        pager.itemName = u"讨论"
        pager.itemUnit = u"个"
        pager.pageSize = 20
        pager.totalRows = qry.count()
        topic_list = qry.query_map(pager)
        request.setAttribute("topic_list", topic_list)
        request.setAttribute("pager", pager)
        

        return "/WEB-INF/mod/topic/my_created_object.ftl"