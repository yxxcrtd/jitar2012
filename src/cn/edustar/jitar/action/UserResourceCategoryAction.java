package cn.edustar.jitar.action;

import cn.edustar.jitar.model.CategoryTreeModel;
import cn.edustar.jitar.pojos.Category;
import cn.edustar.jitar.service.CategoryService;
import cn.edustar.jitar.service.ResourceService;
import cn.edustar.jitar.util.CommonUtil;
import cn.edustar.jitar.util.FileCache;

/**
 * 用户资源分类管理
 * 
 *
 */
public class UserResourceCategoryAction extends ManageBaseAction {
	
	/** serialVersionUID */
	private static final long serialVersionUID = 5621956877421414262L;
	
	/** 资源服务 */
	private ResourceService res_svc;
	
	/** 分类管理辅助对象 */
	private CategoryActionHelper cate_helper = new CategoryActionHelper();

	/** 资源服务 */
	public void setResourceService(ResourceService res_svc) {
		this.res_svc = res_svc;
	}

	/** 分类服务 */
	public void setCategoryService(CategoryService cate_svc) {
		cate_helper.setCategoryService(cate_svc);
	}

	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.action.ManageBaseAction#execute(java.lang.String)
	 */
	@Override
	protected String execute(String cmd) throws Exception {
		if (isUserLogined() == false) return LOGIN;
		if (canVisitUser(getLoginUser()) == false) return ERROR;
		
		if (cmd == null || cmd.length() == 0) cmd = "list";
		if ("list".equals(cmd))
			return list();
		else if ("add".equals(cmd))
			return add();
		else if ("edit".equals(cmd))
			return edit();
		else if ("save".equals(cmd))
			return save();
		else if ("delete".equals(cmd))
			return delete();
		
		return unknownCommand(cmd);
	}
	
	/**
	 * 列出用户的分类树.
	 * @return
	 */
	private String list() {
		// 得到分类树.
		CategoryTreeModel category_tree = getCategoryTree();
		
		setRequestAttribute("user", getLoginUser());
		setRequestAttribute("category_tree", category_tree);
		
		return LIST_SUCCESS;
	}

	// 得到要操作的分类树类型.
	private String getCategoryItemType() {
		return CommonUtil.toUserResourceCategoryItemType(getLoginUser().getUserId());
	}
	
	// 得到指定分类树.
	private CategoryTreeModel getCategoryTree() {
		String itemType = getCategoryItemType();
		CategoryTreeModel category_tree = cate_helper.getCategoryTreeUncached(itemType);
		return category_tree;
	}
	
	/**
	 * 添加一个子分类.
	 * @return
	 */
	private String add() {
		// 得到父分类标识.
		Integer parentId = param_util.getIntParamZeroAsNull("categoryId");
		setRequestAttribute("parentId", parentId);
		
		// 得到分类树.
		CategoryTreeModel category_tree = getCategoryTree();
		
		setRequestAttribute("user", getLoginUser());
		setRequestAttribute("category_tree", category_tree);
		
		// 创建新增的分类对象.
		Category category = new Category();
		category.setParentId(parentId);
		category.setName("请输入分类名");
		setRequestAttribute("category", category);
		
		setRequestAttribute("__referer", getRefererHeader());
		
		return ADD_SUCCESS;
	}
	
	/**
	 * 编辑一个分类.
	 * @return
	 */
	private String edit() {
		int cid = param_util.getIntParam("categoryId");
		
		// 得到要编辑的分类.
		Category category = cate_helper.getCategory(cid);
		if (category == null) {
			addActionError("未找到要编辑的分类, 分类标识为 " + cid);
			return ERROR;
		}

		// 验证分类类型必须匹配.
		String itemType = this.getCategoryItemType();
		if (itemType.equals(category.getItemType()) == false) {
			addActionError("要编辑的分类不属于当前登录用户, 操作被拒绝");
			return ERROR;
		}
		
		setRequestAttribute("category", category);
		
		// 父分类标识.
		setRequestAttribute("parentId", category.getParentId());
		
		// 得到分类树.
		CategoryTreeModel category_tree = cate_helper.getCategoryTreeUncached(itemType);
		
		setRequestAttribute("user", getLoginUser());
		setRequestAttribute("category_tree", category_tree);
		
		setRequestAttribute("__referer", getRefererHeader());
		
		return ADD_SUCCESS;
	}
	
	/**
	 * 保存一个分类.
	 * @return
	 */
	private String save() {
		// 收集提交上来的数据.
		String itemType = this.getCategoryItemType();
		Category category = cate_helper.collectCategoryObject(param_util);
		category.setItemType(itemType);
		
		if (category.getCategoryId() == 0) {
			// 创建分类
			cate_helper.getCategoryService().createCategory(category);
			addActionMessage("分类 " + category.getName() + " 创建成功.");
		} else {
			// 更新/移动分类.
			cate_helper.getCategoryService().updateCategory(category);
			addActionMessage("分类 " + category.getName() + " 修改成功完成.");
		}
		
		setRequestAttribute("category", category);
		FileCache fc = new FileCache();
		fc.deleteUserResourceCate(getLoginUser().getLoginName());
		fc = null;
		return SUCCESS;
	}
	
	/**
	 * 删除一个分类.
	 * @return
	 */
	private String delete() {
		// 得到分类对象.
		int categoryId = param_util.getIntParam("categoryId");
		Category category = cate_helper.getCategory(categoryId);
		if (category == null) {
			addActionError("未找到要删除的分类, 分类标识为 " + categoryId);
			return ERROR;
		}

		// 验证分类类型必须匹配.
		String itemType = this.getCategoryItemType();
		if (itemType.equals(category.getItemType()) == false) {
			addActionError("要删除的分类不属于当前登录用户, 操作被拒绝");
			return ERROR;
		}

		// 更新使用此分类的资源, 将其 userCateId 都设置为 NULL.
		res_svc.batchClearResourceUserCategory(category.getCategoryId());
		// 执行删除.
		cate_helper.getCategoryService().deleteCategory(category);
		
		addActionMessage("分类 " + category.getName() + " 成功删除");
		FileCache fc = new FileCache();
		fc.deleteUserResourceCate(getLoginUser().getLoginName());
		fc = null;
		return SUCCESS;
	}

}
