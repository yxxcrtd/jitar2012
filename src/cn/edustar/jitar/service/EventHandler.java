package cn.edustar.jitar.service;

/**
 * 定义事件服务中事件处理器的接口。
 * 
 *
 *
 */
public interface EventHandler {
	/**
	 * 当有一个事件被发布的时候，此方法被调用。
	 * @param eventName - 事件名字。
	 * @param publisher - 发布该事件的发布者。
	 * @param event - 发布的事件对象。
	 */
	public void onEventPublished(String eventName, Object publisher, Object event);
}
