package cn.edustar.jitar.model;
import java.util.Date;

import cn.edustar.jitar.pojos.Category;
import cn.edustar.jitar.pojos.GroupPhoto;
import cn.edustar.jitar.pojos.Photo;

public class PhotoModel extends Photo implements ModelObject, TypedModelObject {
	
	protected final Photo photo;
	
	/**
	 * 包装一个 Photo 对象.
	 */
	public static PhotoModel wrap(Photo photo) {
		return new PhotoModel(photo);
	}

	/**
	 * 构造, 不能从外部调用
	 * 
	 * @param Photo
	 */
	protected PhotoModel(Photo photo) {
		this.photo=photo;
	}

	/** 对象类型，= ObjectType.OBJECT_TYPE_PHOTO */
	public static final ObjectType OBJECT_TYPE = ObjectType.OBJECT_TYPE_PHOTO;
	
	public Photo _getPhotoObject() {
		return this.photo;
	}	

	/** 发布的作者标识，必须有正确的值. */
	public int getUserId() {
		return photo.getUserId();
		
	}	
	
	public short getAuditState() {
		return photo.getAuditState();
	}	
	
	public Integer getSubjectId() {
		return photo.getSubjectId();
	}	
	
	public Integer getExtGradeId() {
		return photo.getExtGradeId();
	}	
	
	public String getTags() {
		return photo.getTags();
	}	
	
	public Date getCreateDate() {
		return photo.getCreateDate();
	}	
	
	public String getTitle() {
		return photo.getTitle();
	}	
	
	public String getHref() {
		return photo.getHref();
	}		
	public int getViewCount() {
		return photo.getViewCount();
	}	
	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.edustar.jitar.model.TypedModelObject#getObjectId()
	 */
	public int getObjectId() {
		return photo.getPhotoId();
	}
	public int getPhotoId() {
		return photo.getPhotoId();
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

	/** GroupPhoto 对象 */
	private GroupPhoto group_photo;
	
	/** GroupResource 对象 */
	public void setGroupPhoto(GroupPhoto group_photo) {
		this.group_photo = group_photo;
	}
	
	/** GroupResource 对象 */
	public GroupPhoto getGroupPhoto() {
		return this.group_photo;
	}
	
	/** 获得是否群组中最佳图片*/
	public boolean getIsGroupBest() {
		return group_photo.getIsGroupBest();
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
