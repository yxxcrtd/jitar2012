#encoding=utf-8
from cn.edustar.jitar.pojos import Vote, VoteQuestion, VoteQuestionAnswer, VoteResult
from java.util import HashMap
from common_data import CommonData

class showresult(CommonData):
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
        #self.vote_svc.reCountVoteData(self.vote)
        voteTotal = self.vote_svc.getVoteUserCount(self.vote.voteId)
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
        map.put("vote_total",voteTotal)
        map.put("loginUser",self.loginUser)
        map.put("parentGuid",self.parentGuid)
        map.put("parentType",self.parentType)

        pagedata = self.pageFrameService.transformTemplate(map, "/WEB-INF/mod/vote/showresult.ftl")      
        
        page_frame = self.pageFrameService.getFramePage(self.parentGuid,self.parentType)
        page_frame = page_frame.replace("[placeholder_content]",pagedata)
        page_frame = page_frame.replace("[placeholder_title]",u"查看调查投票结果")        
        self.writeToResponse(page_frame)