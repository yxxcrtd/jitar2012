package cn.edustar.jitar.dao;

import cn.edustar.jitar.pojos.Subject;
import cn.edustar.jitar.pojos.TimerCount;
import cn.edustar.jitar.pojos.Unit;

/**
 * <p>
 * 定时统计数据查询接口。
 * </p>
 * @author mxh
 *
 */
public interface TimerCountDao extends Dao{
	
	/**
	 * <p>
	 * 查询某个计数对象的统计数据。
	 * </p>
	 * @param 计数对象标识。
	 * @return 查询的统计对象。
	 */
	public TimerCount getTimerCountById(int countId);
	
	
	/**
	 * <p>
	 * 更新或者保存统计对象。
	 * </p>
	 * @param 要更新或者保存的统计对象
	 */
	
	public void saveOrUpdateTimerCount(TimerCount timerCount);	
	
	/**
	 * <p>
	 * 执行站点统计。
	 * </p>
	 * @return 无返回值。
	 */
	public TimerCount doSiteCount();
	
	/**
	 * 进行学科统计。
	 * @param 要统计的学科对象。
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
