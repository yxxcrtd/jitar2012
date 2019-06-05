package cn.edustar.jitar.service;

import cn.edustar.data.hql.QueryHelper;

/**
 * 'Admin'用户查询参数,作用是增强用户表的关联.
 * 
 * @author Yang Xinxin
 * @version 1.0.0 Jul 3, 2008 10:10:47 AM
 */
public class AdminUserQueryParam extends UserQueryParam implements PrivilegeSupportQueryParam {

	/** 用户管理中的投影查询字段 */
	public String selectFields = "u.userId, u.loginName, u.nickName, u.email, u.blogName, u.userIcon, " +
			"u.unitId, u.subjectId, u.gradeId, u.userStatus, " +
			"subj.subjectName,  grad.gradeName, unit.unitName";

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.edustar.jitar.service.UserQueryParam#createQuery()
	 */
	public QueryHelper createQuery() {
		QueryHelper query = super.createQuery();
		query.selectClause = "SELECT " + this.selectFields;
		query.fromClause = "FROM User u LEFT JOIN u.subject subj LEFT JOIN u.grade grad LEFT JOIN u.unit unit";
		return query;
	}

	public void addCityCriteria(Integer cityId) {
		// TODO: 支持地市级别.
		throw new java.lang.UnsupportedOperationException();
	}


	public void addGradeCriteria(Integer gradeId) {
		this.gradeId = gradeId;
	}

	public void addProvinceCriteria(Integer provinceId) {
		// TODO: 支持地市级别.
		throw new java.lang.UnsupportedOperationException();
	}

	public void addSubjectCriteria(Integer subjectId) {
		this.subjectId = subjectId;
		this.useSubjectId = true;
	}

	public void addUnitCriteria(Integer unitId) {
		this.unitId = unitId;
	}

	public void denyCrtieria() {
		// 没有标识为 -1 的用户, 因此也就查询不出任何内容.
		this.userId = -1;
	}

	public boolean isModeSupport(int mode) {
		return true;
	}
}
