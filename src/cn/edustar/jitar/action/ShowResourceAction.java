package cn.edustar.jitar.action;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;

import cn.edustar.data.Pager;
import cn.edustar.jitar.JitarConst;
import cn.edustar.jitar.model.Configure;
import cn.edustar.jitar.model.MemcachedExpireTimeConfig;
import cn.edustar.jitar.model.ObjectType;
import cn.edustar.jitar.pojos.AccessControl;
import cn.edustar.jitar.pojos.Browsing;
import cn.edustar.jitar.pojos.Category;
import cn.edustar.jitar.pojos.Comment;
import cn.edustar.jitar.pojos.Praise;
import cn.edustar.jitar.pojos.Report;
import cn.edustar.jitar.pojos.Resource;
import cn.edustar.jitar.pojos.UFavorites;
import cn.edustar.jitar.pojos.User;
import cn.edustar.jitar.service.AccessControlService;
import cn.edustar.jitar.service.BrowsingService;
import cn.edustar.jitar.service.CacheService;
import cn.edustar.jitar.service.CategoryService;
import cn.edustar.jitar.service.CommentQueryParam;
import cn.edustar.jitar.service.CommentService;
import cn.edustar.jitar.service.ConfigService;
import cn.edustar.jitar.service.PraiseQuery;
import cn.edustar.jitar.service.PraiseService;
import cn.edustar.jitar.service.ReportService;
import cn.edustar.jitar.service.ResourceQuery;
import cn.edustar.jitar.service.ResourceService;
import cn.edustar.jitar.service.UFavoritesService;
import cn.edustar.jitar.service.UPunishScoreService;
import cn.edustar.jitar.service.UserService;
import cn.edustar.jitar.util.CommonUtil;

/**
 * 资源显示。
 *  
 * @author baimindong
 *
 */
public class ShowResourceAction extends ManageBaseAction{
	private static final long serialVersionUID = 6231737494723747271L;
	/** 资源服务 */
	private ResourceService res_svc;
	/** 评论服务 */
    private CommentService commentService;
    /** 访问权限服务 */
    private AccessControlService accessControlService;
    /** 资源得分罚分统计服务 */
    private UPunishScoreService pun_svc;
    /** 配置服务 */
    private ConfigService configService;
    /** 赞服务 */
    private PraiseService praiseService;

    /** 收藏夹服务 */
    private UFavoritesService favoritesService;

    /** 浏览历史服务 */
    private BrowsingService browsingService;
    /** 举报服务 */
    private ReportService reportService;
    
    private UserService userService;
    
    private CategoryService categoryService;
    
    private Resource resource;
    
    private CacheService cache;
    
    private User loginUser = null;
    
	@Override
	protected String execute(String cmd) throws Exception {
        Integer resourceId = param_util.getIntParam("resourceId");
        if(resourceId == null || resourceId == 0){
            this.addActionError("无效的标识。");
            this.addActionLink("返回首页", request.getContextPath() + "/");
            return "ERROR_INFO";
        }
        
        loginUser = this.getLoginUser();
        
        //每次操作都进行查询不是好的做法，不是每个cmd都需要这个
        cache = super.cacheService; //JitarContext.getCurrentJitarContext().getCacheProvider().getCache("main");  
        String cacheKey = "resource_" + resourceId;
        resource = (Resource)cache.get(cacheKey);
        if(resource == null){
            resource = this.res_svc.getResource(resourceId);
            cache.put(cacheKey, resource, MemcachedExpireTimeConfig.getSiteIndexExpireTime());
        }
        
        if(resource == null){
            this.addActionError("无法加载该资源。");
            this.addActionLink("返回首页", request.getContextPath() + "/");
        	return "ERROR_INFO";
        }
		
        switch (cmd) {
        case "praise" : // 赞
            return praise();
        case "unpraise" : // 赞
            return unpraise();
        case "report" : // 举报
            return report();
        case "favorite" : // 收藏资源
            return favoriteResource();
        case "unfavorite" : // 收藏资源
            return unFavoriteResource();
        case "comment" :
            return commentResource();
        case "replyCmt" :
            return replyComment();
        case "deleteCmt":
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
        qry.objectId = this.resource.getResourceId();
        qry.objectType = ObjectType.OBJECT_TYPE_RESOURCE;
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
        request.setAttribute("comment_list", cmt_list);
        return "ShowMoreComment";
    }
    
    
    /**
     * 删除评论
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
        if(comment.getUserId() != loginUser.getUserId()){
            response.getWriter().print(this.makeJsonReturn(3, "请不要删除不是自己发布的内容。"));
            return NONE;
        }
        //先删除评论的回复
        this.commentService.deleteCommentReply(comment);
        this.commentService.deleteComment(comment);
        response.getWriter().print(this.makeJsonReturn(0, ""));
        return NONE;
    }
    
    
    /**
     * 回复评论
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
        if(parentId == 0){
            cmt.setObjId(comment.getId());
        }
        else{
            cmt.setObjId(parentId);
        }        
        cmt.setObjType(ObjectType.OBJECT_TYPE_COMMENT.getTypeId());
        cmt.setStar(0);
        cmt.setTitle("回复");
        cmt.setUserId(loginUser.getUserId());
        cmt.setUserName(getLoginUser().getTrueName());
        this.commentService.saveComment(cmt);
        //response.getWriter().print(this.makeJsonReturn(0, "",null));
        //return NONE;
        
        //动态显示回复
        User aboutUser = this.userService.getUserById(cmt.getAboutUserId());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");       
        StringBuffer sb = new StringBuffer();
        sb.append("<dl id='r" + cmt.getId() + "'>");
        sb.append("<dt><a href='" + request.getContextPath() + "/go.action?loginName=" + loginUser.getLoginName() + "' target='_blank'><img src='" + this.SSOServerURL1 + "/upload/" + loginUser.getUserIcon() + "' width='50' border='0' onerror=\"this.src='" + request.getContextPath() + "/images/default.gif'\"/></a></dt>");
        sb.append("<dd>");
        sb.append("  <h4><em class='emDate'>" + sdf.format(cmt.getCreateDate()) + "</em><a target='_blank' href='" + request.getContextPath() + "/go.action?loginName=" + loginUser.getLoginName() + "'>" + loginUser.getTrueName() + "</a></h4>");
        sb.append("  <p class='mt10'>");
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
    private String commentResource() throws IOException {
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

        if (null == resource) {
            response.getWriter().print(this.makeJsonReturn(2, "无法加载资源对象。"));
            return NONE;
        }

        Comment cmt = new Comment();
        cmt.setAboutUserId(resource.getUserId());
        cmt.setAudit(true);
        cmt.setContent(content);
        cmt.setCreateDate(new Date());
        cmt.setIp(CommonUtil.getClientIP(request));
        cmt.setObjId(resource.getResourceId());
        cmt.setObjType(ObjectType.OBJECT_TYPE_RESOURCE.getTypeId());
        cmt.setStar(0);
        cmt.setTitle(resource.getTitle());
        cmt.setUserId(loginUser.getUserId());
        cmt.setUserName(loginUser.getTrueName());
        this.commentService.saveComment(cmt);
        this.res_svc.incResourceCommentCount(resource.getResourceId());
        //response.getWriter().print(this.makeJsonReturn(0, "",null));
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");       
        StringBuffer sb = new StringBuffer();
        sb.append("<dl cmtid='" + cmt.getId() + "'>")
        .append("<dt><a target='_blank' href='" + request.getContextPath() + "/go.action?loginName=" + loginUser.getLoginName() + "'><img src='" + this.SSOServerURL1 + "/upload/" + loginUser.getUserIcon() + "' width='50' onerror=\"this.src='" + request.getContextPath() + "/images/default.gif'\"/></a></dt>")
        .append("<dd>")
        .append("<div class='replyContent' id='replyContent" + cmt.getId() + "'>")
        .append("<div id='r" + cmt.getId() + "'>")
        .append("<h4><em class='emDate'>" +  sdf.format(cmt.getCreateDate()) + "</em><a target='_blank' href='" + request.getContextPath() + "/go.action?loginName=" + loginUser.getLoginName() + "'>" + loginUser.getTrueName() + "</a></h4>")
        .append("<p class='mt10'>")
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
     * 收藏资源
     * 
     * @return
     * @throws IOException
     */
    private String favoriteResource() throws IOException {
        response.setContentType("application/json");
        if (null == loginUser) {
            response.getWriter().print(this.makeJsonReturn(1, "请先登录。"));
            return NONE;
        }
        if (null == resource) {
            response.getWriter().print(this.makeJsonReturn(2, "无法加载资源对象。"));
            return NONE;
        }
        
        UFavorites favorate = new UFavorites();
        favorate.setFavHref(CommonUtil.getSiteUrl(request) + "showResource.action?resourceId=" + resource.getResourceId());
        favorate.setFavTitle(resource.getTitle());
        favorate.setFavUser(loginUser.getUserId());
        favorate.setFavTypeId(0);
        favorate.setObjectId(resource.getResourceId());
        favorate.setObjectType(ObjectType.OBJECT_TYPE_RESOURCE.getTypeId());
        favorate.setFavDate(new Date());
        favoritesService.Save(favorate);
        response.getWriter().print(this.makeJsonReturn(0, "收藏完毕。"));
        return NONE;
    }

    /**
     * 取消收藏
     * 
     * @return
     * @throws IOException
     */
    private String unFavoriteResource() throws IOException {
        response.setContentType("application/json");
        if (null == loginUser) {
            response.getWriter().print(this.makeJsonReturn(1, "请先登录。"));
            return NONE;
        }

        UFavorites favorate = this.favoritesService.getFavorites(ObjectType.OBJECT_TYPE_RESOURCE.getTypeId(), resource.getResourceId(), loginUser.getUserId());
        if (favorate != null) {
            this.favoritesService.Del(favorate);
        }
        response.getWriter().print(this.makeJsonReturn(0, ""));
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
        if (null == resource) {
            response.getWriter().print(this.makeJsonReturn(2, "无法加载资源对象。"));
            return NONE;
        }
        Report report = new Report();
        report.setUserId(loginUser.getUserId());
        report.setCreateDate(new Date());
        report.setObjId(resource.getResourceId());
        report.setObjType(ObjectType.OBJECT_TYPE_RESOURCE.getTypeId());
        String articleTitle = resource.getTitle();
        if (articleTitle.length() > 124) {
            articleTitle = articleTitle.substring(0, 124) + "...";
        }
        report.setObjTitle(articleTitle);
        report.setReportType(reportContent);
        if (reportContent.length() > 0) {
            report.setReportContent(reportContent);
        }
        reportService.saveReport(report);
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
            response.getWriter().print(this.makeJsonReturn(1, "请先登录。",null));
            return NONE;
        }
        Praise praise = praiseService.getPraiseByTypeAndUserId(resource.getResourceId(), ObjectType.OBJECT_TYPE_RESOURCE.getTypeId(), loginUser.getUserId());
        if (praise == null) {
            praise = new Praise();
            praise.setObjType(ObjectType.OBJECT_TYPE_RESOURCE.getTypeId());
            praise.setObjId(resource.getResourceId());
            praise.setUserId(loginUser.getUserId());
            praiseService.savePraise(praise);
            PraiseQuery qry = new PraiseQuery("p.praiseId, u.loginName, u.trueName, u.userIcon");
            qry.objId = resource.getResourceId();
            qry.objType = ObjectType.OBJECT_TYPE_RESOURCE.getTypeId();
            List praise_list = qry.query_map(20);
            if (praise_list == null || praise_list.size() == 0) {
                response.getWriter().print(this.makeJsonReturn(0, ""));
            } else {
                response.getWriter().print(this.makeJsonReturn(0, "" + qry.count(), praise_list));
            }
        }else {
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
            response.getWriter().print(this.makeJsonReturn(1, "请先登录。"));
            return NONE;
        }
        Praise praise = praiseService.getPraiseByTypeAndUserId(resource.getResourceId(), ObjectType.OBJECT_TYPE_RESOURCE.getTypeId(), loginUser.getUserId());
        if (praise != null) {
            praiseService.deletePraise(praise);
        }
        // 得到赞的数量
        PraiseQuery qry = new PraiseQuery("p.praiseId, u.loginName, u.trueName, u.userIcon");
        qry.objId = resource.getResourceId();
        qry.objType = ObjectType.OBJECT_TYPE_RESOURCE.getTypeId();
        List praise_list = qry.query_map(20);
        if (praise_list == null || praise_list.size() == 0) {
            response.getWriter().print(this.makeJsonReturn(0, "", null));
        } else {
            response.getWriter().print(this.makeJsonReturn(0, "" + qry.count(), praise_list));
        }
        return NONE;
    }	
	
    /**
     * 显示资源
     * @return
     */
	private String show(){
	    String cacheKey = ObjectType.OBJECT_TYPE_RESOURCE.getTypeId() + "_" + this.resource.getResourceId();	    
	    //管理员、作者自己始终可以下载、查看；登录用户根据配置是否可以下载
	    boolean canDownlaodResource = false;
	    boolean canViewResource = false;
		
       /* UPunishScore punshScore= this.pun_svc.getUPunishScore(ObjectType.OBJECT_TYPE_RESOURCE.getTypeId() , resource.getResourceId());
        if(punshScore!=null){
            if(punshScore.getScore()<0){
            	setRequestAttribute("scoreCreateUserId", punshScore.getCreateUserId());
            	setRequestAttribute("scoreCreateUserName", punshScore.getCreateUserName());
            	setRequestAttribute("score", -1*punshScore.getScore());
            	setRequestAttribute("scoreReason", punshScore.getReason());
            	setRequestAttribute("scoreDate", punshScore.getPunishDate());
            	setRequestAttribute("scoreObjId", punshScore.getObjId());
            	setRequestAttribute("scoreObjTitle", punshScore.getObjTitle());
            }
        }*/
        
        
        //# 如果是管理者或者作者自己，则可以浏览
        if(loginUser!= null){
            canDownlaodResource = true;
            boolean isAdmin = false;
            List<AccessControl> adminList = accessControlService.getAllAccessControlByUser(loginUser);
            if(adminList.size() > 0){
                isAdmin = true;
            }
            
            //如果是作者自己或者管理员，都可以查看、下载
            if(loginUser.getUserId() == resource.getUserId() || isAdmin == true){   
                canDownlaodResource = true;
                canViewResource = true;
            }else{
                //如果不是管理员、作者自己，并且已经登录了，则看资源的共享属性，
                if(resource.getAuditState() != Resource.AUDIT_STATE_OK || resource.getDelState() == true){
                    this.addActionError("该资源未审核或者待删除。");
                    this.addActionLink("返回首页", request.getContextPath() + "/");
                    return "ERROR_INFO"; 
                }
                if(resource.getShareMode() < Resource.SHARE_MODE_FRIEND){
                    this.addActionError("该资源设置为私有。");
                    this.addActionLink("返回首页", request.getContextPath() + "/");
                    return "ERROR_INFO"; 
                }
                canDownlaodResource = true;
                canViewResource = true;
            }
        }else{
            //未登录用户，只有配置为匿名可下载的情况下才能下载。
        	if(resource.getAuditState() != Resource.AUDIT_STATE_OK || resource.getDelState() == true){
        	    this.addActionError("该资源未审核或者待删除。");
                this.addActionLink("返回首页", request.getContextPath() + "/");
                return "ERROR_INFO";
        	}
        	if(resource.getShareMode() >= Resource.SHARE_MODE_GROUP){
        	    canDownlaodResource = true;
                canViewResource = true;
        	}
        	Configure config = this.configService.getConfigure(); 
        	canDownlaodResource = config.getBoolValue("resource.download",false);
        }
        
        if(!canViewResource){
            this.addActionError("该资源未没有完全共享或者没有权限查看。");
            this.addActionLink("返回首页", request.getContextPath() + "/");
            return "ERROR_INFO";
        }
        
        //# 增加资源的访问量
        //this.res_svc.increaseViewCount(resource.getResourceId()); 
        int count = this.saveObjectCount(ObjectType.OBJECT_TYPE_RESOURCE, resource.getResourceId());
        if (null != loginUser) {
            browsingService.cacheData(cacheKey, loginUser.getUserId());
        }
        resource.setViewCount(resource.getViewCount() + count); //count
        request.setAttribute("resource", resource);
        
       showResourceDetail();
        
        //取得其他资源
        getHotResources();
        getNewResources();
        get_syscate_resource_list();
        get_syscate_resource_Count();
        getPraiseList();
        getFavorated();
        getResourceComment();
        List<User> browsingUser = browsingService.getCachedData(cacheKey);
        request.setAttribute("browsingUser", browsingUser);
        setRequestAttribute("resourcedowload", canDownlaodResource);  
        response.setContentType("text/html; charset=UTF-8");
        return "show";
    }
	
	private void showResourceDetail(){
	    boolean fileExists = false;
	    //先判断资源根文件夹
	    String resourceFolder = request.getSession().getServletContext().getInitParameter("userPath");
	    
	    //资源虚拟路径
	    String resourceFile = CommonUtil.getSiteUrl(request) + resource.getHref();
	    
	    //资源物理路径
	    String resourceFilePath = "";
	    
	    //临时变量
	    String filename = resource.getHref();
	    if (resourceFolder  == null || resourceFolder.trim().length() == 0)
        {
	        resourceFolder = request.getSession().getServletContext().getRealPath("/");
        }
        if (resourceFolder  == null || resourceFolder.trim().length() == 0){
            setRequestAttribute("error", "资源文件所在的根文件夹路径不存在，这可能是配置问题。");
            return ;
        }
        
        if (filename  == null || filename.trim().length() == 0){
            setRequestAttribute("error", "数据库中的资源路径不存在，可能数据库信息不完整或者被管理员删除了。");
            return;
        }        
        
        if(!resourceFolder.endsWith(File.separator)){
            resourceFolder += File.separator;
        }
        
        File fs = new File(resourceFolder + filename);
        if(!fs.isFile() && !fs.exists()){
            setRequestAttribute("error", "资源文件本身不存在，文件内容可能违规被管理员移除<br/>或者文件本身有安全性问题被安全监控软件删除，无法查看和下载。");
            fs = null;
            return;
        }
        fs = null;
        fileExists = true;
        setRequestAttribute("fileExists",fileExists);
        resourceFilePath = resourceFolder + resource.getHref().replace("/",File.separator);
        filename = filename.toLowerCase();
	    try{
            if(filename.endsWith(".jpg") || filename.endsWith(".gif") || filename.endsWith(".png")){
                setRequestAttribute("resType", "image");
                setRequestAttribute("resourceFile", resourceFile);
            }else if(filename.endsWith(".txt")){               
                File ff = new File(resourceFilePath);
                setRequestAttribute("resType", "text");
                setRequestAttribute("content", FileUtils.readFileToString(ff, CommonUtil.getFileEncoding(resourceFilePath)));
                ff = null;
            }
            else if(filename.endsWith(".rar")){
                setRequestAttribute("resType", "rar");
                setRequestAttribute("resourceFile", resourceFile);
            }
            else if( filename.endsWith(".zip")){
                setRequestAttribute("resType", "zip");
                setRequestAttribute("resourceFile", resourceFile);
            }else if(filename.endsWith(".swf")){ 
                //System.out.println("resourceFilePath=" + resourceFilePath);
                //System.out.println("FilenameUtils.getBaseName(resourceFilePath)=" + FilenameUtils.getBaseName(resourceFilePath));
                //System.out.println("FilenameUtils.getFullPath(resourceFilePath)=" + FilenameUtils.getFullPath(resourceFilePath));                
                //System.out.println(FilenameUtils.getFullPath(resourceFilePath) +  FilenameUtils.getBaseName(resourceFilePath) + ".swf");
                String swf = FilenameUtils.getFullPath(resourceFilePath) +  FilenameUtils.getBaseName(resourceFilePath) + ".swf";
                File f = new File(swf);
                if(f.isFile() == true && f.exists()){
                    setRequestAttribute("resType", "swf");
                    setRequestAttribute("resourceFile",FilenameUtils.getFullPath(resourceFile) +  FilenameUtils.getBaseName(resourceFile) + ".swf");
                    if(filename.endsWith("swf")){
                        setRequestAttribute("orginIsSwf", ""); //本身就是swf文件的，不能使用播放器显示。
                    }
                }
                else
                {
                    setRequestAttribute("error", "文件可能被管理员，您可以尝试下载原文件查看。");
                    return;
                }                
            }else if(filename.endsWith(".pdf") 
                    || filename.endsWith(".doc") || filename.endsWith(".docx") 
                    || filename.endsWith(".ppt") || filename.endsWith(".pptx") 
                    || filename.endsWith(".xls") || filename.endsWith(".xlsx") 
                    ){ 
                
                String swf = FilenameUtils.getFullPath(resourceFilePath) +  FilenameUtils.getBaseName(resourceFilePath) + ".swf";
                File f = new File(swf);
                if(f.isFile() == true && f.exists()){
                    setRequestAttribute("resType", "swf");
                    setRequestAttribute("resourceFile",FilenameUtils.getFullPath(resourceFile) +  FilenameUtils.getBaseName(resourceFile) + ".swf");                  
                }
                else
                {
                    setRequestAttribute("error", "<span style='font-size:16px;'>文件无法查看？</span><br/><span style='font-size:14px'>1.文件已上传成功，可能正在进行格式转换，请稍候进行在线预览；<br/>2.或者由于文件格式本身有问题没有转换成功，您可以尝试下载原文件查看。</span>");
                    return;
                } 
            }
	    }catch(Exception ex){
	        setRequestAttribute("error", "处理资源的过程中出现错误。");
	    }
	}
	
	private void getFavorated() {
        if (null == loginUser) {
            return;
        }
        UFavorites favorate = this.favoritesService.getFavorites(ObjectType.OBJECT_TYPE_RESOURCE.getTypeId(), this.resource.getId(), loginUser.getUserId());
        if (favorate != null) {
            request.setAttribute("favorate", "1");
        }
    }

    /**
     * 得资源评论
     */
    private void getResourceComment() {        
        Pager pager = this.params.createPager();
        pager.setCurrentPage(1);
        pager.setPageSize(JitarConst.SHOW_COMMENT_LIST_COUNT);
        CommentQueryParam qry = new CommentQueryParam();
        qry.objectId = this.resource.getResourceId();
        qry.objectType = ObjectType.OBJECT_TYPE_RESOURCE;        
        List<Comment> cmt_list = this.commentService.getCommentList(qry, pager);        
        List<Comment> reply_list = null;
        for (Comment c : cmt_list) {
            reply_list = this.commentService.getCommentListByObject(ObjectType.OBJECT_TYPE_COMMENT.getTypeId(), c.getId());
            if (null != reply_list && reply_list.size() > 0) {
                c.setReplyList(reply_list);
            }
        }
        /*
        
        List<Comment> cmt_list = this.commentService.getCommentListByObject(ObjectType.OBJECT_TYPE_RESOURCE.getTypeId(),resource.getResourceId());
        for (Comment c : cmt_list) {
            List<Comment> reply_list = this.commentService.getCommentListByObject(ObjectType.OBJECT_TYPE_COMMENT.getTypeId(), c.getId());
            if (null != reply_list && reply_list.size() > 0) {
                c.setReplyList(reply_list);
            }
        }*/
        request.setAttribute("comment_list", cmt_list);
    }

    
    // 浏览历史
    @SuppressWarnings("unused")
    private void getBrowsingHistory() {
        List<Browsing> browsingHistory = browsingService.getBrowsingTopList(16, ObjectType.OBJECT_TYPE_RESOURCE.getTypeId(), resource.getResourceId());
        List<User> browsingUser = new ArrayList<User>();
        for (Browsing b : browsingHistory) {
            browsingUser.add(userService.getUserById(b.getUserId(), true));
        }
        request.setAttribute("browsingUser", browsingUser);
    }
    
    //redis view history
    /*private void redisGetBrowSingHistroy(){
    	String cacheKey = ObjectType.OBJECT_TYPE_RESOURCE.getTypeId()+"_"+resource.getResourceId();
    	ShardedJedis jedis = shardedJedisPool.getResource();
    	String redis_userid = jedis.get(cacheKey);
    	String[] userIds = null!=redis_userid?redis_userid.split("\\|"):new String[]{};
    	List<User> browsingUser = new ArrayList<User>();
    	for(String userid : userIds){
    		if(!"".equals(userid.trim())){
    			browsingUser.add(userService.getUserById(Integer.valueOf(userid.trim()), true));
    		}
    	}
    	request.setAttribute("browsingUser", browsingUser);
    	shardedJedisPool.returnResource(jedis);
    }*/
    
	@SuppressWarnings("rawtypes")
    private void getHotResources(){
	    String cacheKey = "hot_resource_list";
        List hot_resource_list = (List) this.cacheService.get(cacheKey);
        if (null == hot_resource_list || hot_resource_list.size() == 0) {
            ResourceQuery qry = new ResourceQuery(
                    "r.userId, r.resourceId, r.title, r.href, r.createDate, r.fsize, r.downloadCount,u.loginName, u.trueName, msubj.msubjName, grad.gradeName, sc.name as scName");
            qry.unitId = null;
            qry.custormAndWhereClause = " r.approvedPathInfo Like '%/" + this.rootUnit.getUnitId() + "/%' And DateDiff(day,r.createDate,getdate()) < 10 ";
            qry.orderType = ResourceQuery.ORDER_TYPE_HOT_DESC;
            hot_resource_list = (List) qry.query_map(10);
            this.cacheService.put(cacheKey, hot_resource_list);
        }
        request.setAttribute("hot_resource_list", hot_resource_list);
	}
	
	@SuppressWarnings("rawtypes")
    private void getNewResources(){
	    String cacheKey = "detail_new_resource_list";
	    List new_resource_list = (List)cache.get(cacheKey);
	    if(new_resource_list == null){
            ResourceQuery qry = new ResourceQuery(" r.resourceId, r.href, r.title, r.downloadCount,r.createDate ");
            qry.orderType = ResourceQuery.ORDER_TYPE_ID_DESC;
            new_resource_list = qry.query_map(10); 
            cache.put(cacheKey, new_resource_list);
	    }
   		setRequestAttribute("new_resource_list", new_resource_list);
	}
	
	private void get_syscate_resource_Count(){
		ResourceQuery qry = new ResourceQuery(" r.resourceId ");
		qry.userId = resource.getUserId();
		if(resource.getUserCateId() != null && resource.getUserCateId() > 0){
			qry.userCateId = resource.getUserCateId();
			Category cat = this.categoryService.getCategory(resource.getUserCateId());
			if(cat != null){
				setRequestAttribute("UserCate", cat);
			}
		}
		else
		{
		    qry.userCateId = 0;
		}
		setRequestAttribute("syscate_resource_Count", qry.count());
	}
	@SuppressWarnings("rawtypes")
    private void get_syscate_resource_list(){
	    String cacheKey = "detail_relative_resource_list";
	    List detail_relative_resource_list = (List)cache.get(cacheKey);
	    if(detail_relative_resource_list == null){
	        ResourceQuery qry = new ResourceQuery(" r.resourceId, r.href, r.title, r.downloadCount,r.createDate ");
	        if(resource.getSysCateId() !=null && resource.getSysCateId() > 0){
	            qry.sysCateId = resource.getSysCateId();
	        }
	        if(resource.getSubjectId() !=null && resource.getSubjectId() > 0){
	            qry.subjectId = resource.getSubjectId();
	        }
	        if(resource.getGradeId() !=null && resource.getGradeId() > 0){
	            qry.gradeId = resource.getGradeId();
	        }
	        qry.orderType = ResourceQuery.ORDER_TYPE_DOWNLOADCOUNT_DESC;
	        detail_relative_resource_list = qry.query_map(31); //多取一个，页面显示的和当前资源比较下，如果有当前资源则不显示当前资源。只显示10条。
	        cache.put(cacheKey, detail_relative_resource_list);
	    }		
   		setRequestAttribute("cate_resource_list", detail_relative_resource_list);
	}
	
	//# 资源下载排行.
	@SuppressWarnings({"rawtypes", "unused"})
    private void get_download_resource_list(){
		ResourceQuery qry = new ResourceQuery(" r.resourceId, r.href, r.title, r.downloadCount,r.createDate ");
	    qry.orderType = ResourceQuery.ORDER_TYPE_DOWNLOADCOUNT_DESC;
	    List download_resource_list = qry.query_map(10); 
   		setRequestAttribute("download_resource_list", download_resource_list);
	} 	
	   /**
     * 赞列表
     */
    private void getPraiseList() {
        List<Praise> praiseList = this.praiseService.getPraiseByObjectType(resource.getResourceId(), ObjectType.OBJECT_TYPE_RESOURCE.getTypeId());
        request.setAttribute("praiseList", praiseList);

        if (praiseList == null || praiseList.size() == 0) {
            request.setAttribute("praiseCount", 0);
        } else {
            request.setAttribute("praiseCount", praiseList.size());
        }
        // 当前用户是否赞过
        if (loginUser != null) {
            Praise p = this.praiseService.getPraiseByTypeAndUserId(resource.getResourceId(), ObjectType.OBJECT_TYPE_RESOURCE.getTypeId(), loginUser.getUserId());
            request.setAttribute("praise", p);

            if (p != null && praiseList != null && praiseList.size() == 1) {
                if (p.getUserId() == praiseList.get(0).getUserId()) {
                    request.setAttribute("praiseCount", -1);
                }
            }
        }
    }	
	
	public ResourceService getResourceService() {
		return res_svc;
	}
	public void setResourceService(ResourceService resourceService) {
		this.res_svc = resourceService;
	}
	public CommentService getCommentService() {
		return commentService;
	}
	public void setCommentService(CommentService commentService) {
		this.commentService = commentService;
	}
	public AccessControlService getAccessControlService() {
		return accessControlService;
	}
	public void setAccessControlService(AccessControlService accessControlService) {
		this.accessControlService = accessControlService;
	}
	public UPunishScoreService getuPunishScoreService() {
		return pun_svc;
	}
	public void setuPunishScoreService(UPunishScoreService uPunishScoreService) {
		this.pun_svc = uPunishScoreService;
	}
	public ConfigService getConfigService() {
		return configService;
	}
	public void setConfigService(ConfigService configService) {
		this.configService = configService;
	}

	public PraiseService getPraiseService() {
		return praiseService;
	}

	public void setPraiseService(PraiseService praiseService) {
		this.praiseService = praiseService;
	}

	public UFavoritesService getFavoritesService() {
		return favoritesService;
	}

	public void setFavoritesService(UFavoritesService favoritesService) {
		this.favoritesService = favoritesService;
	}

	public BrowsingService getBrowsingService() {
		return browsingService;
	}

	public void setBrowsingService(BrowsingService browsingService) {
		this.browsingService = browsingService;
	}
	
    public void setReportService(ReportService reportService) {
        this.reportService = reportService;
    }
    
    public void setUserService(UserService userService){
    	this.userService = userService;
    }
    
    public void setCategoryService(CategoryService categoryService){
    	this.categoryService = categoryService;
    }
    public static void main(String[] args) {
        File fs = new File("H:\\xxx\\xxxxx\\xxxxx.gif");
        System.out.println(fs.exists());
    }
}
