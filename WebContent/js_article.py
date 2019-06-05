from cn.edustar.jitar.util import ParamUtil
from article_query import ArticleQuery
from article_query import GroupArticleQuery

class js_article:
    def execute(self):
        # 完整的调用参数：
        # js_article.py?top=4&count=4&type=0&cateid=13&author=1&date=1&groupId=42
        
        # 默认支持 subjectId，gradeId，参数带这些值，默认是支持的
        # 更多参数请参见 article_query的成员定义。
        
        
        self.params = ParamUtil(request)
        ShowCount = self.params.getIntParam("count")
        ShowTop = self.params.getIntParam("top")
        ShowType = self.params.getIntParam("type")
        ShowAuthor = self.params.getIntParamZeroAsNull("author")
        ShowDate = self.params.getIntParamZeroAsNull("date")
        
        unitId = self.params.getIntParamZeroAsNull("unitid")
        cateid = self.params.getIntParamZeroAsNull("cateid")
        groupId = self.params.getIntParamZeroAsNull("groupId")
        unitService = __spring__.getBean("unitService")
        if unitId == None:
            rootUnit = unitService.getRootUnit()
        else:
            rootUnit = unitService.getUnitById(unitId)
        
        if rootUnit == None:
            response.getWriter().println(u"document.write('没有根单位。')")
            return
        
        if ShowTop == None or ShowTop == 0:
            ShowTop = 10
        if ShowCount == None or ShowCount == 0:
            ShowCount = 10
        if ShowType == None:
            ShowType = 0        

        if groupId != None and groupId != 0:
            qry = GroupArticleQuery(""" ga.articleId, ga.title, ga.createDate, ga.userId, ga.loginName, ga.userTrueName as trueName  """)
            qry.groupId = groupId
        else:
            qry = __PrivateArticleQuery(""" a.articleId, a.title, a.createDate, u.userId, u.loginName, u.trueName """)
            qry.unitId = unitId
            qry.sysCateId = cateid

            if ShowType == 1:
                #推荐文章
                #qry.rcmdState = True
                qry.custormAndWhereClause = "a.rcmdPathInfo Like '%/" + str(rootUnit.unitId) + "/%' and a.approvedPathInfo Like '%/" + str(rootUnit.unitId) + "/%'"
                
            elif ShowType == 2:
                #名师文章
                qry.userIsFamous = 1
                qry.custormAndWhereClause = "a.approvedPathInfo Like '%/" + str(rootUnit.unitId) + "/%'"
            elif ShowType == 3:
                #精华文章
                qry.bestState = True
                qry.custormAndWhereClause = "a.approvedPathInfo Like '%/" + str(rootUnit.unitId) + "/%'"
                
        article_list = qry.query_map(ShowTop)
        request.setAttribute("article_list", article_list)            
        request.setAttribute("ShowAuthor", ShowAuthor)
        request.setAttribute("ShowDate", ShowDate)
        request.setAttribute("ShowCount", ShowCount)
        response.contentType = "text/html; charset=utf-8"
        return "/WEB-INF/ftl/js_article.ftl"
        
class __PrivateArticleQuery (ArticleQuery):
    def __init__(self, fields):
        ArticleQuery.__init__(self, fields)
        self.userIsFamous = None
  
    def applyWhereCondition(self, qctx):
        ArticleQuery.applyWhereCondition(self, qctx)
        if self.userIsFamous != None:
            qctx.addAndWhere("u.userType LIKE '%/1/%'")
