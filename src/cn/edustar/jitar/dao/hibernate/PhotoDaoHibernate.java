package cn.edustar.jitar.dao.hibernate;

import java.util.List;

import cn.edustar.data.Pager;
import cn.edustar.data.hql.QueryHelper;
import cn.edustar.jitar.dao.PhotoDao;
import cn.edustar.jitar.pojos.Comment;
import cn.edustar.jitar.pojos.Photo;
import cn.edustar.jitar.service.PhotoQueryParam;

/**
 * 照片DAO的实现
 * 
 */
public class PhotoDaoHibernate extends BaseDaoHibernate implements PhotoDao {
	
	/** 得到默认相册为空的所有记录 */
	private static final String GET_DEFAULT_PHOTO_LIST = "FROM Photo P WHERE P.userStaple is NULL AND P.userId = ?";
	
	/** 根据'用户Id'和'userStaple'得到相册列表 */
	private static final String GET_PHOTO_LIST = "FROM Photo P WHERE P.userStaple = ? AND P.userId = ?";
	
	/** 将照片放入回收站 */
	private static final String PUT_INTO_RECYCLE = "UPDATE Photo p SET p.delState = true WHERE p.id = ?";
	
	/** 将选择的照片只在个人空间显示 */
	private static final String PRIVATE_SHOW = "UPDATE Photo p SET p.isPrivateShow = true WHERE p.id = ?";
	
	/** 从回收站中恢复删除的照片 */
	private static final String RECOVER_PHOTO_FROM_RECYCLE = "UPDATE Photo p SET p.delState = false WHERE p.id = ?";
	
	private static final String GET_PHOTOS = "";
		
	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.edustar.jitar.dao.PhotoDao#findById(int)
	 */
	public Photo findById(int photoId) {
		return (Photo) this.getSession().get(Photo.class, photoId);
	}

	/* 
	 * (non-Javadoc)
	 *
	 * @see cn.edustar.jitar.dao.PhotoDao#createPhoto(cn.edustar.jitar.pojos.Photo)
	 */
	public void createPhoto(Photo photo) {
		getSession().save(photo);
		getSession().flush();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.edustar.jitar.dao.PhotoDao#savePhoto(cn.edustar.jitar.pojos.Photo)
	 */
	public void updatePhoto(Photo photo) {
		this.getSession().update(photo);		
		this.getSession().flush();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.edustar.jitar.dao.PhotoDao#getPhotoList(cn.edustar.jitar.service.PhotoQueryParam, cn.edustar.data.Pager)
	 */
	@SuppressWarnings("unchecked")
	public List<Photo> getPhotoList(PhotoQueryParam param, Pager pager) {
		if (param == null)
			throw new IllegalArgumentException("param == null");

		// 使用条件创建 query 对象，其中不设置选择哪些字段.
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
	 * @see cn.edustar.jitar.dao.PhotoDao#delPhoto(cn.edustar.jitar.pojos.Photo)
	 */
	public void delPhoto(Photo photo) {	
		this.getSession().delete(photo);
		this.getSession().flush();		
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.edustar.jitar.dao.PhotoDao#increaseViewCount(int)
	 */
	public void increaseViewCount(final int photoId, final int viewCount) {
		String hql = "UPDATE Photo P SET P.viewCount = viewCount + :viewCount WHERE P.id = :id";
		this.getSession().createQuery(hql).setInteger("viewCount", viewCount).setInteger("id", photoId).executeUpdate();
		this.getSession().flush();
	}
	
	/* 
	 * (non-Javadoc)
	 *
	 * @see cn.edustar.jitar.dao.PhotoDao#getDefaultPhotoList(int)
	 */
	@SuppressWarnings("unchecked")
	public List<Photo> getDefaultPhotoList(final int userId) {
		return this.getSession().createQuery(GET_DEFAULT_PHOTO_LIST).setInteger(0, userId).list();
	}

	/* 
	 * (non-Javadoc)
	 *
	 * @see cn.edustar.jitar.dao.PhotoDao#getPhotoList(int, java.lang.Integer)
	 */
	@SuppressWarnings("unchecked")
	public List<Photo> getPhotoList(int userId, Integer userStaple) {
		return this.getSession().createQuery(GET_PHOTO_LIST).setInteger(0, userStaple).setInteger(1, userId).list();
	}
	
	/* 
	 * (non-Javadoc)
	 *
	 * @see cn.edustar.jitar.dao.PhotoDao#putIntoCycle(int)
	 */
	public void putIntoRecycle(final int photoId) {
		this.getSession().createQuery(PUT_INTO_RECYCLE).setInteger(0,photoId).executeUpdate();	
		this.getSession().flush();
	}
	
	/* 
	 * (non-Javadoc)
	 *
	 * @see cn.edustar.jitar.dao.PhotoDao#recoverPhoto(int)
	 */
	public void recoverPhoto(final int photoId) {
		Photo p = this.findById(photoId);
		if(p == null) return;
		Boolean showState = (p.getAuditState() == 0 && p.getIsPrivateShow() == false);
		this.getSession().createQuery(RECOVER_PHOTO_FROM_RECYCLE).setInteger(0,photoId).executeUpdate();	
		this.getSession().flush();
		
	}
	
	public void reCountComment(int photoId) {
		String queryString = "Select Count(*) From Comment WHERE objType = 11 And objId = " + photoId;
		Object o = this.getSession().createQuery(queryString).uniqueResult();
		Integer c = 0;
		if(o != null) c = Integer.valueOf(o.toString()).intValue();
		queryString = "Update Photo Set commentCount = ? WHERE photoId = ?";
		this.getSession().createQuery(queryString).setInteger(0, c).setInteger(1, photoId).executeUpdate();
		this.getSession().flush();
	}
	
	/* (non-Javadoc)
	 *
	 * @see cn.edustar.jitar.dao.PhotoDao#privateShow(int)
	 */
	public void privateShow(int photoId) {
		this.getSession().createQuery(PRIVATE_SHOW).setInteger(0, photoId).executeUpdate();
	}

	@Override
	public void evict(Object object) {
		this.getSession().evict(object);
		
	}

	@Override
	public void flush() {
		this.getSession().flush();		
	}

	@Override
	public void incPhotoCommentCount(Comment comment, int count) {
	    String sql = "Update B_Photo SET commentCount = commentCount + ? WHERE  photoId = ? ";
	    	    
		// 增加评论星级
		//String hql = "UPDATE Article SET commentCount = commentCount + ?, starCount = starCount + ? WHERE articleId = ? ";

		if (count > 0)
			this.getSession().createSQLQuery(sql).setInteger(0, count).setInteger(1, comment.getObjId()).executeUpdate();
		
		else
			this.getSession().createSQLQuery(sql).setInteger(0, count).setInteger(1, comment.getObjId()).executeUpdate();
			
		if (comment.getUserId() != null) {
			String hql = "UPDATE User SET commentCount = commentCount + :commentCount WHERE userId = :userId";
			this.getSession().createQuery(hql).setInteger("commentCount", count).setInteger("userId", comment.getUserId()).executeUpdate();			
		}
		this.getSession().flush();
	}

	@SuppressWarnings("unchecked")
    @Override
	public List<String> getphotos(int sId, int eId) {
		String queryString = "Select href From Photo WHERE photoId > :sId And photoId <=:eId"; //倒序是为了先把最新的处理了，显示会好些。		
		return this.getSession().createQuery(queryString).setInteger("sId", sId).setInteger("eId", eId).list();
	}
}
