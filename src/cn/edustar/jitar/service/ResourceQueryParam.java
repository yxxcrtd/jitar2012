package cn.edustar.jitar.service;

import cn.edustar.data.hql.QueryHelper;
import cn.edustar.jitar.pojos.Resource;

/**
 * 资源查询参数.
 *
 *
 */
public class ResourceQueryParam extends DocumentQueryParam implements QueryParam {
	/** 获得条数，缺省 = 10。此条件仅当未指定分页参数时生效。 */
	public int count = 10;
	
	/** 是否被删除, 缺省 = false 表示选择未删除的; = true 表示获得删除的; = null 表示不限制此条件 */
	public Boolean delState = Boolean.FALSE;
	
	/** 是否推荐, 缺省 = null 表示不限制 */
	public Boolean recommendState = null; 
	
	/** 是否提取系统分类信息; 缺省 = false 表示不提取; true 表示提取 */
	public boolean retrieveSystemCategory = false;
	
	/** 是否提取用户分类信息; 缺省 = false 表示不提取; true 表示提取 */
	public boolean retrieveUserCategory = false;
	
	/** 资源类型Id. */
	public Integer resTypeId;
	
	/** 限定的资源共享模式, 缺省 = SHARE_MODEL_FULL 只获取完全共享的 ; == null 表示不限制. */
	public Integer shareMode = Resource.SHARE_MODE_FULL;
	
	/** 是否提取所属群组分类信息; 缺省 = false 表示不提取; 仅在 getGroupResourceList() 时候有效. */
	public boolean retrieveGroupCategory = false;
	
	/** 限定的学科标识, 必须设置 useSubjectId 才生效. */
	public Integer subjectId;
	
	/** 是否使用 subjectId 限制参数, 缺省 = false. */
	public boolean useSubjectId = false;
	
	/** 标题中包含此关键字的, 使用 LIKE 在数据库中进行检索 */
	public String k = null;
	
	/** 群组资源分类id, 缺省为 null 表示不限定群组资源分类 */
	public Integer groupCateId = null;
	
	/** 是否发布到了资源库中 **/
	public Boolean publishToZyk=null;
	/**
	 * 根据条件创建查询对象.
	 * @return
	 */
	public QueryHelper createQuery() {
		QueryHelper query = new QueryHelper();
		query.selectClause = "SELECT r ";
		query.fromClause = "FROM Resource r ";

		// where ---
		// 限定用户.
		if (this.userId != null) {
			query.addAndWhere("r.userId = " + this.userId);
		}
		
		// 删除.
		if (this.delState != null) {
			query.addAndWhere("r.delState = " + this.delState);
		}
		// 推荐.
		if (this.recommendState != null) {
			query.addAndWhere("r.recommendState = " + this.recommendState);
		}
		//发布到资源库	
		if	(this.publishToZyk!=null){
			query.addAndWhere("r.publishToZyk = " + this.publishToZyk);
		}
		// 某个分类.
		if (this.sysCateId != null) {
			query.addAndWhere("r.sysCateId = " + this.sysCateId);
		}
		if (this.userCateId != null) {
			query.addAndWhere("r.userCateId = " + this.userCateId);
		}

		//资源类型.
		if (this.resTypeId != null) {
			query.addAndWhere("r.resTypeId = " + this.resTypeId);
		}
		
		// 共享模式.
		if (this.shareMode != null) {
			if(this.shareMode>=1000)
				query.addAndWhere("r.shareMode >= " + this.shareMode);
			else if (this.shareMode==500)
				query.addAndWhere("r.shareMode = " + this.shareMode);
			else if (this.shareMode==0)
				query.addAndWhere("r.shareMode = " + this.shareMode);
			else
				query.addAndWhere("r.shareMode >= " + this.shareMode);
		}
		
		// 审核状态.
		if (this.auditState != null) {
			query.addAndWhere("r.auditState = " + this.auditState);
		}
		
		// 群组资源ID.
		if (this.groupCateId != null) {
			query.addAndWhere("gr.groupCateId = " + this.groupCateId);
		}
		
		// 学科.
		if (this.useSubjectId) {
			if (this.subjectId == null)
				query.addAndWhere("r.subjectId IS NULL");
			else
				query.addAndWhere("r.subjectId = " + this.subjectId);
		}
		
		// k 关键字.
		if (k != null && k.length() > 0) {
			query.addAndWhere("(r.title LIKE :likeKey) OR (r.tags LIKE :likeKey)");
			query.setString("likeKey", "%" + k + "%");
		}

		// order by, 排序部分.
		switch (this.orderType) {
		case ORDER_TYPE_ID_DESC:
			query.addOrder("r.resourceId DESC"); 
			break;
		case ORDER_TYPE_CREATEDATE_DESC:
			query.addOrder("r.createDate DESC, r.resourceId"); 
			break;
		case ORDER_TYPE_VIEWCOUNT_DESC:
			query.addOrder("r.viewCount DESC, r.resourceId DESC"); 
			break;
		case ORDER_TYPE_COMMENTS_DESC:
			query.addOrder("r.commentCount DESC, r.resourceId DESC");
			break;
		}

		return query;
	}
}
