package cn.edustar.jitar.action;

import cn.edustar.jitar.model.CategoryTreeModel;
import cn.edustar.jitar.pojos.Category;
import cn.edustar.jitar.service.CategoryService;
import cn.edustar.jitar.util.CommonUtil;

/**
 * 群组的分类管理. * 
 *
 *
 */
public class GroupCategoryAction extends BaseGroupAction { // BaseCategoryAction {
	/**	 */
	private static final long serialVersionUID = 1L;

	/** 分类管理辅助器 */
	private CategoryActionHelper cate_helper = new CategoryActionHelper();

	/** 分类服务 */
	public void setCategoryService(CategoryService cate_svc) {
		cate_helper.setCategoryService(cate_svc);
	}

	/** 父分类 */
	private Category parent_category;

	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.action.ManageBaseAction#execute(java.lang.String)
	 */
	protected String execute(String cmd) {
		if (isUserLogined() == false) return LOGIN;
		
		// 得到当前分类.
		if (hasCurrentGroup() == false) return ERROR;
		
		// 下述命令都需要有当前分类.
		if (cmd == null || cmd.length() == 0) cmd = "list";
		if ("list".equals(cmd))
			return list();
		else if ("add".equals(cmd))
			return add();
		else if ("edit".equals(cmd))
			return add();
		else if ("save".equals(cmd)) 
			return save();
		else if ("delete".equals(cmd)) 
		return delete();
		else
			return unknownCommand(cmd); 
	}

	/** 计算出群组分类的项目类型 = 'group_' + $groupId */
	private String calcItemType() {
		return CommonUtil.toGroupResourceCategoryItemType(group_model.getGroupId());
	}
	
	/**
	 * 显示群组分类树.	 * @return
	 */
	private String list() {
		// 得到分类树.		CategoryTreeModel category_tree = cate_helper.getCategoryTreeUncached(calcItemType());
		
		request.setAttribute("group", group_model);
		request.setAttribute("category_tree", category_tree);
		
		return LIST_SUCCESS;
	}

	/**
	 * 添加一个分类.	 * @return
	 */
	private String add() {
		// 父分类.
		Integer cid = param_util.getIntParamZeroAsNull("cid");
		Integer categoryId = param_util.getIntParamZeroAsNull("categoryId");
		if (cid != null) {
			this.parent_category = cate_helper.getCategoryService().getCategory(cid);
		}
		// 构造一个新的分类对象，并设置其属性.		Category category ;
		if (categoryId != null) {
			category=cate_helper.getCategory(categoryId);
		}
		else
		{
			category = cate_helper.newCategory();
			category.setItemType(calcItemType());
			category.setParentId(cid);
		}
		

		request.setAttribute("group", group_model);
		// 项目类型. 
		request.setAttribute("itemType", calcItemType());
		// 新增分类.
		request.setAttribute("category", category);
		// 父分类.		request.setAttribute("parent_category", parent_category);
		// 所有分类树.
		CategoryTreeModel category_tree = cate_helper.getCategoryTreeUncached(calcItemType());
		request.setAttribute("category_tree", category_tree);
		
		return ADD_SUCCESS;
	}

	/**
	 * 删除一个分类.
	 * @return
	 */
	private String delete() {
		
		Category category=cate_helper.getCategory(param_util.getIntParam("categoryId"));
		cate_helper.getCategoryService().deleteCategory(category);
		return SUCCESS;
	}
	/**
	 * 新建一个分类.	 * @return
	 */
	private String save() {
		// 收集提交上来的数据.		Category category = cate_helper.collectCategoryObject(param_util);
		category.setItemType(calcItemType());
		
		// 创建分类.
		if(category.getCategoryId()>0)
		{
			cate_helper.getCategoryService().updateCategory(category);
		}
		else
		{
			cate_helper.getCategoryService().createCategory(category);
		}
		request.setAttribute("group", group_model);
		request.setAttribute("category", category);

		return SUCCESS;
	}
}
