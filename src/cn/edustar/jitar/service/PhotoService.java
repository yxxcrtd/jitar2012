package cn.edustar.jitar.service;

import java.util.List;

import cn.edustar.data.Pager;
import cn.edustar.jitar.pojos.Comment;
import cn.edustar.jitar.pojos.Photo;

/**
 * 照片服务
 * 
 * @author Yang Xinxin
 * @version 1.0.0 Apr 30, 2008 3:06:47 PM
 */
public interface PhotoService {

	/** 图片创建事件, 事件对象为 Photo 对象. */
	public static final String EVENT_PHOTO_CREATED = "jitar.photo.created";

	/**
	 * 根据照片Id得到对应记录.
	 * 
	 * @param id
	 * @return
	 */
	public Photo findById(int photoId);

	/**
	 * 保存一张照片.
	 * 
	 * @param photo
	 */
	public void savePhoto(Photo photo);

	/**
	 * 根据指定查询参数和分页参数得到照片列表.
	 * 
	 * @param param 查询参数.
	 * @param pager 分页参数.
	 * @return
	 */
	public List<Photo> getPhotoListEx(PhotoQueryParam param, Pager pager);

	/**
	 * 根据'照片Id'删除照片.
	 * 
	 * @param id
	 */
	public void delPhoto(Photo photo);

	/**
	 * 将当前点击的照片的浏览次数加1
	 * 
	 * @param photoId
	 */
	public void increaseViewCount(int photoId, int newPhotoId);

	/**
	 * 编辑图片信息后保存.
	 * 
	 * @param photo
	 */
	public void updatePhoto(Photo photo);

	/**
	 * 根据'userId'得到'默认相册分类列表'
	 * 
	 * @param UserId
	 * @return
	 */
	public List<Photo> getDefaultPhotoList(int UserId);
	
	/**
	 * 根据'用户Id'和'userStaple'得到相册列表.
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
	 * 从回收站中恢复删除的照片.
	 *
	 * @param photoId
	 */
	public void recoverPhoto(int photoId);
	
	/**
	 * 审核照片，将其状态修改为'待审核'
	 *
	 * @param photoId
	 */
	public void auditPhoto(Photo photo);
	
	/**
	 * 审核照片，将其状态修改为'已审核'
	 *
	 * @param photoId
	 */
	public void unAuditPhoto(Photo photo);
	
	/**
	 * @param photoId
	 */
	public void privateShow(int photoId);
	
    /**
	 * 增加/减少图片评论数量
	 * 
	 * @param comment
	 * @param count
    */
  	public void incPhotoCommentCount(Comment cmt, int i);

  	public List<String> getPhotos(int sId, int eId);

	
}
