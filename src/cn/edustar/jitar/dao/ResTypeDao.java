package cn.edustar.jitar.dao;

import java.util.List;

import cn.edustar.jitar.pojos.ResType;

/**
 * 资源类型数据库访问接口
 * 
 *
 */
public interface ResTypeDao {

	// property constants
	public static final String TC_TITLE = "tcTitle";
	public static final String TC_CODE = "tcCode";
	public static final String TC_PARENT = "tcParent";
	public static final String TC_SORT = "tcSort";
	public static final String TC_FLAG = "tcFlag";
	public static final String TC_COMMENTS = "tcComments";

	/**
	 * 得到指定标识的资源类型.
	 * @param resTypeId资源类型标识.
	 * @return 如果分类存在则返回 JtrResType 对象，否则返回 null.
	 */
	public ResType getResType(int resTypeId);
	
	/**
	 * 删除指定标识的资源类型.
	 * @param resTypeId
	 */
	public abstract void delResType(ResType jtrResType);
	
	/**
	 * 创建一个新的资源类型.
	 * @param category - 要创建的资源类型.
	 */
	public void createResType(ResType jtrResType);
	
	/**
	 * 得到指定分类的子分类。按照其 orderNum 排序.
	 * @param parent - 父分类标识.
	 * @return 返回子分类集合.
	 */
	public List<ResType> getChildResTypes(Integer parent);
	
	/**
	 * 取得常用的资源类型
	 * @return
	 */
	public List<ResType> getResTypes();
	
	/**
	 * 用来检查相同级别是否有相同的分类
	 */ 
	public ResType getResTypeByNameAndParentId(String resName, Integer parentId);

}
