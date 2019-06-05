package cn.edustar.jitar.service.impl;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.context.ServletContextAware;

import cn.edustar.jitar.jython.JythonObjectWrapper;
import cn.edustar.jitar.model.TemplateModelObject;
import cn.edustar.jitar.service.ModelManager;
import cn.edustar.jitar.service.TemplateProcessor;
import cn.edustar.jitar.servlet.ServletHashModel;
import freemarker.core.Environment;
import freemarker.ext.beans.BeansWrapper;
import freemarker.template.Configuration;
import freemarker.template.ObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;

/**
 * FreeMarker模板执行器的实现 * 
 *
 */
public class FreeMarkerTemplateProcessor implements TemplateProcessor, ServletContextAware {
	
	/** 日志 */
	private static final Log log = LogFactory.getLog(FreeMarkerTemplateProcessor.class);
	
	/** WEB APP 环境对象. */
	private ServletContext servletContext;
	
	/** FreeMarker 配置对象. */
	private Configuration cfg;
	
	/** 对象包装器 */
	private ObjectWrapper obj_wrapper;

	/** 对象包装器的set方法 */
	public void setObjectWrapper(ObjectWrapper ow) {
		this.obj_wrapper = ow;
	}
	
	/** 模型管理器 */
	private ModelManager model_mgr;

	/** 模型管理器的set方法 */
	public void setModelManager(ModelManager model_mgr) {
		this.model_mgr = model_mgr;
	}
	
	/** 缺省构造函数 */
	public FreeMarkerTemplateProcessor() {
		
	}

	/** WEB APP 环境对象 */
	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
	}

	/** FreeMarker 配置对象 */
	public void setConfiguration(Configuration cfg) {
		this.cfg = cfg;
	}
	
	/**
	 * 模板调试异常处理器	 */
	private static final class DebugTemplateExceptionHandler implements TemplateExceptionHandler {
		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * freemarker.template.TemplateExceptionHandler#handleTemplateException
		 * (freemarker.template.TemplateException, freemarker.core.Environment,
		 * java.io.Writer)
		 */
		public void handleTemplateException(TemplateException te, Environment env, Writer out) throws TemplateException {
	        PrintWriter pw = (out instanceof PrintWriter) ? (PrintWriter) out : new PrintWriter(out);
	        pw.println("<!-- FREEMARKER ERROR MESSAGE STARTS HERE -->"
	                + "<span align=left "
	                + "style='background-color:#FFFF00; color:#FF0000; "
	                + "display:block; border-top:double; padding:2pt; "
	                + "font-size:medium; font-family:Arial,sans-serif; "
	                + "font-style: normal; font-variant: normal; "
	                + "font-weight: normal; text-decoration: none; "
	                + "text-transform: none'>"
	                + "<b style='font-size:medium'>模板合成发生错误!</b>"
	                + "");
	        pw.print(te.getMessage());
	        pw.println("</span>");
	        pw.flush();
	        pw.close();
		}
	}
	
	/**
	 * 初始化方法，Spring 将在注入依赖对象之后执行该初始化方法	 */
	public void init() {
		log.info("FreeMarkerTemplateProcessor is initializing...");
		
		// 按照标准 FreeMarker 帮助，第一步：创建 Configuration 实例
		this.cfg = new Configuration();
		
		// 第二步：指定模板来源, 语言等		// cfg.setClassicCompatible(true);			// null 不输出错误
		cfg.setDefaultEncoding("UTF-8");
		cfg.setURLEscapingCharset("UTF-8");		// ?url builtin 使用此设置
		cfg.setServletContextForTemplateLoading(this.servletContext, "/");
		// 第三步：指定对象包装器，不使用 DefaultObjectWrapper, 其不能满足我们的需求
		// BeansWrapper beans_wrap = new BeansWrapper();
		if (this.obj_wrapper == null) {
			JythonObjectWrapper beans_wrap = new JythonObjectWrapper();
			// 方法也能调用, 这样有一些危险, 但是为了功能强只能如此了, 最好不告诉用户这个
			beans_wrap.setExposureLevel(BeansWrapper.EXPOSE_ALL);
			this.obj_wrapper = beans_wrap;
		}
		
		cfg.setObjectWrapper(obj_wrapper);
		cfg.setBooleanFormat("true,false");
		cfg.setNumberFormat("0.##########");
		cfg.setDateFormat("yyyy-MM-dd hh:mm:ss");
		// 我们忽略错误，然后继续执行
		cfg.setTemplateExceptionHandler(new DebugTemplateExceptionHandler());
		
		// 添加共享变量. 可能抛出 RuntimeException 但是我们也无法处理，只能检查配置
		addSharedVariable();
	}
	
	// 添加共享变量.
	@SuppressWarnings("unchecked")
	private void addSharedVariable() throws RuntimeException {
		try {
			List shared_var = model_mgr.getSharedModels();
			if (shared_var == null) return;
			for (Iterator iter = shared_var.iterator(); iter.hasNext(); ) {
				Object o = iter.next();
				if (o instanceof TemplateModelObject) {
					TemplateModelObject tmo = (TemplateModelObject)o;
					cfg.setSharedVariable(tmo.getVariableName(), o);
				}
			}
		} catch (TemplateException ex) {
			log.error("无法添加模板共享变量。", ex);
			throw new RuntimeException("无法添加模板共享变量。也许配置有问题？", ex);
		}
	}
	
	/**
	 * 关闭方法，Spring 关闭的时候调用	 */
	public void destroy() {
		this.cfg = null;
	}
	
	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.service.iface.TemplateProcessor#processTemplate2(java.lang.Object, java.lang.String)
	 */
	public String processTemplate(Object root_map, String template_name, String encoding) {
		StringWriter writer = new StringWriter(4*1024);
		try {
			processTemplate(root_map, writer, template_name, encoding);
		} catch (IOException ex) {
			log.error(ex);
			return ex.toString();
		}
		return writer.toString();
	}

	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.service.iface.TemplateProcessor#processTemplate(java.lang.Object, java.io.Writer, java.lang.String, java.lang.String)
	 */
	public void processTemplate(Object root_map, Writer writer, String template_name, String encoding) 
			throws IOException {
		if (encoding == null || encoding.length() == 0) 
			encoding = "UTF-8";
		Template template = cfg.getTemplate(template_name, encoding);
		if (template == null) 
			throw new FileNotFoundException("未找到模板: " + template_name);
		
		try {
			template.process(root_map, writer);
		} catch (TemplateException ex) {
			log.error(ex);
		}
	}

	public String processStringTemplate(Object root_map, String template_text)
	{
		//System.out.println(template_text);
		String ret = "";
		if((root_map instanceof Map) == false){  
		  throw new IllegalArgumentException("参数 root_map 必须为一个 Map 对象");  
		}
		BufferedReader reader = new BufferedReader(new StringReader(template_text));  
		Template template = null;  
		try{  
			template = new Template(null, reader, cfg, "UTF-8");  
		}catch(Exception ex){  
			ex.printStackTrace();  
		} 
		try{  
			StringWriter stringWriter = new StringWriter();  
			BufferedWriter writer = new BufferedWriter(stringWriter);  
			template.process(root_map, writer);  
			writer.flush();  
			ret = stringWriter.toString(); 
				             
		}catch(Exception ex){  
			ex.printStackTrace(); 
		} 
		return ret;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.edustar.jitar.service.TemplateProcessor#createRootMap(javax.servlet.http.HttpServletRequest, java.util.Map)
	 */
	@SuppressWarnings("rawtypes")
	public Object createRootMap(HttpServletRequest request, Map map) {
		return new ServletHashModel(obj_wrapper, request, map);
	}

	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.service.FreemarkerTemplateProcessor#getObjectWrapper()
	 */
	public ObjectWrapper getObjectWrapper() {
		return this.obj_wrapper;
	}
	
	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.service.FreemarkerTemplateProcessor#getConfiguration()
	 */
	public Configuration getConfiguration() {
		return this.cfg;
	}	
}
