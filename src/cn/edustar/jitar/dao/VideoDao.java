package cn.edustar.jitar.dao;

import java.util.List;

import cn.edustar.data.Pager;
import cn.edustar.jitar.pojos.Video;
import cn.edustar.jitar.query.VideoQueryParam;

/**
 * 视频DAO
 * 
 * @author Yang Xinxin
 * @version 1.0.0 Apr 20, 2009 4:38:13 PM
 */
public interface VideoDao extends Dao {
	
	/**
	 * 保存一个视频对象
	 *
	 * @param video
	 */
	public void save(Video video);
	
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
	 * 得到视频分类
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getVideoCategoryList();
	
	/**
	 * 更新视频的审核状态
	 * 
	 * @param v
	 * @param state
	 */
	public void updateAuditState(Video v,boolean state);
	
	/**
	 * 更新统计视频的评论数量
	 */
	public void updateAllVideoCommentCount();
	
	/**
	 * @param userId
	 */
	public void updateVideoCommentCount(int userId);
	
	public void updateVideo(Video video);
	
	public void batchClearVideoUserCategory(int userCateId);
	
	/**
	 * 更改视频的转换状态
	 * 
	 * @param videoId
	 * @param status
	 */
	public void updateVideoStatus(int videoId, int status);
	
}
