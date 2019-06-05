package cn.edustar.jitar.servlet;

import java.io.File;
import java.io.FileFilter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

/**
 * 清理临时文件
 * 
 * @author Yang Xinxin
 * @version 1.0.0 May 12, 2009 5:11:07 PM
 */
public class DeleteTmpFileServlet extends HttpServlet {

	/** serialVersionUID */
	private static final long serialVersionUID = 4648165735041118417L;

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.GenericServlet#init()
	 */
	public void init() throws ServletException {
		File fDir = new File(File.separator);
		File file = new File(fDir + "tmp" + fDir);
		if (file.exists()) {
			File[] subFiles = file.listFiles(new FileFilter() {
				public boolean accept(File pathName) {
					if (pathName.getName().endsWith("tmp")) {
						return true;
					}
					return false;
				}
			});
			for (File f : subFiles) {
				f.delete();
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.GenericServlet#destroy()
	 */
	public void destroy() {
		super.destroy();
	}

}
