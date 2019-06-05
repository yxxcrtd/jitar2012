from unit_page import UnitBasePage
from downlevel_unit_article_query import MultiUnitArticleQuery
from cn.edustar.jitar.pojos import Article

class unit_push_article(UnitBasePage):
    def __init__(self):
        UnitBasePage.__init__(self)
        self.article_svc = __spring__.getBean("articleService")
        
    def execute(self):
        if self.loginUser == None:
            return self.LOGIN
        
        if self.article_svc == None:
            self.addActionError(u"无法加载组织机构服务，请检查 applicationContext.xml 配置文件，是否缺少 articleService 节点。")
            return self.ERROR
        
        self.unit = self.getUnit()
        
        if self.unit == None:
            self.addActionError(u"您所访问的机构不存在！")
            return self.ERROR
        
        if self.isContentAdmin() == False:
            self.addActionError(u"你没有管理的权限。")
            return self.ERROR       
        
        if request.getMethod() == "POST":
            self.post_action()
            self.clear_cache()
            if request.getHeader("Referer") != None and request.getHeader("Referer") != "":
                response.sendRedirect(request.getHeader("Referer"))
        
        self.get_list()
                
        request.setAttribute("unit", self.unit)        
        configService = __jitar__.configService
        sys_config = configService.getConfigure()
        if sys_config != None:
            if sys_config["topsite_url"] != None and str(sys_config["topsite_url"]).strip() != "":
                request.setAttribute("topsite_url", "")
        
        return "/WEB-INF/unitsmanage/unit_push_article.ftl"
    
    def post_action(self):
        #计算 ParenUnitId       
        parentId = self.unit.parentId
        cmd = self.params.safeGetStringParam("cmd")
        if cmd == "remove":
            self.remove()                    
        elif cmd == "approveLevel":
            self.approveLevel()
        elif cmd == "unapproveLevel":
            self.unapproveLevel()
        elif cmd == "pushup":
            self.pushup()                                   
        elif cmd == "unpushup":
            self.unpushup()
        elif cmd == "setRcmd":
            self.setRcmd()
        elif cmd == "unsetRcmd":
            self.unsetRcmd()
        else:
            self.addActionError(u"无效的命令。")
            return self.ERROR
                        
    def get_list(self):
        qry = MultiUnitArticleQuery(""" 
                a.articleId, a.title, a.createDate, a.viewCount, a.commentCount, a.userId, a.pushState,
                a.unitPathInfo, a.approvedPathInfo, a.rcmdPathInfo,
                a.unitId, u.loginName, u.nickName, u.trueName """)        
 
        qry.unit = self.unit
        
        pager = self.createPager()
        pager.totalRows = qry.count() 
        article_list = qry.query_map(pager)
        request.setAttribute("article_list", article_list)
        request.setAttribute("pager", pager)        
        request.setAttribute("approveState", "")
        request.setAttribute("bestState", "")
        request.setAttribute("pushState", "")        
    
    def createPager(self):
        # private 构造文章的缺省 pager.
        pager = self.params.createPager()
        pager.itemName = u"文章"
        pager.itemUnit = u"篇"
        pager.pageSize = 20
        return pager
    
    def remove(self):
        guids = self.params.safeGetIntValues("guid")
        for g in guids:
            article = self.article_svc.getArticle(int(g))
            if article != None:
                approvedPathInfo = article.getApprovedPathInfo()
                unitPathInfo = article.getUnitPathInfo()
                rcmdPathInfo = article.getRcmdPathInfo()
                if approvedPathInfo != None and approvedPathInfo.find("/" + str(self.unit.unitId) + "/") > -1:
                    approvedPathInfo = approvedPathInfo.replace("/" + str(self.unit.unitId) + "/", "/")
                    if approvedPathInfo == "/":
                        approvedPathInfo = None
                    article.setApprovedPathInfo(approvedPathInfo)
                if unitPathInfo != None and unitPathInfo.find("/" + str(self.unit.unitId) + "/") > -1:
                    unitPathInfo = unitPathInfo.replace("/" + str(self.unit.unitId) + "/", "/")
                    if unitPathInfo == "/":
                        unitPathInfo = None
                    article.setUnitPathInfo(unitPathInfo)
                if rcmdPathInfo != None and rcmdPathInfo.find("/" + str(self.unit.unitId) + "/") > -1:
                    rcmdPathInfo = rcmdPathInfo.replace("/" + str(self.unit.unitId) + "/", "/")
                    if rcmdPathInfo == "/":
                        rcmdPathInfo = None
                    article.setRcmdPathInfo(rcmdPathInfo)  
                self.article_svc.updateArticle(article)
                
    def approveLevel(self):
        guids = self.params.safeGetIntValues("guid")
        for g in guids:
            article = self.article_svc.getArticle(int(g))
            if article != None:
                approvedPathInfo = article.getApprovedPathInfo()
                if approvedPathInfo == None:
                    article.setApprovedPathInfo("/" + str(self.unit.unitId) + "/")
                    self.article_svc.updateArticle(article)
                elif approvedPathInfo.find("/" + str(self.unit.unitId) + "/") == -1:
                    article.setApprovedPathInfo("/" + str(self.unit.unitId) + approvedPathInfo)
                    self.article_svc.updateArticle(article)
                    
    def unapproveLevel(self):
        guids = self.params.safeGetIntValues("guid")
        for g in guids:
            article = self.article_svc.getArticle(int(g))
            if article != None:
                approvedPathInfo = article.getApprovedPathInfo()
                if approvedPathInfo != None and approvedPathInfo.find("/" + str(self.unit.unitId) + "/") > -1:
                    if approvedPathInfo == "/" + str(self.unit.unitId) + "/":
                        article.setApprovedPathInfo(None)
                    else:
                        approvedPathInfo = approvedPathInfo.replace("/" + str(self.unit.unitId) + "/", "/")
                        if approvedPathInfo == "/":
                            approvedPathInfo = None
                        article.setApprovedPathInfo(approvedPathInfo)
                    self.article_svc.updateArticle(article)
    def pushup(self):
        guids = self.params.safeGetIntValues("guid")
        for g in guids:
            article = self.article_svc.getArticle(int(g))
            if article != None:
                unitPathInfo = article.getUnitPathInfo()
                if unitPathInfo.find("/" + str(self.unit.parentId) + "/") == -1:
                    unitPathInfo = "/" + str(self.unit.parentId) + unitPathInfo
                    article.setUnitPathInfo(unitPathInfo)
                    self.article_svc.updateArticle(article)
    
    def unpushup(self):
        guids = self.params.safeGetIntValues("guid")
        for g in guids:
            article = self.article_svc.getArticle(int(g))
            if article != None:
                unitPathInfo = article.getUnitPathInfo()
                if unitPathInfo != None and unitPathInfo.find("/" + str(self.unit.parentId) + "/") > -1:
                    unitPathInfo = unitPathInfo.replace("/" + str(self.unit.parentId) + "/", "/")
                    if unitPathInfo == "/":
                        unitPathInfo = None
                    article.setUnitPathInfo(unitPathInfo)
                    self.article_svc.updateArticle(article)
    
    def setRcmd(self):
        guids = self.params.safeGetIntValues("guid")
        for g in guids:
            article = self.article_svc.getArticle(int(g))
            if article != None:                
                rcmdPathInfo = article.getRcmdPathInfo()
                if rcmdPathInfo == None:
                    rcmdPathInfo = "/" + str(self.unit.unitId) + "/"
                    article.setRcmdPathInfo(rcmdPathInfo)
                    self.article_svc.updateArticle(article)
                else:
                    if rcmdPathInfo.find("/" + str(self.unit.unitId) + "/") == -1:
                        rcmdPathInfo = "/" + str(self.unit.unitId) + "/" + rcmdPathInfo
                        article.setRcmdPathInfo(rcmdPathInfo)
                        self.article_svc.updateArticle(article)
                self.article_svc.rcmdArticle(article)
            
    def unsetRcmd(self):
        guids = self.params.safeGetIntValues("guid")
        for g in guids:
            article = self.article_svc.getArticle(int(g))
            if article != None:                
                rcmdPathInfo = article.getRcmdPathInfo()
                if rcmdPathInfo != None and rcmdPathInfo.find("/" + str(self.unit.unitId) + "/") > -1:
                    rcmdPathInfo = rcmdPathInfo.replace("/" + str(self.unit.unitId) + "/", "/")
                    if rcmdPathInfo == "/":
                        rcmdPathInfo = None
                    article.setRcmdPathInfo(rcmdPathInfo)
                    self.article_svc.updateArticle(article)
                if article.getRcmdPathInfo() == None:
                    self.article_svc.unrcmdArticle(article)
