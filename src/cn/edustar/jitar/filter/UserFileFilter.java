package cn.edustar.jitar.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.edustar.jitar.JitarContext;
import cn.edustar.jitar.JitarRequestContext;
import cn.edustar.jitar.model.Configure;
import cn.edustar.jitar.pojos.User;
import cn.edustar.jitar.util.WebUtil;

public class UserFileFilter implements Filter {
	private static final Logger log = LoggerFactory.getLogger(UserFileFilter.class);
	
	/** WEB 应用环境 */
	private ServletContext servlet_ctxt;
	
	/** 网站的根地址 */
	private String websiteRootUrl = null;
	
	/** 网站的根目录。是带虚拟路径的那个格式 */
	private String siteUrl = null;
	
	public void init(FilterConfig config) throws ServletException {
		this.servlet_ctxt = config.getServletContext();
	}
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException
	{	
		//System.out.print("测试");
		HttpServletRequest http_req = (HttpServletRequest)request;
		//HttpServletResponse http_res = (HttpServletResponse)response;

		
		//如果是个性域名，则需要使用配置中的网站根路径；否则，需要取当前网站的根目录
		websiteRootUrl = http_req.getSession().getServletContext().getInitParameter("siteUrl");
		siteUrl = websiteRootUrl;
		if(websiteRootUrl == null || websiteRootUrl.length() < 1)
		{
			siteUrl = getCurrentSiteUrl(http_req);
			// 只有配置 userPath 的情况下才使用这个 websiteRootUrl 
			websiteRootUrl = getSiteRootUrl(http_req);
		}
		if(websiteRootUrl.endsWith("/") == false)
		{
			websiteRootUrl += "/";
		}		
		JitarContext jtar_ctxt = (JitarContext)servlet_ctxt.getAttribute(JitarContext.JITAR_CONTEXT_KEY_NAME);
		JitarRequestContext.setRequestContext(new JitarRequestContext(this.servlet_ctxt, request, response, jtar_ctxt, siteUrl));
		
		//String uri = http_req.getRequestURI(); 
		//String context_path = http_req.getContextPath() + "/";
		//log.info("测试信息 uri = " + uri);
		//log.info("context_path = " + context_path);	
		checkResource(request,response,chain);
		return;
	}	
	
	
	private void checkResource(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException
	{		
		//log.info("进入到  checkResource ");
		HttpServletRequest http_req = (HttpServletRequest)request;
		HttpServletResponse http_res = (HttpServletResponse)response;
		
		String uri = http_req.getRequestURI(); 
		uri = uri.substring(1); 
		// user 文件夹权限判定，限制某些资源，不登录不能访问
		if(http_req.getRequestURI().toLowerCase().indexOf("/resource/")>0)
		{	
			//log.info("进入到  resource =  " + http_req.getRequestURI());
			//访问的是user下的资源文件
			boolean canView = this.checkUserFile(http_req, http_res);
			//log.info("canView =  " + canView);
			if(canView)
			{
				//继续访问
				ServletContext sc = http_req.getSession().getServletContext();
				String fileUserConfigPath = sc.getInitParameter("userPath");
				if (fileUserConfigPath==null || fileUserConfigPath.equals("")){
					chain.doFilter(request, response);
					return;
				}
				else{
					//定义转向					
					String newUri = uri.replaceFirst("user/", "userfile/");
					newUri = websiteRootUrl + newUri;
					//log.info("新URI:"+newUri);
					//RequestDispatcher dispatcher = this.servlet_ctxt.getRequestDispatcher(newUri);
					//dispatcher.forward(request, response);
					//http_res.resetBuffer();
					newUri = http_req.getRequestURI().replaceFirst("/user/", "/userfile/");
					http_res.sendRedirect(newUri);
					return;
				}
			}
			else
			{
				return;
			}
		}
		else if(http_req.getRequestURI().toLowerCase().indexOf("/photo/")>0)
		{
			//log.info("进入到  photo =  " + http_req.getRequestURI());
			ServletContext sc = http_req.getSession().getServletContext();
			String fileUserConfigPath = sc.getInitParameter("userPath");
			//log.info("fileUserConfigPath = null:  " + (fileUserConfigPath==null || fileUserConfigPath.equals("")));
			if (fileUserConfigPath==null || fileUserConfigPath.equals("")){				
				chain.doFilter(request, response);
				return;
			}
			else{
				//定义转向
				String newUri = uri.replaceFirst("user/", "userfile/");
				newUri = websiteRootUrl + newUri;
				//log.info("新URI:"+newUri);
				//RequestDispatcher dispatcher = this.servlet_ctxt.getRequestDispatcher(newUri);
				//dispatcher.forward(request, response);
				newUri = http_req.getRequestURI().replaceFirst("/user/", "/userfile/");				
				http_res.sendRedirect(newUri);
				
				//http_res.sendRedirect(newUri);
				return;
			}
		}
		 else
		 {
			 //log.info("进入到  其他 =  " + http_req.getRequestURI());
			 Cookie[] cookies = http_req.getCookies();
			 String u = null;
			 if(cookies != null)
			 {
				 //debugOut("过滤器：cookies != null 开始");
				 for (int i = 0; i < cookies.length; i++) 
			    {						 
			       Cookie c = cookies[i]; 
			       String cookieName = c.getName();
			       if(cookieName != null && cookieName.equalsIgnoreCase("url"))
			       {
			          u = c.getValue();
			       }
			    }
			 }
			 
			 if(u != null)
			 {	 
				//chain.doFilter(request, response);
				ServletContext sc = http_req.getSession().getServletContext();
				String fileUserConfigPath = sc.getInitParameter("userPath");
				if (fileUserConfigPath==null){
					chain.doFilter(request, response);
				}
				else if(fileUserConfigPath==""){
					chain.doFilter(request, response);
				}
				else{
					String newUri=uri.replaceFirst("user/", "userfile/");
					newUri = websiteRootUrl + newUri;
					//log.info("新URI:"+newUri);
					
					newUri = http_req.getRequestURI().replaceFirst("/user/", "/userfile/");				
					http_res.sendRedirect(newUri);
					
					//http_res.sendRedirect(newUri);
				}
				 
			 }
			 else
			 {
				 if(uri.toLowerCase().indexOf(".mp3")>-1)
				 {
					 http_res.setStatus(HttpServletResponse.SC_NOT_FOUND);
					 return;
				 }
				 else
				 {
					//chain.doFilter(request, response);
					ServletContext sc = http_req.getSession().getServletContext();
					String fileUserConfigPath = sc.getInitParameter("userPath");
					if (fileUserConfigPath==null || fileUserConfigPath==""){
						chain.doFilter(request, response);
					}
					else{
						String newUri=uri.replaceFirst("user/", "userfile/");
						newUri = websiteRootUrl + newUri;
						//log.info("新URI:"+newUri);
						newUri = http_req.getRequestURI().replaceFirst("/user/", "/userfile/");				
						http_res.sendRedirect(newUri);
						
						//http_res.sendRedirect(newUri);
					}
				 }
			 }
		 }	
	}
	
	private boolean checkUserFile(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{		
		//判断是否是盗联
		String remoteAddr=request.getRemoteAddr(); 
		String remoteAddr1=request.getRemoteHost();
		String referer = request.getHeader("Referer");
		//String resUri = request.getRequestURI().toLowerCase();			

		String OringeUrl =  getCurrentSiteUrl(request).toLowerCase();
		//log.debug("来自 remoteAddr = " + remoteAddr );
		//log.debug("来自 remoteAddr1 = " + remoteAddr1 );
		//log.debug("请求的页面地址 resUri = " + resUri );
		//log.debug("请求的页面地址 referer = " + ( referer == null?"null":referer ) );
		//log.debug("OringeUrl = " + ( OringeUrl == null?"null":OringeUrl ));
		
		String urlAddr = request.getSession().getServletContext().getInitParameter("reslib_ip");
		//log.debug("资源库IP urlAddr = " + urlAddr );
		if(urlAddr!=null)
		{
			if(remoteAddr.equals(urlAddr) || remoteAddr1.equals(urlAddr))
			{
				return true;
			}
		}
		if(referer != null)
		{
			if ((referer.toLowerCase().indexOf(OringeUrl) < 0 ) ||(referer.toLowerCase().indexOf(OringeUrl) > 5))
			{
				//log.info("拒绝访问,转向到"+ siteUrl +"refuse.jsp");
				response.sendRedirect(siteUrl + "refuse.jsp");
				return false;
			}
		}		
		
	  //判断是否允许下载
      Configure conf = JitarContext.getCurrentJitarContext().getConfigService().getConfigure();
      Object obj=conf.getValue("resource.download");
      if(obj!=null)
      {
      	//System.out.println("resource.download is "+obj.toString());
	      boolean downupload = Boolean.valueOf(obj.toString());
	      if(downupload)
	      {
	      	return true;
	      }
	      else
	      {
	      	//判断用户是否登陆了,没登陆不允许下载
	      	/** 登录验证 */
   			HttpSession session = request.getSession();
   			User user = WebUtil.getLoginUser(session);
   			User impersonate = WebUtil.getImpersonateUser(session);
   			if(user == null && impersonate == null )
   			{
   				//log.info("用户没登陆,不允许访问!转向到" + siteUrl + "refuse.jsp");
   				response.sendRedirect(siteUrl + "refuse.jsp");
   				//RequestDispatcher dispatcher = this.servlet_ctxt.getRequestDispatcher(request.getContextPath()+"/refuse.jsp");
   				//dispatcher.forward(request, response);
   				return false;
   			}
   			else
   			{
   				return true;
   			}
	      }
      }
      else
      {
      	return true;
      }	
	}
	
	/** 计算 根路径 */
	private static final String getCurrentSiteUrl(HttpServletRequest request) {
		String root = request.getScheme()
				+ "://"
				+ request.getServerName()
				+ (request.getServerPort() == 80 ? "" : ":"
						+ request.getServerPort()) + request.getContextPath()
				+ "/";
		return root;
	}
	
	/** 计算 顶级路径 */
	private static final String getSiteRootUrl(HttpServletRequest request) {
		String root = request.getScheme()
				+ "://"
				+ request.getServerName()
				+ (request.getServerPort() == 80 ? "" : ":"	+ request.getServerPort()) 
				+ "/";
		return root;
	}
	public void destroy() {
		log.info("UserFileFilter 销毁了");
	}
}
