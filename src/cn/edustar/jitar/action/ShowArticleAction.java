package cn.edustar.jitar.action;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.ContextLoader;

import cn.edustar.data.Pager;
import cn.edustar.jitar.JitarConst;
import cn.edustar.jitar.JitarContext;
import cn.edustar.jitar.model.MemcachedExpireTimeConfig;
import cn.edustar.jitar.model.ObjectType;
import cn.edustar.jitar.pojos.Article;
import cn.edustar.jitar.pojos.Comment;
import cn.edustar.jitar.pojos.Config;
import cn.edustar.jitar.pojos.Praise;
import cn.edustar.jitar.pojos.Report;
import cn.edustar.jitar.pojos.UFavorites;
import cn.edustar.jitar.pojos.User;
import cn.edustar.jitar.service.ArticleQuery;
import cn.edustar.jitar.service.ArticleService;
import cn.edustar.jitar.service.BrowsingService;
import cn.edustar.jitar.service.CacheService;
import cn.edustar.jitar.service.CategoryService;
import cn.edustar.jitar.service.CommentQueryParam;
import cn.edustar.jitar.service.CommentService;
import cn.edustar.jitar.service.ConfigService;
import cn.edustar.jitar.service.PraiseQuery;
import cn.edustar.jitar.service.PraiseService;
import cn.edustar.jitar.service.PrevNextArticleQuery;
import cn.edustar.jitar.service.ReportService;
import cn.edustar.jitar.service.UFavoritesService;
import cn.edustar.jitar.service.UserService;
import cn.edustar.jitar.util.CommonUtil;

/**
 * 文章详情页
 * 
 * @author mxh
 * 
 */
public class ShowArticleAction extends AbstractBasePageAction {
    
    private Logger log = LoggerFactory.getLogger(this.getClass().getSimpleName());

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    /** 文章服务 */
    private ArticleService articleService;

    /** 举报服务 */
    private ReportService reportService;

    /** 赞服务 */
    private PraiseService praiseService;

    /** 赞服务 */
    // private PraiseService praiseService;

    /** 收藏夹服务 */
    private UFavoritesService favoritesService;

    /** 浏览历史服务 */
    private BrowsingService browsingService;

    private CategoryService categoryService;

    private UserService userService;

    private CommentService commentService;
    
    private ConfigService configService;

    /** 文章id */
    private Integer articleId;

    /** 当前登录用户 */
    private User loginUser;

    @Override
    protected String execute(String cmd) throws Exception {
        articleId = this.params.getIntParamZeroAsNull("articleId");
        if (articleId == null) {
            this.addActionError("缺少文章标识。");
            this.addActionLink("返回网站首页", request.getContextPath() + "/index.action");
            return "ERROR_INFO";
        }

        if (cmd == null || cmd.length() == 0) {
            cmd = "show";
        }

        cacheService = JitarContext.getCurrentJitarContext().getCacheProvider().getCache("main");
        
        loginUser = this.getLoginUser();
        switch (cmd) {
            case "praise" : // 赞
                return praise();
            case "unpraise" : // 赞
                return unpraise();
            case "report" : // 举报
                return report();
            case "transfer" : // 转载文章
                return transferArticle();
            case "favorite" : // 收藏文章
                return favoriteArticle();
            case "unfavorite" : // 收藏文章
                return unFavoriteArticle();
            case "comment" :
                return commentArticle();
            case "replyCmt" :
                return replyComment();
            case "deleteCmt" :
                return deleteComment();
            case "showMoreComment":
                return showMoreComment();
            default :
                return show();
        }
    }
    
    /**
     * Ajax 显示更多评论
     * @return
     * @throws IOException
     */    
    private String showMoreComment() throws IOException{
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/html");
        int lastId = this.params.safeGetIntParam("lastId");
        Pager pager = this.params.createPager();
        pager.setCurrentPage(1);
        pager.setPageSize(JitarConst.SHOW_COMMENT_LIST_COUNT);
        CommentQueryParam qry = new CommentQueryParam();
        qry.objectId = this.articleId;
        qry.objectType = ObjectType.OBJECT_TYPE_ARTICLE;
        if(lastId > 0){
            qry.lastId = lastId;        
        }
        
        List<Comment> cmt_list = this.commentService.getCommentList(qry, pager);  
        
        
        List<Comment> reply_list = null;
        for (Comment c : cmt_list) {
            reply_list = this.commentService.getCommentListByObject(ObjectType.OBJECT_TYPE_COMMENT.getTypeId(), c.getId());
            if (null != reply_list && reply_list.size() > 0) {
                c.setReplyList(reply_list);
            }
        }
        request.setAttribute("cmt_list", cmt_list);
        return "ShowMoreComment";
    }

    /**
     * 删除评论
     * 
     * @return
     * @throws IOException
     */
    private String deleteComment() throws IOException {
        int cmtId = this.params.safeGetIntParam("cmtId", 0);
        response.setContentType("application/json");
        if (null == loginUser) {
            response.getWriter().print(this.makeJsonReturn(1, "请先登录。"));
            return NONE;
        }

        Comment comment = this.commentService.getComment(cmtId);
        if (null == comment) {
            response.getWriter().print(this.makeJsonReturn(2, "无法加载评论对象，该评论可能已被删除。"));
            return NONE;
        }
        if (comment.getUserId() != loginUser.getUserId()) {
            response.getWriter().print(this.makeJsonReturn(3, "请不要删除不是自己发布的内容。"));
            return NONE;
        }
        // 先删除评论的回复
        this.commentService.deleteCommentReply(comment);
        this.commentService.deleteComment(comment);
        response.getWriter().print(this.makeJsonReturn(0, ""));
        return NONE;

    }

    /**
     * 回复评论
     * 
     * @return
     * @throws IOException
     */
    private String replyComment() throws IOException {
        int parentId = this.params.safeGetIntParam("parentId", 0);
        int cmtId = this.params.safeGetIntParam("cmtId", 0);
        String content = this.params.safeGetStringParam("content");
        response.setContentType("application/json");
        if (null == loginUser) {
            response.getWriter().print(this.makeJsonReturn(1, "请先登录。"));
            return NONE;
        }
        if (null == content || content.length() == 0) {
            response.getWriter().print(this.makeJsonReturn(3, "请输入内容。"));
            return NONE;
        }
        if (content.length() > 300) {
            response.getWriter().print(this.makeJsonReturn(3, "输入的内容不能多于300字。"));
            return NONE;
        }

        Comment comment = this.commentService.getComment(cmtId);
        if (null == comment) {
            response.getWriter().print(this.makeJsonReturn(2, "无法加载评论对象，该评论可能已被删除。"));
            return NONE;
        }

        Comment cmt = new Comment();
        cmt.setAboutUserId(comment.getUserId());
        cmt.setAudit(true);
        cmt.setContent(content);
        cmt.setCreateDate(new Date());
        cmt.setIp(CommonUtil.getClientIP(request));
        if (parentId == 0) {
            cmt.setObjId(comment.getId());
        } else {
            cmt.setObjId(parentId);
        }
        cmt.setObjType(ObjectType.OBJECT_TYPE_COMMENT.getTypeId());
        cmt.setStar(0);
        cmt.setTitle("回复评论");
        cmt.setUserId(loginUser.getUserId());
        cmt.setUserName(loginUser.getTrueName());
        this.commentService.saveComment(cmt);
        
        //动态显示回复
        User aboutUser = this.userService.getUserById(cmt.getAboutUserId());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");       
        StringBuffer sb = new StringBuffer();
        sb.append("<dl id='r" + cmt.getId() + "'>");
        sb.append("<dt><a href='" + request.getContextPath() + "/go.action?loginName=" + loginUser.getLoginName() + "' target='_blank'><img src='" + this.SSOServerURL1 + "/upload/" + loginUser.getUserIcon() + "' width='50' border='0' onerror=\"this.src='" + request.getContextPath() + "/images/default.gif'\"/></a></dt>");
        sb.append("<dd>");
        sb.append("  <h4><em class='emDate'>" + sdf.format(cmt.getCreateDate()) + "</em><a target='_blank' href='" + request.getContextPath() + "/go.action?loginName=" + loginUser.getLoginName() + "'>" + loginUser.getTrueName() + "</a></h4>");
        sb.append("  <p class='mt10 hardBreak'>");
        sb.append(cmt.getContent());
        sb.append("</p>");
        sb.append("  <p class='clearfix'>");
        sb.append("    <a href='javascript:void(0);' class='replyBtn' id='rb2' onclick='replay(" + cmt.getId() + "," + cmt.getObjId() + ")'>回复</a>");
        sb.append("    <a href='javascript:void(0);' class='removeBtn' commentid='" + cmt.getId() + "' isreply='1' xxxonclick='deleteComment(" + cmt.getId() + ",1);return false;'>删除</a>");
        if(aboutUser != null){
            sb.append("    <span class='replyText'>回复给</span> <a target='_blank' href='" + request.getContextPath() + "/go.action?loginName=" + aboutUser.getLoginName() + "' class='replyYou'>" + aboutUser.getTrueName() + "</a>");
        }        
        sb.append("  </p>");
        sb.append("</dd>");
        sb.append("<span class='replyArrow'></span>");
        sb.append("</dl>");    
        
        response.getWriter().print(this.makeJsonReturn(0, sb.toString(),cmt.getId()));
        sb = null;
        return NONE;

    }
    /**
     * 发表评论。
     * 
     * @return
     * @throws IOException
     */
    private String commentArticle() throws IOException {
        String content = this.params.safeGetStringParam("content");
        response.setContentType("application/json");
        if (null == loginUser) {
            response.getWriter().print(this.makeJsonReturn(1, "请先登录。"));
            return NONE;
        }
        if (null == content || content.length() == 0) {
            response.getWriter().print(this.makeJsonReturn(3, "请输入内容。"));
            return NONE;
        }

        if (content.length() > 300) {
            response.getWriter().print(this.makeJsonReturn(3, "输入的内容不能多于300字。"));
            return NONE;
        }

        String cacheKey = "article_" + this.articleId;
        Article article = (Article)this.cacheService.get(cacheKey);
        if (null == article) {
            article = this.articleService.getArticle(articleId);
            if(article != null){
                this.cacheService.put(cacheKey, article, MemcachedExpireTimeConfig.getSiteIndexExpireTime());
            }
        }
        if (null == article) {
            response.getWriter().print(this.makeJsonReturn(2, "无法加载文章对象。"));
            return NONE;
        }
        
        Comment cmt = new Comment();
        cmt.setAboutUserId(article.getUserId());
        cmt.setAudit(true);
        cmt.setContent(content);
        cmt.setCreateDate(new Date());
        cmt.setIp(CommonUtil.getClientIP(request));
        cmt.setObjId(article.getArticleId());
        cmt.setObjType(ObjectType.OBJECT_TYPE_ARTICLE.getTypeId());
        cmt.setStar(0);
        cmt.setTitle(article.getTitle());
        cmt.setUserId(loginUser.getUserId());
        cmt.setUserName(loginUser.getTrueName());
        this.commentService.saveComment(cmt);
        this.articleService.incArticleCommentCount(cmt, 1);
        //将新的内容动态添加到上面。
        //TODO:好的做法是只传JSON内容，并且不需要传Content部分。
        
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");       
        StringBuffer sb = new StringBuffer();
        sb.append("<dl cmtid='" + cmt.getId() + "'>")
        .append("<dt><a target='_blank' href='" + request.getContextPath() + "/go.action?loginName=" + loginUser.getLoginName() + "'><img src='" + this.SSOServerURL1 + "/upload/" + loginUser.getUserIcon() + "' width='50' onerror=\"this.src='" + request.getContextPath() + "/images/default.gif'\"/></a></dt>")
        .append("<dd>")
        .append("<div class='replyContent' id='replyContent" + cmt.getId() + "'>")
        .append("<div id='r" + cmt.getId() + "'>")
        .append("<h4><em class='emDate'>" +  sdf.format(cmt.getCreateDate()) + "</em><a target='_blank' href='" + request.getContextPath() + "/go.action?loginName=" + loginUser.getLoginName() + "'>" + loginUser.getTrueName() + "</a></h4>")
        .append("<p class='mt10 hardBreak'>")
        .append(cmt.getContent())
        .append("</p>")
        .append("<p class='clearfix'>")
        .append(" <a href='javascript:void(0);' class='replyBtn' onclick='replay(" + cmt.getId() + ", " + cmt.getId() + ")'>回复</a>")
        .append("<a href='javascript:void(0);' class='removeBtn' commentid='" +  cmt.getId() + "' isreply='0' xxxxonclick='deleteComment(" + cmt.getId() + "," + cmt.getId() + ");return false;'>删除</a>")
        .append("</p>")
        .append("</div>")
        .append("</div>")
        .append("</dd>")
        .append("</dl>");
        response.getWriter().print(this.makeJsonReturn(0, sb.toString()));
        sb = null;
        return NONE;
    }

    /**
     * 转发文章
     * 
     * @return
     * @throws IOException
     */
    private String favoriteArticle() throws IOException {
        response.setContentType("application/json");
        if (null == loginUser) {
            response.getWriter().print(this.makeJsonReturn(1, "请先登录。"));
            return NONE;
        }
        Article article = this.articleService.getArticle(articleId);
        if (null == article) {
            response.getWriter().print(this.makeJsonReturn(2, "无法加载文章对象。"));
            return NONE;
        }

        UFavorites favorate = new UFavorites();
        favorate.setFavHref(CommonUtil.getSiteUrl(request) + "showArticle.action?articleId=" + article.getArticleId());
        favorate.setFavTitle(article.getTitle());
        favorate.setFavUser(loginUser.getUserId());
        favorate.setFavTypeId(0);
        favorate.setObjectId(article.getArticleId());
        favorate.setObjectType(ObjectType.OBJECT_TYPE_ARTICLE.getTypeId());
        favorate.setFavDate(new Date());
        this.favoritesService.Save(favorate);
        response.getWriter().print(this.makeJsonReturn(0, "收藏完毕。"));
        return NONE;
    }

    /**
     * 取消收藏文章
     * 
     * @return
     * @throws IOException
     */
    private String unFavoriteArticle() throws IOException {
        response.setContentType("application/json");
        if (null == loginUser) {
            response.getWriter().print(this.makeJsonReturn(1, "请先登录。"));
            return NONE;
        }

        UFavorites favorate = this.favoritesService.getFavorites(ObjectType.OBJECT_TYPE_ARTICLE.getTypeId(), this.articleId, loginUser.getUserId());
        if (favorate != null) {
            this.favoritesService.Del(favorate);
        }
        response.getWriter().print(this.makeJsonReturn(0, ""));
        return NONE;
    }

    /**
     * 转发文章
     * 
     * @return
     * @throws IOException
     */
    private String transferArticle() throws IOException {
        response.setContentType("application/json");
        if (null == loginUser) {
            response.getWriter().print(this.makeJsonReturn(1, "请先登录。"));
            return NONE;
        }
        Article article = this.articleService.getArticle(articleId);
        if (null == article) {
            response.getWriter().print(this.makeJsonReturn(2, "无法加载文章对象。"));
            return NONE;
        }

        Article cloneArticle = new Article();
        cloneArticle.setArticleContent(article.getArticleContent());
        cloneArticle.setTitle(article.getTitle());
        cloneArticle.setUnitId(loginUser.getUnitId());
        cloneArticle.setUnitPathInfo(loginUser.getUnitPathInfo());
        cloneArticle.setLoginName(loginUser.getLoginName());
        cloneArticle.setUserTrueName(loginUser.getTrueName());
        cloneArticle.setOrginPath(loginUser.getUnitPathInfo());
        cloneArticle.setTypeState(true);
        cloneArticle.setWordDownload(article.getWordDownload());
        cloneArticle.setWordHref(article.getWordHref());
        cloneArticle.setSubjectId(loginUser.getSubjectId());
        cloneArticle.setGradeId(loginUser.getGradeId());
        cloneArticle.setAddIp(CommonUtil.getClientIP(request));
        cloneArticle.setUserId(loginUser.getUserId());
        this.articleService.createArticle(cloneArticle);
        response.getWriter().print(this.makeJsonReturn(0, "转发完毕。"));
        return NONE;
    }

    /**
     * 举报。
     * 
     * @return
     * @throws IOException
     */
    private String report() throws IOException {
        String reportContent = this.params.safeGetStringParam("reportContent");
        response.setContentType("application/json");
        if (null == loginUser) {
            response.getWriter().print(this.makeJsonReturn(1, "请先登录。"));
            return NONE;
        }

        reportContent = reportContent.trim();
        if (reportContent.length() == 0) {
            response.getWriter().print(this.makeJsonReturn(3, "请选择举报的理由。"));
            return NONE;
        }

        Article article = this.articleService.getArticle(articleId);
        if (null == article) {
            response.getWriter().print(this.makeJsonReturn(2, "无法要举报的对象。"));
            return NONE;
        }
        Report report = new Report();
        report.setUserId(loginUser.getUserId());
        report.setCreateDate(new Date());
        report.setObjId(articleId);
        report.setObjType(ObjectType.OBJECT_TYPE_ARTICLE.getTypeId());
        String articleTitle = article.getTitle();
        if (articleTitle.length() > 124) {
            articleTitle = articleTitle.substring(0, 124) + "...";
        }
        report.setObjTitle(articleTitle);
        report.setReportType(reportContent);
        this.reportService.saveReport(report);
        response.getWriter().print(this.makeJsonReturn(0, "举报成功。"));
        return NONE;
    }

    /**
     * 实现赞的功能
     * 
     * <p>
     * 注意：此功能需要用户登录。可以参与的用户是整个用户管理系统中的用户，目前可以仅限制在本地系统中。
     * </p>
     * 
     * @throws IOException
     */
    @SuppressWarnings("rawtypes")
    private String praise() throws IOException {
        response.setContentType("application/json");
        if (null == loginUser) {
            response.getWriter().print(this.makeJsonReturn(1, "请先登录。"));
            return NONE;
        }
        Praise praise = this.praiseService.getPraiseByTypeAndUserId(articleId, ObjectType.OBJECT_TYPE_ARTICLE.getTypeId(), loginUser.getUserId());
        if (praise == null) {             
            praise = new Praise();
            praise.setObjType(ObjectType.OBJECT_TYPE_ARTICLE.getTypeId());
            praise.setObjId(this.articleId);
            praise.setUserId(loginUser.getUserId());
            this.praiseService.savePraise(praise);
            // 得到赞的数量
            int count = this.praiseService.getPraiseCountByObjectType(articleId, ObjectType.OBJECT_TYPE_ARTICLE.getTypeId());
            PraiseQuery qry = new PraiseQuery("p.praiseId, u.loginName, u.trueName, u.userIcon");
            qry.objId = this.articleId;
            qry.objType = ObjectType.OBJECT_TYPE_ARTICLE.getTypeId();
            List praise_list = qry.query_map(16);
            if (praise_list == null || praise_list.size() == 0) {
                //如果上面的保存成功，则赞的数量不可能是 0
                response.getWriter().print(this.makeJsonReturn(0, ""));
            } else {
                response.getWriter().print(this.makeJsonReturn(0, "" + count, praise_list));
            }

        } else {
            response.getWriter().print(this.makeJsonReturn(2, "你已经赞过啦。"));
        }
        return NONE;
    }

    /**
     * 实现取消赞的功能
     * 
     * <p>
     * 注意：此功能需要用户登录。可以参与的用户是整个用户管理系统中的用户，目前可以仅限制在本地系统中。
     * </p>
     * 
     * @throws IOException
     */
    @SuppressWarnings("rawtypes")
    private String unpraise() throws IOException {
        response.setContentType("application/json");
        if (null == loginUser) {
            response.getWriter().print(this.makeJsonReturn(1, "请先登录。", null));
            return NONE;
        }
        Praise praise = this.praiseService.getPraiseByTypeAndUserId(articleId, ObjectType.OBJECT_TYPE_ARTICLE.getTypeId(), loginUser.getUserId());
        if (praise != null) {
            this.praiseService.deletePraise(praise);
        }
        // 得到赞的数量
        PraiseQuery qry = new PraiseQuery("p.praiseId, u.loginName, u.trueName, u.userIcon");
        qry.objId = this.articleId;
        qry.objType = ObjectType.OBJECT_TYPE_ARTICLE.getTypeId();
        List praise_list = qry.query_map(20);
        if (praise_list == null || praise_list.size() == 0) {
            response.getWriter().print(this.makeJsonReturn(0, "", null));
        } else {
            response.getWriter().print(this.makeJsonReturn(0, "" + qry.count(), praise_list));
        }
        return NONE;
    }

    // 显示内容
    @SuppressWarnings("unchecked")
    private String show() {
        //user.site.article_show
        //此处的cacheService2与cacheService不同。
        CacheService cacheService2 = ContextLoader.getCurrentWebApplicationContext().getBean("cacheService",CacheService.class);
        String cacheKey = "article_" + this.articleId;
        Article article = (Article)cacheService2.get(cacheKey);
        if(article == null){
            article = this.articleService.getArticle(articleId);
            cacheService2.put(cacheKey, article, MemcachedExpireTimeConfig.getSiteIndexExpireTime());
        }
        if (null == article) {
            this.addActionError("无法加载文章对象。");
            this.addActionLink("返回首页", request.getContextPath() + "/");
            return "ERROR_INFO";
        }
        
        //user.site.article_show 如果系统设置了登录才能查看，则需要进行判断
        Config config = configService.getConfigByItemTypeAndName("jitar", "user.site.article_show");
        boolean canViewContent = true;
        if(config != null){
            if(config.getValue().equals("true")){
                if(loginUser == null){
                    canViewContent = false;
                }
            }
        }
        request.setAttribute("canViewContent", canViewContent);
        
        if (article.getDelState() || article.getDraftState() || article.getHideState() == 1 || article.getAuditState() != Article.AUDIT_STATE_OK) {
            this.addActionError("文章设置为待删除、草稿、隐藏、或者未审核，无法显示此文章。");
            this.addActionLink("返回首页", request.getContextPath() + "/");
            return "ERROR_INFO";
        }

        //this.articleService.increaseViewCount(articleId, 1);       
        int count = this.saveObjectCount(ObjectType.OBJECT_TYPE_ARTICLE, this.articleId);
        article.setViewCount(article.getViewCount() + count); //count？
        request.setAttribute("article", article);
        
        // 上一篇、下一篇查询
        PrevNextArticleQuery qry = new PrevNextArticleQuery("a.articleId, a.title, a.typeState");
        qry.articleId = article.getArticleId();
        qry.prev = true;
        HashMap<String, Object> prevArticle = (HashMap<String, Object>) qry.first_map();
        request.setAttribute("prevArticle", prevArticle);
        qry.prev = false;
        HashMap<String, Object> nextArticle = (HashMap<String, Object>) qry.first_map();
        request.setAttribute("nextArticle", nextArticle);
       
        ArticleQuery qryCount = new ArticleQuery("a.articleId");        
        qryCount.userId = article.getUserId();
        if (article.getUserCateId() != null && article.getUserCateId() > 0) {
            request.setAttribute("UserCate", this.categoryService.getCategory(article.getUserCateId()));
            qryCount.userCateId = article.getUserCateId(); 
        }
        else{
            qryCount.userCateId = 0;       
        }
        request.setAttribute("UserCateArticleCount", qryCount.count());
        
       /* if (article.getSysCateId() != null) {
            request.setAttribute("SysCate", this.categoryService.getCategory(article.getSysCateId()));
        }*/
        request.setAttribute("articleAuthor", this.userService.getUserById(article.getUserId(), true));
        this.getHotArticle();
        this.getNewArticle();
        this.getBrowsingHistory();
        this.getArticleComment();
        this.getPraiseList();
        this.getAricleFavorated();
        this.getReportTypeList();
        return SUCCESS;
    }

    /**
     * 检查是否已经收藏过。
     */
    private void getAricleFavorated() {
        if (null == loginUser) {
            return;
        }
        UFavorites favorate = this.favoritesService.getFavorites(ObjectType.OBJECT_TYPE_ARTICLE.getTypeId(), this.articleId, loginUser.getUserId());
        if (favorate != null) {
            request.setAttribute("favorate", "1");
        }
    }

    /**
     * 赞列表
     */
    private void getPraiseList() {
        List<Praise> praiseList = this.praiseService.getPraiseByObjectType(this.articleId, ObjectType.OBJECT_TYPE_ARTICLE.getTypeId());
        request.setAttribute("praiseList", praiseList);

        if (praiseList == null || praiseList.size() == 0) {
            request.setAttribute("praiseCount", 0);
        } else {
            request.setAttribute("praiseCount", praiseList.size());
        }
        // 当前用户是否赞过
        if (loginUser != null) {
            Praise p = this.praiseService.getPraiseByTypeAndUserId(this.articleId, ObjectType.OBJECT_TYPE_ARTICLE.getTypeId(), loginUser.getUserId());
            request.setAttribute("praise", p);

            if (p != null && praiseList != null && praiseList.size() == 1) {
                if (p.getUserId() == praiseList.get(0).getUserId()) {
                    request.setAttribute("praiseCount", -1);
                }
            }
        }
    }
    /**
     * 得到文章评论
     */
    private void getArticleComment() {
        Pager pager = this.params.createPager();
        pager.setCurrentPage(1);
        pager.setPageSize(JitarConst.SHOW_COMMENT_LIST_COUNT);
        CommentQueryParam qry = new CommentQueryParam();
        qry.objectId = this.articleId;
        qry.objectType = ObjectType.OBJECT_TYPE_ARTICLE;
        
        List<Comment> cmt_list = this.commentService.getCommentList(qry, pager);  
        
        List<Comment> reply_list = null;
        for (Comment c : cmt_list) {
            reply_list = this.commentService.getCommentListByObject(ObjectType.OBJECT_TYPE_COMMENT.getTypeId(), c.getId());
            if (null != reply_list && reply_list.size() > 0) {
                c.setReplyList(reply_list);
            }
        }
        request.setAttribute("cmt_list", cmt_list);
    }

    @SuppressWarnings("unchecked")
    private void getHotArticle() {
        String cacheKey = "hot_article_detail_list";
        List<Article> hot_article_detail_list = (List<Article>)cacheService.get(cacheKey);
        if(hot_article_detail_list == null){
            ArticleQuery qry = new ArticleQuery(" a.articleId, a.createDate, a.title, a.typeState ");
            qry.orderType = 2;
            hot_article_detail_list = qry.query_map(10);
            this.cacheService.put(cacheKey, hot_article_detail_list);
        }
        request.setAttribute("hot_article_list", hot_article_detail_list);
        
        //原来的方法。
        
        /*List<WeekViewCountArticle> hot_article_list = (List<WeekViewCountArticle>) this.cache.get(cacheKey);
        if (null == hot_article_list || hot_article_list.size() == 0) {
            hot_article_list = this.articleService.getWeekViewCountArticleList(10);
            this.cache.put(cacheKey, hot_article_list);
        }*/        
    }

    @SuppressWarnings("unchecked")
    private void getNewArticle() {
        String cacheKey = "new_article_detail_list";
        List<Article> new_article_list = (List<Article>) this.cacheService.get(cacheKey);
        if (null == new_article_list || new_article_list.size() == 0) {
            ArticleQuery qry = new ArticleQuery(" a.articleId, a.createDate, a.title, a.typeState ");
            new_article_list = qry.query_map(10);
            this.cacheService.put(cacheKey, new_article_list);
        }
        request.setAttribute("new_article_list", new_article_list);
    }

    /**
     * 浏览历史，鉴于性能问题，采用Redis 实现，只有演示版本和试用版保存到数据库中
     * 
     */
    private void getBrowsingHistory(){
        String cacheKey = ObjectType.OBJECT_TYPE_ARTICLE.getTypeId() + "_" + this.articleId; 
        //如果是登录用户，把登录用户写入到浏览历史中，并按照顺序排到最前面
        if (null != loginUser) {
            browsingService.cacheData(cacheKey, loginUser.getUserId());
        }
        
        List<User> browsingUser = browsingService.getCachedData(cacheKey);
        request.setAttribute("browsingUser", browsingUser);
    }
    
      
    public void setArticleService(ArticleService articleService) {
        this.articleService = articleService;
    }

    public void setReportService(ReportService reportService) {
        this.reportService = reportService;
    }

    public void setPraiseService(PraiseService praiseService) {
        this.praiseService = praiseService;
    }

    public void setBrowsingService(BrowsingService browsingService) {
        this.browsingService = browsingService;
    }

    public void setFavoritesService(UFavoritesService favoritesService) {
        this.favoritesService = favoritesService;
    }

    public void setCategoryService(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public void setCommentService(CommentService commentService) {
        this.commentService = commentService;
    }

    public void setConfigService(ConfigService configService) {
        this.configService = configService;
    }
}
