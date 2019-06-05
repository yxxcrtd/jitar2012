#encoding=utf-8
from common_data import CommonData
from vote_query import VoteQuery
from java.util import HashMap, Date
from java.text import SimpleDateFormat
from cn.edustar.jitar.pojos import Vote, VoteQuestion, VoteQuestionAnswer


class modify_vote(CommonData):
    def __init__(self):        
        CommonData.__init__(self)  # some share variable can get
        self.vote_svc = __spring__.getBean("voteService")
        self.AuthorityCheck_svc = __spring__.getBean("pluginAuthorityCheckService")
        self.vote = None
        
    def execute(self):
        if self.parentGuid == "" or self.parentType == "":
            self.addActionError(u"无效的标识。")
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
        
        #voteHasExpires = self.vote_svc.voteHasExpires(self.vote.voteId)
        #if voteHasExpires == True:
        #    self.addActionError("该投票已经过期，没有必要再修改了吧。")
        #    return self.ERROR
        
        if request.getMethod()== "POST":
            return self.post_form()
        
        self.get_form()
        
        
    def post_form(self):        
        strVoteTitle = self.params.safeGetStringParam("vote_title")
        strVoteDescription = self.params.safeGetStringParam("vote_desc")
        strEndDate = self.params.safeGetStringParam("vote_enddate")
        if strVoteTitle == "":
            self.addActionError(u"请输入调查或者投票的名称。")
            return self.ERROR
        if strEndDate == "":
            self.addActionError(u"请输入调查或者投票的结束日期。")
            return self.ERROR
        ed = Date()
        try:
            ed  = SimpleDateFormat("yyyy-MM-dd").parse(strEndDate)
        except:
            self.addActionError(u"输入的日期格式不正确，应当是: '年年年年-月月-日日' 格式。")
            return self.ERROR
        
        
        self.vote.setTitle(strVoteTitle)
        self.vote.setDescription(strVoteDescription)
        self.vote.setEndDate(ed)
        self.vote_svc.updateVote(self.vote)       
        
        # print "投票已经更新"
        # 收集数据，区分新加的与原来的。
        # 原来的问题集合
        old_q = self.params.safeGetIntValues("old_q")
        for old_id in old_q:
            # 对于新的问题的，只进行更新，对新的问题选项，则需要插入
            # 原来的问题
            old_question_title = self.params.safeGetStringParam("vote_q_" + str(old_id))
            old_question_type = self.params.safeGetIntParam("vote_t_" + str(old_id))
            old_question_max_selection = self.params.safeGetIntParam("vote_max_" + str(old_id))
            old_question_inemindex = self.params.safeGetIntParam("vote_order_" + str(old_id))
            
            # 如果原来的问题名字为空，则会删除原来的问题、选项、及其投票的结果
            if old_question_title == "":
                # 删除
                self.vote_svc.deleteQuestionById(old_id)
            else:
                old_question = self.vote_svc.getVoteQuestionById(old_id)
                if old_question != None:
                    old_question.setTitle(old_question_title)
                    old_question.setQuestionType(old_question_type)
                    old_question.setMaxSelectCount(old_question_max_selection)
                    old_question.setItemIndex(old_question_inemindex)
                    self.vote_svc.updateVoteQuestion(old_question)                    
                    
                    # 原来问题的选项：
                    old_answer_max_itemdex = 0
                    old_all_answer_id = self.params.safeGetIntValues("q_" + str(old_id) + "_a")
                    for old_a_id in old_all_answer_id:
                        old_answer_desc = self.params.safeGetStringParam("q_a_" + str(old_a_id))
                        if old_answer_desc == "":
                            self.vote_svc.deleteQuestionAnswerById(old_a_id)
                        else:
                            old_answer = self.vote_svc.getVoteQuestionAnswerById(old_a_id)
                            if old_answer != None:
                                if old_answer_max_itemdex < old_answer.getItemIndex():
                                    old_answer_max_itemdex = old_answer.getItemIndex()
                                old_answer.setAnswerContent(old_answer_desc)
                                self.vote_svc.updateVoteQuestionAnswer(old_answer)
                            
                        
                    # 获取新加入的选项：
                    new_answer = request.getParameterValues("new_answer_" + str(old_id))
                    for new_a in new_answer:
                        if new_a != None and new_a != "":                            
                            old_answer_max_itemdex = old_answer_max_itemdex + 1
                            answer = VoteQuestionAnswer()
                            answer.setVoteCount(0)
                            answer.setItemIndex(old_answer_max_itemdex)
                            answer.setVoteQuestionId(old_id)
                            answer.setAnswerContent(new_a)
                            self.vote_svc.addVoteQuestionAnswer(answer)
                    
            
        # 对于新的问题：
        new_question_number = self.params.safeGetIntParam("question_num")
        for row in range(1, new_question_number + 1):
            new_question_title = self.params.safeGetStringParam("new_vote_q_" + str(row))
            new_question_type = self.params.safeGetIntParam("new_vote_t_" + str(row))
            new_question_max_selection = self.params.safeGetIntParam("new_vote_max_" + str(row))
            new_question_itemindex = self.params.safeGetIntParam("new_vote_order_" + str(row))
            new_question_title = self.params.safeGetStringParam("new_vote_q_" + str(row))
            new_question_answer_array = request.getParameterValues("new_vote_a_" + str(row))
            if new_question_title != "":
                # 不为空的内容才添加
                newQuestion = VoteQuestion()
                newQuestion.setTitle(new_question_title)
                newQuestion.setQuestionType(new_question_type)
                newQuestion.setMaxSelectCount(new_question_max_selection)
                newQuestion.setVoteId(self.vote.voteId)
                newQuestion.setItemIndex(new_question_itemindex)
                self.vote_svc.addVoteQuestion(newQuestion)
                
                itemIndex = 0
                for new_as in new_question_answer_array:
                    if new_as != None and new_as != "":
                        itemIndex = itemIndex + 1
                        newAnswer = VoteQuestionAnswer()
                        newAnswer.setAnswerContent(new_as)
                        newAnswer.setVoteQuestionId(newQuestion.getVoteQuestionId())
                        newAnswer.setItemIndex(itemIndex)
                        self.vote_svc.addVoteQuestionAnswer(newAnswer)
        
        response.sendRedirect("getcontent.py?guid=" + self.parentGuid + "&type=" + self.parentType + "&voteId=" + str(self.vote.voteId))
        

    def get_form(self):
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

        pagedata = self.pageFrameService.transformTemplate(map, "/WEB-INF/ftl2/special/vote_update.ftl")      
        
        page_frame = self.pageFrameService.getFramePage(self.parentGuid,self.parentType)
        page_frame = page_frame.replace("[placeholder_content]",pagedata)
        page_frame = page_frame.replace("[placeholder_title]",u"修改调查投票")        
        self.writeToResponse(page_frame)