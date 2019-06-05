package cn.edustar.jitar.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.context.ContextLoader;

import cn.edustar.jitar.pojos.User;
import cn.edustar.jitar.service.UserService;

/**
 * 临时提供用户信息查询功能。
 * @author mxh
 *
 */

@WebServlet(name = "checkUser", urlPatterns = {"/manage/checkUser"})
public class CheckUser extends HttpServlet{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2369568478586010709L;

	public void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String token = request.getParameter("token");
		
		response.setContentType("application/json;charset=UTF-8");
		if(token==null || token.length() == 1)
		{
			response.getWriter().write("{\"status\":\"error\",\"description\":\"缺少 token 参数。\"}");
			return;
		}
		UserService userService = ContextLoader.getCurrentWebApplicationContext().getBean("userService", UserService.class);
		//不验证超时时间了
		User loginUserToken = userService.getUserByLoginName(token, true);
		if(loginUserToken == null)
		{
			response.getWriter().write("{\"status\":\"error\",\"description\":\"没有记录。\"}");
			return;
		}
		
		response.getWriter().write("{\"status\":\"success\",\"LoginUser\":{\"LoginName\":\"" + loginUserToken.getLoginName() + "\",\"UserGuid\":\"" + loginUserToken.getUserGuid() + "\"}}");
			
	}
}
