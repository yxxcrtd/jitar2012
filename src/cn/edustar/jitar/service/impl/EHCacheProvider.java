package cn.edustar.jitar.service.impl;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.edustar.jitar.service.CacheProvider;
import cn.edustar.jitar.service.CacheService;

/**
 * ehcache 的管理器.
 * 
 * 
 */
public class EHCacheProvider implements CacheProvider {
	/** 日志记录器 */
	public static final Log logger = LogFactory.getLog(EHCacheProvider.class);

	private CacheManager manager = null;

	/**
	 * 由 spring 调用进行初始化.
	 */
	public void init() {
		// 创建缺省的 ehcache 管理器.
		this.manager = CacheManager.create();
	}

	public CacheService getCache(String cacheName) {
		return new EHCacheServiceImpl(this, cacheName);
	}

	/**
	 * 得到指定名字的缓存.
	 * 
	 * @param cacheName
	 * @return
	 */
	public Cache getEHCache(String cacheName) {
		// 如果 cache 不存在, 则现增加一个, 这样也许不好.
		Lock lock = new ReentrantLock();
		lock.lock();
		if (!manager.cacheExists(cacheName)) {
			logger.warn("指定名字为 " + cacheName
					+ " 的缓存不存在, 将自动增加一个. 请检查配置文件 ehcache.xml.");
			manager.addCache(cacheName);
		}
		lock.unlock();
		return manager.getCache(cacheName);
	}

	/**
	 * 由 spring 调用进行销毁操作.
	 */
	public void destroy() {
		this.manager.shutdown();
	}
}
