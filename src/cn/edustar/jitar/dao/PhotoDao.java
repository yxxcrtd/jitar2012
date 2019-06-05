package cn.edustar.jitar.dao;

import java.util.List;

import cn.edustar.data.Pager;
import cn.edustar.jitar.pojos.Comment;
import cn.edustar.jitar.pojos.Photo;
import cn.edustar.jitar.service.PhotoQueryParam;

/**
 * 照片DAO.
 * 
 * @author Yang Xinxin
 * @version 1.0.0 Apr 30, 2008 3:12:06 PM
 */
public interface PhotoDao extends Dao {
	
	/**
	 * 根据照片Id得到对应记录.
	 * 
	 * @param photoId
	 * @return
	 */
	public Photo findById(int photoId);

	/**
	 * create
	 *
	 * @param photo
	 */
	public void createPhoto(Photo photo);
	
	/**
	 * update 一张照片.
	 * 
	 * @param photo
	 */
	public void updatePhoto(Photo photo);

	/**
	 * 根据指定查询参数和分页参数得到照片列表.
	 * 
	 * @param param
	 * @param pager
	 * @return
	 */
	public List<Photo> getPhotoList(PhotoQueryParam param, Pager pager);

	/**
	 * 根据照片对象删除照片.
	 * 
	 * @param photo
	 */
	public void delPhoto(Photo photo);

	/**
	 * 将当前点击的照片的浏览次数加1.
	 * 
	 * @param photoId
	 */
	public void increaseViewCount(int photoId, int viewCount);
	
	/**
	 * 根据'userId'得到'默认相册分类列表'
	 *
	 * @param userId
	 * @return
	 */
	public List<Photo> getDefaultPhotoList(int userId);
	
	/**
	 * 根据'用户Id'和'userStaple'得到相册列表
	 *
	 * @param userId
	 * @param userStaple
	 * @return
	 */
	public List<Photo> getPhotoList(int userId, Integer userStaple);
	
	/**
	 * 将指定的照片放入回收站.
	 *
	 * @param photoId
	 */
	public void putIntoRecycle(int photoId);
	
	/**
	 * 从回收站中恢复删除的照片
	 *
	 * @param photoId
	 */
	public void recoverPhoto(int photoId);
	
	/**
	 * 重新计算图片评论数量
	 */
	public void reCountComment(int photoId);
	
	/**
	 * @param photoId
	 */
	public void privateShow(int photoId);

	public void incPhotoCommentCount(Comment cmt, int i);

	public List<String> getphotos(int sId, int eId);

}
