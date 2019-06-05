package cn.edustar.jitar.service.impl;

import java.io.File;
import java.util.List;

import javax.servlet.ServletContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.context.ServletContextAware;

import cn.edustar.data.Pager;
import cn.edustar.jitar.dao.VideoDao;
import cn.edustar.jitar.model.ObjectType;

import cn.edustar.jitar.pojos.Video;
import cn.edustar.jitar.query.VideoQueryParam;
import cn.edustar.jitar.service.TagService;
import cn.edustar.jitar.service.VideoService;
import cn.edustar.jitar.service.CommentService;
import cn.edustar.jitar.util.CommonUtil;

/**
 * 视频服务接口的实现
 * 
 * @author Yang Xinxin
 * @version 1.0.0 Apr 20, 2009 4:37:53 PM
 */
public class VideoServiceImpl implements VideoService, ServletContextAware {

	/** 日志 */
	private static final Log log = LogFactory.getLog(VideoServiceImpl.class);
	
	/** 视频DAO */
	private VideoDao videoDao;

	/** 标签服务 */
	private TagService tagService;
	
	/** 标签服务 */
	private CommentService commentService;
	
	/* (non-Javadoc)
	 *
	 * @see cn.edustar.jitar.service.VideoService#save(cn.edustar.jitar.pojos.Video)
	 */
	public void save(Video video) throws Exception {
		try {
			String[] tags = tagService.parseTagList(video.getTags()); 
			video.setTags(CommonUtil.standardTagsString(tags));
			if(video.getSubjectId().equals(null) || video.getSubjectId()==null)
				video.setSubjectId(0);
			videoDao.save(video);

			// 写入标签, 如果有的话
			if (tags.length > 0) {
				tagService.createUpdateMultiTag(video.getVideoId(), ObjectType.OBJECT_TYPE_VIDEO, tags, null);
			}
		} catch (Exception e) {
			log.error(e);
		}
	}

	/* (non-Javadoc)
	 *
	 * @see cn.edustar.jitar.service.VideoService#getVideoList(cn.edustar.jitar.query.VideoQueryParam, cn.edustar.data.Pager)
	 */
	public List<Video> getVideoList(VideoQueryParam param, Pager pager) {
		return videoDao.getVideoList(param, pager);
	}
	
	/* (non-Javadoc)
	 *
	 * @see cn.edustar.jitar.service.VideoService#findById(int)
	 */
	public Video findById(int videoId) {
		return videoDao.findById(videoId);
	}
	
	/* (non-Javadoc)
	 * 
	 * @see cn.edustar.jitar.service.VideoService#auditVideo(cn.edustar.jitar.pojos.Video)
	 */
	public void auditVideo(Video _video) {
		videoDao.updateAuditState(_video, true);
	}

	/* (non-Javadoc)
	 * 
	 * @see cn.edustar.jitar.service.VideoService#unauditVideo(cn.edustar.jitar.pojos.Video)
	 */
	public void unauditVideo(Video _video) {
		videoDao.updateAuditState(_video, false);
	}
	
	/* (non-Javadoc)
	 *
	 * @see cn.edustar.jitar.service.VideoService#delVideo(cn.edustar.jitar.pojos.Video)
	 */
	public void delVideo(Video video) {
		String sourceFile = video.getHref();
		try {
			// 1，删除原始文件，如果是mp4,flv,hlv,f4v,m4v格式的，将会省去第二步
			File hrefFile = new File(sourceFile);
			if (hrefFile.exists()) {
				hrefFile.delete();
			}
			
			// 2，删除转换后的视频
			String configPathAndPrefix = sourceFile.substring(0, sourceFile.lastIndexOf("."));
			File videoFile = new File(configPathAndPrefix + ".m4v");
			if (videoFile.exists()) {
				videoFile.delete();
			}
			
			// 3，删除flv
			File flvHFile = new File(configPathAndPrefix + ".flv");
			if (flvHFile.exists()) {
				flvHFile.delete();
			}
			
			// 4，删除缩略图
			File thumbFile = new File(configPathAndPrefix + ".jpg");
			if (thumbFile.exists()) {
				thumbFile.delete();
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e);
		}
		
		// 删除视频的评论
		commentService.deleteCommentByObject(ObjectType.OBJECT_TYPE_VIDEO, video.getVideoId());
		
		// 删除视频
		videoDao.delVideo(video);
	}
	
	/* (non-Javadoc)
	 *
	 * @see cn.edustar.jitar.service.VideoService#increseViewCount(int)
	 */
	public void increaseViewCount(int videoId) {
		videoDao.increaseViewCount(videoId);
	}
	
	public void increaseViewCount(int videoId, int count){
	    videoDao.increaseViewCount(videoId, count);
	}
	/* (non-Javadoc)
	 * 
	 * @see cn.edustar.jitar.service.VideoService#increseCommentCount(int)
	 */
	public void increseCommentCount(int videoId) {
		videoDao.increseCommentCount(videoId);
	}

	/* (non-Javadoc)
	 *
	 * @see org.springframework.web.context.ServletContextAware#setServletContext(javax.servlet.ServletContext)
	 */
	public void setServletContext(ServletContext arg0) {
		// 
	}
	
	/**
	 * 视频DAO的set方法
	 *
	 * @param videoDao
	 */
	public void setVideoDao(VideoDao videoDao) {
		this.videoDao = videoDao;
	}

	/**
	 * 标签服务的set方法
	 * 
	 * @param tagService
	 */
	public void setTagService(TagService tagService) {
		this.tagService = tagService;
	}

	/**
	 * 评论服务的set方法
	 *
	 * @param commentService
	 */
	public void setCommentService(CommentService commentService) {
		this.commentService = commentService;
	}
	
	/* (non-Javadoc)
	 * 
	 * @see cn.edustar.jitar.service.VideoService#getVideoCategoryList()
	 */
	@SuppressWarnings("rawtypes")
	public List getVideoCategoryList() {
		return videoDao.getVideoCategoryList();
	}
	
	/* (non-Javadoc)
	 * 
	 * @see cn.edustar.jitar.service.VideoService#updateAllVideoCommentCount()
	 */
	public void updateAllVideoCommentCount(){
		videoDao.updateAllVideoCommentCount();
	}
		
	/* (non-Javadoc)
	 * 
	 * @see cn.edustar.jitar.service.VideoService#updateVideoCommentCount(int)
	 */
	public void updateVideoCommentCount(int userId){
		videoDao.updateVideoCommentCount(userId);
	}
	
	/* (non-Javadoc)
	 * 
	 * @see cn.edustar.jitar.service.VideoService#updateVideo(cn.edustar.jitar.pojos.Video)
	 */
	public void updateVideo(Video video) {
		this.videoDao.updateVideo(video);
	}

	/* (non-Javadoc)
	 * 
	 * @see cn.edustar.jitar.service.VideoService#batchClearVideoUserCategory(int)
	 */
	public void batchClearVideoUserCategory(int userCateId) {
		this.videoDao.batchClearVideoUserCategory(userCateId);
	}
	
	/* (non-Javadoc)
	 * 
	 * @see cn.edustar.jitar.service.VideoService#updateVideoStatus(int, int)
	 */
	public void updateVideoStatus(int videoId, int status) {
		this.videoDao.updateVideoStatus(videoId, status);
	}
	
}
