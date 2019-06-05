package cn.edustar.jitar.pojos;


import java.util.Date;

import cn.edustar.jitar.service.CategoryHelper;

/**
 * ContentSpace entity. @author MyEclipse Persistence Tools
 */



/**
 * @author admin
 *
 */
public class ContentSpace implements java.io.Serializable {

	// Fields
	/** 总站的自定义文章分类：0 */
	public static final int CONTENTSPACE_OWNERTYPE_DEFAULT = 0;	
	/** 机构的自定义文章分类:1 */
	public static final int CONTENTSPACE_OWNERTYPE_UNIT = 1;	
	/** 学科的自定义文章分类:2 */
	public static final int CONTENTSPACE_OWNERTYPE_SUBJECT = 2;	
	
	private static final long serialVersionUID = -7318250601090045864L;
	private int contentSpaceId;
	private String spaceName;
	private Date createDate;
	private int createUserId;
	private int ownerType = 0;
	private int articleCount = 0;
	/** 上级分类Id */
	private Integer parentId;
	/** 父分类的整路径. 如/1/3/8/，指父分类为1，3，8. */
	private String parentPath = "/";

	/** ownerId 为 0 则表示为整站级别的自定义文章分类。 */
	private int ownerId = 0;

	// Constructors

	/** default constructor */
	public ContentSpace() {
	}

	public int getContentSpaceId() {
		return contentSpaceId;
	}

	public void setContentSpaceId(int contentSpaceId) {
		this.contentSpaceId = contentSpaceId;
	}

	public String getSpaceName() {
		return spaceName;
	}

	public void setSpaceName(String spaceName) {
		this.spaceName = spaceName;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public int getCreateUserId() {
		return createUserId;
	}

	public void setCreateUserId(int createUserId) {
		this.createUserId = createUserId;
	}

	public int getOwnerType() {
		return ownerType;
	}

	public void setOwnerType(int ownerType) {
		this.ownerType = ownerType;
	}

	public int getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(int ownerId) {
		this.ownerId = ownerId;
	}
	
	public int getArticleCount() {
		return articleCount;
	}

	public void setArticleCount(int articleCount) {
		this.articleCount = articleCount;
	}

	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	/**
	 * 得到分类自己的路径 = parentPath + id + '/'
	 * @return
	 */
	public String getCategoryPath() {
		return this.getParentPath() + this.contentSpaceId + "/";
	}
	
	/** 父分类的整路径. 如/1/3/8/，指父分类为1，3，8. */
	public String getParentPath() {
		if(null == this.parentPath){
			this.parentPath = "/";
		}
		return this.parentPath;
	}

	/** 父分类的整路径. 如/1/3/8/，指父分类为1，3，8. */
	public void setParentPath(String parentPath) {
		this.parentPath = parentPath;
	}

}