package cn.edustar.jitar.service;

import java.util.Date;

/**
 * 用户在线信息的服务
 * 
 *
 * 
 * 在线信息通常保留在内存以备快速查询, 也许以后该服务应该放在统一用户中完成
 */
public interface OnlineManager {
	
	/**
	 * 判断指定用户是否在线
	 * 
	 * @param loginName	 */
	public boolean isOnline(String loginName);
	
	/**
	 * 设置指定用户在活动状态
	 * 	 * @param loginName	 * @param activeTime 最后活动时间，一般是调用时间，如果未给出则使用当前时间
	 * @param action 正在进行的活动
	 */
	public void userActive(String loginName, Date activeTime, String action);
	
	/**
	 * 得到指定用户活动记录
	 * 
	 * @param loginName
	 * @return 返回的活动记录包括最后活动时间，正在进行的活动	 */
	public UserOnlineInfo getActiveInfo(String loginName);
	
}
