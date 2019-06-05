package cn.edustar.jitar.service;

/**
 * 留言查询参数.
 * 
 * @author Yang Xinxin
 * @version 1.0.0 Mar 31, 2008 10:58:03 AM
 */
public class LeavewordQueryParam {
	/** 获取多少条留言, 仅当不使用 pager 参数的时候有效, 缺省 = 20 */
	public int count = 20;
	
	/** 发表留言的人标识, <b>缺省</b> = null 表示不限定 */
	public Integer userId = null;

	/** 被留言的对象类型, 缺省 == null 表示不限定 */
	public Integer objType = null;
	
	/** 被留言的对象标识, 缺省 == null 表示不限定 */
	public Integer objId = null;
}
