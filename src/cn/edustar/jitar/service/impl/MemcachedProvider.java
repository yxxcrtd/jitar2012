package cn.edustar.jitar.service.impl;

import java.io.IOException;

import net.rubyeye.xmemcached.XMemcachedClient;
import cn.edustar.jitar.service.CacheProvider;
import cn.edustar.jitar.service.CacheService;

/**
 * 编写 MemcachedProvider 这个类，是为了与以前的 EHCacheProvider 提供相同的方法，实现在选择不同的缓存时不用修改任何代码。
 * @author mxh
 *
 */
public class MemcachedProvider implements CacheProvider {
	private XMemcachedClient memcachedClient;

	public void setMemcachedClient(XMemcachedClient memcachedClient) {
		this.memcachedClient = memcachedClient;
	}
	
	public XMemcachedClient getMemcachedClient() {
		return this.memcachedClient;
	}

	@Override
	public CacheService getCache(String cacheName) {
		return new MemcachedServiceImpl(this.memcachedClient, cacheName);
	}

	public void init() {

	}

	public void destroy() {
		try {
			this.memcachedClient.shutdown();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
