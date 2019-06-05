package cn.edustar.jitar.service.impl;

import java.util.List;

import cn.edustar.jitar.module.Module;
import cn.edustar.jitar.service.ModuleContainer;

/**
 * 扩展 ModuleContainer 的模块的包装类.
 *
 *
 */
public class ExtendModuleContainer {
	/** 原来的模块容器 */
	private ModuleContainer mod_cont;
	
	/**
	 * 
	 * @param list
	 */
	@SuppressWarnings("unchecked")
	public void setExtendModule(List list) {
		for (Object o : list) {
			if (o != null && o instanceof Module)
				mod_cont.addModule((Module)o);
		}
	}
}
