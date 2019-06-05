package cn.edustar.jitar.jython;

import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.RequestContext;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.dispatcher.multipart.MultiPartRequestWrapper;

/**
 * 对 request 进行了封装, 使得其能够支持文件上传.
 *
 *
 */
@SuppressWarnings("unchecked")
public class MultiPartRequestImpl extends HttpServletRequestWrapper implements Closeable {
	private static final Log log = LogFactory.getLog(MultiPartRequestImpl.class);
	
	/** 页面上提交的参数集合 */
	private Map parameters = null;
	
	/** 保存所有上传的文件,  */
	private List<FileItem> need_cleanup = null; 
	
	/**
	 * 构造.
	 * @param request
	 */
	private MultiPartRequestImpl(HttpServletRequest request) {
		super(request);
	}
	
	/**
	 * 构造.
	 * @param request
	 */
	private MultiPartRequestImpl(HttpServletRequest request, String saveDir) throws IOException {
		super(request);
		this.parseFiles(request, saveDir);
	}
	
	/**
	 * 构造.
	 * @param request
	 * @param parameters
	 */
	private MultiPartRequestImpl(HttpServletRequest request, Map parameters) {
		super(request);
		this.parameters = parameters;
	}
	
	/**
	 * 构造一个请求包装器.
	 * @param request - 在 servlet 里面收到的 request 对象.
	 * @param origin_request - 早期在 filter 中设置的原始 request 对象, 其可能和 request 不一样.
	 *   也有可能一样, 由于 filter 配置的不同而不同, 因此不能完全依赖.
	 * @return
	 */
	public static MultiPartRequestImpl createWrapper(HttpServletRequest request, HttpServletRequest origin_request)
				throws IOException {
		if ("org.apache.struts2.dispatcher.multipart.MultiPartRequestWrapper".equals(request.getClass().getName()))
			return createWrapperForStruts((MultiPartRequestWrapper)request, origin_request);
		
		// 需要我们自己解析 request, 当前可以进行解析了.
		// 计算正确的临时目录位置 - 如 c:/xxx/Tomcat/temp.
		String saveDir = System.getProperty("java.io.tmpdir");
		return new MultiPartRequestImpl(origin_request, saveDir);
	}

	/*
	 * (non-Javadoc)
	 * @see java.io.Closeable#close()
	 */
	public synchronized void close() {
		// super.setRequest(null);
		this.parameters = null;
		if (this.need_cleanup != null && this.need_cleanup.size() > 0) {
			for (FileItem f : need_cleanup) {
				if (f != null) safeDeleteFileItem(f);
			}
			this.need_cleanup = null;
		}
	}
	
	private void safeDeleteFileItem(FileItem f) {
		if (f == null) return;
		try {
			f.delete();
		} catch (Exception ex) {
			// ignore.
		}
	}
	
	/**
	 * 从 request 中解析出 params, files.
	 * @param request
	 * @param saveDir
	 * @throws IOException
	 */
	private void parseFiles(HttpServletRequest request, String saveDir) 
			throws IOException {
		Map params = new HashMap(request.getParameterMap());
		this.need_cleanup = new ArrayList<FileItem>();
		
      DiskFileItemFactory fac = new DiskFileItemFactory();
      // Make sure that the data is written to file
      fac.setSizeThreshold(0);
      if (saveDir != null) {
          fac.setRepository(new File(saveDir));
      }
      
      // 解析出来的参数和文件对象.
      /// Map<String,List<String>> params = new HashMap<String,List<String>>();
      /// Map<String,List<FileItem>> files = new HashMap<String,List<FileItem>>();
      
      // note: see http://jira.opensymphony.com/browse/WW-633
      // basically, in some cases the charset may be null, so
      // we're just going to try to "other" method (no idea if this
      // will work)
      String charset = request.getCharacterEncoding();
      // Parse the request
      try {
          ServletFileUpload upload = new ServletFileUpload(fac);
          /// TODO: upload.setSizeMax(maxSize);
          List items = upload.parseRequest(request);
          // 这里可能什么也解析不出来.
          if (items == null || items.size() == 0) {
         	 log.warn("试图解析 MultiPart 数据的时候未能得到结果, 很可能是前面已经有过滤器等装置已经解析过了.");
          }

          for (Object item1 : items) {
              FileItem item = (FileItem) item1;
              this.need_cleanup.add(item);

              // if (log.isDebugEnabled()) log.debug("找到一个表单项: " + item.getFieldName());
              if (item.isFormField()) {
                  // log.debug("Item is a normal form field");
                  String value = (charset != null) ? item.getString(charset) : item.getString();
                  _addToParameter(params, item.getFieldName(), value);
              } else {
                 DiskFileItem dfi = (DiskFileItem)item;
                 String fieldName = dfi.getFieldName();

                  // Skip file uploads that don't have a file name - meaning that no file was selected.
                  if (item.getName() == null || item.getName().trim().length() < 1) {
                      log.debug("No file has been uploaded for the field: " + item.getFieldName());
                      continue;
                  }
                  log.debug("表单项 " + fieldName + " 是一个上载文件:" + dfi.getStoreLocation());
                  File f = dfi.getStoreLocation();
                  _addToParameter(params, fieldName, f);
                  _addToParameter(params, fieldName + "Names", getCanonicalName(dfi.getName()));
                  _addToParameter(params, fieldName + "SystemNames", dfi.getStoreLocation().getName());
              }
          }
      } catch (FileUploadException e) {
          log.error(e);
          /// TODO: errors.add(e.getMessage());
          throw new RuntimeException("Can't parse upload files.", e);
      }
      this.parameters = params;
	}
	
	/**
	 * 为 struts MultiPartRequestWrapper 已经处理过的 request 进行处理, 
	 *   我们从它里面直接拿出上传的文件可以给外面用.
	 * @param request
	 * @param origin_request
	 * @return
	 */
	private static MultiPartRequestImpl createWrapperForStruts(MultiPartRequestWrapper request,
			ServletRequest origin_request) {
		// 得到原始 parameter map.
		Map parameters = new HashMap(origin_request.getParameterMap());
		
		// 合并 struts 解析出来的参数进去.
		parameters.putAll(request.getParameterMap());
		
		// 合并 struts 解析出的文件参数进去.
		mergeFileParameters(parameters, request);
		
		// 产生包装对象, 有自己的 ParametersMap.
		return new MultiPartRequestImpl((HttpServletRequest)origin_request, parameters);
	}
	
	/**
	 * 得到被解析出来的文件, 以客户端用 input type='file' name='hello' 为例上传的文件, 
	 *   在 parameters 里面放置的变量为:
	 *     'hello' -- 上传上来的文件集合, 可能有多个.
	 *     'helloNames' -- 用户原始客户端文件名.
	 *     'helloSystemNames' -- 在服务器上的文件名, 一般是临时的 upload_xxx...yyy.tmp .
	 * @param parameters
	 * @param request
	 */
	private static void mergeFileParameters(Map parameters, MultiPartRequestWrapper request) {
		
		Enumeration<String> fieldNames = request.getFileParameterNames();
		while (fieldNames.hasMoreElements()) {
			String fieldName = fieldNames.nextElement();
			
			File[] files = request.getFiles(fieldName);
			if (files != null)
				parameters.put(fieldName, files);
			
			String[] fileNames = request.getFileNames(fieldName);
			if (fileNames != null)
				parameters.put(fieldName + "Names", fileNames);
			
			String[] fileSystemNames = request.getFileSystemNames(fieldName);
			if (fileSystemNames != null)
				parameters.put(fieldName + "SystemNames", fileSystemNames);
		}
	}

	private static void _addToParameter(Map params, String fieldName, Object value) {
		// 如果还没有, 则放进去.
		if (params.containsKey(fieldName) == false) {
			if (value instanceof String)
				params.put(fieldName, new String[]{(String)value});
			else if (value instanceof File)
				params.put(fieldName, new File[]{(File)value});
			// else not support. 
			return;
		}
		
		// 有了, 则扩展 array, 并放入.
		Object o = params.get(fieldName);
		if (o instanceof String[] && value instanceof String) {
			String[] a = (String[])o;
			String[] new_a = new String[a.length + 1];
			for (int i = 0 ; i < a.length; ++i)
				new_a[i] = a[i];
			new_a[new_a.length - 1] = (String)value;
			params.put(fieldName, new_a);
		} else if (o instanceof File[] && value instanceof File) {
			File[] a = (File[])o;
			File[] new_a = new File[a.length + 1];
			for (int i = 0 ; i < a.length; ++i)
				new_a[i] = a[i];
			new_a[new_a.length - 1] = (File)value;
			params.put(fieldName, new_a);
		}
		// else : not match, ignore.
	}
	
   /**
    * Returns the canonical name of the given file.
    *
    * @param filename  the given file
    * @return the canonical name of the given file
    */
   private static String getCanonicalName(String filename) {
       int forwardSlash = filename.lastIndexOf("/");
       int backwardSlash = filename.lastIndexOf("\\");
       if (forwardSlash != -1 && forwardSlash > backwardSlash) {
           filename = filename.substring(forwardSlash + 1, filename.length());
       } else if (backwardSlash != -1 && backwardSlash >= forwardSlash) {
           filename = filename.substring(backwardSlash + 1, filename.length());
       }

       return filename;
   }

	/*
	 * (non-Javadoc)
	 * @see javax.servlet.ServletRequestWrapper#getParameter(java.lang.String)
	 */
	@Override
	public String getParameter(String name) {
		if (this.parameters != null) {
			Object o = this.parameters.get(name);
			if (o instanceof String) return (String)o;
			if (o instanceof String[]) return ((String[])o)[0];
			if (o instanceof File[]) return ((File[])o)[0].getPath();
			return "";
		}
		return super.getParameter(name);
	}

	/*
	 * (non-Javadoc)
	 * @see javax.servlet.ServletRequestWrapper#getParameterMap()
	 */
	@Override
	public Map getParameterMap() {
		if (this.parameters != null) 
			return this.parameters;
		return super.getParameterMap();
	}
	
	/*
	 * (non-Javadoc)
	 * @see javax.servlet.ServletRequestWrapper#getParameterNames()
	 */
	@Override
	public Enumeration getParameterNames() {
		if (this.parameters != null)
			return new EI(parameters.keySet().iterator());
		return super.getParameterNames();
	}
	
	// implement an enumeration on internal iterator.
	private static class EI implements Enumeration {
		Iterator iter;
		EI(Iterator iter) {
			this.iter = iter;
		}
		public boolean hasMoreElements() {
			return iter.hasNext();
		}

		public Object nextElement() {
			return iter.next();
		}
	}

	/*
	 * (non-Javadoc)
	 * @see javax.servlet.ServletRequestWrapper#getParameterValues(java.lang.String)
	 */
	@Override
	public String[] getParameterValues(String name) {
		if (this.parameters != null) {
			Object o = this.parameters.get(name);
			if (o instanceof String) return new String[] {(String)o};
			if (o instanceof String[]) return (String[])o;
			return null;
		}
		return super.getParameterValues(name);
	}

   /**
    * Creates a RequestContext needed by Jakarta Commons Upload.
    *
    * @param req  the request.
    * @return a new request context.
    * 似乎现在 common-upload 支持直接用 HttpServletRequest 类型参数了, 因此此方法可以不用了.
    */
   @SuppressWarnings("unused")
	private static RequestContext createRequestContext(final HttpServletRequest req) {
       return new RequestContext() {
           public String getCharacterEncoding() {
               return req.getCharacterEncoding();
           }

           public String getContentType() {
               return req.getContentType();
           }

           public int getContentLength() {
               return req.getContentLength();
           }

           public InputStream getInputStream() throws IOException {
               return req.getInputStream();
           }
       };
   }

}
/**
	Struts MultiPartRequestWrapper 里面封装的 MultiPartRequest JakartaMultiPartRequest 内容示例. 
{
file=[
	name=C:/Documents and Settings/liujunxing/My Documents/My Pictures/sina_bk/bg.gif, 
	StoreLocation=D:/Workspace/.metadata/.plugins/com.genuitec.eclipse.easie.tomcat.myeclipse/tomcat/work/Catalina/localhost/Groups/upload_32ebe2b1_11b72d54714__7ffb_00000015.tmp, 
	size=257bytes, 
	isFormField=false, 
	FieldName=file, 
	name=C:/Documents and Settings/liujunxing/My Documents/My Pictures/sina_bk/news_mj_001.gif, 
	StoreLocation=D:/Workspace/.metadata/.plugins/com.genuitec.eclipse.easie.tomcat.myeclipse/tomcat/work/Catalina/localhost/Groups/upload_32ebe2b1_11b72d54714__7ffb_00000016.tmp, 
	size=3912bytes, 
	isFormField=false, 
	FieldName=file
	], 
	
newfile=
	[
	name=C:/Documents and Settings/liujunxing/My Documents/My Pictures/sina_bk/option_bgleft2.gif, 
	StoreLocation=D:/Workspace/.metadata/.plugins/com.genuitec.eclipse.easie.tomcat.myeclipse/tomcat/work/Catalina/localhost/Groups/upload_32ebe2b1_11b72d54714__7ffb_00000017.tmp, 
	size=398bytes, 
	isFormField=false, 
	FieldName=newfile
	]
}
*/