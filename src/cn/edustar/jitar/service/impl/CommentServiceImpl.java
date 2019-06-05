package cn.edustar.jitar.service.impl;

import java.util.List;

import cn.edustar.data.Pager;
import cn.edustar.jitar.dao.CommentDao;
import cn.edustar.jitar.dao.ArticleDao;
import cn.edustar.jitar.dao.PhotoDao;
import cn.edustar.jitar.dao.ResourceDao;
import cn.edustar.jitar.model.ObjectType;
import cn.edustar.jitar.pojos.Comment;
import cn.edustar.jitar.service.ArticleCommentQueryParam;
import cn.edustar.jitar.service.CommentQueryParam;
import cn.edustar.jitar.service.CommentService;

/**
 * CommentService 的实现.
 * 
 *
 */
public class CommentServiceImpl implements CommentService {
	
	/** 数据访问对象 */
	private CommentDao cmt_dao;
	private ArticleDao art_dao;
	private ResourceDao res_dao;
	private PhotoDao photo_dao;
	
	public void setPhotoDao(PhotoDao photo_dao) {
		this.photo_dao = photo_dao;
	}
	
	/** 数据访问对象 */
	public void setCommentDao(CommentDao cmt_dao) {
		this.cmt_dao = cmt_dao;
	}

	public void setArticleDao(ArticleDao art_dao) {
		this.art_dao = art_dao;
	}

	public void setResourceDao(ResourceDao res_dao) {
		this.res_dao = res_dao;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.edustar.jitar.service.CommentService#getComment(int)
	 */
	public Comment getComment(int id) {
		return cmt_dao.getComment(id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.edustar.jitar.service.CommentService#deleteComment(cn.edustar.jitar.pojos.Comment)
	 */
	public void deleteComment(Comment comment) {
		cmt_dao.deleteComment(comment);
		
		//判断是文章评论还是资源的评论
		if(comment.getObjType()==3)
		{
			//文章的评论数量-1
			art_dao.incArticleCommentCount(comment, -1);
		}
		if(comment.getObjType()==12)
		{
			//重新计算资源的评论数量
			res_dao.incResourceCommentCount(comment.getObjId());
		}
		if(comment.getObjType() == 11)
		{
			photo_dao.reCountComment(comment.getObjId());
		}
		
	}
	
	public void deleteCommentReply(Comment comment){
	    cmt_dao.deleteCommentReply(comment);
	}

	public void auditComment(Comment comment) {
		cmt_dao.updateCommentAuditState(comment, true);
	}

	public void unauditComment(Comment comment) {
		cmt_dao.updateCommentAuditState(comment, false);
	}

	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.service.CommentService#getCommentList(cn.edustar.jitar.service.CommentQueryParam, cn.edustar.data.Pager)
	 */
	public List<Comment> getCommentList(CommentQueryParam param, Pager pager) {
		List<Comment> list = cmt_dao.getCommentList(param, pager);
		return list;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.edustar.jitar.service.iface.CommentService#saveComment(cn.edustar.jitar.pojos.Comment)
	 */
	public void saveComment(Comment comment) {
		cmt_dao.saveComment(comment);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.edustar.jitar.service.CommentService#getRecentCommentAboutUser(int, int)
	 */
	public List<Comment> getRecentCommentAboutUser(int userId, int count) {
		return cmt_dao.getRecentCommentAboutUser(userId, count);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.edustar.jitar.service.CommentService#getUserArticleCommentList(int, cn.edustar.data.Pager)
	 */
	public List<Object[]> getUserArticleCommentList(int userId, Pager pager, boolean includeUnaduited) {
		return cmt_dao.getUserArticleCommentList(userId, pager, includeUnaduited);
	}
	public List<Object[]> getUserArticleCommentListEx(int userId, Pager pager, boolean includeUnaduited) {
		return cmt_dao.getUserArticleCommentListEx(userId, pager, includeUnaduited);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.edustar.jitar.service.CommentService#deleteCommentByObject(cn.edustar.jitar.model.ObjectType, int)
	 */
	public void deleteCommentByObject(ObjectType objType, int objId) {
		this.cmt_dao.deleteCommentByObject(objType.getTypeId(), objId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.edustar.jitar.service.CommentService#getUserResourceCommentList(cn.edustar.jitar.service.CommentQueryParam, cn.edustar.data.Pager)
	 */
	public List<Object[]> getUserResourceCommentList(CommentQueryParam param, Pager pager) {
		List<Object[]> list = cmt_dao.getUserResourceCommentList(param, pager);
		return list;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.edustar.jitar.service.CommentService#statCommentCountByUserAndObject(cn.edustar.jitar.service.CommentQueryParam)
	 */
	public List<Object[]> statCommentCountByUserAndObject(CommentQueryParam param) {
		return cmt_dao.statCommentCountByUserAndObject(param);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.edustar.jitar.service.CommentService#getArticleCommentList(cn.edustar.jitar.service.ArticleCommentQueryParam, cn.edustar.data.Pager)
	 */
	public List<Comment> getArticleCommentList(ArticleCommentQueryParam param, Pager pager) {
		return cmt_dao.getArticleCommentList(param, pager);
	}
	
	/* (non-Javadoc)
	 * 
	 * @see cn.edustar.jitar.service.CommentService#getUserVideoCommentList(cn.edustar.jitar.service.CommentQueryParam, cn.edustar.data.Pager)
	 */
	public List<Object[]> getUserVideoCommentList(CommentQueryParam param, Pager pager) {
		return cmt_dao.getUserVideoCommentList(param, pager);
	}
	public List<Object[]> getUserVideoCommentListEx(CommentQueryParam param, Pager pager) {
		return cmt_dao.getUserVideoCommentListEx(param, pager);
	}
	
	public List<Comment> getAllCommentByUserId(int userId)
	{
		return this.cmt_dao.getAllCommentByUserId(userId);
	}
	
	
	
	/** 删除某个用户的所有相关评论 */
	public void deleteAllCommentByUserId(int userId)
	{
		this.cmt_dao.deleteAllCommentByUserId(userId);
	}
	
	public List<Comment> getCommentListByObject(int objType, int objId){
	    return this.cmt_dao.getCommentListByObject(objType, objId);
	}
	
	
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
	public Comment getTopComment(int commentId, int topLevel){
		Comment currentComment = getComment(commentId);
		if(null == currentComment){
			return null;
		}
		if(currentComment.getObjType() != ObjectType.OBJECT_TYPE_COMMENT.getTypeId()){
			//当前评论是关于文章  资源 视频 图片 等的评论
			if(topLevel == 0){
				return currentComment;
			}else if(topLevel == 1){
				return null;
			}else{
				//其他不支持
				return null;
			}
		}else{
			//是关于评论的回复
			Comment _comment = currentComment;
			Comment parentComment = getParentComment(_comment);
			while(parentComment.getObjType() == ObjectType.OBJECT_TYPE_COMMENT.getTypeId()){
				_comment = parentComment;
				parentComment = getParentComment(parentComment);
			}
			if(topLevel == 0){
				return parentComment;
			}else if(topLevel == 1){
				return _comment;
			}else{
				return null;
			}
		}
	}
	
	/**
	 * 返回回复的上级评论
	 * @param comment
	 * @return
	 */
	private Comment getParentComment(Comment comment){
		if(comment.getObjType() != ObjectType.OBJECT_TYPE_COMMENT.getTypeId()){
			return comment;
		}
		int commentId = comment.getObjId();
		return getComment(commentId);
	}

	@Override
	public List<Comment> getAllPhotoCommentByPhotoId(Integer photoId) {
	    return this.cmt_dao.getAllPhotoCommentByPhotoId(photoId);
	}
		
}
