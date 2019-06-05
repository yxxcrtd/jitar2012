package cn.edustar.sso.filter;

import java.io.IOException;
import java.text.SimpleDateFormat;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.octopus.sso.service.UserService;
import com.caucho.hessian.client.HessianProxyFactory;

/**
 * 判断当前用户是否登录，如果没登录，则转向到用户服务器去验证用户是否登录[如果服务器登录了，将传回票证]
 * 
 * @author baimindong
 * 
 */
public class SSOFilter implements Filter {
    public final void doFilter(final ServletRequest servletRequest, final ServletResponse servletResponse, final FilterChain filterChain)
            throws IOException, ServletException {
        final HttpServletRequest request = (HttpServletRequest) servletRequest;
        final HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpSession session = request.getSession(true);
        String clientLoginUrl = null;
        String serverLoginUrl = null;
        String reciverAction = null;

        String SSOServerURL = request.getServletContext().getInitParameter("SSOServerURL");
        String SSOServerURL1 = SSOServerURL;
        String SSOServerURL2 = SSOServerURL;
        if(SSOServerURL.indexOf(";")>-1){
        	String[] arrayUrl = SSOServerURL.split("\\;");
        	SSOServerURL1 = arrayUrl[0];
        	SSOServerURL2 = arrayUrl[1];
        }
        clientLoginUrl = request.getServletContext().getFilterRegistration("ssoFilter").getInitParameter("clientLoginUrl");
        reciverAction = request.getServletContext().getFilterRegistration("ssoFilter").getInitParameter("reciverAction");
        String clientCode = request.getServletContext().getInitParameter("clientCode");

        String contextPath = request.getContextPath();
        String basePath = "";
        if(request.getServerPort()!=80){
        	basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + contextPath + "/";
        }else{
        	basePath = request.getScheme() + "://" + request.getServerName() + contextPath + "/";
        }
        // 对教研的登录页面地址，加上 http://**
        if (!clientLoginUrl.startsWith("http://")) {
            if (clientLoginUrl.startsWith("/")) {
                clientLoginUrl = clientLoginUrl.substring(1);
            }
            clientLoginUrl = basePath + clientLoginUrl;
        }
        // 对教研的返回 地址，加上 http://**
        if (!reciverAction.startsWith("http://")) {
            if (reciverAction.startsWith("/")) {
                reciverAction = reciverAction.substring(1);
            }
            reciverAction = basePath + reciverAction;
        }

        serverLoginUrl = SSOServerURL1 + request.getServletContext().getFilterRegistration("ssoFilter").getInitParameter("serverLoginUrl");
        String userServiceUrl = SSOServerURL2 + request.getServletContext().getFilterRegistration("ssoUserFilter").getInitParameter("userServiceUrl");
        // System.out.println("SSOFilter doFilter"); // /hessian/userService
        // 判断服务器是否可用
        String isLive = null;
        cn.edustar.jitar.pojos.User user = (cn.edustar.jitar.pojos.User) session.getAttribute(cn.edustar.jitar.pojos.User.SESSION_LOGIN_USERMODEL_KEY);
        // 没登录成功，且没访问用户管理系统(判断能否实现自动登录)
        //System.out.println("SSOFilter当前过滤的地址是 =" + request.getRequestURI());

        //System.out.println("sesssion[fromServer] = " + session.getAttribute("fromServer"));
        if (null != session.getAttribute("fromServer")) {
            if ("1".equals(session.getAttribute("fromServer").toString())) {
                filterChain.doFilter(request, response);
                session.setAttribute("fromServer", "");
                return;
            }
        }
        if (null != session.getAttribute("ssoUserDeleted")) {
            if ("1".equals(session.getAttribute("ssoUserDeleted").toString())) {
            	session.removeAttribute("ssoUserDeleted");
                filterChain.doFilter(request, response);
                return;
            }
        }
        
        if (null == user) {
            /*
             * try { //调用服务器接口 HessianProxyFactory factory = new
             * HessianProxyFactory(); UserService userService = (UserService)
             * factory.create(UserService.class,
             * "http://localhost:8080/octopus/hessian/userService"); isLive =
             * userService.checkIsLive(); } catch (Exception e) {
             * e.printStackTrace(); }
             */
            isLive = check(userServiceUrl);
            if (null != isLive) {

                // 组合登录URL,转向到用户服务器的登录验证界面
                String loginUrl = serverLoginUrl + "?clientLoginUrl=" + clientLoginUrl + "&backurl=" + reciverAction + "&clientCode=" + clientCode;

                //System.out.println(loginUrl);

                response.sendRedirect(loginUrl); // 当session中无对象时，302跳转到登陆页面
                return;
            } else {
                //System.out.println("用户服务器不通,需要启动产品内部登录功能");
                filterChain.doFilter(request, response);

                // request.getRequestDispatcher("innerLogin.jsp").forward(request,
                // response);
                // filterChain.doFilter(request, response);
                // response.sendError(HttpServletResponse.SC_REQUEST_TIMEOUT,"用户服务器不通");
                return;
            }
            // 没登录成功，但访问过用户管理系统(说明用户之前没通过用户管理系统登录过，未能实现自动登录，所以要跳转到登录页面)
        } else if (null != user) {
            String importDate = "";
            if(importDate.length() > 0){
                SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
                String oldDate = sf.format(user.getCreateDate());
                if(oldDate.equals(importDate)){
                    response.sendRedirect(request.getContextPath() + "/initUser.action");
                    return;
                }
            }
            
            filterChain.doFilter(request, response);
        }
    }

    public void init(final FilterConfig filterConfig) throws ServletException {
    }

    public void destroy() {
    }

    public String check(String userServiceUrl) {
        String isLive = null;
        try {
            HessianProxyFactory factory = new HessianProxyFactory();
            UserService userService = (UserService) factory.create(UserService.class, userServiceUrl);
            isLive = userService.checkIsLive();
            
        } catch (Exception e) {
            // e.printStackTrace();
            System.out.println("********************用户管理系统不可用**********************");
        }
        return isLive;
    }
}
