package cn.edustar.jitar.dao;

import java.util.List;

import cn.edustar.data.DataTable;
import cn.edustar.data.Pager;
import cn.edustar.jitar.pojos.Message;
import cn.edustar.jitar.service.MessageQueryParam;

/**
 * 短信息接口
 * 
 * @author Yang Xinxin
 * @version 1.0.0 Mar 7, 2008 2:41:53 PM
 */
public interface MessageDao extends Dao {
	
	/** 投影查询字段 */
	public static final String GET_MESSAGE_LIST = "U.loginName, U.nickName, U.userIcon, M.title, M.content, M.sendTime";

	/**
	 * 根据 Id 得到对应记录.
	 * 
	 * @param id
	 * @return
	 */
	public Message findById(int id);

	/**
	 * 全部信息列表.
	 * 
	 * @param param-查询条件.
	 * @param pager-分页设置.
	 * @param type-列表类型.
	 * @return 字段包括： {f.id, f.userId, f.friendId, f.addTime, f.typeId, f.remark,
	 *         f.isBlack, u.loginName, u.nickName, u.email, u.virtualDirectory,
	 *         u.userFileFolder, u.blogName, u.userIcon}
	 */
	public DataTable getMessageDataTable(MessageQueryParam param, Pager pager);

	/**
	 * 保存消息.
	 * 
	 * @param message
	 */
	public void saveMessage(Message message);

	/**
	 * 物理删除短消息.
	 * @param message
	 */
	public void deleteMessage(Message message); 
	
	/***
	 * 删除发件箱中的消息.
	 * @param message
	 */
	public void senderDelMessage(int messageId, boolean senderDel);

	/**
	 * 当前登录用户收到的短消息总数.
	 * 
	 * @return
	 */
	public int getTotalMessages(int id);

	/**
	 * 当前登录用户收到的未读消息总数.
	 * 
	 * @param id
	 * @return
	 */
	public int getUnreadMessages(int id);

	/**
	 * 得到指定用户的短消息列表 返回为投影查询结果，字段为：U.loginName, U.nickName, U.userIcon, F.title, F.serdtime
	 * 
	 * @param userId
	 * @return
	 */
	public List<Object[]> getMessageList(int userId);
	
	/***
	 * 设置消息的删除状态(是否在回收站中).
	 * @param message
	 */
	public void setMessageRecycleState(int messageId, boolean toRecycle);  
	
	/***
	 * 更新短消息的阅读状态.
	 * @param MessageId
	 */
	public int setMessageReaderState(int messageId, int receiverId, boolean reade);
	
	/**
	 * 更新短消息的回复状态.
	 * @param MessageId
	 * @param reply
	 */
	public void setMessageReplyState(int messageId, boolean reply);
	
	//删除某人接收到的和发送的所有短消息。
	public void deleteAllMessageByUserId(int userId);

	public void setAllMessageRecycleState(int receiveld, boolean b);

	public void senderDelAllMessage(int sendId, boolean b);

	public void setMessageAllRecycleState(int receiveld, boolean b);

	public void deleteAllMessage(int receiveId,Boolean isDel);
	
}