package cn.edustar.jitar.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class PrepareCourseServlet extends HttpServlet {
	/**
	 * 处理备课的请求的所有页面
	 */
	private static final long serialVersionUID = -5901158296375429991L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 得到访问地址，从地址里面看访问的是什么.
		// 备课访问格式，都是 /p/课程id/流程id/xxxx/
		String path_info = request.getPathInfo(); // 地址格式：/preparecourseId(/........)
		if (path_info.startsWith("/"))
			path_info = path_info.substring(1);  //去掉前导的'/'
		if (path_info.length() == 0) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST);
			return;
		}
		
		//System.out.println("path_info ==== " + path_info);
		
		// 9/0/py/show_preparecourse_action.py?tmp=1227775313000

		String[] parts = path_info.split("/");
		
		if(parts.length > 1)
		{
			request.setAttribute("prepareCourseId", parts[0]);
			request.setAttribute("prepareCourseStageId", parts[1]);
		}
		
		if (parts.length == 2) {			// 访问首页.
			getServletContext().getRequestDispatcher("/WEB-INF/preparecourse/show_preparecourse_index.py").forward(request, response);
			return;
		}

		//测试
		//System.out.println("parts[2] ==== " + parts[2]);
		
		if (parts.length == 3) {
			// 暂时没有这种 url.
		} else if (parts.length == 4) { // ['manager', 'rescate', '22.html']
			if ("rescate".equals(parts[2])) {
				// 访问资源分类.
				getServletContext().getRequestDispatcher("/WEB-INF/py/show_group_resource_category.py").forward(request, response);
				return;

			} else if ("py".equals(parts[2])) {
				String fileName = path_info.substring(path_info.lastIndexOf("/") + 1);
				this.getServletContext().getRequestDispatcher("/WEB-INF/preparecourse/" + fileName ).forward(request, response);
				return;
			}
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest,
	 *      javax.servlet.http.HttpServletResponse)
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.GenericServlet#destroy()
	 */
	@Override
	public void destroy() {

	}

}
