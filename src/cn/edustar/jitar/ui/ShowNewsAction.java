package cn.edustar.jitar.ui;

import cn.edustar.jitar.action.AbstractPageAction;
import cn.edustar.jitar.pojos.SiteNews;
import cn.edustar.jitar.service.SubjectService;
import cn.edustar.jitar.util.ParamUtil;

import com.opensymphony.xwork2.ActionContext;

/**
 * 显示站点/学科新闻.
 *
 *
 */
public class ShowNewsAction extends AbstractPageAction {
	/** serialVersionUID */
	private static final long serialVersionUID = -3387775857408027203L;

	/** 站点/学科新闻服务 */
	private SubjectService subj_svc;
	
	/** 要显示的新闻 */
	private SiteNews news;
	
	/**
	 * 构造.
	 */
	public ShowNewsAction() {
		
	}
	
	/** 站点/学科新闻服务 */
	public void setSubjectService(SubjectService subj_svc) {
		this.subj_svc = subj_svc;
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.opensymphony.xwork2.ActionSupport#execute()
	 */
	@Override
	public String execute(String cmd) {
		ParamUtil param_util = new ParamUtil(ActionContext.getContext().getParameters());
		//String cmd = param_util.safeGetStringParam("cmd");
		
		if (prepareNews(param_util) == false)
			return ERROR;
		
		super.prepareData();
		
		if ("debug".equals(cmd))
			return "debug";
		
		return SUCCESS;
	}
	
	private boolean prepareNews(ParamUtil param) {
		// 得到该新闻.
		int newsId = param.getIntParam("newsId");
		this.news = subj_svc.getSiteNews(newsId);
		if (this.news == null) {
			addActionError("未能找到指定标识的新闻");
			return false;
		}
		
		// 增加新闻浏览数.
		subj_svc.incSiteNewsViewCount(news.getNewsId(), 1);
		
		return true;
	}
	
	/**
	 * 得到当前新闻对象.
	 * @return
	 */
	public SiteNews getNews() {
		return this.news;
	}
	
	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.action.AbstractPageAction#getContextObject(java.lang.String)
	 */
	@Override
	public Object getContextObject(String name) {
		if ("news".equals(name))
			return this.news;
		return super.getContextObject(name);
	}
}
