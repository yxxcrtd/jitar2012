package cn.edustar.jitar.service;

/**
 * 缓存管理器.
 *
 *
 */
public interface CacheProvider {
	/**
	 * 得到指定名字的缓存服务对象.
	 * @param cacheName
	 * @return
	 */
	public CacheService getCache(String cacheName);
}
