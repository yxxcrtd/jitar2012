package cn.edustar.jitar.service.impl;

import java.util.List;

import cn.edustar.jitar.dao.ContentSpaceDao;
import cn.edustar.jitar.pojos.Category;
import cn.edustar.jitar.pojos.ContentSpace;
import cn.edustar.jitar.pojos.ContentSpaceArticle;
import cn.edustar.jitar.service.ContentSpaceService;

public class ContentSpaceServiceImpl implements ContentSpaceService {

	private ContentSpaceDao contentSpaceDao;
	
	public void setContentSpaceDao(ContentSpaceDao contentSpaceDao) {
		this.contentSpaceDao = contentSpaceDao;
	}

	public void deleteContentSpace(ContentSpace contentSpace) {
		this.contentSpaceDao.deleteContentSpace(contentSpace);
	}

	public List<ContentSpace> getAllContentSpaceList(Integer ownerType,
			Integer ownerId) {
		return this.contentSpaceDao.getAllContentSpaceList(ownerType, ownerId);
	}

	public ContentSpace getContentSpaceById(int contentSpaceId) {
		return this.contentSpaceDao.getContentSpaceById(contentSpaceId);
	}

	public void saveOrUpdateContentSpace(ContentSpace contentSpace) {
		this.contentSpaceDao.saveOrUpdateContentSpace(contentSpace);
	}
	public int getContentSpaceArticleCountById(int contentSpaceId)
	{
		return this.contentSpaceDao.getContentSpaceArticleCountById(contentSpaceId);
	}
	/**
	 * newParentId 是 contentSpaceId 的子分类 
	 * @param contentSpaceId
	 * @param newParentId
	 * @return true 是子分类； false 不是子分类
	 */
	public boolean isInChildPath(int contentSpaceId,int newParentId){
		return this.contentSpaceDao.isInChildPath(contentSpaceId,newParentId);
	}
	
	public List<Category> getContentSpaceTreeList(Integer ownerType, Integer ownerId){
		return this.contentSpaceDao.getContentSpaceTreeList(ownerType, ownerId);
	}
	
	public List<ContentSpace> getContentSpaceList(Integer parentId){
		return this.contentSpaceDao.getContentSpaceList(parentId);
	}
	/** 文章部分 */	
	public void saveOrUpdateArticle(ContentSpaceArticle contentSpaceArticle){
		this.contentSpaceDao.saveOrUpdateArticle(contentSpaceArticle);
	}
	public void deleteContentSpaceArticle(ContentSpaceArticle contentSpaceArticle)
	{
		this.contentSpaceDao.deleteContentSpaceArticle(contentSpaceArticle);
	}
	public ContentSpaceArticle getContentSpaceArticleById(int contentSpaceArticleId)
	{
		return this.contentSpaceDao.getContentSpaceArticleById(contentSpaceArticleId);
	}
	public void deleteContentSpaceArticleByContentSpaceId(int contentSpaceId)
	{
		this.contentSpaceDao.deleteContentSpaceArticleByContentSpaceId(contentSpaceId);
	}

}
