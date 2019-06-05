package cn.edustar.jitar.service;

import java.util.ArrayList;
import java.util.List;

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


public interface ChannelPageService {
	
	public Channel getChannel(int channelId);
	public void saveOrUpdateChannel(Channel channel);
	
	public ChannelUser getChannelUser(int channelUserId);
	public ChannelUser getChannelUserByUserIdAndChannelId(int userId, int channelId);
	public void saveOrUpdateChannelUser(ChannelUser channelUser);
	public void deleteChannelUser(ChannelUser channelUser);
	public void deleteChannelUserByUserIdAndChannelId(int channelId, int userId);
	
	public List<Channel> getChannelList();
	public List<Channel> getChannelListByUserId(int userId);
	public void deleteChannel(Channel channel);
	
	public ChannelModule getChannelModule(int moduleId);
	public void saveOrUpdateChannelModule(ChannelModule channelModule);
	public List<ChannelModule> getChannelModuleList(int channelId);
	public void deleteChannelModule(ChannelModule channelModule);
	
	public ChannelModule getChannelModuleByDisplayName(String displayName,int channelId);
	public ArrayList<String> getLabel(String inputString);
	
	public void deleteChannelArticle(int channelId);
	public void deleteChannelResource(int channelId);
	public void deleteChannelPhoto(int channelId);
	public void deleteChannelVideo(int channelId);
	public void deleteChannelOtherData(int channelId);
	
	public ChannelArticle getChannelArticle(String articleGuid);
	public List<ChannelArticle> getChannelArticleList(int articleId);
	public ChannelArticle getChannelArticleById(int channelArticleId);
	public ChannelArticle getChannelArticleByChannelIdAndArticleId(int channelId, int articleId);
	public void saveOrUpdateChannelArticle(ChannelArticle channelArticle);
	public void deleteChannelArticle(ChannelArticle channelArticle);
	public void deleteChannelArticleByGuid(String articleGuid);
	public void deleteChannelArticleById(int channelArticleId);
	
	public ChannelResource getChannelResource(int channelResourceId);
	public ChannelResource getChannelResourceByChannelIdAndResourceId(int channelId, int resourceId);
	public List<ChannelResource> getChannelResourceList(int resourceId);
	public void saveOrUpdateChannelResource(ChannelResource channelResource);
	public void deleteChannelResource(ChannelResource channelResource);
	
	public ChannelPhoto getChannelPhoto(int channelPhotoId);
	public ChannelPhoto getChannelPhoto(int channelId, int photoId);
	public List<ChannelPhoto> getChannelPhotoList(int photoId);
	public void saveOrUpdateChannelPhoto(ChannelPhoto channelPhoto);
	public void deleteChannelPhoto(ChannelPhoto channelPhoto);
	
	public ChannelVideo getChannelVideo(int channelVideoId);
	public ChannelVideo getChannelVideoByChannelIdAndVideoId(int channelId, int videoId);
	public List<ChannelVideo> getChannelVideoList(int videoId);
	public void saveOrUpdateChannelVideo(ChannelVideo channelVideo);
	public void deleteChannelVideo(ChannelVideo channelVideo);
	
	//统计
	/*public int getChannelArticleCount(int channelId, int userId, String startDate, String endDate);
	public int getChannelResourceCount(int channelId, int userId, String startDate, String endDate);
	public int getChannelPhotoCount(int channelId, int userId, String startDate, String endDate);
	public int getChannelVideoCount(int channelId, int userId, String startDate, String endDate);
	public void saveChannelUserStat(ChannelUserStat channelUserStat);
	public void deleteChannelUserStat(String statGuid);*/
	public void statUserData(int channelId, String statGuid, String keyWord, String filter, String startDate, String endDate);
	public void statUnitData(int channelId, String statGuid, String startDate, String endDate);
}
