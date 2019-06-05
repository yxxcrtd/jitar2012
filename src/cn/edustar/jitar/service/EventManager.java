package cn.edustar.jitar.service;

/**
 * 定义事件服务接口。
 * 
 *
 * @remark
 *   事件服务能够为系统内各个组件提供事件的注册、侦听、派发的服务。
 *   
 */
public interface EventManager {
	/**
	 * 订阅一个事件.
	 * @param eventName - 要订阅的事件的名字.
	 * @param handler - 事件处理器.
	 */
	public void subscribe(String eventName, EventHandler handler);
	
	/**
	 * 取消一个事件的订阅.
	 * @param eventName - 要取消订阅的事件的名字.
	 * @param handler - 事件处理器.
	 */
	public void unsubscribe(String eventName, EventHandler handler);
	
	/**
	 * 发布一个事件，此事件将传递给所有订阅该事件的订阅者.
	 * @param eventName - 发布的事件的名字.
	 * @param publisher - 事件发布者.
	 * @param eventObject - 发布的事件对象.
	 */
	public void publishEvent(String eventName, Object publisher, Object eventObject);
}
