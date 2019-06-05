#encoding=utf-8
from common_data import CommonData
from plugintopic_query import *

class admin_list(CommonData):
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
                topic_svc.deletePluginTopicById(g)       
        
        qry = PlugInTopicQuery(""" pt.plugInTopicId, pt.title, pt.createUserId, pt.createUserName, pt.createDate,pt.parentGuid,pt.parentObjectType """)
        pager = self.params.createPager()
        pager.itemName = u"讨论"
        pager.itemUnit = u"个"
        pager.pageSize = 20
        pager.totalRows = qry.count()
        topic_list = qry.query_map(pager)
        request.setAttribute("topic_list", topic_list)
        request.setAttribute("pager", pager)        

        return "/WEB-INF/mod/topic/admin_list.ftl"