package cn.edustar.jitar.query;

import cn.edustar.data.hql.QueryHelper;
import cn.edustar.jitar.service.QueryParam;

/**
 * 视频查询参数
 *
 * @author Yang xinxin
 * @version 1.0.0 Apr 23, 2009 2:50:23 PM
 */
public class VideoQueryParam implements QueryParam {
	
	/** 数量 */
	public int count = 20;

	/** 要查询的用户标识, 缺省 = null 表示不限定. */
	public Integer userId = null;
	
	/** 标题中包含此关键字的, 使用 LIKE 在数据库中进行检索 */
	public String k = null;

	public String f = null;
	
	/** 审核状态 ,默认-1 显示全部*/
   public short auditState = -1; 
   
	/** 是否提取所属群组分类信息; 缺省 = false 表示不提取; 仅在 getGroupVideoList() 时候有效. */
	public boolean retrieveGroupCategory = false;	
	
	/** 群组资源分类id, 缺省为 null 表示不限定群组分类 */
	public Integer groupCateId = null;
	
	/* (non-Javadoc)
	 *
	 * @see cn.edustar.jitar.service.QueryParam#createQuery()
	 */
	public QueryHelper createQuery() {
		QueryHelper query = new QueryHelper();

		query.fromClause = "FROM Video v";
		query.addOrder("v.createDate DESC");
		
		if(f==null)
		{
			if (k != null && k.length() > 0) {
				query.addAndWhere("(v.userId LIKE :likeKey) OR (v.title LIKE :likeKey)");
				query.setString("likeKey", "%" + k +"%");
			}
		}
		else
		{
			if(f.equals("0") || f=="0"){//视频标题
				if (k != null && k.length() > 0) {
					query.addAndWhere("(v.title LIKE :likeKey)");
					query.setString("likeKey", "%" + k +"%");
				}
			}
			if((f.equals("1"))|| (f=="1")){//视频分类
				if (k != null && k.length() > 0) {
					query.addAndWhere("(v.staple LIKE :likeKey)");
					query.setString("likeKey", "%" + k +"%");
				}
			}
			if((f.equals("2")) || (f=="2")){//上传用户
				if (k != null && k.length() > 0) {
					query.addAndWhere("(v.userId IN(Select u.userId from User u Where (u.trueName Like :likeKey)))");
					query.setString("likeKey", "%" + k +"%");
				}
				
			}
		}
		if (userId != null) {
			query.addAndWhere("v.userId = :userId");
			query.setInteger("userId", userId);
		}
		
		if(auditState!=-1){
			query.addAndWhere("v.auditState = :auditState");
			query.setInteger("auditState", auditState);
		}

		// 群组ID.
		if (this.groupCateId != null) {
			query.addAndWhere("gv.groupCateId = " + this.groupCateId);
		}
		return query;
	}

}
