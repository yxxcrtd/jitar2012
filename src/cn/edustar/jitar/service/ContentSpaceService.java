package cn.edustar.jitar.service;

import java.util.List;

import cn.edustar.jitar.pojos.Category;
import cn.edustar.jitar.pojos.ContentSpace;
import cn.edustar.jitar.pojos.ContentSpaceArticle;

public interface ContentSpaceService {
	
	/** 分类部分 */
	public void saveOrUpdateContentSpace(ContentSpace contentSpace);
	public void deleteContentSpace(ContentSpace contentSpace);
	public List<ContentSpace> getAllContentSpaceList(Integer ownerType, Integer ownerId);
	public ContentSpace getContentSpaceById(int contentSpaceId);
	public int getContentSpaceArticleCountById(int contentSpaceId);
	/**
	 * newParentId 是 contentSpaceId 的子分类 
	 * @param contentSpaceId
	 * @param newParentId
	 * @return true 是子分类； false 不是子分类
	 */
	public boolean isInChildPath(int contentSpaceId,int newParentId);	
	/** 得到转换成List<Category> */
	public List<Category> getContentSpaceTreeList(Integer ownerType, Integer ownerId);	
	/**得到其子分类 */
	public List<ContentSpace> getContentSpaceList(Integer parentId);	
	
	
	/** 文章部分 */	
	public void saveOrUpdateArticle(ContentSpaceArticle contentSpaceArticle);
	public void deleteContentSpaceArticle(ContentSpaceArticle contentSpaceArticle);
	public ContentSpaceArticle getContentSpaceArticleById(int contentSpaceArticleId);
	public void deleteContentSpaceArticleByContentSpaceId(int contentSpaceId);
}
