package cn.edustar.jitar.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.type.StandardBasicTypes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import cn.edustar.jitar.dao.hibernate.BaseDaoHibernate;
import cn.edustar.jitar.pojos.AccessControl;
import cn.edustar.jitar.pojos.Article;
import cn.edustar.jitar.pojos.Comment;
import cn.edustar.jitar.pojos.Group;
import cn.edustar.jitar.pojos.Photo;
import cn.edustar.jitar.pojos.Resource;
import cn.edustar.jitar.pojos.SiteStat;
import cn.edustar.jitar.pojos.Subject;
import cn.edustar.jitar.pojos.Topic;
import cn.edustar.jitar.pojos.User;
import cn.edustar.jitar.pojos.UserOnLineStat;
import cn.edustar.jitar.service.CacheService;
import cn.edustar.jitar.service.UnitService;
import cn.edustar.jitar.service.GroupService;
import cn.edustar.jitar.service.StatService;
import cn.edustar.jitar.service.SubjectService;
import cn.edustar.jitar.util.ParamUtil;

/**
 * 统计服务的接口实现
 *
 * @author Yang Xinxin
 * @version 1.0.0 May 4, 2009 3:32:58 PM
 */
@SuppressWarnings({ "static-access",  "unchecked" })
public class StatServiceImpl extends BaseDaoHibernate implements StatService {

	private Logger log = LoggerFactory.getLogger(StatServiceImpl.class);
	/** 学科服务 */
	private SubjectService subj_svc;

	/** 用户访问次数统计集合, Key = userId, Value = visitCount(相对增减值) */
	private Map<Integer, Integer> user_stat = new HashMap<Integer, Integer>();

	/** 写入间隔, 单位: 毫秒, 缺省 = 60000 (1 分钟) */
	private long sleepTime = 60000;

	/** 每多少次 sleepTime 进行一次自动统计 */
	private int tickForStat = 30;

	/** 文章数是否发生了变化, 初始值 = true 表示启动运行的时候总会进行一次重新统计. */
	private boolean countChanged = true;

	/** 写入间隔, 单位: 毫秒, 缺省 = 60000 (1 分钟) */
	public void setSleepTime(long sleepTime) {
		this.sleepTime = sleepTime;
	}

	/** 当前站点统计信息 */
	private SiteStat site_stat = new SiteStat();

	/** 尚未更新的站点统计信息的 delta 值, delta 意思是在 site_stat 基础上增加或减少的值 */
	private SiteStat site_stat_delta = new SiteStat();

	/** 学科服务的set方法 */
	public void setSubjectService(SubjectService subj_svc) {
		this.subj_svc = subj_svc;
	}
	
	/** 群组服务 */
	private GroupService groupService;

	/** 群组服务的set方法 */
	public void setGroupService(GroupService groupService) {
		this.groupService = groupService;
	}

	/** 区县、机构服务 */
	private UnitService unitService;
	
	public void setUnitService(UnitService unitService) {
		this.unitService = unitService;
	}
	
	/** 缓存服务 */
	private CacheService cacheService;
	
	public void setCacheService(CacheService cacheService) {
		this.cacheService = cacheService;
	}

	/**
	 * 初始化方法，从 Spring 调用
	 */
	public void init() {		
	    //此方法已废弃
		log.info("StatLazyWriterImpl 初始化...");
		loadSiteStat();
		this.thread = new LazyWriteThread();
		this.thread.start();
	}

	/**
	 * 从数据库中加载站点统计信息
	 */
	private void loadSiteStat() {
		List<SiteStat> all = this.getSession().createQuery("FROM SiteStat").list();
		if (all == null || all.size() == 0) {
			// 如果没有则现在插入一个缺省的
			SiteStat new_ss = new SiteStat();
			new_ss.setId(1);
			this.getSession().save(new_ss);			
			loadSiteStat(); // 重新加载
			return;
		}
		this.site_stat = all.get(0);
	}

	public void updateSiteStat(SiteStat siteStat)
	{
		this.getSession().update(siteStat);
	}
	
	/**
	 * 清理方法，从 Spring 调用
	 */
	@SuppressWarnings("deprecation")
	public void destroy() {
		// 停止当前线程
		if (this.thread != null) {
			log.info("Stopping LazyWriter thread...");
			this.stopFlag = true;
			this.thread.interrupt();
			try {
				this.thread.join(5000);
			} catch (InterruptedException e) {
				this.thread.destroy();
			}
			this.thread = null;
		}
		// 写入最后尚未写入的统计数
		writeAllStat();
		log.info("StatLazyWriterImpl destroyed.");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.edustar.jitar.service.StatLazyWriter#getSiteStat()
	 */
	public SiteStat getSiteStat() {
		return this.site_stat.clone();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.edustar.jitar.service.StatLazyWriter#incUserVisitCount(cn.edustar.jitar.pojos.User)
	 */
	public void incUserVisitCount(User user) {
		if (user == null) {
			return;
		}
		Integer userId = user.getUserId();

		synchronized (this.user_stat) {
			if (user_stat.containsKey(userId)) {
				Integer visitCount = user_stat.get(userId) + 1;
				user_stat.put(userId, visitCount);
			} else {
				user_stat.put(userId, 1);
			}
		}
		user.setVisitCount(user.getVisitCount() + 1);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.edustar.jitar.service.StatLazyWriter#incGroupVisitCount(cn.edustar.jitar.pojos.Group)
	 */
	public void incGroupVisitCount(Group group) {
		String hql = "UPDATE Group SET visitCount = visitCount + 1 WHERE groupId = " + group.getGroupId();
		this.getSession().createQuery(hql).executeUpdate();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.edustar.jitar.service.StatService#incResourceVisitCount(cn.edustar.jitar.pojos.Resource)
	 */
	public void incResourceVisitCount(Resource resource) {
		String hql = "UPDATE Resource SET viewCount = viewCount + 1 WHERE resourceId = "+resource.getResourceId();
		this.getSession().createQuery(hql).executeUpdate();
		resource.setViewCount(resource.getViewCount() + 1);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.edustar.jitar.service.StatService#incResourceDownloadCount(cn.edustar.jitar.pojos.Resource)
	 */
	public void incResourceDownloadCount(Resource resource) {
		String hql = "UPDATE Resource SET downloadCount = downloadCount + 1 WHERE resourceId = " + resource.getResourceId();
		this.getSession().createQuery(hql).executeUpdate();
		resource.setDownloadCount(resource.getDownloadCount() + 1);
	}

	/* (non-Javadoc)
	 *
	 * @see cn.edustar.jitar.service.StatService#incResourceCommentCount(cn.edustar.jitar.pojos.Resource)
	 */
	public void incResourceCommentCount(Resource resource) {
		String hql = "UPDATE Resource SET commentCount = commentCount + 1 WHERE resourceId = ?";
		this.getSession().createQuery(hql).setInteger(0, resource.getResourceId()).executeUpdate();
		//getSession().bulkUpdate(hql, resource.getResourceId());
		resource.setDownloadCount(resource.getCommentCount() + 1);
	}
	
	/** 延迟写入线程 */
	private LazyWriteThread thread;

	/** 是否停止 */
	private boolean stopFlag = false;
	
	/**
	 * 实际执行延迟写入操作
	 */
	private void runThread() {
		Session session = null;
		try {
			WebApplicationContext wac = ContextLoader.getCurrentWebApplicationContext();
			SessionFactory sf = wac.getBean("sessionFactory", SessionFactory.class);
			session = sf.openSession();
			while (stopFlag == false) {
				writeSiteStat(session);
				if (stopFlag)
					break;

				writeUserStat(session);
				if (stopFlag)
					break;

				if (countChanged) {
					++tickForStat;
					if (tickForStat >= 30) {
						tickForStat = 0;
						autoStat(session);
					}
				}
				Thread.sleep(this.sleepTime);
			}
		} catch (InterruptedException ie) {
			return;
		} catch (Exception e) {
			log.error(e.getLocalizedMessage());
		} finally {
			if(session != null) session.close();
			log.info("LazyWriter thread is stopped.");
		}
	}
	
	/**
	 * 写入所有，在 destroy() 的时候调用
	 */
	private void writeAllStat() {
		writeSiteStat();
		writeUserStat();
	}

	/**
	 * 整站统计
	 */
	private void writeSiteStat(Session... sessions) {		
		try {
			// 复制 site stat delta, 清空原来的 delta
			SiteStat delta;
			synchronized (this) {
				delta = this.site_stat_delta;
				this.site_stat_delta = new SiteStat();
			}
			
			// 如果没有发生变化则返回
			if (siteStatChanged(delta) == false)
				return;
			
			// 进行更新
			String hql = "UPDATE SiteStat SET " +
				" visitCount = visitCount + " + delta.getVisitCount() +
				", userCount = userCount + " + delta.getUserCount() +
				", groupCount = groupCount + " + delta.getGroupCount() +
				", articleCount = articleCount + " + delta.getArticleCount() +
				", resourceCount = resourceCount + " + delta.getResourceCount() +
				", topicCount = topicCount + " + delta.getTopicCount() +
				", commentCount = commentCount + " + delta.getCommentCount() +
				", photoCount = photoCount + " + delta.getPhotoCount();
			log.info("整站统计：" + hql);
			if(sessions != null || sessions.length > 0)
			{
				sessions[0].createQuery(hql).executeUpdate();
			}
			else
			{
				this.getSession().createQuery(hql).executeUpdate();
			}
			
			// 重新加载 SiteStat
			this.loadSiteStat();
		} catch (DataAccessException dae) {
			log.error(dae.getLocalizedMessage());
		}
	}
	
	/**
	 * 判断给定的 delta 是否发生了改变
	 * 
	 * @param delta
	 * @return
	 */
	private boolean siteStatChanged(SiteStat delta) {
		if (delta.getArticleCount() != 0)
			return true;
		
		if (delta.getGroupCount() != 0)
			return true;
		
		if (delta.getPhotoCount() != 0)
			return true;
		
		if (delta.getResourceCount() != 0)
			return true;
		
		if (delta.getUserCount() != 0)
			return true;
		
		if (delta.getVisitCount() != 0)
			return true;
		
		return false;
	}

	/**
	 * 写入用户访问统计数据
	 */
	private void writeUserStat(Session...sessions) {
		if (user_stat.size() == 0) return;
		
		try {
			// 遍历 old_user_stat 写入统计数据
			WusHibernateCallback(sessions);
		} catch (DataAccessException ex) {
			log.error(ex.getLocalizedMessage());
		}
	}
	
	/**
	 * 批量写入 visitCount 
	 */
	private void WusHibernateCallback(Session...sessions) {
			// 把统计数据拿出来, 用新的一个空的 HashMap 替代原来的统计数据. 
			Map<Integer, Integer> old_user_stat;
			synchronized (user_stat) {
				if (user_stat.size() == 0) return;
				old_user_stat = user_stat;
				user_stat = new HashMap<Integer, Integer>();
			}
			log.debug("Will update number of " + old_user_stat.size() + " user visitCount.");
			
			// 创建更新 query, 批量执行以提高效率
			Query query = null;
			if(sessions!=null && sessions.length > 0)
			{
				query = sessions[0].createQuery("UPDATE User SET visitCount = visitCount + ?, articleCount = myArticleCount + otherArticleCount WHERE userId = ?");
			}
			else{
				
				query = this.getSession().createQuery("UPDATE User SET visitCount = visitCount + ?, articleCount = myArticleCount + otherArticleCount WHERE userId = ?");
			}
			
			for (Map.Entry<Integer, Integer> entry : old_user_stat.entrySet()) {
				query.setParameter(0, entry.getValue(), StandardBasicTypes.INTEGER);
				query.setParameter(1, entry.getKey(), StandardBasicTypes.INTEGER);
				query.executeUpdate();
			}			

		}
		

	
	/**
	 * 延迟写入线程
	 */
	private final class LazyWriteThread extends Thread {
		public LazyWriteThread() {
			super.setName("StatLazyWriter");
		}
		
		public void run() {
			try {
				sleep(2000);
			} catch (InterruptedException e) {
				return;
			}
			runThread();
		}
	}

	private void autoStat(Session...sessions) throws ParseException {
		restatSite(sessions);
		subj_svc.subjectAutoStat();
	}

	private void restatSite(Session...sessions) throws ParseException {	
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		GregorianCalendar cc = new GregorianCalendar(); 
		Calendar c = Calendar.getInstance();
		int year = c.get(Calendar.YEAR);
		int month = c.get(Calendar.MONTH) + 1;
		int day = c.get(Calendar.DAY_OF_MONTH);
		String date = String.valueOf(year) + "-" + String.valueOf(month) + "-" + String.valueOf(day);
		int lastDay = cc.getActualMaximum(c.DAY_OF_MONTH);
		int beforeDay = day - 1;
		String beforeDate = "";
		if (beforeDay == 0) {
			c.set(Calendar.DATE, 1);
			c.add(Calendar.DATE, -1);
			beforeDate = sdf.format(c.getTime());
		} else {
			beforeDate = String.valueOf(year) + "-" + String.valueOf(month) + "-" + String.valueOf(beforeDay);
		}
		int afterDay = 0;
		if (lastDay == day) {
			month = month + 1;
			if (month == 13) {
				month = 1;
				year = year + 1;
			}
			afterDay = 1;
		} else {
			afterDay = day + 1;
		}
		String afterDate = String.valueOf(year) + "-" + String.valueOf(month) + "-" + String.valueOf(afterDay);
		int currentHour = c.get(Calendar.HOUR_OF_DAY);
		int currentMinute = c.get(Calendar.MINUTE);
		String beginDate = new StringBuffer(String.valueOf(year)).append("-01-01").toString();
		String endDate = new StringBuffer(String.valueOf(year + 3)).append("-12-31").toString();
		if (1 == currentHour && 30 < currentMinute) {
			int userCount = executeIntStat("SELECT COUNT(*) FROM User WHERE (userStatus = 0)",sessions);
			int groupCount = executeIntStat("SELECT COUNT(*) FROM Group g WHERE g.groupState = 0");
			int articleCount = executeIntStat("SELECT COUNT(*) FROM Article a WHERE a.auditState = 0 AND a.delState = false  AND a.draftState = false",sessions);
			int resourceCount = executeIntStat("SELECT COUNT(*) FROM Resource r WHERE r.auditState = 0 AND r.delState = false",sessions);
			int topicCount = executeIntStat("SELECT COUNT(*) FROM Topic",sessions);
			int photoCount = executeIntStat("SELECT COUNT(*) FROM Photo",sessions);
			int commentCount = executeIntStat("SELECT COUNT(*) FROM Comment",sessions);
			int todayArticleCount = executeIntStat("SELECT COUNT(*) FROM Article a WHERE a.auditState = 0 AND a.delState = false AND a.draftState = false AND a.createDate BETWEEN '" + date + "' AND '" + afterDate + "'",sessions);
			int beforeArticleCount = executeIntStat("SELECT COUNT(*) FROM Article a WHERE a.auditState = 0 AND a.delState = false AND a.draftState = false AND a.createDate BETWEEN '" + beforeDate + "' AND '" + date + "'",sessions);
			int todayResourceCount = executeIntStat("SELECT COUNT(*) FROM Resource r WHERE r.auditState = 0 AND r.delState = false AND r.createDate BETWEEN '" + date + "' AND '" + afterDate + "'",sessions);
			int beforeResourceCount = executeIntStat("SELECT COUNT(*) FROM Resource r WHERE r.auditState = 0 AND r.delState = false AND r.createDate BETWEEN '" + beforeDate + "' AND '" + date + "'",sessions);
			String update_hql = "UPDATE SiteStat SET userCount = " + userCount +
				", groupCount = " + groupCount +
				", articleCount = " + articleCount +
				", resourceCount = " + resourceCount +
				", topicCount = " + topicCount +
				", photoCount = " + photoCount +
				", todayArticleCount = " + todayArticleCount +
				", yesterdayArticleCount = " + beforeArticleCount +
				", todayResourceCount = " + todayResourceCount +
				", yesterdayResourceCount = " + beforeResourceCount +
				", commentCount = " + commentCount;
			if(sessions !=null && sessions.length > 0)
			{
				sessions[0].createQuery(update_hql).executeUpdate();
				update_hql = "UPDATE User Set articleCount = myArticleCount + otherArticleCount ";
				sessions[0].createQuery(update_hql).executeUpdate();
			}
			else
			{
				this.getSession().createQuery(update_hql).executeUpdate();
				update_hql = "UPDATE User Set articleCount = myArticleCount + otherArticleCount ";
				this.getSession().createQuery(update_hql).executeUpdate();
			}
			
		} else {
			int beforeArticleCount = executeIntStat("SELECT COUNT(*) FROM Article a WHERE a.auditState = 0 AND a.delState = false AND a.draftState = false AND a.createDate BETWEEN '" + beforeDate + "' AND '" + date + "'",sessions);
			int beforeResourceCount = executeIntStat("SELECT COUNT(*) FROM Resource r WHERE r.auditState = 0 AND r.delState = false AND r.createDate BETWEEN '" + beforeDate + "' AND '" + date + "'",sessions);
			int todayArticleCount = executeIntStat("SELECT COUNT(*) FROM Article a WHERE a.auditState = 0 AND a.delState = false AND a.draftState = false AND a.createDate BETWEEN '" + date + "' AND '" + afterDate + "'",sessions);
			int todayResourceCount = executeIntStat("SELECT COUNT(*) FROM Resource r WHERE r.auditState = 0 AND r.delState = false AND r.createDate BETWEEN '" + date + "' AND '" + afterDate + "'",sessions);
			int commentCount = executeIntStat("SELECT COUNT(*) FROM Comment",sessions);
			String update_hql = "UPDATE SiteStat SET " +
										"todayArticleCount = " + todayArticleCount + 
										", todayResourceCount = " + todayResourceCount +
										", yesterdayArticleCount = " + beforeArticleCount +
										", yesterdayResourceCount = " + beforeResourceCount +
										", commentCount = " + commentCount;
			if(sessions != null && sessions.length>0)
			{
				sessions[0].createQuery(update_hql).executeUpdate();
			}
			else
			{
				this.getSession().createQuery(update_hql).executeUpdate();
			}
		}
		
		// 在系统启动的时候将在线统计对象放入缓存
		cacheService.put("online", getUserOnLineStat());
		
		// 将获取权限表放入缓存
		// cacheService.put("accesscontrol", getAccessControl());
	}

	/* (non-Javadoc)
	 * 
	 * @see cn.edustar.jitar.service.StatService#getUserOnLineStat()
	 */
	public UserOnLineStat getUserOnLineStat() {
		List<UserOnLineStat> list = this.getSession().createQuery("FROM UserOnLineStat").list();
		return (null == list || list.isEmpty()) ? new UserOnLineStat() : ((UserOnLineStat) list.get(0));
	}
	
	/**
	 * 获取权限列表
	 * 
	 * @return
	 */
	private ArrayList<AccessControl> getAccessControl() {
		ArrayList<AccessControl> list = (ArrayList<AccessControl>) this.getSession().createQuery("FROM AccessControl").list();
		return list;
	}

	/* (non-Javadoc)
	 * 
	 * @see cn.edustar.jitar.service.StatService#subjectStat(int, int, int)
	 */
	public void subjectStat(int subjectId, int beginGradeId, int endGradeId) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		GregorianCalendar cc = new GregorianCalendar(); 
		Calendar c = Calendar.getInstance();
		int year = c.get(Calendar.YEAR);
		int month = c.get(Calendar.MONTH) + 1;
		int day = c.get(Calendar.DAY_OF_MONTH);
		String date = String.valueOf(year) + "-" + String.valueOf(month) + "-" + String.valueOf(day);
		int lastDay = cc.getActualMaximum(c.DAY_OF_MONTH);
		int beforeDay = day - 1;
		String beforeDate = "";
		if (beforeDay == 0) {
			c.set(Calendar.DATE, 1);
			c.add(Calendar.DATE, -1);
			beforeDate = sdf.format(c.getTime());
		} else {
			beforeDate = String.valueOf(year) + "-" + String.valueOf(month) + "-" + String.valueOf(beforeDay);
		}
		int afterDay = 0;
		if (lastDay == day) {
			month = month + 1;
			if (month == 13) {
				month = 1;
				year = year + 1;
			}
			afterDay = 1;
		} else {
			afterDay = day + 1;
		}
		String afterDate = String.valueOf(year) + "-" + String.valueOf(month) + "-" + String.valueOf(afterDay);
		if (subjectId ==0 || beginGradeId == 0 || endGradeId == 0)
			return;
		int currentHour = c.get(Calendar.HOUR_OF_DAY);
		int currentMinute = c.get(Calendar.MINUTE);
		if (currentHour == 1 && currentMinute > 45) {
			int userCount = executeIntStat("SELECT COUNT(*) FROM User u where u.subjectId = '" + subjectId + "' AND u.gradeId BETWEEN '" + beginGradeId + "' AND " + endGradeId);
			int groupCount = executeIntStat("SELECT COUNT(*) FROM Group g where g.subjectId = '" + subjectId + "' AND g.gradeId BETWEEN '" + beginGradeId + "' AND " + endGradeId);
			int articleCount = executeIntStat("SELECT COUNT(*) FROM Article a WHERE a.auditState = 0 AND a.delState = false AND a.draftState = false AND a.subjectId = '" + subjectId + "' AND a.gradeId BETWEEN '" + beginGradeId + "' AND " + endGradeId);
			int resourceCount = executeIntStat("SELECT COUNT(*) FROM Resource r WHERE r.auditState = 0 AND r.delState = false AND r.subjectId = '" + subjectId + "' AND r.gradeId BETWEEN '" + beginGradeId + "' AND " + endGradeId);
			int todayArticleCount = executeIntStat("SELECT COUNT(*) FROM Article a " +
				" WHERE a.auditState = 0 AND a.delState = false AND " +
				" a.draftState = false AND a.createDate BETWEEN '" + date + "' AND '" + afterDate + "' " +
				" AND a.subjectId = " + subjectId + " AND a.gradeId BETWEEN " + beginGradeId + " AND " + endGradeId);
			int beforeArticleCount = executeIntStat("SELECT COUNT(*) FROM Article a " +
				" WHERE a.auditState = 0 AND a.delState = false AND " +
				" a.draftState = false AND a.createDate BETWEEN '" + beforeDate + "' AND '" + date + "' " +
				" AND a.subjectId = " + subjectId + " AND a.gradeId BETWEEN " + beginGradeId + " AND " + endGradeId);
			int todayResourceCount = executeIntStat("SELECT COUNT(*) FROM Resource r " +
				" WHERE r.auditState = 0 AND r.delState = false AND r.createDate BETWEEN '" + date + "' AND '" + afterDate + "' " +
				" AND r.subjectId = " + subjectId + " AND r.gradeId BETWEEN '" + beginGradeId + "' AND " + endGradeId);
			int beforeResourceCount = executeIntStat("SELECT COUNT(*) FROM Resource r " +
				" WHERE r.auditState = 0 AND r.delState = false AND r.createDate BETWEEN '" + beforeDate + "' AND '" + date + "' " +
				" AND r.subjectId = " + subjectId + " AND r.gradeId BETWEEN '" + beginGradeId + "' AND " + endGradeId);	
			String update_hql = "UPDATE Subject SET " +
				"userCount = " + userCount +
				", groupCount = " + groupCount +
				", articleCount = " + articleCount +
				", resourceCount = " + resourceCount +
				", todayArticleCount = " + todayArticleCount +
				", yesterdayArticleCount = " + beforeArticleCount +
				", todayResourceCount = " + todayResourceCount +
				", yesterdayResourceCount = " + beforeResourceCount +
				" WHERE metaSubjectId = " + subjectId +
				" AND metaGradeId BETWEEN " + beginGradeId + " AND " + endGradeId;
			this.getSession().createQuery(update_hql).executeUpdate();
		} else {
			int todayArticleCount = executeIntStat("SELECT COUNT(*) FROM Article a " +
				" WHERE a.auditState = 0 AND a.delState = false AND " +
				" a.draftState = false AND a.createDate BETWEEN '" + date + "' AND '" + afterDate + "' " +
				" AND a.subjectId = " + subjectId + " AND a.gradeId BETWEEN '" + beginGradeId + "' AND " + endGradeId);
			int todayResourceCount = executeIntStat("SELECT COUNT(*) FROM Resource r " +
				" WHERE r.auditState = 0 AND r.delState = false AND r.createDate BETWEEN '" + date + "' AND '" + afterDate + "' " +
				" AND r.subjectId = " + subjectId + " AND r.gradeId BETWEEN '" + beginGradeId + "' AND " + endGradeId);
			int beforeArticleCount = executeIntStat("SELECT COUNT(*) FROM Article a " +
				" WHERE a.auditState = 0 AND a.delState = false AND " +
				" a.draftState = false AND a.createDate BETWEEN '" + beforeDate + "' AND '" + date + "' " +
				" AND a.subjectId = " + subjectId + " AND a.gradeId BETWEEN " + beginGradeId + " AND " + endGradeId);
			int beforeResourceCount = executeIntStat("SELECT COUNT(*) FROM Resource r " +
				" WHERE r.auditState = 0 AND r.delState = false AND r.createDate BETWEEN '" + beforeDate + "' AND '" + date + "' " +
				" AND r.subjectId = " + subjectId + " AND r.gradeId BETWEEN '" + beginGradeId + "' AND " + endGradeId);	
			String update_hql = "UPDATE Subject SET todayArticleCount = " + todayArticleCount +
			", todayResourceCount = " + todayResourceCount +
			", yesterdayArticleCount = " + beforeArticleCount +
			", yesterdayResourceCount = " + beforeResourceCount +
			" WHERE metaSubjectId = " + subjectId +
			" AND metaGradeId BETWEEN " + beginGradeId + " AND " + endGradeId;
			this.getSession().createQuery(update_hql).executeUpdate();
		}
	}
	
	/**
	 * 执行统计
	 *
	 * @param hql
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	private int executeIntStat(String hql, Session...sessions) {
		List result;
		if(sessions!=null && sessions.length > 0)
		{
			result = sessions[0].createQuery(hql).list();
		}
		else
		{
			result = this.getSession().createQuery(hql).list();
		}
		if (result == null || result.size() == 0)
			return 0;
		Integer i = ParamUtil.safeParseIntegerWithNull(result.get(0));
		return i == null ? 0 : i.intValue();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.edustar.jitar.service.StatService#incUserCount(cn.edustar.jitar.pojos.User, int)
	 */
	public void incUserCount(User user, int count) {
		// 用户对象不能为空
		if (user == null || count == 0)
			return;
		
		this.countChanged = true;
		
		// 1. 增加站点级计数
		synchronized (this) {
			this.site_stat.setUserCount(site_stat.getUserCount() + count);
			this.site_stat_delta.setUserCount(site_stat_delta.getUserCount() + count);
		}
		
		// 2. 更新该学科的计数
		if (user.getSubjectId() != null) {
			Subject subject = subj_svc.getSubjectById(user.getSubjectId());
			if (subject != null) {
				subject.setUserCount(subject.getUserCount() + count);				
				String hql = "UPDATE Subject SET userCount = userCount + ? WHERE subjectId = ?";
				this.getSession().createQuery(hql).setInteger(0, count).setInteger(1, user.getSubjectId()).executeUpdate();
				//getSession().bulkUpdate(hql, new Object[]{count, user.getSubjectId()});
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.edustar.jitar.service.StatService#incGroupCount(cn.edustar.jitar.pojos.Group, int)
	 */
	public void incGroupCount(Group group, int count) {
		
		// 群组对象不能为空
		if (group == null || count == 0)
			return;
		
		this.countChanged = true;
		
		// 1. 增加站点级计数
		synchronized (this) {
			this.site_stat.setGroupCount(site_stat.getGroupCount() + count);
			this.site_stat_delta.setGroupCount(site_stat_delta.getGroupCount() + count);
		}
		
		// 2. 更新该学科的计数
		if (group.getSubjectId() != null) {
			Subject subject = subj_svc.getSubjectById(group.getSubjectId());
			if (subject != null) {
				subject.setGroupCount(subject.getGroupCount() + count);				
				String hql = "UPDATE Subject SET groupCount = groupCount + ? WHERE subjectId = ?";
				this.getSession().createQuery(hql).setInteger(0, count).setInteger(1, group.getSubjectId()).executeUpdate();
				//getSession().bulkUpdate(hql, new Object[]{count, group.getSubjectId()});
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.edustar.jitar.service.StatService#incArticleCount(cn.edustar.jitar.pojos.Article, int)
	 */
	public void incArticleCount(Article article, int count) {
		// 文章对象不能为空
		if (article == null || count == 0)
			return;
		
		this.countChanged = true;
		
		// 1. 增加站点级文章计数
		synchronized (this) {
			this.site_stat.setArticleCount(site_stat.getArticleCount() + count);
			this.site_stat_delta.setArticleCount(site_stat_delta.getArticleCount() + count);
		}
		
		// 2. 更新该用户的文章数记录
		String hql = "UPDATE User SET articleCount = articleCount + ? WHERE userId = ?";
		this.getSession().createQuery(hql).setInteger(0, count).setInteger(1, article.getUserId()).executeUpdate();
		//getSession().bulkUpdate(hql, new Object[]{count, article.getUserId()});
		
		// 3. 更新该学科的文章数记录
		if (article.getSubjectId() != null) {
			Subject subject = subj_svc.getSubjectById(article.getSubjectId());
			if (subject != null) {
				subject.setArticleCount(subject.getArticleCount() + count);				
				hql = "UPDATE Subject SET articleCount = articleCount + ? WHERE subjectId = ?";
				this.getSession().createQuery(hql).setInteger(0, count).setInteger(1, article.getSubjectId()).executeUpdate();
				//getSession().bulkUpdate(hql, new Object[]{count, article.getSubjectId()});
			}
		}
	}

	/* 
	 * (non-Javadoc)
	 *
	 * @see cn.edustar.jitar.service.StatService#incResourceCount(cn.edustar.jitar.pojos.Resource, int)
	 */
	public void incResourceCount(Resource resource, int count) {
		// 资源对象不能为空
		if (resource == null || count == 0)
			return;

		this.countChanged = true;
		
		// 1. 增加站点级文章计数
		synchronized (this) {
			this.site_stat.setResourceCount(site_stat.getResourceCount() + count);
			this.site_stat_delta.setResourceCount(site_stat_delta.getResourceCount() + count);
		}
		
		// 2. 更新该用户的资源数记录
		String hql = "UPDATE User SET resourceCount = resourceCount + ? WHERE userId = ?";
		this.getSession().createQuery(hql).setInteger(0, count).setInteger(1, resource.getUserId()).executeUpdate();
		
		
		// 3. 更新该学科的资源数记录
		if (resource.getSubjectId() != null) {
			Subject subject = subj_svc.getSubjectById(resource.getSubjectId());
			if (subject != null) {
				subject.setResourceCount(subject.getResourceCount() + count);				
				hql = "UPDATE Subject SET resourceCount = resourceCount + ? WHERE subjectId = ?";
				this.getSession().createQuery(hql).setInteger(0, count).setInteger(1, resource.getSubjectId()).executeUpdate();
				//getSession().bulkUpdate(hql, new Object[]{count, resource.getSubjectId()});
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.edustar.jitar.service.StatService#incPhotoCount(cn.edustar.jitar.pojos.Photo, int)
	 */
	public void incPhotoCount(Photo photo, int count) {
		// 照片对象不能为空
		if (photo == null || count == 0)
			return;
		this.countChanged = true;

		// 1，增加站点级照片计数
		synchronized (this) {
			this.site_stat.setPhotoCount(site_stat.getPhotoCount() + count);
			this.site_stat_delta.setPhotoCount(site_stat_delta.getPhotoCount() + count);
		}
		
		// 2，更新该用户的照片数
		String hql = "UPDATE User SET photoCount = photoCount + ? WHERE userId = ?";
		this.getSession().createQuery(hql).setInteger(0, count).setInteger(1, photo.getUserId());
		//this.getSession().bulkUpdate(hql, new Object[]{count, photo.getUserId()});
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.edustar.jitar.service.StatService#incTopicCount(cn.edustar.jitar.pojos.Topic, int)
	 */
	public void incTopicCount(Topic topic, int count) {
		// 主题对象不能为空
		if (topic == null || count == 0)
			return;

		this.countChanged = true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.edustar.jitar.service.StatService#incCommentCount(cn.edustar.jitar.pojos.Comment, int)
	 */
	public void incCommentCount(Comment comment, int count) {
		// 评论对象不能为空
		if (comment == null || count == 0)
			return;
		
		this.countChanged = true;
	}
	
	/* (non-Javadoc)
	 *
	 * @see cn.edustar.jitar.service.StatService#InsertIntoProductInfo(java.lang.String, java.lang.String, java.lang.String)
	 */
	public void InsertIntoProductInfo(String productID,String productName,String installDate) {
		String hql = "Insert Into ProductConfig (productID, productName, installDate) Values(?,?,?)";
		this.getSession().createQuery(hql).setString(0,productID).setString(1, productName).setString(2, installDate).executeUpdate();
   		//this.getSession().bulkUpdate(hql,new Object[]{productID,productName,installDate});
		return;
	}

	/* (non-Javadoc)
	 *
	 * @see cn.edustar.jitar.service.StatService#InsertIntoStateDateCount(java.lang.String)
	 */
	public void InsertIntoStateDateCount(String dateCount) {
      String hql = "Insert Into SiteStat(DateCount) Values(?)";
      this.getSession().createQuery(hql).setString(0,dateCount).executeUpdate();
	  return;
	}

	/* 统计两个日期段的所有的群组信息
	 * 
	 * @see cn.edustar.jitar.service.StatService#statAllGroup(java.util.Date, java.util.Date)
	 */
	public void statAllGroup(int groupId, String strBeginDate, String strEndDate) {
		groupService.updateGroupStat(groupId, strBeginDate, strEndDate);
	}

	/* 统计两个日期段的所有的机构信息
	 * 
	 * @see cn.edustar.jitar.service.StatService#statAllUnit(java.util.Date, java.util.Date)
	 */
	public void statAllUnit(int unitId, String strBeginDate, String strEndDate) {
		unitService.updateUnitStat(unitId, strBeginDate, strEndDate);
	}

	/* (non-Javadoc)
	 * 
	 * @see cn.edustar.jitar.service.StatService#userOnLineStat(cn.edustar.jitar.pojos.UserOnLineStat)
	 */
	public void updateUserOnLineStat(UserOnLineStat userOnLineStat) {
		if (userOnLineStat != null && userOnLineStat.getHighest() != 0) {
			//String hql = "UPDATE UserOnLineStat SET highest = " + userOnLineStat.getHighest() + ", appearTime = " + String.format("%1$tY-%1$tm-%1$td %1$tH:%1$tM:%1$tS", new Date()) + " WHERE id = 1";
			this.getSession().saveOrUpdate(userOnLineStat);
			this.getSession().flush();
		}
	}
}
