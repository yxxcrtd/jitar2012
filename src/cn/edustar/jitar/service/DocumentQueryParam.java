package cn.edustar.jitar.service;

/**
 * Article, Photo, Resource QueryParam 基类.
 *
 *
 */
public abstract class DocumentQueryParam {
	/** 如果给出 userId, 则表示查询该用户的文章；否则不区分用户。缺省 = null */
	public Integer userId = null;

	/** 系统分类id，缺省为 null 则表示不限定系统分类。 */
	public Integer sysCateId = null;
	
	/** 用户分类id, 缺省为 null 表示不限定用户分类，通常同时设定 userId 参数。 */
	public Integer userCateId = null;
	
	/** 查询审核状态，== null 表示不区分，缺省 = 0 查询审核通过的。 */
	public Integer auditState = 0;

	
	/** (请使用ORDER_TYPE_XXX 常量) 排序方法，0 按照id desc, 为1按发表时间，为2按点击数，为3按回复数。缺省 = 1
	 * (派生类可能定义更多排序方法.) */
	public int orderType = 0;
	
	public static final int ORDER_TYPE_ID_DESC = 0;
	/** 排序方法，为1按发表时间 逆序排列。 */
	public static final int ORDER_TYPE_CREATEDATE_DESC = 1;
	/** 排序方法，为2按点击数 逆序排列。 */
	public static final int ORDER_TYPE_VIEWCOUNT_DESC = 2;
	/** 排序方法，为3按回复数 逆序排列。 */
	public static final int ORDER_TYPE_COMMENTS_DESC = 3;
}
