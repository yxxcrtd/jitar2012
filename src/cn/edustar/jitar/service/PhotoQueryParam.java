package cn.edustar.jitar.service;

import cn.edustar.data.hql.QueryHelper;

/**
 * 图片查询参数.
 *
 */
public class PhotoQueryParam implements QueryParam {
	
	/** 如果给出 userId, 则表示查询该用户的文章；否则不区分用户。缺省 = null */
	public Integer userId = null;
	
	/** 获得日志条数，缺省 = 10。此条件仅当未指定分页参数时生效。 */
	public int count = 10;
	
	/** (请使用ORDER_TYPE_XXX 常量) 排序方法，为1按发表时间，为2按点击数，为3按回复数。缺省 = 1 */
	public int orderType = 1;
	
	/** 排序方法，为1按发表时间 逆序排列。 */
	public static final int ORDER_TYPE_CREATEDATE_DESC = 1;
	/** 排序方法，为2按点击数 逆序排列。 */
	public static final int ORDER_TYPE_VIEWCOUNT_DESC = 2;
	/** 排序方法，为3按回复数 逆序排列。 */
	public static final int ORDER_TYPE_COMMENTS_DESC = 3;
	
	/** 是否精华，缺省为 null 表示所有文章; = true 表示获取精华的; = false 表示获取非精华的 */
	public Boolean bestType = null;
	
	/** 调用多少天内的日志，以天为单位。 缺省 = 0 表示不限定。 */
	public int days = 0;
	
	/** 系统分类id，缺省为 null 则表示不限定日志的系统分类。 */
	public Integer sysCate = null;
	
	/** 用户分类id, 缺省为 null 表示不区分用户分类，通常同时设定 userId 参数。 */
	public Integer userStaple = null;
	
	/** 查询审核状态，== null 表示不区分，缺省 = 0 查询审核通过的。 */
	public Integer auditState = 0;
	
	/** 查询隐藏状态，== null 表示不区分，缺省 = 0 查询非隐藏的。 */
	public Integer hideState = 0;
	
	/** 删除状态，== null 表示不区分，缺省 = false 查询未删除的。  */
	public Boolean delState = Boolean.FALSE;

	/** 标题中包含此关键字'keyword'的, 使用'LIKE'在数据库中进行检索 */
	public String k = null;
	
	/** 是否提取所属群组分类信息; 缺省 = false 表示不提取; 仅在 getGroupPhotoList() 时候有效. */
	public boolean retrieveGroupCategory = false;

	/** 群组资源分类id, 缺省为 null 表示不限定群组分类 */
	public Integer groupCateId = null;
	
	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.service.QueryParam#createQuery()
	 */
	public QueryHelper createQuery() {
		QueryHelper query = new QueryHelper();
		query.fromClause = "FROM Photo ph";

		// 限定用户.
		if (this.userId != null)
			query.addAndWhere("ph.userId = " + this.userId);
		
		// 查询关键字.
		if (k != null && k.length() > 0) {
			query.addAndWhere("(ph.title LIKE :likeKey)");
			query.setString("likeKey", "%" + k + "%");
		}

		// 某个分类.
		if (this.sysCate != null)
			query.addAndWhere("ph.sysCate = " + this.sysCate);
		
		// 用户分类.
		if (this.userStaple != null) {
			if (this.userStaple == 0) {
				query.addAndWhere("ph.userStaple is NULL");
			} else {
				query.addAndWhere("ph.userStaple = " + this.userStaple);
			}
		}

		// 群组ID.
		if (this.groupCateId != null) {
			query.addAndWhere("gp.groupCateId = " + this.groupCateId);
		}
		
		
		// TODO: 日期限定内.
		if (this.days > 0) {
			// days_hql = " AND createDate > ?";
		}

		// 审核状态.
		if (this.auditState != null) {
			query.addAndWhere("ph.auditState = " + this.auditState);
		}

		// 排序部分.
		String order_hql = "";
		switch (this.orderType) {
		case PhotoQueryParam.ORDER_TYPE_VIEWCOUNT_DESC:
			order_hql = "ph.viewCount DESC, ph.id DESC";
			break;
		case PhotoQueryParam.ORDER_TYPE_COMMENTS_DESC:
			order_hql = "ph.commentCount DESC, ph.id DESC";
			break;
		case PhotoQueryParam.ORDER_TYPE_CREATEDATE_DESC:
			order_hql = "ph.createDate DESC";
			break;
		}
		query.addOrder(order_hql);

		return query;
	}
}
