package cn.edustar.jitar.jython;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.python.core.PyException;
import org.python.core.PyInstance;

import cn.edustar.jitar.servlet.ServletHashModel;

import freemarker.template.ObjectWrapper;
import freemarker.template.TemplateHashModel;
import freemarker.template.TemplateModel;
import freemarker.template.TemplateModelException;

/**
 * 为 python -> freemarker 提供数据的包装实现.
 *
 *
 */
public class JythonServletHashModel extends ServletHashModel implements TemplateHashModel {
	private final PyInstance py_instance;
	
	/**
	 * 构造.
	 */
	@SuppressWarnings("unchecked")
	public JythonServletHashModel(ObjectWrapper obj_wrapper, HttpServletRequest request, 
			Map map, PyInstance py_instance) {
		super(obj_wrapper, request, map);
		this.py_instance = py_instance;
	}

	/**
	 * 构造.
	 */
	public JythonServletHashModel(ObjectWrapper obj_wrapper, HttpServletRequest request, 
			 PyInstance py_instance) {
		super(obj_wrapper, request, null);
		this.py_instance = py_instance;
	}
	
	/*
	 * (non-Javadoc)
	 * @see freemarker.template.TemplateHashModel#get(java.lang.String)
	 */
	public TemplateModel get(String key) throws TemplateModelException {
		if (key == null) return null;
		
		Object obj;
		key = key.intern();		// python need intern for key
		// try get from py_instance
		if (py_instance != null) {
			try {
				obj = py_instance.__findattr__(key);
				if (obj != null) return wrap(obj);
				obj = py_instance.__finditem__(key);
				if (obj != null) return wrap(obj);
			} catch (PyException ex) {
				// ignore
			}
		}
		
		return super.get(key);
	}
	
	/**
	 * 我们假定总是非空.
	 */
	public boolean isEmpty() throws TemplateModelException {
		return false;
	}
}
