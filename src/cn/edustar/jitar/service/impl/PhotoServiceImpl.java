package cn.edustar.jitar.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Collections;

//import org.apache.commons.collections.ListUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.edustar.data.Pager;
import cn.edustar.jitar.dao.PhotoDao;
import cn.edustar.jitar.model.ObjectType;
import cn.edustar.jitar.pojos.Category;
import cn.edustar.jitar.pojos.Comment;
import cn.edustar.jitar.pojos.Photo;
import cn.edustar.jitar.pojos.PhotoStaple;
import cn.edustar.jitar.service.CategoryService;
import cn.edustar.jitar.service.EventManager;
import cn.edustar.jitar.service.FileStorage;
import cn.edustar.jitar.service.PhotoQueryParam;
import cn.edustar.jitar.service.PhotoService;
import cn.edustar.jitar.service.PhotoStapleService;
import cn.edustar.jitar.service.StatService;
import cn.edustar.jitar.service.StoreManager;
import cn.edustar.jitar.service.TagService;
import cn.edustar.jitar.service.UserService;
import cn.edustar.jitar.util.CommonUtil;

/**
 * 照片服务的实现.
 * 
 * @author Yang Xinxin
 * @version 1.0.0 Apr 30, 2008 3:10:22 PM
 */
public class PhotoServiceImpl implements PhotoService {

	/** 日志 */
	private static final Log log = LogFactory.getLog(PhotoServiceImpl.class);

	/** 数据库访问对象 */
	private PhotoDao photoDao;

	/** 分类服务 */
	private CategoryService cat_svc;

	/** 事件服务 */
	private EventManager evt_mgr;

	/** 标签服务 */
	private TagService tag_svc;	
	
	/** 相册分类服务 */
	private PhotoStapleService pstple_svc;
	
	/** 相册分类服务 */
	public void setPhotoStapleService(PhotoStapleService pstple_svc) {
		this.pstple_svc = pstple_svc;
	}

	/** 数据库访问对象 */
	public void setPhotoDao(PhotoDao photoDao) {
		this.photoDao = photoDao;
	}

	/** 分类服务 */
	public void setCategoryService(CategoryService cat_svc) {
		this.cat_svc = cat_svc;
	}

	/** 事件服务 */
	public void setEventManager(EventManager evt_mgr) {
		this.evt_mgr = evt_mgr;
	}

	/** 标签服务 */
	public void setTagService(TagService tag_svc) {
		this.tag_svc = tag_svc;
	}

	/** 统计更新服务 */
	private StatService stat_svc;

	/** 统计更新服务的set方法 */
	public void setStatService(StatService stat_svc) {
		this.stat_svc = stat_svc;
	}

	/** 用户文件存储服务 */
	private StoreManager storeManager;

	/** 用户文件存储服务的set方法 */
	public void setStoreManager(StoreManager storeManager) {
		this.storeManager = storeManager;
	}
	
	/** 用户服务 */
	private UserService userService;
	
	/** 用户服务的set方法 */
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	// 规范化 photo 中某些参数, 如 null 用 "" 安全的代替等
	private void normalizePhoto(Photo photo) {
		// 标签规范化.
		if (photo.getTags() == null)
			photo.setTags("");
		String[] tags = tag_svc.parseTagList(photo.getTags());
		photo.setTags(CommonUtil.standardTagsString(tags));
	}

	/** 对外发布一个事件, this 做为发布者 */
	private void publishEvent(String eventName, Object eventObject) {
		if (evt_mgr == null) {
			if (log.isDebugEnabled())
				log.debug("event service is null when publishEvent: " + eventName);
			return;
		}
		evt_mgr.publishEvent(eventName, this, eventObject);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.edustar.jitar.service.PhotoService#findById(int)
	 */
	public Photo findById(int photoId) {
		return photoDao.findById(photoId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.edustar.jitar.service.PhotoService#createPhoto(cn.edustar.jitar.pojos.Photo)
	 */
	public void savePhoto(Photo photo) {
		if (photo == null)
			throw new IllegalArgumentException("photo == null");

		// 规范化 photo 中某些参数, 如 null 用 "" 安全的代替等
		normalizePhoto(photo);

		// 验证系统分类存在性.
		if (photo.getSysCateId() != null) {
			Category sys_cate = cat_svc.getCategory(photo.getSysCateId());
			if (sys_cate == null)
				throw new RuntimeException("找不到指定标识的系统分类");
		} 
		
		if (photo.getUserStaple() != null) {
			// 验证用户分类存在, 以及是所属用户的.
			PhotoStaple pstaple = pstple_svc.findById(photo.getUserStaple());
			int p_id = photo.getUserStaple();
			
			if (pstaple == null || pstaple.getId() != p_id) 
				throw new RuntimeException("不正确的用户分类");
		}

		// 物理创建文章对象.
		if (photo.getPhotoId() == 0)
			photoDao.createPhoto(photo);
		else
			photoDao.updatePhoto(photo);

		// 写入标签, 如果有的话.
		String[] tags = tag_svc.parseTagList(photo.getTags());
		if (tags.length > 0) {
			tag_svc.createUpdateMultiTag(photo.getId(), ObjectType.OBJECT_TYPE_PHOTO, tags, null);
		}

		// 根据审核状态, 更新一些统计数据.
		incPhotoCount(photo);

		// 发布创建事件.
		publishEvent(EVENT_PHOTO_CREATED, photo);
	}
	
	/**
	 * 上传之后的更新统计数据+1.
	 *
	 * @param photo
	 */
	private void incPhotoCount(Photo photo) {
		if (photo.getAuditState() == Photo.AUDIT_STATE_OK)
			stat_svc.incPhotoCount(photo, 1);
	}
	
	/**
	 * 删除照片之后的更新统计数据-1.
	 *
	 * @param photo
	 */
	private void decPhotoStat(Photo photo) {
		if (photo.getAuditState() == Photo.AUDIT_STATE_OK)
			stat_svc.incPhotoCount(photo, -1);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.edustar.jitar.service.PhotoService#getPhotoListEx(cn.edustar.jitar.service.PhotoQueryParam, cn.edustar.data.Pager)
	 */
	@SuppressWarnings("unchecked")
	public List<Photo> getPhotoListEx(PhotoQueryParam param, Pager pager) {
		List<Photo> photo_list = photoDao.getPhotoList(param, pager);
		if (photo_list == null || photo_list.size() == 0)
			return Collections.EMPTY_LIST;
		return photo_list;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.edustar.jitar.service.PhotoService#delPhoto(int)
	 */
	public void delPhoto(Photo photo) {	
		try {	
			// 先删除磁盘上的物理文件
			FileStorage store = storeManager.getUserFileStorage(userService.getUserById(photo.getUserId())._getUserObject());
			File user_root = store.getRootFolder();
			String href = photo.getHref();
			int start = href.lastIndexOf("/");
			String dFile = href.substring(start + 1);
			File destFile = new File(user_root, "/photo/" + dFile);
			log.info("图片全路径：" + destFile);
			destFile.delete();
			
			// 删除生成的缩略图
			File destFile1 = new File(user_root, "/photo/s_" + dFile);
			File destFile2 = new File(user_root, "/photo/320x240_" + dFile);
			File destFile3 = new File(user_root, "/photo/200x240_" + dFile);
			File destFile4 = new File(user_root, "/photo/200x120_" + dFile);
			File destFile5 = new File(user_root, "/photo/160x120_" + dFile);
			File destFile6 = new File(user_root, "/photo/565x280_" + dFile);
			File destFile7 = new File(user_root, "/photo/70x70_" + dFile);
			File destFile8 = new File(user_root, "/photo/690x400_" + dFile);
			File destFile9 = new File(user_root, "/photo/230x250_" + dFile);
			File destFile10 = new File(user_root, "/photo/230x136_" + dFile);
			File destFile11 = new File(user_root, "/photo/230x100_" + dFile);
			File destFile12 = new File(user_root, "/photo/665x500_" + dFile);
			File destFile13 = new File(user_root, "/photo/125x100_" + dFile);
			
			log.info("图片缩略图的全路径：" + destFile1);
			destFile1.delete();
			destFile2.delete();
			destFile3.delete();
			destFile4.delete();
			destFile5.delete();
			destFile6.delete();
			destFile7.delete();
			destFile8.delete();
			destFile9.delete();
			destFile10.delete();
			destFile11.delete();
			destFile12.delete();
			destFile13.delete();
			log.info("用户删除了照片和照片的缩略图：" + dFile);
		} catch (IOException e) {
			e.printStackTrace();
		}	
		
		//请先确定删除 G_GroupPhoto，再删除Photo
		
		photoDao.delPhoto(photo);
		decPhotoStat(photo);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.edustar.jitar.service.PhotoService#increaseViewCount(int)
	 */
	public void increaseViewCount(int photoId, int viewCount) {
		photoDao.increaseViewCount(photoId, viewCount);
	}
	
	/* 
	 * (non-Javadoc)
	 *
	 * @see cn.edustar.jitar.service.PhotoService#updatePhoto(cn.edustar.jitar.pojos.Photo)
	 */
	public void updatePhoto(Photo photo) {		
		if (photo == null)
			throw new IllegalArgumentException("photo == null");
		// 物理创建文章对象.
		photoDao.updatePhoto(photo);

		// 写入标签, 如果有的话.
		String[] tags = tag_svc.parseTagList(photo.getTags());
		if (tags.length > 0) {
			tag_svc.createUpdateMultiTag(photo.getId(), ObjectType.OBJECT_TYPE_PHOTO, tags, null);
		}

		// 根据审核状态, 更新一些统计数据

		// 发布创建事件
		publishEvent(EVENT_PHOTO_CREATED, photo);		
	}
	
	/* 
	 * (non-Javadoc)
	 *
	 * @see cn.edustar.jitar.service.PhotoService#getDefaultPhotoList(int)
	 */
	public List<Photo> getDefaultPhotoList(int userId) {
		return photoDao.getDefaultPhotoList(userId);
	}

	/* 
	 * (non-Javadoc)
	 *
	 * @see cn.edustar.jitar.service.PhotoService#getPhotoList(int, java.lang.Integer)
	 */
	public List<Photo> getPhotoList(int userId, Integer userStaple) {
		return photoDao.getPhotoList(userId, userStaple);
	}
	
	/* 
	 * (non-Javadoc)
	 *
	 * @see cn.edustar.jitar.service.PhotoService#putIntoCycle(int)
	 */
	public void putIntoRecycle(int photoId) {
		photoDao.putIntoRecycle(photoId);
	}
	
	/* 
	 * (non-Javadoc)
	 *
	 * @see cn.edustar.jitar.service.PhotoService#recoverPhoto(int)
	 */
	public void recoverPhoto(int photoId) {
		photoDao.recoverPhoto(photoId);
	}

	/* 
	 * (non-Javadoc)
	 *
	 * @see cn.edustar.jitar.service.PhotoService#auditPhoto(cn.edustar.jitar.pojos.Photo)
	 */
	public void auditPhoto(Photo photo) {
		if (photo.getAuditState() == Photo.AUDIT_STATE_OK)
			return;
		photo.setAuditState(Photo.AUDIT_STATE_OK);
		this.photoDao.updatePhoto(photo);
	}

	/* 
	 * (non-Javadoc)
	 *
	 * @see cn.edustar.jitar.service.PhotoService#unAuditPhoto(cn.edustar.jitar.pojos.Photo)
	 */
	public void unAuditPhoto(Photo photo) {
		if (photo.getAuditState() != Photo.AUDIT_STATE_OK)
			return;
		photo.setAuditState(Photo.AUDIT_STATE_WAIT_AUDIT);
		this.photoDao.updatePhoto(photo);
	}
	
	/* (non-Javadoc)
	 *
	 * @see cn.edustar.jitar.service.PhotoService#privateShow(int)
	 */
	public void privateShow(int photoId) {
		photoDao.privateShow(photoId);
	}

	@Override
	public void incPhotoCommentCount(Comment cmt, int i) {
		photoDao.incPhotoCommentCount(cmt, i);
	}

	@Override
	public List<String> getPhotos(int sId, int eId) {
		return photoDao.getphotos(sId, eId);
	}
	
}
