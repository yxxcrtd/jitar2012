package cn.edustar.jitar.service;

import cn.edustar.jitar.pojos.User;

/**
 * 能够在 PrivilegeManager 内容权限的对象要实现的接口.
 *
 *
 */
public interface PrivilegeContent {
	/**
	 * 得到内容的发布者.
	 * @return
	 */
	public User getContentPublisher();
	
	/**
	 * 得到内容所属学科标识, 可能返回 null.
	 * @return
	 */
	public Integer getContentSubject();
	
	/**
	 * 得到内容所属学段, 可能返回 null.
	 * @return
	 */
	public Integer getContentGrade();
}
