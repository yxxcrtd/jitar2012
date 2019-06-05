package cn.edustar.jitar.pojos;

import java.util.Date;
import java.io.Serializable;

/**
 * 短消息实体对象.
 * 业务键: sendId, receiveId, sendTime.
 * @author Yang Xinxin
 * @version 1.0.0 Mar 17, 2008 4:26:26 PM
 * 
 * 
 * 发送者删除 (isSenderDel) 表示该消息从发送者 '发件箱' 删除, 也就是不显示.
 * 接收者删除 (isDel) 表示消息从接收者 '收件箱' 删除, 也就是不显示.
 * 在回收站中删除的时候 要检查 isSenderDel, isDel 全部为 true 才真正删除.
 * 同时用 isSenderDel, isDel 做过滤.
 */
public class Message implements Serializable {
	/** serialVersionUID */
	private static final long serialVersionUID = -2245909242084190867L;

	/** 对象标识 */
	private int id;

	/** 发送者用户标识. */
	private int sendId;

	/** 接收者用户标识. */
	private int receiveId;

	/** 消息标题. */
	private String title;

	/** 消息内容. */
	private String content;

	/** 发送时间. */
	private Date sendTime = new Date();

	/** 接收者是否已读, false = 未读，true = 已读. */
	private boolean isRead;

	/** 接收者是否删除 false = 未删， true = 已删. */
	private boolean isDel;
	
	/** 发送者是否删除 false = 未删， true = 已删. */
	private boolean isSenderDel;

	/** 是否回复过 false = 未回复， true = 已回复过. */
	private boolean isReply;
	
	/** 附件标识(暂时未使用) */
	private Integer fileId;

	/** Default Constructor */
	public Message() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		// 先自己与自己比较.
		if (this == obj)
			return true;

		// 对象是否为空，并且检测对象的类型.
		if (obj == null || !(obj instanceof Message))
			return false;

		Message other = (Message) obj;
		if (other.sendId == this.sendId && other.receiveId == this.receiveId &&
				PojoHelper.equals(other.sendTime, this.sendTime))
			return true;

		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Message{id = " + this.id + ", sendId = " + this.sendId
				+ ", title = " + this.title + "}";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return PojoHelper.hashCode(this.sendId, this.receiveId, this.sendTime);
	}

	/**
	 * Full Constructor
	 * 
	 * @param sendId
	 * @param receiveId
	 * @param title
	 * @param content
	 * @param sendTime
	 * @param isRead
	 * @param isDel
	 * @param isReply
	 * @param fileId
	 */
	public Message(int sendId, int receiveId, String title, String content,
			Date sendTime, boolean isRead, boolean isDel, boolean isReply,
			Integer fileId) {
		this.sendId = sendId;
		this.receiveId = receiveId;
		this.title = title;
		this.content = content;
		this.sendTime = sendTime;
		this.isRead = isRead;
		this.isDel = isDel;
		this.isReply = isReply;
		this.fileId = fileId;
	}

	// Property accessors
	/** 对象标识 */
	public int getId() {
		return this.id;
	}

	/** 对象标识 */
	public void setId(int id) {
		this.id = id;
	}

	/** 发送者 */
	public int getSendId() {
		return this.sendId;
	}

	/** 发送者 */
	public void setSendId(int sendId) {
		this.sendId = sendId;
	}

	/** 接收者 */
	public int getReceiveId() {
		return this.receiveId;
	}

	/** 接收者 */
	public void setReceiveId(int receiveId) {
		this.receiveId = receiveId;
	}

	/** 消息标题 */
	public String getTitle() {
		return this.title;
	}

	/** 消息标题 */
	public void setTitle(String title) {
		this.title = title;
	}

	/** 消息内容 */
	public String getContent() {
		return this.content;
	}

	/** 消息内容 */
	public void setContent(String content) {
		this.content = content;
	}

	/** 发送时间 */
	public Date getSendTime() {
		return this.sendTime;
	}

	/** 发送时间 */
	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}

	/** 已/未读（0：(false)未读，1：(true)已读） */
	public boolean getIsRead() {
		return this.isRead;
	}

	/** 已/未读（0：(false)未读，1：(true)已读） */
	public void setIsRead(boolean isRead) {
		this.isRead = isRead;
	}

	/** 是否删除（0：(false)未删，1：(true)已删） */
	public boolean getIsDel() {
		return this.isDel;
	}

	/** 是否删除（0：(false)未删，1：(true)已删） */
	public void setIsDel(boolean isDel) {
		this.isDel = isDel;
	}
	
	/** 发送者是否删除（0：(false)未删，1：(true)已删） */
	public boolean getIsSenderDel() {
		return this.isSenderDel;
	}
	
	/** 发送者是否删除（0：(false)未删，1：(true)已删） */
	public void setIsSenderDel(boolean isSenderDel) {
		this.isSenderDel = isSenderDel;
	}

	/** 是否回复（0：(false)未回复，1：(true)已回复） */
	public boolean getIsReply() {
		return this.isReply;
	}

	/** 是否回复（0：(false)未回复，1：(true)已回复） */
	public void setIsReply(boolean isReply) {
		this.isReply = isReply;
	}

	/** 附件标识 */
	public Integer getFileId() {
		return this.fileId;
	}

	/** 附件标识 */
	public void setFileId(Integer fileId) {
		this.fileId = fileId;
	}

	/** Hibernate 映射所需字段, 外面不需要访问 */
	private User sender;
	User getSender() {
		return this.sender;
	}
	void setSender(User sender) {
		// this.sender = sender;
	}
	private User receiver;
	User getReceiver() {
		return this.receiver;
	}
	void setReceiver(User receiver) {
		// this.receiver = receiver;
	}
}