package cn.edustar.jitar.servlet.handler;

import cn.edustar.jitar.pojos.Page;
import cn.edustar.jitar.pojos.User;
import cn.edustar.jitar.service.PageKey;
import cn.edustar.jitar.service.PageService;
import cn.edustar.jitar.service.TemplateProcessor;
import cn.edustar.jitar.service.UserService;

/**
 * 提供给 ShowCategoryBean, ShowEntryBean 做为通用基类.
 *
 *
 */
public abstract class BaseShowUser extends ServletBeanBase {
	/** 用户服务 */
	private UserService user_svc;
	
	/** 页面服务 */
	private PageService page_svc;
	
	/** 模板合成器. */
	private TemplateProcessor t_proc;

	/** 用户服务 */
	public UserService getUserService() {
		return this.user_svc;
	}
	
	/** 用户服务 */
	public void setUserService(UserService user_svc) {
		this.user_svc = user_svc;
	}
	
	/** 页面服务 */
	public PageService getPageService() {
		return this.page_svc;
	}
	
	/** 页面服务 */
	public void setPageService(PageService page_svc) {
		this.page_svc = page_svc;
	}
	
	/** 模板合成服务 */
	public TemplateProcessor getTemplateProcessor() {
		return this.t_proc;
	}
	
	/** 模板合成服务 */
	public void setTemplateProcessor(TemplateProcessor t_proc) {
		this.t_proc = t_proc;
	}

	/**
	 * 得到指定的系统页, 并附加上指定用户的首页的皮肤属性.
	 * @param sysPk
	 * @param user
	 * @return
	 */
	protected Page getSystemPageWithUserSkin(PageKey sysPk, User user) {
		// 得到此系统页.
		Page sys_page = page_svc.getPageByKey(sysPk);
		if (sys_page == null) return null;
		
		// 得到用户的个人首页.
		Page index_page = page_svc.getUserIndexPage(user);
		// if (index_page == null) return null;
		
		// 复制一份原系统页面.
		Page new_page = sys_page._getPageObject().clone();
		if (index_page != null)
			new_page.setSkin(index_page.getSkin());
		
		return new_page;
	}
}
