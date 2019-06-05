from cn.edustar.jitar.util import ParamUtil
from contentspacearticle_query import ContentSpaceArticleQuery

class get_index_contentspace_article:
    def execute(self):
        self.params = ParamUtil(request)
        showCount = self.params.safeGetIntParam("count")
        contentSpaceId = self.params.safeGetIntParam("contentSpaceId")
        textLength = self.params.safeGetIntParam("textLength")
        showType = self.params.safeGetIntParam("showType")
        if textLength == 0 :
            textLength = 12
        if showCount == 0:
            showCount = 8
        if showType == 0:
            showType = 1
            
        qry = ContentSpaceArticleQuery(" csa.title,csa.contentSpaceArticleId,csa.createDate,csa.pictureUrl,csa.viewCount,cs.contentSpaceId ")
        qry.contentSpaceId = contentSpaceId
        if showType == 1 or showType == 3:
            article_list = qry.query_map(showCount)
            request.setAttribute("article_list", article_list)
            #return "/WEB-INF/subjectpage/" + subjectTemplateName + "/get_contentspace_article.ftl"
            #print "article_list=",article_list
        if showType == 3: 
            qry.hasPicture = True
            picture_article_list = qry.query_map(4)
            request.setAttribute("picture_article_list", picture_article_list)
            
        if showType == 2:
            qry.hasPicture = True
            picture_article_list = qry.query_map(showCount)
            request.setAttribute("picture_article_list", picture_article_list)
        
        request.setAttribute("textLength", textLength)
        request.setAttribute("showType", showType)
        return "/WEB-INF/ftl2/index/get_contentspace_article.ftl"