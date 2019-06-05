#encoding=utf-8
from common_data import CommonData
from question_query import QuestionQuery
from java.util import HashMap

class manage_list(CommonData):
    def __init__(self):        
        CommonData.__init__(self)  # some share variable can get
        self.questionAnswerService = __spring__.getBean("questionAnswerService")
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
            self.questionAnswerService.deleteQuestionByQuestionId(g)
        refurl = request.getHeader("Referer")
        if refurl == None or refurl == "":
            refurl = self.pageFrameService.getSiteUrl() + "mod/questionanswer/question_manage_list.action?guid=" + self.parentGuid + "&type=" + self.parentType
        response.sendRedirect(refurl)

    def get_form(self):
        show_type = self.params.safeGetStringParam("show")
        qry = QuestionQuery(""" q.questionId, q.parentGuid, q.parentObjectType, q.topic, q.createDate, q.createUserId, q.createUserName, q.questionContent, q.addIp """)
        qry.parentGuid = self.parentGuid
        pager = self.params.createPager()
        pager.itemName = u"投票"
        pager.itemUnit = u"个"
        pager.pageSize = 20
        pager.totalRows = qry.count()
        q_list = qry.query_map(pager)
        if show_type == "":
            map = HashMap()
            map.put("SiteUrl",self.pageFrameService.getSiteUrl())
            map.put("UserMgrUrl",self.pageFrameService.getUserMgrUrl())
            map.put("q_list",q_list)
            map.put("pager",pager)
            map.put("loginUser",self.loginUser)
            map.put("parentGuid",self.parentGuid)
            map.put("parentType",self.parentType)
    
            pagedata = self.pageFrameService.transformTemplate(map, "/WEB-INF/mod/questionanswer/manage_list.ftl")      
            
            page_frame = self.pageFrameService.getFramePage(self.parentGuid,self.parentType)
            page_frame = page_frame.replace("[placeholder_content]",pagedata)
            page_frame = page_frame.replace("[placeholder_title]",u"问题管理")        
            self.writeToResponse(page_frame)
            return
        else:
            request.setAttribute("q_list",q_list)
            request.setAttribute("pager",pager)
            return "/WEB-INF/mod/questionanswer/manage_list_nohead.ftl"