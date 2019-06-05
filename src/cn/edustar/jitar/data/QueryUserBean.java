package cn.edustar.jitar.data;

import cn.edustar.jitar.pojos.Category;
import cn.edustar.jitar.pojos.Subject;
import cn.edustar.jitar.pojos.User;
import cn.edustar.jitar.service.UserQueryParam;

/**
 * 通过查询条件获得用户信息的 bean, 
 * 	支持通过 contextObject 设置 userId, subjectId, userClassId 条件,
 *  缺省 varName = 'user_list'
 *
 *
 */
public class QueryUserBean extends UserBean {
	/**
	 * 构造.
	 */
	public QueryUserBean() {
		super.setVarName("user_list");
		super.setUsePager(true);
		super.setPageSize(20);
	}
	
	/**
	 * 设置项目名.
	 */
	@Override
	public void setItemName(String itemName) {
		if ("wr".equals(itemName))
			super.setItemName("工作室");
	}
	
	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.data.UserBean#doPrepareData(cn.edustar.jitar.data.DataHost)
	 */
	@Override
	protected void doPrepareData(DataHost host) {
		// 得到查询参数.
		UserQueryParam param = contextQueryParam(host);
		
		String k = host.getParameters().safeGetStringParam("k");
		if (k != null && k.length() > 0)
			param.k = k;
		
		super.doPrepareData(host);
	}

	/**
	 * 得到和环境绑定的查询条件.
	 * @param host
	 * @return
	 */
	protected UserQueryParam contextQueryParam(DataHost host) {
		UserQueryParam param = getQueryParam();
		
		// 如果有学科对象则设置学科条件.
		Subject subject = (Subject)host.getContextObject("subject");
		if (subject != null) {
			param.useSubjectId = true;
			param.subjectId = subject.getSubjectId();
		}
		
		// 如果有用户对象则设置用户条件.
		User user = (User)host.getContextObject("user");
		if (user != null) {
			param.userId = user.getUserId();
		}
		
		// 如果有分类对象则设置系统分类条件.
		Category category = (Category)host.getContextObject("category");
		if (category != null) {
			param.useUserClassId = true;
			param.userClassId = category.getCategoryId();
		}
		
		return param;
	}
}
