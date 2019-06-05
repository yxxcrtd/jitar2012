package cn.edustar.jitar.service;

import java.util.List;

//import cn.edustar.jitar.model.ArticleModelEx;
import cn.edustar.jitar.pojos.GroupArticle;


public interface GroupArticleService {
	/**
	 * 得到指定群组指定数量的最新文章列表.
	 * @param groupId - 群组标识
	 * @param count - 获取数量
	 * @return 返回的 ArticleModelEx 中设置有 GroupArticle 对象, 其对应的属性从而可以被访问.
	 */
	public List<GroupArticle> getNewGroupArticleList(int groupId, int count);
	public List<GroupArticle> getNewGroupArticleList(int groupId, int count,boolean includeChildGroup);
	
	/**
	 * 得到指定群组指定数量的热门文章列表.
	 * @param groupId - 群组标识
	 * @param count - 获取数量
	 * @return
	 */
	public List<GroupArticle> getHotGroupArticleList(int groupId, int count);
	public List<GroupArticle> getHotGroupArticleList(int groupId, int count,boolean includeChildGroup);
	
	/**
	 * 得到指定群组指定数量的精华文章列表.
	 * @param groupId - 群组标识
	 * @param count - 获取数量
	 * @return
	 */
	public List<GroupArticle> getBestGroupArticleList(int groupId, int count);
	public List<GroupArticle> getBestGroupArticleList(int groupId, int count,boolean includeChildGroup);
	
	/**
	 * 某文章是否是小组精华
	 * 不是小组文章返回False
	 * 是小组文章但不是精华返回False
	 * 是小组精华文章返回True
	 * @param articleId
	 * @return
	 */
	public boolean isBestInGroupArticle(int articleId);
	/**
	 * 此方法不应该出现在这里，应该在GroupResourceService中
	 * @param resourceId
	 * @return
	 */
	public boolean isBestInGroupResource(int resourceId);
	
};
