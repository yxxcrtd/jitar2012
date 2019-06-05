package cn.edustar.jitar.service;

import cn.edustar.data.hql.QueryHelper;

/**
 * 支持特定用户群发布的资源的查询参数, 同时也支持得到 学科、学段等 信息.
 *
 *
 */
public class UserResourceQueryParam extends ResourceQueryParam {
	public UserResourceQueryParam() {
		
	}
	
	/** 用户所属城市标识(当前未使用) */
	public Integer userCityId;
	
	
	/** 用户所属机构标识, 缺省 = null 表示不限制 */
	public Integer userUnitId;
	
	/** 要选择的字段, 缺省仅选择很少的一部分字段 */
	public String selectFields = "r.resourceId, r.title, r.tags, r.createDate, r.fsize as fsize, " +
		"u.userId, u.nickName, u.loginName";

	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.service.ResourceQueryParam#createQuery()
	 */
	@Override
	public QueryHelper createQuery() {
		QueryHelper query = super.createQuery();
		
		query.selectClause = "SELECT " + this.selectFields;
		query.fromClause = "FROM Resource r, User u";
		query.addAndWhere("r.userId = u.userId");		
		
		if (this.userUnitId != null)
			query.addAndWhere("u.unitId = " + this.userUnitId);

		return query;
	}
}
