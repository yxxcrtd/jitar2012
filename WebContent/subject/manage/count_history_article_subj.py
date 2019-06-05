from subject_page import *

class count_history_article_subj(BaseSubject):
    def __init__(self):
        BaseSubject.__init__(self)
        
    def execute(self):
        if self.loginUser == None:
            return self.LOGIN
        
        if self.isAdmin() == False:
            self.addActionError(u"您你没有管理的权限！")
            return self.ERROR

        webSiteManageService = __spring__.getBean("webSiteManageService")
        backYearList = webSiteManageService.getBackYearList("article")
        articleCount = 0
        for bYear in backYearList:
            articleCount = articleCount + webSiteManageService.getSubejctYearArticleCount(bYear.backYear,self.subject.metaSubject.msubjId,self.subject.metaGrade.gradeId)
        self.subject.setHistoryArticleCount(articleCount)
        __jitar__.subjectService.saveOrUpdateSubject(self.subject)
        self.addActionMessage(u"本学科历史文章数：" + str(articleCount))
        return self.SUCCESS