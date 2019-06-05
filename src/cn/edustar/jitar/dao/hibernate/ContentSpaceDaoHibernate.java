package cn.edustar.jitar.dao.hibernate;

import java.util.List;

import cn.edustar.jitar.dao.ContentSpaceDao;
import cn.edustar.jitar.pojos.Category;
import cn.edustar.jitar.pojos.ContentSpace;
import cn.edustar.jitar.pojos.ContentSpaceArticle;
import cn.edustar.jitar.util.CommonUtil;

public class ContentSpaceDaoHibernate extends BaseDaoHibernate implements ContentSpaceDao {

	public void deleteContentSpace(ContentSpace contentSpace) {
		//如果存在子分类，则不允许删除
		List<ContentSpace> list = getContentSpaceList(contentSpace.getContentSpaceId());
		if(null != list && list.size() > 0){
			throw new RuntimeException("子分类存在，不能删除该分类.");
		}
		
		this.getSession().delete(contentSpace);
		this.getSession().flush();
	}
	public ContentSpace getContentSpaceById(int contentSpaceId)
	{
		return (ContentSpace)this.getSession().get(ContentSpace.class, contentSpaceId);
	}
	
	/**
	 * 得到子分类
	 */
	@SuppressWarnings("unchecked")
	public List<ContentSpace> getContentSpaceList(Integer parentId){
		String queryString = "FROM ContentSpace Where parentId =:parentId Order BY contentSpaceId";
		return (List<ContentSpace>)this.getSession().createQuery(queryString).setParameter("parentId", parentId).list();
	}
	
	@SuppressWarnings("unchecked")
	public List<ContentSpace> getAllContentSpaceList(Integer ownerType, Integer ownerId) {
		String queryString = null;
		if(ownerType != null && ownerId != null){
			queryString = "FROM ContentSpace Where ownerType =:ownerType And ownerId =:ownerId Order BY parentId,contentSpaceId";
			return (List<ContentSpace>)this.getSession().createQuery(queryString).setParameter("ownerType", ownerType).setParameter("ownerId", ownerId).list();
		}
		else
		{
			queryString = "FROM ContentSpace Order BY parentId,contentSpaceId";
			return (List<ContentSpace>)this.getSession().createQuery(queryString).list();
		}
	}
	
	/** 得到转换成List<Category> */
	@SuppressWarnings("unchecked")
	public List<Category> getContentSpaceTreeList(Integer ownerType, Integer ownerId){
		String queryString = null;
		if(ownerType != null && ownerId != null){
			queryString = "SELECT new cn.edustar.jitar.pojos.Category(cs.contentSpaceId,cs.spaceName,cs.parentId,cs) FROM ContentSpace cs Where cs.ownerType =:ownerType And cs.ownerId =:ownerId Order BY cs.parentId,cs.contentSpaceId";
			return this.getSession().createQuery(queryString).setParameter("ownerType", ownerType).setParameter("ownerId", ownerId).list();
		}
		else
		{
			queryString = "SELECT new cn.edustar.jitar.pojos.Category(cs.contentSpaceId,cs.spaceName,cs.parentId,cs) FROM ContentSpace cs Order BY cs.parentId,cs.contentSpaceId";
			return this.getSession().createQuery(queryString).list();
		}
	}
	
	/**
	 * 计算指定分类的分类路径. 分类路径 = 父分类路径 + c.id + "/" .
	 * @param c
	 * @return 
	 */
	public static final String calcCategoryPath(ContentSpace c) {
		return c.getParentPath() + c.getContentSpaceId() + "/";
	}	
	
	/**
	 * 好像和上面的函数calcCategoryPath(ContentSpace c)没什么区别
	 * @param new_parent
	 * @return
	 */
	public static final String calcParentCategoryPath(ContentSpace new_parent){
		if (new_parent == null) return "/";
		return new_parent.getCategoryPath();		
	}
	
	/**
	 * newParentId 是 contentSpaceId 的子分类 
	 * @param contentSpaceId
	 * @param newParentId
	 * @return true 是子分类； false 不是子分类
	 */
	public boolean isInChildPath(int contentSpaceId,Integer newParentId)
	{
		if(null == newParentId){
			return false;
		}
		ContentSpace origin_contentSpace = getContentSpaceById(contentSpaceId);
		if(null == origin_contentSpace){
			return false;
		}
		if (CommonUtil.equals(origin_contentSpace.getParentId(), newParentId)){
			//父分类没变
			return false;
		}else{
			ContentSpace parent_ContentSpace =  getContentSpaceById(newParentId);
			if(null == parent_ContentSpace){
				return false;
			}else{
				String newParentPath = calcParentCategoryPath(parent_ContentSpace);
				if(newParentPath.indexOf("/"+contentSpaceId+"/") >= 0){
					return true;
				}else{
					return false;
				}
			}
		}
	}
	
	public void saveOrUpdateContentSpace(ContentSpace contentSpace) {
		if(null == contentSpace){
			return ;
		}
		
		Integer parentId =  contentSpace.getParentId();
		
		int csId = contentSpace.getContentSpaceId();
		if (csId > 0 ){
			//修改分类，需要检查其父分类不能是在子分类中，否则报告错误
			//如果父分类有变，检查:新的父分类中如果出现了原来的分类，说明新的分类被设置到了原来的分类下，不允许
			if(isInChildPath(csId,parentId)){
				throw new RuntimeException("不能把分类移动到自己的子分类下面.");
			}
		}
		
		/*计算设置parentPath*/
		if(null == parentId){
			contentSpace.setParentPath("/");
		}else{
			ContentSpace parentContentSpace =  getContentSpaceById(parentId);
			if( null == parentContentSpace){
				contentSpace.setParentId(null);
				contentSpace.setParentPath("/");
			}else{
				contentSpace.setParentPath(calcParentCategoryPath(parentContentSpace));
			}
		}
		
		this.getSession().saveOrUpdate(contentSpace);
		this.getSession().flush();
	}	
	
	public int getContentSpaceArticleCountById(int contentSpaceId)
	{
		String queryString = "SELECT COUNT(*) FROM ContentSpaceArticle Where contentSpaceId =:contentSpaceId";
		Object o = this.getSession().createQuery(queryString).setInteger("contentSpaceId", contentSpaceId).iterate().next();
		if(o == null) return 0;		
		return Integer.valueOf(o.toString()).intValue();
	}

	
	/** 文章部分 */	
	public void saveOrUpdateArticle(ContentSpaceArticle contentSpaceArticle)
	{
		this.getSession().saveOrUpdate(contentSpaceArticle);
		this.getSession().flush();
	}
	public void deleteContentSpaceArticle(ContentSpaceArticle contentSpaceArticle)
	{
		this.getSession().delete(contentSpaceArticle);
		this.getSession().flush();
	}
	public ContentSpaceArticle getContentSpaceArticleById(int contentSpaceArticleId){
		return (ContentSpaceArticle)this.getSession().get(ContentSpaceArticle.class, contentSpaceArticleId);		
	}
	public void deleteContentSpaceArticleByContentSpaceId(int contentSpaceId)
	{
		String queryString = "DELETE FROM ContentSpaceArticle WHERE contentSpaceId=:contentSpaceId";
		this.getSession().createQuery(queryString).setInteger("contentSpaceId", contentSpaceId).executeUpdate();
		this.getSession().flush();
	}

}
