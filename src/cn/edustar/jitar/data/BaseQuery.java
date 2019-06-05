package cn.edustar.jitar.data;

import java.util.List;

import cn.edustar.data.Pager;

/**
 * 提供给各 Query 实现做为公共基类, 当前可以提供给 Python 写的 User,ResourceQuery 做为基类.
 * 
 * 
 * @remark 期待的用法. 1. query = ResourceQuery() query.list() -> 返回缺省查询出的资源列表.
 * 
 *         2. query =
 *         ResourceQuery(selectFields="resourceId, title, href, fsize ...")
 *         query.list() -> 投影查询出这些字段, 返回为 List<Object[]>. query.query_map() ->
 *         投影查询出这些字段, 返回为 List<Map> . query.query_map(12) -> 查询出前 12 个.
 *         query.query_map(20, 8) -> 从第 20 条开始查询出 8 条.
 * 
 *         3. query = ResourceQuery(selectFields=
 *         "r.resourceId,u.userId,r.href,subj.subjectName ...") query.subjectId
 *         = 1 query.orderType = 3 query.query_map() -> 查询带有表关联多个投影字段, 其中自动解析
 *         r,u,subj 等缩写的含义.
 * 
 */
@SuppressWarnings("unchecked")
public abstract class BaseQuery {
	/** 投影查询的字段列表, 以 ',' 分隔多个字段. */
	protected String selectFields = "";

	/** 开始查询记录的位置 */
	protected int firstResult = -1;

	/** 缺省要获取的记录数量 */
	protected int maxResults = 10;

	/**
	 * 构造.
	 * 
	 * @param selectFields
	 */
	protected BaseQuery(String selectFields) {
		this.selectFields = selectFields;
	}

	/** 开始查询记录的位置 */
	public void setFirstResult(int firstResult) {
		this.firstResult = firstResult;
	}

	/** 缺省要获取的记录数量 */
	public void setMaxResults(int maxResults) {
		this.maxResults = maxResults;
	}

	/**
	 * 得到限定的 where 条件, 派生类应该重载此方法.
	 * 
	 * @return
	 */
	public void applyWhereCondition(QueryContext qctx) {
	}

	/**
	 * 提供排序限定 order by, 派生类应该重载此方法.
	 * 
	 * @param qctx
	 */
	public void applyOrderCondition(QueryContext qctx) {

	}

	/**
	 * 得到初始化的 FromEntity 集合, 一般 Query 要指定主查询对象为初始化 entity. 如 ResourceQuery 以
	 * Resource 为主查询对象. 也可以是多个主查询对象.
	 * 
	 * @return
	 */
	public abstract void initFromEntities(QueryContext qctx);

	/**
	 * 解析指定的实体, 并加入到 qctx 环境中. 缺省实现为抛出异常.
	 * 
	 * @param qctx
	 * @param entity
	 */
	public void resolveEntity(QueryContext qctx, String entity) {
		// throw new RuntimeException("查询类 " + this.getClass().getSimpleName() +
		// " 不支持的实体 " + entity);
		throw new RuntimeException("Query class "
				+ this.getClass().getSimpleName() + " does not support entity "
				+ entity);
	}

	/**
	 * 返回缺省查询的数据, 为了防止查询出太多数据, 缺省从 firstResult 开始 获取 maxResults 条.
	 * 
	 * @return
	 */
	public List query_map() {
		// 构造查询器工厂和查询器.
		QueryContext qctx = buildQueryContext();
		qctx.createQueryHelperForMap();

		// 查询.
		return qctx.queryData(qctx.getSession(), firstResult, maxResults);
	}

	/**
	 * 查询从 firstResult 开始的 maxResults 条记录.
	 * 
	 * @param maxResults
	 * @return
	 */
	public List query_map(int maxResults) {
		// 构造查询器工厂和查询器.
		QueryContext qctx = buildQueryContext();
		qctx.createQueryHelperForMap();

		// 查询.
		return qctx.queryData(qctx.getSession(), firstResult, maxResults);
	}

	/**
	 * 查询从 firstResult 开始的 maxResults 条记录.
	 * 
	 * @param maxResults
	 * @return
	 */
	public List query_map(int firstResult, int maxResults) {
		// 构造查询器工厂和查询器.
		QueryContext qctx = buildQueryContext();
		qctx.createQueryHelperForMap();

		// 查询.
		return qctx.queryData(qctx.getSession(), firstResult, maxResults);
	}

	/**
	 * 按照指定分页条件查询数据.
	 * 
	 * @param pager
	 * @return
	 */
	public Object query_map(Pager pager) {
		// 构造查询器工厂和查询器.
		QueryContext qctx = buildQueryContext();
		qctx.createQueryHelperForMap();

		// 查询.
		return qctx.queryData(qctx.getSession(), pager);
	}

	/**
	 * 得到第一个返回的元素.
	 * 
	 * @return
	 */
	public Object first_map() {
		List result = query_map(1);
		if (result == null || result.size() == 0)
			return null;
		return result.get(0);
	}

	/**
	 * 得到第一个元素.
	 * 
	 * @return
	 */
	public Object first() {
		List result = list(1);
		if (result == null)
			return null;
		return result.get(0);
	}

	/**
	 * 以 List&lt;Object[]&gt; 返回要查询的数据.
	 * 
	 * @return
	 */
	public List list() {
		// 构造查询器工厂和查询器.
		QueryContext qctx = buildQueryContext();
		qctx.createQueryHelperForList();

		// 查询.
		return qctx.queryData(qctx.getSession(), firstResult, maxResults);
	}

	/**
	 * 以 List&lt;Object[]&gt; 返回要查询的数据.
	 * 
	 * @return
	 */
	public List list(int maxResults) {
		// 构造查询器工厂和查询器.
		QueryContext qctx = buildQueryContext();
		qctx.createQueryHelperForList();

		// 查询.
		return qctx.queryData(qctx.getSession(), firstResult, maxResults);
	}

	/**
	 * 以 List&lt;Object[]&gt; 返回要查询的数据.
	 * 
	 * @return
	 */
	public List list(int firstResult, int maxResults) {
		// 构造查询器工厂和查询器.
		QueryContext qctx = buildQueryContext();
		qctx.createQueryHelperForList();

		// 查询.
		return qctx.queryData(qctx.getSession(), firstResult, maxResults);
	}

	/**
	 * 按照指定分页条件查询数据.
	 * 
	 * @param pager
	 * @return
	 */
	public List list(Pager pager) {
		// 构造查询器工厂和查询器.
		QueryContext qctx = buildQueryContext();
		qctx.createQueryHelperForList();

		// 查询.
		return qctx.queryData(qctx.getSession(), pager);
	}

	/**
	 * 按照统计语句方式查.
	 * 
	 * @return
	 */
	public int count() {
		// 构造查询器工厂和查询器.
		QueryContext qctx = buildQueryContext();
		qctx.createQueryHelperForCount();

		// 查询.
		return qctx.queryIntValue(qctx.getSession());
	}

	/**
	 * 构造查询器工厂.
	 * 
	 * @return
	 */
	protected QueryContext buildQueryContext() {
		QueryContext qctx = new QueryContext(this);

		// 1. 第一步: 分解 selectFields 为字段列表 List<SelectField>.
		qctx.parseSelectFields(this.selectFields);

		// 2. 第二步: 构造 FROM.
		qctx.buildFrom();

		return qctx;
	}

	// 将学段格式化成整数，例如: 3100 -》 3000 gradeId年级id
	public Integer convertRoundMinNumber(Integer gradeId) {
		if (gradeId == null) {
			return 0;
		}

		String strV = String.valueOf(gradeId);
		if (!strV.matches("^[-+]?(([0-9]+)([.]([0-9]+))?|([.]([0-9]+))?)$")) {
			return 0;
		}

		int StrLen = strV.length();
		if (StrLen < 2) {
			return gradeId;
		}
		String strPad = "0";
		for (int i = 2; i < StrLen; i++) {
			strPad = strPad + "0";
		}

		strV = strV.substring(0, 1) + strPad;
		return Integer.parseInt(strV);
	}

	// 将学段格式化成整数，例如: 3100 -》 4000 gradeId年级id
	public Integer convertRoundMaxNumber(Integer gradeId) {
		if (gradeId == null) {
			return 0;
		}
		String strV = String.valueOf(gradeId);
		if (!strV.matches("^[-+]?(([0-9]+)([.]([0-9]+))?|([.]([0-9]+))?)$")) {
			return 0;
		}

		int StrLen = strV.length();
		if (StrLen < 2) {
			return gradeId;
		}

		String strPad = "0";
		for (int i = 2; i < StrLen; i++) {
			strPad = strPad + "0";
		}
		strV = (Integer.parseInt(strV.substring(0, 1)) + 1) + strPad;
		return Integer.parseInt(strV);
	}
}
