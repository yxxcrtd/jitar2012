package cn.edustar.usermgr;

/**
 * 用户常量
 */
public interface UserConst {
	
	// 0：正常
	public static final int USER_STATUS_NORMAL = 0;
	
	// 1：待审核
	public static final int USER_STATUS_WAIT_AUTID = 1;
	
	// 2：已删除
	public static final int USER_STATUS_DELETED = 2;
	
	// 3：已锁定
	public static final int USER_STATUS_LOCKED = 3;
	
}
