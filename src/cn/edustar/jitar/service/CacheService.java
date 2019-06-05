package cn.edustar.jitar.service;

import java.util.List;

/**
 * 定义缓存的基本操作接口.
 * 
 * @author Yang Xinxin
 * @version 1.0.0 Mar 13, 2008 8:16:55 AM
 */
public interface CacheService {
	/**
	 * 获得匹配指定键值的元素值.
	 * 
	 * @param key - 元素的键值.
	 * @return Object 之前放入缓存的元素值，如果找不到或者过期则为 null.
	 */
	public Object get(String key);

	/**
	 * 放置对象到缓存中.
	 * 
	 * @param key 字符串键值.
	 * @param value 元素值, 最好可序列化.
	 */
	public void put(String key, Object value);

	/**
	 * 放置对象到缓存中, 支持设置过期时间的.
	 * 
	 * @param key
	 * @param value
	 * @param timeToLive ehcache the number of seconds to live
	 */
	public void put(String key, Object value, int timeToLive);

	/**
	 * 清除匹配键值的元素值，如果没有元素匹配，则没有清除动作且不抛出任何异常.
	 * 
	 * @param key-需要清除的元素键值.
	 */
	public void remove(String key);

	/**
	 * 清除缓存中所有元素，但保留缓存在可用状态.
	 */
	public void clear();
	
	public List<String> getAllKeys();
	
	public boolean touch(String key, int newExpireTime);
	
	public void setExpireTime(int expireTime);
	
	public int getExpireTime();
}
