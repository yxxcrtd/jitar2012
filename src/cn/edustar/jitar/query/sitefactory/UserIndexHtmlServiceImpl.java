package cn.edustar.jitar.query.sitefactory;

import java.util.HashMap;
import java.util.List;



import com.alibaba.fastjson.JSONObject;

import cn.edustar.jitar.JitarContext;
import cn.edustar.jitar.dao.hibernate.BaseDaoHibernate;
import cn.edustar.jitar.model.ArticleModelEx;
import cn.edustar.jitar.model.ResourceModelEx;
import cn.edustar.jitar.model.SiteUrlModel;
import cn.edustar.jitar.pojos.Page;
import cn.edustar.jitar.pojos.User;
import cn.edustar.jitar.pojos.Widget;
import cn.edustar.jitar.service.ArticleQueryParam;
import cn.edustar.jitar.service.ArticleService;
import cn.edustar.jitar.service.PageService;
import cn.edustar.jitar.service.ResourceQueryParam;
import cn.edustar.jitar.service.ResourceService;
import cn.edustar.jitar.service.TemplateProcessor;
import cn.edustar.jitar.util.CommonUtil;
import cn.edustar.jitar.util.FileCache;


public class UserIndexHtmlServiceImpl  extends BaseDaoHibernate implements UserIndexHtmlService {

	private PageService pageService;
	private JitarContext jitarContext;
	private ArticleService articleService;
	private ResourceService resourceService;	
	
	private TemplateProcessor templateProcessor;
	
	public Widget getWidget(User user, String moduleName) {
		//先得到用户的首页对象
		Page page = pageService.getUserIndexPage(user);
		if(page==null) return null;
		Widget widget = pageService.getWidgetByModuleNameAndPageId(moduleName,page.getId());
		return widget;
	}
	
	@SuppressWarnings("unchecked")
	public void genEntriesList(User user)
	{
		String moduleName = "user_entries";
		Widget w = this.getWidget(user, "entries");
		if(w == null) return;
		int showCount = 10;
		String data = w.getData();
		data = "{" + data + "}";
		if (data != null && data.equals("") == false) {
			JSONObject wdData = (JSONObject) JSONObject.parse(data);
			if (wdData.containsKey("count")) {
				String c = wdData.getString("count");
				if(CommonUtil.isNumber(c))
				{
					try {
						showCount = Integer.parseInt(c);
					} 
					catch (NumberFormatException ex) {}		
				}
			}
			wdData = null;
		}
		
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("user", user);
		map.put("UserSiteUrl", this.getUserSiteUrl(user));
	
		ArticleQueryParam para = new ArticleQueryParam();
		para.count = showCount;
		para.userId = user.getUserId();
		List<ArticleModelEx> article_list = this.articleService.getArticleList(para, null);
		map.put("article_list", article_list);
		String content = templateProcessor.processTemplate(map, "/WEB-INF/user/default/" + moduleName + ".ftl", "utf-8");
		FileCache fc = new FileCache();
		fc.writeUserFileCacheContent(user.getLoginName(), moduleName+".html", content);
		fc = null;
	}
	
	@SuppressWarnings("unchecked")
	public void genResourceList(User user)
	{
		String moduleName = "user_resources";
		Widget w = this.getWidget(user, "user_resources");
		if(w == null) return;
		int showCount = 10;
		String data = w.getData();
		data = "{" + data + "}";
		if (data != null && data.equals("") == false) {
			JSONObject wdData = (JSONObject) JSONObject.parse(data);
			if (wdData.containsKey("count")) {
				String c = wdData.getString("count");
				if(CommonUtil.isNumber(c))
				{
					try {
						showCount = Integer.parseInt(c);
					} 
					catch (NumberFormatException ex) {}		
				}
			}
			wdData = null;
		}
		
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("user", user);
		map.put("UserSiteUrl", this.getUserSiteUrl(user));
	
		ResourceQueryParam para = new ResourceQueryParam();
		para.count = showCount;
		para.userId = user.getUserId();
		List<ResourceModelEx> resource_list = this.resourceService.getResourceList(para, null);
		map.put("resource_list", resource_list);
		String content = templateProcessor.processTemplate(map, "/WEB-INF/user/default/" + moduleName + ".ftl", "utf-8");
		FileCache fc = new FileCache();
		fc.writeUserFileCacheContent(user.getLoginName(), moduleName+".html", content);
		fc = null;
	}
	
	@SuppressWarnings("rawtypes")
	public void genPhotoList(User user)
	{
		String moduleName = "user_photo";
		Widget w = this.getWidget(user, "user_photo");
		if(w == null) return;
		int showCount = 4;
		String data = w.getData();
		data = "{" + data + "}";
		if (data != null && data.equals("") == false) {
			JSONObject wdData = (JSONObject) JSONObject.parse(data);
			if (wdData.containsKey("count")) {
				String c = wdData.getString("count");
				if(CommonUtil.isNumber(c))
				{
					try {
						showCount = Integer.parseInt(c);
					} 
					catch (NumberFormatException ex) {}		
				}
			}
			wdData = null;
		}
		
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("user", user);
		map.put("UserSiteUrl", this.getUserSiteUrl(user));
		CommonQuery2 qa = new CommonQuery2(this.getSession());
		qa.setMaxRow(showCount);
		qa.setStartRow(0);
		qa.setHql("SELECT new Map(p.photoId as photoId, p.title as title, p.href as href,u.loginName as loginName) FROM Photo as p,User u");
		qa.setOrderByClause("p.photoId DESC");
		qa.setWhereClause("p.userId = u.userId And p.userId=" + user.getUserId() + " And p.auditState = 0 ");
		List photo_list = qa.getQueryList();
		map.put("photo_list", photo_list);
		String content = templateProcessor.processTemplate(map, "/WEB-INF/user/default/" + moduleName + ".ftl", "utf-8");
		FileCache fc = new FileCache();
		fc.writeUserFileCacheContent(user.getLoginName(), moduleName+".html", content);
		fc = null;
		qa = null;
	}
	
	public void setPageService(PageService pageService) {
		this.pageService = pageService;
	}


	public void setJitarContext(JitarContext jitarContext) {
		this.jitarContext = jitarContext;
	}
	
	public void setArticleService(ArticleService articleService) {
		this.articleService = articleService;
	}

	public void setResourceService(ResourceService resourceService) {
		this.resourceService = resourceService;
	}
	

	public void setTemplateProcessor(TemplateProcessor templateProcessor) {
		this.templateProcessor = templateProcessor;
	}

	public String getSiteUrl()
	{
		return SiteUrlModel.getSiteUrl();
	}
	
	public String getUserSiteUrl(User user)
	{
		String userSiteUrl = this.jitarContext.getServletContext().getInitParameter("userUrlPattern");
		if (userSiteUrl == null || userSiteUrl.length() == 0)
			userSiteUrl = this.getSiteUrl() + "u/" + user.getLoginName() + "/";
		else
			userSiteUrl = userSiteUrl.replace("{loginName}", user.getLoginName());
		return userSiteUrl;
	}
}
