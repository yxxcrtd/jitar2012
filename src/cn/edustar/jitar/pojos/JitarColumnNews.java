package cn.edustar.jitar.pojos;

import java.io.Serializable;
import java.util.Date;

public class JitarColumnNews implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3869464928634226785L;

	private int columnNewsId;
	private int columnId;
	private int userId;
	private String title;
	private String content;
	private String picture;
	private Date createDate = new Date();
	private int viewCount = 0;

	public JitarColumnNews() {
	}

	public int getColumnNewsId() {
		return columnNewsId;
	}

	public void setColumnNewsId(int columnNewsId) {
		this.columnNewsId = columnNewsId;
	}

	public int getColumnId() {
		return columnId;
	}

	public void setColumnId(int columnId) {
		this.columnId = columnId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public int getViewCount() {
		return viewCount;
	}

	public void setViewCount(int viewCount) {
		this.viewCount = viewCount;
	}
}
