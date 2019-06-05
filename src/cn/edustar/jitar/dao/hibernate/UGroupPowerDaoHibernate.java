package cn.edustar.jitar.dao.hibernate;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import cn.edustar.jitar.pojos.UGroupPower;
import cn.edustar.jitar.dao.UGroupPowerDao;

public class UGroupPowerDaoHibernate extends BaseDaoHibernate implements UGroupPowerDao{
	
	@SuppressWarnings("unchecked")
	public UGroupPower getUGroupPower(int groupId){
		List<UGroupPower> list=this.getSession().createQuery("FROM UGroupPower WHERE groupId = ?").setInteger(0, groupId).list();
		if(list==null || list.size()==0){
			return null;
		}
		else {
			return list.get(0);
		}
	}
	
	@SuppressWarnings("unchecked")
	public boolean getAllowVideoConferenceCreate(Integer[] groupId){
		if(groupId==null){
			return false;
		}
		Query query= this.getSession().createQuery("FROM UGroupPower WHERE videoConference=1 and groupId in (:para)");
		query.setParameterList("para",groupId);
		List<UGroupPower> list1=query.list();
		if(list1!=null){
			if(list1.size()>0){
				return true;
			}
		}
		return false;
	}
	
	@SuppressWarnings("unchecked")
	public int getMaxUploadArticleNum(Integer[] groupId){
		if(groupId==null){
			return 0;
		}
		Session session=this.getSession();
		Query query= session.createQuery("FROM UGroupPower WHERE uploadArticleNum=-1 and groupId in (:para)");
		query.setParameterList("para",groupId);
		List<UGroupPower> list1=query.list();
		if(list1!=null){
			if(list1.size()>0){
				return -1;
			}
		}
		query= session.createQuery("SELECT MAX(uploadArticleNum) FROM UGroupPower WHERE groupId in (:para)");
		query.setParameterList("para",groupId);
		//List<Integer> list=this.getSession().find("SELECT MAX(uploadArticleNum) FROM UGroupPower WHERE groupId in (:para)",sGroupId);
		
		List<Integer> list=query.list();
		if( list==null || list.size()==0 ){
			return 0;
		}
		else{
			return list.get(0) == null?0:list.get(0);
		}
	}
	
	@SuppressWarnings("unchecked")
	public int getMaxUploadResourceNum(Integer[] groupId){
		if(groupId==null){
			return 0;
		}
		Session session=this.getSession();

		Query query= session.createQuery("FROM UGroupPower WHERE uploadResourceNum=-1 and groupId in (:para)");
		query.setParameterList("para",groupId);
		List<UGroupPower> list1=query.list();
		if(list1!=null){
			if(list1.size()>0){
				return -1;
			}
		}
		
		query= session.createQuery("SELECT MAX(uploadResourceNum) FROM UGroupPower WHERE groupId in (:para)");
		query.setParameterList("para",groupId);
		List<Integer> list=query.list();
		//List<Integer> list=this.getSession().find("SELECT MAX(uploadResourceNum) FROM UGroupPower WHERE groupId in (?)",sGroupId);
		if(list==null || list.size()==0 ){
			return 0;
		}
		else{
			return list.get(0) == null?0:list.get(0);
		}		
	}
	
	@SuppressWarnings("unchecked")
	public int getMaxUploadDiskNum(Integer[] groupId){
		if(groupId==null){
			return 0;
		}
		Session session=this.getSession();
		Query query= session.createQuery("SELECT MAX(uploadDiskNum) FROM UGroupPower WHERE groupId in (:para)");
		query.setParameterList("para",groupId);
		List<Integer> list=query.list();
		//List<Integer> list=this.getSession().find("SELECT MAX(uploadDiskNum) FROM UGroupPower WHERE groupId in (?)",sGroupId);
		if( list==null || list.size()==0 ){
			return 0;
		}
		else{
			return list.get(0)==null?0:list.get(0);
		}			
	}
	public void Save(UGroupPower uGroupPower){
		this.getSession().saveOrUpdate(uGroupPower);
	}
	public void Delete(int groupId){
		String queryString = "DELETE FROM UGroupPower WHERE groupId=?";
		this.getSession().createQuery(queryString).setInteger(0, groupId).executeUpdate();
	}
	
	public void Delete(UGroupPower uGroupPower){
		this.getSession().delete(uGroupPower);
	}
}
