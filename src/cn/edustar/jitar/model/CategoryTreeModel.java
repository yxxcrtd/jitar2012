package cn.edustar.jitar.model;

import java.io.Serializable;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Collections;

//import org.apache.commons.collections.ListUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.edustar.jitar.pojos.Category;

/**
 * 分类树模型对象
 * 
 *
 */
public class CategoryTreeModel extends TreeModelBase<CategoryModel> implements Serializable {
	/**
     * 
     */
    private static final long serialVersionUID = -5693912031310307621L;

    /** 文章记录器 */
	private static final Log logger = LogFactory.getLog(CategoryTreeModel.class);

	/** '|' - 表示这里显示一个竖线，也即代表该层的祖先节点拥有一个弟节点。*/
	//public static final String TREE_FLAG_VLINE = "|";
	
	/** ' ' (空格) - 显示一个空白图片，表示该层的祖先节点没有弟节点。 */
	//public static final String TREE_FLAG_BLANK = " ";
	
	/** 'L' - 显示一个折线，表示是一个子节点，且子节点没有弟节点。 */
	//public static final String TREE_FLAG_L = "L";
	
	/** 'T' - 显示一个 '|-' (横过来的 T)型的线，表示是一个子节点，且有弟节点。 */
	//public static final String TREE_FLAG_T = "T";
	
	
	/**
	 * 构造一个分类树对象。
	 * @param iter
	 * @return
	 */
	public static CategoryTreeModel createTree(String itemType, Iterator<Category> iter) {
		CategoryTreeModel tree = new CategoryTreeModel(itemType, iter);
		tree.initTreeFlag();
		return tree;
	}
	
	/** 分类对象类型。 */
	private String item_type;
	
	/** 分类总数 */
	private int _count;
	
	/** 第一级子分类列表 */
	//private List<CategoryModel> root_cates = new ArrayList<CategoryModel>(); 
	
	/** 分类标识到分类模型的快速索引, 在树建立的时候建立 */
	private Map<Integer, CategoryModel> id_map;
	
	/** TODO: 分类名字到分类模型的快速索引, 根据需要建立 */
	// private Map<String, CategoryModel> name_map;
	
	/**
	 * 构造一个 CategoryTreeModel 的新实例。
	 */
	protected CategoryTreeModel(String itemType, Iterator<Category> iter) {
		this.item_type = itemType;
		internalInitTree(iter);
	}
	
	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return "CategoryTree{itemType=" + this.item_type + 
			", count=" + this._count +
			", root.size=" + super.childs.size() + "}";
	}
	
	/**
	 * 计算一个路径的深度，根('/')的深度 = 0。根据路径中有多少个 / 来计算深度。例如 '/1/Z3/' 深度 = 2。
	 * 
	 * @param nodePath
	 * @return 返回此路径代表的节点的深度。
	 * 
	 */
	public static int calcPathDepth(String nodePath) {
		if (nodePath == null || nodePath.length() == 0)
			return 0;
		int slash_num = 0;
		for (int i = 0; i < nodePath.length(); ++i) {
			if (nodePath.charAt(i) == '/')
				++slash_num;
		}
		return slash_num - 1;
	}

	/**
	 * 包装一个空集合的辅助函数。
	 * @param list
	 * @return
	 */
	static List<CategoryModel> _readonly_list(List<CategoryModel> list) {
		if (list == null || list.size() == 0) 
			return EMPTY_LIST;

		return new ReadonlyList(list);
	}
	
	/**
	 * 得到分类总数。
	 * @return
	 */
	public int getCount() {
		return this._count;
	}
	
	/**
	 * 得到此分类树的对象类型。
	 * @return
	 */
	public String getItemType() {
		return this.item_type;
	}
	
	/**
	 * 得到所有分类对象。
	 * @return 每次都将构造一个新的集合，可能比较慢。
	 */
	public List<CategoryModel> getAll() {
		List<CategoryModel> all = new ArrayList<CategoryModel>();
		for (int i = 0; i < super.childs.size(); ++i) {
			super.childs.get(i)._collect_child(all);
		}
		return _readonly_list(all);
	}
	
	/**
	 * 得到第一级的分类节点。
	 * @return
	 */
	public List<CategoryModel> getRoot() {
		return _readonly_list(super.childs);
	}

	/** 空的分类集合 */
	@SuppressWarnings("unchecked")
	public static final List<CategoryModel> EMPTY_LIST = Collections.EMPTY_LIST;
	
	/**
	 * 实现一个只读的列表类。
	 */
	public static final class ReadonlyList extends AbstractList<CategoryModel> implements List<CategoryModel> {
		private final List<CategoryModel> inner_list;
		
		/**
		 * 构造一个 ReadonlyList 的新实例。
		 * @param inner_list
		 */

		public ReadonlyList(List<CategoryModel> inner_list) {
			this.inner_list = inner_list == null ? EMPTY_LIST : inner_list;
		}
		
		/*
		 * (non-Javadoc)
		 * @see java.util.AbstractList#get(int)
		 */
		@Override
		public CategoryModel get(int index) {
			return inner_list.get(index);
		}

		/*
		 * (non-Javadoc)
		 * @see java.util.AbstractCollection#size()
		 */
		@Override
		public int size() {
			return inner_list.size();
		}

		/*
		 * (non-Javadoc)
		 * @see java.util.AbstractList#add(java.lang.Object)
		 */
		public boolean add(CategoryModel o) {
			throw uoe();
		}

		/*
		 * (non-Javadoc)
		 * @see java.util.AbstractList#add(int, java.lang.Object)
		 */
		public void add(int index, CategoryModel element) {
			throw uoe();
		}

		/*
		 * (non-Javadoc)
		 * @see java.util.AbstractList#set(int, java.lang.Object)
		 */
		public CategoryModel set(int index, CategoryModel element) {
			throw uoe();
		}
		
		private UnsupportedOperationException uoe() {
			return new java.lang.UnsupportedOperationException("collection is readonly");
		}
	}

	/** 内部初始化树 */
	private void internalInitTree(Iterator<Category> iter) {
		this.id_map = new HashMap<Integer, CategoryModel>();
		
		CategoryModel parent = null;
		while (iter.hasNext()) {
			Category category = iter.next();
			
			// 找其父节点。 此处修改于2008-7-10 谷升

			if (category.getParentId() != null  && category.getParentId()!=0 ) {
				parent = id_map.get(category.getParentId());
				if (parent == null) {
					logger.warn("构造分类树 " + this + " 的时候，未找到分类 " + category + " 的父分类, 该分类被忽略");
					continue;
				}
			} else {
				parent = null;
			}
			
			// 构造 model 对象，放到 id_map 里面
			category.setItemType(this.item_type);	// 能节省内存吧?
			CategoryModel model = newCategoryModel(category);
			id_map.put(category.getCategoryId(), model);
			
			if (parent == null)
				super.childs.add(model);
			else
				parent._appendChild(model);
		}
		
		this._count = id_map.size();
	}
	
	// 给派生类一个机会构造 CategoryModel 对象。
	protected CategoryModel newCategoryModel(Category category) {
		return new CategoryModel(category, this);
	}

	/** 初始化树标志 */
	private void initTreeFlag() {
		int size = super.childs.size();
		for (int i = 0; i < size; ++i) {
			CategoryModel model = super.childs.get(i);
			boolean has_prev = i > 0;
			boolean has_next = i < (size - 1);
			model._calcTreeFlag("", false, has_prev, has_next);
		}
	}
}
/*
 *  [+]-    有子节点
 *  
 *  
 *   |
 *  [+]-    有兄节点和子节点，无弟节点
 *  
 *  
 *  [+]-    有弟节点和子节点，无兄节点
 *   |
 *   
 *   |
 *  [+]-    有兄节点、弟节点、子节点
 *   |
 */
