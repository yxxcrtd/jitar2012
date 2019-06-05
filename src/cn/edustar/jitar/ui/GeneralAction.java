package cn.edustar.jitar.ui;

import cn.edustar.jitar.JitarContext;
import cn.edustar.jitar.action.AbstractPageAction;
import cn.edustar.jitar.pojos.Category;
import cn.edustar.jitar.pojos.Group;
import cn.edustar.jitar.pojos.Subject;
import cn.edustar.jitar.pojos.User;
import cn.edustar.jitar.service.CategoryService;
import cn.edustar.jitar.service.GroupService;
import cn.edustar.jitar.service.SubjectService;
import cn.edustar.jitar.service.UserService;
import cn.edustar.jitar.util.ParamUtil;

import com.opensymphony.xwork2.ActionContext;

/**
 * 一种通用的 action 容器页面, 支持 subjectId, categoryId, userId 参数.
 *
 * @deprecated data bean 方式不使用之后, 此类也就过期了.
 */
public class GeneralAction extends AbstractPageAction {
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -1651113854401292936L;

	/** 使能不同学科不同结果的返回. */
	private boolean enableSubjectResult = false;
	
	/** 返回的结果值，缺省 = 'success' */
	private String result = SUCCESS;
	
	/** 参数对象 */
	public ParamUtil param;
	
	/** 用户服务 */
	private UserService user_svc;
	
	/** 分类服务 */
	private CategoryService cate_svc;
	
	/** 学科服务 */
	private SubjectService subj_svc;
	
	/** 群组服务 */
	private GroupService group_svc;

	/** 使能不同学科不同结果的返回. */
	public void setEnableSubjectResult(boolean enableSubjectResult) {
		this.enableSubjectResult = enableSubjectResult;
	}

	/** 返回的结果值，缺省 = 'success' */
	public void setResult(String result) {
		this.result = result;
	}

	/** 用户服务 */
	public UserService getUserService() {
		return this.user_svc;
	}

	/** 用户服务 */
	public void setUserService(UserService user_svc) {
		this.user_svc = user_svc;
	}

	/** 分类服务 */
	public CategoryService getCategoryService() {
		return this.cate_svc;
	}
	
	/** 分类服务 */
	public void setCategoryService(CategoryService cate_svc) {
		this.cate_svc = cate_svc;
	}
	
	/** 学科服务 */
	public SubjectService getSubjectService() {
		return this.subj_svc;
	}
	
	/** 学科服务 */
	public void setSubjectService(SubjectService subj_svc) {
		this.subj_svc = subj_svc;
	}

	/** 群组服务 */
	public GroupService getGroupService() {
		return this.group_svc;
	}
	
	/** 群组服务 */
	public void setGroupService(GroupService group_svc) {
		this.group_svc = group_svc;
		
	}

	/** 当前用户对象 */
	private User user;
	
	/** 当前分类对象 */
	private Category category;
	
	/** 当前学科对象 */
	private Subject subject;
	
	/** 当前群组对象 */
	private Group group;
	
	/**
	 * 构造.
	 */
	public GeneralAction() {
		JitarContext jtar_ctxt = JitarContext.getCurrentJitarContext();
		if (jtar_ctxt != null) {
			this.user_svc = jtar_ctxt.getUserService();
			this.cate_svc = jtar_ctxt.getCategoryService();
			this.subj_svc = jtar_ctxt.getSubjectService();
			this.group_svc = jtar_ctxt.getGroupService();
		}
	}
	
	/** 当前用户对象 */
	public User getUser() {
		return this.user;
	}
	
	/** 当前分类对象 */
	public Category getCategory() {
		return this.category;
	}

	/**
	 * 得到当前学科.
	 */
	public Subject getSubject() {
		return this.subject;
	}
	
	/**
	 * 设置当前学科.
	 * @param subject
	 */
	public void setSubject(Subject subject) {
		this.subject = subject;
	}
	
	/** 当前群组对象 */
	public Group getGroup() {
		return this.group;
	}

	/**
	 * 执行前的初始化.
	 */
	protected String beforeExecute() {
		// 预先准备可能提供的参数 userId, loginName, categoryId, subjectId.
		prepareUser();
		prepareCategory();
		prepareSubject();
		prepareGroup();
		return null;
	}
	
	/**
	 * 执行完成之后的处理, 派生类可以重载实现后处理.
	 */
	protected void afterExecute() {
	
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.opensymphony.xwork2.ActionSupport#execute()
	 */
	@Override
	public String execute(String cmd) {
		this.param = new ParamUtil(ActionContext.getContext().getParameters());
		
		beforeExecute();
		
		// 准备 dataList 中配置的数据.
		prepareData();
		
		// 如果有当前学科, 且设置了 enableSubjectResult 则返回为 'success_$yuwen' 形式.
		if (this.enableSubjectResult && this.subject != null) {
			return this.result + "_" + this.subject.getSubjectName();
		}
		
		afterExecute();
		
		return this.result;
	}

	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.action.AbstractPageAction#getContextObject(java.lang.String)
	 */
	public Object getContextObject(String name) {
		if (this.user != null && "user".equals(name))
			return this.user;
		else if (this.category != null && "category".equals(name))
			return this.category;
		else if (this.subject != null && "subject".equals(name))
			return this.subject;
		else if (this.group != null && "group".equals(name))
			return this.group;
		
		return super.getContextObject(name);
	}

	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.data.DataHost#getParameters()
	 */
	public ParamUtil getParameters() {
		return this.param;
	}

	/**
	 * 准备这个页面使用的用户对象.
	 */
	private void prepareUser() {
		if (prepareUserById()) return;
		prepareUserByName();
	}
	
	private boolean prepareUserByName() {
		// 尝试是否有 loginName 参数.
		if (param.existParam("loginName") == false) return false;
		String loginName = param.safeGetStringParam("loginName");
		if (loginName == null) return false;
		loginName = loginName.trim();
		if (loginName.length() == 0) return false;
		
		// 得到用户.
		User user = user_svc.getUserByLoginName(loginName, true);
		if (user == null) return false;
		
		this.user = user;
		super.setData("user", this.user);
		return true;
	}
	
	private boolean prepareUserById() {
		// 尝试 userId 参数.
		if (param.existParam("userId") == false) return false;
		int userId = param.getIntParam("userId");
		if (userId == 0) return false;
		
		// 得到用户.
		User user = user_svc.getUserById(userId, true);
		if (user == null) return false;
		
		this.user = user;
		super.setData("user", this.user);
		return true;
	}
	
	/**
	 * 准备这个页面使用的分类对象.
	 */
	private boolean prepareCategory() {
		// 尝试 categoryId 参数.
		if (param.existParam("categoryId") == false) return false;
		int categoryId = param.getIntParam("categoryId");
		if (categoryId == 0) return false;
		
		// 得到分类.
		Category category = cate_svc.getCategory(categoryId);
		if (category == null) return false;
		
		this.category = category;
		super.setData("category", this.category);
		return true;
	}

	/**
	 * 准备这个页面使用的学科对象.
	 */
	private boolean prepareSubject() {
		// 尝试 subjectId 参数.
		if (param.existParam("subjectId") == false) return false;
		int subjectId = param.getIntParam("subjectId");
		if (subjectId == 0) return false;
		
		// 得到学科.
		Subject subject = subj_svc.getSubjectById(subjectId);
		if (subject == null) return false;
		
		this.subject = subject;
		super.setData("subject", this.subject);
		return true;
	}
	
	/**
	 * 准备这个页面使用的群组对象.
	 * @return
	 */
	private boolean prepareGroup() {
		// 尝试 groupId 参数.
		if (param.existParam("groupId") == false) return false;
		int groupId = param.getIntParam("groupId");
		if (groupId == 0) return false;
		
		// 得到群组.
		Group group = group_svc.getGroupMayCached(groupId);
		if (group == null) return false;
		
		this.group = group;
		super.setData("group", group);
		return true;
	}
}
