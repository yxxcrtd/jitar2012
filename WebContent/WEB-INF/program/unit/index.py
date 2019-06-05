from unit_page import *
from article_query import ArticleQuery
from resource_query import ResourceQuery
from unit_other_query import *
from photo_query import PhotoQuery
from video_query import VideoQuery
from cn.edustar.jitar.pojos import UnitWebpart
from java.util import HashMap
from cn.edustar.jitar.util import FileCache
from cn.edustar.jitar.util import CommonUtil
from java.io import File
from cn.edustar.jitar.model import MemcachedExpireTimeConfig

ENABLE_CACHE = False

if ENABLE_CACHE:
    cache = __jitar__.cacheProvider.getCache('unit')
else:
    cache = NoCache()

class index(UnitBasePage):
    def __init__(self):
        UnitBasePage.__init__(self)        
        self.templateProcessor = __spring__.getBean("templateProcessor")
        self.viewcount_svc = __spring__.getBean("viewCountService")
        self.htmlGeneratorService = __spring__.getBean("htmlGeneratorService")        
        
    def execute(self):
        #print "MemcachedExpireTimeConfig.getSiteIndexExpireTime()=",MemcachedExpireTimeConfig.getSiteIndexExpireTime()
        if self.viewcount_svc == None:
            self.addActionError(u"无法加载组织机构服务，请检查 applicationContext.xml 配置文件，是否缺少 viewCountService 节点。")
            return self.ERROR
        
        if self.templateProcessor == None:
            self.addActionError(u"无法加载组织机构服务，请检查 applicationContext.xml 配置文件，是否缺少 templateProcessor 节点。")
            return self.ERROR
        
        if self.unitService == None:
            self.addActionError(u"无法加载组织机构服务，请检查 applicationContext.xml 配置文件，是否缺少 unitService 节点。")
            return self.ERROR
        
        self.unit = self.getUnit()
        if self.unit == None:
            self.addActionError(u"您所访问的机构不存在！")
            return self.ERROR
        #print "self.unit.parentId = ", self.unit.parentId
        if self.unit.delState == True:
            self.addActionError(u"您所访问的机构已经被删除！")
            return self.ERROR
        if self.unit.parentId == 0:
            configSiteRoot = request.getAttribute("configSiteRoot")
            if configSiteRoot == None or len(configSiteRoot) == 0:
                response.sendRedirect(request.getContextPath() + "/")
            else:
                response.sendRedirect(configSiteRoot)                
            return
        preview = self.params.safeGetStringParam("preview")
        if self.isUnitAdmin() == False or preview == "":            
            fc = FileCache()
            html = ""
            out = response.getWriter()
            theme = self.params.safeGetStringParam("theme")
             
            if theme != "":  
                #这是预览
                html = self.htmlGeneratorService.UnitIndex(self.unit,"",theme)            
                out.write(html)
            else:
                unitIndexHtmlPath = fc.getUnitHtmlFolder(self.unit.unitName) + "index.html"
                if fc.contentIsExpired(unitIndexHtmlPath, MemcachedExpireTimeConfig.getSiteIndexExpireTime() / 60) == True:
                    html = self.htmlGeneratorService.UnitIndex(self.unit)            
                    out.write(html)
                else:
                    file = File(unitIndexHtmlPath)
                    out.write(CommonUtil.readFile(file.getCanonicalPath(), "UTF-8"))
                    file = None
            fc = None
            #request.getSession().getServletContext().getRequestDispatcher("/html/unit/" + self.unit.unitName + "/index.html").forward(request, response)
            return
        """""""""
                        注意：以下代码只有管理员执行！！！！！！！
        """""""""
        self.templateName = "template1"
        if self.unit.templateName != None:
            self.templateName = self.unit.templateName
        
        webpartList = self.unitService.getUnitWebpartList(self.unit.unitId)
        if self.params.existParam("tm") == False:                
            if len(webpartList) < 1:
                self.genWebparts()
                response.sendRedirect("?tm=1")
                return
        
        for webpart in webpartList:
            self.set_webpart_flag(webpart)
            #print "webpart.moduleName = ", webpart.moduleName
            # 生成具体的内容，放到content字段内
            if webpart.moduleName == UnitWebpart.WEBPART_MODULENAME_ARTICLE:
               self.genernate_article_content(webpart)
            elif webpart.moduleName == UnitWebpart.WEBPART_MODULENAME_RESOURCE:
                self.genernate_resoure_content(webpart)
            elif webpart.moduleName == UnitWebpart.WEBPART_MODULENAME_PHOTO:
                self.genernate_photo_content(webpart)
            elif webpart.moduleName == UnitWebpart.WEBPART_MODULENAME_VIDEO:
                self.genernate_video_content(webpart)
            elif webpart.moduleName == UnitWebpart.WEBPART_MODULENAME_PICNEWS:
                self.genernate_picnews_content(webpart)
            elif webpart.moduleName == UnitWebpart.WEBPART_MODULENAME_NEWESTNEWS:
                self.genernate_newestnews_content(webpart)
            elif webpart.moduleName == UnitWebpart.WEBPART_MODULENAME_UNITNOTICE:
                self.genernate_unitnotice_content(webpart)
            elif webpart.moduleName == UnitWebpart.WEBPART_MODULENAME_LINKS:
                self.genernate_links_content(webpart)
            elif webpart.moduleName == UnitWebpart.WEBPART_MODULENAME_STATISTICS:
                self.genernate_statistics_content(webpart)
            elif webpart.moduleName == UnitWebpart.WEBPART_MODULENAME_VOTE:
                self.genernate_vote_content(webpart)
            elif webpart.moduleName == UnitWebpart.WEBPART_MODULENAME_UNITSUBJECT:
                self.genernate_unitsubject_content(webpart)
            elif webpart.moduleName == UnitWebpart.WEBPART_MODULENAME_UNITGROUP:
                self.genernate_group_content(webpart)
            elif webpart.moduleName == UnitWebpart.WEBPART_MODULENAME_UNITPREPARECOURSE:
                self.genernate_preparecourse_content(webpart)              
            else:
                #自写内容，无需处理
                cache_key = "unit" + str(self.unit.unitId) + "_" + str(webpart.getUnitWebpartId())
                content = cache.get(cache_key)
                if content != None:
                    request.setAttribute(cache_key, content)
                else:
                    map = HashMap()
                    map.put("unit", self.unit)
                    map.put("UnitRootUrl", self.unitRootUrl)
                    map.put("webpart", webpart)
                    content = self.templateProcessor.processTemplate(map, "/WEB-INF/unitspage/" + self.templateName + "/custorm.ftl", "utf-8")
                    request.setAttribute(cache_key, content)
                    cache.put(cache_key, content)
        
        theme = self.params.safeGetStringParam("theme")
        request.setAttribute("head_nav", "unit")
        request.setAttribute("unit", self.unit)
        request.setAttribute("webpartList", webpartList)
        if theme != "":
            request.setAttribute("theme", theme)
        
        if self.loginUser != None:
            request.setAttribute("loginUser", self.loginUser)
            
            if self.isUnitAdmin() == True and preview != "":
                request.setAttribute("role", "admin")
        
        request.setAttribute("req", request)
        return "/WEB-INF/unitspage/" + self.templateName + "/index.ftl"
    
    def set_webpart_flag(self, webpart):
        if webpart.webpartZone == UnitWebpart.WEBPART_TOP:
            request.setAttribute("hasTopWebpart", "1")
        elif webpart.webpartZone == UnitWebpart.WEBPART_BOTTOM:
            request.setAttribute("hasBottomWebpart", "1")
        elif webpart.webpartZone == UnitWebpart.WEBPART_LEFT:
            request.setAttribute("hasLeftWebpart", "1")
        elif webpart.webpartZone == UnitWebpart.WEBPART_MIDDLE:
            request.setAttribute("hasMiddleWebpart", "1")
        elif webpart.webpartZone == UnitWebpart.WEBPART_RIGHT:
            request.setAttribute("hasRightWebpart", "1")
    
    def genernate_article_content(self, webpart):
        cache_key = "unit" + str(self.unit.unitId) + "_" + str(webpart.getUnitWebpartId())
        content = cache.get(cache_key)
        #content = None
        if content != None:
            request.setAttribute(cache_key, content)
            return        
        
        map = HashMap()
        qry = ArticleQuery(""" a.articleId, a.title, a.createDate,a.typeState, u.userId, u.userIcon, u.nickName, u.loginName """)
        qry.custormAndWhereClause = "a.approvedPathInfo Like '%/" + str(self.unit.unitId) + "/%' "
                            
        newest_article_list = qry.query_map(10)
        map.put("newest_article_list", newest_article_list)
        
        #hot_article_list=self.viewcount_svc.getViewCountListShared(3,7,10,self.unit.unitPath,self.unit.unitDepth);
        #map.put("hot_article_list",hot_article_list)
        
        qry = ArticleQuery(""" a.articleId, a.title, a.createDate,a.typeState, u.userId, u.userIcon, u.nickName, u.loginName """)
        qry.custormAndWhereClause = " a.approvedPathInfo Like '%/" + str(self.unit.unitId) + "/%' "
        qry.orderType = 2
        hot_article_list = qry.query_map(10)
        map.put("hot_article_list", hot_article_list)
        
        qry = ArticleQuery(""" a.articleId, a.title, a.createDate, a.articleAbstract, a.articleContent, a.typeState, u.userId, u.userIcon, u.nickName, u.loginName """)
        #qry.rcmdState = True
        qry.custormAndWhereClause = " a.approvedPathInfo Like '%/" + str(self.unit.unitId) + "/%' and a.rcmdPathInfo Like '%/" + str(self.unit.unitId) + "/%' "
        rcmd_article_list = qry.query_map(10)
        map.put("rcmd_article_list", rcmd_article_list)        
        
        map.put("unit", self.unit)
        map.put("webpart", webpart)
        map.put("UnitRootUrl", self.unitRootUrl)
        content = self.templateProcessor.processTemplate(map, "/WEB-INF/unitspage/" + self.templateName + "/article.ftl", "utf-8")
        request.setAttribute(cache_key, content)
        cache.put(cache_key, content)
        
    def genernate_resoure_content(self, webpart):
        cache_key = "unit" + str(self.unit.unitId) + "_" + str(webpart.getUnitWebpartId())
        content = cache.get(cache_key)
        if content != None:
            request.setAttribute(cache_key, content)
            return            
        
        map = HashMap()        
        #最新资源
        qry = ResourceQuery(""" r.resourceId, r.title, r.href, r.createDate, r.fsize, r.downloadCount, 
            r.userId, r.subjectId as subjectId, grad.gradeName, sc.name as scName """)
        #qry.unitId = self.unit.unitId
        qry.custormAndWhereClause = " r.approvedPathInfo Like '%/" + str(self.unit.unitId) + "/%' "
        new_resource_list = qry.query_map(10)
        map.put("new_resource_list", new_resource_list)
        
        #本周热门资源
        qry = ResourceQuery(""" r.resourceId, r.title, r.href, r.createDate, r.fsize, r.downloadCount, 
            r.userId, msubj.msubjName, grad.gradeName, sc.name as scName """)
        qry.orderType = 4       # downloadCount DESC
        #qry.unitId = self.unit.unitId
        qry.custormAndWhereClause = " r.approvedPathInfo Like '%/" + str(self.unit.unitId) + "/%' "
        hot_resource_list = qry.query_map(10)
        map.put("hot_resource_list", hot_resource_list)
        
        #hot_resource_list = self.viewcount_svc.getViewCountListShared(12,7,10,self.unit.unitPath,self.unit.unitDepth);
        #map.put("hot_resource_list", hot_resource_list)
        
        # 推荐资源
        qry = ResourceQuery(""" r.resourceId, r.title, r.href, r.createDate, r.fsize, r.downloadCount, 
            r.userId,msubj.msubjName, grad.gradeName, sc.name as scName """)
        #qry.rcmdState = True
        #qry.unitId = self.unit.unitId
        qry.custormAndWhereClause = " r.approvedPathInfo Like '%/" + str(self.unit.unitId) + "/%' And r.rcmdPathInfo Like '%/" + str(self.unit.unitId) + "/%' "
        rcmd_resource_list = qry.query_map(10)
        map.put("rcmd_resource_list", rcmd_resource_list)    
       
        map.put("unit", self.unit)
        map.put("webpart", webpart)
        map.put("UnitRootUrl", self.unitRootUrl)
        content = self.templateProcessor.processTemplate(map, "/WEB-INF/unitspage/" + self.templateName + "/resource.ftl", "utf-8")
        request.setAttribute(cache_key, content)
        cache.put(cache_key, content)
    
    def genernate_photo_content(self, webpart):
        cache_key = "unit" + str(self.unit.unitId) + "_" + str(webpart.getUnitWebpartId())
        content = cache.get(cache_key)
        if content != None:
            request.setAttribute(cache_key, content)
            return
        
        map = HashMap()
        qry = PhotoQuery(" p.photoId, p.title, p.createDate, p.href, u.userId, u.loginName, u.nickName, p.summary ")
        qry.unitId = self.unit.unitId
        photo_list = qry.query_map(6)
        map.put("photo_list", photo_list)
        map.put("unit", self.unit)
        map.put("webpart", webpart)
        map.put("UnitRootUrl", self.unitRootUrl)
        map.put("UserUrlPattern", request.getAttribute("UserUrlPattern"))        
        content = self.templateProcessor.processTemplate(map, "/WEB-INF/unitspage/" + self.templateName + "/photo.ftl", "utf-8")
        request.setAttribute(cache_key, content)
        cache.put(cache_key, content)

    def genernate_video_content(self, webpart):
        cache_key = "unit" + str(self.unit.unitId) + "_" + str(webpart.getUnitWebpartId())
        content = cache.get(cache_key)
        if content != None:
            request.setAttribute(cache_key, content)
            return
        
        map = HashMap()
        qry = VideoQuery(""" v.videoId, v.title, v.createDate, v.href, v.flvHref, v.userId, u.loginName, u.nickName, v.summary,v.flvThumbNailHref """)
        qry.orderType = VideoQuery.ORDER_TYPE_VIDEOID_DESC
        qry.unitId = self.unit.unitId
        new_video_list = qry.query_map(4)
        map.put("new_video_list", new_video_list)
        map.put("unit", self.unit)
        map.put("webpart", webpart)
        map.put("UnitRootUrl", self.unitRootUrl)
        content = self.templateProcessor.processTemplate(map, "/WEB-INF/unitspage/" + self.templateName + "/video.ftl", "utf-8")
        request.setAttribute(cache_key, content)
        cache.put(cache_key, content)    
                
    def genernate_picnews_content(self, webpart):
        cache_key = "unit" + str(self.unit.unitId) + "_" + str(webpart.getUnitWebpartId())
        content = cache.get(cache_key)
        if content != None:
            request.setAttribute(cache_key, content)
            return
        
        map = HashMap()
        qry = UnitNewsNoticeQuery(""" un.unitNewsId, un.title, un.picture, un.createDate""")
        qry.unitId = self.unit.unitId
        qry.itemType = 0
        pic_news = qry.query_map(6)
    
        map.put("pic_news", pic_news)
        map.put("unit", self.unit)
        map.put("webpart", webpart)
        map.put("UnitRootUrl", self.unitRootUrl)
        content = self.templateProcessor.processTemplate(map, "/WEB-INF/unitspage/" + self.templateName + "/pic_news.ftl", "utf-8")
        request.setAttribute(cache_key, content)
        cache.put(cache_key, content)
        
    def genernate_newestnews_content(self, webpart):
        cache_key = "unit" + str(self.unit.unitId) + "_" + str(webpart.getUnitWebpartId())
        content = cache.get(cache_key)
        if content != None:
            request.setAttribute(cache_key, content)
            return
        
        map = HashMap()
        qry = UnitNewsNoticeQuery(""" un.unitNewsId, un.title, un.createDate""")
        qry.unitId = self.unit.unitId
        qry.itemType = 2
        newest_news = qry.query_map(6)
        
        map.put("newest_news", newest_news)
        map.put("unit", self.unit)
        map.put("webpart", webpart)
        map.put("UnitRootUrl", self.unitRootUrl)
        content = self.templateProcessor.processTemplate(map, "/WEB-INF/unitspage/" + self.templateName + "/newest_news.ftl", "utf-8")
        request.setAttribute(cache_key, content)
        cache.put(cache_key, content)
        
    def genernate_unitnotice_content(self, webpart):
        cache_key = "unit" + str(self.unit.unitId) + "_" + str(webpart.getUnitWebpartId())
        content = cache.get(cache_key)
        if content != None:
            request.setAttribute(cache_key, content)
            return
        
        map = HashMap()
        qry = UnitNewsNoticeQuery(""" un.unitNewsId, un.title, un.createDate""")
        qry.unitId = self.unit.unitId
        qry.itemType = 1
        unit_notice = qry.query_map(6)
        
        map.put("unit_notice", unit_notice)
        map.put("unit", self.unit)
        map.put("webpart", webpart)
        map.put("UnitRootUrl", self.unitRootUrl)
        content = self.templateProcessor.processTemplate(map, "/WEB-INF/unitspage/" + self.templateName + "/newest_notice.ftl", "utf-8")
        request.setAttribute(cache_key, content)
        cache.put(cache_key, content)
        
    def genernate_links_content(self, webpart):
        cache_key = "unit" + str(self.unit.unitId) + "_" + str(webpart.getUnitWebpartId())
        content = cache.get(cache_key)
        if content != None:
            request.setAttribute(cache_key, content)
            return
        
        map = HashMap()
        links = self.unitService.getUnitLinksByUnitId(self.unit.unitId)
        if links != None:
            map.put("links", links)
        map.put("unit", self.unit)
        map.put("UnitRootUrl", self.unitRootUrl)
        map.put("webpart", webpart)
        content = self.templateProcessor.processTemplate(map, "/WEB-INF/unitspage/" + self.templateName + "/unit_links.ftl", "utf-8")
        request.setAttribute(cache_key, content)
        cache.put(cache_key, content)
        
    def genernate_statistics_content(self, webpart):
        cache_key = "unit" + str(self.unit.unitId) + "_" + str(webpart.getUnitWebpartId())
        content = cache.get(cache_key)
        if content != None:
            request.setAttribute(cache_key, content)
            return
        map = HashMap()
        map.put("unit", self.unit)
        map.put("UnitRootUrl", self.unitRootUrl)
        map.put("webpart", webpart)
        # 查询下级机构数据
        map.put("unitAllUserCount", self.unitService.getAllUserCount(self.unit))
        map.put("unitAllArticleCount", self.unitService.getAllArticleCount(self.unit))
        map.put("unitAllResourceCount", self.unitService.getAllResourceCount(self.unit))
        map.put("unitAllPhotoCount", self.unitService.getAllPhotoCount(self.unit))
        map.put("unitAllVideoCount", self.unitService.getAllVideoCount(self.unit))
        
        content = self.templateProcessor.processTemplate(map, "/WEB-INF/unitspage/" + self.templateName + "/unit_count.ftl", "utf-8")
        request.setAttribute(cache_key, content)
        cache.put(cache_key, content)
        
    def genernate_vote_content(self, webpart):
        cache_key = "unit" + str(self.unit.unitId) + "_" + str(webpart.getUnitWebpartId())
        content = cache.get(cache_key)
        if content != None:
            request.setAttribute(cache_key, content)
            return
        
        map = HashMap()
        map.put("unit", self.unit)
        map.put("webpart", webpart)
        map.put("UnitRootUrl", self.unitRootUrl)
        content = self.templateProcessor.processTemplate(map, "/WEB-INF/unitspage/" + self.templateName + "/unit_vote.ftl", "utf-8")
        request.setAttribute(cache_key, content)
        cache.put(cache_key, content)
        
    def genernate_group_content(self, webpart):
        cache_key = "unit" + str(self.unit.unitId) + "_" + str(webpart.getUnitWebpartId())
        content = cache.get(cache_key)
        if content != None:
            request.setAttribute(cache_key, content)
            return
        
        qry = UnitGroupQuery("ug.groupName,ug.groupTitle")
        qry.unitId = self.unit.unitId
        unitGroupList = qry.query_map(10)
        map = HashMap()
        map.put("unit", self.unit)
        map.put("webpart", webpart)
        map.put("UnitRootUrl", self.unitRootUrl)
        map.put("unitGroupList", unitGroupList)
        
        content = self.templateProcessor.processTemplate(map, "/WEB-INF/unitspage/" + self.templateName + "/unit_group.ftl", "utf-8")
        request.setAttribute(cache_key, content)
        cache.put(cache_key, content)
        
    def genernate_preparecourse_content(self, webpart):
        cache_key = "unit" + str(self.unit.unitId) + "_" + str(webpart.getUnitWebpartId())
        content = cache.get(cache_key)
        if content != None:
            request.setAttribute(cache_key, content)
            return
        qry = UnitPrepareCourseQuery("pc.prepareCourseId, pc.title, pc.startDate, pc.endDate, u.userId")
        qry.unitId = self.unit.unitId
        unitPrepareCourseList = qry.query_map(10)
        map = HashMap()
        map.put("unit", self.unit)
        map.put("webpart", webpart)
        map.put("UnitRootUrl", self.unitRootUrl)
        map.put("unitPrepareCourseList", unitPrepareCourseList)
        content = self.templateProcessor.processTemplate(map, "/WEB-INF/unitspage/" + self.templateName + "/unit_preparecourse.ftl", "utf-8")
        request.setAttribute(cache_key, content)
        cache.put(cache_key, content)
    
    def genernate_unitsubject_content(self, webpart):
        #print "-================"
        cache_key = "unit" + str(self.unit.unitId) + "_" + str(webpart.getUnitWebpartId())
        content = cache.get(cache_key)
        if content != None:
            request.setAttribute(cache_key, content)
            return
        
        unitsubjectlist = self.unitService.getSubjectByUnitId(self.unit.unitId)
        map = HashMap()        
        map.put("unit", self.unit)
        map.put("webpart", webpart)
        map.put("UnitRootUrl", self.unitRootUrl)
        map.put("unitsubjectlist", unitsubjectlist)
        content = self.templateProcessor.processTemplate(map, "/WEB-INF/unitspage/" + self.templateName + "/subject.ftl", "utf-8")
        request.setAttribute(cache_key, content)
        cache.put(cache_key, content)
    
    def getCurrentSiteUrl(self, request):
        root = request.getScheme() + "://" + request.getServerName()
        if request.getServerPort() != 80:
            root = root + ":" + str(request.getServerPort())
        root = root + request.getContextPath() + "/"
        return root
    
    def getUnitRootUrl(self):
        contextPath = request.getContextPath()
        if contextPath != "/":
            contextPath = contextPath + "/"
        unitUrlPattern = request.getAttribute("UnitUrlPattern")
        if unitUrlPattern != None and unitUrlPattern != "":
            unitUrlPattern = unitUrlPattern.replace("{unitName}", self.unit.unitName)
            return unitUrlPattern
        else:
            return contextPath + "d/" + self.unit.unitName + "/"
    
    def genWebparts(self):
        unit_list = self.unitService.getAllUnitOrChildUnitList(None,[False])
        for unit in unit_list:
            unit_webpart_list = self.unitService.getUnitWebpartList(unit.unitId)
            if len(unit_webpart_list) == 0:                
                moduleName = [u"机构文章", u"机构图片", u"机构资源", u"机构视频", u"图片新闻",u"机构协作组", u"最新动态", u"最新公告", u"统计信息", u"调查投票", u"机构学科",u"机构集备", u"友情链接"] 
                i = 0
                for m in moduleName:
                    i = i + 1
                    unitWebpart = UnitWebpart()
                    unitWebpart.setModuleName(m)
                    unitWebpart.setDisplayName(m)
                    unitWebpart.setRowIndex(i)
                    unitWebpart.setVisible(True)
                    unitWebpart.setSystemModule(True)
                    unitWebpart.setUnitId(unit.getUnitId())
                    if m == u"机构文章" or  m == u"机构资源" or m == u"机构视频" or m == u"机构集备":
                       unitWebpart.setWebpartZone(4)
                    elif m == u"图片新闻" or m == u"友情链接" or m == u"机构协作组":
                        unitWebpart.setWebpartZone(3)
                    elif m == "图片模块":
                        unitWebpart.setWebpartZone(2)
                    elif m == "机构学科":
                        unitWebpart.setWebpartZone(1)
                    else:
                        unitWebpart.setWebpartZone(5)
                    self.unitService.saveOrUpdateUnitWebpart(unitWebpart)
