package cn.edustar.jitar.pojos;

import java.io.Serializable;

/**
 * 相册分类管理
 * 
 * @author Yang Xinxin
 * @version 1.0.0 Apr 28, 2008 4:31:30 PM
 */
public class PhotoStaple implements Serializable {

	/** serialVersionUID */
	private static final long serialVersionUID = -6883093355597195801L;

	/** 对象标识 */
	private int id;

	/** 用户标识。 */
	private int userId;

	/** 分类排序 */
	private int orderNum;

	/** 分类名称 */
	private String title;

	/** 分类描述 */
	private String stapleDescribe;

	/** 是否隐藏(0:不隐藏,1:隐藏) */
	private boolean isHide;

	/** 上级分类  */
	private Integer parentId;
	/** 父分类的整路径. 如/1/3/8/，指父分类为1，3，8. */
	private String parentPath = "/";
	
	/** Constructor */
	public PhotoStaple() {

	}

	// Property accessors
	/** 对象标识 */
	public int getId() {
		return id;
	}

	/** 对象标识 */
	public void setId(int id) {
		this.id = id;
	}

	/** 用户标识。 */
	public int getUserId() {
		return userId;
	}

	/** 用户标识。 */
	public void setUserId(int userId) {
		this.userId = userId;
	}

	/** 分类排序 */
	public int getOrderNum() {
		return orderNum;
	}

	/** 分类排序 */
	public void setOrderNum(int orderNum) {
		this.orderNum = orderNum;
	}

	/** 分类名称 */
	public String getTitle() {
		return title;
	}

	/** 分类名称 */
	public void setTitle(String title) {
		this.title = title;
	}

	/** 分类描述 */
	public String getStapleDescribe() {
		return stapleDescribe;
	}

	/** 分类描述 */
	public void setStapleDescribe(String stapleDescribe) {
		this.stapleDescribe = stapleDescribe;
	}

	/** 是否隐藏(0:不隐藏,1:隐藏) */
	public boolean isHide() {
		return isHide;
	}

	/** 是否隐藏(0:不隐藏,1:隐藏) */
	public boolean getIsHide() {
		return isHide;
	}

	/** 是否隐藏(0:不隐藏,1:隐藏) */
	public void setHide(boolean isHide) {
		this.isHide = isHide;
	}

	/** 是否隐藏(0:不隐藏,1:隐藏) */
	public void setIsHide(boolean isHide) {
		this.isHide = isHide;
	}

	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}
	
	/**
	 * 得到分类自己的路径 = parentPath + id + '/'
	 * @return
	 */
	public String getCategoryPath() {
		return this.getParentPath() + this.id + "/";
	}
	
	/** 父分类的整路径. 如/1/3/8/，指父分类为1，3，8. */
	public String getParentPath() {
		if(null == this.parentPath){
			this.parentPath = "/";
		}
		return this.parentPath;
	}

	/** 父分类的整路径. 如/1/3/8/，指父分类为1，3，8. */
	public void setParentPath(String parentPath) {
		this.parentPath = parentPath;
	}	
}