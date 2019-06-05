package cn.edustar.jitar.jython;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.python.core.Py;
import org.python.core.PyDictionary;
import org.python.core.PyFloat;
import org.python.core.PyInteger;
//import org.python.core.PyJavaInstance;
import org.python.core.PyLong;
import org.python.core.PyNone;
import org.python.core.PyObject;
import org.python.core.PyObjectDerived;
import org.python.core.PySequence;
import org.python.core.PyString;
import org.python.core.PyStringMap;

import freemarker.ext.beans.BeansWrapper;
import freemarker.template.AdapterTemplateModel;
import freemarker.template.ObjectWrapper;
import freemarker.template.TemplateBooleanModel;
import freemarker.template.TemplateHashModel;
import freemarker.template.TemplateHashModelEx;
import freemarker.template.TemplateMethodModel;
import freemarker.template.TemplateMethodModelEx;
import freemarker.template.TemplateModel;
import freemarker.template.TemplateModelAdapter;
import freemarker.template.TemplateModelException;
import freemarker.template.TemplateNumberModel;
import freemarker.template.TemplateScalarModel;
import freemarker.template.TemplateSequenceModel;
import freemarker.template.utility.OptimizerUtil;

/**
 * An object wrapper that wraps Jython objects into FreeMarker template models
 * and vice versa.
 * 
 * @version $Id: JythonWrapper.java,v 1.23.2.1 2005/10/04 16:18:08 revusky Exp $
 * @author Attila Szegedi
 */
public class JythonObjectWrapper extends BeansWrapper implements ObjectWrapper {
	@SuppressWarnings("unchecked")
	private static final Class PYOBJECT_CLASS = PyObject.class;
	public static final JythonObjectWrapper INSTANCE = new JythonObjectWrapper();

	public JythonObjectWrapper() {
	}

	/**
	 * Wraps the passed Jython object into a FreeMarker template model. If the
	 * object is not a Jython object, it is first coerced into one using
	 * {@link Py#java2py(java.lang.Object)}. {@link PyDictionary} and
	 * {@link PyStringMap} are wrapped into a hash model, {@link PySequence}
	 * descendants are wrapped into a sequence model, {@link PyInteger},
	 * {@link PyLong}, and {@link PyFloat} are wrapped into a number model. All
	 * objects are wrapped into a scalar model (using {@link Object#toString()}
	 * and a boolean model (using {@link PyObject#__nonzero__()}. For internal
	 * general-purpose {@link PyObject}s returned from a call to
	 * {@link #unwrap(TemplateModel)}, the template model that was passed to
	 * <code>unwrap</code> is returned.
	 */
	public TemplateModel wrap(Object obj) throws TemplateModelException {

		// System.out.println("TemplateModel wrap =" +
		// obj.getClass().getCanonicalName());

		if (obj == null) {
			return super.wrap(null);
		}
		if (obj instanceof TemplateModel) {
			return (TemplateModel) obj;
		}
		if (obj instanceof TemplateModelAdapter) {
			return ((TemplateModelAdapter) obj).getTemplateModel();
		}
		/*
		 * 问题: Jython 不支持 boolean 型, 其 boolean 型以数字 1, 0 表示; 而 Freemarker 却必须要求
		 * boolean 型. 如果我们不包装, 则从 Jython 中设置的 request.setAttribute("bool", True)
		 * 在 freemarker 中不能用; 如果我们包装了, 则从 Freemarker 中调用 a == b 进行数字比较又会出问题,
		 * 因此很烦恼. if (obj instanceof Integer) { return new
		 * IntegerAndBooleanModel((Integer)obj); }
		 */
		// 如果不是 python 对象则让 beansWrapper 进行包装.
		if (!(obj instanceof PyObject))
			return super.wrap(obj);

		if (obj instanceof PyObjectDerived) {
			 //Object jobj = ((PyObjectDerived) obj).__tojava__(java.lang.Object.class);
			// return super.wrap(obj);
			return JythonSequenceModel.FACTORY.create(obj, this);
		}

		// 如果 python 对象里面包装着 java 对象, 则取出让基类进行包装.
		//System.out.println("obj=" + obj.getClass().getCanonicalName());
		/*
		 * if (obj instanceof PyJavaInstance) { Object jobj = ((PyJavaInstance)
		 * obj).__tojava__(java.lang.Object.class); return super.wrap(jobj); }
		 */
		boolean asHash = false;
		boolean asSequence = false;

		if (asHash || obj instanceof PyDictionary || obj instanceof PyStringMap) {
			return JythonHashModel.FACTORY.create(obj, this);
			// return modelCache.getInstance(obj, JythonHashModel.FACTORY);
		}
		if (asSequence || obj instanceof PySequence) {
			return JythonSequenceModel.FACTORY.create(obj, this);
			// return modelCache.getInstance(obj, JythonSequenceModel.FACTORY);
		}
		if (obj instanceof PyInteger || obj instanceof PyLong
				|| obj instanceof PyFloat) {
			return JythonNumberModel.FACTORY.create(obj, this);
			// return modelCache.getInstance(obj, JythonNumberModel.FACTORY);
		}
		if (obj instanceof PyNone) {
			return super.wrap(null);
		}
		return super.wrap(obj);
	}

	/**
	 * Coerces a template model into a {@link PyObject}.
	 * 
	 * @param model
	 *            the model to coerce
	 * @return the coerced model.
	 *         <ul>
	 *         <li>
	 *         <li>{@link AdapterTemplateModel}s (i.e.
	 *         {@link freemarker.ext.beans.BeanModel}) are marshalled using the
	 *         standard Python marshaller {@link Py#java2py(Object)} on the
	 *         result of <code>getWrappedObject(PyObject.class)</code>s. The
	 *         native JythonModel instances will just return the underlying
	 *         PyObject.
	 *         <li>All other models that are {@link TemplateScalarModel scalars}
	 *         are marshalled as {@link PyString}.
	 *         <li>All other models that are {@link TemplateNumberModel numbers}
	 *         are marshalled using the standard Python marshaller
	 *         {@link Py#java2py(Object)} on their underlying
	 *         <code>Number</code></li>
	 *         <li>All other models are marshalled to a generic internal
	 *         <code>PyObject</code> subclass that'll correctly pass
	 *         <code>__finditem__</code>, <code>__len__</code>,
	 *         <code>__nonzero__</code>, and <code>__call__</code> invocations
	 *         to appropriate hash, sequence, and method models.</li>
	 *         </ul>
	 */
	@SuppressWarnings("deprecation")
	public PyObject unwrap(TemplateModel model) throws TemplateModelException {
		if (model instanceof AdapterTemplateModel) {
			return Py.java2py(((AdapterTemplateModel) model)
					.getAdaptedObject(PYOBJECT_CLASS));
		}
		if (model instanceof freemarker.ext.util.WrapperTemplateModel) {
			return Py
					.java2py(((freemarker.ext.util.WrapperTemplateModel) model)
							.getWrappedObject());
		}

		// Scalars are marshalled to PyString.
		if (model instanceof TemplateScalarModel) {
			return new PyString(((TemplateScalarModel) model).getAsString());
		}

		// Numbers are wrapped to Python built-in numeric types.
		if (model instanceof TemplateNumberModel) {
			Number number = ((TemplateNumberModel) model).getAsNumber();
			if (number instanceof BigDecimal) {
				number = OptimizerUtil.optimizeNumberRepresentation(number);
			}
			if (number instanceof BigInteger) {
				// Py.java2py can't automatically coerce a BigInteger into a
				// PyLong. This will probably get fixed in later Jython release.
				return new PyLong((BigInteger) number);
			} else {
				return Py.java2py(number);
			}
		}
		// Return generic TemplateModel-to-Python adapter
		return new TemplateModelToJythonAdapter(model);
	}

	@SuppressWarnings("serial")
	private class TemplateModelToJythonAdapter extends PyObject implements
			TemplateModelAdapter {
		private final TemplateModel model;

		TemplateModelToJythonAdapter(TemplateModel model) {
			this.model = model;
		}

		public TemplateModel getTemplateModel() {
			return model;
		}

		public PyObject __finditem__(PyObject key) {
			if (key instanceof PyInteger) {
				return __finditem__(((PyInteger) key).getValue());
			}
			return __finditem__(key.toString());
		}

		public PyObject __finditem__(String key) {
			if (model instanceof TemplateHashModel) {
				try {
					return unwrap(((TemplateHashModel) model).get(key));
				} catch (TemplateModelException e) {
					throw Py.JavaError(e);
				}
			}
			throw Py.TypeError("item lookup on non-hash model ("
					+ getModelClass() + ")");
		}

		public PyObject __finditem__(int index) {
			if (model instanceof TemplateSequenceModel) {
				try {
					return unwrap(((TemplateSequenceModel) model).get(index));
				} catch (TemplateModelException e) {
					throw Py.JavaError(e);
				}
			}
			throw Py.TypeError("item lookup on non-sequence model ("
					+ getModelClass() + ")");
		}

		@SuppressWarnings("unchecked")
		public PyObject __call__(PyObject args[], String keywords[]) {
			if (model instanceof TemplateMethodModel) {
				boolean isEx = model instanceof TemplateMethodModelEx;
				List list = new ArrayList(args.length);
				try {
					for (int i = 0; i < args.length; ++i) {
						list.add(isEx ? (Object) wrap(args[i])
								: (Object) (args[i] == null ? null : args[i]
										.toString()));
					}
					return unwrap((TemplateModel) ((TemplateMethodModelEx) model)
							.exec(list));
				} catch (TemplateModelException e) {
					throw Py.JavaError(e);
				}
			}
			throw Py.TypeError("call of non-method model (" + getModelClass()
					+ ")");
		}

		public int __len__() {
			try {
				if (model instanceof TemplateSequenceModel) {
					return ((TemplateSequenceModel) model).size();
				}
				if (model instanceof TemplateHashModelEx) {
					return ((TemplateHashModelEx) model).size();
				}
			} catch (TemplateModelException e) {
				throw Py.JavaError(e);
			}

			return 0;
		}

		public boolean __nonzero__() {
			try {
				if (model instanceof TemplateBooleanModel) {
					return ((TemplateBooleanModel) model).getAsBoolean();
				}
				if (model instanceof TemplateSequenceModel) {
					return ((TemplateSequenceModel) model).size() > 0;
				}
				if (model instanceof TemplateHashModel) {
					return !((TemplateHashModelEx) model).isEmpty();
				}
			} catch (TemplateModelException e) {
				throw Py.JavaError(e);
			}
			return false;
		}

		private String getModelClass() {
			return model == null ? "null" : model.getClass().getName();
		}
	}

}
