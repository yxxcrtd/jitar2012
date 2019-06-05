package com.jitar2Infowarelab.utils;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.List;

import com.jitar2Infowarelab.model.CemUser;
import com.jitar2Infowarelab.model.MeetingQueryParam;
import com.jitar2Infowarelab.model.Org;
public class MessageUserXML {
	private static String xmlVersion = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n";
	private static String InfowareLab = "box";
	private static int serviceVersion = 30;
	
	//因为是后台服务管理，需要管理员的用户和密码 
	private static String currentLoginName = "admin";
	private static String currentLoginPassword = "admin";
	
	//private static String headerAccount = "0000000B";										//0000000B		不知道干啥的
	//private static String headerTicket =  "402881e21b727955011b730fc7950003"; 				//402881e21b727955011b730fc7950003
	
	private static void initParam(){
		String _site = Utils.getInfowarelab_SiteName();
		if(!Utils.isNullorBlank(_site)){
			InfowareLab =  _site;
		}
		String version = Utils.getInfowarelab_AdminServiceVersion();
		if(!Utils.isNullorBlank(version)){
			serviceVersion =  Integer.parseInt(version);
		}
        currentLoginName = Utils.getInfowarelab_AdminUser();
        currentLoginPassword = Utils.getInfowarelab_AdminPassword();		
	}
	
	private static String getHeadXml(String action)
	{
		initParam();
		StringBuilder strbuilderXml = new StringBuilder();
		strbuilderXml.append("<header>\n");
		//strbuilderXml.append("<account>"+headerAccount+"</account>");
		strbuilderXml.append("<action>"+action+"</action>\n");
		strbuilderXml.append("<service>siteadmin</service>\n");
		strbuilderXml.append("<type>XML</type>\n");
		strbuilderXml.append("<siteName>"+InfowareLab+"</siteName>\n");
		//strbuilderXml.append("<ticket>"+headerTicket+"</ticket>\n");
		strbuilderXml.append("<userName>"+currentLoginName+"</userName>\n");
		strbuilderXml.append("<password>"+currentLoginPassword+"</password>\n");
		strbuilderXml.append("<version>"+serviceVersion+"</version>\n");
		strbuilderXml.append("</header>\n");	
		return strbuilderXml.toString();
	}
	
	/**
	 * 创建新用户的 提交的XML
	 * @param user
	 * @return
	 */
	public static String getMessageXML_createCemUser(CemUser user){
		
		StringBuilder strbuilderXml = new StringBuilder(xmlVersion);
		strbuilderXml.append("<Message>\n");
		strbuilderXml.append(getHeadXml("createCemUser"));
		strbuilderXml.append("<body>\n"); 
		strbuilderXml.append("<address>"+ user.getAddress() +"</address>\n");
		strbuilderXml.append("<cellphone>"+ user.getCellphone() +"</cellphone>\n");
		strbuilderXml.append("<city>"+ user.getCity() + "</city>\n");
		strbuilderXml.append("<company>"+ user.getCompany() + "</company>\n");
		strbuilderXml.append("<country>" + user.getCountry() +  "</country>\n");
		strbuilderXml.append("<deptId>"+ user.getDeptId() +"</deptId>\n");
		strbuilderXml.append("<duty>"+ user.getDuty() +"</duty>\n");
		strbuilderXml.append("<email>"+ user.getEmail() +"</email>\n");
		strbuilderXml.append("<enabled>"+ user.getEnabled() +"</enabled>\n");
		strbuilderXml.append("<fax>"+ user.getFax()  +"</fax>\n");
		strbuilderXml.append("<firstName>"+ user.getFirstName() +"</firstName>\n");
		strbuilderXml.append("<forceCreate>"+ user.getForceCreate() +"</forceCreate>\n");
		strbuilderXml.append("<gender>"+ user.getGender() +"</gender>\n");
		strbuilderXml.append("<lastName>"+ user.getLastName() +"</lastName>\n");
		strbuilderXml.append("<nickname>"+ user.getNickname() +"</nickname>\n");
		strbuilderXml.append("<officePhone>"+ user.getOfficePhone() +"</officePhone>\n");
		strbuilderXml.append("<otherEmail>"+ user.getOtherEmail() +"</otherEmail>\n");
		strbuilderXml.append("<otherInfo>"+ user.getOtherInfo() +"</otherInfo>\n");
		strbuilderXml.append("<otherPhone>"+ user.getOtherPhone() +"</otherPhone>\n");
		strbuilderXml.append("<password>"+ user.getPassword() +"</password>\n");
		strbuilderXml.append("<postcode>"+ user.getPostcode() +"</postcode>\n");
		strbuilderXml.append("<province>"+ user.getProvince() +"</province>\n");
		strbuilderXml.append("<reportTo>"+ user.getReportTo() +"</reportTo>\n");
		strbuilderXml.append("<userName>"+ user.getUserName() +"</userName>\n");
		strbuilderXml.append("<userType>"+ user.getUserType() +"</userType>\n");
		strbuilderXml.append("</body>\n");
		strbuilderXml.append("</Message>\n");
		
		return strbuilderXml.toString();
	}

	/**
	 * 更新用户(根据用户名修改用户其它信息) 提交的XML
	 * @param user
	 * @return
	 */
	public static String getMessageXML_updateCemUser(CemUser user){
		
		StringBuilder strbuilderXml = new StringBuilder(xmlVersion);
		strbuilderXml.append("<Message>\n");
		strbuilderXml.append(getHeadXml("updateCemUser"));
		strbuilderXml.append("<body>\n"); 
		strbuilderXml.append("<address>"+ user.getAddress() +"</address>\n");
		strbuilderXml.append("<cellphone>"+ user.getCellphone() +"</cellphone>\n");
		strbuilderXml.append("<city>"+ user.getCity() + "</city>\n");
		strbuilderXml.append("<company>"+ user.getCompany() + "</company>\n");
		strbuilderXml.append("<country>" + user.getCountry() +  "</country>\n");
		strbuilderXml.append("<deptId>"+ user.getDeptId() +"</deptId>\n");
		strbuilderXml.append("<duty>"+ user.getDuty() +"</duty>\n");
		strbuilderXml.append("<email>"+ user.getEmail() +"</email>\n");
		strbuilderXml.append("<enabled>"+ user.getEnabled() +"</enabled>\n");
		strbuilderXml.append("<fax>"+ user.getFax()  +"</fax>\n");
		strbuilderXml.append("<firstName>"+ user.getFirstName() +"</firstName>\n");
		//strbuilderXml.append("<forceCreate>"+ user.getForceCreate() +"</forceCreate>\n");
		strbuilderXml.append("<gender>"+ user.getGender() +"</gender>\n");
		strbuilderXml.append("<lastName>"+ user.getLastName() +"</lastName>\n");
		strbuilderXml.append("<nickname>"+ user.getNickname() +"</nickname>\n");
		strbuilderXml.append("<officePhone>"+ user.getOfficePhone() +"</officePhone>\n");
		strbuilderXml.append("<otherEmail>"+ user.getOtherEmail() +"</otherEmail>\n");
		strbuilderXml.append("<otherInfo>"+ user.getOtherInfo() +"</otherInfo>\n");
		strbuilderXml.append("<otherPhone>"+ user.getOtherPhone() +"</otherPhone>\n");
		strbuilderXml.append("<password>"+ user.getPassword() +"</password>\n");
		strbuilderXml.append("<postcode>"+ user.getPostcode() +"</postcode>\n");
		strbuilderXml.append("<province>"+ user.getProvince() +"</province>\n");
		strbuilderXml.append("<reportTo>"+ user.getReportTo() +"</reportTo>\n");
		strbuilderXml.append("<userName>"+ user.getUserName() +"</userName>\n");
		strbuilderXml.append("<userType>"+ user.getUserType() +"</userType>\n");
		strbuilderXml.append("</body>\n");
		strbuilderXml.append("</Message>\n");
		
		return strbuilderXml.toString();
	}

/**
 * 
 	* 根据参数来删除用户的
 * 
 * @param queryparam
 * @return
 */
	public static String getMessageXML_delCemUser(MeetingQueryParam queryparam){
		StringBuilder strbuilderXml = new StringBuilder(xmlVersion);
		strbuilderXml.append("<Message>\n");
		strbuilderXml.append(getHeadXml("delCemUser"));
		strbuilderXml.append("<body>\n");
		strbuilderXml.append("<parameter>");
		strbuilderXml.append("<type>"+queryparam.getType()+"</type>");
		strbuilderXml.append("<value>"+queryparam.getValue()+"</value>");
		strbuilderXml.append("</parameter>");
		strbuilderXml.append("</body>\n");
		strbuilderXml.append("</Message>\n");
		return strbuilderXml.toString();
	}
	
	/**
	 * 根据参数来 更新用户的密码
	 * @param MeetingQueryParam
	 * @param password
	 * @return
	 */
	public static String getMessageXML_resetUserPassword(MeetingQueryParam queryparam,String password){
		StringBuilder strbuilderXml = new StringBuilder(xmlVersion);
		strbuilderXml.append("<Message>\n");
		strbuilderXml.append(getHeadXml("resetUserPassword"));
		strbuilderXml.append("<body>\n");
		strbuilderXml.append("<parameter>");
		strbuilderXml.append("<type>"+queryparam.getType()+"</type>");
		strbuilderXml.append("<value>"+queryparam.getValue()+"</value>");
		strbuilderXml.append("</parameter>");
		strbuilderXml.append("<password>"+password+"</password>");
		strbuilderXml.append("</body>\n");
		strbuilderXml.append("</Message>\n");
		return strbuilderXml.toString();		
	}
	
	/**
	 * 更新当前用户密码 
	 * @param password
	 * @return
	 */
	public static String getMessageXML_changePassword(String password){
		StringBuilder strbuilderXml = new StringBuilder(xmlVersion);
		strbuilderXml.append("<Message>\n");
		strbuilderXml.append(getHeadXml("changePassword"));
		strbuilderXml.append("<body>\n");
		strbuilderXml.append("<password>"+password+"</password>");
		strbuilderXml.append("</body>\n");
		strbuilderXml.append("</Message>\n");
		return strbuilderXml.toString();	
	}
	
	/**
	 * 用户详细信息查询（for Siteadmin）
	 * 
	 * 根据提供的用户名、用户ID、电子邮件、手机以及对应的类型，查询用户详细信息。
	 * 一次最多可以查询10条用户记录。
	 * 调用该接口的用户必须具有Siteadmin中浏览用户的权限，查看调用者信息不经过权限验证
	 * @param queryparams
	 * @return
	 */
	public static String getMessageXML_getCemUserBatch(List<MeetingQueryParam> queryparams){
		StringBuilder strbuilderXml = new StringBuilder(xmlVersion);
		strbuilderXml.append("<Message>\n");
		strbuilderXml.append(getHeadXml("getCemUserBatch"));
		strbuilderXml.append("<body>\n");
		strbuilderXml.append("<parameters>\n");
		for(int i = 0; i < queryparams.size() ; i++){
			MeetingQueryParam p = queryparams.get(i);
			if(null != p){
				strbuilderXml.append("<parameter>\n");
				strbuilderXml.append("<type>"+p.getType()+"</type>\n");
				strbuilderXml.append("<value>"+p.getValue()+"</value>\n");
				strbuilderXml.append("</parameter>\n");
			}
		}
		strbuilderXml.append("</parameters>\n");
		strbuilderXml.append("</body>\n");
		strbuilderXml.append("</Message>\n");
		return strbuilderXml.toString();		
	}

/**
 * 用户详细信息查询（for ET）
*根据提供的用户名、用户ID、电子邮件、手机以及对应的类型，查询用户详细信息。
*一次最多可以查询10条用户记录。
*调用该接口的用户必须具有ET中浏览用户的权限。
 * @param queryparams
 * @return
 */
	public static String getMessageXML_getUserForET(List<MeetingQueryParam> queryparams){
		StringBuilder strbuilderXml = new StringBuilder(xmlVersion);
		strbuilderXml.append("<Message>\n");
		strbuilderXml.append(getHeadXml("getUserForET"));
		strbuilderXml.append("<body>\n");
		strbuilderXml.append("<parameters>\n");
		for(int i = 0; i < queryparams.size() ; i++){
			MeetingQueryParam p = queryparams.get(i);
			if(null != p){
				strbuilderXml.append("<parameter>\n");
				strbuilderXml.append("<type>"+p.getType()+"</type>\n");
				strbuilderXml.append("<value>"+p.getValue()+"</value>\n");
				strbuilderXml.append("</parameter>\n");
			}
		}
		strbuilderXml.append("</parameters>\n");
		strbuilderXml.append("</body>\n");
		strbuilderXml.append("</Message>\n");
		return strbuilderXml.toString();			
	}
	
	/**
	 * 用户验证请求	XML
	 * @param p
	 * @param password
	 * @return
	 */
	public static String getMessageXML_validateUser(MeetingQueryParam p,String password){
		StringBuilder strbuilderXml = new StringBuilder(xmlVersion);
		strbuilderXml.append("<Message>\n");
		strbuilderXml.append(getHeadXml("validateUser"));
		strbuilderXml.append("<body>\n");
		strbuilderXml.append("<parameter>\n");
		strbuilderXml.append("<type>"+p.getType()+"</type>\n");
		strbuilderXml.append("<value>"+p.getValue()+"</value>\n");
		strbuilderXml.append("</parameter>\n");
		strbuilderXml.append("<password>"+password+"</password>\n");		
		strbuilderXml.append("</body>\n");
		strbuilderXml.append("</Message>\n");
		return strbuilderXml.toString();				
	}
	
	/**
	 * 增加组织
	 * @param org
	 * @return
	 */
	public static String getMessageXML_createOrg(Org org){
		StringBuilder strbuilderXml = new StringBuilder(xmlVersion);
		strbuilderXml.append("<Message>\n");
		strbuilderXml.append(getHeadXml("createOrg"));
		strbuilderXml.append("<body>\n");
		strbuilderXml.append("<address>"+org.getAddress() +"</address>\n");
		strbuilderXml.append("<code>"+ org.getCode() +"</code>\n");
		strbuilderXml.append("<description>"+ org.getDescription() +"</description>\n");
		strbuilderXml.append("<email>"+ org.getEmail() +"</email>\n");
		strbuilderXml.append("<linkman>"+ org.getLinkman() +"</linkman>\n");
		strbuilderXml.append("<name>"+ org.getName() +"</name>\n");
		strbuilderXml.append("<parentOrgId>"+ org.getParentOrgId() +"</parentOrgId>\n");
		strbuilderXml.append("<postcode>"+ org.getPostcode() +"</postcode>\n");
		strbuilderXml.append("<principal>"+ org.getPrincipal() +"</principal>\n");
		strbuilderXml.append("</body>\n");
		strbuilderXml.append("</Message>\n");
		return strbuilderXml.toString();			
	}
}
