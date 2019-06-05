package cn.edustar.jitar.model;
import java.util.Date;

import cn.edustar.jitar.pojos.Category;
import cn.edustar.jitar.pojos.GroupVideo;
import cn.edustar.jitar.pojos.Video;

public class VideoModel extends Video implements ModelObject, TypedModelObject {
	
	protected final Video video;
	
	/**
	 * 包装一个 Video 对象.
	 */
	public static VideoModel wrap(Video video) {
		return new VideoModel(video);
	}

	/**
	 * 构造, 不能从外部调用
	 * 
	 * @param video
	 */
	protected VideoModel(Video video) {
		this.video=video;
	}

	/** 对象类型，= ObjectType.OBJECT_TYPE_VIDEO */
	public static final ObjectType OBJECT_TYPE = ObjectType.OBJECT_TYPE_VIDEO;
	
	public Video _getVideoObject() {
		return video;
	}	

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.edustar.jitar.model.TypedModelObject#getObjectId()
	 */
	public int getObjectId() {
		return video.getVideoId();
	}

	public int getVideoId() {
		return video.getVideoId();
	}
	
	public int getUserId() {
		return video.getUserId();
		
	}	
	
	public short getAuditState() {
		return video.getAuditState();
	}	
	
	public Integer getSubjectId() {
		return video.getSubjectId();
	}	
	
	public Integer getExtGradeId() {
		return video.getExtGradeId();
	}	
	
	public String getTags() {
		return video.getTags();
	}	
	
	public Date getCreateDate() {
		return video.getCreateDate();
	}	
	
	public String getTitle() {
		return video.getTitle();
	}
	
	public int getViewCount() {
		return video.getViewCount();
	}
	
	public String getFlvHref() {
		return video.getFlvHref();
	}

	public String getFlvThumbNailHref() {
		return video.getFlvThumbNailHref();
	}
	
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.edustar.jitar.model.TypedModelObject#getObjectType()
	 */
	public ObjectType getObjectType() {
		return OBJECT_TYPE;
	}
	
	/** 用户分类 */
	private Category user_cate;
	
	/** 用户分类 */
	public void setUserCategory(Category user_cate) {
		this.user_cate = user_cate;
	}
	
	/** 用户分类 */
	public Category getUserCategory() {
		return this.user_cate;
	}

	/** GroupVideo 对象 */
	private GroupVideo group_video;
	
	/** GroupResource 对象 */
	public void setGroupVideo(GroupVideo group_video) {
		this.group_video = group_video;
	}
	
	/** GroupResource 对象 */
	public GroupVideo getGroupVideo() {
		return this.group_video;
	}
	
	/** 获得是否群组中最佳图片*/
	public boolean getIsGroupBest() {
		return group_video.getIsGroupBest();
	}

	/** 图片在群组中的分类 */
	private Category group_cate;
	
	/** 图片在群组中的分类 */
	public Category getGroupCategory() {
		return group_cate;
	}
	
	/** 图片在群组中的分类 */
	public void setGroupCategory(Category cate) {
		this.group_cate = cate;
	}	
}
