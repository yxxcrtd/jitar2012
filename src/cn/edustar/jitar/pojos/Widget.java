package cn.edustar.jitar.pojos;

import java.io.Serializable;
import java.util.Date;

import com.alibaba.fastjson.JSONObject;


/**
 * Widget 实体对象, 对应客户端的 widget. * 
 * 我们使用 pageId, WidgetType, name 做为业务键. * 
 * @author mengxianhui
 */
public class Widget implements Serializable, Cloneable {
	/** serialVersionUID */
	private static final long serialVersionUID = -4450655243863922338L;

	/** 内容块标识，数据库产生、主键. */
	private int id;
	
	/** 内容块的名称，一般是英文的(可能由程序自动生成) */
	private String name;
	
	/** 此内容块的标题. */
	private String title;
	
	/** 内容块的创建时间. */
	private Date createDate = new Date();
	
	/** 此内容块所属的页面标识，必须合法. */
	private int pageId;
	
	/** 内容，以 JSON 格式保存的内容块的自定义数据，对应页面中 widget.data 数据 . */
	private String data;
	
	/** 是否隐藏，隐藏的内容块不显示在界面上. */
	private boolean isHidden;
	
	/** 此内容块在其所在栏目的顺序，数字小的排在前面. */
	private int itemOrder;
	
	/** 此内容块所在的栏目编号，从 1 开始编. 如 column1, column2 等. */
	private int columnIndex;
	
	/** ?? 暂时不使用. */
	private int rowIndex;
	
	/** ?? 自定义模板  */
	private String customTemplate;
	
	/** 此内容块的模块类型，根据类型能够得到其对应的模块 */
	private String module;
	
	private String icon;

	/** default constructor */
	public Widget() {
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return "Widget{id=" + id + ",page=" + pageId + 
			",module=" + module + ",title=" + title + "}";
	}
	
	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return PojoHelper.hashCode(pageId, name, module);
	}
	
	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object o) {
		if ((o == null) || !(o instanceof Widget)) 
			return false;
		Widget other = (Widget)o;
		
		return PojoHelper.equals(pageId, other.pageId) &&
			PojoHelper.equals(name, other.name) &&
			PojoHelper.equals(module, other.module);
	}
	
	/**
	 * 兼容 WidgetModel.
	 * @return
	 */
	public Widget _getWidgetObject() {
		return this;
	}
	
	/** 内容块标识，数据库产生、主键. */
	public int getId() {
		return this.id;
	}

	/** 内容块标识，数据库产生、主键. */
	public void setId(int id) {
		this.id = id;
	}

	/** 内容块的名称，一般是英文的(可能由程序自动生成) */
	public String getName() {
		return this.name;
	}

	/** 内容块的名称，一般是英文的(可能由程序自动生成) */
	public void setName(String name) {
		this.name = name;
	}

	/** 此内容块的标题. */
	public String getTitle() {
		return this.title;
	}

	/** 此内容块的标题. */
	public void setTitle(String title) {
		this.title = title;
	}

	/** 内容块的创建时间. */
	public Date getCreateDate() {
		return this.createDate;
	}

	/** 内容块的创建时间. */
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	/** 此内容块所属的页面标识，必须存在. */
	public int getPageId() {
		return this.pageId;
	}

	/** 此内容块所属的页面标识，必须存在. */
	public void setPageId(int pageId) {
		this.pageId = pageId;
	}

	/** 内容，以 JSON 格式保存的内容块的自定义数据，对应页面中 widget.data 数据 . */
	public String getData() {
		String data = this.data;
		if (data == null || data.length() == 0) return "";
		
		try {
			// 使用 JSON 进行解析.
			Object json = null;
			if (data.startsWith("{") == false && data.endsWith("}") == false) {
				json = JSONObject.parse("{" + data + "}");
			} else {
				json = JSONObject.parse(data);
			}
			if (json == null) return "";
			data = json.toString();
			if (data.startsWith("{") && data.endsWith("}"))
				data = data.substring(1, data.length() - 1);
			return data;
		} catch (Error e) {
			return "";
		}
	}

	/** 内容，以 JSON 格式保存的内容块的自定义数据，对应页面中 widget.data 数据 . */
	public void setData(String data) {
		this.data = data;
	}

	/** 是否隐藏，隐藏的内容块不显示在界面上. */
	public boolean getIsHidden() {
		return this.isHidden;
	}

	/** 是否隐藏，隐藏的内容块不显示在界面上. */
	public void setIsHidden(boolean isHidden) {
		this.isHidden = isHidden;
	}

	/** 此内容块在其所在栏目的顺序，数字小的排在前面. */
	public int getItemOrder() {
		return this.itemOrder;
	}

	/** 此内容块在其所在栏目的顺序，数字小的排在前面. */
	public void setItemOrder(int itemOrder) {
		this.itemOrder = itemOrder;
	}

	/** 此内容块所在的栏目编号，从 1 开始编. 如 column1, column2 等. */
	public int getColumnIndex() {
		return this.columnIndex;
	}

	/** 此内容块所在的栏目编号，从 1 开始编. 如 column1, column2 等. */
	public void setColumnIndex(int columnIndex) {
		this.columnIndex = columnIndex;
	}

	/** ?? 暂时不使用. */
	public int getRowIndex() {
		return this.rowIndex;
	}

	/** ?? 暂时不使用. */
	public void setRowIndex(int rowIndex) {
		this.rowIndex = rowIndex;
	}

	/** ?? 自定义模板  */
	public String getCustomTemplate() {
		return this.customTemplate;
	}

	/** ?? 自定义模板  */
	public void setCustomTemplate(String customTemplate) {
		this.customTemplate = customTemplate;
	}

	/** 此内容块的模块类型，根据类型能够得到其对应的模块 */
	public String getModule() {
		return this.module;
	}

	/** 此内容块的模块类型，根据类型能够得到其对应的模块 */
	public void setModule(String module) {
		this.module = module;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}
}
