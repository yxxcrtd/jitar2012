#encoding=utf-8
from common_data import CommonData
from vote_query import VoteQuery

class my_created_object(CommonData):
    def execute(self):
        if self.loginUser == None:
            self.addActionError(u"请先登录。")
            return self.ERROR
        
        # 列出当前用户的所有问答实例
        vote_svc = __spring__.getBean("voteService")
        
        if request.getMethod() == "POST":
            # 自己可以删除自己创建的？
            guid = self.params.safeGetIntValues("guid")
            for g in guid:
                vote_svc.deleteVoteWithVoteIdAndCreateUserId(g,self.loginUser.userId)        
        
        qry = VoteQuery(""" vote.voteId, vote.title, vote.createDate, vote.endDate, vote.parentGuid, vote.parentObjectType """)
        qry.createUserId = self.loginUser.userId
        pager = self.params.createPager()
        pager.itemName = u"投票"
        pager.itemUnit = u"个"
        pager.pageSize = 20
        pager.totalRows = qry.count()
        vote_list = qry.query_map(pager)
        request.setAttribute("vote_list", vote_list)
        request.setAttribute("pager", pager)
        return "/WEB-INF/mod/vote/my_created_object.ftl"