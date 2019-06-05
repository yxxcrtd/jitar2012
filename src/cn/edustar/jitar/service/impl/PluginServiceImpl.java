package cn.edustar.jitar.service.impl;

import java.util.List;

import cn.edustar.jitar.dao.PluginDao;
import cn.edustar.jitar.pojos.Plugin;
import cn.edustar.jitar.service.PluginService;

/**
 * @author 孟宪会
 *
 */
public class PluginServiceImpl implements PluginService {
	
	/**
	 * 
	 */
	private PluginDao pluginDao;	


	/**
	 * 获取插件列表
	 * @return
	 */
	public List<Plugin> getPluginList()
	{
		return this.pluginDao.getPluginList();
	}
	
	/**
	 * 得到全部的插件
	 * @return
	 */
	public List<Plugin> getAllPluginList()
	{
		return this.pluginDao.getAllPluginList();
	}
	
	/**
	 * 获取一个插件
	 * @param pluginId
	 * @return
	 */
	/* (non-Javadoc)
	 * @see cn.edustar.jitar.service.PluginService#getPluginById(int)
	 */
	/* (non-Javadoc)
	 * @see cn.edustar.jitar.service.PluginService#getPluginById(int)
	 */
	public Plugin getPluginById(int pluginId){
		return this.pluginDao.getPluginById(pluginId);
	}
	
	/**
	 * 删除一个插件
	 * @param plugin
	 */
	public void deletePlugin(Plugin plugin){
		this.pluginDao.deletePlugin(plugin);
	}
	
	/**
	 * 修改一个插件
	 * @param plugin
	 */
	public void saveOrUpdatePlugin(Plugin plugin)
	{
		this.pluginDao.saveOrUpdatePlugin(plugin);
	}
	
	/**
	 * 检查插件是否启用
	 * @param pluginName
	 * @return
	 */
	public boolean checkPluginEnabled(String pluginName)
	{
		List<Plugin> plugin_list  = this.getAllPluginList();
		for(Plugin p : plugin_list)
		{
			if(p.getPluginName().equalsIgnoreCase(pluginName) && p.getEnabled() == 1)
			{
				return true;
			}
		}
		return false;
	}
	
	public PluginDao getPluginDao() {
		return pluginDao;
	}

	public void setPluginDao(PluginDao pluginDao) {
		this.pluginDao = pluginDao;
	}

}
