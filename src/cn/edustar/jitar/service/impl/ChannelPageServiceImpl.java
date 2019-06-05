package cn.edustar.jitar.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.*;

import cn.edustar.jitar.dao.ChannelPageDao;
import cn.edustar.jitar.pojos.Article;
import cn.edustar.jitar.pojos.Channel;
import cn.edustar.jitar.pojos.ChannelArticle;
import cn.edustar.jitar.pojos.ChannelModule;
import cn.edustar.jitar.pojos.ChannelPhoto;
import cn.edustar.jitar.pojos.ChannelResource;
import cn.edustar.jitar.pojos.ChannelUser;
import cn.edustar.jitar.pojos.ChannelUserStat;
import cn.edustar.jitar.pojos.ChannelVideo;
import cn.edustar.jitar.pojos.Photo;
import cn.edustar.jitar.pojos.Resource;
import cn.edustar.jitar.pojos.Video;
import cn.edustar.jitar.service.CacheService;
import cn.edustar.jitar.service.ChannelPageService;

public class ChannelPageServiceImpl implements ChannelPageService {

	private CacheService cacheService;
	private ChannelPageDao channelPageDao;
	private static final String CACHEKEY = "channel_alllist";

	public void setChannelPageDao(ChannelPageDao channelPageDao) {
		this.channelPageDao = channelPageDao;
	}

	public void setCacheService(CacheService cacheService) {
		this.cacheService = cacheService;
	}

	@SuppressWarnings("unchecked")
	public Channel getChannel(int channelId) {
		List<Channel> channel_list = (List<Channel>)this.cacheService.get(CACHEKEY);
		Channel _channel= null;
		if(channel_list != null && channel_list.size() > 0){	
			for(Channel _c :channel_list)
			{
				if(_c.getChannelId() == channelId)
				{
					_channel = _c;
					return _channel;
				}
			}
		}
		_channel = this.channelPageDao.getChannel(channelId);
		return _channel;
		
	}

	public void saveOrUpdateChannel(Channel channel) {
		this.channelPageDao.saveOrUpdateChannel(channel);
		this.cacheService.remove(CACHEKEY);
	}
	
	public ChannelUser getChannelUser(int channelUserId){
		return this.channelPageDao.getChannelUser(channelUserId);
	}
	
	public ChannelUser getChannelUserByUserIdAndChannelId(int userId, int channelId)
	{
		return this.channelPageDao.getChannelUserByUserIdAndChannelId(userId, channelId);
	}
	
	public void saveOrUpdateChannelUser(ChannelUser channelUser){
		this.channelPageDao.saveOrUpdateChannelUser(channelUser);
	}
	
	public void deleteChannelUser(ChannelUser channelUser){
		this.channelPageDao.deleteChannelUser(channelUser);
	}
	
	public void deleteChannelUserByUserIdAndChannelId(int channelUserId, int userId){
		this.channelPageDao.deleteChannelUserByUserIdAndChannelId(channelUserId, userId);
	}

	@SuppressWarnings("unchecked")
	public List<Channel> getChannelList() {
		List<Channel> channel_list = (List<Channel>)this.cacheService.get(CACHEKEY);
		if(channel_list == null)
		{
			channel_list = this.channelPageDao.getChannelList();
			this.cacheService.put(CACHEKEY, channel_list);
		}
		return channel_list;
	}

	public void deleteChannel(Channel channel) {
		this.channelPageDao.deleteChannel(channel);
		this.cacheService.remove(CACHEKEY);
	}

	public ChannelModule getChannelModule(int moduleId) {
		return this.channelPageDao.getChannelModule(moduleId);
	}

	public void saveOrUpdateChannelModule(ChannelModule channelModule) {
		this.channelPageDao.saveOrUpdateChannelModule(channelModule);
	}

	public List<ChannelModule> getChannelModuleList(int channelId) {
		return this.channelPageDao.getChannelModuleList(channelId);
	}

	public void deleteChannelModule(ChannelModule channelModule) {
		this.channelPageDao.deleteChannelModule(channelModule);
	}

	public ChannelModule getChannelModuleByDisplayName(String displayName,
			int channelId) {
		return this.channelPageDao.getChannelModuleByDisplayName(displayName,
				channelId);
	}

	public void deleteChannelArticle(int channelId) {
		this.channelPageDao.deleteChannelArticle(channelId);
	}

	public void deleteChannelArticleById(int channelArticleId)
	{
		this.channelPageDao.deleteChannelArticleById(channelArticleId);
	}
	public void deleteChannelResource(int channelId) {
		this.channelPageDao.deleteChannelResource(channelId);
	}

	public void deleteChannelPhoto(int channelId) {
		this.channelPageDao.deleteChannelPhoto(channelId);
	}

	public void deleteChannelVideo(int channelId) {
		this.channelPageDao.deleteChannelVideo(channelId);
	}

	public void deleteChannelOtherData(int channelId) {
		this.channelPageDao.deleteChannelOtherData(channelId);
	}

	public ArrayList<String> getLabel(String inputString) {
		ArrayList<String> ar = new ArrayList<String>();
		try {
			String REGEX = "(?<=#\\[).*?(?=\\])";
			// String REGEX = "#\\u007B.*?\\u007D";
			Pattern p = Pattern.compile(REGEX);
			Matcher m = p.matcher(inputString);
			while (m.find()) {
				ar.add(m.group());
			}
		} catch (Exception e) {
		}
		return ar;
	}

	public ChannelArticle getChannelArticle(String articleGuid) {
		return this.channelPageDao.getChannelArticle(articleGuid);
	}

	public ChannelArticle getChannelArticleById(int channelArticleId) {
		return this.channelPageDao.getChannelArticleById(channelArticleId);
	}

	public void saveOrUpdateChannelArticle(ChannelArticle channelArticle) {
		this.channelPageDao.saveOrUpdateChannelArticle(channelArticle);
	}

	public void deleteChannelArticle(ChannelArticle channelArticle) {
		this.channelPageDao.deleteChannelArticle(channelArticle);
	}

	public void deleteChannelArticleByGuid(String articleGuid) {
		this.channelPageDao.deleteChannelArticleByGuid(articleGuid);
	}
	
	
	public ChannelResource getChannelResource(int channelResourceId){
		return this.channelPageDao.getChannelResource(channelResourceId);
	}
	public ChannelResource getChannelResourceByChannelIdAndResourceId(int channelId, int resourceId){
		return this.channelPageDao.getChannelResourceByChannelIdAndResourceId(channelId, resourceId);
	}
	public void saveOrUpdateChannelResource(ChannelResource channelResource){
		this.channelPageDao.saveOrUpdateChannelResource(channelResource);
	}
	public void deleteChannelResource(ChannelResource channelResource){
		this.channelPageDao.deleteChannelResource(channelResource);
	}
	
	public ChannelPhoto getChannelPhoto(int channelPhotoId){
		return this.channelPageDao.getChannelPhoto(channelPhotoId);
	}
	public void saveOrUpdateChannelPhoto(ChannelPhoto channelPhoto){
		this.channelPageDao.saveOrUpdateChannelPhoto(channelPhoto);
	}
	public void deleteChannelPhoto(ChannelPhoto channelPhoto){
		this.channelPageDao.deleteChannelPhoto(channelPhoto);
	}
	
	public ChannelVideo getChannelVideo(int channelVideoId){
		return this.channelPageDao.getChannelVideo(channelVideoId);				
	}
	public ChannelVideo getChannelVideoByChannelIdAndVideoId(int channelId, int videoId)
	{
		return this.channelPageDao.getChannelVideoByChannelIdAndVideoId(channelId, videoId);
	}
	public void saveOrUpdateChannelVideo(ChannelVideo channelVideo){
		this.channelPageDao.saveOrUpdateChannelVideo(channelVideo);
	}
	public void deleteChannelVideo(ChannelVideo channelVideo){
		this.channelPageDao.deleteChannelVideo(channelVideo);
	}
	//统计
	/*public int getChannelArticleCount(int channelId, int userId, String startDate, String endDate){
		return this.channelPageDao.getChannelArticleCount(channelId, userId, startDate, endDate);
	}
	public int getChannelResourceCount(int channelId, int userId, String startDate, String endDate){
		return this.channelPageDao.getChannelResourceCount(channelId, userId, startDate, endDate);
	}
	public int getChannelPhotoCount(int channelId, int userId, String startDate, String endDate){
		return this.channelPageDao.getChannelPhotoCount(channelId, userId, startDate, endDate);
	}
	public int getChannelVideoCount(int channelId, int userId, String startDate, String endDate){
		return this.channelPageDao.getChannelVideoCount(channelId, userId, startDate, endDate);
	}
	public void saveChannelUserStat(ChannelUserStat channelUserStat)
	{
		this.channelPageDao.saveChannelUserStat(channelUserStat);
	}
	public void deleteChannelUserStat(String statGuid){
		this.channelPageDao.deleteChannelUserStat(statGuid);
	}*/
	
	/**
	 * 得到某用户所属的所有的自定义频道
	 */
	public List<Channel> getChannelListByUserId(int userId)
	{
	    
		return this.channelPageDao.getChannelListByUserId(userId);
	}
	
	public ChannelPhoto getChannelPhoto(int channelId, int photoId){
		return this.channelPageDao.getChannelPhoto(channelId, photoId);
	}
	public List<ChannelPhoto> getChannelPhotoList(int photoId)
	{
		return this.channelPageDao.getChannelPhotoList(photoId);
	}
	public ChannelArticle getChannelArticleByChannelIdAndArticleId(int channelId, int articleId)
	{
		return this.channelPageDao.getChannelArticleByChannelIdAndArticleId(channelId, articleId);
	}

	public List<ChannelArticle> getChannelArticleList(int articleId) {
		return this.channelPageDao.getChannelArticleList(articleId);
	}

	public List<ChannelResource> getChannelResourceList(int resourceId) {
		return this.channelPageDao.getChannelResourceList(resourceId);
	}

	public List<ChannelVideo> getChannelVideoList(int videoId) {
		return this.channelPageDao.getChannelVideoList(videoId);
	}
	
	public void statUserData(int channelId, String statGuid, String keyWord, String filter, String startDate, String endDate){
		this.channelPageDao.statUserData(channelId, statGuid, keyWord, filter, startDate, endDate);
	}
	public void statUnitData(int channelId, String statGuid, String startDate, String endDate)
	{
		this.channelPageDao.statUnitData(channelId, statGuid, startDate, endDate);
	}
}
