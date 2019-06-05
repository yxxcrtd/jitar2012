package cn.edustar.data;

/**
 * 分页对象
 * 
 * @author Yang XinXin
 * @version 1.0.0, 2008-02-06 11:16:58
 */
public class Pager {
	
	/** 总行数 */
	private int totalRows;

	/** 每页显示的行数 */
	private int pageSize = 2;

	/** 当前页号 */
	private int currentPage = 1;

	/** 当前的项目名称 */
	private String itemName = "项目";

	/** 当前的项目机构 */
	private String itemUnit = "个";

	/** 页面的Url Pattern, 内部的 '{page}' 将被替换为当时的页次 */
	private String url_pattern = "?page={page}&total={total}";

	/** 首页的链接地址。 */
	private String firstPageUrl;

	/** 上一页的链接地址。 */
	private String prevPageUrl;

	/** 下一页的链接地址。 */
	private String nextPageUrl;

	/** 尾页的链接地址。 */
	private String lastPageUrl;

	public Pager() {
		this.currentPage = 1;
		this.pageSize = 20;
	}

	/**
	 * 使用指定的页号、页大小构造一个 Pager 的新实例.
	 * @param currentPage
	 * @param pageSize
	 */
	public Pager(int currentPage, int pageSize) {
		this.currentPage = currentPage;
		this.pageSize = pageSize;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Pager{page=" + this.currentPage + ",size=" + this.pageSize
				+ ",total=" + this.totalRows + ",pages=" + this.getTotalPages() + 
				",name=" + this.itemName + ",unit=" + this.itemUnit + "}";
	}

	/** 当前页在数据库中的起始行 */
	public int getStartRow() {
		int p = this.getCurrentPage();
		if (p <= 1) p = 1;
		return (p-1)*getPageSize();
	}

	/** 总页数, 根据总记录数和分页大小动态计算出来的。 */
	public int getTotalPages() {
		if (this.totalRows <= 0)
			return 0;
		if (this.pageSize <= 0)
			return 0;
		int i = this.totalRows / this.pageSize;
		if ((this.totalRows % this.pageSize) != 0)
			++i;
		return i;
	}

	/** 当前页号 */
	public int getCurrPage() {
		if (this.currentPage > this.getTotalPages())
		{
			return this.getTotalPages();
		}
		return this.currentPage;
	}

	/** 当前页号 */
	public int getCurrentPage() {
		return currentPage;
	}

	/** 每页显示的行数 */
	public int getPageSize() {
		return pageSize;
	}

	/** 总行数 */
	public void setTotalRows(int totalRows) {
		this.totalRows = totalRows;
	}

	/** 当前页号 */
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	/** 每页显示的行数 */
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	/** 得到总行数 */
	public int getTotalRows() {
		return totalRows;
	}

	/**
	 * 获取当前的项目机构.
	 * 
	 * @return
	 */
	public String getItemUnit() {
		return itemUnit;
	}

	/**
	 * 设置当前的项目名称.
	 * 
	 * @param itemName
	 */
	public void setItemUnit(String itemUnit) {
		this.itemUnit = itemUnit;
	}

	/**
	 * 获取当前的项目名称.
	 * @return
	 */
	public String getItemName() {
		return itemName;
	}

	/**
	 * 设置当前的项目名称.
	 * @param itemName
	 */
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	/**
	 * 设置项目名称和机构.
	 * 
	 * @param itemName
	 * @param itemUnit
	 */
	public void setItemNameAndUnit(String itemName, String itemUnit) {
		this.itemName = itemName;
		this.itemUnit = itemUnit;
	}

	/**
	 * 获取页面的UrlPattern, 例如 'list_{page}.html'
	 * 
	 * @return
	 */
	public String getUrlPattern() {
		return url_pattern;
	}

	/**
	 * 设置页面的Url。比如：'list_{page}.html'
	 * 
	 * @param url
	 */
	public void setUrlPattern(String url) {
		this.url_pattern = url;
	}

	/**
	 * 获取第一页的链接地址.
	 * 
	 * @return
	 */
	public String getFirstPageUrl() {
		if (this.firstPageUrl == null || this.firstPageUrl.length() == 0)
		{
			return this.internalGetPageUrl(1);
		}
		return firstPageUrl;
	}

	/**
	 * 设置第一页的链接地址.
	 * 
	 * @param firstPageUrl
	 */
	public void setFirstPageUrl(String firstPageUrl) {
		this.firstPageUrl = firstPageUrl;
	}

	/**
	 * 获取最后一页的链接地址.
	 * 
	 * @return
	 */
	public String getLastPageUrl() {
		if (this.lastPageUrl == null || this.lastPageUrl.length() == 0)
			return this.internalGetPageUrl(getTotalPages());
		return lastPageUrl;
	}

	/**
	 * 设置最后一页的链接地址.
	 * 
	 * @param firstPageUrl
	 */
	public void setLastPageUrl(String lastPageUrl) {
		this.lastPageUrl = lastPageUrl;
	}

	/**
	 * 获取下一页的链接地址.
	 * 
	 * @return
	 */
	public String getNextPageUrl() {
		if (this.nextPageUrl == null || this.nextPageUrl.length() == 0)
			return this.internalGetPageUrl(this.currentPage + 1);
		return nextPageUrl;
	}

	/**
	 * 获取下一页的链接地址.
	 * 
	 * @param firstPageUrl
	 */
	public void setNextPageUrl(String nextPageUrl) {
		this.nextPageUrl = nextPageUrl;
	}

	/**
	 * 获取最后一页的链接地址.
	 * 
	 * @return
	 */
	public String getPrevPageUrl() {
		if (this.prevPageUrl == null || this.prevPageUrl.length() == 0)
			return this.internalGetPageUrl(this.currentPage - 1);
		return prevPageUrl;
	}

	/**
	 * 获取最后一页的链接地址
	 * 
	 * @param firstPageUrl
	 */
	public void setPrevPageUrl(String prevPageUrl) {
		this.prevPageUrl = prevPageUrl;
	}

	/**
	 * 获得指定页号的地址
	 * 
	 * @param page
	 * @return
	 */
	private String internalGetPageUrl(int page) {
		if (page <= 1)
			page = 1;

		if (page >= getTotalPages())
			page = getTotalPages();

		if (this.url_pattern == null)
			return "";
		if(page==1)
		{
			return this.url_pattern.replace("{page}", String.valueOf(page)).replace("{total}", "0");
		}
		else
		{
			return this.url_pattern.replace("{page}", String.valueOf(page)).replace("{total}", String.valueOf(totalRows));
		}
	}
	
}
