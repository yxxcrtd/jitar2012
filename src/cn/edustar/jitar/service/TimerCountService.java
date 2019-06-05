package cn.edustar.jitar.service;

import cn.edustar.jitar.pojos.Subject;
import cn.edustar.jitar.pojos.TimerCount;
import cn.edustar.jitar.pojos.Unit;

/**
 * <p>定时站点统计接口。</p>
 * @author mxh
 *
 */
public interface TimerCountService {
    
    public void saveOrUpdateTimerCount(TimerCount timerCount);
    
	/**
	 * <p>得到站点的统计数据，站点Id默认设置为1。此参数在TimerCount中设置。</p>
	 * @see TimerCount
	 * @param 统计标识
	 * @return 返回站点统计的数据。
	 * @author mxh
	 */
	public TimerCount getTimerCountById(int countId);
	
	/**
	 * <p>执行站点统计。</p>
	 */
	public void doSiteCount();
	
	/**
	 * <p>
	 * 对指定学科进行统计。
	 * </p>
	 * @param subject 要统计的学科对象。
	 */
	public void doSubjectCount(Subject subject);
	
	/**
	 * <p>
	 * 对指定机构进行统计。
	 * </p>
	 * @param unit 要统计的机构对象。
	 */
	public void doUnitCount(Unit unit);
}
