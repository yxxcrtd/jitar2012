package cn.edustar.jitar.data;

import cn.edustar.data.DataTable;
import cn.edustar.data.Pager;
import cn.edustar.jitar.JitarContext;
import cn.edustar.jitar.pojos.Subject;
import cn.edustar.jitar.pojos.User;
import cn.edustar.jitar.service.SiteNewsQueryParam;
import cn.edustar.jitar.service.SubjectService;
import cn.edustar.jitar.util.ParamUtil;

/**
 * 获得站点/学科新闻的 bean, 缺省 varName = 'news_list'.
 *
 *
 */
public class SiteNewsBean extends AbstractPageDataBean {
	/** 查询参数 */
	private SiteNewsQueryParam param = newSiteNewsQueryParam();
	
	/** 学科服务 */
	private SubjectService subj_svc;
	
	/**
	 * 构造.
	 */
	public SiteNewsBean() {
		super.setVarName("news_list");
		super.setItemName("新闻");
		super.setItemUnit("条");
		this.subj_svc = JitarContext.getCurrentJitarContext().getSubjectService();
	}
	
	/**
	 * 实例化一个 SiteNewsQueryParam.
	 * @return
	 */
	protected SiteNewsQueryParam newSiteNewsQueryParam() {
		return new SiteNewsQueryParam();
	}
	
	/**
	 * 得到查询参数.
	 * @return
	 */
	public SiteNewsQueryParam getQueryParam() {
		return this.param;
	}
	
	/** 群组服务 */
	public void setSubjectService(SubjectService subj_svc) {
		this.subj_svc = subj_svc;
	}

	/** 查询条数, 一般在未指定 pager 的时候使用 */
	public void setCount(int count) {
		param.count = count;
	}
	
	/** 限定学科标识, 缺省 = 0 表示获取站点的; = null 表示不限制 */
	public void setSubjectId(String subjectId) {
		param.subjectId = ParamUtil.safeParseIntegerWithNull(subjectId, null);
	}
	
	/** 发布用户标识, 缺省 = null 表示不限制 */
	public void setUserId(String userId) {
		param.userId = ParamUtil.safeParseIntegerWithNull(userId, null);
	}
	
	/** 要获取的对象状态, 缺省 = GroupNews.NEWS_STATUS_NORMAL 获取正常状态的; = null 则不限制 */
	public void setStatus(String status) {
		param.status = ParamUtil.safeParseIntegerWithNull(status, null);
	}
	
	/** 要获取的类型, 缺省 = null 表示不限制 */
	public void setNewsType(String newsType) {
		param.newsType = ParamUtil.safeParseIntegerWithNull(newsType, null);
	}

	/** 是否要求有图片, 缺省 = null 表示不限制 */
	public void setHasPicture(String val) {
		param.hasPicture = ParamUtil.safeParseBooleanWithNull(val, null);
	}

	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.data.AbstractDataBean#doOpen()
	 */
	@Override
	protected Object doOpen() {
		// 得到查询参数和分页选项.
		SiteNewsQueryParam param = contextQueryParam(getDataHost());
		
		Pager pager = getUsePager() ? getContextPager(getDataHost()) : null;
		DataTable news_list = subj_svc.getSiteNewsDataTable(param, pager);
		
		return news_list;
	}

	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.data.AbstractDataBean#doPrepareData(cn.edustar.jitar.data.DataHost)
	 */
	@Override
	protected void doPrepareData(DataHost host) {
		// 得到查询参数和分页选项.
		SiteNewsQueryParam param = contextQueryParam(host);
		
		Pager pager = getUsePager() ? getContextPager(host) : null;
		DataTable news_list = subj_svc.getSiteNewsDataTable(param, pager);
		
		host.setData(getVarName(), news_list);
		if (getUsePager())
			host.setData(getPagerName(), pager);
	}

	/**
	 * 得到指定环境下的查询参数.
	 * @param host
	 * @return
	 */
	protected SiteNewsQueryParam contextQueryParam(DataHost host) {
		SiteNewsQueryParam param = getQueryParam();
		Subject subject = (Subject)host.getContextObject("subject");
		if (subject != null)
			param.subjectId = subject.getSubjectId();
		
		User user = (User)host.getContextObject("user");
		if (user != null)
			param.userId = user.getUserId();
		return param;
	}
}
