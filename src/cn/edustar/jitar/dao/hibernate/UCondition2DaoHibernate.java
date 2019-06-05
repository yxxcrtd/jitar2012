package cn.edustar.jitar.dao.hibernate;

import java.util.List;
import cn.edustar.jitar.dao.UCondition2Dao;
import cn.edustar.jitar.pojos.UCondition2;
import cn.edustar.jitar.pojos.User;

public class UCondition2DaoHibernate  extends BaseDaoHibernate implements UCondition2Dao {
	
	@SuppressWarnings("unchecked")
	public List<UCondition2> getUCondition2(){
		List<UCondition2> list = this.getSession().createQuery("FROM UCondition2").list();
		return list;
	}
	
	@SuppressWarnings("unchecked")
	public UCondition2 getUCondition2(String teacherTypeKeyword){
		List<UCondition2> list = this.getSession().createQuery("FROM UCondition2 WHERE teacherTypeKeyword = ?").setString(0, teacherTypeKeyword).list();
		if (list == null || list.size() == 0)
			return null;
		
		return (UCondition2) list.get(0);
		
	}
	
	public void Save(UCondition2 uCondition2){
		getSession().saveOrUpdate(uCondition2);
	}
	
	@SuppressWarnings("unchecked")
	public boolean FindUser(int groupId,int userId){
		List<UCondition2> list= this.getSession().createQuery("FROM UCondition2 WHERE groupId = ?").setInteger(0, groupId).list();
		for(int i=0;i<list.size();i++){
			UCondition2 condition2=list.get(i);
			String hql=condition2.getSqlCondition();
			if(hql.indexOf(" User ")>0){
				List<User> users=this.getSession().createQuery(hql).list();
				for(int j=0;j<users.size();j++){
					User user=users.get(j);
					if(user.getUserId()==userId){
						return true;
					}
				}
			}
					
		}
		return false;
	}
}
