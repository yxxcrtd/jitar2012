package cn.edustar.jitar.service;

import java.util.Date;

import cn.edustar.data.hql.QueryHelper;
import cn.edustar.jitar.util.DateUtil;

/**
 * 用户查询参数
 * 
 * @author Yang Xinxin
 * @version 1.0.0 Apr 2, 2008 1:30:14 PM
 */
public class UserQueryParam implements QueryParam {

	/** 要查询的用户最多数量, 缺省 = 10; 只在未指定 Pager 的时候生效 */
	public int count = 10;

	/** 是否被删除, 缺省 = false 表示选择未删除的; = true 表示获得删除的; = null 表示不限制此条件 */
	public Boolean delState = Boolean.FALSE;

	/** 要查询的用户标识, 缺省 = null 表示不限定. */
	public Integer userId = null;

	/** 要查询的用户登录名, 缺省 = null 表示不限定. */
	public String loginName = null;

	/** 要查询的用户的所属'系统分类', 缺省 = null, 必须和 useUserClassId 结合使用 */
	public Integer userClassId = null;

	/** 是否限定用户分类, 缺省 = false */
	public boolean useUserClassId = false;

	/** 查询的学科条件过滤 */
	public Integer subjectId = null;
	
	/** 是否限定'subjectId'条件, 缺省 = false 表示不限制 */
	public boolean useSubjectId = false;

	/** 查询的学段条件 */
	public Integer gradeId = null;
	
	/** 用户类型：名师、教研员等等等等自定义 */
	public Integer userTypeId = null;
	
	/** 查询的机构条件 */
	public Integer unitId = null;
	
	/** 查询条件中包含此关键字'keyword'的, 使用'LIKE'在数据库中进行检索 */
	public String k = null;
	
	/** 多少天内创建的用户, 缺省 = null 表示不限制, 否则表示自当前日期算起向前推的天数, 例如 0.5 表示 12小时. */
	public Double daysFromCreate = null;

	/**
	 * 排序方式, 默认 = 0 表示按照 id 逆序排列, 1 - visitCount DESC, 2 - articleCount DESC, 3 -
	 * userScore desc, 4 - resourceCount DESC
	 */
	public int orderType = 0;

	/** 排序方式, = 0 表示按照 id 逆序排列 */
	public static final int ORDER_TYPE_ID_DESC = 0;
	
	/** 排序方式, = 1 表示按照 visitCount(访问量) 逆序排列 */
	public static final int ORDER_TYPE_VISIT_DESC = 1;
	
	/** 排序方式, = 2 表示按照 articleCount(文章数) 逆序排列 */
	public static final int ORDER_TYPE_ARTICLE_DESC = 2;
	
	/** 排序方式, = 3 表示按照 userScore(积分) 逆序排列 */
	public static final int ORDER_TYPE_SCORE_DESC = 3;
	
	/** 排序方式, = 4 表示按照 resourceCount(上传资源数) 逆序排列 */
	public static final int ORDER_TYPE_RESOURCE_DESC = 4;

	/**
	 * 根据当前条件创建查询对象.
	 * 
	 * @return
	 */
	public QueryHelper createQuery() {
		
		// 构造查询条件.
		QueryHelper query = new QueryHelper();
		query.fromClause = "FROM User u ";

		// this.userId 条件.
		if (this.userId != null)
			query.addAndWhere("u.userId = " + this.userId);

		// this.loginName 条件.
		if (this.loginName != null) {
			query.addAndWhere("u.loginName = :loginName");
			query.setString("loginName", this.loginName);
		}

		// 所属系统分类.
		if (this.useUserClassId) {
			if (this.userClassId == null)
				query.addAndWhere("u.userClassId IS NULL");
			else
				query.addAndWhere("u.userClassId = " + this.userClassId);
		}
		
		// 如果搜索的关键字不为空，则在'用户ID','登录名','呢称','真实姓名'中查询
		if (k != null && k.length() > 0) {
			query.addAndWhere("(u.userId LIKE :likeKey) OR (u.loginName LIKE :likeKey) OR (u.nickName LIKE :likeKey) OR (u.trueName LIKE :likeKey)");
			query.setString("likeKey", "%" + k + "%");
		}

		// 学科, 学段.
		if (this.useSubjectId) {
			if (this.subjectId == null)
				query.addAndWhere("u.subjectId IS NULL");
			else
				query.addAndWhere("u.subjectId = " + this.subjectId);
		}
		
		// 学科条件
		if (this.subjectId != null) {
			query.addAndWhere("u.subjectId = " + this.subjectId);
		}
		
		// 学段条件
		if (this.gradeId != null)
			query.addAndWhere("u.gradeId = " + this.gradeId);
		
		// 机构条件
		if (this.unitId != null) {
			query.addAndWhere("u.unitId = " + this.unitId);
		}

		// 是否是名师, 专家, 推荐, 教研员.
		if (this.userTypeId != null) {
			query.addAndWhere("u.userType LIKE '%/" + this.userTypeId + "/%' " );
		}
		
		// 创建日期.
		if (this.daysFromCreate != null) {
			query.addAndWhere("u.createDate >= :createDate");
			Date fromCreate = DateUtil.addDays(DateUtil.getNow(), -daysFromCreate);
			query.setDate("createDate", fromCreate);
		}

		// 排序.
		switch (this.orderType) {
		case ORDER_TYPE_ID_DESC:
			query.addOrder("u.id DESC");
			break;
		case ORDER_TYPE_VISIT_DESC:
			query.addOrder("u.visitCount DESC, u.id DESC");
			break;
		case ORDER_TYPE_ARTICLE_DESC:
			query.addOrder("u.myArticleCount DESC, u.id DESC");
			break;
		case ORDER_TYPE_SCORE_DESC:
			query.addOrder("u.userScore DESC, u.id DESC");
			break;
		case ORDER_TYPE_RESOURCE_DESC:
			query.addOrder("u.resourceCount DESC, u.id DESC");
			break;
		}

		// 返回.
		return query;
	}
}
