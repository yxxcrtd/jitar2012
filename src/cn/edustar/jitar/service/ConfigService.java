package cn.edustar.jitar.service;

import java.util.List;

import cn.edustar.jitar.model.Configure;
import cn.edustar.jitar.pojos.Config;

/**
 * 提供教研系统配置信息的服务接口定义 *
 * @author Yang Xinxin
 * @version 1.0.0 Mar 17, 2008 13:30:25 PM
 */
public interface ConfigService {
	
	/** 站点配置键的名字, 在模板中通过 ${SiteConfig} 即可访问到 */
	public static final String CONFIG_KEY_NAME = "SiteConfig";
	
	/**
	 * 得到当前系统配置. (itemType = 'system')
	 * 
	 * @return
	 */
	public Configure getConfigure();
	
	/**
	 * 得到指定标识的配置项
	 * 
	 * @param id
	 * @return
	 */
	public Config getConfigById(int id);

	/**
	 * 根据项目类型和名字得到指定配置
	 * 
	 * @param itemType
	 * @param name
	 * @return
	 */
	public Config getConfigByItemTypeAndName(String itemType, String name);
	
	/**
	 * 创建一个新配置项
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
	 * @param cfg_val
	 */
	public void deleteConfig(Config config);
	
	/**
	 * 重新加载所有配置, 一般用于更新完所有配置之后调用以迫使系统重新加载配置
	 */
	public void reloadConfig();
	
	/**
	 * 得到所有'boolean'型的数据
	 *
	 * @return
	 */
	public List<Config> getSysConfigList();
	
}
