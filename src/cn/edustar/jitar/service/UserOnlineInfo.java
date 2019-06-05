package cn.edustar.jitar.service;

import java.util.Date;

/**
 * 用户在线活动信息
 * 
 *
 */
public interface UserOnlineInfo {

	/**
	 * 得到用户登录名
	 * 
	 * @return
	 */
	public String getLoginName();

	/**
	 * 获得该用户最后在线进行活动的时间
	 * 
	 * @return
	 */
	public Date getLastActiveTime();

	/**
	 * 获得用户最后在线进行的活动, 可能为 null
	 * 
	 * @return
	 */
	public String getLastAction();
	
}
