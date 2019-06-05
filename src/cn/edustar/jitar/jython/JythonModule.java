package cn.edustar.jitar.jython;

import java.io.File;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.python.core.Py;
import org.python.core.PyLong;
import org.python.core.PyModule;
import org.python.core.PyObject;
import org.python.core.PyString;
import org.python.core.PyStringMap;

/**
 * 提供自动重新加载 jython module 能力的模块容器.
 *
 * 
 * 此实现强烈依赖于 jython 实现, 因此可能以后不能用, 或者更换了 jython 版本之后需要测试.
 * 
 */
public class JythonModule extends PyStringMap {
	/** serialVersionUID */
	private static final long serialVersionUID = 4948605018105292183L;

	/** 日志记录器 */
	public static final Log logger = LogFactory.getLog(JythonModule.class);

	/** 是否使能重新加载模块. */
	private boolean enableReload = true;
	
	/** 脚本管理器. */
	private JythonScriptManager mgr;
	
	/**
	 * 构造函数.
	 * @param mgr
	 */
	public JythonModule(JythonScriptManager mgr, PyObject src) {
		this.mgr = mgr;
		if (src != null && src instanceof PyStringMap)
			this.update((PyStringMap)src);
	}

	/** 是否使能重新加载模块. */
	public void setEnableReload(boolean enableReload) {
		this.enableReload = enableReload;
	}

	/**
	 * 我们重载了 PyStringMap 的 __finditem__ 方法, 在这里:
	 *   1. 检测加载的模块文件是否已经更新了, 如果更新了则返回未找到, 这样 jython 会再次加载.
	 *   2. TODO:  
	 */
	@Override
	public PyObject __finditem__(String key) {
		PyObject module = super.__finditem__(key);
		// 如果不激活重新加载功能, 则不用后面的复杂处理了.
		if (enableReload == false) return module;
		
		if (module == null || !(module instanceof PyModule)) 
			return module;
		
		// 检测这个模块文件是否更新了, 如果更新了则删除这个模块.
		if (checkModule((PyModule)module) == false) {
			if (logger.isDebugEnabled()) {
				logger.debug("检测到 jython 模块 " + key + " 的文件已经更新或丢失, 将强制 jython 重新查找和加载该模块.");
			}
			super.__delitem__(key);		// 删除此模块.
			return null;
		}
		
		return module;
	}
	
	/**
	 * 将一个模块加入到内存模块表中, called by jython, 此时我们有机会给模块中注入全局变量.
	 * 此方法暂时不用了, 留下以供参考.
	 */
	/*
	@Override
	public void __setitem__(String key, PyObject value) {
		if (value != null && value instanceof PyModule) {
			injectModule((PyModule)value);
		}
		
		super.__setitem__(key, value);
	}
	*/
	
	/**
	 * 检查指定 jython 模块文件是否更新了.
	 * @param module
	 * @return 返回 true 表示文件未更新; false 表示文件更新了, 模块需要重新加载.
	 */
	private boolean checkModule(PyModule module) {
		// 每次都检查可能比较慢, 不过应该检查不多.
		if (module == null) return true;
		PyObject dict = module.getDict();
		if (dict == null || dict == Py.None) return true;

		// fileName - 模块文件名; lastModified - 最后修改时间.
		PyString fileName = (PyString)dict.__finditem__("__file__".intern());
		PyLong lastModified = (PyLong)dict.__finditem__("__date__".intern());
		
		if (fileName == null || fileName == Py.None) return true;
		File file = new File(fileName.toString());
		// 如果文件不存在了, 则模块被删除了, 返回 false.
		if (file.exists() == false) return false;
		
		// 如果 module 里面没有 lastModified 数据则一定是第一次调用, 现在写入模块.
		if (lastModified == null || lastModified == Py.None) {
			lastModified = new PyLong(file.lastModified());
			dict.__setitem__("__date__".intern(), lastModified);
			return true;
		}
		
		// 如果文件最后修改时间的比较. false 表示修改过了, 从而需要加载.
		long curLastModified = file.lastModified();
		return (curLastModified == lastModified.asLong(0));
	}

	/**
	 * 注入一些全局变量到 module 中, 此方法现在不用了, 保留以供参考.
	 * @param module
	 */
	@SuppressWarnings("unused")
	private void injectModule(PyModule module) {
		// 注入全局变量到模块中.
		if (module.getDict() == null)
			module.setDict(new PyStringMap());
		mgr.injectGlobalVariable(module.getDict());
	}
}
