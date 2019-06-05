package cn.edustar.jitar.action;

/**
 * 为了兼容 Struts Action, Python Action 两者必须实现的相同的功能接口
 * 
 * @author Yang XinXin
 */
public interface ActionInterface {

	/**
	 * 给Action添加一个错误信息
	 * 
	 * @param error
	 */
	public void addActionError(String error);

}
