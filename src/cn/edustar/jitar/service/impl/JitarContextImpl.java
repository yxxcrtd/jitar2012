package cn.edustar.jitar.service.impl;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Hashtable;
import java.util.Map;
import java.util.Properties;

import javax.servlet.ServletContext;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.hibernate.SessionFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.web.context.ServletContextAware;

import cn.edustar.jitar.JitarContext;
import cn.edustar.jitar.manage.OnlineManage;
//import cn.edustar.jitar.query.sitefactory.UserHtmlService;
import cn.edustar.jitar.query.sitefactory.UserIndexHtmlService;
import cn.edustar.jitar.service.AccessControlService;
import cn.edustar.jitar.service.ActionService;
import cn.edustar.jitar.service.ChannelPageService;
import cn.edustar.jitar.service.UnitService;
import cn.edustar.jitar.service.ArticleService;
import cn.edustar.jitar.service.BbsService;
import cn.edustar.jitar.service.CacheProvider;
import cn.edustar.jitar.service.CategoryService;
import cn.edustar.jitar.service.CommentService;
import cn.edustar.jitar.service.ConfigService;
import cn.edustar.jitar.service.FriendService;
import cn.edustar.jitar.service.GroupService;
import cn.edustar.jitar.service.LeavewordService;
import cn.edustar.jitar.service.OnlineManager;
import cn.edustar.jitar.service.PageService;
import cn.edustar.jitar.service.PhotoService;
import cn.edustar.jitar.service.PhotoStapleService;
import cn.edustar.jitar.service.PlacardService;
import cn.edustar.jitar.service.PrivilegeManager;
import cn.edustar.jitar.service.ResourceService;
import cn.edustar.jitar.service.ScriptManager;
import cn.edustar.jitar.service.StatService;
import cn.edustar.jitar.service.SubjectService;
import cn.edustar.jitar.service.TagService;
import cn.edustar.jitar.service.VideoService;
import cn.edustar.jitar.service.TemplateProcessor;
import cn.edustar.jitar.service.UserService;
import cn.edustar.jitar.service.PrepareCourseService;
import cn.edustar.jitar.service.ViewCountService;
import cn.edustar.jitar.service.UPunishScoreService;
import cn.edustar.jitar.service.UnitTypeService;
import cn.edustar.jitar.service.SiteThemeService;
import cn.edustar.jitar.service.GroupKTUserService;

/**
 * 教研系统环境对象实现. 实现将通过 JitarContext 可以获得系统内任何服务.
 * 
 * 此环境对象在一个 WebApplication 中有且只有一个.
 * 
 *
 *
 */
public class JitarContextImpl extends JitarContext implements ServletContextAware, ApplicationContextAware {
	/** 日志记录器. */
	private static final Logger logger = LoggerFactory.getLogger(JitarContextImpl.class);
	
	/** WEB 应用程序环境对象. */
	private ServletContext servlet_ctxt;
	
	/** Spring ApplicationContext 环境对象. */
	private ApplicationContext spring_ctxt;
	
	/** 配置服务对象. */
	private ConfigService conf_svc;
	
	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.JitarContext#getSpringContext()
	 */
	public ApplicationContext getSpringContext() {
		return this.spring_ctxt;
	}

	/** 配置服务对象. */
	public void setConfigService(ConfigService conf_svc) {
		this.conf_svc = conf_svc;
	}
	
	/** 配置服务对象. */
	public ConfigService getConfigService() {
		if (this.conf_svc != null)
			return this.conf_svc;
		
		this.conf_svc = (ConfigService)spring_ctxt.getBean("configService");
		return this.conf_svc;
	}
	
	/** 用户访问服务对象. */
	private UserService user_svc;
	
	/** 属性集合 */
	private Map<String, Object> attribute = new Hashtable<String, Object>();

	/**
	 * 缺省构造函数.
	 */
	public JitarContextImpl() {
		// logger.info("JitarContextImpl instaniant this = " + this);
	}
	
	private File prop_file = null;
	private long prop_file_lastmodified = 0;
	private Properties props = new Properties();
	
	/**
	 * 初始化方法.
	 */
	public void init() {
		// 把自己放到 servletContext 里面.
		servlet_ctxt.setAttribute(JITAR_CONTEXT_KEY_NAME, this);
		
		logger.info("JitarContextImpl initialized.");
		
		prop_file = new File(servlet_ctxt.getRealPath("/WEB-INF/classes/jitar.properties"));
		this.props = this.loadProperty(prop_file);		
	}
	
	private Properties loadProperty(File file) {
		this.prop_file_lastmodified = file.lastModified();
		Properties props = new Properties();
		try {
			InputStream stream = new java.io.FileInputStream(file);
			if (stream != null)
				props.load(stream);
		} catch (IOException ex) {
			logger.warn("无法从 jitar.properties 文件中加载属性.", ex);
		}
		this.props = props;
		return props;
	}
	
	/**
	 * 得到属性集.
	 * @return
	 */
	public Properties getProperties() {
		// 如果文件没有变过则返回.
		if (this.prop_file != null && this.prop_file_lastmodified == this.prop_file.lastModified())
			return this.props;
		
		// 如果没有文件或文件已经被删除则返回 empty properties.
		if (this.prop_file == null || this.prop_file.exists() == false)
			return new Properties();
		
		// 重新加载.
		return this.loadProperty(prop_file);
	}
	
	/**
	 * 销毁方法.
	 */
	public void destroy() {
		logger.debug("JitarContextImpl destroyed.");
		
		// no-op
	}

	/*
	 * (non-Javadoc)
	 * @see org.springframework.web.context.ServletContextAware#setServletContext(javax.servlet.ServletContext)
	 */
	public void setServletContext(ServletContext servletContext) {
		this.servlet_ctxt = servletContext;
	}
	
	/**
	 * 得到 WEB 应用系统环境对象.
	 * @return
	 */
	public ServletContext getServletContext() {
		return this.servlet_ctxt;
	}

	/*
	 * (non-Javadoc)
	 * @see org.springframework.context.ApplicationContextAware#setApplicationContext(org.springframework.context.ApplicationContext)
	 */
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.spring_ctxt = applicationContext;
	}
	
	/**
	 * 得到 Spring ApplicationContext 环境对象.
	 * @return
	 */
	public ApplicationContext getApplicationContext() {
		return this.spring_ctxt;
	}
	
	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.service.iface.TarContext#getVersion()
	 */
	public String getVersion() {
		return "1.0.0";
	}
	
	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.JitarContext#getAttribute(java.lang.String)
	 */
	public Object getAttribute(String key) {
		return this.attribute.get(key);
	}

	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.JitarContext#setAttribute(java.lang.String, java.lang.Object)
	 */
	public void setAttribute(String key, Object value) {
		this.attribute.put(key, value);
	}

	private DataSource _data_src;
	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.JitarContext#getDataSource()
	 */
	public DataSource getDataSource() {
		if (this._data_src == null) 
			this._data_src = (DataSource)this.spring_ctxt.getBean("DataSource");
		return this._data_src;
	}
	
	public void setDataSource(DataSource data_src) {
		this._data_src = data_src;
	}
	
	private SessionFactory _sf;

	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.JitarContext#getSessionFactory()
	 */
	public SessionFactory getSessionFactory() {
		if (this._sf == null)
			this._sf = (SessionFactory)this.spring_ctxt.getBean("sessionFactory");
		return this._sf;
	}
	
	public void setSessionFactory(SessionFactory sf) {
		this._sf = sf;
	}
	
	/**
	 * 得到配置的 spring HibernateTemplate 对象.
	 * @return
	 */
	//public HibernateTemplate getHibernateTemplate() {
	//	return new HibernateTemplate(this.getSessionFactory());
	//}
	
	/** 模板合成器. */
	private TemplateProcessor templateProcessor;
	
	/**
	 * 得到模板合成器.
	 * @return
	 */
	public TemplateProcessor getTemplateProcessor() {
		if (templateProcessor == null)
			return (TemplateProcessor)this.spring_ctxt.getBean("templateProcessor");
		else
			return templateProcessor;
	}

	/**
	 * 设置模板合成器.
	 */
	public void setTemplateProcessor(TemplateProcessor templateProcessor) {
		this.templateProcessor = templateProcessor;
	}

	/** 站点配置 */	
	private SiteThemeService siteThemeService;
	public SiteThemeService getSiteThemeService(){
		if(this.siteThemeService == null){
			this.siteThemeService = (SiteThemeService)this.spring_ctxt.getBean("siteThemeService");
		}
		return this.siteThemeService;
	}
	public void setSiteThemeService(SiteThemeService siteThemeService){
		this.siteThemeService = siteThemeService;
	}
	
	/** 用户访问服务对象. */
	public UserService getUserService() {
		if (this.user_svc == null) {
			this.user_svc = (UserService)this.spring_ctxt.getBean("userService");
		}
		return this.user_svc;
	}
	
	/** 用户访问服务对象. */
	public void setUserService(UserService user_svc) {
		this.user_svc = user_svc;
	}

	/** Page 服务对象. */
	private PageService pp_svc;
	
	/** Page 服务对象. */
	public PageService getPageService() {
		if (this.pp_svc == null) {
			this.pp_svc = (PageService)this.spring_ctxt.getBean("pageService");
		}
		return this.pp_svc;
	}
	
	/** Page 服务对象. */
	public void setPageService(PageService pp_svc) {
		this.pp_svc = pp_svc;
	}

	/** 文章服务对象. */
	private ArticleService art_svc;

	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.JitarContext#getArticleService()
	 */
	public ArticleService getArticleService() {
		if (this.art_svc == null) {
			this.art_svc = (ArticleService)this.spring_ctxt.getBean("articleService");
		}
		return this.art_svc;
	}
	
	/**
	 * 设置文章服务对象.
	 * @param art_svc
	 */
	public void setArticleService(ArticleService art_svc) {
		this.art_svc = art_svc;
	}

	/** 视频服务对象. */
	private VideoService video_svc;
	
	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.JitarContext#getArticleService()
	 */
	public VideoService getVideoService() {
		if (this.video_svc == null) {
			this.video_svc = (VideoService)this.spring_ctxt.getBean("videoService");
		}
		return this.video_svc;
	}
	
	/**
	 * 设置视频服务对象.
	 * @param art_svc
	 */
	public void setVideoService(VideoService video_svc) {
		this.video_svc = video_svc;
	}	
	/** 标签服务对象. */
	private TagService tag_svc;

	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.JitarContext#getTagService()
	 */
	public TagService getTagService() {
		if (tag_svc == null) {
			tag_svc = (TagService)this.spring_ctxt.getBean("tagService");
		}
		return tag_svc;
	}

	/** 设置标签服务对象. */
	public void setTagService(TagService tag_svc) {
		this.tag_svc = tag_svc;
	}
	
	/** 群组服务对象. */
	private GroupService group_svc;

	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.JitarContext#getGroupService()
	 */
	public GroupService getGroupService() {
		if (this.group_svc == null) {
			this.group_svc = (GroupService)this.spring_ctxt.getBean("groupService");
		}
		return this.group_svc;
	}
	
	/** 设置群组服务对象. */
	public void setGroupService(GroupService group_svc) {
		this.group_svc = group_svc;
	}
	
	/** 在线服务对象 */
	private OnlineManager online_mgr;
	
	/** 设置在线服务对象 */
	public void setOnlineManager(OnlineManager online_mgr) {
		this.online_mgr = online_mgr;
	}
	
	/** 得到在线服务对象 */
	public OnlineManager getOnlineManager() {
		if (this.online_mgr == null) {
			this.online_mgr = (OnlineManager)this.spring_ctxt.getBean("onlineManager");
		}
		return this.online_mgr;
	}

	/** 学科服务 */
	private SubjectService subj_svc;
	
	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.JitarContext#getSubjectService()
	 */
	public SubjectService getSubjectService() {
		if (subj_svc == null)
			subj_svc = (SubjectService)this.spring_ctxt.getBean("subjectService");
		return this.subj_svc;
	}

	/** 设置学科服务 */
	public void setSubjectService(SubjectService subj_svc) {
		this.subj_svc = subj_svc;
	}

	/** 相册服务 */
	private PhotoService photo_svc;
	private PhotoStapleService photoStaple_Service;
	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.JitarContext#getPhotoService()
	 */
	public PhotoService getPhotoService() {
		if (this.photo_svc == null) 
			this.photo_svc = (PhotoService)this.spring_ctxt.getBean("photoService");
		return this.photo_svc;
	}
	
	/** 设置相册服务 */
	public void setPhotoService(PhotoService photo_svc) {
		this.photo_svc = photo_svc;
	}
	
	/** 设置相册分类服务 */
	public PhotoStapleService getPhotoStapleService() {
		if (this.photoStaple_Service == null) 
			this.photoStaple_Service = (PhotoStapleService)this.spring_ctxt.getBean("photoStapleService");
		return this.photoStaple_Service;
	}	
	public void setPhotoStapleService(PhotoStapleService photoStaple_Service) {
		this.photoStaple_Service = photoStaple_Service;
	}
	
	
	/** 资源服务 */
	private ResourceService res_svc;

	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.JitarContext#getResourceService()
	 */
	public ResourceService getResourceService() {
		if (this.res_svc == null)
			this.res_svc = (ResourceService)this.spring_ctxt.getBean("resourceService");
		return this.res_svc;
	}
	
	/** 资源服务 */
	public void setResourceService(ResourceService res_svc) {
		this.res_svc = res_svc;
	}

	/** 统计服务 */
	private StatService stat_svc;
	
	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.JitarContext#getStatService()
	 */
	public StatService getStatService() {
		if (this.stat_svc == null)
			this.stat_svc = (StatService)this.spring_ctxt.getBean("statService");
		return this.stat_svc;
	}

	/** 设置统计服务 */
	public void setStatService(StatService stat_svc) {
		this.stat_svc = stat_svc;
	}
	
	/** 公告服务 */
	private PlacardService pla_svc;
	
	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.JitarContext#getPlacardService()
	 */
	public PlacardService getPlacardService() {
		if (this.pla_svc == null)
			this.pla_svc = (PlacardService)this.spring_ctxt.getBean("placardService");
		return this.pla_svc;
	}
	
	/** 设置公告服务 */
	public void setPlacardService(PlacardService pla_svc) {
		this.pla_svc = pla_svc;
	}
	
	/** 群组论坛服务 */
	private BbsService bbs_svc;
	
	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.JitarContext#getBbsService()
	 */
	public BbsService getBbsService() {
		if (this.bbs_svc == null)
			this.bbs_svc = (BbsService)this.spring_ctxt.getBean("bbsService");
		return this.bbs_svc;
	}
	
	/** 设置群组论坛服务 */
	public void setBbsService(BbsService bbs_svc) {
		this.bbs_svc = bbs_svc;
	}
	
	/** 分类服务对象 */
	private CategoryService cate_svc;
	
	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.JitarContext#getCategoryService()
	 */
	public CategoryService getCategoryService() {
		if (this.cate_svc == null)
			this.cate_svc = (CategoryService)this.spring_ctxt.getBean("categoryService");
		return this.cate_svc;
	}
	
	/** 设置分类服务对象 */
	public void setCategoryService(CategoryService cate_svc) {
		this.cate_svc = cate_svc;
	}
	
	
	/** 活动服务 */
	private ActionService act_svc;	
	public ActionService getActionService() {	
		if (this.act_svc == null)			
			this.act_svc = (ActionService)this.spring_ctxt.getBean("actionService");
		return this.act_svc;
	}		

	public void setActionService(ActionService act_svc) {
		this.act_svc = act_svc;
	}	
	
	/** 集体备课服务 */
	private PrepareCourseService pc_svc;
	public PrepareCourseService getPrepareCourseService()
	{
		if(this.pc_svc == null)
			this.pc_svc = (PrepareCourseService)this.spring_ctxt.getBean("prepareCourseService");
		return this.pc_svc;
	}
	public void setPrepareCourseService(PrepareCourseService pc_svc)
	{
		this.pc_svc = pc_svc;
	}
	
	/** 好友服务 */
	private FriendService frd_svc;
	public FriendService getFriendService()
	{
		if(this.frd_svc == null)
			this.frd_svc = (FriendService)this.spring_ctxt.getBean("friendService");
		return this.frd_svc;
	}
	public void setAFriendService(FriendService frd_svc) {
		this.frd_svc = frd_svc;
	}	
	
	/** 评论服务对象 */
	private CommentService cmt_svc;
	
	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.JitarContext#getCommentService()
	 */
	public CommentService getCommentService() {
		if (this.cmt_svc == null)
			this.cmt_svc = (CommentService)this.spring_ctxt.getBean("commentService");
		return this.cmt_svc;
	}
	
	/** 设置评论服务对象 */
	public void setCommentService(CommentService cmt_svc) {
		this.cmt_svc = cmt_svc;
	}

	/** 点击率服务对象 */
	private ViewCountService viewcount_svc;
	
	
	
	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.JitarContext#getCommentService()
	 */
	public ViewCountService getViewCountService() {
		if (this.viewcount_svc == null)
			this.viewcount_svc = (ViewCountService)this.spring_ctxt.getBean("viewCountService");
		return this.viewcount_svc;
	}
	
	
	/** 设置点击率服务对象 */
	public void setViewCountService(ViewCountService viewcount_svc) {
		this.viewcount_svc = viewcount_svc;
	}
	
	private UPunishScoreService upunish_svc;
	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.JitarContext#getCommentService()
	 */
	public UPunishScoreService getUPunishScoreService() {
		if (this.upunish_svc == null)
			this.upunish_svc = (UPunishScoreService)this.spring_ctxt.getBean("UPunishScoreService");
		return this.upunish_svc;
	}
	
	/** 设置罚分服务对象 */
	public void setUPunishScoreService(UPunishScoreService upunish_svc) {
		this.upunish_svc = upunish_svc;
	}	

	private UnitTypeService unitType_svc;
	
	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.JitarContext#getCommentService()
	 */
	public UnitTypeService getUnitTypeService() {
		if (this.unitType_svc == null)
			this.unitType_svc = (UnitTypeService)this.spring_ctxt.getBean("unitTypeService");
		return this.unitType_svc;
	}
	
	
	/** 设置单位分类服务对象 */
	public void setUnitTypeService(UnitTypeService unitType_svc) {
		this.unitType_svc = unitType_svc;
	}

	

		
	/** 脚本服务对象 */
	private ScriptManager script_mgr;

	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.JitarContext#getScriptManager()
	 */
	public ScriptManager getScriptManager() {
		if (this.script_mgr == null)
			this.script_mgr = (ScriptManager)this.spring_ctxt.getBean("scriptManager");
		return this.script_mgr;
	}
	
	/** 脚本服务对象 */
	public void setScriptManager(ScriptManager script_mgr) {
		this.script_mgr = script_mgr;
	}

	/** 留言服务对象 */
	private LeavewordService lw_svc;
	
	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.JitarContext#getLeavewordService()
	 */
	public LeavewordService getLeavewordService() {
		if (lw_svc == null)
			lw_svc = (LeavewordService)this.spring_ctxt.getBean("leavewordService");
		return this.lw_svc;
	}

	/** 留言服务对象 */
	public void setLeavewordService(LeavewordService lw_svc) {
		this.lw_svc = lw_svc;
	}

	/** 区域服务 */
	private UnitService unitService;
	

	public UnitService getUnitService() {
		if (this.unitService == null)
			this.unitService = (UnitService)this.spring_ctxt.getBean("unitService");
		return this.unitService;
	}
	
	/** 设置区域服务 */
	public void setUnitService(UnitService unitService) {
		this.unitService = unitService;
	}

	/** 权限管理器 */
	private PrivilegeManager priv_mgr;
	
	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.JitarContext#getPrivilegeManager()
	 */
	public PrivilegeManager getPrivilegeManager() {
		if (this.priv_mgr == null)
			this.priv_mgr = (PrivilegeManager)this.spring_ctxt.getBean("privilegeManager");
		return this.priv_mgr;
	}	
	
	/** 设置权限管理器 */
	public void setPrivilegeManager(PrivilegeManager priv_mgr) {
		this.priv_mgr = priv_mgr;
	}
	

	private ChannelPageService channelPageService;
	public void setChannelPageService(ChannelPageService channelPageService)
	{
		this.channelPageService = channelPageService;
	}
	public ChannelPageService getChannelPageService()
	{
		if (this.channelPageService == null)
			this.channelPageService = (ChannelPageService)this.spring_ctxt.getBean("channelPageService");
		return this.channelPageService;
	}
	
/*	private UserHtmlService userHtmlService;
	public UserHtmlService getUserHtmlService() {
		if (this.userHtmlService == null)
			this.userHtmlService = (UserHtmlService)this.spring_ctxt.getBean("userHtmlService");
		return userHtmlService;
	}

	public void setUserHtmlService(UserHtmlService userHtmlService) {
		this.userHtmlService = userHtmlService;
	}*/

	private UserIndexHtmlService userIndexHtmlService;
	public UserIndexHtmlService getUserIndexHtmlService()
	{
		if(userIndexHtmlService == null)
			this.userIndexHtmlService = (UserIndexHtmlService)this.spring_ctxt.getBean("userIndexHtmlService");
		
		return this.userIndexHtmlService;
	}
	public void setUserIndexHtmlService(UserIndexHtmlService userIndexHtmlService)
	{
		this.userIndexHtmlService = userIndexHtmlService;
	}
	
	private GroupKTUserService groupKTUserService;
	
	private AccessControlService accessControlService;
	
	
	public AccessControlService getAccessControlService() {
	    if(accessControlService == null)
            this.accessControlService = (AccessControlService)this.spring_ctxt.getBean("accessControlService");        
        return accessControlService;
    }

    public void setAccessControlService(AccessControlService accessControlService) {
        this.accessControlService = accessControlService;
    }

    /** 缓存管理器. */
	private CacheProvider cache_prov;
	
	/** 设置缓存管理器. */
	public void setCacheProvider(CacheProvider cache_mgr) {
		this.cache_prov = cache_mgr;
	}

	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.JitarContext#getCacheProvider()
	 */
	public CacheProvider getCacheProvider() {
		if (cache_prov == null)
			cache_prov = (CacheProvider)spring_ctxt.getBean("cacheProvider");
		return this.cache_prov;
	}

	public GroupKTUserService getGroupKTUserService() {
		if (this.groupKTUserService == null)
			this.groupKTUserService = (GroupKTUserService)spring_ctxt.getBean("groupKTUserService");
		return this.groupKTUserService;			}

	public void setGroupKTUserService(GroupKTUserService groupKTUserService) {
		this.groupKTUserService=groupKTUserService;		
	}
	
	private OnlineManage onlineManage;

	@Override
	public OnlineManage getOnlineManage() {
		if (null == onlineManage) {
			onlineManage = (OnlineManage) this.spring_ctxt.getBean("onlineManage");
		}
		return onlineManage;
	}
}
