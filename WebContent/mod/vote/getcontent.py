#encoding=utf-8
from cn.edustar.jitar.pojos import Vote, VoteQuestion, VoteQuestionAnswer, VoteResult, VoteUser
from java.util import Date
from java.text import SimpleDateFormat
from java.util import UUID
from java.util import HashMap
from common_data import CommonData

"""

说明：
投票的规则：
1，匿名用户不能参与投票
2，一人只能投票一次
3，匿名用户可以浏览投票
4，用户是系统登录用户即可，无需分类

"""


class getcontent(CommonData):
    def __init__(self): 
        CommonData.__init__(self)  # some share variable can get
        self.vote = None
        self.vote_svc =  __spring__.getBean("voteService")

    def execute(self):
        if self.parentGuid == "" or self.parentType == "":
            self.addActionError(u"无效的访问。")
            return self.ERROR
        voteId = self.params.safeGetIntParam("voteId")
        if voteId == 0:
            self.addActionError(u"请选择一个投票。")
            return self.ERROR
        self.vote = self.vote_svc.getVoteById(voteId)
        if self.vote == None:
            self.addActionError(u"无法加载投票，可能已经被删除、或者禁用。")
            return self.ERROR
        
        voteHasExpires = self.vote_svc.voteHasExpires(self.vote.voteId)
        if voteHasExpires == True:
            voteHasExpires = "true"
        else:
            voteHasExpires = "false"
        
        if request.getMethod() == "POST":
            return self.save_post()
        
        """ 得到投票问题和答案 """
        vote_question_list = self.vote_svc.getVoteQuestionList(self.vote.voteId)
        vote_question_answer_list = []
        for q in vote_question_list:
            vote_answer_list = self.vote_svc.getVoteQuestionAnswerList(q.voteQuestionId)
            vote_question = {"question":None,"answer":None}
            #for x in vote_question:
            #    print x
            vote_question["question"] = q
            vote_question["answer"] = vote_answer_list
            vote_question_answer_list.append(vote_question)        
        
        map = HashMap()
        map.put("SiteUrl",self.pageFrameService.getSiteUrl())
        map.put("UserMgrUrl",self.pageFrameService.getUserMgrUrl())
        map.put("vote",self.vote)
        map.put("vote_question_answer_list",vote_question_answer_list)
        map.put("voteHasExpires",voteHasExpires)
        map.put("loginUser",self.loginUser)
        map.put("parentGuid",self.parentGuid)
        map.put("parentType",self.parentType)

        pagedata = self.pageFrameService.transformTemplate(map, "/WEB-INF/mod/vote/getcontent.ftl")      
        
        page_frame = self.pageFrameService.getFramePage(self.parentGuid,self.parentType)
        page_frame = page_frame.replace("[placeholder_content]",pagedata)
        page_frame = page_frame.replace("[placeholder_title]",u"调查投票")        
        self.writeToResponse(page_frame)
        
    def save_post(self):
        if self.loginUser == None:
            self.addActionError(u"要参与投票，请先登录。")
            return self.ERROR
        hasVoted = self.vote_svc.checkVoteResultWithUserId(self.vote.voteId, self.loginUser.userId)
        if hasVoted == True:
            self.addActionError(u"你已经投过票了，谢谢你的参与。")
            return self.ERROR
        q_list = self.params.safeGetIntValues("q_list")
        for q in q_list:
            answer_id = self.params.safeGetIntValues("q_" + str(q))
            for a_id in answer_id:
                q_ret = VoteResult(a_id, Date(), self.loginUser.userId)
                self.vote_svc.addVoteResult(q_ret)
        voteUser = VoteUser()
        voteUser.setVoteId(self.vote.voteId)
        voteUser.setUserId(self.loginUser.userId)
        voteUser.setAddIp(self.get_client_ip())
        self.vote_svc.addVoteUser(voteUser)
        #更新投票数据
        self.vote_svc.reCountVoteData(self.vote)
        response.sendRedirect("showresult.action?guid=" + self.parentGuid + "&type=" + self.parentType + "&voteId=" + str(self.vote.voteId))
        