package cn.edustar.jitar.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.edustar.jitar.model.SiteUrlModel;
import cn.edustar.jitar.pojos.User;
import cn.edustar.jitar.util.WebUtil;

public class MakeJnlpServlet extends HttpServlet {

	private static final long serialVersionUID = -7431314770747340663L;
	/**
	 * Constructor of the object.
	 */
	public MakeJnlpServlet() {
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	/**
	 * The doGet method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		  String siteurl=SiteUrlModel.getSiteUrl();
		  User user=WebUtil.getLoginUser(request.getSession());
		  PrintWriter out = response.getWriter();    
		  StringBuffer str = new StringBuffer();
		  if(user!=null)
		  {   
	        @SuppressWarnings("unused")   
	        String app = request.getContextPath();    
	        @SuppressWarnings("unused")
	        String href = "";    
	        response.setContentType("application/x-java-jnlp-file");    
	        str.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");    
	        str.append("<jnlp spec=\"1.0+\" codebase=\"" + siteurl + "\" >\n");    
	        str.append("<information>\n");    
	        str.append("   <title>教研网资源上传组件</title>\n");    
	        str.append("   <vendor>中教育星（北京）科技发展有限公司(www.chinaedustar.com)</vendor>\n");    
	        str.append("   <homepage href=\""+siteurl+"\"/>\n");    
	        str.append("   <description>Web Start Version</description>\n");    
	        str.append("   <description kind=\"short\">Web Start Version</description>\n");    
	        str.append("</information>\n");    
	        str.append("<security>\n");    
	        str.append("   <all-permissions/>\n");    
	        str.append("</security>\n");    
	        str.append("<resources>\n");    
	        str.append("   <j2se version=\"1.5+\"/>\n");    
	        str.append("   <jar href=\"manage/upload/edustar-fileuploads-v1.0.0.jar\"/>\n");      
	        str.append("   <jar href=\"manage/upload/commons-codec-1.3.jar\"/>\n");      
	        str.append("   <jar href=\"manage/upload/commons-httpclient-3.1.jar\"/>\n");      
	        str.append("   <jar href=\"manage/upload/commons-logging-1.1.jar\"/>\n");      
	        str.append("</resources>\n");    
	        str.append("<application-desc main-class=\"cn.edustar.jupload.AppMain\">\n");         
	        str.append("  <argument>"+ user.getLoginName() +"</argument>\n");       
	        str.append("  <argument>"+ user.getUserId() +"</argument>\n");       
	        str.append("  <argument>"+ siteurl +"</argument>\n");       
	        str.append("</application-desc>\n");      
	        str.append("</jnlp>\n");   
	        
	        System.out.println(str.toString());
	        
	        out.println(str.toString());
	        out.flush();    
	        out.close();    
		  }
		  else
		  {
			  response.setContentType("text/html");    
			  out.println("<script >alert('您还没有登陆!')</script>");    
	        out.flush();    
	        out.close(); 
		  }
	}

	/**
	 * The doPost method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to post.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request,response);
	}

	/**
	 * Initialization of the servlet. <br>
	 *
	 * @throws ServletException if an error occurs
	 */
	public void init() throws ServletException {
		// Put your code here
	}

}
