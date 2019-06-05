package cn.edustar.jitar.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;

/**
 * 相对路径
 * 
 * @author Yang Xinxin
 * @version 1.0.0 Mar 17, 2008 4:42:06 PM
 */

public class PageContent {
	
	/** 输出文章 */
	private static final Log log = LogFactory.getLog(PageContent.class);

	/** 默认使用 国际化的 UTF-8 编码 */
	public static final String PAGE_UTF8 = "<meta http-equiv=\"Content-Type\" content=\"text/html;charset=UTF-8\">";

	/**
	 * 得到相对路径
	 * 
	 * @return
	 */
	public static String getAppPath() {
		String path = ServletActionContext.getRequest().getContextPath();

		try {
			path = ServletActionContext.getRequest().getContextPath();
		} catch (java.lang.Exception e) {
			e.printStackTrace();
			log.error("得到 得到相对路径 失败！", e);
		}

		if (path.equals("/")) {
			path = "";
		}

		return path;
	}
}
