from unit_page import *
from article_query import ArticleQuery
from contentspacearticle_query import ContentSpaceArticleQuery
from org.python.core import codecs

class show_unit_custorm_article(UnitBasePage):
    def __init__(self):
        UnitBasePage.__init__(self)

    def execute(self):
        self.params = ParamUtil(request)
        contentSpaceId = self.params.safeGetIntParam("contentSpaceId")
        
        self.unit = self.getUnit()
        if self.unit == None:
            self.addActionError(u"不能加载机构信息。")
            return self.ERROR        
        
        if self.unit.delState == True:
            self.addActionError(u"您所访问的机构已经被删除！")
            return self.ERROR
        
        partType = self.params.safeGetIntParam("partType")
        
        self.templateName = "template1"
        if self.unit.templateName != None and self.unit.templateName != "":
            self.templateName = self.unit.templateName
            
        if partType == 2:
            qry = ContentSpaceArticleQuery(""" csa.title,csa.contentSpaceArticleId,csa.createDate,csa.pictureUrl,csa.viewCount,cs.contentSpaceId  """)
            qry.contentSpaceId = self.params.safeGetIntParam("categoryId")
        else:   
            qry = ArticleQuery(""" a.articleId, a.title, a.createDate, a.viewCount, a.commentCount, a.userId,
                                  a.recommendState, a.typeState, u.loginName, u.nickName, u.trueName """)                
            qry.custormAndWhereClause = "a.approvedPathInfo LIKE '%/" + str(self.unit.unitId) + "/%'"
            qry.sysCateId = self.params.getIntParamZeroAsNull("categoryId")
        
        strError = ""
        pager = self.createPager()
        pager.totalRows = qry.count()
        article_list = qry.query_map(pager)
        request.setAttribute("article_list", article_list)
        request.setAttribute("pager", pager)
        request.setAttribute("partType", partType)
        request.setAttribute("unit", self.unit)
        pt = self.params.safeGetStringParam("title")
        request.setAttribute("Page_Title", pt)
        
        return "/WEB-INF/unitspage/" + self.templateName + "/show_unit_custorm_article.ftl"
    def createPager(self):
        pager = self.params.createPager()
        pager.pageSize = 30
        pager.itemName = u"文章"
        pager.itemUnit = u"篇"
        return pager
