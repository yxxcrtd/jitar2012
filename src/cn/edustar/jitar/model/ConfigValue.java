package cn.edustar.jitar.model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import cn.edustar.jitar.pojos.Config;
import cn.edustar.jitar.util.DateUtil;

/**
 * 一个配置值. * 
 *
 *
 */
public class ConfigValue implements ConfigValueModel {
	/** 值的类型 */
	private String type;
	
	/** 名字 */
	private String name;
	
	/** 配置的值 */
	private Object value;
	
	/**
	 * 构造一个 ConfigValue 的新实例.	 * @param type
	 * @param name
	 * @param value
	 */
	public ConfigValue(String type, String name, String value) {
		this.type = canonilizeType(type);
		this.name = name;
		this.value = canonilizeValue(value);
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return String.valueOf(value);
	}
	
	/**
	 * 将 ConfigValue 对象转换为 Config 对象, 用于数据库操作.
	 * @return
	 */
	public Config toConfig() {
		Config cfg = new Config();
		cfg.setName(this.name);
		cfg.setValue(this.getStringValue());
		cfg.setType(this.type);
		return cfg;
	}
	
	// 获得规范的类型表示.	private String canonilizeType(String type) {
		if (type == null) type = "string";
		if ("int".equals(type)) 
			type = VALUE_TYPE_INTEGER;
		if ("bool".equals(type))
			type = VALUE_TYPE_BOOLEAN;
		
		if (type.equals(VALUE_TYPE_INTEGER) ||
				type.equals(VALUE_TYPE_DOUBLE) ||
				type.equals(VALUE_TYPE_STRING) ||
				type.equals(VALUE_TYPE_DATE) ||
				type.equals(VALUE_TYPE_BOOLEAN) ) {
			// support these type
		} else
			type = VALUE_TYPE_STRING;
		
		return type.intern();
	}
	
	// 获得规范的值表示.	private Object canonilizeValue(String value) {
		if (type.equals(VALUE_TYPE_INTEGER))
			return integerValue(value);
		else if (type.equals(VALUE_TYPE_DOUBLE))
			return doubleValue(value);
		else if (type.equals(VALUE_TYPE_STRING))
			return value == null ? "".intern() : value;
		else if (type.equals(VALUE_TYPE_BOOLEAN))
			return booleanValue(value);
		else if (type.equals(VALUE_TYPE_DATE))
			return dateValue(value);
		else
			throw new RuntimeException("Unrecognized type: " + type);
	}
	
	private static final Integer ZERO = new Integer(0);
	private Integer integerValue(String value) {
		try {
			return Integer.parseInt(value);
		} catch (Exception ignored) {
			return ZERO;
		}
	}
	
	private Double doubleValue(String value) {
		try {
			return Double.parseDouble(value);
		} catch (Exception ignored) {
			return new Double(0);
		}
	}

	private Boolean booleanValue(String value) {
		if (value == null) return Boolean.FALSE;
		try {
			return Boolean.parseBoolean(value);
		} catch (Exception ignored) {
			return Boolean.FALSE;
		}
	}
	
	private static final Date DATE_ZERO = new Date(0); 
	@SuppressWarnings("deprecation")
	private Date dateValue(String value) {
		// 尝试用 SimpleDateFormat 解析。
		try {
			return new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(value);
		} catch (Exception ex) {
			// ignore
		}
		
		// 尝试用 DateFormat 解析。
		try {
			return DateFormat.getInstance().parse(value);
		} catch (Exception ex) {
			// ignore
		}
		
		// 不行用 Date.parse 方法。
		try {
			return new Date(Date.parse(value));
		} catch (Exception ex) {
			// ignore
		}
		
		// 还不行，则返回 Date(0)
		return DATE_ZERO;
	}
	
	/**
	 * 得到配置值的类型.
	 * @return
	 */
	public String getType() {
		return this.type;
	}
	
	/**
	 * 得到配置的名字.	 * @return
	 */
	public String getName() {
		return this.name;
	}
	
	/**
	 * 得到配置的值.	 * @return
	 */
	public Object getValue() {
		return this.value;
	}

	/**
	 * 得到整形值.	 * @return
	 */
	public int getIntValue() {
		return getIntValue(0);
	}
	
	/**
	 * 得到整形值，如果没有值或类型不对则返回 defval.
	 * @param defval
	 * @return
	 */
	public int getIntValue(int defval) {
		if (value == null) return defval;
		
		if (VALUE_TYPE_INTEGER.equals(type) || VALUE_TYPE_DOUBLE.equals(type))
			return ((Number)value).intValue();
		
		return defval;
	}

	/**
	 * 得到浮点型值.	 * @return
	 */
	public double getDoubleValue() {
		return getDoubleValue(0);
	}

	/**
	 * 得到浮点型值.	 * @param defval
	 * @return
	 */
	public double getDoubleValue(double defval) {
		if (value == null) return defval;
		
		if (VALUE_TYPE_INTEGER.equals(type) || VALUE_TYPE_DOUBLE.equals(type))
			return ((Number)value).doubleValue();
		
		return defval;
	}

	/**
	 * 得到字符串型，任何值都可以被转换为字符串型的.	 * @return
	 */
	public String getStringValue() {
		return getStringValue("");
	}
	
	/**
	 * 得到字符串型，任何值都可以被转换为字符串型的.	 * @param defval
	 * @return
	 */
	public String getStringValue(String defval) {
		if (value == null) return defval;
		
		if (value instanceof Date)
			return DateUtil.toStandardString((Date)value);
		
		return value.toString();
	}

	/**
	 * 得到日期型值.
	 */
	public Date getDateValue() {
		return getDateValue(DATE_ZERO);
	}
	
	/**
	 * 得到日期型值.
	 * @param defval
	 * @return
	 */
	public Date getDateValue(Date defval) {
		if (value == null) return defval;
		if (type.equals(VALUE_TYPE_DATE))
			return (Date)value;
		return defval;
	}

	/**
	 * 得到布尔型值.	 * @return
	 */
	public boolean getBoolValue() {
		return getBoolValue(false);
	}
	
	/**
	 * 得到布尔型值.
	 * 
	 * @param defval
	 * @return
	 */
	public boolean getBoolValue(boolean defval) {
		if (value == null)
			return defval;
		if (type.equals(VALUE_TYPE_BOOLEAN))
			return (Boolean) value;
		return defval;
	}
	
}
