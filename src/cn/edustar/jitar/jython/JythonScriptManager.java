package cn.edustar.jitar.jython;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Properties;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.python.core.Py;
import org.python.core.PyClass;
import org.python.core.PyFile;
import org.python.core.PyObject;
import org.python.core.PyString;
import org.python.core.PyStringMap;
import org.python.core.PySystemState;
import org.python.util.PythonInterpreter;
import org.springframework.web.context.ServletContextAware;

import com.opensymphony.xwork2.Action;

import cn.edustar.jitar.JitarContext;
import cn.edustar.jitar.service.PythonActionHandler;
import cn.edustar.jitar.service.ScriptManager;
import cn.edustar.jitar.ui.ActionHandler;

/**
 * Python/Jython 脚本支持的服务实现.
 * 
 *
 */
public class JythonScriptManager implements ScriptManager, ServletContextAware {
	
	/** 文章记录器 */
	private static final Log log = LogFactory.getLog(JythonScriptManager.class);

	/** 应用系统环境 */
	private ServletContext servlet_ctxt;

	/** 教研系统环境 */
	private JitarContext jtar_ctxt;

	/** 缺省 Jython 系统环境. */
	@SuppressWarnings("unused")
	private PySystemState jython_sys;
	
	/** jython 脚本的缓存 */
	private Hashtable<String, CacheEntry> cache = new Hashtable<String, CacheEntry>();

	/** WebApp 级的全局变量, 包括 application, jitar, spring 等 */
	protected PyStringMap app_dict = new PyStringMap();

	/** webapp 根路径 */
	private String rootPath;
	
	/** 全局变量 application, request, response, session, __jitar__, __spring__ */
	private PyObject application;
	private PyObject request;
	private PyObject response;
	private PyObject session;
	private PyObject __jitar__;
	private PyObject __spring__;

	/**
	 * 得到应用系统环境.
	 */
	public ServletContext getServletContext() {
		return this.servlet_ctxt;
	}

	/*
	 * (non-Javadoc)
	 * @see org.springframework.web.context.ServletContextAware#setServletContext(javax.servlet.ServletContext)
	 */
	public void setServletContext(ServletContext servlet_ctxt) {
		this.servlet_ctxt = servlet_ctxt;
	}

	/** 教研系统环境 */
	public void setJitarContext(JitarContext jtar_ctxt) {
		this.jtar_ctxt = jtar_ctxt;
	}

	/**
	 * 服务初始化, 由 spring 负责调用.
	 */
	@SuppressWarnings("unchecked")
	public void init() {
		rootPath = getServletContext().getRealPath("/");
		//log.info("JythonScriptManager is loading, rootPath = " + rootPath);
		if (!rootPath.endsWith(File.separator))
			rootPath += File.separator;

		Properties props = new Properties();
		Properties baseProps = PySystemState.getBaseProperties();

		// Context parameters
		ServletContext context = getServletContext();
		Enumeration e = context.getInitParameterNames();
		while (e.hasMoreElements()) {
			String name = (String) e.nextElement();
			props.put(name, context.getInitParameter(name));
		}

		// 设置 python lib 路径地址.
		if (props.getProperty("python.home") == null
				&& baseProps.getProperty("python.home") == null) {
			props.put("python.home", rootPath + "WEB-INF" + File.separator	+ "lib");
		}

		PySystemState.initialize(baseProps, props, new String[0]);
		PySystemState sys = Py.getSystemState();
		this.jython_sys = sys;

		// 设置缺省系统模块容器为我们的容器.
		sys.modules = new JythonModule(this, sys.modules);

		// for java servlet package.
		PySystemState.add_package("javax.servlet");
		PySystemState.add_package("javax.servlet.http");
		PySystemState.add_package("javax.servlet.jsp");
		PySystemState.add_package("javax.servlet.jsp.tagext");

		PySystemState.add_classdir(rootPath + "WEB-INF" + File.separator	+ "classes");

		PySystemState.add_extdir(rootPath + "WEB-INF" + File.separator + "lib",	true);

		sys.path.append(new PyString(rootPath));

		// 模块地址.
		String modulesDir = rootPath + "WEB-INF" + File.separator + "jython";
		
		sys.path.append(new PyString(modulesDir));
		
		// 修复 jython stdout, stderr 编码问题, 使得可以正确输出中文.
		// 这些没用了？？？？
		//sys.stdout = new PyFile(new WriterOutputStream(new WriterWrapper(System.out)));
		//sys.stderr = new PyFile(new WriterOutputStream(new WriterWrapper(System.err)));

		// 初始化全局变量, 其提供给 jython 脚本.
		this.application = Py.java2py(getServletContext());
		this.request = Py.java2py(new JythonRequestWrapper());
		this.response = Py.java2py(new JythonResponseWrapper());
		this.session = Py.java2py(new JythonSessionWrapper());
		this.__jitar__ = Py.java2py(jtar_ctxt);
		this.__spring__ = Py.java2py(jtar_ctxt.getSpringContext());

		// 注入缺省 app_dict.
		initAppDict();
	}

	/**
	 * 销毁, 由 spring 负责调用.
	 */
	public void destroy() {
		destroyCache();
	}

	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.service.ScriptManager#getScript(java.lang.String)
	 */
	public ActionHandler getScript(String path) {
		PyObject cls = internalGetScript(path);
		return new PythonActionHandlerImpl(cls);
	}

	/**
	 * 内部类: 包装 python script 使得接受 request,response 请求并返回一个字符串. 
	 */
	static class PythonActionHandlerImpl implements ActionHandler {
		PyObject cls;

		PythonActionHandlerImpl(PyObject cls) {
			this.cls = cls;
		}

		public String execute(HttpServletRequest request, HttpServletResponse response) {
			// 实例化该类的一个对象.
			//以下判断是新加的 2011-3-34
			if(cls == null)
			{
				response.setStatus(404);
				return Action.ERROR;
			}
			PyObject inst = cls.__call__();
			if (inst == null)
				throw new RuntimeException("无法实例化 " + cls + " 的 python 脚本对象");
			Object o = inst.__tojava__(PythonActionHandler.class);
			if (o == Py.NoConversion) {
				throw new RuntimeException(
						"The value from pyscript must extend PythonAction");
			}
			PythonActionHandler action = (PythonActionHandler) o;

			return action.processRequest(request, response, inst);
		}
	}

	// get servlet from cache or load it.
	private synchronized PyObject internalGetScript(String path) {
		CacheEntry entry = (CacheEntry) cache.get(path);
		if (entry == null)
			return loadPythonClass(path);
		File file = new File(path);
		if (file.lastModified() > entry.date)
			return loadPythonClass(path);
		return entry.cls;
	}

	/**
	 * 获得指定文件中定义的 python 类, 可能从缓存中直接获取.
	 * @param rpath
	 * @return
	 */
	public PyObject getPythonClass(String rpath) throws ServletException {
		// 先尝试从 cache 中获取.
		File file = new File(rpath);
		CacheEntry entry = cache.get(rpath);
		if (entry != null) {
			// 如果缓存中有, 并且自加载后文件未修改则返回.
			if (file.lastModified() <= entry.date)
				return entry.cls;
		}
		
		//log.info("rpath = " + rpath);
		// 立刻加载 python 脚本, 并放到缓存中.
		PyObject cls = loadPythonClass(rpath);
		//log.info("loadPythonClass 结束 ");
		entry = new CacheEntry(cls, file.lastModified());
		cache.put(rpath, entry);
		return cls;
	}

	/**
	 * 加载 python 脚本.
	 */
	private PyObject loadPythonClass(String path) {
		// 从路径中获取文件名字, 该名字也做为类名, 用于获取该 python 类的定义.
		int start = path.lastIndexOf(File.separator);
		if (start < 0)
			start = 0;
		else
			start++;
		int end = path.lastIndexOf('.');
		if ((end < 0) || (end <= start))
			end = path.length();
		String name = path.substring(start, end);		
		
		//log.info("开始创建解释器");
		// 创建一个新的解释器, 并编译 path 指定的 python 脚本.
		//PythonInterpreter interp = new PythonInterpreter();
		JythonInterpreter interp = createInterpreter();
		interp.execfile(path);
		
		//log.info("内部执行完毕");
		
		PyObject cls = interp.get(name);
		
		//log.info("PyObject=" + cls.getClass().getName());
		
		//if (cls == null || !(cls instanceof PyClass)) {
			/*
			throw new RuntimeException("No callable class named " + name + " in "
					+ path+ " 可能是文件大小写导致的。");
					*/
		//	return null; //2011-3-24 加的本行和以上的注释
		//}

		return (PyObject) cls;
	}

	/**
	 * 创建一个 Python 代码解释器.
	 * 
	 * @return
	 */
	protected JythonInterpreter createInterpreter() {
		PyStringMap dict = this.app_dict;

		// 每次均创建一个新的 interpreter, 但是使用相同的 defaultSystemState .
		return new JythonInterpreter(dict, Py.defaultSystemState);
	}

	/**
	 * 给指定的 dict 中注入全局变量.
	 * @param dict
	 */
	public void injectGlobalVariable(PyObject dict) {
		// web 环境全局变量.
		dict.__setitem__("application".intern(), this.application);
		dict.__setitem__("request".intern(), this.request);
		dict.__setitem__("response".intern(), this.response);
		dict.__setitem__("session".intern(), this.session);
		
		// 教研系统特有全局变量.
		dict.__setitem__("__jitar__".intern(), this.__jitar__);
		dict.__setitem__("__spring__".intern(), this.__spring__);
	}
	
	/**
	 * 初始化全局变量字典.
	 */
	protected void initAppDict() {
		// 原来是注入到 module 的 app_dict 中.
		// this.injectGlobalVariable(app_dict);
		
		// 现在我们尝试注入到 jython sys. builtin 中.
		injectGlobalVariable(PySystemState.builtins);
	}

	private void destroyCache() {
		/*
		 for (Enumeration e = cache.elements(); e.hasMoreElements(); ) {
		     CacheEntry entry = (CacheEntry) e.nextElement();
		     /// entry.servlet.destroy();
		 }
		 */
		cache.clear();
	}

	/** Python 脚本缓存 */
	private static class CacheEntry {
		public long date;
		public PyObject cls;

		CacheEntry(PyObject cls, long date) {
			this.cls = cls;
			this.date = date;
		}
	}

	/**
	 * 缺省的 python print 使用 stdout stream 输出会有编码问题, 我们提供给它经过包装的, 会好一些. 
	 */
	private static class WriterWrapper extends java.io.Writer{
		public PrintStream ostm;

		public WriterWrapper(PrintStream ostm) {
			this.ostm = ostm;
		}

		@Override
		public void close() throws IOException {
			ostm.close();
		}

		@Override
		public void flush() throws IOException {
			ostm.flush();
		}

		@Override
		public void write(char[] cbuf, int off, int len) throws IOException {
			String s = new String(cbuf, off, len);
			ostm.print(s);
		}

		/** 主要是这个函数覆盖实现解决编码问题. */
		@Override
		public void write(String s) {
			ostm.print(s);
		}
	}

}
