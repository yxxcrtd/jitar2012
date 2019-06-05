package cn.edustar.jitar.service;

import cn.edustar.data.hql.QueryHelper;
import cn.edustar.jitar.dao.GroupDao;
import cn.edustar.jitar.pojos.Group;
import cn.edustar.jitar.pojos.GroupMember;

/**
 * 群组成员查询对象.
 *
 *
 */
public class GroupMemberQueryParam implements QueryParam {
	/** 查询的记录数量, 一般在未指定 pager 的时候使用 */
	public int count = 10;
	
	/** getGroupMemberList() 方法返回的字段, 缺省包括用户和群组成员信息 */
	public String fieldList = GroupDao.GROUP_USER_LIST_FIELDS;
	
	/** 查找该组的成员, 缺省 = null 表示不限定 */
	public Integer groupId = null;
	
	/** 要查找的用户标识, 缺省 = null 表示不限定 */
	public Integer userId = null;
	
	/** 要查找的成员状态, 缺省 = null 表示不限制 */
	public Integer memberStatus = null;
	
	/** 要查找的成员角色, 缺省 = null 表示不限制 */
	public Integer groupRole = null;
	
	/** 是否要查找管理员, 包括正管理员和副管理员, 缺省 = null 表示不限制, 一般不和 groupRole 条件合用 */
	public Boolean groupManager = null;
	
	/** 是否要保证协作组可以向其发布内容, 其表示 group.groupState == 0 or group.groupState == hide */
	public Boolean groupCanPubTo = null;
	
	/** 排序方式, 缺省 = 0 表示按照 gm.id 逆序排列 */
	public int orderType = 0;
	
	/** 排序方式, = 0 表示按照 gm.id 逆序排列 */
	public static final int ORDER_TYPE_GMID_DESC = 0;
	/** 排序方式, = 1 表示按照 gm.id 正序排列 */
	public static final int ORDER_TYPE_GMID_ASC = 1;
	/** 排序方式, = 2 表示按照 gm.articleCount 逆序排列 */
	public static final int ORDER_TYPE_ARTICLECOUNT_DESC = 2;
	
	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.service.QueryParam#createQuery()
	 */
	public QueryHelper createQuery() {
		QueryHelper query = new QueryHelper();
		
		query.selectClause = "SELECT " + fieldList;
		query.fromClause = " FROM GroupMember gm, User u, Group g ";
		query.whereClause = " WHERE gm.userId = u.userId " +
			"   AND g.groupId = gm.groupId ";
		
		if (this.groupId != null)
			query.addAndWhere("gm.groupId = " + this.groupId);
		if (this.userId != null)
			query.addAndWhere("gm.userId = " + this.userId);
		if (this.memberStatus != null)
			query.addAndWhere("gm.status = " + this.memberStatus);
		if (this.groupRole != null)
			query.addAndWhere("gm.groupRole = " + this.groupRole);
		if (this.groupManager != null) {
			if (this.groupManager.booleanValue() == false)
				query.addAndWhere("gm.groupRole = " + GroupMember.GROUP_ROLE_MEMBER);
			else
				query.addAndWhere("gm.groupRole >= " + GroupMember.GROUP_ROLE_VICE_MANAGER);
		}
		if (this.groupCanPubTo != null) {
			if (this.groupCanPubTo.booleanValue() == true)
				query.addAndWhere("g.groupState = " + Group.GROUP_STATE_NORMAL + 
						" OR g.groupState = " + Group.GROUP_STATE_HIDED);
		}

		// 排序.
		switch (this.orderType) {
		case ORDER_TYPE_GMID_DESC:
			query.addOrder("gm.id DESC"); break;
		case ORDER_TYPE_GMID_ASC:
			query.addOrder("gm.id ASC"); break;
		case ORDER_TYPE_ARTICLECOUNT_DESC:
			query.addOrder("gm.articleCount DESC"); break;
		}
		
		return query;
	}

}
