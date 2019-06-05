#encoding=utf-8
from common_data import CommonData
from vote_query import UserVoteQuery

class my_joined_object(CommonData):
    def execute(self):
        if self.loginUser == None:
            self.addActionError(u"请先登录。")
            return self.ERROR
        
        qry = UserVoteQuery(""" vote.voteId, vote.title, vote.createDate, vote.endDate, vote.parentGuid, vote.parentObjectType """)
        qry.userId = self.loginUser.userId
        pager = self.params.createPager()
        pager.itemName = u"投票"
        pager.itemUnit = u"个"
        pager.pageSize = 20
        pager.totalRows = qry.count()
        vote_list = qry.query_map(pager)
        request.setAttribute("vote_list", vote_list)
        request.setAttribute("pager", pager)
        return "/WEB-INF/mod/vote/my_joined_object.ftl"