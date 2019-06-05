package cn.edustar.jitar.service;

import java.util.List;
import cn.edustar.jitar.pojos.UnitType;

/**
 * 单位类型的服务接口 
 * @author dell
 *
 */
public interface UnitTypeService {
	/***
	 * 保存一个单位类型.
	 * @param type
	 */
	public void saveUnitType(UnitType type);

	/**
	 *得到全部的单位分类 
	 * @return
	 */
	public List<UnitType> getUnitTypeList(); 
	/**
	 *得到全部的单位分类名称
	 * @return
	 */
	public List<String> getUnitTypeNameList(); 
	
	/**
	 * 通过标识Id得到单位分类.
	 * @param Id
	 * @return
	 */
	public UnitType getUnitTypeById(int Id);
	
	/**
	 * 通过标识guid得到单位分类.
	 * @param guid
	 * @return
	 */
	public UnitType getUnitTypeByGuid(String guid);
	
	/***
	 * 删除单位分类.
	 * @param type
	 */
	public void deleteUnitType(UnitType type);
}
