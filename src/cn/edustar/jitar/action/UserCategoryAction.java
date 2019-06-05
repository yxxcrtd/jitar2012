package cn.edustar.jitar.action;

import cn.edustar.jitar.model.CategoryTreeModel;
import cn.edustar.jitar.pojos.Category;
import cn.edustar.jitar.query.sitefactory.UserIndexHtmlService;
import cn.edustar.jitar.service.ArticleService;
import cn.edustar.jitar.service.CategoryService;
import cn.edustar.jitar.util.CommonUtil;
import cn.edustar.jitar.util.FileCache;

/**
 * 用户新分类管理(使用 CategoryService 建立树状分类)
 * 
 *
 */
public class UserCategoryAction extends ManageBaseAction {
	
	/** serialVersionUID */
	private static final long serialVersionUID = 7052213829546597401L;

	/** 分类管理辅助对象 */
	private CategoryActionHelper cate_helper = new CategoryActionHelper();

	/** 文章服务. */
	private ArticleService art_svc;

	/** 分类服务的set方法 */
	public void setCategoryService(CategoryService cate_svc) {
		cate_helper.setCategoryService(cate_svc);
	}

	/** 文章服务的set方法 */
	public void setArticleService(ArticleService art_svc) {
		this.art_svc = art_svc;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.edustar.jitar.action.ManageBaseAction#execute(java.lang.String)
	 */
	protected String execute(String cmd) throws Exception {
		
		// 登录验证
		if (isUserLogined() == false)
			return LOGIN;
		
		if (canVisitUser(getLoginUser()) == false)
			return ERROR;

		if (cmd == null || cmd.length() == 0)
			cmd = "list";
		if ("list".equals(cmd))
			return list();

		// 以下需要管理权限.
		if (canManageBlog(getLoginUser()) == false)
			return ERROR;

		if ("add".equals(cmd))
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
	 * 
	 * @return
	 */
	private String list() {
		// 得到分类树.
		String itemType = CommonUtil.toUserArticleCategoryItemType(getLoginUser().getUserId());
		CategoryTreeModel category_tree = cate_helper.getCategoryTreeUncached(itemType);

		setRequestAttribute("user", getLoginUser());
		setRequestAttribute("category_tree", category_tree);

		return LIST_SUCCESS;
	}

	/**
	 * 添加一个子分类.
	 * 
	 * @return
	 */
	private String add() {
		// 得到父分类标识.
		Integer parentId = param_util.getIntParamZeroAsNull("cid");
		setRequestAttribute("parentId", parentId);

		// 得到分类树.
		String itemType = CommonUtil.toUserArticleCategoryItemType(getLoginUser().getUserId());
		CategoryTreeModel category_tree = cate_helper.getCategoryTreeUncached(itemType);

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
	 * 
	 * @return
	 */
	private String edit() {
		int cid = param_util.getIntParam("cid");

		// 得到要编辑的分类.
		Category category = cate_helper.getCategory(cid);
		if (category == null) {
			addActionError("未找到要编辑的分类, 分类标识为 " + cid);
			return ERROR;
		}

		// 验证分类类型必须匹配.
		String itemType = CommonUtil.toUserArticleCategoryItemType(getLoginUser().getUserId());
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
	 * 
	 * @return
	 */
	private String save() {
		// 收集提交上来的数据.
		String itemType = CommonUtil.toUserArticleCategoryItemType(getLoginUser().getUserId());
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
		fc.deleteUserArticleCate(getLoginUser().getLoginName());
		fc = null;
		return SUCCESS;
	}

	/**
	 * 删除一个分类.
	 * 
	 * @return
	 */
	private String delete() {
		// 得到分类对象.
		int cid = param_util.getIntParam("cid");
		Category category = cate_helper.getCategory(cid);
		if (category == null) {
			addActionError("未找到要删除的分类, 分类标识为 " + cid);
			return ERROR;
		}

		// 验证分类类型必须匹配.
		String itemType = CommonUtil.toUserArticleCategoryItemType(getLoginUser().getUserId());
		if (itemType.equals(category.getItemType()) == false) {
			addActionError("要删除的分类不属于当前登录用户, 操作被拒绝");
			return ERROR;
		}

		// 更新使用此分类的文章, 将其 userCateId 都设置为 NULL.
		art_svc.batchClearArticleUserCategory(category.getCategoryId());
		// 执行删除.
		cate_helper.getCategoryService().deleteCategory(category);

		addActionMessage("分类 " + category.getName() + " 成功删除");
		
		FileCache fc = new FileCache();
		fc.deleteUserArticleCate(getLoginUser().getLoginName());
		fc = null;
		
		return SUCCESS;
	}
	
}
