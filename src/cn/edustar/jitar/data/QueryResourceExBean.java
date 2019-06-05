package cn.edustar.jitar.data;

import cn.edustar.data.DataTable;
import cn.edustar.data.Pager;
import cn.edustar.data.hql.QueryHelper;
import cn.edustar.jitar.service.ResourceQueryParam;
import cn.edustar.jitar.service.UserResourceQueryParam;

/**
 * 查询资源, 将连接多个表进行查询.
 *
 *
 */
public class QueryResourceExBean extends QueryResourceBean {
	/**
	 * 构造.
	 */
	public QueryResourceExBean() {
		
	}
	
	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.data.ResourceBean#newResourceQueryParam()
	 */
	protected ResourceQueryParam newResourceQueryParam() {
		CustomQueryParam p = new CustomQueryParam();
		p.selectFields = "r.resourceId, r.title, r.tags, r.createDate, r.fsize as fsize, r.href, " +
			"u.userId, u.nickName, u.loginName, subj.subjectName, grad.gradeName, rt.tcTitle ";
		return p;
	}
	
	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.data.QueryResourceBean#doPrepareData(cn.edustar.jitar.data.DataHost)
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
		
		// 得到查询参数和分页参数, 查询数据.
		UserResourceQueryParam ur_param = contextQueryParam(host);
		Pager pager = getUsePager() ? getContextPager(host) : null;
		DataTable resource_list = res_svc.getResourceDataTable(ur_param, pager);
		
		// 设置到环境中.
		host.setData(getVarName(), resource_list);
		if (getUsePager())
			host.setData(getPagerName(), pager);
	}
	
	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.data.ResourceBean#contextQueryParam(cn.edustar.jitar.data.DataHost)
	 */
	protected UserResourceQueryParam contextQueryParam(DataHost host) {
		UserResourceQueryParam param = (UserResourceQueryParam)super.contextQueryParam(host);
		
				
		return param;
	}

	/**
	 * 更改了 fromClause 的查询.
	 */
	public static class CustomQueryParam extends UserResourceQueryParam {
		/*
		 * (non-Javadoc)
		 * @see cn.edustar.jitar.service.UserResourceQueryParam#createQuery()
		 */
		public QueryHelper createQuery() {
			QueryHelper query = super.createQuery();
			// 添加 Subject, Grade, ResType 表关联.
			query.fromClause = "FROM User u, Resource r LEFT JOIN r.subject subj LEFT JOIN r.grade grad LEFT JOIN r.resType rt ";
			
			return query;
		}
	}
}
