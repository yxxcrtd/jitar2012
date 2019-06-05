package cn.edustar.jitar.pojos;

/**
 * 表示一个配置项在 db 中存储的实体对象.
 *   itemType + name 被用做业务键.
 */
public class Config {
	/** 配置项标识，数据库自动产生 */
	private int id;
	
	/** 此配置项属于哪个对象类型，如 'system' */
	private String itemType = "system";
	
	/** 配置项的键，不重复，非空 (alias key) */
	private String name;
	
	/** 此配置的值，依据 type 解释其实际值 */
	private String value;
	
	/** 此配置项的类型，当前支持 'int[eger]', 'string', 'bool[ean]', 'date' */
	private String type = "string";
	
	/** 缺省值，可选 */
	private String defval;
	
	/** 中文标题，可选 */
	private String title;
	
	/** 描述，可选 */
	private String description;

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return "Config{id=" + id + 
			", itemType=" + itemType +
			", name=" + name +
			", value=" + this.value +
			"}";
	}
	
	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return PojoHelper.hashCode(itemType, name);
	}
	
	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object o) {
		if (o == this) return true;
		if (o == null || !(o instanceof Config)) return false;
		
		Config other = (Config)o;
		return PojoHelper.equals(name, other.name) && PojoHelper.equals(itemType, other.itemType);
	}
	
	/** 配置项标识，数据库自动产生 */
	public int getId() {
		return id;
	}

	/** 配置项标识，数据库自动产生 */
	public void setId(int id) {
		this.id = id;
	}

	/** 此配置项属于哪个对象类型，如 'system' */
	public String getItemType() {
		return this.itemType;
	}

	/** 此配置项属于哪个对象类型，如 'system' */
	public void setItemType(String itemType) {
		this.itemType = itemType;
	}
	
	/** 配置项的键，不重复，非空 (alias key) */
	public String getName() {
		return name;
	}

	/** 配置项的键，不重复，非空 (alias key) */
	public void setName(String name) {
		this.name = name;
	}

	/** 此配置的值，依据 type 解释其实际值 */
	public String getValue() {
		return value;
	}

	/** 此配置的值，依据 type 解释其实际值 */
	public void setValue(String value) {
		this.value = value;
	}

	/** 此配置项的类型，当前支持 'int[eger]', 'string', 'bool[ean]', 'date' */
	public String getType() {
		return type;
	}

	/** 此配置项的类型，当前支持 'int[eger]', 'string', 'bool[ean]', 'date' */
	public void setType(String type) {
		this.type = type;
	}

	/** 缺省值，可选 */
	public String getDefval() {
		return defval;
	}

	/** 缺省值，可选 */
	public void setDefval(String defval) {
		this.defval = defval;
	}

	/** 中文标题，可选 */
	public String getTitle() {
		return title;
	}

	/** 中文标题，可选 */
	public void setTitle(String title) {
		this.title = title;
	}

	/** 描述，可选 */
	public String getDescription() {
		return description;
	}

	/** 描述，可选 */
	public void setDescription(String description) {
		this.description = description;
	}
}
