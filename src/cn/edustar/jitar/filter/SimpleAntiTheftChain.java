package cn.edustar.jitar.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SimpleAntiTheftChain implements Filter {
	/* 
	 * 防盗链测试
	*/
	private static final Logger log = LoggerFactory.getLogger(SimpleAntiTheftChain.class);
	
	/* 网站的最后部分，必须是所有子站点共同拥有的部分 */
	private String mainDomain;
	private String splider;
	private String ERROR_MESSAGE = "你无权访问该资源。You cann't be authorizated to access this resource.please contact with the China Edustar.";
	private String INFO_MESSAGE;
	private Boolean canDebug = false;
	
	public void init(FilterConfig config) throws ServletException {
		mainDomain = config.getInitParameter("MainDomainName");
		splider = config.getInitParameter("Splider");
		String dbg = config.getInitParameter("Debug");
		if(dbg == null || dbg.equals(""))
		{
			canDebug = false;
		}
		else
		{
			if(dbg.toLowerCase().equals("true"))
			{
				canDebug = true;
			}
			else
			{
				canDebug = false;
			}
		}
		
		
		if(mainDomain == null)
		{
			mainDomain = "";
		}
		mainDomain = mainDomain.toLowerCase();
		//System.out.println("mainDomain = " + mainDomain);
		//System.out.println("Splider = " + splider);
	}
	
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		
		if(null == splider || splider.length() <1)
		{
			chain.doFilter(request, response);
		}		
		
		HttpServletRequest http_req = (HttpServletRequest)request;
		HttpServletResponse http_res = (HttpServletResponse)response;
		String referer = http_req.getHeader("Referer");
		String userAgent = http_req.getHeader("User-Agent");
		
		INFO_MESSAGE = "referer = " + referer + "\tuserAgent = " + userAgent + "\turl = " + http_req.getRequestURI();
		
		/** 先拦截访问来路 */
		if(null == referer && null == userAgent)
		{	
			if(canDebug)
			{
				System.out.println("拦截原因：null == referer && null == userAgent\t" + INFO_MESSAGE);
			}
			http_res.sendError(HttpServletResponse.SC_UNAUTHORIZED,this.ERROR_MESSAGE);
			return;
		}
		if (referer == null) 
		{
			referer="";
		}
		if (userAgent == null) 
		{
			userAgent="";
		}
		
		referer = referer.toLowerCase();
		userAgent = userAgent.toLowerCase();
		
		//屏蔽所有来自  baidu 和  QQ Zone 的链接
		if(referer.indexOf(".baidu.com") > -1 || referer.indexOf("baidusg") > -1 || referer.indexOf("baidumt") > -1
				|| referer.indexOf(".qq.com") > -1)
		{
			if(canDebug)
			{
				System.out.println("拦截原因：百度或QQ进行调用。\t" + INFO_MESSAGE);
			}
			http_res.sendError(HttpServletResponse.SC_UNAUTHORIZED,this.ERROR_MESSAGE);
			return;
		}
		
		// 检测是否是本站的链接，不过，这个应该可以伪造，但没有更好的处理办法。如果是本站的链接，就放行吧，适用于本页播放器的情况。
		// 只检查配置的情况
		if(!mainDomain.equals(""))
		{
			if(referer.indexOf(mainDomain) > -1)
			{
				chain.doFilter(request, response);
				return;
			}
		}
		
		// Media Player  放行
		if(userAgent.indexOf("nsplayer") > -1)
		{
			chain.doFilter(request, response);
			return;
		}
		
		
		/** 判断是否是从网站访问的 */
		Cookie[] cookies = http_req.getCookies();		
		if(cookies == null)
		{
			if(canDebug)
			{
				System.out.println("拦截原因：Cookie = null，或者不是直接从页面访问?\t" + INFO_MESSAGE);
			}
			http_res.sendError(HttpServletResponse.SC_UNAUTHORIZED,this.ERROR_MESSAGE);
			return;
		}
		else
		{
			boolean hasKillCookie = false;
			for (int i = 0; i < cookies.length; i++) {
		        Cookie cookie = cookies[i];
		        if(cookie.getName().equals("kill"))
		        {
		        	hasKillCookie = true;
		        	break;
		        }  
		      }
			
			if(!hasKillCookie)
			{
				if(canDebug)
				{
					System.out.println("拦截原因：Cookie kill = false\t" + INFO_MESSAGE);
				}
				http_res.sendError(HttpServletResponse.SC_UNAUTHORIZED,this.ERROR_MESSAGE);
				return;
			}
		}
		
		splider = splider.toLowerCase();
		splider = splider.replaceAll(" ", "");
		String[] spliders = splider.split(",");
		
		/** 再判断是否是搜索引擎  */
		for(int i = 0;i<spliders.length;i++)
		{
			if(userAgent.indexOf(spliders[i]) >-1 )
			{
				if(canDebug)
				{
					System.out.println("拦截原因：spliders = " + spliders[i] + "\t" + INFO_MESSAGE);
				}
				http_res.sendError(HttpServletResponse.SC_UNAUTHORIZED,this.ERROR_MESSAGE);
				return;
			}
		}
		chain.doFilter(request, response);
	}

	public void destroy() {
		log.info("SimpleAntiTheftChain 销毁了");
	}
}
