package cn.edustar.jitar.util;

import org.apache.commons.httpclient.methods.PostMethod;

/**
 * @author yxx
 */
public class UTF8PostMethod extends PostMethod {
	public UTF8PostMethod(String url) {
		super(url);
	}

	@Override
	public String getRequestCharSet() {
		// return super.getRequestCharSet();
		return "UTF-8";
	}
	
}

