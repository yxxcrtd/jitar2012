package cn.edustar.jitar.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import redis.clients.jedis.ShardedJedisPool;

import cn.edustar.jitar.pojos.Browsing;
import cn.edustar.jitar.pojos.User;

public interface BrowsingService {
    
    /** 得到某类型对象的一个访问记录 */
    public Browsing getBrowsing(int objType, int objId, int userId);
    
    /** 添加一个访问记录 */
    public void saveBrowsing(Browsing browsing);
    
    /** 删除一个访问记录 */
    public void deleteBrowsing(Browsing browsing);
    
    /** 添加或者更新一个访问记录 */
    public void saveOrUpdateBrowsing(Browsing browsing);
    
    /** 得到某对象的所有访问记录列表 */
    public List<Browsing> getBrowsingList(int objType, int objId);
    
    /** 得到最新的访问记录列表 */
    public List<Browsing> getBrowsingTopList(int topNumber, int objType, int objId);
    /**存入历史到redis*/
    /**
     * @param cacheKey key
     * @param shardedJedisPool redis池对象
     * @param UserId 用户Id
     */
	public void cacheData(String cacheKey,int UserId);

	/**
	 * 
	 * @param string cacheKey
	 * @param shardedJedisPool 连接池
	 * @param request httpRequest
	 * @param userService 用户服务
	 */
	public List<User> getCachedData(String string);
}
