from cn.edustar.jitar.util import ParamUtil
from contentspacearticle_query import ContentSpaceArticleQuery
from unit_page import *

class get_unit_contentspace_article(UnitBasePage):
    def __init__(self):
        UnitBasePage.__init__(self)
        
    def execute(self):
        self.params = ParamUtil(request)
        showCount = self.params.safeGetIntParam("count")
        contentSpaceId = self.params.safeGetIntParam("contentSpaceId")
        showType = self.params.safeGetIntParam("showType")
        unitId = self.params.safeGetIntParam("unitId")
        if showCount == 0:
            showCount = 8
        if showType == 0:
            showType = 1
        if contentSpaceId == 0 or unitId == 0:
            response.getWriter().println(u"标识不足。")
            return
        
        self.unit = self.getUnit()
        if self.unit == None:
            response.getWriter().println(u"不能加载机构信息。")
            return
        
        unitTemplateName = "template1"
        if self.unit.templateName != None and self.unit.templateName != "":
            unitTemplateName = self.unit.templateName
        qry = ContentSpaceArticleQuery(""" csa.title,csa.contentSpaceArticleId,csa.createDate,csa.pictureUrl,csa.viewCount,cs.contentSpaceId """)
        qry.contentSpaceId = contentSpaceId
        article_list = qry.query_map(showCount)
        request.setAttribute("article_list", article_list)
        request.setAttribute("showType", showType) 
        request.setAttribute("unit", self.unit)          
        return "/WEB-INF/unitspage/" + unitTemplateName + "/get_unit_contentspace_article.ftl"
