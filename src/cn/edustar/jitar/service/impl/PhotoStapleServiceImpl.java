package cn.edustar.jitar.service.impl;

import java.util.List;
import java.util.Map;

import cn.edustar.data.DataTable;
import cn.edustar.data.Pager;
import cn.edustar.jitar.dao.PhotoStapleDao;
import cn.edustar.jitar.pojos.Category;
import cn.edustar.jitar.pojos.PhotoStaple;
import cn.edustar.jitar.service.PhotoStapleQueryParam;
import cn.edustar.jitar.service.PhotoStapleService;

/**
 * 相册分类接口的实现.
 * 
 * @author Yang Xinxin
 * @version 1.0.0 Apr 28, 2008 11:02:45 AM
 */
public class PhotoStapleServiceImpl implements PhotoStapleService {

	/** 相册分类DAO */
	private PhotoStapleDao photoStapleDao;

	/**
	 * 相册分类DAO的set方法.
	 * 
	 * @param photoStapleDao
	 */
	public void setPhotoStapleDao(PhotoStapleDao photoStapleDao) {
		this.photoStapleDao = photoStapleDao;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.edustar.jitar.service.PhotoStapleService#findById(int)
	 */
	public PhotoStaple findById(int id) {
		return this.photoStapleDao.findById(id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.edustar.jitar.service.PhotoStapleService#getPhotoStapleDataTable(cn.edustar.jitar.service.PhotoStapleQueryParam, cn.edustar.data.Pager)
	 */
	public DataTable getPhotoStapleDataTable(PhotoStapleQueryParam param, Pager pager) {
		return this.photoStapleDao.getPhotoStapleDataTable(param, pager);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.edustar.jitar.service.PhotoStapleService#savePhotoStaple(cn.edustar.jitar.pojos.PhotoStaple)
	 */
	public void savePhotoStaple(PhotoStaple photoStaple) {
		photoStapleDao.savePhotoStaple(photoStaple);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.edustar.jitar.service.PhotoStapleService#delPhotoStaple(int)
	 */
	public void delPhotoStaple(int id) {
		PhotoStaple photoStaple = photoStapleDao.findById(id);
		photoStapleDao.delPhotoStaple(photoStaple);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.edustar.jitar.service.PhotoStapleService#getPhotoStapleList(int)
	 */
	@SuppressWarnings("unchecked")
	public List getPhotoStapleList(int userId) {
		return photoStapleDao.getPhotoStapleList(userId);
	}

	/* 
	 * (non-Javadoc)
	 *
	 * @see cn.edustar.jitar.service.PhotoStapleService#getPhotoAndStapleList(int)
	 */
	@SuppressWarnings("unchecked")
	public List<Map> getPhotoAndStapleList(int userId) {
		return photoStapleDao.getPhotoAndStapleList(userId);
	}
	
	/* 
	 * (non-Javadoc)
	 *
	 * @see cn.edustar.jitar.service.PhotoStapleService#getPhotoStapleList()
	 */
	public List<PhotoStaple> getPhotoStapleList() {
		return photoStapleDao.getPhotoStapleList();
	}

	/**
	 * 查询用户照片分类，转换为Category对象
	 * @param userId
	 * @return
	 */
	public List<Category> getPhotoStapleTreeList(int userId){
		return photoStapleDao.getPhotoStapleTreeList(userId);
	}

	/**
	 *  newParentId 是否是 id 的子分类 
	 * @param id
	 * @param newParentId
	 * @return true 是子分类； false 不是子分类
	 * @return
	 */
	public boolean isInChildPath(int id,Integer newParentId){
		return photoStapleDao.isInChildPath(id,newParentId);
	}
	
	/**
	 * 得到用户照片子分类
	 * @param parentId
	 * @return
	 */
	public List<PhotoStaple> getPhotoStapleChilds(int parentId){
		return photoStapleDao.getPhotoStapleChilds(parentId);
	}
}
