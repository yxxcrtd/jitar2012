package cn.edustar.jitar.user;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import cn.edustar.jitar.util.*;

public final class UserComments {
	private static UserComments instance = new UserComments();// 私有的静态的本类实例的变量;

	// 公共的静态的提供本类实例的方法;
	public static UserComments getInstance() {
		return instance;
	}

	private UserComments() {
	} // 私有化构造方法;

	private List<UserComment> comments = new ArrayList<UserComment>();

	public boolean CanAddComment(int userId, int objType, long minutes) {		
		if (comments.size() == 0) {
			return true;
		} else {
			for (int i = 0; i < comments.size(); i++) {
				UserComment userComment = comments.get(i);
				if (userComment.getUserId() == userId && userComment.getObjType() == objType) {
					try {
						long splitMinutes = DateUtil.minutesNum(userComment.getCreateDate());
						splitMinutes = Math.abs(splitMinutes);
						//System.out.println("间隔时间分钟:" + splitMinutes);
						if (splitMinutes >= minutes) {
							return true;
						} else {
							return false;
						}
					} catch (Exception ex) {
						return true;
					}
				}
			}
		}
		return true;
	}
	
	public void DeleteComment(int userId, int objType, String comment)
	{
		if (comments.size() == 0) {
			return;
		}
		for (int i = 0; i < comments.size(); i++) {
			UserComment userComment = comments.get(i);
			if (userComment.getUserId() == userId
					&& userComment.getObjType() == objType 
					&& userComment.getComment().equalsIgnoreCase(comment)) {
				comments.remove(userComment);
			}
		}
		
	}

	public boolean CommentIsEqual(int userId, int objType, String comment) {
		if (comments.size() == 0) {
			return false;
		} else {
			for (int i = comments.size()-1; i >-1; i--) {
				UserComment userComment = comments.get(i);
				if (userComment.getUserId() == userId
						&& userComment.getObjType() == objType) {
					String oldComment = userComment.getComment();
					if (oldComment.equals(comment)) {
						return true;
					} else if (oldComment.indexOf(comment) >= 0
							|| comment.indexOf(oldComment) >= 0) {
						return true;
					} else {
						return false;
					}
				}
			}
		}
		return false;
	}

	public void AddCommentStatus(int userId, int objType, String comment) {
		UserComment userComment;
		for (int i = 0; i < comments.size(); i++) {
			userComment = comments.get(i);
			if (userComment.getUserId() == userId
					&& userComment.getObjType() == objType) {
				userComment.setCreateDate(new Date());
				userComment.setComment(comment);
				return;
			}
		}
		userComment = new UserComment();
		userComment.setObjType(objType);
		userComment.setUserId(userId);
		userComment.setComment(comment);
		comments.add(userComment);
	}
}

class UserComment {
	/** 发表评论的作者 */
	private int userId;

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	/** 被评论的对象类型，参见 ObjectType 定义 */
	private int objType;

	public int getObjType() {
		return objType;
	}

	public void setObjType(int objType) {
		this.objType = objType;
	}

	/** 评论发表时间 */
	private Date createDate = new Date();

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	/** 评论内容 */
	private String comment;

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

}