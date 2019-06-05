package cn.edustar.jitar.pojos;

import java.io.Serializable;
import java.util.Date;

/**
 * 论坛版面
 * @author Yang XinXin
 * @version 1.0.0 Mar 17, 2008 9:06:49 AM
 */
public class Board implements Serializable {

	/** serialVersionUID*/
	private static final long serialVersionUID = -2420175648863451858L;

	/** 对象标识 */
	private int boardId;

	/** 论坛唯一名字, 'gbbs_$groupId', 'user_$userid', 'gbbs2_$groupid' */
	private String name;

	/** 论坛中文标题. */
	private String title;
	
	/** (统计数据)留言板的访客数量 */
	private int visitor_count;

	/** (统计数据)主题数量 */
	private int topic_count;

	/** 此论坛创建的时间 */
	private Date createDate = new Date();
	
	/** 版主, '|' 分隔的, 'yang|meng|' */
	private String boardMaster;

	// private String perm;

	public Board() {
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this)
			return true;
		if (obj == null || !(obj instanceof Board))
			return false;

		Board other = (Board) obj;
		return PojoHelper.equals(this.name, other.name);
	}

	@Override
	public int hashCode() {
		return PojoHelper.hashCode(this.name);
	}

	@Override
	public String toString() {
		return "Board{id=" + this.boardId + 
			",name=" + this.name + 
			",title=" + title + "}";
	}
	
	/** 对象标识 */
	public int getBoardId() {
		return boardId;
	}
	/** 对象标识 */
	public void setBoardId(int boardId) {
		this.boardId = boardId;
	}
	/** 论坛唯一名字 */
	public String getName() {
		return name;
	}
	/** 论坛唯一名字 */
	public void setName(String name) {
		this.name = name;
	}
	/** 论坛中文标题. */
	public String getTitle() {
		return title;
	}
	/** 论坛中文标题. */
	public void setTitle(String title) {
		this.title = title;
	}
	
	/** 版主 */
	public String getBoardMaster() {
		return boardMaster;
	}
	
	/** 版主 */
	public void setBoardMaster(String boardMaster) {
		this.boardMaster = boardMaster;
	}

	/** 留言板的访客数量 */
	public int getVisitorCount() {
		return visitor_count;
	}

	/** 留言板的访客数量 */
	public void setVisitorCount(int visitor_count) {
		this.visitor_count = visitor_count;
	}

	/** 主题数量 */
	public int getTopicCount() {
		return topic_count;
	}

	/** 主题数量 */
	public void setTopicCount(int topic_count) {
		this.topic_count = topic_count;
	}

	/** 此论坛创建的时间 */
	public Date getCreateDate() {
		return createDate;
	}

	/** 此论坛创建的时间 */
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
}
