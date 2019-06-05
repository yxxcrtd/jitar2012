package cn.edustar.jitar.service.impl;

import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.edustar.jitar.service.EventHandler;
import cn.edustar.jitar.service.EventManager;

/**
 * 事件管理的简单实现.
 * 
 *
 *
 */
public class EventManagerImpl implements EventManager {
	/** 文章记录器 */
	private static final Log logger = LogFactory.getLog(EventManagerImpl.class);

	/** 所有处理器的记录 */
	private Map<String, HandlerEntry> h_map = new HashMap<String, HandlerEntry>();

	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.service.EventManager#subscribe(java.lang.String, cn.edustar.jitar.service.EventHandler)
	 */
	public void subscribe(String eventName, EventHandler handler) {
		HandlerEntry entry = this.getHandlerEntry(eventName, true);
		entry.addHandler(handler);
	}

	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.service.iface.EventService#unsubscribe(java.lang.String, cn.edustar.jitar.service.iface.EventHandler)
	 */
	public void unsubscribe(String eventName, EventHandler handler) {
		HandlerEntry entry = this.getHandlerEntry(eventName, false);
		if (entry != null)
			entry.removeHandler(handler);
		
		// TODO: 清理没有任何 handler 的项目
	}

	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.service.iface.EventService#publishEvent(java.lang.String, java.lang.Object, java.lang.Object)
	 */
	public void publishEvent(String eventName, Object publisher, Object event) {
		if (logger.isDebugEnabled()) {
			logger.debug("publishEvent eventName=" + eventName + 
					", publisher=" + publisher +
					", event=" + event);
		}
		
		// TODO: 具体派发此 event 给订阅者
		HandlerEntry entry = this.getHandlerEntry(eventName, false);
		if (entry != null)
			entry.publishEvent(eventName, publisher, event);
	}

	/**
	 * 根据事件名字获得事件映射对象.
	 * @param eventName
	 * @param create - 为 true 表示如果没有则创建一个.
	 * @return
	 */
	private HandlerEntry getHandlerEntry(String eventName, boolean create) {
		HandlerEntry entry = this.h_map.get(eventName);
		if (create == false || entry != null) 
			return entry;
		
		// 现在需要创建新的 map 项目了
		synchronized (this) {
			entry = this.h_map.get(eventName);
			if (entry != null) return entry;
			
			// 创建一个新的 map
			Map<String, HandlerEntry> new_map = new HashMap<String, HandlerEntry>(h_map);
			entry = new HandlerEntry(eventName);
			new_map.put(eventName, entry);
			this.h_map = new_map;
		}
		
		return entry;
	}
	
	/**
	 * 事件容器.
	 */
	private static class HandlerEntry {
		/** 事件名 */
		private final String evt_name;
		
		private ArrayList<EventHandler> handlers = new ArrayList<EventHandler>();
		
		// 构造
		public HandlerEntry(String evt_name) {
			this.evt_name = evt_name;
		}

		public String getEventName() {
			return this.evt_name;
		}
		
		// 触发事件
		public void publishEvent(String eventName, Object publisher, Object event) {
			ArrayList<EventHandler> handlers = this.handlers;
			for (int i = 0; i < handlers.size(); ++i) {
				handlers.get(i).onEventPublished(eventName, publisher, event);
			}
		}
		
		// 添加一个处理器
		public synchronized void addHandler(EventHandler handler) {
			ArrayList<EventHandler> new_handlers = new ArrayList<EventHandler>(this.handlers);
			new_handlers.add(handler);
			this.handlers = new_handlers;
		}
		
		// 删除一个处理器
		public synchronized void removeHandler(EventHandler handler) {
			ArrayList<EventHandler> new_handlers = new ArrayList<EventHandler>(this.handlers);
			new_handlers.remove(handler);
			this.handlers = new_handlers;
		}
	}
}
