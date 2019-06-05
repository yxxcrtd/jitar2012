package cn.edustar.jitar.model;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import cn.edustar.jitar.pojos.Config;

/**
 * 表示一组配置，一般使用'getValue()','getIntValue()'等系列函数获得值即可
 * 
 *
 */
@SuppressWarnings("rawtypes")
public class Configure extends AbstractMap {

	/** 是否允许用户注册, boolean 配置, 缺省 = true , = false 表示关'闭用户注册' */
	public static final String USER_REGISTER_ENABLED = "user.register.enabled";
	public static final String SITE_VERIFYCODE_ENABLED = "site.verifyCode.enabled";

	/** 用户注册必须要审核, boolean 配置, 缺省 = false 注册就可用, = true 表示注册之后需要审核 */
	public static final String USER_REGISTER_NEED_AUDIT = "user.register.needAudit";

	/** 用户注册时, boolean 型配置, = true 表示必须要选择一个学科; = false 表示学科可以不选择 */
	public static final String USER_REGISTER_MUST_SUBJECT = "user.register.mustSubject";

	/** 用户注册时, boolean 型配置, = true 表示必须要选择一个学段; = false 表示学段可以不选择 */
	public static final String USER_REGISTER_MUST_GRADE = "user.register.mustGrade";

	/** 后台用户管理中是否显示 email,注册时间 列, 部分用户不喜欢显示该列. 缺省 = true 表示显示 */
	public static final String USER_ADMIN_SHOW_EMAIL = "user.admin.showEmail";

	/**用户邮件是否唯一*/
	public static final String USER_REGISTER_EMAIL_UNIQUE= "user.register.uniqueEmail";
	
	/**昵称是否重复*/
	public static final String USER_REGISTER_NICKNAME_UNIQUE= "user.register.uniqueNickName";
	
	/** 用户注册时，是否必须填写真实姓名, boolean 型配置, = true 表示必须填写真实姓名; = false 表示不需要填写真实姓名 */
	public static final String USER_REGISTER_MUST_TRUENAME = "user.register.mustTrueName";

	/** 用户注册时, boolean 型配置, = true 表示必须选择一个角色; = false 表示不需要选择一个角色
	 * 该配置项已经从数据库中删除 */
	//public static final String USER_REGISTER_MUST_ROLE = "user.register.mustRole";

	/** 是否身份证必须输入，boolean 型配置，= true 表示必须输入身份证; = false 表示不需要输入身份证 */
	public static final String USER_REGISTER_MUST_IDCARD = "user.register.mustIdCard";

	/** 用户注册时, boolean 型配置, = true 表示必须要选择一个区县; = false 表示区县可以不填写 */
	public static final String USER_REGISTER_MUST_DISTRICT = "user.register.mustDistrict";

	/** 用户注册时, boolean 型配置, = true 表示必须要选择一个机构; = false 表示机构可以不填写 */
	public static final String USER_REGISTER_MUST_UNIT = "user.register.mustUnit";

	/** 是否修改个人信息中的真实姓名修改要管理员审核, = 是 表示需要管理员审核通过才能登录；= 否 表示不需要 */
	public static final String PROFILE_UPDATE_TRUENAME_NEEDAUDIT = "profile.update.trueName.needAudit";

	/** 是否修改个人信息中的学科修改要管理员审核, = 是 表示需要管理员审核通过才能登录；= 否 表示不需要 */
	public static final String PROFILE_UPDATE_SUBJECT_NEEDAUDIT = "profile.update.subject.needAudit";

	/** 是否修改个人信息中的学段修改要管理员审核, = 是 表示需要管理员审核通过才能登录；= 否 表示不需要 */
	public static final String PROFILE_UPDATE_GRADE_NEEDAUDIT = "profile.update.grade.needAudit";

	/** 是否修改个人信息中的区县修改要管理员审核, = 是 表示需要管理员审核通过才能登录；= 否 表示不需要 */
	public static final String PROFILE_UPDATE_DISTRICT_NEEDAUDIT = "profile.update.district.needAudit";

	/** 是否允许大文件上载, = 是 表示需要；= 否 表示不需要 */
	public static final String LARGEFILE_UPLOAD = "site.largefileupload";

	/** 是否限制用户上传的资源大小，= 是 表示需要限制(默认大小:100M)；= 否 表示不需要限制 */
	public static final String USER_RESOURCE_UPLOAD_LIMIT = "user.resource.upload.limit";

	/** 是否屏蔽系统中出现的非法词汇, = 是 表示需要；= 否 表示不需要 */
	public static final String SITE_SCREEN_ENALBED = "site.screen.enalbed";

	/** 是否要求真实姓名和呢称一致，= 是 表示要求一致；= 否 表示不要求一致 */
	public static final String TRUENAME_EQUALS_NICKNAME = "trueName.equals.nickName";
	
	/** 是否需要先审核后发布, = 是 表示需要；= 否 表示不需要 */
	public static final String BEFAULE_PUBLISH_CHECK = "site.resource.beforecheck";
	
	/** 是否只有登录用户才能评论 */
	public static final String USER_SITE_COMMENT_ENABLED = "user.site.comment.enabled";
	
	/** 是否启用静态化 */
	public static final String SITE_AUTO_HTML = "site.auto.html";
	
	/** 配置对象类型 */
	private String itemType;

	/** 提供给配置的对象, 可能没有 */
	private Object itemObject;

	/** 配置的值的集合 */
	private Map<String, ConfigValue> values = new HashMap<String, ConfigValue>();

	/**
	 * 构造
	 * 
	 * @param itemType
	 * @param conf_list
	 */
	public Configure(String itemType, List<Config> conf_list) {
		this(itemType, conf_list, null);
	}

	/**
	 * 构造
	 * 
	 * @param itemType
	 * @param conf_list
	 * @param itemObject
	 */
	public Configure(String itemType, List<Config> conf_list, Object itemObject) {
		this.itemType = itemType;
		this.itemObject = itemObject;
		initConfig(conf_list);
	}

	/**
	 * 初始化所有配置项
	 * 
	 * @param conf_list
	 */
	private void initConfig(List<Config> conf_list) {
		if (conf_list == null || conf_list.size() == 0)
			return;
		
		for (int i = 0; i < conf_list.size(); ++i) {
			Config config = conf_list.get(i);
			ConfigValue value = new ConfigValue(config.getType(), config.getName(), config.getValue());
			this.values.put(value.getName(), value);
		}
	}

	/** 配置对象类型 */
	public String getItemType() {
		return this.itemType;
	}

	/** 获得提供给配置的对象 */
	public Object _getItemObject() {
		return this.itemObject;
	}

	/** 设置提供给配置的对象 */
	public void _setItemObject(Object itemObject) {
		this.itemObject = itemObject;
	}

	/**
	 * 得到指定配置名的配置值，注意：得到的是最终配置值
	 * 
	 * @param name
	 * @return
	 */
	public Object getValue(String name) {
		ConfigValue value = values.get(name);
		return (value == null) ? null : value.getValue();
	}

	/**
	 * 得到指定配置名的配置值，得到的是值'ConfigValue'对象
	 * 
	 * @param name
	 * @return
	 */
	public ConfigValue getConfigValue(String name) {
		return values.get(name);
	}

	/**
	 * 得到指定配置名的整数型配置值
	 * 
	 * @param name
	 * @return 如果配置项不存在则返回：0; 如果配置项类型不是整数则返回：0
	 */
	public int getIntValue(String name) {
		return getIntValue(name, 0);
	}

	/**
	 * 得到指定配置名的整数型配置值
	 * 
	 * @param name
	 * @param defval
	 * @return 如果配置项不存在则返回'defval'；如果配置项类型不是整数则返回'defval'
	 */
	public int getIntValue(String name, int defval) {
		ConfigValue value = values.get(name);
		
		if (value == null)
			return defval;

		return value.getIntValue(defval);
	}

	/**
	 * 得到布尔型值
	 * 
	 * @param name
	 * @return
	 */
	public boolean getBoolValue(String name) {
		return getBoolValue(name, false);
	}

	/**
	 * 得到布尔型值
	 * 
	 * @param name
	 * @param defval
	 * @return
	 */
	public boolean getBoolValue(String name, boolean defval) {
		ConfigValue value = values.get(name);
		
		if (value == null)
			return defval;
		
		return value.getBoolValue(defval);
	}

	/**
	 * 得到字符串配置值，如果配置值类型不是字符串的，则将通过'toString()'强制转换为'string'的
	 * 
	 * @param name
	 * @return
	 */
	public String getStringValue(String name) {
		return getStringValue(name, "");
	}

	/**
	 * 得到字符串配置值，如果配置值类型不是字符串的，则将通过'toString()'强制转换为'string'的
	 * 
	 * @param name
	 * @return
	 */
	public String getStringValue(String name, String defval) {
		ConfigValue value = values.get(name);
		
		if (value == null)
			return defval;

		return value.getStringValue(defval);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.AbstractMap#entrySet()
	 */
	@Override
	public Set entrySet() {
		return this.values.entrySet();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.AbstractMap#get(java.lang.Object)
	 */
	@Override
	public Object get(Object key) {
		if (this.values.containsKey(key))
			return this.values.get(key);
		
		if (key == null)
			return null;
		
		return getSubset(key.toString());
	}

	/**
	 * 得到某个配置的子集，如config.user－得到'user'配置的子集。此函数提供给模板使用
	 * 
	 * @param name
	 * @return
	 */
	public ConfigSubset getSubset(String name) {
		return new ConfigSubset(this, name);
	}

}
