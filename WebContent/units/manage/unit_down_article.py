#encoding=utf-8
from unit_page import UnitBasePage
from article_query import ArticleQuery
from cn.edustar.jitar.util import CommonUtil

class unit_down_article(UnitBasePage):
    def __init__(self):
        UnitBasePage.__init__(self)
        self.unitService = __spring__.getBean("unitService")
        self.articleService = __spring__.getBean("articleService")
        
    def execute(self):
        # 此页面需要系统管理员和机构内容管理员进行管理
        if self.loginUser == None:
            return self.LOGIN
                
        self.unit = self.getUnit()
        if self.unit == None:
            self.addActionError(u"您所访问的机构不存在！")
            return self.ERROR
        if self.isUserAdmin() == False and self.isContentAdmin() == False:
            self.addActionError(u"你没有管理的权限。")
            return self.ERROR
        
        if request.getMethod() == "POST":
            self.post_request()
            if request.getHeader("Referer") != None and request.getHeader("Referer") != "":
                response.sendRedirect(request.getHeader("Referer"))
        
        self.get_request()
        request.setAttribute("unit", self.unit)
        
        return "/WEB-INF/unitsmanage/unit_down_article.ftl"
    
    def get_request(self):
        qry = ArticleQuery(""" a.articleId, a.title, a.createDate, a.viewCount, a.commentCount, a.userId, a.pushState,
                        a.unitPathInfo,a.approvedPathInfo, a.rcmdPathInfo,a.unitId,a.orginPath,
                        a.recommendState, a.auditState, a.delState, u.loginName, u.nickName, u.trueName """)
        
        qry.unit = None
        qry.orderType = 0
        qry.unitId = None
        qry.auditState = None
        qry.delState = None
        qry.rcmdState = None
       
        rcmdState = self.params.safeGetStringParam("rcmdState")            
        auditState = self.params.safeGetStringParam("auditState")
        delState = self.params.safeGetStringParam("delState")
        
        pushupState = self.params.safeGetStringParam("pushupState")
        custormAndWhereClause = "a.orginPath Like '%/" + str(self.unit.unitId) + "/%' And a.unitId <> " + str(self.unit.unitId) + " And"
                
        # 得到全部的下级单位
        unitList = self.unitService.getDownUnitList(self.unit)
        if rcmdState == "1":
            strRcmd = ""
            for ut in unitList:
                strRcmd = strRcmd + " a.rcmdPathInfo LIKE '%/" + str(ut.unitId) + "/%' or"
            if strRcmd != "":
                strRcmd = strRcmd[0:len(strRcmd)-2]
                custormAndWhereClause += " ("+strRcmd+") And"
        elif rcmdState == "0":
            strRcmd = ""
            for ut in unitList:
                strRcmd = strRcmd + " a.rcmdPathInfo Not LIKE '%/" + str(ut.unitId) + "/%' And"
            if strRcmd != "":
                strRcmd = strRcmd[0:len(strRcmd)-3]
                custormAndWhereClause += " (a.rcmdPathInfo Is Null or ("+strRcmd+")) And"
        
        if auditState == "0":
            strApprove = ""
            for ut in unitList:
                strApprove = strApprove + " a.approvedPathInfo LIKE '%/" + str(ut.unitId) + "/%' or"
            if strApprove != "":
                strApprove = strApprove[0:len(strApprove)-2]
                custormAndWhereClause += "("+strApprove+") And"
        elif auditState == "1":
            strApprove = ""
            for ut in unitList:
                strApprove = strApprove + " a.approvedPathInfo NOT LIKE '%/" + str(ut.unitId) + "/%' And"
            if strApprove != "":
                strApprove = strApprove[0:len(strApprove)-3]
                custormAndWhereClause += " (a.approvedPathInfo Is Null or ("+strApprove+")) And"            
            
        
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
    
    def post_request(self):
        cmd = self.params.safeGetStringParam("cmd")
        if cmd == "delete":
            self.delete()
        elif cmd == "undelete":
            self.undelete()
        elif cmd == "crash":
            self.crash()
        elif cmd == "approveLevel":
            self.approveLevel()
        elif cmd == "unapproveLevel":
            self.unapproveLevel()
        elif cmd == "setRcmd":
            self.setRcmd()
        elif cmd == "unsetRcmd":
            self.unsetRcmd()
        else:
            self.addActionError(u"无效的命令。")
            return self.ERROR
        
            
    def delete(self):
        guids = self.params.safeGetIntValues("guid")
        for g in guids:
            article = self.articleService.getArticle(g)
            if article != None:                    
                if article.delState != True:
                    self.articleService.deleteArticle(article)
                
    def undelete(self):
        guids = self.params.safeGetIntValues("guid")
        for g in guids:
            article = self.articleService.getArticle(g)
            if article != None:
                if article.delState == True:
                    self.articleService.recoverArticle(article)
    
    def crash(self):
        guids = self.params.safeGetIntValues("guid")
        deleteComplexObjectService = __spring__.getBean("deleteComplexObjectService")
        for g in guids:
            article = self.articleService.getArticle(g)
            if article != None:
                deleteComplexObjectService.deleteArticle(article)
    
    def approveLevel(self):
        guids = self.params.safeGetIntValues("guid")
        for g in guids:
            article = self.articleService.getArticle(g)
            if article != None:
                self.articleService.auditArticle(article)
                articleUnitId = article.unitId
                if articleUnitId != None:
                    articleUnit = self.unitService.getUnitById(articleUnitId)
                    if articleUnit != None:
                        unitPath = self.unitService.getUnitPathBetweenUnits(self.unit, articleUnit)
                        if unitPath != "":
                            unitPath = CommonUtil.sortNumberString(unitPath)
                            approvedPathInfo = article.approvedPathInfo
                            if approvedPathInfo == None:
                                article.setApprovedPathInfo(unitPath)
                            else:                                
                                arraypath = unitPath.split("/")
                                for p in range(len(arraypath) - 1, -1, -1):
                                    if arraypath[p] != "":
                                        partInfo = "/" + arraypath[p] + "/"
                                        approvedPathInfo = article.approvedPathInfo
                                        if approvedPathInfo.find(partInfo) == -1:
                                            PathInfo = "/" + arraypath[p] + approvedPathInfo
                                            PathInfo = CommonUtil.sortNumberString(PathInfo)
                                            article.setApprovedPathInfo(PathInfo)
                            self.articleService.updateArticle(article)               
                
    
    def unapproveLevel(self):
        guids = self.params.safeGetIntValues("guid")
        for g in guids:
            article = self.articleService.getArticle(g)
            if article != None:
                articleUnitId = article.unitId
                if articleUnitId != None:
                    articleUnit = self.unitService.getUnitById(articleUnitId)
                    if articleUnit != None:
                        unitPath = self.unitService.getUnitPathBetweenUnits(self.unit, articleUnit)
                        if unitPath != "":
                            approvedPathInfo = article.approvedPathInfo
                            if approvedPathInfo != None:      
                                arraypath = unitPath.split("/")
                                for p in range(len(arraypath) - 1, -1, -1):
                                    if arraypath[p] != "":
                                        partInfo = "/" + arraypath[p] + "/"
                                        approvedPathInfo = article.approvedPathInfo
                                        if approvedPathInfo.find(partInfo) > -1:
                                            approvedPathInfo = approvedPathInfo.replace(partInfo, "/")
                                            if approvedPathInfo == "/":
                                                approvedPathInfo = None
                                                self.articleService.unauditArticle(article)
                                            
                                            if approvedPathInfo != None:
                                                approvedPathInfo = CommonUtil.sortNumberString(approvedPathInfo)
                                            
                                            article.setApprovedPathInfo(approvedPathInfo)
                            self.articleService.updateArticle(article)
    
    def setRcmd(self):
        guids = self.params.safeGetIntValues("guid")
        for g in guids:
            article = self.articleService.getArticle(g)
            if article != None:
                articleUnitId = article.unitId
                if articleUnitId != None:
                    articleUnit = self.unitService.getUnitById(articleUnitId)
                    if articleUnit != None:
                        unitPath = self.unitService.getUnitPathBetweenUnits(self.unit, articleUnit)
                        if unitPath != "":
                            unitPath = CommonUtil.sortNumberString(unitPath)
                            rcmdPathInfo = article.rcmdPathInfo
                            if rcmdPathInfo == None:
                                article.setRcmdPathInfo(unitPath)
                            else:                                
                                arraypath = unitPath.split("/")
                                for p in range(len(arraypath) - 1, -1, -1):
                                    if arraypath[p] != "":
                                        partInfo = "/" + arraypath[p] + "/"
                                        rcmdPathInfo = article.rcmdPathInfo
                                        if rcmdPathInfo.find(partInfo) == -1:
                                            PathInfo = "/" + arraypath[p] + rcmdPathInfo
                                            PathInfo = CommonUtil.sortNumberString(PathInfo)
                                            article.setRcmdPathInfo(PathInfo)
                            self.articleService.updateArticle(article)
                            
    
    def unsetRcmd(self):
        guids = self.params.safeGetIntValues("guid")
        for g in guids:
            article = self.articleService.getArticle(g)
            if article != None:
                articleUnitId = article.unitId
                if articleUnitId != None:
                    articleUnit = self.unitService.getUnitById(articleUnitId)
                    if articleUnit != None:
                        unitPath = self.unitService.getUnitPathBetweenUnits(self.unit, articleUnit)
                        if unitPath != "":
                            rcmdPathInfo = article.rcmdPathInfo
                            if rcmdPathInfo != None:      
                                arraypath = unitPath.split("/")
                                for p in range(len(arraypath) - 1, -1, -1):
                                    if arraypath[p] != "":
                                        partInfo = "/" + arraypath[p] + "/"
                                        rcmdPathInfo = article.rcmdPathInfo
                                        if rcmdPathInfo.find(partInfo) > -1:
                                            rcmdPathInfo = rcmdPathInfo.replace(partInfo, "/")
                                            if rcmdPathInfo == "/":
                                                rcmdPathInfo = None
                                            
                                            if rcmdPathInfo != None:
                                                rcmdPathInfo = CommonUtil.sortNumberString(rcmdPathInfo)
                                            article.setRcmdPathInfo(rcmdPathInfo)
                            self.articleService.updateArticle(article)
