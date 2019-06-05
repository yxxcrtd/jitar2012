package cn.edustar.jitar.service.impl;

import java.util.List;
import java.util.concurrent.TimeoutException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.edustar.jitar.service.CacheService;
import net.rubyeye.xmemcached.XMemcachedClient;
import net.rubyeye.xmemcached.exception.MemcachedException;

/**
 * 基于 memcached 缓存服务的实现。此类的方法是为了兼容EHCache的实现方法，如果单独采用 Memcached，无需这样实现。
 * 
 * @author mxh
 * 
 */
public class MemcachedServiceImpl implements CacheService {

	private Logger log = LoggerFactory.getLogger(MemcachedServiceImpl.class);

	/** 缓存的名字，为了兼容 EHCache 的方法，加入这个属性，但此实现可能不用。 */
	private String cacheName = "";

	/** memcached 客户端 */
	private XMemcachedClient memcachedClient;

	/** 缓存超时时间，单位是秒，用来设置缓存的存活时间。 */
	private int expireTime = 60 * 60;

	public void init() {

	}

	public MemcachedServiceImpl(XMemcachedClient memcachedClient, String cacheName) {
		this.memcachedClient = memcachedClient;
		this.cacheName = cacheName;
	}

	public MemcachedServiceImpl() {
	}

	public MemcachedServiceImpl(MemcachedProvider provider, String cacheName) {
		this.memcachedClient = provider.getMemcachedClient();
		this.cacheName = cacheName;
	}

	/**
	 * 从缓存中取出数据。
	 * 
	 * @param key
	 * @return
	 */
	public Object get(String key) {
		String cacheKey = this.cacheName + key;
		Object cachedData = null;
		try {
			cachedData = this.memcachedClient.get(cacheKey);
		} catch (TimeoutException | InterruptedException | MemcachedException e) {
			log.error("获取缓存时出现错误：key=" + key + "，错误描述：", e);
		}
		return cachedData;
	}

	/**
	 * 向缓存中加入数据。
	 * 
	 * @param key
	 * @param value
	 */
	public void put(String key, Object value) {
	    if(null == value){
	        return;
	    }
		String cacheKey = this.cacheName + key;
		try {
			this.memcachedClient.set(cacheKey, expireTime, value); // 第二个参数标识缓存过期时间
		} catch (TimeoutException | InterruptedException | MemcachedException e) {
			log.error("添加缓存时出现错误：key=" + key + "，错误描述：", e);
		}
	}

	/**
	 * 向缓存中加入数据，并设置缓存的失效时间（秒）。此方法是为了兼容EHCache的写法，以后可能不再使用。
	 * 
	 * @param key
	 * @param value
	 */
	public void put(String key, Object value, int timeToLive) {
	    if(null == value){
            return;
        }
		String cacheKey = this.cacheName + key;
		try {
			this.memcachedClient.set(cacheKey, timeToLive, value); // 第二个参数标识缓存过期时间
		} catch (TimeoutException | InterruptedException | MemcachedException e) {
			log.error("添加缓存时出现错误：key=" + key + "，错误描述：", e);
		}
	}

	/**
	 * 删除一条缓存。
	 * 
	 * @param key
	 */
	public void remove(String key) {
	    if(null == key){
            return;
        }
		String cacheKey = this.cacheName + key;
		try {
			this.memcachedClient.delete(cacheKey);
		} catch (TimeoutException | InterruptedException | MemcachedException e) {
			log.error("删除缓存时出现错误：key=" + key + "，错误描述：", e);
		}
	}

	/**
	 * 删除一条缓存，等同于 remove方法，为了了使用方便而加入的方法。
	 * 
	 * @param key
	 */
	public void delete(String key) {
	    if(null == key){
            return;
        }
		String cacheKey = this.cacheName + key;
		this.remove(cacheKey);
	}

	/**
	 * 重置缓存过期时间。重置缓存过期时间，不用把数据取出来，直接更新即可。
	 * 
	 * @param key
	 *            要更新的key。
	 * @param newExpireTime
	 *            新的缓存过期时间。
	 * @return
	 */
	public boolean touch(String key, int newExpireTime) {
	    if(null == key){
            return true;
        }
		String cacheKey = this.cacheName + key;
		try {
			return this.memcachedClient.touch(cacheKey, newExpireTime);
		} catch (TimeoutException | InterruptedException | MemcachedException e) {
			log.error("重置缓存过期时间出现错误：key=" + key + "，错误描述：", e);
		}
		return false;
	}

	public void setMemcachedClient(XMemcachedClient memcachedClient) {
		this.memcachedClient = memcachedClient;
	}

	public void setExpireTime(int expireTime) {
		this.expireTime = expireTime;
	}

	public int getExpireTime() {
		return expireTime;
	}

	@Override
	public void clear() {
		// TODO Auto-generated method stub

	}

	/**
	 * 由于性能和实现方法问题，memcached不提供这个方法。
	 */
	@SuppressWarnings({"unchecked", "rawtypes"})
    @Override
	public List getAllKeys() {
	    //返回空的List是为了避免循环的时候出错。
	    return java.util.Collections.EMPTY_LIST;
	}

}
