package cn.edustar.jitar.pojos;

/**
 * SPlugin entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class Plugin implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -3713140188049739470L;
	private Integer pluginId;
	private String pluginName;
	private String pluginTitle;
	private int itemOrder;
	private int enabled;
	private String pluginType;	
	private String icon;	
	
	// Constructors

	/** default constructor */
	public Plugin() {
	}

	/** full constructor */
	public Plugin(String pluginName, String pluginTitle, int itemOrder) {
		this.pluginName = pluginName;
		this.pluginTitle = pluginTitle;
		this.itemOrder = itemOrder;
	}

	public Integer getPluginId() {
		return pluginId;
	}

	public void setPluginId(Integer pluginId) {
		this.pluginId = pluginId;
	}

	public String getPluginName() {
		return pluginName;
	}

	public void setPluginName(String pluginName) {
		this.pluginName = pluginName;
	}

	public String getPluginTitle() {
		return pluginTitle;
	}

	public void setPluginTitle(String pluginTitle) {
		this.pluginTitle = pluginTitle;
	}

	public int getItemOrder() {
		return itemOrder;
	}

	public void setItemOrder(int itemOrder) {
		this.itemOrder = itemOrder;
	}

	public int getEnabled() {
		return enabled;
	}

	public void setEnabled(int enabled) {
		this.enabled = enabled;
	}

	public String getPluginType() {
		return pluginType;
	}

	public void setPluginType(String pluginType) {
		this.pluginType = pluginType;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	
}