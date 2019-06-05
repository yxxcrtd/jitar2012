package cn.edustar.jitar.pojos;
import java.io.Serializable;
 
/***
 * 聊天颜色

 * @author bai mindong
 * @version 1.0.0 Mar 17, 2008 9:06:49 AM
 * 
 */
public class ChatColor implements Serializable {

	/** serialVersionUId */
	private static final long serialVersionUID = -483652132446523709L;

	/** 标识 */
	private int id;
	
	/** 名称 */
	private String colorName; 

	/** 数值 */
	private String colorValue; 
	
	/***
	 * 无参构造函数
	 */
	public ChatColor() {
	}

	/** 标识 */
	public int getId() {
		return id;
	}
	
	/** 标识 */
	public void setId(int id) {
		this.id = id;
	}

	/** 名称 */
	public String getColorName() {
		return colorName;
	}
	
	public void setColorName(String  colorName) {
		this.colorName = colorName;
	}
	
	public String getColorValue() {
		return colorValue;
	}
	
	public void setColorValue(String colorValue) {
		this.colorValue = colorValue;
	}
	
}
