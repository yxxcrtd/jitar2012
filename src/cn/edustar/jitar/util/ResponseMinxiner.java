package cn.edustar.jitar.util;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts2.ServletActionContext;


public class ResponseMinxiner {
	String ACCESS_ERROR = "/WEB-INF/ftl/mengv1/access_error.ftl";
	
	/*# 报告客户端指定 uri 未找到.
	  # 函数返回 None*/
	public static String sendNotFound(String uri){
		HttpServletResponse response = ServletActionContext.getResponse();
		HttpServletRequest request = ServletActionContext.getRequest();
		if(uri == null){
			try {
				response.sendError(404, request.getRequestURI());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}else{
			 try {
				response.sendError(404, uri);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
}
