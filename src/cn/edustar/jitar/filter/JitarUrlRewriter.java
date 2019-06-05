package cn.edustar.jitar.filter;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringEscapeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.edustar.jitar.JitarContext;
import cn.edustar.jitar.JitarRequestContext;
import cn.edustar.jitar.model.Configure;
import cn.edustar.jitar.pojos.User;
import cn.edustar.jitar.service.AccessControlService;
import cn.edustar.jitar.service.impl.JitarContextImpl;
import cn.edustar.jitar.util.WebUtil;

public class JitarUrlRewriter implements Filter {

	/** 日志 */
	private static final Logger log = LoggerFactory.getLogger(JitarUrlRewriter.class);

	/** WEB 应用环境 */
	private ServletContext servlet_ctxt;

	/** 保留不重定向的目录集合 */
	private Set<String> reserved_dir = new HashSet<String>();

	/**
	 * 已经放到配置文件中了：系统保留目录,这些目录不能被用户注册时候使用'manage,group,css,images,js,user...'总是保留
	 */
	
	// private static final String[] sys_reserved = new String[] {};

	/* 配置的网站地址 */
	private String configSiteRoot;

	/* 配置的博客个性域名地址 */
	private String configBlogSiteRoot, configUnitSiteRoot, configSubjectSiteRoot;

	public void init(FilterConfig config) throws ServletException {
		this.servlet_ctxt = config.getServletContext();

		Set<String> reserved_dir = new HashSet<String>();
		// for (int i = 0; i < sys_reserved.length; ++i)
		// reserved_dir.add(sys_reserved[i]);

		String cfg_resv = servlet_ctxt.getInitParameter("reserved");
		//System.out.println("cfg_resv=" + cfg_resv);
		if (cfg_resv == null || cfg_resv.length() == 0)
			return;
		String[] temp = cfg_resv.split(",");
		for (int i = 0; i < temp.length; ++i) {
			String dir = temp[i].trim();
			if (dir.length() > 0)
				reserved_dir.add(dir);
		}
		this.reserved_dir = reserved_dir;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest,
	 * javax.servlet.ServletResponse, javax.servlet.FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
			ServletException {
		HttpServletRequest http_req = (HttpServletRequest) request;
		HttpServletResponse http_res = (HttpServletResponse) response;
		// Context context = Context.newInstance(http_req); //JitarRequestContext实现了该功能。
		//
		//XSS和SQL注入屏蔽
		if(http_req.getMethod().equals("GET"))
		{
			try{
			String request_url = http_req.getRequestURI() + (http_req.getQueryString()==null?"":http_req.getQueryString());
			request_url = request_url.toLowerCase();
			
			String request_url1 = request_url + URLDecoder.decode(request_url, "utf-8").replace(" ", "");
			String request_url2 = request_url + StringEscapeUtils.unescapeHtml4(request_url).replace(" ", "");
			boolean unSafeChar1 = request_url1.indexOf("<script")>-1 
								|| request_url1.indexOf("<iframe")>-1
								|| request_url1.indexOf("<object")>-1
								|| request_url1.indexOf("javascript:")>-1
								|| request_url1.indexOf("vbscript:")>-1
								|| request_url1.indexOf("url(")>-1
								|| request_url1.indexOf("&{")>-1
								|| request_url1.indexOf("src=")>-1
								|| request_url1.indexOf(">")>-1
								|| request_url1.indexOf("'")>-1
								|| request_url1.indexOf("<")>-1
								|| request_url1.indexOf("\"")>-1
								|| request_url1.indexOf("(")>-1
								|| request_url1.indexOf(")")>-1
								|| request_url1.indexOf("&#")>-1
								|| request_url1.indexOf("\\u")>-1
								|| request_url1.indexOf("/*")>-1
								|| request_url1.indexOf("*/")>-1
								|| request_url1.indexOf("--")>-1
								|| request_url1.indexOf("xp_")>-1
								|| request_url1.indexOf("execute")>-1
								|| request_url1.indexOf("exec")>-1
								|| (request_url1.indexOf(";")>-1 && request_url1.indexOf(";jsessionid=")==-1);     //;jsessionid=**** 是允许的
			boolean unSafeChar2 = request_url2.indexOf("<script")>-1 
								|| request_url2.indexOf("<iframe")>-1
								|| request_url2.indexOf("<object")>-1
								|| request_url2.indexOf("javascript:")>-1								
								|| request_url2.indexOf("vbscript:")>-1
								|| request_url2.indexOf("url(")>-1
								|| request_url2.indexOf("&{")>-1
								|| request_url2.indexOf("src=")>-1
								|| request_url2.indexOf(">")>-1
								|| request_url2.indexOf("'")>-1
								|| request_url2.indexOf("<")>-1
								|| request_url2.indexOf("\"")>-1
								|| request_url2.indexOf("(")>-1
								|| request_url2.indexOf(")")>-1								
								|| request_url2.indexOf("&#")>-1
								|| request_url2.indexOf("\\u")>-1
								|| request_url2.indexOf("/*")>-1
								|| request_url2.indexOf("*/")>-1
								|| request_url2.indexOf("--")>-1
								|| request_url2.indexOf("xp_")>-1
								|| request_url2.indexOf("execute")>-1
								|| request_url2.indexOf("exec")>-1
								|| (request_url2.indexOf(";")>-1 && request_url2.indexOf(";jsessionid=")==-1); //;jsessionid=**** 是允许的
			
			if(unSafeChar1 || unSafeChar2)
			{
				http_res.setContentType("text/html; charset=UTF-8");
				PrintWriter pw = http_res.getWriter();
				pw.write("<!DOCTYPE html PUBLIC '-//W3C//DTD XHTML 1.0 Transitional//EN' 'http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd'>");
				pw.write("<html xmlns='http://www.w3.org/1999/xhtml'>");
				pw.print("<head>");
				pw.print("<meta http-equiv='Content-Type' content='text/html; charset=UTF-8' />");
				pw.print("<title>错误提示</title>");
				pw.print("</head>");
				pw.print("<body>");
				pw.print("请求的地址中含有不安全字符，请更换你输入的内容，或与管理员联系。");
				pw.print("</body></html>");
				return;
				//http_res.sendError(HttpServletResponse.SC_UNSUPPORTED_MEDIA_TYPE,"请求的地址中含有非法字符，请与管理员联系。");
				//return;
			}
			}
			catch(Exception x){}
		}
		
		// 本文件增加了 http_req.getContextPath() + ，是为了测试虚拟目录下的部署。如果页面访问不正常，可以暂时把 2处的
		// http_req.getContextPath() + 去掉
		// 增加的位置在 startsWith 的判断的地方。

		Cookie kill = new Cookie("kill", "1");
		kill.setPath("/");
		kill.setVersion(1);
		kill.setMaxAge(30);
		http_res.addCookie(kill);

		// 设置当前 request context, 其在 DataBean 中可以方便的获取.
		if (servlet_ctxt.getAttribute(JitarContext.JITAR_CONTEXT_KEY_NAME) == null) {
			log.info("过滤器：servlet_ctxt.getAttribute 为  null ");
		}
		JitarContext jtar_ctxt = (JitarContext) servlet_ctxt.getAttribute(JitarContext.JITAR_CONTEXT_KEY_NAME);
		String siteUrl = getCurrentSiteUrl(http_req);
		JitarRequestContext.setRequestContext(new JitarRequestContext(this.servlet_ctxt, request, response, jtar_ctxt, siteUrl));

		String context_path = http_req.getRequestURI();

		// 判断站点的状态：
		Configure conf = JitarContext.getCurrentJitarContext().getConfigService().getConfigure();
		if (conf != null ) {
			if (conf.getBoolValue("site.enabled") == false  && !context_path.endsWith("admin.py")) {
				HttpSession session = http_req.getSession();
				User user = WebUtil.getLoginUser(session);
				AccessControlService acs = (AccessControlService) JitarContextImpl.getCurrentJitarContext().getSpringContext().getBean("accessControlService");
				if (user == null || !(acs.isSystemAdmin(user))) {
					http_res.setContentType("text/html; charset=UTF-8");
					PrintWriter out;
					out = http_res.getWriter();
					out.println("<!doctype html>");
					out.println("<html>");
					out.println("<head>");
	                out.println("<meta charset=\"utf-8\">");
	                out.println("<title>网站维护提示</title>");
	                out.println("<link rel=\"icon\" href=\"" + http_req.getContextPath()  + "/favicon.ico\" />");
	                out.println("<style>");
	                out.println("html{padding-top:100px;text-align:center;}");
	                out.println("div{font-family: \"Microsoft YaHei\",\"黑体\";padding:200px 0;color:#fff;font-size:36px;font-weight:bold;");
	                out.println("background-color: rgb(0, 135, 255);");
	                out.println("border-radius: 60px 60px 60px 60px;");
	                out.println("border-width: 0px;");
	                out.println("box-shadow: 0px 0px 50px 20px rgba(0, 0, 0, 0.24);");
	                out.println("}");
	                out.println("</style>");
	                out.println("</head>");
	                out.println("<body>");
	                out.println("<div>" + conf.getStringValue("site.stop.info") + "</div>");
	                out.println("</body>");
	                out.println("</html>");
	                out.close();
					return;
				}
			}
		}

		String platformType = http_req.getSession().getServletContext().getInitParameter("platformType");
		if (null == platformType)
			platformType = "";
		if (http_req.getAttribute("platformType") == null) {
			http_req.setAttribute("platformType", platformType);
		}

		if (context_path.startsWith(http_req.getContextPath() + "/user/")) {
			if (chain == null || request == null || response == null) {
				return;
			}

			chain.doFilter(request, response);
			return;
		}

		RequestDispatcher dispatcher;
		// configSiteRoot 是必须配置的项目，如果不存在，则其他配置忽略
		configSiteRoot = http_req.getSession().getServletContext().getInitParameter("siteUrl");
		configBlogSiteRoot = http_req.getSession().getServletContext().getInitParameter("userUrlPattern");
		configUnitSiteRoot = http_req.getSession().getServletContext().getInitParameter("unitUrlPattern");
		configSubjectSiteRoot = http_req.getSession().getServletContext().getInitParameter("subjectUrlPattern");

		if (null != configBlogSiteRoot && !configBlogSiteRoot.equals("")) {
			request.setAttribute("UserUrlPattern", configBlogSiteRoot);
		}
		if (null != configUnitSiteRoot && !configUnitSiteRoot.equals("")) {
			request.setAttribute("UnitUrlPattern", configUnitSiteRoot);
		}
		if (null != configSubjectSiteRoot && !configSubjectSiteRoot.equals("")) {
			request.setAttribute("SubjectUrlPattern", configSubjectSiteRoot);
		}
		if (null != configSiteRoot && !configSiteRoot.equals("")) {
            request.setAttribute("configSiteRoot", configSiteRoot);
        }
		

		// configSiteRoot 如果是采用个性域名，这个配置必写，否则，按不进行个性域名处理

		if (configSiteRoot == null || configSiteRoot.length() == 0) {
			// log.info("执行原来模式的过滤器");
			// 原来的模式，直接从原来的文件中拷贝过来的

			// 得到当前用户访问的地址, 如
			// '/Groups/mengxianhui','/Groups/admin/article.action'.
			//还有下面特殊的需要处理！！！！！
			//还有特殊的 /Groups/;jsessionid=2A2ED0842F4EF6C675F42901A9BBBAFF
			String uri = http_req.getRequestURI();

			// 去掉前面的 ContextPath 部分，如 '/Groups', '' (ROOT)
			context_path = http_req.getContextPath() + "/"; // /Groups/

			uri = uri.substring(context_path.length()); // 空  或;jsessionid=******

			// uri 现在是 'mengxianhui', 'admin/article.action', 'login.jsp' 或;jsessionid=**************** 等.
			String first_word = uri;
			int pos = uri.indexOf('/');
			int dot = -1;
			if (pos >= 0) {
				// 'manage' or 'css', 'images'
				first_word = first_word.substring(0, pos); // 'admin'
			}
			else {
				dot = uri.indexOf('.');
			}

			int dot_file = uri.indexOf('.');
			if (dot_file > 0 && first_word.equals("user")) {
				// this.checkResource(request,response,chain);
			}
			else {
				if (dot >= 0 || first_word.length() <= 1 || first_word.startsWith("_") || first_word.startsWith(";jsessionid=")
						|| this.reserved_dir.contains(first_word)) {
					// 如果是根目录下文件 或 单字母目录(系统保留) 或者 保留的目录名，则继续进行不重写.
					// log.info(uri + "判断是否需要重写");
					if (chain == null || request == null || response == null) {
						return;
					}
					if(response.isCommitted())
					{
						return;
					}
					chain.doFilter(request, response);
				}
				else {
					// 转到 '/u/' + uri 下面.uri 只有用户名，如 mengxianhui,admin
					dispatcher = this.servlet_ctxt.getRequestDispatcher("/u/" + uri);
					Cookie namecookie = new Cookie("url", "kill");
					namecookie.setPath("/");
					namecookie.setVersion(1);
					namecookie.setMaxAge(30);
					http_res.addCookie(namecookie);
					dispatcher.forward(request, response);
				}
			}
		}
		// 下面是个性域名的分支
		else {
			String fullPath = http_req.getRequestURI();
			// log.info("fullPath = " + fullPath);
			// log.info("执行个性域名模式的过滤器");
			// 个性域名模式，如果是主域名，则直接不做处理，但对下载等进行判断
			// 来自主域名的，直接进行下一个过滤器
			if (configSiteRoot.equalsIgnoreCase(JitarUrlRewriter.getSiteRootUrl(http_req))) {
				// log.info("如果进入到这里，说明执行了主站的程序啊  request.getRequestURL().toString()");
				// 过滤user上传文件夹、下载权限判定等
				/*
				 * if(fullPath.startsWith("/user/") ||
				 * fullPath.startsWith("/userfile/")) {
				 * checkResource(request,response,chain); } else {
				 * chain.doFilter(request, response); }
				 */
				if (chain == null || request == null || response == null) {
					return;
				}

				chain.doFilter(request, response);
				return;
			}
			else {
				// log.info("如果进入到这里，说明执行了个人用户的程序分支" +
				// http_req.getRequestURL().toString());
				if (fullPath.startsWith(http_req.getContextPath() + "/user/")
						|| fullPath.startsWith(http_req.getContextPath() + "/userfile/")) {
					if (chain == null || request == null || response == null) {
						return;
					}

					chain.doFilter(request, response);
					return;
				}

				// 以下可能是工作室、协作组、集备、机构、学科的个性域名
				String userName = this.checkDomain(http_req, "blog");
				String unitName = this.checkDomain(http_req, "unit");
				String subjectName = this.checkDomain(http_req, "subject");

				if (unitName != null) {

					if (context_path.equals("/")) {
						// log.info("进入执行机构首页。" + unitName );
						dispatcher = this.servlet_ctxt.getRequestDispatcher("/d/" + unitName + context_path);
						dispatcher.forward(request, response);
					}
					else {
						String[] context_part = context_path.split("/");
						if (!this.reserved_dir.contains(context_part[1])) {
							if (context_path.startsWith("/d/")) {
								if (chain == null || request == null || response == null) {
									return;
								}

								chain.doFilter(request, response);
							}
							else {
								dispatcher = this.servlet_ctxt.getRequestDispatcher("/d/" + unitName + context_path);
								dispatcher.forward(request, response);
							}
						}
						else {
							if (chain == null || request == null || response == null) {
								return;
							}

							chain.doFilter(request, response);
						}
					}
				}
				else if (subjectName != null) {
					if (context_path.equals("/")) {
						// log.info("进入执行学科首页。" + subjectName );
						dispatcher = this.servlet_ctxt.getRequestDispatcher("/k/" + subjectName + context_path);
						dispatcher.forward(request, response);
					}
					else {
						String[] context_part = context_path.split("/");
						if (!this.reserved_dir.contains(context_part[1])) {
							if (context_path.startsWith("/k/")) {
								if (chain == null || request == null || response == null) {
									return;
								}

								chain.doFilter(request, response);
							}
							else {
								dispatcher = this.servlet_ctxt.getRequestDispatcher("/k/" + subjectName + context_path);
								dispatcher.forward(request, response);
							}
						}
						else {
							if (chain == null || request == null || response == null) {
								return;
							}
							chain.doFilter(request, response);
						}
					}
				}
				else if (userName != null) {
					String[] context_part = context_path.split("/");
					if (context_path.equals("/")) {
						Cookie namecookie = new Cookie("url", "kill");
						namecookie.setPath("/");
						namecookie.setVersion(1);
						namecookie.setMaxAge(30);
						http_res.addCookie(namecookie);
						// log.info("进入重写的地址 " + context_path + " 重写为：" + "/u/"
						// + userName + context_path);
						dispatcher = this.servlet_ctxt.getRequestDispatcher("/u/" + userName + context_path);
						dispatcher.forward(request, response);
					}
					else {
						if (!this.reserved_dir.contains(context_part[1])) {
							if (context_path.startsWith("/u/")) {
								if (chain == null || request == null || response == null) {
									return;
								}

								chain.doFilter(request, response);
							}
							else {
								dispatcher = this.servlet_ctxt.getRequestDispatcher("/u/" + userName + context_path);
								dispatcher.forward(request, response);
							}
						}
						else {
							if (chain == null || request == null || response == null) {
								return;
							}
							chain.doFilter(request, response);
						}
					}
				}
				else {
					if (chain == null || request == null || response == null) {
						return;
					}
					// 如果都不是个性域名，直接进行下一个过滤器
					chain.doFilter(request, response);
				}
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.Filter#destroy()
	 */
	public void destroy() {
	}

	/** 计算 根路径 */
	private static final String getCurrentSiteUrl(HttpServletRequest request) {
		String root = request.getScheme() + "://" + request.getServerName()
				+ (request.getServerPort() == 80 ? "" : ":" + request.getServerPort()) + request.getContextPath() + "/";
		return root;
	}

	/** 计算 顶级路径 */
	private static final String getSiteRootUrl(HttpServletRequest request) {
		String root = request.getScheme() + "://" + request.getServerName()
				+ (request.getServerPort() == 80 ? "" : ":" + request.getServerPort()) + "/";
		return root;
	}

	/**
	 * 检查一个地址是否是个性域名
	 * 
	 * @param request
	 * @return
	 */
	private String checkDomain(HttpServletRequest request, String type) {
		String userName = null;
		String registerDomainSiteRoot = null;
		String replacer = null;

		if (type == null) {
			return null;
		}
		else if (type.equalsIgnoreCase("blog")) {
			registerDomainSiteRoot = configBlogSiteRoot;
			replacer = "{loginName}";
		}
		else if (type.equalsIgnoreCase("unit")) {
			registerDomainSiteRoot = configUnitSiteRoot;
			replacer = "{unitName}";
		}
		else if (type.equalsIgnoreCase("subject")) {
			registerDomainSiteRoot = configSubjectSiteRoot;
			replacer = "{subjectCode}";
		}
		else {
			return null;
		}
		if (registerDomainSiteRoot == null || registerDomainSiteRoot.length() < 1) {
			return null;
		}
		String currentUrl = request.getRequestURL().toString();
		// System.out.println("<li>currentUrl = " + currentUrl);
		if (registerDomainSiteRoot.indexOf(replacer) == -1) {
			// log.info("配置不正确，也无需进行比较。");
			return null;
		}

		if (currentUrl.startsWith(configSiteRoot)) {
			// log.info("是总站路径，直接返回。");
			return null;
		}

		// 检查是否是匹配的地址

		// 接下来的地址肯定是个性域名的，此函数只判断是否是个人的 Blog 域名，因此只判断 registerDomainSiteRoot
		// 配置是否满足
		// 下面的方法也通过 request 对象获取

		String currentServerName = currentUrl.substring(0, currentUrl.indexOf("/", 8) + 1);

		// System.out.println("<li>currentServerName = " + currentServerName);
		String[] currentUrlParts = currentServerName.split("\\.");
		String[] configBlogUrlParts = registerDomainSiteRoot.split("\\.");

		if (currentUrlParts.length != configBlogUrlParts.length) {
			return null;
		}

		// 使用正则表达式试试
		// String pattern = registerDomainSiteRoot.replaceAll("{loginName}","")

		// 找到 {loginName}处所在的位置
		int userNamePos = 0;
		boolean findLoginName = false;
		for (userNamePos = 0; userNamePos < configBlogUrlParts.length; userNamePos++) {
			// log.info("userNamePos = " + configBlogUrlParts[userNamePos] +
			// " = " + configBlogUrlParts[userNamePos].indexOf("{loginName}"));
			if (configBlogUrlParts[userNamePos].indexOf(replacer) > -1) {
				findLoginName = true;
				// log.info("找到了 {loginName} ，终止循环 userNamePos = " +
				// userNamePos);
				break;
			}
		}
		if (findLoginName == false) {
			// log.info("循环结束，没有找到 {loginName}。");
			return null;
		}

		// 要循环全部的内容进行判断，才能确保不出错，以下循环不要退出
		int i = 0;
		for (i = 0; i < configBlogUrlParts.length; i++) {
			// System.out.println("<li>configBlogUrlParts = " + i + " = " +
			// configBlogUrlParts[i]);
			if (i == userNamePos) {
				int pos = configBlogUrlParts[userNamePos].indexOf(replacer);
				// log.info("pos = " + pos);
				if (configBlogUrlParts[i].substring(0, pos).equalsIgnoreCase(currentUrlParts[i].substring(0, pos))) {
					userName = currentUrlParts[i].substring(pos);
					continue; // 注意此处不要退出，以便进行后面的地址验证
				}
				else {
					// log.info("这个部分不对应，地址肯定是错误的：" + currentUrlParts[i] +
					// " != " + configBlogUrlParts[i]);
					break;
				}
			}
			else {
				if (!configBlogUrlParts[i].equalsIgnoreCase(currentUrlParts[i])) {
					// log.info("这个部分不对应，地址是错误的。" + currentUrlParts[i] + " != "
					// + configBlogUrlParts[i]);
					break;
				}
			}
		}

		// 如果格式完全匹配，则循环必定执行完毕了
		if (i != configBlogUrlParts.length) {
			return null;
		}
		return userName;
	}
}