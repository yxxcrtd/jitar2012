package cn.edustar.jitar.service;

import java.util.List;

import cn.edustar.data.Pager;
import cn.edustar.jitar.pojos.Video;
import cn.edustar.jitar.query.VideoQueryParam;

/**
 * 视频服务的接口
 * 
 * @author Yang xinxin
 * @version 1.0.0 Apr 20, 2009 4:26:02 PM
 */
public interface VideoService {

	/**
	 * 保存一个视频对象
	 * 
	 * @param flvHref
	 * @param flvThumbNailHref
	 * @throws Exception
	 */
	public void save(Video video) throws Exception;

	/**
	 * 得到视频列表
	 * 
	 * @param param
	 * @param pager
	 * @return
	 */
	public List<Video> getVideoList(VideoQueryParam param, Pager pager);

	/**
	 * 根据 视频Id 得到视频对象
	 * 
	 * @param videoId
	 * @return
	 */
	public Video findById(int videoId);

	/**
	 * 删除视频对象
	 * 
	 * @param video
	 */
	public void delVideo(Video video);

	/**
	 * 增加视频播放次数
	 * 
	 * @param videoId
	 */
	public void increaseViewCount(int videoId);
	
	public void increaseViewCount(int videoId, int count);

	/**
	 * 增加视频评论次数
	 * 
	 * @param videoId
	 */
	public void increseCommentCount(int videoId);

	/**
	 * 得到视频的分类
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getVideoCategoryList();

	/**
	 * 更新统计视频的评论数量
	 */
	public void updateAllVideoCommentCount();

	/**
	 * @param userId
	 */
	public void updateVideoCommentCount(int userId);

	/**
	 * 视频通过审核
	 * 
	 * @param _video
	 */
	public void auditVideo(Video _video);

	/**
	 * 视频未通过审核
	 * 
	 * @param _video
	 */
	public void unauditVideo(Video _video);
	
	public void updateVideo(Video video);
	
	/**
	 * 设置所有使用指定个人资源分类的资源的个人资源分类标识为 null, 该分类将被删除时候调用.
	 * 
	 * @param userCateId
	 */
	public void batchClearVideoUserCategory(int userCateId);	
	
	public void updateVideoStatus(int videoId, int status);
	
}
