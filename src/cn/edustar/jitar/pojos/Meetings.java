package cn.edustar.jitar.pojos;

import java.io.Serializable;
import java.util.Date;

import com.jitar2Infowarelab.utils.Utils;

public class Meetings implements Serializable {
	private static final long serialVersionUID = 4791301079420006109L;
	
	private int id;
	
	private String obj;
	
	private int objId;

	/**视频会议的ID*/
	private String confId;
	
	/** 视频会议的Key */
	private String confKey;
	
	/** 视频会议的管理Url */
	private String hostUrl;

	/** 视频会议的参会Url */
	private String attendeeUrl;
	
	/** 会议主题 */
	private String subject;
	
	/** 会议开始时间 */
	private Date startTime;
	 
	/** 会议结束时间 */
	private Date endTime;
	
	/** 主持人 */
	private String hostName;
	
	/** 会议室密码 */
	private String passwd;
	
	/** 会议描述 */
	private String agenda;
	
	/** 网络视频会议的网址*/
	private String webBaseUrl = Utils.getInfowarelab_ServerURL();
	
	/** 提前进入会议室时间*/
	private Integer beforehandTime;
	
	/** 会议类型  预约会议：0  即时会议：1 固定会议：2 周期会议：3*/
	private Integer conferenceType = 0;  //默认预约会议
	
	/** true 公开   false 不公开*/
	private Boolean openType = true;	//默认公开
	
	/** 创建者  */
	private String creator;
	
	/** 参会人数 */
	private int attendeeAmount;

	private String attendeeIds;
	private String attendeeNames;
	
	/** 会议视频的文件链接 */
	private String href;
	
	private String flvThumbNailHref;
	private String flvHref;
	
	/**
	 * 会议状态	status  0：未开始；1：正在进行；2：已结束；3：已锁定
	 */
	private Integer status = 0;
	
	private String resourceIds;
	
	public String getConfId() {
		return confId;
	}

	public void setConfId(String confId) {
		this.confId = confId;
	}

	public String getConfKey() {
		return confKey;
	}

	public void setConfKey(String confKey) {
		this.confKey = confKey;
	}

	public String getHostUrl() {
		return hostUrl;
	}

	public void setHostUrl(String hostUrl) {
		this.hostUrl = hostUrl;
	}

	public String getAttendeeUrl() {
		return attendeeUrl;
	}

	public void setAttendeeUrl(String attendeeUrl) {
		this.attendeeUrl = attendeeUrl;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getHostName() {
		return hostName;
	}

	public void setHostName(String hostName) {
		this.hostName = hostName;
	}

	public String getPasswd() {
		return passwd;
	}

	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}

	public String getAgenda() {
		return agenda;
	}

	public void setAgenda(String agenda) {
		this.agenda = agenda;
	}

	public String getWebBaseUrl() {
		return webBaseUrl;
	}

	public void setWebBaseUrl(String webBaseUrl) {
		this.webBaseUrl = webBaseUrl;
	}

	public Integer getBeforehandTime() {
		return beforehandTime;
	}

	public void setBeforehandTime(Integer beforehandTime) {
		this.beforehandTime = beforehandTime;
	}

	public Integer getConferenceType() {
		return conferenceType;
	}

	public void setConferenceType(Integer conferenceType) {
		this.conferenceType = conferenceType;
	}

	public Boolean getOpenType() {
		return openType;
	}

	public void setOpenType(Boolean openType) {
		this.openType = openType;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Meetings() {
		 
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getObj() {
		return obj;
	}

	public void setObj(String obj) {
		this.obj = obj;
	}

	public int getObjId() {
		return objId;
	}

	public void setObjId(int objId) {
		this.objId = objId;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public String getHref() {
		return href;
	}

	public void setHref(String href) {
		this.href = href;
	}

	public int getAttendeeAmount() {
		return attendeeAmount;
	}

	public void setAttendeeAmount(int attendeeAmount) {
		this.attendeeAmount = attendeeAmount;
	}

	public String getFlvThumbNailHref() {
		return flvThumbNailHref;
	}

	public void setFlvThumbNailHref(String flvThumbNailHref) {
		this.flvThumbNailHref = flvThumbNailHref;
	}

	public String getFlvHref() {
		return flvHref;
	}

	public void setFlvHref(String flvHref) {
		this.flvHref = flvHref;
	}
	
	public String getAttendeeIds() {
		return attendeeIds;
	}

	public void setAttendeeIds(String attendeeIds) {
		this.attendeeIds = attendeeIds;
	}

	public String getAttendeeNames() {
		return attendeeNames;
	}

	public void setAttendeeNames(String attendeeNames) {
		this.attendeeNames = attendeeNames;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getResourceIds() {
		return resourceIds;
	}

	public void setResourceIds(String resourceIds) {
		this.resourceIds = resourceIds;
	}	
}
