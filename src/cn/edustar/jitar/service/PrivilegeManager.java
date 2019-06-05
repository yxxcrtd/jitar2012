package cn.edustar.jitar.service;

import cn.edustar.jitar.pojos.Article;
import cn.edustar.jitar.pojos.Resource;
import cn.edustar.jitar.pojos.User;

/**
 * 用户/协作组/文章/资源权限检查管理器.
 *
 *
 */
public interface PrivilegeManager {
	/**
	 * 判定指定用户是不是一个合法的管理者.
	 * @return
	 */
	public boolean isValidManager(User user);
	
	/**
	 * 检查指定管理者 manager 是否对指定用户 user 具有操作权.
	 * @param user - 被操作的用户. 
	 * @param manager - 管理者.
	 * @return
	 */
	public boolean canManageUser(User user, User manager);

	/**
	 * 检测是否用户 user 发表的文章 article 位于管理者 manager 的管理范围.
	 * @param user
	 * @param article
	 * @param manager
	 * @return
	 */
	public boolean canManageArticle(User user, Article article, User manager);

	/**
	 * 检测是否用户 user 发表的资源 resource 位于管理者 manager 的管理范围.
	 * @param user
	 * @param resource
	 * @param manager
	 * @return
	 */
	public boolean canManageResource(User user, Resource resource, User manager);
}
