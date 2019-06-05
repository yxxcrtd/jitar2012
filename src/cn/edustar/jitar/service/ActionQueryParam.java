package cn.edustar.jitar.service;

import cn.edustar.data.hql.QueryHelper;

public class ActionQueryParam  implements QueryParam {

	/**
	 * 默认查询条数
	 */
	public int count = 10;
	public Integer ownerId = null;
	public String ownerType = null;
	public Integer createUserId = null;
	public Integer status = null;
	public Integer visibility = null;
	
	/** 
     * 排序方式 
	 * 排序方法，为1按发表时间，
	 */
	public int orderType = 1;
	public static final int ORDER_TYPE_CREATEDATE_DESC = 1;
	
	
	public QueryHelper createQuery() {
		QueryHelper query = new QueryHelper();
		query.fromClause = "FROM Action act";

		if (this.ownerId != null)
			query.addAndWhere("act.ownerId = " + this.ownerId);
		

		if (this.createUserId != null)
			query.addAndWhere("act.createUserId = " + this.createUserId);
		

		if (this.ownerType != null)
			query.addAndWhere("act.ownerType = " + this.ownerType);

		if(this.status != null) {
			query.addAndWhere("act.status = " + this.status);
		}
		// 是否删除.
		if(this.visibility != null) {
			query.addAndWhere("act.visibility = " + this.visibility);
		}
		
		// 排序.
		switch (this.orderType) {
		case 1:
			query.addOrder("act.createDate DESC");
			break;
		}
		return query;
		
	}
}
