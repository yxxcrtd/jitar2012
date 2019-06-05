package cn.edustar.jitar.action;

import java.util.List;

import cn.edustar.jitar.JitarContext;
import cn.edustar.jitar.model.ArticleModelEx;
import cn.edustar.jitar.service.ArticleQueryParam;
import cn.edustar.jitar.service.ArticleService;
import cn.edustar.jitar.service.CategoryService;
import cn.edustar.jitar.service.GroupService;
import cn.edustar.jitar.service.SubjectService;
import cn.edustar.jitar.util.ParamUtil;

public class RssAction extends AbstractServletAction {
	
	/** serialVersionUID */
	private static final long serialVersionUID = 8881698547737061628L;
	
	protected ParamUtil param_util;
		
	/** 分类服务对象 */
	@SuppressWarnings("unused")
	private CategoryService cate_svc;

	/** 文章服务 */
	private ArticleService article_svc;

	/** 学科服务 */
	@SuppressWarnings("unused")
	private SubjectService subj_svc;

	/** 群组服务 */
	@SuppressWarnings("unused")
	private GroupService group_svc;

	@SuppressWarnings("unused")
	private String type;
	/**
	 * 构造函数.
	 */
	public RssAction() {
		JitarContext jtar_ctxt = JitarContext.getCurrentJitarContext();
		if (jtar_ctxt != null) {
			this.cate_svc = jtar_ctxt.getCategoryService();
			this.article_svc = jtar_ctxt.getArticleService();
			this.subj_svc = jtar_ctxt.getSubjectService();
			this.group_svc = jtar_ctxt.getGroupService();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.edustar.jitar.action.ManageBaseAction#execute(java.lang.String)
	 */
	@Override
	public String execute() throws Exception {
		
		this.param_util = new ParamUtil(getActionContext().getParameters());
		String cmd = param_util.safeGetStringParam("cmd");
		
		if("article".equalsIgnoreCase(cmd))
		{
			ArticleQueryParam ap = new ArticleQueryParam();
			ap.hideState = 0;
			
			List<ArticleModelEx> a = (List<ArticleModelEx>)this.article_svc.getArticleList(ap, null);
			return getArticles();
		}
		else if("photo".equalsIgnoreCase(cmd))
		{
			return getPhotos();
		}
		else if("topic".equalsIgnoreCase(cmd))
		{
			return getTopics();
		}
		else if("resource".equalsIgnoreCase(cmd))
		{
			return getResources();
		}		
		else{
			return SUCCESS;
		}
	}

	
	private String getPhotos(){
		return "photo";
	}
	private String getTopics(){
		return "topic";
	}
	private String getResources(){
		return "resource";
	}
	private String getArticles() {
		
		
		return "article";
	}
	
	
	/** 分类服务对象的set方法. */
	public void setCategoryService(CategoryService cat_svc) {
		this.cate_svc = cat_svc;
	}

	/** 评论服务的set方法. */
	public void setArticleService(ArticleService article_svc) {
		this.article_svc = article_svc;
	}

	/** 学科服务的set方法. */
	public void setSubjectService(SubjectService subj_svc) {
		this.subj_svc = subj_svc;
	}

	/** 设置群组服务. */
	public void setGroupService(GroupService group_svc) {
		this.group_svc = group_svc;
	}
	
	
}