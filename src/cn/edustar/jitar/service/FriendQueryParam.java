package cn.edustar.jitar.service;

/**
 * 查询好友信息时候的条件
 * 
 *
 */
public class FriendQueryParam {
	/** 要查询的用户标识，必须给出。 */
	public int userId;

	/** 是否查询黑名单， = null 表示不区分，= true 表示查询黑名单的，= false 表示查询非黑名单的。 */
	public Boolean isBlack = null;

	/** 是否限定 typeId 条件。 */
	public boolean useTypeId = false;

	/** 查询的分类标识， = null 表示未分类。 */
	public Integer typeId = null;

	/** 排序方式。 0 - 缺省 id 逆序排列。 */
	public int orderType;
}
