package cn.edustar.jitar.data;

import java.util.List;

import cn.edustar.data.Pager;
import cn.edustar.jitar.JitarContext;
import cn.edustar.jitar.model.ArticleModelEx;
import cn.edustar.jitar.pojos.Category;
import cn.edustar.jitar.pojos.Subject;
import cn.edustar.jitar.pojos.User;
import cn.edustar.jitar.service.ArticleQueryParam;
import cn.edustar.jitar.service.ArticleService;
import cn.edustar.jitar.util.ParamUtil;

/**
 * 文章访问基础 bean, 缺省 varName = 'article_list'.
 *
 *
 */
public class ArticleBean extends AbstractPageDataBean {
	/** 查询参数 */
	protected ArticleQueryParam param = newArticleQueryParam();
	
	/** 文章服务 */
	protected ArticleService art_svc;
	
	/**
	 * 构造.
	 */
	public ArticleBean() {
		super.setVarName("article_list");
		super.setItemName("文章");
		super.setItemUnit("篇");
		this.art_svc = JitarContext.getCurrentJitarContext().getArticleService();
	}
	
	/**
	 * 构造一个新的查询参数.
	 * @return
	 */
	protected ArticleQueryParam newArticleQueryParam() {
		return new ArticleQueryParam();
	}

	/** 文章服务 */
	public void setArticleService(ArticleService art_svc) {
		this.art_svc = art_svc;
	}

	/** 如果给出 userId, 则表示查询该用户的文章；否则不区分用户。缺省 = null */
	public void setUserId(Object userId) {
		param.userId = ParamUtil.safeParseIntegerWithNull(userId);
	}
	
	/** 系统分类id，缺省为 null 则表示不限定系统分类。 */
	public void setSysCateId(Object sysCateId) {
		param.sysCateId = ParamUtil.safeParseIntegerWithNull(sysCateId);
	}
	
	/** 用户分类id, 缺省为 null 表示不限定用户分类，通常同时设定 userId 参数。 */
	public void setUserCateId(Object userCateId) {
		param.userCateId = ParamUtil.safeParseIntegerWithNull(userCateId);
	}
	
	/** 查询审核状态，== null 表示不区分，缺省 = 0 查询审核通过的。 */
	public void setAuditState(Object auditState) {
		param.auditState = ParamUtil.safeParseIntegerWithNull(auditState);
	}
	
	/** 获得文章条数，缺省 = 10。此条件仅当未指定分页参数时生效。 */
	public void setCount(int count) {
		param.count = count;
	}
	
	/** 是否精华，缺省为 null 表示所有文章; = true 表示获取精华的; = false 表示获取非精华的 */
	public void setBestType(String bestType) {
		param.bestType = ParamUtil.safeParseBooleanWithNull(bestType, null);
	}
	
	/** 查询隐藏状态，== null 表示不区分，缺省 = 0 查询非隐藏的。 */
	public void setHideState(String hideState) {
		param.hideState = ParamUtil.safeParseIntegerWithNull(hideState, null);
	}
	
	/** 查询草稿状态，== null 表示不区分，缺省 = false 查询非草稿的。 */
	public void setDraftState(String draftState) {
		param.draftState = ParamUtil.safeParseBooleanWithNull(draftState, null);
	}
	
	/** 查询推荐状态, 缺省 = null 表示不区分.  */
	public void setRecommendState(String recommendState) {
		param.recommendState = ParamUtil.safeParseBooleanWithNull(recommendState, null);
	}
	
	/** 删除状态，== null 表示不区分，缺省 = false 查询未删除的。  */
	public void setDelState(String delState) {
		param.delState = ParamUtil.safeParseBooleanWithNull(delState, null);
	}
	
	/** 是否提取系统分类信息; 缺省 = false 表示不提取; true 表示提取 */
	public void setRetrieveSystemCategory(String val) {
		param.retrieveSystemCategory = ParamUtil.safeParseBooleanWithNull(val, null);
	}
	
	/** 是否提取用户分类信息; 缺省 = false 表示不提取; true 表示提取 */
	public void setRetrieveUserCategory(String val) {
		param.retrieveUserCategory = ParamUtil.safeParseBooleanWithNull(val, null);
	}
	
	/** 所属学科标识, 缺省 = null. 必须设置 useSubjectId 才生效. */
	public void setSubjectId(String subjectId) {
		param.subjectId = ParamUtil.safeParseIntegerWithNull(subjectId, null);
	}
	
	/** 是否限定所属学科, 缺省 = false 不限定. */
	public void setUseSubjectId(String val) {
		param.useSubjectId = ParamUtil.safeParseBoolean(val, false);
	}

	/** 从创建开始算起到到现在的天数在指定范围内的, 单位: 天；缺省 = null 表示不限制. */
	public void setDaysFromCreate(String val) {
		param.daysFromCreate = ParamUtil.safeParseDoubleWithNull(val, null);
	}

	/** (请使用ORDER_TYPE_XXX 常量) 排序方法，为1按发表时间，为2按点击数，为3按回复数。缺省 = 1
	 * (派生类可能定义更多排序方法.) */
	public void setOrderType(int orderType) {
		param.orderType = orderType;
	}
	
	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.data.AbstractDataBean#doPrepareData(cn.edustar.jitar.data.DataHost)
	 */
	@Override
	protected void doPrepareData(DataHost host) {
		// 得到查询参数和分页选项.
		ArticleQueryParam param = getQueryParam(host);
		Pager pager = getUsePager() ? getContextPager(host) : null; 
		List<ArticleModelEx> article_list = art_svc.getArticleList(param, pager);
		
		
		// 将数据设置到环境中.
		host.setData(getVarName(), article_list);
		if (getUsePager())
			host.setData(getPagerName(), pager);
	}
	
	/**
	 * 得到查询参数, 参数可能根据 contextObject 进行了修正. 
	 */
	public ArticleQueryParam getQueryParam(DataHost host) {
		// 根据 contextObject 修正 QueryParam - 学科 subject.
		Subject subject = (Subject)host.getContextObject("subject");
		if (subject != null) {
			param.useSubjectId = true;
			param.subjectId = subject.getSubjectId();
		}
		
		// 分类 category.
		Category category = (Category)host.getContextObject("category");
		if (category != null) {
			param.sysCateId = category.getCategoryId();
		}

		// 用户 user.
		User user = (User)host.getContextObject("user");
		if (user != null) {
			param.userId = user.getUserId();
		}
		
		return this.param;
	}
}
