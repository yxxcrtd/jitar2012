package cn.edustar.jitar.dao;

import java.util.List;

import cn.edustar.jitar.pojos.Config;

/**
 * 配置数据库访问接口定义
 * 
 * @author Yang Xinxin
 * @version 1.0.0 Feb 17, 2009 11:14:51 AM
 */
public interface ConfigDao extends Dao {
	
	/**
	 * 得到指定类型的所有配置项
	 * 
	 * @param itemType
	 * @return
	 */
	public List<Config> getConfigList(String itemType);

	/**
	 * 得到指定标识的配置项
	 * 
	 * @param id
	 * @return
	 */
	public Config getConfigById(int id);
	
	/**
	 * 得到指定项目类型和指定项目名的配置项
	 * 
	 * @param itemType
	 * @param name
	 * @return
	 */
	public Config getConfigByItemTypeAndName(String itemType, String name); 
	
	/**
	 * 创建一个新的配置项
	 * 
	 * @param config
	 */
	public void createConfig(Config config);

	/**
	 * 更新一个配置项
	 * 
	 * @param config
	 */
	public void updateConfig(Config config);

	/**
	 * 删除一个配置项
	 * 
	 * @param config
	 */
	public void deleteConfig(Config config);
	
	/**
	 * 得到所有'boolean'型的数据
	 *
	 * @return
	 */
	public List<Config> getSysConfigList();
	
}
