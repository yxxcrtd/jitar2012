package cn.edustar.jitar.data;

import cn.edustar.jitar.service.ResourceQueryParam;

/**
 * 查询资源的 bean, 缺省 varName = 'resource_list'.
 *
 *
 */
public class QueryResourceBean extends ResourceBean {
	/**
	 * 构造.
	 */
	public QueryResourceBean() {
		super.setVarName("resource_list");
		super.setUsePager(true);
		super.setPageSize(20);
	}
	
	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.data.ResourceBean#doPrepareData(cn.edustar.jitar.data.DataHost)
	 */
	@Override
	protected void doPrepareData(DataHost host) {
		String list_type = "所有资源";
		
		// 类型.
		String type = host.getParameters().safeGetStringParam("type", "new");
		if (type == null || type.length() == 0) type = "new";
		if (type == null || type.length() == 0 || "new".equals(type))
			list_type = "最新资源";		// 最新, 缺省按照时间排序.
		else if ("hot".equals(type)) {	// 热门, 按照点击数排序.
			param.orderType = ResourceQueryParam.ORDER_TYPE_VIEWCOUNT_DESC;
			list_type = "热门资源";
		} else if ("rcmd".equals(type))	{	// 推荐.
			param.recommendState  = Boolean.TRUE;
			list_type = "推荐资源";
		} else if ("cmt".equals(type)) {	// 评论最多.
			param.orderType = ResourceQueryParam.ORDER_TYPE_COMMENTS_DESC;
			list_type = "评论最多资源";
		}
		
		// 有关键字则添加关键字条件.
		String k = host.getParameters().safeGetStringParam("k", null);
		if (k != null && k.length() > 0) {
			param.k = k;
			list_type = "查询关键字为 '" + k + "' 的资源";
		}
		host.setData("type", type);
		host.setData("list_type", list_type);

		super.doPrepareData(host);
	}
}
