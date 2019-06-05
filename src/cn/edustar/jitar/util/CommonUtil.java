package cn.edustar.jitar.util;

import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.SimpleTimeZone;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;
import javax.servlet.ServletContext;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

import org.apache.catalina.util.URLEncoder;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.mozilla.universalchardet.UniversalDetector;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import cn.edustar.jitar.JitarRequestContext;
import cn.edustar.jitar.model.SiteUrlModel;

import com.sun.xml.internal.messaging.saaj.packaging.mime.internet.MimeUtility;

import freemarker.template.utility.StringUtil;
//import org.apache.commons.collections.ListUtils;

/**
 * <p>教研系统使用的一些辅助函数</p>
 * 
 * 引入com.sun.xml.internal.messaging.saaj.packaging.mime.internet.MimeUtility包出现下面错误的解决方法
 * Access restriction: The type QName is not accessible due to restriction on required library C:\Program Files\Java\jdk1.5.0_16\jre\lib\rt.jar
 * 
 * 解决方法：工程右键-Build With-Configure Build Path...-Libraries，先Remove JRE System Library，再添加合适的JRE
 *
 */
public class CommonUtil {
	public static final String EMPTY_STRING = "";

	@SuppressWarnings("rawtypes")
	public static final List EMPTY_LIST = Collections.EMPTY_LIST;

	private CommonUtil() {
	}

	/**
	 * 对指定字符串进行 UTF-8 的 URL 编码
	 * 
	 * @param url
	 * @return
	 */
	public static String urlUtf8Encode(String url) {
		try {
			if (url == null || url.length() == 0)
				return "";
			return StringUtil.URLEnc(url, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			return url;
		}
	}
	
	public static String urlGB2312Encode(String url) {	
		URLEncoder encoder = new URLEncoder();
		if (url == null || url.length() == 0)
			return "";
		return encoder.encode(url);		
	}


	public static Boolean isYYYYMMDDDate(String d) {
		if (d == null)
			return false;
		try {
			new SimpleDateFormat("yyyy-MM-dd").parse(d);
			return true;
		} catch (Exception x) {
			return false;
		}
	}

	/**
	 * 安全的比较两个字符串
	 * 
	 * @param a
	 * @param b
	 * @return
	 */
	public static boolean stringEquals(String a, String b) {
		// null == null
		if (a == null && b == null)
			return true;

		// null != not null string
		if (a == null || b == null)
			return false;

		return a.equals(b);
	}

	/**
	 * 判定指定的字符串是否为空, 如 null, '', '(全部为空格的字符串)'
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isEmptyString(String str) {
		if (str == null || str.length() == 0)
			return true;
		if (str.trim().length() == 0)
			return true;
		return false;
	}

	/**
	 * 判定给定的名字是否是一个合法的名字，系统中：user.loginName,group.groupName都要求使用：英文、数字、下划线做为名字字符
	 * ，第一个字符必须是英文
	 * 
	 * @param name
	 * @return
	 * @remark 函数比较简单，在'GroupService,UserService'中使用
	 */
	public static boolean isValidName(String name) {
		if (name == null)
			return false;

		if (name.length() == 0)
			return false;

		for (int i = 0; i < name.length(); ++i) {
			char ch = name.charAt(i);
			if (isEnglishChar(ch))
				continue;
			if (i > 0 && isDigitChar(ch))
				continue;
			//if (i > 0 && ch == '_')
			//	continue;
			return false; // 不合法
		}
		return true; // 合法
	}

	/**
	 * isValidName 是否的辅助函数
	 * 
	 * @param ch
	 * @return
	 */
	private static final boolean isDigitChar(char ch) {
		return (ch <= '9' && ch >= '0');
	}

	/**
	 * isValidName 使用的辅助函数
	 * 
	 * @param ch
	 * @return
	 */
	private static final boolean isEnglishChar(char ch) {
		return (ch <= 'Z' && ch >= 'A') || (ch <= 'z' && ch >= 'a');
	}

	/**
	 * 空字符串数组
	 */
	public static final String[] EMPTY_STRING_ARRAY = new String[] {};

	/**
	 * 解析一个标签字符串，返回String[],标签字符串使用','等字符分隔
	 * 
	 * @param tagstr
	 * @return
	 */
	public static final String[] parseTagList(final String tagstr) {
		if (tagstr == null || tagstr.length() == 0)
			return EMPTY_STRING_ARRAY;

		List<String> tag_list = new ArrayList<String>();

		// sep_string 也许可以被配置在外面。下面的列表基本够用.
		String sep_string = ",;，。；|｜\r\n\t ";
		int start = 0, next = 0;

		while (next < tagstr.length()) {
			char ch = tagstr.charAt(next);
			if (sep_string.indexOf(ch) >= 0) {
				String tag_name = tagstr.substring(start, next).trim();
				start = next + 1;
				if (tag_name.length() > 0 && !tag_list.contains(tag_name))
					tag_list.add(tag_name);
			}
			++next;
		}
		if (start < next) {
			String tag_name = tagstr.substring(start).trim();
			if (tag_name.length() > 0 && !tag_list.contains(tag_name))
				tag_list.add(tag_name);
		}
		return (String[]) tag_list.toArray(EMPTY_STRING_ARRAY);
	}

	/**
	 * 标准化表示一个标签字符串
	 * 
	 * @param tagstr
	 * @return 去掉了空格、标签用标准','来分隔所有标签
	 */
	public static final String standardTagsString(String tagstr) {
		return standardTagsString(parseTagList(tagstr));
	}

	/**
	 * 标准化表示一个标签字符串，使用','分隔所有标签
	 * 
	 * @param tags
	 * @return
	 * @remark GroupServiceImpl 使用其规范化标签字符串
	 */
	public static final String standardTagsString(String[] tags) {
		return arrayConcat(tags, ",");
	}

	/**
	 * 将一个字符串数组使用'concator'拼接起来
	 * 
	 * @param strs
	 *            - 字符串数组
	 * @param concator
	 *            - 拼接字符串, 如 ','
	 * @return 例如 strs = ["Hello", "World"], concator = "," 返回为 "Hello,World"
	 */
	public static final String arrayConcat(String[] strs, String concator) {
		if (strs == null || strs.length == 0)
			return "";
		if (concator == null)
			concator = "";
		if (strs.length == 1)
			return strs[0];
		if (strs.length == 2)
			return strs[0] + concator + strs[1];
		StringBuffer strbuf = new StringBuffer();
		strbuf.append(strs[0]);
		for (int i = 1; i < strs.length; ++i)
			strbuf.append(concator).append(strs[i]);
		return strbuf.toString();
	}

	/**
	 * 判断一个 Integer 对象是否为 null 或者值等于 0
	 * 
	 * @param i
	 * @return true 表示为 null 或者值等于 0; false 表示不是
	 */
	public static final boolean isZeroOrNull(Integer i) {
		if (i == null)
			return true;
		return i.intValue() == 0;
	}

	/**
	 * 比较两个字符串是否相等，如果两个都等于'null'则认为相等
	 * 
	 * @param a
	 * @param b
	 * @return
	 */
	public static final boolean equals(String a, String b) {
		if (a == null)
			return b == null;
		if (b == null)
			return false;
		return a.equals(b);
	}

	/**
	 * 比较两个数字是否相等，如果两个都等于 null 则认为相等
	 * 
	 * @param i
	 * @param j
	 * @return
	 */
	public static final boolean equals(Integer i, Integer j) {
		if (i == null)
			return j == null;
		if (j == null)
			return false;
		return i.intValue() == j.intValue();
	}

	/**
	 * 创建缩略图
	 * 
	 * @param imgFileDir
	 * @param imgfile
	 * @param newWidth
	 * @param newHeight
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("static-access")
	public static boolean CreateNewThumbnail(String imgFileDir, String imgfile,
			Integer newWidth, Integer newHeight) throws Exception {
		String prefix = "s";
		double Ratio = 0.0;
		File F = new File(imgFileDir, imgfile);
		if (!F.isFile())
			throw new Exception(F + " 不是一个文件路径!");
		String FileName = imgfile.substring(imgfile.lastIndexOf("\\") + 1,
				imgfile.length());
		String fileExt = FileName.substring(FileName.lastIndexOf(".") + 1,
				FileName.length());
		File ThF = new File(imgFileDir, prefix + FileName);
		BufferedImage Bi = ImageIO.read(F);

		Image Itemp = Bi
				.getScaledInstance(newWidth, newHeight, Bi.SCALE_SMOOTH);

		// 等比例缩放
		if ((Bi.getHeight() > newHeight) || (Bi.getWidth() > newWidth)) {
			if (Bi.getHeight() > Bi.getWidth())
				Ratio = (double) newHeight / Bi.getHeight();
			else
				Ratio = (double) newWidth / Bi.getWidth();
		}

		AffineTransformOp op = new AffineTransformOp(
				AffineTransform.getScaleInstance(Ratio, Ratio), null);
		Itemp = op.filter(Bi, null);
		try {
			ImageIO.write((BufferedImage) Itemp, fileExt, ThF);
		} catch (Exception ex) {
			throw new Exception("ImageIo.write error in CreateThumbnail : "
					+ ex.getLocalizedMessage());
		}
		return true;
	}

	/**
	 * 实现 JavaScript 的 escape
	 * 
	 * @param src
	 * @return
	 */
	public static String escape(String src) {
		int i;
		char j;
		StringBuffer tmp = new StringBuffer();
		tmp.ensureCapacity(src.length() * 6);
		for (i = 0; i < src.length(); i++) {
			j = src.charAt(i);
			if (Character.isDigit(j) || Character.isLowerCase(j)
					|| Character.isUpperCase(j))
				tmp.append(j);
			else if (j < 256) {
				tmp.append("%");
				if (j < 16)
					tmp.append("0");
				tmp.append(Integer.toString(j, 16));
			} else {
				tmp.append("%u");
				tmp.append(Integer.toString(j, 16));
			}
		}
		return tmp.toString();
	}

	/**
	 * 实现 JavaScript 的 unescape
	 * 
	 * @param src
	 * @return
	 */
	public static String unescape(String src) {
		StringBuffer tmp = new StringBuffer();
		tmp.ensureCapacity(src.length());
		int lastPos = 0, pos = 0;
		char ch;
		while (lastPos < src.length()) {
			pos = src.indexOf("%", lastPos);
			if (pos == lastPos) {
				if (src.charAt(pos + 1) == 'u') {
					ch = (char) Integer.parseInt(
							src.substring(pos + 2, pos + 6), 16);
					tmp.append(ch);
					lastPos = pos + 6;
				} else {
					ch = (char) Integer.parseInt(
							src.substring(pos + 1, pos + 3), 16);
					tmp.append(ch);
					lastPos = pos + 3;
				}
			} else {
				if (pos == -1) {
					tmp.append(src.substring(lastPos));
					lastPos = src.length();
				} else {
					tmp.append(src.substring(lastPos, pos));
					lastPos = pos;
				}
			}
		}
		return tmp.toString();
	}

	public static String xmlEncode(String strXml) {
		if (strXml == null)
			return "";
		return freemarker.template.utility.StringUtil.XMLEnc(strXml);
	}

	public static String responseXml(StatusCode status, String desc) {
		return ResponseXml(status, desc, "", "");
	}

	public static String ResponseXml(StatusCode status, String desc,
			String redUrl) {
		return ResponseXml(status, desc, redUrl, "");
	}

	public static String ResponseXml(StatusCode status, String desc,
			String redUrl, String retdata) {
		return "<response>" + "<status>" + status + "</status>"
				+ "<description>" + xmlEncode(desc) + "</description>"
				+ "<redirecturl>" + xmlEncode(redUrl) + "</redirecturl>"
				+ "<data>" + xmlEncode(retdata) + "</data>" + "</response>";
	}

	public static enum StatusCode {
		OK, ERROR, NONE;
	}

	/**
	 * 对指定字符串执行 HTML 编码.
	 */
	@SuppressWarnings("deprecation")
	public static final String htmlEncode(String s) {
		if (s == null || s.length() == 0)
			return "";
		return freemarker.template.utility.StringUtil.HTMLEnc(s);
		/*
		 * int ln = s.length(); for (int i = 0; i < ln; i++) { char c =
		 * s.charAt(i); if (c == '<' || c == '>' || c == '&' || c == '"') {
		 * StringBuffer b = new StringBuffer(s.substring(0, i)); switch (c) {
		 * case '<': b.append("&lt;"); break; case '>': b.append("&gt;"); break;
		 * case '&': b.append("&amp;"); break; case '"': b.append("&quot;");
		 * break; } i++; int next = i; while (i < ln) { c = s.charAt(i); if (c
		 * == '<' || c == '>' || c == '&' || c == '"') {
		 * b.append(s.substring(next, i)); switch (c) { case '<':
		 * b.append("&lt;"); break; case '>': b.append("&gt;"); break; case '&':
		 * b.append("&amp;"); break; case '"': b.append("&quot;"); break; } next
		 * = i + 1; } i++; } if (next < ln) b.append(s.substring(next)); s =
		 * b.toString(); break; } // if c == } // for return s;
		 */
	}

	/**
	 * 得到不包含HTML代码的字符串
	 * 
	 * @param source
	 * @return
	 */
	public static final String eraseHtml(String source) {
		if (source == null || source.trim().length() <= 0)
			return source;
		Pattern pattern = Pattern.compile("<([^>]*)>");  // <([^>]*)>     <[^<|^>]*>   
		Matcher matcher = pattern.matcher(source);
		StringBuffer sb = new StringBuffer();
		while (matcher.find()) {
			String group = matcher.group();
			if (group.matches("<[\\s]*>")) {
				matcher.appendReplacement(sb, group);
			} else {
				matcher.appendReplacement(sb, " ");
			}
		}
		matcher.appendTail(sb);
		return sb.toString();
	}

	/**
	 * 将一组标识符转换为 SQL IN 子句的格式.
	 * 
	 * @param ids
	 * @return 例如 ids = [1, 2, 3] 则返回为 '(1, 2, 3)'
	 * @throws IllegalArgumentException
	 *             如果 ids == null 或者 ids.length == 0
	 */
	public static final String toSqlInString(int[] ids) {
		if (ids == null || ids.length == 0)
			throw new IllegalArgumentException("ids");

		StringBuffer strbuf = new StringBuffer(ids.length * 6);
		strbuf.append("(").append(ids[0]);
		for (int i = 1; i < ids.length; ++i)
			strbuf.append(",").append(ids[i]);
		strbuf.append(")");
		return strbuf.toString();
	}

	/**
	 * 将一组标识符转换为 SQL IN 子句的格式.
	 * 
	 * @param ids
	 * @return 例如 ids = [1, 2, 3] 则返回为 '(1, 2, 3)'
	 * @throws IllegalArgumentException
	 *             如果 ids == null 或者 ids.length == 0
	 */
	public static final String toSqlInString(List<Integer> ids) {
		if (ids == null || ids.size() == 0)
			throw new IllegalArgumentException("ids");

		StringBuffer strbuf = new StringBuffer(ids.size() * 6);
		strbuf.append("(").append(ids.get(0));
		for (int i = 1; i < ids.size(); ++i)
			strbuf.append(",").append(ids.get(i));
		strbuf.append(")");
		return strbuf.toString();
	}

	// 文件读取/写入辅助函数.

	/**
	 * 将指定文件内容写入指定正文文件，使用指定的编码，我们建议使用 'GB2312' 或 'UTF-8'.
	 * 
	 * @param fileName
	 * @param content
	 * @param charsetName
	 *            - 字符集，参见 OutputStreamWriter().
	 */
	public static final void saveFile(String fileName, String content, String charsetName) {
	    File file = null;
		try {
			file = new File(fileName);
			FileUtils.writeStringToFile(file, content, charsetName, false);			
		} catch (IOException ex) {
		    //什么也不做了。
		} finally {
		     file = null;
		}
	}

	/**
	 * 从指定文件读取入正文，使用指定的编码。我们建议使用 'GB2312' or 'UTF-8'.
	 * 
	 * @param fileName
	 * @param charsetName
	 * @return
	 */
	public static final String readFile(String fileName, String charsetName) {	   
	    String content = null;
	    File file = null;
	    try{
	        file = new File(fileName);	  
	        content = FileUtils.readFileToString(file, charsetName);
	    }
	    catch(Exception ex){}
	    finally{
	        file = null;
	    }
	    return content;
	    //原来的方法
		/*java.io.FileInputStream stream = null;
		try {
			stream = new java.io.FileInputStream(fileName);
			java.io.InputStreamReader reader = new java.io.InputStreamReader(
					stream, charsetName);

			StringBuilder strbuf = new StringBuilder();
			char[] cbuf = new char[4096];
			while (true) {
				int len = reader.read(cbuf);
				if (len < 0)
					break;
				strbuf.append(cbuf, 0, len);
			}
			return strbuf.toString();
		} catch (java.io.IOException ex) {
			return null;
		} finally {
			if (stream != null) {
				try {
					stream.close();
				} catch (Exception ignore) {

				}
			}
		}*/
	}

	/**
	 * 得到指定文件的后缀.
	 * 
	 * @param fileName
	 * @return 如果没有后缀则返回 "", 否则返回后缀(不含 . 符号)
	 */
	public static final String getFileExtension(String fileName) {
		if (fileName == null || fileName.length() == 0)
			return "";
		int pos = fileName.lastIndexOf('.');
		if (pos < 0)
			return "";
		String ext = fileName.substring(pos + 1);
		if (ext.indexOf('/') >= 0 || ext.indexOf('\\') >= 0)
			return "";
		return ext;
	}

	/**
	 * 得到一个 href 中的文件名部分, 一般是最后一个 '/', '\\' 后面的部分.
	 * 
	 * @param href
	 * @return
	 */
	public static final String getFileNameFromHref(String href) {
		if (href == null || href.length() == 0)
			return "";
		int index = href.lastIndexOf('/');
		if (index >= 0)
			href = href.substring(index + 1);
		index = href.lastIndexOf('\\');
		if (index >= 0)
			href = href.substring(index + 1);
		return href;
	}

	// 计算分类 itemType.

	/**
	 * 根据协作组标识计算出'协作组文章分类'中使用的 itemType 字符串, = 'gart_' + $groupId.
	 * 
	 * @param groupId
	 * @return
	 */
	public static final String toGroupArticleCategoryItemType(int groupId) {
		return "gart_" + groupId;
	}

	/**
	 * 根据协作组标识计算出'协作组资源分类'中使用的 itemType 字符串, = 'gres_' + $groupId.
	 * 
	 * @param groupId
	 * @return
	 */
	public static final String toGroupResourceCategoryItemType(int groupId) {
		return "gres_" + groupId;
	}

	/**
	 * 根据协作组标识计算出'协作组图片分类'中使用的 itemType 字符串, = 'gpho_' + $groupId.
	 * 
	 * @param groupId
	 * @return
	 */
	public static final String toGroupPhotoCategoryItemType(int groupId) {
		return "gpho_" + groupId;
	}	
	
	/**
	 * 根据协作组标识计算出'协作组视频分类'中使用的 itemType 字符串, = 'gvid_' + $groupId.
	 * 
	 * @param groupId
	 * @return
	 */
	public static final String toGroupVideoCategoryItemType(int groupId) {
		return "gvid_" + groupId;
	}
	
	/**
	 * 根据用户标识计算出用户'个人文章分类'中使用的 itemType 字符串, = 'user_' + $userId
	 * 
	 * @param userId
	 * @return
	 */
	public static final String toUserArticleCategoryItemType(int userId) {
		return "user_" + userId;
	}

	/**
	 * 根据用户标识计算用户'个人资源分类'的 itemType, 其 = 'user_res_' + $userId
	 * 
	 * @param userId
	 * @return 'user_res_' + $userId
	 */
	public static final String toUserResourceCategoryItemType(int userId) {
		return "user_res_" + userId;
	}

	/**
	 * 根据用户标识计算用户'个人视频分类'的 itemType, 其 = 'user_video_' + $userId
	 * 
	 * @param userId
	 * @return 'user_video_' + $userId
	 */
	public static final String toUserVideoCategoryItemType(int userId) {
		return "user_video_" + userId;
	}	
	/**
	 * 将 Hibernate 返回的 Long, Integer 转换为 Integer, Hibernate 有的版本返回的 COUNT(*) 为
	 * Long 型.
	 * 
	 * @param result
	 * @return
	 */
	public static final Integer safeXtransHiberInteger(Object result) {
		if (result == null)
			return null;
		if (result instanceof Integer)
			return (Integer) result;
		if (result instanceof Long)
			return ((Long) result).intValue();
		if (result instanceof Number)
			return ((Number) result).intValue();
		return null;
	}

	/**
	 * 计算一个地址的 url 完全表示. 其中如果 href 以 '/' 开头则返回 $SiteUrl + href . 如果 href 以 '//'
	 * 开头则返回 HostUrl + href (HostUrl 不含 SiteUrl 中 context path 部分). 如果 href 以
	 * http://, https:// 开头则直接返回.
	 * 
	 * @param href
	 * @return
	 */
	public static String calcAbsUrl(String href) {
		if (href == null || href.length() == 0)
			return "";
		String lower_href = href.toLowerCase();
		// 以 http://, https:// 开头则直接返回.
		if (lower_href.startsWith("http://")
				|| lower_href.startsWith("https://"))
			return href;

		String siteUrl = SiteUrlModel.getSiteUrl();
		if (href.startsWith("//"))
			return hostUrl(siteUrl) + href.substring(2);
		if (href.startsWith("/"))
			return siteUrl + href.substring(1);
		return siteUrl + href;
	}

	/**
	 * 生成照片的缩略图. param user/yang/zhaopian.gif ---> user/yang/s_zhaopian.jpg
	 * 
	 * @param href
	 *            -- http://www.jitar.com.cn/Groups/ + user/admin/photo/10.jpg
	 * @return
	 * 
	 *         注意：如果文件不存在，则需要加载原始图片 by 孟宪会
	 */
	public static String thumbnails(String href) {
		if (href == null || href.length() == 0)
			return "";
		String lower_href = href.toLowerCase();
		// 以 http://, https:// 开头则直接返回.
		if (lower_href.startsWith("http://")
				|| lower_href.startsWith("https://"))
			return href;
		String siteUrl = SiteUrlModel.getSiteUrl();
		if (href.startsWith("//"))
			return hostUrl(siteUrl) + href.substring(2);
		if (href.startsWith("/"))
			return siteUrl + href.substring(1);

		String fileName = href.substring(href.lastIndexOf("/") + 1,
				href.length());
		String fileFile = href.substring(0, href.lastIndexOf("/"));
		JitarRequestContext req_ctxt = JitarRequestContext.getRequestContext();
		String SiteRootPath = req_ctxt.getServletContext().getRealPath("/");
		if (SiteRootPath == null) {
			SiteRootPath = "";
		}

		// System.out.println("SiteRootPath="+SiteRootPath);
		// System.out.println("fileFile="+fileFile);
		// System.out.println("fileName="+fileName);

		String FileLocalPath = SiteRootPath + fileFile + "/s_" + fileName;

		// System.out.println("FileLocalPath="+FileLocalPath);

		String fileUserConfigPath = req_ctxt.getServletContext().getInitParameter("userPath");
		String filePath = fileFile;
		if (fileUserConfigPath == "" || fileUserConfigPath == null)
			FileLocalPath = FileLocalPath.replace("\\", "/");
		else {
			filePath = filePath.replace('/', '\\');
			if (!fileUserConfigPath.endsWith("\\"))
				fileUserConfigPath = fileUserConfigPath + "\\";
			if (filePath.startsWith("\\"))
				FileLocalPath = fileUserConfigPath + fileFile.substring(1)	+ "/s_" + fileName;
			else
				FileLocalPath = fileUserConfigPath + fileFile + "/s_" + fileName;
		}
		// System.out.println("FileLocalPath="+FileLocalPath);
		File f = new File(FileLocalPath);
		if (f.exists() == false) {
			f = null;
			return siteUrl + href;
		} else {
			f = null;
			return siteUrl + fileFile + "/s_" + fileName;
		}
	}

	/**
	 * 修改此方法，是为了在没有request请求的条件下，正常运行，不依赖于 JitarRequestContext
	 * 
	 * @param href
	 * @return
	 */
	public static String thumbnails2(String href) {
		if (href == null || href.length() == 0)
			return "";
		String lower_href = href.toLowerCase();
		// 以 http://, https:// 开头则直接返回.
		if (lower_href.startsWith("http://") || lower_href.startsWith("https://"))
			return href;
		
		//String siteUrl = SiteUrlModel.getSiteUrl();
		//if (href.startsWith("//"))
		//	return hostUrl(siteUrl) + href.substring(2);
		if (href.startsWith("/"))
			return href.substring(1);

		String fileName = href.substring(href.lastIndexOf("/") + 1,	href.length());
		String fileFile = href.substring(0, href.lastIndexOf("/"));
		WebApplicationContext wac = ContextLoader.getCurrentWebApplicationContext();
		ServletContext sc = wac.getServletContext();
		String SiteRootPath = sc.getRealPath("/");
		if (SiteRootPath == null) {
			SiteRootPath = "";
		}

		// System.out.println("SiteRootPath="+SiteRootPath);
		// System.out.println("fileFile="+fileFile);
		// System.out.println("fileName="+fileName);

		String FileLocalPath = SiteRootPath + fileFile + "/s_" + fileName;

		// System.out.println("FileLocalPath="+FileLocalPath);

		String fileUserConfigPath = sc.getInitParameter("userPath");
		String filePath = fileFile;
		if (fileUserConfigPath == null || fileUserConfigPath.equals(""))
			FileLocalPath = FileLocalPath.replace("\\", "/");
		else {
			filePath = filePath.replace('/', '\\');
			if (!fileUserConfigPath.endsWith("\\"))
				fileUserConfigPath = fileUserConfigPath + "\\";
			if (filePath.startsWith("\\"))
				FileLocalPath = fileUserConfigPath + fileFile.substring(1) + "/s_" + fileName;
			else
				FileLocalPath = fileUserConfigPath + fileFile + "/s_" + fileName;
		}
		// System.out.println("FileLocalPath="+FileLocalPath);
		File f = new File(FileLocalPath);
		if (f.exists() == false) {
			f = null;
			return sc.getContextPath() + "/" + href;
		} else {
			f = null;
			return sc.getContextPath() + "/" + fileFile + "/s_" + fileName;
		}
	}

	/**
	 * siteUrl - 'http://www.domain.com/', 'http://domain/ctxt/' must endwith
	 * '/'
	 */
	public static final String hostUrl(String siteUrl) {
		int pos1 = siteUrl.indexOf("://");
		if (pos1 < 0)
			return siteUrl;
		int pos2 = siteUrl.indexOf("/", pos1 + 3);
		if (pos2 < 0)
			return siteUrl;
		return siteUrl.substring(0, pos2 + 1);
	}

	/**
	 * 按照 RSS GMT 日期格式输出一个日期的字符串.
	 * 
	 * @param date
	 * @return 结果示例: 'Wed,07 Jun 2006 01:54:50 GMT'
	 */
	public static String rssDateFormat(Date date) {
		// 得到服务器时区偏差，然后用该时区偏差构造一个 GMT TimeZone.
		int timeZoneOffset = getTimeZoneOffset();
		SimpleTimeZone zone = new SimpleTimeZone(timeZoneOffset, "GMT");

		// 构造日期格式化字符串.
		SimpleDateFormat formatter = new SimpleDateFormat(
				"EEE, d MMM yyyy HH:mm:ss z", Locale.US);
		formatter.setTimeZone(zone);

		return formatter.format(date);
	}

	/**
	 * 获得当前服务器缺省的时区偏差, 以小时为单位. 例如中国返回 +8.
	 * 
	 * @return
	 */
	public static int getTimeZoneOffset() {
		TimeZone deft = TimeZone.getDefault();
		if (deft == null)
			return 8;
		int raw_ofs = deft.getRawOffset(); // 以毫秒为单位.

		return raw_ofs / (60 * 60 * 1000);
	}

	/**
	 * 
	 * @return
	 */
	public static String getProductName(HttpServletRequest request) {
		ServletContext sc = request.getSession().getServletContext();
		String sName = sc.getInitParameter("product_name");
		// 江苏南通港闸
		// “学科带头人”改成“学科之星”；“专家名师”改成“优秀教师”
		return sName;
	}

	public static String getLoginToSubjectPage(HttpServletRequest request) {
		ServletContext sc = request.getSession().getServletContext();
		String v = sc.getInitParameter("LoginToSubjectPage");
		// 登陆成功后是否转到学科页面 1是 0否
		return v;
	}

	/**
	 * 
	 * @return 上传插件服务器地址
	 */
	/*
	 * public static String getUploadServerName(HttpServletRequest request) {
	 * ServletContext sc = request.getSession().getServletContext(); String
	 * sName = sc.getInitParameter("uploadserver_name"); return sName; }
	 */

	// 过滤HTML
	public static String getTxtWithoutHTMLElement(String element) {
		if (null == element || "".equals(element.trim())) {
			return element;
		}

		Pattern pattern = Pattern.compile("<[^<|^>]*>");
		Matcher matcher = pattern.matcher(element);
		StringBuffer txt = new StringBuffer();
		while (matcher.find()) {
			String group = matcher.group();
			if (group.matches("<[\\s]*>")) {
				matcher.appendReplacement(txt, group);
			} else {
				matcher.appendReplacement(txt, "");
			}
		}
		matcher.appendTail(txt);
		repaceEntities(txt, "&amp;", "&");
		repaceEntities(txt, "&lt;", "<");
		repaceEntities(txt, "&gt;", ">");
		repaceEntities(txt, "&quot;", "\"");
		repaceEntities(txt, "&nbsp;", "");
		return txt.toString();
	}

	private static void repaceEntities(StringBuffer txt, String entity,
			String replace) {
		int pos = -1;
		while (-1 != (pos = txt.indexOf(entity))) {
			txt.replace(pos, pos + entity.length(), replace);
		}
	}

	public static void deleteDir(String dir) {
		try {
			File f = new File(dir);
			if (f.exists() == false)
				return;
			File[] fs = f.listFiles();
			for (File fd : fs) {
				if (fd.isDirectory()) {
					deleteDir(fd.getPath());
				}

				fd.delete();
			}
			f.delete();
		} catch (Exception ex) {
		}
	}

	/**
	 * 将 /5/7/0/1/2/4/ 格式化成 /0/1/2/4/5/7/
	 * 
	 * @param inputString
	 * @return
	 */
	public static String sortNumberString(String inputString) {
		if (inputString == null || inputString.equals(""))
			return "";
		if (inputString.startsWith("/"))
			inputString = inputString.substring(1);
		if (inputString.endsWith("/"))
			inputString = inputString.substring(0, inputString.length() - 1);
		String[] a = inputString.split("/");
		int[] b = new int[a.length];
		for (int i = 0; i < a.length; i++) {
			b[i] = Integer.valueOf(a[i]);
		}
		inputString = "/";
		Arrays.sort(b);
		if (b.length > 0) {
			for (int i = 0; i < b.length; i++) {
				inputString += String.valueOf(b[i]) + "/";

			}
		}
		if (inputString.equals("/"))
			inputString = "";
		return inputString;

	}

	public static String readFileToString(String filePath, String encoding) {
		String content = null;
		File file = new File(filePath);
		if (file.exists() == false)
			return content;
		Long filelength = file.length();
		byte[] filecontent = new byte[filelength.intValue()];
		try {
			FileInputStream in = new FileInputStream(file);
			in.read(filecontent);
			in.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return content;
		} catch (IOException e) {
			e.printStackTrace();
			return content;
		}
		try {
			return new String(filecontent, encoding);
		} catch (UnsupportedEncodingException e) {
			System.err.println("操作系统不支持该编码： " + encoding);
			e.printStackTrace();
			return content;
		}
	}

	public static final boolean isInteger(String val) {
		if (val == null)
			return false;

		if (val.length() == 0)
			return false;

		val = val.trim();
		if (val.length() == 0)
			return false;

		for (int i = 0; i < val.length(); ++i) {
			char c = val.charAt(i);
			if (c == '+' || c == '-')
				continue;
			if (c > '9' || c < '0')
				return false;
		}
		try {
			Integer.parseInt(val);
		} catch (NumberFormatException ex) {
			return false;
		}
		return true;
	}

	public static void copyfile(String srFile, String dtFile) {
		try {
			File f1 = new File(srFile);
			File f2 = new File(dtFile);
			InputStream in = new FileInputStream(f1);

			// 追加内容.
			// OutputStream out = new FileOutputStream(f2,true);

			// For Overwrite the file.
			OutputStream out = new FileOutputStream(f2);

			byte[] buf = new byte[1024];
			int len;
			while ((len = in.read(buf)) > 0) {
				out.write(buf, 0, len);
			}
			in.close();
			out.close();

		} catch (FileNotFoundException ex) {
			System.out.println(ex.getMessage());
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}

	public static boolean isNumber(String str) {
		Pattern pattern = Pattern.compile("[0-9]*");
		Matcher match = pattern.matcher(str);
		if (match.matches() == false) {
			return false;
		} else {
			return true;
		}
	}

	public static boolean renameTo(File from, File to) {
		FileInputStream fromFile = null;
		FileOutputStream toFile = null;
		boolean success = false;
		try {
			fromFile = new FileInputStream(from);
			toFile = new FileOutputStream(to);
			byte[] buf = new byte[4096];
			int bytes_read = 0;
			try {
				while ((bytes_read = fromFile.read(buf)) != -1) {
					toFile.write(buf, 0, bytes_read); // write
				}
				success = true;
			} catch (IOException iox) {
				//System.out.println("IOException = " + iox.getLocalizedMessage());
			}
		} catch (FileNotFoundException ex) {
			//System.out.println("FileNotFoundException = " + ex.getLocalizedMessage());
		} finally {
			if (fromFile != null)
				try {
					fromFile.close();
				} catch (IOException e) {
				}
			if (toFile != null)
				try {
					toFile.close();
				} catch (IOException e) {
				}
		}

		if(from.delete()==false)
		{
			from.deleteOnExit();
		}
		return success;
	}
	
	//36进制-》10 用在分类的Path处理上
	public static String convertIntFrom36To10(String strPath)
	{
		String[] ts = strPath.split("/");
		String t = "/";
		for(int i=0; i<ts.length; i++)
		{   
			String cp = ts[i];
			if(cp.equals("") == false)
			{
				t += String.valueOf(Integer.parseInt(cp,36)) + "/";
			}
		}		
		return t;
	}
	
	//返回 /xx/yyy/这种格式的最后一位yyy
	public static Integer getLastInteger(String param)
	{
		Integer ret = null;
		if(param == null || param.indexOf("/") == -1 || param.length()<2) return ret;
		
		String channelCateIds = param.substring(0, param.length()-1);
		
		if(channelCateIds.indexOf("/")>-1)
		{
			channelCateIds = channelCateIds.substring(channelCateIds.lastIndexOf("/") + 1);
		}
		
		if(isInteger(channelCateIds))
		{
			ret = Integer.valueOf(channelCateIds);
		}
		
		return ret;
	}
	
	//得到集备的存储文件夹位置
	public static String[] GetPrepareCourseFolder(HttpServletRequest request)
	{
		String[] p = new String[]{"",""}; //p[0]标识物理路径，p[1]表示虚拟路径
		try
		{
			//配置到网站根目录下面
			p[0] = request.getServletContext().getRealPath("/preparecoursefolder");
			p[1] = getSiteUrl(request) + "preparecoursefolder/";
			File f = new File(p[0]);
			if(!f.isDirectory() || !f.exists())
			{				
				//配置到虚拟目录下面
				p[0] = request.getServletContext().getContext("/preparecoursefolder").getRealPath("/");
				p[1] = "/preparecoursefolder/";
				f = new File(p[0]);
				if(!f.isDirectory() || !f.exists())
				{					
					p = new String[]{"",""};
				}				
			}
			f = null;
		}
		catch(Exception e){
			p = new String[]{"",""};
		}
		if(!p[0].equals(""))
		{
			if(!p[0].endsWith(File.separator))
				p[0] += File.separator;
		}
		return p;
	}
	
	public static String getSiteUrl(HttpServletRequest request)
	{
		String root = "";
        if( request.getServerPort() == 80)
        {
            root = request.getScheme() + "://" + request.getServerName() + request.getContextPath() + "/";
        }
        else
        {
            root = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/";
        }
        return root;
	}
	
	
	public static String getSiteServer(HttpServletRequest request)
	{
		String root = "";
        if( request.getServerPort() == 80)
        {
            root = request.getScheme() + "://" + request.getServerName();
        }
        else
        {
            root = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
        }
        return root;
	}
	

	public static String getLocalHostSiteIP(ServletRequest _request)
	{
		if(_request == null) return "/";
		HttpServletRequest request = (HttpServletRequest)_request;
		String root = "";
        if( request.getServerPort() == 80)
        {
            root = request.getScheme() + "://127.0.0.1/";
        }
        else
        {
            root = request.getScheme() + "://127.0.0.1:" + request.getServerPort() + "/";
        }
        return root;
	}
	
	public static String getContextUrl(HttpServletRequest request)
	{
		String contextUrl = request.getContextPath();

		if (contextUrl.endsWith("/") == false)
		{
			contextUrl += "/";
		}		
        return contextUrl;
	}
	
	public static String encodeContentDisposition(HttpServletRequest request,String fileNameWithExtion) throws UnsupportedEncodingException
	{		
	    String fileName = fileNameWithExtion;
		String fileExt = "";
		//分解成文件名进行Utf8编码，有些浏览器需要进行文件名编码，扩展名要单独拿出来。
		if(fileNameWithExtion.lastIndexOf(".") > -1){
		    fileName = fileNameWithExtion.substring(0, fileNameWithExtion.lastIndexOf("."));
		    fileExt = fileNameWithExtion.substring(fileNameWithExtion.lastIndexOf("."));
		}
		if(fileName.equals("")){
		    fileName = "未命名的文档";
		}
		//进行编码过的完整文件名+扩展名。
		String utf8File = CommonUtil.urlUtf8Encode(fileName) + fileExt;
		//没有进行编码的完整文件名和扩展名，可以使用fileNameWithExtion代替吧
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
		return returnFile;		
	}
	
	//# 将学段格式化成整数，例如: 3100 -》 3000
	public static Integer convertRoundMinNumber(Integer intV)
	{
		if(intV == null || intV < 0) return 0;
		String strV = intV.toString();
		int intStrLen = strV.length();
		if(intStrLen == 0) return 0;
		strV = StringUtils.rightPad(strV.substring(0,1),intStrLen , "0"); 
		return Integer.valueOf(strV);
	}
	  //将学段格式化成整数，例如: 3100 -》 4000
	public static Integer convertRoundMaxNumber(Integer intV)
	{
		if(intV == null || intV < 0) return 0;
		String strV = intV.toString();
		int intStrLen = strV.length();
		if(intStrLen == 0) return 0;
		String firstLetter = String.valueOf(Integer.parseInt(strV.substring(0,1)) + 1);
		strV = StringUtils.rightPad(firstLetter,intStrLen , "0"); 
		return Integer.valueOf(strV);
	}
		
	 public static String toUnicode(String s) {
	        String as[] = new String[s.length()];
	        String s1 = "";
	        for (int i = 0; i < s.length(); i++) {
	            as[i] = String.format("%04X",s.charAt(i) & 0xffff);
	            s1 = s1 + "\\u" + as[i];
	        }
	        return s1;
	    }
	 


		/**
		 * 某个字符串是否包含在另一个字符串中
		 * 
		 * @param obj - 需要检测的对象
		 * @param strin - 需要检测的字符串
		 * @return
		 */
		public static boolean isContainsString(String obj, String strInput) {
			HashMap<String, String> settingExt = new HashMap<String, String>();
			// 注意两点：1，使用英文的逗号分割；2，逗号后面不能有空格
			settingExt.put("video", "avi,mpg,wmv,3gp,mov,mp4,asf,asx,flv,hlv,f4v,m4v,mpeg,mpeg4,mpe,mkv,rm,rmvb");
			settingExt.put("ffmpeg", "avi,mpg,wmv,3gp,mov,mp4,asf,asx,flv,hlv,f4v,m4v,mpeg,mpeg4,mpe");
			settingExt.put("mencoder", "mkv,rm,rmvb");
			settingExt.put("play", "mp4,flv,hlv,f4v,m4v");
			if (Arrays.<String> asList(settingExt.get(obj).split(",")).contains(strInput)) {
				return true;
			}
			return false;
		}
		
		
		/**
		 * 生成视频截图
		 * 
		 * @param configPath - 视频配置路径
		 * @param ffmpegRoot - ffmpeg视频转换工具的绝对路径，for windows
		 * @param destString - 最终的文件全路径
		 * @param prefix - 文件前缀
		 */
		public static void interceptorVideo(String configPath, String ffmpegRoot, String destString, String prefix) {
			String os = System.getProperty("os.name");
			String picture = "";
			if (os.contains("Linux")) {
				picture = new StringBuffer().append("ffmpeg -itsoffset 4 -i \"").append(destString).append("\" -vcodec png -vframes 1 -an -f rawvideo -ss 10 -s 120x100 -y \"").append(configPath).append(File.separator).append(prefix).append(".png\"").toString();
			} else if(os.contains("Windows")) {
				picture = new StringBuffer().append(ffmpegRoot).append(" -itsoffset 4 -i \"").append(destString).append("\" -vcodec png -vframes 1 -an -f rawvideo -ss 10 -s 120x100 -y \"").append(configPath).append(File.separator).append(prefix).append(".png\"").toString();
			}
			processHandler(picture);
		}

		/**
		 * 视频转换
		 * 
		 * @param type - 视频类型
		 * @param root - 视频转换工具的绝对路径，for windows
		 * @param sourceVideo - 视频的原始全路径
		 * @param configPath - 视频配置路径
		 * @param prefix - 视频前缀
		 */
		public static void convertVideo(String type, String root, String sourceVideo, String configPath, String prefix) {
			String os = System.getProperty("os.name");
			String video = "";

			if (os.contains("Linux")) {
				if ("ffmpeg".equals(type)) {
					video = new StringBuffer().append("ffmpeg -i \"").append(sourceVideo).append("\" -subq 9 -me_range 32 -g 250 -i_qfactor 1.3 -b_qfactor 1.4 \"").append(configPath).append(File.separator).append(prefix).append(".m4v\"").toString();
				}
				if ("mencoder".equals(type)) {
					video = new StringBuffer().append("mencoder \"").append(sourceVideo).append("\" -of lavf -oac mp3lame -lameopts abr:br=56 -ovc lavc -lavcopts vcodec=flv:vbitrate=1000:mbd=2:mv0:trell:v4mv:cbp:last_pred=3:dia=4:cmp=6:vb_strategy=1 -vf scale=320:240 -ofps 12 -srate 22050 -o \"").append(configPath).append(File.separator).append(prefix).append(".flv\"").toString();
				}
			} else if(os.contains("Windows")) {
				if ("ffmpeg".equals(type)) {
					video = new StringBuffer().append(root).append(" -i \"").append(sourceVideo).append("\" -subq 9 -me_range 32 -g 250 -i_qfactor 1.3 -b_qfactor 1.4 \"").append(configPath).append(File.separator).append(prefix).append(".m4v\"").toString();
				}
				if ("mencoder".equals(type)) {
					video = new StringBuffer().append(root).append(" \"").append(sourceVideo).append("\" -of lavf -oac mp3lame -lameopts abr:br=56 -ovc lavc -lavcopts vcodec=flv:vbitrate=1000:mbd=2:mv0:trell:v4mv:o=mpv_flags=+cbp_rd:last_pred=3:dia=4:cmp=6:vb_strategy=1 -vf scale=320:240 -ofps 12 -srate 22050 -o \"").append(configPath).append(File.separator).append(prefix).append(".flv\"").toString();
				}
			}
			//System.out.println(video);
			processHandler(video);
		}
		
		/**
		 * 执行
		 * 
		 * @param s - 将要被执行的字符串
		 */
		private static void processHandler(String s) {
			String os = System.getProperty("os.name");
			Process process = null;
			try {
				if (os.contains("Linux")) {
					// 进程睡眠的时候，使用 sh -c，将自定义 CMD 行作为其参数
					process = Runtime.getRuntime().exec(new String[] { "sh", "-c", s });
				} else if(os.contains("Windows")) {
					process = Runtime.getRuntime().exec(s);
				}
				StreamGobbler errorGobbler = new StreamGobbler(process.getErrorStream(), "INFO");
				errorGobbler.start();
				StreamGobbler outGobbler = new StreamGobbler(process.getInputStream(), "STDOUT");
				outGobbler.start();
				// 使用Runtime.getRuntime().exec()会开启一个子进程，如果当前线程想等待该子进程执行完毕之后再继续往下执行，可以根据业务需要调用：java.lang.Process.waitFor()
				//process.waitFor();
				
				// 另一种方法
				// BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
				// while (null != bufferedReader.readLine());
			} catch (Exception e) {
				System.out.println("进程执行失败！");
				e.printStackTrace();
			}
		}
	    		
	public static String getFileEncoding(String fileName){
	    String encoding = null;
	    byte[] buf = new byte[4096];
	    FileInputStream fis = null;
	    UniversalDetector detector = new UniversalDetector(null);
	    try
	    {        
	        fis = new FileInputStream(fileName);
	        int nread;
    	    while ((nread = fis.read(buf)) > 0 && !detector.isDone()) {
    	      detector.handleData(buf, 0, nread);
    	    }
    	    detector.dataEnd();
    	    encoding = detector.getDetectedCharset();
    	    detector.reset();
    	    fis.close();
	    }
	    catch(Exception ex){}
	    finally{
	        if(null == encoding || encoding.length() == 0){
	            encoding = "GB2312";
	        }
	        if(null != fis){
	            fis = null;
	        }
	    }
        return encoding;
	}
	
	public static String escapeSQLString(String keyword)
	{
	    return keyword.replace("'","''").replace("[","[[]").replace("%","[%]").replace("_","[_]");
	}
	
	public static String getClientIP(HttpServletRequest request)
    {
	    String  strIP = request.getRemoteAddr();
	    if (strIP == null || strIP.trim().length() ==0){
	        strIP = request.getHeader("x-forwarded-for");
	    }
	    return strIP;
    }	
	
	public static String moveHashToEnd(String r) {
        if (r.contains("#") == false) {
            return r;
        }
        String part1 = r.substring(0, r.indexOf("#"));
        String s1 = r.substring(r.indexOf("#") + 1);
        String hash = "";
        for (int i = 0; i < s1.length(); i++) {
            if (s1.charAt(i) == '?' || s1.charAt(i) == '&') {
                break;
            }
            hash += String.valueOf(s1.charAt(i));
        }
        String part2 = s1.substring(hash.length());
        String ret = part1 + part2;
        if (hash.length() > 0) {
            ret += "#" + hash;
        }
        return ret;
    }
	
	//返回 |xx|xxxxx|格式字符串的前count个|的内容
	 public static String getLeftCountedString(String inStr, int count){
        if(inStr == null || inStr.length() == 0 || inStr.contains("|") == false) return inStr;
        String[] arr = inStr.split("\\|");
        int arrLength = arr.length;
        String outStr = "";
        if(arrLength > count && count > 0){
            for(int i=0;i<count+1;i++){
                outStr += arr[i] + "|";
            }
        }else
        {
            return inStr;
        }
        return  outStr;
    }
}
