package cn.edustar.jitar.service.impl;

import cn.edustar.jitar.dao.TimerCountDao;
import cn.edustar.jitar.pojos.Subject;
import cn.edustar.jitar.pojos.TimerCount;
import cn.edustar.jitar.pojos.Unit;
import cn.edustar.jitar.service.CacheService;
import cn.edustar.jitar.service.TimerCountService;

/**
 * <p>
 * 定时统计的服务实现。
 * </p>
 * @author mxh
 *
 */
public class TimerCountServiceImpl implements TimerCountService {

	private TimerCountDao timerCountDao;
	private CacheService cacheService;
	public void setTimerCountDao(TimerCountDao timerCountDao) {
		this.timerCountDao = timerCountDao;
	}

	public void setCacheService(CacheService cacheService) {
		this.cacheService = cacheService;
	}

	public void saveOrUpdateTimerCount(TimerCount timerCount){
	    this.timerCountDao.saveOrUpdateTimerCount(timerCount);
	    this.cacheService.remove(this.getCacheKeyName());
	}
	
	/**
	 * 默认从缓存中获取。
	 */
	@Override
	public TimerCount getTimerCountById(int countId){
		TimerCount tc = (TimerCount)this.cacheService.get(this.getCacheKeyName());
		if(null == tc){
			tc = this.timerCountDao.getTimerCountById(TimerCount.COUNT_TYPE_SITE);
			this.cacheService.put(this.getCacheKeyName(), tc, this.getCacheLiveTime()); 
		}
		return tc;
	}
	
	/**
	 * 执行站点统计。
	 */
	@Override
	public void doSiteCount() {		
		TimerCount tc = this.timerCountDao.doSiteCount();
		this.cacheService.remove(this.getCacheKeyName());
		this.cacheService.put(this.getCacheKeyName(), tc, this.getCacheLiveTime()); 
	}
	
	/**
	 * <p>
	 * 执行某学科的统计。
	 * </p>
	 */	
	public void doSubjectCount(Subject subject){
		this.timerCountDao.doSubjectCount(subject);
	}
	
	
	/**
	 * 设置缓存的key
	 * @return
	 */
	private String getCacheKeyName(){
		return "timerCount" + TimerCount.COUNT_TYPE_SITE;
	}
	
	/**
	 * 设置缓存的时间，单位为秒.
	 * @return
	 */
	private int getCacheLiveTime(){
		return 36 * 60; //存活时间设置为36分钟 ，30分钟更新一次，安全起见，设置为36分钟。
	}

	@Override
	public void doUnitCount(Unit unit) {
		// TODO Auto-generated method stub
	}
}
