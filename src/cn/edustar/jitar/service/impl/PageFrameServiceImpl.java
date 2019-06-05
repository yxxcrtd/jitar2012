package cn.edustar.jitar.service.impl;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Properties;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jasig.cas.client.util.CasConst;
import org.springframework.web.context.ServletContextAware;

import com.alibaba.fastjson.JSONObject;

import cn.edustar.jitar.JitarRequestContext;
import cn.edustar.jitar.model.Configure;
import cn.edustar.jitar.model.ObjectType;
import cn.edustar.jitar.model.SiteUrlModel;
import cn.edustar.jitar.model.TemplateModelObject;
import cn.edustar.jitar.model.UserMgrModel;
import cn.edustar.jitar.model.UserMgrClientModel;
import cn.edustar.jitar.model.UtilModel;
import cn.edustar.jitar.pojos.Grade;
import cn.edustar.jitar.pojos.Group;
import cn.edustar.jitar.pojos.Page;
import cn.edustar.jitar.pojos.PrepareCourse;
import cn.edustar.jitar.pojos.SiteTheme;
import cn.edustar.jitar.pojos.SpecialSubject;
import cn.edustar.jitar.pojos.Subject;
import cn.edustar.jitar.pojos.Unit;
import cn.edustar.jitar.pojos.User;
import cn.edustar.jitar.service.GroupService;
import cn.edustar.jitar.service.ModelManager;
import cn.edustar.jitar.service.UnitService;
import cn.edustar.jitar.service.PageFrameService;
import cn.edustar.jitar.service.PageKey;
import cn.edustar.jitar.service.PageService;
import cn.edustar.jitar.service.PrepareCourseService;
import cn.edustar.jitar.service.SiteNavService;
import cn.edustar.jitar.service.SiteThemeService;
import cn.edustar.jitar.service.SpecialSubjectService;
import cn.edustar.jitar.service.SubjectService;
import cn.edustar.jitar.service.UserService;
import cn.edustar.jitar.service.ConfigService;
import cn.edustar.jitar.util.ParamUtil;
import cn.edustar.jitar.util.SubjectNav;
import cn.edustar.jitar.util.WebUtil;
import freemarker.template.Configuration;
import freemarker.template.ObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 * 
 * 页面框架服务的实现。本服务提供将模板文件和数据进行转换，合并成HMTL字符串，以便调用者进行拼接成最终的页面。
 * @author 孟宪会
 *
 */

public class PageFrameServiceImpl implements PageFrameService, ServletContextAware {
	
	/** 日志对象 */
	private static final Log log = LogFactory.getLog(PageFrameServiceImpl.class);
	
	/** WEB APP 环境对象. 需要实现 ServletContextAware 才能得到该对象 */
	private ServletContext servletContext;
	
	/** FreeMarker 配置对象. */
	private Configuration cfg;
	
	/** 用户对象服务 */
	private UserService user_svc;	
	private GroupService group_svc;	
	private PrepareCourseService preparecourse_svc;
	private SubjectService subjectService;
	private SiteThemeService siteThemeService;
	private SiteNavService siteNavService;	
	private SpecialSubjectService specialSubjectService;
	private UnitService unitService;
	private ConfigService configService;
	
	/** 对象包装器 */
	@SuppressWarnings("unused")
	private ObjectWrapper obj_wrapper;

	/** 对象包装器的set方法 */
	public void setObjectWrapper(ObjectWrapper ow) {
		this.obj_wrapper = ow;
	}
	
	/** 模型管理器 */
	private ModelManager model_mgr;

	/** 模型管理器的set方法 */
	public void setModelManager(ModelManager model_mgr) {
		this.model_mgr = model_mgr;
	}
	
	
	/** 页面对象服务 */
	private PageService page_svc;
		
	/** WEB APP 环境对象 */
	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
	}

	/** 设置 FreeMarker 配置对象 */
	public void setConfiguration(Configuration cfg) {
		this.cfg = cfg;
	}
	
	/** 初始化方法，对模板处理器进行配置 
	 * @throws TemplateException */
	public void init() throws IOException, TemplateException {  
		
		this.cfg = new Configuration();
		
		//加载属性文件
		InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("freemarker.properties");
		if(inputStream == null)
		{
			this.cfg.setDateFormat("yyyy-MM-dd");
			this.cfg.setDateTimeFormat("yyyy-MM-dd HH:mm:ss");
			this.cfg.setTimeFormat("HH:mm:ss");
			this.cfg.setNumberFormat("#");
			this.cfg.setLocale(Locale.SIMPLIFIED_CHINESE);
			this.cfg.setDefaultEncoding("UTF-8");
			log.error("属性文件没有加载成功，这里使用程序配置： freemarker.properties ");
		}
		Properties properties = new Properties(); 
		// load the inputStream using the Properties  
		properties.load(inputStream);
		//log.info("属性文件 freemarker.properties 加载成功");
		
		// 按照标准 FreeMarker 帮助，第一步：创建 Configuration 实例		
		cfg.setSettings(properties);

		// 第二步：指定模板来源, 语言等		
		// 设置加载模板文件的地址，网站根目录
		cfg.setServletContextForTemplateLoading(this.servletContext, "/");
		
	// 添加共享变量. 可能抛出 RuntimeException 但是我们也无法处理，只能检查配置
		addSharedVariable();
	}
	
	
// 添加共享变量.
	@SuppressWarnings("rawtypes")
	private void addSharedVariable() throws RuntimeException {
		try {
			List shared_var = model_mgr.getSharedModels();
			if (shared_var == null) return;
			for (Iterator iter = shared_var.iterator(); iter.hasNext(); ) {
				Object o = iter.next();
				if (o instanceof TemplateModelObject) {
					TemplateModelObject tmo = (TemplateModelObject)o;
					cfg.setSharedVariable(tmo.getVariableName(), o);
				}
			}
		} catch (TemplateException ex) {
			log.error("无法添加模板共享变量。", ex);
			throw new RuntimeException("无法添加模板共享变量。也许配置有问题？", ex);
		}
	}
	
	
	/**
	 * 执行模板的转换。
	 * @return 返回转换后的 HTML 内容
	 */
	public String transformTemplate(Object root_map, String template_name){
		String encoding = "utf-8";
		StringWriter writer = new StringWriter(1024 * 2);
		try {
			this.processTemplate(root_map, writer, template_name, encoding);
		} catch (IOException ex) {
			log.error(ex);
			return ex.toString();
		}
		return writer.toString();

	}
	public void processTemplate(Object root_map, Writer writer, String template_name, String encoding) throws IOException {
		if (encoding == null || encoding.length() == 0) {
			encoding = "UTF-8";
		}
		
		// 加载一个模板
		Template template = cfg.getTemplate(template_name, encoding);
		if (template == null) 
			throw new FileNotFoundException("未找到模板: " + template_name);		
		// 执行数据与模板的合并
		try {
			template.process(root_map, writer);
		} catch (TemplateException ex) {
			log.error(ex);
		}
	}
	
	/**
	 * 框架服务页面，供各个模块进行调用
	 */
	public String getFramePage(String objectGuid, String objectType)
	{
		if (objectType.equalsIgnoreCase("user"))
		{
			return this.getUserFramePage(objectGuid);
		}
		else if (objectType.equalsIgnoreCase("group"))
		{
			return this.getGroupFramePage(objectGuid);
		}
		else if (objectType.equalsIgnoreCase("preparecourse"))
		{
			return this.getPrepareCourseFramePage(objectGuid);
		}
		else if (objectType.equalsIgnoreCase("specialsubject"))
		{
			return this.getSpecialSubjectFramePage(objectGuid);
		}
		else if (objectType.equalsIgnoreCase("unit"))
		{
			return this.getUnitFramePage(objectGuid);
		}
		else if (objectType.equalsIgnoreCase("subject"))
		{
			return this.getSubjectFramePage(objectGuid);
		}
		else
		{
			return "暂未实现。";
		}
	}
	
	/**
	 * 用户对象的框架页面
	 * @param objectGuid 用户的对象的 guid
	 * @return 返沪所传递对象的框架页面
	 */
	@SuppressWarnings("unchecked")
	private String getUserFramePage(String objectGuid)
	{		
		if(this.user_svc == null)
		{
			log.error("没有设置 userService ，请检查 applicationContext.xml 中 id='pageFrameService' 节的设置。");
			return "没有设置 userService ，请检查 applicationContext.xml 中 id='pageFrameService' 节的设置。";
		}
		
		HashMap<String,Object> m = new HashMap<String,Object>();
		
		// 得到要请求的用户对象
		User user = this.user_svc.getUserByGuid(objectGuid);
		if(user == null) return "没有得到用户的信息";
		
		// 得到用户工作室的页面和自定义皮肤信息
		Page page = this.page_svc.getUserIndexPage(user);
		if(page == null ) return "没有找到该用户的当前主页信息";	
		
		// 自定义皮肤
		String customSkin = page.getCustomSkin();
		if((customSkin != null ) && (customSkin.length() > 1)) {
			m.put("customSkin", JSONObject.parse(customSkin));
		}
		SimpleDateFormat df = new SimpleDateFormat("yyyy年M月d日 E");
		String currentDate = df.format(new Date());
		//m.put("SiteUrl", this.getSiteUrl());
		//m.put("UserMgrUrl", this.getUserMgrUrl());		
		m.put("user", user);
		m.put("page", page);
		m.put("loginUser", this.getLoginUser());		 
		m.put("currentDate", currentDate);
		m.put("SiteConfig", this.getSiteConfig());
		
		UtilModel utilModel = new UtilModel();
		m.put("Util", utilModel);
		return this.transformTemplate(m,"/WEB-INF/framepage/user_frame_page.ftl");
	}
	
	/**
	 * 协作组框架页面
	 * @param objectGuid
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private String getGroupFramePage(String objectGuid)
	{		
		if(this.group_svc == null)
		{
			log.error("没有设置 groupService ，请检查 applicationContext.xml 中 id='pageFrameService' 节的设置。");
			return "没有设置 groupService ，请检查 applicationContext.xml 中 id='pageFrameService' 节的设置。";
		}
		
		HashMap<String,Object> m = new HashMap<String,Object>();
		
		// 得到要请求的用户对象
		Group group = this.group_svc.getGroupByGuid(objectGuid);
		if(group == null) return "没有得到协作组的信息";
		
		// 得到用户工作室的页面和自定义皮肤信息

		PageKey pk = new PageKey(ObjectType.OBJECT_TYPE_GROUP,group.getGroupId(),"index");

		Page page = this.page_svc.getPageByKey(pk);
		if(page == null ) return "没有找到该协作组的当前主页信息";	
		
		// 自定义皮肤
		String customSkin = page.getCustomSkin();
		if((customSkin != null ) && (customSkin.length() > 1))
		{
			m.put("customSkin", JSONObject.parse(customSkin));
		}
		SimpleDateFormat df = new SimpleDateFormat("yyyy年M月d日 E");
		String currentDate = df.format(new Date());
		//m.put("SiteUrl", this.getSiteUrl());
		//m.put("UserMgrUrl", this.getUserMgrUrl());		
		m.put("group", group);
		m.put("page", page);
		m.put("loginUser", this.getLoginUser());		 
		m.put("currentDate", currentDate);
		m.put("SiteConfig", this.getSiteConfig());
		UtilModel utilModel = new UtilModel();
		m.put("Util", utilModel);
		
		return this.transformTemplate(m,"/WEB-INF/framepage/group_frame_page.ftl");
	}
	
	/**
	 * 集体备课框架页面
	 * @param objectGuid
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String getPrepareCourseFramePage(String objectGuid)
	{	

		if(this.preparecourse_svc == null)
		{
			log.error("没有设置 prepareCourseService ，请检查 applicationContext.xml 中 id='pageFrameService' 节的设置。");
			return "没有设置 prepareCourseService ，请检查 applicationContext.xml 中 id='pageFrameService' 节的设置。";
		}
		
		HashMap<String,Object> m = new HashMap<String,Object>();
	
		// 得到要请求的用户对象
		PrepareCourse prepareCourse = this.preparecourse_svc.getPrepareCourseByGuid(objectGuid);
		if(prepareCourse == null) return "没有找到集体备课信息";
		
		// 得到用户工作室的页面和自定义皮肤信息
	
		PageKey pk = new PageKey(ObjectType.OBJECT_TYPE_PREPARECOURSE,prepareCourse.getPrepareCourseId(),"index");
	
		Page page = this.page_svc.getPageByKey(pk);
		if(page == null ) return "没有找到该集体备课的当前主页信息";	
		
		// 自定义皮肤
		String customSkin = page.getCustomSkin();
		if((customSkin != null ) && (customSkin.length() > 1))
		{
			m.put("customSkin", JSONObject.parse(customSkin));
		}
		SimpleDateFormat df = new SimpleDateFormat("yyyy年M月d日 E");
		String currentDate = df.format(new Date());
		//m.put("SiteUrl", this.getSiteUrl());
		//m.put("UserMgrUrl", this.getUserMgrUrl());		
		m.put("prepareCourse", prepareCourse);
		m.put("page", page);
		m.put("loginUser", this.getLoginUser());		 
		m.put("currentDate", currentDate);
		m.put("SiteConfig", this.getSiteConfig());
		m.put("SiteNavList", this.siteNavService.getAllSiteNav(false,0,0));
		m.put("ConfigFromTemplate", "1");
		return this.transformTemplate(m,"/WEB-INF/framepage/preparecourse_frame_page.ftl");
		
	}
	
	/**
	 * 主题/普通页面使用的框架页面
	 * @param objectGuid
	 * @return
	 */
	public String getSpecialSubjectFramePage(String objectGuid)
	{	
		if(this.specialSubjectService == null)
		{
			log.error("没有设置 specialSubjectService ，请检查 applicationContext.xml 中 id='pageFrameService' 节的设置。");
			return "没有设置 specialSubjectService ，请检查 applicationContext.xml 中 id='pageFrameService' 节的设置。";
		}
		
		SpecialSubject specialSubject = this.specialSubjectService.getSpecialSubjectByGuid(objectGuid);		
		HashMap<String,Object> m = new HashMap<String,Object>();		
		// 对于专题，对象还不存在，只提供公共信息	
	
		m.put("loginUser", this.getLoginUser());	
		
		// 模拟一个对象，设置必要的属性
		m.put("objectGuid", objectGuid);			
		ArrayList<SubjectNav> metaSubject;
		List<Grade> MetaGrade = new ArrayList<Grade>();	
		List<Integer> gradeIdList = subjectService.getGradeIdList();		
		metaSubject = new ArrayList<SubjectNav>();
		for (int i = 0; i < gradeIdList.size(); i++) {			
			Grade grade = (Grade)subjectService.getGrade(gradeIdList.get(i));
			MetaGrade.add(grade);
			List<Subject> subj = subjectService.getSubjectByGradeId(grade.getGradeId());
			List<Object> ms = new ArrayList<Object>();
			if (subj != null) {
				for (int j = 0; j < subj.size(); j++) {
					ms.add(((Subject) subj.get(j)).getMetaSubject());
				}
				SubjectNav sn = new SubjectNav(grade.getGradeName(), grade.getGradeId(), ms);
				metaSubject.add(sn);
			}
		}
		
		m.put("metaGrade", MetaGrade);
		m.put("meta_Grade", MetaGrade);
		m.put("SubjectNav", metaSubject);
		m.put("SiteThemeUrl", this.getSiteThemeUrl());
		m.put("SiteConfig", this.getSiteConfig());
		m.put("SiteNavList", this.siteNavService.getAllSiteNav(false,0,0));
		m.put("ConfigFromTemplate", "1");
		m.put("head_nav", "special_subject");
		if(specialSubject != null) m.put("specialSubject", specialSubject);
		return this.transformTemplate(m, "/WEB-INF/ftl2/special/special_topic_frame.ftl");
	}
	
	/**
	 * 机构框架页面
	 * @param objectGuid
	 * @return
	 */
	public String getUnitFramePage(String objectGuid)
	{
		if(this.unitService == null)
		{
			log.error("没有设置 unitService ，请检查 applicationContext.xml 中 id='pageFrameService' 节的设置。");
			return "没有设置 unitService ，请检查 applicationContext.xml 中 id='pageFrameService' 节的设置。";
		}
		Unit unit = this.unitService.getUnitByGuid(objectGuid);
		if(unit == null)
		{
			return "没有加载到指定的机构";
		}
		String unitRootUrl = GetWebXmlInitParam("unitUrlPattern");
		if(unitRootUrl == null || unitRootUrl.equals(""))
		{
			unitRootUrl = getSiteUrl() + "d/" + unit.getUnitName() + "/";
		}
		else
		{
			unitRootUrl = unitRootUrl.replaceAll("\\{unitName\\}", unit.getUnitName());
		}
		
		HashMap<String,Object> m = new HashMap<String,Object>();	
		m.put("loginUser", this.getLoginUser());
		m.put("unit", unit);
		m.put("UnitRootUrl", unitRootUrl);
		m.put("SiteConfig", this.getSiteConfig());
		m.put("SiteNavList", this.siteNavService.getAllSiteNav(false,0,0));
		m.put("UnitSiteNavList", this.siteNavService.getAllSiteNav(false,1,unit.getUnitId()));		
		m.put("ConfigFromTemplate", "1");
		//m.put("SiteUrl", this.getSiteUrl());
		return this.transformTemplate(m,"/WEB-INF/framepage/unit_frame_page.ftl");
	}
	
	public String getSubjectFramePage(String objectGuid)
	{		
		if(this.subjectService == null)
		{
			log.error("没有设置 subjectService ，请检查 applicationContext.xml 中 id='pageFrameService' 节的设置。");
			return "没有设置 subjectService ，请检查 applicationContext.xml 中 id='pageFrameService' 节的设置。";
		}
		Subject subject = this.subjectService.getSubjectByGuid(objectGuid);
		if(subject == null)
		{
			return "没有加载到指定的学科";
		}
		String subjectRootUrl = GetWebXmlInitParam("subjectUrlPattern");
		if(subjectRootUrl == null || subjectRootUrl.equals(""))
		{
			subjectRootUrl = getSiteUrl() + "k/" + subject.getSubjectCode() + "/";
		}
		else
		{
			subjectRootUrl = subjectRootUrl.replaceAll("\\{subjectCode\\}", subject.getSubjectCode());
		}
		HashMap<String,Object> m = new HashMap<String,Object>();	
		m.put("loginUser", this.getLoginUser());
		m.put("subject", subject);
		m.put("SubjectRootUrl", subjectRootUrl);
		m.put("SiteConfig", this.getSiteConfig());
		m.put("SiteNavList", this.siteNavService.getAllSiteNav(false,0,0));
		m.put("SubjectSiteNavList", this.siteNavService.getAllSiteNav(false,2,subject.getSubjectId()));
		m.put("ConfigFromTemplate", "1");
		ParamUtil param_util = new ParamUtil(JitarRequestContext.getRequestContext().getRequest());
		Integer unitId = param_util.getIntParamZeroAsNull("unitId");
		m.put("unitId", unitId);
		return this.transformTemplate(m,"/WEB-INF/framepage/subject_frame_page.ftl");
	}
	
	public Configure getSiteConfig()
	{
		Configure cfg = this.configService.getConfigure();
		return cfg;
	}
	
	/**
	 * 得到当前登录用户, 登录用户在 统一用户认证 Filter 里面被设置
	 * @return 如果没有则返回 null
	 */
	public User getLoginUser() {
		JitarRequestContext req_ctxt = JitarRequestContext.getRequestContext();
		HttpServletRequest req = (HttpServletRequest)req_ctxt.getRequest();
		if(req == null) return null;
		HttpSession session = req.getSession();
		if (session == null)
			return null;
		return WebUtil.getLoginUser(session);
	}
	
	private String GetWebXmlInitParam(String param)
	{
		JitarRequestContext req_ctxt = JitarRequestContext.getRequestContext();
		HttpServletRequest req = (HttpServletRequest)req_ctxt.getRequest();
		if(req == null) return null;
		HttpSession session = req.getSession();
		if (session == null)
			return null;
		String paramValue = session.getServletContext().getInitParameter(param);
		return paramValue;
	}	
		
	/**
	 * 得到网站的主题地址
	 * @return
	 */
	private String getSiteThemeUrl()
	{
		String SiteThemeUrl;
		SiteTheme siteTheme = this.siteThemeService.getCurrentSiteTheme();
		if(siteTheme == null)
		{
//			SiteThemeUrl = this.getSiteUrl() + "css/index/";
			SiteThemeUrl = this.getSiteUrl() + "skin/default/";
		}
		else
		{
			SiteThemeUrl = this.getSiteUrl() + "theme/site/" + siteTheme.getFolder() + "/";
		}
		return SiteThemeUrl;
	}
	/**
	 * 关闭方法，Spring 关闭的时候调用
	 */
	public void destroy() {
		this.cfg = null;
	}	

	/**
	 * 辅助函数，给py页面提供网站根目录路径。
	 * @return
	 */
	public String getSiteUrl()
	{
		return SiteUrlModel.getSiteUrl();
	}
	/**
	 * 辅助函数，给py页面提供统一用户的地址信息。
	 * @return
	 */
	public String getUserMgrUrl()
	{
		// return UserMgrModel.getUserMgrUrl();
		String mgrUrl=UserMgrModel.getUserMgrUrl();
		if(mgrUrl==null || mgrUrl.length()==0){
			mgrUrl= CasConst.getInstance().getCasServerUrlPrefix();
		}
		return mgrUrl;
	}
	public String getUserMgrClientUrl()
	{
		//return UserMgrClientModel.getUserMgrClientUrl();
		String ClientUrl=UserMgrClientModel.getUserMgrClientUrl();
		if(ClientUrl==null || ClientUrl.length()==0){
			ClientUrl=CasConst.getInstance().getCasServerLoginUrl();
		}
		return ClientUrl;
	
	}
		
	public UserService getUser_svc() {
		return user_svc;
	}

	public void setUserService(UserService user_svc) {
		this.user_svc = user_svc;
	}

	public PageService getPage_svc() {
		return page_svc;
	}

	public void setPageService(PageService page_svc) {
		this.page_svc = page_svc;
	}
	public Configuration getConfiguration() {
		return this.cfg;
	}

	public ServletContext getServletContext() {
		return servletContext;
	}

	public GroupService getGroup_svc() {
		return group_svc;
	}

	public PrepareCourseService getPreparecourse_svc() {
		return preparecourse_svc;
	}

	public void setGroupService(GroupService group_svc) {
		this.group_svc = group_svc;
	}

	public void setPrepareCourseService(PrepareCourseService preparecourse_svc) {
		this.preparecourse_svc = preparecourse_svc;
	}

	public void setSubjectService(SubjectService subjectService) {
		this.subjectService = subjectService;
	}

	public void setSiteThemeService(SiteThemeService siteThemeService) {
		this.siteThemeService = siteThemeService;
	}

	public void setSpecialSubjectService(SpecialSubjectService specialSubjectService) {
		this.specialSubjectService = specialSubjectService;
	}



	public void setConfigService(ConfigService configService) {
		this.configService = configService;
	}
	
	public void setSiteNavService(SiteNavService siteNavService) {
		this.siteNavService = siteNavService;
	}

	public void setUnitService(UnitService unitService) {
		this.unitService = unitService;
	}
}
