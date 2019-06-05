package cn.edustar.jitar.servlet;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpProtocolParams;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;



import com.alibaba.fastjson.JSONObject;

import cn.edustar.jitar.JitarContext;
import cn.edustar.jitar.JitarRequestContext;
import cn.edustar.jitar.pojos.Unit;
import cn.edustar.jitar.pojos.User;
import cn.edustar.jitar.pojos.UserOnLine;
import cn.edustar.jitar.service.OnLineService;
import cn.edustar.jitar.service.UnitService;
import cn.edustar.jitar.service.UserService;
import cn.edustar.jitar.util.CommonUtil;

public class EduYunServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7287065896868828957L;
	public void init() {}

	public void destroy() {}
	
	HttpServletRequest _request = null;
	HttpServletResponse _response = null;
	String userKey = UUID.randomUUID().toString().toLowerCase();
	private PrintWriter out = null;
	private UserService userService;
	private UnitService unitService;
	private String loginName = null;
	private String email = null;
	private String usertype = null;//1,2,3
	private String trueName = null;
	private String account = null;
	private String gender = null;
	private String _loginName = null;
	
	User user = null;
	String tokenId = "MTEzNDEzMjUwNw==";
	boolean isDebug = true;
	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		_request = request;
		_response = response;
		String startHtml = "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\"><html xmlns=\"http://www.w3.org/1999/xhtml\"><head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" /><title>为了教研</title></head><body>";
		String endHtml = "</body></html>";
		response.setContentType("text/html;charset=utf-8");
		out = response.getWriter();
		userService = JitarContext.getCurrentJitarContext().getUserService();
		unitService = JitarContext.getCurrentJitarContext().getUnitService();		
		tokenId = request.getParameter("tokenId");
		if(tokenId == null || tokenId.length()<1)
		{
			out.print(startHtml + "<p>无效令牌。</p>" + endHtml);
			return;
		}
		
		//得到用户信息。创建用户，进行登录
		String tokenMessage = this.getLoginUserInfo();
		if(!tokenMessage.equals(""))
		{
			out.print(startHtml + "返回的结果：" + tokenMessage + endHtml);	
			return;
		}
		
		if(this.loginName == null || this.loginName.equals(""))
		{
			out.print(startHtml + "返回的登录名为空。" + endHtml);	
			return;
		}
		
		//为了防止与原有系统重名。
		if(account == null)
		{
			this._loginName = "eduyun" + this.loginName;
		}
		else
		{
			this._loginName = this.account;
		}
				
		user = userService.getUserByLoginName(this._loginName);
		if(user != null)
		{
			//进行程序的二次登录
			this.loginJitarAndRedirectToIndex(request,response);
			return;
		}
				
		Unit unit = unitService.getRootUnit();
		int unitId = 1;
		String unitPathInfo = "/1/";
		if (unit != null && unit.getUnitPathInfo() != null) {
			unitId = unit.getUnitId();
			unitPathInfo = unit.getUnitPathInfo();
		}
		
		user = new User();		
		user.setUnitId(unitId);
		user.setUnitPathInfo(unitPathInfo);
		if(account != null)
		{
			user.setAccountId(this.account);
		}
		
		user.setLoginName(this._loginName);
		user.setNickName(this.loginName);
		user.setTrueName(this.trueName);
		user.setBlogIntroduce(this.trueName + "的工作室");
		if(this.email !=null) user.setEmail(this.email);
		if(this.gender!=null)
		{
			if(this.gender.equals("1"))
			{
				user.setGender((short)1);
			}
			else if(this.gender.equals("0"))
			{
				user.setGender((short)0);
			}
		}
		if(this.usertype != null)
		{
			//云【平台 1:学生、2:教师、3:家长、4:学校、5：教育局6：电教馆、7：私有云GSA
			//本系统    3： 教师   5：学生   4：教育局职工
			if(this.usertype.equals("1"))
			{
				user.setPositionId(5);
			}
			else if(this.usertype.equals("2"))
			{
				user.setPositionId(3);
			}
			else if(this.usertype.equals("3"))
			{
				user.setPositionId(4);
			}
		}
		user.setUserIcon("images/default.gif");
		
		userService.createUser(user,false);
		this.loginJitarAndRedirectToIndex(request,response);
		//进行登录。
		
		
	}

	private void loginJitarAndRedirectToIndex(HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		HttpSession session = this._request.getSession();
		session.setAttribute(User.SESSION_USER_TICKET, userKey);
		session.setAttribute(User.SESSION_LOGIN_NAME_KEY,user.getLoginName());
		session.setAttribute(User.SESSION_LOGIN_USERMODEL_KEY, user);
		System.out.println("session.getAttribute(User.SESSION_LOGIN_USERMODEL_KEY) = " + session.getAttribute(User.SESSION_LOGIN_USERMODEL_KEY));
		session.setAttribute(User.SESSION_LOGIN_GENUSER_KEY, null);
		long nowTime = System.currentTimeMillis();
		SimpleDateFormat sdf = new SimpleDateFormat("",	Locale.SIMPLIFIED_CHINESE);
		sdf.applyPattern("yyyy年MM月dd日 HH时mm分ss秒");
		UserOnLine userOnLine = new UserOnLine();
		userOnLine.setUserId(user.getUserId());
		userOnLine.setUserName(user.getLoginName());
		userOnLine.setOnlineTime(nowTime);
		WebApplicationContext app_ctxt = WebApplicationContextUtils.getWebApplicationContext(this._request.getServletContext());		
		OnLineService onlineService = (OnLineService) app_ctxt.getBean("onlineService");
		onlineService.saveUserOnLine(userOnLine);
		writeCookieToClient();
		_response.sendRedirect(CommonUtil.getSiteUrl(this._request));
	}
	
	protected void writeCookieToClient() {
		
		Cookie cookie = new Cookie("UserTicket", this.userKey);
		cookie.setMaxAge(Integer.MAX_VALUE);
		String domain = getDomain(_request.getServerName());
		if (!"".equals(domain) && !Character.isDigit(domain.charAt(0))) {
			cookie.setDomain("." + domain);
		}
		cookie.setPath("/");
		_response.addCookie(cookie);
	}

	private String getDomain(String url) {
		String returnDomain = "";
		Boolean isIP = true;
		if (url.contains(".")) {
			String[] tempArray = url.split("\\.");
			for (int i = 0; i < tempArray.length; i++) {
				try {
					Integer.parseInt(tempArray[i]);
				} catch (NumberFormatException e) {
					isIP = false;
					break;
				}
			}
			if (isIP) {
				returnDomain = "";
			} else {
				switch (tempArray.length) {
				case 2:
					returnDomain = url;
					break;
				default:
					returnDomain = url.substring(url.indexOf(".") + 1);
					break;
				}
			}
		} else {
			returnDomain = "";
		}
		return returnDomain;
	}


@SuppressWarnings({"rawtypes", "unchecked"})
private String getLoginUserInfo()
{
	
	String url = "http://111.4.115.109:8083/edu-gate/eduServer?" + Math.random();	
	JSONObject inputJsonData = new JSONObject();
	JSONObject headJsonData = new JSONObject();
	JSONObject bodyJsonData = new JSONObject();
	
	headJsonData.put("serialNumber","120319181305000011");
	headJsonData.put("method","012105");
	headJsonData.put("version","1");
	
	bodyJsonData.put("tokenId",tokenId);
	
	inputJsonData.put("body",bodyJsonData);
	
	inputJsonData.put("head",headJsonData);
	
	inputJsonData.put("mac","32435lksldjflejger8jflsdjfou9jsdfo");
	
	String postData = inputJsonData.toString();
	inputJsonData.clear();
	inputJsonData = null;
	if(isDebug)	System.out.println("postData= " + postData);

	try {
		/*URL dataUrl = new URL(url);
		HttpURLConnection con = (HttpURLConnection)dataUrl.openConnection();
		con.setRequestMethod("POST");
		con.setRequestProperty("Proxy-Connection", "Keep-Alive");
		con.setDoOutput(true);
		con.setDoInput(true);

		OutputStream os = con.getOutputStream();
		DataOutputStream dos = new DataOutputStream(os);

		dos.write(postData.getBytes());
		dos.flush();
		dos.close();

		InputStream is = con.getInputStream();
		DataInputStream dis = new DataInputStream(is);
		byte d[] = new byte[dis.available()];

		dis.read(d);
		String data = new String(d);
		con.disconnect();*/
		
		DefaultHttpClient client = new DefaultHttpClient();
		HttpPost post = new HttpPost(url);
		post.addHeader("Content-Type", "application/x-www-form-urlencoded");
		StringEntity stringEntity = new StringEntity(postData, "UTF-8");
		post.setEntity(stringEntity);
		HttpProtocolParams.setUseExpectContinue(client.getParams(), false);
		HttpProtocolParams.setUseExpectContinue(post.getParams(), false);
		HttpResponse resp = client.execute(post);
		InputStream content = resp.getEntity().getContent();
		String data = IOUtils.toString(content, "UTF-8");
		
		
		if(isDebug)	System.out.println("返回结果： " + data);
		JSONObject responseBodyJson = (JSONObject) JSONObject.parse(data);
		JSONObject head = (JSONObject) responseBodyJson.get("head");
		JSONObject body = (JSONObject) responseBodyJson.get("body");
		String code = (String)body.get("code");
		if(!code.equals("000000"))
		{			
			return "返回状态码不是成功标志：" + (String)body.get("message");
		}
		
		JSONObject result = (JSONObject) body.get("result");
		/*JSONArray classDto = (JSONArray)result.get("classDto");
		for(int i=0;i<classDto.size();i++)
		{
			JSONObject x = (JSONObject)classDto.get(i);
			System.out.println("读取结果……  " + x.get("gradeName"));
		}*/
		
		if(result == null)
		{
			return "没有结果数据。";
		}
		
		this.email = (String)result.get("email");
		this.trueName = (String)result.get("name");
		this.account = (String)result.get("account");
		this.loginName = (String)result.get("loginAccount");
		System.out.println("读取结果 this.loginName = " + this.loginName);
		this.usertype = (String)result.get("usertype");
		this.gender = (String)result.get("gender");
		return "";
	} catch (Exception ex) {
		return "提交数据出现异常：" + ex.getLocalizedMessage();
	}
	
}
}
