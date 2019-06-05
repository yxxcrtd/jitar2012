#encoding=utf-8
from common_data import CommonData
from question_query import QuestionQuery

class admin_list(CommonData):
    def execute(self):
        self.pluginAuthorityCheckService = __spring__.getBean("pluginAuthorityCheckService")
        if self.pluginAuthorityCheckService == None:
            self.addActionError(u"无法加载插件权限验证服务，请检查 applicationContext.xml 文件的配置。")
            return self.ERROR
        
        if self.loginUser == None:
            self.addActionError(u"请先登录。")
            return self.LOGIN
        
        #ret = self.pluginAuthorityCheckService.canManagePluginInstance(self.commonObject, self.loginUser)
        #print "ret = ",ret
        
        accessControlService = __spring__.getBean("accessControlService")
        if accessControlService.isSystemAdmin(self.loginUser) == False:
            self.addActionError(u"需要超级管理员才能进行管理。")
            return self.ERROR
        
        # 列出当前用户的所有问答实例
        q_svc = __spring__.getBean("questionAnswerService")        
               
        if request.getMethod() == "POST":            
            guid = self.params.safeGetIntValues("guid")
            for g in guid:
                q_svc.deleteQuestionByQuestionId(g)        
            
        qry = QuestionQuery(""" q.questionId, q.parentGuid, q.parentObjectType, q.topic, q.createDate, q.createUserId, q.createUserName, q.questionContent, q.addIp """)
        pager = self.params.createPager()
        pager.itemName = u"问题"
        pager.itemUnit = u"个"
        pager.pageSize = 20
        pager.totalRows = qry.count()
        q_list = qry.query_map(pager)
        request.setAttribute("q_list", q_list)
        request.setAttribute("pager", pager)
        
        return "/WEB-INF/mod/questionanswer/admin_list.ftl"  