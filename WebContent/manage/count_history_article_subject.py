from cn.edustar.jitar.jython import BaseAdminAction
from cn.edustar.jitar.util import ParamUtil
from cn.edustar.jitar.data import Command
from base_action import ActionResult

class count_history_article_subject(BaseAdminAction, ActionResult):
    def execute(self):
        if self.loginUser == None:
            return ActionResult.LOGIN
        
        accessControlService = __spring__.getBean("accessControlService")
        if accessControlService.isSystemAdmin(self.loginUser) == False:
            self.addActionError(u"没有管理站点配置的权限，只有超级管理员才能进行管理。")
            return ActionResult.ERROR
        
        params = ParamUtil(request)
        webSiteManageService = __spring__.getBean("webSiteManageService")
        if params.existParam("subjectId") and params.existParam("gradeId"):            
            subjectId = params.safeGetIntParam("subjectId")
            gradeId = params.safeGetIntParam("gradeId")
            subject = __jitar__.subjectService.getSubjectByMetaData(subjectId, gradeId)
            if subject == None:
                self.addActionError(u"不能加载学科对象。")
                return ActionResult.ERROR
            backYearList = webSiteManageService.getBackYearList("article")
            articleCount = 0
            for bYear in backYearList:
                articleCount = articleCount + webSiteManageService.getSubejctYearArticleCount(bYear.backYear,subjectId,gradeId)
            subject.setHistoryArticleCount(articleCount)
            __jitar__.subjectService.saveOrUpdateSubject(subject)
            self.addActionLink(subject.subjectName + u" 更新完毕，点击返回","?","_self")
            return ActionResult.SUCCESS
        else:
            #得到学科列表
            subject_list = __jitar__.subjectService.getSubjectList()
            request.setAttribute("subject_list",subject_list)
            return "/WEB-INF/ftl/admin/count_history_article_subject.ftl"