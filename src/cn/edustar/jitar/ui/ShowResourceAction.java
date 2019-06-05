package cn.edustar.jitar.ui;

import java.util.List;

import cn.edustar.data.Pager;
import cn.edustar.jitar.action.AbstractPageAction;
import cn.edustar.jitar.model.ObjectType;
import cn.edustar.jitar.pojos.Comment;
import cn.edustar.jitar.pojos.Grade;
import cn.edustar.jitar.pojos.Resource;
import cn.edustar.jitar.pojos.Subject;
import cn.edustar.jitar.pojos.User;
import cn.edustar.jitar.service.CommentQueryParam;
import cn.edustar.jitar.service.CommentService;
import cn.edustar.jitar.service.ResourceService;
import cn.edustar.jitar.service.StatService;
import cn.edustar.jitar.service.SubjectService;
import cn.edustar.jitar.service.UserService;
import cn.edustar.jitar.util.ParamUtil;

import com.opensymphony.xwork2.ActionContext;

/**
 * 显示指定资源的页面.
 *
 *
 */
public class ShowResourceAction extends AbstractPageAction {
	/** serialVersionUID */
	private static final long serialVersionUID = 6240812502318182227L;

	/** 资源服务 */
	private ResourceService res_svc;
	
	/** 评论服务 */
	private CommentService cmt_svc;
	
	/** 用户服务 */
	private UserService user_svc;
	
	/** 统计服务 */
	private StatService stat_svc;
	
	/** 学科服务. */
	private SubjectService subj_svc;
	
	/** 参数对象 */
	private ParamUtil param_util;
	
	/** 当前资源对象 */
	private Resource resource;
	
	/** 资源的拥有者对象 */
	private User resource_user;

	/**
	 * 构造.
	 */
	public ShowResourceAction() {
		
	}
	
	/** 资源服务 */
	public void setResourceService(ResourceService res_svc) {
		this.res_svc = res_svc;
	}

	/** 评论服务 */
	public void setCommentService(CommentService cmt_svc) {
		this.cmt_svc = cmt_svc;
	}

	/** 用户服务 */
	public void setUserService(UserService user_svc) {
		this.user_svc = user_svc;
	}

	/** 统计服务 */
	public void setStatService(StatService stat_svc) {
		this.stat_svc = stat_svc;
	}

	/** 学科服务. */
	public void setSubjectService(SubjectService subj_svc) {
		this.subj_svc = subj_svc;
	}

	/*
	 * (non-Javadoc)
	 * @see com.opensymphony.xwork2.ActionSupport#execute()
	 */
	public String execute(String cmd) {
		this.param_util = new ParamUtil(ActionContext.getContext().getParameters());
		
		String result = prepareResource();
		if (result != null) return result;
		prepareResourceComment();
		
		super.prepareData();
		
		//String cmd = param_util.safeGetStringParam("cmd");
		if ("debug".equals(cmd))
			return "debug";
		
		return SUCCESS;
	}
	
	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.action.AbstractPageAction#getContextObject(java.lang.String)
	 */
	public Object getContextObject(String name) {
		if ("resource".equals(name))
			return this.resource;
		else if ("resource_user".equals(name))
			return this.resource_user;
		
		return super.getContextObject(name);
	}

	/**
	 * 准备页面所需的资源对象，所属用户，分类等对象.
	 */
	private String prepareResource() {
		// 获得资源对象.
		int resourceId = param_util.getIntParam("resourceId");
		this.resource = res_svc.getResource(resourceId);
		super.setData("resource", resource);
		
		// 验证资源是否存在以及是否审核通过、未删除.
		if (resource == null) {
			addActionError("指定标识为 " + resourceId + " 的资源不存在, 如果您该资源原来存在, 则其可能被删除了.");
			return ERROR;
		}
		if (resource.getAuditState() != 0) {
			addActionError("资源 " + resource.toDisplayString() + " 未审核通过, 请等待管理员审核通过才可查看该资源.");
			return ERROR;
		}
		if (resource.getDelState()) {
			addActionError("资源 " + resource.toDisplayString() + " 已经被删除.");
			return ERROR;
		}
		
		// 得到资源的发布者信息.
		int userId = resource.getUserId();
		this.resource_user = user_svc.getUserById(userId); 
		super.setData("resource_user", this.resource_user);
		
		// 得到资源所属学科, 学段.
		if (resource.getSubjectId() != null) {
			Subject resource_subject = subj_svc.getSubjectById(resource.getSubjectId());
			super.setData("resource_subject", resource_subject);
		}
		if (resource.getGradeId() != null) {
			Grade resource_grade = subj_svc.getGrade(resource.getGradeId());
			super.setData("resource_grade", resource_grade);
		}
	
		stat_svc.incResourceVisitCount(resource);
		return null;
	}

	/**
	 * 准备页面的评论.
	 */
	private void prepareResourceComment() {
		Pager pager = new Pager();
		pager.setCurrentPage(param_util.safeGetIntParam("page", 1));
		pager.setPageSize(20);
		pager.setItemNameAndUnit("评论", "条");
		pager.setUrlPattern(param_util.generateUrlPattern());
		
		CommentQueryParam param = new CommentQueryParam();
		param.objectType = ObjectType.OBJECT_TYPE_RESOURCE;
		param.objectId = resource.getResourceId();
		param.audit = true;
		List<Comment> comment_list = cmt_svc.getCommentList(param, pager);
		
		setData("comment_list", comment_list);
		setData("pager", pager);
	}
}
