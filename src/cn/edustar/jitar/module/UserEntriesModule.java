package cn.edustar.jitar.module;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import cn.edustar.jitar.model.ArticleModelEx;
import cn.edustar.jitar.pojos.User;
import cn.edustar.jitar.service.ArticleQueryParam;
import cn.edustar.jitar.service.ArticleService;
import cn.edustar.jitar.service.FileStorage;
import cn.edustar.jitar.service.StoreManager;
import cn.edustar.jitar.util.CommonUtil;
import cn.edustar.jitar.util.ParamUtil;

/**
 * 用户最新博文列表. * 
 * 使用的模板： "/WEB-INF/user/default/user_entries.html"
 * 
 *
 * TODO: 待解决问题 博文链接 URL 规则；所有博文列表页面；按照分类列表页面；翻页问题.
 */
public class UserEntriesModule extends AbstractModuleWithTP {
	/** 博文服务 */
	private ArticleService art_svc;
	
	/** 用户临时存储服务 */
	private StoreManager sto_mgr;

	/** 文件缓存更新时间, 单位: 秒 */
	private long cacheTime = 0;		// 15*60. 

	/** 缺省显示文章数. */
	private static final int DEFAULT_COUNT = 10;
	
	/**
	 * 构造.
	 */
	public UserEntriesModule() {
		super("entries", "最新文章");
	}
	
	/** 博文服务 */
	public void setArticleService(ArticleService art_svc) {
		this.art_svc = art_svc;
	}
	
	/** 用户临时存储服务 */
	public void setStoreManager(StoreManager sto_mgr) {
		this.sto_mgr = sto_mgr;
	}

	/** 文件缓存更新时间, 单位: 秒 */
	public void setCacheTime(long val) {
		this.cacheTime = val;
	}

	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.module.Module#handleRequest(cn.edustar.jitar.module.ModuleRequest, cn.edustar.jitar.module.ModuleResponse)
	 */
	public void handleRequest(ModuleRequest request, ModuleResponse response)
			throws IOException {
		// 得到用户数据.
		User user = (User)request.getAttribute(ModuleRequest.USER_MODEL_KEY);

		ParamUtil param_util = new ParamUtil(request.getParameters());
		int count = param_util.getIntParam("count");
		if (count <= 0) count = DEFAULT_COUNT;

		// // 只对 count = 10 进行缓存, 看看有否设置缓存.
		if (count == DEFAULT_COUNT && this.cacheTime > 0) {
			FileStorage store = sto_mgr.getUserFileStorage(user._getUserObject());
			File f = store.getFile("/system/user_entries.fragment.xhtml");
			if (this.fileIsValid(f)) {
				String content = CommonUtil.readFile(f.getCanonicalPath(), UTF_8);
				response.setContentType(Module.TEXT_HTML_UTF_8);
				response.getOut().write(content);
				return;
			}
		}
		
		// 得到用户发表的最新 count 篇博文.
		ArticleQueryParam para = new ArticleQueryParam();
		para.count = count;
		para.userId = user.getUserId();
		List<ArticleModelEx> article_list = art_svc.getArticleList(para, null);
		
		// 模板合成.
		HashMap<String, Object> root_map = new HashMap<String, Object>();
		root_map.put("user", user);
		root_map.put("article_list", article_list);
		
		String template_name = "/WEB-INF/user/default/user_entries.ftl";
		
		String result = processTemplate2(root_map, template_name);
		response.setContentType(Module.TEXT_HTML_UTF_8);
		response.getOut().write(result);
		
		// 写入到缓存.
		if (count == DEFAULT_COUNT && this.cacheTime > 0) {			FileStorage store = sto_mgr.getUserFileStorage(user._getUserObject());
			File f = store.getFile("/system/user_entries.fragment.xhtml");
			if (f.getParentFile().exists() == false)
				f.getParentFile().mkdirs();
			CommonUtil.saveFile(f.getCanonicalPath(), result, UTF_8);
		}
	}
	
	// 判断以前缓存的存储文件是否可用.	private boolean fileIsValid(File f) {
		if (f.exists() == false) return false;
		if (cacheTime <= 0) return false;
		
		long lastModified = f.lastModified();
		long now = (new java.util.Date()).getTime();
		
		return ((now - lastModified) < (cacheTime*1000L));
	}
}
