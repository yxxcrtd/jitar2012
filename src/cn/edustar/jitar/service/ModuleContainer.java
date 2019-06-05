package cn.edustar.jitar.service;

import cn.edustar.jitar.module.Module;

/**
 * 定义模块容器服务接口。
 * 
 *
 *
 */
public interface ModuleContainer {
	/**
	 * 得到指定名字的模块。
	 * @param module_name
	 * @return
	 */
	public Module getModule(String module_name);
	
	/**
	 * 添加一个模块.
	 * @param module
	 */
	public void addModule(Module module);
}
