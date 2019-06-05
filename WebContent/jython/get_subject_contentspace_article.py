from cn.edustar.jitar.util import ParamUtil
from contentspacearticle_query import ContentSpaceArticleQuery
from subject_page import *

class get_subject_contentspace_article(BaseSubject):
    def __init__(self):
        BaseSubject.__init__(self)
        
    def execute(self):
        self.params = ParamUtil(request)
        showCount = self.params.safeGetIntParam("count")
        contentSpaceId = self.params.safeGetIntParam("contentSpaceId")
        showType = self.params.safeGetIntParam("showType")   
        if showCount == 0:
            showCount = 8
        if showType == 0:
            showType = 1
        if self.params.existParam("subject") == True:
            subjectTemplateName = "template1"
            if self.subject.templateName != None:
                subjectTemplateName = self.subject.templateName
            qry = ContentSpaceArticleQuery(""" csa.title,csa.contentSpaceArticleId,csa.createDate,csa.pictureUrl,csa.viewCount,cs.contentSpaceId """)
            qry.contentSpaceId = contentSpaceId
            article_list = qry.query_map(showCount)
            request.setAttribute("article_list", article_list)
            request.setAttribute("showType", showType)           
            return "/WEB-INF/subjectpage/" + subjectTemplateName + "/get_contentspace_article.ftl"
        else:
            response.getWriter().println(u"无效的请求。")
