from cn.edustar.jitar.util import ParamUtil
from article_query import ArticleQuery

class get_cate_article:
    def execute(self):
        self.params = ParamUtil(request)
        showCount = self.params.safeGetIntParam("count")
        unitId = self.params.safeGetIntParam("unitId")
        textLength = self.params.safeGetIntParam("textLength")
        request.setAttribute("textLength", textLength)
        if unitId == None:
            unitId == 0
        if showCount == 0:
            showCount = 8
        sysCateId = self.params.safeGetIntParam("id")
        qry = ArticleQuery(""" a.articleId, a.title, a.createDate, a.viewCount, a.commentCount, a.userId,
                              a.recommendState, a.typeState, u.loginName, u.nickName """)
        qry.sysCateId = sysCateId
        qry.custormAndWhereClause = "a.approvedPathInfo LIKE '%/" + str(unitId) + "/%'"
        if self.params.existParam("subject") == True:
            subjectId = self.params.safeGetIntParam("subject")
            subject = __spring__.getBean("subjectService").getSubjectById(subjectId)
            if subject != None:
                qry.subjectId = subject.metaSubject.msubjId
                qry.gradeId = subject.metaGrade.gradeId
                qry.FuzzyMatch = True
                article_list = qry.query_map(showCount)
                request.setAttribute("subject", subject)
                request.setAttribute("article_list", article_list)
                subjectTemplateName = "template1"
                if subject.templateName != None:
                    subjectTemplateName = subject.templateName
                return "/WEB-INF/subjectpage/" + subjectTemplateName + "/get_cate_article.ftl"
            else:
                response.getWriter().println(u"不能加载学科对象。")
        else:
            article_list = qry.query_map(showCount)
            request.setAttribute("article_list", article_list)
            return "/WEB-INF/ftl/mengv1/index/get_cate_article.ftl"
