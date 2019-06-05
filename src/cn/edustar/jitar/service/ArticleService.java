package cn.edustar.jitar.service;

import java.util.List;

import cn.edustar.data.DataTable;
import cn.edustar.data.Pager;
import cn.edustar.data.Tuple;
import cn.edustar.jitar.model.ArticleModelEx;
import cn.edustar.jitar.pojos.Article;
import cn.edustar.jitar.pojos.Comment;
import cn.edustar.jitar.pojos.HtmlArticleBase;
import cn.edustar.jitar.pojos.User;
import cn.edustar.jitar.pojos.WeekCommentArticle;
import cn.edustar.jitar.pojos.WeekViewCountArticle;

/**
 * 获取用户文章的服务接口定义
 * 
 * @author Yang Xinxin
 * @version 1.0.0 Apr 16, 2008 10:11:22 AM
 */
public interface ArticleService {
	
	/** 事件: 文章即将创建, 事件对象为 Article 对象 */
	public static final String EVENT_ARTICLE_CREATING = "jitar.article.creating";

	/** 事件: 文章被创建, 事件对象为 Article 对象 */
	public static final String EVENT_ARTICLE_CREATED = "jitar.article.created";

	/**
	 * 根据ID获得记录
	 * 
	 * @param articleId
	 * @return
	 */
	public Article getArticle(int articleId);

	/**
	 * 得到指定标识的文章及其发表者
	 * 
	 * @param articleId
	 * @return
	 */
	public Tuple<Article, User> getArticleAndUser(int articleId);

	/**
	 * 创建一篇文章
	 * 
	 * @param article
	 */
	public void createArticle(Article article);

	/**
	 * 修改一篇博文
	 * 
	 * @param article
	 */
	public void updateArticle(Article article);

	/**
	 * 得到指定查询条件和分页选项的文章列表
	 * 
	 * @param param
	 * @param pager
	 * @return 包装好的文章模型对象
	 */
	public List<ArticleModelEx> getArticleList(ArticleQueryParam param, Pager pager);

	/**
	 * 得到指定查询条件和分页选项的文章数据表
	 * 
	 * @param param
	 * @param pager
	 * @return
	 */
	public DataTable getUserArticleDataTable(UserArticleQueryParam param, Pager pager);

	/**
	 * 逻辑上删除文章, 仅设置文章的状态为已删除
	 * 
	 * @param article
	 */
	public void deleteArticle(Article article);

	/**
	 * 审核通过文章, 设置其审核状态 = OK, 并更新该分类,用户等文章统计信息
	 * 
	 * @param article
	 */
	public void auditArticle(Article article);

	/**
	 * 取消一个文章的审核, 设置其审核状态 = WAIT_AUDIT
	 * 
	 * @param article
	 */
	public void unauditArticle(Article article);

	/**
	 * 设置一个文章为推荐文章
	 * 
	 * @param article
	 */
	public void rcmdArticle(Article article);

	/**
	 * 取消文章的推荐文章状态
	 * 
	 * @param article
	 */
	public void unrcmdArticle(Article article);

	/**
	 * 设置一个文章为精华文章
	 * 
	 * @param article
	 */
	public void bestArticle(Article article);

	/**
	 * 取消精华文章
	 * 
	 * @param article
	 */
	public void unbestArticle(Article article);

	/**
	 * 隐藏一个文章
	 * 
	 * @param article
	 */
	public void hideArticle(Article article);

	/**
	 * 取消文章的隐藏
	 * 
	 * @param article
	 */
	public void unhideArticle(Article article);

	/**
	 * 设置一个文章为草稿状态
	 * 
	 * @param article
	 */
	public void draftArticle(Article article);

	/**
	 * 设置一个文章为非草稿状态(正式发布状态)
	 * 
	 * @param article
	 */
	public void undraftArticle(Article article);

	/**
	 * 设置为合法文章
	 * 
	 * @param article
	 */
	public void validArticle(Article article);

	/**
	 * 设置为非法文章
	 * 
	 * @param article
	 */
	public void invalidArticle(Article article);

	/**
	 * 恢复一篇文章为非删除状态
	 * 
	 * @param article
	 */
	public void recoverArticle(Article article);

	/**
	 * 彻底删除一篇文章, 包括其评论、标签、发布到的协作组等
	 * 
	 * @param article
	 */
	public void crashArticle(Article article);

	/**
	 * 给指定文章创建一个评论
	 * 
	 * @param article
	 * @param comment
	 */
	public void createArticleComment(Article article, Comment comment);

	/**
	 * 将使用指定用户分类标识的文章的用户分类设置为 null, 该分类将被删除
	 * 
	 * @param categoryId
	 */
	public void batchClearArticleUserCategory(int categoryId);

	/**
	 * 将指定的文章访问数增加/减少 count 个
	 * 
	 * @param articleId
	 * @param count
	 */
	public void increaseViewCount(int articleId, int count);

	/**
	 * 增加/减少文章评论数量
	 * 
	 * @param comment
	 * @param count
	 */
	public void incArticleCommentCount(Comment comment, int count);

	/**
	 * 更新用户表中的文章数、更新文章表中的评论数
	 * 
	 * @param user
	 * @return
	 */
	public Object statForUser(User user);

	/**
	 * 更新文章评论的统计
	 * 
	 * @param user
	 */
	public void statComment(User user);

	/**
	 * 移动指定文章的分类
	 * 
	 * @param article
	 */
	public void moveArticleCategory(Article article);
	public void moveArticleCategory2(int articleId,Integer userCateId,Integer sysCateId);

	/**
	 * 移动文章分类
	 * 
	 * @param article
	 * @param sysCateId
	 */
	public void moveArticleSysCate(Article article, Integer sysCateId);

	/**
	 * @param days
	 * @param topNum
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public List getArticleList_Order_By_CommentNums(int days, int topNum);
	
	public List<WeekCommentArticle> getWeekCommentArticleList();
	public List<WeekViewCountArticle> getWeekViewCountArticleList();
	public List<WeekViewCountArticle> getWeekViewCountArticleList(int topCount);
	public void setPushState(Article article, int pushState);
	/**
	 * 得到今天上载的文章
	 * @param userId
	 * @return
	 */
	public List<Article> getTodayArticles(int userId);
	
	public List<Article> getAllUserArticle(int UserId);
	public HtmlArticleBase getHtmlArticleBaseByArticleId(int articleId);
	public HtmlArticleBase getHtmlArticleBaseByArticleGuid(String articleGuid);
	public void deleteArticleWithRelativeData(int articleId, int year);
	
	public void addDigg(int articleId);
	public void addTrample(int articleId);
	public int getDigg(int articleId);
	public int getTrample(int articleId);
	
	public void updateGroupArticleState(HtmlArticleBase htmlArticleBase);
	public void updateChannelArticleState(HtmlArticleBase htmlArticleBase);
	public void updateSpecialSubjectArticleState(HtmlArticleBase htmlArticleBase);
	public void updatePrepareCourseArticleState(HtmlArticleBase htmlArticleBase);
	public void updateGroupArticleState(Article article);
	public void updateChannelArticleState(Article article);
	public void updateSpecialSubjectArticleState(Article article);
	public void updatePrepareCourseArticleState(Article article);
	
	public List<Article> getHotArticleList(int topCount);
	
}
