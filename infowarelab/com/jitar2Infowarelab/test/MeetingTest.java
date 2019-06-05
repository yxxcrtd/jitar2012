package com.jitar2Infowarelab.test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.dom4j.DocumentException;
import org.springframework.web.context.ContextLoader;

import cn.edustar.jitar.pojos.Meetings;
import cn.edustar.jitar.pojos.User;
import cn.edustar.jitar.service.UserService;

import com.jitar2Infowarelab.model.Attendee;
import com.jitar2Infowarelab.model.CemUser;
import com.jitar2Infowarelab.model.CemUsers;
import com.jitar2Infowarelab.model.HistoryMeetings;
import com.jitar2Infowarelab.model.HistoryMeetingMulti;
import com.jitar2Infowarelab.model.Meeting;
import com.jitar2Infowarelab.model.MeetingQueryParam;
import com.jitar2Infowarelab.service.InfowarelabService;
import com.jitar2Infowarelab.utils.MessageUserXML;
import com.jitar2Infowarelab.utils.MessageXML;
import com.jitar2Infowarelab.utils.PostUtils;
import com.jitar2Infowarelab.utils.ResponseXML;
import com.jitar2Infowarelab.utils.Utils;
import com.jitar2Infowarelab.utils.XmlUtil;

/**
 * 测试
 * @author dell
 *
 */
public class MeetingTest {
	
	private static String serviceUrl = "http://192.168.0.25/integration/xml";
	private static String webBaseUrl = "http://192.168.0.25";
	
	public static void main(String[] args) throws ParseException {
		
		String _serviceUrl = Utils.getInfowarelab_XmlServiceURL();
		if(!Utils.isNullorBlank(_serviceUrl)){
			serviceUrl = _serviceUrl;
		}
		String _webBaseUrl = Utils.getInfowarelab_ServerURL();
		if(!Utils.isNullorBlank(_webBaseUrl)){
			webBaseUrl = _webBaseUrl;
		}
		
		
		//查询会议测试 
		//listSummaryMeeting();

		//查询我的会议列表请
		//listSummaryPrivateMeeting();
		
		//获取启动会议Url
		//readStartMeetingURL();
		
		//加入会议的url
		//readJoinMeetingURL();
		
		//读取会议启动信息
		//startMeeting();
		
		//加入会议
		//joinMeeting();
		
		//创建预约会议
		//createReserveMeeting();
		
		//创建固定会议
		//createFixedMeeting();
		
		//查询会议状态
		//readMeetingStatus();
		
		//查询历史会议
		//listHistoryMeeting();
		//导入用户
		//UpdateUsers();
	}
	public static void UpdateUsers(){
		InfowarelabService infowarelabService = (InfowarelabService)ContextLoader.getCurrentWebApplicationContext().getBean("infowarelabService");
		UserService userService = (UserService)ContextLoader.getCurrentWebApplicationContext().getBean("userService");
		System.out.println("向视频会议同步用户信息");
		List<User> users = userService.getUserList();
		for(int i = 0; i < users.size() ; i++){
			User user = users.get(i);
    		List<MeetingQueryParam> queryparams = new ArrayList<MeetingQueryParam>();
    		MeetingQueryParam queryparam = new MeetingQueryParam();
    		queryparam.setType(1);
    		queryparam.setValue(user.getLoginName());
    		queryparams.add(queryparam);
    		CemUsers cemUsers = infowarelabService.getCemUserBatch(queryparams);
    		boolean exists = false;
    		if(null != cemUsers){
    			if(null != cemUsers.getUsers() && cemUsers.getUsers().size() > 0){
    				//更新用户
    				exists = true;
    				CemUser cemUser = cemUsers.getUsers().get(0);
            		String firstName = user.getTrueName().substring(0,1);
            		String lastName = firstName;
            		if(user.getTrueName().length() > 1) {
            			lastName = user.getTrueName().substring(1);
            		}
            		
            		cemUser.setEmail(user.getEmail());
            		cemUser.setNickname(user.getNickName());
            		cemUser.setUserName(user.getLoginName());
            		cemUser.setGender(user.getGender());
            		cemUser.setFirstName(user.getTrueName());
            		cemUser.setLastName(lastName);
            		cemUser.setPassword(user.getPassword());
            		boolean bupdateOk = infowarelabService.updateCemUser(cemUser);
            		if(!bupdateOk){
            			System.out.println("更新视频服务器用户信息出现错误:");
            			System.out.println(infowarelabService.getReason());
            		}
    			}
    		}
    		if(!exists){
    			//增加用户
        		String firstName = user.getTrueName().substring(0,1);
        		String lastName = firstName;
        		if(user.getTrueName().length() > 1) {
        			lastName = user.getTrueName().substring(1);
        		}
        		CemUser cemUser =  new CemUser();
        		cemUser.setAddress("");
        		cemUser.setCellphone("");
        		cemUser.setEmail(user.getEmail());
        		cemUser.setNickname(user.getNickName());
        		cemUser.setUserName(user.getLoginName());
        		cemUser.setGender(user.getGender());
        		cemUser.setPassword(user.getPassword());
        		cemUser.setFirstName(user.getTrueName());
        		cemUser.setLastName(lastName);
        		cemUser.setEnabled(true);
        		cemUser.setUserType(1);
        		cemUser.setForceCreate(true);
        		String cemUserId = infowarelabService.createCemUser(cemUser);
        		if(null == cemUserId){
        			//出错误了
        			System.out.println("用户注册到视频服务器出错:" + infowarelabService.getReason());
        		}    			
    		}
		}		
	}
	public static void listHistoryMeeting() throws ParseException{
		SimpleDateFormat bartDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");    
		Date startDate =  bartDateFormat.parse("2013-09-29 15:00:00");
		Date endDate =  bartDateFormat.parse("2013-11-30 18:30:00");		
		String xml =MessageXML.getMessageXML_listHistoryMeeting("0",100,true,startDate,endDate);
		System.out.println(xml);
		System.out.println("+++++++++++++++++++++++++++++++"+serviceUrl);
		String responseXml = PostUtils.getResponsePostedXML(serviceUrl, xml);
		System.out.println(responseXml);
		ResponseXML pxml =new ResponseXML();
		try {
			boolean b = pxml.Parse(responseXml);
			if(b){
				if(!pxml.isResult()){
					System.out.println("错误号:"+ pxml.getExceptionID());
					System.out.println("错误原因:"+ pxml.getReason());
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
					//</body></Message>
					
					String historyMeetingsXml = pxml.selectSingleNodeXML(pxml.getBody(), "/body/historyMeetings");
					System.out.println("----------------");
					System.out.println(historyMeetingsXml);
					HistoryMeetings mms = XmlUtil.toBean(historyMeetingsXml,HistoryMeetings.class);
					System.out.println("======3=======");
					String s_returnNum = pxml.selectSingleNodeText(pxml.getBody(), "/body/returnNum");
					String s_startFrom = pxml.selectSingleNodeText(pxml.getBody(), "/body/startFrom");
					String s_totalNum = pxml.selectSingleNodeText(pxml.getBody(), "/body/totalNum");
					
					int returnNum = 0;
					int startFrom = 0;
					int totalNum = 0;
					
					if(!Utils.isNullorBlank(s_returnNum)){returnNum = Integer.parseInt(s_returnNum);}
					if(!Utils.isNullorBlank(s_startFrom)){startFrom = Integer.parseInt(s_startFrom);}
					if(!Utils.isNullorBlank(s_totalNum)){totalNum = Integer.parseInt(s_totalNum);}
					
					HistoryMeetingMulti ms = new HistoryMeetingMulti();
					
					ms.setHistoryMeetings(mms.getHistoryMeetings());
					ms.setReturnNum(returnNum);
					ms.setStartFrom(startFrom);
					ms.setTotalNum(totalNum);
					
				}
			}
		}catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}			
	}
	
	public static void readMeetingStatus(){
		String xml =MessageXML.getMessageXML_readMeetingStatus("57451823");
		System.out.println(xml);
		System.out.println("+++++++++++++++++++++++++++++++");
		String responseXml = PostUtils.getResponsePostedXML(serviceUrl, xml);
		System.out.println(responseXml);
		ResponseXML pxml =new ResponseXML();
		try {
			boolean b = pxml.Parse(responseXml);
			if(b){
				if(!pxml.isResult()){
					System.out.println("错误号:"+ pxml.getExceptionID());
					System.out.println("错误原因:"+ pxml.getReason());
				}else{
					//<body><confKey>80818892</confKey><status>0</status></body>
					//	0 ：未开始 1：已开始 2：已结束
					String status = pxml.selectSingleNodeText(pxml.getBody(), "/body/status");
					System.out.println("status="+ status);
					
				}
			}
		}catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
	
	public static void createFixedMeeting() throws ParseException{
		SimpleDateFormat bartDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");    
		Date startDate =  bartDateFormat.parse("2013-10-29 15:00:00");
		Date endDate =  bartDateFormat.parse("2013-10-30 18:30:00");
		
		//组合参会人员
		
		List<Attendee> listAttendee = new ArrayList<Attendee>();
		
		Attendee e1 = new Attendee();
		e1.setName("admin");
		e1.setEmail("zhangsan@sian.com");
		listAttendee.add(e1);
		
		
		e1 = new Attendee();
		e1.setName("李四");
		e1.setEmail("lisi@sian.com");
		listAttendee.add(e1);
		
		
		int attendeeAmount= listAttendee.size();
		
		String xml =MessageXML.getMessageXML_createFixedMeeting("关于教研与教学的关系研讨2",startDate,endDate,attendeeAmount,
				"admin",true,"123456",15,webBaseUrl,"这是描述信息",listAttendee);
		System.out.println(xml);
		System.out.println("+++++++++++++++++++++++++++++++");
		String responseXml = PostUtils.getResponsePostedXML(serviceUrl, xml);
		System.out.println(responseXml);
		ResponseXML pxml =new ResponseXML();
		try {
			boolean b = pxml.Parse(responseXml);
			if(b){
				if(!pxml.isResult()){
					System.out.println("错误号:"+ pxml.getExceptionID());
					System.out.println("错误原因:"+ pxml.getReason());
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
					System.out.println("attendeeUrl="+ attendeeUrl);
					System.out.println("confId="+ confId);
					System.out.println("confKey="+ confKey);
					System.out.println("hostUrl="+ hostUrl);
					
				}
			}
		}catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}			
	}
	
	
	public static void createReserveMeeting() throws ParseException{
		SimpleDateFormat bartDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");    
		Date startDate =  bartDateFormat.parse("2013-10-29 15:00:00");
		Date endDate =  bartDateFormat.parse("2013-10-30 18:30:00");
		
		//组合参会人员
		
		List<Attendee> listAttendee = new ArrayList<Attendee>();
		
		Attendee e1 = new Attendee();
		e1.setName("admin");
		e1.setEmail("zhangsan@sian.com");
		listAttendee.add(e1);
		
		
		e1 = new Attendee();
		e1.setName("李四");
		e1.setEmail("lisi@sian.com");
		listAttendee.add(e1);
		
		
		int attendeeAmount= listAttendee.size();
		
		String xml =MessageXML.getMessageXML_createReserveMeeting("关于教研与教学的关系研讨",startDate,endDate,attendeeAmount,
				"admin","admin",true,"123456",15,webBaseUrl,"这是描述信息",listAttendee);
		System.out.println(xml);
		System.out.println("+++++++++++++++++++++++++++++++");
		String responseXml = PostUtils.getResponsePostedXML(serviceUrl, xml);
		System.out.println(responseXml);
		ResponseXML pxml =new ResponseXML();
		try {
			boolean b = pxml.Parse(responseXml);
			if(b){
				if(!pxml.isResult()){
					System.out.println("错误号:"+ pxml.getExceptionID());
					System.out.println("错误原因:"+ pxml.getReason());
				}else{
					//创建成功返回
					//<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
					//<Message><header><result>SUCCESS</result></header>
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
					System.out.println("attendeeUrl="+ attendeeUrl);
					System.out.println("confId="+ confId);
					System.out.println("confKey="+ confKey);
					System.out.println("hostUrl="+ hostUrl);
					
				}
			}
		}catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}			
	}
	
	public static void joinMeeting(){
		String xml =MessageXML.getMessageXML_joinMeeting(
				"张三","57451823","123456","admin@sina.com","servername",webBaseUrl);
		System.out.println(xml);
		System.out.println("+++++++++++++++++++++++++++++++");
		String responseXml = PostUtils.getResponsePostedXML(serviceUrl, xml);
		System.out.println(responseXml);
		ResponseXML pxml =new ResponseXML();
		try {
			boolean b = pxml.Parse(responseXml);
			if(b){
				if(!pxml.isResult()){
					System.out.println("错误号:"+ pxml.getExceptionID());
					System.out.println("错误原因:"+ pxml.getReason());
				}else{
					String ciURL = pxml.selectSingleNodeText(pxml.getBody(), "/body/ciURL");
					String token = pxml.selectSingleNodeText(pxml.getBody(), "/body/token");
					System.out.println("ciURL="+ ciURL);
					System.out.println("token="+ token);
				}
			}
		}catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
	
	public static void startMeeting(){
		String xml =MessageXML.getMessageXML_startMeeting(
				"admin","管理员","57451823","123456","admin@sina.com",webBaseUrl);
		System.out.println(xml);
		System.out.println("+++++++++++++++++++++++++++++++");
		String responseXml = PostUtils.getResponsePostedXML(serviceUrl, xml);
		System.out.println(responseXml);
		ResponseXML pxml =new ResponseXML();
		try {
			boolean b = pxml.Parse(responseXml);
			if(b){
				if(!pxml.isResult()){
					System.out.println("错误号:"+ pxml.getExceptionID());
					System.out.println("错误原因:"+ pxml.getReason());
				}else{
					String ciURL = pxml.selectSingleNodeText(pxml.getBody(), "/body/ciURL");
					String token = pxml.selectSingleNodeText(pxml.getBody(), "/body/token");
					System.out.println("ciURL="+ ciURL);
					System.out.println("token="+ token);
				}
			}
		}catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
	
	public static void readJoinMeetingURL(){
		String xml =MessageXML.getMessageXML_readJoinMeetingURL("57451823",webBaseUrl);
		System.out.println(xml);
		System.out.println("+++++++++++++++++++++++++++++++");
		String responseXml = PostUtils.getResponsePostedXML(serviceUrl, xml);
		System.out.println(responseXml);
		ResponseXML pxml =new ResponseXML();
		try {
			boolean b = pxml.Parse(responseXml);
			if(b){
				if(!pxml.isResult()){
					System.out.println("错误号:"+ pxml.getExceptionID());
					System.out.println("错误原因:"+ pxml.getReason());
				}else{
					String url = pxml.selectSingleNodeText(pxml.getBody(), "/body/url");
					System.out.println("参加会议的地址Url是:" + url);
				}
			}
		}catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}			
	}
	
	public static void readStartMeetingURL(){
		String xml =MessageXML.getMessageXML_readStartMeetingURL("76093592",webBaseUrl);
		System.out.println(xml);
		System.out.println("+++++++++++++++++++++++++++++++");
		String responseXml = PostUtils.getResponsePostedXML(serviceUrl, xml);
		System.out.println(responseXml);
		ResponseXML pxml =new ResponseXML();
		try {
			boolean b = pxml.Parse(responseXml);
			if(b){
				if(!pxml.isResult()){
					System.out.println("错误号:"+ pxml.getExceptionID());
					System.out.println("错误原因:"+ pxml.getReason());
				}else{
					//<body>
					//<confKey>76093592</confKey>
					//<url>http://192.168.16.203/meeting/app/hostJoin/index.action?confKey=76093592&amp;site=box&amp;pwd=2CA150E48BB79E83</url>
					//</body>
					
					String url = pxml.selectSingleNodeText(pxml.getBody(), "/body/url");
					System.out.println("会议启动的地址Url是:" + url);
				}
			}
		}catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
	
	public static void listSummaryPrivateMeeting(){
		//查询我的会议列表
		System.out.println("------------------------------");
		String xml =MessageXML.getMessageXML_listSummaryPrivateMeeting();
		System.out.println(xml);
		System.out.println("+++++++++++++++++++++++++++++++");
		String responseXml = PostUtils.getResponsePostedXML(serviceUrl, xml);
		System.out.println(responseXml);
		
		ResponseXML pxml =new ResponseXML();
		try {
			boolean b = pxml.Parse(responseXml);
			if(b){
				System.out.println(pxml.isResult());
				System.out.println("***********************显示会议信息************************");
				System.out.println(pxml.getNakedBody());
				if(pxml.isResult()){
					com.jitar2Infowarelab.model.Meetings ms = XmlUtil.toBean(pxml.getNakedBody(), com.jitar2Infowarelab.model.Meetings.class);
		            for(Iterator it=ms.getMeetings().iterator();it.hasNext();){
		            	Meeting meeting = (Meeting) it.next();
		            	System.out.println("会议key:"+meeting.getConfKey());
		            	System.out.println("主持人:"+meeting.getHostName());		            	
		            	System.out.println("主题:"+meeting.getSubject());
		            	System.out.println("开始时间:"+meeting.getStartTime());
		            	System.out.println("结束时间:"+meeting.getEndTime());
		            	System.out.println("状态:"+meeting.getStatus());
		            	System.out.println("****************");
		             }
		        }
			}
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
	public static void listSummaryMeeting() throws ParseException{
		//查询某时间段的会议列表
		SimpleDateFormat bartDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");    
		Date startDate =  bartDateFormat.parse("2013-08-12 12:00:00");
		Date endDate =  bartDateFormat.parse("2013-11-29 18:30:00");
		System.out.println("------------------------------");
		String xml = MessageXML.getMessageXML_listSummaryMeeting(startDate, endDate);
		System.out.println(xml);
		System.out.println("+++++++++++++++++++++++++++++++");
		String responseXml = PostUtils.getResponsePostedXML(serviceUrl, xml);
		System.out.println(responseXml);
		
		//转换为对象看看
		//http://www.oschina.net/code/snippet_116183_14202
		ResponseXML pxml =new ResponseXML();
		try {
			boolean b = pxml.Parse(responseXml);
			if(b){
				System.out.println(pxml.isResult());
				System.out.println("***********************显示会议信息************************");
				System.out.println(pxml.getNakedBody());
				if(pxml.isResult()){
					com.jitar2Infowarelab.model.Meetings ms = XmlUtil.toBean(pxml.getNakedBody(), com.jitar2Infowarelab.model.Meetings.class);
		            for(Iterator it=ms.getMeetings().iterator();it.hasNext();){
		            	Meeting meeting = (Meeting) it.next();
		            	System.out.println("会议key:"+meeting.getConfKey());
		            	System.out.println("主持人:"+meeting.getHostName());		            	
		            	System.out.println("主题:"+meeting.getSubject());
		            	System.out.println("开始时间:"+meeting.getStartTime());
		            	System.out.println("结束时间:"+meeting.getEndTime());
		            	System.out.println("状态:"+meeting.getStatus());
		            	System.out.println("****************");
		             }
		        }
			}
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

}
