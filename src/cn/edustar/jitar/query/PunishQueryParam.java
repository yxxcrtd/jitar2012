package cn.edustar.jitar.query;

import cn.edustar.data.hql.QueryHelper;
import cn.edustar.jitar.service.QueryParam;

/**
 * 视频查询参数
 *
 * @author Yang xinxin
 * @version 1.0.0 Apr 23, 2009 2:50:23 PM
 */
public class PunishQueryParam implements QueryParam {
	
	/** 数量 */
	public int count = 20;

	/** 要查询的用户标识, 缺省 = null 表示不限定. */
	public Integer userId = null;
	
	/** 标题中包含此关键字的, 使用 LIKE 在数据库中进行检索 */
	public String k = null;

	public String objType =null;
	/* (non-Javadoc)
	 *
	 * @see cn.edustar.jitar.service.QueryParam#createQuery()
	 */
	public QueryHelper createQuery() {
		QueryHelper query = new QueryHelper();

		query.fromClause = "FROM UPunishScore v";
		query.addOrder("v.punishDate DESC");
		
		if (objType != null && objType.length() > 0){
			if(Integer.parseInt(objType)>0){
				if (k != null && k.length() > 0) {
					query.addAndWhere(" (v.reason LIKE :likeKey) OR (v.objTitle LIKE :likeKey)");
					query.setString("likeKey", "%" + k +"%");
				}
				query.addAndWhere("v.objType = :objType");
				query.setString("objType", objType);
			}
			else if (objType.equals("-1")){
				//评判人
				query.addAndWhere("v.createUserName Like :userName");
				query.setString("userName", "%" + k +"%");
			}
			else if (objType.equals("-2")){
				//被评判人
				query.addAndWhere("v.userId IN (SELECT userId FROM cn.edustar.jitar.pojos.User WHERE trueName LIKE :userName)");
				query.setString("userName", "%" + k +"%");				
			}
		}else{
			if (k != null && k.length() > 0) {
				query.addAndWhere(" (v.reason LIKE :likeKey) OR (v.objTitle LIKE :likeKey)");
				query.setString("likeKey", "%" + k +"%");
			}
		}
		
		if (userId != null) {
			query.addAndWhere("v.userId = :userId");
			query.setInteger("userId", userId);
		}
		return query;
	}

}
