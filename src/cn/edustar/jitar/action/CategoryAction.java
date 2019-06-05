package cn.edustar.jitar.action;

import cn.edustar.jitar.service.CategoryService;

/**
 * 系统分类管理.
 * 
 * 页面参数： 
 *   t - 要管理的分类类型，参见定义 Category
 *   parent - 父分类标识，等于 0 等同于空
 *   cmd - 操作命令。 为空 - 等同于 list
 *     list - 显示分类列表树. 
 *     add - 添加分类. 
 *     modify - 修改分类. 
 *     move - 移动分类. 
 *     delete - 删除分类. 
 *     sort - 分类排序. 
 *     unite - 分类合并.
 * 
 *
 * @deprecated - 被 admin_category.py 取代.
 */
@Deprecated
public class CategoryAction extends ManageBaseAction {
	/** 序列号 */
	private static final long serialVersionUID = 5155864228320052753L;

	private CategoryActionHelper cate_helper = new CategoryActionHelper();
	
	/** 分类类型, 缺省 = 'default' */
	@SuppressWarnings("unused")
	private String itemType = "default";
	
	/** 父分类标识，为空表示没有 */
	@SuppressWarnings("unused")
	private Integer parentId;

	/** 用户分类服务接口 */
	public void setCategoryService(CategoryService cate_svc) {
		cate_helper.setCategoryService(cate_svc);
		
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.edustar.jitar.action.ManageBaseAction#execute(java.lang.String)
	 */
	protected String execute(String cmd) throws Exception {
		return unknownCommand(cmd);
	}
	
}
