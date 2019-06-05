package cn.edustar.jitar.service;

import java.util.List;

import cn.edustar.data.Pager;
import cn.edustar.jitar.model.ObjectType;
import cn.edustar.jitar.pojos.Comment;

/**
 * 评论服务
 */
public interface CommentService {
	
	/**
	 * 得到指定标识的评论
	 * 
	 * @param id
	 * @return
	 */
	public Comment getComment(int id);

	/**
	 * 删除指定评论
	 * 
	 * @param comment
	 */
	public void deleteComment(Comment comment);
	
	public void deleteCommentReply(Comment comment);
	

	/**
	 * 审核通过评论
	 * 
	 * @param comment
	 */
	public void auditComment(Comment comment);

	/**
	 * 取消审核指定评论
	 * 
	 * @param comment
	 */
	public void unauditComment(Comment comment);

	/**
	 * 得到指定查询条件下的评论
	 * 
	 * @param param
	 * @param pager
	 * @return
	 */
	public List<Comment> getCommentList(CommentQueryParam param, Pager pager);

	/**
	 * 保存评论
	 * 
	 * @param comment
	 */
	public void saveComment(Comment comment);

	/**
	 * 得到最新针对某用户发表的评论
	 * 
	 * @param userId
	 * @param count
	 * @return
	 */
	public List<Comment> getRecentCommentAboutUser(int userId, int count);

	/**
	 * 获得指定用户的文章评论列表, 按照时间逆序排列
	 * 
	 * @param userId 用户标识
	 * @param pager
	 * @param includeUnaduited true 表示包括未审核的; false 表示只获取审核过的
	 * @return 返回 List&lt;Object[Comment, Article]&gt; 集合
	 */
	public List<Object[]> getUserArticleCommentList(int userId, Pager pager, boolean includeUnaduited);
	public List<Object[]> getUserArticleCommentListEx(int userId, Pager pager, boolean includeUnaduited);

	/**
	 * 获得文章评论列表
	 * 
	 * @param param 查询条件
	 * @param pager
	 * @return 返回 List&lt;Comment&gt; 集合
	 */
	public List<Comment> getArticleCommentList(ArticleCommentQueryParam param, Pager pager);

	/**
	 * 获得资源评论列表.
	 * 
	 * @param param
	 * @param pager
	 * @return 返回 List&lt;Object[Comment, R]&gt; 集合
	 */
	public List<Object[]> getUserResourceCommentList(CommentQueryParam param, Pager pager);

	/**
	 * 删除指定对象的所有评论, 一般用于该对象将要被删除时
	 * 
	 * @param objType
	 * @param objId
	 */
	public void deleteCommentByObject(ObjectType objType, int objId);

	/**
	 * 统计指定用户的指定对象类型的评论数
	 * 
	 * @param param
	 * @return 返回 List&lt;Object[objectId, COUNT]&gt;
	 */
	public List<Object[]> statCommentCountByUserAndObject(CommentQueryParam param);
	
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
	
	/** 由于支持对评论的评论，本函数得到某个评论所在的顶层的评论 
	 *  例如：
	 *     对某文章的评论   0
	 *        |
	 *        +---对评论的回复  1
	 *            |
	 *            +---对这个评论的回复  2     
	 *                |
	 *     			  ......
	 *      	
	 *     topLevel = 0   则返回  对某文章的评论
	 *     topLevel = 1   则返回  对评论的回复 
	 *     其他不支持.
	 *     
	 * */
	public Comment getTopComment(int commentId, int topLevel);
	
    //查询相片的所有评论
	public List<Comment> getAllPhotoCommentByPhotoId(Integer photoId);	
}
