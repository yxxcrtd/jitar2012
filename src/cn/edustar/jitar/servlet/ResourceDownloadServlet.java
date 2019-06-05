package cn.edustar.jitar.servlet;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import cn.edustar.jitar.JitarContext;
import cn.edustar.jitar.model.Configure;
import cn.edustar.jitar.pojos.Resource;
import cn.edustar.jitar.pojos.User;
import cn.edustar.jitar.service.ResourceService;
import cn.edustar.jitar.service.StatService;
import cn.edustar.jitar.util.CommonUtil;
import cn.edustar.jitar.util.ParamUtil;
import cn.edustar.jitar.util.WebUtil;

/**
 * 资源下载
 *
 * @author Yang Xinxin
 * @version 1.0.0 Apr 15, 2008 8:59:10 AM
 */
public class ResourceDownloadServlet extends HttpServlet {
	
	/** serialVersionUID */
	private static final long serialVersionUID = -4297573662217219167L;

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.GenericServlet#init()
	 */
	@Override
	public void init() {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 判断参数是否正确
		String resourceId = request.getParameter("resourceId");
		if (resourceId == null || resourceId.trim().length() == 0 || ParamUtil.isInteger(resourceId) == false) {
			response.sendError(HttpServletResponse.SC_NOT_FOUND, "resourceId parameter error");
			return;
		}

	// 加载该标识的资源
		Resource resource = getResource(ParamUtil.safeParseInt(resourceId, 0));
		if (resource == null) {
			response.sendError(HttpServletResponse.SC_NOT_FOUND, "resource object unexist");
			return;
		}
		
		// 判断是否允许下载
		Configure conf = JitarContext.getCurrentJitarContext().getConfigService().getConfigure();
		Object obj = conf.getValue("resource.download");
		if (obj != null) {
			boolean largefileupload = Boolean.valueOf(obj.toString());
			if (largefileupload) {
				// 允许下载
			} else {
				/** 登录验证 */
				HttpSession session = request.getSession();
				User user = WebUtil.getLoginUser(session);
				User impersonate = WebUtil.getImpersonateUser(session);
				if (user == null && impersonate == null) {
					response.sendRedirect(request.getContextPath() + "/refuse.jsp");
					return;
				}
				if(user == null && impersonate != null)
				{
					//模拟用户只允许查看被推送的资源
				
					if(resource.getPushState() != 1)
					{
						response.sendRedirect(request.getContextPath() + "/refuse.jsp");
						return;
					}
				
				}
			}
		}

		

		// 更改统计信息
		getStatService().incResourceDownloadCount(resource);

		// 写出文件
		writeResourceFile(request, response, resource);
	}

	/**
	 * 向客户端输出资源文件. TODO: 为了支持断点续传, 还需要分析请求中的 range 属性
	 *
	 * @param request
	 * @param response
	 * @param resource
	 * @throws IOException
	 */
	private void writeResourceFile(HttpServletRequest request, HttpServletResponse response, Resource resource) throws IOException {
		// 计算文件地址
		String href = resource.getHref();
		String ext = CommonUtil.getFileExtension(href);
		String fileName = resource.getTitle();
		if (ext.equalsIgnoreCase(CommonUtil.getFileExtension(fileName)) == false) {
			fileName += "." + ext;
		}
		//System.out.print("开始下载文件:"+href);
		
		ServletContext sc = request.getSession().getServletContext();
		String fileUserConfigPath = sc.getInitParameter("userPath");
		String filePath="";
		if(fileUserConfigPath=="" || fileUserConfigPath==null)
			filePath = super.getServletContext().getRealPath(href);
		else
		{
			href=href.replace('/', '\\');
			if(!fileUserConfigPath.endsWith("\\"))
				if(href.startsWith("\\"))
					filePath=fileUserConfigPath+href;
				else
					filePath=fileUserConfigPath+"\\"+href;
			else
				if(href.startsWith("\\"))
					filePath=fileUserConfigPath+href.substring(1);
				else
					filePath=fileUserConfigPath+href;
		}
		//System.out.print("开始下载文件:"+filePath);
		// 读取文件
		File file = new File(filePath);
		// 如果文件存在
		if (file.exists() == false || file.isFile() == false) {
			response.sendError(HttpServletResponse.SC_NOT_FOUND, "Resource File Not Found");
			return;
		}

		// 输出下载流
		ServletOutputStream stream = null;
		BufferedInputStream buf = null;
		FileInputStream fis = null;
		try {
			// 设置响应首部
			response.setStatus(HttpServletResponse.SC_OK);
			response.setContentType("application/octet-stream");
			if (request.getHeader("User-Agent").indexOf("MSIE 5.5") != -1) {
				// IE5.5特别处理
				response.setHeader("Content-Disposition", "filename=" + CommonUtil.urlUtf8Encode(fileName));
			} else {
				// 其它的Header设定方式
				response.addHeader("Content-Disposition", "attachment;" + CommonUtil.encodeContentDisposition(request, fileName));
			}
			// 设置附件长度，可有可无. <- 当然最好有
			// if (fileLength != 0)
			response.setContentLength((int) file.length());			
			stream = response.getOutputStream();
			fis = new FileInputStream(file);
			buf = new BufferedInputStream(fis);
			byte[] b = new byte[4096];
			// 从文件中读取；写入ServletOutputStream
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

	/**
	 * 获得指定标识的资源对象
	 * 
	 * @param resourceId
	 * @return
	 */
	private Resource getResource(int resourceId) {
		Resource resource = getResourceService().getResource(resourceId);
		return resource;
	}

	/**
	 * 得到资源服务
	 * 
	 * @return
	 */
	private ResourceService getResourceService() {
		return JitarContext.getCurrentJitarContext().getResourceService();
	}

	/**
	 * 得到统计服务
	 * 
	 * @return
	 */
	private StatService getStatService() {
		return JitarContext.getCurrentJitarContext().getStatService();
	}
	
}
