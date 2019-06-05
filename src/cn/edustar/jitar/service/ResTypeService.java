package cn.edustar.jitar.service;

import java.util.List;

import cn.edustar.jitar.pojos.ResType;

/**
 * 资源类型服务接口定义.
 *
 *
 */
public interface ResTypeService {

	/**
	 * 得到指定标识的资源类型.
	 * @param resTypeId资源类型标识.
	 * @return 如果分类存在则返回 JtrResType 对象，否则返回 null.
	 */
	public abstract ResType getResType(int resTypeId);
	
	
	/**
	 * 删除指定标识的资源类型.
	 * @param resTypeId
	 */
	public abstract void delResType(ResType resType);

	/**
	 * 创建一个新的资源类型.
	 * @param category - 要创建的资源类型.
	 */
	public abstract void createResType(ResType resType);

	/**
	 * 得到指定分类的子分类。按照其 orderNum 排序。
	 * @param parent - 父分类标识。
	 * @return 返回子分类集合。
	 */
	public abstract List<ResType> getChildResTypes(Integer parentId);
	
	/**
	 * 取得常用的资源类型
	 * @return
	 */
	public abstract List<ResType> getResTypes();
	
	/**
	 * 用来检查相同级别是否有相同的分类
	 */ 
	public ResType getResTypeByNameAndParentId(String resName, Integer parentId);
}
