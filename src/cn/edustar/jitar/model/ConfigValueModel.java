package cn.edustar.jitar.model;

import freemarker.template.TemplateBooleanModel;
import freemarker.template.TemplateModel;

/**
 * 配置的值的类型。
 * 
 *
 */
public interface ConfigValueModel {
	/** 值类型: 整形 = 'integer' */
	public static final String VALUE_TYPE_INTEGER = "integer";
	
	/** 值类型: 浮点 = 'double' */
	public static final String VALUE_TYPE_DOUBLE = "double";
	
	/** 值类型: 字符串型 = 'string', 这是缺省类型 */
	public static final String VALUE_TYPE_STRING = "string";
	
	/** 值类型: 日期型 = 'date' */
	public static final String VALUE_TYPE_DATE = "date";
	
	/** 值类型: 布尔型 = 'boolean' */
	public static final String VALUE_TYPE_BOOLEAN = "boolean";

	public static final TemplateModel NOTHING = TemplateModel.NOTHING;
	
	public static final TemplateBooleanModel TRUE = TemplateBooleanModel.TRUE;
	
	public static final TemplateBooleanModel FALSE = TemplateBooleanModel.FALSE;
}
