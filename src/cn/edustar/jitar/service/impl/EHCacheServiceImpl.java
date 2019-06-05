package cn.edustar.jitar.service.impl;

import java.util.List;

import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;
import cn.edustar.jitar.service.CacheService;

/**
 * 缓存接口的实现.
 * 
 * @author Yang Xinxin
 * @version 1.0.0 Mar 13, 2008 8:16:55 AM
 */
public class EHCacheServiceImpl implements CacheService {
	/** 缓存管理器. */
	private final EHCacheProvider provider;

	/** 缓存的名字 */
	private  String cacheName;

	/** ehcache 实例 */
	private final Cache cache;
	
	/** 缓存默认到期时间 */
	private int expireTime = 60 * 60;

	/**
	 * 使用指定的缓存管理器和缓存名字构造一个 EHCacheServiceImpl 的新实例.
	 * @param provider
	 * @param cacheName
	 */
	public EHCacheServiceImpl(EHCacheProvider provider, String cacheName) {
		this.provider = provider;
		this.cacheName = cacheName;
		this.cache = provider.getEHCache(cacheName);
	}
	
	/** 得到缓存管理器. */
	public EHCacheProvider getCacheProvider() {
		return this.provider;
	}
	
	/** 缓存的名字 */
	public String getCacheName() {
		return this.cacheName;
	}	
	public void setCacheName(String cacheName) {
		this.cacheName = cacheName;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.edustar.jitar.service.iface.CacheService#get(java.lang.String)
	 */
	public Object get(String key) {
		if (cache == null) return null;
		Element element = cache.get(key);
		return element == null ? null : element.getObjectValue();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.edustar.jitar.service.iface.CacheService#put(java.lang.String, java.lang.Object)
	 */
	public void put(String key, Object value) {
		if (cache == null) return;
		Element element = new Element(key, value);
		cache.put(element);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.edustar.jitar.service.CacheService#put(java.lang.String, java.lang.Object, int)
	 */
	public void put(String key, Object value, int timeToLive) {
		if (cache == null) return;
		Element element = new Element(key, value);
		element.setTimeToLive(timeToLive);
		cache.put(element);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.edustar.jitar.service.iface.CacheService#remove(java.lang.String)
	 */
	public void remove(String key) {
		if (cache == null) return;
		cache.remove(key);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.edustar.jitar.service.iface.CacheService#clear()
	 */
	public void clear() {
		if (cache == null) return;
		cache.removeAll();
		//cache.dispose();
	}
	
	/**
	 * 得到底层实际实现的缓存对象.
	 * @return
	 */
	public Cache getUnderlayerCache() {
		return this.cache;
	}
	
	@SuppressWarnings({"unchecked", "rawtypes"})
	public List getAllKeys()
	{
		return this.cache.getKeys();
	}
	
	/**
	 * 重设缓存时间
	 */
	public boolean touch(String key, int newExpireTime){
	    if (cache == null) {
	        return false;
	    }
	    Object data = this.get(key);
	    if(data == null){
	        return false;
	    }
	    cache.remove(key);
	    Element element = new Element(key, data);
        element.setTimeToLive(newExpireTime);
        cache.put(element);
        return true;
	}
	
	public void setExpireTime(int expireTime){
	    this.expireTime = expireTime;
	}
	public int getExpireTime(){
	    return this.expireTime;
	}
}
