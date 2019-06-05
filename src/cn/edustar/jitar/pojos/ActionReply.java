package cn.edustar.jitar.pojos;

import java.io.Serializable;
import java.util.Date;
/**
 * 
 * 教研活动的话题回复
 * @author 孟宪会
 *
 */
public class ActionReply implements Serializable, Cloneable  {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4148715772163874446L;
	
	/** 活动讨论标识 */
	private int actionReplyId;
	
	/** 活动标识 */
	private int actionId;
	
	/** 活动讨论标题 */
	private String topic;
	
	/** 活动回复创建时间 */
	private Date createDate;
	
	/** 活动回复用户标识 */
	private int userId;
	
	/** 活动回复内容 */
	private String content;
	
	/** 活动回复者ip */
	private String addIp;
	
	/** 活动回复标识 */
	public int getActionReplyId() {
		return actionReplyId;
	}
	/** 活动回复标识 */
	public void setActionReplyId(int actionReplyId) {
		this.actionReplyId = actionReplyId;
	}
	
	/** 活动标识 */
	public int getActionId() {
		return actionId;
	}
	
	/** 活动标识 */
	public void setActionId(int actionId) {
		this.actionId = actionId;
	}
	
	/** 活动回复标题 */
	public String getTopic() {
		return topic;
	}
	
	/** 活动回复标题 */
	public void setTopic(String topic) {
		this.topic = topic;
	}
	
	/** 获得活动回复创建时间 */
	public Date getCreateDate() {
		return createDate;
	}
	
	/** 设置活动回复创建时间 */
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	
	/** 得到活动回复用户标识 */
	public int getUserId() {
		return userId;
	}
	/** 设置活动回复用户标识 */
	public void setUserId(int userId) {
		this.userId = userId;
	}
	
	/** 得到活动回复内容 */
	public String getContent() {
		return content;
	}
	
	/** 设置活动回复内容 */
	public void setContent(String content) {
		this.content = content;
	}
	
	/** 得到活动回复者ip */
	public String getAddIp() {
		return addIp;
	}
	
	/** 设置活动回复ip */
	public void setAddIp(String addIp) {
		this.addIp = addIp;
	}



}
