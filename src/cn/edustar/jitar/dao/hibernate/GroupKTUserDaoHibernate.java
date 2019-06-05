package cn.edustar.jitar.dao.hibernate;


import java.util.List;

import cn.edustar.jitar.pojos.GroupKTUser;
import cn.edustar.jitar.dao.GroupKTUserDao;

public class GroupKTUserDaoHibernate extends BaseDaoHibernate implements GroupKTUserDao {

	public GroupKTUser GetGroupKTUser(int id)
	{
		String hql = "FROM GroupKTUser WHERE id = ?";
		List list = this.getSession().createQuery(hql).setInteger(0, id).list();
		if (list == null || list.size() == 0)
			return null;
		return (GroupKTUser) list.get(0);		
	}
	
	public GroupKTUser GetGroupKTUser(int groupId,int userId)
	{
		String hql = "FROM GroupKTUser WHERE groupId = ? And teacherId = ? ";
		List list = this.getSession().createQuery(hql).setInteger(0, groupId).setInteger(1, userId).list();
		if (list == null || list.size() == 0)
			return null;
		return (GroupKTUser) list.get(0);		
	}	
	
	@SuppressWarnings("unchecked")
	public List<GroupKTUser> GetGroupKTUsers(int groupId)
	{
		String hql = "FROM GroupKTUser WHERE groupId = ?";
		return this.getSession().createQuery(hql).setInteger(0, groupId).list();
	}
	
	public void CreateGroupKTUser(GroupKTUser ktUser)
	{
		this.getSession().save(ktUser);
	}
	public void UpdateGroupKTUser(GroupKTUser ktUser){
		this.getSession().saveOrUpdate(ktUser);
	}
	public void DeleteGroupKTUser(GroupKTUser ktUser){
		//将负责人角色设置为普通成员
		SetGroupMember(ktUser);
		//删除负责人
		
		getSession().delete(ktUser);
		getSession().flush();
	}
	private void SetGroupMember(GroupKTUser ktUser){
		//将负责人角色设置为普通成员
		if(ktUser==null){return ;}
		String hql = "UPDATE GroupMember SET groupRole=0 WHERE groupId = ? AND userId= ? ";
		this.getSession().createQuery(hql).setInteger(0,  ktUser.getGroupId()).setInteger(1, ktUser.getTeacherId()).executeUpdate();
	}
	public void DeleteGroupKTUser(int id){
		GroupKTUser ktUser=GetGroupKTUser(id);
		SetGroupMember(ktUser);
		
		String hql = "DELETE GroupKTUser WHERE id = ?";
		this.getSession().createQuery(hql).setInteger(0,id).executeUpdate();
	}
	
	public void DeleteGroupKTUsers(int groupId){
		List<GroupKTUser> ktUsers= GetGroupKTUsers(groupId);
		for(GroupKTUser user : ktUsers){
			SetGroupMember(user);
		}
		String hql = "DELETE GroupKTUser WHERE groupId = ?";
		this.getSession().createQuery(hql).setInteger(0, groupId).executeUpdate();
	}

	@Override
	public void evict(Object object) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void flush() {
		// TODO Auto-generated method stub
		
	}
}
