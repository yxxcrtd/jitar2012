#encoding=utf-8
from subject_page import *
from article_query import ArticleQuery
from article_query import GroupArticleQuery
from cn.edustar.jitar.pojos import Article
from cn.edustar.jitar.pojos import Message
from cn.edustar.jitar.model import ObjectType

class article(BaseSubject):
    def __init__(self):
        BaseSubject.__init__(self)
        self.articleService = __spring__.getBean("articleService")
        self.groupArticleService = __spring__.getBean("groupService")
        self.categoryService = __spring__.getBean("categoryService")
        self.pun_svc = __jitar__.UPunishScoreService
        self.msg_svc = __spring__.getBean("messageService")
        
    def execute(self):
        if self.loginUser == None:
            return self.LOGIN
        
        if self.isAdmin == False and self.isContentAdmin() == False:
            self.addActionError(u"您你没有管理的权限！")
            return self.ERROR
        
        if request.getMethod() == "POST":
            self.save_post()
            self.clear_subject_cache()
            response.sendRedirect(request.getHeader("Referer"))
        
        configService = __jitar__.configService
        sys_config = configService.getConfigure()
        if sys_config != None:
            if sys_config["topsite_url"] != None and str(sys_config["topsite_url"]).strip() != "":
                request.setAttribute("topsite_url", "")
                
        return self.article_list()
    
    def save_post(self):        
        cmd = self.params.safeGetStringParam("cmd")

        guids = self.params.safeGetIntValues("guid")
        if cmd == "remove":
            for guid in guids:
                art = self.articleService.getArticle(guid)
                if art != None:
                    art.subjectId = None
                    self.articleService.updateArticle(art)
        elif cmd == "push":
            for guid in guids:
                art = self.articleService.getArticle(guid)
                if art != None:
                    # print art.auditState
                    if art.pushState == 0 and art.auditState == Article.AUDIT_STATE_OK:
                        art.setPushState(2)
                        art.setPushUserId(self.loginUser.userId)
                        self.articleService.updateArticle(art)
                    
        elif cmd == "unpush":
            for guid in guids:
                art = self.articleService.getArticle(guid)
                if art != None:
                    if art.pushState == 2:
                        art.setPushState(0)
                        art.setPushUserId(None)
                        self.articleService.updateArticle(art)
                    
        elif cmd == "real_delete":
            for guid in guids:
                art = self.articleService.getArticle(guid)
                if art != None:   
                    #self.loginUser.userId==art.userId
                    #删除文章的罚分机制
                    #upun =self.pun_svc.createUPunishScore(3,art.id,art.userId)
                    #self.pun_svc.saveUPunishScore(upun)
                    #正式删除文章                 
                    self.articleService.crashArticle(art) 
        elif cmd == "addscore":
            #加分
            score = self.params.safeGetIntParam("add_score")
            if score>0:
                #注意：加分是正的，保存是负的
                score = score*-1   
            #print "score="+str(score)
            score_reason = self.params.safeGetStringParam("add_score_reason")
            for guid in guids:
                art = self.articleService.getArticle(guid)
                if art != None:   
                    upun =self.pun_svc.createUPunishScore(ObjectType.OBJECT_TYPE_ARTICLE.getTypeId(),art.id,art.userId,float(score),score_reason,self.loginUser.userId,self.loginUser.trueName)
                    self.pun_svc.saveUPunishScore(upun)
        elif cmd == "minusscore":
            #罚分 并删除删除文章，发消息
            score = self.params.safeGetIntParam("minus_score")
            #print "score="+str(score)
            if score<0:
                #注意：罚分是负的，保存是正的
                score = score*-1   
            score_reason = self.params.safeGetStringParam("minus_score_reason")
            for guid in guids:
                art = self.articleService.getArticle(guid)
                if art != None:
                    title=art.title
                    upun =self.pun_svc.createUPunishScore(ObjectType.OBJECT_TYPE_ARTICLE.getTypeId(),art.id,art.userId,float(score),score_reason,self.loginUser.userId,self.loginUser.trueName)
                    self.pun_svc.saveUPunishScore(upun)
                    
                    #发送消息：管理员删除了您的文章/资源/视频及扣分信息，内容为您的 ***（内容标题） 被删除，罚*分，原因：***。
                    message = Message()
                    message.sendId = self.loginUser.userId
                    message.receiveId = art.userId
                    message.title = u"管理员删除了您的文章及扣分信息"
                    if score_reason != "":
                        message.content = u"您的文章 " + title + u" 被删除,扣" + str(score) + u"分,原因:" + score_reason
                    else:  
                        message.content = u"您的文章 " + title + u" 被删除,扣" + str(score) + u"分"
                    self.msg_svc.sendMessage(message)
                   
                    #删除文章                 
                    self.articleService.crashArticle(art) 
                    #self.articleService.deleteArticle(art)
        elif cmd == "select_type":
            cmd_type = self.params.safeGetStringParam("cmdtype")
            if cmd_type == "":
                return
            for guid in guids:
                art = self.articleService.getArticle(guid)
                if art != None:
                    if cmd_type == "approve":                        
                        art.setApprovedPathInfo(art.getOrginPath())                        
                        self.articleService.updateArticle(art)
                        self.articleService.auditArticle(art)
                    elif cmd_type == "unapprove":
                        art.setApprovedPathInfo(None)                        
                        self.articleService.updateArticle(art)
                        self.articleService.unauditArticle(art)
                    elif cmd_type == "best":
                        self.articleService.bestArticle(art)
                    elif cmd_type == "unbest":
                        self.articleService.unbestArticle(art)
                    elif cmd_type == "rcmd":
                        art.setRcmdPathInfo(art.getOrginPath())  
                        self.articleService.updateArticle(art)                        
                        self.articleService.rcmdArticle(art)
                    elif cmd_type == "unrcmd":
                        art.setRcmdPathInfo(None)  
                        self.articleService.updateArticle(art)
                        self.articleService.unrcmdArticle(art)
                    elif cmd_type == "delete":
                        self.articleService.deleteArticle(art)
                    elif cmd_type == "undelete":
                        self.articleService.recoverArticle(art)            
    
    def article_list(self):        
        self.collectionQueryString()
        if self.rcmdState == "-1":
            #print "Create GroupArticleQuery..."
            qry = GroupArticleQuery(""" a.articleId, a.title, a.createDate,a.pushState,
                               a.gradeId, a.hideState, a.topState, a.bestState, a.delState, a.recommendState,
                                a.typeState, a.unitId, sc.name as sysCateName,
                                a.userId, a.auditState, u.userId, ga.isGroupBest 
                           """)
        else:
            qry = ArticleQuery(""" a.articleId, a.title, a.createDate,a.pushState,
                               a.gradeId, a.hideState, a.topState, a.bestState, a.delState, a.recommendState,
                               a.typeState, a.unitId, sc.name as sysCateName,
                               a.userId, a.auditState, u.userId 
                           """)
        
        if self.sysCateId != "" and self.sysCateId.isdigit() == True:
            qry.sysCateId = int(self.sysCateId)            
        
        if self.approveState == "" or self.approveState.isdigit() == False:
            qry.auditState = None
        else:
            qry.auditState = int(self.approveState)        
        
        if self.bestState == "" or self.bestState.isdigit() == False:
            qry.bestState = None
        else:
            qry.bestState = self.triblesimu(self.bestState)
        
        if self.rcmdState == "-1":
            #小组文章的精华 GroupArticle 中的  isGroupBest = True
            qry.groupBest = True
        else:     
            if self.rcmdState == "" or self.rcmdState.isdigit() == False:       #凭什么说 -1 不是数字??????
                qry.rcmdState = None
            else:
                qry.rcmdState = self.triblesimu(self.rcmdState)
        
        if self.deleteState == "" or self.deleteState.isdigit() == False:
            qry.delState = None
        else:
            qry.delState = self.triblesimu(self.deleteState)
        
        qry.subjectId = self.metaSubjectId
        qry.gradeId = self.metaGradeId
        qry.FuzzyMatch = True
        pager = self.params.createPager()
        pager.itemName = u"文章"
        pager.itemUnit = u"篇"
        pager.pageSize = 25
        pager.totalRows = qry.count()
        article_list = qry.query_map(pager)
        groupbest_list = []
        if self.rcmdState != "-1":
            #增加小组文章精华的字段
            for a in article_list :
                artId = a['articleId']
                groupbest_list.append(self.groupArticleService.isBestInGroupArticle(artId))
                
        grade_list = self.subjectService.getMainGradeList()
        article_categories = self.categoryService.getCategoryTree('default')        
        request.setAttribute("article_categories", article_categories)
        request.setAttribute("grade_list", grade_list)
        request.setAttribute("groupbest_list", groupbest_list)     
        request.setAttribute("article_list", article_list)
        request.setAttribute("pager", pager)
        request.setAttribute("subject", self.subject)
        return "/WEB-INF/subjectmanage/article.ftl"
    
    def collectionQueryString(self):
        self.sysCateId = self.params.safeGetStringParam("ss", "")
        self.approveState = self.params.safeGetStringParam("sa", "")
        self.bestState = self.params.safeGetStringParam("sb", "")
        self.rcmdState = self.params.safeGetStringParam("sr", "")
        self.deleteState = self.params.safeGetStringParam("sd", "")
       
        
        request.setAttribute("ss", self.sysCateId)
        request.setAttribute("sa", self.approveState)
        request.setAttribute("sb", self.bestState)
        request.setAttribute("sr", self.rcmdState)
        request.setAttribute("sd", self.deleteState)
        
    def triblesimu(self, int1):
        if int1 == "0":
            return False
        else:
            return True        
