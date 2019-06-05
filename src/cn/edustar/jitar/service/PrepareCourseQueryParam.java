package cn.edustar.jitar.service;
import cn.edustar.data.hql.QueryHelper;

public class PrepareCourseQueryParam implements QueryParam {

	public QueryHelper createQuery() {
		QueryHelper query = new QueryHelper();
		query.fromClause = "FROM PrepareCourse pc";
		
		
		
		return query;
	}
}
