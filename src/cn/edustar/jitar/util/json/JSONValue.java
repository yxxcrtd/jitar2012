/*
 * $Id: JSONValue.java,v 1.1 2006/04/15 14:37:04 platform Exp $
 * Created on 2006-4-15
 */
package cn.edustar.jitar.util.json;

import java.io.Reader;
import java.io.StringReader;

import cn.edustar.jitar.util.json.parser.JSONParser;

public class JSONValue {
	/**
	 * parse into java object from input source.
	 * @param in
	 * @return instance of : JSONObject,JSONArray,String,Boolean,Long,Double or null
	 */
	public static Object parse(Reader in){
		try{
			JSONParser parser=new JSONParser();
			return parser.parse(in);
		}
		catch(Exception e){
			return null;
		}
	}
	
	public static Object parse(String s){
		StringReader in=new StringReader(s);
		return parse(in);
	}
}
