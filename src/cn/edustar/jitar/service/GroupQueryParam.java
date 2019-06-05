package cn.edustar.jitar.service;

import cn.edustar.data.hql.QueryHelper;
import cn.edustar.jitar.pojos.Group;

/**
 * 定义查询群组时候的查询参数
 * 
 *
 */
public class GroupQueryParam implements QueryParam {
	/** 查询最多记录数, -1 表示不限制; 只在未指定查询分页参数时候生效, 缺省 = 10 */
	public int count = 10;
	
	/** 创建群组的用户标识。 缺省 = null 表示不限定此条件。 */
	public Integer createUserId = null;
	
	/** 审核状态，缺省为 true; false 表示获取 未审核的, true 表示获取审核通过的_	 *   对应 group.groupState 属性 (Group.GROUP_STATE_WAIT_AUDIT)
	 */
	public Boolean audit_state = Boolean.TRUE;
	
	/** 分类标识, 缺省 = null. 设置 useCategoryId 才生效. */
	public Integer categoryId = null;
	
	/** 是否限定分类标识 */
	public boolean useCategoryId = false;
	
	/** 是否优秀团队群组, 缺省 = null 表示不限制 */
	public Boolean bestGroup = null;
	
	/** 是否推荐协作组, 缺省 = null 表示不限制 */
	public Boolean isRecommend = null;
	
	/** 要查询的群组的所属学科, 设置 useSubjectId 才生效. 缺省 = null */
	public Integer subjectId = null;

	/** 查询的学段条件 */
	public Integer gradeId = null;
	
	/** 是否限制查询所属学科, 缺省 = false */
	public boolean useSubjectId = false;

	
	/** 查询的关键字, 缺省 = null */
	public String k = null;
	
	/** 排序方式, 缺省为 0 表示 id 逆序排列  */
	public int orderType = ORDER_BY_ID_DESC;
	
	/** 按照 id 逆序排列，这是缺省排列方式 */
	public static final int ORDER_BY_ID_DESC = 0;
	
	/** 按照 id 正序排列 */
	public static final int ORDER_BY_ID_ASC = 1;

	/** 按照创建日期逆序排列 */
	public static final int ORDER_BY_CREATEDATE_DESC = 2;
	
	/** 按照创建日期正序排列 */
	public static final int ORDER_BY_CREATEDATE_ASC = 3;

	/** 按照群组名逆序排列 */
	public static final int ORDER_BY_GROUPNAME_DESC = 4;
	
	/** 按照群组名正序排列 */
	public static final int ORDER_BY_GROUPNAME_ASC = 5;
	
	/** 按照群组标题逆序排列 */
	public static final int ORDER_BY_GROUPTITLE_DESC = 6;
	
	/** 按照群组标题正序排列 */
	public static final int ORDER_BY_GROUPTITLE_ASC = 7;
	
	/** 按照访问次数逆序排列 */
	public static final int ORDER_BY_VISITCOUNT_DESC = 8;
	
	/** 按照主题数排序, 当前暂时用主题数当作活跃度 */
	public static final int ORDER_BY_TOPICCOUNT_DESC = 9;

	/** 按文章数、精华文章数、资源数、精华资源数、主题数、讨论数、活动数的总和从大到小排序 */
	/** 此为寿光定制用的 */
	public static final int ORDER_BY_ALLCCOUNT_DESC = 10;
	
	public static final int ORDER_BY_USERCOUNT_DESC=11;
	public static final int  ORDER_BY_ARTICLECOUNT_DESC=12;
	public static final int  ORDER_BY_BESTARTICLECOUNT_DESC=13;
	public static final int  ORDER_BY_RESOURCECOUNT_DESC=14;
	public static final int  ORDER_BY_BESTRESOURCECOUNT_DESC=15;
	public static final int  ORDER_BY_DISCUSSCOUNT_DESC=16;
	public static final int  ORDER_BY_ACTIONCOUNT_DESC=17;
	
	
	// TODO: 更多排序方式
	
	/** 从 GroupQueryParam.ORDER_BY_XXX 到字符串的映射 */
	private static final String[] _order_by =
	{
		"g.groupId DESC",			// ORDER_BY_ID_DESC 
		"g.groupId ASC",			// ORDER_BY_ID_ASC
		"g.createDate DESC",		// ORDER_BY_CREATEDATE_DESC 
		"g.createDate ASC",		// ORDER_BY_CREATEDATE_ASC
		"g.groupName DESC",		// ORDER_BY_GROUPNAME_DESC
		"g.groupName ASC",		// ORDER_BY_GROUPNAME_ASC
		"g.groupTitle DESC, g.groupId DESC",	// ORDER_BY_GROUPTITLE_DESC
		"g.groupTitle ASC, g.groupId ASC",		// ORDER_BY_GROUPTITLE_ASC
		"g.visitCount DESC",		// ORDER_BY_VISITCOUNT_DESC
		"g.topicCount DESC",		// ORDER_BY_TOPICCOUNT_DESC
		"g.articleCount+g.resourceCount+g.topicCount+g.discussCount+g.actionCount DESC",		// ORDER_BY_ALLCCOUNT_DESC
		"g.userCount DESC",		// ORDER_BY_USERCOUNT_DESC
		"g.articleCount DESC",	// ORDER_BY_ARTICLECOUNT_DESC
		"g.bestArticleCount DESC", // ORDER_BY_BESTARTICLECOUNT_DESC
		"g.resourceCount DESC",	// ORDER_BY_RESOURCECOUNT_DESC
		"g.bestResourceCount DESC",// ORDER_BY_BESTRESOURCECOUNT_DESC
		"g.discussCount DESC", // ORDER_BY_DISCUSSCOUNT_DESC
		"g.actionCount DESC"  // ORDER_BY_ACTIONCOUNT_DESC
	};

	/**
	 * 根据条件创建查询对象.
	 */
	public QueryHelper createQuery() {
		QueryHelper query = new QueryHelper();
		
		query.fromClause = "FROM Group g";
		
		// 根据 param 设置 where, order 等.
		if (this.createUserId != null) {
			query.addAndWhere("g.createUserId = " + this.createUserId);
		}
		
		if (this.audit_state != null) {
			short groupState = this.audit_state.booleanValue() ? Group.GROUP_STATE_NORMAL : Group.GROUP_STATE_WAIT_AUDIT;
			query.addAndWhere("g.groupState = " + groupState);
		}
		
		// 是否优秀团队?
		if (this.bestGroup != null)
			query.addAndWhere("g.isBestGroup = " + this.bestGroup);
		
		// 是否推荐?
		if (this.isRecommend != null) {
			query.addAndWhere("g.isRecommend = :isRecommend");
			query.setBoolean("isRecommend", this.isRecommend);
		}
		
		// 限制分类.
		if (this.useCategoryId) {
			if (this.categoryId == null) {
				query.addAndWhere("g.categoryId IS NULL");
			} else {
				query.addAndWhere("g.categoryId = " + this.categoryId);
			}
		}
		
		// 学科
		//if (this.subjectId != null) {
		//	query.addAndWhere("g.subjectId = " + this.subjectId);
		//}
		// 学段
		//if (this.gradeId != null) {
		//	query.addAndWhere("g.gradeId = " + this.gradeId);
		//}
		
		if (this.gradeId != null && this.subjectId != null) {
			query.addAndWhere("g.XKXDId LIKE %," +this.gradeId +"/"+ this.subjectId +",%");
		}
		else if (this.gradeId != null){
			query.addAndWhere("g.XKXDId LIKE %," +this.gradeId +"/%");
		}
		else if (this.subjectId != null){
			query.addAndWhere("g.XKXDId LIKE %/"+ this.subjectId +",%");
		}
		
		// 群组分类
		if (this.categoryId != null) {
			query.addAndWhere("g.categoryId = " + this.categoryId);
		}
		
		// 限制学科
		if (this.useSubjectId) {
			if (this.subjectId == null)
				query.addAndWhere("g.subjectId IS NULL");
			else
				query.addAndWhere("g.subjectId = " + this.subjectId);
		}
		
		// 学段条件
		if (this.gradeId != null) {
			query.addAndWhere("g.gradeId = " + this.gradeId);
		}
		
		
		// 限制关键字.
		if (this.k != null && this.k.length() > 0) {
			query.addAndWhere("(g.groupId LIKE :likeKey) OR (g.groupTitle LIKE :likeKey) OR (groupTags LIKE :likeKey) OR (groupIntroduce LIKE :likeKey)");
			query.setString("likeKey", "%" + k + "%");
		}
		
		// 排序条件.
		if (this.orderType >= 0 && this.orderType < _order_by.length)
			query.addOrder(_order_by[this.orderType]);
		
		return query;
	}
	
}
