package cn.edustar.jitar.data;

import java.util.List;

import cn.edustar.data.Pager;
import cn.edustar.jitar.JitarContext;
import cn.edustar.jitar.model.ResourceModelEx;
import cn.edustar.jitar.pojos.Category;
import cn.edustar.jitar.pojos.Subject;
import cn.edustar.jitar.pojos.User;
import cn.edustar.jitar.service.ResourceQueryParam;
import cn.edustar.jitar.service.ResourceService;
import cn.edustar.jitar.util.ParamUtil;

/**
 * 资源获取基本数据对象, 缺省 varName = 'resource_list'.
 *
 *
 */
public class ResourceBean extends AbstractPageDataBean {
	/** 资源服务 */
	protected ResourceService res_svc;
	
	/** 查询参数 */
	protected ResourceQueryParam param = newResourceQueryParam();
	
	/**
	 * 构造.
	 */
	public ResourceBean() {
		super.setVarName("resource_list");
		super.setItemName("资源");
		super.setItemUnit("个");
		this.res_svc = JitarContext.getCurrentJitarContext().getResourceService();
	}
	
	/**
	 * 构造 ResourceQueryParam.
	 * @return
	 */
	protected ResourceQueryParam newResourceQueryParam() {
		return new ResourceQueryParam();
	}
	
	/** 如果给出 userId, 则表示查询该用户的文章；否则不区分用户。缺省 = null */
	public void setUserId(String userId) {
		param.userId = ParamUtil.safeParseIntegerWithNull(userId, null);
	}

	/** 系统分类id，缺省为 null 则表示不限定系统分类。 */
	public void setSysCateId(String sysCateId) {
		param.sysCateId = ParamUtil.safeParseIntegerWithNull(sysCateId, null);
	}
	
	/** 用户分类id, 缺省为 null 表示不限定用户分类，通常同时设定 userId 参数。 */
	public void setUserCateId(String userCateId) {
		param.userCateId = ParamUtil.safeParseIntegerWithNull(userCateId, null);
	}
	
	/** 查询审核状态，== null 表示不区分，缺省 = 0 查询审核通过的。 */
	public void setAuditState(String auditState) {
		param.auditState = ParamUtil.safeParseIntegerWithNull(auditState, null);
	}
	
	/** (请使用ORDER_TYPE_XXX 常量) 排序方法，为1按发表时间，为2按点击数，为3按回复数。缺省 = 1
	 * (派生类可能定义更多排序方法.) */
	public void setOrderType(int orderType) {
		param.orderType = orderType;
	}
	
	/** 是否被删除, 缺省 = false 表示选择未删除的; = true 表示获得删除的; = null 表示不限制此条件 */
	public void setDelState(String delState) {
		param.delState = ParamUtil.safeParseBooleanWithNull(delState, null);
	}
	
	/** 是否推荐, 缺省 = null 表示不限制 */
	public void setRecommendState(String recommendState) {
		param.recommendState = ParamUtil.safeParseBooleanWithNull(recommendState, null);
	}

	/** 获得条数，缺省 = 10。此条件仅当未指定分页参数时生效。 */
	public void setCount(int count) {
		param.count = count;
	}
	
	/** 是否提取系统分类信息; 缺省 = false 表示不提取; true 表示提取 */
	public void setRetrieveSystemCategory(boolean retrieveSystemCategory) {
		param.retrieveSystemCategory = retrieveSystemCategory;
	}
	
	/** 是否提取用户分类信息; 缺省 = false 表示不提取; true 表示提取 */
	public void setRetrieveUserCategory(boolean val) {
		param.retrieveUserCategory = val;
	}
	
	/** 是否提取所属群组分类信息; 缺省 = false 表示不提取; 仅在 getGroupResourceList() 时候有效. */
	// public boolean retrieveGroupCategory = false;
	
	/** 限定的学科标识, 必须设置 useSubjectId 才生效. */
	public void setSubjectId(String subjectId) {
		param.subjectId = ParamUtil.safeParseIntegerWithNull(subjectId, null);
	}
	
	/** 是否使用 subjectId 限制参数, 缺省 = false. */
	public void setUseSubjectId(String useSubjectId) {
		param.useSubjectId = ParamUtil.safeParseBoolean(useSubjectId, false);
	}

	/** 资源服务 */
	public void setResourceService(ResourceService res_svc) {
		this.res_svc = res_svc;
	}

	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.data.AbstractDataBean#doPrepareData(cn.edustar.jitar.data.DataHost)
	 */
	@Override
	protected void doPrepareData(DataHost host) {
		// 得到查询参数和分页参数, 查询数据.
		ResourceQueryParam param = contextQueryParam(host);
		Pager pager = getUsePager() ? getContextPager(host) : null;
		List<ResourceModelEx> resource_list = res_svc.getResourceList(param, pager);
		
		// 设置到环境中.
		host.setData(getVarName(), resource_list);
		if (getUsePager())
			host.setData(getPagerName(), pager);
	}

	/**
	 * 得到与环境绑定的查询参数.
	 * @param host
	 * @return
	 */
	protected ResourceQueryParam contextQueryParam(DataHost host) {
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
	
	/**
	 * 得到资源查询参数, 不和环境绑定相关.
	 * @return
	 */
	public ResourceQueryParam getQueryParam() {
		return this.param;
	}
}
