package cn.edustar.jitar.data;

import cn.edustar.data.Pager;

/**
 * 带有支持分页属性的 DataBean 基类.
 *
 *
 */
public abstract class AbstractPageDataBean extends AbstractDataBean {
	/** 当前分页选项 */
	private Pager pager = new Pager();
	
	/** 是否使用分页选项 */
	private boolean usePager = false;
	
	/** 分页对象在环境中的名称, 缺省 = 'pager' */
	private String pagerName = "pager";
	
	/** 是否使用分页选项 */
	public void setUsePager(boolean usePager) {
		this.usePager = usePager;
	}
	
	/** 是否使用分页选项 */
	public boolean getUsePager() {
		return this.usePager;
	}

	/** 分页对象在环境中的名称, 缺省 = 'pager' */
	public String getPagerName() {
		return this.pagerName;
	}
	
	/** 分页对象在环境中的名称, 缺省 = 'pager' */
	public void setPagerName(String pagerName) {
		this.pagerName = pagerName;
	}

	/** 当前分页选项 */
	public Pager getPager() {
		return this.pager;
	}
	
	/** 当前分页选项 */
	public void setPager(Pager pager) {
		this.pager = pager;
	}
	
	/** 每页显示的行数 */
	public void setPageSize(int pageSize) {
		pager.setPageSize(pageSize);
	}
	
	/**
	 * 设置当前的项目名称.
	 * 
	 * @param itemName
	 */
	public void setItemName(String itemName) {
		pager.setItemName(itemName);
	}
	
	/**
	 * 设置当前的项目名称.
	 * 
	 * @param itemName
	 */
	public void setItemUnit(String itemUnit) {
		pager.setItemUnit(itemUnit);
	}
	
	/**
	 * 得到指定页面环境下(DataHost)的分页对象.
	 * @param host
	 * @return
	 */
	public Pager getContextPager(DataHost host) {
		Pager pager = this.pager;
		
		// 设置当前页号.
		int page = host.getParameters().getIntParam("page");
		if (page <= 0) page = 1;
		pager.setCurrentPage(page);
		
		// 设置 UrlPattern.
		String urlPattern = host.getParameters().generateUrlPattern();
		pager.setUrlPattern(urlPattern);
		
		return pager;
	}
}
