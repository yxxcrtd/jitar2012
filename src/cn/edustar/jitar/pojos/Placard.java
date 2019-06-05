package cn.edustar.jitar.pojos;

import java.util.Date;

/**
 * 表示一个公告，可以被用户或群组或任何其它需要公告的对象使用。表格 S_Placard
 * 
 *   此对象暂时没有业务键。(也许以后以 objId, objType, createDate 联合起来为业务键)
 * 
 *
 *
 */
public class Placard implements java.io.Serializable {
	/** serialVersionUID */
	private static final long serialVersionUID = -777527150484267160L;

	/** 对象标识。 */
	private int id;

	/** 使用公告的对象类型, 参见 model.ObjectType 的定义。 */
	private int objType;
	
	/** 对象(用户或群组)标识。 */
	private int objId;
	
	/** 发布公告的用户标识 */
	private int userId;
	
	/** 创建/最后修改的日期。 */
	private Date createDate = new Date();
	
	/** 是否隐藏此公告。 */
	private boolean hide;
	
	/** 公告标题 */
	private String title;
	
	/** 公告内容。 */
	private String content;

	/** 对象标识。 */
	public int getId() {
		return id;
	}
	
	/** 对象标识。 */
	public int getPlacardId() {
		return this.id;
	}
	
	/** 对象标识。 */
	public void setPlacardId(int id) {
		this.id = id;
	}

	/** 对象标识。 */
	public void setId(int id) {
		this.id = id;
	}
	
	/** 使用公告的对象类型, 参见 model.ObjectType 的定义。 */
	public int getObjType() {
		return this.objType;
	}
	
	/** 使用公告的对象类型, 参见 model.ObjectType 的定义。 */
	public void setObjType(int objType) {
		this.objType = objType;
	}

	/** 对象(用户或群组)标识。 */
	public int getObjId() {
		return objId;
	}

	/** 对象(用户或群组)标识。 */
	public void setObjId(int objId) {
		this.objId = objId;
	}

	/** 发布公告的用户标识 */
	public int getUserId() {
		return this.userId;
	}
	
	/** 发布公告的用户标识 */
	public void setUserId(int userId) {
		this.userId = userId;
	}

	/** 创建/最后修改的日期。 */
	public Date getCreateDate() {
		return createDate;
	}

	/** 创建/最后修改的日期。 */
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	/** 是否隐藏此公告。 */
	public boolean getHide() {
		return hide;
	}

	/** 是否隐藏此公告。 */
	public void setHide(boolean hide) {
		this.hide = hide;
	}

	/** 公告标题 */
	public String getTitle() {
		return this.title;
	}
	
	/** 公告标题 */
	public void setTitle(String title) {
		this.title = title;
	}

	/** 公告内容。 */
	public String getContent() {
		return content;
	}

	/** 公告内容。 */
	public void setContent(String content) {
		this.content = content;
	}
}
