package cn.edustar.jitar.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import cn.edustar.jitar.pojos.Category;

/**
 * Category 包装类
 * 
 * @author Yang Xinxin
 * @version 1.0.0 Mar 10, 2008 1:18:37 PM
 */
public class CategoryModel extends TreeModelBase<CategoryModel> implements ModelObject, Serializable {
	/**
     * 
     */
    private static final long serialVersionUID = -3283651352678943380L;

    /** 封装的 category 对象 */
	private final Category category;
	
	/** 分类所属树对象. */
	private final CategoryTreeModel tree;

	/** 父分类. */
	private CategoryModel parent_category;
	
	/**
	 * 对一个分类进行包装，此方式包装的分类模型因为没有 category_tree 对象，所以
	 *   树方法都不被支持
	 * @param category
	 * @return
	 */
	public static final CategoryModel wrap(Category category) {
		if (category == null) return null;
		return new CategoryModel(category, null);
	}
	
	/**
	 * CategoryTreeModel 内部使用，添加一个子分类
	 * @param child
	 */
	void _appendChild(CategoryModel child) {
		super.childs.add(child);
		child._setParent(this);
	}

	private String _tree_flag = "";
	
	/**
	 * 计算这个节点的树标志.
	 * @param hasPrev - 是否有兄节点.
	 * @param hasNext - 是否有弟节点.
	 * ├ │ ┌ └ ┼ ─ ┊   .
	 */
	void _calcTreeFlag(String a, boolean hasParent, boolean hasPrev, boolean hasNext) {
		// 计算自己的标志
		String t_flag = null, child_flag = null;
		if (hasPrev && hasNext) {
			t_flag = "├";
		}
		else if (hasPrev) {
			t_flag = "└";
		}
		else if (hasNext) {
			t_flag = hasParent ? "├" : "┌";
		}
		else {
			t_flag = hasParent ? "└" : "─";
		}
		child_flag = hasNext ? "│" : " ";
	
		this._tree_flag = a + t_flag;
		
		// 计算子节点标志
		for (int i = 0; i < super.childs.size(); ++i) {
			boolean has_prev = i > 0;
			boolean has_next = i < (super.childs.size() - 1);
			childs.get(i)._calcTreeFlag(a + child_flag, true, has_prev, has_next);
		}
	}
	
	/**
	 * 设置父分类.
	 * @param parent
	 */
	private void _setParent(CategoryModel parent) {
		this.parent_category = parent;
	}
	
	/**
	 * 封装 Category 模型对象.
	 * 
	 * @param category
	 * @param jtar_ctxt
	 */
	CategoryModel(Category category, CategoryTreeModel tree) {
		this.category = category;
		this.tree = tree;
	}

	/** 分类所属树对象. */
	public CategoryTreeModel getTree() {
		return this.tree;
	}
	
	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return category.toString();
	}
	
	/** 分类的标识主键，数据库自动生成. */
	public int getId() {
		return category.getCategoryId();
	}
	
	/** 分类的标识主键，数据库自动生成. */
	public int getCategoryId() {
		return category.getCategoryId();
	}
	
	/** 分类的全局唯一标识. */
	public String getObjectUuid() {
		return category.getObjectUuid();
	}

	/** 分类的名字，可以使用中文和空格等特殊字符. */
	public String getName() {
		return this.category.getName();
	}

	public boolean getIsSystem() {
		return this.category.getIsSystem();
	}
	
	/** 分类中项目类型，也代表该分类应用给哪种对象. 参见 Category 类的说明. */
	public String getItemType() {
		return category.getItemType();
	}

	/** 父分类标识. */
	public Integer getParentId() {
		return category.getParentId();
	}

	/** 父分类的整路径. 如/1/3/8/，指父分类为1，3，8. */
	public String getParentPath() {
		return category.getParentPath();
	}

	/** 分类在该层的排序值. */
	public int getOrderNum() {
		return category.getOrderNum();
	}
	
	/** 分类的详细描述. */
	public String getDescription() {
		return category.getDescription();
	}

	/** 此分类下内容数量. 对于博文分类指该分类下博文数量. 其它项目对象可以不统计（不使用此字段） */
	public int getItemNum() {
		return category.getItemNum();
	}

	/**得到扩展对象 */
	public Object getExtendedObject(){
		return category.getExtendedObject();
	}
	// === 为树实现的属性 ===================================================== 
	
	/**
	 * 得到子分类数量.
	 * @return
	 */
	public int getChildNum() {
		return super.childs.size();
	}
	
	/**
	 * 得到是否有子节点.
	 * @return
	 */
	public boolean getHasChild() {
		return (getChildNum() > 0);
	}

	/**
	 * 得到子分类列表.
	 * @return 每次返回一个新的包装过的 List, 该列表只读.
	 */
	public List<CategoryModel> getChildren() {
		return CategoryTreeModel._readonly_list(super.childs);
	}
	
	/**
	 * 得到所有子孙节点列表，第一个就是自己.
	 * @return 每次都现构造一个集合，也许比较慢.
	 */
	public List<CategoryModel> getAllChildren() {
		List<CategoryModel> all_child = new ArrayList<CategoryModel>();
		this._collect_child(all_child);
		
		return CategoryTreeModel._readonly_list(super.childs);
	}
	
	/** 为 getAllChildren() 方法提供支持的辅助函数, CategoryTreeModel 也调用此方法 */
	void _collect_child(List<CategoryModel> list) {
		// 添加自己到列表中
		list.add(this);
		
		if (super.childs != null) {
			// 所有子节点再收集
			for (int i = 0; i < super.childs.size(); ++i) {
				super.childs.get(i)._collect_child(list);
			}
		}
	}
	
	/**
	 * 得到父分类.
	 * @return
	 */
	public CategoryModel getParent() {
		return this.parent_category;
	}
	
	/**
	 * 得到所有祖先分类集合, 不包括自己.
	 * @return
	 */
	public List<CategoryModel> getAncestor() {
		if (this.parent_category == null)
			return CategoryTreeModel.EMPTY_LIST;
		
		List<CategoryModel> ancestor = new ArrayList<CategoryModel>();
		CategoryModel p = this.parent_category;
		while (p != null) {
			ancestor.add(0, p);
			p = p.parent_category;
		}
		return CategoryTreeModel._readonly_list(ancestor);
	}
	
	/**
	 * (未实现) 得到前一个兄分类.
	 * @return
	 */
	public CategoryModel getPrevSibling() {
		throw new java.lang.UnsupportedOperationException();
	}
	
	/**
	 * (未实现) 得到后一个弟分类.
	 * @return
	 */
	public CategoryModel getNextSibling() {
		throw new java.lang.UnsupportedOperationException();
	}
	
	/**
	 * (未实现) 得到是否具有弟分类标志.
	 * @return
	 */
	public boolean getHasSibling() {
		throw new java.lang.UnsupportedOperationException();
	}
	
	/**
	 * 得到此分类的深度. 一级分类深度 = 0, 依次类推.
	 * @return
	 */
	public int getDepth() {
		return CategoryTreeModel.calcPathDepth(category.getParentPath());
	}
	
	/**
	 * 为显示一颗树使用的标志.
	 * @return
	 */
	public String getTreeFlag() {
		return this._tree_flag;
	}
	
	/**
	 * 为显示一颗树使用的标志. 提供给 html select 使用.
	 * @return
	 */
	public String getTreeFlag2() {
		return this._tree_flag.replaceAll(" ", "&nbsp; &nbsp;");
	}
}
