package com.jitar2Infowarelab.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.DocumentException;

import cn.edustar.jitar.action.GroupAction;

import com.jitar2Infowarelab.model.Attendee;
import com.jitar2Infowarelab.model.CemUser;
import com.jitar2Infowarelab.model.CemUsers;
import com.jitar2Infowarelab.model.CreatedMeeting;
import com.jitar2Infowarelab.model.HistoryMeetingMulti;
import com.jitar2Infowarelab.model.HistoryMeetings;
import com.jitar2Infowarelab.model.Meeting;
import com.jitar2Infowarelab.model.MeetingQueryParam;
import com.jitar2Infowarelab.utils.MessageUserXML;
import com.jitar2Infowarelab.utils.MessageXML;
import com.jitar2Infowarelab.utils.PostUtils;
import com.jitar2Infowarelab.utils.ResponseXML;
import com.jitar2Infowarelab.utils.Utils;
import com.jitar2Infowarelab.utils.XmlUtil;

public class InfowarelabService {
	
	/** 日志 */
	private static final Log log = LogFactory.getLog(InfowarelabService.class);

	
	private boolean result = false;
	private String exceptionID = null;
	private String reason = null;
	
	public boolean isResult() {
		return result;
	}

	public String getExceptionID() {
		return exceptionID;
	}

	public String getReason() {
		return reason;
	}

	/**
	 * 查询某时间段的会议列表
	 * @param startDate	开始时间
	 * @param endDate	结束时间
	 * @return
	 * @throws ParseException
	 */
	public com.jitar2Infowarelab.model.Meetings listSummaryMeeting(final Date startDate,final Date endDate) throws ParseException{
		String xml = MessageXML.getMessageXML_listSummaryMeeting(startDate, endDate);
		log.info(xml);
		String serviceUrl = Utils.getInfowarelab_XmlServiceURL();
		log.info(serviceUrl);
		String responseXml = PostUtils.getResponsePostedXML(serviceUrl, xml);
		log.info(responseXml);
		ResponseXML pxml =new ResponseXML();
		try {
			boolean b = pxml.Parse(responseXml);
			if(b){
				this.result = pxml.isResult();
				if(pxml.isResult()){
					com.jitar2Infowarelab.model.Meetings ms = XmlUtil.toBean(pxml.getNakedBody(), com.jitar2Infowarelab.model.Meetings.class);
					return ms;
		        }else{
					this.exceptionID =  pxml.getExceptionID();
					this.reason =  pxml.getReason();
		        	return null;
		        }
			}else{
				return null;
			}
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 查询我(当前用户)的会议列表
	 * @return
	 */
	public com.jitar2Infowarelab.model.Meetings listSummaryPrivateMeeting(){
		String xml =MessageXML.getMessageXML_listSummaryPrivateMeeting();
		log.info(xml);
		String serviceUrl = Utils.getInfowarelab_XmlServiceURL();
		log.info(serviceUrl);
		String responseXml = PostUtils.getResponsePostedXML(serviceUrl, xml);
		log.info(responseXml);
		ResponseXML pxml =new ResponseXML();
		try {
			boolean b = pxml.Parse(responseXml);
			if(b){
				this.result = pxml.isResult();
				if(pxml.isResult()){
					com.jitar2Infowarelab.model.Meetings ms = XmlUtil.toBean(pxml.getNakedBody(), com.jitar2Infowarelab.model.Meetings.class);
					return ms;
		        }else{
					this.exceptionID =  pxml.getExceptionID();
					this.reason =  pxml.getReason();
		        	return null;		        	
		        }
			}else{
				return null;
			}
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}		
	}	

	/**
	 * 获取启动会议Url
	 * @param confKey	会议key
	 * @return
	 */
	public String readStartMeetingURL(final String confKey){
		String webBaseUrl = Utils.getInfowarelab_ServerURL();
		String serviceUrl = Utils.getInfowarelab_XmlServiceURL();
		String xml =MessageXML.getMessageXML_readStartMeetingURL(confKey,webBaseUrl);
		log.info(xml);
		log.info(serviceUrl);
		String responseXml = PostUtils.getResponsePostedXML(serviceUrl, xml);
		log.info(responseXml);
		ResponseXML pxml =new ResponseXML();
		try {
			boolean b = pxml.Parse(responseXml);
			if(b){
				this.result = pxml.isResult();
				if(!pxml.isResult()){
					this.exceptionID =  pxml.getExceptionID();
					this.reason =  pxml.getReason();
					return null;
				}else{
					//返回的结果如下：
					//<body>
					//<confKey>76093592</confKey>
					//<url>http://192.168.16.203/meeting/app/hostJoin/index.action?confKey=76093592&amp;site=box&amp;pwd=2CA150E48BB79E83</url>
					//</body>
					String url = pxml.selectSingleNodeText(pxml.getBody(), "/body/url");
					return url;
				}
			}else{
				return null;
			}
		}catch (DocumentException e) {
			e.printStackTrace();
			return null;
		}		
	}

	/**
	 * 加入会议的url
	 * @param confKey 会议key
	 * @return
	 */
	public String readJoinMeetingURL(final String confKey){
		String webBaseUrl = Utils.getInfowarelab_ServerURL();
		String serviceUrl = Utils.getInfowarelab_XmlServiceURL();
		String xml =MessageXML.getMessageXML_readJoinMeetingURL(confKey,webBaseUrl);
		log.info(xml);
		log.info(serviceUrl);
		String responseXml = PostUtils.getResponsePostedXML(serviceUrl, xml);
		log.info(responseXml);
		ResponseXML pxml =new ResponseXML();
		try {
			boolean b = pxml.Parse(responseXml);
			if(b){
				this.result = pxml.isResult();
				if(!pxml.isResult()){
					this.exceptionID =  pxml.getExceptionID();
					this.reason =  pxml.getReason();
					return null;
				}else{
					String url = pxml.selectSingleNodeText(pxml.getBody(), "/body/url");
					return url;
				}
			}else{
				return null;
			}
		}catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}			
	}
	
	/**
	 * 读取会议启动信息
	 * @param hostName		主持人
	 * @param displayName	主持人的显示名称
	 * @param meetingKey	会议key
	 * @param meetingPwd	会议密码
	 * @param meetingEmail	会议email
	 * 
	 * @return 返回的是url和token，两者之间用竖线|分开
	 */
	public String startMeeting(final String hostName,final String displayName,final String meetingKey,final String meetingPwd,final String meetingEmail){
		String webBaseUrl = Utils.getInfowarelab_ServerURL();
		String serviceUrl = Utils.getInfowarelab_XmlServiceURL();
		String xml =MessageXML.getMessageXML_startMeeting(
				hostName,displayName,meetingKey,meetingPwd,meetingEmail,webBaseUrl);
		log.info(xml);
		log.info(serviceUrl);
		String responseXml = PostUtils.getResponsePostedXML(serviceUrl, xml);
		log.info(responseXml);
		ResponseXML pxml =new ResponseXML();
		try {
			boolean b = pxml.Parse(responseXml);
			if(b){
				this.result = pxml.isResult();
				if(!pxml.isResult()){
					this.exceptionID =  pxml.getExceptionID();
					this.reason =  pxml.getReason();
					return null;
				}else{
					String ciURL = pxml.selectSingleNodeText(pxml.getBody(), "/body/ciURL");
					String token = pxml.selectSingleNodeText(pxml.getBody(), "/body/token");
					return ciURL + "|" + token;
				}
			}else{
				return null;
			}
		}catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}		
	}

	/**
	 * 加入会议
	 * @param attendeeName		参会人
	 * @param confKey			会议key
	 * @param meetingPwd		会议密码
	 * @param meetingEmail		会议email
	 * @param serverName		会场
	 * @return  返回的是url和token，两者之间用竖线|分开
	 */
	public String joinMeeting(final String attendeeName,final String confKey,final String meetingPwd,final String meetingEmail,final String serverName ){
		String webBaseUrl = Utils.getInfowarelab_ServerURL();
		String serviceUrl = Utils.getInfowarelab_XmlServiceURL();
		String xml =MessageXML.getMessageXML_joinMeeting(
				attendeeName,confKey,meetingPwd,meetingEmail,serverName,webBaseUrl);
		log.info(xml);
		log.info(serviceUrl);
		String responseXml = PostUtils.getResponsePostedXML(serviceUrl, xml);
		log.info(responseXml);
		ResponseXML pxml =new ResponseXML();
		try {
			boolean b = pxml.Parse(responseXml);
			if(b){
				this.result = pxml.isResult();
				if(!pxml.isResult()){
					this.exceptionID =  pxml.getExceptionID();
					this.reason =  pxml.getReason();
					return null;
				}else{
					String ciURL = pxml.selectSingleNodeText(pxml.getBody(), "/body/ciURL");
					String token = pxml.selectSingleNodeText(pxml.getBody(), "/body/token");
					return ciURL + "|" + token;
				}
			}else{
				return null;
			}
		}catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}		
	}	

	/**
	 * 创建预约会议
	 * @param startDate  开始时间
	 * @param endDate	结束时间
	 * @param listAttendee	参加会议人员
	 * @param subject		会议主题
	 * @param hostName		主持人
	 * @param creator		创建者
	 * @param openType		是否公开
	 * @param passwd		会议密码
	 * @param beforehandTime	提前进入会议室
	 * @param agenda		会议描述
	 * @param attendeeAmount		参会人数
	 * @return	返回会议的关键信息
	 * @throws ParseException
	 */
	public CreatedMeeting createReserveMeeting(final Date startDate,final Date endDate,final List<Attendee> listAttendee,
			final String subject,final String hostName,String creator,final boolean openType,final String passwd,
			final int beforehandTime,final String agenda,int attendeeAmount ) throws ParseException{
		//用户
		creator = Utils.getInfowarelab_AdminUser();
		
		if(attendeeAmount == 0){
			if(null != listAttendee){
				attendeeAmount = listAttendee.size();
			}
		}
		String webBaseUrl = Utils.getInfowarelab_ServerURL();
		String serviceUrl = Utils.getInfowarelab_XmlServiceURL();
		
		String xml =MessageXML.getMessageXML_createReserveMeeting(subject,startDate,endDate,attendeeAmount,
				hostName,creator,openType,passwd,beforehandTime,webBaseUrl,agenda,listAttendee);
		log.info(xml);
		log.info(serviceUrl);
		String responseXml = PostUtils.getResponsePostedXML(serviceUrl, xml);
		log.info(responseXml);
		ResponseXML pxml =new ResponseXML();
		try {
			boolean b = pxml.Parse(responseXml);
			if(b){
				this.result = pxml.isResult();
				if(!pxml.isResult()){
					this.exceptionID =  pxml.getExceptionID();
					this.reason =  pxml.getReason();
					return null;
				}else{
					//创建成功返回
					//<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
					//<Message>
					//<header><result>SUCCESS</result></header>
					//<body>
					//<attendeeUrl>http://192.168.16.203/meeting/app/attendeeJoin/index.action?confKey=62885871&amp;site=box&amp;pwd=2CA150E48BB79E83</attendeeUrl>
					//<confId>100000000001022</confId>
					//<confKey>62885871</confKey>
					//<hostUrl>http://192.168.16.203/meeting/app/hostJoin/index.action?confKey=62885871&amp;site=box&amp;pwd=2CA150E48BB79E83</hostUrl>
					//</body>
					//</Message>
					
					String attendeeUrl = pxml.selectSingleNodeText(pxml.getBody(), "/body/attendeeUrl");
					String confId = pxml.selectSingleNodeText(pxml.getBody(), "/body/confId");
					String confKey = pxml.selectSingleNodeText(pxml.getBody(), "/body/confKey");
					String hostUrl = pxml.selectSingleNodeText(pxml.getBody(), "/body/hostUrl");
					
					CreatedMeeting m =  new CreatedMeeting();
					m.setAttendeeUrl(attendeeUrl);
					m.setConfId(confId);
					m.setConfKey(confKey);
					m.setHostUrl(hostUrl);
					return m;
				}
			}else{
				return null;
			}
		}catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}			
	}	

	/**
	 * 编辑预约会议
	 * @param confKey  会议的key
	 * @param startDate	开始时间
	 * @param endDate	结束时间
	 * @param listAttendee	参会者
	 * @param subject	会议主题
	 * @param hostName	主持人
	 * @param creator	创建者
	 * @param openType	是否公开
	 * @param passwd	会议密码
	 * @param beforehandTime	提前入会
	 * @param agenda		会议描述
	 * @param attendeeAmount		参会人数
	 * @return
	 * @throws ParseException
	 */
	public boolean updateReserveMeeting(final String confKey,final Date startDate,final Date endDate,final List<Attendee> listAttendee,
			final String subject,final String hostName,final String creator,final boolean openType,final String passwd,
			final int beforehandTime,final String agenda,int attendeeAmount)throws ParseException{
		if(attendeeAmount == 0){
			if(null != listAttendee){
				attendeeAmount = listAttendee.size();
			}
		}
		String webBaseUrl = Utils.getInfowarelab_ServerURL();
		String serviceUrl = Utils.getInfowarelab_XmlServiceURL();
		
		String xml =MessageXML.getMessageXML_updateReserveMeeting(confKey,subject,startDate,endDate,attendeeAmount,
				hostName,creator,openType,passwd,beforehandTime,webBaseUrl,agenda,listAttendee);
		log.info(xml);
		log.info(serviceUrl);
		
		String responseXml = PostUtils.getResponsePostedXML(serviceUrl, xml);
		log.info(responseXml);
		ResponseXML pxml =new ResponseXML();
		try {
			boolean b = pxml.Parse(responseXml);
			if(b){
				this.result = pxml.isResult();
				if(!pxml.isResult()){
					this.exceptionID =  pxml.getExceptionID();
					this.reason =  pxml.getReason();
					return false;
				}else{
					return true;
				}
			}else{
				return false;
			}
		}catch (DocumentException e) {
			e.printStackTrace();
			return false;
		}				
	}
	
	/**
	 * 创建固定会议
	 * 
	 * @param startDate  开始时间
	 * @param endDate	 结束时间
	 * @param subject	会议主题
	 * @param hostName	主持人
	 * @param openType	是否公开
	 * @param passwd	密码
	 * @param beforehandTime	提前入场时间
	 * @param agenda		会议描述
	 * @param attendeeAmount 参会人数
	 * @param listAttendee	参会人员
	 * @return	返回创建的会议信息
	 * @throws ParseException	
	 */
	public CreatedMeeting createFixedMeeting(final Date startDate,final Date endDate,
			final String subject,final String hostName ,final boolean openType,final String passwd,
			final int beforehandTime,final String agenda,int attendeeAmount,			
			final List<Attendee> listAttendee) throws ParseException{
		if( attendeeAmount == 0 ){
			if(null != listAttendee){
				attendeeAmount= listAttendee.size();
			}
		}
		String webBaseUrl = Utils.getInfowarelab_ServerURL();
		String serviceUrl = Utils.getInfowarelab_XmlServiceURL();
		
		String xml =MessageXML.getMessageXML_createFixedMeeting(subject,startDate,endDate,attendeeAmount,
				hostName,openType,passwd,beforehandTime,webBaseUrl,agenda,listAttendee);
		
		log.info(xml);
		log.info(serviceUrl);
		
		String responseXml = PostUtils.getResponsePostedXML(serviceUrl, xml);
		log.info(responseXml);
		ResponseXML pxml =new ResponseXML();
		try {
			boolean b = pxml.Parse(responseXml);
			if(b){
				this.result = pxml.isResult();
				if(!pxml.isResult()){
					this.exceptionID =  pxml.getExceptionID();
					this.reason =  pxml.getReason();
					return null;
				}else{
					//创建成功返回
					//<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
					//<Message>
					//<header><result>SUCCESS</result></header>
					//<body>
					//<attendeeUrl>http://192.168.16.203/meeting/app/attendeeJoin/index.action?confKey=80818892&amp;site=box&amp;pwd=2CA150E48BB79E83</attendeeUrl>
					//<confId>100000000001023</confId>
					//<confKey>80818892</confKey>
					//<hostUrl>http://192.168.16.203/meeting/app/hostJoin/index.action?confKey=80818892&amp;site=box&amp;pwd=2CA150E48BB79E83</hostUrl>
					//</body>
					//</Message>
					
					String attendeeUrl = pxml.selectSingleNodeText(pxml.getBody(), "/body/attendeeUrl");
					String confId = pxml.selectSingleNodeText(pxml.getBody(), "/body/confId");
					String confKey = pxml.selectSingleNodeText(pxml.getBody(), "/body/confKey");
					String hostUrl = pxml.selectSingleNodeText(pxml.getBody(), "/body/hostUrl");
					CreatedMeeting m =  new CreatedMeeting();
					m.setAttendeeUrl(attendeeUrl);
					m.setConfId(confId);
					m.setConfKey(confKey);
					m.setHostUrl(hostUrl);
					return m;
					
				}
			}else{
				return null;
			}
		}catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}			
	}	

	/**
	 * 查询会议状态（ 0 ：未开始 1：已开始 2：已结束）
	 * 
	 * @param confKey 会议的key
	 * 
	 * @return  0 ：未开始 1：已开始 2：已结束
	 */
	public String readMeetingStatus(final String confKey){
		String serviceUrl = Utils.getInfowarelab_XmlServiceURL();
		String xml =MessageXML.getMessageXML_readMeetingStatus(confKey);
		log.info(xml);
		log.info(serviceUrl);
		String responseXml = PostUtils.getResponsePostedXML(serviceUrl, xml);
		log.info(responseXml);
		ResponseXML pxml =new ResponseXML();
		try {
			boolean b = pxml.Parse(responseXml);
			if(b){
				this.result = pxml.isResult();
				if(!pxml.isResult()){
					this.exceptionID =  pxml.getExceptionID();
					this.reason =  pxml.getReason();
					return null;
				}else{
					//<body><confKey>80818892</confKey><status>0</status></body>
					//	0 ：未开始 1：已开始 2：已结束
					String status = pxml.selectSingleNodeText(pxml.getBody(), "/body/status");
					return status;
				}
			}else{
				return null;
			}
		}catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}		
	}	

	/**
	 * 查询历史会议
	 * @param startFrom  从第几条开始查  0 
	 * @param maximumNum 最大查询数量
	 * @param openType	是否公开
	 * @param startDate	开始日期
	 * @param endDate	结束日期
	 * @return 
	 * @throws ParseException
	 */
	public HistoryMeetingMulti listHistoryMeeting(final String startFrom,final int maximumNum,final boolean openType,
			final Date startDate,final Date endDate) throws ParseException{
		String serviceUrl = Utils.getInfowarelab_XmlServiceURL();
		String xml =MessageXML.getMessageXML_listHistoryMeeting(startFrom,maximumNum,openType,startDate,endDate);
		log.info(xml);
		log.info(serviceUrl);
		String responseXml = PostUtils.getResponsePostedXML(serviceUrl, xml);
		log.info(responseXml);
		ResponseXML pxml =new ResponseXML();
		try {
			boolean b = pxml.Parse(responseXml);
			if(b){
				this.result = pxml.isResult();
				if(!pxml.isResult()){
					this.exceptionID =  pxml.getExceptionID();
					this.reason =  pxml.getReason();
					return null;
				}else{
					//<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
					//<Message><header><result>SUCCESS</result></header>
					
					//<body>
					
					//<historyMeetings>
					
					//<historyMeeting>
					//<confId>100000000001018</confId>
					//<conferenceType>1</conferenceType>
					//<duration>752</duration>
					//<endTime>2013-10-29T03:13:27</endTime>
					//<hostName>admin</hostName>
					//<openType>true</openType>
					//<peak_attendee>3</peak_attendee>
					//<startTime>2013-10-29T03:00:55</startTime>
					//<subject>admin的会议</subject>
					//<total_attendee>4</total_attendee>
					//</historyMeeting>
					
					//<historyMeeting><confId>100000000001017</confId><conferenceType>0</conferenceType><duration>32456</duration><endTime>2013-10-29T10:10:43</endTime><hostName>admin</hostName><openType>true</openType><peak_attendee>1</peak_attendee><startTime>2013-10-29T01:09:47</startTime><subject>Test1</subject><total_attendee>1</total_attendee></historyMeeting>
					
					//</historyMeetings>
					//<returnNum>2</returnNum>
					//<startFrom>0</startFrom>
					//<totalNum>2</totalNum>
					//</body>
					
					//</Message>
					
					String historyMeetingsXml = pxml.selectSingleNodeXML(pxml.getBody(), "/body/historyMeetings");
					HistoryMeetings mms = XmlUtil.toBean(historyMeetingsXml,HistoryMeetings.class);
					String s_returnNum = pxml.selectSingleNodeText(pxml.getBody(), "/body/returnNum");
					String s_startFrom = pxml.selectSingleNodeText(pxml.getBody(), "/body/startFrom");
					String s_totalNum = pxml.selectSingleNodeText(pxml.getBody(), "/body/totalNum");
					
					int i_returnNum = 0;
					int i_startFrom = 0;
					int i_totalNum = 0;
					
					if(!Utils.isNullorBlank(s_returnNum)){i_returnNum = Integer.parseInt(s_returnNum);}
					if(!Utils.isNullorBlank(s_startFrom)){i_startFrom = Integer.parseInt(s_startFrom);}
					if(!Utils.isNullorBlank(s_totalNum)){i_totalNum = Integer.parseInt(s_totalNum);}
					
					HistoryMeetingMulti ms = new HistoryMeetingMulti();
					
					ms.setHistoryMeetings(mms.getHistoryMeetings());
					ms.setReturnNum(i_returnNum);
					ms.setStartFrom(i_startFrom);
					ms.setTotalNum(i_totalNum);
					
					return ms;
				}
			}else{
				return null;
			}
		}catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}			
	}	

	/**
	 * 删除视频会议
	 * @param confKey
	 * @return
	 */
	public boolean deleteMeeting(final String confKey){
		String serviceUrl = Utils.getInfowarelab_XmlServiceURL();
		String xml =MessageXML.getMessageXML_deleteMeeting(confKey,serviceUrl);
		log.info(xml);
		log.info(serviceUrl);
		String responseXml = PostUtils.getResponsePostedXML(serviceUrl, xml);
		log.info(responseXml);
		ResponseXML pxml =new ResponseXML();
		try {
			boolean b = pxml.Parse(responseXml);
			if(b){
				this.result = pxml.isResult();
				if(!pxml.isResult()){
					this.exceptionID =  pxml.getExceptionID();
					this.reason =  pxml.getReason();
					return false;
				}else{
					return true;
				}
			}else{
				return false;
			}
		}catch (DocumentException e) {
			e.printStackTrace();
			return false;
		}		
	}

	/**
	 * 返回用户的userId
	 * @param user
	 * @return 如果null,则有错误
	 */
	public String createCemUser(CemUser user){
		String serviceUrl = Utils.getInfowarelab_XmlServiceURL();
		String xml = MessageUserXML.getMessageXML_createCemUser(user);
		log.info(xml);
		log.info(serviceUrl);
		String responseXml = PostUtils.getResponsePostedXML(serviceUrl, xml);
		log.info(responseXml);
		ResponseXML pxml =new ResponseXML();
		try {
			boolean b = pxml.Parse(responseXml);
			if(b){
				this.result = pxml.isResult();
				if(!pxml.isResult()){
					this.exceptionID =  pxml.getExceptionID();
					this.reason =  pxml.getReason();
					return null;
				}else{
					//<userId>103</userId>
					//<userName>3rd015</userName>
					String userId = pxml.selectSingleNodeText(pxml.getBody(), "/body/userId");
					return userId;
				}
			}else{
				return null;
			}
		}catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}		
		
	}
	
	/**
	 * 更新用户
	 * @param user
	 * @return
	 */
	public boolean updateCemUser(CemUser user){
		String serviceUrl = Utils.getInfowarelab_XmlServiceURL();
		String xml = MessageUserXML.getMessageXML_updateCemUser(user);
		log.info(xml);
		log.info(serviceUrl);
		String responseXml = PostUtils.getResponsePostedXML(serviceUrl, xml);
		log.info(responseXml);
		ResponseXML pxml =new ResponseXML();
		try {
			boolean b = pxml.Parse(responseXml);
			if(b){
				this.result = pxml.isResult();
				if(!pxml.isResult()){
					this.exceptionID =  pxml.getExceptionID();
					this.reason =  pxml.getReason();
					return false;
				}else{
					return true;
				}
			}else{
				return false;
			}
		}catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}		
	}
	
	/**
	 * 删除用户 
	 * @param queryparam
	 * @return
	 */
	public boolean delCemUser(MeetingQueryParam queryparam){
		String serviceUrl = Utils.getInfowarelab_XmlServiceURL();
		String xml = MessageUserXML.getMessageXML_delCemUser(queryparam);
		log.info(xml);
		log.info(serviceUrl);
		String responseXml = PostUtils.getResponsePostedXML(serviceUrl, xml);
		log.info(responseXml);
		ResponseXML pxml =new ResponseXML();
		try {
			boolean b = pxml.Parse(responseXml);
			if(b){
				this.result = pxml.isResult();
				if(!pxml.isResult()){
					this.exceptionID =  pxml.getExceptionID();
					this.reason =  pxml.getReason();
					return false;
				}else{
					return true;
				}
			}else{
				return false;
			}
		}catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}			
	}
	
	/**
	 * 更新用户密码 
	 * @param queryparam
	 * @param password
	 * @return
	 */
	public boolean resetUserPassword(MeetingQueryParam queryparam,String password){
		String serviceUrl = Utils.getInfowarelab_XmlServiceURL();
		String xml = MessageUserXML.getMessageXML_resetUserPassword(queryparam,password);
		log.info(xml);
		log.info(serviceUrl);
		String responseXml = PostUtils.getResponsePostedXML(serviceUrl, xml);
		log.info(responseXml);
		ResponseXML pxml =new ResponseXML();
		try {
			boolean b = pxml.Parse(responseXml);
			if(b){
				this.result = pxml.isResult();
				if(!pxml.isResult()){
					this.exceptionID =  pxml.getExceptionID();
					this.reason =  pxml.getReason();
					return false;
				}else{
					return true;
				}
			}else{
				return false;
			}
		}catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}			
	}	
	
	/**
	 * 重新设置 密码 
	 * @param password
	 * @return
	 */
	public boolean changePassword(String password){
		String serviceUrl = Utils.getInfowarelab_XmlServiceURL();
		String xml = MessageUserXML.getMessageXML_changePassword(password);
		log.info(xml);
		log.info(serviceUrl);
		String responseXml = PostUtils.getResponsePostedXML(serviceUrl, xml);
		log.info(responseXml);
		ResponseXML pxml =new ResponseXML();
		try {
			boolean b = pxml.Parse(responseXml);
			if(b){
				this.result = pxml.isResult();
				if(!pxml.isResult()){
					this.exceptionID =  pxml.getExceptionID();
					this.reason =  pxml.getReason();
					return false;
				}else{
					return true;
				}
			}else{
				return false;
			}
		}catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}			
	}	
	
	/**
	 * 用户详细信息查询 
	 * @return
	 */
	public CemUsers getCemUserBatch(List<MeetingQueryParam> queryparams){
		String serviceUrl = Utils.getInfowarelab_XmlServiceURL();
		String xml = MessageUserXML.getMessageXML_getCemUserBatch(queryparams);
		log.info(xml);
		log.info(serviceUrl);
		String responseXml = PostUtils.getResponsePostedXML(serviceUrl, xml);
		log.info(responseXml);
		ResponseXML pxml =new ResponseXML();
		try {
			boolean b = pxml.Parse(responseXml);
			if(b){
				this.result = pxml.isResult();
				if(!pxml.isResult()){
					this.exceptionID =  pxml.getExceptionID();
					this.reason =  pxml.getReason();
					return null;
				}else{
					//<body><users><user>
					CemUsers us = XmlUtil.toBean(pxml.getNakedBody(), com.jitar2Infowarelab.model.CemUsers.class);
					return us;
				}
			}else{
				return null;
			}
		}catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}			
	}
}

