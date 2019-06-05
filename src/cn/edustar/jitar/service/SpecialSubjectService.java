package cn.edustar.jitar.service;

import java.util.List;

import cn.edustar.jitar.pojos.NewSpecialSubject;
import cn.edustar.jitar.pojos.SpecialSubject;
import cn.edustar.jitar.pojos.SpecialSubjectArticle;
import cn.edustar.jitar.pojos.SpecialSubjectPhoto;

public interface SpecialSubjectService {
	/**
	 * 保存专题
	 * @param specialSubject
	 */
	public void saveOrUpdateSpecialSubject(SpecialSubject specialSubject);	
	
	/**
	 * 加载一个专题
	 * @param specialSubjectId
	 * @return
	 */
	public SpecialSubject getSpecialSubject(int specialSubjectId);
	
	
	/**
	 * 根据 guid 得到专题对象
	 * @param specialSubjectGuid
	 * @return
	 */
	public SpecialSubject getSpecialSubjectByGuid(String specialSubjectGuid);
	
	
	/**
	 * 删除一个专题
	 * @param specialSubject
	 */
	public void deleteSpecialSubject(SpecialSubject specialSubject);
	
	/**
	 * 根据标识删除专题
	 * @param specialSubjectId
	 */
	public void deleteSpecialSubjectById(int specialSubjectId);
	
	
	/**
	 * 得到没有过期的所有专题
	 * @return
	 */
	public List<SpecialSubject> getValidSpecialSubjectList();
	
	
	/**
	 * 判断标识是否过期
	 * @param specialSubject
	 * @return
	 */
	public boolean checkSpecialSubjectIsExpired(SpecialSubject specialSubject);
	
	/**
	 * 更新一个专题文章
	 * @param specialSubjectArticle
	 */
	public void saveOrUpdateSpecialSubjectArticle(SpecialSubjectArticle specialSubjectArticle);
	
	/**
	 * 得到专题文章，我们规定，一篇文章只属于一个专题。这是因为，文章编辑界面限制的
	 * @param articleId
	 * @return
	 */
	public SpecialSubjectArticle getSpecialSubjectArticleByArticleId(String articleGuid);
	
	/**
	 * 删除专题文章
	 * @param specialSubjectArticle
	 */
	public void deleteSpecialSubjectArticle(SpecialSubjectArticle specialSubjectArticle);
	
	/**
	 * 删除一个专题的所有文章
	 * @param specialSubjectId
	 */
	public void deleteSpecialSubjectArticleBySpecialSubjectId(int specialSubjectId);
	
	/**
	 * 从所有专题中删除一个文章
	 * @param aricleId
	 */
	public void removeArticleFromSpecialSubjectArticleByAricleId(int aricleId);
	
	/**
	 * 删除用户的所有文章
	 * @param userId
	 */
	public void removeArticleFromSpecialSubjectArticleByUserId(int userId);
	
	/**
	 * 更新一个专题照片
	 * @param specialSubjectPhoto
	 */
	public void saveOrUpdateSpecialSubjectPhoto(SpecialSubjectPhoto specialSubjectPhoto);
	
	/**
	 * 删除一个专题图片
	 * @param specialSubjectPhoto
	 */
	public void deleteSpecialSubjectPhoto(SpecialSubjectPhoto specialSubjectPhoto);
	
	/**
	 * 删除一个专题的所有图片
	 * @param specialSubjectId
	 */
	public void deleteSpecialSubjectPhotoBySpecialSubjectId(int specialSubjectId);
	
	/**
	 * 从所有专题中删除一个图片
	 * @param aricleId
	 */
	public void removePhotoFromSpecialSubjectPhotoByPhotoId(int photoId);
	
	/**
	 * 删除一个用户的所有照片
	 * @param userId
	 */
	public void removePhotoFromSpecialSubjectPhotoByUserIdId(int userId);
	
	/**
	 * 删除一个用户创建的所有专题
	 * @param userId
	 */
	public void deleteSpecialSubjectByUserId(int userId);
	/**
	 * 添加一个候选主题
	 * @param newSpecialSubject
	 */
	public void addNewSpecialSubject(NewSpecialSubject newSpecialSubject);
	
	/**
	 * 删除一个候选主题
	 * @param newSpecialSubject
	 */
	public void deleteNewSpecialSubject(NewSpecialSubject newSpecialSubject);
	
	/**
	 * 删除某用户的所有候选主题
	 * @param createUserId
	 */
	public void deleteNewSpecialSubjectByCreateUserId(int createUserId);
	
   /**
    * 根据标识删除候选主题
    * @param newSpecialSubjectId
    */
	public void deleteNewSpecialSubjectById(int newSpecialSubjectId);
	
	/**
	 * 得到最新的候选话题
	 * @return
	 */
	public List<NewSpecialSubject> getAllNewSpecialSubject();
	
	/**
	 * 得到某种类型的专题中一个最新的
	 * @param objectType
	 * @return
	 */
	public SpecialSubject getNewestSpecialSubjectByType(String objectType);
	
	/**
	 * 得到特定对象的专题中一个最新的
	 * @param objectType
	 * @return
	 */
	public SpecialSubject getNewestSpecialSubjectByTypeId(int objectTypeId);
	
	public void deleteSubjectArticleByArticleId(int articleId);
	public void deleteSubjectArticleByArticleGuid(String articleGuid);
}
