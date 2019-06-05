package cn.edustar.jitar.servlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 读取图片并显示
 * 
 * @author Yang XinXin
 * @version 2.0.0, 2011-03-30 11:31:56
 */
public class ShowImageServlet extends HttpServlet {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -1343550228076163043L;

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			String f = request.getParameter("flvThumbNailHref");
			FileInputStream fileInputStream = new FileInputStream(f);
			int i = fileInputStream.available();
			byte data[] = new byte[i];
			fileInputStream.read(data);
			fileInputStream.close();
			response.setContentType("image/jpeg");
			OutputStream outputStream = response.getOutputStream();
			outputStream.write(data);
			outputStream.close();
		} catch (IOException ioe) {
			String f = getServletContext().getRealPath("/");
			f += "images" + File.separatorChar + "nopic.jpg";
			FileInputStream fileInputStream = new FileInputStream(f);
			int i = fileInputStream.available();
			byte data[] = new byte[i];
			fileInputStream.read(data);
			fileInputStream.close();
			response.setContentType("image/jpeg");
			OutputStream outputStream = response.getOutputStream();
			outputStream.write(data);
			outputStream.close();
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
