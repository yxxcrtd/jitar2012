package cn.edustar.jitar.pojos;

import java.io.Serializable;
import java.util.Date;
 
/***
 * 聊天者
 * @author bai mindong
 * @version 1.0.0 Mar 17, 2008 9:06:49 AM
 * 
 */
public class ChatUser implements Serializable {

	/** serialVersionUId */
	private static final long serialVersionUID = -483652132336573709L;

	/** 用户标识 */
	private int id;
	
	/** 用户标识 */
	private int userId;

	/** 房间标识 */
	private int roomId;
	
	/** 用户名称 */
	private String UserName; 
	
	/** 用户加入时间 */
	private Date joinDate = new Date();
	
	/** 用户能否发言 */
	private boolean isSay;
	
	/** 字体的颜色 */
	private String fontColor="#000000";
	
	/** 字体的大小 */
	private String fontSize="12px";
	
	/** 是否激活 */
	private boolean isActived;
	
	/** 是否离开 */
	private boolean isLeave;

	private Date actTime=new Date();
	private Date sayDate;
	/***
	 * 无参构造函数
	 */
	public ChatUser() {
	}
	
	@Override
	public boolean equals(Object obj) {
		
		//先自己与自己比较
		if(this == obj)	return true;
		
		// 对象是否为空，并且检测对象的类型
		if(obj == null || !(obj instanceof ChatUser))
			return false;
		ChatUser other = (ChatUser)obj;
		if(PojoHelper.equals(this.UserName, other.UserName))
			return true;
		return false;
	}

	@Override
	public int hashCode() {
		return PojoHelper.hashCode(this.userId, this.roomId);
	}

	@Override
	public String toString() {
		return "ChatUser{userId = " +this.userId+ 
						", roomId =" + this.roomId + 
						",joinDate = " + this.joinDate + "}"; 
	}
	
	/** 标识 */
	public int getId() {
		return id;
	}
	
	/** 标识 */
	public void setId(int id) {
		this.id = id;
	}

	/** 用户标识 */
	public int getUserId() {
		return userId;
	}
	
	/** 用户标识 */
	public void setUserId(int userId) {
		this.userId = userId;
	}
	
	/** 房间标识 */
	public int getRoomId() {
		return roomId;
	}
	
	/** 房间标识 */
	public void setRoomId(int roomId) {
		this.roomId = roomId;
	}
	
	/** 用户名称 */
	public String getUserName() {
		return UserName;
	}
	
	/** 用户名称 */
	public void setUserName(String userName) {
		UserName = userName;
	}
	
	
	public Date getsayDate(){
		return sayDate;
	}
	public void setsayDate(Date sayDate){
		this.sayDate=sayDate;
	}
	
	public Date getactTime(){
			return actTime;
	}
	public void setactTime(Date actTime){
		this.actTime=actTime;
}
		
	/** 用户加入时间 */
	public Date getJoinDate() {
		return joinDate;
	}
	
	/** 用户加入时间 */
	public void setJoinDate(Date joinDate) {
		this.joinDate = joinDate;
	}
	
	/** 用户能否发言 */
	public boolean getIsSay() {
		return isSay;
	}
	
	/** 用户能否发言 */
	public void setIsSay(boolean isSay) {
		this.isSay = isSay;
	}
	
	/** 字体的颜色 */
	public String getFontColor() {
		return fontColor;
	}
	
	/** 字体的颜色 */
	public void setFontColor(String fontColor) {
		this.fontColor = fontColor;
	}
	
	/** 字体的大小 */
	public String getFontSize() {
		return fontSize;
	}
	
	/** 字体的大小 */
	public void setFontSize(String fontSize) {
		this.fontSize = fontSize;
	}
	
	/** 是否激活 */
	public boolean getIsActived() {
		return isActived;
	}
	
	/** 是否激活 */
	public void setIsActived(boolean isActived) {
		this.isActived = isActived;
	}
	
	/** 是否离开 */
	public boolean getIsLeave() {
		return isLeave;
	}
	
	/** 是否离开 */
	public void setIsLeave(boolean isLeave) {
		this.isLeave = isLeave;
	}
	
}
