package cn.edustar.data;

/**
 * 元组对象（表示一对值的对象）
 *
 * @param <K>
 * @param <V>
 */
public class Tuple<K, V> {
	private K k;
	private V v;
	
	/**
	 * 构造
	 */
	public Tuple() {
		// 
	}
	
	/**
	 * 构造
	 */
	public Tuple(K k, V v) {
		this.k = k;
		this.v = v;
	}

	/**
	 * 获得 Key
	 * 
	 * @return
	 */
	public K getKey() {
		return k;
	}
	
	/**
	 * 设置 Key
	 * 
	 * @param k
	 */
	public void setKey(K k) {
		this.k = k;
	}
	
	/**
	 * 获得 Value
	 * 
	 * @return
	 */
	public V getValue() {
		return v;
	}
	
	/**
	 * 设置 Value
	 * 
	 * @param v
	 */
	public void setValue(V v) {
		this.v = v;
	}
	
}
