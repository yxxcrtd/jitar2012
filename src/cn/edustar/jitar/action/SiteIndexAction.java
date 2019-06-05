package cn.edustar.jitar.action;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import cn.edustar.jitar.model.MemcachedExpireTimeConfig;
import cn.edustar.jitar.pojos.SiteIndexPart;
import cn.edustar.jitar.pojos.TimerCount;
import cn.edustar.jitar.pojos.UserType;
import cn.edustar.jitar.service.ActionQuery;
import cn.edustar.jitar.service.ArticleQuery;
import cn.edustar.jitar.service.ArticleService;
import cn.edustar.jitar.service.GroupQuery;
import cn.edustar.jitar.service.PhotoQuery;
import cn.edustar.jitar.service.PlacardQuery;
import cn.edustar.jitar.service.PrepareCourseQuery;
import cn.edustar.jitar.service.ProductConfigService;
import cn.edustar.jitar.service.ResourceQuery;
import cn.edustar.jitar.service.SiteIndexPartService;
import cn.edustar.jitar.service.SiteNewsQuery;
import cn.edustar.jitar.service.SpecialSubjectQuery;
import cn.edustar.jitar.service.SubjectService;
import cn.edustar.jitar.service.TimerCountService;
import cn.edustar.jitar.service.UnitQuery;
import cn.edustar.jitar.service.UserQuery;
import cn.edustar.jitar.service.VideoQuery;
import cn.edustar.jitar.service.impl.NoCache;
import cn.edustar.jitar.util.CommonUtil;

import net.zdsoft.passport.service.client.PassportClient;

public class SiteIndexAction extends AbstractBasePageAction {

    /**
     * 网站首页
     * 
     * @author mxh
     */
    private static final long serialVersionUID = -4808657402510081838L;

    /** 文章服务 */
    private ArticleService articleService;

    /** 定时统计服务 */
    private TimerCountService timerCountService;

    /** 首页自定义模块服务 */
    private SiteIndexPartService siteIndexPartService;
    
    /** 许可证验证服务 */
    private ProductConfigService productConfigService;
    
    /** 宽甸定制，变量 isKuanDianUser 涉及到ftl ,务必注意 */
    private boolean isKuanDianUser = false;

    @Override
    protected String execute(String cmd) throws Exception {
        if(!this.validateProductInfo()){
            this.addActionError("无效的许可证。");
            if(!CommonUtil.isEmptyString(licenseErrorMessage)){
                this.addActionError(licenseErrorMessage);
            }            
            return "ERROR_INFO";
        }
        
        HttpSession session = request.getSession(true);
        if (null != session.getAttribute("returnFlagMsg")) {
            if (session.getAttribute("returnFlagMsg").toString().length() > 0) {
                request.setAttribute("returnFlagMsg", session.getAttribute("returnFlagMsg"));
            }
            session.removeAttribute("returnFlagMsg");
        }

        // 预览页面样式
        // String previewThemeUrl = this.params.getStringParam("preview", null);
        // if (null != previewThemeUrl && previewThemeUrl.length() > 0) {
        // request.setAttribute("preview_theme_url", previewThemeUrl);
        // }
        // 根据配置执行一些操作
        // Configure config = this.configService.getConfigure();
        // Boolean autoHtml = config.getBoolValue(Configure.SITE_AUTO_HTML,
        // false);
        // if (autoHtml && debugMode == null) {
        // 如果配置为自动生成静态文件，如果静态文件不存在，则生成静态文件，如果静态文件存在，则转向到静态文件直接显示
        // this.genIndexFile();
        // } else {
        // # 学科导航可能需要重新加载，每次都动态生成
        // cache = new NoCache();
        // this.genSubjectNavFile();
        // }
        
        // 启用页面调试模式
        if (this.params.existParam("debug")) {
            cacheService = new NoCache();
        }

        // # 页面导航高亮为 'index'
        request.setAttribute("head_nav", "index");
        
        //设置本页的缓存过期时间
        this.cacheService.setExpireTime(MemcachedExpireTimeConfig.getSiteIndexExpireTime());
        
        // 焦点新闻
        this.sitePicNewsList();

        // 教研动态
        this.siteNewsList();

        // 最新公告
        this.showPlacardList();

        // 最新文章、最热文章、名师文章
        this.showNewArticleList();
        this.showHotArticleList();
        this.showFamousArticleList();

        // 最新资源、热门资源
        this.showNewResourceList();
        this.showHotResourceList();

        // 最新教研活动
        this.showActionList();

        // 最新工作室、热门工作室、推荐工作室、教师风采
        this.showNewUserList();
        this.showTeacherFengcaiList();
        this.showHotUserList();
        this.showRcmdUserList();

        // 最新图片、热门图片
        this.showNewPhotoList();
        this.showHotPhotoList();

        // 教研专题
        this.showSpecialSubjectList();

        // 最新备课
        this.showPrepareCourseList();

        // 推荐协作组、最新协作组、优秀团队
        this.showRcmdGroupList();
        this.showNewGroupList();
        this.showBestGroupList();

        // 名师、学科带头人、教研之星、教研员
        this.showFamousUserList();
        this.showExpertUserList();
        this.showStarUserList();
        this.showInstructorUserList();

        // 最新视频、热门视频
        this.showNewVideoList();
        this.showHotVideoList();

        // 周活跃机构
        this.showActiveUnitList();

        // 自定义模块
        this.showCustomPart();

        // 站点统计
        this.showSiteStats();
        
        //定制用户
        if(isKuanDianUser){        
            request.setAttribute("isKuanDianUser",isKuanDianUser);        
        }      
        
        return SUCCESS;
    }


    
    @SuppressWarnings("rawtypes")
    private void showNewUserList() {
        String cache_key = "new_user_list";
        List new_wr_list = null != cacheService.get(cache_key) ? (List) cacheService.get(cache_key) : null;
        if (new_wr_list == null || new_wr_list.size() == 0) {
            UserQuery qry = new UserQuery(" u.loginName, u.userIcon, u.blogName, u.trueName, u.blogIntroduce ");
            qry.userStatus = 0;
            qry.orderType = 0;
            new_wr_list = qry.query_map(7);
            cacheService.put(cache_key, new_wr_list);
        }
        request.setAttribute("new_wr_list", new_wr_list);
    }

    @SuppressWarnings("rawtypes")
    private void showRcmdUserList() {
        String cache_key = "rcmd_wr_list";
        List rcmd_wr_list = null != cacheService.get(cache_key) ? (List) cacheService.get(cache_key) : null;
        if (rcmd_wr_list == null || rcmd_wr_list.size() == 0) {
            UserQuery qry = new UserQuery(" u.loginName, u.userIcon, u.blogName ");
            qry.userTypeId = 2;
            qry.userStatus = 0;
            rcmd_wr_list = qry.query_map(6);
            cacheService.put(cache_key, rcmd_wr_list);
        }
        request.setAttribute("rcmd_wr_list", rcmd_wr_list);
    }

    @SuppressWarnings("rawtypes")
    private void showHotUserList() {
        String cache_key = "hot_wr_list";
        List hot_wr_list = (List) cacheService.get(cache_key);
        if (hot_wr_list == null || hot_wr_list.size() == 0) {
            UserQuery qry = new UserQuery(" u.loginName, u.userIcon, u.blogName, u.trueName, u.blogIntroduce ");
            qry.userStatus = 0;
            qry.orderType = 1;// visitCount DESC
            hot_wr_list = qry.query_map(6);
            cacheService.put(cache_key, hot_wr_list);
        }
        request.setAttribute("hot_wr_list", hot_wr_list);
    }

    /**
     * 推荐协作组
     */
    @SuppressWarnings("rawtypes")
    private void showRcmdGroupList() {
        String cache_key = "rcmd_group_list";
        List rcmd_group_list = (List) cacheService.get(cache_key);
        if (rcmd_group_list == null || rcmd_group_list.size() == 0) {
            GroupQuery qry = new GroupQuery(" g.groupId, g.groupIcon, g.groupTitle, g.createDate, g.groupIntroduce ");
            qry.isRecommend = true;
            rcmd_group_list = (List) qry.query_map(7);
            cacheService.put(cache_key, rcmd_group_list);
        }
        request.setAttribute("rcmd_group_list", rcmd_group_list);
    }

    /**
     * 最新协作组
     */
    @SuppressWarnings("rawtypes")
    private void showNewGroupList() {
        String cache_key = "new_group_list";
        List new_group_list = (List) cacheService.get(cache_key);
        if (new_group_list == null || new_group_list.size() == 0) {
            GroupQuery qry = new GroupQuery(" g.groupId, g.groupIcon, g.groupTitle, g.createDate, g.groupIntroduce ");
            new_group_list = qry.query_map(7);
            cacheService.put(cache_key, new_group_list);
        }
        request.setAttribute("new_group_list", new_group_list);
    }

    /**
     * 优秀团队
     */
    @SuppressWarnings("rawtypes")
    private void showBestGroupList() {
        String cache_key = "best_group_list";
        List best_group_list = (List) cacheService.get(cache_key);
        if (best_group_list == null || best_group_list.size() == 0) {
            GroupQuery qry = new GroupQuery("g.groupId, g.groupName, g.groupTitle, g.groupIcon ");
            qry.isBestGroup = true;
            best_group_list = qry.query_map(7);
            cacheService.put(cache_key, best_group_list);
        }
        request.setAttribute("best_group_list", best_group_list);
    }

    /**
     * 专家名师.
     */
    @SuppressWarnings("rawtypes")
    private void showFamousUserList() {
        String cache_key = "famous_teachers";
        List famous_teachers = (List) cacheService.get(cache_key);
        if (famous_teachers == null || famous_teachers.size() == 0) {
            UserQuery qry = new UserQuery(" u.loginName, u.userIcon, u.trueName, u.visitCount ");
            qry.userTypeId = UserType.USERTYPE_FAMOUSE;
            qry.userStatus = 0;
            qry.orderType = 100;
            famous_teachers = qry.query_map(12);
            cacheService.put(cache_key, famous_teachers);
        }
        request.setAttribute("famous_teachers", famous_teachers);
    }

    /**
     * 学科带头人
     */
    @SuppressWarnings("rawtypes")
    private void showExpertUserList() {
        String cache_key = "expert_list";
        List expert_list = (List) cacheService.get(cache_key);
        if (expert_list == null || expert_list.size() == 0) {
            UserQuery qry = new UserQuery(" u.loginName, u.userIcon, u.blogName, u.trueName, u.visitCount ");
            qry.userTypeId = UserType.USERTYPE_EXPERT;
            qry.userStatus = 0;
            expert_list = qry.query_map(12);
            cacheService.put(cache_key, expert_list);
        }
        request.setAttribute("expert_list", expert_list);
    }

    /**
     * 教研之星
     */
    @SuppressWarnings("rawtypes")
    private void showStarUserList() {
        String cache_key = "teacher_star";
        List teacher_star = (List) cacheService.get(cache_key);
        if (teacher_star == null || teacher_star.size() == 0) {
            UserQuery qry = new UserQuery(" u.userId, u.loginName, u.trueName, u.userIcon, u.blogName, u.blogIntroduce ");
            qry.userTypeId = UserType.USERTYPE_STAR; // # 1- 教师风采; 2- 研修之星.
            qry.orderType = 100; // # 按随机来排序
            // # star = qry.first_map()
            teacher_star = qry.query_map(12);
            cacheService.put(cache_key, teacher_star);
        }
        request.setAttribute("teacher_star", teacher_star);
    }

    /**
     * 教研员
     */
    @SuppressWarnings("rawtypes")
    private void showInstructorUserList() {
        String cache_key = "instructor_list";
        List instructor_list = (List) cacheService.get(cache_key);
        if (instructor_list == null || instructor_list.size() == 0) {
            UserQuery qry = new UserQuery(" u.userId, u.loginName, u.trueName, u.userIcon, u.blogName, u.blogIntroduce ");
            qry.userTypeId = UserType.USERTYPE_INSTRUCTOR;
            qry.orderType = 100; // # 按随机来排序
            instructor_list = qry.query_map(12);
            cacheService.put(cache_key, instructor_list);
        }
        request.setAttribute("instructor_list", instructor_list);
    }

    /**
     * 最新视频
     */
    @SuppressWarnings("rawtypes")
    private void showNewVideoList() {
        // 视频查询需注意：首页只查询成功生成了缩略图的视频。
        String cache_key = "new_video_list";
        List new_video_list = (List) cacheService.get(cache_key);
        if (new_video_list == null || new_video_list.size() == 0) {
            VideoQuery qry = new VideoQuery(
                    " v.videoId, v.title, v.createDate, v.href, v.flvHref, v.userId, u.loginName, u.trueName, v.flvThumbNailHref ");
            qry.orderType = VideoQuery.ORDER_TYPE_VIDEOID_DESC;
            new_video_list = qry.query_map(8);
            cacheService.put(cache_key, new_video_list);
        }
        request.setAttribute("new_video_list", new_video_list);
    }

    /**
     * 热门视频
     */
    @SuppressWarnings("rawtypes")
    private void showHotVideoList() {
        // 视频查询需注意：首页只查询成功生成了缩略图的视频。
        String cache_key = "hot_video_list";
        List hot_video_list = (List) cacheService.get(cache_key);
        if (hot_video_list == null || hot_video_list.size() == 0) {
            VideoQuery qry = new VideoQuery(
                    " v.videoId, v.title, v.createDate, v.href, v.flvHref, v.userId, u.loginName, u.trueName, v.flvThumbNailHref ");
            qry.orderType = VideoQuery.ORDER_TYPE_VIEWCOUNT_DESC;
            hot_video_list = qry.query_map(8);
            cacheService.put(cache_key, hot_video_list);
        }
        request.setAttribute("hot_video_list", hot_video_list);
    }

    /**
     * 周活跃机构
     */
    @SuppressWarnings("rawtypes")
    private void showActiveUnitList() {
        String cache_key = "school_list";
        List school_list = (List) cacheService.get(cache_key);
        if (school_list == null || school_list.size() == 0) {
            UnitQuery qry = new UnitQuery("unit.unitName, unit.unitTitle");
            qry.delState = false;
            qry.orderType = 1;
            school_list = qry.query_map(10);
            cacheService.put(cache_key, school_list);
        }
        request.setAttribute("school_list", school_list);
    }

    /**
     * 最新备课
     */
    @SuppressWarnings("rawtypes")
    private void showPrepareCourseList() {
        String cache_key = "course_list";
        List course_list = (List) cacheService.get(cache_key);
        if (course_list == null || course_list.size() == 0) {
            PrepareCourseQuery qry = new PrepareCourseQuery(
                    " pc.createUserId,pc.leaderId,pc.memberCount,pc.articleCount,pc.resourceCount,    pc.status,pc.actionCount,pc.topicCount,pc.topicReplyCount,pc.viewCount,pc.startDate,pc.endDate,         pc.title, pc.prepareCourseId, pc.createDate, pc.metaSubjectId, pc.gradeId,     u.loginName,u.trueName ");
            qry.status = 0;
            course_list = qry.query_map(6);
            cacheService.put(cache_key, course_list);
        }
        request.setAttribute("course_list", course_list);
    }

    /**
     * 教研专题
     */
    @SuppressWarnings("rawtypes")
    private void showSpecialSubjectList() {
        String cache_key = "special_subject_list";
        List special_subject_list = (List) cacheService.get(cache_key);
        if (special_subject_list == null || special_subject_list.size() == 0) {
            SpecialSubjectQuery qry = new SpecialSubjectQuery("ss.specialSubjectId, ss.title, ss.createDate");
            special_subject_list = qry.query_map(6);
            cacheService.put(cache_key, special_subject_list);
        }
        request.setAttribute("special_subject_list", special_subject_list);
    }

    /**
     * 热门图片
     */
    @SuppressWarnings("rawtypes")
    private void showHotPhotoList() {
        String cache_key = "index_hot_photo_list";
        List index_hot_photo_list = (List) cacheService.get(cache_key);
        if (index_hot_photo_list == null || index_hot_photo_list.size() == 0) {
            PhotoQuery qry = new PhotoQuery(" p.id , p.title, p.createDate, p.href ");
            qry.orderType = PhotoQuery.ORDER_TYPE_VIEWCOUNT_DESC; // # photoId
            index_hot_photo_list = qry.query_map(10);
            if(index_hot_photo_list != null && index_hot_photo_list.size() > 0){
                cacheService.put(cache_key, index_hot_photo_list);
            }
        }
        request.setAttribute("hot_photo_list", index_hot_photo_list);
    }

    /**
     * 最新图片
     */
    @SuppressWarnings("rawtypes")
    private void showNewPhotoList() {
        String cache_key = "index_new_photo_list";
        List index_new_photo_list = (List) cacheService.get(cache_key);
        if (index_new_photo_list == null || index_new_photo_list.size() == 0) {
            PhotoQuery qry = new PhotoQuery(" p.id , p.title, p.href");
            qry.orderType = PhotoQuery.ORDER_TYPE_PHOTOID_DESC; // # photoId
            index_new_photo_list = qry.query_map(10);
            if(index_new_photo_list != null && index_new_photo_list.size() > 0){
                cacheService.put(cache_key, index_new_photo_list);
            }            
        }       
        request.setAttribute("new_photo_list", index_new_photo_list);
    }

    /**
     * 站点统计
     */
    private void showSiteStats() {
        String cache_key = "site_stat";
        TimerCount site_stat = (TimerCount) cacheService.get(cache_key);
        if (site_stat == null) {
            site_stat = timerCountService.getTimerCountById(TimerCount.COUNT_TYPE_SITE);
            cacheService.put(cache_key, site_stat);
        }
        request.setAttribute("site_stat", site_stat);
    }

    /**
     * 教师风采.，根据需要，还可以实现延迟加载
     */
    @SuppressWarnings("rawtypes")
    private void showTeacherFengcaiList() {
        String cacheKey = "teacher_show";
        List teacher_show = (List) this.cacheService.get(cacheKey);
        if (null == teacher_show || teacher_show.size() == 0) {
            UserQuery qry = new UserQuery(" u.userId, u.loginName, u.trueName, u.userIcon ");
            qry.userTypeId = 6;
            qry.orderType = 100;
            teacher_show = (List) qry.query_map(8);
            this.cacheService.put(cacheKey, teacher_show);
        }
        request.setAttribute("teacher_show", teacher_show);
    }

    /**
     * 最新教研活动
     */
    @SuppressWarnings("rawtypes")
    private void showActionList() {
        String cacheKey = "jitar_actions";
        List jitar_actions = (List) this.cacheService.get(cacheKey);
        if (null == jitar_actions || jitar_actions.size() == 0) {
            ActionQuery qry = new ActionQuery(" act.title, act.createDate, act.actionId, act.createUserId, act.actionType, u.loginName,u.trueName");
            int count = 8;
            if(isKuanDianUser){
                count = 5;
            }
            
            jitar_actions = (List) qry.query_map(count);
            this.cacheService.put(cacheKey, jitar_actions);
        }
        request.setAttribute("jitar_actions", jitar_actions);
    }

    /**
     * 名师文章，根据需要，还可以实现延迟加载
     */
    @SuppressWarnings("rawtypes")
    private void showFamousArticleList() {
        String cacheKey = "famous_article_list";
        List famous_article_list = (List) this.cacheService.get(cacheKey);
        if (null == famous_article_list || famous_article_list.size() == 0) {
            ArticleQuery qry = new ArticleQuery(" a.articleId, a.title, a.createDate, a.typeState,u.userId, u.userIcon, u.trueName, u.loginName ");
            qry.custormAndWhereClause = "u.userType LIKE '%/" + UserType.USERTYPE_FAMOUSE + "/%' And a.approvedPathInfo Like '%/"
                    + this.rootUnit.getUnitId() + "/%' ";
            famous_article_list = (List) qry.query_map(7);
            this.cacheService.put(cacheKey, famous_article_list);
        }
        request.setAttribute("famous_article_list", famous_article_list);
    }

    /**
     * 最热文章，根据需要，还可以实现延迟加载
     */
    private void showHotArticleList() {
        String cachekey = "hot_article_list";
        Object hot_article_list = cacheService.get(cachekey);
        if (null == hot_article_list) {
            hot_article_list = this.articleService.getHotArticleList(7);
        }
        request.setAttribute("hot_article_list", hot_article_list);
    }

    /**
     * 最新文章，根据需要，还可以实现延迟加载
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    private void showNewArticleList() {
        /*
         * 1 最新文章按文章发布时间取内容； 2 若文章内容本身不足200汉字则不作为左侧“推荐”文章出现； 3 推荐文章应该原创文章； 4
         * 中间列再取四条最新原创文章； 5 剩余三条文章取三条最新转载类型文章 6
         * 首页文章区域内文章标题不可重复出现；最新、最热、名师三个不同页签下文章可重复；
         */
        String cacheKey = "newest_article_list";
        List newest_article_list = (List) this.cacheService.get(cacheKey);
        if (null == newest_article_list || newest_article_list.size() == 0) {
            ArticleQuery qry = new ArticleQuery(" a.articleId, a.title, a.createDate, a.typeState,u.userId, u.userIcon, u.trueName, u.loginName ");
            qry.custormAndWhereClause = "a.approvedPathInfo Like '%/" + this.rootUnit.getUnitId() + "/%' ";
            qry.typeState = false; // 原创
            newest_article_list = (List) qry.query_map(4);
            qry.typeState = true;// 转载
            List newest_article_list2 = (List) qry.query_map(3);
            if (newest_article_list2 != null && newest_article_list2.size() > 0) {
                newest_article_list.addAll(newest_article_list.size(), newest_article_list2);
                this.cacheService.put(cacheKey, newest_article_list);
            }
        }
        request.setAttribute("newest_article_list", newest_article_list);
    }

    /**
     * 最热资源，根据需要，还可以实现延迟加载
     */
    @SuppressWarnings("rawtypes")
    private void showHotResourceList() {
        String cacheKey = "hot_resource_list";
        List hot_resource_list = (List) this.cacheService.get(cacheKey);
        if (null == hot_resource_list || hot_resource_list.size() == 0) {
            ResourceQuery qry = new ResourceQuery(
                    "r.userId, r.resourceId, r.title, r.href, r.createDate, r.fsize, r.downloadCount,u.loginName, u.trueName, msubj.msubjName, grad.gradeName, sc.name as scName");
            qry.unitId = null;
            qry.custormAndWhereClause = " r.approvedPathInfo Like '%/" + this.rootUnit.getUnitId() + "/%' And DateDiff(day,r.createDate,getdate()) < 10 ";
            qry.orderType = ResourceQuery.ORDER_TYPE_HOT_DESC;
            int count = 10;
            if(isKuanDianUser){
                count = 7;
            }
            hot_resource_list = (List) qry.query_map(count);
            this.cacheService.put(cacheKey, hot_resource_list);
        }
        request.setAttribute("hot_resource_list", hot_resource_list);
    }

    /**
     * 最新资源
     */
    @SuppressWarnings("rawtypes")
    private void showNewResourceList() {
        String cacheKey = "new_resource_list";
        List new_resource_list = (List) this.cacheService.get(cacheKey);
        if (null == new_resource_list || new_resource_list.size() == 0) {
            ResourceQuery qry = new ResourceQuery(
                    "r.userId, r.resourceId, r.title, r.href, r.createDate, r.fsize, r.downloadCount,u.loginName, u.trueName, r.subjectId as subjectId, grad.gradeName, sc.name as scName");
            qry.unitId = null;
            qry.custormAndWhereClause = " r.approvedPathInfo Like '%/" + this.rootUnit.getUnitId() + "/%' ";
            int count = 10;
            if(isKuanDianUser){
                count = 7;
            }
            new_resource_list = (List) qry.query_map(count);
            this.cacheService.put(cacheKey, new_resource_list);
        }
        request.setAttribute("new_resource_list", new_resource_list);
    }

    /**
     * 最新公告
     */
    @SuppressWarnings("rawtypes")
    private void showPlacardList() {
        String cacheKey = "site_placard_list";
        List site_placard_list = (List) this.cacheService.get(cacheKey);
        if (null == site_placard_list || site_placard_list.size() == 0) {
            PlacardQuery qry = new PlacardQuery("pld.id, pld.title, pld.createDate");
            qry.objType = 100;
            int count = 4;
            if(isKuanDianUser){
                count = 10;
            }
            site_placard_list = (List) qry.query_map(count);
            this.cacheService.put(cacheKey, site_placard_list);
        }
        request.setAttribute("site_placard_list", site_placard_list);
    }

    /**
     * 教研动态(无图片)
     */
    @SuppressWarnings("rawtypes")
    private void siteNewsList() {
        String cacheKey = "jitar_news";
        List jitar_news = (List) this.cacheService.get(cacheKey);
        if (null == jitar_news || jitar_news.size() == 0) {
            SiteNewsQuery qry = new SiteNewsQuery("snews.newsId, snews.title, snews.createDate");
            qry.status = 0;
            qry.hasPicture = false;
            qry.subjectId = 0;
            jitar_news = (List) qry.query_map(9);
            this.cacheService.put(cacheKey, jitar_news);
        }
        if (jitar_news != null && jitar_news.size() > 0) {
            request.setAttribute("jitar_news", jitar_news);
        }
    }

    /**
     * 教研动态(焦点图)
     */
    @SuppressWarnings({"rawtypes"})
    private void sitePicNewsList() {
        String cacheKey = "pic_news";
        List pic_news = (List) this.cacheService.get(cacheKey);
        if (null == pic_news) {
            SiteNewsQuery qry = new SiteNewsQuery("snews.newsId, snews.title, snews.createDate, snews.picture");
            qry.status = 0;
            qry.hasPicture = true;
            qry.subjectId = 0;
            qry.orderType = 0;
            pic_news = (List) qry.query_map(5);
            if (pic_news != null && pic_news.size() > 0) {
                this.cacheService.put(cacheKey, pic_news);
                request.setAttribute("pic_news", pic_news);
            }
        } else {
            request.setAttribute("pic_news", pic_news);
        }
    }

    /**
     * 自定义模块
     */
    @SuppressWarnings({"unchecked"})
    private void showCustomPart(){
      String cache_key = "show_custorm_part";
      List<SiteIndexPart> siteIndexPartList = (List<SiteIndexPart>)cacheService.get(cache_key);
      if (siteIndexPartList == null){
          siteIndexPartList = siteIndexPartService.getSiteIndexPartList(true);                
          cacheService.put(cache_key, siteIndexPartList);
       }
      //自定义区域受布局和UI等限制，目前没有自动化的方法实现
      //目前有5个区域可以自定义，自定义的地方也不适合保存到数据库中，目前只能采用固定的方法实现，
      //为了显示方便，定义变量
      List<SiteIndexPart> show_custom_part1 = new ArrayList<SiteIndexPart>();
      List<SiteIndexPart> show_custom_part2 = new ArrayList<SiteIndexPart>();
      List<SiteIndexPart> show_custom_part3 = new ArrayList<SiteIndexPart>();
      List<SiteIndexPart> show_custom_part4 = new ArrayList<SiteIndexPart>();
      List<SiteIndexPart> show_custom_part5 = new ArrayList<SiteIndexPart>(); 
      for(SiteIndexPart p : siteIndexPartList){
          if(p.getModuleZone() == 1){
              show_custom_part1.add(p);
          }
          else if(p.getModuleZone() == 2){
              show_custom_part2.add(p);
          }

          else if(p.getModuleZone() == 3){
              show_custom_part3.add(p);
          }

          else if(p.getModuleZone() == 4){
              show_custom_part4.add(p);
          }

          else if(p.getModuleZone() == 5){
              show_custom_part5.add(p);
          }          
      }
      if(show_custom_part1.size() > 0){
          request.setAttribute("show_custom_part1", show_custom_part1);
      }
      
      if(show_custom_part2.size() > 0){
          request.setAttribute("show_custom_part2", show_custom_part2);
      }
      
      if(show_custom_part3.size() > 0){
          request.setAttribute("show_custom_part3", show_custom_part3);
      }
      
      if(show_custom_part4.size() > 0){
          request.setAttribute("show_custom_part4", show_custom_part4);
      }
      
      if(show_custom_part5.size() > 0){
          request.setAttribute("show_custom_part5", show_custom_part5);
      }
      request.setAttribute("siteIndexPartList", siteIndexPartList);
   }

    /**
     * 许可证服务
     * @return
     */
    private String licenseErrorMessage = null;
    private boolean validateProductInfo() {
        boolean p = productConfigService.isValid();
        String errMessage = productConfigService.getErrMessage();
        this.addActionError(errMessage);
        String productName = productConfigService.getProductName();
        String productdays = productConfigService.getDays();
        String remainDays = productConfigService.getRemainDays();
        String guid = productConfigService.getProductGuid();
        request.setAttribute("guid", guid);
        if (!productdays.equals("0") && !productdays.equals("-1")) {
            productName = productName + " 还可以继续试用" + remainDays + "天";
        }
        request.setAttribute("productName", productName);
        request.setAttribute("p", p);
        licenseErrorMessage = productConfigService.getErrMessage();
        return p;
    }

    public void setArticleService(ArticleService articleService) {
        this.articleService = articleService;
    }

    public void setSubjectService(SubjectService subjectService) {
        this.subjectService = subjectService;
    }

    public void setTimerCountService(TimerCountService timerCountService) {
        this.timerCountService = timerCountService;
    }

    public void setSiteIndexPartService(SiteIndexPartService siteIndexPartService) {
        this.siteIndexPartService = siteIndexPartService;
    }

    public void setProductConfigService(ProductConfigService productConfigService) {
        this.productConfigService = productConfigService;
    }
}
