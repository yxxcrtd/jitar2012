package cn.edustar.jitar.dao;

import cn.edustar.jitar.pojos.PlugInTopic;
import cn.edustar.jitar.pojos.PlugInTopicReply;

/**
 * @author 孟宪会
 *
 */
public interface PlugInTopicDao {

	/**
	 * 添加创建一个讨论话题
	 * @param plugInTopic
	 */
	public void addPluginTopic(PlugInTopic plugInTopic);
	
	/**
	 * 根据 id 标识加载讨论话题
	 * @return
	 */
	public PlugInTopic getPlugInTopicById(int plugInTopicById);
	
	/**
	 * 删除一个讨论话题
	 * @param plugInTopic
	 */
	public void deletePluginTopic(PlugInTopic plugInTopic);
	
	/**
	 * 根据标识删除讨论话题
	 * @param plugInTopicId
	 */
	public void deletePluginTopicById(int plugInTopicId);
	
	/**
	 * 删除某一个用户的全部讨论话题
	 * @param userId
	 */
	public void deletePluginTopicByUserId(int userId);
	
	/**
	 * 删除某一对象的全部讨论话题
	 * @param parentGuid
	 */
	public void deletePluginTopicByParentGuid(String parentGuid);
	
	/**
	 * 删除某一类型的全部讨论话题
	 * @param parentObjectType
	 */
	public void deletePluginTopicByParentObjectType(String parentObjectType);
	
	/**
	 * 添加一个讨论话题回复
	 * @param plugInTopicReply
	 */
	public void addPlugInTopicReply(PlugInTopicReply plugInTopicReply);
	
	/**
	 * 删除一个讨论话题回复
	 * @param plugInTopicReply
	 */
	public void deletePlugInTopicReply(PlugInTopicReply plugInTopicReply);
	
	/**
	 * 根据标识删除一个讨论话题回复
	 * @param plugInTopicReplyId
	 */
	public void deletePlugInTopicReplyById(int plugInTopicReplyId);
	
	/**
	 * 删除某一个用户的全部讨论话题回复
	 * @param userId
	 */
	public void deletePlugInTopicReplyByUserId(int userId);
	
	/**
	 * 删除某一个讨论话题的所有讨论话题回复
	 * @param plugInTopicId
	 */
	public void deletePlugInTopicReplyByPlugInTopicId(int plugInTopicId);
	
}
