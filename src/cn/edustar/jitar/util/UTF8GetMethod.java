package cn.edustar.jitar.util;

import org.apache.commons.httpclient.methods.GetMethod;

public class UTF8GetMethod extends GetMethod{
	public UTF8GetMethod(String url){
		super(url);
	}
	@Override
	public String getRequestCharSet() {
		// return super.getRequestCharSet();
		return "UTF-8";
	}	
}
