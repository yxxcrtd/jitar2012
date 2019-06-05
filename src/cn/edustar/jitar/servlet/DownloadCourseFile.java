package cn.edustar.jitar.servlet;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.sun.xml.internal.messaging.saaj.packaging.mime.internet.MimeUtility;

import cn.edustar.jitar.pojos.PrepareCourse;
import cn.edustar.jitar.pojos.PrepareCourseMember;
import cn.edustar.jitar.pojos.User;
import cn.edustar.jitar.service.PrepareCourseService;
import cn.edustar.jitar.service.impl.JitarContextImpl;
import cn.edustar.jitar.util.CommonUtil;
import cn.edustar.jitar.util.ParamUtil;
import cn.edustar.jitar.util.WebUtil;

public class DownloadCourseFile extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7037519373659122480L;
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//判断是否登录
		HttpSession session = request.getSession();
		User user = WebUtil.getLoginUser(session);
		User impersonate = WebUtil.getImpersonateUser(session);
		if (user == null && impersonate == null) {
			response.sendRedirect(request.getContextPath() + "/refuse.jsp");
			return;
		}
		
		
		String p = request.getParameter("p");
		String t = request.getParameter("t");
		//f=0为个案，f=1为共案
		String f = request.getParameter("f");
		
		if(p == null || p.trim().equals("") || ParamUtil.isInteger(p) == false)
		{
			response.sendError(HttpServletResponse.SC_NOT_FOUND, "error parameter 1");
			return;
		}
		
		if(t == null || t.trim().equals("") || (!t.equalsIgnoreCase("doc") && !t.equalsIgnoreCase("pdf")))
		{
			response.sendError(HttpServletResponse.SC_NOT_FOUND, "error parameter 2");
			return;
		}
		
		if(f == null || f.trim().equals("") || (!f.equalsIgnoreCase("0") && !f.equalsIgnoreCase("1")))
		{
			response.sendError(HttpServletResponse.SC_NOT_FOUND, "error parameter 3");
			return;
		}
		
		Integer pid = Integer.valueOf(p);		
		
		PrepareCourseService prepareCourseService = (PrepareCourseService)JitarContextImpl.getCurrentJitarContext().getSpringContext().getBean("prepareCourseService");
		String fileName = "";
		String fileDoc = "";
		PrepareCourse pc = null;
		if(f.equals("0"))
		{
			PrepareCourseMember pm = prepareCourseService.getPrepareCourseMemberById(pid);
			if(pm == null)
			{
				response.sendError(HttpServletResponse.SC_NOT_FOUND, "can not load object PrepareCourseMember");
				return;
			}
			pc = prepareCourseService.getPrepareCourse(pm.getPrepareCourseId());
			
			if(pc == null)
			{
				response.sendError(HttpServletResponse.SC_NOT_FOUND, "can not load object PrepareCourse");
				return;
			}
			if(pm.getContentType() != 2 && pm.getContentType() != 3 && pm.getContentType() != 4 && pm.getContentType() != 5 && pm.getContentType() != 100)
			{
				response.sendError(HttpServletResponse.SC_NOT_FOUND, "no file content");
				return;
			}
			fileName = pc.getTitle() + "个案";
			fileDoc = pm.getPrivateContent();
		}
		else if(f.equals("1"))
		{
			pc = prepareCourseService.getPrepareCourse(pid);
			
			if(pc == null)
			{
				response.sendError(HttpServletResponse.SC_NOT_FOUND, "can not load object PrepareCourse");
				return;
			}
			
			fileName = pc.getTitle() + "共案";
			fileDoc = pc.getCommonContent();
			
		}
		else
		{
			response.sendError(HttpServletResponse.SC_NOT_FOUND, "Invalid type");
			return;
		}
		
		//输出内容
		String fpath = CommonUtil.GetPrepareCourseFolder(request)[0] + pc.getPrepareCourseId() + File.separator;
		String pdf = fileDoc;
		if(pdf.indexOf(".")>-1)
		{
			pdf = pdf.substring(0,pdf.indexOf(".")) + ".pdf";
		}
		if(t.equalsIgnoreCase("pdf"))
		{
			fileDoc = pdf;
		}
		
		String PrepareCourseFileServer = request.getServletContext().getInitParameter("PrepareCourseFileServer");
		if(PrepareCourseFileServer != null && PrepareCourseFileServer.length() > 0)
		{
			response.sendRedirect(PrepareCourseFileServer + "DownloadCourseFile?pcid=" + pc.getPrepareCourseId() + "&file=" + fileDoc + "&fileName=" + URLEncoder.encode(fileName, "utf-8"));
			return;
		}
		//System.out.println("fpath = " + fpath);
		//System.out.println("fileDoc = " + fileDoc);
		File file = new File(fpath,fileDoc);
		String fileExt = "";
		if(file.exists() && file.isFile())
		{
			fileExt = file.getName().substring(file.getName().lastIndexOf("."));			
			String utf8File = CommonUtil.urlUtf8Encode(fileName) + fileExt;
			fileName = fileName + fileExt;
			String userAgent = request.getHeader("User-Agent");
			String returnFile = "filename=\"" + utf8File + "\""; 

			// 如果没有UA，则默认使用IE的方式进行编码，因为毕竟IE还是占多数的 
			if (userAgent != null) 
			{ 
				userAgent = userAgent.toLowerCase();
				if (userAgent.indexOf("msie") != -1) 
			     { 
					returnFile = "filename=\"" + utf8File + "\""; 
			     }			   
				else if (userAgent.indexOf("opera") != -1) 
			     { 
			    	 returnFile = "filename*=UTF-8''" + utf8File; 
			     }			  
			      else if (userAgent.indexOf("safari") != -1 ) 
			      { 
			    	  returnFile = "filename=\"" + new String(fileName.getBytes("UTF-8"),"ISO8859-1") + "\""; 
			      } 			    
			      else if (userAgent.indexOf("applewebkit") != -1 ) 
			       { 
			    	  returnFile = MimeUtility.encodeText(fileName, "UTF8", "B"); 
			    	  returnFile = "filename=\"" + returnFile + "\""; 
			       } 			      
			       else if (userAgent.indexOf("mozilla") != -1) 
			       { 
			    	   returnFile = "filename*=UTF-8''" + utf8File; 
			       }
			}
			
			fileName = fileName.replaceAll(" ", "");
			ServletOutputStream stream = null;
			BufferedInputStream buf = null;
			FileInputStream fis = null;
			try {
				// 设置响应首部
				response.setStatus(HttpServletResponse.SC_OK);
				response.setContentType("application/octet-stream");
				response.setHeader("Content-Disposition","attachment; " + returnFile);
				response.setContentLength((int) file.length());			
				stream = response.getOutputStream();
				fis = new FileInputStream(file);
				buf = new BufferedInputStream(fis);
				byte[] b = new byte[4096];
				int readBytes = 0;
				while ((readBytes = buf.read(b)) != -1) {
					stream.write(b, 0, readBytes);
				}
				stream.flush();
				response.flushBuffer();
			} finally {
				// 关闭输入/输出流
				if (stream != null) {
					stream.close();
				}
				if (buf != null) {
					buf.close();
				}
				if (fis != null) {
					fis.close();
				}
			}
		}
		else
		{
			response.sendError(HttpServletResponse.SC_NOT_FOUND, "File not found");
			return;
		}
		
		
	}
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
/*
 * 浏览器能正确识别的编码格式，只要按照这样的编码来设置对应的Content-Disposition，那么应该就不会出现中文文件名的乱码问题了。
首先，Content-Disposition值可以有以下几种编码格式
1. 直接urlencode：

    Content-Disposition: attachment; filename="struts2.0%E4%B8%AD%E6%96%87%E6%95%99%E7%A8%8B.chm"

2. Base64编码：

    Content-Disposition: attachment; filename="=?UTF8?B?c3RydXRzMi4w5Lit5paH5pWZ56iLLmNobQ==?="

3. RFC2231规定的标准：

    Content-Disposition: attachment; filename*=UTF-8''%E5%9B%9E%E6%89%A7.msg

4. 直接ISO编码的文件名：

    Content-Disposition: attachment;filename="测试.txt"

然后，各浏览器支持的对应编码格式为：

1.  IE浏览器，采用URLEncoder编码
2.  Opera浏览器，采用filename*方式
3.  Safari浏览器，采用ISO编码的中文输出
4.  Chrome浏览器，采用Base64编码或ISO编码的中文输出
5.  FireFox浏览器，采用Base64或filename*或ISO编码的中文输出
 * */
}
