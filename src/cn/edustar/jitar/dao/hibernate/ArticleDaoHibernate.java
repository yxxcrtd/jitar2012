package cn.edustar.jitar.dao.hibernate;

import java.util.Calendar;
import java.util.List;
import java.util.Date;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.jdbc.Work;
import org.hibernate.jdbc.ReturningWork;

import cn.edustar.data.Pager;
import cn.edustar.data.Tuple;
import cn.edustar.data.hql.QueryHelper;
import cn.edustar.jitar.dao.ArticleDao;
import cn.edustar.jitar.pojos.Article;
import cn.edustar.jitar.pojos.Comment;
import cn.edustar.jitar.pojos.HtmlArticleBase;
import cn.edustar.jitar.pojos.User;
import cn.edustar.jitar.pojos.WeekCommentArticle;
import cn.edustar.jitar.pojos.WeekViewCountArticle;
import cn.edustar.jitar.service.ArticleQueryParam;
import cn.edustar.jitar.service.UserArticleQueryParam;
import cn.edustar.jitar.service.ViewCountService;
import cn.edustar.jitar.util.CommonUtil;

/**
 * 获取文章信息的数据访问实现.
 * 
 */
public class ArticleDaoHibernate extends BaseDaoHibernate implements ArticleDao {

	/** 统计用户的文章数 */
	@SuppressWarnings("unused")
	private static final String STAT_USER_ARTICLE_COUNT = "SELECT COUNT(*) FROM Article a WHERE a.userId = ? AND a.auditState = 0 AND a.delState = false AND a.draftState = false";

	/** 统计用户的文章数 */
	private static final String STAT_USER_All_ARTICLE_COUNT = "SELECT COUNT(*) FROM Article a WHERE a.userId = ? AND a.auditState = 0 AND a.delState = false AND a.draftState = false";

	/** 统计文章的评论数, List<[articleId, cmt-count]>, 所有的文章 */
	private static final String STAT_ARTICLE_COMMENT = "SELECT a.articleId, COUNT(*) as commentCount FROM Article a, Comment c WHERE c.objType = 3 AND c.objId = a.articleId AND c.audit = true AND a.userId = ? GROUP BY a.articleId";

	/** 统计文章的评论数, List<[articleId, cmt-count]> */
	private static final String STAT_ARTICLE_COMMENT_COUNT = "SELECT a.articleId, COUNT(*) as commentCount FROM Article a, Comment c WHERE c.objType = 3 AND c.objId = a.articleId AND a.userId = ? AND a.auditState = 0 AND a.delState = false AND a.draftState = false GROUP BY a.articleId";

	/** 更新文章的评论数 */
	private static final String UPDATE_ARTICLE_COMMENT_COUNT = "update Article set commentCount = :commentCount where articleId = :articleId";

	/** 点击率服务对象 */
	private ViewCountService viewcount_svc;

	/**
	 * 构造.
	 */
	public ArticleDaoHibernate() {

	}

	/** 设置点击率服务. */
	public void setViewCountService(ViewCountService viewcount_svc) {
		this.viewcount_svc = viewcount_svc;
	}

	/*
	 * 保存一篇文章，具体保存到哪个表，需要进行判断，也可以是来自历史表？
	 * 
	 * @see cn.edustar.jitar.dao.iface.ArticleDao#getArticle(int)
	 */
	public Article getArticle(int articleId) {
		// 先得到基本文章对象，找到对应的表名		
		String queryString = "FROM HtmlArticleBase WHERE articleId = :articleId";
		HtmlArticleBase htmlArticleBase = (HtmlArticleBase) this.getSession().createQuery(queryString).setInteger("articleId", articleId).uniqueResult();
		if (htmlArticleBase == null) {
			return null;
		}
		
		String tableName = htmlArticleBase.getTableName();
		if (tableName == null || tableName.length() == 0){
			return null;
		}
		
		Session session = getSession();
		String sql = "select * from " + tableName + " where articleId=:articleId";
		SQLQuery query = session.createSQLQuery(sql);
		query.addEntity(Article.class);
		query.setInteger("articleId", articleId);
		Article article = (Article) query.uniqueResult();
		query = null;		
		return article;
	}

	/**
	 * 得到用户当天的文章数，这个表的位置是固定的，不会到其他的表中加载数据的。只针对当前最新表 Jitar_Article
	 */
	@SuppressWarnings("unchecked")
	public List<Article> getTodayArticles(int userId) {
		String hql = "FROM Article WHERE userId = ? and Year(createDate) = ? and Month(createDate) = ? and Day(createDate) = ?";
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
		String syear = sdf.format(date);
		sdf = new SimpleDateFormat("M");
		String smonth = sdf.format(date);
		sdf = new SimpleDateFormat("d");
		String sday = sdf.format(date);
		int year = Integer.parseInt(syear);
		int month = Integer.parseInt(smonth);
		int day = Integer.parseInt(sday);
		return this.getSession().createQuery(hql).setInteger(0, userId).setInteger(1,year).setInteger(2, month).setInteger(3, day).list();
	}

	/*
	 * 假定这里的操作只针对当前最新表 Jitar_Article
	 * 
	 * @see cn.edustar.jitar.dao.ArticleDao#getArticleAndUser(int)
	 */
	@SuppressWarnings("unchecked")
	public Tuple<Article, User> getArticleAndUser(int articleId) {
		String hql = "SELECT a, u " + " FROM Article a, User u "
				+ " WHERE a.userId = u.userId AND a.articleId = " + articleId;
		List<Object[]> result = this.getSession().createQuery(hql).list();
		if (result == null || result.size() == 0)
			return null;
		Object[] row = result.get(0);

		return new Tuple<Article, User>((Article) row[0], (User) row[1]);
	}

	/*
	 * 得到用户的文章数目？此方法废弃了吧，不再采用这样实时统计的方法了！！！
	 * 
	 * @see cn.edustar.jitar.dao.ArticleDao#getUserArticleCount(int)
	 */	
	public int getUserArticleCount(int userId) {
		// 注意: userid 在 Article 表格上 '必须' 有索引, 否则此查询统计将很慢.
		String stat_hql = "SELECT COUNT(*) FROM HtmlArticleBase WHERE userId = ? "
				+ " AND auditState = " + Article.AUDIT_STATE_OK + // 审核通过.
				" AND draftState = false " + // 非草稿.
				" AND hideState = 0 " + // 非隐藏.
				" AND delState = false "; // 非删除.
		return ( (Integer) this.getSession().createQuery(stat_hql).setInteger(0, userId).iterate().next() ).intValue();
	}

	/*
	 * 此方法废弃了吧，不再采用这样实时统计的方法了！！！
	 * 
	 * @see cn.edustar.jitar.dao.ArticleDao#getSystemCategoryArticleCount(int)
	 */
	public int getSystemCategoryArticleCount(int systemCategoryId) {
		// 注意: categoryId 在 Article 表格上 '应该' 有索引, 否则此查询统计将很慢.
		String stat_hql = "SELECT COUNT(*) FROM HtmlArticleBase WHERE sysCateId = ? "
				+ " AND auditState = " + Article.AUDIT_STATE_OK + // 审核通过.
				" AND draftState = false " + // 非草稿.
				" AND hideState = 0 " + // 非隐藏.
				" AND delState = false "; // 非删除.
		return( (Integer) this.getSession().createQuery(stat_hql).setInteger(0, systemCategoryId).iterate().next() ).intValue();
		//return getSession().executeIntScalar(stat_hql,systemCategoryId);
	}

	/*
	 * 创建新文章，文章的位置是固定的
	 * 
	 * @see
	 * cn.edustar.jitar.dao.ArticleDao#createArticle(cn.edustar.jitar.pojos.
	 * Article)
	 */
	public void createArticle(Article article) {
		HtmlArticleBase htmlArticleBase = new HtmlArticleBase();
		copyData(article,htmlArticleBase);
		htmlArticleBase.setTableName("Jitar_Article");
		this.getSession().save(htmlArticleBase);
		this.getSession().flush();
		article.setArticleId(htmlArticleBase.getArticleId());
		article.setObjectUuid(htmlArticleBase.getArticleGuid());
		this.getSession().save(article);
		this.getSession().flush();
		this.getSession().createQuery("UPDATE User SET articleCount = otherArticleCount + myArticleCount WHERE userId = ?").setInteger(0,  article.getUserId()).executeUpdate();
		
	}

	/*
	 * 更新文章，位置可能不是固定的
	 * 
	 * @see
	 * cn.edustar.jitar.dao.ArticleDao#saveArticle(cn.edustar.jitar.pojos.Article
	 * )
	 */
	public void updateArticle(Article article) {
		
		HtmlArticleBase htmlArticleBase = (HtmlArticleBase)this.getSession().get(HtmlArticleBase.class, article.getArticleId());
		if (htmlArticleBase == null) {
			return;
		}
		
		//更新基础数据表，此表起到对照的作用。
		htmlArticleBase.setTitle(article.getTitle());
		htmlArticleBase.setViewCount(article.getViewCount());
		htmlArticleBase.setCommentCount(article.getCommentCount());
		htmlArticleBase.setUserCateId(article.getUserCateId());
		htmlArticleBase.setSysCateId(article.getSysCateId());
		htmlArticleBase.setTypeState(article.getTypeState());
		htmlArticleBase.setHideState(article.getHideState());
		htmlArticleBase.setAuditState(article.getAuditState());
		htmlArticleBase.setDraftState(article.getDraftState());
		htmlArticleBase.setDelState(article.getDelState());
		htmlArticleBase.setRecommendState(article.getRecommendState());
		htmlArticleBase.setBestState(article.getBestState());		
		this.getSession().saveOrUpdate(htmlArticleBase);
		
		String tableName = htmlArticleBase.getTableName();
		if (tableName == null || tableName.length() == 0){
			return;
		}
		
		// 更新数据最全的表，因为存储的表名字是不确定的，因此需要这样实现		
		Session session = getSession();
		StringBuilder sb = new StringBuilder();
		sb.append("update " + tableName + " set ");
		sb.append("Title=?,");
		sb.append("LastModified=?,");
		sb.append("ArticleContent=?,");
		sb.append("ArticleAbstract=?,");
		sb.append("ArticleTags=?,");
		sb.append("SubjectId=?,");
		sb.append("GradeId=?,");
		sb.append("UserCateId=?,");
		sb.append("SysCateId=?,");
		sb.append("HideState=?,");
		sb.append("AuditState=?,");
		sb.append("TopState=?,");
		sb.append("BestState=?,");
		sb.append("DraftState=?,");
		sb.append("DelState=?,");
		sb.append("RecommendState=?,");
		sb.append("CommentState=?,");
		sb.append("TypeState=?,");
		sb.append("OrginPath=?,");
		sb.append("UnitPathInfo=?,");
		sb.append("ApprovedPathInfo=?,");
		sb.append("RcmdPathInfo=?,");
		sb.append("ArticleFormat=?,");
		sb.append("WordDownload=?,");
		sb.append("WordHref=?");
		sb.append(" where articleId=" + article.getArticleId());
		String sql = sb.toString();
		sb = null;
		
		/*
		System.out.println("tableName=" + tableName);
		System.out.println("article.getUserCateId()=" + article.getUserCateId());
		*/
		SQLQuery query = session.createSQLQuery(sql);
		query.setString(0, article.getTitle());
		query.setDate(1, new Date());
		query.setText(2, article.getArticleContent());
		query.setString(3, article.getArticleAbstract());
		query.setString(4,article.getArticleTags());
		query.setParameter(5,article.getSubjectId());
		query.setParameter(6,article.getGradeId());
		//query.setInteger(7,article.getUserCateId());不能设置null值，不能这样写
		query.setParameter(7, article.getUserCateId());		
		query.setParameter(8,article.getSysCateId());
		query.setShort(9, article.getHideState());
		query.setShort(10, article.getAuditState());
		query.setBoolean(11, article.getTopState());
		query.setBoolean(12, article.getBestState());
		query.setBoolean(13, article.getDraftState());
		query.setBoolean(14, article.getDelState());
		query.setBoolean(15, article.getRecommendState());
		query.setBoolean(16, article.getCommentState());
		query.setBoolean(17, article.getTypeState());
		query.setString(18, article.getOrginPath());
		query.setString(19, article.getUnitPathInfo());
		query.setString(20, article.getApprovedPathInfo());
		query.setString(21, article.getRcmdPathInfo());
		query.setParameter(22, article.getArticleFormat());		
		query.setBoolean(23, article.getWordDownload());		
		query.setString(24, article.getWordHref());		
		query.executeUpdate();
		query = null;
		
		/* 更新相关数据 */
		this.updateGroupArticleState(htmlArticleBase);
		this.updateChannelArticleState(htmlArticleBase);
		this.updateSpecialSubjectArticleState(htmlArticleBase);
		this.updatePrepareCourseArticleState(htmlArticleBase);
		htmlArticleBase = null;
		
		
	}

	public void updateGroupArticleState(HtmlArticleBase htmlArticleBase)
	{
		this.updateRelativeArticleState(htmlArticleBase, "G_GroupArticle");
	}
	
	public void updateChannelArticleState(HtmlArticleBase htmlArticleBase)
	{
		this.updateRelativeArticleState(htmlArticleBase, "Jitar_ChannelArticle");
	}
	
	public void updateSpecialSubjectArticleState(HtmlArticleBase htmlArticleBase)
	{
		this.updateRelativeArticleState(htmlArticleBase, "Jitar_SpecialSubjectArticle");
	}
	
	public void updatePrepareCourseArticleState(HtmlArticleBase htmlArticleBase)
	{
		this.updateRelativeArticleState(htmlArticleBase, "C_PrepareCourseArticle");
	}
	
	public void updateGroupArticleState(Article article)
	{
		this.updateRelativeArticleState(article, "G_GroupArticle");
	}
	
	public void updateChannelArticleState(Article article)
	{
		this.updateRelativeArticleState(article, "Jitar_ChannelArticle");
	}
	
	public void updateSpecialSubjectArticleState(Article article)
	{
		this.updateRelativeArticleState(article, "Jitar_SpecialSubjectArticle");
	}
	
	public void updatePrepareCourseArticleState(Article article)
	{
		this.updateRelativeArticleState(article, "C_PrepareCourseArticle");
	}
	
	private void updateRelativeArticleState(HtmlArticleBase htmlArticleBase, String tableName)
	{
		if(htmlArticleBase == null || tableName == null || tableName.length() == 0)
		{
			return;
		}
		boolean articleState = ((htmlArticleBase.getDelState() == false) && (htmlArticleBase.getDraftState() == false) && (htmlArticleBase.getHideState()==0) && (htmlArticleBase.getAuditState() == 0));
		Session session = getSession();
		StringBuilder sb = new StringBuilder();
		sb.append("UPDATE " + tableName + " SET ");
		sb.append("Title=:Title,");
		sb.append("ArticleState=:ArticleState,");
		sb.append("TypeState=:TypeState");
		sb.append(" WHERE articleId=" + htmlArticleBase.getArticleId());
		String sql = sb.toString();
		sb = null;
		
		SQLQuery query = session.createSQLQuery(sql);
		query.setString("Title", htmlArticleBase.getTitle());
		query.setBoolean("ArticleState", articleState);
		query.setBoolean("TypeState", htmlArticleBase.getTypeState());
		query.executeUpdate();
		query = null;
	}
	
	private void updateRelativeArticleState(Article article, String tableName)
	{
		if(article == null || tableName == null || tableName.length() == 0)
		{
			return;
		}
		boolean articleState = ((article.getDelState() == false) && (article.getDraftState() == false) && (article.getHideState()==0) && (article.getAuditState() == 0));
		Session session = getSession();
		StringBuilder sb = new StringBuilder();
		sb.append("UPDATE " + tableName + " SET ");
		sb.append("Title=:Title,");
		sb.append("ArticleState=:ArticleState,");
		sb.append("TypeState=:TypeState");
		sb.append(" WHERE articleId=" + article.getArticleId());
		String sql = sb.toString();
		sb = null;
		
		SQLQuery query = session.createSQLQuery(sql);
		query.setString("Title", article.getTitle());
		query.setBoolean("ArticleState", articleState);
		query.setBoolean("TypeState", article.getTypeState());
		query.executeUpdate();
		query = null;
	}
	/*
	 * 删除文章不能在这里调用了，删除的东西很多的，如评论，其他地方的文章，是个复杂的过程。
	 * 
	 * @see
	 * cn.edustar.jitar.dao.ArticleDao#crashArticle(cn.edustar.jitar.pojos.Article
	 * )
	 */
	public void crashArticle(Article article) {
		if(article == null) return;
		final int aId = article.getArticleId();
		Date d = article.getCreateDate();
		Calendar c = Calendar.getInstance();
		c.setTime(d);
		final int dataYear = c.get(Calendar.YEAR);
					
		this.getSession().doWork(new Work() {
			public void execute(Connection connection)
					throws SQLException {
				CallableStatement cs = connection.prepareCall("{Call DeleteArticleById(?,?)}",ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);							
				cs.setInt(1, aId);
				cs.setInt(2, dataYear);
				cs.execute();
			}
		});
						
	}

	
	public int updateArticleDeleteState(Article article, boolean delstatus) {
		HtmlArticleBase baseArticle = (HtmlArticleBase)this.getSession().get(HtmlArticleBase.class, article.getArticleId());
		baseArticle.setDelState(delstatus);
		this.getSession().update(baseArticle);
		String tableName =  baseArticle.getTableName();
		
		this.updateChannelArticleState(baseArticle);
		this.updateGroupArticleState(baseArticle);
		this.updateSpecialSubjectArticleState(baseArticle);
		this.updatePrepareCourseArticleState(baseArticle);
		
		final String sql = "UPDATE " + tableName + " SET delState = " + (delstatus?1:0) + " WHERE articleId = " + article.getArticleId();
					
		this.getSession().doWork(new Work() {
			public void execute(Connection connection)
					throws SQLException {
				connection.createStatement().executeUpdate(sql);
			}
		});
		return 1;
	}

	
	public int updateArticleAuditState(Article article, short auditState) {
		HtmlArticleBase baseArticle = (HtmlArticleBase)this.getSession().get(HtmlArticleBase.class, article.getArticleId());
		if(baseArticle == null) return 0;
		baseArticle.setAuditState(auditState);
		this.getSession().update(baseArticle);
		this.getSession().flush();
		this.updateChannelArticleState(baseArticle);
		this.updateGroupArticleState(baseArticle);
		this.updateSpecialSubjectArticleState(baseArticle);
		this.updatePrepareCourseArticleState(baseArticle);
		
		String tableName = baseArticle.getTableName();		
		final String sql = "UPDATE " + tableName + " SET auditState = " + auditState + " WHERE articleId = " + article.getArticleId();
				
		return this.getSession().doReturningWork(new ReturningWork<Integer>() {
			public Integer execute(Connection connection) throws SQLException {
				return connection.createStatement().executeUpdate(sql);
			}
		});
	}

	

	public int updateArticleRcmdState(Article article, boolean rcmdState) {
		HtmlArticleBase baseArticle = (HtmlArticleBase)this.getSession().get(HtmlArticleBase.class, article.getArticleId());
		if(baseArticle == null) return 0;
		baseArticle.setRecommendState(rcmdState);
		this.getSession().update(baseArticle);
		String tableName = baseArticle.getTableName();		
		final String sql = "UPDATE " + tableName + " SET recommendState = " + (rcmdState?1:0) + " WHERE articleId = " + article.getArticleId();
		return this.getSession().doReturningWork(new ReturningWork<Integer>() {
				public Integer execute(Connection connection)
						throws SQLException {
					return connection.createStatement().executeUpdate(sql);
				}
			});
	}

	public int updateArticleBestState(Article article, boolean bestState) {
		HtmlArticleBase baseArticle = (HtmlArticleBase)this.getSession().get(HtmlArticleBase.class, article.getArticleId());
		if(baseArticle == null) return 0;
		baseArticle.setBestState(bestState);
		this.getSession().update(baseArticle);
		String tableName = baseArticle.getTableName();		
		final String sql = "UPDATE " + tableName + " SET bestState = " + (bestState?1:0) + " WHERE articleId = " + article.getArticleId();
		return  this.getSession().doReturningWork(new ReturningWork<Integer>() {
						public Integer execute(Connection connection)
								throws SQLException {
						return	connection.createStatement().executeUpdate(sql);
						}
					});
}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.edustar.jitar.dao.ArticleDao#updateArticleDraftStatus(int,
	 * boolean)
	 */
	public int updateArticleDraftState(Article article, boolean draftState) {
		String queryString = "FROM HtmlArticleBase WHERE articleId = :articleId";
		HtmlArticleBase baseArticle = (HtmlArticleBase) this.getSession().createQuery(queryString).setInteger("articleId", article.getArticleId()).uniqueResult();
		if (baseArticle == null) {
			return 0;
		}
		
		
		queryString = "UPDATE HtmlArticleBase SET draftState = :draftState WHERE articleId = :articleId";
		this.getSession().createQuery(queryString).setBoolean("draftState", draftState).setInteger("articleId", article.getArticleId()).executeUpdate();
		//getSession().bulkUpdate(queryString, new Object[]{draftState, });
		
		this.updateChannelArticleState(baseArticle);
		this.updateGroupArticleState(baseArticle);
		this.updateSpecialSubjectArticleState(baseArticle);
		this.updatePrepareCourseArticleState(baseArticle);
		
		String tableName = baseArticle.getTableName();		
		final String sql = "UPDATE " + tableName + " SET DraftState=" + (draftState?1:0) + " WHERE articleId = " + article.getArticleId();
		return 	this.getSession().doReturningWork(new ReturningWork<Integer>() {
			public Integer execute(Connection connection)
					throws SQLException {
				return connection.createStatement().executeUpdate(sql);
			}
		});
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.edustar.jitar.dao.ArticleDao#updateArticleHideStatus(int, short)
	 */
	public int updateArticleHideState(Article article, short hideState) {
		HtmlArticleBase baseArticle = (HtmlArticleBase)this.getSession().get(HtmlArticleBase.class, article.getArticleId());
		if(baseArticle == null)
		{
			return 0;
		}
		baseArticle.setHideState(hideState);
		this.getSession().update(baseArticle);
		
		this.updateChannelArticleState(baseArticle);
		this.updateGroupArticleState(baseArticle);
		this.updateSpecialSubjectArticleState(baseArticle);
		this.updatePrepareCourseArticleState(baseArticle);
		
		String tableName = baseArticle.getTableName();		
		final String sql = "UPDATE " + tableName + " SET hideState = " + hideState + " WHERE articleId = " + article.getArticleId();
		return this.getSession().doReturningWork(new ReturningWork<Integer>() {
				public Integer execute(Connection connection)
						throws SQLException {
					return connection.createStatement().executeUpdate(sql);
				}
			});
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cn.edustar.jitar.dao.iface.ArticleDao#getArticleList(cn.edustar.jitar
	 * .service.ArticleQueryParam, cn.edustar.jitar.util.Pager)
	 */
	@SuppressWarnings("unchecked")
	public List<Article> getArticleList(ArticleQueryParam param, Pager pager) {
		if (param == null)
			throw new IllegalArgumentException("param == null");

		// 使用条件创建'query'对象，其中不设置选择哪些字段.
		QueryHelper query = param.createQuery();

		if (pager == null) {
			return query.queryData(this.getSession(), -1, param.count);
		} else {
			return query.queryDataAndTotalCount(this.getSession(), pager);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cn.edustar.jitar.dao.ArticleDao#getUserArticleDataTable(cn.edustar.jitar
	 * .service.UserArticleQueryParam, cn.edustar.data.Pager)
	 */
	@SuppressWarnings("unchecked")
	public List<Object[]> getUserArticleDataTable(UserArticleQueryParam param,
			Pager pager) {
		QueryHelper query = param.createQuery();
		if (pager == null)
			// 按照 param 中指定的数量获取.
			return query.queryData(this.getSession(), -1, param.count);
		else
			return query.queryDataAndTotalCount(this.getSession(), pager);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.edustar.jitar.dao.ArticleDao#incArticleCommentCount(Comment, int)
	 */
	public void incArticleCommentCount(Comment comment, int count) {
	    HtmlArticleBase articleBase = this.getHtmlArticleBaseByArticleId(comment.getObjId());
	    if(null == articleBase) return;
	    articleBase.setCommentCount(articleBase.getCommentCount() + count);
	    this.getSession().saveOrUpdate(articleBase);
	    
	    String sql = "UPDATE " + articleBase.getTableName() + " SET commentCount = commentCount + :commentCount, starCount = starCount + :starCount WHERE articleId = :articleId";
	    	    
		// 增加评论星级
		//String hql = "UPDATE Article SET commentCount = commentCount + ?, starCount = starCount + ? WHERE articleId = ? ";

		if (count > 0)
			this.getSession().createSQLQuery(sql).setInteger("commentCount", count).setInteger("starCount", comment.getStar()).setInteger("articleId", comment.getObjId()).executeUpdate();
		
		else
			this.getSession().createSQLQuery(sql).setInteger("commentCount", count).setInteger("starCount", -1 * comment.getStar()).setInteger("articleId", comment.getObjId()).executeUpdate();
			
		if (comment.getUserId() != null) {
			String hql = "UPDATE User SET commentCount = commentCount + :commentCount WHERE userId = :userId";
			this.getSession().createQuery(hql).setInteger("commentCount", count).setInteger("userId", comment.getUserId()).executeUpdate();			
		}
		this.getSession().flush();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.edustar.jitar.dao.ArticleDao#batchClearArticleUserCategory(int)
	 */
	public int batchClearArticleUserCategory(int categoryId) {
		String hql = "UPDATE Article SET userCateId = NULL WHERE userCateId = :userCateId ";
		return this.getSession().createQuery(hql).setInteger("userCateId", categoryId).executeUpdate();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.edustar.jitar.dao.ArticleDao#increaseViewCount(int, int)
	 */
	public void increaseViewCount(final int articleId, final int count) {
	    //先更新基础表
	    HtmlArticleBase articleBase = this.getHtmlArticleBaseByArticleId(articleId);
	    if(null == articleBase) return;
	    articleBase.setViewCount(articleBase.getViewCount() + count);
	    this.getSession().saveOrUpdate(articleBase);
	    
	    //再更新拆分表
	    String sql = "UPDATE " + articleBase.getTableName() + " SET viewCount = viewCount + " + count + " WHERE articleId =" + articleId;
	    this.getSession().createSQLQuery(sql).executeUpdate();

		//viewcount_svc.incViewCount(ObjectType.OBJECT_TYPE_ARTICLE.getTypeId(), articleId, 1);
	}

	/*
	 * 更新用户表中的文章数、更新文章表中的评论数.
	 * 
	 * @see
	 * cn.edustar.jitar.dao.ArticleDao#statForUser(cn.edustar.jitar.pojos.User)
	 */
	public Object statForUser(User user) {
		int userArticleCount = statUserArticleCount(user);	
		this.getSession().createQuery("UPDATE User SET articleCount =:articleCount where userId =:userId").setInteger("articleCount", userArticleCount).setInteger("userId", user.getUserId()).executeUpdate();
		@SuppressWarnings("unused")
		int articleCommentCount = statArticleCommentCount(user);
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cn.edustar.jitar.dao.ArticleDao#statComment(cn.edustar.jitar.pojos.User)
	 */
	public void statComment(User user) {
		statArticleComment(user);
	}

	@SuppressWarnings("unchecked")
	private void statArticleComment(User user) {
		List<Object[]> count_list = this.getSession().createQuery(STAT_ARTICLE_COMMENT).setInteger(0, user.getUserId()).list();
		for (Object[] oa : count_list) {
			Integer articleId = CommonUtil.safeXtransHiberInteger(oa[0]);
			Integer count = CommonUtil.safeXtransHiberInteger(oa[1]);
			if (count == null)
				count = 0;
			if (articleId != null) {
				this.getSession().createQuery(UPDATE_ARTICLE_COMMENT_COUNT).setInteger("commentCount", count).setInteger("articleId", articleId).executeUpdate();
			}
		}
	}

	/**
	 * 更新用户表中的文章数.
	 * 
	 * @param user
	 * @return
	 */
	private int statUserArticleCount(User user) {
		Object o = this.getSession().createQuery(STAT_USER_All_ARTICLE_COUNT).setInteger(0, user.getUserId()).uniqueResult();
		if(o == null) return 0;
		return Integer.valueOf(o.toString()).intValue();
		//return getSession().executeIntScalar(	STAT_USER_All_ARTICLE_COUNT, user.getUserId());
	}

	/**
	 * 更新文章表中的评论数.
	 * 
	 * @param user
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private int statArticleCommentCount(User user) {
		List<Object[]> count_list = this.getSession().createQuery(STAT_ARTICLE_COMMENT_COUNT).setInteger(0, user.getUserId()).list();
		for (Object[] oa : count_list) {
			Integer articleId = CommonUtil.safeXtransHiberInteger(oa[0]);
			Integer count = CommonUtil.safeXtransHiberInteger(oa[1]);
			if (count == null)
				count = 0;
			if (articleId != null) {
				this.getSession().createQuery(UPDATE_ARTICLE_COMMENT_COUNT).setInteger("commentCount", count).setInteger("articleId", articleId).executeUpdate();
			}
		}
		return 0;
	}

	//新版的更新
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void moveArticleCategory2(int articleId,Integer userCateId,Integer sysCateId)
	{
		String queryString = null;
		HtmlArticleBase baseArticle = (HtmlArticleBase) this.getSession().get(HtmlArticleBase.class, articleId);
		if (baseArticle == null) {
			return;
		}	
	
		queryString = "UPDATE HtmlArticleBase SET SysCateId = " + sysCateId + ",UserCateId = " + userCateId + " WHERE articleId = " + articleId;
		this.getSession().createQuery(queryString).executeUpdate();
		
		String tableName = baseArticle.getTableName();
		
		final String sql = "UPDATE " + tableName + " SET SysCateId = " + sysCateId + ",UserCateId = " + userCateId + " WHERE articleId = " + articleId;
		this.getSession().doWork(new Work() {
			public void execute(Connection connection)
					throws SQLException {
				connection.createStatement().executeUpdate(sql);
			}
		});
		
		
		// 重新计算分类,并更新分类表
		int intItemNum = statArticleCountForCategory2(articleId,userCateId);
		if(intItemNum == 0) return;
		this.getSession().createQuery("UPDATE Category SET itemNum =:itemNum WHERE categoryId=:categoryId")
		.setInteger("itemNum", intItemNum)
		.setInteger("categoryId", userCateId)
		.executeUpdate();
				
	}
	
	/**
	 * 得到个人分类下的文章数
	 */
	public void updateUserCategoryArticleCount(Integer userCateId){
	    int articleCount = 0;
	    if(userCateId == null) return;
	    String hql = "SELECT COUNT(*) FROM HtmlArticleBase WHERE userCateId = :userCateId";
	    articleCount = Integer.valueOf(this.getSession().createQuery(hql).setInteger("userCateId", userCateId).uniqueResult().toString()).intValue();
	    hql = "UPDATE Category SET itemNum = :itemNum WHERE categoryId=:categoryId";
	    this.getSession().createQuery(hql).setInteger("itemNum", articleCount).setInteger("categoryId",userCateId).executeUpdate();
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cn.edustar.jitar.dao.ArticleDao#updateArticleCategory(cn.edustar.jitar
	 * .pojos.Article)
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void updateArticleCategory(Article article) {		
		HtmlArticleBase baseArticle = (HtmlArticleBase)this.getSession().get(HtmlArticleBase.class, article.getArticleId());
		if(baseArticle == null) return;
		baseArticle.setUserCateId(article.getUserCateId());
		baseArticle.setSysCateId(article.getSysCateId());
		this.getSession().update(baseArticle);
		
		String tableName = baseArticle.getTableName();
		//System.out.println("getTableName=" + tableName);
		
		final String sql = "UPDATE " + tableName + " SET SysCateId = " + article.getSysCateId() + ",UserCateId="+article.getUserCateId()+" WHERE articleId = " + article.getArticleId();
		
		baseArticle = null;
		
		this.getSession().doWork(new Work() {
			public void execute(Connection connection)
					throws SQLException {
				connection.createStatement().execute(sql);
			}
		});
		
		
		// 重新计算分类,并更新分类表
		int intItemNum = statArticleCountForCategory(article);
		this.getSession().createQuery("UPDATE Category SET itemNum =:itemNum WHERE categoryId=:categoryId")
		.setInteger("itemNum", intItemNum)
		.setInteger("categoryId", article.getUserCateId() )
		.executeUpdate();
	}

	/**
	 * @param article
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private int statArticleCountForCategory(Article article) {
		if(article.getUserCateId() == null) return 0;
		final String sql = "SELECT COUNT(*) FROM HtmlArticleBase WHERE userCateId =" + article.getUserCateId();
		return this.getSession().doReturningWork(new ReturningWork<Integer>() {							
				public Integer execute(Connection connection)
						throws SQLException {
					PreparedStatement cs = connection.prepareStatement(sql,ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);		
					ResultSet rs = cs.executeQuery();
					int ret = 0;
					if(rs != null && rs.next())
					{
						ret = rs.getInt(1);
					}
					rs.close();
					return ret;
				}
			});
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private int statArticleCountForCategory2(int articleId, Integer userCateId) {
		if(userCateId == null) return 0;
		final String sql = "SELECT COUNT(*) FROM HtmlArticleBase WHERE userCateId =" + userCateId;
		return this.getSession().doReturningWork(new ReturningWork<Integer>(){ 
								
				public Integer execute(Connection connection)
						throws SQLException {
					PreparedStatement cs = connection.prepareStatement(sql,ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);		
					ResultSet rs = cs.executeQuery();
					int ret = 0;
					if(rs != null && rs.next())
					{
						ret = rs.getInt(1);
					}
					rs.close();
					return ret;
				}
			});
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cn.edustar.jitar.dao.ArticleDao#moveArticleSysCate(cn.edustar.jitar.pojos
	 * .Article, java.lang.Integer)
	 */
	public void moveArticleSysCate(Article article, Integer sysCateId) {
		String hql = "UPDATE HtmlArticleBase SET sysCateId =:sysCateId WHERE articleId =:articleId";
		this.getSession().createQuery(hql).setInteger("sysCateId", sysCateId).setInteger("articleId", article.getArticleId()).executeUpdate();
		//getSession().bulkUpdate(hql,new Object[] { sysCateId, article.getArticleId() });
		
		hql = "UPDATE Article SET sysCateId =:sysCateId WHERE articleId =:articleId";
		this.getSession().createQuery(hql).setInteger("sysCateId", sysCateId).setInteger("articleId", article.getArticleId()).executeUpdate();
		//getSession().bulkUpdate(hql,new Object[] { sysCateId, article.getArticleId() });
	}

	@SuppressWarnings("rawtypes")
	public List getArticleList_Order_By_CommentNums(int days, int topNum) {
		String hql = "";
		if (days > 0) {
			hql = "SELECT new Map( a.articleId as articleId, a.title as title, a.createDate as createDate, a.articleAbstract as articleAbstract, a.typeState as typeState, u.userId as userId, u.userIcon as userIcon, u.nickName as nickName, u.loginName as loginName,Sum(c.id) as commentCount) FROM Article a,User u,Comment c Where a.userId = u.userId and c.objId=a.articleId and c.objType=3 and DateDiff(day,c.createDate,getdate())<="
					+ days
					+ " AND a.auditState = "
					+ Article.AUDIT_STATE_OK
					+ // 审核通过.
					" AND a.draftState = false "
					+ // 非草稿.
					" AND a.delState = false "
					+ // 非删除.
					" AND a.hideState = 0 "
					+ // 非隐藏的
					"  GROUP BY a.articleId, a.title, a.createDate, a.articleAbstract, u.userId, u.userIcon, u.nickName, u.loginName,a.typeState  Order By Sum(c.id) DESC";
			Query query = this.getSession().createQuery(hql);
			query.setMaxResults(topNum);
			return query.list();
		} else {
			hql = "SELECT new Map( a.articleId as articleId, a.title as title, a.createDate as createDate, a.articleAbstract as articleAbstract, a.typeState as typeState, u.userId as userId, u.userIcon as userIcon, u.nickName as nickName, u.loginName as loginName,Sum(c.id) as commentCount) FROM Article a,User u,Comment c Where a.userId = u.userId and c.objId=a.articleId and c.objType=3 and DateDiff(day,c.createDate,getdate())<="
					+ days
					+ " AND a.auditState = " + Article.AUDIT_STATE_OK + // 审核通过.
					" AND a.draftState = false "
					+ // 非草稿.
					" AND a.delState = false "
					+ // 非删除.
					" AND a.hideState = 0 "
					+ // 非隐藏的
					"  GROUP BY a.articleId, a.title, a.createDate, a.articleAbstract, u.userId, u.userIcon, u.nickName, u.loginName,a.typeState  Order By Sum(c.id) DESC";
			List result = this.getSession().createQuery(hql).list();
			if (result == null || result.size() == 0)
				return null;
			else
				return result;
		}
	}

	
	@SuppressWarnings("unchecked")
	public List<WeekCommentArticle> getWeekCommentArticleList()
	{
		Query q = this.getSession().createQuery("FROM WeekCommentArticle ORDER BY commentCount DESC");
		q.setFirstResult(0);
		q.setMaxResults(11);
		return (List<WeekCommentArticle>)q.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<WeekViewCountArticle> getWeekViewCountArticleList()
	{
		Query q = this.getSession().createQuery("FROM WeekViewCountArticle ORDER BY viewCount DESC");
		q.setFirstResult(0);
		q.setMaxResults(7);
		return (List<WeekViewCountArticle>)q.list();
	}
	
	@SuppressWarnings("unchecked")
    public List<WeekViewCountArticle> getWeekViewCountArticleList(int topCount)
    {
        Query q = this.getSession().createQuery("FROM WeekViewCountArticle ORDER BY viewCount DESC");
        q.setFirstResult(0);
        q.setMaxResults(topCount);
        return (List<WeekViewCountArticle>)q.list();
    }
	public void setPushState(Article article, int pushState) {
		String queryString = "UPDATE Article SET pushState = :pushState WHERE articleId = :articleId";
		this.getSession().createQuery(queryString)
		.setInteger("pushState", pushState)
		.setInteger("articleId", article.getArticleId())
		.executeUpdate();
	}

	@SuppressWarnings("unchecked")
	public List<Article> getAllUserArticle(int UserId) {
		String queryString = "FROM Article Where userId=:userId";
		return (List<Article>) this.getSession().createQuery(queryString).setInteger("userId", UserId).list();
	}
	public HtmlArticleBase getHtmlArticleBaseByArticleId(int articleId) {		
		return (HtmlArticleBase)this.getSession().get(HtmlArticleBase.class, articleId);
	}


	@SuppressWarnings("unchecked")
	public HtmlArticleBase getHtmlArticleBaseByArticleGuid(String articleGuid) {
		String queryString = "From HtmlArticleBase Where articleGuid=:articleGuid";
		List<HtmlArticleBase> ls = (List<HtmlArticleBase>)this.getSession().createQuery(queryString).setString("articleGuid", articleGuid).list();
		if(ls.size() == 0) return null;
		return (HtmlArticleBase)ls.get(0);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void deleteArticleWithRelativeData(int articleId, int year)
	{
		final int aId = articleId;
		final int dataYear = year;
		
		this.getSession().doWork(new Work() {
			public void execute(Connection connection)
					throws SQLException {
				CallableStatement cs = connection.prepareCall("{Call DeleteArticleById(?,?)}");							
				cs.setInt(1, aId);
				cs.setInt(2, dataYear);
				cs.execute();
			}
		});
	}
	
	private void copyData(Article article,HtmlArticleBase htmlArticleBase)
	{
		if(article == null || htmlArticleBase == null) return;
		htmlArticleBase.setTitle(article.getTitle());
		htmlArticleBase.setAuditState(article.getAuditState());
		htmlArticleBase.setBestState(article.getBestState());
		htmlArticleBase.setCommentCount(article.getCommentCount());
		htmlArticleBase.setDelState(article.getDelState());
		htmlArticleBase.setDraftState(article.getDraftState());
		htmlArticleBase.setHideState(article.getHideState());
		htmlArticleBase.setLoginName(article.getLoginName());
		htmlArticleBase.setRecommendState(article.getRecommendState());
		htmlArticleBase.setSysCateId(article.getSysCateId());
		htmlArticleBase.setTypeState(article.getTypeState());
		htmlArticleBase.setUserCateId(article.getUserCateId());
		htmlArticleBase.setUserId(article.getUserId());
		htmlArticleBase.setViewCount(article.getViewCount());
	}
	
	
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void addDigg(int articleId)
	{
		HtmlArticleBase hba = this.getHtmlArticleBaseByArticleId(articleId);
		if(hba == null) return;
		String tableName = hba.getTableName();
		final String sql =  "UPDATE " + tableName + " SET Digg = Digg + 1 Where articleId = " + articleId;
		
		this.getSession().doWork(new Work() {
			public void execute(Connection connection)
					throws SQLException {
				connection.createStatement().execute(sql);//原来是 prepareStatement.execute();
			}
		});
	}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void addTrample(int articleId)
	{
		HtmlArticleBase hba = this.getHtmlArticleBaseByArticleId(articleId);
		if(hba == null) return;
		String tableName = hba.getTableName();
		final String sql =  "UPDATE " + tableName + " SET Trample = Trample + 1 Where articleId = " + articleId;
		this.getSession().doWork(new Work() {
			public void execute(Connection connection)
					throws SQLException {
				connection.createStatement().execute(sql);//原来是prepareStatement.execute();
			}
		});
						
	}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public int getDigg(int articleId)
	{
		HtmlArticleBase hba = this.getHtmlArticleBaseByArticleId(articleId);
		if(hba == null) return 0;
		String tableName = hba.getTableName();
		
		final String sql = "SELECT Digg FROM " + tableName + " WHERE ArticleId=" + articleId;
		return this.getSession().doReturningWork(new ReturningWork<Integer>()
				{							
					public Integer execute(Connection connection) throws SQLException {								
						PreparedStatement cs = connection.prepareStatement(sql,ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);		
						ResultSet rs = cs.executeQuery();
						int ret = 0;
						if(rs != null && rs.next())
						{
							ret = rs.getInt(1);
						}
						rs.close();
						return ret;
					}
				});
						
	}

	public int getTrample(int articleId)
	{
		HtmlArticleBase hba = this.getHtmlArticleBaseByArticleId(articleId);
		if(hba == null) return 0;
		String tableName = hba.getTableName();
		
		final String sql = "SELECT Trample FROM " + tableName + " WHERE ArticleId=" + articleId;
		return this.getSession().doReturningWork(new ReturningWork<Integer>(){
							public Integer execute(Connection connection)
									throws SQLException {
								PreparedStatement cs = connection.prepareStatement(sql,ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);		
								ResultSet rs = cs.executeQuery();
								int ret = 0;
								if(rs != null && rs.next())
								{
									ret = rs.getInt(1);
								}
								rs.close();
								return ret;
							}
						});
	}

	
	@SuppressWarnings("unchecked")
    public List<Article> getHotArticleList(int topCount){
	    String query = "FROM Article WHERE auditState = 0 And DateDiff(day,createDate,getdate()) < 10 ORDER BY viewCount DESC";
	    return (List<Article>)this.getSession().createQuery(query).setFirstResult(0).setMaxResults(topCount).list();
	}
	@Override
	public void evict(Object object) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void flush() {
		// TODO Auto-generated method stub
		
	}
}


