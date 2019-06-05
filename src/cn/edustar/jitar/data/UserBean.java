package cn.edustar.jitar.data;

import java.util.List;

import cn.edustar.data.Pager;
import cn.edustar.jitar.JitarContext;
import cn.edustar.jitar.pojos.User;
import cn.edustar.jitar.service.UserQueryParam;
import cn.edustar.jitar.service.UserService;
import cn.edustar.jitar.util.ParamUtil;

/**
 * 用户访问基本 bean, 缺省 varName = 'user_list' .
 *
 *
 */
public class UserBean extends AbstractPageDataBean {
	/** 用户查询对象 */
	protected UserQueryParam param = newUserQueryParam();

	/** 用户服务 */
	private UserService user_svc;

	/**
	 * 构造.
	 */
	public UserBean() {
		super.setVarName("user_list");
		super.setItemName("用户");
		super.setItemUnit("个");
		this.user_svc = JitarContext.getCurrentJitarContext().getUserService();
	}

	/** 用户服务 */
	public void setUserService(UserService user_svc) {
		this.user_svc = user_svc;
	}

	/**
	 * 返回新构造的 UserQueryParam.
	 * @return
	 */
	protected UserQueryParam newUserQueryParam() {
		return new UserQueryParam();
	}
	
	/** 用户查询对象 */
	public UserQueryParam getQueryParam() {
		return this.param;
	}

	/** 要查询的用户最多数量, 缺省 = 10; 只在未指定 Pager 的时候生效 */
	public void setCount(int count) {
		param.count = count;
	}
	
	/** 要查询的用户类型，如名师、教研员 */
	public void setUserTypeId(Integer userTypeId) {
		param.userTypeId = userTypeId;
	}
	
	/** 要查询的用户标识, 缺省 = null 表示不限定. */
	public void setUserId(String userId) {
		param.userId = ParamUtil.safeParseIntegerWithNull(userId, null);
	}
	
	/** 要查询的用户登录名, 缺省 = null 表示不限定. */
	public void setLoginName(String loginName) {
		param.loginName = loginName;
	}
	
	/** 要查询的用户的所属分类, 缺省 = null, 必须和 useUserClassId 结合使用 */
	public void setUserClassId(String userClassId) {
		param.userClassId = ParamUtil.safeParseIntegerWithNull(userClassId, null);
	}
	
	/** 是否限定用户分类, 缺省 = false */
	public void setUseUserClassId(String useUserClassId) {
		param.useUserClassId = ParamUtil.safeParseBoolean(useUserClassId, false);
	}
	
	/** 要查询的用户所属学科标识 */
	public void setSubjectId(String subjectId) {
		param.subjectId = ParamUtil.safeParseIntegerWithNull(subjectId, null);
	}
	
	/** 是否限定 subjectId 条件, 缺省 = false 表示不限制 */
	public void setUseSubjectId(String val) {
		param.useSubjectId = ParamUtil.safeParseBoolean(val, false);
	}
	
	/** 排序方式, 默认 = 0 表示按照 id 逆序排列, 1 - visitCount DESC, 2 - articleCount DESC,
	 * 3 - userScore desc, 4 - resourceCount DESC */
	public void setOrderType(int orderType) {
		param.orderType = orderType;
	}

	
	/** 多少天内创建的用户, 缺省 = null 表示不限制, 否则表示自当前日期算起向前推的天数, 例如 0.5 表示 12小时. */
	public void setDaysFromCreate(String fromCreate) {
		param.daysFromCreate = ParamUtil.safeParseDoubleWithNull(fromCreate, null);
	}
	
	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.data.AbstractDataBean#doPrepareData(cn.edustar.jitar.data.DataHost)
	 */
	@Override
	protected void doPrepareData(DataHost host) {
		// 得到查询参数和分页选项.
		UserQueryParam param = getQueryParam();
		Pager pager = getUsePager() ? getContextPager(host) : null;
		List<User> user_list = user_svc.getUserList(param, pager);
		
		host.setData(getVarName(), user_list);
		if (getUsePager())
			host.setData(getPagerName(), pager);
	}
}
