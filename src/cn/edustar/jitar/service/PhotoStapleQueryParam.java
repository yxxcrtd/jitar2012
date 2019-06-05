package cn.edustar.jitar.service;

import cn.edustar.data.hql.QueryHelper;

/**
 * 相册分类查询参数
 * 
 * @author Yang XinXin
 * @version 1.0.0 Jul 15, 2008 11:34:08 AM
 */
public class PhotoStapleQueryParam implements QueryParam {

	/** 要查询的用户标识，必须给出。 */
	public int userId;

	/* 
	 * (non-Javadoc)
	 *
	 * @see cn.edustar.jitar.service.QueryParam#createQuery()
	 */
	public QueryHelper createQuery() {
		QueryHelper query = new QueryHelper();
		
		if (userId > 0) {
			query.addAndWhere("p.userId = " + this.userId );
		}
		
		return query;
	}

}
