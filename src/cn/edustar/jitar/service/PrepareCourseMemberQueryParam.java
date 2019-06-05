package cn.edustar.jitar.service;

import cn.edustar.data.hql.QueryHelper;

public class PrepareCourseMemberQueryParam implements QueryParam {
	
	public int count = 10;
	public Integer prepareCourseId = null;
	public Integer userId = null;
	public int orderType = 1;
	
	public static final int ORDER_BY_PREPARECOURSEMEMBERID =1;
	
	public QueryHelper createQuery() {
		QueryHelper query = new QueryHelper();
		query.fromClause = "FROM PrepareCourseMember pcm";
		
		if (this.prepareCourseId != null)
			query.addAndWhere("pcm.prepareCourseId = " + this.prepareCourseId);
		if (this.userId != null)
			query.addAndWhere("pcm.userId = " + this.userId);
				
		switch(orderType)
		{
		case ORDER_BY_PREPARECOURSEMEMBERID:
			query.addOrder("pcm.prepareCourseMemberId DESC");
			break;		
		}
		
		return query;
	}
}
