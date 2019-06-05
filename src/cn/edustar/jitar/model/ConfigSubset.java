package cn.edustar.jitar.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import freemarker.template.SimpleCollection;
import freemarker.template.TemplateBooleanModel;
import freemarker.template.TemplateCollectionModel;
import freemarker.template.TemplateDateModel;
import freemarker.template.TemplateHashModelEx;
import freemarker.template.TemplateModel;
import freemarker.template.TemplateModelAdapter;
import freemarker.template.TemplateModelException;
import freemarker.template.TemplateNumberModel;
import freemarker.template.TemplateScalarModel;

/**
 * 表示一个配置的子集, 配置子集的值等于 prefix 在配置中的值(可能没有).
 *   同时实现一个集合，包括该名字下面的所有子配置项.
 *  
 * 如 config.user 得到 'user' 子集, 可以再用子集访问 config.user.name 
 *   配置属性. * 
 *
 *
 */
@SuppressWarnings("unchecked")
public class ConfigSubset implements ConfigValueModel, TemplateModelAdapter {
	/** 原始配置对象 */
	private final Configure configs;
	
	/** 子集的前缀，如 'user.name' */
	private final String prefix;
	
	/**
	 * 构造一个配置子集.	 * @param configs
	 * @param prefix
	 */
	public ConfigSubset(Configure configs, String prefix) {
		this.configs = configs;
		this.prefix = prefix;
	}
	
	/*
	 * (non-Javadoc)
	 * @see freemarker.template.TemplateModelAdapter#getTemplateModel()
	 */
	public TemplateModel getTemplateModel() {
		ConfigValue value = configs.getConfigValue(prefix);
		if (value == null) {
			// 没有自己的值, 则自己是一个集合.			return new HashImpl();
		} else {
			// 自己有值, 且是一个集合.			String type = value.getType();
			if (type.equals(VALUE_TYPE_INTEGER) || type.equals(VALUE_TYPE_DOUBLE))
				return new NumberHashImpl((Number)value.getValue());
			else if (type.equals(VALUE_TYPE_STRING))
				return new StringHashImpl((String)value.getValue());
			else if (type.equals(VALUE_TYPE_DATE))
				return new DateHashModel((Date)value.getValue());
			else if (type.equals(VALUE_TYPE_BOOLEAN))
				return new BooleanHashModel((Boolean)value.getValue());
			else
				return NOTHING;		// 不支持的类型??
		}
	}
	
	/**
	 * TemplateHashModel 模型实现.
	 */
	private class HashImpl implements TemplateHashModelEx, TemplateScalarModel {
		private List cached_keys = null;
		
		public HashImpl() {
			
		}

		/*
		 * (non-Javadoc)
		 * @see freemarker.template.TemplateHashModel#get(java.lang.String)
		 */
		public TemplateModel get(String key) throws TemplateModelException {
			ConfigSubset subset = new ConfigSubset(configs, prefix + "." + key);
			return subset.getTemplateModel();
		}

		/*
		 * (non-Javadoc)
		 * @see freemarker.template.TemplateHashModel#isEmpty()
		 */
		public boolean isEmpty() throws TemplateModelException {
			return size() == 0;
		}

		/*
		 * (non-Javadoc)
		 * @see freemarker.template.TemplateScalarModel#getAsString()
		 */
		public String getAsString() throws TemplateModelException {
			List keys = getKeyList();
			if (keys.size() == 0) return "";
			return keys.toString();
		}

		/*
		 * (non-Javadoc)
		 * @see freemarker.template.TemplateHashModelEx#keys()
		 */
		public TemplateCollectionModel keys() throws TemplateModelException {
			return new SimpleCollection(getKeyList());
		}

		/*
		 * (non-Javadoc)
		 * @see freemarker.template.TemplateHashModelEx#size()
		 */
		public int size() throws TemplateModelException {
			return getKeyList().size();
		}

		/*
		 * (non-Javadoc)
		 * @see freemarker.template.TemplateHashModelEx#values()
		 */
		public TemplateCollectionModel values() throws TemplateModelException {
			List keys = getKeyList();
			List values = new ArrayList();
			for (int i = 0; i < keys.size(); ++i) {
				values.add(configs.get(prefix + "." + keys.get(i)));
			}
			return new SimpleCollection(values);
		}
	
		private List getKeyList() {
			if (this.cached_keys != null) return cached_keys;
			
			Set all_keys = configs.keySet();
			List keys = new java.util.ArrayList();
			String prefix_with_end_dot = prefix + ".";
			for (Object key : all_keys) {
				if (String.valueOf(key).startsWith(prefix_with_end_dot) )
					keys.add(((String)key).substring(prefix_with_end_dot.length()));
			}
			
			this.cached_keys = keys;
			return this.cached_keys;
		}
	}

	/**
	 * TemplateNumberModel + TemplateBooleanModel + TemplateHashModel 模型实现,
	 *   不知道 freemarker 会不会晕倒?
	 */
	private final class NumberHashImpl extends HashImpl implements TemplateNumberModel, TemplateBooleanModel {
		private final Number number;
		public NumberHashImpl(Number number) {
			this.number = number;
		}
		public Number getAsNumber() throws TemplateModelException {
			return number;
		}
		public boolean getAsBoolean() throws TemplateModelException {
			// 非 0 的数就认为是 true, 否则为 false.
			return (number.intValue() != 0);
		}
	}
	
	/**
	 * TemplateScalarModel + TemplateHashModel 模型实现.
	 */
	private final class StringHashImpl extends HashImpl implements TemplateScalarModel {
		private final String string;
		StringHashImpl(String string) {
			this.string = string;
		}
		public String getAsString() throws TemplateModelException {
			return string;
		}
	}
	
	/**
	 * TemplateDateModel + TemplateHashModel 模型实现.
	 */
	private final class DateHashModel extends HashImpl implements TemplateDateModel {
		private final Date date;
		public DateHashModel(Date date) {
			this.date = date;
		}
		public Date getAsDate() throws TemplateModelException {
			return date;
		}
		public int getDateType() {
			return DATETIME;
		}
	}

	/**
	 * TemplateBooleanModel + TemplateHashModel 模型实现.
	 */
	private final class BooleanHashModel extends HashImpl implements TemplateBooleanModel {
		private boolean value;
		public BooleanHashModel(boolean value) {
			this.value = value;
		}
		public boolean getAsBoolean() throws TemplateModelException {
			return value;
		}
	}
}
