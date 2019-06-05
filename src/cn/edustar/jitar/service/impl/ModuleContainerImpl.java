package cn.edustar.jitar.service.impl;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.edustar.jitar.module.Module;
import cn.edustar.jitar.service.ModuleContainer;

/**
 * 模块容器的实现.
 * 
 *
 *
 */
public class ModuleContainerImpl implements ModuleContainer {
	/** 文章记录器 */
	private static final Log logger = LogFactory.getLog(ModuleContainerImpl.class);
	
	/** 模块的实际容器 */
	private Map<String, Module> module_map = 
		new Hashtable<String, Module>();
	
	/**
	 * 初始化，从 Spring 调用.
	 */
	public void init() {
		logger.info("ModuleContainer initialized.");
	}
	
	/**
	 * 结束，从 Spring 调用.
	 */
	public void destroy() {
		this.module_map.clear();
	}
	
	/**
	 * 添加一个模块.
	 * @param module
	 */
	public void addModule(Module module) {
		if (module == null) throw new IllegalArgumentException("module == null");
		if (module.getEnabled())
			module_map.put(module.getModuleName(), module);
	}

	/**
	 * 通过 Spring 注入进来的模块列表.
	 *   我们假定此方法只在初始化的时候执行1次，并且在任何使用 container 方法之前.
	 * @param modules
	 */
	@SuppressWarnings("unchecked")
	public void setModules(List modules) {
		for (int i = 0; i < modules.size(); ++i) {
			Module m = (Module)modules.get(i);
			if (m != null && m.getEnabled())
				module_map.put(m.getModuleName(), m);
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.service.iface.ModuleContainer#getModule(java.lang.String)
	 */
	public Module getModule(String module_name) {
		return module_map.get(module_name);
	}
}
