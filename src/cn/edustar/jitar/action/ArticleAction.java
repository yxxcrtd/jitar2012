package cn.edustar.jitar.action;

import java.io.File;
import java.io.PrintWriter;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.regex.Pattern;

import javax.servlet.ServletContext;

import org.apache.commons.io.FileUtils;
import org.springframework.web.context.ContextLoader;

import com.chinaedustar.fcs.bi.enums.DeleteSrcEnum;
import com.chinaedustar.fcs.bi.enums.ObjectEnum;
import com.chinaedustar.fcs.bi.service.BiServiceImpl;
import com.chinaedustar.fcs.bi.vo.TaskVo;

import cn.edustar.data.Pager;
import cn.edustar.data.Tuple;
import cn.edustar.data.paging.PagingQuery;
import cn.edustar.jitar.model.CategoryTreeModel;
import cn.edustar.jitar.model.Configure;
import cn.edustar.jitar.model.ObjectType;
import cn.edustar.jitar.model.SiteUrlModel;
import cn.edustar.jitar.pojos.Article;
import cn.edustar.jitar.pojos.Category;
import cn.edustar.jitar.pojos.ChannelArticle;
import cn.edustar.jitar.pojos.ChannelUser;
import cn.edustar.jitar.pojos.Comment;
import cn.edustar.jitar.pojos.Channel;
import cn.edustar.jitar.pojos.Group;
import cn.edustar.jitar.pojos.GroupArticle;
import cn.edustar.jitar.pojos.GroupMember;
import cn.edustar.jitar.pojos.HtmlArticleBase;
import cn.edustar.jitar.pojos.PrepareCourseStage;
import cn.edustar.jitar.pojos.SpecialSubject;
import cn.edustar.jitar.pojos.SpecialSubjectArticle;
import cn.edustar.jitar.pojos.User;
import cn.edustar.jitar.query.sitefactory.UserIndexHtmlService;
import cn.edustar.jitar.service.AccessControlService;
import cn.edustar.jitar.service.CacheService;
import cn.edustar.jitar.service.CategoryService;
import cn.edustar.jitar.service.CommentService;
import cn.edustar.jitar.service.ConfigService;
import cn.edustar.jitar.service.ChannelPageService;
import cn.edustar.jitar.service.FileStorage;
import cn.edustar.jitar.service.GroupService;
import cn.edustar.jitar.service.PagingService;
import cn.edustar.jitar.service.PrepareCourseService;
import cn.edustar.jitar.service.ResourceService;
import cn.edustar.jitar.service.SpecialSubjectService;
import cn.edustar.jitar.service.StoreManager;
import cn.edustar.jitar.service.SubjectService;
import cn.edustar.jitar.service.TagService;
import cn.edustar.jitar.service.UserPowerService;
import cn.edustar.jitar.service.UserService;
import cn.edustar.jitar.service.ViewCountService;
import cn.edustar.jitar.user.UserComments;
import cn.edustar.jitar.util.ChineseAnalyzer;
import cn.edustar.jitar.util.CommonUtil;
import cn.edustar.jitar.util.FileCache;
import cn.edustar.jitar.util.ParamUtil;

/**
 * 文章管理
 * 
 * @author Yang Xinxin
 * @version 1.0.0 Jun 2, 2008 3:31:59 PM
 */
public class ArticleAction extends BaseArticleAction {

	/** serialVersionUID */
	private static final long serialVersionUID = -1313218204058300763L;

	/** 分类服务对象 */
	private CategoryService cate_svc;

	/** 评论服务 */
	private CommentService cmt_svc;

	/** 学科服务 */
	private SubjectService subj_svc;

	/** 群组服务 */
	private GroupService group_svc;

	/** 点击率服务对象 */
	private ViewCountService viewcount_svc;

	/** 课程服务 */
	private PrepareCourseService pc_svc;

	/** 配置对象 */
	private Configure config;

	/** 配置服务 */
	private ConfigService configService;

	/** 标签服务 */
	private TagService tagService;

	/**用户角色权限服务*/
	private UserPowerService userPowerService;
	
	/** 用户服务 */
	protected UserService userService;

	/**  */
	private SpecialSubjectService specialSubjectService;
	
	/** 自定义频道  */
	private ChannelPageService channelPageService;
	
	private AccessControlService accessControlService;
	
	private PagingService pagingService;
	
	private UserIndexHtmlService userIndexHtmlService;
	
	/** 最后得到拆分的字符串 */
	private String splitString;

	/** 资源服务 */
	private ResourceService res_svc;
	protected static final long FSIZE_K = 1024L; // 1K
	protected static final long FSIZE_M = 1024L * 1024L; // 1M
	
	/** 用户文件存储服务 */
	private StoreManager sto_mgr;	

	private File file;
	private String fileFileName;
	private String fileContentType;	
	
	protected String username;	
	
	
	/**
	 * Action 执行入口
	 */
	@Override
	protected String execute(String cmd) throws Exception {
	
		// 得到配置对象
		this.config = configService.getConfigure();

		// 进行不验证用户登录的操作.
		if ("comment".equals(cmd)) { // 发表评论
			return comment();
		}else if ("uploadify".equalsIgnoreCase(cmd)) {
			return uploadify();
		}

		// 以下操作需要用户登录
		if (isUserLogined() == false) {
			return LOGIN;
		}
		
		if ("dest_cate".equals(cmd)) {
			return dest_cate();
		}else if("channel_cate".equals(cmd)){
			return channel_cate();
		}
		
		// 以下操作需要用户能够被访问(正常或锁定)
		if (canVisitUser(getLoginUser()) == false) {
			return ERROR;
		}

		if ("list".equalsIgnoreCase(cmd)) {
			return list();
		} else if ("view".equalsIgnoreCase(cmd)) {
			return view();
		} else if ("comment_list".equals(cmd)) {
			return comment_list();
		} else if ("comment_list_my".equals(cmd)) {
			return comment_list_my();
		} else if ("stat".equals(cmd)) {
			return stat();
		} else if ("comment_stat".equals(cmd)) {
			return statComment();
		}

		// 以下操作需要用户能够管理内容(未被锁定)
		if (canManageBlog(getLoginUser()) == false) {
			return ERROR;
		}

		if ("input".equalsIgnoreCase(cmd)) {
			return input();
		} else if ("save".equalsIgnoreCase(cmd)) {
			return save();
		} else if ("edit".equalsIgnoreCase(cmd)) {
			return edit();
		} else if ("delete".equalsIgnoreCase(cmd)) {
			return delete();
		} else if ("hide".equals(cmd)) {
			return hide();
		} else if ("unhide".equals(cmd)) {
			return unhide();
		} else if ("draft".equals(cmd)) {
			return draft();
		} else if ("undraft".equals(cmd)) {
			return undraft();
		} else if ("reply_comment".equals(cmd)) {
			return reply_comment();
		} else if ("save_comment_reply".equals(cmd)) {
			return save_comment_reply();
		} else if ("edit_comment".equals(cmd)) {
			return edit_comment();
		} else if ("save_edit_comment".equals(cmd)) {
			return save_edit_comment();
		} else if ("delete_comment".equals(cmd)) {
			return delete_comment();
		} else if ("audit_comment".equals(cmd)) {
			return audit_comment();
		} else if ("unaudit_comment".equals(cmd)) {
			return unaudit_comment();
		} else if ("pub_to_group".equals(cmd)) {
			return pub_to_group();
		} else if ("pub_to_channel".equals(cmd)) {
			return pub_to_channel();
		}  else if ("move".equals(cmd)) {
			return move();
		} else if ("split".equals(cmd)) {
			return split();
		} else if ("tags".equals(cmd)) {
			return tags();
		} else {
			return unknownCommand(cmd);
		}
	}
	
	
	/**
	 * 为上传上来的指定文件生成服务器端名字
	 * 
	 * @param file 上传上来的文件
	 * @param oldName 用户客户端该文件的名字
	 * @return
	 */
	private String createFileName(File file, String oldName) {
		String extension = CommonUtil.getFileExtension(oldName);
		String newName = UUID.randomUUID().toString().toLowerCase();
		return newName + "." + extension;
	}
	/**
	 * 上载文章
	 * @return
	 * @throws Exception
	 */
	private String uploadify() throws Exception {
		PrintWriter out = response.getWriter();
		User user = userService.getUserByLoginName(username);
		int num = this.userPowerService.getUploadResourceNum(user.getUserId());
		int todaynum = 0;
		if(-1 != num) {
			todaynum = this.userPowerService.getTodayResources(user.getUserId());
			if(!this.userPowerService.AllowUploadResource(user.getUserId(), num, todaynum)) {
				out.print("a");
				out.flush();
				out.close();
				return NONE;
			}
		}
		
		if (null != file || "".equals(fileFileName)) {
			if (fileFileName.length() > 4) {
				if (!".exe".equals(fileFileName.substring(fileFileName.length() - 4).toLowerCase()) && !".bat".equals(fileFileName.substring(fileFileName.length() - 4).toLowerCase())) {
					if (null != user) {
						ServletContext sc = request.getSession().getServletContext();
						String fileConfigPath = sc.getInitParameter("userPath");
						File dest = null;
						String href = "user/" + user.getLoginName() + "/article/";
						
						if (null != fileConfigPath && !"".equals(fileConfigPath)) {
							String fileName = createFileName(file, fileFileName);
							String lastString = fileConfigPath + File.separator + href + File.separator + fileName;
							dest = new File(lastString.replaceAll("\\\\", "/"));
							href = href + fileName;
						} else {
							FileStorage store = sto_mgr.getUserFileStorage(user);
							File user_root = store.getRootFolder();
							String fileName = createFileName(file, fileFileName);
							href = href + fileName;
							dest = new File(user_root, "/article/" + fileName);
							/*
							//验证检查是否用户的资源上载有限制大小
							if (this.config.getBoolValue(Configure.USER_RESOURCE_UPLOAD_LIMIT, false) == true) {
								int sSize = this.userPowerService.getUploadDiskNum(user.getUserId(), false);				
								String totalSize = "" + sSize;
								long longTotalSize = Integer.valueOf(totalSize) * 1024 * 1024;
								long resSize = res_svc.totalResourceSize(user.getUserId());
								if (resSize > longTotalSize) {									
									out.print("b");
									out.flush();
									out.close();
									return NONE;
								}
							}
							*/
						}
						dest.getParentFile().mkdirs();
						
						// For Linux
						FileUtils.copyFile(file, dest);
						
						// href 文件的下载地址
						// 文件大小  (int) dest.length()
						
						out.print(href);
						out.flush();
						out.close();
						
						// 本机自带的转换服务
						//if(canConvert2Swf()){
						    //ConvertWordToSWF(dest.getAbsolutePath());
						//}
						// 使用转换服务
						if (dest.getAbsolutePath().endsWith(".doc") || dest.getAbsolutePath().endsWith(".docx")) {
							String tar = dest.toString().substring(0, dest.toString().lastIndexOf("."));
							TaskVo taskVo = new TaskVo(fileFileName, dest.toString(), tar + ".swf", ObjectEnum.TASK_OBJECT_OFFICE);
							taskVo.setDeleteSrcEnum(DeleteSrcEnum.NOT_DELETE);
							new BiServiceImpl().send(taskVo);
						}
					}
				} else {
					out.print("d");
					out.flush();
					out.close();
					return NONE;
				}
			}
		}
		return NONE;
	}
	
	
	/**
	 * 发布到备课
	 */
	private void saveArticleToPrepareCourseStage(Article article) {
		PrepareCourseStage prepareCourseStage = getPrepareCourseStage();
		if (prepareCourseStage != null) {
			// 判断是否是成员
			if (this.pc_svc.checkUserExistsInPrepareCourse(prepareCourseStage.getPrepareCourseId(), getLoginUser().getUserId())) {
				// 进行保存
				this.pc_svc.saveArticleToPrepareCourseStage(prepareCourseStage, article);
			}
		}
	}

	/**
	 * 专题分类
	 */
	private void putSpecialSubject() {
		List<SpecialSubject> ss = this.specialSubjectService.getValidSpecialSubjectList();
		if (ss != null) {
			setRequestAttribute("specialsubject_list", ss);
		}
	}

	private String channel_cate() {
		int channelId = param_util.getIntParam("channelId");
		String itemType = CategoryService.CHANNEL_ARTICLE_PREFIX + channelId;
		CategoryTreeModel gres_categories = this.cate_svc.getCategoryTree(itemType);
		setRequestAttribute("gres_categories", gres_categories);
		return "Channel_Cate";
	}	
	/**
	 * 从集备来的发布文章
	 * 
	 * @return
	 */
	private PrepareCourseStage getPrepareCourseStage() {
		int prepareCourseStageId = param_util.getIntParam("prepareCourseStageId");
		PrepareCourseStage prepareCourseStage = this.pc_svc.getPrepareCourseStage(prepareCourseStageId);
		if (prepareCourseStage != null) {
			setRequestAttribute("prepareCourseStage", prepareCourseStage);
		}
		return prepareCourseStage;
	}

	private void updateArticleToPrepareCourseStage(Article article) {
		this.pc_svc.updateArticleToPrepareCourseStage(article);
	}
	
	private void updateSpecialSubjectArticleCate(Article article)
	{
		boolean articleState = ((article.getDelState() == false) && (article.getDraftState() == false) && (article.getHideState()==0) && (article.getAuditState()==0));
		int specialSubjectId = param_util.safeGetIntParam("specialSubjectId");	
		SpecialSubjectArticle specialSubjectArticle = this.specialSubjectService.getSpecialSubjectArticleByArticleId(article.getObjectUuid());
		if(specialSubjectArticle == null)
		{
			if(specialSubjectId > 0)
			{
				//新加专题文章
				specialSubjectArticle = new SpecialSubjectArticle();
				specialSubjectArticle.setArticleGuid(article.getObjectUuid());
				specialSubjectArticle.setArticleId(article.getArticleId());
				specialSubjectArticle.setArticleState(articleState);
				specialSubjectArticle.setCreateDate(new Date());
				specialSubjectArticle.setLoginName(article.getLoginName());
				specialSubjectArticle.setSpecialSubjectId(specialSubjectId);
				specialSubjectArticle.setTitle(article.getTitle());
				specialSubjectArticle.setTypeState(article.getTypeState());
				specialSubjectArticle.setUserId(article.getUserId());
				specialSubjectArticle.setUserTrueName(article.getUserTrueName());
				this.specialSubjectService.saveOrUpdateSpecialSubjectArticle(specialSubjectArticle);
			}
		}
		else
		{		
			if(specialSubjectId == 0)
			{
				//删除
				this.specialSubjectService.deleteSpecialSubjectArticle(specialSubjectArticle);
			}
			else
			{
				specialSubjectArticle.setArticleGuid(article.getObjectUuid());
				specialSubjectArticle.setArticleId(article.getArticleId());
				specialSubjectArticle.setArticleState(articleState);
				specialSubjectArticle.setCreateDate(new Date());
				specialSubjectArticle.setLoginName(article.getLoginName());
				specialSubjectArticle.setSpecialSubjectId(specialSubjectId);
				specialSubjectArticle.setTitle(article.getTitle());
				specialSubjectArticle.setTypeState(article.getTypeState());
				specialSubjectArticle.setUserId(article.getUserId());
				specialSubjectArticle.setUserTrueName(article.getUserTrueName());
				this.specialSubjectService.saveOrUpdateSpecialSubjectArticle(specialSubjectArticle);
			}
		}
	}

	/*
	private void updateChannelArticleCate(Article article)
	{
		String channelCate = param_util.getStringParam("channelCate");
		if(channelCate == null || channelCate.equals("")) channelCate = null;
				
		ChannelArticle channelArticle = this.channelPageService.getChannelArticle(article.getObjectUuid());
		if(channelArticle == null)
		{
			if(channelCate!=null)
			{
				//此时需要增加一篇.
				this.publishArticleToChannel(article);
			}
		}
		else
		{		
			if(channelCate == null)
			{
				channelPageService.deleteChannelArticle(channelArticle);
				return;
			}
			Integer channelCateId = CommonUtil.getLastInteger(channelCate);			
			channelArticle.setChannelCateId(channelCateId);
			channelArticle.setChannelCate(channelCate);
			channelPageService.saveOrUpdateChannelArticle(channelArticle);
		}
	}
	*/
	/**
	 * 发布一篇或多篇文章到指定的协作组
	 * 
	 * @return
	 */
	private String pub_to_group() {
	    User loginUser = this.getLoginUser();
		// 获得文章标识参数
		if (getArticleIds() == false)
			return ERROR;

		// 得到参数 groupId - 目标群组; groupCateId - 目标协作组分类
		int groupId = param_util.getIntParam("groupId");

		// 文章当前不支持协作组分类, 未来可能做
		// Integer groupCateId = param_util.getIntParamZeroAsNull("groupCateId");

		if (groupId == 0) {
			addActionError("未选择要发布的目标协作组");
			return ERROR;
		}

		// 验证组的存在性和状态
		Group group = group_svc.getGroup(groupId);
		if (group == null) {
			addActionError("指定标识的协作组不存在.");
			return ERROR;
		}
		if (checkGroupState(group) == false)
			return ERROR;

		// 验证当前用户是这个组的成员，并且有权限发布文章到该组
		GroupMember gm = group_svc.getGroupMemberByGroupIdAndUserId(group.getGroupId(), loginUser.getUserId());
		if (this.checkGroupMemberState(gm, group, getLoginUser()._getUserObject()) == false)
			return ERROR;

		// 循环操作开始
		int oper_count = 0;
		for (Integer articleId : this.art_ids) {
			// 获得要操作的文章
			HtmlArticleBase htmlArticleBase = this.art_svc.getHtmlArticleBaseByArticleId(articleId);
			if (htmlArticleBase == null) {
				addActionError("未找到标识为 " + articleId + " 的文章");
				continue;
			}
			// 验证是这个用户的, (权限验证)
			if (htmlArticleBase.getUserId() != loginUser.getUserId()) {
				addActionError("权限被拒绝, 试图操作他人的文章.");
				continue;
			}

			// 验证是否已经发布过了
			GroupArticle gr = group_svc.getGroupArticleByGroupAndArticle(group.getGroupId(), htmlArticleBase.getArticleId());
			if (gr != null) {
				addActionError("文章 " + htmlArticleBase.getTitle() + " 已经发布到了协作组 " + group.toDisplayString());
				continue;
			}
			
			Integer groupCateId = param_util.getIntParam("groupCateId");
			if(groupCateId == 0) groupCateId = null;
			//System.out.println("groupCateId = " + groupCateId);
			// 发布文章到这个组
			boolean articleState = ((htmlArticleBase.getDelState()==false) && (htmlArticleBase.getDraftState()==false) && (htmlArticleBase.getHideState()==0) && (htmlArticleBase.getAuditState()==0));
			GroupArticle ga = new GroupArticle();
			ga.setGroupId(group.getGroupId());
			ga.setArticleId(htmlArticleBase.getArticleId());
			/*ga.setArticleGuid(htmlArticleBase.getArticleGuid());*/
			ga.setTitle(htmlArticleBase.getTitle());
			ga.setCreateDate(htmlArticleBase.getCreateDate());
			ga.setLoginName(loginUser.getLoginName());
			ga.setUserTrueName(loginUser.getTrueName());
			ga.setUserId(loginUser.getUserId());
			ga.setGroupCateId(groupCateId);
			ga.setIsGroupBest(false);
			ga.setPubDate(new Date());
			ga.setTypeState(htmlArticleBase.getTypeState());
			ga.setArticleState(articleState);
			group_svc.publishArticleToGroup(ga);

			// 消息.
			addActionMessage("文章 " + htmlArticleBase.getTitle() + " 成功发布到协作组 "
					+ group.getGroupTitle() + " 中.");
			++oper_count;
		}

		addActionMessage("共发布了 " + oper_count + " 个文章到协作组 "
				+ group.getGroupTitle() + ".");

		return SUCCESS;
	}

	/**
	 * 发布一篇文章到多个频道
	 * @return
	 */
	private String pub_to_channel(){
	    User loginUser = this.getLoginUser();
		// 获得文章标识参数
		if (getArticleIds() == false){
			return ERROR;
		}
		// 得到参数 channelId:要发布的自定义频道; channelCateId：自定义频道文章分类
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
		ChannelUser cu = this.channelPageService.getChannelUserByUserIdAndChannelId(loginUser.getUserId(), channelId);
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
		// 对选中的文章进行操作，需要先判断是否在这个分类中
		int oper_count = 0;
		for (int articleId : this.art_ids) {
				// 获得要操作的文章
				HtmlArticleBase htmlArticleBase = this.art_svc.getHtmlArticleBaseByArticleId(articleId);
				if (htmlArticleBase == null) {
					addActionError("未找到标识为 " + articleId + " 的文章");
					continue;
				}
				// 验证是这个用户的, (权限验证)
				if (htmlArticleBase.getUserId() != loginUser.getUserId()) {
					addActionError("权限被拒绝, 试图操作他人的文章.");
					continue;
				}
				
				Boolean toUpdated = false;
				ChannelArticle ca = this.channelPageService.getChannelArticleByChannelIdAndArticleId(channelId, articleId);
				if(null != ca ){
					//更新文章分类
					//为了方便比较
					if(ca.getChannelCate() == null) ca.setChannelCate("");
					if(ca.getChannelCateId() == null) ca.setChannelCateId(0);
					
					if(channelCateId == null){
						if(!ca.getChannelCate().equals(channelCate) || ca.getChannelCateId() != null)
						{
							toUpdated = true;			
							ca.setChannelCateId(null);
						}
					}
					else
					{
						if(!ca.getChannelCate().equals(channelCate) || !ca.getChannelCateId().equals(Integer.valueOf(channelCateId))){
							toUpdated = true;
							ca.setChannelCateId(Integer.valueOf(channelCateId));
						}
					}
					if(toUpdated){
						ca.setChannelCate(channelCate);
						if(ca.getChannelCate() == null || ca.getChannelCate().equals("")) ca.setChannelCate(null);
						if(ca.getChannelCateId() == null || ca.getChannelCateId().equals(0)) ca.setChannelCateId(null);
						
						this.channelPageService.saveOrUpdateChannelArticle(ca);
						addActionMessage("文章 " + htmlArticleBase.getTitle() + " 更新了文章分类.");
					}
					else{
						addActionError("文章 " + htmlArticleBase.getTitle() + " 已经发布到了频道  " + c.getTitle());
					}					
					continue;
				}
				
				// 发布文章到频道
				boolean articleState = ((htmlArticleBase.getDelState()==false) && (htmlArticleBase.getDraftState()==false) && (htmlArticleBase.getHideState()==0) && (htmlArticleBase.getAuditState()==0));
				ChannelArticle channelArticle = new ChannelArticle();
				channelArticle.setArticleGuid(htmlArticleBase.getArticleGuid());
				channelArticle.setArticleId(htmlArticleBase.getArticleId());
				channelArticle.setArticleState(true); //默认先都审核
				channelArticle.setTitle(htmlArticleBase.getTitle());
				channelArticle.setChannelCate(channelCate);
				channelArticle.setChannelId(channelId);
				channelArticle.setChannelCateId(channelCateId == null?null:Integer.valueOf(channelCateId));
				channelArticle.setCreateDate(htmlArticleBase.getCreateDate());
				channelArticle.setLoginName(htmlArticleBase.getLoginName());
				channelArticle.setUserId(htmlArticleBase.getUserId());
				channelArticle.setUserTrueName(loginUser.getTrueName());
				channelArticle.setTypeState(htmlArticleBase.getTypeState());
				channelArticle.setArticleState(articleState);
				this.channelPageService.saveOrUpdateChannelArticle(channelArticle);	
				// 消息.
				addActionMessage("文章 " + htmlArticleBase.getTitle() + " 成功发布到自定义频道 " + c.getTitle() + " 中.");
				++oper_count;
			}
		addActionMessage("共发布了 " + oper_count + " 个文章到自定义频道 " + c.getTitle() + ".");
		return SUCCESS;
	}
	
	
	/**
	 * 在 list_form 的 groupId select 中填入当前选择的协作组的文章分类.
	 * 
	 * @return
	 */
	private String dest_cate() {
		int groupId = param_util.getIntParam("groupId");
		if (groupId != 0) {
			String itemType = CommonUtil.toGroupArticleCategoryItemType(groupId);
			CategoryTreeModel group_cate = cate_svc.getCategoryTree(itemType);
			setRequestAttribute("group_cate", group_cate);
		}
		return "Dest_Cate";
	}

	/**
	 * 保存一条文章的评论, 在显示文章的页面上的添加评论 form 提交到这里.
	 * 
	 * @return
	 * @throws Exception
	 */
	private String comment() throws java.lang.Exception {
	    User loginUser = this.getLoginUser();
		int cmt_id = param_util.getIntParam("id");
		if(cmt_id>0){
			//对评论的回复
			// 得到评论和文章对象.
			if (getCommentAndCheck() == false)
				return ERROR;

			// 得到提交的参数.
			String commentReply = param_util.safeGetStringParam("content", "");
			return saveComment_reply(commentReply);
		}
		else	//继续原来的代码,普通的保存评论
		{
		// 得到被评论的文章对象.
		// 匿名用户不允许评论

		if (loginUser == null) {
			if (this.config.getBoolValue(Configure.USER_SITE_COMMENT_ENABLED, false) == true) {
				addActionError("匿名用户不允许发表评论，请先登录系统。");
				return ERROR;
			}
		}

		int articleId = param_util.safeGetIntParam("articleId");
		Article article = art_svc.getArticle(articleId);
		if (article == null) {
			addActionError("要评论的文章不存在");
			return ERROR;
		}
		if (article.getCommentState() == false) {
			addActionError("该文章被设置为不能评论");
			return ERROR;
		}

		String title = param_util.safeGetStringParam("title");
		String content = param_util.safeGetStringParam("content");
		String commentContent = CommonUtil.eraseHtml(content).trim();
		if(commentContent.length() == 0)
		{
			addActionError("请输入评论内容。");
			return ERROR;
		}
	
		
		if (this.config.getBoolValue(Configure.USER_SITE_COMMENT_ENABLED, false) == true) {
			UserComments userComments=UserComments.getInstance();
			boolean enableComment= userComments.CanAddComment(loginUser.getUserId(), ObjectType.OBJECT_TYPE_ARTICLE.getTypeId(), 2);
						
			if(enableComment==false){
				addActionError("请2分钟后再发评论");
				return ERROR;
			}

			boolean CommentEqual= userComments.CommentIsEqual(loginUser.getUserId(), ObjectType.OBJECT_TYPE_ARTICLE.getTypeId(), commentContent);
			if(CommentEqual){
				addActionError("有创意些吧，重复的内容毋需再发");
				return ERROR;
			}
		}
		title = "Re:" + article.getTitle();
		if (title == null || title.length() == 0) {
			title = CommonUtil.eraseHtml(content).trim();
			if (title.length() == 0) {
				this.addActionError(this.getText("groups.comment.title"));
				return ERROR;
			}
			if (title.length() > 36)
				title = title.substring(0, 36) + "...";
		}
		if(title.startsWith("&nbsp;")) title = title.substring(6);
		title = title.trim();

		// 进行验证.
		Comment comment = new Comment();
		// 保存评论用户Id.
		comment.setUserId(loginUser == null ? null : getLoginUser().getUserId());
		// 保存评论用户的登录名.
		comment.setUserName(param_util.safeGetStringParam("userName")); // 评论人的名字.
		if (getLoginUser() != null)
			comment.setUserName(loginUser.getTrueName());
		else if (comment.getUserName() == null || comment.getUserName().length() == 0)
			comment.setUserName("匿名用户");

		
		comment.setTitle(title); // 评论标题.
		comment.setContent(content); // 评论的内容.// 规范化评论内容.
		comment.setStar(param_util.getIntParam("star")); // 评论的星级,取值为0-5.
		comment.setCreateDate(new Date()); // 评论发表时间.
		comment.setIp(request.getRemoteAddr()); // 发表评论时候的 ip 地址.
		comment.setObjType(ObjectType.OBJECT_TYPE_ARTICLE.getTypeId());
		comment.setObjId(article.getArticleId()); // 被评论的对象标识.
		comment.setAboutUserId(article.getUserId()); // 评论相关的人: 文章作者.

		// 给指定文章添加一条评论.
		art_svc.createArticleComment(article, comment);

		if(loginUser != null)
		{
			UserComments userComments=UserComments.getInstance();
			userComments.AddCommentStatus(loginUser.getUserId(), ObjectType.OBJECT_TYPE_ARTICLE.getTypeId(), commentContent);
		}
		
		String returnUrl = this.param_util.safeGetStringParam("returnUrl");
		if(returnUrl == null || returnUrl.length() == 0)
		{
			returnUrl = request.getHeader("Referer");
		}
		if(returnUrl == null || returnUrl.length() == 0)
		{
			returnUrl = request.getSession().getServletContext().getContextPath() + "/go.py?articleId=" + article.getArticleId();
		}
		
		response.sendRedirect(returnUrl);
		
		//PrintWriter out = response.getWriter();
		//out.append(PageContent.PAGE_UTF8);
		//if(returnUrl.length()>0) {
		///	out.println("<script>window.location.href='" + returnUrl + "';</script>");
		//} else {	
		//	out.println("<script>window.history.go(-1);</script>");
		//}
		//out.flush();
		//out.close();
		return NONE;
		}
	}

	protected String calculate(String totalSize, long longTotalSize, long resSize) {
		log.info("已经上传的资源总量：" + resSize);
		log.info("剩余的空间：" + (longTotalSize - resSize));

		// 小于1K
		if (resSize < FSIZE_K) {
			if (resSize == 0) {
				return "您还没有上传任何资源！您的空间是：<b>" + totalSize + "M</b> ！";
			} else {
				return "您已经使用了 <b><1K</b> 空间！ 目前还剩余：<b>" + (int) ((longTotalSize - resSize) / FSIZE_M) + "M</b> 空间！";
			}
		}

		// 大于1K，小于1M
		if (resSize < FSIZE_M) {
			return "您已经使用了 <b>>" + (resSize / FSIZE_K) + "K</b> 空间！目前还剩余：<b>" + (int) ((longTotalSize - resSize) / FSIZE_M) + "M</b> 空间！";
		}

		// 大于1M，小于系统的设置的？M
		if (resSize < longTotalSize) {
			return "您已经使用了 <b>>" + (resSize / FSIZE_M) + "M</b> 空间！目前还剩余：<b>" + (int) ((longTotalSize - resSize) / FSIZE_M) + "M</b> 空间！";
		} else
			return "您已经使用了超过 <b>" + totalSize + "M</B> 的空间！目前已经无法再上传资源！";
	}	
	/**
	 * 显示 发表文章 页面
	 * 
	 * @return
	 * @throws Exception
	 */
	public String input() throws Exception {
		User loginUser = getLoginUser();
		if(null == loginUser){
		    this.addActionLink("请重新登录。", request.getContextPath() + "/index.jsp");
            return ERROR;
		}
		// 验证能否发表/修改内容.
		if (canManageBlog(loginUser) == false) {
			return ERROR;
		}
		
		//检查今天是否还允许上载文章
		int num = this.userPowerService.getUploadArticleNum(loginUser.getUserId());
		int todaynum = 0;
		if(num != -1){
			todaynum = this.userPowerService.getTodayArticles(loginUser.getUserId());
			if(!this.userPowerService.AllowUploadArticle(loginUser.getUserId(),num,todaynum)){
				this.addActionError("您今天不能再发表文章了！您每天允许发表文章的数量是"+num);
				return ERROR;
			}
		}
		
		/** 是否显示当前用户的'剩余空间' */
		String wordmessage="";
		if (this.config.getBoolValue(Configure.USER_RESOURCE_UPLOAD_LIMIT, false) == true) {
			int sSize=this.userPowerService.getUploadDiskNum(loginUser.getUserId(),false);
			if(sSize!=-1){
				if(sSize==0){
					wordmessage="由于您所在的角色组不允许上载资源,因此您无法上载Word文章";
				}
				else{
					String totalSize=""+sSize;
					long longTotalSize = Integer.valueOf(totalSize) * 1024 * 1024;
					
					/** 计算当前用户的空间总和 */
					long resSize = res_svc.totalResourceSize(loginUser.getUserId());
					wordmessage=calculate(totalSize, longTotalSize, resSize);
					if (resSize > longTotalSize) {
						wordmessage=wordmessage+"您已经使用了超过 " + totalSize + "M 的空间！ 目前已经无法再上传文章！";
					}
				}
			}
		}
		setRequestAttribute("wordmessage", wordmessage); //上载文档旁的提示信息
		
		Article article = new Article();
		article.setUserId(loginUser.getUserId());
		article.setLoginName(loginUser.getLoginName());
		article.setUserTrueName(loginUser.getTrueName());
		article.setSubjectId(loginUser.getSubjectId());
		article.setGradeId(loginUser.getGradeId());
		article.setSysCateId(loginUser.getCategoryId());
		int specialSubjectId = param_util.getIntParam("specialSubjectId");
		if (specialSubjectId > 0) {
			setRequestAttribute("specialSubjectId", specialSubjectId);
		}
		else
		{
			setRequestAttribute("specialSubjectId", -1);
		}		
		
		// 将文章对象的几个属性显示给'发表文章'页面
		setRequestAttribute("article", article);
		setRequestAttribute("userid", loginUser.getUserId());
		if(num>0){
			setRequestAttribute("num", num);
			setRequestAttribute("todaynum", todaynum);
		}
		// 将 学科列表, 学段列表 放到 request 中
		putMetaSubjectListToRequest();
		putGradeListToRequest();

		// 将'系统分类树', '个人分类树' 放到 request 中
		putArticleCategoryToRequest();
		putUserCategoryToRequest();

		// 将个人加入的协作组放到 request 中
		putJoinedGroupToRequest(group_svc);
		
		Integer groupId = param_util.getIntParamZeroAsNull("groupId");
		CategoryTreeModel res_cate=null;
		if (groupId != null) {
			res_cate = cate_svc.getCategoryTree(getGroupArticleCategoryItemType(groupId));
		}
		setRequestAttribute("res_cate", res_cate);	
		setRequestAttribute("returnGroupId", groupId);	
		setRequestAttribute("returnPage", param_util.getStringParam("returnPage",null));
		getPrepareCourseStage();
		putUserChannelList();
		putSpecialSubject();
		
		return INPUT;
	}
	/** 计算群组文章树分类名字 */
	private String getGroupArticleCategoryItemType(int groupId) {
		return CommonUtil.toGroupArticleCategoryItemType(groupId);
	}
	
	
	
	/**
	 * 保存 新发表的/修改的 文章
	 * 
	 * @return
	 * @throws Exception
	 */
	private String save() throws Exception {
	    User loginUser = this.getLoginUser();
		// 验证能否发表或修改内容
		if (canManageBlog(loginUser) == false) {
			return ERROR;
		}

		boolean isUploadWordSetup = true;
		//新版本直接转换了，不再进行配置了 boolean isUploadWordSetup = canConvert2Swf();
		
		int oldArticleId = param_util.getIntParam("articleId");
		//得到原来的Word的上传地址 user/userId/article/***.doc
		String oldWordHref = null;
		if(oldArticleId > 0){
			Article article = art_svc.getArticle(oldArticleId);
			if(article!=null){
				oldWordHref = article.getWordHref();
			}
		}
		
		
		Article article = collectArticleData();
		//检查文章中是否有<script <iframe 等代码。
		String articleContent = article.getArticleContent();
		
		int articleFormat = param_util.getIntParam("articleFormat");
		String wordHref = param_util.getStringParam("wordHref");
		int wordDownload = param_util.getIntParam("wordDownload");
		article.setArticleFormat(articleFormat);
		
		//System.out.println("articleFormat="+articleFormat);
		//System.out.println("wordHref="+wordHref);
		//System.out.println("isUploadWordSetup="+isUploadWordSetup);
		if(isUploadWordSetup && articleFormat==1){
			if(wordHref == null || wordHref.length() == 0)
			{
				this.addActionError("请上载文章。");
				return ERROR;
			}
			article.setWordHref(wordHref);
			article.setWordDownload(wordDownload==1);
		}else{
			if(articleContent == null || articleContent.length() == 0)
			{
				this.addActionError("请输入文章内容。");
				return ERROR;
			}
		}
		
		articleContent = Pattern.compile("<script.*?>.*?</script>", Pattern.CASE_INSENSITIVE).matcher(articleContent).replaceAll("");  
		articleContent = Pattern.compile("<iframe.*?>.*?</iframe>", Pattern.CASE_INSENSITIVE).matcher(articleContent).replaceAll("");  
		articleContent = Pattern.compile("<frameset.*?>.*?</frameset>", Pattern.CASE_INSENSITIVE).matcher(articleContent).replaceAll(""); 
		articleContent = Pattern.compile("<frame.*?>.*?</frame>", Pattern.CASE_INSENSITIVE).matcher(articleContent).replaceAll("");
		article.setArticleContent(articleContent);
		
		// 对数据进行逻辑检验
		if(isUploadWordSetup && articleFormat==1){
			String articleAbstract = new String(article.getArticleAbstract()); // 文章摘要
			String articleTags = new String(article.getArticleTags()); // 关键词
			if(articleTags == null || articleTags.length() == 0){
				this.addActionError("请输入文章关键字。");
				return ERROR;			
			}
			if(articleAbstract == null || articleAbstract.length() == 0){
				this.addActionError("请输入文章摘要。");
				return ERROR;			
			}
		}else{
			// 验证是否填写了文章内容
			if (null == article.getArticleContent() || article.getArticleContent().length() == 0) {
				this.addActionError("没有填写文章内容. 或者浏览器脚本出现问题??");
				return ERROR;
			}			
		}
		if (checkArticle(article)) {
			return ERROR;
		}
		
		//转换为swf
		if(isUploadWordSetup && articleFormat == 1){
			String word_Href = wordHref;
			if(!wordHref.startsWith("/")){
				word_Href = "/"+wordHref;
			}
			//在上传时转换
			//ConvertWordToSWF(request.getServletContext().getRealPath(word_Href));
		}
		
		// 判断是保存或修改
		if (article.getArticleId() == 0) {
			//检查今天是否还允许上载文章
			int num = this.userPowerService.getUploadArticleNum(loginUser.getUserId());
			int todaynum = 0;
			if(num != -1){
				todaynum = this.userPowerService.getTodayArticles(loginUser.getUserId());
				if(!this.userPowerService.AllowUploadArticle(loginUser.getUserId(),num,todaynum)){
					this.addActionError("您今天不能再上载文章了！您每天允许上载文章的数量是"+num);
					return ERROR;
				}
			}
			
			return createArticle(article);
		} else {
			//准备删除原来的文档
			if(null != oldWordHref && !oldWordHref.equals(article.getWordHref()))
			{
				if(oldWordHref!=null && oldWordHref.length()>0){
					try {
						if(!oldWordHref.startsWith("/")){
							oldWordHref="/"+oldWordHref;
						}
						File file = new File(request.getServletContext().getRealPath(oldWordHref) );
						if(file.exists()){
							file.delete();
						}
					} catch (Exception e) {
						//addActionMessage("文件  " + oldWordHref + " 未删除,请手动删除！");
					}	
					String swfFile=null;
					if(oldWordHref.endsWith(".doc")){
						swfFile=oldWordHref.substring(0, oldWordHref.length()-4)+".swf";
					}else if(oldWordHref.endsWith(".docx")){
						swfFile=oldWordHref.substring(0, oldWordHref.length()-5)+".swf";	
					}
					if(swfFile!=null && swfFile.length()>0){
						try{
							File file = new File(request.getServletContext().getRealPath(swfFile) );
							if(file.exists()){
								file.delete();
							}
						}catch(Exception e){
							//addActionMessage("文件  " + swfFile + " 未删除,请手动删除！");
						}
					}
				}
			}
			CacheService cacheService2 = ContextLoader.getCurrentWebApplicationContext().getBean("cacheService", CacheService.class);
			cacheService2.remove("article_" + article.getArticleId());
			this.cacheService.remove("article_" + article.getArticleId());
			return updateArticle(article);
		}
	}
	
	
	/**
	 * 检测用户文章是否填写正确，并且检查：文章标题、文章标签、文章摘要和文章内容中是否包含非法词汇
	 * 
	 * @param article
	 * @return true 表示有错误; false 表示没有错误
	 */
	private boolean checkArticle(Article article) {
		boolean has_error = false;
		String articleTitle = new String(article.getTitle()); // 文章标题
		String[] articleTags = tagService.parseTagList((String) article.getArticleTags()); // 文章标签
		String articleAbstract = new String(article.getArticleAbstract()); // 文章摘要
		String articleContent = new String(article.getArticleContent()); // 文章内容
		String[] screen_keyword = tagService.parseTagList((String) this.config.getValue("site.screen.keyword")); // 系统中定义的需要屏蔽的非法词汇数组
		String screen_enalbed = (String) this.config.getValue("site.screen.enalbed"); // 是否屏蔽
		String screen_replace = (String) this.config.getValue("site.screen.replace"); // 替换的字符

		// 是否屏蔽系统中出现的非法词汇
		if ("true".equals(screen_enalbed)) {
			for (int i = 0; i < screen_keyword.length; ++i) {
				// 1，检查'文章标题'中是否含有'非法词汇'
				if (articleTitle.equals(screen_keyword[i])) {
					this.addActionError("您输入的'文章标题'与'非法词汇'完全相同！");
					has_error = true;
					return has_error;
				}
				if (articleTitle.indexOf(screen_keyword[i]) != -1) {
					articleTitle = articleTitle.replaceAll(screen_keyword[i], screen_replace);
					article.setTitle(articleTitle);
				}

				// 2，检查'文章摘要'中是否含有'非法词汇'
				if (articleAbstract.equals(screen_keyword[i])) {
					this.addActionError("您输入的'文章摘要'与'非法词汇'完全相同！");
					has_error = true;
					return has_error;
				}
				if (articleAbstract.indexOf(screen_keyword[i]) != -1) {
					articleAbstract = articleAbstract.replaceAll(screen_keyword[i], screen_replace);
					article.setArticleAbstract(articleAbstract);
				}

				// 3，检查'文章内容'中是否含有'非法词汇'
				/*
				 * if (articleContent.equals(screen_keyword[i])) {
				 * log.info("文章内容与'非法词汇'完全重合了！");
				 * this.addActionError("您输入的'文章内容与'非法词汇'完全相同！"); has_error = true;
				 * return has_error; }
				 */
				if (articleContent.indexOf(screen_keyword[i]) != -1) {
					articleContent = articleContent.replaceAll(screen_keyword[i], screen_replace);
					article.setArticleContent(articleContent);
				}
			}

			// 检查'文章标签'中是否含有'非法词汇'
			for (int i1 = 0; i1 < screen_keyword.length; ++i1) {
				for (int j = 0; j < articleTags.length; j++) {
					if (articleTags[j].indexOf(screen_keyword[i1]) != -1) {
						articleTags[j] = articleTags[j].replaceAll(screen_keyword[i1], screen_replace);
						article.setArticleTags(CommonUtil.standardTagsString(articleTags));
					}
				}
			}
		}

		// 验证是否填写了标题
		if (null == articleTitle || articleTitle.length() == 0 || "".equals(articleTitle)) {
			this.addActionError("请填写文章标题！");
			has_error = true;
		}

		// 验证标题长度
		if (articleTitle != null && articleTitle.length() > 255) {
			this.addActionError("文章的标题长度不能超过：" + 255 + "个字符！");
			has_error = true;
		}

		return has_error;
	}

	/**
	 * '文章标题、文章标签、文章摘要或文章内容'是否与'非法词汇'完全重合
	 * 
	 * @param article_string
	 * @param screen_string
	 * @return
	 */
	@SuppressWarnings("unused")
	private boolean duplicate(String article_string, String screen_string) {
		if (article_string.equals(screen_string)) {
			this.addActionError("您输入的文章标题、文章标签、文章摘要或文章内容中有一个与'非法词汇'完全相同！");
			return true;
		}
		return false;
	}

	/**
	 * 从用户提交的数据中收集 Article 对象, 不进行任何验证
	 * 
	 * @return
	 */
	private Article collectArticleData() {
		Article article = new Article();
		article.setArticleId(param_util.getIntParam("articleId"));
		article.setTitle(param_util.safeGetStringParam("articleTitle"));
		article.setArticleContent(param_util.safeGetStringParam("articleContent"));
		article.setArticleAbstract(param_util.safeGetStringParam("articleAbstract"));
		article.setArticleTags(param_util.safeGetStringParam("articleTags"));
		article.setSubjectId(param_util.getIntParamZeroAsNull("subjectId"));
		article.setUserCateId(param_util.getIntParamZeroAsNull("userCate"));		
		article.setSysCateId(param_util.getIntParamZeroAsNull("sysCate"));
		article.setHideState((short) (int) param_util.getIntParam("hideState"));		
		//根据配置获取是否自动推送内容
		boolean autoPush = false;
		if(this.config.containsKey("auto_push_up"))
		{
			autoPush = this.config.getBoolValue("auto_push_up", false);
		}
		
		if(article.getArticleId()==0){
			//根据配置,是否先审核后发布
			// 是否允许大文件上载			
			boolean bCheck = this.config.getBoolValue(Configure.BEFAULE_PUBLISH_CHECK, false);
			if(bCheck)
			{
				article.setAuditState((short)1);
				if(autoPush)
				{
					article.setUnitPathInfo(getLoginUser().getUnitPathInfo());
				}
				else
				{
					article.setUnitPathInfo("/"+getLoginUser().getUnitId() + "/");
				}
			}
			else
			{
				article.setAuditState((short)0);
				if(autoPush)
				{
					article.setApprovedPathInfo(getLoginUser().getUnitPathInfo());
					article.setUnitPathInfo(getLoginUser().getUnitPathInfo());
				}
				else
				{
					article.setApprovedPathInfo("/"+getLoginUser().getUnitId() + "/");
					article.setUnitPathInfo("/"+getLoginUser().getUnitId() + "/");
				}
			}
		}
		else{
			article.setAuditState((short) (int) param_util.getIntParam("auditState"));
		}
		article.setDraftState(param_util.getBooleanParam("draftState"));
		article.setCommentState(param_util.getBooleanParam("commentState"));
		//article.setArticlePassword(param_util.safeGetStringParam("articlePassword", null));
		article.setGradeId(param_util.getIntParamZeroAsNull("gradeId"));
		article.setTypeState(param_util.getBooleanParam("articleType"));

		article.setUnitId(getLoginUser().getUnitId());
		article.setOrginPath(getLoginUser().getUnitPathInfo());
		return article;
	}

	/**
	 * 创建新文章
	 * 
	 * @param article
	 * @return
	 */
	private String createArticle(Article article) {
		// 设置更多新建时候的属性
		setNewArticleProperty(article);

		// 创建文章
		art_svc.createArticle(article);

		// 设置返回信息
		setRequestAttribute("article", article);
		if(article.getDraftState())
		{
			addActionMessage("文章：" + article.toDisplayString() + " 保存草稿成功！");
		}
		else{
			addActionMessage("文章：" + article.toDisplayString() + " 发表成功！");
		}		

		// 同时发布到选中的协作组中
		publishArticleToGroups(article);
		/* 发布到  群组，专题，集体备课，自定义频道 */
		/* 专题 */
		publishArticleToSpecialSubject(article);
		
		/* 备课 */
		saveArticleToPrepareCourseStage(article);
		
		/* 自定义频道 */
		publishArticleToChannel(article);				
				
		PrepareCourseStage prepareCourseStage = getPrepareCourseStage();
		if (prepareCourseStage != null) {
			addActionLink("添加另一篇", "?cmd=input&amp;prepareCourseStageId=" + prepareCourseStage.getPrepareCourseStageId());
		} 

		String returnPage = this.param_util.getStringParam("returnPage",null);
		Integer returnGroupId = this.param_util.getIntParamZeroAsNull("returnGroupId");
		if(CommonUtil.isEmptyString(returnPage))
		{
			addActionLink("添加另一篇", "?cmd=input");
			addActionLink("修改该文章", "?cmd=edit&amp;articleId=" + article.getArticleId());
			addActionLink("文章管理", "?cmd=list");
			addActionLink("查看文章", SiteUrlModel.getSiteUrl() + "go.action?articleId=" + article.getArticleId(), "_blank");
			addActionLink("个人主页", SiteUrlModel.getSiteUrl() + "go.action?userId=" + getLoginUser().getUserId(), "_blank");
		}
		else{
			addActionLink("添加另一篇", "article.action?cmd=input&groupId=" + returnGroupId + "&returnPage=group");			
			addActionLink("文章管理", "groupArticle.action?cmd=list&groupId=" + returnGroupId + "&returnPage=" + returnPage);
			addActionLink("查看文章", SiteUrlModel.getSiteUrl() + "go.action?articleId=" + article.getArticleId(),"_blank");
			addActionLink("个人主页",SiteUrlModel.getSiteUrl() + "go.action?userId=" + getLoginUser().getUserId(), "_blank");
		}

		if (prepareCourseStage != null) {
			addActionLink("返回备课", "../p/" + prepareCourseStage.getPrepareCourseId() + "/" + prepareCourseStage.getPrepareCourseStageId() + "/");
		}

		this.userIndexHtmlService.genEntriesList(getLoginUser());

		return SUCCESS;
	}

	//添加频道文章 
	private void publishArticleToChannel(Article article)
	{	
		if(article == null) return;
		User loginedUser =  this.getLoginUser();
		if(loginedUser == null) return;
		//如果没有频道，就不处理了。
		if(!param_util.existParam("channelTotalCount")) return;
		int channelTotalCount = param_util.safeGetIntParam("channelTotalCount");
		log.info("channelTotalCount = " + channelTotalCount);
		for(int i = 0;i<channelTotalCount + 1;i++)
		{
			ChannelArticle channelArticle = null;
			Integer channelId = param_util.getIntParamZeroAsNull("channelId" + i);
			String channelCate = param_util.getStringParam("channelCate" + i, null);
			String channelCateId = null;
			if(channelId != null)
			{
				channelArticle = this.channelPageService.getChannelArticleByChannelIdAndArticleId(channelId, article.getArticleId());
				if(channelArticle != null){
					channelArticle = null;
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
				
				//Integer channelCateId = CommonUtil.getLastInteger(channelCate);		
				boolean articleState = ((article.getDelState() == false) && (article.getDraftState() == false) && (article.getHideState()==0) && (article.getAuditState()==0));
				channelArticle = new ChannelArticle();
				channelArticle.setArticleGuid(article.getObjectUuid());
				channelArticle.setArticleId(article.getArticleId());
				channelArticle.setArticleState(true); //默认先都审核
				channelArticle.setTitle(article.getTitle());
				channelArticle.setChannelCate(channelCate);
				channelArticle.setChannelId(channelId);
				channelArticle.setChannelCateId(channelCateId == null?null:Integer.valueOf(channelCateId));
				channelArticle.setCreateDate(article.getCreateDate());
				channelArticle.setLoginName(article.getLoginName());
				channelArticle.setUserId(article.getUserId());
				channelArticle.setUserTrueName(article.getUserTrueName());
				channelArticle.setTypeState(article.getTypeState());
				channelArticle.setArticleState(articleState);
				this.channelPageService.saveOrUpdateChannelArticle(channelArticle);	
			}
		}
	}
	
	private void publishArticleToSpecialSubject(Article article)
	{
		Integer specialSubjectId = param_util.getIntParamZeroAsNull("specialSubjectId");
		if(specialSubjectId == null) return;
		boolean articleState = ((article.getDelState() == false) && (article.getDraftState() == false) && (article.getHideState()==0) && (article.getAuditState()==0));
		SpecialSubjectArticle specialSubjectArticle = new SpecialSubjectArticle();
		specialSubjectArticle.setArticleGuid(article.getObjectUuid());
		specialSubjectArticle.setArticleId(article.getArticleId());
		specialSubjectArticle.setArticleState(true); //默认先都审核
		specialSubjectArticle.setTitle(article.getTitle());
		specialSubjectArticle.setCreateDate(article.getCreateDate());
		specialSubjectArticle.setLoginName(article.getLoginName());
		specialSubjectArticle.setUserId(article.getUserId());
		specialSubjectArticle.setUserTrueName(article.getUserTrueName());
		specialSubjectArticle.setSpecialSubjectId(specialSubjectId);
		specialSubjectArticle.setArticleState(articleState);
		specialSubjectArticle.setTypeState(article.getArticleFormat() == 1 ? true : false);
		this.specialSubjectService.saveOrUpdateSpecialSubjectArticle(specialSubjectArticle);
	}
	
	/**
	 * 发布文章到参数中给出的协作组中
	 * 
	 * @param article
	 */
	private void publishArticleToGroups(Article article) {
		Integer _groupIndex = param_util.safeGetIntParam("_groupIndex");
		if(_groupIndex==0)
			_groupIndex=1;
		
		// 循环进行操作
		for(int i=1;i<=_groupIndex;i++){
			Integer groupId=param_util.getIntParamZeroAsNull("groupId"+i);
			Integer groupCateId=param_util.getIntParamZeroAsNull("groupCateId"+i);	
			if (groupId == null)
				continue;
			
			// 得到协作组并验证
			Group group = group_svc.getGroupMayCached(groupId);
			if (group == null) {
				addActionError("指定标识的协作组不存在 groupId = " + groupId);
				continue;
			}
			if (checkGroupState(group._getGroupObject()) == false)
				continue;

			// 得到成员关系并验证
			GroupMember gm = group_svc.getGroupMemberByGroupIdAndUserId(groupId,getLoginUser().getUserId());
			if (this.checkGroupMemberState(gm, group._getGroupObject(),getLoginUser()._getUserObject()) == false)
				continue;

			// 是否已经发布了
			GroupArticle ga = group_svc.getGroupArticleByGroupAndArticle(groupId,article.getArticleId());
			if (ga != null) {
				addActionError("您已经将文章 " + article.toDisplayString() + " 发布到了协作组 "	+ group.toDisplayString() + ", 不需再次发布.");
				continue;
			}
			
			boolean articleState = ((article.getDelState() == false) && (article.getDraftState() == false) && (article.getHideState()==0) && (article.getAuditState()==0));
			// 创建发布
			ga = new GroupArticle();
			ga.setGroupCateId(groupCateId);
			ga.setArticleId(article.getArticleId());
			ga.setGroupId(group.getGroupId());
			ga.setUserId(article.getUserId());
			ga.setLoginName(article.getLoginName());
			ga.setUserTrueName(article.getUserTrueName());
			ga.setTitle(article.getTitle());			
			ga.setIsGroupBest(false);
			ga.setPubDate(new Date());
			ga.setCreateDate(article.getCreateDate());
			ga.setArticleState(articleState);
			ga.setTypeState(article.getTypeState());
			group_svc.publishArticleToGroup(ga);

			addActionMessage("文章 " + article.toDisplayString() + " 成功发布到了协作组 " + group.toDisplayString());
		}
	}

	/**
	 * 设置新建一个文章时候的不需要/也不让用户设置的那些属性
	 * 
	 * @param article
	 */
	private void setNewArticleProperty(Article article) {
		article.setUserId(getLoginUser().getUserId());		
		article.setLoginName(getLoginUser().getLoginName());
		article.setUserTrueName(getLoginUser().getTrueName());
		article.setCreateDate(new Date());
		article.setLastModified(article.getCreateDate());
		article.setViewCount(0);
		article.setCommentCount(0);
		article.setAddIp(CommonUtil.getClientIP(request));
		//根据配置,是否先审核后发布
		boolean bCheck = this.config.getBoolValue(Configure.BEFAULE_PUBLISH_CHECK, false);
		if(bCheck)
			article.setAuditState(Article.AUDIT_STATE_WAIT_AUDIT);
		else
		{
			article.setAuditState(Article.AUDIT_STATE_OK);
		}
		
		article.setBestState(false);
		article.setDelState(false);
		article.setRecommendState(false);
	}

	/**
	 * 移动文章的'系统分类'和'个人分类'
	 * 
	 * @return
	 * @throws Exception
	 */
	private String move() throws Exception {
		if (getArticleIds() == false) {
			return ERROR;
		}

		// 得到目标分类参数
		Integer sysCateId = param_util.getIntParamZeroAsNull("sysCateId");
		Integer userCateId = param_util.getIntParamZeroAsNull("userCateId");

		if (ParamUtil.MINUS_ONE.equals(sysCateId)
				&& ParamUtil.MINUS_ONE.equals(userCateId)) {
			addActionError("目标系统分类和目标用户分类必须至少选择一者.");
			return ERROR;
		}

		// 验证分类正确
		if (sysCateId != null && ParamUtil.MINUS_ONE.equals(sysCateId) == false) {
			Category sysCate = cate_svc.getCategory(sysCateId);
			if (sysCate == null) {
				addActionError("目标系统分类不存在.");
				return ERROR;
			}
			if (CategoryService.ARTICLE_CATEGORY_TYPE
					.equals(sysCate.getItemType()) == false) {
				addActionError("目标系统分类 '" + sysCate.getName() + "' 不是一个合法的系统文章分类.");
				return ERROR;
			}
		}
		if (userCateId != null && ParamUtil.MINUS_ONE.equals(userCateId) == false) {
			Category userCate = cate_svc.getCategory(userCateId);
			if (userCate == null) {
				addActionError("目标用户分类不存在.");
				return ERROR;
			}
			String itemType = this.getArticleUserCategoryItemType();
			if (itemType.equals(userCate.getItemType()) == false) {
				addActionError("用户分类不正确.");
				return ERROR;
			}
		}

		// 循环操作
		int oper_count = 0;
		for (Integer aid : art_ids) {
			Article article = art_svc.getArticle(aid);
			if (article == null) {
				addActionError("未找到标识为 " + aid + " 的文章");
				continue;
			}
			

			// 检查权限
			//if (checkRightAndSetError(tuple, "move", "移动分类") == false)
			//	continue;

			if (article.getUserId() != this.getLoginUser().getUserId()) {
				addActionError(" " + aid + " 的文章 不是你的。");
				continue;
			}
			if (ParamUtil.MINUS_ONE.equals(sysCateId) == true)
				sysCateId = null;
			if (ParamUtil.MINUS_ONE.equals(userCateId) == true)
				userCateId = null;
			
			// 移动分类
			//art_svc.moveArticleCategory(article);
			art_svc.moveArticleCategory2(article.getArticleId(), userCateId, sysCateId);
			
			
			++oper_count;
			addActionMessage("文章 " + article.toDisplayString() + " 成功移动到了目标分类.");
		}

		addActionMessage("共移动了 " + oper_count + " 个文章到目标分类.");

		return SUCCESS;
	}

	/**
	 * 更新用户表中的文章数、更新文章表中的评论数.
	 * 
	 * @return
	 * @throws Exception
	 */
	private String stat() throws Exception {
		art_svc.statForUser(this.getLoginUser()._getUserObject());
		this.addActionMessage("统计成功完成");
		return SUCCESS;
	}

	/**
	 * 更新文章评论的统计.
	 * 
	 * @return
	 * @throws Exception
	 */
	private String statComment() throws Exception {
		art_svc.statComment(this.getLoginUser()._getUserObject());
		this.addActionMessage("统计成功完成");
		this.addActionLink("返回", "?cmd=comment_list");
		this.addActionLink("文章管理", "?cmd=list");
		this.addActionLink("评论管理", "?cmd=comment_list");
		return SUCCESS;
	}

	/**
	 * 修改文章.
	 * 
	 * @param article
	 * @return
	 */
	private String updateArticle(Article article) {
		// 得到原来的文章.
		Article origin_article = art_svc.getArticle(article.getArticleId());
		if (origin_article == null) {
			addActionError("无法找到原文章");
			return ERROR;
		}
		if (origin_article.getUserId() != getLoginUser().getUserId()) {
			addActionError("权限不足, 不能编辑修改他人的文章");
			return ERROR;
		}

		// 设置不从页面传递的属性.
		article.setUserId(origin_article.getUserId());
		
		article.setLoginName(origin_article.getLoginName());
		article.setUserTrueName(origin_article.getUserTrueName());
		article.setObjectUuid(origin_article.getObjectUuid());
		article.setBestState(origin_article.getBestState());
		article.setDelState(origin_article.getDelState());
		
		article.setTopState(origin_article.getTopState());
		article.setAuditState(origin_article.getAuditState());
		article.setUnitPathInfo(origin_article.getUnitPathInfo());
		article.setOrginPath(origin_article.getOrginPath());		
		article.setUnitId(origin_article.getUnitId());
		article.setApprovedPathInfo(origin_article.getApprovedPathInfo());
		article.setRcmdPathInfo(origin_article.getRcmdPathInfo());

		article.setPushState(origin_article.getPushState());
		article.setPushUserId(origin_article.getPushUserId());		

		//其他地方使用的数据，需要加载
		
		article.setViewCount(origin_article.getViewCount());
		article.setCommentCount(origin_article.getCommentCount());
		
		
		//System.out.println("article.getWordHref()="+article.getWordHref());
		//System.out.println("article.getArticleFormat()="+article.getArticleFormat());
		
		// 修改.
		art_svc.updateArticle(article);
		
		//updateArticleToPrepareCourseStage(article);		
		//updateChannelArticleCate(article);		
		updateSpecialSubjectArticleCate(article);
		
		
		// 处理 群组，专题，集体备课，自定义频道 文章的更新。这些文章将的表暂时都是固定的
		//publishArticleToGroups(article);
		setRequestAttribute("article", article);

		addActionMessage("文章 " + article.toDisplayString() + " 修改成功完成.");
		String returnPage = this.param_util.getStringParam("returnPage",null);
		Integer returnGroupId = this.param_util.getIntParamZeroAsNull("returnGroupId");
		if(CommonUtil.isEmptyString(returnPage))
		{
			addActionLink("文章管理", "?cmd=list");
			addActionLink("查看文章", SiteUrlModel.getSiteUrl() + "go.action?articleId=" + article.getArticleId(),"_blank");
			addActionLink("个人主页",SiteUrlModel.getSiteUrl() + "go.action?userId=" + getLoginUser().getUserId(), "_blank");
		}
		else{
			addActionLink("文章管理", "groupArticle.action?cmd=list&groupId=" + returnGroupId + "&returnPage=" + returnPage);
			addActionLink("查看文章", SiteUrlModel.getSiteUrl() + "go.action?articleId=" + article.getArticleId(),"_blank");
			addActionLink("个人主页",SiteUrlModel.getSiteUrl() + "go.action?userId=" + getLoginUser().getUserId(), "_blank");
		}
		

		return SUCCESS;
	}

	/**
	 * 编辑一篇文章.
	 * 
	 * @return
	 * @throws Exception
	 */
	public String edit() throws Exception {
		// 获得文章对象.
		int id = param_util.getIntParam("articleId");
		Article article = art_svc.getArticle(id);
		if (article == null) {
			addActionError("未找到指定标识的文章");
			return ERROR;
		}
		User user = getLoginUser();
		if (article.getUserId() != user.getUserId()) {
			addActionError("不能编辑他(她)人的文章.");
			return ERROR;
		}

		setRequestAttribute("user", user);
		setRequestAttribute("article", article);

		// 将 学科列表, 学段列表 放到'request'中.
		putMetaSubjectListToRequest();
		putGradeListToRequest();

		// 将'系统分类树', 个人分类树 放到'request'中.
		putArticleCategoryToRequest();
		putUserCategoryToRequest();
		this.putSpecialSubject();
		
		//putJoinedGroupToRequest(group_svc);
		
		//检查专题文章
		SpecialSubjectArticle specialSubjectArticle = this.specialSubjectService.getSpecialSubjectArticleByArticleId(article.getObjectUuid());
		if(specialSubjectArticle == null)
		{
			setRequestAttribute("specialSubjectId", -1);
		}
		else
		{
			setRequestAttribute("specialSubjectId", specialSubjectArticle.getSpecialSubjectId());
		}	
		
		return INPUT;
	}

	/**
	 * 文章管理.
	 * 
	 * @return
	 */
	private String list() {
		String k = param_util.safeGetStringParam("k", null);
		Integer sysCateId = param_util.getIntParamZeroAsNull("sc");
		Integer userCateId = param_util.getIntParamZeroAsNull("uc");
		int f = param_util.safeGetIntParam("f",0);
		setRequestAttribute("k", k);
		setRequestAttribute("sc", sysCateId);
		setRequestAttribute("uc", userCateId);
		setRequestAttribute("f", f);
		String sqlWhere = "UserId=" + getLoginUser().getUserId();
		
		switch (f) {
		case 0:
			break; // 全部状态
		case 1: // 已经审核
			sqlWhere += " And AuditState=" + Article.AUDIT_STATE_OK;
			break;
		case 2: // 待审核
			sqlWhere += " And AuditState=" + Article.AUDIT_STATE_WAIT_AUDIT;
			break;
		case 3: // 精华
			sqlWhere += " And BestState=1";
			break;
		case 4: // 非精华
			sqlWhere += " And BestState=0";
			break;
		case 5: // 草稿
			sqlWhere += " And DraftState=1";
			break;
		case 6: // 非草稿
			sqlWhere += " And DraftState=0";
			break;
		}
		
		if (sysCateId != null)
		{
			sqlWhere += " And SysCateId=" + sysCateId;
		}
		
		if (userCateId != null)
		{
			sqlWhere += " And UserCateId=" + userCateId;
		}
		
		if(k!=null && !k.equals(""))
		{
			k = k.replaceAll("'", "''");
			k = k.replaceAll(" ", "");
			k = k.replaceAll(";", "");
			k = k.replaceAll("20%", "");
			sqlWhere += " And Title Like '%" + k + "%'";
		}

		PagingQuery pagingQuery = new PagingQuery();
		pagingQuery.keyName = "ArticleId";
		pagingQuery.fetchFieldsName = "*";
		pagingQuery.orderByFieldName = "ArticleId DESC";
		pagingQuery.spName = "findPagingArticleBase";
		pagingQuery.tableName = "HtmlArticleBase";
		pagingQuery.whereClause = sqlWhere;
		
		// 构造分页参数
		Pager pager = new Pager();
		pager.setCurrentPage(param_util.safeGetIntParam("page", 1));
		pager.setPageSize(20);
		pager.setItemNameAndUnit("文章", "篇");
		pager.setUrlPattern(param_util.generateUrlPattern());
		pager.setTotalRows(pagingService.getRowsCount(pagingQuery));
		
		List<HtmlArticleBase> article_list = (List<HtmlArticleBase>)pagingService.getPagingList(pagingQuery, pager);

		// 设置数据.
		setRequestAttribute("user", getLoginUser());
		setRequestAttribute("article_list", article_list);
		setRequestAttribute("pager", pager);

		// 将元学科列表放到 request 中.
		putMetaSubjectListToRequest();

		// 将'系统分类树'放到'request'中.
		putArticleCategoryToRequest();

		// 将'个人分类树'放到'request'中.
		putUserCategoryToRequest();

		// 当前登录用户加入的自定义频道列表
		putUserChannelList();
		// 获得用户参与的协作组.
		super.putJoinedGroupToRequest(group_svc);

		return LIST_SUCCESS;
	}

	/**
	 * 显示文章评论列表
	 * 
	 * @return
	 */
	private String comment_list() throws Exception {
		// 构造分页查询对象
		Pager pager = getCurrentPager();
		pager.setItemNameAndUnit("评论", "条");
		pager.setUrlPattern("article.action?cmd=comment_list&amp;page={page}");
		// 查询数据
		List<Object[]> comment_list = cmt_svc.getUserArticleCommentList(getLoginUser().getUserId(), pager, true);
		setRequestAttribute("my", 0);
		setRequestAttribute("user", getLoginUser());
		setRequestAttribute("comment_list", comment_list);
		setRequestAttribute("pager", pager);
		return "Comment_List";
	}

	/**
	 * 显示文章评论列表
	 * 
	 * @return
	 */
	private String comment_list_my() throws Exception {
		// 构造分页查询对象
		Pager pager = getCurrentPager();
		pager.setItemNameAndUnit("评论", "条");
		pager.setUrlPattern("article.action?cmd=comment_list_my&amp;page={page}");
		// 查询数据
		List<Object[]> comment_list = cmt_svc.getUserArticleCommentListEx(getLoginUser().getUserId(), pager, true);
		setRequestAttribute("user", getLoginUser());
		setRequestAttribute("comment_list", comment_list);
		setRequestAttribute("my", 1);
		setRequestAttribute("pager", pager);
		return "Comment_List";
	}

	private Comment comment;
	private Article comment_article;

	/**
	 * 得到当前提交的单个评论, 并验证对应的 article 是否正确
	 * 
	 * @return
	 */
	private boolean getCommentAndCheck() {
		// 得到评论.
		int cmt_id = param_util.getIntParam("id");
		this.comment = cmt_svc.getComment(cmt_id);
		if (comment == null) {
			addActionError("未找到指定标识的评论");
			return false;
		}

		// 验证用户有权限修改此评论.
		if (comment.getObjType() != ObjectType.OBJECT_TYPE_ARTICLE.getTypeId()) {
			addActionError("不支持的评论目标对象");
			return false;
		}
		
		this.comment_article = art_svc.getArticle(comment.getObjId());
		if (comment_article == null) {
			addActionError("被评论的文章已不存在");
			return false;
		}
		/*
		if ((comment_article.getUserId() != getLoginUser().getUserId())
				&& (comment.getUserId() != getLoginUser().getUserId())) {
			addActionError("权限不足, 被评论的文章不是当前登录用户的,或者评论人不是当前登录用户");
			return false;
		}
		*/
		setRequestAttribute("comment", comment);
		setRequestAttribute("article", comment_article);

		return true;
	}

	/**
	 * 回复评论.
	 * 
	 * @return
	 */
	private String reply_comment() {
		if (getCommentAndCheck() == false)
			return ERROR;

		setRequestAttribute("__referer", getRefererHeader());

		return "Reply_Comment";
	}

	/**
	 * 保存评论的回复
	 * 
	 * @return
	 * @throws Exception
	 */
	private String save_comment_reply() throws Exception {
		// 得到评论和文章对象.
		if (getCommentAndCheck() == false)
			return ERROR;

		// 得到提交的参数.
		String commentReply = param_util.safeGetStringParam("commentReply", "");
		
		return saveComment_reply(commentReply);

	}

	private String saveComment_reply(String commentReply) throws Exception {
		// 回复不能为空
		if ("".equals(commentReply) || commentReply.length() < 0) {
			this.addActionError("请输入回复的内容！");
			return ERROR;
		}

		String[] screen_keyword = tagService.parseTagList((String) this.config
				.getValue("site.screen.keyword")); // 系统中定义的需要屏蔽的非法词汇数组
		String screen_enalbed = (String) this.config
				.getValue("site.screen.enalbed"); // 是否屏蔽
		String screen_replace = (String) this.config
				.getValue("site.screen.replace"); // 替换的字符

		// 是否屏蔽系统中出现的非法词汇
		if ("true".equals(screen_enalbed)) {
			for (int i = 0; i < screen_keyword.length; ++i) {
				if (commentReply.indexOf(screen_keyword[i]) != -1) {
					commentReply = commentReply.replaceAll(screen_keyword[i],
							screen_replace);
					// comment.setContent(commentReply);
				}
			}
		}

		// 构造回复.
		String content = comment.getContent();
		if (content == null)
			content = "";
		content += "<div class='commentReply'>" + "<div>以下为 "
				+ getLoginUser().getTrueName() + " 的回复：</div><div>" + commentReply
				+ "</div></div>";
		comment.setContent(content);

		// 保存回复.
		cmt_svc.saveComment(comment);

		return SUCCESS;	
	}
	
	/**
	 * 编辑一个评论, 似乎允许编辑修改他人的评论比较无耻?
	 * 
	 * @return
	 */
	private String edit_comment() {
		if (getCommentAndCheck() == false)
			return ERROR;

		setRequestAttribute("__referer", getRefererHeader());

		return "Edit_Comment";
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
		String commentContent = param_util.safeGetStringParam("commentContent",
				"");

		String[] screen_keyword = tagService.parseTagList((String) this.config
				.getValue("site.screen.keyword")); // 系统中定义的需要屏蔽的非法词汇数组
		String screen_enalbed = (String) this.config
				.getValue("site.screen.enalbed"); // 是否屏蔽
		String screen_replace = (String) this.config
				.getValue("site.screen.replace"); // 替换的字符

		// 是否屏蔽系统中出现的非法词汇
		if ("true".equals(screen_enalbed)) {
			for (int i = 0; i < screen_keyword.length; ++i) {
				if (commentContent.indexOf(screen_keyword[i]) != -1) {
					commentContent = commentContent.replaceAll(screen_keyword[i],
							screen_replace);
					// comment.setContent(commentReply);
				}
			}
		}

		//删除回复中的html
		//commentContent = CommonUtil.eraseHtml(commentContent);
		//commentContent = CommonUtil.htmlEncode(commentContent);
		
		// 构造回复.
		comment.setContent(commentContent);

		// 保存回复.
		cmt_svc.saveComment(comment);
		//删除首页缓存
		FileCache fc = new FileCache();
		fc.deleteUserAllCache(getLoginUser().getLoginName());
		fc = null;
		addActionMessage("修改评论成功完成");

		return SUCCESS;
	}

	/**
	 * 删除一个或多个评论
	 * 
	 * @return
	 */
	private String delete_comment() {
		// 获得参数
		List<Integer> ids = param_util.safeGetIntValues("id");
		if (ids == null || ids.size() == 0) {
			addActionError("没有选择要删除的评论");
			return ERROR;
		}

		// 循环每一个要删除的评论
		int oper_count = 0;
		for (Integer id : ids) {
			// 得到评论
			if (id == null)
				continue;
			Comment comment = cmt_svc.getComment(id);
			if (comment == null) {
				addActionError("未能找到标识为 " + id + " 的评论");
				continue;
			}

			// 得到该评论的文章, 并验证是否有权限删除该文章的评论.
			if (comment.getObjType() != ObjectType.OBJECT_TYPE_ARTICLE.getTypeId()) {
				addActionError("评论 " + id + " 不是文章的评论");
				continue;
			}
			Article article = art_svc.getArticle(comment.getObjId());
			if (article == null) {
				addActionError("评论对应的文章已不存在, 评论将被删除");
			} else if (article.getUserId() != getLoginUser().getUserId()) {
				if (comment.getUserId() != getLoginUser().getUserId()) {
					addActionError("权限不足, 不能删除他人文章的评论");
					continue;
				}
			}

			//删除对评论的记忆
			UserComments userComments=UserComments.getInstance();
			String commentContent = comment.getContent();
			if(getLoginUser() != null){				
				boolean CommentEqual= userComments.CommentIsEqual(getLoginUser().getUserId(), ObjectType.OBJECT_TYPE_ARTICLE.getTypeId(), commentContent);
				if(CommentEqual){
					userComments.DeleteComment(getLoginUser().getUserId(), ObjectType.OBJECT_TYPE_ARTICLE.getTypeId(), commentContent);
				}
			}
			
			// 删除评论
			cmt_svc.deleteComment(comment);
			++oper_count;

		}

		addActionMessage("共删除了 " + oper_count + " 条评论");

		return SUCCESS;
	}

	/**
	 * 审核通过一个或多个评论
	 * 
	 * @return
	 */
	private String audit_comment() {
		// 获得参数.
		List<Integer> ids = param_util.safeGetIntValues("id");
		if (ids == null || ids.size() == 0) {
			addActionError("没有选择要审核通过的评论");
			return ERROR;
		}

		// 循环每一个要审核的评论
		int oper_count = 0;
		for (Integer id : ids) {
			// 得到评论.
			if (id == null)
				continue;
			Comment comment = cmt_svc.getComment(id);
			if (comment == null) {
				addActionError("未能找到标识为 " + id + " 的评论");
				continue;
			}

			// 得到该评论的文章, 并验证是否有权限删除该文章的评论.
			if (comment.getObjType() != ObjectType.OBJECT_TYPE_ARTICLE.getTypeId()) {
				addActionError("评论 " + id + " 不是文章的评论");
				continue;
			}
			Article article = art_svc.getArticle(comment.getObjId());
			if (article == null) {
				addActionError("评论对应的文章已不存在");
				continue;
			} else if (article.getUserId() != getLoginUser().getUserId()) {
				addActionError("权限不足, 不能删除他人文章的评论");
				continue;
			}
			// 删除评论.
			cmt_svc.auditComment(comment);
			++oper_count;
		}
		//删除首页缓存
		FileCache fc = new FileCache();
		fc.deleteUserAllCache(getLoginUser().getLoginName());
		fc = null;
		addActionMessage("共审核通过了 " + oper_count + " 条评论");

		return SUCCESS;
	}

	/**
	 * 取消一个或多个评论的审核
	 * 
	 * @return
	 */
	private String unaudit_comment() {
		// 获得参数
		List<Integer> ids = param_util.safeGetIntValues("id");
		if (ids == null || ids.size() == 0) {
			addActionError("没有选择要取消审核的评论");
			return ERROR;
		}

		// 循环每一个要审核的评论.
		int oper_count = 0;
		for (Integer id : ids) {
			// 得到评论
			if (id == null)
				continue;
			Comment comment = cmt_svc.getComment(id);
			if (comment == null) {
				addActionError("未能找到标识为 " + id + " 的评论");
				continue;
			}

			// 得到该评论的文章, 并验证是否有权限删除该文章的评论.
			if (comment.getObjType() != ObjectType.OBJECT_TYPE_ARTICLE.getTypeId()) {
				addActionError("评论 " + id + " 不是文章的评论");
				continue;
			}
			Article article = art_svc.getArticle(comment.getObjId());
			if (article == null) {
				addActionError("评论对应的文章已不存在");
				continue;
			} else if (article.getUserId() != getLoginUser().getUserId()) {
				addActionError("权限不足, 不能取消审核他人文章的评论");
				continue;
			}
			// 删除评论
			cmt_svc.unauditComment(comment);
			++oper_count;
		}
		//删除首页缓存
		FileCache fc = new FileCache();
		fc.deleteUserAllCache(getLoginUser().getLoginName());
		fc = null;
		addActionMessage("共取消审核了 " + oper_count + " 条评论");

		return SUCCESS;
	}

	/**
	 * 修改点击次数
	 * 
	 * @return
	 * @throws Exception
	 */
	private String view() throws Exception {
		int articleId = param_util.safeGetIntParam("articleId");
		art_svc.increaseViewCount(articleId, 1);
		Article article = art_svc.getArticle(articleId);
		viewcount_svc.incViewCount(ObjectType.OBJECT_TYPE_ARTICLE.getTypeId(), article.getArticleId(), 1);
		return ERROR;
	}
	
	/**
	 * 中文分词
	 * 
	 * @return
	 * @throws Exception
	 */
	private String split() throws Exception {
		String splitString = param_util.getStringParam("articleTitle");
		//log.info(splitString);
		String splitAfter = ChineseAnalyzer.splitString(splitString);
		if (!"".equals(splitAfter)) {
			splitString = splitAfter.substring(0, splitAfter.length() - 1);	
		}
		//log.info(splitString);		
		PrintWriter out = response.getWriter();
		out.print(CommonUtil.escape(splitString));     
		return NONE;
	}
	
	/**
	 * @return
	 * @throws Exception
	 */
	private String tags() throws Exception {
		if (this.accessControlService.isSystemAdmin(getLoginUser()._getUserObject())) {
			int bid = param_util.getIntParam("bid");
			int eid = param_util.getIntParam("eid");
			for (int i = bid; i <= eid; i++) {
				String currentTitle = "";
				String afterTitle = "";
				Article article = art_svc.getArticle(i);
				if (article != null) {
					currentTitle = article.getTitle();
					afterTitle = ChineseAnalyzer.splitString(currentTitle);
					if (!"".equals(afterTitle)) {
						afterTitle = afterTitle.substring(0, afterTitle.length() - 1);	
					}
					if ("".equals(afterTitle)) {
						article.setTags(currentTitle);
					} else {
						article.setTags(afterTitle);
					}
					art_svc.updateArticle(article);
				}
			}
		}
		PrintWriter out = response.getWriter();
		out.flush();
		out.close();
		return NONE;
	}

	/**
	 * 将学科列表放到 request 中
	 */
	// private void putSubjectListToRequest() {
	// super.setRequestAttribute("subject_list", subj_svc.getSubjectList());
	// }
	/** 将元学科列表放到'request'中 */
	private void putMetaSubjectListToRequest() {
		setRequestAttribute("subject_list", subj_svc.getMetaSubjectList());
	}

	/**
	 * 把 grade_list 放到 request 环境中, 提供给模板使用
	 */
	private void putGradeListToRequest() {
		super.setRequestAttribute("grade_list", subj_svc.getGradeList());
	}

	/**
	 * 将文章系统分类放到 request 中
	 */
	private void putArticleCategoryToRequest() {
		Object syscate_tree = cate_svc.getCategoryTree(CategoryService.ARTICLE_CATEGORY_TYPE);
		setRequestAttribute("article_categories", syscate_tree);
	}
	
	private void putChannelArticleCategoryToRequest(Channel channel)
	{
		Object cate_tree = cate_svc.getCategoryTree("channel_article_" + channel.getChannelId(),false);
		setRequestAttribute("channel_article_categories", cate_tree);
		setRequestAttribute("channelId", channel.getChannelId());
	}

	/**
	 * 将'个人分类树：usercate_tree'放到'request'中
	 */
	private void putUserCategoryToRequest() {
		String uc_itemType = CommonUtil.toUserArticleCategoryItemType(getLoginUser().getUserId());
		Object usercate_tree = cate_svc.getCategoryTree(uc_itemType);
		setRequestAttribute("usercate_tree", usercate_tree);
	}

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
	 * 检测能否对文档进行操作
	 * 
	 * @param article 文章对象
	 * @param cmd 进行的操作
	 * @return
	 */
	@Override
	protected final boolean checkRight(Tuple<Article, User> tuple, String cmd) {
		Article article = tuple.getKey();

		// 只能对自己的文档进行操作
		if (article.getUserId() != getLoginUser().getUserId())
			return false;
		return true;
	}

	/** 分类服务对象的set方法 */
	public void setCategoryService(CategoryService cat_svc) {
		this.cate_svc = cat_svc;
	}

	/** 评论服务的set方法 */
	public void setCommentService(CommentService cmt_svc) {
		this.cmt_svc = cmt_svc;
	}

	/** 学科服务的set方法 */
	public void setSubjectService(SubjectService subj_svc) {
		this.subj_svc = subj_svc;
	}

	/** 设置群组服务 */
	public void setGroupService(GroupService group_svc) {
		this.group_svc = group_svc;
	}

	/** 设置点击率服务 */
	public void setViewCountService(ViewCountService viewcount_svc) {
		this.viewcount_svc = viewcount_svc;
	}

	/** 计算用户个人 文章分类树的 itemType */
	protected String getArticleUserCategoryItemType() {
		return CommonUtil.toUserArticleCategoryItemType(getLoginUser().getUserId());
	}

	public void setPrepareCourseService(PrepareCourseService pc_svc) {
		this.pc_svc = pc_svc;
	}

	public PrepareCourseService getPrepareCourseService() {
		return this.pc_svc;
	}

	/** 配置服务的'set'方法 */
	public void setConfigService(ConfigService configService) {
		this.configService = configService;
	}

	/** 标签服务的'set'方法 */
	public void setTagService(TagService tagService) {
		this.tagService = tagService;
	}

	/**角色权限服务*/
	public UserPowerService getUserPowerService(){
		return this.userPowerService;
	}
	/**角色权限服务*/
	public void setUserPowerService(UserPowerService userPowerService){
		this.userPowerService=userPowerService;
	}
	
	public SpecialSubjectService getSpecialSubjectService() {
		return specialSubjectService;
	}

	public void setSpecialSubjectService(
			SpecialSubjectService specialSubjectService) {
		this.specialSubjectService = specialSubjectService;
	}

	public void setChannelPageService(ChannelPageService channelPageService) {
		this.channelPageService = channelPageService;
	}

	public void setAccessControlService(AccessControlService accessControlService) {
		this.accessControlService = accessControlService;
	}

	public void setPagingService(PagingService pagingService) {
		this.pagingService = pagingService;
	}


	public void setUserIndexHtmlService(UserIndexHtmlService userIndexHtmlService) {
		this.userIndexHtmlService = userIndexHtmlService;
	}

	public String getSplitString() {
		return splitString;
	}

	public void setSplitString(String splitString) {
		this.splitString = splitString;
	}
	
	//更新文章最新文章模块
	public void updateArticleModule(User user)
	{
		
	}
	
	public void setResourceService(ResourceService res_svc){
		this.res_svc=res_svc;
	}
	
	public ResourceService getResourceService(){
		return this.res_svc;
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
	/** 用户文件存储服务的set方法 */
	public void setStoreManager(StoreManager sto_mgr) {
		this.sto_mgr = sto_mgr;
	}
	public void setUsername(String username) {
		this.username = username;
	}

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	
}
