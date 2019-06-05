package cn.edustar.jitar.data;

import cn.edustar.data.DataTable;
import cn.edustar.data.Pager;
import cn.edustar.jitar.JitarContext;
import cn.edustar.jitar.pojos.Group;
import cn.edustar.jitar.service.ArticleQueryParam;
import cn.edustar.jitar.service.GroupArticleQueryParam;
import cn.edustar.jitar.service.GroupService;
import cn.edustar.jitar.util.ParamUtil;

/**
 * 群组文章获取.
 *
 *
 */
public class GroupArticleBean extends ArticleBean {
	/** 群组服务 */
	private GroupService group_svc;
	
	/** 查询参数 */
	private GroupArticleQueryParam ga_param = new GroupArticleQueryParam();
	
	/**
	 * 构造.
	 */
	public GroupArticleBean() {
		this.group_svc = JitarContext.getCurrentJitarContext().getGroupService();
	}
	
	/** 要查询的文章的所属群组, 缺省 = null 表示不限制 */
	public void setGroupId(String groupId) {
		ga_param.groupId = ParamUtil.safeParseIntegerWithNull(groupId);
	}
	
	/** 文章标识, 缺省 = null 表示不限制 */
	public void setArticleId(String articleId) {
		ga_param.articleId = ParamUtil.safeParseIntegerWithNull(articleId);
	}

	/** 是否是群组精华文章, 缺省 = null 表示不限制 */
	public void setIsGroupBest(String isGroupBest) {
		ga_param.isGroupBest = ParamUtil.safeParseBooleanWithNull(isGroupBest, null);
	}

	/** 要查询的字段列表, 缺省有文章的部分属性和群组文章部分属性 */
	public void setSelectFields(String fields) {
		ga_param.selectFields = fields;
	}
	
	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.data.ArticleBean#doPrepareData(cn.edustar.jitar.data.DataHost)
	 */
	@Override
	protected void doPrepareData(DataHost host) {
		GroupArticleQueryParam param = this.getQueryParam2(host);
		Pager pager = getUsePager() ? getContextPager(host) : null; 
		DataTable article_list = group_svc.getGroupArticleDataTable(param, pager);
		
		// 将数据设置到环境中.
		host.setData(getVarName(), article_list);
		if (getUsePager())
			host.setData(getPagerName(), pager);
	}
	
	/**
	 * 得到查询条件.
	 * @param host
	 * @return
	 */
	protected GroupArticleQueryParam getQueryParam2(DataHost host) {
		Group group = (Group)host.getContextObject("group");
		if (group != null) {
			int groupId = group.getGroupId();
			this.ga_param.groupId = groupId;
		}
		
		ArticleQueryParam inner_param = super.getQueryParam(host);
		//临时注释 this.ga_param.articleQueryParam = inner_param;
		return this.ga_param;
	}
}
