# -*- coding: UTF-8 -*-
# coding=UTF-8
from cn.edustar.jitar.util import ParamUtil
from cn.edustar.jitar.pojos import SubjectWebpart, SiteNav
from subject_page import *
from java.util import HashMap
from article_query import ArticleQuery
from resource_query import ResourceQuery
from site_news_query import SiteNewsQuery
from video_query import VideoQuery
from user_query import UserQuery
from placard_query import PlacardQuery
from group_query import GroupQuery
from action_query import ActionQuery
from base_specialsubject_page import SpecialSubjectQuery
from org.python.core import codecs

ENABLE_CACHE = False
if ENABLE_CACHE:
    cache = __jitar__.cacheProvider.getCache('subject')
else:
    cache = NoCache()

class index(BaseSubject):
    def __init__(self):
        BaseSubject.__init__(self)
        self.templateProcessor = __spring__.getBean("templateProcessor")
        self.siteLinksService = __spring__.getBean("siteLinksService")
        self.statService = __jitar__.statService
        self.cacheKeyFix = ""
        
    def execute(self):
        if self.subject == None:
            self.addActionError("Object not be found !")
            return self.ERROR
        cacheCount = cache.get("cacheCount")
        print "cacheCount",cacheCount
        if cacheCount == None:
            timerCountService = __spring__.getBean("timerCountService")
            timerCountService.doSubjectCount(self.subject)
            self.subjectService.clearCacheData()
            self.subject = self.subjectService.getSubjectById(self.subject.subjectId)        
        
        shortcutTarget = self.subject.shortcutTarget
        if shortcutTarget != None:
            response.sendRedirect(shortcutTarget)
            return
        
        if self.unitId != None and self.unitId != 0:
            self.cacheKeyFix = "_" + str(self.unitId)
        
        
        self.templateName = self.subject.templateName        
        if self.templateName == None or self.templateName == "":
            self.templateName = "template1"
        
        theme = self.params.safeGetStringParam("theme")
        if theme != "":
            request.setAttribute("theme", theme)
        webpartList = self.subjectService.getSubjectWebpartList(self.subject.subjectId, True)
        if self.params.existParam("tm") == False:
            if len(webpartList) < 1:
                self.genWebparts()
                self.addSubjectNav()
                response.sendRedirect("?tm=1")
                return
        
        
        for webpart in webpartList:
            self.set_webpart_flag(webpart)
            if webpart.moduleName == SubjectWebpart.WEBPART_MODULENAME_ARTICLE:
               self.genernate_article_content(webpart)
            elif webpart.moduleName == SubjectWebpart.WEBPART_MODULENAME_RESOURCE:
                self.genernate_resoure_content(webpart)
            elif webpart.moduleName == SubjectWebpart.WEBPART_MODULENAME_JIAOYANSHIPIN:
                self.genernate_jiaoyanshipin_content(webpart)
            elif webpart.moduleName == SubjectWebpart.WEBPART_MODULENAME_PICNEWS:
                self.genernate_picnews_content(webpart)
            elif webpart.moduleName == SubjectWebpart.WEBPART_MODULENAME_NEWS:
                self.genernate_news_content(webpart)
            elif webpart.moduleName == SubjectWebpart.WEBPART_MODULENAME_NOTICE:
                self.genernate_notice_content(webpart)
            elif webpart.moduleName == SubjectWebpart.WEBPART_MODULENAME_LINKS:
                self.genernate_links_content(webpart)
            elif webpart.moduleName == SubjectWebpart.WEBPART_MODULENAME_STATISTICS:
                self.genernate_statistics_content(webpart)
            elif webpart.moduleName == SubjectWebpart.WEBPART_MODULENAME_VOTE:
                self.genernate_vote_content(webpart)
            elif webpart.moduleName == SubjectWebpart.WEBPART_MODULENAME_JIAOYANYUAN:
                self.genernate_jiaoyanyuan_content(webpart)
            elif webpart.moduleName == SubjectWebpart.WEBPART_MODULENAME_MINGSHI:
                self.genernate_mingshi_content(webpart)
            elif webpart.moduleName == SubjectWebpart.WEBPART_NODULENAME_DAITOUREN:
                self.genernate_daitouren_content(webpart)
            elif webpart.moduleName == SubjectWebpart.WEBPART_MODULENAME_GONGZUOSHI:
                self.genernate_gongzuoshi_content(webpart)
            elif webpart.moduleName == SubjectWebpart.WEBPART_MODULENAME_XIEZUOZU:
                self.genernate_xiezuozu_content(webpart)
            elif webpart.moduleName == SubjectWebpart.WEBPART_MODULENAME_JIAOYANHUODONG:
                self.genernate_jiaoyanhuodong_content(webpart)
            elif webpart.moduleName == SubjectWebpart.WEBPART_MODULENAME_JIAOYANZHUANTI:
                self.genernate_jiaoyanzhuanti_content(webpart)
            elif webpart.moduleName == SubjectWebpart.WEBPART_MODULENAME_WENDA:
                self.genernate_wenda_content(webpart)
            elif webpart.moduleName == SubjectWebpart.WEBPART_MODULENAME_TOPIC:
                self.genernate_topic_content(webpart)
            else:
                cache_key = "sbj" + str(self.subject.subjectId) + "_" + str(webpart.getSubjectWebpartId()) + self.cacheKeyFix
                content = cache.get(cache_key)
                if content != None:
                    request.setAttribute(cache_key, content)
                else:
                    map = HashMap()
                    map.put("subject", self.subject.subjectId)
                    map.put("webpart", webpart)
                    map.put("unitId", self.unitId)
                    map.put("SubjectRootUrl", self.subjectRootUrl)
                    content = self.templateProcessor.processTemplate(map, "/WEB-INF/subjectpage/" + self.templateName + "/custorm.ftl", "utf-8")
                    request.setAttribute(cache_key, content)
                    cache.put(cache_key, content)
        
        theme = self.params.safeGetStringParam("theme")
        request.setAttribute("head_nav", "subject")
        request.setAttribute("subject", self.subject)
        request.setAttribute("webpartList", webpartList)
        if theme != "":
            request.setAttribute("theme", theme)
        
        if self.loginUser != None:
            request.setAttribute("loginUser", self.loginUser)
            preview = self.params.safeGetStringParam("preview")
            if self.isAdmin() == True and preview != "":
                request.setAttribute("role", "admin")        
        
        request.setAttribute("unitId", self.unitId)
        request.setAttribute("req", request)
        return "/WEB-INF/subjectpage/" + self.templateName + "/index.ftl"
    
    def set_webpart_flag(self, webpart):
        if webpart.webpartZone == SubjectWebpart.WEBPART_TOP:
            request.setAttribute("hasTopWebpart", "1")
        elif webpart.webpartZone == SubjectWebpart.WEBPART_BOTTOM:
            request.setAttribute("hasBottomWebpart", "1")
        elif webpart.webpartZone == SubjectWebpart.WEBPART_LEFT:
            request.setAttribute("hasLeftWebpart", "1")
        elif webpart.webpartZone == SubjectWebpart.WEBPART_MIDDLE:
            request.setAttribute("hasMiddleWebpart", "1")
        elif webpart.webpartZone == SubjectWebpart.WEBPART_RIGHT:
            request.setAttribute("hasRightWebpart", "1")
    
    def genernate_article_content(self, webpart):
        cache_key = "sbj" + str(self.subject.subjectId) + "_" + str(webpart.getSubjectWebpartId()) + self.cacheKeyFix
        content = cache.get(cache_key)
        if content != None:
            request.setAttribute(cache_key, content)
            return
        
        map = HashMap()
        qry = ArticleQuery("a.articleId, a.title, a.createDate, a.typeState, a.userId, a.loginName, a.userTrueName")
        qry.subjectId = self.metaSubjectId
        qry.gradeId = self.metaGradeId
        qry.FuzzyMatch = True
        if self.unitId != None and self.unitId != 0:
            qry.custormAndWhereClause = " a.approvedPathInfo LIKE '%/" + str(self.unitId) + "/%'"
            
        newest_article_list = qry.query_map(10)
        map.put("newest_article_list", newest_article_list)        
        #hot_article_list=self.viewcount_svc.getViewCountListShared(3,7,10,self.unit.unitPath,self.unit.unitDepth);
        #map.put("hot_article_list",hot_article_list)
        
        qry = ArticleQuery(""" a.articleId, a.title, a.createDate,a.typeState, a.userId, a.loginName, a.userTrueName """)
        qry.subjectId = self.metaSubjectId
        qry.gradeId = self.metaGradeId
        qry.FuzzyMatch = True
        qry.orderType = 2
        if self.unitId != None and self.unitId != 0:
            qry.custormAndWhereClause = " a.approvedPathInfo LIKE '%/" + str(self.unitId) + "/%'"
        hot_article_list = qry.query_map(10)
        map.put("hot_article_list", hot_article_list)        
        
        qry = ArticleQuery(""" a.articleId, a.title, a.createDate, a.typeState, a.userId, a.loginName, a.userTrueName """)
        qry.rcmdState = True
        qry.subjectId = self.metaSubjectId
        qry.gradeId = self.metaGradeId
        qry.FuzzyMatch = True
        if self.unitId != None and self.unitId != 0:
            qry.custormAndWhereClause = " a.rcmdPathInfo LIKE '%/" + str(self.unitId) + "/%'"
           
        rcmd_article_list = qry.query_map(10)
        map.put("rcmd_article_list", rcmd_article_list)
        
        map.put("subject", self.subject)
        map.put("webpart", webpart)
        map.put("unitId", self.unitId)
        map.put("SubjectRootUrl", self.subjectRootUrl)
        content = self.templateProcessor.processTemplate(map, "/WEB-INF/subjectpage/" + self.templateName + "/article.ftl", "utf-8")
        request.setAttribute(cache_key, content)
        cache.put(cache_key, content)
        
    def genernate_resoure_content(self, webpart):
        cache_key = "sbj" + str(self.subject.subjectId) + "_" + str(webpart.getSubjectWebpartId()) + self.cacheKeyFix
        content = cache.get(cache_key)
        if content != None:
            request.setAttribute(cache_key, content)
            return
        
        map = HashMap()        
        qry = ResourceQuery(""" r.resourceId, r.title, r.href, r.createDate, r.fsize, r.downloadCount, 
        u.loginName, u.nickName, r.subjectId as subjectId, grad.gradeName, sc.name as scName """)
        qry.subjectId = self.metaSubjectId
        qry.gradeId = self.metaGradeId
        qry.FuzzyMatch = True
        if self.unitId != None and self.unitId != 0:
            qry.custormAndWhereClause = " r.approvedPathInfo LIKE '%/" + str(self.unitId) + "/%'"
        new_resource_list = qry.query_map(10)
        map.put("new_resource_list", new_resource_list)
        
        qry = ResourceQuery(""" r.resourceId, r.title, r.href, r.createDate, r.fsize, r.downloadCount, 
        u.loginName, u.nickName, msubj.msubjName, grad.gradeName, sc.name as scName """)
        qry.orderType = 4       # downloadCount DESC
        qry.subjectId = self.metaSubjectId
        qry.gradeId = self.metaGradeId
        qry.FuzzyMatch = True
        if self.unitId != None and self.unitId != 0:
            qry.custormAndWhereClause = " r.approvedPathInfo LIKE '%/" + str(self.unitId) + "/%'"
        hot_resource_list = qry.query_map(10)
        map.put("hot_resource_list", hot_resource_list)
        
        #hot_resource_list = self.viewcount_svc.getViewCountListShared(12,7,10,self.unit.unitPath,self.unit.unitDepth);
        #map.put("hot_resource_list", hot_resource_list)
        
        # �Ƽ���Դ
        qry = ResourceQuery(""" r.resourceId, r.title, r.href, r.createDate, r.fsize, r.downloadCount, 
        u.loginName, u.nickName, msubj.msubjName, grad.gradeName, sc.name as scName """)
        qry.rcmdState = True
        qry.subjectId = self.metaSubjectId
        qry.gradeId = self.metaGradeId
        qry.FuzzyMatch = True
        if self.unitId != None and self.unitId != 0:
            qry.custormAndWhereClause = " r.rcmdPathInfo LIKE '%/" + str(self.unitId) + "/%'"
        rcmd_resource_list = qry.query_map(10)
        map.put("rcmd_resource_list", rcmd_resource_list)    
       
        map.put("subject", self.subject)
        map.put("SubjectRootUrl", self.subjectRootUrl)
        map.put("webpart", webpart)
        map.put("unitId", self.unitId)
        content = self.templateProcessor.processTemplate(map, "/WEB-INF/subjectpage/" + self.templateName + "/resource.ftl", "utf-8")
        request.setAttribute(cache_key, content)
        cache.put(cache_key, content)

    def genernate_jiaoyanshipin_content(self, webpart):
        cache_key = "sbj" + str(self.subject.subjectId) + "_" + str(webpart.getSubjectWebpartId()) + self.cacheKeyFix
        content = cache.get(cache_key)
        if content != None:
            request.setAttribute(cache_key, content)
            return
        
        map = HashMap()
        qry = VideoQuery(""" v.videoId, v.title, v.createDate, v.href, v.flvHref, v.userId, u.loginName, u.nickName, v.summary,v.flvThumbNailHref """)
        qry.orderType = VideoQuery.ORDER_TYPE_VIDEOID_DESC
        qry.subjectId = self.metaSubjectId
        qry.gradeId = self.metaGradeId
        if self.unitId != None and self.unitId != 0:
            qry.unitId = self.unitId
        new_video_list = qry.query_map(4)
        map.put("new_video_list", new_video_list)
        map.put("subject", self.subject)
        map.put("SubjectRootUrl", self.subjectRootUrl)
        map.put("webpart", webpart)
        map.put("unitId", self.unitId)
        content = self.templateProcessor.processTemplate(map, "/WEB-INF/subjectpage/" + self.templateName + "/video.ftl", "utf-8")
        request.setAttribute(cache_key, content)
        cache.put(cache_key, content)
        
    def genernate_picnews_content(self, webpart):
        cache_key = "sbj" + str(self.subject.subjectId) + "_" + str(webpart.getSubjectWebpartId()) + self.cacheKeyFix
        content = cache.get(cache_key)
        if content != None:
            request.setAttribute(cache_key, content)
            return
        
        map = HashMap()
        qry = SiteNewsQuery(""" snews.newsId, snews.title, snews.createDate, snews.picture """)
        qry.subjectId = self.subject.subjectId
        qry.hasPicture = True
        pic_news = qry.query_map(6)
    
        map.put("pic_news", pic_news)
        map.put("subject", self.subject)
        map.put("SubjectRootUrl", self.subjectRootUrl)
        map.put("webpart", webpart)
        map.put("unitId", self.unitId)
        content = self.templateProcessor.processTemplate(map, "/WEB-INF/subjectpage/" + self.templateName + "/pic_news.ftl", "utf-8")
        request.setAttribute(cache_key, content)
        cache.put(cache_key, content)
        
    def genernate_news_content(self, webpart):
        cache_key = "sbj" + str(self.subject.subjectId) + "_" + str(webpart.getSubjectWebpartId()) + self.cacheKeyFix
        content = cache.get(cache_key)
        if content != None:
            request.setAttribute(cache_key, content)
            return
        
        map = HashMap()
        qry = SiteNewsQuery(""" snews.newsId, snews.title, snews.createDate""")
        qry.subjectId = self.subject.subjectId
        qry.hasPicture = False
        subject_text_news = qry.query_map(6)
        
        map.put("subject_text_news", subject_text_news)
        map.put("subject", self.subject)
        map.put("SubjectRootUrl", self.subjectRootUrl)
        map.put("webpart", webpart)
        map.put("unitId", self.unitId)
        content = self.templateProcessor.processTemplate(map, "/WEB-INF/subjectpage/" + self.templateName + "/text_news.ftl", "utf-8")
        request.setAttribute(cache_key, content)
        cache.put(cache_key, content)
        
    def genernate_notice_content(self, webpart):
        cache_key = "sbj" + str(self.subject.subjectId) + "_" + str(webpart.getSubjectWebpartId()) + self.cacheKeyFix
        content = cache.get(cache_key)
        if content != None:
            request.setAttribute(cache_key, content)
            return
        
        map = HashMap()
        qry = PlacardQuery(""" pld.id, pld.title, pld.content """)
        qry.objType = 14  
        qry.objId = self.subject.subjectId        
        placard_list = qry.query_map(6)
        map.put("placard_list", placard_list)
        map.put("subject", self.subject)
        map.put("SubjectRootUrl", self.subjectRootUrl)
        map.put("webpart", webpart)
        map.put("unitId", self.unitId)
        content = self.templateProcessor.processTemplate(map, "/WEB-INF/subjectpage/" + self.templateName + "/notice.ftl", "utf-8")
        request.setAttribute(cache_key, content)
        cache.put(cache_key, content)
        
    def genernate_jiaoyanyuan_content(self, webpart):
        cache_key = "sbj" + str(self.subject.subjectId) + "_" + str(webpart.getSubjectWebpartId()) + self.cacheKeyFix
        content = cache.get(cache_key)
        if content != None:
            request.setAttribute(cache_key, content)
            return
        
        map = HashMap()
        jiaoyanyuan = self.get_subject_comissioner()
        map.put("jiaoyanyuan", jiaoyanyuan)
        map.put("subject", self.subject)
        map.put("SubjectRootUrl", self.subjectRootUrl)
        map.put("webpart", webpart)
        map.put("unitId", self.unitId)
        content = self.templateProcessor.processTemplate(map, "/WEB-INF/subjectpage/" + self.templateName + "/jiaoyanyuan.ftl", "utf-8")
        request.setAttribute(cache_key, content)
        cache.put(cache_key, content)

    def genernate_mingshi_content(self, webpart):
        cache_key = "sbj" + str(self.subject.subjectId) + "_" + str(webpart.getSubjectWebpartId()) + self.cacheKeyFix
        content = cache.get(cache_key)
        if content != None:
            request.setAttribute(cache_key, content)
            return
        
        map = HashMap()        
        famous_user_list = self.get_famous_list()
        map.put("famous_user_list", famous_user_list)
        map.put("subject", self.subject)
        map.put("SubjectRootUrl", self.subjectRootUrl)
        map.put("webpart", webpart)
        map.put("unitId", self.unitId)
        content = self.templateProcessor.processTemplate(map, "/WEB-INF/subjectpage/" + self.templateName + "/mingshi.ftl", "utf-8")
        request.setAttribute(cache_key, content)
        cache.put(cache_key, content)
    
    def genernate_daitouren_content(self, webpart):
        cache_key = "sbj" + str(self.subject.subjectId) + "_" + str(webpart.getSubjectWebpartId()) + self.cacheKeyFix
        content = cache.get(cache_key)
        if content != None:
            request.setAttribute(cache_key, content)
            return
        
        map = HashMap()        
        expert_user_list = self.get_expert_list()
        map.put("expert_user_list", expert_user_list)
        map.put("subject", self.subject)
        map.put("SubjectRootUrl", self.subjectRootUrl)
        map.put("webpart", webpart)
        map.put("unitId", self.unitId)
        content = self.templateProcessor.processTemplate(map, "/WEB-INF/subjectpage/" + self.templateName + "/daitouren.ftl", "utf-8")
        request.setAttribute(cache_key, content)
        cache.put(cache_key, content)
        
    def genernate_links_content(self, webpart):
        cache_key = "sbj" + str(self.subject.subjectId) + "_" + str(webpart.getSubjectWebpartId()) + self.cacheKeyFix
        content = cache.get(cache_key)
        if content != None:
            request.setAttribute(cache_key, content)
            return
        
        map = HashMap()
        links = self.siteLinksService.getSiteLinksList("subject", self.subject.subjectId)
        if links != None:
            map.put("links", links)
        map.put("subject", self.subject)
        map.put("SubjectRootUrl", self.subjectRootUrl)
        map.put("webpart", webpart)
        map.put("unitId", self.unitId)
        content = self.templateProcessor.processTemplate(map, "/WEB-INF/subjectpage/" + self.templateName + "/links.ftl", "utf-8")
        request.setAttribute(cache_key, content)
        cache.put(cache_key, content)
        
    def genernate_statistics_content(self, webpart):
        cache_key = "sbj" + str(self.subject.subjectId) + "_" + str(webpart.getSubjectWebpartId()) + self.cacheKeyFix
        content = cache.get(cache_key)
        if content != None:
            request.setAttribute(cache_key, content)
            return
        
        # 重新进行统计        
        map = HashMap()
        map.put("subject", self.subject)
        map.put("SubjectRootUrl", self.subjectRootUrl)
        map.put("webpart", webpart)
        map.put("unitId", self.unitId)
        content = self.templateProcessor.processTemplate(map, "/WEB-INF/subjectpage/" + self.templateName + "/count.ftl", "utf-8")
        request.setAttribute(cache_key, content)
        cache.put(cache_key, content)
        
    def genernate_vote_content(self, webpart):
        cache_key = "sbj" + str(self.subject.subjectId) + "_" + str(webpart.getSubjectWebpartId()) + self.cacheKeyFix
        content = cache.get(cache_key)
        if content != None:
            request.setAttribute(cache_key, content)
            return
        
        map = HashMap()
        map.put("subject", self.subject)
        map.put("SubjectRootUrl", self.subjectRootUrl)
        map.put("webpart", webpart)
        map.put("unitId", self.unitId)
        content = self.templateProcessor.processTemplate(map, "/WEB-INF/subjectpage/" + self.templateName + "/vote.ftl", "utf-8")
        request.setAttribute(cache_key, content)
        cache.put(cache_key, content)
    
    def genernate_gongzuoshi_content(self, webpart):
        cache_key = "sbj" + str(self.subject.subjectId) + "_" + str(webpart.getSubjectWebpartId()) + self.cacheKeyFix
        content = cache.get(cache_key)
        if content != None:
            request.setAttribute(cache_key, content)
            return
        
        map = HashMap()
        new_blog_list = self.get_new_list(4)
        map.put("new_blog_list", new_blog_list)
        
        hot_blog_list = self.get_hot_list(4)
        map.put("hot_blog_list", hot_blog_list)        
       
        rcmd_blog_list = self.get_rcmd_list(4)
        map.put("rcmd_blog_list", rcmd_blog_list)
        
        map.put("subject", self.subject)
        map.put("SubjectRootUrl", self.subjectRootUrl)
        map.put("webpart", webpart)
        map.put("unitId", self.unitId)
        content = self.templateProcessor.processTemplate(map, "/WEB-INF/subjectpage/" + self.templateName + "/gongzuoshi.ftl", "utf-8")
        request.setAttribute(cache_key, content)
        cache.put(cache_key, content)
        
        #print "qry.metaSubjectId:", qry.metaSubjectId
        #print "qry.metaGradeId:", qry.metaGradeId
        self.statService.subjectStat(self.metaSubjectId, self.metaGradeId, self.metaGradeId + 1000)
        
        
    def genernate_xiezuozu_content(self, webpart):
        cache_key = "sbj" + str(self.subject.subjectId) + "_" + str(webpart.getSubjectWebpartId()) + self.cacheKeyFix
        content = cache.get(cache_key)
        if content != None:
            request.setAttribute(cache_key, content)
            return
        
        map = HashMap()
        qry = GroupQuery("""  g.groupName,g.groupId, g.groupIcon, g.groupTitle, g.createDate, g.groupIntroduce """)
        qry.subjectId = self.metaSubjectId
        qry.gradeId = self.metaGradeId
        new_group_list = qry.query_map(4)
        map.put("new_group_list", new_group_list)
        
        qry = GroupQuery("""  g.groupName,g.groupIcon, g.createDate, g.groupId, g.groupTitle, g.groupIntroduce """)
        qry.subjectId = self.metaSubjectId
        qry.gradeId = self.metaGradeId
        qry.orderType = 8
        hot_group_list = qry.query_map(4)
        map.put("hot_group_list", hot_group_list)
        
        Qry = GroupQuery("""  g.groupName,g.groupId, g.groupIcon, g.groupTitle, g.createDate, g.groupIntroduce """)
        qry.subjectId = self.metaSubjectId
        qry.gradeId = self.metaGradeId
        qry.isRecommend = True
        rcmd_group_list = qry.query_map(4)
        map.put("rcmd_group_list", rcmd_group_list)   
    
        map.put("subject", self.subject)
        map.put("SubjectRootUrl", self.subjectRootUrl)
        map.put("webpart", webpart)
        map.put("unitId", self.unitId)
        content = self.templateProcessor.processTemplate(map, "/WEB-INF/subjectpage/" + self.templateName + "/xiezuozu.ftl", "utf-8")
        request.setAttribute(cache_key, content)
        cache.put(cache_key, content)
    
    def genernate_jiaoyanhuodong_content(self, webpart):
        cache_key = "sbj" + str(self.subject.subjectId) + "_" + str(webpart.getSubjectWebpartId()) + self.cacheKeyFix
        content = cache.get(cache_key)
        if content != None:
            request.setAttribute(cache_key, content)
            return
        
        map = HashMap()
        qry = ActionQuery(""" act.title, act.createDate, act.actionId, act.ownerId, act.ownerType, act.createUserId, act.actionType,
                              act.description, act.userLimit, act.startDateTime,act.finishDateTime, act.attendLimitDateTime, act.place,
                              act.status, act.visibility, act.attendCount
                            """)
        qry.ownerType = "subject"
        qry.ownerId = self.subject.subjectId
        action_list = qry.query_map(8)
        map.put("action_list", action_list)        
        map.put("subject", self.subject)
        map.put("SubjectRootUrl", self.subjectRootUrl)
        map.put("webpart", webpart)
        map.put("unitId", self.unitId)
        content = self.templateProcessor.processTemplate(map, "/WEB-INF/subjectpage/" + self.templateName + "/jiaoyanhuodong.ftl", "utf-8")
        request.setAttribute(cache_key, content)
        cache.put(cache_key, content)
        
    def genernate_jiaoyanzhuanti_content(self, webpart):
        cache_key = "sbj" + str(self.subject.subjectId) + "_" + str(webpart.getSubjectWebpartId()) + self.cacheKeyFix
        content = cache.get(cache_key)
        if content != None:
            request.setAttribute(cache_key, content)
            return
        
        map = HashMap()
        qry = SpecialSubjectQuery(""" ss.specialSubjectId, ss.logo, ss.title,ss.createUserId, ss.createDate,ss.expiresDate """)
        qry.objectId = self.subject.subjectId
        qry.objectType = "subject"
        ss_list = qry.query_map(8)
        map.put("ss_list", ss_list)
        map.put("subject", self.subject)
        map.put("SubjectRootUrl", self.subjectRootUrl)
        map.put("webpart", webpart)
        map.put("unitId", self.unitId)
        content = self.templateProcessor.processTemplate(map, "/WEB-INF/subjectpage/" + self.templateName + "/jiaoyanzhuanti.ftl", "utf-8")
        request.setAttribute(cache_key, content)
        cache.put(cache_key, content)
    
    def genernate_wenda_content(self, webpart):
        cache_key = "sbj" + str(self.subject.subjectId) + "_" + str(webpart.getSubjectWebpartId()) + self.cacheKeyFix
        content = cache.get(cache_key)
        if content != None:
            request.setAttribute(cache_key, content)
            return
        
        map = HashMap()
        map.put("subject", self.subject)
        map.put("SubjectRootUrl", self.subjectRootUrl)
        map.put("webpart", webpart)
        map.put("unitId", self.unitId)
        content = self.templateProcessor.processTemplate(map, "/WEB-INF/subjectpage/" + self.templateName + "/wenda.ftl", "utf-8")
        request.setAttribute(cache_key, content)
        cache.put(cache_key, content)
        
    def genernate_topic_content(self, webpart):
        cache_key = "sbj" + str(self.subject.subjectId) + "_" + str(webpart.getSubjectWebpartId()) + self.cacheKeyFix
        content = cache.get(cache_key)
        if content != None:
            request.setAttribute(cache_key, content)
            return
        
        map = HashMap()
        map.put("subject", self.subject)
        map.put("SubjectRootUrl", self.subjectRootUrl)
        map.put("webpart", webpart)
        map.put("unitId", self.unitId)
        content = self.templateProcessor.processTemplate(map, "/WEB-INF/subjectpage/" + self.templateName + "/topic.ftl", "utf-8")
        request.setAttribute(cache_key, content)
        cache.put(cache_key, content)
        
    def getCurrentSiteUrl(self, request):
        root = request.getScheme() + "://" + request.getServerName()
        if request.getServerPort() != 80:
            root = root + ":" + str(request.getServerPort())
        root = root + request.getContextPath() + "/"
        return root
    
    def genWebparts(self):
        #subject_list = self.subjectService.getSubjectList()
        #for subject in subject_list:
        subject_webpart_list = self.subjectService.getSubjectWebpartList(self.subject.subjectId, None)
        if len(subject_webpart_list) == 0:                
            i = 0
            for m in SubjectWebpart.MODULE_NAME:
                i = i + 1
                subjectWebpart = SubjectWebpart()
                subjectWebpart.setModuleName(m)
                subjectWebpart.setDisplayName(m)
                subjectWebpart.setRowIndex(i)
                subjectWebpart.setVisible(True)
                subjectWebpart.setSystemModule(1)
                subjectWebpart.setSubjectId(self.subject.subjectId)
                if i < 7:
                   subjectWebpart.setWebpartZone(3)
                elif i < 13 :
                    subjectWebpart.setWebpartZone(4)
                else:
                    subjectWebpart.setWebpartZone(5)
                self.subjectService.saveOrUpdateSubjectWebpart(subjectWebpart)
                    
    def addSubjectNav(self):
        siteNameArray = SubjectWebpart.SUBJECT_NAVNAME
        siteUrlArray = SubjectWebpart.SUBJECT_NAVURL        
        siteHightlightArray = SubjectWebpart.SUBJECT_NAVHIGHLIGHT
        siteNavService = __spring__.getBean("siteNavService")
        i = 0
        for name in siteNameArray:            
            siteNav = SiteNav()
            siteNav.setSiteNavName(siteNameArray[i])
            siteNav.setIsExternalLink(False)
            siteNav.setSiteNavUrl(siteUrlArray[i])
            siteNav.setSiteNavIsShow(True)
            siteNav.setSiteNavItemOrder(i)
            siteNav.setCurrentNav(siteHightlightArray[i])
            siteNav.setOwnerType(2)
            siteNav.setOwnerId(self.subject.getSubjectId())
            siteNavService.saveOrUpdateSiteNav(siteNav)
            i = i + 1           
        self.clearcache()
        
    def clearcache(self):
        cache = __jitar__.cacheProvider.getCache('sitenav')
        if cache != None:                    
            cache_list = cache.getAllKeys()
            cache_key = "subject_nav_" + str(self.subject.subjectId)
            for c in cache_list:
                if c == cache_key:
                    cache.remove(c)