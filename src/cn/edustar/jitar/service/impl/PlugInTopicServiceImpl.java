package cn.edustar.jitar.service.impl;

import cn.edustar.jitar.dao.PlugInTopicDao;
import cn.edustar.jitar.pojos.PlugInTopic;
import cn.edustar.jitar.pojos.PlugInTopicReply;
import cn.edustar.jitar.service.PlugInTopicService;

public class PlugInTopicServiceImpl implements PlugInTopicService {

	private PlugInTopicDao plugInTopicDao;
	/**
	 * 添加创建一个讨论话题
	 * @param plugInTopic
	 */
	public void addPluginTopic(PlugInTopic plugInTopic)
	{
		this.plugInTopicDao.addPluginTopic(plugInTopic);
	}
	
	/**
	 * 根据 id 标识加载讨论话题
	 * @return
	 */
	public PlugInTopic getPlugInTopicById(int plugInTopicById)
	{
		return this.plugInTopicDao.getPlugInTopicById(plugInTopicById);
	}
	
	/**
	 * 删除一个讨论话题
	 * @param plugInTopic
	 */
	public void deletePluginTopic(PlugInTopic plugInTopic)
	{
		this.plugInTopicDao.deletePluginTopic(plugInTopic);
	}
	
	/**
	 * 根据标识删除讨论话题
	 * @param plugInTopicId
	 */
	public void deletePluginTopicById(int plugInTopicId)
	{
		this.plugInTopicDao.deletePluginTopicById(plugInTopicId);
	}
	
	/**
	 * 删除某一个用户的全部讨论话题
	 * @param userId
	 */
	public void deletePluginTopicByUserId(int userId)
	{
		this.plugInTopicDao.deletePluginTopicByUserId(userId);
	}
	
	/**
	 * 删除某一对象的全部讨论话题
	 * @param parentGuid
	 */
	public void deletePluginTopicByParentGuid(String parentGuid)
	{
		this.plugInTopicDao.deletePluginTopicByParentGuid(parentGuid);
	}
	
	/**
	 * 删除某一类型的全部讨论话题
	 * @param parentObjectType
	 */
	public void deletePluginTopicByParentObjectType(String parentObjectType)
	{
		this.plugInTopicDao.deletePluginTopicByParentObjectType(parentObjectType);
	}
	
	/**
	 * 添加一个讨论话题回复
	 * @param plugInTopicReply
	 */
	public void addPlugInTopicReply(PlugInTopicReply plugInTopicReply)
	{
		this.plugInTopicDao.addPlugInTopicReply(plugInTopicReply);
	}
	
	/**
	 * 删除一个讨论话题回复
	 * @param plugInTopicReply
	 */
	public void deletePlugInTopicReply(PlugInTopicReply plugInTopicReply)
	{
		this.plugInTopicDao.deletePlugInTopicReply(plugInTopicReply);
	}
	
	/**
	 * 根据标识删除一个讨论话题回复
	 * @param plugInTopicReplyId
	 */
	public void deletePlugInTopicReplyById(int plugInTopicReplyId)
	{
		this.plugInTopicDao.deletePlugInTopicReplyById(plugInTopicReplyId);
	}
	
	/**
	 * 删除某一个用户的全部讨论话题回复
	 * @param userId
	 */
	public void deletePlugInTopicReplyByUserId(int userId)
	{
		this.plugInTopicDao.deletePlugInTopicReplyByUserId(userId);
	}
	
	/**
	 * 删除某一个讨论话题的所有讨论话题回复
	 * @param plugInTopicId
	 */
	public void deletePlugInTopicReplyByPlugInTopicId(int plugInTopicId)
	{
		this.plugInTopicDao.deletePlugInTopicReplyByPlugInTopicId(plugInTopicId);
	}


	public PlugInTopicDao getPlugInTopicDao() {
		return plugInTopicDao;
	}

	public void setPlugInTopicDao(PlugInTopicDao plugInTopicDao) {
		this.plugInTopicDao = plugInTopicDao;
	}
}