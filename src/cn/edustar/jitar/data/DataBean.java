package cn.edustar.jitar.data;

/**
 * 数据提供对象必须要实现的接口.
 *
 *
 */
public interface DataBean {
	// 旧接口.
	public void prepareData(DataHost host);
	
	/**
	 * 获取此 bean 的数据.
	 */
	public Object open();
}
