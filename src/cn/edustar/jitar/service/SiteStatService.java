package cn.edustar.jitar.service;

import cn.edustar.jitar.pojos.SiteStat;

/**
 * 站点级统计服务接口定义.
 * 
 *
 */
public interface SiteStatService {
	
	/**
	 * 得到当前站点统计信息的一个副本, 一般用于显示当前统计信息.
	 * 
	 * @return
	 */
	public SiteStat getSiteStat();
	
}
