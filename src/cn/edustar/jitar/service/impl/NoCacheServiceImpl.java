package cn.edustar.jitar.service.impl;

import java.util.List;

import cn.edustar.jitar.service.CacheService;

/**
 * 不缓存任何东西的缓存实现.
 * 
 */
public class NoCacheServiceImpl implements CacheService {
    /** 缺省实例. */
    public static final NoCacheServiceImpl INSTANCE = new NoCacheServiceImpl();

    public void clear() {
    }

    public Object get(String key) {
        return null;
    }

    public void put(String key, Object value) {
    }

    public void put(String key, Object value, int timeToLive) {
    }

    public void remove(String key) {
    }

    @SuppressWarnings("unchecked")
    public List getAllKeys() {
        return java.util.Collections.EMPTY_LIST;
    }
    public boolean touch(String key, int newExpireTime) {
        return true;
    }
    public void setExpireTime(int expireTime) {
    }
    public int getExpireTime() {
        return 0;
    }
}
