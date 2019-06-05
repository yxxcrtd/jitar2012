package cn.edustar.jitar.ui;

import cn.edustar.jitar.action.AbstractPageAction;
import cn.edustar.jitar.pojos.Category;
import cn.edustar.jitar.service.CategoryService;
import cn.edustar.jitar.util.ParamUtil;

import com.opensymphony.xwork2.ActionContext;

/**
 * 系统页面, 显示指定的系统分类. 
 *
 *
 */
public class ShowCategoryAction extends AbstractPageAction {
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -789113406853177746L;

	/** 分类服务 */
	private CategoryService cate_svc;
	
	/** 分类服务 */
	public void setCategoryService(CategoryService cate_svc) {
		this.cate_svc = cate_svc;
	}
	
	/** 当前要显示的分类 */
	private Category category;
	
	/*
	 * (non-Javadoc)
	 * @see com.opensymphony.xwork2.ActionSupport#execute()
	 */
	public String execute(String cmd) throws Exception {
		ParamUtil param_util = new ParamUtil(ActionContext.getContext().getParameters());
		//String cmd = param_util.safeGetStringParam("cmd");
		prepareCategory(param_util);
		
		prepareData();
		
		if ("debug".equals(cmd))
			return "debug";
		
		return SUCCESS;
	}
	
	/**
	 * 准备要显示的分类对象.
	 * @param param_util
	 */
	private void prepareCategory(ParamUtil param_util) {
		int categoryId = param_util.getIntParam("categoryId");
		this.category = cate_svc.getCategory(categoryId);
	}
	
	/**
	 * 得到当前分类对象.
	 * @return
	 */
	public Category getCategory() {
		return this.category;
	}
	
	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.action.AbstractPageAction#getContextObject(java.lang.String)
	 */
	public Object getContextObject(String name) {
		if ("category".equals(name))
			return this.category;
		return super.getContextObject(name);
	}
}
