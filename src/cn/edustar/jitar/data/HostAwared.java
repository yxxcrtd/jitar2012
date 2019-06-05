package cn.edustar.jitar.data;

/**
 * 需要 host 环境的 DataBean 实现此接口.
 *
 *
 */
public interface HostAwared extends DataBean {
	/**
	 * 设置此数据 bean 的数据环境.
	 * @param host - 数据环境对象.
	 */
	public void setDataHost(DataHost host);
}
