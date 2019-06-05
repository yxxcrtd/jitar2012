package cn.edustar.jitar.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 做为 CategoryModel, CategoryTreeModel 的基类。
 * 
 *
 * 
 * @remark 模板需要的参数是自己，java 这个支持不知道是否有问题
 */
abstract class TreeModelBase<T extends TreeModelBase<T> > implements Serializable{
	/** 父节点 */
	// private TreeModel parent;
	
	/**
     * 
     */
    private static final long serialVersionUID = 8379381578049776452L;
    /** 拥有的子节点列表 */
	protected List<T> childs = new ArrayList<T>();
	
	/** 得到子节点列表 */
	protected List<T> _internal_getChilds() {
		return childs;
	}
	
	/*
	protected void _setParent(TreeModel p) {
		this.parent = p;
	}
	
	protected TreeModel _getParent() {
		return parent;
	}
	*/
}
