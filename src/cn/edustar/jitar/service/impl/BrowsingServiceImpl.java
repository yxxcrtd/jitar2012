package cn.edustar.jitar.service.impl;

import java.util.ArrayList;
import java.util.List;

import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPipeline;
import redis.clients.jedis.ShardedJedisPool;
import cn.edustar.jitar.dao.BrowsingDao;
import cn.edustar.jitar.pojos.Browsing;
import cn.edustar.jitar.pojos.User;
import cn.edustar.jitar.service.BrowsingService;
import cn.edustar.jitar.service.UserService;
import cn.edustar.jitar.util.CommonUtil;

public class BrowsingServiceImpl implements BrowsingService {

    private BrowsingDao browsingDao;
    private ShardedJedisPool shardedJedisPool;
    private UserService userService;

    @Override
    public Browsing getBrowsing(int objType, int objId, int userId) {
        return this.browsingDao.getBrowsing(objType, objId, userId);
    }

    @Override
    public void saveBrowsing(Browsing browsing) {
        this.browsingDao.saveBrowsing(browsing);
    }

    /** 添加或者更新一个访问记录 */
    public void saveOrUpdateBrowsing(Browsing browsing) {
        this.browsingDao.saveOrUpdateBrowsing(browsing);
    }

    @Override
    public void deleteBrowsing(Browsing browsing) {
        this.browsingDao.deleteBrowsing(browsing);
    }

    @Override
    public List<Browsing> getBrowsingList(int objType, int objId) {
        return this.browsingDao.getBrowsingList(objType, objId);
    }

    @Override
    public List<Browsing> getBrowsingTopList(int topNumber, int objType, int objId) {
        return this.browsingDao.getBrowsingTopList(topNumber, objType, objId);
    }

    public void setBrowsingDao(BrowsingDao browsingDao) {
        this.browsingDao = browsingDao;
    }

    /**
     * 兼容数据库的实现方式。
     */
    @Override
    public void cacheData(String cacheKey, int userId) {
        if (shardedJedisPool == null) {
            // 数据库实现方式
            String[] obj = cacheKey.split("_");
            String objType = obj[0];
            String objId = obj[1];
            if (!CommonUtil.isInteger(objType) || !CommonUtil.isInteger(objId)) {
                System.out.println("objType或者objId不是整数，请通知软件开发商修改代码。");
                return;
            }
            // 为了实现去重，先进行判断是否已经存在记录，有的话，就先删除，因为最后访问的要显示到最前面。
            Browsing browsing = this.getBrowsing(Integer.valueOf(objType).intValue(), Integer.valueOf(objId).intValue(), userId);
            if (null != browsing) {
                this.deleteBrowsing(browsing);
            }
            browsing = new Browsing(Integer.valueOf(objType).intValue(), Integer.valueOf(objId).intValue(), userId);
            this.saveOrUpdateBrowsing(browsing);
        } else {
            // Redis实现方式
            ShardedJedis jedis = shardedJedisPool.getResource();
            ShardedJedisPipeline pipeLine = jedis.pipelined();
            String bu = "|" + userId;
            String history_user_id = jedis.get(cacheKey);
            if (null == history_user_id) {
                history_user_id = bu + "|";
            } else {
                if (history_user_id.contains(bu + "|")) {
                    history_user_id = history_user_id.replace(bu + "|", "|");
                }
                history_user_id = bu + history_user_id;
                history_user_id = CommonUtil.getLeftCountedString(history_user_id, 16); // 页面显示16人
            }
            jedis.set(cacheKey, history_user_id);
            pipeLine.sync();
            shardedJedisPool.returnResource(jedis);
        }
    }

    @Override
    public List<User> getCachedData(String cacheKey) {
        List<User> browsingUser = new ArrayList<User>();
        if (shardedJedisPool == null) {
            // 数据库实现方式
            String[] obj = cacheKey.split("_");
            String objType = obj[0];
            String objId = obj[1];
            if (!CommonUtil.isInteger(objType) || !CommonUtil.isInteger(objId)) {
                System.out.println("objType或者objId不是整数，请通知软件开发商修改代码。");
                return browsingUser;
            }
            List<Browsing> browsingHistory = this.getBrowsingList(Integer.valueOf(objType).intValue(), Integer.valueOf(objId).intValue());
            if (browsingHistory.size() > 16) {
                int browsingCount = browsingHistory.size();
                Browsing b = null;
                for (int index = 16; index < browsingCount; index++) {
                    b = browsingHistory.get(index);
                    if (b != null) {
                        this.deleteBrowsing(b);
                    }
                }
            }

            for (Browsing b : browsingHistory) {
                browsingUser.add(this.userService.getUserById(b.getUserId(), true));
            }
        } else {
            // Redis 实现方式
            ShardedJedis jedis = shardedJedisPool.getResource();
            String redis_userid = jedis.get(cacheKey);
            String[] userIds = null != redis_userid ? redis_userid.split("\\|") : new String[]{};

            for (String userid : userIds) {
                if (!"".equals(userid.trim())) {
                    browsingUser.add(userService.getUserById(Integer.valueOf(userid.trim()), true));
                }
            }
            shardedJedisPool.returnResource(jedis);
        }
        return browsingUser;
    }

    public void setShardedJedisPool(ShardedJedisPool shardedJedisPool) {
        this.shardedJedisPool = shardedJedisPool;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

}
