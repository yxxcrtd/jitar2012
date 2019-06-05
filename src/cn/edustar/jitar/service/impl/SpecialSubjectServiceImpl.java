package cn.edustar.jitar.service.impl;

import java.util.List;

import cn.edustar.jitar.dao.SpecialSubjectDao;
import cn.edustar.jitar.pojos.NewSpecialSubject;
import cn.edustar.jitar.pojos.SpecialSubject;
import cn.edustar.jitar.pojos.SpecialSubjectArticle;
import cn.edustar.jitar.pojos.SpecialSubjectPhoto;
import cn.edustar.jitar.service.SpecialSubjectService;

public class SpecialSubjectServiceImpl implements SpecialSubjectService {

private SpecialSubjectDao specialSubjectDao;
	
	/**
	 * 保存专题
	 * @param specialSubject
	 */
	public void saveOrUpdateSpecialSubject(SpecialSubject specialSubject)
	{
		this.specialSubjectDao.saveOrUpdateSpecialSubject(specialSubject);
	}
	
	/**
	 * 加载一个专题
	 * @param specialSubjectId
	 * @return
	 */
	public SpecialSubject getSpecialSubject(int specialSubjectId)
	{
		return this.specialSubjectDao.getSpecialSubject(specialSubjectId);
	}
	
	/**
	 * 根据 guid 得到专题对象
	 * @param specialSubjectGuid
	 * @return
	 */
	public SpecialSubject getSpecialSubjectByGuid(String specialSubjectGuid)
	{
		return this.specialSubjectDao.getSpecialSubjectByGuid(specialSubjectGuid);
	}
	
	/**
	 * 删除一个专题
	 * @param specialSubject
	 */
	public void deleteSpecialSubject(SpecialSubject specialSubject)
	{
		this.specialSubjectDao.deleteSpecialSubject(specialSubject);
	}
	
	/**
	 * 根据标识删除专题
	 * @param specialSubjectId
	 */
	public void deleteSpecialSubjectById(int specialSubjectId)
	{
		this.specialSubjectDao.deleteSpecialSubjectById(specialSubjectId);
	}
	
	/**
	 * 得到没有过期的所有专题
	 * @return
	 */
	public List<SpecialSubject> getValidSpecialSubjectList()
	{
		return this.specialSubjectDao.getValidSpecialSubjectList();
	}
	
	
	/**
	 * 判断标识是否过期
	 * @param specialSubject
	 * @return
	 */
	public boolean checkSpecialSubjectIsExpired(SpecialSubject specialSubject)
	{
		return this.specialSubjectDao.checkSpecialSubjectIsExpired(specialSubject);
	}
	
	/**
	 * 更新一个专题文章
	 * @param specialSubjectArticle
	 */
	public void saveOrUpdateSpecialSubjectArticle(SpecialSubjectArticle specialSubjectArticle)
	{
		this.specialSubjectDao.saveOrUpdateSpecialSubjectArticle(specialSubjectArticle);
	}
	
	
	/**
	 * 得到专题文章，我们规定，一篇文章只属于一个专题。这是因为，文章编辑界面限制的
	 * @param articleId
	 * @return
	 */
	public SpecialSubjectArticle getSpecialSubjectArticleByArticleId(String articleGuid)
	{
		return this.specialSubjectDao.getSpecialSubjectArticleByArticleId(articleGuid);
	}
	
	
	/**
	 * 删除专题文章
	 * @param specialSubjectArticle
	 */
	public void deleteSpecialSubjectArticle(SpecialSubjectArticle specialSubjectArticle)
	{
		this.specialSubjectDao.deleteSpecialSubjectArticle(specialSubjectArticle);
	}
	
	/**
	 * 删除一个专题的所有文章
	 * @param specialSubjectId
	 */
	public void deleteSpecialSubjectArticleBySpecialSubjectId(int specialSubjectId)
	{
		this.deleteSpecialSubjectArticleBySpecialSubjectId(specialSubjectId);
	}
	
	/**
	 * 从所有专题中删除一个文章
	 * @param aricleId
	 */
	public void removeArticleFromSpecialSubjectArticleByAricleId(int aricleId)
	{
		this.specialSubjectDao.removeArticleFromSpecialSubjectArticleByAricleId(aricleId);
	}
	
	/**
	 * 删除用户的所有文章
	 * @param userId
	 */
	public void removeArticleFromSpecialSubjectArticleByUserId(int userId)
	{
		this.specialSubjectDao.removeArticleFromSpecialSubjectArticleByUserId(userId);
	}
	
	/**
	 * 更新一个专题照片
	 * @param specialSubjectPhoto
	 */
	public void saveOrUpdateSpecialSubjectPhoto(SpecialSubjectPhoto specialSubjectPhoto)
	{
		this.specialSubjectDao.saveOrUpdateSpecialSubjectPhoto(specialSubjectPhoto);
	}
	
	/**
	 * 删除一个专题图片
	 * @param specialSubjectPhoto
	 */
	public void deleteSpecialSubjectPhoto(SpecialSubjectPhoto specialSubjectPhoto)
	{
		this.specialSubjectDao.deleteSpecialSubjectPhoto(specialSubjectPhoto);
	}
	
	/**
	 * 删除一个专题的所有图片
	 * @param specialSubjectId
	 */
	public void deleteSpecialSubjectPhotoBySpecialSubjectId(int specialSubjectId)
	{
		this.specialSubjectDao.deleteSpecialSubjectPhotoBySpecialSubjectId(specialSubjectId);
	}
	
	/**
	 * 从所有专题中删除一个图片
	 * @param photoId
	 */
	public void removePhotoFromSpecialSubjectPhotoByPhotoId(int photoId)
	{
		this.removePhotoFromSpecialSubjectPhotoByPhotoId(photoId);
	}
	
	/**
	 * 删除一个用户的所有照片
	 * @param userId
	 */
	public void removePhotoFromSpecialSubjectPhotoByUserIdId(int userId)
	{
		this.specialSubjectDao.removePhotoFromSpecialSubjectPhotoByUserIdId(userId);
	}
	
	/**
	 * 删除一个用户创建的所有专题
	 * @param userId
	 */
	public void deleteSpecialSubjectByUserId(int userId)
	{
		this.specialSubjectDao.deleteSpecialSubjectByUserId(userId);
	}

	public void setSpecialSubjectDao(SpecialSubjectDao specialSubjectDao) {
		this.specialSubjectDao = specialSubjectDao;
	}
	
	/**
	 * 添加一个候选主题
	 * @param newSpecialSubject
	 */
	public void addNewSpecialSubject(NewSpecialSubject newSpecialSubject)
	{
		this.specialSubjectDao.addNewSpecialSubject(newSpecialSubject);
	}
	
	/**
	 * 删除一个候选主题
	 * @param newSpecialSubject
	 */
	public void deleteNewSpecialSubject(NewSpecialSubject newSpecialSubject)
	{
		this.specialSubjectDao.deleteNewSpecialSubject(newSpecialSubject);
	}
	
	/**
	 * 删除某用户的所有候选主题
	 * @param createUserId
	 */
	public void deleteNewSpecialSubjectByCreateUserId(int createUserId)
	{
		this.specialSubjectDao.deleteNewSpecialSubjectByCreateUserId(createUserId);
	}
	
   /**
    * 根据标识删除候选主题
    * @param newSpecialSubjectId
    */
	public void deleteNewSpecialSubjectById(int newSpecialSubjectId)
	{
		this.specialSubjectDao.deleteNewSpecialSubjectById(newSpecialSubjectId);
	}
	
	/**
	 * 得到最新的候选话题
	 * @return
	 */
	public List<NewSpecialSubject> getAllNewSpecialSubject()
	{
		return this.specialSubjectDao.getAllNewSpecialSubject();
	}
	
	/**
	 * 得到某种类型的专题中一个最新的
	 * @param objectType
	 * @return
	 */
	public SpecialSubject getNewestSpecialSubjectByType(String objectType)
	{
		return this.specialSubjectDao.getNewestSpecialSubjectByType(objectType);
	}
	/**
	 * 得到特定对象的专题中一个最新的
	 * @param objectType
	 * @return
	 */
	public SpecialSubject getNewestSpecialSubjectByTypeId(int objectTypeId)
	{
		return this.specialSubjectDao.getNewestSpecialSubjectByTypeId(objectTypeId);
	}
	
	public void deleteSubjectArticleByArticleId(int articleId)
	{
		this.specialSubjectDao.deleteSubjectArticleByArticleId(articleId);
	}
	public void deleteSubjectArticleByArticleGuid(String articleGuid)
	{
		this.specialSubjectDao.deleteSubjectArticleByArticleGuid(articleGuid);
	}
}