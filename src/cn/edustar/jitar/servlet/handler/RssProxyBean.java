package cn.edustar.jitar.servlet.handler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.servlet.ServletException;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 实际完成 RssProxyServlet 任务的对象类.
 * 
 *
 *
 */
public class RssProxyBean extends ServletBeanBase {
	/** 文章记录器 */
	private static final Logger logger = LoggerFactory.getLogger(RssProxyBean.class);
	
	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.action.ServletBeanBase#handleRequst()
	 */
	@Override
	protected void handleRequst() throws IOException, ServletException {
		// 得到 url 参数
		String url = request.getParameter("url");
		if (logger.isDebugEnabled()) {
		logger.debug("Rssurl="+url);
		}
		//System.out.println("Rssurl="+url);
		// 向该 url 发出获取数据请求
		// 构造 apache HttpClient 对象
		DefaultHttpClient httpclient = new DefaultHttpClient();
		// TODO: 设置代理服务器
		// if (config.xxx) hc.getHostConfiguration().setProxy(proxyHost, proxyPort);
		// 使用 GET 方法
	
		try {
			HttpGet httpGet = new HttpGet(url);
			HttpResponse httpResponse = httpclient.execute(httpGet);
			HttpEntity entity = httpResponse.getEntity();
			
			int status = httpResponse.getStatusLine().getStatusCode();
			if (logger.isDebugEnabled()) {
				logger.debug("handleRequest url = " + url + ", return status = " + status);
			
				/*
				Header[] headers = method.getResponseHeaders();
				for (int i = 0; i < headers.length; ++i) {
					logger.debug("  header[" + i + "] = " + headers[i].toString().trim());
				}
				*/
			}
			
			// 如果有错误, 则直接返回
			if (status < 200 || status >= 300) {
				//System.out.println("测试啊 Rssurl status="+status);
				//response.sendError(status, method.getStatusText());
				response.setContentType("text/html;charset=utf-8");
				response.getWriter().write(url+":" + status);
				response.flushBuffer();
				response.getWriter().close();
				return;
			}
			
			// 返回的数据头可能有多个, 我们选用 Server, Date, Content-Type, Last-Modified, Age 几个 header
			//   给我们的客户端
			/*if (method.getResponseHeader("Server") != null)
				response.setHeader("Server", method.getResponseHeader("Server").getValue());
			if (method.getResponseHeader("Date") != null)
				response.setHeader("Date", method.getResponseHeader("Date").getValue());
			if (method.getResponseHeader("Content-Type") != null)
				response.setContentType(method.getResponseHeader("Content-Type").getValue());
			if (method.getResponseHeader("Last-Modified") != null)
				response.setHeader("Last-Modified", method.getResponseHeader("Last-Modified").getValue());
			if (method.getResponseHeader("Age") != null)
				response.setHeader("Age", method.getResponseHeader("Age").getValue());*/
			
			// 原样发回数据给客户端
			/*byte[] cbuf = new byte[4096];
			InputStream _in = method.getResponseBodyAsStream();
			OutputStream _out = response.getOutputStream();
			while (true) {
				int len = _in.read(cbuf);
				if (len <= 0) break;
				_out.write(cbuf, 0, len);
			}
			_in.close();
			_out.close();*/
			String charset = null;
			String contentType = httpResponse.getHeaders("Content-Type")[0].toString().toLowerCase();
			/*
			org.apache.http.Header[] hs=httpResponse.getAllHeaders();
			for(int i=0;i<hs.length;i++){
				if (logger.isDebugEnabled()) {
					logger.debug("Header Name="+hs[i].getName());
					logger.debug("Header Value="+hs[i].getValue());
					}					
			}
			if (logger.isDebugEnabled()) {
				logger.debug("contentType="+contentType);
				}
			*/
			
			if(contentType.contains("charset="))
			{
				charset = contentType.substring(contentType.lastIndexOf("charset=") + "charset=".length());
				if (logger.isDebugEnabled()) {
					logger.debug("charset="+charset);
					}					
			}
			
			
			/*
			 StringBuilder MyStringBuilder = new StringBuilder();
			//继续检查 <?xml version="1.0" encoding="utf-8" ?>
			if(charset == null || charset.length() == 0){
			 if (entity != null) {  
		           BufferedReader reader = new BufferedReader(new InputStreamReader(entity.getContent()));  
		           String str = reader.readLine();
		           boolean bFind=false;
		           while(null != str)
		           {
		               //System.out.println(str);
		        	   MyStringBuilder.append(str);
		        	   if(!bFind){
				   			if(str.contains("encoding="))
							{
								str = str.substring(str.lastIndexOf("encoding=") + "encoding=".length(),str.lastIndexOf("?"));
								str=str.replace("\'", "");
								charset=str.replace("\"", "");
								bFind=true;
								if (logger.isDebugEnabled()) {
									logger.debug("xml charset="+charset);
									}					
							}	
		        	   }
			   			str = reader.readLine();
		            }  
		        }  
			}
			String myString=MyStringBuilder.toString();
			*/
			if(charset == null || charset.length() == 0) charset = "gb2312"; 
			
			if (logger.isDebugEnabled()) {
				logger.debug("entity == null "+(entity == null));
				}				
			if (entity != null) {
				InputStream content = entity.getContent();
				String myString;
				
				//myString = IOUtils.toString(content);
				 myString = IOUtils.toString(content, charset);
				
				//如果没有得到charset，则从xml中的<?xml version="1.0" encoding="utf-8" ?>得到之
				/*
				if(charset == null || charset.length() == 0){
					String temp=myString.substring(0,1000); 
		   			if(temp.contains("encoding="))
					{
		   				logger.debug("xml temp="+temp);
		   				temp = temp.substring(temp.lastIndexOf("encoding=") + "encoding=".length());
		   				temp = temp.substring(0,temp.indexOf("?"));
						
		   				if (logger.isDebugEnabled()) {
							logger.debug("xml charset temp="+temp);
							}					
		   				temp=temp.replace("\'", "");
						charset=temp.replace("\"", "");
						if (logger.isDebugEnabled()) {
							logger.debug("xml charset="+charset);
							}					
					}	
				}
				if(charset == null || charset.length() == 0) charset = "utf-8"; 
				*/
				response.setContentType("application/xml;charset="+charset);
				response.getWriter().write(myString);
				response.flushBuffer();
				response.getWriter().close();
			}
			
		}
		catch (Exception ex)
		{
			//response.reset();
			response.setContentType("text/html;charset=utf-8");
			response.getWriter().write(ex.getLocalizedMessage());
			response.flushBuffer();
			response.getWriter().close();
			return;
		}
		finally {
			httpclient.getConnectionManager().shutdown();
		}
	}
}
