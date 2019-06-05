package cn.edustar.jitar.service;

import cn.edustar.data.hql.QueryHelper;
import cn.edustar.jitar.model.ObjectType;

/**
 * 链接查询参数.
 *
 *
 */
public class LinkQueryParam implements QueryParam {
	/** 按照群组识组查询, 暂时保留下来兼容原来的代码 */
	public Integer groupId;
	
	/** 限定所属对象类型 */
	public Integer objectType = null;
	
	/** 限定所属对象标识 */
	public Integer objectId = null;
	
	/** 排序方法，为1按创建时间 逆序排列。 */
	public static final int ORDER_TYPE_CREATEDATE_DESC = 1;
	
	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.service.QueryParam#createQuery()
	 */
	public QueryHelper createQuery() {
		QueryHelper query = new QueryHelper();
		query.fromClause = "FROM Link lk";
		
		if (this.groupId != null) {
			query.addAndWhere("lk.objectType = :objectType AND lk.objectId = :objectId");
			query.setInteger("objectType", ObjectType.OBJECT_TYPE_GROUP.getTypeId());
			query.setInteger("objectId", this.groupId);
		} else {
			if (this.objectType != null) {
				query.addAndWhere("lk.objectType = :objectType");
				query.setInteger("objectType", this.objectType);
			}
			if (this.objectId != null) {
				query.addAndWhere("lk.objectId = :objectId");
				query.setInteger("objectId", this.objectId);
			}
		}
		
		return query;
	}
}
