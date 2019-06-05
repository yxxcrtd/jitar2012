package cn.edustar.jitar.pojos;

import java.util.Date;

/**
 * 用来帮助建立 hashCode, equals, toString 方法。
 * 
 *
 */
public class PojoHelper {
	
	/**
	 * 安全的为一个对象产生 hashCode.
	 * 
	 * @param a
	 * @return
	 */
	public static int hashCode(Object a) {
		if (a == null)
			return 0;
		return a.hashCode();
	}

	/**
	 * 为两个整数产生 hashCode
	 * 
	 * @param a 整数 a
	 * @param b 整数 b
	 * @return
	 */
	public static final int hashCode(int a, int b) {
		return ((a * 1987) << 3) + b;
	}

	/**
	 * 安全的为 2 个对象产生 hashCode.
	 * 
	 * @param a
	 * @param b
	 * @return
	 */
	public static int hashCode(Object a, Object b) {
		return hashCode(a) + hashCode(b);
	}

	/**
	 * 安全的为 3 个对象产生 hashCode.
	 * 
	 * @param a
	 * @param b
	 * @param c
	 * @return
	 */
	public static int hashCode(Object a, Object b, Object c) {
		return hashCode(a) + hashCode(b) + hashCode(c);
	}

	/**
	 * 安全的为多个对象产生 hashCode.
	 * 
	 * @param objects
	 * @return
	 */
	public static int hashCode(Object... objects) {
		if (objects == null)
			return 0;
		int result = 0;
		for (int i = 0; i < objects.length; ++i)
			result += hashCode(objects[i]);
		return result;
	}

	/**
	 * 比较两个字符串是否相等。
	 * 
	 * @param a
	 * @param b
	 * @return
	 */
	public static boolean equals(String a, String b) {
		if (a == null) {
			return (b == null);
		} else {
			return a.equals(b);
		}
	}

	/**
	 * 比较两个数字是否相等。
	 * 
	 * @param a
	 * @param b
	 * @return
	 */
	public static boolean equals(int a, int b) {
		return a == b;
	}

	/**
	 * 比较两个日期是否相等。
	 * 
	 * @param a
	 * @param b
	 * @return
	 */
	public static boolean equals(Date a, Date b) {
		if (a == null)
			return b == null;
		else
			return a.equals(b);
	}

	/**
	 * 比较两个对象是否相等。
	 * 
	 * @param a
	 * @param b
	 * @return
	 */
	public static boolean equals(Object a, Object b) {
		if (a == null)
			return b == null;
		else
			return a.equals(b);
	}
}
