package cn.edustar.push;

import java.util.Calendar;
import java.util.List;
import java.text.SimpleDateFormat;

import cn.edustar.jitar.dao.hibernate.BaseDaoHibernate;


/**
 * @author 孟宪会
 */
public class MashupDaoHibernate extends BaseDaoHibernate implements MashupDao {

	/** 保存 Mashup 内容 */
	public void saveMashupContent(MashupContent mashupContent)
	{
		this.getSession().save(mashupContent);
	}
	
	public MashupContent getMashupContent(int mashupContentId)
	{
		return (MashupContent)this.getSession().get(MashupContent.class, mashupContentId);
	}
	
	/** 删除 Mashup 内容 */
	public void deleteMashupContent(MashupContent mashupContent)
	{
		this.getSession().delete(mashupContent);		
	}
	
	/** 更新 Mashup 内容 */
	public void updateMashupContent(MashupContent mashupContent)
	{
		this.getSession().update(mashupContent);
	}
	
	/** 检查所推送的资源是否已经存在 */
	@SuppressWarnings("unchecked")
	public boolean mashupContentIsExist(MashupContent mashupContent)
	{
		//此时，mashupContent 并不一定在数据库中，所以，不能使用 mashupContent.mashupContentId来加载
		String queryString = "FROM MashupContent Where documentType = ? And orginId = ? And href = ?";
		List<MashupContent> mc = (List<MashupContent>)this.getSession().createQuery(queryString)
				.setString(0, mashupContent.getDocumentType())
				.setInteger(1, mashupContent.getOrginId())
				.setString(2, mashupContent.getHref())
				.list();
		return (mc.size() > 0);
	}
	
	/** 添加一个登录用户 */
	public void saveOrUpdateMashupUser(MashupUser mashupUser)
	{
		this.getSession().saveOrUpdate(mashupUser);
	}
	/** 加载一个登录用户 */
	public MashupUser getMashupUserByGuid(String userGuid)
	{
		String queryString = "FROM MashupUser Where mashupUserGuid = ?";
		return (MashupUser)this.getSession().createQuery(queryString).setString(0, userGuid).uniqueResult();
	}
	
	/** 删除所有登录过期的用户 */
	public void deleteAllInValidMashupUser()
	{
		SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar d = Calendar.getInstance();	
		d.add(Calendar.MINUTE, -60);
		String startDate = s.format(d.getTime());
		
		//System.out.println("startDate = " + startDate);
		//System.out.println("endDate = " + endDate);
		String queryString = "DELETE FROM MashupUser Where lastUpdated < '" + startDate + "' ";
		this.getSession().createQuery(queryString).executeUpdate();
		//queryString = "From MashupUser Where lastUpdated between '" + startDate + "' and '" + endDate + "' ";
		//List<MashupUser> m = (List<MashupUser>)this.getHibernateTemplate().find(queryString);
		//System.out.println("m.length = " + m.size());
		//for(MashupUser u : m)
		//{
		//	System.out.println("m = " + u.getTitle());
		//}
	}
	
	/** 得到所有的机构和域名 */
	@SuppressWarnings("unchecked")
	public List<MashupPlatform> getAllMashupPlatform(Boolean isApproved)
	{
		String queryString;
		if(null == isApproved || false == isApproved)
		{
			queryString = "FROM MashupPlatform ORDER BY mashupPlatformId DESC";
		}
		else
		{
			queryString = "FROM MashupPlatform WHERE platformState = 0 ORDER BY mashupPlatformId DESC";
		}
		
		return (List<MashupPlatform>)this.getSession().createQuery(queryString).list();
	}
	
	/** 得到机构和域名 */
	public MashupPlatform getMashupPlatformByGuid(String unitGuid)
	{
		String queryString = "FROM MashupPlatform Where platformGuid=?";
		return (MashupPlatform)this.getSession().createQuery(queryString).setString(0, unitGuid).uniqueResult();
	}
	
	/** 得到机构和域名 */
	public MashupPlatform getMashupPlatformById(int mashupPlatformId)
	{
		return (MashupPlatform)this.getSession().get(MashupPlatform.class, mashupPlatformId);
	}
	
	/** 更新机构信息 */
	public void saveOrUpdateMashupPlatform(MashupPlatform mashupPlatform)
	{
		this.getSession().saveOrUpdate(mashupPlatform);
	}
	
	/** 删除机构信息 */
	public void deleteMashupPlatform(MashupPlatform mashupPlatform){
		String queryString = "DELETE FROM MashupContent Where platformGuid = ?";
		this.getSession().createQuery(queryString).setString(0, mashupPlatform.getPlatformGuid()).executeUpdate();
		this.getSession().delete(mashupPlatform);
		
	}
	
	/** 更新机构的信息 */
	public void updateMashupContentByPlatform(MashupPlatform mashupPlatform)
	{
		String queryString = "UPDATE MashupContent Set platformName=?,href=? Where platformGuid = ?";
		this.getSession().createQuery(queryString)
		.setString(0, mashupPlatform.getPlatformName())
		.setString(1, mashupPlatform.getPlatformHref())
		.setString(2, mashupPlatform.getPlatformGuid())
		.executeUpdate();		
		
		queryString = "UPDATE MashupBlogGroup Set platformName=?,href=? Where platformGuid = ?";
		this.getSession().createQuery(queryString)
		.setString(0, mashupPlatform.getPlatformName())
		.setString(1, mashupPlatform.getPlatformHref())
		.setString(2, mashupPlatform.getPlatformGuid())
		.executeUpdate();		
	}
	
	/** 更新原来文章的推送状态 0:未设置，1:已经推送，2:待推送 */
	public void setOrginObjectPushed(String documentType,int pushState)
	{
		String queryString = "";
		if(null == documentType) return;
		if(documentType.equalsIgnoreCase("article"))
		{
			queryString = "UPDATE Article Set pushState = ?";
		}
		else if(documentType.equalsIgnoreCase("resource"))
		{
			queryString = "UPDATE Resource Set pushState = ?";
		}
		
		this.getSession().createQuery(queryString).setInteger(0, pushState).executeUpdate();
	}
	
	/** 检查资源是否已经存在 */
	@SuppressWarnings("unchecked")
	public boolean mashupBlogGroupIsExist(MashupBlogGroup mashupBlogGroup)
	{
		String queryString = "FROM MashupBlogGroup Where contentType = ? And orginId = ? And href = ?";
		List<MashupBlogGroup> mbg = (List<MashupBlogGroup>)this.getSession().createQuery(queryString)
				.setString(0, mashupBlogGroup.getContentType())
				.setInteger(1,  mashupBlogGroup.getOrginId())
				.setString(2, mashupBlogGroup.getHref())
				.list();
		return (mbg.size() > 0);
	}
	
	/** 保存对象 */
	public void saveMashupBlogGroup(MashupBlogGroup mashupBlogGroup)
	{
		this.getSession().save(mashupBlogGroup);
	}
	/** 更新对象 */
	public void updateMashupBlogGroup(MashupBlogGroup mashupBlogGroup){
		this.getSession().update(mashupBlogGroup);
	}
	/** 加载对象 */
	public MashupBlogGroup getMashupBlogGroupById(int mashupBlogGroupId)
	{
		return (MashupBlogGroup)this.getSession().get(MashupBlogGroup.class, mashupBlogGroupId);
	}
	
	/** 删除对象 */
	public void deleteMashupBlogGroup(MashupBlogGroup mashupBlogGroup)
	{
		this.getSession().delete(mashupBlogGroup);
	}
}
