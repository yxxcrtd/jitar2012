package cn.edustar.jitar.action;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.edustar.data.DataTable;
import cn.edustar.data.Pager;
import cn.edustar.jitar.JitarConst;
import cn.edustar.jitar.model.Configure;
import cn.edustar.jitar.pojos.SiteNews;
import cn.edustar.jitar.pojos.Subject;
import cn.edustar.jitar.pojos.User;
import cn.edustar.jitar.service.AccessControlService;
import cn.edustar.jitar.service.ConfigService;
import cn.edustar.jitar.service.SiteNewsQueryParam;
import cn.edustar.jitar.service.SubjectService;
import cn.edustar.jitar.service.TagService;

/**
 * 管理站点/学科新闻
 * 
 */
@SuppressWarnings("unused")
public class AdminSiteNewsAction extends ManageBaseAction {

	/** serialVersionUID */
	private static final long serialVersionUID = 3078566670780257690L;

	/** 日志 */
	private static final Logger log = LoggerFactory.getLogger(AdminSiteNewsAction.class);

	/** 学科/学段服务 */
	private SubjectService subj_svc;
	
	private AccessControlService accessControlService;

	/** 学科/学段服务 */
	public void setSubjectService(SubjectService subj_svc) {
		this.subj_svc = subj_svc;
	}

	/** 配置对象 */
	private Configure config;

	/** 配置服务 */
	private ConfigService configService;
	
	/** 标签服务 */
	private TagService tagService;

	/** 新闻类别，图片新闻还是文字新闻 ，type != null 为图片新闻 */
	private String newsType = ""; 
	
	private String displayName = "教研动态";
	
	@Override
	protected String execute(String cmd) throws Exception {
		
		// 得到配置对象
		this.config = configService.getConfigure();		
		
		// 检查是否登陆及是否有管理权限
		// 管理权限：系统管理员、系统内容管理员
		if (isUserLogined() == false)
			return LOGIN;

		if (newsAdmin() == false) {
			this.addActionError("没有管理系统新闻的权限!");
			return ERROR;
		}

		newsType = this.param_util.getStringParam("type", null);
		if(null == newsType || newsType.trim().length() == 0){
		    newsType = "";
		}
		if(newsType.trim().length() > 0 ){
		    displayName = "图片新闻";
	        setRequestAttribute("newsType", newsType);
		}
        setRequestAttribute("displayName", displayName);
        
		if (cmd == null || cmd.length() == 0)
			cmd = "list";

		if ("list".equals(cmd))
			return list();
		else if ("add".equals(cmd))
			return add();
		else if ("edit".equals(cmd))
			return edit();
		else if ("save".equals(cmd))
			return save();
		else if ("delete".equals(cmd))
			return delete();
		else if ("audit".equals(cmd))
			return audit();
		else if ("unaudit".equals(cmd))
			return unaudit();
		return super.unknownCommand(cmd);
	}

	// 判断当前用户是否具有新闻管理的权限,如果是:超级管理员,超级内容管理员,学科管理员,学段学科管理员则都有权限
	private boolean newsAdmin() {
		User u = getLoginUser();
		if(u == null) return false;		
		return (this.accessControlService.isSystemAdmin(u) || this.accessControlService.isSystemContentAdmin(u));
	}

	/**
	 * 列出新闻列表
	 * 
	 * @return
	 */
	private String list() {
		int subjectId = param_util.getIntParam("subjectId");
		if (subjectId > 0) {
			Subject subject = subj_svc.getSubjectById(subjectId);
			setRequestAttribute("subject", subject);
			if (subject == null) {
				addActionError("未能找到指定标识的学科");
				return ERROR;
			}
		}
		setRequestAttribute("subjectId", subjectId);
		setRequestAttribute("admin", "0");
		// 构造查询参数.
		SiteNewsQueryParam param = new SiteNewsQueryParam();
		if(newsType.length() == 0){
		    param.hasPicture = false;
		}
		else{
		    param.hasPicture = true;
		}
		param.status = null; // 所有, 包括待审核/删除的.
		param.subjectId = subjectId < 0 ? null : subjectId;
		String subjectIds = "";
		List<Subject> subject_list = super.subjectService.getSubjectList();
		if (this.newsAdmin()) {
			// 是管理员,不限制查询范围
			setRequestAttribute("admin", "1");
		}
		Pager pager = super.getCurrentPager();
		pager.setItemNameAndUnit(displayName, "条");
		// 获得新闻数据.
		DataTable news_list = subj_svc.getSiteNewsDataTable(param, pager);

		putSubjectList();
		setRequestAttribute("news_list", news_list);
		setRequestAttribute("pager", pager);
		putSubjectList();
		return LIST_SUCCESS;
	}

	/**
	 * 添加一个新闻. URL: admin_news.action?cmd=add&subjectId=x .
	 * 
	 * @return
	 */
	private String add() {
		SiteNews news = new SiteNews();
		news.setSubjectId(param_util.getIntParam("subjectId"));
		news.setStatus(SiteNews.NEWS_STATUS_NORMAL);
		setRequestAttribute("news", news);
		putSubjectList();
		setRequestAttribute("__referer", "admin_news.action?cmd=list&subjectId=" + param_util.getIntParam("subjectId") + "&type=" + newsType);
		if(this.newsAdmin()){
		    setRequestAttribute("admin", "1");
		}
		return "Add_Or_Edit";
	}

	/**
	 * 编辑一个新闻. URL: admin_news.action?cmd=edit&newsId=x .
	 * 
	 * @return
	 */
	private String edit() {
		int newsId = param_util.getIntParam("newsId");
		SiteNews news = subj_svc.getSiteNews(newsId);
		if (news == null) {
			addActionError("未找到指定标识的新闻");
			return ERROR;
		}
		setRequestAttribute("news", news);
		putSubjectList();
		setRequestAttribute("__referer", "admin_news.action?cmd=list&subjectId=" + param_util.getIntParam("subjectId") + "&type=" + newsType);
		if(this.newsAdmin()){
            setRequestAttribute("admin", "1");
        }
		return "Add_Or_Edit";
	}

	
	/** 将 subject_list 放到 request 中，其提供给模板使用 */
	/*TODO:检查这里..........使用了基类的方法
	private void putSubjectList() {
		// System.out.println("根据用户权限检查用户的学科");
		List<Subject> subject_list = null;
		subject_list = subj_svc.getSubjectList();
		if (this.newsAdmin()) {
			setRequestAttribute("admin", "1");
		}
		setRequestAttribute("subject_list", subject_list);
	}
	*/
	/**
	 * 新建/更新一个新闻
	 * 
	 * @return
	 */
	private String save() {
		SiteNews news = collectNewsObject();
		if (news == null) {			
			addActionError("未找到指定标识的新闻");
			return ERROR;
		}		
		
		// 过滤新闻中出现的非法词汇
		if (screenNews(news)) {
			addActionError("内容含有 非法词汇 。");
			if(news.getNewsId() < 1){
				this.addActionLink("返回", "admin_news.action?cmd=add&subjectId=" + news.getSubjectId() + "&type=" + newsType);
			}
			else{
				this.addActionLink("返回", "admin_news.action?cmd=edit&newsId=" + news.getNewsId() + "&type=" + newsType);
			}
			return ERROR;
		}
		if(news.getTitle().trim().length() == 0){
			this.addActionError("请输入标题!");
			if(news.getNewsId() < 1){
				this.addActionLink("返回", "admin_news.action?cmd=add&subjectId=" + news.getSubjectId() + "&type=" + newsType);
			}
			else{
				this.addActionLink("返回", "admin_news.action?cmd=edit&newsId=" + news.getNewsId() + "&type=" + newsType);
			}
			return ERROR;
		}
		
		if(news.getContent().trim().length() == 0){
			this.addActionError("请输入内容!");
			if(news.getNewsId() < 1){
				this.addActionLink("返回", "admin_news.action?cmd=add&subjectId=" + news.getSubjectId() + "&type=" + newsType);
			}
			else{
				this.addActionLink("返回", "admin_news.action?cmd=edit&newsId=" + news.getNewsId() + "&type=" + newsType);
			}
			return ERROR;
		}
		if(this.newsType.length() > 0){
		    if(news.getPicture() == null || news.getPicture().trim().length() == 0){
		        this.addActionError("请选择或者上传一个宽度不小于 " + JitarConst.IMG_WIDTH_CONTROL + "px、高度不小于 " + JitarConst.IMG_HEIGHT_CONTROL + " px的 jpg 图片");
	            if(news.getNewsId() < 1){
	                this.addActionLink("返回", "admin_news.action?cmd=add&subjectId=" + news.getSubjectId() + "&type=" + newsType);
	            }
	            else{
	                this.addActionLink("返回", "admin_news.action?cmd=edit&newsId=" + news.getNewsId() + "&type=" + newsType);
	            }
	            return ERROR;
		    }
		}
		
		// 如果是新建则设置发布人为当前登录用户.
		if (news.getNewsId() == 0) {
			news.setUserId(getLoginUser().getUserId());
		}
		// 保存新闻
		subj_svc.saveOrUpateSiteNews(news);
		addActionMessage("添加或修改" + this.displayName + "成功完成");
		return SUCCESS;
	}

	/** 收集提交的参数到 SiteNews 对象中 */
	private SiteNews collectNewsObject() {
		int newsId = param_util.getIntParam("newsId");
		SiteNews news;
		if (newsId != 0) {
			news = subj_svc.getSiteNews(newsId);
		} else {
			news = new SiteNews();
		}
		if (news == null) {
			return null;
		}
		news.setSubjectId(param_util.getIntParam("subjectId"));
		news.setTitle(param_util.safeGetStringParam("title", "未输入标题"));
		news.setContent(param_util.safeGetStringParam("content"));
		news.setPicture(param_util.safeGetStringParam("picUrl"));
		int audit = param_util.getIntParam("audit");
		news.setStatus(audit == 0 ? SiteNews.NEWS_STATUS_NORMAL : SiteNews.NEWS_STATUS_WAIT_AUTID);
		return news;
	}
	
	/**
	 * 过滤新闻中出现的非法词汇
	 * 
	 * @param news
	 * @return
	 */
	private boolean screenNews(SiteNews news) {
		String title = news.getTitle(); // 新闻标题
		String content = news.getContent(); // 新闻描述
		String[] screen_keyword = tagService.parseTagList((String) this.config.getValue("site.screen.keyword")); // 系统中定义的需要屏蔽的非法词汇数组
		String screen_enalbed = (String) this.config.getValue("site.screen.enalbed"); // 是否屏蔽
		String screen_replace = (String) this.config.getValue("site.screen.replace"); // 替换的字符
		
		// 是否屏蔽系统中出现的非法词汇
		if ("true".equals(screen_enalbed)) {
			for (int i = 0; i < screen_keyword.length; ++i) {
				// 1，检查'新闻标题'中是否含有'非法词汇'
				if (title.equals(screen_keyword[i])) {
						this.addActionError("您输入的'新闻标题'与'非法词汇'完全相同！");
					return true;
				}
				if (title.indexOf(screen_keyword[i]) != -1) {
					title = title.replaceAll(screen_keyword[i], screen_replace);
					news.setTitle(title);
				}
				
				// 2，检查'新闻描述'中是否含有'非法词汇'
	
				if (content.equals(screen_keyword[i])) {
					this.addActionError("您输入的'新闻描述与'非法词汇'完全相同！");
					return true;
				}
				if (content.indexOf(screen_keyword[i]) != -1) {
					content = content.replaceAll(screen_keyword[i], screen_replace);
					news.setContent(content);
				}
			}
		}
		return false;
	}

	/** 提交的 newsId 参数, 可能有多个 newsId */
	private List<Integer> news_ids;

	/** 收集并验证 'newsId=1,2,3' 参数, 用于 delete, audit 等命令的处理 */
	private boolean collectNewsIds() {
		this.news_ids = param_util.safeGetIntValues("newsId");
		if (this.news_ids == null || this.news_ids.size() == 0) {
			addActionError("未选择要操作的" + displayName);
			return false;
		}
		return true;
	}

	/**
	 * 删除一个或多个新闻
	 * 
	 * @return
	 */
	private String delete() {
		if (collectNewsIds() == false)
			return ERROR;
		// 循环进行处理.
		int oper_count = 0;
		for (Integer newsId : this.news_ids) {
			SiteNews news = subj_svc.getSiteNews(newsId);
			if (news == null) {
				addActionError("未找到指定标识为 " + newsId + " 的" + displayName);
				continue;
			}
			subj_svc.deleteSiteNews(news);
			addActionMessage( displayName + "'" + news.getTitle() + "' 已经成功删除");
			++oper_count;
		}
		addActionMessage("共删除了 " + oper_count + " 条" + displayName);
		return SUCCESS;
	}

	/**
	 * 审核通过一个或多个新闻
	 * 
	 * @return
	 */
	private String audit() {
		if (collectNewsIds() == false)
			return ERROR;
		// 循环进行处理.
		int oper_count = 0;
		for (Integer newsId : this.news_ids) {
			SiteNews news = subj_svc.getSiteNews(newsId);
			if (news == null) {
				addActionError("未找到指定标识为 " + newsId + " 的新闻");
				continue;
			}
			subj_svc.auditSiteNews(news, true);
			addActionMessage(displayName + "'" + news.getTitle() + "' 成功审核通过");
			++oper_count;
		}
		addActionMessage("共审核通过了 " + oper_count + " 条" + displayName);
		return SUCCESS;
	}

	/**
	 * 解除一个或多个新闻的审核
	 * 
	 * @return
	 */
	private String unaudit() {
		if (collectNewsIds() == false)
			return ERROR;
		// 循环进行处理
		int oper_count = 0;
		for (Integer newsId : this.news_ids) {
			SiteNews news = subj_svc.getSiteNews(newsId);
			if (news == null) {
				addActionError("未找到指定标识为 " + newsId + " 的" + displayName);
				continue;
			}
			subj_svc.auditSiteNews(news, false);
			addActionMessage(displayName + " '" + news.getTitle() + "' 成功设置未审核");
			++oper_count;
		}
		addActionMessage("共设置了 " + oper_count + " 条" + displayName + "为未审核");
		return SUCCESS;
	}

	/** 配置服务的'set'方法 */
	public void setConfigService(ConfigService configService) {
		this.configService = configService;
	}

	public void setAccessControlService(AccessControlService accessControlService) {
		this.accessControlService = accessControlService;
	}

	/** 标签服务的'set'方法 */
	public void setTagService(TagService tagService) {
		this.tagService = tagService;
	}

}
