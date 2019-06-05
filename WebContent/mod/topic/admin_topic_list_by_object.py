#encoding=utf-8
from common_data import CommonData
from plugintopic_query import *

class admin_topic_list_by_object(CommonData):
    def __init__(self):
        CommonData.__init__(self)
        
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
        
        if request.getMethod() == "POST":
            topic_svc = __spring__.getBean("plugInTopicService")
            guids = self.params.safeGetIntValues("topicId")
            for g in guids:
                topic_svc.deletePluginTopicById(g)        
        
        qry = PlugInTopicQuery(""" pt.plugInTopicId, pt.title, pt.createDate, pt.createUserId, pt.createUserName, pt.addIp, pt.parentGuid, pt.parentObjectType  """)
        qry.parentGuid = self.parentGuid
        qry.parentObjectType = self.parentType
        pager = self.params.createPager()
        pager.itemName = u"话题讨论"
        pager.itemUnit = u"个"
        pager.pageSize = 20
        pager.totalRows = qry.count()
        topic_list = qry.query_map(pager)
        request.setAttribute("topic_list",topic_list)
        request.setAttribute("pager",pager)
        return "/WEB-INF/mod/topic/admin_topic_list_by_object.ftl"
    