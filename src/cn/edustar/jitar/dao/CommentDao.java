package cn.edustar.jitar.dao;

import java.util.List;

import cn.edustar.data.Pager;
import cn.edustar.jitar.pojos.Comment;
import cn.edustar.jitar.service.ArticleCommentQueryParam;
import cn.edustar.jitar.service.CommentQueryParam;

/**
 * 评论的数据库访问接口定义.
 * 
 *
 */
public interface CommentDao extends Dao {
	/**
	 * 得到指定标识的评论.
	 * 
	 * @param id
	 * @return
	 */
	public Comment getComment(int id);

	/**
	 * 删除一个评论.
	 * 
	 * @param comment
	 */
	public void deleteComment(Comment comment);
	
	public void deleteCommentReply(Comment comment);

	/**
	 * 修改评论审核状态.
	 *
	 * @param comment
	 * @param audit
	 */
	public void updateCommentAuditState(Comment comment, boolean audit);

	/**
	 * 保存一个评论.
	 * 
	 * @param comment
	 */
	public void saveComment(Comment comment);

	/**
	 * 得到指定对象的评论.
	 * 
	 * @param obj_type
	 * @param obj_id
	 * @param pager
	 * @return
	 */
	public List<Comment> getCommentList(CommentQueryParam param, Pager pager);

	/**
	 * 得到最新针对某用户发表的评论.
	 * 
	 * @param userId
	 * @param count
	 * @return
	 */
	public List<Comment> getRecentCommentAboutUser(int userId, int count);
	
	/**
	 * 得到文章评论列表.
	 * 
	 * @param userId 该用户发表的文章的审核.
	 * @param pager 分页参数.
	 * @param includeUnaduited true 表示包括未审核的; false 表示只获取审核过的.
	 * @return 返回 List&lt;Object[Comment, Article]&gt; 集合
	 */
	public List<Object[]> getUserArticleCommentList(int userId, Pager pager, boolean includeUnaduited);
	
	public List<Object[]> getUserArticleCommentListEx(int userId, Pager pager, boolean includeUnaduited);

	/**
	 * 删除指定对象的所有评论.
	 * 
	 * @param objType
	 * @param objId
	 * @return 返回被删除的评论总数.
	 */
	public int deleteCommentByObject(int objType, int objId);

	/**
	 * 得到用户资源评论列表.
	 * 
	 * @param param 查询参数.
	 * @param pager
	 * @return 返回为 List&lt;Object[Comment, Resource]&gt; 集合.
	 */
	public List<Object[]> getUserResourceCommentList(CommentQueryParam param, Pager pager);

	/**
	 * 根据指定条件统计评论数, CommentQueryParam 中支持 userId, aboutUserId, objType, objId, audit.
	 * 
	 * @param param 统计查询参数.
	 * @return 返回 List&lt;Object[objectId, COUNT]&gt;.
	 */
	public List<Object[]> statCommentCountByUserAndObject(CommentQueryParam param);

	/**
	 * 得到指定条件下的文章评论.
	 * 
	 * @param param
	 * @param pager
	 * @return
	 */
	public List<Comment> getArticleCommentList(ArticleCommentQueryParam param, Pager pager);
	
	/**
	 * 视频评论列表
	 * 
	 * @param param
	 * @param pager
	 * @return
	 */
	public List<Object[]> getUserVideoCommentList(CommentQueryParam param, Pager pager);
	public List<Object[]> getUserVideoCommentListEx(CommentQueryParam param, Pager pager);
	
	public List<Comment> getAllCommentByUserId(int userId);
	
	
	/** 删除某个用户的所有相关评论 */
	public void deleteAllCommentByUserId(int userId);
	
	public List<Comment> getCommentListByObject(int objType, int objId);

	public List<Comment> getAllPhotoCommentByPhotoId(Integer photoId);
}
