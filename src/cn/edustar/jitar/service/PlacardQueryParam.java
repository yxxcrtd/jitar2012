package cn.edustar.jitar.service;

import cn.edustar.data.hql.QueryHelper;
import cn.edustar.jitar.model.ObjectType;

/**
 * 查询公告使用的参数对象.
 *
 *
 */
public class PlacardQueryParam implements QueryParam {
	/** 要获取的记录数量, 一般在未指定 pager 参数时使用 */
	public int count = 10;
	
	/** 要查找的公告的对象类型, 缺省 = null */
	public ObjectType objType;
	
	/** 要查找的公告的所属对象标识, 缺省 = null 表示不限制 */
	public Integer objId;
	
	public String subjectIds="";
	
	/** 是否查询隐藏的, 缺省 = false 表示查询非隐藏的; = null 表示不限制; = true 表示查询隐藏的 */
	public Boolean hideState = Boolean.FALSE;

	/** 排序方式, 缺省 = 0 表示按照 id 逆序排列 */
	public int orderType = 0;

	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.service.QueryParam#createQuery()
	 */
	public QueryHelper createQuery() {
		QueryHelper query = new QueryHelper();
		
		query.fromClause = "FROM Placard pla";
		
		// 条件.
		// 限制条件.
		if ((this.subjectIds != null) && (this.subjectIds != ""))
		{
			String[] a=this.subjectIds.split(",");
			String sSQL="(";
			for(int i=0;i<a.length;i++)
			{
				if(i>0)
				{sSQL+=" Or ";}
				sSQL+="pla.objId = " + a[i];
			}
			sSQL+=")";
			query.addAndWhere(sSQL);
		}		
		if (this.objType != null) {
			query.addAndWhere("pla.objType = " + objType.getTypeId());
		}
		if (this.objId != null){
			query.addAndWhere("pla.objId = " + objId);
		}
		if (this.hideState != null){
			query.addAndWhere("pla.hide = " + this.hideState);
		}
		// 排序.
		switch (orderType) {
		case 0:
			query.addOrder("pla.id DESC");
			break;
		}
		return query;
	}
}
