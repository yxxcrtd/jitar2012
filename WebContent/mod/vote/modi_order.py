#encoding=utf-8
from common_data import CommonData
from vote_query import VoteQuery
from java.util import HashMap

class modi_order(CommonData):
    def __init__(self):        
        CommonData.__init__(self)  # some share variable can get
        self.vote_svc = __spring__.getBean("voteService")
        self.AuthorityCheck_svc = __spring__.getBean("pluginAuthorityCheckService")
        self.vote = None
        
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
        
        canManage = self.AuthorityCheck_svc.canManagePluginInstance(self.commonObject, self.loginUser)
        if canManage == False:
            self.addActionError(u"权限被拒绝。")
            return self.ERROR
        
        if request.getMethod()== "POST":
            self.post_form()
        
        
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
        map.put("loginUser",self.loginUser)
        map.put("parentGuid",self.parentGuid)
        map.put("parentType",self.parentType)

        pagedata = self.pageFrameService.transformTemplate(map, "/WEB-INF/mod/vote/modi_order.ftl")      
        
        page_frame = self.pageFrameService.getFramePage(self.parentGuid,self.parentType)
        page_frame = page_frame.replace("[placeholder_content]",pagedata)
        page_frame = page_frame.replace("[placeholder_title]",u"修改调查投票显示顺序")        
        self.writeToResponse(page_frame)
        
    def post_form(self):
        qlist = self.params.safeGetIntValues("q_list")
        alist = self.params.safeGetIntValues("answer_list")
        for q in qlist:
            question = self.vote_svc.getVoteQuestionById(q)
            if question != None:
                question.setItemIndex(self.params.safeGetIntParam("question_" + str(q)))
                self.vote_svc.updateVoteQuestion(question)        

        for aid in alist:
            answer = self.vote_svc.getVoteQuestionAnswerById(aid)
            if answer != None:
                answer.setItemIndex(self.params.safeGetIntParam("question_answer_" + str(aid)))
                self.vote_svc.updateVoteQuestionAnswer(answer)
        response.sendRedirect("showresult.action?guid=" + self.parentGuid + "&type=" + self.parentType + "&voteId=" + str(self.vote.voteId))
        