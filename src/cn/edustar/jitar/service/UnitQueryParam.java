package cn.edustar.jitar.service;

import cn.edustar.data.hql.QueryHelper;

/**
 * 机构查询参数
 * 
 * @author Yang Xinxin
 * @version 1.0.0 Jun 27, 2008 4:54:39 PM
 */
public class UnitQueryParam implements QueryParam {
	public int count = 1000;
	
	/** 机构的所属区县标识 */
	public Integer unitId = null;

	/** 标题中包含此关键字'keyword'的, 使用'LIKE'在数据库中进行检索 */
	public String k = null;

	/** 排序类型 -1 表示默认排序**/
	public int orderType = 0;
	public final int ORDER_NAME_ASC = 0;		//默认排序
	public final int ORDER_aveScore_DESC = 99;	//按照个人平均分排序	//aveScore
	public final int ORDER_studioCount_DESC = 11;
	public final int ORDER_articleCount_DESC = 12;
	public final int ORDER_recommendArticleCount_DESC = 13;
	public final int ORDER_resourceCount_DESC = 14;
	public final int ORDER_recommendResourceCount_DESC = 15;
	public final int ORDER_photoCount_DESC = 16;
	public final int ORDER_videoCount_DESC = 17;
	public final int ORDER_totalScore_DESC = 18;
	
	/** 机构管理中的投影查询字段 */
	public String selectFields = "new Map(u.unitId as unitId, u.unitName as unitName, u.unitType as unitType, " +
		"u.createDate as createDate, u.unitTitle as unitTitle,  " +
		"u.studioCount as studioCount, u.myArticleCount, u.otherArticleCount as articleCount, u.recommendArticleCount as recommendArticleCount, " +
		"u.resourceCount as resourceCount, u.recommendResourceCount as recommendResourceCount, u.totalScore as totalScore,u.aveScore as aveScore)";

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.edustar.jitar.service.QueryParam#createQuery()
	 */
	public QueryHelper createQuery() {
		QueryHelper query = new QueryHelper();

		query.fromClause = "FROM Unit u";
		if(orderType==ORDER_NAME_ASC)
			query.addOrder("u.unitName ASC");
		else if(orderType==ORDER_aveScore_DESC)
			query.addOrder("u.aveScore DESC,u.unitName ASC");
		else if(orderType==ORDER_studioCount_DESC)
			query.addOrder("u.studioCount DESC,u.unitName ASC");
		else if(orderType==ORDER_articleCount_DESC)
			query.addOrder("u.articleCount DESC,u.unitName ASC");
		else if(orderType==ORDER_recommendArticleCount_DESC)
			query.addOrder("u.recommendArticleCount DESC,u.unitName ASC");
		else if(orderType==ORDER_resourceCount_DESC)
			query.addOrder("u.resourceCount DESC,u.unitName ASC");
		else if(orderType==ORDER_recommendResourceCount_DESC)
			query.addOrder("u.recommendResourceCount DESC,u.unitName ASC");
		else if(orderType==ORDER_photoCount_DESC)
			query.addOrder("u.photoCount DESC,u.unitName ASC");
		else if(orderType==ORDER_videoCount_DESC)
			query.addOrder("u.videoCount DESC,u.unitName ASC");
		else if(orderType==ORDER_totalScore_DESC)
			query.addOrder("u.totalScore DESC,u.unitName ASC");
		
		if (k != null && k.length() > 0) {
			query.addAndWhere("(u.unitId LIKE :likeKey) OR (u.unitName LIKE :likeKey) OR (u.unitTitle LIKE :likeKey) ");
			query.setString("likeKey", "%" + k +"%");
		}
		
		if (unitId != null)
			query.addAndWhere("u.unitId = " + this.unitId);
		

		return query;
	}
	
}
