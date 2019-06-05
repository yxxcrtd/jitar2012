package cn.edustar.jitar.user;

/**
 * 内存中的用户Session对象
 */
public class UserSession {
	   
	// 当前的SessionId
	private String sessionId;

	// 用户名
	private String username;

	// 用户真实姓名
	private String trueName;

	// 角色Id
	private int positionId;
	
	// 是否管理员（权限表AccessControl中有当前用户的用户Id的就是管理员）
	private int isAdmin;
	
	// 超级管理员的名称，详见：AccessControl.java中的objectType
	private String superAdmin;

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getTrueName() {
		return trueName;
	}

	public void setTrueName(String trueName) {
		this.trueName = trueName;
	}

	public int getPositionId() {
		return positionId;
	} 

	public void setPositionId(int positionId) {
		this.positionId = positionId;
	}

	public int getIsAdmin() {
		return isAdmin;
	}

	public void setIsAdmin(int isAdmin) {
		this.isAdmin = isAdmin;
	}

	public String getSuperAdmin() {
		return superAdmin;
	}

	public void setSuperAdmin(String superAdmin) {
		this.superAdmin = superAdmin;
	}
	
}
