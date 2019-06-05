package cn.edustar.jitar.action;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.chinaedustar.fcs.bi.enums.DeleteSrcEnum;
import com.chinaedustar.fcs.bi.enums.ObjectEnum;
import com.chinaedustar.fcs.bi.service.BiServiceImpl;
import com.chinaedustar.fcs.bi.vo.TaskVo;

import cn.edustar.data.Pager;
import cn.edustar.data.Tuple;
import cn.edustar.jitar.JitarConst;
import cn.edustar.jitar.JitarContext;
import cn.edustar.jitar.model.CategoryTreeModel;
import cn.edustar.jitar.model.Configure;
import cn.edustar.jitar.model.MemcachedExpireTimeConfig;
import cn.edustar.jitar.model.ObjectType;
import cn.edustar.jitar.pojos.AccessControl;
import cn.edustar.jitar.pojos.Browsing;
import cn.edustar.jitar.pojos.Category;
import cn.edustar.jitar.pojos.Channel;
import cn.edustar.jitar.pojos.ChannelUser;
import cn.edustar.jitar.pojos.ChannelVideo;
import cn.edustar.jitar.pojos.Comment;
import cn.edustar.jitar.pojos.Grade;
import cn.edustar.jitar.pojos.Group;
import cn.edustar.jitar.pojos.GroupMember;
import cn.edustar.jitar.pojos.GroupVideo;
import cn.edustar.jitar.pojos.Message;
import cn.edustar.jitar.pojos.MetaSubject;
import cn.edustar.jitar.pojos.Praise;
import cn.edustar.jitar.pojos.Report;
import cn.edustar.jitar.pojos.SpecialSubject;
import cn.edustar.jitar.pojos.UFavorites;
import cn.edustar.jitar.pojos.UPunishScore;
import cn.edustar.jitar.pojos.User;
import cn.edustar.jitar.pojos.Video;
import cn.edustar.jitar.query.VideoQueryParam;
import cn.edustar.jitar.service.AccessControlService;
import cn.edustar.jitar.service.BrowsingService;
import cn.edustar.jitar.service.CacheService;
import cn.edustar.jitar.service.CategoryService;
import cn.edustar.jitar.service.ChannelPageService;
import cn.edustar.jitar.service.CommentQueryParam;
import cn.edustar.jitar.service.CommentService;
import cn.edustar.jitar.service.ConfigService;
import cn.edustar.jitar.service.GroupService;
import cn.edustar.jitar.service.MessageService;
import cn.edustar.jitar.service.PraiseQuery;
import cn.edustar.jitar.service.PraiseService;
import cn.edustar.jitar.service.ReportService;
import cn.edustar.jitar.service.SiteNavigationService;
import cn.edustar.jitar.service.SpecialSubjectService;
import cn.edustar.jitar.service.SubjectService;
import cn.edustar.jitar.service.TagService;
import cn.edustar.jitar.service.UFavoritesService;
import cn.edustar.jitar.service.UPunishScoreService;
import cn.edustar.jitar.service.UserService;
import cn.edustar.jitar.service.VideoQuery;
import cn.edustar.jitar.service.VideoService;
import cn.edustar.jitar.util.CommonUtil;
import cn.edustar.jitar.util.FileCache;

/**
 * 视频管理
 * 
 * @author Yang Xinxin
 * @version 1.0.0 Apr 20, 2009 1:33:25 PM
 */
@SuppressWarnings({ "unchecked", "rawtypes", "serial" })
public class VideoAction extends ManageDocBaseAction {

	/** 日志 */
	private static final Log log = LogFactory.getLog(VideoAction.class);
	
	/** 视频对象 */
	private Video video;
	
	/** 评论对象 */
	private Comment comment;
	
	private File file;
	private String fileFileName;
	private String fileContentType;

	/** 视频服务 */
	private VideoService videoService;
	
	/** 协作组服务 */
	private GroupService group_svc;
	
	/** 学科导航服务 */
	@SuppressWarnings("unused")
	private SiteNavigationService siteNavigationService;
	
	/** 系统分类服务 */
	private CategoryService categoryService;

	/** 评论服务 */
	private CommentService commentService;

	/** 用户服务 */
	private UserService userService;
	
	/** 视频列表 */
	private List<Video> videoList = new ArrayList<Video>();
	
	/** 视频评论 */
	private List<Object[]> videoCommentList = new ArrayList<Object[]>();
	
	/** 标签服务 */
	private TagService tagService;
	
	/** 配置服务 */
	private ConfigService configService;
	
	/** 学科服务 */
	protected SubjectService subj_svc;
	
	/** 罚分服务 */
	protected UPunishScoreService pun_svc;
	
	/** 消息服务 */
	protected MessageService msg_svc;
	
    /** 赞服务 */
    private PraiseService praiseService;

    /** 收藏夹服务 */
    private UFavoritesService favoritesService;

    /** 浏览历史服务 */
    private BrowsingService browsingService;
    /** 举报服务 */
    private ReportService reportService;
    
    private CacheService cacheService;
    
	/** 配置对象 */
	private Configure config;
	
	private SpecialSubjectService specialSubjectService;
	
	private ChannelPageService channelPageService;
	
	private AccessControlService accessControlService;
	
	/** 分页对象 */
	Pager pager = null;
	
	/** 视频Id */
	private int videoId;
	
	/** 视频标题 */
	private String title;

	/** 视频的类型：原创 = 1 = true；转载 = 0 = false */
	private boolean typeState;

	protected String username;

	/**
	 * Default Constructor
	 */
	public VideoAction() {		
		JitarContext jtar_ctxt = JitarContext.getCurrentJitarContext();
		if (jtar_ctxt != null) {
			this.subj_svc = jtar_ctxt.getSubjectService();
		}
	}

	/** 将个人分类放到 request 中. */
	private void putVideoUserCategory() {
		CategoryTreeModel user_video_cate = categoryService.getCategoryTree(CommonUtil.toUserVideoCategoryItemType(getLoginUser().getUserId()));
		setRequestAttribute("user_video_cate", user_video_cate);
	}
	
	private boolean checkVideo(){
		video = videoService.findById(videoId);
		// 如果视频被删除或不存在
		if (null == video) {
			addActionError("视频不存在！");
			return false;
		}		
		return true;
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.edustar.jitar.action.ManageBaseAction#execute(java.lang.String)
	 */
	@Override
	protected String execute(String cmd) throws Exception {
		if ("uploadify".equals(cmd)) {
			return uploadify();
		}		
		
		if(cmd==null || cmd.length() == 0){
			cmd = "show";
		}
		
		//对于前端的ajax操作返回json格式的内容
		if(cmd.equalsIgnoreCase("praise") || cmd.equalsIgnoreCase("unpraise") || cmd.equalsIgnoreCase("favorite") 
		        || cmd.equalsIgnoreCase("unfavorite") || cmd.equalsIgnoreCase("replyCmt") || cmd.equalsIgnoreCase("comment")){
		    if (isUserLogined() == false) {
		       response.setContentType("application/json");
		       response.getWriter().print(this.makeJsonReturn(1, "请先登录。"));
               return NONE;
            }     
		}
		
		if(!cmd.equalsIgnoreCase("show") && !cmd.equalsIgnoreCase("showMoreComment")){
		 // 登录验证
	        if (isUserLogined() == false) {
	            return LOGIN;
	        }	         
		}
		
		if ("show".equals(cmd)) {
			return show();
		}else if ("dest_cate".equals(cmd)) {
			return dest_cate();
		}else if ("channel_cate".equals(cmd)) {
			return channel_cate();
		}else if ("praise".equals(cmd)){ // 赞
			return praise();
		}else if("unpraise".equals(cmd)){  // 取消赞
			return unpraise();
		}else if("report".equals(cmd)){ // 举报
			return report();
		}else if("favorite".equals(cmd)){ // 收藏资源
			return favorite();
		}else if("unfavorite".equals(cmd)){ // 收藏资源
			return unFavorite();
		}else if("comment".equals(cmd)){
			return savecomment();
		}else if("replyCmt".equals(cmd)){
			return replyComment();
		}else if("deleteCmt".equals(cmd)){
			return deleteCmt();
		}else if("showMoreComment".equals(cmd)){
            return showMoreComment();
        }
		// 得到配置对象
		this.config = configService.getConfigure();
		// 以下操作需要用户能够管理内容(未被锁定)
		
		if (canManageBlog(getLoginUser()) == false)
			return ERROR;
		
		if ("upload".equals(cmd)) {
			return upload();
		} else if ("del".equals(cmd)) {
			return delete();
		}  else if ("edit".equals(cmd)) {
			return edit();
		} else if ("save".equals(cmd)) {
			return save();
		} else if ("list".equals(cmd)) {
			return list();
		} else if ("uservideo".equals(cmd)) {
			return uservideo();			
		}  else if ("comment".equals(cmd)) {
			return comment();
		} else if ("comment_list".equals(cmd)) {
			return comment_list();
		} else if ("comment_list_my".equals(cmd)) {
			return comment_list_my();
		} else if ("edit_comment".equals(cmd)) {
			return edit_comment();
		} else if ("save_edit_comment".equals(cmd)) {
			return save_edit_comment();
		} else if ("pub_comment".equals(cmd)) {
			return pub_comment();
		} else if ("pub_group".equals(cmd)) {
			return pub_group();
		} else if("reply_comment".equals(cmd)) {
			reply_comment();
			return comment_list();
		} else if("delete_comment".equals(cmd)) {
			return delete_comment();
		} else if("comment_stat".equals(cmd)) {
			return comment_stat();
		}else if ("delete_videos".equals(cmd)) {
			return delete_videos();
		} 
		
		// 以下操作需要用户是管理员
		if (canAdmin() == false)
			return ERROR;
		
		if ("admin_list".equals(cmd)) {
			return admin_list();
		} else if ("audit_video".equals(cmd)) {
			return audit_video();
		} else if ("unaudit_video".equals(cmd)) {
			return unaudit_video();
		} else if ("comment_admin_list".equals(cmd)) {
			return comment_admin_list();
		} else if ("audit_comment".equals(cmd)) {
			return audit_comment();
		} else if ("unaudit_comment".equals(cmd)) {
			return unaudit_comment();
		} else if ("comment_admin_stat".equals(cmd)) {
			return comment_admin_stat();
		} else if ("pub_to_channel".equals(cmd)) {
            return pub_to_channel();
        }
		
		
		return super.unknownCommand(cmd);
	}
	
	
	
	/**
     * Ajax 显示更多评论
     * @return
     * @throws IOException
     */    
    private String showMoreComment() throws IOException{
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/html");
        if(!checkVideo()){
            response.getWriter().print("不能加载视频对象。");
            return NONE;
        }     
        
        int lastId = this.params.safeGetIntParam("lastId");
        Pager pager = this.params.createPager();
        pager.setCurrentPage(1);
        pager.setPageSize(JitarConst.SHOW_COMMENT_LIST_COUNT);
        CommentQueryParam qry = new CommentQueryParam();
        qry.objectId = this.video.getVideoId();
        qry.objectType = ObjectType.OBJECT_TYPE_VIDEO;
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
    private String deleteCmt() throws IOException {
        int cmtId = this.params.safeGetIntParam("cmtId", 0);
        response.setContentType("application/json");
        if (null == getLoginUser()) {
            response.getWriter().print(this.makeJsonReturn(1, "请先登录。",null));
            return NONE;
        }
        
        Comment comment = this.commentService.getComment(cmtId);
        if (null == comment) {
            response.getWriter().print(this.makeJsonReturn(2, "无法加载评论对象，该评论可能已被删除。",null));
            return NONE;
        }
        if(comment.getUserId() != getLoginUser().getUserId()){
            response.getWriter().print(this.makeJsonReturn(3, "请不要删除不是自己发布的内容。",null));
            return NONE;
        }
        //先删除评论的回复
        this.commentService.deleteCommentReply(comment);
        this.commentService.deleteComment(comment);
        response.getWriter().print(this.makeJsonReturn(0, "",null));
        return NONE;

    }	
	private void get_syscate_video_Count(){
		VideoQuery qry = new VideoQuery(" v.videoId ");
		qry.setUserId(video.getUserId());
		if(video.getUserCateId() != null && video.getUserCateId() > 0){
		    qry.setUserCateId(video.getUserCateId());
		    Category cat = video.getSysCate();
		    setRequestAttribute("UserCate", cat);
		}
		else{
		    qry.setUserCateId(0);
		}
		
		setRequestAttribute("syscate_video_Count", qry.count());
	}	

    /**
     * 删除评论
     * @return
     * @throws IOException
     */
    @SuppressWarnings("unused")
	private String deleteComment() throws IOException {
        int cmtId = this.params.safeGetIntParam("cmtId", 0);
        response.setContentType("application/json");
        if (null == getLoginUser()) {
            response.getWriter().print(this.makeJsonReturn(1, "请先登录。",null));
            return NONE;
        }
        
        Comment comment = this.commentService.getComment(cmtId);
        if (null == comment) {
            response.getWriter().print(this.makeJsonReturn(2, "无法加载评论对象，该评论可能已被删除。",null));
            return NONE;
        }
        if(comment.getUserId() != getLoginUser().getUserId()){
            response.getWriter().print(this.makeJsonReturn(3, "请不要删除不是自己发布的内容。",null));
            return NONE;
        }
        //先删除评论的回复
        this.commentService.deleteCommentReply(comment);
        this.commentService.deleteComment(comment);
        response.getWriter().print(this.makeJsonReturn(0, "",null));
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
        if (null == getLoginUser()) {
            response.getWriter().print(this.makeJsonReturn(1, "请先登录。",null));
            return NONE;
        }
        if (null == content || content.length() == 0) {
            response.getWriter().print(this.makeJsonReturn(3, "请输入内容。",null));
            return NONE;
        }
        Comment comment = this.commentService.getComment(cmtId);
        if (null == comment) {
            response.getWriter().print(this.makeJsonReturn(2, "无法加载评论对象，该评论可能已被删除。",null));
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
        cmt.setUserId(getLoginUser().getUserId());
        cmt.setUserName(getLoginUser().getTrueName());
        this.commentService.saveComment(cmt);
        //response.getWriter().print(this.makeJsonReturn(0, "",null));
        //return NONE;
        
        User aboutUser = this.userService.getUserById(cmt.getAboutUserId());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");       
        StringBuffer sb = new StringBuffer();
        sb.append("<dl id='r" + cmt.getId() + "'>");
        sb.append("<dt><a href='" + request.getContextPath() + "/go.action?loginName=" + getLoginUser().getLoginName() + "' target='_blank'><img src='" + this.SSOServerURL1 + "/upload/" + getLoginUser().getUserIcon() + "' width='50' border='0' onerror=\"this.src='" + request.getContextPath() + "images/default.gif'\"/></a></dt>");
        sb.append("<dd>");
        sb.append("  <h4><em class='emDate'>" + sdf.format(cmt.getCreateDate()) + "</em><a target='_blank' href='" + request.getContextPath() + "/go.action?loginName=" + getLoginUser().getLoginName() + "'>" + getLoginUser().getTrueName() + "</a></h4>");
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
    private String savecomment() throws IOException {
    	if(!checkVideo()){
            response.getWriter().print(this.makeJsonReturn(1, "无法加载视频对象。"));
            return NONE;
    	}       	
        String content = this.params.safeGetStringParam("content");
        response.setContentType("application/json");
        if (null == getLoginUser()) {
            response.getWriter().print(this.makeJsonReturn(1, "请先登录。"));
            return NONE;
        }
        if (null == content || content.length() == 0) {
            response.getWriter().print(this.makeJsonReturn(3, "请输入内容。"));
            return NONE;
        }

        if (null == video) {
            response.getWriter().print(this.makeJsonReturn(2, "无法加载视频对象。"));
            return NONE;
        }

        Comment cmt = new Comment();
        cmt.setAboutUserId(video.getUserId());
        cmt.setAudit(true);
        cmt.setContent(content);
        cmt.setCreateDate(new Date());
        cmt.setIp(CommonUtil.getClientIP(request));
        cmt.setObjId(video.getVideoId());
        cmt.setObjType(ObjectType.OBJECT_TYPE_VIDEO.getTypeId());
        cmt.setStar(0);
        cmt.setTitle(video.getTitle());
        cmt.setUserId(getLoginUser().getUserId());
        cmt.setUserName(getLoginUser().getTrueName());
        this.commentService.saveComment(cmt);
        this.videoService.increseCommentCount(video.getVideoId());
        //response.getWriter().print(this.makeJsonReturn(0, "",null));
        //return NONE;
        //将新的内容动态添加到上面。
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");       
        StringBuffer sb = new StringBuffer();
        sb.append("<dl cmtid='" + cmt.getId() + "'>")
        .append("<dt><a target='_blank' href='" + request.getContextPath() + "/go.action?loginName=" + getLoginUser().getLoginName() + "'><img src='" + this.SSOServerURL1 + "/upload/" + getLoginUser().getUserIcon() + "' width='50' onerror=\"this.src='" + request.getContextPath() + "/images/default.gif'\"/></a></dt>")
        .append("<dd>")
        .append("<div class='replyContent' id='replyContent" + cmt.getId() + "'>")
        .append("<div id='r" + cmt.getId() + "'>")
        .append("<h4><em class='emDate'>" +  sdf.format(cmt.getCreateDate()) + "</em><a target='_blank' href='" + request.getContextPath() + "/go.action?loginName=" + getLoginUser().getLoginName() + "'>" + getLoginUser().getTrueName() + "</a></h4>")
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
    private String favorite() throws IOException {
    	if(!checkVideo()){
            response.getWriter().print(this.makeJsonReturn(1, "视频为空。",null));
            return NONE;
    	}       	
        response.setContentType("application/json");
        if (null == getLoginUser()) {
            response.getWriter().print(this.makeJsonReturn(1, "请先登录。",null));
            return NONE;
        }
        if (null == video) {
            response.getWriter().print(this.makeJsonReturn(2, "无法加载资源对象。",null));
            return NONE;
        }
        
        UFavorites favorate = new UFavorites();
        favorate.setFavHref(CommonUtil.getSiteUrl(request) + "manage/video.action?cmd=show&videoId=" + video.getVideoId());
        favorate.setFavTitle(video.getTitle());
        favorate.setFavUser(getLoginUser().getUserId());
        favorate.setFavTypeId(0);
        favorate.setObjectId(video.getVideoId());
        favorate.setObjectType(ObjectType.OBJECT_TYPE_VIDEO.getTypeId());
        favorate.setFavDate(new Date());
        favoritesService.Save(favorate);
        response.getWriter().print(this.makeJsonReturn(0, "收藏完毕。",null));
        return NONE;
    }

    /**
     * 取消收藏
     * 
     * @return
     * @throws IOException
     */
    private String unFavorite() throws IOException {
    	if(!checkVideo()){
            response.getWriter().print(this.makeJsonReturn(1, "视频为空。",null));
            return NONE;
    	}       	
        response.setContentType("application/json");
        if (null == getLoginUser()) {
            response.getWriter().print(this.makeJsonReturn(1, "请先登录。", null));
            return NONE;
        }

        UFavorites favorate = this.favoritesService.getFavorites(ObjectType.OBJECT_TYPE_VIDEO.getTypeId(), video.getVideoId(), getLoginUser().getUserId());
        if (favorate != null) {
            this.favoritesService.Del(favorate);
        }
        response.getWriter().print(this.makeJsonReturn(0, "", null));
        return NONE;
    }

    /**
     * 举报。
     * 
     * @return
     * @throws IOException
     */
    private String report() throws IOException {
    	if(!checkVideo()){
            response.getWriter().print(this.makeJsonReturn(2, "无法加载资源对象。"));
            return NONE;
    	}       	
        String reportContent = this.params.safeGetStringParam("reportContent");
        response.setContentType("application/json");
        if (null == getLoginUser()) {
            response.getWriter().print(this.makeJsonReturn(1, "请先登录。"));
            return NONE;
        }
        if (null == video) {
            response.getWriter().print(this.makeJsonReturn(2, "无法加载资源对象。"));
            return NONE;
        }
        Report report = new Report();
        report.setUserId(getLoginUser().getUserId());
        report.setCreateDate(new Date());
        report.setObjId(video.getVideoId());
        report.setObjType(ObjectType.OBJECT_TYPE_VIDEO.getTypeId());
        String articleTitle = video.getTitle();
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
    private String praise() throws IOException {
    	if(!checkVideo()){
            response.getWriter().print(this.makeJsonReturn(1, "视频为空。"));
            return NONE;
    	}    	
        response.setContentType("application/json");
        if (null == getLoginUser()) {
            response.getWriter().print(this.makeJsonReturn(1, "请先登录。"));
            return NONE;
        }
        Praise praise = praiseService.getPraiseByTypeAndUserId(video.getVideoId(), ObjectType.OBJECT_TYPE_VIDEO.getTypeId(), getLoginUser().getUserId());
        if (praise == null) {
            praise = new Praise();
            praise.setObjType(ObjectType.OBJECT_TYPE_VIDEO.getTypeId());
            praise.setObjId(video.getVideoId());
            praise.setUserId(getLoginUser().getUserId());
            praiseService.savePraise(praise);
            PraiseQuery qry = new PraiseQuery("p.praiseId, u.loginName, u.trueName, u.userIcon");
            qry.objId = video.getVideoId();
            qry.objType = ObjectType.OBJECT_TYPE_VIDEO.getTypeId();
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
    private String unpraise() throws IOException {
    	if(!checkVideo()){
            response.getWriter().print(this.makeJsonReturn(1, "视频为空。"));
            return NONE;
    	}       	
        response.setContentType("application/json");
        if (null == getLoginUser()) {
            response.getWriter().print(this.makeJsonReturn(1, "请先登录。"));
            return NONE;
        }
        Praise praise = praiseService.getPraiseByTypeAndUserId(video.getVideoId(), ObjectType.OBJECT_TYPE_VIDEO.getTypeId(), getLoginUser().getUserId());
        if (praise != null) {
            praiseService.deletePraise(praise);
        }
        // 得到赞的数量
        PraiseQuery qry = new PraiseQuery("p.praiseId, u.loginName, u.trueName, u.userIcon");
        qry.objId = video.getVideoId();
        qry.objType = ObjectType.OBJECT_TYPE_VIDEO.getTypeId();
        List praise_list = qry.query_map(20);
        if (praise_list == null || praise_list.size() == 0) {
            response.getWriter().print(this.makeJsonReturn(0, ""));
        } else {
            response.getWriter().print(this.makeJsonReturn(0, "" + qry.count(), praise_list));
        }
        return NONE;
    }	
    
    /**
     * 得资源评论
     */
    private void getVideoComment() {
        Pager pager = this.params.createPager();
        pager.setCurrentPage(1);
        pager.setPageSize(JitarConst.SHOW_COMMENT_LIST_COUNT);
        CommentQueryParam qry = new CommentQueryParam();
        qry.objectId = this.video.getVideoId();
        qry.objectType = ObjectType.OBJECT_TYPE_VIDEO;
        List<Comment> cmt_list = this.commentService.getCommentList(qry, pager);  
        List<Comment> reply_list = null;
        for (Comment c : cmt_list) {
            reply_list = this.commentService.getCommentListByObject(ObjectType.OBJECT_TYPE_COMMENT.getTypeId(), c.getId());
            if (null != reply_list && reply_list.size() > 0) {
                c.setReplyList(reply_list);
            }
        }
        request.setAttribute("comment_list", cmt_list);
        
        /*  
        List<Comment> cmt_list = this.commentService.getCommentListByObject(ObjectType.OBJECT_TYPE_VIDEO.getTypeId(),video.getVideoId());
        for (Comment c : cmt_list) {
            List<Comment> reply_list = this.commentService.getCommentListByObject(ObjectType.OBJECT_TYPE_COMMENT.getTypeId(), c.getId());
            if (null != reply_list && reply_list.size() > 0) {
                c.setReplyList(reply_list);
            }
        }
        request.setAttribute("comment_list", cmt_list);
        */
    }

    
    // 浏览历史
    private void getBrowsingHistory() {
        //this.addBrowserUser();
    	String cacheKey = ObjectType.OBJECT_TYPE_VIDEO.getTypeId()+"_"+videoId;
    	if(null!=getLoginUser()){
    		browsingService.cacheData(cacheKey,getLoginUser().getUserId());
    	}
    	List<User> browsingUser = browsingService.getCachedData(cacheKey);
        /*List<Browsing> browsingHistory = browsingService.getBrowsingTopList(16, ObjectType.OBJECT_TYPE_VIDEO.getTypeId(), video.getVideoId());
        List<User> browsingUser = new ArrayList<User>();
        for (Browsing b : browsingHistory) {
            browsingUser.add(userService.getUserById(b.getUserId(), true));
        }*/
        request.setAttribute("browsingUser", browsingUser);
    }
    
	private String dest_cate() {
		int groupId = param_util.getIntParam("groupId");
		String itemType = CommonUtil.toGroupVideoCategoryItemType(groupId);
		CategoryTreeModel gres_categories = this.categoryService.getCategoryTree(itemType);
		setRequestAttribute("gres_categories", gres_categories);
		return "Dest_Cate";
	}		
	
	private String channel_cate() {
		int channelId = param_util.getIntParam("channelId");
		String itemType = CategoryService.CHANNEL_VIDEO_PREFIX + channelId;
		CategoryTreeModel gres_categories = this.categoryService.getCategoryTree(itemType);
		setRequestAttribute("gres_categories", gres_categories);
		return "Channel_Cate";
	}	
	
	/**
	 * 列表
	 *
	 * @return
	 * @throws Exception
	 */
	private String list() throws Exception {
		VideoQueryParam param = new VideoQueryParam();
		param.f = this.param_util.getStringParam("f");
		param.k = this.param_util.getStringParam("k");
		String au = this.param_util.getStringParam("auditState");
		if(au.equals("0")){
			param.auditState = 0;
		}
		else if(au.equals("1")){
			param.auditState = 1;
		}
		else{
			param.auditState = -1;
		}
		param.userId = getLoginUser().getUserId();
		pager = super.getCurrentPager();
		pager.setItemName("视频");
		videoList = videoService.getVideoList(param, pager);
		request.setAttribute("admin", 0);
		if(null == videoList || videoList.size() == 0)
		{
			request.setAttribute("userlist", null);
		}
		else
		{
			//视频的上载用户
			Map userlist = new HashMap();
			Iterator it = videoList.iterator();
			while (it.hasNext()) {
				Video _video = (Video) it.next();
				if(_video.getCategoryId() != null)
				{
				Category sysCate = this.categoryService.getCategory(_video.getCategoryId());
				_video.setSysCate(sysCate);
				}
				int _videoId = _video.getVideoId();
				String userTrueName = getLoginUser().getTrueName();
				userlist.put("v" + _videoId, userTrueName);
			}
			request.setAttribute("userlist", userlist);
		}
		// 获得用户参与的协作组
		putJoinedGroupToRequest(group_svc);		
		this.putUserChannelList();
		
		return LIST_SUCCESS;
	}
	
	/**
	 * 列表
	 *
	 * @return
	 * @throws Exception
	 */
	private String uservideo() throws Exception {
		VideoQueryParam param = new VideoQueryParam();
		int userId=param_util.getIntParam("userId");
		if(userId==0){
			//如果没有传入参数userId则将显示当前用户的资源 
			if(getLoginUser()!=null){
				userId = getLoginUser().getUserId();
			}
		}		
		param.userId = userId;
		param.auditState=0;
		pager = super.getCurrentPager();
		pager.setItemName("视频");
		videoList = videoService.getVideoList(param, pager);
		//视频的上载用户
		Map userlist = new HashMap();
		Iterator it = videoList.iterator();
		while (it.hasNext()) {
			Video _video = (Video) it.next();
			if(_video.getCategoryId() != null)
			{
			Category sysCate = this.categoryService.getCategory(_video.getCategoryId());
			_video.setSysCate(sysCate);
			}
			int _videoId = _video.getVideoId();
			String userTrueName = getLoginUser().getTrueName();
			userlist.put("v" + _videoId, userTrueName);
		}
		request.setAttribute("userlist", userlist);		
		return "List_UserVideo";
	}	
	private String admin_list() throws Exception {
		VideoQueryParam param = new VideoQueryParam();
		param.f = this.param_util.getStringParam("f");
		param.k = this.param_util.getStringParam("k");
		String au = this.param_util.getStringParam("auditState");
		if(au.equals("0")){
			param.auditState = 0;
		}
		else if(au.equals("1")){
			param.auditState = 1;
		}
		else{
			param.auditState = -1;
		}
		param.userId = null;
		String auditState=param_util.safeGetStringParam("auditState");
		String _type=param_util.safeGetStringParam("type");
		if(_type.equals("unaudit")){
			param.auditState=1;
		}
		else{
			if(auditState=="") auditState="-1";
			param.auditState=Short.parseShort(auditState);
		}
		String f=param_util.safeGetStringParam("f");
		String k=param_util.safeGetStringParam("k");
		param.f=f;
		param.k=k;
		pager = super.getCurrentPager();
		pager.setItemName("视频");
		pager.setPageSize(10);
		videoList = videoService.getVideoList(param, pager);
		
		//视频的上载用户
		Map userlist=new HashMap();
		Iterator it = videoList.iterator();
		while(it.hasNext()) {
			Video _video = (Video) it.next();
			if(_video.getCategoryId() != null)
			{
			Category sysCate = this.categoryService.getCategory(_video.getCategoryId());
			_video.setSysCate(sysCate);
			}
			int _videoId=_video.getVideoId();
			int userId=_video.getUserId();
			User u=userService.getUserById(userId);
			String userTrueName="未知";
			if(u!=null) userTrueName=u.getTrueName();
			userlist.put("v"+_videoId, userTrueName);
		}
		request.setAttribute("userlist", userlist);
		request.setAttribute("admin", 1);
		request.setAttribute("f", f);
		request.setAttribute("k", k);
		request.setAttribute("auditState", auditState);
		request.setAttribute("type", _type);
		return ADMIN_LIST_SUCCESS;
	}
	
	/**
	 * 视频播放
	 *
	 * @return
	 * @throws Exception
	 */
	private String show() throws Exception {		
		if(!checkVideo()){
		    return ERROR;
		}		
		// 判断视频的转换状态，并更新
		if (-1 == video.getStatus()) {
			addActionError("视频不合法！");
			return ERROR;
		}
		
		// 第一次访问，先去查
		if (0 == video.getStatus() || 3 == video.getStatus()) {
		 // 原始视频的后缀
	        String suffix = video.getHref().substring(video.getHref().lastIndexOf(".") + 1, video.getHref().length()).toLowerCase();
			String flv = "";
			if (CommonUtil.isContainsString("play", suffix)) {
				flv = video.getHref().substring(0, video.getHref().lastIndexOf(".")) + ".jpg";
			} else {
				flv = video.getHref().substring(0, video.getHref().lastIndexOf(".")) + ".flv";
			}
			
			String result = "";
			try{
			    result = new BiServiceImpl().getTaskStatus(flv) + "";
			}
			catch(Exception ex){
			    ex.printStackTrace();
			    result = "-2";
			}
			if ("-2".equals(result)) {
                videoService.updateVideoStatus(videoId, -1);
                addActionError("发送任务发生异常。请检查配置。");
                return ERROR;
            } else if ("-1".equals(result)) {
				videoService.updateVideoStatus(videoId, -1);
				addActionError("视频不合法！");
				return ERROR;
			} else if ("1".equals(result)) {
				videoService.updateVideoStatus(videoId, 1);
			} else if ("2".equals(result)) {
				videoService.updateVideoStatus(videoId, 2);
				addActionError("视频转换未完成，请稍后访问...");
				return ERROR;
			} else if ("3".equals(result)) {
				videoService.updateVideoStatus(videoId, 3);
				addActionError("视频正在转换，请稍后访问...");
				return ERROR;
			} else {
				addActionError("无法识别的视频！");
				return ERROR;
			}
		}
		
		User loginuser = getLoginUser();
		if(null == loginuser){
		    request.setAttribute("error", "此视频需要登录才能观看。");
		}
		if (1 == video.getAuditState()) {
			// 判断权限，如果是管理员，或者上载者 可以看			
			boolean bPlay = false;
			if(null != loginuser){			    
    			List<AccessControl> la = accessControlService.getAllAccessControlByUser(loginuser);
    			if(la != null && la.size()>0) {
    				bPlay = true;
    			}
    			
    			if (video.getUserId() == loginuser.getUserId()) {
    				bPlay=true;
    			}
			}
			
			if (!bPlay) {
				request.setAttribute("error", "视频未审核！");
				
			}
		}

		//下面这段在显示视频的时候需要吗？
		
		/*UPunishScore punshScore= pun_svc.getUPunishScore(ObjectType.OBJECT_TYPE_VIDEO.getTypeId() , videoId);
        if(punshScore!=null){
            if(punshScore.getScore()<0){
                request.setAttribute("scoreCreateUserId", punshScore.getCreateUserId());
                request.setAttribute("scoreCreateUserName", punshScore.getCreateUserName());
                request.setAttribute("score", -1*punshScore.getScore());
                request.setAttribute("scoreReason", punshScore.getReason());
                request.setAttribute("scoreDate", punshScore.getPunishDate());
                request.setAttribute("scoreObjId", punshScore.getObjId());
                request.setAttribute("scoreObjTitle", punshScore.getObjTitle());
            }
        }*/
		//视频的上载用户
		int gradeId = video.getGradeId();
		if (gradeId != 0) {
			Grade grade = subj_svc.getGrade(gradeId);
			if(grade != null){
			    request.setAttribute("gradeName", grade.getGradeName());
			}			
		}
			
		int userId = video.getUserId();
		User u = userService.getUserById(userId);
		String userTrueName = "未知";
		if(u != null) {
		    userTrueName = u.getTrueName();
		}
		request.setAttribute("userName", userTrueName);
		request.setAttribute("loginName", u.getLoginName());
		
		flvHref = video.getFlvHref();
		flvThumbNailHref = video.getFlvThumbNailHref();
		//siteNavigationService.renderSiteNavition();
		// 增加视频播放次数,未审核的视频不增加播放次数了
		if (video.getAuditState() != 1) {
			//videoService.increseViewCount(videoId);
			int count = this.saveObjectCount(ObjectType.OBJECT_TYPE_VIDEO, videoId);
			video.setViewCount(video.getViewCount() + count);
		}
		
		if (video.getCategoryId() != null) {
			Category sysCate = this.categoryService.getCategory(video.getCategoryId());
			video.setSysCate(sysCate);
		}
		
		request.setAttribute("video", video);
		
		getVideoComment();
        getPraiseList();
        getBrowsingHistory();
        get_syscate_video_Count();
        get_syscate_video_list();
        get_new_video_list();
        get_hot_video_list();
        getFavorated();
		return "Show_Success";
	}
	
	@SuppressWarnings("unused")
    private void addBrowserUser(){
	    User loginUser =  getLoginUser();
        if (null != loginUser) {
            Browsing browsing = browsingService.getBrowsing(ObjectType.OBJECT_TYPE_VIDEO.getTypeId(), this.videoId, loginUser.getUserId());
            if (null != browsing) {
                this.browsingService.deleteBrowsing(browsing);
            }
            browsing = new Browsing(ObjectType.OBJECT_TYPE_VIDEO.getTypeId(), this.videoId, loginUser.getUserId());
            this.browsingService.saveBrowsing(browsing);
        }
	}
	
	private void getFavorated() {
        if (null == getLoginUser()) {
            return;
        }
        UFavorites favorate = this.favoritesService.getFavorites(ObjectType.OBJECT_TYPE_VIDEO.getTypeId(), this.video.getVideoId(), getLoginUser().getUserId());
        if (favorate != null) {
            request.setAttribute("favorate", "1");
        }
    }
	
	private void get_hot_video_list(){
	    String cacheKey = "hot_video_list";
	    List video_list = (List)this.cacheService.get(cacheKey);
	    if(null == video_list){
	        VideoQuery qry = new VideoQuery("v.videoId, v.title, v.createDate, v.href, v.flvHref, v.userId, u.loginName, u.nickName, v.summary,v.flvThumbNailHref");
	        qry.setOrderType(VideoQuery.ORDER_TYPE_VIEWCOUNT_DESC);
	        video_list = qry.query_map(6); 
	        if(video_list != null){
                this.cacheService.setExpireTime(MemcachedExpireTimeConfig.getSiteIndexExpireTime());
                this.cacheService.put(cacheKey, video_list);
            }
	    }		
   		setRequestAttribute("hot_video_list", video_list);
	}
	
	private void get_new_video_list(){
	    String cacheKey = "new_video_list";
	    List video_list = (List)this.cacheService.get(cacheKey);
	    if(null == video_list){
    		VideoQuery qry = new VideoQuery("v.videoId, v.title, v.createDate, v.href, v.flvHref, v.userId, u.loginName, u.nickName, v.summary,v.flvThumbNailHref");
    		qry.setOrderType(VideoQuery.ORDER_TYPE_VIDEOID_DESC);
    	    video_list = qry.query_map(18); 
    	    if(video_list != null){
    	        this.cacheService.setExpireTime(MemcachedExpireTimeConfig.getSiteIndexExpireTime());
    	        this.cacheService.put(cacheKey, video_list);
    	    }
	    }
   		setRequestAttribute("new_video_list", video_list);
	}
	
	private void get_syscate_video_list(){
	    String cacheKey = "cate_video_list";
	    List cate_video_list = (List)this.cacheService.get(cacheKey);
	    if(cate_video_list == null){
    		VideoQuery qry = new VideoQuery(" v.videoId, v.href, v.flvHref, v.title, v.downloadCount,v.createDate, v.flvThumbNailHref ");
    		if(video.getCategoryId() !=null && video.getCategoryId() > 0){
    			qry.setCategoryId(video.getCategoryId());
    		}
    		if(video.getSubjectId() !=null && video.getSubjectId() > 0){
    			qry.setSubjectId(video.getSubjectId());
    		}
    		if(video.getGradeId() !=null && video.getGradeId() > 0){
    			qry.setGradeId(video.getGradeId());
    		}
    	    qry.orderType = VideoQuery.ORDER_TYPE_CREATEDATE_DESC;
    	    cate_video_list = qry.query_map(18); 
    	    if(cate_video_list != null){
        	    this.cacheService.setExpireTime(MemcachedExpireTimeConfig.getSiteIndexExpireTime());
        	    this.cacheService.put(cacheKey, cate_video_list);
    	    }
	    }	    
   		setRequestAttribute("cate_video_list", cate_video_list);
	}
	
 /**
  * 赞列表
  */
 private void getPraiseList() {
     List<Praise> praiseList = this.praiseService.getPraiseByObjectType(video.getVideoId(), ObjectType.OBJECT_TYPE_VIDEO.getTypeId());
     request.setAttribute("praiseList", praiseList);

     if (praiseList == null || praiseList.size() == 0) {
         request.setAttribute("praiseCount", 0);
     } else {
         request.setAttribute("praiseCount", praiseList.size());
     }
     // 当前用户是否赞过
     if (getLoginUser() != null) {
         Praise p = this.praiseService.getPraiseByTypeAndUserId(video.getVideoId(), ObjectType.OBJECT_TYPE_VIDEO.getTypeId(), getLoginUser().getUserId());
         request.setAttribute("praise", p);

         if (p != null && praiseList != null && praiseList.size() == 1) {
             if (p.getUserId() == praiseList.get(0).getUserId()) {
                 request.setAttribute("praiseCount", -1);
             }
         }
     }
 }		
	/**
	 * 评论列表
	 *
	 * @return
	 * @throws Exception
	 */
	private String comment_list() throws Exception {
		CommentQueryParam param = new CommentQueryParam();
		param.objectType = ObjectType.OBJECT_TYPE_VIDEO;
		if(videoId!=0)
			param.objectId=videoId;
		else
			param.aboutUserId = getLoginUser().getUserId();
		param.audit = null;
		pager = super.getCurrentPager();
		pager.setItemName("评论");
		videoCommentList = commentService.getUserVideoCommentListEx(param, pager);
		request.setAttribute("admin", 0);
		request.setAttribute("my", 0);
		return "Comment_List_Success";
	}

	
	private String comment_list_my() throws Exception {
		CommentQueryParam param = new CommentQueryParam();
		param.objectType = ObjectType.OBJECT_TYPE_VIDEO;
		param.userId = getLoginUser().getUserId();
		param.audit = null;
		pager = super.getCurrentPager();
		pager.setItemName("评论");
		videoCommentList = commentService.getUserVideoCommentListEx(param, pager);
		request.setAttribute("admin", 0);
		request.setAttribute("my", 1);
		return "Comment_List_Success";
	}
	
	private String comment_admin_list() throws Exception {
		CommentQueryParam param = new CommentQueryParam();
		param.objectType = ObjectType.OBJECT_TYPE_VIDEO;
		param.aboutUserId = null;
		param.audit = null;
		pager = super.getCurrentPager();
		pager.setItemName("评论");
		videoCommentList = commentService.getUserVideoCommentListEx(param, pager);
		request.setAttribute("admin", 1);
		request.setAttribute("my", 0);
		return "Comment_List_Success";
	}
	
	/**
	 * 视频评论
	 *
	 * @return
	 * @throws Exception
	 */
	private String comment() throws Exception {
		CommentQueryParam param = new CommentQueryParam();
		param.objectType = ObjectType.OBJECT_TYPE_VIDEO;
		param.objectId = videoId;
		//param.aboutUserId = getLoginUser().getUserId();
		param.audit = null;
		pager = super.getCurrentPager();
		pager.setItemName("评论");
		videoCommentList = commentService.getUserVideoCommentList(param, pager);
		video = videoService.findById(videoId);
		return "Comment_list";
	}
	
	/**
	 * 编辑评论
	 * @return
	 */
	private String edit_comment(){
      String id = param_util.getStringParam("commentId");
      if(id==""){
      	addActionError("没有选择视频评论");
      	return ERROR;
      }
      
		Comment _comment = commentService.getComment(Integer.parseInt(id));
		if(_comment==null)
		{
      	addActionError("没有找到视频评论");
      	return ERROR;
		}
		setRequestAttribute("comment", _comment);
		return "Edit_Comment";
	}
	
	private boolean getCommentAndCheck() {
		// 得到评论.
		int cmt_id = param_util.getIntParam("id");
		this.comment = commentService.getComment(cmt_id);
		if (comment == null) {
			addActionError("未找到指定标识的评论");
			return false;
		}

		// 验证用户有权限修改此评论.
		if (comment.getObjType() != ObjectType.OBJECT_TYPE_VIDEO.getTypeId()) {
			addActionError("不支持的评论目标对象");
			return false;
		}
		video = videoService.findById(comment.getObjId());
		if (video == null) {
			addActionError("被评论的视频已不存在");
			return false;
		}
		if ((video.getUserId() != getLoginUser().getUserId()) && (comment.getUserId() != getLoginUser().getUserId())) {
			addActionError("权限不足, 被评论的视频不是当前登录用户的,或者评论人不是当前登录用户");
			return false;
		}

		setRequestAttribute("comment", comment);
		setRequestAttribute("video", video);

		return true;
	}	
	/**
	 * 保存一个编辑了的评论.
	 * 
	 * @return
	 */
	private String save_edit_comment() {
		if (getCommentAndCheck() == false)
			return ERROR;

		// 得到提交的参数.
		String commentContent = param_util.safeGetStringParam("commentContent", "");
		String[] screen_keyword = tagService.parseTagList((String) this.config.getValue("site.screen.keyword")); // 系统中定义的需要屏蔽的非法词汇数组
		String screen_enalbed = (String) this.config.getValue("site.screen.enalbed"); // 是否屏蔽
		String screen_replace = (String) this.config.getValue("site.screen.replace"); // 替换的字符
		
		// 是否屏蔽系统中出现的非法词汇
		if ("true".equals(screen_enalbed)) {
			for (int i = 0; i < screen_keyword.length; ++i) {
				if (commentContent.indexOf(screen_keyword[i]) != -1) {
					commentContent = commentContent.replaceAll(screen_keyword[i], screen_replace);
					//comment.setContent(commentReply);
				}
			}
		}

		// 构造回复.
		comment.setContent(commentContent);

		// 保存回复.
		if(comment.getContent().trim().length() == 0)
		{
			addActionError("请输入评论内容");
			return ERROR;
		}
		commentService.saveComment(comment);

		addActionMessage("修改评论成功完成");

		return SUCCESS;
	}
	
	/**
	 * 保存视频的评论
	 * 
	 * @return
	 * @throws Exception
	 */
	private String pub_comment() throws Exception {
		//comment.setAboutUserId(getLoginUser().getUserId());
		video = videoService.findById(videoId);
		comment.setTitle("Re:"+video.getTitle());
		comment.setAboutUserId(video.getUserId());
		comment.setAudit(true);
		comment.setCreateDate(new Date());
		comment.setIp(request.getRemoteAddr());
		comment.setObjId(param_util.safeGetIntParam("videoId"));
		comment.setObjType(ObjectType.OBJECT_TYPE_VIDEO.getTypeId());
		comment.setStar(param_util.safeGetIntParam("star", 1));
		comment.setUserId(getLoginUser().getUserId());
		comment.setUserName(getLoginUser().getTrueName());
		if(comment.getContent().trim().length() == 0)
		{
			addActionError("请输入评论内容。");
	      	return ERROR;
		}
		commentService.saveComment(comment);

		// 更新视频的评论数
		videoService.increseCommentCount(videoId);
		
		return comment();
	}
	
	/**
	 * 对评论的回复
	 * @return
	 * @throws Exception
	 */
	private String reply_comment() throws Exception {
	   int cmtId = param_util.safeGetIntParam("cmtId");
	   String cmtContent = param_util.getStringParam("content");
	   cmtContent = "<div class='commentReply'><div>以下为 " + getLoginUser().getTrueName() + " 的回复：</div><div>" + cmtContent + "</div></div>";
	   Comment _comment = commentService.getComment(cmtId);
	   _comment.setContent(_comment.getContent() + cmtContent);
	   commentService.saveComment(_comment);
	   return SUCCESS;
	}

	/**
	 * 删除评论
	 * @return
	 */
	private String delete_comment(){
      String ids = param_util.safeGetStringParam("commentId");
      if(ids.equals("")){
      	addActionError("没有选择评论");
      	return ERROR;
      }
      String[] aid=ids.split(",");
      for(int i=0;i<aid.length;i++){
      	if(aid[i]!=""){
      		Comment _comment = commentService.getComment(Integer.parseInt( aid[i]));
      		if(_comment!=null)
      		{
      			
      			//	罚分
      			String score = param_util.getStringParam("score");
      			String reason = param_util.getStringParam("reason");
      			UPunishScore upun;
      	      if (score.equals(null) || score.equals(""))
      	         upun =pun_svc.createUPunishScore(ObjectType.OBJECT_TYPE_COMMENT.getTypeId(),_comment.getId(),_comment.getUserId(),0,"",getLoginUser().getUserId(),getLoginUser().getTrueName());
      	      else
      	      	if(Float.parseFloat(score)<0)
      	      		upun =pun_svc.createUPunishScore(ObjectType.OBJECT_TYPE_COMMENT.getTypeId(),_comment.getId(),_comment.getUserId(),-1*Float.parseFloat(score),"",getLoginUser().getUserId(),getLoginUser().getTrueName());
      	      	else
      	      		upun =pun_svc.createUPunishScore(ObjectType.OBJECT_TYPE_COMMENT.getTypeId(),_comment.getId(),_comment.getUserId(),Float.parseFloat(score),reason,getLoginUser().getUserId(),getLoginUser().getTrueName());
      	      
      	      pun_svc.saveUPunishScore(upun);
      			//消息提醒
      	      Message message=new Message();
      	      message.setSendId(getLoginUser().getUserId());
      	      message.setReceiveId(_comment.getUserId());
      	      message.setTitle("删除了您的评论及扣分信息");
      	      if (score.equals(null) || score.equals(""))
      	      	message.setContent("您的评论"+_comment.getTitle()+"被删除");	
      	      else	
	      	      if (reason!="")
	      	        message.setContent("您的评论"+_comment.getTitle()+"被删除,扣"+ upun.getScore()+"分,原因:"+reason);
	      	      else  
	      	        message.setContent("您的评论"+_comment.getTitle()+"被删除,扣"+upun.getScore()+"分");
      	      msg_svc.sendMessage(message);
      	      
      			commentService.deleteComment(_comment);
      		}
      	}
      }
		//删除首页缓存
		FileCache fc = new FileCache();
		fc.deleteUserAllCache(getLoginUser().getLoginName());
		fc = null;
      return SUCCESS;
	}
	/**
	 * 视频未通过审核
	 * @return
	 */	
	
	private String unaudit_video(){
		List<Integer> ids = param_util.safeGetIntValues("vId");
      if(ids==null){
      	addActionError("没有选择视频");
      	return ERROR;
      }

      for(int i=0;i<ids.size();i++){

      		Video _video = videoService.findById(ids.get(i));
      		if(_video!=null)
      		{
      			videoService.unauditVideo(_video);
      		}
  
      }
      return SUCCESS;		
	}
	/**
	 * 视频通过审核
	 * @return
	 */	
	private String audit_video(){
      List<Integer> ids = param_util.safeGetIntValues("vId");
      if(ids==null){
      	addActionError("没有选择视频");
      	return ERROR;
      }
     
      for(int i=0;i<ids.size();i++){

      		Video _video = videoService.findById(ids.get(i));
      		if(_video!=null)
      		{
      			videoService.auditVideo(_video);
      		}

      }
      return SUCCESS;		
	}
	/**
	 * 评论通过审核
	 * @return
	 */
	private String audit_comment(){
      String ids = param_util.getStringParam("commentId");
      if(ids==""){
      	addActionError("没有选择评论");
      	return ERROR;
      }
      String[] aid=ids.split(",");
      for(int i=0;i<aid.length;i++){
      	if(aid[i]!=""){
      		Comment _comment = commentService.getComment(Integer.parseInt( aid[i]));
      		if(_comment!=null)
      		{
      			commentService.auditComment(_comment);
      		}
      	}
      }
      return SUCCESS;
	}
	/**
	 * 评论未通过审核
	 * @return
	 */
	private String unaudit_comment(){
      String ids = param_util.getStringParam("commentId");
      if(ids==""){
      	addActionError("没有选择评论");
      	return ERROR;
      }
      String[] aid=ids.split(",");
      for(int i=0;i<aid.length;i++){
      	if(aid[i]!=""){
      		Comment _comment = commentService.getComment(Integer.parseInt( aid[i]));
      		if(_comment!=null)
      		{
      			commentService.unauditComment(_comment);
      		}
      	}
      }
      return SUCCESS;
	}
	
	/**
	 * 重新统计评论数量
	 * @return
	 */
	private String comment_stat(){
		int userId=getLoginUser().getUserId();
		videoService.updateVideoCommentCount(userId);
		return SUCCESS;
	}
	
	private String comment_admin_stat(){
		videoService.updateAllVideoCommentCount();
		return SUCCESS;
	}
	
	/**
	 * 删除
	 *
	 * @return
	 * @throws Exception
	 */
	private String delete() throws Exception {
		videoId = this.param_util.safeGetIntParam("vId");
		Video video = videoService.findById(videoId);
		if (null == video) {
			this.addActionError(this.getText("视频不存在。"));
			return list();
		}
		if (video.getUserId() != getLoginUser().getUserId()) {
			this.addActionError(this.getText("你不能删除别人的视频。"));
			return list();
		}
		videoService.delVideo(video);
		addActionError("删除成功。");
		return list();
	}
	
	private String delete_videos() throws Exception {
      List<Integer> ids = param_util.safeGetIntValues("vId");

      if(ids==null){
      	addActionError("没有选择视频");
      	return ERROR;
      }
      //判断权限????
      
     
      for(int i=0;i<ids.size();i++){
      	
      		Video video = videoService.findById(ids.get(i));
      		if(video!=null)
      		{
      			upunish_Msg(video);
      			//删除协作组的视频
      			this.group_svc.deleteGroupVideoByVideo(video);
      			videoService.delVideo(video);
      		}
      	
      }
      return SUCCESS;
	}
	
	/**
	 * 删除视频的罚分处理
	 * @param video
	 */
	private void upunish_Msg(Video video)	{
		//	罚分
		String score = param_util.getStringParam("score");
		String reason = param_util.getStringParam("reason");
		if (reason==null)
			reason="系统删除";
		UPunishScore upun;
      if (score.equals(null) || score=="")
      {
         upun =pun_svc.createUPunishScore(ObjectType.OBJECT_TYPE_VIDEO.getTypeId(),video.getId(),video.getUserId(),getLoginUser().getUserId(),getLoginUser().getTrueName());
      }
      else
      {
    	 if(Float.parseFloat(score)<0){ 
    		 upun =pun_svc.createUPunishScore(ObjectType.OBJECT_TYPE_VIDEO.getTypeId(),video.getId(),video.getUserId(),-1*Float.parseFloat(score),reason,getLoginUser().getUserId(),getLoginUser().getTrueName());
    	 }
    	 else{
    		 	upun =pun_svc.createUPunishScore(ObjectType.OBJECT_TYPE_VIDEO.getTypeId(),video.getId(),video.getUserId(),Float.parseFloat(score),reason,getLoginUser().getUserId(),getLoginUser().getTrueName());
    	 }
      }
      pun_svc.saveUPunishScore(upun);
		//消息提醒
      Message message=new Message();
      message.setSendId(getLoginUser().getUserId());
      message.setReceiveId(video.getUserId());
      message.setTitle("管理员删除了您的视频及扣分信息");
      if (reason!="")
        message.setContent("您的 "+video.getTitle()+" 被删除,扣"+ upun.getScore()+"分,原因:"+reason);
      else  
        message.setContent("您的 "+video.getTitle()+" 被删除,扣"+upun.getScore()+"分");
      msg_svc.sendMessage(message);
	}
	
	/** 计算群组视频树分类名字 */
	private String getGroupVideoCategoryItemType(int groupId) {
		return CommonUtil.toGroupVideoCategoryItemType(groupId);
	}
	
	/**
	 * 上传
	 *
	 * @return
	 * @throws Exception
	 */
	private String upload() throws Exception {
		pubSpecialSubjectList();
		putVideoSystemCategory();
		putMetaSubjectList();
		putGradeList();
		putVideoUserCategory(); //得到用户自己的视频分类
		
		
		setRequestAttribute("videoId", 0);
		User loginUser = getLoginUser();
		putUserChannelList();
		
		
		setRequestAttribute("userSubjectId", loginUser.getSubjectId());
		setRequestAttribute("userGradeId", loginUser.getGradeId());
		//传递来的参数。如果是1，是其他模块调用的 ,需要设置 window.returnvalve=videoid
		int needreturn= param_util.getIntParam("needreturn");
		setRequestAttribute("needreturn", needreturn);
		
		Integer groupId = param_util.getIntParamZeroAsNull("groupId");  //上载到协助组
		if (groupId != null) {
			setRequestAttribute("groupId", groupId);
		} else {
			setRequestAttribute("groupId", 0);
		}
		
		CategoryTreeModel res_cate=null;
		if (groupId != null) {
			res_cate = categoryService.getCategoryTree(getGroupVideoCategoryItemType(groupId));
		}
		setRequestAttribute("res_cate", res_cate);
		
		// 获得用户参与的协作组
		putJoinedGroupToRequest(group_svc);	
		
		return EDIT_SUCCESS;
	}
	
	/**
	 * 将系统的视频分类放到 request 中
	 */
	private void putVideoSystemCategory() {
		CategoryTreeModel videoCategory = categoryService.getCategoryTree(CategoryService.VIDEO_CATEGORY_TYPE);
		setRequestAttribute("videoCategory", videoCategory);
	}
	
	private void pubSpecialSubjectList() {
		List<SpecialSubject> sl = (List<SpecialSubject>)specialSubjectService.getValidSpecialSubjectList();
		setRequestAttribute("specialSubjectList", sl);
	}
	
	/** 将学科列表放到 request 中. */
	protected void putMetaSubjectList() {
		List<MetaSubject> subject_list = subj_svc.getMetaSubjectList();
		setRequestAttribute("subject_list", subject_list);
	}
	
	/** 把学段列表放到 request 中. */
	/*TODO:检查这里，使用了基类的方法
	protected void putGradeList() {
		Object grade_list = subj_svc.getGradeList();
		request.setAttribute("gradeList", grade_list);
	}	
	*/
	
	private void putUserChannelList()
	{
		User loginUser = super.getLoginUser();
		if(loginUser != null)
		{
			List<Channel> channel_List = this.channelPageService.getChannelListByUserId(loginUser.getUserId());
			if(channel_List != null && channel_List.size() > 0) setRequestAttribute("channel_List", channel_List);
		}
	}
	
	/**
	 * 废弃
	 * @param channel
	 */
	@SuppressWarnings("unused")
	private void putChannelVideoCategoryToRequest(Channel channel) {
		Object cate_tree = categoryService.getCategoryTree("channel_video_" + channel.getChannelId());
		setRequestAttribute("channel_video_categories", cate_tree);
	}
	
	/**
	 * 编辑
	 *
	 * @return
	 * @throws Exception
	 */
	private String edit() throws Exception {
		putVideoSystemCategory();
		pubSpecialSubjectList();
		putVideoUserCategory(); //得到用户自己的视频分类
		int adminvalue=param_util.safeGetIntParam("admin");
		Video video = videoService.findById(videoId);
		setRequestAttribute("video", video);
		setRequestAttribute("videoId", videoId);
		putMetaSubjectList();
		putGradeList();
		User loginUser = getLoginUser();		
		setRequestAttribute("userSubjectId", loginUser.getSubjectId());
		setRequestAttribute("userGradeId", loginUser.getGradeId());
		setRequestAttribute("admin", adminvalue);
		return EDIT_SUCCESS;
	}
	
	/**
	 * Flash 上传
	 * 
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unused")
	private String uploadify() throws Exception {
		int obj;
		PrintWriter out = response.getWriter();
		User user = userService.getUserByLoginName(username);
		// 视频配置
		ServletContext sc = request.getSession().getServletContext();
		String fileConfigPath = sc.getInitParameter("videoUploadPath");
		String mappingPath = sc.getInitParameter("mappingPath");
		// 前缀
		String prefix = String.format("%1$tY%1$tm%1$td%1$tH%1$tM%1$tS%1$tL", new Date());
		// 后缀
		String suffix = fileFileName.substring(fileFileName.lastIndexOf(".") + 1, fileFileName.length()).toLowerCase();
		// 最后合成视频的原始全路径
		String destString = new StringBuffer().append(fileConfigPath).append(File.separator).append(prefix).append(".").append(suffix).toString();
		
		// 仅仅是验证上传文件的后缀在不在系统支持的范围内
		if (!CommonUtil.isContainsString("video", suffix)) {
			out.print("e");
			out.flush();
			out.close();
			return NONE;
		}
		
		// 检查目标文件所在的目录是否存在
		File destFile = new File(destString);
		// 如果目标文件所在的目录不存在，则创建父目录
		if (!destFile.getParentFile().exists()) {
			if (!destFile.getParentFile().mkdirs()) {
				System.out.println("创建目标文件所在的目录失败！");
				out.print("e");
				out.flush();
				out.close();
				return NONE;
			}
		}
		
		// 文件上传，同时也适应 Linux 环境
		FileUtils.copyFile(file, destFile);
		
		// 保存
		Video video = new Video();
		video.setUserId(user.getUserId());
		video.setTitle(FilenameUtils.getBaseName(fileFileName));
		video.setAddIp(request.getRemoteAddr());
		video.setUnitId(user.getUnitId());
		Configure conf = JitarContext.getCurrentJitarContext().getConfigService().getConfigure();
		boolean bCheck = conf.getBoolValue(Configure.BEFAULE_PUBLISH_CHECK, false);
		if (bCheck) {
			video.setAuditState(Video.AUDIT_STATE_WAIT_AUDIT);
		} else {
			video.setAuditState(Video.AUDIT_STATE_OK);
		}
		video.setHref(destString);

		StringBuffer fcs_tar = new StringBuffer().append(fileConfigPath).append(File.separator).append(prefix);
		if (CommonUtil.isContainsString("play", suffix)) {
			video.setFlvHref(new StringBuffer().append(mappingPath).append(prefix).append(".").append(suffix).toString());
			fcs_tar.append(".").append(suffix).toString();
			obj = 3;
		} else {
			video.setFlvHref(new StringBuffer().append(mappingPath).append(prefix).append(".flv").toString());
			fcs_tar.append(".flv").toString();
			obj = 1;
		}
		String thumbNail = new StringBuffer().append(mappingPath).append(prefix).append(".jpg").toString();
		thumbNail = thumbNail.replaceAll("\\\\", "\\/");
		video.setFlvThumbNailHref(thumbNail);
		videoService.save(video);

		// 转换服务
		TaskVo taskVo;
		if (CommonUtil.isContainsString("play", suffix)) {
			taskVo = new TaskVo(video.getTitle(), destString,fileConfigPath + File.separator + prefix + ".jpg", "150*120",ObjectEnum.TASK_OBJECT_IMAGE);
			taskVo.setDeleteSrcEnum(DeleteSrcEnum.NOT_DELETE);
		} else {
			taskVo = new TaskVo(video.getTitle(), destString,fcs_tar.toString(), "150*120", ObjectEnum.TASK_OBJECT_HD);
			taskVo.setDeleteSrcEnum(DeleteSrcEnum.DELETE);
		}
		new BiServiceImpl().send(taskVo);
		
		// 返回
		out.print(video.getVideoId());
		out.flush();
		out.close();
		return NONE;
	}
	
	/**
	 * 保存并进行视频转换
	 * 
	 * @return
	 * @throws Exception
	 */
	private String save() throws Exception {
		Boolean saveNew = param_util.existParam("saveNew");
		int videoId = param_util.safeGetIntParam("videoId");
		// System.out.println("返回的视频Id：" + videoId);
		if(0 == videoId) { 
			return saveNewVideo();
		} else {
			Video video = videoService.findById(videoId);
			String href = video.getHref();
			href = href.replaceAll("\\/", "\\" + File.separator);
			
			UpdateVideo();
			
			publishVideoToGroups(video);		
			if(saveNew) {
				publishVideoToChannel(video);
			}
			int needreturn = param_util.getIntParam("needreturn");
			if(1 == needreturn) {
				setRequestAttribute("returnvideoId", "" + video.getVideoId());
				return "Upload_Video_ReturnId";
			} else {
			    Integer groupId = param_util.safeGetIntParam("groupId");
				int adminValue = param_util.safeGetIntParam("admin");
				// this.addActionMessage("视频：<font style='color: #FF0000; font-weight: bold;'>" + fileFileName + "</font> 修改成功！");
				this.addActionMessage("视频：<font style='color: #FF0000; font-weight: bold;'>" + video.getTitle() + "</font> 操作成功！");
				if(groupId > 0){
				    this.addActionLink("上传视频", "?cmd=upload&groupId=" + groupId);
				}
				else{
				    this.addActionLink("上传视频", "?cmd=upload");
				}
				
				if (adminValue == 0) {
					this.addActionLink("视频管理", "?cmd=list");
				} else {
					this.addActionLink("视频管理", "?cmd=admin_list");
				}				
				if(groupId > 0){
				    this.addActionLink("小组视频管理", "groupVideo.action?cmd=list&groupId="+groupId);
				}
				return SUCCESS;
			}
		}
	}
	
	/**
	 * 发布到组
	 * 
	 * @return
	 */
	private String pub_group() {
		List<Integer> video_ids = param_util.safeGetIntValues("vId");
		if (video_ids == null || video_ids.size() == 0 || 
				(video_ids.size() == 1 && video_ids.get(0).intValue() == 0)) {
			addActionError("未选择或给出要操作的视频");
			return ERROR;
		}		

		// 得到参数 groupId - 目标协作组; groupCateId - 目标协作组分类
		int groupId = param_util.getIntParam("groupId");
		Integer groupCateId = param_util.getIntParamZeroAsNull("groupCateId");
		if (groupId == 0) {
			addActionError("未选择要发布的目标协作组.");
			return ERROR;
		}

		// 验证组的存在性于合法性
		Group group = group_svc.getGroup(groupId);
		if (super.checkGroupState(group) == false) {
			return ERROR;
		}

		// 验证当前用户是这个组的成员，并且有权限发布资源到该组
		GroupMember gm = group_svc.getGroupMemberByGroupIdAndUserId(group.getGroupId(), getLoginUser().getUserId());
		if (super.checkGroupMemberState(gm, group, getLoginUser()._getUserObject()) == false) {
			return ERROR;
		}

		// 循环操作
		int oper_count = 0;
		for (Integer videoId : video_ids) {
			Video video = videoService.findById(videoId);
			if (video == null) {
				addActionError("未找到标识为 " + videoId + " 的视频！");
				continue;
			}
			// 验证是这个用户的, (权限验证), 以及视频不是私有的
			if (video.getUserId() != getLoginUser().getUserId()) {
				addActionError("不能发布他(她)人的视频！");
				continue;
			}

			// 验证是否已经发布过了
			Tuple<Video, GroupVideo> gr_t = group_svc.getGroupVideoByVideoId(group.getGroupId(), video.getVideoId());
			if (gr_t != null) {
				addActionError("视频：" + video.toDisplayString() + "，已经发布到了协作组：" + group.getGroupTitle());
				continue;
			}

			GroupVideo gv = new GroupVideo();
			gv.setGroupId(groupId);
			gv.setGroupCateId(groupCateId);
			gv.setVideoId(video.getId());
			gv.setUserId(video.getUserId());
			gv.setPubDate(new Date());
			gv.setIsGroupBest(false);
			group_svc.publishVideoToGroup(gv);
			addActionMessage("视频 ：" + video.toDisplayString() + "，成功发布到了协作组：" + group.toDisplayString());		
			
			++oper_count;
		}
		addActionMessage("共发布了：" + oper_count + " 个视频到协作组：" + group.getGroupTitle());
		return SUCCESS;
	}
	
	/**
	 *同时发布到协作组中 
	 * @param video
	 */
	private void publishVideoToGroups(Video video) {
		Integer _groupIndex = param_util.safeGetIntParam("_groupIndex");
		if(_groupIndex==0)
			_groupIndex=1;

		// 进行循环操作
		for (int i=1;i<=_groupIndex;i++) {
			Integer groupId=param_util.getIntParamZeroAsNull("gpId"+i);
			Integer groupCateId=param_util.getIntParamZeroAsNull("groupCateId"+i);	
			if (groupId == null){
				continue;
			}
			// 得到协作组并验证
			Group group = group_svc.getGroupMayCached(groupId);
			if (group == null) {
				addActionError("指定标识的协作组不存在 groupId = " + groupId);
				continue;
			}
			if (checkGroupState(group._getGroupObject()) == false) {
				//System.out.println("该组不允许增加内容");
				continue;
			}

			// 得到成员关系并验证
			GroupMember gm = group_svc.getGroupMemberByGroupIdAndUserId(groupId, getLoginUser().getUserId());
			if (this.checkGroupMemberState(gm, group._getGroupObject(), getLoginUser()._getUserObject()) == false) {
				continue;
			}

			// 是否已经发布了
			GroupVideo gv = group_svc.getGroupVideoByGroupAndVideo(groupId, video.getVideoId());
			if (gv != null) {
				addActionError("您已经将视频：" + video.getTitle() + "，发布到了协作组：" + group.toDisplayString() + ", 不需再次发布！");
				continue;
			}

			gv = new GroupVideo();
			gv.setGroupId(groupId);
			gv.setGroupCateId(groupCateId);
			gv.setVideoId(video.getId());
			gv.setUserId(video.getUserId());
			gv.setPubDate(new Date());
			gv.setIsGroupBest(false);
			group_svc.publishVideoToGroup(gv);
			addActionMessage("视频 ：" + video.toDisplayString() + "，成功发布到了协作组：" + group.toDisplayString());
		}
	}
	
	
	private void publishVideoToChannel(Video video)
	{
		if(video == null) return;
		User loginedUser =  this.getLoginUser();
		if(loginedUser == null) return;
		//如果没有频道，就不处理了。
		if(!param_util.existParam("channelTotalCount")) return;
		int channelTotalCount = param_util.safeGetIntParam("channelTotalCount");
		for(int i = 0;i<channelTotalCount + 1; i++)
		{
			Integer channelId = param_util.getIntParamZeroAsNull("channelId" + i);
			String channelCate = param_util.getStringParam("channelCate" + i, null);
			String channelCateId = null;
			if(channelId != null)
			{
				//检查是否已经存在，这里是新加，一般不存在，可以不用检查。
				ChannelVideo channelVideo = this.channelPageService.getChannelVideoByChannelIdAndVideoId(channelId, video.getVideoId());
				if(channelVideo != null){
					continue;
				}
				if(channelCate != null){
					 String[] cateArray = channelCate.split("/");
					 if(cateArray.length > 1)
					 {
						 channelCateId = cateArray[cateArray.length - 1];
					 }
					 if(CommonUtil.isInteger(channelCateId) == false){
						 channelCateId = null;
					 }
				}
				if(channelCateId == null){
					channelCate = null;
				}
				ChannelVideo cv = new ChannelVideo();
				cv.setChannelCate(channelCate);
				cv.setChannelCateId(channelCateId == null?null:Integer.valueOf(channelCateId));
				cv.setChannelId(channelId);
				cv.setVideoId(video.getVideoId());
				cv.setUnitId(loginedUser.getUnitId());
				cv.setUserId(loginedUser.getUserId());
				this.channelPageService.saveOrUpdateChannelVideo(cv);
			}			
		}
	}
	
	
	@SuppressWarnings("unused")
	private String saveNewVideo() throws Exception {
		// 验证是否选择了上传文件
		if (null == file || 0 == file.length()) {
			this.addActionError("请选择一个视频文件！");
			return upload();
		} else {
			// 输出最原始的视频信息
			for (int i = 0; i < this.file.length(); ++i) {
				log.debug("上传的视频：" + this.file + ", name=" + this.fileFileName + ", content-type=" + this.fileContentType);
			}
		}
		Video video = collectVideoData();		
		
		File tmpFile = this.file;
		File dest = new File(video.getHref());
		
		log.info("最后的全路径：" + dest);
		log.info("最后的全路径：" + dest.getAbsolutePath());
		
		ServletContext sc = request.getSession().getServletContext();
		log.info("最后的flvHref:" + sc.getInitParameter("mappingPath") + flvHref.substring(flvHref.lastIndexOf("\\") + 1, flvHref.length()).toLowerCase());
		video.setFlvHref(sc.getInitParameter("mappingPath") + flvHref.substring(flvHref.lastIndexOf("\\") + 1, flvHref.length()).toLowerCase());
		video.setFlvThumbNailHref(flvThumbNailHref.replaceAll("\\\\", "/"));
		
		Integer subjectId = param_util.safeGetIntParam("subjectId");
		Integer gradeId = param_util.safeGetIntParam("gradeId");
		video.setGradeId(gradeId);
		video.setSubjectId(subjectId);		
		video.setSpecialSubjectId(param_util.getIntParamZeroAsNull("specialSubjectId"));
		// 根据配置,是否先审核后发布
		Configure conf = JitarContext.getCurrentJitarContext().getConfigService().getConfigure();
		boolean bCheck = conf.getBoolValue(Configure.BEFAULE_PUBLISH_CHECK, false);
		log.info("bCheck = " + bCheck);
		if(bCheck) {
			video.setAuditState(Video.AUDIT_STATE_WAIT_AUDIT);
		} else {
			video.setAuditState(Video.AUDIT_STATE_OK);
		}
		
		// 保存
		videoService.save(video);		
		publishVideoToGroups(video);
		
		int needreturn= param_util.getIntParam("needreturn");
		if(needreturn==1)
		{
			setRequestAttribute("returnvideoId", ""+video.getVideoId());
			return "Upload_Video_ReturnId";
		}else{		
		
		this.addActionMessage("视频：<font style='color: #FF0000; font-weight: bold;'>" + fileFileName + "</font> 上传成功！");
		this.addActionLink("上传视频", "?cmd=upload");
		this.addActionLink("视频管理", "?cmd=list");
		Integer groupId = param_util.safeGetIntParam("groupId");
		this.addActionLink("小组视频管理", "groupVideo.action?cmd=list&groupId="+groupId);
		
		return SUCCESS;
		}
	}
	
	private String UpdateVideo() throws Exception {
		int _videoId=param_util.safeGetIntParam("videoId");
		Video video = videoService.findById(_videoId);
		title = param_util.safeGetStringParam("title");
		if ("".equals(title) || title == null || title.length() < 0) {
			title = video.getTitle();
		}
		video.setTitle(FilenameUtils.getBaseName(title));
		video.setTags(param_util.safeGetStringParam("tags"));
		video.setCategoryId(param_util.safeGetIntParam("categoryId",null));
		video.setUserCateId(param_util.safeGetIntParam("userCateId",null));
		video.setSummary(param_util.safeGetStringParam("summary"));
		video.setTypeState(param_util.safeGetBooleanParam("typeState"));
		Integer subjectId = param_util.getIntParamZeroAsNull("subjectId");
		Integer specialSubjectId = param_util.getIntParamZeroAsNull("specialSubjectId");
		/*video.setChannelCate(param_util.safeGetStringParam("channelCate").equals("")?null:param_util.safeGetStringParam("channelCate"));
		if(video.getChannelCate() == null)
		{
			video.setChannelId(null);
			video.setChannelCateId(null);
		}
		else
		{
			video.setChannelCateId(CommonUtil.getLastInteger(video.getChannelCate()));
			video.setChannelId(super.getLoginUser().getChannelId());
		}*/
		if(subjectId==null)
			subjectId=0;
		Integer gradeId = param_util.getIntParamZeroAsNull("gradeId");
		if(gradeId==null)
			gradeId=0;
		video.setGradeId(gradeId);
		video.setSubjectId(subjectId);
		video.setSpecialSubjectId(specialSubjectId);
		
		videoService.save(video);
		return LIST_SUCCESS;
	}
	
	/**
	 * 收集视频数据
	 *
	 * @param video
	 * @return
	 */
	private Video collectVideoData() {
		Video video = new Video();
		video.setUserId(getLoginUser().getUserId());
		title = param_util.safeGetStringParam("title");
		if ("".equals(title) || title == null || title.length() < 0) {
			title = "";
		}
		boolean nameAsTitle = param_util.getBooleanParam("nameAsTitle");
		video.setTitle(title);
		if (nameAsTitle || title.length() == 0) {
			video.setTitle(FilenameUtils.getBaseName(fileFileName));
		}
		video.setTags(param_util.safeGetStringParam("tags"));
		video.setCategoryId(param_util.safeGetIntParam("categoryId",null));
		video.setUserCateId(param_util.safeGetIntParam("userCateId",null));
		video.setSummary(param_util.safeGetStringParam("summary"));
		video.setAddIp(request.getRemoteAddr());
		video.setUnitId(super.getLoginUser().getUnitId());		
		//video.setChannelCate(param_util.safeGetStringParam("channelCate").equals("")?null:param_util.safeGetStringParam("channelCate"));
		video.setAuditState(Short.parseShort("1"));
		// 根据web.xml配置获取路径
		ServletContext sc = request.getSession().getServletContext();
		String fileConfigPath = sc.getInitParameter("videoUploadPath");
		String prefix = String.format("%1$tY%1$tm%1$td%1$tH%1$tM%1$tS%1$tL", new Date());
		String suffix = fileFileName.substring(fileFileName.lastIndexOf(".") + 1, fileFileName.length()).toLowerCase();
		String destString = new StringBuffer().append(fileConfigPath).append(File.separator).append(prefix).append(".").append(suffix).toString();
		log.info("上传的文件：" + destString);
		video.setHref(destString);
		video.setTypeState(param_util.safeGetBooleanParam("typeState"));
		return video;
	}

	protected List<Integer> video_ids;
	protected final boolean getVideoIds() {
        this.video_ids = param_util.safeGetIntValues("vId");
        if (video_ids == null || video_ids.size() == 0 || (video_ids.size() == 1 && video_ids.get(0).intValue() == 0)) {
            this.addActionError("未选择或给出要操作的视频");
            return false;
        }
        return true;
    }
	
	private String pub_to_channel(){
        // 获得标识参数
        if (this.getVideoIds() == false){
            return ERROR;
        }
        // 得到参数 channelId:要发布的自定义频道; channelCateId：自定义频道视频分类
        int channelId = param_util.safeGetIntParam("channelId");
        if (channelId == 0) {
            addActionError("选择的自定义频道错误。");
            return ERROR;
        }
        
        Channel c = this.channelPageService.getChannel(channelId);
        if(null == c){
            addActionError("不能加载所选择的自定义频道信息。");
            return ERROR;
        }
        
        //检查协作组成员关系
        ChannelUser cu = this.channelPageService.getChannelUserByUserIdAndChannelId(getLoginUser().getUserId(), channelId);
        if(cu == null){
            addActionError("您不是该自定义频道的成员。");
            return ERROR;
        }
        
        //得到自定义频道文章分类 /xx/xxx/这样的格式
        String channelCate = param_util.safeGetStringParam("channelCateId",null);       
        
        if(null == channelCate){
            addActionError("没有选择自定义频道文章分类。");
            return ERROR;
        }
        String channelCateId = null;
        String[] cateArray = channelCate.split("/");
        if(cateArray.length > 1) {
            channelCateId = cateArray[cateArray.length - 1];
        }
        if(CommonUtil.isInteger(channelCateId) == false){
            channelCateId = null;
        }
        if(channelCateId == null){
            channelCate = null;
        }
        // 对选中的进行操作，需要先判断是否在这个分类中
        int oper_count = 0;
        for (int videoId : this.video_ids) {
                // 获得要操作的文章
                Video v = this.videoService.findById(videoId);
                if (v == null) {
                    addActionError("未找到标识为 " + videoId + " 的视频");
                    continue;
                }
                // 验证是这个用户的, (权限验证)
                if (v.getUserId() != getLoginUser().getUserId()) {
                    addActionError("权限被拒绝, 试图操作他人的视频.");
                    continue;
                }
                
                Boolean toUpdated = false;
                ChannelVideo cv = this.channelPageService.getChannelVideoByChannelIdAndVideoId(channelId, videoId);
                if(null != cv ){
                    //更新文章分类
                    //为了方便比较
                    if(cv.getChannelCate() == null) cv.setChannelCate("");
                    if(cv.getChannelCateId() == null) cv.setChannelCateId(0);
                    
                    if(channelCateId == null){
                        if(!cv.getChannelCate().equals(channelCate) || cv.getChannelCateId() != null)
                        {
                            toUpdated = true;           
                            cv.setChannelCateId(null);
                        }
                    }
                    else
                    {
                        if(!cv.getChannelCate().equals(channelCate) || !cv.getChannelCateId().equals(Integer.valueOf(channelCateId))){
                            toUpdated = true;
                            cv.setChannelCateId(Integer.valueOf(channelCateId));
                        }
                    }
                    if(toUpdated){
                        cv.setChannelCate(channelCate);
                        if(cv.getChannelCate() == null || cv.getChannelCate().equals("")) cv.setChannelCate(null);
                        if(cv.getChannelCateId() == null || cv.getChannelCateId().equals(0)) cv.setChannelCateId(null);
                        
                        this.channelPageService.saveOrUpdateChannelVideo(cv);
                        addActionMessage("视频 " + v.getTitle() + " 更新了视频分类.");
                    }
                    else{
                        addActionError("视频 " + v.getTitle() + " 已经发布到了频道  " + c.getTitle());
                    }                   
                    continue;
                }
                
                // 发布文章到频道
                ChannelVideo channelVideo = new ChannelVideo();
                channelVideo.setChannelCate(channelCate);
                channelVideo.setChannelId(channelId);
                channelVideo.setChannelCateId(channelCateId == null?null:Integer.valueOf(channelCateId));
                channelVideo.setUnitId(this.getLoginUser().getUnitId());
                channelVideo.setUserId(v.getUserId());
                channelVideo.setVideoId(videoId);
                this.channelPageService.saveOrUpdateChannelVideo(channelVideo); 
                // 消息.
                addActionMessage("视频 " + v.getTitle() + " 成功发布到自定义频道 " + c.getTitle() + " 中.");
                ++oper_count;
            }
        addActionMessage("共发布了 " + oper_count + " 个视频到自定义频道 " + c.getTitle() + ".");
        return SUCCESS;
    }
	
	
	/** 视频服务的set方法 */
	public void setVideoService(VideoService videoService) {
		this.videoService = videoService;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public void setFileFileName(String fileFileName) {
		this.fileFileName = fileFileName;
	}

	public void setFileContentType(String fileContentType) {
		this.fileContentType = fileContentType;
	}

	/** 视频标题 */
	public String getTitle() {
		return title;
	}

	/** 视频标题 */
	public void setTitle(String title) {
		this.title = title;
	}

	/** 视频的类型：原创 = 1 = true；转载 = 0 = false */
	public boolean isTypeState() {
		return typeState;
	}

	/** 视频的类型：原创 = 1 = true；转载 = 0 = false */
	public void setTypeState(boolean typeState) {
		this.typeState = typeState;
	}

	/** 视频列表 */
	public List<Video> getVideoList() {
		return videoList;
	}

	/** 分页对象 */
	public Pager getPager() {
		return pager;
	}

	/** 视频Id */
	public int getVideoId() {
		return videoId;
	}

	/** 视频Id */
	public void setVideoId(int videoId) {
		this.videoId = videoId;
	}

	/**
	 * 视频对象
	 *
	 * @return
	 */
	public Video getVideo() {
		return video;
	}

	/**
	 * 视频对象
	 *
	 * @param video
	 */
	public void setVideo(Video video) {
		this.video = video;
	}

	/**
	 * 评论对象 
	 * 
	 * @return
	 */
	public Comment getComment() {
		return comment;
	}

	/**
	 * 评论对象
	 * 
	 * @param comment
	 */
	public void setComment(Comment comment) {
		this.comment = comment;
	}

	/**
	 * 学科导航服务的set方法
	 * 
	 * @param siteNavigationService
	 */
	public void setSiteNavigationService(SiteNavigationService siteNavigationService) {
		this.siteNavigationService = siteNavigationService;
	}

	/**
	 * 用户服务的set方法
	 * @param userService
	 */
	public void setUserService(UserService userService){
		this.userService=userService;
	}
	/**
	 * 评论服务的set方法
	 * 
	 * @param commentService
	 */
	public void setCommentService(CommentService commentService) {
		this.commentService = commentService;
	}

	/**
	 * 系统分类服务的set方法
	 *
	 * @param categoryService
	 */
	public void setCategoryService(CategoryService categoryService) {
		this.categoryService = categoryService;
	}

	public void setSpecialSubjectService(SpecialSubjectService specialSubjectService) {
		this.specialSubjectService = specialSubjectService;
	}

	public void setChannelPageService(ChannelPageService channelPageService) {
		this.channelPageService = channelPageService;
	}

	/** 标签服务的'set'方法 */
	public void setTagService(TagService tagService) {
		this.tagService = tagService;
	}
	
	/** 配置服务的'set'方法 */
	public void setConfigService(ConfigService configService) {
		this.configService = configService;
	}
	
	/** 学科服务 */
	public void setSubjectService(SubjectService subj_svc) {
		this.subj_svc = subj_svc;
	}

	/** 罚分服务 */
	public void setUPunishScoreService(UPunishScoreService pun_svc) {
		this.pun_svc = pun_svc;
	}

	/** 消息服务 */
	public void setMessageService(MessageService msg_svc) {
		this.msg_svc = msg_svc;
	}
	
	public void setCacheService(CacheService cacheService) {
        this.cacheService = cacheService;
    }

    /** 协作组服务的set方法 */
	public void setGroupService(GroupService group_svc) {
		this.group_svc = group_svc;
	}
	
	/**
	 * 视频评论列表
	 * 
	 * @return
	 */
	public List<Object[]> getVideoCommentList() {
		return videoCommentList;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}

	public void setAccessControlService(AccessControlService accessControlService) {
		this.accessControlService = accessControlService;
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

	public ReportService getReportService() {
		return reportService;
	}

	public void setReportService(ReportService reportService) {
		this.reportService = reportService;
	}
	
}
