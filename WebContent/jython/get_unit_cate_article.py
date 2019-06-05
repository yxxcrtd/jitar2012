from cn.edustar.jitar.util import ParamUtil
from article_query import ArticleQuery

class get_unit_cate_article:
    
    def execute(self):
        self.params = ParamUtil(request)
        showCount = self.params.safeGetIntParam("count")
        unitId = self.params.safeGetIntParam("unitId")
        sysCateId = self.params.safeGetIntParam("cateId")        
        if showCount == 0:
            showCount = 6
        if sysCateId == 0 or unitId == 0:
            response.getWriter().write(u"无效的参数。")
            return
        unit = __spring__.getBean("unitService").getUnitById(unitId)
        if unit == None:
            response.getWriter().write(u"不能加载机构信息。")
            return
        qry = ArticleQuery(""" a.articleId, a.title, a.createDate, a.viewCount, a.commentCount, a.userId,
                              a.recommendState, a.typeState, u.loginName, u.nickName """)
        qry.sysCateId = sysCateId
        qry.custormAndWhereClause = "a.approvedPathInfo LIKE '%/" + str(unitId) + "/%'"
        article_list = qry.query_map(showCount)
        request.setAttribute("article_list", article_list)
        unitTemplateName = "template1"
        if unit.templateName != None:
            unitTemplateName = unit.templateName
        return "/WEB-INF/unitspage/" + unitTemplateName + "/get_unit_cate_article.ftl"
