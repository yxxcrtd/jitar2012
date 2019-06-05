package cn.edustar.jitar.query;

import java.util.Date;
import cn.edustar.jitar.util.DateUtil;
import cn.edustar.data.hql.QueryHelper;
import cn.edustar.jitar.service.QueryParam;

/**
 * 用户查询参数
 * 
 * @author Yang Xinxin
 * @version 1.0.0 Apr 2, 2008 1:30:14 PM
 */
public class UserStatQueryParam implements QueryParam {
	private String objectTableName = "UserStat";

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


	/** 查询的机构条件 */
	public Integer unitId = null;
	
	/** 查询条件中包含此关键字'keyword'的, 使用'LIKE'在数据库中进行检索 */
	public String k = null;

	/** 多少天内创建的用户, 缺省 = null 表示不限制, 否则表示自当前日期算起向前推的天数, 例如 0.5 表示 12小时. */
	public Double daysFromCreate = null;
	public Integer teacherType = null;
	
	/** 查询用标识 */
	public String statGuid = null;

	/**
	 * 排序方式, 默认 = 0 表示按照 id 逆序排列, 1 - visitCount DESC, 2 - articleCount DESC, 3
	 * - userScore desc, 4 - resourceCount DESC
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

	public static final int ORDER_TYPE_trueName_ASC = 31;
	public static final int ORDER_TYPE_userScore_DESC = 32;
	public static final int ORDER_TYPE_visitCount_DESC = 33;
	public static final int ORDER_TYPE_visitArticleCount_DESC = 34;
	public static final int ORDER_TYPE_visitResourceCount_DESC = 35;
	public static final int ORDER_TYPE_myArticleCount_DESC = 36;
	public static final int ORDER_TYPE_otherArticleCount_DESC = 37;
	public static final int ORDER_TYPE_recommendArticleCount_DESC = 38;
	public static final int ORDER_TYPE_articleCommentCount_DESC = 39;
	public static final int ORDER_TYPE_articleICommentCount_DESC = 40;
	public static final int ORDER_TYPE_resourceCount_DESC = 41;
	public static final int ORDER_TYPE_recommendResourceCount_DESC = 42;
	public static final int ORDER_TYPE_resourceCommentCount_DESC = 43;
	public static final int ORDER_TYPE_resourceICommentCount_DESC = 44;
	public static final int ORDER_TYPE_resourceDownloadCount_DESC = 45;
	public static final int ORDER_TYPE_prepareCourseCount_DESC = 46;
	public static final int ORDER_TYPE_createGroupCount_DESC = 47;
	public static final int ORDER_TYPE_jionGroupCount_DESC = 48;
	public static final int ORDER_TYPE_photoCount_DESC = 49;
	public static final int ORDER_TYPE_videoCount_DESC = 50;
	public static final int ORDER_TYPE_articlePunishScore_DESC = 51;
	public static final int ORDER_TYPE_resourcePunishScore_DESC = 52;
	public static final int ORDER_TYPE_commentPunishScore_DESC = 53;
	public static final int ORDER_TYPE_photoPunishScore_DESC = 54;
	public static final int ORDER_TYPE_videoPunishScore_DESC = 55;

	public UserStatQueryParam() {
	}

	/**
	 * 为了能使用其他表 User 的 构造函数
	 * 
	 * @param objectTable
	 */
	public UserStatQueryParam(String objectTable) {
		objectTableName = objectTable;
	}

	/**
	 * 根据当前条件创建查询对象
	 * 
	 * @return
	 */
	public QueryHelper createQuery() {
		QueryHelper query = new QueryHelper();
		query.fromClause = "FROM " + objectTableName + " u ";

		if (this.teacherType != null && teacherType > 0) {
			query.addAndWhere("u.userType LIKE :userType");
			query.setString("userType", "%/" + teacherType + "/%");
		}

		if (this.userId != null)
			query.addAndWhere("u.userId = " + this.userId);

		if (this.loginName != null) {
			query.addAndWhere("u.loginName = :loginName");
			query.setString("loginName", this.loginName);
		}

		if (this.useUserClassId) {
			if (this.userClassId == null)
				query.addAndWhere("u.userClassId IS NULL");
			else
				query.addAndWhere("u.userClassId = " + this.userClassId);
		}

		if (k != null && k.length() > 0) {
			query.addAndWhere("(u.userId LIKE :likeKey) OR (u.loginName LIKE :likeKey) OR (u.nickName LIKE :likeKey) OR (u.trueName LIKE :likeKey)");
			query.setString("likeKey", "%" + k + "%");
		}

		if (this.useSubjectId) {
			if (this.subjectId == null)
				query.addAndWhere("u.subjectId IS NULL");
			else
				query.addAndWhere("u.subjectId = " + this.subjectId);
		}

		if (this.subjectId != null) {
			query.addAndWhere("u.subjectId = " + this.subjectId);
		}

		if (this.gradeId != null)
			query.addAndWhere("u.gradeId = " + this.gradeId);


		if (this.unitId != null) {
			query.addAndWhere("u.unitId = " + this.unitId);
		}		

		if (this.daysFromCreate != null) {
			query.addAndWhere("u.createDate >= :createDate");
			Date fromCreate = DateUtil.addDays(DateUtil.getNow(),-daysFromCreate);
			query.setDate("createDate", fromCreate);
		}

		if(this.statGuid != null)
		{
			query.addAndWhere("u.statGuid = '" + this.statGuid + "'" );
		}
		switch (this.orderType) {
		case ORDER_TYPE_ID_DESC:
			query.addOrder("u.userId DESC");
			break;
		case ORDER_TYPE_VISIT_DESC:
			query.addOrder("u.visitCount DESC, u.userId DESC");
			break;
		case ORDER_TYPE_ARTICLE_DESC:
			query.addOrder("u.myArticleCount DESC, u.userId DESC");
			break;
		case ORDER_TYPE_SCORE_DESC:
			query.addOrder("u.userScore DESC, u.userId DESC");
			break;
		case ORDER_TYPE_RESOURCE_DESC:
			query.addOrder("u.resourceCount DESC, u.userId DESC");
			break;
		case ORDER_TYPE_trueName_ASC:
			query.addOrder("u.trueName ASC, u.userId ASC");
			break;
		case ORDER_TYPE_userScore_DESC:
			query.addOrder("u.userScore DESC, u.userId DESC");
			break;
		case ORDER_TYPE_visitCount_DESC:
			query.addOrder("u.visitCount DESC, u.userId DESC");
			break;
		case ORDER_TYPE_visitArticleCount_DESC:
			query.addOrder("u.visitArticleCount DESC, u.userId DESC");
			break;
		case ORDER_TYPE_visitResourceCount_DESC:
			query.addOrder("u.visitResourceCount DESC, u.userId DESC");
			break;
		case ORDER_TYPE_myArticleCount_DESC:
			query.addOrder("u.myArticleCount DESC, u.userId DESC");
			break;
		case ORDER_TYPE_otherArticleCount_DESC:
			query.addOrder("u.otherArticleCount DESC, u.userId DESC");
			break;
		case ORDER_TYPE_recommendArticleCount_DESC:
			query.addOrder("u.recommendArticleCount DESC, u.userId DESC");
			break;
		case ORDER_TYPE_articleCommentCount_DESC:
			query.addOrder("u.articleCommentCount DESC, u.userId DESC");
			break;
		case ORDER_TYPE_articleICommentCount_DESC:
			query.addOrder("u.articleICommentCount DESC, u.userId DESC");
			break;
		case ORDER_TYPE_resourceCount_DESC:
			query.addOrder("u.resourceCount DESC, u.userId DESC");
			break;
		case ORDER_TYPE_recommendResourceCount_DESC:
			query.addOrder("u.recommendResourceCoun DESC, u.userId DESC");
			break;
		case ORDER_TYPE_resourceCommentCount_DESC:
			query.addOrder("u.resourceCommentCount DESC, u.userId DESC");
			break;
		case ORDER_TYPE_resourceICommentCount_DESC:
			query.addOrder("u.resourceICommentCount DESC, u.userId DESC");
			break;
		case ORDER_TYPE_resourceDownloadCount_DESC:
			query.addOrder("u.resourceDownloadCount DESC, u.userId DESC");
			break;
		case ORDER_TYPE_prepareCourseCount_DESC:
			query.addOrder("u.prepareCourseCount DESC, u.userId DESC");
			break;
		case ORDER_TYPE_createGroupCount_DESC:
			query.addOrder("u.createGroupCount DESC, u.userId DESC");
			break;
		case ORDER_TYPE_jionGroupCount_DESC:
			query.addOrder("u.jionGroupCount DESC, u.userId DESC");
			break;
		case ORDER_TYPE_photoCount_DESC:
			query.addOrder("u.photoCount DESC, u.userId DESC");
			break;
		case ORDER_TYPE_videoCount_DESC:
			query.addOrder("u.videoCount DESC, u.userId DESC");
			break;
		case ORDER_TYPE_articlePunishScore_DESC:
			query.addOrder("u.articlePunishScore DESC, u.userId DESC");
			break;
		case ORDER_TYPE_resourcePunishScore_DESC:
			query.addOrder("u.resourcePunishScore DESC, u.userId DESC");
			break;
		case ORDER_TYPE_commentPunishScore_DESC:
			query.addOrder("u.commentPunishScore DESC, u.userId DESC");
			break;
		case ORDER_TYPE_photoPunishScore_DESC:
			query.addOrder("u.photoPunishScore DESC, u.userId DESC");
			break;
		case ORDER_TYPE_videoPunishScore_DESC:
			query.addOrder("u.videoPunishScore DESC, u.userId DESC");
			break;
		}
		return query;
	}
}
