package cn.edustar.jitar.service;

import java.util.List;

import cn.edustar.jitar.pojos.UserOnLine;

/**
 * 在线服务
 *
 * @author Yang xinxin
 * @version 1.0.0 Apr 10, 2009 4:03:31 PM
 */
public interface OnLineService {
	
	/**
	 * 在线注册用户列表
	 *
	 * @param optTime
	 * @return
	 */
	public List<UserOnLine> findOnLineUser(long optTime);

	/**
	 * 在线的注册人数
	 *
	 * @param optTime
	 * @return
	 */
	public long getOnLineUesrNum(long optTime);

	/**
	 * 在线的游客人数
	 *
	 * @param optTime
	 * @return
	 */
	public long getOnLineGuestNum(long optTime);

	/**
	 * 保存在线用户对象
	 *
	 * @param userOnLine
	 */
	public void saveUserOnLine(UserOnLine userOnLine);

	/**
	 * 根据 在线用户名称 查找 在线用户
	 *
	 * @param userName
	 * @return
	 */
	public UserOnLine findUserOnLineByUserName(String userName);

	/**
	 * 删除在线用户
	 *
	 * @param userOnLine
	 */
	public void removeUserOnLine(UserOnLine userOnLine);

	/**
	 * 删除过期时间的在线用户
	 *
	 * @param optTime
	 */
	public void removeUserOnLineOutTime(long optTime);
	
}
