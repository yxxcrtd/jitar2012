# coding=utf-8
from java.lang import *
from java.text import SimpleDateFormat
from java.util import Date, ArrayList, HashMap
from java.io import File, FileOutputStream, OutputStreamWriter
from cn.edustar.jitar.util import CommonUtil, ParamUtil, IPSeeker, ProductInfo, EncryptDecrypt
from cn.edustar.jitar.data import Command
from cn.edustar.jitar.service import ViewCountService
from cn.edustar.jitar.pojos import User, TimerCount
from cn.edustar.jitar.jython import JythonBaseAction
from cn.edustar.jitar.model import Configure, SiteThemeUrlModel
from site_news_query import SiteNewsQuery
from article_query import ArticleQuery, UserArticleQuery
from resource_query import ResourceQuery
from comment_query import CommentQuery
from action_query import ActionQuery
from user_query import UserQuery
from group_query import GroupQuery
from base_specialsubject_page import SpecialSubjectQuery
from bbs_query import TopicQuery
from photo_query import PhotoQuery
from video_query import VideoQuery
from tag_query import TagQuery
from base_preparecourse_page import *
from base_specialsubject_page import *
from placard_query import PlacardQuery
from net.zdsoft.passport.service.client import PassportClient
from org.jasig.cas.client.validation import AssertionImpl
from org.jasig.cas.client.util import CommonUtils

# 配置: 是否使用缓存
ENABLE_CACHE = True
if ENABLE_CACHE:
    cache = __jitar__.cacheProvider.getCache('main')
else:
    cache = NoCache()
    
class index(JythonBaseAction):    
    cfg_svc = __jitar__.configService

    def __init__(self):
        self.pro_svc = __spring__.getBean("ProductConfigService")
        self.viewcount_svc = __spring__.getBean("viewCountService")
        self.art_svc = __spring__.getBean("articleService")
        self.params = ParamUtil(request)
        self.unitService = __spring__.getBean("unitService")
    	self.userService = __spring__.getBean("userService")
        
    def execute(self):
        response.sendRedirect("index.action")
        return
        self.config = self.cfg_svc.getConfigure()
        autoHtml = self.config.getBoolValue(Configure.SITE_AUTO_HTML, True)
        if autoHtml == True:
            self.genIndexFile()
        else:
            # 学科导航可能需要重新加载，每次都动态生成
            cache = NoCache()
            self.get_subject_nav()
        # 以下代码在 preview 或者 debug的情况下运行
        cache = NoCache()
        rootUnit = self.unitService.getRootUnit()
        if rootUnit == None:
            response.sendRedirect("add_root_unit.py")
            return
        self.unit = rootUnit        
        self.isTest = self.getProductInfo()
        if self.isTest == False or self.isTest == "False" or self.isTest == "false" :
            request.setAttribute("errMessage", u"无效的许可证。")
            return "/WEB-INF/ftl/site_err.ftl"
            
        # 是否是浙大统一用户       
        if request.getServletContext().getServletRegistration("passportClientInit") != None:
            self.passportURL = PassportClient.getInstance().getPassportURL()
            if self.passportURL == None:
                self.passportURL = ""
            if self.passportURL == "http://":
                self.passportURL = ""
            if self.passportURL != "" :
                if self.passportURL[len(self.passportURL) - 1] != "/":
                    self.passportURL += "/"
            self.passportServerId = PassportClient.getInstance().getServerId();
            self.passportVerifyKey = PassportClient.getInstance().getVerifyKey();
            request.setAttribute("passportURL", self.passportURL)
            request.setAttribute("passportServerId", self.passportServerId)
            request.setAttribute("passportVerifyKey", self.passportVerifyKey)        
        # 判断是否是东师理想的单点登录
        dsssoserverLogin = ""
        dsssoserver = ""
        if request.getServletContext().getFilterRegistration("filterchainproxy") != None:
            if request.getServletContext().getFilterRegistration("filterchainproxy").getClassName() == "dsidealsso.FilterChainProxy":
                dsssoserverLogin = request.getServletContext().getFilterRegistration("filterchainproxy").getInitParameter("casServerLoginUrl")
                dsssoserver = request.getServletContext().getFilterRegistration("filterchainproxy").getInitParameter("casServerUrlPrefix")
                request.setAttribute("dsssoserverLogin", dsssoserverLogin)
                request.setAttribute("dsssoserver", dsssoserver)        
        # 页面导航高亮为 'index'
        request.setAttribute("head_nav", "index")
        # 教研动态新闻, 图片新闻.
        self.get_pic_news()
        self.get_jitar_news()     

        # 最新文章, 热门, 推荐, 名师文章, 文章评论.
        self.get_newest_article_list()
        self.get_hot_article_list()
        self.get_cmt_article_list()
        self.get_rcmd_article_list()
        self.get_famous_article_list()
        self.get_digg_article_list()
        self.get_trample_article_list()
        self.get_starcount_article_list()
        self.get_new_comment_list()
        
        # 最新, 热门, 推荐资源.
        self.get_new_resource_list()
        self.get_hot_resource_list()
        self.get_rcmd_resource_list()

        # 名师, 学科带头人, 研修之星, 优秀团队.
        self.get_famous_teacher()
        self.get_expert_list()
        self.teacher_star()
        self.best_group()
        self.get_comissioner_list()
        
        # 最新, 热门, 推荐工作室.
        self.get_new_wr_list()
        self.get_hot_wr_list()
        self.get_rcmd_wr_list()
        self.get_score_wr_list()
        
        # 最新, 热门, 推荐协作组, 协作组最新主题.
        self.get_new_group_list()
        self.get_hot_group_list()
        self.get_rcmd_group_list()
        self.get_new_topic_list()
        
        # 最新图片.
        self.get_new_photo_list()
        self.get_hot_hot_list()        
        self.get_new_video_list()
        self.get_hot_video_list()        
        self.get_new_preparecourse()        
        self.get_special_subject()        
        pf = request.getAttribute("platformType")
        if pf != None and pf == "1":
            self.show_platform()
            
        # ======= 右侧各个栏目 =======
        # 公告栏.
        self.get_jitar_placard()
        
        # 活动
        self.get_jitar_actions()
        
        # 机构风采, 教师风采.
        self.school_link()
        self.tearcher_show()
        
        # 热点标签, 站点统计.
        self.get_hot_tags()
        self.get_site_stat()
        
        # 自定义内容
        self.show_custorm_part()
        
        # 根据后台配置，决定是否显示'注册的超连接'
        is_show_reg_link = self.config.getBoolValue(Configure.USER_REGISTER_ENABLED, True)
        if is_show_reg_link :
            is_show_reg_link = "true"
        else:
            is_show_reg_link = "false"
        request.setAttribute("is_show_reg_link", is_show_reg_link)
        
        # 根据后台配置，决定是否显示'注册的超连接'
        is_show_verifyCode = self.config.getBoolValue(Configure.SITE_VERIFYCODE_ENABLED, True)
        if is_show_verifyCode :
            is_show_verifyCode = "true"
        else:
            is_show_verifyCode = "false"
        request.setAttribute("isshowverifyCode", is_show_verifyCode)
        
        preview = self.params.safeGetStringParam("preview")
        if preview != "":
            request.setAttribute("preview_theme_url", preview)
        # self.getEncryptLogin()
        
        # 返回并显示首页
        response.contentType = "text/html; charset=UTF-8"
        
        b = self.params.safeGetStringParam("b")
        if b == "1":
            # 从统一用户转过来的页面
            # 判断登陆用户是否转到学科页面
            if CommonUtil.getLoginToSubjectPage(request) == "1":
                if self.loginUser != None:
                    subjectId = self.loginUser.subjectId
                    if subjectId != None : 
                        if subjectId != 0:
                            gradeId = self.loginUser.gradeId
                            if gradeId != None:
                                response.sendRedirect("subject.py?subjectId=" + str(subjectId) + "&gradeId=" + str(gradeId))
                                return
        self.isSystemManage()
        genUser = session.getAttribute(User.SESSION_LOGIN_GENUSER_KEY)
        if genUser != None:
            ip = IPSeeker.getInstance()
            request.setAttribute("lastLoginAddress", ip.getAddress(genUser.getLastLoginIp()))            
        request.setAttribute("unit", self.unit)
        
        casServerUrl = ""
        if request.getServletContext().getFilterRegistration("CAS-Authentication-Filter") != None:
            casServerUrl = request.getServletContext().getFilterRegistration("CAS-Authentication-Filter").getInitParameter("casServerLoginUrl")
            if casServerUrl == "" :
               casServerUrl = CommonUtils.getCasServerUrlPrefix(request)
            else:    
                if casServerUrl[-1] == '/':
                   casServerUrl = casServerUrl[:len(casServerUrl) - 1]
                if casServerUrl[-6:] == '/login':
                   casServerUrl = casServerUrl[:-6]        
        request.setAttribute("casServerUrl", casServerUrl)        
        return "/WEB-INF/ftl2/site_index.ftl"

    # Test
    def getProductInfo(self):
        p = self.pro_svc.isValid()
        errMessage = self.pro_svc.getErrMessage()
        request.setAttribute("errMessage", errMessage)
        productName = self.pro_svc.getProductName()
        productdays = self.pro_svc.getDays()
        remainDays = self.pro_svc.getRemainDays()
        guid = self.pro_svc.getProductGuid()
        request.setAttribute("guid", guid)        
        if productdays == "0":
            productName = productName
        elif productdays == "-1":
            productName = productName
        else:
            productName = productName + u"    还可以继续试用" + remainDays + u"天"
            
        request.setAttribute("productName", productName)
        request.setAttribute("p", p)
        return p
    
    #commssioner_list 这里是新加入的方法在首页获取教研员列表
    def get_comissioner_list(self):
        userQuery = UserQuery(
                "u.loginName, u.trueName, u.userIcon, u.blogName, u.createDate, u.myArticleCount, u.otherArticleCount, u.resourceCount, u.blogIntroduce, u.articleCount")
        userQuery.userTypeId = 4
        userQuery.userStatus = 0
        userQuery.orderType = 100
        request.setAttribute("comissioner_list", userQuery.query_map(9))
    
    # 教研动态新闻.
    def get_jitar_news(self):
        cache_key = 'index_jitar_news'
        jitar_news = cache.get(cache_key)  # 从缓存中查找.
        if jitar_news == None:
            qry = SiteNewsQuery(" snews.newsId, snews.title, snews.createDate ")
            qry.subjectId = 0
            qry.orderType = 0
            qry.hasPicture = None
            jitar_news = qry.query_map(15)
            
            qry = SiteNewsQuery("snews.newsId")
            qry.hasPicture = True
            qry.subjectId = 0
            qry.orderType = 0
            pic_news = qry.query_map(5)
            str_picid = "_"
            if pic_news != None:                
                for p in pic_news:
                    str_picid += str(p["newsId"]) + "_"
            i = 0
            jitar_news2 = ArrayList()
            if jitar_news != None:                
                for news in jitar_news:
                    if str_picid.find("_" + str(news["newsId"]) + "_") == -1:
                        jitar_news2.add(news)                    
                        i += 1
                        if i > 8:
                            break
        request.setAttribute("jitar_news" , jitar_news2)
    
    # 带图片的新闻.
    def get_pic_news(self):
        cache_key = 'index_pic_news'
        pic_news = cache.get(cache_key)
        if pic_news == None:
            qry = SiteNewsQuery(""" snews.newsId, snews.title, snews.picture """)
            qry.hasPicture = True
            qry.subjectId = 0
            pic_news = qry.query_map(5)
            if pic_news == None:return
            for p in pic_news:
                p_url = p["picture"]
                p_url_path = p_url[0: p_url.rfind("/") + 1]
                p_url_fileName = p_url[p_url.rfind("/") + 1:p_url.rfind(".")]
                p_url_fileExt = p_url[p_url.rfind("."):]
                p_url_fileName = CommonUtil.escape(CommonUtil.urlGB2312Encode(p_url_fileName))
                p["picture"] = p_url_path + p_url_fileName + p_url_fileExt
            cache.put(cache_key, pic_news)
            
        request.setAttribute("pic_news", pic_news)
        # print "pic_news = ", pic_news        
        
    # 活动
    def get_jitar_actions(self):
        cache_key = "jitar_actions"
        jitar_actions = cache.get(cache_key)
        if jitar_actions == None:
            qry = ActionQuery(""" act.title, act.createDate, act.actionId, act.ownerId, act.ownerType, act.createUserId, act.actionType,
                           act.description, act.userLimit, act.startDateTime,act.finishDateTime, act.attendLimitDateTime, act.place,
                           act.status, act.visibility, act.attendCount,u.loginName,u.trueName
                           """)
            jitar_actions = qry.query_map(8)
            cache.put(cache_key, jitar_actions)
        
        request.setAttribute("jitar_actions", jitar_actions)        

    # 最新文章.
    def get_newest_article_list(self):
        cache_key = "newest_article_list"
        newest_article_list = cache.get(cache_key)
        if newest_article_list == None:
            qry = ArticleQuery(""" a.articleId, a.title, a.createDate, a.typeState,u.userId, u.userIcon, u.trueName, u.loginName """)
            # qry.unit = self.unit
            qry.custormAndWhereClause = "a.approvedPathInfo Like '%/" + str(self.unit.unitId) + "/%' "
            newest_article_list = qry.query_map(7)
            cache.put(cache_key, newest_article_list)
            
        request.setAttribute("newest_article_list", newest_article_list)

    # 热门文章, orderType=7 按照点击数.
    def get_hot_article_list(self):
        # 按照周点击数.days=7
        cache_key = "hot_article_list"
        hot_article_list = cache.get(cache_key)
        if hot_article_list == None:
            hot_article_list = self.art_svc.getWeekViewCountArticleList()
            cache.put(cache_key, hot_article_list)
        request.setAttribute("hot_article_list", hot_article_list)

    # 推荐文章.
    def get_rcmd_article_list(self):
        cache_key = "rcmd_article_list"
        rcmd_article_list = cache.get(cache_key)
        if rcmd_article_list == None:
            qry = ArticleQuery(""" a.articleId, a.title, a.createDate, a.typeState, u.userId, u.userIcon, u.trueName, u.loginName """)
            qry.custormAndWhereClause = "a.approvedPathInfo Like '%/" + str(self.unit.unitId) + "/%' And a.rcmdPathInfo Like '%/" + str(self.unit.unitId) + "/%' "
            rcmd_article_list = qry.query_map(11)
            cache.put(cache_key, rcmd_article_list)
        
        request.setAttribute("rcmd_article_list" , rcmd_article_list)

    # 名师文章.
    def get_famous_article_list(self):
        cache_key = "famous_article_list"
        famous_article_list = cache.get(cache_key)
        if famous_article_list == None:
            qry = UserArticleQuery(""" a.articleId, a.title, a.createDate,a.typeState, u.userId, u.userIcon, u.trueName, u.loginName """)
            qry.userIsFamous = True
            qry.custormAndWhereClause = "a.approvedPathInfo Like '%/" + str(self.unit.unitId) + "/%' "
            famous_article_list = qry.query_map(7)
            cache.put(cache_key, famous_article_list)
            
        request.setAttribute("famous_article_list" , famous_article_list)

    # 按顶排序文章.
    def get_digg_article_list(self):
        cache_key = "digg_article_list"
        digg_article_list = cache.get(cache_key)
        if digg_article_list == None:
            qry = ArticleQuery(""" a.articleId, a.title, a.createDate,a.digg, a.typeState, u.userId, u.userIcon, u.trueName, u.loginName """)
            # qry.unit = self.unit
            qry.custormAndWhereClause = "a.approvedPathInfo Like '%/" + str(self.unit.unitId) + "/%' "
            qry.orderType = 4
            digg_article_list = qry.query_map(11)
            cache.put(cache_key, digg_article_list)
            
        request.setAttribute("digg_article_list" , digg_article_list)

    # 按踩排序文章.
    def get_trample_article_list(self):
        cache_key = "trample_article_list"
        trample_article_list = cache.get(cache_key)
        if trample_article_list == None:
            qry = ArticleQuery(""" a.articleId, a.title, a.createDate, a.trample, a.typeState, u.userId, u.userIcon, u.trueName, u.loginName """)
            # qry.unit = self.unit
            qry.custormAndWhereClause = "a.approvedPathInfo Like '%/" + str(self.unit.unitId) + "/%' "
            qry.orderType = 5
            trample_article_list = qry.query_map(11)
            cache.put(cache_key, trample_article_list)
            
        request.setAttribute("trample_article_list" , trample_article_list)
        
    # 按星排序文章. 
    def get_starcount_article_list(self):
        cache_key = "starcount_article_list"
        starcount_article_list = cache.get(cache_key)
        if starcount_article_list == None:
            qry = ArticleQuery(""" a.articleId, a.title, a.createDate, a.starCount, a.typeState, a.commentCount, u.userId, u.userIcon, u.trueName, u.loginName """)
            # qry.unit = self.unit
            qry.custormAndWhereClause = "a.approvedPathInfo Like '%/" + str(self.unit.unitId) + "/%' "
            qry.orderType = 6
            starcount_article_list = qry.query_map(11)
            cache.put(cache_key, starcount_article_list)
            
        request.setAttribute("starcount_article_list" , starcount_article_list)
        # print "starcount_article_list = ",starcount_article_list

    # 最新文章评论.
    def get_new_comment_list(self):
        cache_key = "new_comment_list"
        new_comment_list = cache.get(cache_key)
        new_comment_list = None
        if new_comment_list == None:
            qry = CommentQuery(" cmt.id, cmt.objId, cmt.createDate, cmt.userId, cmt.userName, cmt.title ")
            qry.objType = 3
            new_comment_list = qry.query_map(10)
            cache.put(cache_key, new_comment_list)            
        request.setAttribute("new_comment_list", new_comment_list)

    # 文章评论排序.
    def get_cmt_article_list(self):
        cache_key = "cmt_article_list"
        cmt_article_list = cache.get(cache_key)
        # cmt_article_list =None
        if cmt_article_list == None:
            cmt_article_list = self.art_svc.getWeekCommentArticleList();
            cache.put(cache_key, cmt_article_list)
        request.setAttribute("cmt_article_list", cmt_article_list) 

    # 得到最新资源列表.
    def get_new_resource_list(self):
        cache_key = "new_resource_list"
        new_resource_list = cache.get(cache_key)
        if new_resource_list == None:
            qry = ResourceQuery(""" r.userId, r.resourceId, r.title, r.href, r.createDate, r.fsize, r.downloadCount, 
                u.loginName, u.trueName, r.createDate """)
            # qry.unit = self.unit
            qry.unitId = None
            qry.custormAndWhereClause = " r.approvedPathInfo Like '%/" + str(self.unit.unitId) + "/%' "
            new_resource_list = qry.query_map(10)
            cache.put(cache_key, new_resource_list)
        request.setAttribute("new_resource_list", new_resource_list)
        

    # 热门资源列表.
    def get_hot_resource_list(self):
        cache_key = "hot_resource_list"
        hot_resource_list = cache.get(cache_key)
        # hot_resource_list=None
        if hot_resource_list == None:
            qry = ResourceQuery(""" r.userId, r.resourceId, r.title, r.href, r.createDate, r.fsize, r.downloadCount, 
                u.loginName, u.trueName, msubj.msubjName, grad.gradeName, sc.name as scName """)
            # qry.unit = self.unit
            qry.unitId = None
            qry.custormAndWhereClause = " r.approvedPathInfo Like '%/" + str(self.unit.unitId) + "/%' "
            qry.orderType = 4  # downloadCount DESC
            hot_resource_list = qry.query_map(10)
            cache.put(cache_key, hot_resource_list)
        request.setAttribute("hot_resource_list", hot_resource_list)
    
    # 得到推荐资源.
    def get_rcmd_resource_list(self):
        cache_key = "rcmd_resource_list"
        rcmd_resource_list = cache.get(cache_key)
        if rcmd_resource_list == None:    
            qry = ResourceQuery(" r.userId, r.resourceId, r.title, r.href, r.createDate, r.fsize, r.downloadCount, r.subjectId as subjectId, grad.gradeName, sc.name as scName ")
            # qry.unit = self.unit
            qry.unitId = None
            # qry.rcmdState = True
            qry.custormAndWhereClause = " r.rcmdPathInfo Like '%/" + str(self.unit.unitId) + "/%' and r.approvedPathInfo Like '%/" + str(self.unit.unitId) + "/%' "
            rcmd_resource_list = qry.query_map(10)
            cache.put(cache_key, rcmd_resource_list)
        request.setAttribute("rcmd_resource_list", rcmd_resource_list)

    # 专家名师.
    def get_famous_teacher(self):
        cache_key = "famous_teachers"
        famous_teachers = cache.get(cache_key)
        if famous_teachers == None:            
            qry = UserQuery(""" u.loginName, u.userIcon, u.trueName, u.visitCount """)
            qry.userTypeId = 1
            qry.userStatus = 0
            qry.orderType = 100
            famous_teachers = qry.query_map(12)
            cache.put(cache_key, famous_teachers)
        request.setAttribute("famous_teachers", famous_teachers)
    # 学科带头人.
    def get_expert_list(self):
        cache_key = "expert_list"
        expert_list = cache.get(cache_key)
        if expert_list == None:    
            qry = UserQuery(""" u.loginName, u.userIcon, u.blogName, u.trueName, u.visitCount """)
            qry.userTypeId = 3
            qry.userStatus = 0
            expert_list = qry.query_map(12)
            cache.put(cache_key, expert_list)
        request.setAttribute("expert_list", expert_list)

    # 获取当前第一个研修之星.
    def teacher_star(self):
        cache_key = "teacher_star"
        star = cache.get(cache_key)
        if star == None:
            qry = UserQuery(""" u.userId, u.loginName, u.trueName, u.userIcon, u.blogName, u.blogIntroduce """)
            qry.userTypeId = 5  # 1- 教师风采; 2- 研修之星.
            qry.orderType = 100  # 按随机来排序
            # star = qry.first_map()
            star = qry.query_map(12)
            cache.put(cache_key, star)
            
        request.setAttribute("teacher_star", star)

    # 优秀团队.
    def best_group(self):
        qry = GroupQuery("""g.groupId, g.groupName, g.groupTitle, g.groupIcon """)
        qry.isBestGroup = True
        best_group = qry.query_map(-1)
        count = best_group.size()
        if count > 4 :
            s = []
            iLoop = 0
            ff = []
            while iLoop < 4:
                iLoop = iLoop + 1
                rnd = Math.random() * best_group.size()
                index = int(rnd)
                while index in s:
                    rnd = Math.random() * best_group.size()
                    index = int(rnd)
                s.append(index)    
                ff.append(best_group[index])
            best_group = ff
        best_group_list = best_group    
        request.setAttribute("best_group_list", best_group_list)

    # 最新工作室.
    def get_new_wr_list(self):
        cache_key = "new_wr_list"
        new_wr_list = cache.get(cache_key)
        if new_wr_list == None:
            qry = UserQuery(" u.loginName, u.userIcon, u.blogName, u.trueName, u.blogIntroduce ")
            qry.userStatus = 0
            new_wr_list = qry.query_map(7)
            cache.put(cache_key, new_wr_list)
        request.setAttribute("new_wr_list" , new_wr_list)
        
    # 热门工作室.
    def get_hot_wr_list(self):
        cache_key = "hot_wr_list"
        hot_wr_list = cache.get(cache_key)
        if hot_wr_list == None:
            qry = UserQuery(" u.loginName, u.userIcon, u.blogName, u.trueName, u.blogIntroduce ")
            qry.userStatus = 0
            qry.orderType = 1  # visitCount DESC
            hot_wr_list = qry.query_map(6)
            cache.put(cache_key, hot_wr_list)
        request.setAttribute("hot_wr_list", hot_wr_list)
        
    # 积分工作室.
    def get_score_wr_list(self):
        cache_key = "score_wr_list"
        score_wr_list = cache.get(cache_key)
        if score_wr_list == None:
            qry = UserQuery(""" u.loginName, u.userIcon, u.blogName, u.trueName, u.blogIntroduce ,u.commentCount,u.userScore    """)
            qry.orderType = 6
            qry.userStatus = 0
            score_wr_list = qry.query_map(6)
            cache.put(cache_key, score_wr_list)
        request.setAttribute("score_wr_list", score_wr_list)
        
    # 推荐工作室.
    def get_rcmd_wr_list(self):
        cache_key = "rcmd_wr_list"
        rcmd_wr_list = cache.get(cache_key)
        if rcmd_wr_list == None:
            qry = UserQuery(" u.loginName, u.userIcon, u.blogName ")
            qry.userTypeId = 2
            qry.userStatus = 0
            rcmd_wr_list = qry.query_map(6)
            cache.put(cache_key, rcmd_wr_list)
        request.setAttribute("rcmd_wr_list", rcmd_wr_list)


    # 最新协作组.
    def get_new_group_list(self):
        cache_key = "new_group_list"
        new_group_list = cache.get(cache_key)
        if new_group_list == None:
            qry = GroupQuery(" g.groupId, g.groupIcon, g.groupTitle, g.createDate, g.groupIntroduce ")
            new_group_list = qry.query_map(7)
            cache.put(cache_key, new_group_list)
            
        request.setAttribute("new_group_list", new_group_list)
        
        
    # 热门协作组.
    def get_hot_group_list(self):
        cache_key = "hot_group_list"
        hot_group_list = cache.get(cache_key)
        if hot_group_list == None:
            qry = GroupQuery(" g.groupId, g.groupIcon, g.groupTitle, g.createDate, g.groupIntroduce ")
            qry.orderType = GroupQuery.ORDER_BY_VISITCOUNT_DESC  # visitCount DESC
            hot_group_list = qry.query_map(7)
            cache.put(cache_key, hot_group_list)
        request.setAttribute("hot_group_list", hot_group_list)
     
        
    # 推荐协作组.
    def get_rcmd_group_list(self):
        cache_key = "rcmd_group_list"
        rcmd_group_list = cache.get(cache_key)
        if rcmd_group_list == None:
            qry = GroupQuery(" g.groupId, g.groupIcon, g.groupTitle, g.createDate, g.groupIntroduce ")
            qry.isRecommend = True
            rcmd_group_list = qry.query_map(7)
            cache.put(cache_key, rcmd_group_list)
            
        request.setAttribute("rcmd_group_list", rcmd_group_list)
        
        
    # 协作组最新主题.
    def get_new_topic_list(self):
        cache_key = "new_topic_list"
        new_topic_list = cache.get(cache_key)
        if new_topic_list == None:
            qry = TopicQuery(" t.createDate, t.title, t.groupId, t.topicId ")
            qry.orderType = 1
            new_topic_list = qry.query_map(5)
            cache.put(cache_key, new_topic_list)
            
        request.setAttribute("new_topic_list" , new_topic_list)
    
    # 最新图片.
    def get_new_photo_list(self):
        pw = request.getSession().getServletContext().getInitParameter("photo_width")
        ph = request.getSession().getServletContext().getInitParameter("photo_height")
        if pw == None or pw == "" or pw.isdigit() == False : pw = 150
        if ph == None or ph == "" or ph.isdigit() == False : ph = 120
        request.setAttribute("pw", pw)
        request.setAttribute("ph", ph)
        cache_key = "photo_list"
        photo_list = cache.get(cache_key)
        if photo_list == None:
            qry = PhotoQuery(" p.id , p.title, p.createDate, p.href, u.userId, u.loginName, u.trueName, p.summary ")
            qry.orderType = 0  # photoId DESC
            photo_list = qry.query_map(10)
            cache.put(cache_key, photo_list)            
        request.setAttribute("photo_list", photo_list)
        
    #最热图片 现暂时用viewCount排序
    def get_hot_hot_list(self):
        pw = request.getSession().getServletContext().getInitParameter("photo_width")
        ph = request.getSession().getServletContext().getInitParameter("photo_height")
        if pw == None or pw == "" or pw.isdigit() == False : pw = 150
        if ph == None or ph == "" or ph.isdigit() == False : ph = 120
        request.setAttribute("pw", pw)
        request.setAttribute("ph", ph)
        cache_key="hot_photo_list"
        hot_photo_list = cache.get(cache_key)
        if hot_photo_list == None:
            qry = PhotoQuery(" p.id , p.title, p.createDate, p.href, u.userId, u.loginName, u.trueName, p.summary ")
            qry.orderType=2 #viewCount desc
            hot_photo_list = qry.query_map(10)
            cache.put(cache_key,hot_photo_list)
        request.setAttribute("hot_photo_list",hot_photo_list)
        
    # 机构风采.
    def school_link(self):
        cache_key = "school_show"
        school_link = cache.get(cache_key)
        if school_link == None:
            # 机构风采 objectType = 100, objectId = 1.
            # cmd = Command(""" FROM Link WHERE objectType = 100 AND objectId = 1 ORDER BY linkId DESC """)
            cmd = Command(" FROM Unit Where parentId <> 0 ORDER BY rank DESC ")
            school_link = cmd.open(10)
            cache.put(cache_key, school_link)
            
        request.setAttribute("school_link", school_link)
        # DEBUG: print "school_link = ", school_link

    # 教师风采.
    def tearcher_show(self):
        cache_key = "teacher_show"
        teacher_show = cache.get(cache_key)
        if teacher_show == None:                
            qry = UserQuery(" u.userId, u.loginName, u.trueName, u.userIcon ")
            qry.userTypeId = 6
            qry.orderType = 100
            teacher_show = qry.query_map(8)
            cache.put(cache_key, teacher_show)
        request.setAttribute("teacher_show", teacher_show)

    # 公告栏.
    def get_jitar_placard(self):
        cache_key = "site_placard_list"
        site_placard_list = cache.get(cache_key)
        if site_placard_list == None:
            qry = PlacardQuery(" pld.id, pld.title, pld.createDate ")
            qry.objType = 100
            site_placard_list = qry.query_map(4)
            cache.put(cache_key, site_placard_list)
        request.setAttribute("site_placard_list", site_placard_list)

    # 热点标签
    def get_hot_tags(self):
        cache_key = "hot_tags"
        hot_tags = cache.get(cache_key)
        if hot_tags == None:
            qry = TagQuery(" tag.tagId, tag.tagName ")
            qry.orderType = 1
            hot_tags = qry.query_map(40)
            cache.put(cache_key, hot_tags)
            
        request.setAttribute("hot_tags", hot_tags)

    # 站点统计.
    def get_site_stat(self):
        cache_key = "site_stat"
        site_stat = cache.get(cache_key)
        if site_stat == None:
            timerCountService = __spring__.getBean("timerCountService");
            site_stat = timerCountService.getTimerCountById(TimerCount.COUNT_TYPE_SITE)
            cache.put(cache_key, site_stat)            
        request.setAttribute("site_stat", site_stat)

    def get_new_video_list(self):
        cache_key = "video_list"
        new_video_list = cache.get(cache_key)
        if new_video_list == None:
            qry = VideoQuery(""" v.videoId, v.title, v.createDate, v.href, v.flvHref, v.userId, u.loginName, u.trueName, v.flvThumbNailHref """)
            qry.orderType = VideoQuery.ORDER_TYPE_VIDEOID_DESC
            new_video_list = qry.query_map(8)
            cache.put(cache_key, new_video_list)
        request.setAttribute("new_video_list", new_video_list) 
        
    def get_hot_video_list(self):
        cache_key = "video_hot_list"
        hot_video_list = cache.get(cache_key)
        if hot_video_list == None:
            qry = VideoQuery(""" v.videoId, v.title, v.createDate, v.href, v.flvHref, v.userId, u.loginName, u.trueName, v.flvThumbNailHref """)
            qry.orderType = VideoQuery.ORDER_TYPE_VIEWCOUNT_DESC
            hot_video_list = qry.query_map(8)
            cache.put(cache_key, hot_video_list)
        request.setAttribute("hot_video_list", hot_video_list) 
        
    def get_new_preparecourse(self):
        cache_key = "course_list"
        course_list = cache.get(cache_key)
        if course_list == None:
            qry = PrepareCourseQuery(""" pc.createUserId,pc.leaderId,pc.memberCount,pc.articleCount,pc.resourceCount,
                                     pc.status,pc.actionCount,pc.topicCount,pc.topicReplyCount,pc.viewCount,pc.startDate,pc.endDate,
                                     pc.title, pc.prepareCourseId, pc.createDate, pc.metaSubjectId, pc.gradeId,
                                     u.loginName,u.trueName """)
            qry.status = 0
            course_list = qry.query_map(6)
            cache.put(cache_key, course_list)
        request.setAttribute("course_list", course_list)
        
    def get_special_subject(self):
        cache_key = "special_subject_list"
        special_subject_list = cache.get(cache_key)
        if special_subject_list == None:
            qry = SpecialSubjectQuery("ss.specialSubjectId, ss.title, ss.createDate")
            special_subject_list = qry.query_map(6)
            cache.put(cache_key, special_subject_list)
            
        request.setAttribute("special_subject_list", special_subject_list)        

    def getEncryptLogin(self):
        if self.loginUser != None:
            userGuid = self.loginUser.userGuid
            servletContext = request.getSession().getServletContext()
            privateKey = servletContext.getInitParameter("privateKey")
            if privateKey == None or privateKey == "":
                privateKey = "www.chinaedustar.com"

            des = EncryptDecrypt(privateKey)
            userGuid = des.encrypt(userGuid)
            request.setAttribute("encUserGuid", userGuid)

    def show_platform(self):
        cache_key = "platfotm_list"
        platfotm_list = cache.get(cache_key)
        if platfotm_list == None:
            mashupService = __spring__.getBean("mashupService")
            platfotm_list = mashupService.getAllMashupPlatform(True)                        
            cache.put(cache_key, platfotm_list)
        request.setAttribute("platfotm_list", platfotm_list)
    
    def show_custorm_part(self):
        cache_key = "siteIndexPartList"
        siteIndexPartList = cache.get(cache_key)
        if siteIndexPartList == None:
            siteIndexPartService = __spring__.getBean("siteIndexPartService")
            if siteIndexPartService != None:
                siteIndexPartList = siteIndexPartService.getSiteIndexPartList(True)                
                cache.put(cache_key, siteIndexPartList)
        request.setAttribute("siteIndexPartList", siteIndexPartList)
        
    def isSystemManage(self):
        accessControlService = __spring__.getBean("accessControlService")
        if accessControlService.isSystemAdmin(self.loginUser) or accessControlService.isSystemContentAdmin(self.loginUser) or accessControlService.isSystemUserAdmin(self.loginUser):
            request.setAttribute("isSysAdmin", "1")
    
    def genIndexFile(self):
        return
        strFile = request.getServletContext().getRealPath("/")
        strSubjectFile = strFile + "html" + File.separator
        file = File(strSubjectFile)
        if file.exists() == False:
            file.mkdirs()
        # 创建学科导航
        strSubjectFile = strSubjectFile + "subject_nav.html"
        file = File(strSubjectFile)
        if file.exists() == False:
            self.GenSubjectNav(strSubjectFile)
        file = None
                
        if self.params.existParam("preview") == False and self.params.existParam("debug") == False:    
            file = File(strFile + "index.html")
            if file.exists():
                file = None
                response.sendRedirect("index.html")
                return
            else:
                htmlGeneratorService = __spring__.getBean("htmlGeneratorService")
                htmlGeneratorService.SiteIndex()
                
    def GenSubjectNav(self, strPath):
        # 先得到年级
        subjectService = __spring__.getBean("subjectService")      
        mGradeId = subjectService.getGradeIdList()
        MetaGrade = ArrayList()
        metaSubject = ArrayList()
        for grade in mGradeId:
            mGrade = subjectService.getGrade(int(grade))
            MetaGrade.add(mGrade)
            subj = subjectService.getSubjectByGradeId(int(grade))
            m = ArrayList()
            if subj != None:
                for sj in range(0, subj.size()):
                    m.add(subj[sj].metaSubject)
                metaSubject.add({"gradeName" : mGrade.gradeName, "gradeId" : grade, "metaSubject" : m })
        map = HashMap()
        map.put("metaGrade", MetaGrade)
        map.put("meta_Grade", MetaGrade)
        map.put("SubjectNav", metaSubject)
        map.put("SiteUrl", self.get_context_url())
        templateProcessor = __spring__.getBean("templateProcessor")
        str = templateProcessor.processTemplate(map, "/WEB-INF/ftl/site_subject_nav.ftl", "utf-8")
        try:
            file = File(strPath)
            fw = OutputStreamWriter(FileOutputStream(file), "utf-8")
            fw.flush()
            fw.write(str)
            fw.close()
        finally:
            file = None
            fw = None
            
    def get_subject_nav(self):
        strFile = request.getServletContext().getRealPath("/")
        strSubjectFile = strFile + "html" + File.separator
        file = File(strSubjectFile)
        if file.exists() == False:
            file.mkdirs()
        # 创建学科导航
        strSubjectFile = strSubjectFile + "subject_nav.html"
        file = File(strSubjectFile)
        if file.exists() == False:
            self.GenSubjectNav(strSubjectFile)
        file = None
        
    def get_context_url(self):
        return CommonUtil.getContextUrl(request)
    
# 不做任何缓存处理.
class NoCache:
    def get(self, key):
        return None
    def put(self, key):
        pass
    def put(self, key, ttl):
        pass
    def remove(self, key):
        pass
    def clear(self):
        pass
