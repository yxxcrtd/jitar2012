#encoding=utf-8
from common_data import CommonData
from plugintopic_query import PlugInTopicQuery
from java.util import HashMap

class manage_list(CommonData):
    def __init__(self):        
        CommonData.__init__(self)  # some share variable can get
        self.plugInTopicService = __spring__.getBean("plugInTopicService")
        self.pluginAuthorityCheckService = __spring__.getBean("pluginAuthorityCheckService")
        self.vote = None
        
    def execute(self):
        if self.parentGuid == "" or self.parentType == "":
            self.addActionError(u"无效的标识。")
            return self.ERROR
        canManage = self.pluginAuthorityCheckService.canManagePluginInstance(self.commonObject, self.loginUser)
        if canManage == False:
            self.addActionError(u"权限被拒绝。")
            return self.ERROR
        
        if request.getMethod()== "POST":
            self.post_form()
        return self.get_form()
        
    def post_form(self):
        guid = self.params.safeGetIntValues("q_guid")
        for g in guid:
            self.plugInTopicService.deletePluginTopicById(g)
        refurl = request.getHeader("Referer")
        if refurl == None or refurl == "":
            refurl = self.pageFrameService.getSiteUrl() + "mod/topic/manage_list.py?guid=" + self.parentGuid + "&type=" + self.parentType
        response.sendRedirect(refurl)

    def get_form(self):
        show_type = self.params.safeGetStringParam("show")
        qry = PlugInTopicQuery(""" pt.plugInTopicId, pt.title, pt.createDate, pt.createUserId, pt.createUserName """)
        qry.parentGuid = self.parentGuid
        pager = self.params.createPager()
        pager.itemName = u"讨论"
        pager.itemUnit = u"个"
        pager.pageSize = 20
        pager.totalRows = qry.count()
        t_list = qry.query_map(pager)
        if show_type == "":
            map = HashMap()
            map.put("SiteUrl",self.pageFrameService.getSiteUrl())
            map.put("UserMgrUrl",self.pageFrameService.getUserMgrUrl())
            map.put("t_list",t_list)
            map.put("pager",pager)
            map.put("loginUser",self.loginUser)
            map.put("parentGuid",self.parentGuid)
            map.put("parentType",self.parentType)
    
            pagedata = self.pageFrameService.transformTemplate(map, "/WEB-INF/mod/topic/manage_list.ftl")      
            
            page_frame = self.pageFrameService.getFramePage(self.parentGuid,self.parentType)
            page_frame = page_frame.replace("[placeholder_content]",pagedata)
            page_frame = page_frame.replace("[placeholder_title]",u"讨论管理")  
            self.writeToResponse(page_frame)
            return
        else:
            request.setAttribute("t_list",t_list)
            request.setAttribute("pager",pager)
            return "/WEB-INF/mod/topic/manage_list_nohead.ftl"