from cn.edustar.jitar.util import ParamUtil
from article_query import ArticleQuery
from cn.edustar.jitar.util import FileCache
from cn.edustar.jitar.model import SiteUrlModel

class category_article:
    def execute(self):
        params = ParamUtil(request)
        self.userName = params.safeGetStringParam("loginName")
        title = params.safeGetStringParam("title")
        
        userCateId = params.safeGetIntParam("userCateId")
        if userCateId == 0:
            response.getWriter().write(u"<div style='text-align:center;'>请选择一个个人文章分类。</div>")
            return
        
        fc = FileCache()
        content = fc.getUserFileCacheContent(self.userName, "category_article_" + str(userCateId) + ".html",30)
        if content != "":
            response.getWriter().write(content)
            fc = None
            return
        
        count = params.safeGetIntParam("count")
        if count == 0 : count = 10
        user = __jitar__.userService.getUserByLoginName(self.userName)
        if user == None:
            response.getWriter().write(u"不能加载当前用户。")
            return
        qry = ArticleQuery(""" a.articleId, a.title, a.createDate, a.typeState """)
        qry.userId = user.userId
        article_list = qry.query_map(count)
        templateProcessor = __spring__.getBean("templateProcessor")
        map = HashMap()
        map.put("article_list", article_list)
        map.put("UserSiteUrl", self.getUserSiteUrl())
        map.put("userCateId", userCateId)
        map.put("title", title)      
        content = templateProcessor.processTemplate(map, "/WEB-INF/user/default/category_article.ftl", "utf-8")
        
        fc.writeUserFileCacheContent(self.userName, "category_article_" + str(userCateId) + ".html",content)        
        response.getWriter().write(content)
        fc = None
    
    def getUserSiteUrl(self):
        siteUrl = SiteUrlModel.getSiteUrl()
        userSiteUrl = request.getSession().getServletContext().getInitParameter("userUrlPattern")
        if userSiteUrl == None or userSiteUrl == "":
            userSiteUrl = siteUrl + "u/" + self.userName + "/"
        else:
            userSiteUrl = userSiteUrl.replace("{loginName}", self.userName)            
        return userSiteUrl