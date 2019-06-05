package cn.edustar.jitar.dao.hibernate;

import java.util.List;



import cn.edustar.jitar.dao.PlugInTopicDao;
import cn.edustar.jitar.pojos.PlugInTopic;
import cn.edustar.jitar.pojos.PlugInTopicReply;

public class PlugInTopicDaoHibernate extends BaseDaoHibernate implements PlugInTopicDao {	
	
	/**
	 * 添加创建一个讨论话题
	 * @param plugInTopic
	 */
	public void addPluginTopic(PlugInTopic plugInTopic)
	{
		this.getSession().save(plugInTopic);
	}
	
	/**
	 * 根据 id 标识加载讨论话题
	 * @return
	 */
	public PlugInTopic getPlugInTopicById(int plugInTopicById)
	{
		return (PlugInTopic)this.getSession().get(PlugInTopic.class, plugInTopicById);
	}
	
	/**
	 * 删除一个讨论话题
	 * @param plugInTopic
	 */
	public void deletePluginTopic(PlugInTopic plugInTopic)
	{
		if (plugInTopic == null) return;
		//先删除回复：
		String queryString = "DELETE FROM PlugInTopicReply Where plugInTopicId = ?";
		this.getSession().createQuery(queryString).setInteger(0,  plugInTopic.getPlugInTopicId()).executeUpdate();		
		this.getSession().delete(plugInTopic);
	}
	
	/**
	 * 根据标识删除讨论话题
	 * @param plugInTopicId
	 */
	public void deletePluginTopicById(int plugInTopicId)
	{
		String queryString = "DELETE FROM PlugInTopicReply Where plugInTopicId = ?";
		this.getSession().createQuery(queryString).setInteger(0, plugInTopicId).executeUpdate();	
		
		queryString = "DELETE FROM PlugInTopic Where plugInTopicId = ?";
		this.getSession().createQuery(queryString).setInteger(0, plugInTopicId).executeUpdate();	
	}
	
	/**
	 * 删除某一个用户的全部讨论话题
	 * @param userId
	 */
	@SuppressWarnings("unchecked")
	public void deletePluginTopicByUserId(int userId)
	{
		String queryString;
		queryString = "FROM PlugInTopic Where createUserId = ?";
		List<PlugInTopic> ts = (List<PlugInTopic>)this.getSession().createQuery(queryString).setInteger(0, userId).list();
		for(int i = 0; i<ts.size();i++)
		{
			PlugInTopic p = (PlugInTopic)ts.get(i);
			this.deletePluginTopic(p);
		}
	}
	
	/**
	 * 删除某一对象的全部讨论话题
	 * @param parentGuid
	 */
	@SuppressWarnings("unchecked")
	public void deletePluginTopicByParentGuid(String parentGuid)
	{
		String queryString;
		queryString = "FROM PlugInTopic Where Where parentGuid = ?";
		List<PlugInTopic> ts = (List<PlugInTopic>)this.getSession().createQuery(queryString).setString(0, parentGuid).list();//this.getSession().find(queryString, parentGuid);
		for(int i = 0; i<ts.size();i++)
		{
			PlugInTopic p = (PlugInTopic)ts.get(i);
			this.deletePluginTopic(p);
		}	
	}
	
	/**
	 * 删除某一类型的全部讨论话题
	 * @param parentObjectType
	 */
	@SuppressWarnings("unchecked")
	public void deletePluginTopicByParentObjectType(String parentObjectType)
	{
		String queryString = "FROM PlugInTopic Where parentObjectType = ?";
		List<PlugInTopic> ts = (List<PlugInTopic>)this.getSession().createQuery(queryString).setString(0, parentObjectType).list();
		for(int i = 0; i<ts.size();i++)
		{
			PlugInTopic p = (PlugInTopic)ts.get(i);
			this.deletePluginTopic(p);
		}	
	}
	
	/**
	 * 添加一个讨论话题回复
	 * @param plugInTopicReply
	 */
	public void addPlugInTopicReply(PlugInTopicReply plugInTopicReply)
	{
		this.getSession().save(plugInTopicReply);
	}
	
	/**
	 * 删除一个讨论话题回复
	 * @param plugInTopicReply
	 */
	public void deletePlugInTopicReply(PlugInTopicReply plugInTopicReply)
	{
		this.getSession().delete(plugInTopicReply);
	}
	
	/**
	 * 根据标识删除一个讨论话题回复
	 * @param plugInTopicReplyId
	 */
	public void deletePlugInTopicReplyById(int plugInTopicReplyId)
	{
		String queryString = "DELETE FROM PlugInTopicReply Where plugInTopicReplyId = ?";
		this.getSession().createQuery(queryString).setInteger(0,plugInTopicReplyId).executeUpdate();
	}
	
	/**
	 * 删除某一个用户的全部讨论话题回复
	 * @param userId
	 */
	public void deletePlugInTopicReplyByUserId(int userId)
	{
		String queryString = "DELETE FROM PlugInTopicReply Where createUserId = ?";
		this.getSession().createQuery(queryString).setInteger(0, userId).executeUpdate();
	}
	
	/**
	 * 删除某一个讨论话题的所有讨论话题回复
	 * @param plugInTopicId
	 */
	public void deletePlugInTopicReplyByPlugInTopicId(int plugInTopicId)
	{
		String queryString = "DELETE FROM PlugInTopicReply Where plugInTopicId = ?";
		this.getSession().createQuery(queryString).setInteger(0, plugInTopicId).executeUpdate();
	}
}
