package cn.edustar.jitar.dao;

import java.util.List;

import cn.edustar.data.Pager;
import cn.edustar.data.Tuple;
import cn.edustar.jitar.pojos.Article;
import cn.edustar.jitar.pojos.Comment;
import cn.edustar.jitar.pojos.HtmlArticleBase;
import cn.edustar.jitar.pojos.User;
import cn.edustar.jitar.pojos.WeekCommentArticle;
import cn.edustar.jitar.pojos.WeekViewCountArticle;
import cn.edustar.jitar.service.ArticleQueryParam;
import cn.edustar.jitar.service.UserArticleQueryParam;

/**
 * DAO的接口类.
 * 
 * @author Yang Xinxin
 */
public interface ArticleDao extends Dao {
	/**
	 * 根据ID获得记录.
	 * 
	 * @param articleId
	 * @return
	 */
	public Article getArticle(int articleId);
	/**
	 * 得到今天的上载文章
	 * @return
	 */
	public List<Article> getTodayArticles(int userId);
	/**
	 * 得到指定标识的文章及其发表者.
	 * @param articleId
	 * @return
	 */
	public Tuple<Article, User> getArticleAndUser(int articleId);

	/**
	 * 创建文章对象.
	 * @param article
	 */
	public void createArticle(Article article);
	
	/**
	 * 修改文章对象.
	 * @param article
	 */
	public void updateArticle(Article article);
	
	/**
	 * 彻底删除掉一篇文章, 包括:
	 *   1. 被引用到的群组中的相关记录.
	 *   2. 文章本身.
	 * @param article
	 */
	public void crashArticle(Article article);

	/**
	 * 按照指定查询条件和分页选项查询文章.
	 * 
	 * @param param
	 * @param pager
	 * @return 返回文章列表 List&lt;Article&gt;
	 */
	public List<Article> getArticleList(ArticleQueryParam param, Pager pager);

	/**
	 * 得到指定查询条件和分页选项的文章数据表.
	 * @param param
	 * @param pager
	 * @return
	 */
	public List<Object[]> getUserArticleDataTable(UserArticleQueryParam param, Pager pager);

	/**
	 * 统计指定用户发表的文章数量, 只包括审核通过、非草稿的、非删除的、合法的.
	 * 
	 * @param userId
	 * @return 返回该用户发表的文章数量
	 */
	public int getUserArticleCount(int userId);

	/**
	 * 统计指定系统分类下文章数量, 只包括审核通过、非草稿的、非删除的、合法的.
	 * 
	 * @param systemCategoryId
	 * @return
	 */
	public int getSystemCategoryArticleCount(int systemCategoryId);

	/**
	 * 设置/更新文章的删除标志.
	 * 
	 * @param article - 文章对象.
	 * @param delstate - 删除状态.
	 * 
	 * @return 返回更新的记录数量，正常应该返回 1; 否则肯定有问题, 例如文章不存在.
	 * 
	 */
	public int updateArticleDeleteState(Article article, boolean delState);

	/**
	 * 设置/更新文章的审核标志.
	 * 
	 * @param article - 文章对象.
	 * @param auditState - 审核状态值
	 * 
	 * @return 返回更新的记录数量 = 1 应该是正常; 否则可能文章不存在??
	 */
	public int updateArticleAuditState(Article article, short auditState);

	/**
	 * 设置/取消文章的推荐状态.
	 * @param article - 文章对象.
	 * @param rcmdState
	 * @return
	 */
	public int updateArticleRcmdState(Article article, boolean rcmdState);
	
	/**
	 * 设置/更新文章的精华标志.
	 * 
	 * @param article - 文章对象.
	 * @param bestState - 精华状态.
	 * 
	 * @return 返回更新的记录数量 = 1 应该是正常; 否则可能文章不存在??
	 */
	public int updateArticleBestState(Article article, boolean bestState);

	/**
	 * 设置文章的草稿状态标志.
	 * 
	 * @param article - 文章对象.
	 * @param draftState - 草稿状态.
	 * @return 返回更新的记录数量 = 1 应该是正常; 否则可能文章不存在??
	 */
	public int updateArticleDraftState(Article article, boolean draftState);

	/**
	 * 设置/更新文章的隐藏状态.
	 * 
	 * @param article - 文章对象.
	 * @param hideState - 隐藏状态.
	 * @return
	 */
	public int updateArticleHideState(Article article, short hideState);

	

	/**
	 * 相对增加或减少文章评论数.
	 * 
	 * @param comment - 文章的评论对象.
	 * @param count 为正表示增加数量, 为负表示减少数量, 为 0 不操作.
	 */
	public void incArticleCommentCount(Comment comment, int count);

	/**
	 * 将使用指定用户分类标识的文章的用户分类设置为 null, 该分类将被删除.
	 * 
	 * @param categoryId
	 */
	public int batchClearArticleUserCategory(int categoryId);

	/**
	 * 将指定的文章访问数增加/减少 count 个.
	 * 
	 * @param articleId - 文章标识.
	 * @param count - 增加/减少数量.
	 */
	public void increaseViewCount(int articleId, int count);
	
	/**
	 * 更新用户表中的文章数、更新文章表中的评论数.
	 *
	 * @param user
	 * @return
	 */
	public Object statForUser(User user);
	
	/**
	 * 更新文章评论的统计.
	 *
	 * @param user
	 */
	public void statComment(User user);
	
	public void setPushState(Article article, int pushState);
	
	/**
	 * 移动指定文章的分类.
	 * @param article
	 */
	public void updateArticleCategory(Article article);
	public void moveArticleCategory2(int articleId,Integer userCateId,Integer sysCateId);
	
	public void updateUserCategoryArticleCount(Integer userCateId);
	
	/**
	 * 移动文章分类
	 *
	 * @param article
	 * @param sysCateId
	 */
	public void moveArticleSysCate(Article article, Integer sysCateId);
	
	/**
	 * 
	 *
	 * @param days
	 * @param topNum
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public List getArticleList_Order_By_CommentNums(int days,int topNum);
	public List<WeekCommentArticle> getWeekCommentArticleList();
	public List<WeekViewCountArticle> getWeekViewCountArticleList();
	public List<WeekViewCountArticle> getWeekViewCountArticleList(int topCount);
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
