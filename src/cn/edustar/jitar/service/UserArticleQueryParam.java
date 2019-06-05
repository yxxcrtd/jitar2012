package cn.edustar.jitar.service;

import cn.edustar.data.hql.QueryHelper;

/**
 * 特定用户群的文章查询参数, 用于 Article 后台管理.
 *
 *
 */
public class UserArticleQueryParam extends ArticleQueryParam implements PrivilegeSupportQueryParam {
	/** 是否应该拒绝此次查询 */
	public boolean isDenyCriteria = false;
	
	/** 用户所属城市标识(当前未使用) */
	public Integer userCityId;
	
	
	/** 用户所属机构标识, 缺省 = null 表示不限制 */
	public Integer userUnitId;
	
	/** 要选择的字段, 缺省仅选择很少的一部分字段 */
	public String selectFields = "a.articleId, a.title, a.articleTags, a.createDate, " +
		"u.userId, u.nickName, u.loginName";
	
	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.service.ArticleQueryParam#createQuery()
	 */
	@Override
	public QueryHelper createQuery() {
		QueryHelper query = super.createQuery();
		
		query.selectClause = "SELECT " + this.selectFields;
		query.fromClause = "FROM Article a, User u";
		query.addAndWhere("a.userId = u.userId");
		
		if (this.userUnitId != null)
			query.addAndWhere("u.unitId = " + this.userUnitId);

		return query;
	}

	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.service.PrivilegeSupportQueryParam#addCityCriteria(java.lang.Integer)
	 */
	public void addCityCriteria(Integer cityId) {
		this.userCityId = cityId;
	}

	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.service.PrivilegeSupportQueryParam#addGradeCriteria(java.lang.Integer)
	 */
	public void addGradeCriteria(Integer gradeId) {
		// TODO: 当前暂时不支持.
	}

	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.service.PrivilegeSupportQueryParam#addProvinceCriteria(java.lang.Integer)
	 */
	public void addProvinceCriteria(Integer provinceId) {
		// TODO: 当前暂时不支持.
	}

	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.service.PrivilegeSupportQueryParam#addSubjectCriteria(java.lang.Integer)
	 */
	public void addSubjectCriteria(Integer subjectId) {
		this.useSubjectId = true;
		this.subjectId = subjectId;
	}

	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.service.PrivilegeSupportQueryParam#addUnitCriteria(java.lang.Integer)
	 */
	public void addUnitCriteria(Integer unitId) {
		this.userUnitId = unitId;
	}

	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.service.PrivilegeSupportQueryParam#denyCrtieria()
	 */
	public void denyCrtieria() {
		this.isDenyCriteria = true;
	}

	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.service.PrivilegeSupportQueryParam#isModeSupport(int)
	 */
	public boolean isModeSupport(int mode) {
		return true;
	}
}
