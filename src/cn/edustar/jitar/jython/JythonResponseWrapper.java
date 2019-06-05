package cn.edustar.jitar.jython;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.Locale;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import cn.edustar.jitar.JitarRequestContext;

/**
 * 提供给 python 使用的当前 response 对象包装
 *
 *
 */
@SuppressWarnings("deprecation")
public class JythonResponseWrapper implements HttpServletResponse {
	private HttpServletResponse resp() {
		return (HttpServletResponse)JitarRequestContext.getRequestContext().getResponse();
	}

	public void addCookie(Cookie arg0) {
		resp().addCookie(arg0);
	}

	public void addDateHeader(String arg0, long arg1) {
		resp().addDateHeader(arg0, arg1);
	}

	public void addHeader(String arg0, String arg1) {
		resp().addHeader(arg0, arg1);
	}

	public void addIntHeader(String arg0, int arg1) {
		resp().addIntHeader(arg0, arg1);
	}

	public boolean containsHeader(String arg0) {
		return resp().containsHeader(arg0);
	}

	public String encodeRedirectURL(String arg0) {
		return resp().encodeRedirectURL(arg0);
	}

	public String encodeRedirectUrl(String arg0) {
		return resp().encodeRedirectUrl(arg0);
	}

	public String encodeURL(String arg0) {
		return resp().encodeURL(arg0);
	}

	public String encodeUrl(String arg0) {
		return resp().encodeUrl(arg0);
	}

	public void sendError(int arg0) throws IOException {
		resp().sendError(arg0);
	}

	public void sendError(int arg0, String arg1) throws IOException {
		resp().sendError(arg0, arg1);
	}

	public void sendRedirect(String arg0) throws IOException {
		resp().sendRedirect(arg0);
	}

	public void setDateHeader(String arg0, long arg1) {
		resp().setDateHeader(arg0, arg1);
	}

	public void setHeader(String arg0, String arg1) {
		resp().setHeader(arg0, arg1);
	}

	public void setIntHeader(String arg0, int arg1) {
		resp().setIntHeader(arg0, arg1);
	}

	public void setStatus(int arg0) {
		resp().setStatus(arg0);
	}

	public void setStatus(int arg0, String arg1) {
		resp().setStatus(arg0, arg1);
	}

	public void flushBuffer() throws IOException {
		resp().flushBuffer();
	}

	public int getBufferSize() {
		return resp().getBufferSize();
	}

	public String getCharacterEncoding() {
		return resp().getCharacterEncoding();
	}

	public String getContentType() {
		return resp().getContentType();
	}

	public Locale getLocale() {
		return resp().getLocale();
	}

	public ServletOutputStream getOutputStream() throws IOException {
		return resp().getOutputStream();
	}

	public PrintWriter getWriter() throws IOException {
		return resp().getWriter();
	}

	public boolean isCommitted() {
		return resp().isCommitted();
	}

	public void reset() {
		resp().reset();
	}

	public void resetBuffer() {
		resp().resetBuffer();
	}

	public void setBufferSize(int arg0) {
		resp().setBufferSize(arg0);
	}

	public void setCharacterEncoding(String arg0) {
		resp().setCharacterEncoding(arg0);
	}

	public void setContentLength(int arg0) {
		resp().setContentLength(arg0);
	}

	public void setContentType(String arg0) {
		resp().setContentType(arg0);
	}

	public void setLocale(Locale arg0) {
		resp().setLocale(arg0);
	}


	public int getStatus() {
		return resp().getStatus();
	}

	
	public String getHeader(String paramString) {
		return resp().getHeader(paramString);
	}


	public Collection<String> getHeaders(String paramString) {	
		return 	resp().getHeaders(paramString);
	}


	public Collection<String> getHeaderNames() {	
		return resp().getHeaderNames();
	}

	
}
