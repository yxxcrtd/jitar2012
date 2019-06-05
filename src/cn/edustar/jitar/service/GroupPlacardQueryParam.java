package cn.edustar.jitar.service;

import cn.edustar.data.hql.QueryHelper;
import cn.edustar.jitar.model.ObjectType;

/**
 * 群组公告查询条件, 用于 groupService.getGroupPlacardDataTable() 方法.
 *
 *
 */
public class GroupPlacardQueryParam implements QueryParam {
	/** 缺省获得的公告数量. */
	public int count = 10;

	/** 要获取的公告的所属群组标识, 缺省 = null 表示不限制 */
	public Integer groupId;
	
	/** 要获取的公告的发布者标识, 缺省 = null 表示不限制 */
	public Integer userId;
	
	/** 是否获取隐藏的公告, 缺省 = false; = null 表示不限制; = true 表示获取隐藏的公告 */
	public Boolean isHide = Boolean.FALSE;
	
	/** 排序方式 */
	public int orderType = 0;
	
	/** 排序方式 = 0 按照 id 逆序排列 */
	public static final int ORDER_TYPE_ID_DESC = 0;
	
	/** 要获取的字段列表, 支持从 group(g), user(u), placard(pla) 获取字段 */
	public String selectFields = GroupService.GROUP_PLACARD_FIELDS;
	
	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.service.QueryParam#createQuery()
	 */
	public QueryHelper createQuery() {
		QueryHelper query = new QueryHelper();
		query.selectClause = "SELECT " + this.selectFields;
		query.fromClause = "FROM Placard pla, Group g, User u";
		query.addAndWhere("pla.objType = " + ObjectType.OBJECT_TYPE_GROUP.getTypeId());
		query.addAndWhere("pla.objId = g.groupId");
		query.addAndWhere("pla.userId = u.userId");
		
		// 条件.
		if (this.groupId != null)
			query.addAndWhere("g.groupId = " + this.groupId);
		if (this.userId != null)
			query.addAndWhere("u.userId = " + this.userId);
		if (this.isHide != null)
			query.addAndWhere("pla.hide = " + this.isHide);
		
		// 排序.
		switch (this.orderType) {
		case ORDER_TYPE_ID_DESC:
			query.addOrder("pla.id DESC"); break;
		}
		
		return query;
	}
}
