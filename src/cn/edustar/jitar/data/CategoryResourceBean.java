package cn.edustar.jitar.data;

import cn.edustar.data.Pager;
import cn.edustar.jitar.pojos.Category;

/**
 * 显示指定分类下的资源列表, 缺省 varName = 'cate_resource_list'.
 *
 *
 */
public class CategoryResourceBean extends ResourceBean {
	/**
	 * 构造.
	 */
	public CategoryResourceBean() {
		super.setVarName("cate_resource_list");
	}
	
	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.data.ResourceBean#doPrepareData(cn.edustar.jitar.data.DataHost)
	 */
	@Override
	protected void doPrepareData(DataHost host) {
		// 获得当前要显示的分类对象, 强制查找的文章的系统分类为 category.
		Category category = (Category)host.getContextObject("category");
		super.setSysCateId(String.valueOf(category.getCategoryId()));
		
		// 查找资源.
		super.doPrepareData(host);
	}
	
	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.data.AbstractPageDataBean#getContextPager(cn.edustar.jitar.data.DataHost)
	 */
	@Override
	public Pager getContextPager(DataHost host) {
		Category category = (Category)host.getContextObject("category");
		Pager pager = super.getContextPager(host);
		pager.setUrlPattern("?categoryId=" + category.getCategoryId() + "&page={page}");
		return pager;
	}
}
