package cn.edustar.jitar.pojos;

import java.io.Serializable;
import java.util.Date;

/***
 * 聊天内容
 * @author Yang XinXin
 * @version 1.0.0 Mar 17, 2008 9:06:49 AM
 *
 */
public class ChatMessage implements Serializable  {

	/** serialVersionUID*/
	private static final long serialVersionUID = -6000433623893485921L;
	
	/** 聊天内容标识 */
	private int chatMessageId; 
	
	/** 房间标识 */
	private int roomId;
	
	
	/** 发送者标识 */
	private int senderId;
	
	/** 发送者名字 */
	private String senderName;
	
	/** 接收者标识 */
	private int receiverId;
	
	/** 接收者名字 */
	private String receiverName;
	
	/** 聊天内容*/
	private String talkContent = "";
	
	/** 发送时间 */
	private Date sendDate = new Date();
	
	/** 是否发送给所有人 */
	private boolean isSendAll;
	
	/** 是否私聊 */
	private boolean isPrivate;
	
	/** 动作 */
	private String actText;
	
	/** 表情图片 */
	private String faceImg;

	/** 颜色 */
	private String senderColor="#000000";
	private String receiverColor="#000000";
	
	@Override
	public boolean equals(Object obj) {
		if(obj == this) return true;
		
		if(obj == null || !(obj instanceof ChatMessage))
			return false;
		ChatMessage other = (ChatMessage)obj;
		return PojoHelper.equals(this.chatMessageId, other.chatMessageId);
	}

	@Override
	public int hashCode() {
		return PojoHelper.hashCode(this.chatMessageId, this.roomId);
	}

	@Override
	public String toString() {
		return "CharMessage{chatMessageId = " + this.chatMessageId +
					", roomId = " + this.roomId +
					", sendDate = " + this.sendDate + "}";
	}

	/** 聊天内容标识 */
	public int getChatMessageId() {
		return chatMessageId;
	}
	
	/** 聊天内容标识 */
	public void setChatMessageId(int chatMessageId) {
		this.chatMessageId = chatMessageId;
	}
	
	/** 房间标识 */
	public int getRoomId() {
		return roomId;
	}
	
	/** 房间标识 */
	public void setRoomId(int roomId) {
		this.roomId = roomId;
	}
	

	/** 发送者标识 */
	public int getSenderId() {
		return senderId;
	}
	
	/** 发送者标识 */
	public void setSenderId(int senderId) {
		this.senderId = senderId;
	}
	
	/** 发送者名字 */
	public String getSenderName() {
		return senderName;
	}
	
	/** 发送者名字 */
	public void setSenderName(String senderName) {
		this.senderName = senderName;
	}
	
	/** 接收者标识 */
	public int getReceiverId() {
		return receiverId;
	}
	
	/** 接收者标识 */
	public void setReceiverId(int receiverId) {
		this.receiverId = receiverId;
	}
	
	/** 接收者名字 */
	public String getReceiverName() {
		return receiverName;
	}
	
	/** 接收者名字 */
	public void setReceiverName(String receiverName) {
		this.receiverName = receiverName;
	}
	
	/** 聊天内容*/
	public String getTalkContent() {
		return talkContent;
	}
	
	/** 聊天内容*/
	public void setTalkContent(String talkContent) {
		this.talkContent = talkContent;
	}
	
	/** 发送时间 */
	public Date getSendDate() {
		return sendDate;
	}
	
	/** 发送时间 */
	public void setSendDate(Date sendDate) {
		this.sendDate = sendDate;
	}
	
	/** 是否发送给所有人 */
	public boolean getIsSendAll() {
		return isSendAll;
	}
	
	/** 是否发送给所有人 */
	public void setIsSendAll(boolean isSendAll) {
		this.isSendAll = isSendAll;
	}
	
	/** 是否私聊 */
	public boolean getIsPrivate() {
		return isPrivate;
	}
	
	/** 是否私聊 */
	public void setIsPrivate(boolean isPrivate) {
		this.isPrivate = isPrivate;
	}

	/** ..... */
	public String getActText() {
		return actText;
	}

	/** ..... */
	public void setActText(String actText) {
		this.actText = actText;
	}
	
	/** 表情图片 */
	public String getFaceImg() {
		return faceImg;
	}

	/** 表情图片 */
	public void setFaceImg(String faceImg) {
		this.faceImg = faceImg;
	}
	
	/** 颜色 */
	public String getSenderColor() {
		return senderColor;
	}

	/** 颜色 */
	public void setSenderColor(String color) {
		if(color=="")
		{
			color="#000000";
		}
		this.senderColor = color;
	}
	
	/** 颜色 */
	public String getReceiverColor() {
		return receiverColor;
	}

	/** 颜色 */
	public void setReceiverColor(String color) {
		if(color=="")
		{
			color="#000000";
		}
		this.receiverColor = color;
	}
	
}
