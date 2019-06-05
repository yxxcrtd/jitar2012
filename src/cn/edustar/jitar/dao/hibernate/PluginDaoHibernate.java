package cn.edustar.jitar.dao.hibernate;

import java.util.List;



import cn.edustar.jitar.dao.PluginDao;
import cn.edustar.jitar.pojos.Plugin;
/**
 * @author 孟宪会
 *
 */
public class PluginDaoHibernate extends BaseDaoHibernate implements PluginDao{
		
	/**
	 * 获取插件列表
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Plugin> getPluginList()
	{
		return (List<Plugin>)this.getSession().createQuery("From Plugin Where enabled = 1 Order By itemOrder ASC").list();
	}
	
	/**
	 * 得到全部的插件
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Plugin> getAllPluginList()
	{
		return (List<Plugin>)this.getSession().createQuery("From Plugin Order By itemOrder ASC").list();
	}
	
	/**
	 * 获取一个插件
	 * @param pluginId
	 * @return
	 */
	public Plugin getPluginById(int pluginId)
	{
		return (Plugin)this.getSession().get(Plugin.class, pluginId);
	}
	
	/**
	 * 删除一个插件
	 * @param plugin
	 */
	public void deletePlugin(Plugin plugin)
	{
		String queryString;
		if(plugin.getPluginName().equalsIgnoreCase("vote"))
		{
			queryString = "DELETE FROM VoteUser";
			this.getSession().createQuery(queryString).executeUpdate();
			//this.getSession().bulkUpdate(queryString);
			queryString = "DELETE FROM VoteResult";
			this.getSession().createQuery(queryString).executeUpdate();
			this.getSession().createQuery(queryString).executeUpdate();
			//this.getSession().bulkUpdate(queryString);
			queryString = "DELETE FROM VoteQuestionAnswer";
			this.getSession().createQuery(queryString).executeUpdate();
			//this.getSession().bulkUpdate(queryString);
			queryString = "DELETE FROM VoteQuestion";
			this.getSession().createQuery(queryString).executeUpdate();
			//this.getSession().bulkUpdate(queryString);
			queryString = "DELETE FROM Vote";
			this.getSession().createQuery(queryString).executeUpdate();
			//this.getSession().bulkUpdate(queryString);			
		}
		else if(plugin.getPluginName().equalsIgnoreCase("topic"))
		{
			queryString = "DELETE FROM PlugInTopicReply";
			this.getSession().createQuery(queryString).executeUpdate();
			//this.getSession().bulkUpdate(queryString);
			queryString = "DELETE FROM PlugInTopic";
			this.getSession().createQuery(queryString).executeUpdate();
		}
		else if(plugin.getPluginName().equalsIgnoreCase("questionanswer"))
		{
			queryString = "DELETE FROM QuestionAnswer";
			this.getSession().createQuery(queryString).executeUpdate();
			//this.getSession().bulkUpdate(queryString);
			queryString = "DELETE FROM Question";
			this.getSession().createQuery(queryString).executeUpdate();
			//this.getSession().bulkUpdate(queryString);
		}
		
		this.getSession().delete(plugin);
	}
	
	/**
	 * 修改一个插件
	 * @param plugin
	 */
	public void saveOrUpdatePlugin(Plugin plugin)
	{
		this.getSession().saveOrUpdate(plugin);
	}
}
