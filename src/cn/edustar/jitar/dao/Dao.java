package cn.edustar.jitar.dao;

/**
 * 通用 DAO 接口
 * 
 * @author Yang Xinxin
 * @version 1.0.0 Apr 20, 2009 4:18:44 PM
 */
public interface Dao {
	
	/**
	 * 删除 Hibernate session cache 中的所有对象
	 * 
	 * @param object
	 */
	public void evict(Object object);

	/**
	 * 从数据库清除所有在保存，修改和删除期间的对象
	 */
	public void flush();
	
}
