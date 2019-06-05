package cn.edustar.jitar.dao.hibernate;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.hibernate.jdbc.Work;

import cn.edustar.jitar.dao.ChannelPageDao;
import cn.edustar.jitar.model.ObjectType;
import cn.edustar.jitar.pojos.AccessControl;
import cn.edustar.jitar.pojos.Channel;
import cn.edustar.jitar.pojos.ChannelModule;
import cn.edustar.jitar.pojos.ChannelArticle;
import cn.edustar.jitar.pojos.ChannelPhoto;
import cn.edustar.jitar.pojos.ChannelResource;
import cn.edustar.jitar.pojos.ChannelUser;
import cn.edustar.jitar.pojos.ChannelUserStat;
import cn.edustar.jitar.pojos.ChannelVideo;

public class ChannelPageDaoHibernate extends BaseDaoHibernate implements ChannelPageDao {
	
	public Channel getChannel(int ChannelId)
	{
		Channel channel = (Channel)this.getSession().get(Channel.class, ChannelId);
		return channel;
	}
	
	public void saveOrUpdateChannel(Channel channel)
	{
		this.getSession().saveOrUpdate(channel);
	}	
	
	public ChannelUser getChannelUser(int channelUserId){
		return (ChannelUser)this.getSession().get(ChannelUser.class, channelUserId);
	}
	
	@SuppressWarnings("unchecked")
	public ChannelUser getChannelUserByUserIdAndChannelId(int userId, int channelId){
		String queryString = "FROM ChannelUser WHERE channelId = :channelId And userId = :userId";
		List<ChannelUser> ls = this.getSession().createQuery(queryString).setInteger("channelId", channelId).setInteger("userId", userId).list();
		if(ls == null || ls.size() == 0) return null;
		return (ChannelUser)ls.get(0);
	}
	
	public void saveOrUpdateChannelUser(ChannelUser channelUser){
		this.getSession().saveOrUpdate(channelUser);
	}
	
	public void deleteChannelUser(ChannelUser channelUser){
		this.getSession().delete(channelUser);
	}
	
	public void deleteChannelUserByUserIdAndChannelId(int channelId, int userId){
		String queryString = "DELETE ChannelUser WHERE channelId = :channelId And userId = :userId";
		this.getSession().createQuery(queryString).setInteger("channelId", channelId).setInteger("userId", userId).executeUpdate();
	}
	
	@SuppressWarnings("unchecked")
	public List<Channel> getChannelList()
	{
		String queryString = "FROM Channel Order By channelId ASC";		
		List<Channel> fc = this.getSession().createQuery(queryString).list();
		return fc;
	}
	public void deleteChannel(Channel channel)
	{
		this.getSession().delete(channel);
	}	
	
	public ChannelModule getChannelModule(int moduleId)
	{
		return (ChannelModule) this.getSession().get(ChannelModule.class, moduleId);
	}
	public void saveOrUpdateChannelModule(ChannelModule channelModule)
	{
		this.getSession().saveOrUpdate(channelModule);
		
	}

	@SuppressWarnings("unchecked")
	public List<ChannelModule> getChannelModuleList(int channelId)
	{
		String queryString = "FROM ChannelModule Where channelId = :channelId Order By moduleId DESC";
		List<ChannelModule> fcm = this.getSession().createQuery(queryString).setInteger("channelId",channelId).list();
		return fcm;
	}
	
	public void deleteChannelModule(ChannelModule channelModule)
	{
		this.getSession().delete(channelModule);
	}
	
	@SuppressWarnings("unchecked")
	public ChannelModule getChannelModuleByDisplayName(String displayName,int channelId)
	{
		String queryString = "FROM ChannelModule Where displayName = :displayName And channelId = :channelId";
		List<ChannelModule> ls  = this.getSession().createQuery(queryString).setString("displayName", displayName).setInteger("channelId", channelId).list();
		if(ls == null || ls.size() == 0) return null;
		return ls.get(0);
		//ChannelModule fcm = (ChannelModule)this.getSession().createQuery(queryString).setString("displayName", displayName).setInteger("channelId", channelId).uniqueResult();
		//return fcm;
	}
	
	public void deleteChannelArticle(int channelId)
	{		
		String queryString = "DELETE ChannelArticle Where channelId = :channelId";
		this.getSession().createQuery(queryString).setInteger("channelId", channelId).executeUpdate();
		//this.getSession().bulkUpdate(queryString, channelId);
		//queryString = "DELETE Category Where itemType = :itemType";
		//this.getSession().createQuery(queryString).setString("itemType", "channel_article_" + channelId ).executeUpdate();
	}
	
	public void deleteChannelArticleById(int channelArticleId)
	{
		String queryString = "DELETE ChannelArticle Where channelArticleId = :channelArticleId";
		this.getSession().createQuery(queryString).setInteger("channelArticleId", channelArticleId).executeUpdate();
	}
	public void deleteChannelResource(int channelId)
	{
		String queryString = "DELETE ChannelResource Where channelId = :channelId";
		this.getSession().createQuery(queryString).setInteger("channelId", channelId).executeUpdate();
		
		//queryString = "DELETE Category Where itemType = :itemType";
		//this.getSession().createQuery(queryString).setString("itemType", "channel_resource_" + channelId ).executeUpdate();

	}
	public void deleteChannelPhoto(int channelId)
	{
		String queryString = "DELETE ChannelPhoto Where channelId = :channelId";
		this.getSession().createQuery(queryString).setInteger("channelId", channelId).executeUpdate();
		
		//queryString = "DELETE Category Where itemType = :itemType";
		//this.getSession().createQuery(queryString).setString("itemType", "channel_photo_" + channelId ).executeUpdate();
	}
	public void deleteChannelVideo(int channelId)
	{
		String queryString = "DELETE ChannelVideo Where channelId = :channelId";
		this.getSession().createQuery(queryString).setInteger("channelId", channelId).executeUpdate();

		//queryString = "DELETE Category Where itemType = :itemType";
		//this.getSession().createQuery(queryString).setString("itemType", "channel_video_" + channelId ).executeUpdate();
	}
	
	public void deleteChannelOtherData(int channelId)
	{
		String queryString;
		queryString = "DELETE FROM AccessControl Where (objectType = "+ AccessControl.OBJECTTYPE_CHANNELSYSTEMADMIN +" or objectType = "+AccessControl.OBJECTTYPE_CHANNELUSERADMIN+" or objectType = "+AccessControl.OBJECTTYPE_CHANNELCONTENTADMIN+") And objectId = :objectId";
		this.getSession().createQuery(queryString).setInteger("objectId", channelId).executeUpdate();		
		
		queryString = "DELETE FROM ChannelModule Where channelId = :channelId";
		this.getSession().createQuery(queryString).setInteger("channelId", channelId).executeUpdate();
		
		queryString = "DELETE FROM Placard Where objType = :objType And objId = :channelId";
		this.getSession().createQuery(queryString).setInteger("objType", ObjectType.OBJECT_TYPE_CHANNEL.getTypeId()).setInteger("channelId", channelId).executeUpdate();
		
		queryString = "DELETE FROM SiteNav Where ownerType = 3 And ownerId = :ownerId";
		this.getSession().createQuery(queryString).setInteger("ownerId", channelId).executeUpdate();
	}
	
	@SuppressWarnings("unchecked")
	public ChannelArticle getChannelArticle(String articleGuid)
	{
		String queryString = "FROM ChannelArticle Where articleGuid = :articleGuid";
		List<ChannelArticle> list = (List<ChannelArticle> )this.getSession().createQuery(queryString).setString("articleGuid", articleGuid).list();
		if(list == null || list.size() == 0)
		{
			return null;
		}
		else
		{
			return (ChannelArticle)list.get(0);
		}
	}
	
	public ChannelArticle getChannelArticleById(int channelArticleId)
	{
		return (ChannelArticle)this.getSession().get(ChannelArticle.class, channelArticleId);
	}
	
	@SuppressWarnings("unchecked")
	public ChannelArticle getChannelArticleByChannelIdAndArticleId(int channelId, int articleId)
	{
		String queryString = "FROM ChannelArticle Where channelId = :channelId And articleId = :articleId";
		List<ChannelArticle> ls = this.getSession().createQuery(queryString).setInteger("channelId", channelId).setInteger("articleId", articleId).list();
		if(ls == null || ls.size() == 0) return null;
		return ls.get(0);
	}
	public void saveOrUpdateChannelArticle(ChannelArticle channelArticle)
	{
		this.getSession().saveOrUpdate(channelArticle);
		this.getSession().flush();
	}
	
	public void deleteChannelArticle(ChannelArticle channelArticle)
	{
		this.getSession().delete(channelArticle);
		this.getSession().flush();
	}
	
	public void deleteChannelArticleByGuid(String articleGuid)
	{		
		String queryString = "DELETE FROM ChannelArticle Where articleGuid = :articleGuid";
		this.getSession().createQuery(queryString).setString("articleGuid", articleGuid).executeUpdate();
	}
	
	
	public ChannelResource getChannelResource(int channelResourceId){
		return (ChannelResource)this.getSession().get(ChannelResource.class, channelResourceId);
	}
	@SuppressWarnings("unchecked")
	public ChannelResource getChannelResourceByChannelIdAndResourceId(int channelId, int resourceId){
		String queryString = "FROM ChannelResource Where channelId = :channelId And resourceId = :resourceId";
		List<ChannelResource> ls = this.getSession().createQuery(queryString).setInteger("channelId", channelId).setInteger("resourceId", resourceId).list();
		if(ls == null || ls.size() == 0) return null;
		return ls.get(0);
	}
	public void saveOrUpdateChannelResource(ChannelResource channelResource){
		this.getSession().saveOrUpdate(channelResource);
	}
	public void deleteChannelResource(ChannelResource channelResource){
		this.getSession().delete(channelResource);
	}
	
	public ChannelPhoto getChannelPhoto(int channelPhotoId){
		return (ChannelPhoto)this.getSession().get(ChannelPhoto.class, channelPhotoId);
	}
	public void saveOrUpdateChannelPhoto(ChannelPhoto channelPhoto){
		this.getSession().saveOrUpdate(channelPhoto);
		this.getSession().flush();
	}
	public void deleteChannelPhoto(ChannelPhoto channelPhoto){
		this.getSession().delete(channelPhoto);
	}
	
	public ChannelVideo getChannelVideo(int channelVideoId){
		return (ChannelVideo)this.getSession().get(ChannelVideo.class, channelVideoId);
	}
	@SuppressWarnings("unchecked")
	public ChannelVideo getChannelVideoByChannelIdAndVideoId(int channelId, int videoId){
		String queryString = "FROM ChannelVideo Where channelId = :channelId And videoId = :videoId";
		List<ChannelVideo> ls = this.getSession().createQuery(queryString).setInteger("channelId", channelId).setInteger("videoId", videoId).list();
		if(ls == null || ls.size() == 0) return null;
		return ls.get(0);
	}
	public void saveOrUpdateChannelVideo(ChannelVideo channelVideo){
		this.getSession().saveOrUpdate(channelVideo);
	}
	public void deleteChannelVideo(ChannelVideo channelVideo){
		this.getSession().delete(channelVideo);
	}
	
	
	public int getChannelArticleCount(int channelId, int userId, String startDate, String endDate)
	{
		String queryString;
		Object count;
		if(startDate != null && startDate.length() > 0 && endDate != null && endDate.length() > 0)
		{
			startDate = startDate + " 00:00:00";
			endDate = endDate + " 23:59:59";
			queryString = "SELECT COUNT(*) FROM ChannelArticle WHERE channelId=:channelId And userId = :userId And (CreateDate between :startDate And :endDate)";
			count = this.getSession().createQuery(queryString).setInteger("channelId", channelId).setInteger("userId", userId).setString("startDate", startDate).setString("endDate", endDate).uniqueResult();
		}
		else{
			queryString = "SELECT COUNT(*) FROM ChannelArticle WHERE channelId=:channelId And userId = :userId";
			count = this.getSession().createQuery(queryString).setInteger("channelId", channelId).setInteger("userId", userId).uniqueResult();
		}
		if(count == null) return 0;
		return Integer.valueOf(count.toString()).intValue();
		
	}
	public int getChannelResourceCount(int channelId, int userId, String startDate, String endDate){
		String queryString;
		Object count;
		if(startDate != null && startDate.length() > 0 && endDate != null && endDate.length() > 0)
		{
			startDate = startDate + " 00:00:00";
			endDate = endDate + " 23:59:59";
			queryString = "SELECT COUNT(*) FROM ChannelResource WHERE channelId=:channelId And userId = :userId And (CreateDate between :startDate And :endDate)";
			count = this.getSession().createQuery(queryString).setInteger("channelId", channelId).setInteger("userId", userId).setString("startDate", startDate).setString("endDate", endDate).uniqueResult();
		}
		else{
			queryString = "SELECT COUNT(*) FROM ChannelResource WHERE channelId=:channelId And userId = :userId";
			count = this.getSession().createQuery(queryString).setInteger("channelId", channelId).setInteger("userId", userId).uniqueResult();
		}
		if(count == null) return 0;
		return Integer.valueOf(count.toString()).intValue();
	}
	public int getChannelPhotoCount(int channelId, int userId, String startDate, String endDate)
	{
		String queryString;
		Object count;
		if(startDate != null && startDate.length() > 0 && endDate != null && endDate.length() > 0)
		{
			startDate = startDate + " 00:00:00";
			endDate = endDate + " 23:59:59";
			queryString = "SELECT COUNT(*) FROM ChannelPhoto WHERE channelId=:channelId And userId = :userId And (CreateDate between :startDate And :endDate)";
			count = this.getSession().createQuery(queryString).setInteger("channelId", channelId).setInteger("userId", userId).setString("startDate", startDate).setString("endDate", endDate).uniqueResult();
		}
		else{
			queryString = "SELECT COUNT(*) FROM ChannelPhoto WHERE channelId=:channelId And userId = :userId";
			count = this.getSession().createQuery(queryString).setInteger("channelId", channelId).setInteger("userId", userId).uniqueResult();
		}
		if(count == null) return 0;
		return Integer.valueOf(count.toString()).intValue();
		
	}
	public int getChannelVideoCount(int channelId, int userId, String startDate, String endDate){
		String queryString;
		Object count;
		if(startDate != null && startDate.length() > 0 && endDate != null && endDate.length() > 0)
		{
			startDate = startDate + " 00:00:00";
			endDate = endDate + " 23:59:59";
			queryString = "SELECT COUNT(*) FROM ChannelVideo WHERE channelId=:channelId And userId = :userId And (CreateDate between :startDate And :endDate)";
			count = this.getSession().createQuery(queryString).setInteger("channelId", channelId).setInteger("userId", userId).setString("startDate", startDate).setString("endDate", endDate).uniqueResult();
		}
		else{
			queryString = "SELECT COUNT(*) FROM ChannelVideo WHERE channelId=:channelId And userId = :userId";
			count = this.getSession().createQuery(queryString).setInteger("channelId", channelId).setInteger("userId", userId).uniqueResult();
		}
		if(count == null) return 0;
		return Integer.valueOf(count.toString()).intValue();
	}
	public void saveChannelUserStat(ChannelUserStat channelUserStat){
		this.getSession().save(channelUserStat);
	}
	public void deleteChannelUserStat(String statGuid){
		String queryString = "DELETE ChannelUserStat WHERE statGuid=:statGuid";
		this.getSession().createQuery(queryString).setString("statGuid", statGuid).executeUpdate();
	}
	@SuppressWarnings("unchecked")
	public List<Channel> getChannelListByUserId(int userId){
		String queryString = "SELECT channel FROM ChannelUser u LEFT JOIN u.channel channel Where u.channelId=channel.channelId and u.userId = :userId";
		return this.getSession().createQuery(queryString).setInteger("userId", userId).list();
	}

	@SuppressWarnings("unchecked")
	public ChannelPhoto getChannelPhoto(int channelId, int photoId){
		String queryString = "FROM ChannelPhoto WHERE channelId = :channelId And photoId = :photoId";
		List<ChannelPhoto> ls = this.getSession().createQuery(queryString).setInteger("channelId", channelId).setInteger("photoId", photoId).list();
		if(ls == null || ls.size() == 0) return null;
		return ls.get(0);
	}
	@SuppressWarnings("unchecked")
	public List<ChannelPhoto> getChannelPhotoList(int photoId){
		String queryString = "FROM ChannelPhoto WHERE photoId = :photoId";
		List<ChannelPhoto> ls = this.getSession().createQuery(queryString).setInteger("photoId", photoId).list();
		if(ls == null || ls.size() == 0) return null;
		return ls;
	}
	
	@Override
	public void evict(Object object) {
		this.getSession().evict(object);
		
	}

	@Override
	public void flush() {
		this.getSession().flush();
		
	}
	
	@SuppressWarnings("unchecked")
	public List<ChannelResource> getChannelResourceList(int resourceId) {
		String queryString = "FROM ChannelResource WHERE resourceId = :resourceId";
		List<ChannelResource> ls = this.getSession().createQuery(queryString).setInteger("resourceId", resourceId).list();
		if(ls == null || ls.size() == 0) return null;
		return ls;
	}
	
	@SuppressWarnings("unchecked")
	public List<ChannelVideo> getChannelVideoList(int videoId) {
		String queryString = "FROM ChannelVideo WHERE videoId = :videoId";
		List<ChannelVideo> ls = this.getSession().createQuery(queryString).setInteger("videoId", videoId).list();
		if(ls == null || ls.size() == 0) return null;
		return ls;
	}
	
	@SuppressWarnings("unchecked")
	public List<ChannelArticle> getChannelArticleList(int articleId) {
		String queryString = "FROM ChannelArticle WHERE articleId = :articleId";
		List<ChannelArticle> ls = this.getSession().createQuery(queryString).setInteger("articleId", articleId).list();
		if(ls == null || ls.size() == 0) return null;
		return ls;
	}

	public void statUserData(int channelId, String statGuid, String keyWord, String filter, String startDate, String endDate)
	{
		final String k = keyWord;
		final String guid = statGuid;
		final String f = filter;
		final String s = startDate;
		final String e = endDate;
		final int id = channelId;
		
		this.getSession().doWork(new Work() {
			public void execute(Connection connection) throws SQLException {		
					//@KeyWords NVARCHAR(256),@Filter VARCHAR(50),@StartDate VARCHAR(50),	@EndDate VARCHAR(50),@StatGuid  VARCHAR(50),@ChannelId int
					PreparedStatement ps = connection.prepareStatement("{call ChannelUserStat(?,?,?,?,?,?)}");
					ps.setString(1, k);
					ps.setString(2, f);
					ps.setString(3, s);
					ps.setString(4, e);
					ps.setString(5, guid);
					ps.setInt(6, id);
					ps.executeUpdate();
					}
				});
	}
	
	public void statUnitData(int channelId, String statGuid, String startDate, String endDate)
	{
		final String guid = statGuid;
		final String s = startDate;
		final String e = endDate;
		final int id = channelId;
		
		this.getSession().doWork(new Work() {
			public void execute(Connection connection) throws SQLException {		
					//@KeyWords NVARCHAR(256),@Filter VARCHAR(50),@StartDate VARCHAR(50),	@EndDate VARCHAR(50),@StatGuid  VARCHAR(50),@ChannelId int
					PreparedStatement ps = connection.prepareStatement("{call ChannelUnitStat(?,?,?,?)}");
					ps.setString(1, s);
					ps.setString(2, e);
					ps.setString(3, guid);
					ps.setInt(4, id);
					ps.executeUpdate();
					}
				});
	}
}
