package cn.edustar.jitar.service;

import cn.edustar.data.hql.QueryHelper;

/**
 * 可支持查询参数的一般性接口.
 * 
 *
 */
public interface QueryParam {

	/**
	 * 通过此条件创建出对应查询.
	 * 
	 * @return
	 */
	public QueryHelper createQuery();

}
