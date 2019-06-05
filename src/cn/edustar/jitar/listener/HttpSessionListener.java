package cn.edustar.jitar.listener;

//import java.text.SimpleDateFormat;
//import java.util.Locale;

import java.util.Map;

import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
//东师理想的jar
import com.dsideal.ws.shareclient.impl.DataEXResultSet;
import com.dsideal.ws.shareclient.impl.DataEXToken;
import com.dsideal.ws.shareclient.impl.WSDataEXShare;
import com.dsideal.ws.shareclient.impl.DataEXQueryRequest;
import com.dsideal.ws.shareclient.impl.DataEXQueryResult;

import javax.servlet.ServletContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;
import org.jasig.cas.client.authentication.AttributePrincipal;
import org.jasig.cas.client.validation.AssertionImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import cn.edustar.jitar.pojos.Unit;
import cn.edustar.jitar.pojos.User;
import cn.edustar.jitar.service.UnitService;
//import cn.edustar.jitar.pojos.UserOnLine;
//import cn.edustar.jitar.service.OnLineService;
import cn.edustar.jitar.service.UserService;

/**
 * 监听 CAS 中 session 的创建和删除，自动根据单点登录系统的信息设置本系统的用户信息
 */
public class HttpSessionListener implements HttpSessionAttributeListener {
	Logger log = LoggerFactory.getLogger(HttpSessionListener.class);
	
	public void attributeAdded(HttpSessionBindingEvent e) {
//		System.out.println("HttpSessionBindingEvent：" + e.getName());
		
		// _const_cas_assertion_是CAS中存放登录用户名的session标志
		if ("_const_cas_assertion_".equals(e.getName())) {
			Object obj = e.getValue();
			if ((obj != null) && (obj instanceof AssertionImpl)) {
				AssertionImpl assertion = (AssertionImpl) obj;
				String ssoId = assertion.getPrincipal().getName();
				log.info("当前登录的用户名：" + ssoId);
				System.out.println("当前登录的用户名：" + ssoId);
				User user = null;
				ServletContext sc = e.getSession().getServletContext();
				ApplicationContext ac = WebApplicationContextUtils.getWebApplicationContext(sc);
				UserService userService = (UserService) ac.getBean("userService");
//				OnLineService onlineService = (OnLineService) ac.getBean("onlineService");
				user = userService.getUserByLoginName(ssoId);

				if(null != sc.getFilterRegistration("filterchainproxy")){
					//东师理想的 
					String className = sc.getFilterRegistration("filterchainproxy").getClassName();
					//需要继续判断？？ if(className.equals("dsidealsso.FilterChainProxy")){
					String userid= "";
					String sso_userid= ""; 
					String username = ssoId;
					String orgid = "";
					String deptid = ""; 
					String realname = ssoId;
				 
					AttributePrincipal attributePrincipal = (AttributePrincipal)assertion.getPrincipal();
					if(null != attributePrincipal){
						Map <String,Object>map = attributePrincipal.getAttributes();
						if( null !=map ){
							/*
							System.out.println("检查map中信息......map.size()="+map.size());
							for(int i=0;i<map.size();i++){
								 Set<String> key = map.keySet();
							        for (Iterator it = key.iterator(); it.hasNext();) {
							            String s = (String) it.next();
							            System.out.println("key:"+ s +"   =   "+map.get(s));
							        }
							}*/
							
							if( null !=map.get("userid")){
								userid= String.valueOf(map.get("userid"));
							}else{
								System.out.println("map.get(userid) = null ");
							}
							if( null !=map.get("sso_userid")){
								sso_userid= String.valueOf(map.get("sso_userid"));
							}else{
								System.out.println("map.get(sso_userid) = null ");
							}
							if( null !=map.get("username")){
								username = String.valueOf(map.get("username"));
							}else{
								System.out.println("map.get(username) = null ");
							}
							if( null !=map.get("orgid")){
								orgid = String.valueOf(map.get("orgid"));
							}else{
								System.out.println("map.get(orgid) = null ");
							}
							if( null !=map.get("deptid")){
								deptid = String.valueOf(map.get("deptid"));
							}else{
								System.out.println("map.get(deptid) = null ");
							}
							if( null !=map.get("realname")){
								realname = String.valueOf(map.get("realname"));
							}else{
								System.out.println("map.get(realname) = null ");
							}
						}else{
							System.out.println("attributePrincipal.getAttributes() MAP = null ");
						}
					}else{
						System.out.println("attributePrincipal = null ");
					}
				
					Unit unit = getUserSchoolInfo(sc,orgid);
					if (null == user) {
						if (null == unit){
							//单位为空，不允许增加用户
							throw new RuntimeException("单位为空，不允许增加用户");
						}
						user = new User();
						user.setAccountId(sso_userid);
						user.setLoginName(ssoId);
						user.setUserStatus(0);
						user.setUnitId(unit.getUnitId());
						user.setUsn(1);
						user.setPositionId(3);
						user.setVirtualDirectory("u");
						user.setUserFileFolder(ssoId);
						user.setBlogName(realname + " 的博客");
						user.setUnitPathInfo("/1/"+unit.getUnitId()+"/");
						user.setTrueName(realname);
						user.setNickName(realname);
						userService.createUser(user);
						System.out.println("本地用户：" + user.getLoginName() + "，创建成功！");
						log.info("本地用户：" + user.getLoginName() + "，创建成功！");
					}else{
						//Unit unit = getUserSchoolInfo(sc,orgid);
						user.setAccountId(sso_userid);
						//user.setLoginName(ssoId);
						//user.setUserStatus(0);
						//user.setUnitId(unit.getUnitId());
						//user.setUsn(1);
						//user.setPositionId(3);
						//user.setVirtualDirectory("u");
						//user.setUserFileFolder(ssoId);
						//user.setBlogName(realname + " 的博客");
						//user.setUnitPathInfo("/1/"+unit.getUnitId()+"/");
						user.setTrueName(realname);
						user.setNickName(realname);
						userService.updateUser(user);
					}
				}else if (null != sc.getFilterRegistration("CAS-Authentication-Filter")){
					//中教启星
					if (null == user) {
						user = new User();
						user.setLoginName(ssoId);
						user.setUserStatus(1);
						user.setTrueName(ssoId);
						user.setUsn(1);
						user.setPositionId(3);
						user.setVirtualDirectory("u");
						user.setUserFileFolder(ssoId);
						user.setBlogName(ssoId + " 的博客");
						userService.createUser(user);
						log.info("本地用户：" + user.getLoginName() + "，创建成功！");
					}
										
				}
				e.getSession().setAttribute(User.SESSION_LOGIN_NAME_KEY, ssoId);
				e.getSession().setAttribute(User.SESSION_LOGIN_USERMODEL_KEY, user);
				log.info("Session 设置成功！");

//				long nowTime = System.currentTimeMillis();
//				SimpleDateFormat sdf = new SimpleDateFormat("", Locale.SIMPLIFIED_CHINESE);
//				sdf.applyPattern("yyyy年MM月dd日 HH时mm分ss秒");
//				log.info("添加或更新在线用户：用户Id:" + user.getUserId() + ",登录名:" + user.getLoginName() + ",当前登录时间:" + nowTime + "[" + sdf.format(Long.valueOf(nowTime)) + "]");
//				UserOnLine userOnLine = new UserOnLine();
//				userOnLine.setUserId(Integer.valueOf(user.getUserId()));
//				userOnLine.setUserName(user.getLoginName());
//				userOnLine.setOnlineTime(nowTime);
//				onlineService.saveUserOnLine(userOnLine);
				
				
			}
		}
	}
	@SuppressWarnings("unused")
	private Unit getUserSchoolInfo(ServletContext sc,String orgid){
		ApplicationContext ac = WebApplicationContextUtils.getWebApplicationContext(sc);
		UnitService unitService  = (UnitService) ac.getBean("unitService");
		String WS_DataEX_ShareImplService = sc.getInitParameter("WS_DataEX_ShareImplService");
		if(null == orgid || orgid.length() == 0){
			return null;
		}
		if(null == WS_DataEX_ShareImplService){
			return null;
		}else if(WS_DataEX_ShareImplService.length() == 0){
			return null;
		}else { 
			/*
	        ClientProxyFactoryBean factory = new ClientProxyFactoryBean();
	        String serviceURL = WS_DataEX_ShareImplService;
	        factory.setServiceName(new QName("http://dataEX_share", "loginUserInfo"));
	        factory.setAddress(serviceURL);
	        factory.setWsdlURL(serviceURL + "?wsdl");
	        factory.getServiceFactory().setDataBinding(new AegisDatabinding());
	        */
			
			System.out.println("查询用户的单位信息"+WS_DataEX_ShareImplService);
			
			//新建一个查询请求
			DataEXQueryRequest queryRequest = new DataEXQueryRequest();
			
			//创建web服务代理工厂
			JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
			//设置要调用的web服务服务端发布地址
			factory.setAddress(WS_DataEX_ShareImplService);
			//设置要调用的web服务
			factory.setServiceClass(WSDataEXShare.class); 
			//生成web服务接口对象
			WSDataEXShare share = (WSDataEXShare)factory.create();
			//初始化登陆信息
			//DataEXToken dataEX_Token = new DataEXToken();
			//dataEX_Token.setLoginTime(logintime);
			//dataEX_Token.setUserName(username);
			//dataEX_Token.setUserToken(share.login(request.getParameter("username"),request.getParameter("password"), request.getParameter("logintime")));
			//queryRequest.setDataEXToken(dataEX_Token);
			
			//初始化登陆信息
			DataEXToken dataEX_Token = new DataEXToken();
			dataEX_Token.setUserToken("bmm0OM/w+2CHr6mdZSkB4w==");
			dataEX_Token.setLoginTime("2013-05-07");
			dataEX_Token.setUserName("ZJYX");
			queryRequest.setDataEXToken(dataEX_Token);
			
			//初始化查询的表名
			queryRequest.setDataEXQueryObject("T_BASE_ORGTREE");
			//初始化查询条件
			queryRequest.setDataEXQueryCondition("where ID='"+orgid+"'");

			System.out.println("查询t_base_orgtree where ID='"+orgid+"'");

			//调用查询操作   
			if( null == queryRequest){
				throw new RuntimeException("queryRequest is null");
			}
			DataEXQueryResult result = share.queryData(queryRequest,0);
			DataEXResultSet dset = result.getDataEXResultSet();
			String unitIDChar = null;
			String unitTitle = null;
			
			System.out.println("得到的单位信息" + "="+dset.toString());
			String ID_ZJYX="";
			String parentID_ZJYX="";
			if(null !=dset.getDataResult().getDataRow().get(0)){
				ID_ZJYX= dset.getDataResult().getDataRow().get(0).getData().get(0);
				parentID_ZJYX = dset.getDataResult().getDataRow().get(0).getData().get(1);
				unitIDChar = dset.getDataResult().getDataRow().get(0).getData().get(2);
				unitTitle = dset.getDataResult().getDataRow().get(0).getData().get(3);
				System.out.println("ID_ZJYX=="+ID_ZJYX);
				System.out.println("parentID_ZJYX=="+parentID_ZJYX);
				System.out.println("unitIDChar=="+unitIDChar);
				System.out.println("unitTitle=="+unitTitle);
			}else{
				System.out.println("单位信息dset.getDataResult().getDataRow().get(0)==null!");
			}
			
			if(null == unitIDChar || unitIDChar.length() == 0){
				return null;
			}else if( null == unitTitle || unitTitle.length() == 0){
				return null;
			}else{
				Unit unit = null;
				if(ID_ZJYX != null && ID_ZJYX.length() > 0 && (!ID_ZJYX.equals("0"))){
					unit = unitService.getUnitById(Integer.parseInt(ID_ZJYX));
					if(unit !=null){
						System.out.println("找到了单位 =="+unit.getUnitTitle());
					}else{
						System.out.println("没找到单位: ID_ZJYX="+ID_ZJYX);
					}
				}
				if(unit ==null) {
					String unitName = "unit_"+unitIDChar;
					unit = unitService.getUnitByName(unitName);
					if( null == unit){
						unit = new Unit();
						unit.setUnitName(unitName);
						unit.setUnitTitle(unitTitle);
						unit.setSiteTitle(unitTitle);
						int parentId = 1;
						if(parentID_ZJYX != null && parentID_ZJYX.length() > 0){
							parentId = Integer.parseInt(parentID_ZJYX);
						}
						unit.setParentId(parentId);
						unit.setUnitPathInfo("");
						unit.setHasChild(false);
						unitService.saveOrUpdateUnit(unit);
					}else{
						unit.setUnitTitle(unitTitle);
						unit.setSiteTitle(unitTitle);
						unitService.saveOrUpdateUnit(unit);
					}
				}else{
					unit.setUnitTitle(unitTitle);
					unit.setSiteTitle(unitTitle);
					unitService.saveOrUpdateUnit(unit);
				}
				return unit;
			}
		}
	}
	public void attributeRemoved(HttpSessionBindingEvent arg0) {
//		System.out.println("Imcoming......");
		
	}

	public void attributeReplaced(HttpSessionBindingEvent arg0) {
//		System.out.println("Nothing to do ...");
	}
	
	@SuppressWarnings("unused")
	private static final String getClientTicket(HttpServletRequest request) {
		String userTicket = "";
		Cookie[] cookies = request.getCookies();
		if (null != cookies) {
			for (Cookie cookie : cookies) {
				if ("UserTicket".equals(cookie.getName())) {
					userTicket = cookie.getValue();
					break;
				}
			}
		}
		
		return null == userTicket ? "" : userTicket;
	}

}
