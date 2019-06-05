package cn.edustar.jitar.dao.hibernate;
import java.util.List;

import cn.edustar.jitar.pojos.UGroup;
import cn.edustar.jitar.dao.UGroupDao;
public class UGroupDaoHibernate  extends BaseDaoHibernate implements UGroupDao{
	@SuppressWarnings("unchecked")
	public UGroup getUGroup(int groupId){
		List<UGroup> list = this.getSession().createQuery("FROM UGroup WHERE groupId = ?").setInteger(0, groupId).list();
		if (list == null || list.size() == 0)
			return null;
		return (UGroup) list.get(0);
	}
	
	@SuppressWarnings("unchecked")
	public UGroup getUGroup(String groupName){
		List<UGroup> list = this.getSession().createQuery("FROM UGroup WHERE groupName = ?").setString(0, groupName).list();
		if (list == null || list.size() == 0)
			return null;
		return (UGroup) list.get(0);
	}
	@SuppressWarnings("unchecked")
	public List<UGroup> getUGroups(){
		List<UGroup> list = this.getSession().createQuery("FROM UGroup").list();
		return list;
	}
	
	public void Save(UGroup uGroup){
		this.getSession().saveOrUpdate(uGroup);
	}
	public void Delete(UGroup uGroup){
		this.getSession().delete(uGroup);
	}
	
}
