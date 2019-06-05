package cn.edustar.jitar.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MD5Servlet  extends HttpServlet{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8565302153126306951L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String password = request.getParameter("password");
		if(null == password){
			response.getWriter().write("");
			return;
		}
		if(password.length()==0){
			response.getWriter().write("");
			return;
		}
		response.setContentType("text/html");
		response.getWriter().write(com.chinaedustar.util.MD5.crypt(password));
		return;
	}
	
	public static void main(String[] args) {
		String password = "admin";
		String md5Passowrd1 = com.chinaedustar.util.MD5.crypt(password);
		String md5Passowrd2 = com.chinaedustar.util.MD5.crypt(md5Passowrd1);
		System.out.println("md5Passowrd1="+md5Passowrd1);
		System.out.println("md5Passowrd2="+md5Passowrd2);
	}
}
