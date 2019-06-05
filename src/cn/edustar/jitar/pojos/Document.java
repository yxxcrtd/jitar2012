package cn.edustar.jitar.pojos;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

/**
 * 作为 Article, Resource, Video, Photo 等基础对象的基类
 * 
 * @author Yang Xinxin
 * @version 1.0.0 Apr 21, 2009 10:25:06 AM
 */
public abstract class Document implements Serializable{

	/**
     * 
     */
    private static final long serialVersionUID = -4982492102090393533L;

    /** 审核状态：通过 = 0 */
	public static final short AUDIT_STATE_OK = 0;

	/** 审核状态：未审核 = 1 */
	public static final short AUDIT_STATE_WAIT_AUDIT = 1;

	/** 隐藏状态：未隐藏 = 0 */
	public static final short HIDE_STATE_SHOW = 0;

	/** 隐藏状态：隐藏 = 1 */
	public static final short HIDE_STATE_HIDE = 1;

	/** 文章、资源、视频、图片等的标识 */
	private int id;

	/** 文章、资源、视频、图片等的全局唯一标识 */
	private String objectUuid = UUID.randomUUID().toString().toUpperCase();

	/** 文章、资源、视频、图片等的标题 */
	private String title;

	/** 文章、资源、视频、图片等的作者标识 */
	private int userId;

	/** 发表日期，缺省为当前日期 */
	private Date createDate = new Date();

	/** 最后修改日期，缺省为当前日期 */
	private Date lastModified = new Date();

	/** 摘要 */
	private String summary;

	/** 标签 */
	private String tags;

	/** 访问次数 */
	private int viewCount;

	/** 评论次数 */
	private int commentCount;

	/** extGradeId */
	private Integer extGradeId = null;

	/** 审核状态：审核通过 = 0；待审核 = 1；初审通过 = 2，其它数值含义待定 */
	private short auditState;

	/** 所属学科，可以为 null */
	private Integer subjectId;

	// Get and set
	/** 文章、资源、视频、图片等的标识 */
	public int getId() {
		return id;
	}

	/** 文章、资源、视频、图片等的标识 */
	public void setId(int id) {
		this.id = id;
	}

	/** 文章、资源、视频、图片等的全局唯一标识 */
	public String getObjectUuid() {
		return objectUuid;
	}

	/** 文章、资源、视频、图片等的全局唯一标识 */
	public void setObjectUuid(String objectUuid) {
		this.objectUuid = objectUuid;
	}

	/** 文章、资源、视频、图片等的标题 */
	public String getTitle() {
		return title;
	}

	/** 文章、资源、视频、图片等的标题 */
	public void setTitle(String title) {
		this.title = title;
	}

	/** 文章、资源、视频、图片等的作者标识 */
	public int getUserId() {
		return userId;
	}

	/** 文章、资源、视频、图片等的作者标识 */
	public void setUserId(int userId) {
		this.userId = userId;
	}

	/** 发表日期，缺省为当前日期 */
	public Date getCreateDate() {
		return createDate;
	}

	/** 发表日期，缺省为当前日期. */
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	/** 最后修改日期，缺省为当前日期 */
	public Date getLastModified() {
		return lastModified;
	}

	/** 最后修改日期，缺省为当前日期 */
	public void setLastModified(Date lastModified) {
		this.lastModified = lastModified;
	}

	/** 摘要 */
	public String getSummary() {
		return this.summary;
	}

	/** 摘要 */
	public void setSummary(String summary) {
		this.summary = summary;
	}

	/** 标签 */
	public String getTags() {
		return this.tags;
	}

	/** 标签 */
	public void setTags(String tags) {
		this.tags = tags;
	}

	/** 访问次数 */
	public int getViewCount() {
		return viewCount;
	}

	/** 访问次数 */
	public void setViewCount(int viewCount) {
		this.viewCount = viewCount;
	}

	/** 评论次数 */
	public int getCommentCount() {
		return commentCount;
	}

	/** 评论次数 */
	public void setCommentCount(int commentCount) {
		this.commentCount = commentCount;
	}

	/** 审核状态：审核通过 = 0，待审核 = 1，初审通过 = 2，其它数值含义待定 */
	public short getAuditState() {
		return auditState;
	}

	/** 审核状态：审核通过 = 0，待审核 = 1，初审通过 = 2，其它数值含义待定 */
	public void setAuditState(short auditState) {
		this.auditState = auditState;
	}

	/** 所属元学科, 注意: 现在改为关联到 MetaSubject 对象, 可以为 null */
	public Integer getSubjectId() {
		return this.subjectId;
	}

	/** 所属元学科, 注意: 现在改为关联到 MetaSubject 对象, 可以为 null */
	public void setSubjectId(Integer subjectId) {
		this.subjectId = subjectId;
	}

	public Integer getExtGradeId() {
		return extGradeId;
	}

	public void setExtGradeId(Integer extGradeId) {
		this.extGradeId = extGradeId;
	}

}
