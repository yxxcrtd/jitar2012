#encoding=utf-8
from common_data import CommonData
from vote_query import VoteQuery
from java.util import HashMap

class manage_list(CommonData):
    def __init__(self):        
        CommonData.__init__(self)  # some share variable can get
        self.vote_svc = __spring__.getBean("voteService")
        self.AuthorityCheck_svc = __spring__.getBean("pluginAuthorityCheckService")
        self.vote = None
        
    def execute(self):
        if self.parentGuid == "" or self.parentType == "":
            self.addActionError(u"无效的标识。")
            return self.ERROR
        canManage = self.AuthorityCheck_svc.canManagePluginInstance(self.commonObject, self.loginUser)
        if canManage == False:
            self.addActionError(u"权限被拒绝。")
            return self.ERROR
        
        if request.getMethod()== "POST":
            self.post_form()
        
        return self.get_form()        
        
    def post_form(self):
        guid = self.params.safeGetIntValues("q_guid")
        for g in guid:
            self.vote_svc.deleteVoteById(g)

    def get_form(self):
        show_type = self.params.safeGetStringParam("show")
        qry = VoteQuery(""" vote.voteId, vote.title, vote.createDate, vote.createUserId, vote.createUserName, vote.endDate, vote.parentGuid, vote.parentObjectType """)
        qry.parentGuid = self.parentGuid
        pager = self.params.createPager()
        pager.itemName = u"投票"
        pager.itemUnit = u"个"
        pager.pageSize = 20
        pager.totalRows = qry.count()
        vote_list = qry.query_map(pager)
        
        if show_type == "":
            map = HashMap()
            map.put("SiteUrl",self.pageFrameService.getSiteUrl())
            map.put("UserMgrUrl",self.pageFrameService.getUserMgrUrl())
            map.put("vote_list",vote_list)
            map.put("pager",pager)
            map.put("loginUser",self.loginUser)
            map.put("parentGuid",self.parentGuid)
            map.put("parentType",self.parentType)
            pagedata = self.pageFrameService.transformTemplate(map, "/WEB-INF/mod/vote/manage_list.ftl")
            page_frame = self.pageFrameService.getFramePage(self.parentGuid,self.parentType)
            page_frame = page_frame.replace("[placeholder_content]",pagedata)
            page_frame = page_frame.replace("[placeholder_title]",u"调查投票管理")
            self.writeToResponse(page_frame)
            return
        else:
            request.setAttribute("vote_list",vote_list)
            request.setAttribute("pager",pager)
            return "/WEB-INF/mod/vote/manage_list_nohead.ftl" 