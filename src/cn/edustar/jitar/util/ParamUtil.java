package cn.edustar.jitar.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;

import cn.edustar.data.Pager;

/**
 * 从请求中获取参数的辅助类 * 
 *
 */
@SuppressWarnings("rawtypes")
public class ParamUtil {
	/** 常用数字 0 */
	public static final Integer ZERO_INT = new Integer(0);

	/** 常用数字 +1 */
	public static final Integer PLUS_ONE = new Integer(1);
	
	/** 常用数字 -1 */
	public static final Integer MINUS_ONE = new Integer(-1);
	
	/** 常用字符串 EMPTY */
	public static final String EMPTY_STRING = "";

	/** 存放请求参数的 Map. */
	private Map param_map;

	public ParamUtil(ServletRequest request) {
		this(request.getParameterMap());
	}
	
	/**
	 * 使用参数 Map 构造 ParamUtil 的新的实例.	 * 
	 * @param param_map
	 */
	public ParamUtil(Map param_map) {
		this.param_map = param_map;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		if (param_map == null) return "{}";
		
		StringBuffer strbuf = new StringBuffer(1024);
		strbuf.append("Params{");
		boolean first = true;
		for (Object key : param_map.keySet()) {
			if (first == false)
				strbuf.append(", ");
			strbuf.append(key).append("=");
			Object value = param_map.get(key);
			if (value == null || !(value instanceof Object[]))
				strbuf.append(value);
			else {
				Object[] a = (Object[])value;
				strbuf.append("[");
				for (int j = 0; j < a.length; ++j) {
					strbuf.append(a[j]);
					if (j < a.length - 1)
						strbuf.append(",");
				}
				strbuf.append("]");
			}
			first = false;
		}
		strbuf.append("}");
		return strbuf.toString();
	}

	/**
	 * 判断指定键的参数是否存在, 存在不表示该参数合法，或者不为 ''(空字符串).
	 * @param key
	 * @return 
	 */
	public boolean existParam(String key) {
		return this.param_map.containsKey(key);
	}
	
	/**
	 * 得到指定键的请求值.
	 * 
	 * @param key
	 * @return
	 */
	public String getRequestParam(String key) {
		Object v = param_map.get(key);
		
		if (v == null)
			return null;
		
		if (v instanceof String)
			return (String) v;
		
		if (v instanceof String[]) {
			String[] sa = (String[]) v;
			if (sa.length == 0)
				return EMPTY_STRING;
			if (sa.length == 1)
				return sa[0];
			if (sa.length == 2)
				return sa[0] + "," + sa[1];

			StringBuilder strbuf = new StringBuilder();
			strbuf.append(sa[0]);
			
			for (int i = 1; i < sa.length; ++i)
				strbuf.append(",").append(sa[i]);
			
			return strbuf.toString();
		}
		return v.toString();
	}

	/**
	 * 得到指定键的请求值，返回为数组形态.
	 * 
	 * @param key
	 * @return
	 */
	public String[] getRequestParamValues(String key) {
		Object val = this.param_map.get(key);
		if (val == null) return null;
		if (val instanceof String) return new String[] { (String)val };
		return (String[])val;	
	}

	// === Pager ==============================================================
	
	/**
	 * 生成分页中使用的 UrlPattern, 返回格式为 ?xxx=yyy&page={page}
	 * @return
	 */
	public String generateUrlPattern() {
		StringBuffer strbuf = new StringBuffer();
		strbuf.append("?");
		
		boolean appendAmp = false;
		for (Object key : param_map.keySet()) {
			if (key == null) continue;
			Object v = getRequestParamValues(key.toString());
			if (!(v instanceof String[])) continue;
			
			String[] values = (String[])v;
			if (values == null || values.length == 0) continue;
			
			if ("page".equals(key) || "total".equals(key))
				// 后面会附加, 这里跳过.
				continue;

			if (appendAmp) strbuf.append("&");
			for (int i = 0; i < values.length; ++i) {
				strbuf.append(key).append("=").append(CommonUtil.urlUtf8Encode(values[i]));
				if (i < values.length - 1)
					strbuf.append("&");
			}
			appendAmp = true;
		}
		
		// 附加 page 参数.
		if (appendAmp) 
			strbuf.append("&");
		strbuf.append("page={page}");
		strbuf.append("&");
		strbuf.append("total={total}");
		return strbuf.toString();
	}
	
	/**
	 * 根据此参数, 生成一个适合的 Pager 对象.
	 * @return
	 */
	public Pager createPager() {
		Pager pager = new Pager();
		int page = this.getIntParam("page");
		int total = this.getIntParam("total");
		if (page <= 0) page = 1;
		if (total < 0) total = 0;
		//if(page > pager.getTotalPages()) page = pager.getTotalPages();
		pager.setCurrentPage(page);
		pager.setTotalRows(total);
		pager.setPageSize(20);
		pager.setUrlPattern(generateUrlPattern());
		return pager;
	}

	// === getStringParam ======================================================
	
	/**
	 * 得到指定键的参数, 等同于 getStringParam(key).
	 */
	public String getParam(String key) {
		return getStringParam(key);
	}
	
	/**
	 * 得到字符串参数.
	 */
	public String getStringParam(String key) {
		String result = this.safeGetStringParam(key, EMPTY_STRING);
		return (result == null) ? EMPTY_STRING : result;
	}
	
	/**
	 * 获得指定键的字符串参数，如果没有则使用缺省值 defval.
	 * @param key
	 * @return
	 */
	public String getStringParam(String key, String defval) {
		return safeGetStringParam(key, defval);
	}

	/**
	 * 获得指定键的字符串参数.
	 * 
	 * @param key
	 * @return
	 */
	public String safeGetStringParam(String key) {
		return safeGetStringParam(key, EMPTY_STRING);
	}

	/**
	 * 获得指定键的字符串参数，如果没有则使用缺省值 defval.
	 * 
	 * @param key
	 * @return
	 */
	public String safeGetStringParam(String key, String defval) {
		String val = getRequestParam(key);
		if (val == null)
			return defval;
		return val.trim();
	}

	/**
	 * 判断给定键的请求参数是否是一个整数.
	 * 
	 * @param key
	 * @return
	 */
	public boolean isIntegerParam(String key) {
		String val = getRequestParam(key);
		return isInteger(val);
	}

	/**
	 * 得到整型参数, null 被当作 0 返回 .
	 * @param key
	 * @return
	 */
	public int getIntParam(String key) {
		Integer i = safeGetIntParam(key, ZERO_INT);
		return i == null ? 0 : i.intValue();
	}

	/**
	 * 得到整型参数，值 0 被当作 null 返回 .
	 * @return
	 */
	public Integer getIntParamZeroAsNull(String key) {
		Integer i = safeGetIntParam(key, null);
		if (i == null) return null;
		if (i.intValue() == 0) return null;
		return i;
	}
	
	/**
	 * 安全的获取请求值，如果没有或非法则返回缺省值 0 .
	 * 
	 * @param key
	 * @return
	 */
	public Integer safeGetIntParam(String key) {
		return safeGetIntParam(key, ZERO_INT);
	}

	/**
	 * 安全的获取请求值，如果没有或非法则返回缺省值.
	 * 
	 * @param key
	 * @param defval
	 * @return
	 */
	public Integer safeGetIntParam(String key, Integer defval) {
		String val = getRequestParam(key);
		return safeParseIntegerWithNull(val, defval);
	}

	// === getIdList ===========================================================
	
	/**
	 * 得到标识数组.
	 */
	public List<Integer> getIdList(String key) {
		return this.safeGetIntValues(key);
	}
	
	/**
	 * 获得指定键的参数，并转换为一个 int[] 类型. 此函数主要用于 id 数组类型.
	 * 
	 * @param key
	 * @return 返回 List&lt;Integer&gt; 整数集合，如果没有任何数据则返回空集合.
	 */
	public List<Integer> safeGetIntValues(String key) {
		// 返回一个数组，如 {'1', '2', '3, 4'}
		String[] values = getRequestParamValues(key);
		java.util.ArrayList<Integer> id_array = new java.util.ArrayList<Integer>();
		if (values == null || values.length == 0)
			return id_array;

		for (int i = 0; i < values.length; ++i) {
			if (values[i] == null || values[i].length() == 0)
				continue;
			
			// 每个单项都可以是 '3, 4, 5' 的 标识数组.
			String[] val_split = values[i].split(",");
			for (int j = 0; j < val_split.length; ++j) {
				try {
					id_array.add(Integer.parseInt(val_split[j]));
				} catch (NumberFormatException ex) {
					// ignore
				}
			}
		}
		return id_array;
	}


	// === getBooleanParam ======================================================
	
	/**
	 * 得到 boolean 型参数, null 被当作 false 返回 .
	 * @param key
	 * @return
	 */
	public boolean getBooleanParam(String key) {
		Boolean b = safeGetBooleanParam(key, null);
		return b == null ? false : b.booleanValue();
	}
	
	/**
	 * 安全的获取boolean型的请求值，如果没有或者非法返回缺省值 null .
	 * 
	 * @param key request.getParameter 中的 Key .
	 * @return
	 */
	public Boolean safeGetBooleanParam(String key) {
		return safeGetBooleanParam(key, null);
	}

	/**
	 * 安全的获取boolean型的请求值，如果没有或者非法返回给定的默认值.
	 * 
	 * @param key
	 * @param defval
	 * @return
	 */
	public Boolean safeGetBooleanParam(String key, Boolean defval) {
		String val = getRequestParam(key);
		return safeParseBooleanWithNull(val, defval);
	}

	// === getDateParam =========================================================
	
	/**
	 * 得到日期参数.
	 */
	public Date getDateParam(String key) {
		return this.safeGetDate(key);
	}
	
	/**
	 * 安全的获得日期参数.
	 * 
	 * @param key
	 * @return
	 */
	public Date safeGetDate(String key) {
		return safeGetDate(key, new Date());
	}

	/**
	 * 安全的获得日期参数.
	 * 
	 * @param key
	 * @return
	 */
	public Date safeGetDate(String key, Date defval) {
		String temp = safeGetStringParam(key);
		if (temp == null || temp.length() == 0)
			return defval;

		// 尝试用 SimpleDateFormat 解析.
		try {
			return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
					.parse(temp);
		} catch (Exception ex) {
			// ignore
		}

		// 尝试用 DateFormat 解析.
		try {
			return DateFormat.getInstance().parse(temp);
		} catch (Exception ex) {
			// ignore
		}

		// 不行用 Date.parse 方法.
		try {
			@SuppressWarnings("deprecation")
			Date result = new Date(Date.parse(temp));
			return result;
		} catch (Exception ex) {
			// ignore
		}

		// 还不行吗 ??
		return defval;
	}


	
	// === isXxx =================================================================

	/**
	 * 判定所给的字符串是否是一个整数.
	 * 
	 * @param val
	 * @return
	 */
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

	/**
	 * 判定所给的字符串是否是一个空字符串，空字符串 == null 或其 trim() == "" .
	 * 
	 * @param val
	 * @return
	 */
	public static final boolean isEmptyString(String val) {
		if (val == null) return true;
		if (val.length() == 0) return true;
		if (val.trim().length() == 0)	return true;
		
		return false;
	}
	
	/**
	 * 判定所给的字符串是否是一个空字符串，空字符串 == null 或其 trim() == "" .
	 * 等同于 isEmptyString().
	 * @param val 内容字符串.
	 * @return
	 */
	public static final boolean isBlankString(String val) {
		return isEmptyString(val);
	}

	/**
	 * 给定的字符串是否是合法的 boolean 型.
	 * 
	 * @param val 内容字符串.
	 * @return 是否是合法的 boolean 型字符串.
	 * 
	 */
	public static final boolean isBoolean(String val) {
		if (val == null) {
			return false;
		} else if (val.trim().length() < 1) {
			return false;
		} else {
			try {
				Boolean.parseBoolean(val);
			} catch (Exception ex) {
				return false;
			}
			return true;
		}
	}

	/**
	 * 给定的字符串是否全部由英文字母组成. 注意：如果字符串为null，将返回false.
	 * 
	 * @param val
	 * @return 注意：原来名字 isEnglish()
	 */
	public static final boolean isEnglishName(String val) {
		if (val == null || val.length() == 0)
			return false;

		for (int i = 0; i < val.length(); i++) {
			char ch = val.charAt(i);
			if (ch < 'A' || (ch > 'Z' && ch < 'a') || ch > 'z') {
				return false;
			}
		}
		return true;
	}

	// === safeParseXxx =============================================================

	/**
	 * 安全的解析字符串为整形.
	 * @param val
	 * @return
	 */
	public static Integer safeParseIntegerWithNull(String val) {
		return safeParseIntegerWithNull(val, null);
	}
	
	/**
	 * 安全的解析对象类型为整形.
	 * @param val
	 * @return
	 */
	public static Integer safeParseIntegerWithNull(Object val) {
		return safeParseIntegerWithNull(val, null);
	}
	
	/**
	 * 安全的解析对象类型为整形.
	 * @param val
	 * @param defval
	 * @return
	 */
	public static Integer safeParseIntegerWithNull(Object val, Integer defval) {
		if (val == null) return defval;
		if (val instanceof Number) return ((Number)val).intValue();
		if (val instanceof String) return safeParseIntegerWithNull((String)val, defval);
		
		return safeParseIntegerWithNull(val.toString(), defval);
	}
	
	/**
	 * 安全的解析字符串为整形.
	 * @param val
	 * @param defval
	 * @return
	 */
	public static Integer safeParseIntegerWithNull(String val, Integer defval) {
		if (val == null) return defval;
		val = val.trim();
		if (val.length() == 0 || "null".equalsIgnoreCase(val)) return defval;
		
		if (isInteger(val) == false)
			return defval;
		return Integer.parseInt(val.trim());
	}
	
	/**
	 * 安全的解析整数, null 做为 defval 处理.
	 * @param val
	 * @param defval
	 * @return
	 */
	public static int safeParseInt(String val, int defval) {
		if (val == null || val.length() == 0) return defval;
		Integer i = safeParseIntegerWithNull(val, defval);
		return i == null ? defval : i.intValue();
	}
	
	/**
	 * 安全的解析浮点数.
	 * @param val
	 * @param defval
	 * @return
	 */
	public static Double safeParseDoubleWithNull(String val, Double defval) {
		if (val == null) return defval;
		val = val.trim();
		if (val.length() == 0) return defval;
		if ("null".equals(val)) return null;
		
		try {
			return Double.parseDouble(val);
		} catch (NumberFormatException ex) {
			// ignore
			return defval;
		}
	}
	
	/**
	 * 安全的解析 boolean 字符串.
	 * @param val
	 * @param defval
	 * @return
	 */
	public static Boolean safeParseBooleanWithNull(String val, Boolean defval) {
		if (val == null) return defval;
		
		val = val.trim();
		if (val.length() == 0) return defval;
		
		if (val.equals("0"))
			return Boolean.FALSE;
		else if (val.equals("1"))
			return Boolean.TRUE;
		else if (val.equalsIgnoreCase("yes") || "true".equalsIgnoreCase(val))
			return Boolean.TRUE;
		else if (val.equalsIgnoreCase("no") || "false".equalsIgnoreCase(val))
			return Boolean.FALSE;
		else if ("null".equalsIgnoreCase(val))
			return null;
		else {
			try {
				return Boolean.parseBoolean(val);
			} catch (Exception ex) {
				// ignore ex
				return defval;
			}
		}
	}

	/**
	 * 解析字符串为 boolean 型值.
	 * @param val
	 * @param defval
	 * @return
	 */
	public static boolean safeParseBoolean(String val, boolean defval) {
		Boolean result = safeParseBooleanWithNull(val, defval);
		return result == null ? defval : result;
	}
}
