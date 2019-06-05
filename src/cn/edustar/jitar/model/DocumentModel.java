package cn.edustar.jitar.model;

import java.util.Date;

import cn.edustar.jitar.pojos.Document;

/**
 * 做为 ArticleModel, PhotoModel 的基类.
 *
 *
 * @param <T>
 */
public class DocumentModel<T extends Document> {
	/** 实际的文档对象 */
	protected final T document;
	
	/**
	 * 派生类派生的构造函数.
	 * @param document
	 */
	protected DocumentModel(T document) {
		this.document = document;
	}
	
	/** 文章/图片的标识. */
	public int getId() {
		return document.getId();
	}
	
	/** 文章/图片全局唯一标识. */
	public String getObjectUuid() {
		return document.getObjectUuid();
	}
	
	/** 文章/图片标题. */
	public String getTitle() {
		return document.getTitle();
	}
	
	/** 发布文章/图片的作者标识，必须有正确的值. */
	public int getUserId() {
		return document.getUserId();
	}
	
	/** 发表日期，缺省为当前日期. */
	public Date getCreateDate() {
		return document.getCreateDate();
	}
	
	/** 最后修改日期，缺省为当前日期. */
	public Date getLastModified() {
		return document.getLastModified();
	}

	/** 摘要. */
	public String getSummary() {
		return document.getSummary();
	}
	
	/** 标签. */
	public String getTags() {
		return document.getTags();
	}
	
	/** 访问次数. */
	public int getViewCount() {
		return document.getViewCount();
	}
	
	/** 评论次数. */
	public int getCommentCount() {
		return document.getCommentCount();
	}
	
	/** 审核状态, 参见 Document AUDIT 常量定义. */
	public short getAuditState() {
		return document.getAuditState();
	}
}
