package cn.edustar.jitar.data;

import cn.edustar.jitar.JitarContext;
import cn.edustar.jitar.model.CategoryTreeModel;
import cn.edustar.jitar.service.CategoryService;

/**
 * 提取分类信息的 bean.
 *
 *
 */
public class CategoryBean extends AbstractDataBean {
	/** 分类服务 */
	protected CategoryService cate_svc;

	/**
	 * 构造.
	 */
	public CategoryBean() {
		super.setVarName("category_tree");
		this.cate_svc = JitarContext.getCurrentJitarContext().getCategoryService();
	}
	
	/** 分类服务 */
	public void setCategoryService(CategoryService cate_svc) {
		this.cate_svc = cate_svc;
	}
	
	/** 要获取的分类类型，缺省 = 'default' */
	protected String itemType = CategoryService.ARTICLE_CATEGORY_TYPE;
	
	/** 要获取的分类类型，缺省 = 'default' */
	public void setItemType(String itemType) {
		this.itemType = itemType;
	}
	
	/** 要获取的分类类型，缺省 = 'default' 表示系统分类 */
	public String getItemType() {
		return this.itemType;
	}

	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.data.AbstractDataBean#doPrepareData(cn.edustar.jitar.data.DataHost)
	 */
	@Override
	protected void doPrepareData(DataHost host) {
		CategoryTreeModel cate_tree = cate_svc.getCategoryTree(this.itemType);
		host.setData(getVarName(), cate_tree);
	}
}
