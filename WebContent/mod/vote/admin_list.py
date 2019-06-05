#encoding=utf-8
from common_data import CommonData
from vote_query import VoteQuery

class admin_list(CommonData):
    def execute(self):
        if self.loginUser == None:
            self.addActionError(u"请先登录。")
            return self.ERROR
        
        accessControlService = __spring__.getBean("accessControlService")
        if accessControlService.isSystemAdmin(self.loginUser) == False:
            self.addActionError(u"需要超级管理员才能进行管理。")
            return self.ERROR

        vote_svc = __spring__.getBean("voteService")
        
        if request.getMethod() == "POST":
            # 自己可以删除自己创建的？
            guid = self.params.safeGetIntValues("guid")
            for g in guid:
                vote_svc.deleteVoteById(g)        
        
        qry = VoteQuery(""" vote.voteId, vote.title, vote.createDate,vote.createUserId, vote.createUserName, vote.endDate, vote.parentGuid, vote.parentObjectType """)
        pager = self.params.createPager()
        pager.itemName = u"投票"
        pager.itemUnit = u"个"
        pager.pageSize = 20
        pager.totalRows = qry.count()
        vote_list = qry.query_map(pager)
        request.setAttribute("vote_list", vote_list)
        request.setAttribute("pager", pager)
        return "/WEB-INF/mod/vote/admin_list.ftl"