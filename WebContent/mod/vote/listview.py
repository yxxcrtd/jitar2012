#encoding=utf-8
from common_data import CommonData
from cn.edustar.jitar.pojos import Plugin

class listview(CommonData):
    def __init__(self):        
        CommonData.__init__(self)  # some share variable can get
        self.vote_svc = __spring__.getBean("voteService")
        self.AuthorityCheck_svc = __spring__.getBean("pluginAuthorityCheckService")        
        self.vote = None
        
    def execute(self):
        self.pluginService = __spring__.getBean("pluginService")
        if self.pluginService.checkPluginEnabled("vote") == False:
            request.setAttribute("message", u"该插件已经被管理员禁用。")
            return "/WEB-INF/mod/show_text.ftl"
        
        if self.parentGuid == "" or self.parentType == "":
            self.addActionError(u"无效的标识。")
            return self.ERROR
        self.vote = self.vote_svc.getNewestVote(self.parentGuid)

        if self.vote != None:
            self.show_vote_ui()
            request.setAttribute("vote", self.vote)
        
        canManage = "false"
        if self.AuthorityCheck_svc.canManagePluginInstance(self.commonObject, self.loginUser) == True:
            canManage = "true"
        
        request.setAttribute("canManage", canManage)
        request.setAttribute("unitId", self.params.getIntParamZeroAsNull("unitId"))  
        return "/WEB-INF/mod/vote/listview.ftl" 
        
    def show_vote_ui(self):
        voteHasExpires = self.vote_svc.voteHasExpires(self.vote.voteId)
        if voteHasExpires == True:
            voteHasExpires = "true"
        else:
            voteHasExpires = "false"
    
        """ 得到投票问题和答案 """
        vote_question_list = self.vote_svc.getVoteQuestionList(self.vote.voteId)   
        #print "vote_question_list = ",vote_question_list
        vote_question_answer_list = []
        for q in vote_question_list:
            vote_answer_list = self.vote_svc.getVoteQuestionAnswerList(q.voteQuestionId)
            vote_question = {"question":None, "answer":None}
            vote_question["question"] = q
            vote_question["answer"] = vote_answer_list
            vote_question_answer_list.append(vote_question)        
        
        request.setAttribute("vote_question_answer_list", vote_question_answer_list)
        request.setAttribute("voteHasExpires", voteHasExpires)
        request.setAttribute("loginUser", self.loginUser)
        
