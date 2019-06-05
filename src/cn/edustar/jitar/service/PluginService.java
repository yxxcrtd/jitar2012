package cn.edustar.jitar.service;

import java.util.List;

import cn.edustar.jitar.pojos.Plugin;
/**
 * @author 孟宪会
 *
 */
public interface PluginService {

	/**
	 * 获取插件列表
	 * @return
	 */
	public List<Plugin> getPluginList();
	
	/**
	 * 得到全部的插件
	 * @return
	 */
	public List<Plugin> getAllPluginList();
	
	/**
	 * 获取一个插件
	 * @param pluginId
	 * @return
	 */
	public Plugin getPluginById(int pluginId);
	
	/**
	 * 删除一个插件
	 * @param plugin
	 */
	public void deletePlugin(Plugin plugin);
	
	/**
	 * 修改一个插件
	 * @param plugin
	 */
	public void saveOrUpdatePlugin(Plugin plugin);
	
	/**
	 * 检查插件是否启用
	 * @param pluginName
	 * @return
	 */
	public boolean checkPluginEnabled(String pluginName);
}
