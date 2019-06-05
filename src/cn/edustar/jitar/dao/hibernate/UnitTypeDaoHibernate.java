package cn.edustar.jitar.dao.hibernate;

import java.util.List;
import cn.edustar.jitar.dao.UnitTypeDao;
import cn.edustar.jitar.pojos.UnitType;

/**
 * 
 * 实现单位分类的DAO
 * 
 * @author baimindong
 *
 */
public class UnitTypeDaoHibernate extends BaseDaoHibernate implements UnitTypeDao{
	/***
	 * 保存一个单位类型.
	 * @param type
	 */
	public void saveUnitType(UnitType type){
		this.getSession().saveOrUpdate(type);
	}

	/**
	 *得到全部的单位分类 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<UnitType> getUnitTypeList(){
		String hql = "FROM UnitType ORDER BY orderNo";
		return (List<UnitType>)this.getSession().createQuery(hql).list();
	}
	/**
	 *得到全部的单位分类名称
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<String> getUnitTypeNameList(){
		String hql = "SELECT unitTypeName FROM UnitType ORDER BY orderNo";
		return (List<String>)this.getSession().createQuery(hql).list();	
	}
	
	/**
	 * 通过标识Id得到单位分类.
	 * @param Id
	 * @return
	 */
	public UnitType getUnitTypeById(int Id){
		return (UnitType)this.getSession().get(UnitType.class, Id);
	}
	
	/**
	 * 通过标识guid得到单位分类.
	 * @param guid
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public UnitType getUnitTypeByGuid(String guid){
		String hql = "FROM UnitType WHERE unitTypeGuid = ? ORDER BY orderNo";
		List<UnitType> list= this.getSession().createQuery(hql).setString(0, guid).list();
		if(null==list || list.size()==0){
			return null;
		}
		else{
			return list.get(0);
		}
	}
	
	/***
	 * 删除单位分类.
	 * @param type
	 */
	public void deleteUnitType(UnitType type){
		this.getSession().delete(type);
	}

}
