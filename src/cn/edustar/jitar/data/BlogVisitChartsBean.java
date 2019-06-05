package cn.edustar.jitar.data;

import cn.edustar.jitar.service.UserQueryParam;

/**
 * 博客访问排行榜, 缺省 varName = 'blog_visit_charts'.
 *
 *
 */
public class BlogVisitChartsBean extends UserBean {
	public BlogVisitChartsBean() {
		super.setVarName("blog_visit_charts");
	}
	
	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.data.UserBean#getQueryParam()
	 */
	public UserQueryParam getQueryParam() {
		UserQueryParam param = super.getQueryParam();
		
		// 修改为按照访问数量进行排行.
		param.orderType = UserQueryParam.ORDER_TYPE_VISIT_DESC;
		return param;
	}
}
