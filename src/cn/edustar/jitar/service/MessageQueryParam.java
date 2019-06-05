package cn.edustar.jitar.service;

/**
 * 短信息查询参数
 * 
 * @author Yang Xinxin
 * @version 1.0.0 Mar 7, 2008 2:37:54 PM
 */
public class MessageQueryParam {

	/** 发送者标识 */
	public int sendId; 
	
	/** 接收者标识*/
	public int receiveId;
	
	/**
	 * 消息的接收(send)和发送(receive)标识 
	 * 如果 = null , 则显示收件箱
	 */
	public String type = "receive";
	

}
