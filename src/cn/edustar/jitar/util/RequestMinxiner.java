package cn.edustar.jitar.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts2.ServletActionContext;

//request 辅助类, 用于获取及解析 uri
public class RequestMinxiner {

	/*
	 * # 可靠的得到客户端原来请求的 uri 地址, 当请求被 forward 过来的时候不能通过简单的 # #
	 * request.getRequestURI 得到该地址, 其可能返回为: /Groups/WEB-INF/py/show_article.py #
	 * 而不是原始的请求 uri. # 注意: 此返回地址包括了 context_path 部分, 为去掉 context_path TODO:
	 */

	public static String getRequestURI() {
		HttpServletRequest request = ServletActionContext.getRequest();
		// # 先尝试从 attribute['javax.servlet.forward.request_uri'] 获取, 如果有则解码返回.
		String uri = (String) request
				.getAttribute("javax.servlet.forward.request_uri");
		if (uri != null && !"".equals(uri)) {
			try {
				return URLDecoder.decode(uri, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		// # 否则直接用 request 对象里面的.
		uri = request.getRequestURI();
		if (uri != null) {
			try {
				return URLDecoder.decode(uri, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;
	}

	/*
	 * # 从指定的 uri 参数中去掉 '.html', '.htm' 后缀. # 如果后缀不是 '.html', '.htm' 则返回 ''.
	 */
	public String removeHtmlExt(String uri) {
		if (uri == null || uri.trim().equals("")) {
			return "";
		}
		if (uri.endsWith(".html")) {
			return uri.substring(0, uri.length() - ".html".length());
		}
		if (uri.endsWith(".htm")) {
			return uri.substring(0, uri.length() - ".htm".length());
		}
		return "";
	}

}
