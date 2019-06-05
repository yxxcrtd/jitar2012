package cn.edustar.jitar.service;

import cn.edustar.jitar.ui.ActionHandler;

/**
 * 支持某种脚本的 service, 当前为 python 设计.
 * 
 *
 */
public interface ScriptManager {
	
	/**
	 * 得到指定文件对应的可执行脚本对象, 服务可能缓存编译过的脚本.
	 * @param fileName - 脚本实际文件的路径.
	 * @return
	 */
	public ActionHandler getScript(String fileName);
	
}
