package cn.edustar.jitar.pojos;

import java.io.Serializable;

/**
 * 在线统计
 * 
 * @author Yang xinxin
 * @version 1.0.0 Apr 13, 2009 16:21:51 PM
 */
public class UserOnLineStat implements Serializable, Cloneable {

	/** serialVersionUID */
	private static final long serialVersionUID = 4102787072770329288L;

	/** 对象标识 */
	private int id;

	/** 最高在线人数*/
	private int highest = 0;

	/** 最高在线人数出现的时间 */
	private String appearTime;
	
	/**
	 * Default Constructor
	 */
	public UserOnLineStat() {
		// 
	}

	/** 对象标识 */
	public int getId() {
		return id;
	}

	/** 对象标识 */
	public void setId(int id) {
		this.id = id;
	}

	/** 最高在线人数*/
	public int getHighest() {
		return highest;
	}

	/** 最高在线人数*/
	public void setHighest(int highest) {
		this.highest = highest;
	}

	/** 最高在线人数出现的时间 */
	public String getAppearTime() {
		return appearTime;
	}

	/** 最高在线人数出现的时间 */
	public void setAppearTime(String appearTime) {
		this.appearTime = appearTime;
	}
	

}
