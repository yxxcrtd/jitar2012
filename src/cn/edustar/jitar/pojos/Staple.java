package cn.edustar.jitar.pojos;

import java.util.Date;

/**
 * 用户个人分类, 用于对好友, 订阅, 收藏, 友情链接, 音乐等非主内容的单层分类.
 * 
 * @author mengxianhui, liujunxing
 */
public class Staple implements java.io.Serializable {
	/** serialVersionUID */
	private static final long serialVersionUID = 2564787875231387902L;
	
	// Fields

	/** 分类标识，数据库自动生成主键。 */
	private int stapleId;
	
	/** 分类名，可使用中文。别名：name。 */
	private String stapleName;
	
	/** 创建、使用该分类的用户标识。 */
	private int userId;
	
	/** 该分类被应用在哪种用途。缺省0，非空。参见 ObjectType. */
 	private int stapleType;
 	
	/** 该分类的排序号。一般从1开始。缺省0，非空。 */
	private int orderNum;
	
 	/** 不可见标志。缺省false。false - 可见，true - 不可见。 */
	private boolean invisible;
	
	/** 该分类的创建日期。默认值：当前。 */
	private Date createDate = new Date();
	
	/** 此分类的图标 */
	private String icon;
	
	/** 该分类中博文数量，仅用于文章分类。(StapleType=0) */
	@Deprecated
	private int blogNum;
	
	/** 浏览量统计数字？（不明，暂时不用） */
	private int views;

	// Constructors

	/** default constructor */
	public Staple() {
	}

	// Property accessors

	/** 得到分类标识，数据库自动生成主键。 */
	public int getId() {
		return this.stapleId;
	}
	
	/** 分类标识，数据库自动生成主键。 */
	public int getStapleId() {
		return this.stapleId;
	}

	/** 分类标识，数据库自动生成主键。 */
	public void setStapleId(int stapleId) {
		this.stapleId = stapleId;
	}

	/** 创建、使用该分类的用户标识。 */
	public int getUserId() {
		return this.userId;
	}

	/** 创建、使用该分类的用户标识。 */
	public void setUserId(int userId) {
		this.userId = userId;
	}

	/** 分类名，可使用中文。别名：name。 */
	public String getStapleName() {
		return this.stapleName;
	}

	/** 分类名，可使用中文。别名：name。 */
	public void setStapleName(String stapleName) {
		this.stapleName = stapleName;
	}

	/** 该分类的排序号。一般从1开始。缺省0，非空。 */
	public int getOrderNum() {
		return this.orderNum;
	}

	/** 该分类的排序号。一般从1开始。缺省0，非空。 */
	public void setOrderNum(int orderNum) {
		this.orderNum = orderNum;
	}

	/** 该分类被应用在哪种用途。缺省0，非空。参见 ObjectType. */
	public int getStapleType() {
		return this.stapleType;
	}

	/** 该分类被应用在哪种用途。缺省0，非空。参见 ObjectType. */
	public void setStapleType(int stapleType) {
		this.stapleType = stapleType;
	}

	/** 不可见标志。缺省false。false - 可见，true - 不可见。 */
	public boolean getInvisible() {
		return this.invisible;
	}

	/** 不可见标志。缺省false。false - 可见，true - 不可见。 */
	public void setInvisible(boolean invisible) {
		this.invisible = invisible;
	}

	/** 该分类的创建日期。默认值：当前。 */
	public Date getCreateDate() {
		return this.createDate;
	}

	/** 该分类的创建日期。默认值：当前。 */
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	/** 此分类的图标 */
	public String getIcon() {
		return this.icon;
	}
	
	/** 此分类的图标 */
	public void setIcon(String icon) {
		this.icon = icon;
	}

	/** 该分类中博文数量，仅用于文章分类。(StapleType=0) */
	@Deprecated
	public int getBlogNum() {
		return this.blogNum;
	}

	/** 该分类中博文数量，仅用于文章分类。(StapleType=0) */
	@Deprecated
	public void setBlogNum(int blogNum) {
		this.blogNum = blogNum;
	}

	/** 浏览量统计数字？（不明，暂时不用） */
	public int getViews() {
		return this.views;
	}

	/** 浏览量统计数字？（不明，暂时不用） */
	public void setViews(int views) {
		this.views = views;
	}

}