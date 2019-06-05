from base_action import *

class admin_history_article_subject(ActionExecutor):        
    def execute(self):
        if self.loginUser == None:
            return ActionResult.LOGIN
        
        webSiteManageService = __spring__.getBean("webSiteManageService")
        backYearList = webSiteManageService.getBackYearList("article")
        if len(backYearList) < 1:
            self.addActionMessage(u"没有历史记录可以统计。")
            return ActionResult.SUCCESS
        subjectService = __jitar__.subjectService
        subjectList = subjectService.getSubjectList()
        for sub in subjectList:
            articleCount = 0
            for bYear in backYearList:
                articleCount = articleCount + webSiteManageService.getSubejctYearArticleCount(bYear.backYear, sub.metaSubject.msubjId, sub.metaGrade.gradeId)
            subject = subjectService.getSubjectById(sub.subjectId)
            subject.setHistoryArticleCount(articleCount)
            subjectService.saveOrUpdateSubject(subject)
        self.addActionMessage(u"所有学科历史文章统计完毕。")
        return ActionResult.SUCCESS
        
