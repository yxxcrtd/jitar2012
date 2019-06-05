package cn.edustar.jitar.service;

import cn.edustar.data.DataTable;
import cn.edustar.data.Pager;
import cn.edustar.jitar.pojos.Message;

/**
 * 短信息接口服务
 * 
 * @author Yang Xinxin
 * @version 1.0.0 Mar 7, 2008 3:00:34 PM
 */
public interface MessageService {
	
	/**
	 * 根据 Id 得到对应记录
	 * 
	 * @param id
	 * @return
	 */
	public Message findById(int id);

	/**
	 * 短信息列表
	 * 
	 * @param param-查询条件
	 * @param pager-分页设置
	 * @param type-列表类型
	 * @return
	 */
	public DataTable getMessageDataTable(MessageQueryParam param, Pager pager);

	/**
	 * 发送短消息
	 * 
	 * @param message
	 */
	public void sendMessage(Message message);

	/**
	 * 彻底删除短消息
	 * 
	 * @param id
	 */
	public void crashMessage(Message message);
	
	/***
	 * 删除回收站中的消息
	 * 
	 * @param id
	 */
	public void senderDelMessage(Message message);
	
	/**
	 * 计算指定用户收到的短消息总数
	 * 
	 * @param userId
	 * @return
	 */
	public int getTotalMessages(int userId);

	/**
	 * 计算指定用户未读取的短消息总数
	 * 
	 * @param userId
	 * @return
	 */
	public int getUnreadMessages(int userId);

	/**
	 * 得到指定用户的短消息列表
	 * 
	 * @param userId
	 * @return
	 */ 
	public DataTable getMessageList(int userId);
	
	/***
	 * 将消息移动到回收站中
	 * 
	 * @param message
	 */
	public void moveMessageToRecycle(Message message);
	
	/***
	 * 恢复消息的删除状态(将消息移除回收站)
	 * 
	 * @param message
	 */
	public void unMoveMessageToRecycle(int messageId);
	
	/***
	 * 设置指定用户收到的指定消息已经读过了
	 * 
	 * @param messageId
	 */
	public void setMessageIsRead(int messageId, int receiveId);
	
	/***
	 * 更新短消息的回复状态
	 * 
	 * @param messageId
	 */
	public void updateReplyStatus(int messageId);
	
	public void deleteAllMessageByUserId(int userId);
	
	/**
	 * 删除所有发件箱中的信息
	 * @param receiveld
	 */
	public void moveAllMessageToRecycle (int receiveId);
	
	/**
	 * 删除所有收件箱中的信息
	 */
	public void senderDelAllMessage(int sendId);
	
	/**
	 * 恢复所有的手机向信息
	 */
	
	public void unMoveMessageToAllRecycle(int receiveId);
	
	/**
	 * 彻底删除回收站中的所有短消息
	 */
	
	public void crashAllMessage(int receiveId);
	
}
