/*
 * $Id: JSONArray.java,v 1.1 2006/04/15 14:10:48 platform Exp $
 * Created on 2006-4-10
 */
package cn.edustar.jitar.util.json;

import java.util.ArrayList;
import java.util.Iterator;


/**
 * 对 ArrayList 的一个派生，重载了 toString() 方法。 */
public class JSONArray<E> extends ArrayList<E> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5525137567439692475L;

	/*
	 * (non-Javadoc)
	 * @see java.util.AbstractCollection#toString()
	 */
	@SuppressWarnings("unchecked")
	public String toString(){
		ItemList list = new ItemList();
		
		Iterator iter = iterator();
		
		while(iter.hasNext()){
			Object value = iter.next();
			if(value instanceof String){
				list.add("\"" + JSONObject.escape((String)value) + "\"");
			}
			else
				list.add(String.valueOf(value));
		}
		return "[" + list.toString() + "]";
	}
}
