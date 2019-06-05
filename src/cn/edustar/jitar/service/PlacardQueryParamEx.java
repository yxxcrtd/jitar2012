package cn.edustar.jitar.service;

import cn.edustar.data.hql.QueryHelper;

/**
 * 课题组公告，继承公告查询参数
 * @author baimindong
 *
 */
public class PlacardQueryParamEx extends PlacardQueryParam {
	/** 课题组公告  */
	private Integer groupCateId = null;

	/** 查询字段部分 例如 Select AAA,BBB  */
	private String select_Clause = null;
	
	public QueryHelper createQuery() {
		QueryHelper query = super.createQuery();
		if (this.groupCateId != null){
			query.fromClause = "FROM Placard pla,Group g";
			query.addAndWhere(" g.groupId = pla.objId ");
			query.addAndWhere(" g.categoryId = " + this.groupCateId);
		}
		
		if(null != this.select_Clause && this.select_Clause.length() > 0){
			query.selectClause = this.select_Clause; 
		}
		
		return query;
	}
	
	public Integer getGroupCateId() {
		return groupCateId;
	}

	public void setGroupCateId(Integer groupCateId) {
		this.groupCateId = groupCateId;
	}

	public String getSelect_Clause() {
		return select_Clause;
	}

	public void setSelect_Clause(String select_Clause) {
		this.select_Clause = select_Clause;
	}	
}
