#encoding=utf-8
from common_data import CommonData
from java.util import HashMap
from plugintopic_query import PlugInTopicQuery


class listall(CommonData):
    def __init__(self):        
        CommonData.__init__(self) 
        self.topic_svc = __spring__.getBean("plugInTopicService")
        
    def execute(self):
                
        if self.parentGuid == "" or self.parentType == "":
            self.addActionError(u"无效的标识。")
            return self.ERROR        
        qry = PlugInTopicQuery(""" pt.plugInTopicId, pt.title, pt.createDate, pt.createUserId, pt.createUserName """)
        qry.parentGuid = self.parentGuid
        qry.parentObjectType = self.parentType
        pager = self.params.createPager()
        pager.itemName = u"讨论"
        pager.itemUnit = u"个"
        pager.pageSize = 20
        pager.totalRows = qry.count()
        topic_list = qry.query_map(pager)
        
        map = HashMap()
        map.put("SiteUrl", self.pageFrameService.getSiteUrl())
        map.put("UserMgrUrl", self.pageFrameService.getUserMgrUrl())
        map.put("topic_list", topic_list)
        map.put("pager", pager)
        map.put("loginUser", self.loginUser)
        map.put("parentGuid", self.parentGuid)
        map.put("parentType", self.parentType)
        map.put("unitId", self.params.getIntParamZeroAsNull("unitId"))
        
        pagedata = self.pageFrameService.transformTemplate(map, "/WEB-INF/mod/topic/listall.ftl")      
        
        page_frame = self.pageFrameService.getFramePage(self.parentGuid, self.parentType)
        page_frame = page_frame.replace("[placeholder_content]", pagedata)
        page_frame = page_frame.replace("[placeholder_title]", u"全部话题讨论")        
        self.writeToResponse(page_frame)
