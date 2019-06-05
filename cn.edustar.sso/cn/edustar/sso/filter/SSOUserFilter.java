package cn.edustar.sso.filter;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Date;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import cn.edustar.jitar.pojos.AccessControl;
import cn.edustar.jitar.pojos.Unit;
import cn.edustar.jitar.pojos.User;
import cn.edustar.jitar.service.AccessControlService;
import cn.edustar.jitar.service.UserDeletedService;
import cn.edustar.jitar.util.ParamUtil;

import com.alibaba.fastjson.JSON;
import com.caucho.hessian.client.HessianProxyFactory;
import com.octopus.system.service.UnitManageService;
/**
 * 
 * 得到用户服务器返回的票证，根据票证得到当前登录的用户信息，设置教研系统为登录状态
 * 
 * @author baimindong
 *
 */
public class SSOUserFilter  implements Filter {
	public final void doFilter(final ServletRequest servletRequest, 
			final ServletResponse servletResponse, final FilterChain filterChain) 
					throws IOException, ServletException {
        final HttpServletRequest request = (HttpServletRequest) servletRequest;
        final HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpSession session = request.getSession(true);
        String reciverAction=request.getServletContext().getFilterRegistration("ssoFilter").getInitParameter("reciverAction");

        String SSOServerURL = request.getServletContext().getInitParameter("SSOServerURL");
        String SSOServerURL1 = SSOServerURL;
        String SSOServerURL2 = SSOServerURL;
        if(SSOServerURL.indexOf(";")>-1){
        	String[] arrayUrl = SSOServerURL.split("\\;");
        	SSOServerURL1 = arrayUrl[0];
        	SSOServerURL2 = arrayUrl[1];
        }        
        if(SSOServerURL1.endsWith("/")){
        	SSOServerURL1 = SSOServerURL1.substring(0, SSOServerURL1.length()-1);
        }
        if(SSOServerURL2.endsWith("/")){
        	SSOServerURL2 = SSOServerURL2.substring(0, SSOServerURL2.length()-1);
        }        
        String basePath ="";
        String contextPath = request.getContextPath();
        if(request.getServerPort() == 80){
        	basePath = request.getScheme() + "://" + request.getServerName() + contextPath + "/";
        }else{
        	basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + contextPath + "/";
        }
        
        //对教研的返回 地址，加上 http://**
        if(!reciverAction.startsWith("http://")){
        	if(reciverAction.startsWith("/")){
        		reciverAction = reciverAction.substring(1);
        	}
        	reciverAction = basePath + reciverAction;
        }
        
        //System.out.println("SSOUserFilter当前过滤的地址是 =" + request.getRequestURI());

        //如果带有fromServer参数是从用户服务器转向过来的 
        if(null != request.getParameter("fromServer") && request.getParameter("fromServer").equals("1")){
        	session.setAttribute("fromServer", 1);
        	filterChain.doFilter(request, response);
        	return;
        }
        //如果带有returnFlag参数是从用户服务器转向过来的   returnFlag = 2 没权限访问本系统
        if(null != request.getParameter("returnFlag")){
        	 if(request.getParameter("returnFlag").length()>0){
	        	session.setAttribute("fromServer", 1);
	        	filterChain.doFilter(request, response);
        	 }
        	return;
        }
        String ticket = null;
        ParamUtil param = new ParamUtil(request);
        if(!param.existParam("ticket")){
            filterChain.doFilter(request, response);
            return;            
        }
        else{
            ticket = param.getParam("ticket");
            if(ticket == null || ticket.length() == 0) {
                //没有票证,是否继续去服务器验证
                session.setAttribute("fromServer", 1);
                filterChain.doFilter(request, response);
                return;
            }
        }      
        
    	
    	//System.out.println("用户票证：" + ticket);
    	
    	//验证票证,得到用户信息
    	String userServiceUrl = SSOServerURL2 + request.getServletContext().getFilterRegistration("ssoUserFilter").getInitParameter("userServiceUrl");
        com.octopus.sso.model.User ssoUser = null;
        try {
        	ssoUser = getSSOUser(userServiceUrl,reciverAction,ticket);
        	if(null != ssoUser){
        		//System.out.println("user=" + ssoUser.getTrueName());
        	}else{
        		//可能用户已经删除？？？？/
            	filterChain.doFilter(request, response);
            	return;
        	}
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("传输错误");
            return;
        }
        
        String unitManageServiceUrl = SSOServerURL2 + request.getServletContext().getFilterRegistration("ssoUserFilter").getInitParameter("unitManageServiceUrl");
        
        //到教研的库里查查该用户信息
        cn.edustar.jitar.pojos.User user = null;
    	ApplicationContext ac = WebApplicationContextUtils.getWebApplicationContext(request.getServletContext());   
    	cn.edustar.jitar.service.UserService userservice = (cn.edustar.jitar.service.UserService) ac.getBean("userService");
    	cn.edustar.jitar.service.UnitService unitservice = (cn.edustar.jitar.service.UnitService) ac.getBean("unitService");
    	UserDeletedService userdeletedService = (UserDeletedService) ac.getBean("userDeletedService");
    	user = userservice.getUserByLoginName(ssoUser.getUserName());
    	if(null == user) {
    		if(null != userdeletedService.getByUserGuid(ssoUser.getPassportId())){
    			//该用户曾经被教研管理员删除了。放弃他。
    			session.setAttribute("ssoUserDeleted", 1);
    	    	filterChain.doFilter(request, response);
    	    	return;
    		}
    		if(null != userdeletedService.getByUserLoginName(ssoUser.getUserName())){
    			//该用户曾经被教研管理员删除了。放弃他。
    			session.setAttribute("ssoUserDeleted", 1);
    	    	filterChain.doFilter(request, response);
    	    	return;
    		}    		
    		//检查单位是否存在
    		Integer unitId = ssoUser.getUnitId();
    		Unit unit = unitservice.getUnitById(unitId);
    		if(null == unit){
    		    response.sendRedirect(basePath + "nounit.jsp?unitId=" + unitId);
    		    return;
    		    //这么添加可能会导致机构混乱，应该是统一从基础数据里面提取，目前先不实现吧 
    		    
    			/*//需要从服务器上得到单位信息？
    			com.octopus.system.model.Unit ssoUnit = getSSOUnit(unitManageServiceUrl,unitId);
    			if(null != ssoUnit){
    				unit = new Unit();
    				unit.setUnitId(ssoUnit.getUnitId());
    				unit.setUnitName(ssoUnit.getUnitEName());
    				unit.setUnitTitle(ssoUnit.getUnitName());
    				unit.setSiteTitle(ssoUnit.getWebSiteName());
    				unit.setUnitPathInfo(ssoUnit.getUnitPath());
    				//ssoUnit.getParentUnitCode();
    				//unit.setUnitGuid(ssoUnit.getUnitCode());
    				//创建单位
    				unitservice.saveOrUpdateUnit(unit);
    				
    			}else{
    				throw new java.lang.NullPointerException("单位服务器上没有unitId="+unitId+"的单位信息");
    			}*/
    		}
    		
    		//用户不存在，则需要增加到库里,密码在哪里设置？
    		
    		user = new cn.edustar.jitar.pojos.User();
    		user.setLoginName(ssoUser.getUserName());
    		user.setTrueName(ssoUser.getTrueName());
    		user.setNickName(ssoUser.getNickName());
    		user.setEmail(ssoUser.getEmail());
    		user.setUserIcon(ssoUser.getUserIcon());   		
    		
    		/*
    		if(ssoUser.getBase64UserIcon()!=null){
    			if(ssoUser.getBase64UserIcon().length()>0){
    				byte[] imageData = new sun.misc.BASE64Decoder().decodeBuffer(ssoUser.getBase64UserIcon().trim());
    				String userIcon = cn.edustar.jitar.util.PicProcessUtil.cutImage(imageData, 180, 180, ssoUser.getUserIcon(), "1");
    				//需要加上 /images/headImg
    				userIcon = "/images/headImg/"+userIcon;
    				user.setUserIcon(userIcon);
    			}
    		}
    		*/
    		
    		if(null != ssoUser.getGender()){
    			user.setGender(Short.parseShort(ssoUser.getGender().toString()));
    		}
    		user.setUnitId(unit.getUnitId());   		//设置单位Id
    		user.setUnitPathInfo(unit.getUnitPathInfo()); 		//
    		//ssoUser.getPassword();
    		if(ssoUser.getUserType()!=null){
    			if(ssoUser.getUserType().equals("学生") || ssoUser.getUserType().equals("1")){
    				user.setPositionId(5);
    			}else if(ssoUser.getUserType().equals("教师") || ssoUser.getUserType().equals("老师") || ssoUser.getUserType().equals("2")){
    				user.setPositionId(3);
    			}
    		}
    		user.setUserGuid(ssoUser.getPassportId());
    		//user.setUserStatus(ssoUser.getUserStatus());  //用户登录的时候，其状态信息不允许更新。和用户服务器的状态时两回事
    		user.setUserStatus(User.USER_STATUS_NORMAL);
   		    user.setCreateDate(new Date());
    		user.setVersion(ssoUser.getVersion());
    		userservice.createUser(user);   //增加用户  		
    		
    		//第一个用户设置为管理员？
    		//此方法在系统运行时是执行一次的，所以，第一个用户的判断可以使用此方法，每次都判断不是一个好的方法，目前也没有简单的做法啊。    		
    		int count = userservice.getUserCount();
    		if(count < 2){
    		    AccessControlService accessControlService =ac.getBean("accessControlService",AccessControlService.class);
    		    AccessControl accessControl = new AccessControl();
                accessControl.setObjectType(AccessControl.OBJECTTYPE_SUPERADMIN);
                accessControl.setObjectId(0);
                accessControl.setObjectTitle("系统超级管理员");
                accessControl.setUserId(user.getUserId());            
    		    accessControlService.saveOrUpdateAccessControl(accessControl);
    		    //默认进行审核,再次确认一下，就一个用户，不影响
                userservice.updateUserStatus(user, User.USER_STATUS_NORMAL);
    		}
    		
    		//设置密码？userservice.resetPassword(user.getLoginName(), ssoUser.getPassword());
    	}else{
    		
    		//System.out.println(user.getLoginName() +"SSOUserFilter user.Status:"+user.getUserStatus());
    		
    		//如果本地存在用户，则验证版本号，确定是否更新本地信息
    		if(ssoUser.getVersion() > user.getVersion() || !ssoUser.getPassportId().equals(user.getUserGuid())){
        		user.setTrueName(ssoUser.getTrueName());
        		user.setNickName(ssoUser.getNickName());
        		user.setEmail(ssoUser.getEmail());
        		user.setUserGuid(ssoUser.getPassportId());
        		//为大庆而修改的头像问题
        		if(user.getUserIcon() != null){
        		    if(user.getUserIcon().indexOf(user.getUserGuid())  == -1 && ssoUser.getUserIcon().indexOf(".") > -1){
        		        user.setUserIcon(user.getUserGuid() + ssoUser.getUserIcon().substring(ssoUser.getUserIcon().lastIndexOf(".")));
        		    }
        		}
        		else{
        		user.setUserIcon(ssoUser.getUserIcon());
        		}
        		/*
        		if(ssoUser.getBase64UserIcon()!=null){
        			if(ssoUser.getBase64UserIcon().length()>0){
        				byte[] imageData = new sun.misc.BASE64Decoder().decodeBuffer(ssoUser.getBase64UserIcon().trim());
        				String userIcon = cn.edustar.jitar.util.PicProcessUtil.cutImage(imageData, 180, 180, ssoUser.getUserIcon(), "1");
        				//需要加上 /images/headImg
        				userIcon = "/images/headImg/"+userIcon;
        				user.setUserIcon(userIcon);
        			}
        		} 
        		*/
        		//user.setUserStatus(ssoUser.getUserStatus());  //用户登录的时候，其状态信息不允许更新。和用户服务器的状态时两回事
        		if(ssoUser.getUserType()!=null){
        			if(ssoUser.getUserType().equals("学生") || ssoUser.getUserType().equals("1")){
        				user.setPositionId(5);
        			}else if(ssoUser.getUserType().equals("教师") || ssoUser.getUserType().equals("老师") || ssoUser.getUserType().equals("2")){
        				user.setPositionId(3);
        			}
        		}        		
        		user.setVersion(ssoUser.getVersion());
        		user.setGender(Short.parseShort(ssoUser.getGender().toString()));
        		//System.out.println(user.getLoginName() +" 过滤器 准备更新 用户  "+user.getUserStatus());
        		userservice.updateUser(user,false);  //更新用户,不需要再提交到服务器
        		//System.out.println(user.getLoginName() +" 过滤器 更新 用户 完毕  "+user.getUserStatus());
    		}
    	}
    	
    	//System.out.println(user.getLoginName() +"SSOUserFilter user2222222222.Status:"+user.getUserStatus());
    	
    	session.setAttribute("CPUSER", user);

    	session.setAttribute("rand", null);
    	session.setAttribute("jitar.login.userId", user.getUserId());
    	session.setAttribute("jitar.login.loginName", user.getLoginName());
    	session.setAttribute(cn.edustar.jitar.pojos.User.SESSION_LOGIN_USERMODEL_KEY,user);
    	
    	filterChain.doFilter(request, response);
    	return;
	}
	
	public void init(final FilterConfig filterConfig) throws ServletException {
	}

	public void destroy() {
	}	
	
	/**
	 * 根据Id,得到单位
	 * @param unitServiceUrl	单位服务地址
	 * @param unitId			单位Id
	 * @return 单位
	 */
	public com.octopus.system.model.Unit getSSOUnit(String unitServiceUrl,Integer unitId){
        HessianProxyFactory factory = new HessianProxyFactory();
        factory.setConnectTimeout(5000L); // 毫秒
        com.octopus.system.model.Unit unit = null;
        try {
            UnitManageService unitManageService = (UnitManageService) factory.create(UnitManageService.class, unitServiceUrl);
            unit = unitManageService.getById(unitId.toString());
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("传输错误");
        }
		return unit;
	}
	
	/**
	 * 向用户服务器(userServiceUrl)，验证ticket票证，返回用户对象
	 * @param userServiceUrl  	用户服务器地址 
	 * @param LoginUrl          生成 ticket票证时刻的 登录地址 
	 * @param ticket            需要验证的票证 
	 * @return 			返回用户对象
	 * @throws MalformedURLException
	 */
	public com.octopus.sso.model.User getSSOUser(String userServiceUrl,String LoginUrl, String ticket) throws MalformedURLException{
		
		if(null == userServiceUrl || userServiceUrl.length() == 0){
			return null;
		}
		
		if(null == ticket || ticket.length() == 0){
			return null;
		}
		
        HessianProxyFactory factory = new HessianProxyFactory();
        factory.setConnectTimeout(5000L); // 毫秒
        String result = null;
        com.octopus.sso.model.User ssoUser = null;
		
        com.octopus.sso.service.UserService userService = (com.octopus.sso.service.UserService) factory.create(com.octopus.sso.service.UserService.class,userServiceUrl);
        //getUserinfoByST(arg0,arg1)参数说明：
        //arg0是登录时使用的backurl，arg1是票证信息，登录返回的ticket参数。
        result = userService.getUserinfoByST(LoginUrl, ticket);
        //System.out.println("result= " + result);
        //返回值是用户对象的json格式
        ssoUser = JSON.parseObject(result, com.octopus.sso.model.User.class);	
        
        return ssoUser;
	}
}
