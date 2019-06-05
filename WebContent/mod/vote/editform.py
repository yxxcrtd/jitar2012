#encoding=utf-8
from cn.edustar.jitar.pojos import Vote, VoteQuestion, VoteQuestionAnswer
from java.util import Date
from java.text import SimpleDateFormat
from java.util import UUID
from java.util import HashMap
from common_data import CommonData

class editform(CommonData):
    def __init__(self):        
        CommonData.__init__(self)  # some share variable can get
        self.vote = None
        self.vote_svc =  __spring__.getBean("voteService")

    def execute(self):
        if self.parentGuid == "" or self.parentType == "":
            self.addActionError(u"无效的访问。")
            return self.ERROR
        
        if self.loginUser == None:
            self.addActionError(u"请先登录。")
            return self.LOGIN
        
        # 检查权限：
        canCreate = self.pluginAuthorityCheckService.canManagePluginInstance(self.commonObject, self.loginUser)
        if canCreate == False:
            self.addActionError(u"你无权进行操作。")
            return self.ERROR
        
        if request.getMethod() == "POST":
            return self.save_or_update()
        map = HashMap()
        map.put("SiteUrl",self.pageFrameService.getSiteUrl())
        map.put("UserMgrUrl",self.pageFrameService.getUserMgrUrl())
        
        pagedata = self.pageFrameService.transformTemplate(map, "/WEB-INF/mod/vote/editform.ftl")      
        
        page_frame = self.pageFrameService.getFramePage(self.parentGuid,self.parentType)
        page_frame = page_frame.replace("[placeholder_content]",pagedata)
        page_frame = page_frame.replace("[placeholder_title]",u"发起调查投票")        
        self.writeToResponse(page_frame)        
        
    def save_or_update(self):
        vote_title = self.params.safeGetStringParam("vote_title")
        vote_desc = self.params.safeGetStringParam("vote_desc")
        vote_enddate = self.params.safeGetStringParam("vote_enddate")
        question_num = self.params.safeGetIntParam("question_num")
        if vote_title == "":
            self.addActionError(u"请输入调查或者投票的名称。")
            return self.ERROR
        if vote_enddate == "":
            self.addActionError(u"请输入调查或者投票的结束日期。")
            return self.ERROR
        ed = Date()
        try:
            ed  = SimpleDateFormat("yyyy-MM-dd").parse(vote_enddate)
        except:
            self.addActionError(u"输入的日期格式不正确，应当是: '年年年年-月月-日日' 格式。")
            return self.ERROR
        
        if question_num < 1:
            self.addActionError(u"请输入调查问题。")
            return self.ERROR
        
        # 先保存投票
        self.vote = Vote()
        self.vote.setTitle(vote_title)
        self.vote.setDescription(vote_desc)
        self.vote.setCreateDate(Date())
        self.vote.setCreateUserId(self.loginUser.userId)
        self.vote.setCreateUserName(self.loginUser.trueName)
        self.vote.setEndDate(ed)
        self.vote.setParentGuid(self.parentGuid)
        self.vote.setParentObjectType(self.parentType)        
        self.vote_svc.addVote(self.vote)
        
        # 保存问题：
        for i in range(1, question_num + 1):
            vote_q = self.params.safeGetStringParam("vote_q_" + str(i))
            vote_t = self.params.safeGetIntParam("vote_t_" + str(i))
            vote_max = self.params.safeGetIntParam("vote_max_" + str(i))
            if vote_q.strip() != "":               
                vote_question = VoteQuestion()
                vote_question.setTitle(vote_q)
                vote_question.setVoteId(self.vote.voteId)
                vote_question.setQuestionType(vote_t)
                vote_question.setMaxSelectCount(vote_max)
                vote_question.setItemIndex(i)
                self.vote_svc.addVoteQuestion(vote_question)
                                
                vote_a_list = request.getParameterValues("vote_a_" + str(i))
                m = 0
                for answer in vote_a_list:
                    if answer.strip() != "":
                        vote_question_answer = VoteQuestionAnswer()
                        vote_question_answer.setAnswerContent(answer.strip())
                        vote_question_answer.setVoteQuestionId(vote_question.voteQuestionId)
                        vote_question_answer.setItemIndex(m)
                        m = m + 1
                        self.vote_svc.addVoteQuestionAnswer(vote_question_answer)
                        
        response.sendRedirect("getcontent.py?guid=" + self.parentGuid + "&type=" + self.parentType + "&voteId=" + str(self.vote.voteId))