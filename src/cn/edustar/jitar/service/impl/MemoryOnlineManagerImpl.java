package cn.edustar.jitar.service.impl;

import java.util.Date;
import java.util.Timer;
import java.util.Iterator;
import java.util.TimerTask;

import cn.edustar.jitar.pojos.Config;
import cn.edustar.jitar.service.ConfigService;
import cn.edustar.jitar.service.OnlineManager;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import cn.edustar.jitar.service.UserOnlineInfo;
import cn.edustar.jitar.util.CommonUtil;

/**
 * 使用内存来实现在线状态服务的实现
 * 
 *
 */
public class MemoryOnlineManagerImpl implements OnlineManager {
	/** 在线用户活动信息记录 */
	private ConcurrentHashMap<String, UserOnlineInfoImpl> online_map = new ConcurrentHashMap<String, UserOnlineInfoImpl>();

	/** 多少时间内活动算作是在线, 单位: 秒; 缺省设置为 10 分钟 */
	private int activeSeconds = 10 * 60;

	/** 定期清理过期项目的定时器 */
	private Timer cleanupTimer;

	/** 第一次启动定时器的时间 */
	private long timerDelay = 5 * 60 * 1000;

	/** 定时器多长时间运转一次 */
	private long timerPeriod = 15 * 60 * 1000;

	/**
	 * 初始化方法 (一般从 Spring 调用)
	 */
	public void init() {
		
		this.cleanupTimer = new java.util.Timer();
		cleanupTimer.schedule(new CleanExpiredTask(), timerDelay, timerPeriod);
	}

	/**
	 * 销毁方法
	 */
	public void destroy() {
		if (cleanupTimer != null) {
			try {
				cleanupTimer.cancel();
			} catch (Exception ignored) {

			}
			cleanupTimer = null;
		}
	}

	/** 多少时间内活动算作是在线, 单位: 秒; 缺省设置为 10 分钟 */
	public void setActiveSeconds(int activeSeconds) {
		this.activeSeconds = activeSeconds;
	}

	/** 第一次启动定时器的时间 */
	public void setTimerDelay(long timerDelay) {
		this.timerDelay = timerDelay;
	}

	/** 定时器多长时间运转一次 */
	public void setTimerPeriod(long timerPeriod) {
		this.timerPeriod = timerPeriod;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.edustar.jitar.service.OnlineManager#isOnline(java.lang.String)
	 */
	public boolean isOnline(String loginName) {
		checkLoginName(loginName);
		UserOnlineInfoImpl online = online_map.get(loginName);
		return online == null ? false : isOnline(online.lastActive);
	}

	
	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.edustar.jitar.service.OnlineManager#userActive(java.lang.String,
	 * java.util.Date, java.lang.String)
	 */
	public void userActive(String loginName, Date activeTime, String action) {
		checkLoginName(loginName);
		online_map.put(loginName, new UserOnlineInfoImpl(loginName, activeTime,
				action));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cn.edustar.jitar.service.OnlineManager#getActiveInfo(java.lang.String)
	 */
	public UserOnlineInfo getActiveInfo(String loginName) {
		checkLoginName(loginName);
		UserOnlineInfoImpl online = online_map.get(loginName);
		if (online == null)
			return null;
		if (isOnline(online.lastActive))
			return online;
		online_map.remove(online.loginName, online);

		return null;
	}

	/**
	 * 过期清理
	 */
	private final class CleanExpiredTask extends TimerTask {
		public void run() {
			cleanExpired();
		}
	}

	/**
	 * 内部调用: 定期清理过期项目
	 */
	private final void cleanExpired() {
		int clean_num = 0;
		for (Iterator<UserOnlineInfoImpl> iter = online_map.values().iterator(); iter
				.hasNext();) {
			UserOnlineInfoImpl online = iter.next();
			if (online == null)
				continue;
			if (isOnline(online.lastActive) == false) {
				if (online_map.remove(online.loginName, online))
					++clean_num;
			}
		}
	}

	/**
	 * 指定时间内活动的是否算在线
	 */
	private boolean isOnline(Date lastActive) {
		long now = new Date().getTime();
		long period = now - lastActive.getTime();
		return period < activeSeconds * 1000;
	}

	// 检查 loginName 参数
	private void checkLoginName(String loginName) {
		if (loginName == null)
			throw new IllegalArgumentException("loginName == null");
	}

	/**
	 * UserOnlineInfo 的简单实现
	 */
	private static final class UserOnlineInfoImpl implements UserOnlineInfo {
		private final String loginName;
		private Date lastActive;
		private String lastAction;

		public UserOnlineInfoImpl(String loginName, Date activeTime,
				String action) {
			this.loginName = loginName;
			this.lastActive = activeTime;
			this.lastAction = action;
		}

		public String getLoginName() {
			return this.loginName;
		}

		public Date getLastActiveTime() {
			return lastActive;
		}

		public String getLastAction() {
			return this.lastAction;
		}

		@SuppressWarnings("unused")
		public void setLastActiveTimeAndAction(Date active, String action) {
			this.lastActive = active;
			this.lastAction = action;
		}
	}

}
