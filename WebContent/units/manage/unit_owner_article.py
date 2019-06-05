from unit_page import UnitBasePage
from article_query import ArticleQuery
from cn.edustar.jitar.pojos import Article

class unit_owner_article(UnitBasePage):
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
        
        return "/WEB-INF/unitsmanage/unit_owner_article.ftl"
    
    def post_action(self):
        #计算 ParenUnitId       
        pid = self.unit.unitId
        cmd = self.params.safeGetStringParam("cmd")
        if cmd == "rcmdPathInfo":
            self.rcmdPathInfo()
        elif cmd == "unrcmdPathInfo":
            self.unrcmdPathInfo()
        elif cmd == "approve":
            self.approve()
        elif cmd == "unapprove":
            self.unapprove()       
        elif cmd == "delete":
            self.delete()
        elif cmd == "push_up":
            self.push_up()
        elif cmd == "un_push_up":
            self.un_push_up()
        elif cmd == "push":
            self.push()
        elif cmd == "unpush":
            self.unpush()
        else:
            self.addActionError(u"无效的命令。")
            return self.ERROR
            
    def get_list(self):
        qry = ArticleQuery(""" a.articleId, a.title, a.createDate, a.viewCount, a.commentCount, a.userId, a.pushState,
                        a.unitPathInfo,a.approvedPathInfo, a.rcmdPathInfo,
                        a.digg, a.trample, a.starCount, a.recommendState, a.typeState, a.auditState, a.delState, u.loginName, u.nickName, u.trueName """)
        
        qry.unit = self.unit
        qry.orderType = 0
        qry.unitId = None
        qry.auditState = None
        qry.delState = None
        qry.rcmdState = None
        
        rcmdState = self.params.safeGetStringParam("rcmdState")            
        auditState = self.params.safeGetStringParam("auditState")
        delState = self.params.safeGetStringParam("delState")
        pushupState = self.params.safeGetStringParam("pushupState")
        custormAndWhereClause = "a.unitId = " + str(self.unit.unitId) + " And"
        
        if rcmdState == "1":
            custormAndWhereClause += " a.rcmdPathInfo LIKE '%" + str(self.unit.unitId) + "%' And"
        elif rcmdState == "0":
            custormAndWhereClause += " (a.rcmdPathInfo Is Null or a.rcmdPathInfo Not LIKE '%" + str(self.unit.unitId) + "%') And"
        
        if auditState == "0":
            custormAndWhereClause += " a.approvedPathInfo LIKE '%" + str(self.unit.unitId) + "%' And"
        elif auditState == "1":
            custormAndWhereClause += " (a.approvedPathInfo Is Null or a.approvedPathInfo NOT LIKE '%" + str(self.unit.unitId) + "%') And"
        
        if pushupState == "1":
            custormAndWhereClause += " a.unitPathInfo LIKE '%" + str(self.unit.parentId) + "%' And"
        elif pushupState == "0":
            custormAndWhereClause += " a.unitPathInfo Not LIKE '%" + str(self.unit.parentId) + "%' And"
        
        if delState == "0":
            qry.delState = False
        elif delState == "1":
            qry.delState = True
        
        
        if custormAndWhereClause[len(custormAndWhereClause) - 3:] == "And":
            custormAndWhereClause = custormAndWhereClause[0:len(custormAndWhereClause) - 3]
        
        #print "custormAndWhereClause = ", custormAndWhereClause
        qry.custormAndWhereClause = custormAndWhereClause
        pager = self.createPager()
        pager.totalRows = qry.count() 
        article_list = qry.query_map(pager)
        request.setAttribute("article_list", article_list)
        request.setAttribute("pager", pager)        
        request.setAttribute("rcmdState", rcmdState)   
        request.setAttribute("auditState", auditState)
        request.setAttribute("delState", delState)
        request.setAttribute("pushupState", pushupState)
    
    def createPager(self):
        # private 构造文章的缺省 pager.
        pager = self.params.createPager()
        pager.itemName = u"文章"
        pager.itemUnit = u"篇"
        pager.pageSize = 20
        return pager
    
    
    # 推荐文章，是推荐到本机构，而不是上级机构
    def rcmdPathInfo(self):
        guids = self.params.safeGetIntValues("guid")
        for g in guids:
            article = self.article_svc.getArticle(int(g))
            if article != None:
                rcmdPathInfo = article.getRcmdPathInfo()
                if rcmdPathInfo == None:
                    article.setRcmdPathInfo("/" + str(self.unit.unitId) + "/")
                    self.article_svc.updateArticle(article)
                elif rcmdPathInfo.find("/" + str(self.unit.unitId) + "/") == -1:
                    rcmdPathInfo = "/" + str(self.unit.unitId) + rcmdPathInfo
                    article.setRcmdPathInfo(rcmdPathInfo)
                    self.article_svc.updateArticle(article)
                # 保留原先的字段
                self.article_svc.rcmdArticle(article)
    
    # 取消推荐文章，是取消推荐文章到本机构，
    def unrcmdPathInfo(self):
        guids = self.params.safeGetIntValues("guid")
        for g in guids:
            article = self.article_svc.getArticle(int(g))
            if article != None:                
                rcmdPathInfo = article.getRcmdPathInfo()
                if rcmdPathInfo != None and rcmdPathInfo.find("/" + str(self.unit.unitId) + "/") != -1:                    
                    rcmdPathInfo = rcmdPathInfo.replace("/" + str(self.unit.unitId) + "/", "/")
                    if rcmdPathInfo == "/":
                        rcmdPathInfo = None
                    article.setRcmdPathInfo(rcmdPathInfo)
                    self.article_svc.updateArticle(article)
                if article.getRcmdPathInfo() == None:
                    # 保留原先的字段
                    self.article_svc.unrcmdArticle(article)
    
    # 审核文章 
    def approve(self):
        guids = self.params.safeGetIntValues("guid")
        for g in guids:
            article = self.article_svc.getArticle(int(g))
            if article != None: 
                # 保留之前的做法                                  
                article.setAuditState(0)
                approvedPathInfo = article.getApprovedPathInfo()
                if approvedPathInfo == None:
                    approvedPathInfo = "/" + str(self.unit.unitId) + "/"
                    article.setApprovedPathInfo(approvedPathInfo)
                    self.article_svc.updateArticle(article)
                elif approvedPathInfo.find("/" + str(self.unit.unitId) + "/") == -1:
                    approvedPathInfo = "/" + str(self.unit.unitId) + approvedPathInfo
                    article.setApprovedPathInfo(approvedPathInfo)
                    self.article_svc.updateArticle(article)
    
    #取消审核
    def unapprove(self):
        guids = self.params.safeGetIntValues("guid")
        for g in guids:
            article = self.article_svc.getArticle(int(g))
            if article != None:                                   
                article.setAuditState(1)
                approvedPathInfo = article.getApprovedPathInfo()
                if approvedPathInfo != None and approvedPathInfo.find("/" + str(self.unit.unitId) + "/") != -1:
                    approvedPathInfo = approvedPathInfo.replace("/" + str(self.unit.unitId) + "/", "/")
                    if approvedPathInfo == "/":
                        approvedPathInfo = None
                    article.setApprovedPathInfo(approvedPathInfo)
                    self.article_svc.updateArticle(article)
    def delete(self):
        guids = self.params.safeGetIntValues("guid")
        for g in guids:
            article = self.article_svc.getArticle(int(g))
            if article != None:                                   
                self.article_svc.deleteArticle(article)
                
    def push_up(self):
        guids = self.params.safeGetIntValues("guid")
        for g in guids:
            article = self.article_svc.getArticle(int(g))
            if article != None:
                #判断是否已经推送给上级
                if article.unitPathInfo.find("/" + str(self.unit.parentId) + "/") == -1:
                    #未推送，则执行推送
                    article.setUnitPathInfo("/" + str(self.unit.parentId) + article.unitPathInfo)
                    self.article_svc.updateArticle(article)
    def un_push_up(self):
        guids = self.params.safeGetIntValues("guid")
        for g in guids:
            article = self.article_svc.getArticle(int(g))
            if article != None:
                #判断是否已经推送给上级
                if article.unitPathInfo != str(self.unit.unitId) + "/":
                    #已经推送，则执行取消推送
                    article.setUnitPathInfo("/" + str(self.unit.unitId) + "/")
                    self.article_svc.updateArticle(article)
    def push(self):
        guids = self.params.safeGetIntValues("guid")
        for g in guids:
            article = self.article_svc.getArticle(int(g))
            if article != None:
                if article.pushState == 0 and article.auditState == 0:
                    article.setPushState(2)
                    article.setPushUserId(self.loginUser.userId)
                    self.article_svc.updateArticle(article)
    def unpush(self):
        guids = self.params.safeGetIntValues("guid")
        for g in guids:
            article = self.article_svc.getArticle(int(g))
            if article != None:
                if article.pushState == 2: #待推送的才可以取消推送
                    article.setPushState(0)
                    article.setPushUserId(None)
                    self.article_svc.updateArticle(article)