package cn.edustar.jitar.service.impl;

import java.util.ArrayList;
import java.util.List;

import cn.edustar.jitar.service.CacheService;

/**
 * 不使用缓存时的无缓存对象实现方法。
 * 
 * @author mxh
 * 
 */
public class NoCache implements CacheService {
    public NoCache() {
    }

    @Override
    public void put(String key, Object value) {
    }
    @Override
    public void put(String key, Object value, int timeToLive) {

    }
    @SuppressWarnings({"rawtypes", "unchecked"})
    @Override
    public List getAllKeys() {
        return java.util.Collections.EMPTY_LIST;
    }

    @Override
    public Object get(String key) {
        return null;
    }

    @Override
    public void remove(String key) {
    }

    @Override
    public void clear() {
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
