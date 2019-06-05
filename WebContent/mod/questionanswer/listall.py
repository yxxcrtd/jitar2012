#encoding=utf-8
from common_data import CommonData
from question_query import QuestionQuery
from java.util import HashMap

class listall(CommonData):
    def __init__(self):        
        CommonData.__init__(self)  # some share variable can get

    def execute(self):       
        self.pluginService =  __spring__.getBean("pluginService")
        if self.pluginService.checkPluginEnabled("questionanswer") == False:
            request.setAttribute("message",u"该插件已经被管理员禁用。")
            return "/WEB-INF/mod/show_text.ftl"
        
        if self.parentGuid == "" or self.parentType == "":
            return "/WEB-INF/mod/questionanswer/not_found.ftl"
        
        pageIndex = self.params.safeGetStringParam("page")
        if pageIndex.isdigit() == False:
            pageIndex = "1"
        
        qry = QuestionQuery(""" q.questionId,q.topic,q.createDate,q.createUserId,q.createUserName,
                                q.objectGuid,q.createUserId, q.createUserName """)
        qry.parentGuid = self.parentGuid
        pager = self.params.createPager()
        pager.itemName = u"问题"
        pager.itemUnit = u"个"
        pager.pageSize = 20
        pager.setCurrentPage(int(pageIndex))
        pager.totalRows = qry.count()
        q_list = qry.query_map(pager)        
        
        map = HashMap()
        map.put("SiteUrl",self.pageFrameService.getSiteUrl())
        map.put("UserMgrUrl",self.pageFrameService.getUserMgrUrl())
        map.put("q_list", q_list)
        map.put("pager", pager)
        map.put("parentGuid", self.parentGuid)
        map.put("parentType", self.parentType)        
        
        pagedata = self.pageFrameService.transformTemplate(map, "/WEB-INF/mod/questionanswer/listall.ftl")      
        
        page_frame = self.pageFrameService.getFramePage(self.parentGuid,self.parentType)
        page_frame = page_frame.replace("[placeholder_content]",pagedata)
        page_frame = page_frame.replace("[placeholder_title]",u"全部问题列表")

        self.writeToResponse(page_frame)