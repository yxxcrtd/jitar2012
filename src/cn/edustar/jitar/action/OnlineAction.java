package cn.edustar.jitar.action;

import java.util.Date;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.servlet.http.HttpSession;

import cn.edustar.jitar.manage.OnlineManage;
import cn.edustar.jitar.pojos.UserOnLineStat;
import cn.edustar.jitar.service.CacheService;
import cn.edustar.jitar.service.ConfigService;
import cn.edustar.jitar.user.Constant;
import cn.edustar.jitar.user.UserSession;

/**
 * 首页的在线用户
 */
@SuppressWarnings({"serial", "rawtypes", "unchecked"})
public class OnlineAction extends ManageBaseAction {
	
	// 缓存服务
	private CacheService cacheService;
	
	// 配置服务
	private ConfigService configService;
	
	// 在线管理
	private OnlineManage onlineManage;
	
	// 在线总人数（注册用户 + 游客）
	private int userCount = 0;
	
	// 最高在线人数
	private int highest = 0;

	// 最高在线人数出现的时间
	private String appearTime = "";
	
	// 注册用户列表
	private CopyOnWriteArrayList<Object> userList = new CopyOnWriteArrayList<Object>();
	
	// 游客列表
	private CopyOnWriteArrayList<Object> guestList = new CopyOnWriteArrayList<Object>();
	
	// 是否显示更多用户的参数
	private String ss;

	/* (non-Javadoc)
	 * 
	 * @see cn.edustar.jitar.action.ManageBaseAction#execute(java.lang.String)
	 */
	@Override
	protected String execute(String cmd) throws Exception {
		ss = cmd;
		HttpSession session = request.getSession();
		//为null时如何处理?暂时什么也不做
		if(null == session){
		    return NONE;
		}
		UserSession us = (UserSession) session.getAttribute(Constant.USER_SESSION_KEY);
		
		// session.getServletContext() 是全局的 Application
		CopyOnWriteArrayList onlineList = (CopyOnWriteArrayList) session.getServletContext().getAttribute("onlineList");
		if (null == onlineList) {
			onlineList = new CopyOnWriteArrayList();
			session.getServletContext().setAttribute("onlineList", onlineList);
		} else {
			// 游客session不为空，因为可能是已登录用户再次访问首页
			if (null != us) {
				onlineList.remove(us);
			}
		}
		
		if (null != us) {
			onlineList.add(us);
		}

		// 在归类之前清空游客列表和注册用户列表
		guestList.clear();
		userList.clear();
		for (Object object : onlineList) {
			UserSession u = (UserSession) object;
			if (null != u) {
				if (u.getUsername().startsWith("_")) {
					guestList.add(u);
				} else {
					userList.add(u);
				}
			}
		}
		
		// 在线总人数
		userCount = onlineList.size();
		
		// 最大用户数判断
		UserOnLineStat userOnLineStat = (UserOnLineStat) cacheService.get("online");
		//如果缓存不存在，就从数据库中取数据
		if(null == userOnLineStat){
		    userOnLineStat = onlineManage.getUserOnLineStat();
		    //如果数据库还不存在记录，则需要插入一条记录
		    if(null == userOnLineStat){
		        userOnLineStat = new UserOnLineStat();
		        userOnLineStat.setId(1);
		        userOnLineStat.setAppearTime(String.format("%1$tY-%1$tm-%1$td %1$tH:%1$tM:%1$tS", new Date()));
		        userOnLineStat.setHighest(userCount);
		        onlineManage.saveUserOnLineStat(userOnLineStat);
		    }
            //存储到缓存里面
            cacheService.put("online",userOnLineStat);
		}		
		highest = userOnLineStat.getHighest();
		// 如果当前在线人数大于数据库（此刻是缓存）中的人数，则更新数据库，并更新缓存
		if (userCount > highest) {    
			// 更新数据库
			onlineManage.updateOnLineStat(userCount);
			// 更新缓存
			userOnLineStat = onlineManage.getUserOnLineStat();
			cacheService.put("online",userOnLineStat);
		}			
		
        highest = userOnLineStat.getHighest();
        appearTime = userOnLineStat.getAppearTime();
        request.setAttribute("appearTime", appearTime);
        request.setAttribute("highest", highest);
		return SUCCESS;
	}

	/**
	 * 获取系统配置的超时时间
	 * 
	 * @return
	 */
	private long getTime() {
		return Integer.valueOf(getConfigValue("site.user.online.time")) * 1 * 60 * 1000;
	}

	/**
	 * 根据配置 Name 得到相应的 Value
	 * 
	 * @param string
	 * @return
	 */
	private String getConfigValue(String string) {
		return (String) configService.getConfigure().getValue(string);
	}
	
	public void setCacheService(CacheService cacheService) {
		this.cacheService = cacheService;
	}
	
	public void setConfigService(ConfigService configService) {
		this.configService = configService;
	}

	public void setOnlineManage(OnlineManage onlineManage) {
		this.onlineManage = onlineManage;
	}

	public int getUserCount() {
		return userCount;
	}

	public int getHighest() {
		return highest;
	}

	public String getAppearTime() {
		return appearTime;
	}

	public CopyOnWriteArrayList<Object> getUserList() {
		return userList;
	}

	public void setUserList(CopyOnWriteArrayList<Object> userList) {
		this.userList = userList;
	}

	public CopyOnWriteArrayList<Object> getGuestList() {
		return guestList;
	}

	public void setGuestList(CopyOnWriteArrayList<Object> guestList) {
		this.guestList = guestList;
	}
	
	public String getSs() {
		return ss;
	}
	
	public void setSs(String ss) {
		this.ss = ss;
	}
	
}
