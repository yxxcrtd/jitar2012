package cn.edustar.jitar.dao.hibernate;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import cn.edustar.jitar.dao.SpecialSubjectDao;
import cn.edustar.jitar.pojos.NewSpecialSubject;
import cn.edustar.jitar.pojos.SpecialSubject;
import cn.edustar.jitar.pojos.SpecialSubjectArticle;
import cn.edustar.jitar.pojos.SpecialSubjectPhoto;

public class SpecialSubjectDaoHibernate extends BaseDaoHibernate implements SpecialSubjectDao {
	/**
	 * 保存专题
	 * @param specialSubject
	 */
	public void saveOrUpdateSpecialSubject(SpecialSubject specialSubject)
	{
		this.getSession().saveOrUpdate(specialSubject);
	}
	
	/**
	 * 加载一个专题
	 * @param specialSubjectId
	 * @return
	 */
	public SpecialSubject getSpecialSubject(int specialSubjectId)
	{
		return (SpecialSubject)this.getSession().get(SpecialSubject.class, specialSubjectId);
	}
	
	/**
	 * 根据 guid 得到专题对象
	 * @param specialSubjectGuid
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public SpecialSubject getSpecialSubjectByGuid(String specialSubjectGuid)
	{
		String queryString = "FROM SpecialSubject Where ObjectGuid = ?";
		List<SpecialSubject> sl = (List<SpecialSubject>)this.getSession().createQuery(queryString).setString(0, specialSubjectGuid).list();
		if(sl.size() > 0)
		{
			return (SpecialSubject)sl.get(0);
		}
		else
		{
			return null;
		}
	}
	
	/**
	 * 删除一个专题
	 * @param specialSubject
	 */
	public void deleteSpecialSubject(SpecialSubject specialSubject)
	{
		//删除文件，图片，
		String queryString = "DELETE FROM SpecialSubjectArticle Where specialSubjectId = ?";
		this.getSession().createQuery(queryString).setInteger(0, specialSubject.getSpecialSubjectId()).executeUpdate();
		
		queryString = "UPDATE Photo Set specialSubjectId = 0 Where specialSubjectId = ?";
		this.getSession().createQuery(queryString).setInteger(0, specialSubject.getSpecialSubjectId()).executeUpdate();
		
		this.getSession().delete(specialSubject);
	}
	
	/**
	 * 根据标识删除专题
	 * @param specialSubjectId
	 */
	public void deleteSpecialSubjectById(int specialSubjectId)
	{
		SpecialSubject specialSubject = this.getSpecialSubject(specialSubjectId);
		if(specialSubject != null)
		{
			this.deleteSpecialSubject(specialSubject);
		}
	}
	
	/**
	 * 得到没有过期的所有专题
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<SpecialSubject> getValidSpecialSubjectList()
	{
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		String d = sf.format(new Date());		
		String queryString = "FROM SpecialSubject Where '" + d + "' between '2009-05-12' and expiresDate) Order By specialSubjectId DESC";
		return (List<SpecialSubject>)this.getSession().createQuery(queryString).list();
	}
	
	
	/**
	 * 判断标识是否过期
	 * @param specialSubject
	 * @return
	 */
	public boolean checkSpecialSubjectIsExpired(SpecialSubject specialSubject)
	{
		if(specialSubject == null) return true;
		Date d = new Date();
		return d.after(specialSubject.getExpiresDate());
	}
	
	/**
	 * 更新一个专题文章
	 * @param specialSubjectArticle
	 */
	public void saveOrUpdateSpecialSubjectArticle(SpecialSubjectArticle specialSubjectArticle)
	{
		this.getSession().saveOrUpdate(specialSubjectArticle);
	}
	
	/**
	 * 得到专题文章，我们规定，一篇文章只属于一个专题。这是因为，文章编辑界面限制的
	 * @param articleId
	 * @return
	 */
	public SpecialSubjectArticle getSpecialSubjectArticleByArticleId(String  articleGuid)
	{
		String queryString = "FROM SpecialSubjectArticle Where articleGuid = ?";
		List<SpecialSubjectArticle> list = (List<SpecialSubjectArticle>)this.getSession().createQuery(queryString).setString(0,articleGuid).list();
		if(list == null || list.size()<1) return null;
		else return (SpecialSubjectArticle)list.get(0);		
	}
	
	
	/**
	 * 删除专题文章
	 * @param specialSubjectArticle
	 */
	public void deleteSpecialSubjectArticle(SpecialSubjectArticle specialSubjectArticle)
	{
		this.getSession().delete(specialSubjectArticle);
	}
	
	/**
	 * 删除一个专题的所有文章
	 * @param specialSubjectId
	 */
	public void deleteSpecialSubjectArticleBySpecialSubjectId(int specialSubjectId)
	{
		String queryString = "DELETE FROM SpecialSubjectArticle Where specialSubjectId = ?";
		this.getSession().createQuery(queryString).setInteger(0, specialSubjectId).executeUpdate();
	}
	
	/**
	 * 从所有专题中删除一个文章
	 * @param aricleId
	 */
	public void removeArticleFromSpecialSubjectArticleByAricleId(int aricleId)
	{
		String queryString = "DELETE FROM SpecialSubjectArticle Where aricleId = ?";
		this.getSession().createQuery(queryString).setInteger(0, aricleId).executeUpdate();
	}
	
	/**
	 * 删除用户的所有文章
	 * @param userId
	 */
	public void removeArticleFromSpecialSubjectArticleByUserId(int userId){
		String queryString = "DELETE FROM SpecialSubjectArticle Where articleUserId = ?";
		this.getSession().createQuery(queryString).setInteger(0, userId).executeUpdate();
		}

	
	/**
	 * 更新一个专题照片
	 * @param specialSubjectPhoto
	 */
	public void saveOrUpdateSpecialSubjectPhoto(SpecialSubjectPhoto specialSubjectPhoto)
	{
		this.getSession().saveOrUpdate(specialSubjectPhoto);
	}
	
	/**
	 * 删除一个专题图片
	 * @param specialSubjectPhoto
	 */
	public void deleteSpecialSubjectPhoto(SpecialSubjectPhoto specialSubjectPhoto)
	{
		this.getSession().delete(specialSubjectPhoto);
	}
	
	/**
	 * 删除一个专题的所有图片
	 * @param specialSubjectId
	 */
	public void deleteSpecialSubjectPhotoBySpecialSubjectId(int specialSubjectId){
		String queryString = "DELETE FROM SpecialSubjectPhoto Where specialSubjectId = ?";
		this.getSession().createQuery(queryString).setInteger(0, specialSubjectId).executeUpdate();
	}
	
	/**
	 * 从所有专题中删除一个图片
	 * @param photoId
	 */
	public void removePhotoFromSpecialSubjectPhotoByPhotoId(int photoId){
		String queryString = "DELETE FROM SpecialSubjectPhoto Where photoId = ?";
		this.getSession().createQuery(queryString).setInteger(0, photoId).executeUpdate();
	}
	
	/**
	 * 删除一个用户的所有照片
	 * @param userId
	 */
	public void removePhotoFromSpecialSubjectPhotoByUserIdId(int userId)
	{
		String queryString = "DELETE FROM SpecialSubjectPhoto Where photoUserId = ?";
		this.getSession().createQuery(queryString).setInteger(0, userId).executeUpdate();
	}
	
	/**
	 * 删除一个用户创建的所有专题
	 * @param userId
	 */
	public void deleteSpecialSubjectByUserId(int userId)
	{
		String queryString = "DELETE FROM SpecialSubject Where createUserId = ?";
		this.getSession().createQuery(queryString).setInteger(0, userId).executeUpdate();
	}
	
	/**
	 * 添加一个候选主题
	 * @param newSpecialSubject
	 */
	public void addNewSpecialSubject(NewSpecialSubject newSpecialSubject)
	{
		this.getSession().save(newSpecialSubject);
	}
	
	/**
	 * 删除一个候选主题
	 * @param newSpecialSubject
	 */
	public void deleteNewSpecialSubject(NewSpecialSubject newSpecialSubject)
	{
		this.getSession().delete(newSpecialSubject);
	}
	
	/**
	 * 删除某用户的所有候选主题
	 * @param createUserId
	 */
	public void deleteNewSpecialSubjectByCreateUserId(int createUserId)
	{
		String queryString = "DELETE FROM NewSpecialSubject Where createUserId = ?";
		this.getSession().createQuery(queryString).setInteger(0, createUserId).executeUpdate(); 
	}
	
   /**
    * 根据标识删除候选主题
    * @param newSpecialSubjectId
    */
	public void deleteNewSpecialSubjectById(int newSpecialSubjectId)
	{
		String queryString = "DELETE FROM NewSpecialSubject Where newSpecialSubjectId = ?";
		this.getSession().createQuery(queryString).setInteger(0, newSpecialSubjectId).executeUpdate(); 
	}
	
	/**
	 * 得到最新的候选话题
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<NewSpecialSubject> getAllNewSpecialSubject()
	{
		String queryString = "FROM NewSpecialSubject Order By newSpecialSubjectId DESC";
		return (List<NewSpecialSubject>)this.getSession().createQuery(queryString).list();
	}
	
	/**
	 * 得到某种类型的专题中一个最新的
	 * @param objectType
	 * @return
	 */
	public SpecialSubject getNewestSpecialSubjectByType(String objectType)
	{
		String queryString = "FROM SpecialSubject Where objectType = ? Order By specialSubjectId DESC";
		return (SpecialSubject)this.getSession().createQuery(queryString).setString(0, objectType).uniqueResult();
	}
	
	/**
	 * 得到特定对象的专题中一个最新的
	 * @param objectType
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public SpecialSubject getNewestSpecialSubjectByTypeId(int objectTypeId)
	{
		String queryString = "FROM SpecialSubject Where objectId = :objectId Order By specialSubjectId DESC";
		List<SpecialSubject> ls = this.getSession().createQuery(queryString).setInteger("objectId", objectTypeId).list();
		if(ls==null || ls.size() == 0) return null;
		return (SpecialSubject)ls.get(0);
	}
	
	/** 按文章id的引用进行删除  */
	public void deleteSubjectArticleByArticleId(int articleId)
	{
		String queryString = "DELETE FROM SpecialSubjectArticle Where articleId=:articleId";
		this.getSession().createQuery(queryString).setInteger("articleId", articleId).executeUpdate();		
	}
	public void deleteSubjectArticleByArticleGuid(String articleGuid)
	{
		String queryString = "DELETE FROM SpecialSubjectArticle Where articleGuid=:articleGuid";
		this.getSession().createQuery(queryString).setString("articleGuid", articleGuid).executeUpdate();	
	}
	
}
