package com.jitar2Infowarelab.utils;

import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.ServletRequest;

import cn.edustar.jitar.JitarRequestContext;

import com.jitar2Infowarelab.model.Attendee;

/**
 * 组合请求的XML
 * 
 * @author dell
 *
 */
public class MessageXML {

	private static String xmlVersion = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n";
	private static int timeZoneId = 45;   		//默认是  45  // GMT+8 北京时间 
	private static int timeZoneHours = 8 ; 	//需要把当前时间  减去 8小时 ，才能和视频 服务器的一直 
	
	//当前登录用户和密码 
	private static String currentLoginName = "admin";
	private static String currentLoginPassword = "admin";

	private static String InfowareLab = "box";
	private static int serviceVersion = 50;
	
	private static void initParam(){
		String _site = Utils.getInfowarelab_SiteName();
		if(!Utils.isNullorBlank(_site)){
			InfowareLab =  _site;
		}
		String version = Utils.getInfowarelab_ServiceVersion();
		if(!Utils.isNullorBlank(version)){
			serviceVersion =  Integer.parseInt(version);
		}
		/*
        currentLoginName = Utils.getLoginUser().getLoginName();
        //教研中用户的密码是加密的了。
        //currentLoginPassword = Utils.getLoginUserPasswordByCookie();
        currentLoginPassword = Utils.getLoginUser().getPassword();
        */
		
        currentLoginName = Utils.getInfowarelab_AdminUser();
        currentLoginPassword = Utils.getInfowarelab_AdminPassword();	        
	}
	
	private static String getHeadXml(String action)
	{
		initParam();
		StringBuilder strbuilderXml = new StringBuilder();
		strbuilderXml.append("<header>\n");
		strbuilderXml.append("<action>"+action+"</action>\n");
		strbuilderXml.append("<service>meeting</service>\n");
		strbuilderXml.append("<version>"+serviceVersion+"</version>\n");
		strbuilderXml.append("<type>xml</type>\n");
		strbuilderXml.append("<siteName>"+InfowareLab+"</siteName>\n");
		strbuilderXml.append("<userName>"+currentLoginName+"</userName>\n");
		strbuilderXml.append("<password>"+currentLoginPassword+"</password>\n");
		strbuilderXml.append("</header>\n");	
		return strbuilderXml.toString();
	}
	
	/**
	 * 
	 * 查询会议列表的请求XML
	 * 
	 * @param startDate
	 * @param endDate
	 *
	 * @return
	 */
	public static String getMessageXML_listSummaryMeeting(final Date startDate,final Date endDate){
		StringBuilder strbuilderXml = new StringBuilder(xmlVersion);
		strbuilderXml.append("<Message>\n");
		strbuilderXml.append(getHeadXml("listSummaryMeeting"));
		strbuilderXml.append("<body>\n"); 
		strbuilderXml.append("<startDateStart>" + Utils.formatForGmtTime(startDate) + "</startDateStart>\n"); 	//会议开始时间
		strbuilderXml.append("<startDateEnd>" + Utils.formatForGmtTime(endDate) + "</startDateEnd>\n"); 			//会议结束时间
		strbuilderXml.append("<timeZoneId>"+ timeZoneId +"</timeZoneId>\n");			//时区ID 默认45（GMT+8）
		strbuilderXml.append("</body>\n");
		strbuilderXml.append("</Message>\n");
		
		return strbuilderXml.toString();
	}
	
	/**
	 * 查询我的会议列表请求XML
	 * @return
	 */
	public static String getMessageXML_listSummaryPrivateMeeting(){
		StringBuilder strbuilderXml = new StringBuilder(xmlVersion);
		strbuilderXml.append("<Message>\n");
		strbuilderXml.append(getHeadXml("listSummaryPrivateMeeting"));
		strbuilderXml.append("<body>\n"); 
		strbuilderXml.append("<timeZoneId>"+ timeZoneId +"</timeZoneId>\n");			//时区ID 默认45（GMT+8）
		strbuilderXml.append("</body>\n");
		strbuilderXml.append("</Message>\n");
		
		return strbuilderXml.toString();		
	}
	
	/**
	 * 获取启动会议Url请求XML
	 * 
	 *
	 *
	 * @param confKey
	 * @param webBaseUrl
	 * @return
	 */
	public static String getMessageXML_readStartMeetingURL(final String confKey,final String webBaseUrl){
		StringBuilder strbuilderXml = new StringBuilder(xmlVersion);
		strbuilderXml.append("<Message>\n");
		strbuilderXml.append(getHeadXml("readStartMeetingURL"));
		strbuilderXml.append("<body>\n"); 
		strbuilderXml.append("<confKey>"+confKey+"</confKey>\n");		
		strbuilderXml.append("<webBaseUrl>"+webBaseUrl+"</webBaseUrl>\n");
		strbuilderXml.append("</body>\n");
		strbuilderXml.append("</Message>\n");
		
		return strbuilderXml.toString();			
	}
	
	/**
	 * 获取加入会议Url 请求XML
	 *
	 *
	 * @param confKey
	 * @param webBaseUrl
	 * @return
	 */
	public static String getMessageXML_readJoinMeetingURL(final String confKey,final String webBaseUrl){
		StringBuilder strbuilderXml = new StringBuilder(xmlVersion);
		strbuilderXml.append("<Message>\n");
		strbuilderXml.append(getHeadXml("readJoinMeetingURL"));
		strbuilderXml.append("<body>\n"); 
		strbuilderXml.append("<confKey>"+confKey+"</confKey>\n");		
		strbuilderXml.append("<webBaseUrl>"+webBaseUrl+"</webBaseUrl>\n");
		strbuilderXml.append("</body>\n");
		strbuilderXml.append("</Message>\n");
		
		return strbuilderXml.toString();			
	}	
	
	/**
	 * 读取会议启动信息 的 请求XML
	 *
	 *
	 * @param hostName
	 * @param displayName
	 * @param confKey
	 * @param meetingPwd
	 * @param meetingEmail
	 * @param webBaseUrl
	 * @return
	 */
	public static String getMessageXML_startMeeting(final String hostName,final String displayName,final String meetingKey,final String meetingPwd,final String meetingEmail,final String webBaseUrl){
		StringBuilder strbuilderXml = new StringBuilder(xmlVersion);
		strbuilderXml.append("<Message>\n");
		strbuilderXml.append(getHeadXml("startMeeting"));
		strbuilderXml.append("<body>\n"); 
		strbuilderXml.append("<hostName>"+hostName+"</hostName>\n");
		strbuilderXml.append("<displayName>"+displayName+"</displayName>\n");
		strbuilderXml.append("<meetingKey>"+meetingKey+"</meetingKey>\n");	
		strbuilderXml.append("<confKey>"+meetingKey+"</confKey>\n");
		strbuilderXml.append("<meetingPwd>"+meetingPwd+"</meetingPwd>\n");
		strbuilderXml.append("<email>"+meetingEmail+"</email>\n");
		strbuilderXml.append("<webBaseUrl>"+webBaseUrl+"</webBaseUrl>\n");
		strbuilderXml.append("</body>\n");
		strbuilderXml.append("</Message>\n");
		
		return strbuilderXml.toString();			
	}	

	/**
	 * 读取加入会议信息
	 *
	 *
	 * @param attendeeName
	 * @param confKey
	 * @param meetingPwd
	 * @param meetingEmail
	 * @param serverName
	 * @param webBaseUrl
	 * @return
	 */
	public static String getMessageXML_joinMeeting(final String attendeeName,final String confKey,final String meetingPwd,final String meetingEmail,final String serverName ,final String webBaseUrl){
		StringBuilder strbuilderXml = new StringBuilder(xmlVersion);
		strbuilderXml.append("<Message>\n");
		strbuilderXml.append(getHeadXml("joinMeeting"));
		strbuilderXml.append("<body>\n"); 
		strbuilderXml.append("<attendeeName>"+attendeeName+"</attendeeName>\n");
		strbuilderXml.append("<confKey>"+confKey+"</confKey>\n");	
		strbuilderXml.append("<meetingPwd>"+meetingPwd+"</meetingPwd>\n");
		strbuilderXml.append("<meetingEmail>"+meetingEmail+"</meetingEmail>\n");
		strbuilderXml.append("<email>"+meetingEmail+"</email>\n");
		strbuilderXml.append("<serverName>"+serverName+"</serverName>\n");
		strbuilderXml.append("<webBaseUrl>"+webBaseUrl+"</webBaseUrl>\n");
		strbuilderXml.append("</body>\n");
		strbuilderXml.append("</Message>\n");
		
		return strbuilderXml.toString();			
	}	
	
	/**
	 * 创建预约会议的 请求 XML
	 *
	 *
	 * @param subject
	 * @param startTime
	 * @param endTime
	 * @param attendeeAmount
	 * @param hostName
	 * @param creator
	 * @param openType
	 * @param passwd
	 * @param beforehandTime
	 * @param webBaseUrl
	 * @param agenda  描述
	 * @param listAttendee   参会者
	 * @return
	 */
	public static String getMessageXML_createReserveMeeting(final String subject,final Date startTime,final Date endTime,final int attendeeAmount,
			final String hostName ,final String creator,final boolean openType,final String passwd,
			final int beforehandTime,final String webBaseUrl,final String agenda,final List<Attendee> listAttendee){
		StringBuilder strbuilderXml = new StringBuilder(xmlVersion);
		strbuilderXml.append("<Message>\n");
		strbuilderXml.append(getHeadXml("createReserveMeeting"));
		strbuilderXml.append("<body>\n"); 
		strbuilderXml.append("<subject>"+subject+"</subject>\n");

		SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");   
		strbuilderXml.append("<startTime>" + Utils.formatForGmtTime(new Date(startTime.getTime() - timeZoneHours * 60 * 60 * 1000)) + "</startTime>\n"); 		//会议开始时间
		strbuilderXml.append("<endTime>" + Utils.formatForGmtTime(new Date(endTime.getTime() - timeZoneHours * 60 * 60 * 1000)) + "</endTime>\n"); 				//会议结束时间
		strbuilderXml.append("<timeZoneId>"+ timeZoneId +"</timeZoneId>\n");
		strbuilderXml.append("<attendeeAmount>"+attendeeAmount+"</attendeeAmount>\n");
		strbuilderXml.append("<hostName>"+hostName+"</hostName>\n");
		strbuilderXml.append("<creator>"+creator+"</creator>\n");
		strbuilderXml.append("<openType>"+openType+"</openType>\n");
		strbuilderXml.append("<passwd>"+passwd+"</passwd>\n");
		
		strbuilderXml.append("<conferencePattern>0</conferencePattern>\n");		//主持人模式：0 自由模式：1
		strbuilderXml.append("<agenda>"+agenda+"</agenda>\n");
		strbuilderXml.append("<mailTemplateLocal>zh_CN</mailTemplateLocal>\n");
		
		strbuilderXml.append("<beforehandTime>"+beforehandTime+"</beforehandTime>\n");
		strbuilderXml.append("<webBaseUrl>"+webBaseUrl+"</webBaseUrl>\n");
		
		
		if(null != listAttendee && listAttendee.size() > 0){
			strbuilderXml.append("<attendees>\n");
			for(int i = 0; i < listAttendee.size(); i++){
				Attendee attendee = listAttendee.get(i);
				strbuilderXml.append("<attendee>\n");
				strbuilderXml.append("<name>"+ attendee.getName() +"</name>\n");
				if(!Utils.isNullorBlank(attendee.getEmail())){
					strbuilderXml.append("<email>" + attendee.getEmail() + "</email>\n");
				}else{
					strbuilderXml.append("<email>none@none.com</email>\n");
				}
				if(!Utils.isNullorBlank(attendee.getPhone())){
					strbuilderXml.append("<phone>" + attendee.getPhone() + "</phone>\n");
				}
				strbuilderXml.append("</attendee>\n");
			}
			strbuilderXml.append("</attendees>\n");
		}
		
		
		strbuilderXml.append("</body>\n");
		strbuilderXml.append("</Message>\n");
		
		return strbuilderXml.toString();			
	}	
	
	/**
	 * 创建固定会议请求 XML
	 *
	 *
	 * @param subject
	 * @param startTime
	 * @param endTime
	 * @param attendeeAmount
	 * @param hostName
	 * @param openType
	 * @param passwd
	 * @param beforehandTime
	 * @param webBaseUrl
	 * @param agenda  会议描述
	 * @param listAttendee
	 * @return
	 */
	public static String getMessageXML_createFixedMeeting(final String subject,final Date startTime,final Date endTime,final int attendeeAmount,
			final String hostName ,final boolean openType,final String passwd,
			final int beforehandTime,final String webBaseUrl,final String agenda,final List<Attendee> listAttendee){
		StringBuilder strbuilderXml = new StringBuilder(xmlVersion);
		strbuilderXml.append("<Message>\n");
		strbuilderXml.append(getHeadXml("createFixedMeeting"));
		strbuilderXml.append("<body>\n"); 
		strbuilderXml.append("<subject>"+subject+"</subject>\n");
		strbuilderXml.append("<startTime>" + Utils.formatForGmtTime(new Date(startTime.getTime() - timeZoneHours * 60 * 60 * 1000)) + "</startTime>\n"); 		//会议开始时间
		strbuilderXml.append("<endTime>" + Utils.formatForGmtTime(new Date(endTime.getTime() - timeZoneHours * 60 * 60 * 1000)) + "</endTime>\n"); 			//会议结束时间
		strbuilderXml.append("<timeZoneId>"+ timeZoneId +"</timeZoneId>\n");
		strbuilderXml.append("<attendeeAmount>"+attendeeAmount+"</attendeeAmount>\n");
		strbuilderXml.append("<hostName>"+hostName+"</hostName>\n");
		strbuilderXml.append("<openType>"+openType+"</openType>\n");
		strbuilderXml.append("<passwd>"+passwd+"</passwd>\n");
		
		strbuilderXml.append("<agenda>"+agenda+"</agenda>\n");
		strbuilderXml.append("<mailTemplateLocal>zh_CN</mailTemplateLocal>\n");
		
		strbuilderXml.append("<beforehandTime>"+beforehandTime+"</beforehandTime>\n");
		strbuilderXml.append("<webBaseUrl>"+webBaseUrl+"</webBaseUrl>\n");
		
		
		if(null != listAttendee && listAttendee.size() > 0){
			strbuilderXml.append("<attendees>\n");
			for(int i = 0; i < listAttendee.size(); i++){
				Attendee attendee = listAttendee.get(i);
				strbuilderXml.append("<attendee>\n");
				strbuilderXml.append("<name>"+ attendee.getName() +"</name>\n");
				if(!Utils.isNullorBlank(attendee.getEmail())){
					strbuilderXml.append("<email>" + attendee.getEmail() + "</email>\n");
				}else{
					strbuilderXml.append("<email>none@none.com</email>\n");
				}
				if(!Utils.isNullorBlank(attendee.getPhone())){
					strbuilderXml.append("<phone>" + attendee.getPhone() + "</phone>\n");
				}
				strbuilderXml.append("</attendee>\n");
			}
			strbuilderXml.append("</attendees>\n");
		}
		
		
		strbuilderXml.append("</body>\n");
		strbuilderXml.append("</Message>\n");
		
		return strbuilderXml.toString();			
	}	
	
	/**
	 * 创建周期会议请求 XML
	 *
	 *
	 * @param subject
	 * @param startHourMinute
	 * @param endHourMinute
	 * @param effectiveTime
	 * @param expirationTime
	 * @param attendeeAmount
	 * @param hostName
	 * @param creator
	 * @param openType
	 * @param passwd
	 * @param beforehandTime
	 * @param webBaseUrl
	 * @param agenda  描述
	 * @param listAttendee
	 * @return
	 */
	public static String getMessageXML_createRegularMeeting(final String subject,final String startHourMinute,final String endHourMinute,final Date effectiveTime,final Date expirationTime,final int attendeeAmount,
			final String hostName ,final String creator,final boolean openType,final String passwd,
			final int beforehandTime,final String webBaseUrl,final String agenda,final List<Attendee> listAttendee){
		StringBuilder strbuilderXml = new StringBuilder(xmlVersion);
		strbuilderXml.append("<Message>\n");
		strbuilderXml.append(getHeadXml("createRegularMeeting"));
		strbuilderXml.append("<body>\n"); 
		strbuilderXml.append("<subject>"+subject+"</subject>\n");
		strbuilderXml.append("<startHourMinute>"+startHourMinute+"</startHourMinute>\n");
		strbuilderXml.append("<endHourMinute>"+endHourMinute+"</endHourMinute>\n");
		strbuilderXml.append("<effectiveTime>" + Utils.formatForGmtTime(new Date(effectiveTime.getTime() - timeZoneHours * 60 * 60 * 1000)) + "</effectiveTime>\n"); 		
		strbuilderXml.append("<expirationTime>" + Utils.formatForGmtTime(new Date(expirationTime.getTime() - timeZoneHours * 60 * 60 * 1000)) + "</expirationTime>\n"); 	
		strbuilderXml.append("<timeZoneId>"+ timeZoneId +"</timeZoneId>\n");
		strbuilderXml.append("<attendeeAmount>"+attendeeAmount+"</attendeeAmount>\n");
		strbuilderXml.append("<hostName>"+hostName+"</hostName>\n");
		strbuilderXml.append("<creator>"+creator+"</creator>\n");
		strbuilderXml.append("<repeatTypeKey>DAILY</repeatTypeKey>\n");
		strbuilderXml.append("<repeatTypeValue>2</repeatTypeValue>\n");
		strbuilderXml.append("<openType>"+openType+"</openType>\n");
		strbuilderXml.append("<passwd>"+passwd+"</passwd>\n");
		
		strbuilderXml.append("<conferencePattern>0</conferencePattern>\n");		//主持人模式：0 自由模式：1
		strbuilderXml.append("<agenda>"+agenda+"</agenda>\n");
		strbuilderXml.append("<mailTemplateLocal>zh_CN</mailTemplateLocal>\n");
		
		strbuilderXml.append("<beforehandTime>"+beforehandTime+"</beforehandTime>\n");
		strbuilderXml.append("<webBaseUrl>"+webBaseUrl+"</webBaseUrl>\n");
		
		
		if(null != listAttendee && listAttendee.size() > 0){
			strbuilderXml.append("<attendees>\n");
			for(int i = 0; i < listAttendee.size(); i++){
				Attendee attendee = listAttendee.get(i);
				strbuilderXml.append("<attendee>\n");
				strbuilderXml.append("<name>"+ attendee.getName() +"</name>\n");
				if(!Utils.isNullorBlank(attendee.getEmail())){
					strbuilderXml.append("<email>" + attendee.getEmail() + "</email>\n");
				}else{
					strbuilderXml.append("<email>none@none.com</email>\n");
				}
				if(!Utils.isNullorBlank(attendee.getPhone())){
					strbuilderXml.append("<phone>" + attendee.getPhone() + "</phone>\n");
				}
				strbuilderXml.append("</attendee>\n");
			}
			strbuilderXml.append("</attendees>\n");
		}
		
		
		strbuilderXml.append("</body>\n");
		strbuilderXml.append("</Message>\n");
		
		return strbuilderXml.toString();			
	}	
	
	/**
	 * 读取会议状态 请求 XML
	 *
	 *
	 * @param confKey
	 * @return
	 */
	public static String getMessageXML_readMeetingStatus(final String confKey){
		StringBuilder strbuilderXml = new StringBuilder(xmlVersion);
		strbuilderXml.append("<Message>\n");
		strbuilderXml.append(getHeadXml("readMeetingStatus"));
		strbuilderXml.append("<body>\n"); 
		strbuilderXml.append("<confKey>"+confKey+"</confKey>\n");		
		strbuilderXml.append("</body>\n");
		strbuilderXml.append("</Message>\n");
		
		return strbuilderXml.toString();		
	}
	
	/**
	 * 获取查询参数[startTime,endTime]和[会议开始时间,结束时间]存在交集的会议列表  请求 XML
	 *
	 *
	 * @param startFrom
	 * @param maximumNum
	 * @param openType
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public static String getMessageXML_listSummaryMeeting41(final String startFrom,final int maximumNum,final boolean openType,final Date startTime,final Date endTime){
		StringBuilder strbuilderXml = new StringBuilder(xmlVersion);
		strbuilderXml.append("<Message>\n");
		strbuilderXml.append(getHeadXml("listSummaryMeeting41"));
		strbuilderXml.append("<body>\n");
		strbuilderXml.append("<startFrom>"+startFrom+"</startFrom>\n"); 
		strbuilderXml.append("<maximumNum>"+maximumNum+"</maximumNum>\n"); 
		strbuilderXml.append("<openType>"+openType+"</openType>\n"); 
		strbuilderXml.append("<startTime>" + Utils.formatForGmtTime(startTime) + "</startTime>\n"); 
		strbuilderXml.append("<endTime>" + Utils.formatForGmtTime(endTime) + "</endTime>\n"); 
		strbuilderXml.append("<timeZoneId>"+ timeZoneId +"</timeZoneId>\n");		
		strbuilderXml.append("</body>\n");
		strbuilderXml.append("</Message>\n");
		
		return strbuilderXml.toString();		
	}
	
	/**
	 * 获取历史会议列表   请求 XML
	 *
	 *
	 * @param startFrom		从符合条件的第N条记录开始检索
	 * @param maximumNum	检索返回的最大记录数
	 * @param openType
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public static String getMessageXML_listHistoryMeeting(final String startFrom,final int maximumNum,final boolean openType,final Date startTime,final Date endTime){
		StringBuilder strbuilderXml = new StringBuilder(xmlVersion);
		strbuilderXml.append("<Message>\n");
		strbuilderXml.append(getHeadXml("listHistoryMeeting"));
		strbuilderXml.append("<body>\n");
		strbuilderXml.append("<startFrom>"+startFrom+"</startFrom>\n"); 				//0
		strbuilderXml.append("<maximumNum>"+maximumNum+"</maximumNum>\n"); 			//10
		strbuilderXml.append("<openType>"+openType+"</openType>\n"); 
		strbuilderXml.append("<startTime>" + Utils.formatForGmtTime(startTime) + "</startTime>\n"); 
		strbuilderXml.append("<endTime>" + Utils.formatForGmtTime(endTime) + "</endTime>\n"); 
		strbuilderXml.append("</body>\n");
		strbuilderXml.append("</Message>\n");
		
		return strbuilderXml.toString();		
	}	
	
	
	/**
	 * 获取历史会议参加者信息   请求 XML
	 *
	 *
	 * @param startFrom
	 * @param maximumNum
	 * @param confId
	 * @return
	 */
	public static String getMessageXML_listHistoryAttendee(final String startFrom,final int maximumNum,final String confId){
		StringBuilder strbuilderXml = new StringBuilder(xmlVersion);
		strbuilderXml.append("<Message>\n");
		strbuilderXml.append(getHeadXml("listHistoryAttendee"));
		strbuilderXml.append("<body>\n");
		strbuilderXml.append("<startFrom>"+startFrom+"</startFrom>\n"); 				//0
		strbuilderXml.append("<maximumNum>"+maximumNum+"</maximumNum>\n"); 			//10
		strbuilderXml.append("<confId>"+confId+"</confId>\n"); 
		strbuilderXml.append("</body>\n");
		strbuilderXml.append("</Message>\n");
		
		return strbuilderXml.toString();		
	}
	
	/**
	 * 获取未结束会议的邀请参加者信息   请求 XML
	 *
	 *
	 * @param startFrom
	 * @param maximumNum
	 * @param confKey
	 * @return
	 */
	public static String getMessageXML_listSummaryAttendee(final String startFrom,final int maximumNum,final String confKey){
		StringBuilder strbuilderXml = new StringBuilder(xmlVersion);
		strbuilderXml.append("<Message>\n");
		strbuilderXml.append(getHeadXml("listSummaryAttendee"));
		strbuilderXml.append("<body>\n");
		strbuilderXml.append("<startFrom>"+startFrom+"</startFrom>\n"); 				//0
		strbuilderXml.append("<maximumNum>"+maximumNum+"</maximumNum>\n"); 			//10
		strbuilderXml.append("<confKey>"+confKey+"</confKey>\n"); 
		strbuilderXml.append("</body>\n");
		strbuilderXml.append("</Message>\n");
		
		return strbuilderXml.toString();		
	}
	
	/**
	 * 读取未结束的单个会议信息   请求 XML
	 *
	 *
	 * @param confKey
	 * @return
	 */
	public static String getMessageXML_readMeeting(final String confKey){
		StringBuilder strbuilderXml = new StringBuilder(xmlVersion);
		strbuilderXml.append("<Message>\n");
		strbuilderXml.append(getHeadXml("readMeeting"));
		strbuilderXml.append("<body>\n");
		strbuilderXml.append("<confKey>"+confKey+"</confKey>\n"); 
		strbuilderXml.append("</body>\n");
		strbuilderXml.append("</Message>\n");
		
		return strbuilderXml.toString();		
	}	
	
	/**
	 * 邀请参加者 请求 XML
	 *
	 *
	 * @param confKey
	 * @param webBaseUrl
	 * @param listAttendee
	 * @return
	 */
	public static String getMessageXML_createAttendee(final String confKey,final String webBaseUrl,final List<Attendee> listAttendee){
		StringBuilder strbuilderXml = new StringBuilder(xmlVersion);
		strbuilderXml.append("<Message>\n");
		strbuilderXml.append(getHeadXml("createAttendee"));
		strbuilderXml.append("<body>\n");
		strbuilderXml.append("<confKey>"+confKey+"</confKey>\n"); 
		strbuilderXml.append("<webBaseUrl>"+webBaseUrl+"</webBaseUrl>\n");
		 
		if(null != listAttendee && listAttendee.size() > 0){
			strbuilderXml.append("<attendees>\n");
			for(int i = 0; i < listAttendee.size(); i++){
				Attendee attendee = listAttendee.get(i);
				strbuilderXml.append("<attendee>\n");
				strbuilderXml.append("<name>"+ attendee.getName() +"</name>\n");
				if(!Utils.isNullorBlank(attendee.getEmail())){
					strbuilderXml.append("<email>" + attendee.getEmail() + "</email>\n");
				}else{
					strbuilderXml.append("<email>none@none.com</email>\n");
				}
				strbuilderXml.append("<phone>" + attendee.getPhone() + "</phone>\n");
				strbuilderXml.append("</attendee>\n");
			}
			strbuilderXml.append("</attendees>\n");
		}
		
		strbuilderXml.append("</body>\n");
		strbuilderXml.append("</Message>\n");
		
		return strbuilderXml.toString();		
	}	

	/**
	 * 删除会议 请求 XML
	 *
	 *
	 * @param confKey
	 * @param webBaseUrl
	 * @return
	 */
	public static String getMessageXML_deleteMeeting(final String confKey,final String webBaseUrl){
		StringBuilder strbuilderXml = new StringBuilder(xmlVersion);
		strbuilderXml.append("<Message>\n");
		strbuilderXml.append(getHeadXml("deleteMeeting"));
		strbuilderXml.append("<body>\n");
		strbuilderXml.append("<confKey>"+confKey+"</confKey>\n"); 
		strbuilderXml.append("<webBaseUrl>"+webBaseUrl+"</webBaseUrl>\n");
		strbuilderXml.append("</body>\n");
		strbuilderXml.append("</Message>\n");
		
		return strbuilderXml.toString();		
	}	
	
	/**
	 * 编辑预约会议   请求 XML
	 *
	 *
	 * @param confKey
	 * @param subject
	 * @param startTime
	 * @param endTime
	 * @param attendeeAmount
	 * @param hostName
	 * @param creator
	 * @param openType
	 * @param passwd
	 * @param beforehandTime
	 * @param webBaseUrl
	 * @param agenda 会议描述
	 * @param listAttendee
	 * @return
	 */
	public static String getMessageXML_updateReserveMeeting(final String confKey,final String subject,
			final Date startTime,final Date endTime,final int attendeeAmount,
			final String hostName ,final String creator,final boolean openType,final String passwd,
			final int beforehandTime,final String webBaseUrl,final String agenda,final List<Attendee> listAttendee){
		StringBuilder strbuilderXml = new StringBuilder(xmlVersion);
		strbuilderXml.append("<Message>\n");
		strbuilderXml.append(getHeadXml("updateReserveMeeting"));
		strbuilderXml.append("<body>\n"); 
		strbuilderXml.append("<confKey>"+confKey+"</confKey>\n");
		strbuilderXml.append("<subject>"+subject+"</subject>\n");
		strbuilderXml.append("<startTime>" + Utils.formatForGmtTime(new Date(startTime.getTime() - timeZoneHours * 60 * 60 * 1000)) + "</startTime>\n"); 		//会议开始时间
		strbuilderXml.append("<endTime>" + Utils.formatForGmtTime(new Date(endTime.getTime() - timeZoneHours * 60 * 60 * 1000)) + "</endTime>\n"); 			//会议结束时间
		strbuilderXml.append("<timeZoneId>"+ timeZoneId +"</timeZoneId>\n");
		strbuilderXml.append("<attendeeAmount>"+attendeeAmount+"</attendeeAmount>\n");
		strbuilderXml.append("<hostName>"+hostName+"</hostName>\n");
		strbuilderXml.append("<creator>"+creator+"</creator>\n");
		strbuilderXml.append("<openType>"+openType+"</openType>\n");
		strbuilderXml.append("<passwd>"+passwd+"</passwd>\n");
		
		strbuilderXml.append("<conferencePattern>0</conferencePattern>\n");		//主持人模式：0 自由模式：1
		strbuilderXml.append("<agenda>"+agenda+"</agenda>\n");
		strbuilderXml.append("<mailTemplateLocal>zh_CN</mailTemplateLocal>\n");
		
		strbuilderXml.append("<beforehandTime>"+beforehandTime+"</beforehandTime>\n");
		strbuilderXml.append("<webBaseUrl>"+webBaseUrl+"</webBaseUrl>\n");
		
		
		if(null != listAttendee && listAttendee.size() > 0){
			strbuilderXml.append("<attendees>\n");
			for(int i = 0; i < listAttendee.size(); i++){
				Attendee attendee = listAttendee.get(i);
				strbuilderXml.append("<attendee>\n");
				strbuilderXml.append("<name>"+ attendee.getName() +"</name>\n");
				if(!Utils.isNullorBlank(attendee.getEmail())){
					strbuilderXml.append("<email>" + attendee.getEmail() + "</email>\n");
				}else{
					strbuilderXml.append("<email>none@none.com</email>\n");
				}
				if(!Utils.isNullorBlank(attendee.getPhone())){
					strbuilderXml.append("<phone>" + attendee.getPhone() + "</phone>\n");
				}
				strbuilderXml.append("</attendee>\n");
			}
			strbuilderXml.append("</attendees>\n");
		}
		
		
		strbuilderXml.append("</body>\n");
		strbuilderXml.append("</Message>\n");
		
		return strbuilderXml.toString();			
	}	

	/**
	 * 编辑固定会议
	 *
	 *
	 * @param confKey
	 * @param subject
	 * @param startTime
	 * @param endTime
	 * @param attendeeAmount
	 * @param hostName
	 * @param openType
	 * @param passwd
	 * @param beforehandTime
	 * @param webBaseUrl
	 * @param agenda  会议描述
	 * @param listAttendee
	 * @return
	 */
	public static String getMessageXML_updateFixedMeeting(
			final String confKey,final String subject,final Date startTime,final Date endTime,final int attendeeAmount,
			final String hostName ,final boolean openType,final String passwd,
			final int beforehandTime,final String webBaseUrl,final String agenda,final List<Attendee> listAttendee){
		StringBuilder strbuilderXml = new StringBuilder(xmlVersion);
		strbuilderXml.append("<Message>\n");
		strbuilderXml.append(getHeadXml("updateFixedMeeting"));
		strbuilderXml.append("<body>\n"); 
		strbuilderXml.append("<confKey>"+confKey+"</confKey>\n");
		strbuilderXml.append("<subject>"+subject+"</subject>\n");
		strbuilderXml.append("<startTime>" + Utils.formatForGmtTime(new Date(startTime.getTime() - timeZoneHours * 60 * 60 * 1000)) + "</startTime>\n"); 		//会议开始时间
		strbuilderXml.append("<endTime>" + Utils.formatForGmtTime(new Date(endTime.getTime() - timeZoneHours * 60 * 60 * 1000)) + "</endTime>\n"); 			//会议结束时间
		strbuilderXml.append("<timeZoneId>"+ timeZoneId +"</timeZoneId>\n");
		strbuilderXml.append("<attendeeAmount>"+attendeeAmount+"</attendeeAmount>\n");
		strbuilderXml.append("<hostName>"+hostName+"</hostName>\n");
		strbuilderXml.append("<openType>"+openType+"</openType>\n");
		strbuilderXml.append("<passwd>"+passwd+"</passwd>\n");
		
		strbuilderXml.append("<agenda>"+agenda+"</agenda>\n");
		strbuilderXml.append("<mailTemplateLocal>zh_CN</mailTemplateLocal>\n");
		
		strbuilderXml.append("<beforehandTime>"+beforehandTime+"</beforehandTime>\n");
		strbuilderXml.append("<webBaseUrl>"+webBaseUrl+"</webBaseUrl>\n");
		
		
		if(null != listAttendee && listAttendee.size() > 0){
			strbuilderXml.append("<attendees>\n");
			for(int i = 0; i < listAttendee.size(); i++){
				Attendee attendee = listAttendee.get(i);
				strbuilderXml.append("<attendee>\n");
				strbuilderXml.append("<name>"+ attendee.getName() +"</name>\n");
				if(!Utils.isNullorBlank(attendee.getEmail())){
					strbuilderXml.append("<email>" + attendee.getEmail() + "</email>\n");
				}else{
					strbuilderXml.append("<email>none@none.com</email>\n");
				}
				if(!Utils.isNullorBlank(attendee.getPhone())){
					strbuilderXml.append("<phone>" + attendee.getPhone() + "</phone>\n");
				}
				strbuilderXml.append("</attendee>\n");
			}
			strbuilderXml.append("</attendees>\n");
		}
		
		
		strbuilderXml.append("</body>\n");
		strbuilderXml.append("</Message>\n");
		
		return strbuilderXml.toString();			
	}	

	/**
	 * 编辑周期会议
	 *
	 *
	 * @param confKey
	 * @param subject
	 * @param startHourMinute
	 * @param endHourMinute
	 * @param effectiveTime
	 * @param expirationTime
	 * @param attendeeAmount
	 * @param hostName
	 * @param creator
	 * @param openType
	 * @param passwd
	 * @param beforehandTime
	 * @param webBaseUrl
	 * @param agenda  会议描述
	 * @param listAttendee
	 * @return
	 */
	public static String getMessageXML_updateRegularMeeting(
			final String confKey,final String subject,final String startHourMinute,final String endHourMinute,final Date effectiveTime,final Date expirationTime,final int attendeeAmount,
			final String hostName ,final String creator,final boolean openType,final String passwd,
			final int beforehandTime,final String webBaseUrl,final String agenda,final List<Attendee> listAttendee){
		StringBuilder strbuilderXml = new StringBuilder(xmlVersion);
		strbuilderXml.append("<Message>\n");
		strbuilderXml.append(getHeadXml("updateRegularMeeting"));
		strbuilderXml.append("<body>\n"); 
		strbuilderXml.append("<confKey>"+confKey+"</confKey>\n");
		strbuilderXml.append("<subject>"+subject+"</subject>\n");
		strbuilderXml.append("<startHourMinute>"+startHourMinute+"</startHourMinute>\n");
		strbuilderXml.append("<endHourMinute>"+endHourMinute+"</endHourMinute>\n");
		strbuilderXml.append("<effectiveTime>" + Utils.formatForGmtTime(new Date(effectiveTime.getTime() - timeZoneHours * 60 * 60 * 1000)) + "</effectiveTime>\n"); 		
		strbuilderXml.append("<expirationTime>" + Utils.formatForGmtTime(new Date(expirationTime.getTime() - timeZoneHours * 60 * 60 * 1000)) + "</expirationTime>\n"); 	
		strbuilderXml.append("<timeZoneId>"+ timeZoneId +"</timeZoneId>\n");
		strbuilderXml.append("<attendeeAmount>"+attendeeAmount+"</attendeeAmount>\n");
		strbuilderXml.append("<hostName>"+hostName+"</hostName>\n");
		strbuilderXml.append("<creator>"+creator+"</creator>\n");
		strbuilderXml.append("<repeatTypeKey>DAILY</repeatTypeKey>\n");
		strbuilderXml.append("<repeatTypeValue>2</repeatTypeValue>\n");
		strbuilderXml.append("<openType>"+openType+"</openType>\n");
		strbuilderXml.append("<passwd>"+passwd+"</passwd>\n");
		
		strbuilderXml.append("<conferencePattern>0</conferencePattern>\n");		//主持人模式：0 自由模式：1
		strbuilderXml.append("<agenda>"+agenda+"</agenda>\n");
		strbuilderXml.append("<mailTemplateLocal>zh_CN</mailTemplateLocal>\n");
		
		strbuilderXml.append("<beforehandTime>"+beforehandTime+"</beforehandTime>\n");
		strbuilderXml.append("<webBaseUrl>"+webBaseUrl+"</webBaseUrl>\n");
		
		
		if(null != listAttendee && listAttendee.size() > 0){
			strbuilderXml.append("<attendees>\n");
			for(int i = 0; i < listAttendee.size(); i++){
				Attendee attendee = listAttendee.get(i);
				strbuilderXml.append("<attendee>\n");
				strbuilderXml.append("<name>"+ attendee.getName() +"</name>\n");
				if(!Utils.isNullorBlank(attendee.getEmail())){
					strbuilderXml.append("<email>" + attendee.getEmail() + "</email>\n");
				}else{
					strbuilderXml.append("<email>none@none.com</email>\n");
				}
				if(!Utils.isNullorBlank(attendee.getPhone())){
					strbuilderXml.append("<phone>" + attendee.getPhone() + "</phone>\n");
				}
				strbuilderXml.append("</attendee>\n");
			}
			strbuilderXml.append("</attendees>\n");
		}
		
		
		strbuilderXml.append("</body>\n");
		strbuilderXml.append("</Message>\n");
		
		return strbuilderXml.toString();			
	}	

	/**
	 * 获取会议服务器名称列表
	 *
	 *
	 * @param confKey
	 * @return
	 */
	public static String getMessageXML_listMeetingServer(final String confKey){
		StringBuilder strbuilderXml = new StringBuilder(xmlVersion);
		strbuilderXml.append("<Message>\n");
		strbuilderXml.append(getHeadXml("listMeetingServer"));
		strbuilderXml.append("<body>\n"); 
		strbuilderXml.append("<confKey>"+confKey+"</confKey>\n");		
		strbuilderXml.append("</body>\n");
		strbuilderXml.append("</Message>\n");
		
		return strbuilderXml.toString();		
	}

	

}
