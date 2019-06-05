package cn.edustar.jitar.pojos;

import java.io.Serializable;


/**
 * 
 * @author baimindong
 *
 */
public class GroupMutil implements Serializable {



	/**
	 * 
	 */
	private static final long serialVersionUID = 7590934680276939537L;

	/** 此对象的标识 */
	private int id;
	
	/** 群组页面上综合分类模块标识 */
	private int widgetId;
	
	/** 文章分类标识 */
	private int articleCateId;
	/** 显示文章数目 */
	private int articleNumShow=10;

	/** 资源分类标识 */
	private int resourceCateId;
	/** 显示资源数目 */
	private int resourceNumShow=10;
	
	/** 图片分类标识 */
	private int photoCateId;
	/** 显示图片数目 */
	private int photoNumShow=3;

	/** 视频分类标识 */
	private int videoCateId;
	/** 显示视频数目 */
	private int videoNumShow=3;
	
	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return "GroupMutil{id=" + this.id + 
			",widgetId=" + this.widgetId +
			",articleCateId=" + this.articleCateId +
			",resourceCateId=" + this.resourceCateId +
			",photoCateId=" + this.photoCateId +
			",videoCateId=" + this.videoCateId +
			"}";
	}
	
	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return PojoHelper.hashCode(id, widgetId);
	}
	
	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object o) {
		if (o == this) return true;
		if (o == null || !(o instanceof GroupMutil)) return false;
		
		GroupMutil other = (GroupMutil)o;
		
		return this.widgetId == other.widgetId;
	}
	
	/** 此对象的标识 */
	public int getId() {
		return id;
	}

	/** 此对象的标识 */
	public void setId(int id) {
		this.id = id;
	}

	public int getWidgetId() {
		return widgetId;
	}

	public void setWidgetId(int widgetId) {
		this.widgetId = widgetId;
	}

	public int getArticleCateId() {
		return articleCateId;
	}

	public void setArticleCateId(int articleCateId) {
		this.articleCateId = articleCateId;
	}

	public int getArticleNumShow() {
		return articleNumShow;
	}

	public void setArticleNumShow(int articleNumShow) {
		this.articleNumShow = articleNumShow;
	}

	public int getResourceCateId() {
		return resourceCateId;
	}

	public void setResourceCateId(int resourceCateId) {
		this.resourceCateId = resourceCateId;
	}

	public int getResourceNumShow() {
		return resourceNumShow;
	}

	public void setResourceNumShow(int resourceNumShow) {
		this.resourceNumShow = resourceNumShow;
	}

	public int getPhotoCateId() {
		return photoCateId;
	}

	public void setPhotoCateId(int photoCateId) {
		this.photoCateId = photoCateId;
	}

	public int getPhotoNumShow() {
		return photoNumShow;
	}

	public void setPhotoNumShow(int photoNumShow) {
		this.photoNumShow = photoNumShow;
	}

	public int getVideoCateId() {
		return videoCateId;
	}

	public void setVideoCateId(int videoCateId) {
		this.videoCateId = videoCateId;
	}

	public int getVideoNumShow() {
		return videoNumShow;
	}

	public void setVideoNumShow(int videoNumShow) {
		this.videoNumShow = videoNumShow;
	}

}
