package cn.edustar.jitar.dao.hibernate;

import java.util.List;

import cn.edustar.jitar.dao.MeetingsDao;
import cn.edustar.jitar.pojos.Meetings;

@SuppressWarnings("unchecked")
public class MeetingsDaoHibernate extends BaseDaoHibernate implements MeetingsDao {

	@Deprecated
	public Meetings getMeetingsByObjAndObjId(String obj, int objId) {
		List<Meetings> list = this.getSession().createQuery("FROM Meetings WHERE obj = ? AND objId = ?").setString(0, obj).setInteger(1, objId).list();
		return (Meetings) ((null != list && list.size() > 0) ? list.get(0) : null);
	}
	
	public void saveMeetings(Meetings meetings) {
		this.getSession().save(meetings);
	}
	
	public void delMeetings(Meetings meetings) {
		this.getSession().delete(meetings);
	}

	public List<Meetings> getMeetings(String obj, int objId){
		List<Meetings> list = this.getSession().createQuery("FROM Meetings WHERE obj = ? AND objId = ? Order By id").setString(0, obj).setInteger(1, objId).list();
		return list;
	}
	
	public List<Meetings> getUnfinishedMeetings(){
		List<Meetings> list = this.getSession().createQuery("FROM Meetings WHERE status<>2 Order By id").list();
		return list;
	}
	
	public List<Meetings> getMeetings()	{
		List<Meetings> list = this.getSession().createQuery("FROM Meetings Order By id").list();
		return list;
	}
	
	public Meetings getMeetings(int id){
		String hql = "FROM Meetings WHERE id = :id";
		List<Meetings> ms = this.getSession().createQuery(hql).setInteger("id", id).list();
		if(ms==null || ms.size() == 0) return null;
		return (Meetings)ms.get(0);
	}
	
	public void addMeetings(Meetings m){
		this.getSession().save(m);
	}
	
	public void updateMeetings(Meetings m){
		this.getSession().update(m);
	}
	
	public void deleteMeetings(int id){
		Meetings m = getMeetings(id);
		if(null != m){
			this.getSession().delete(m);
		}
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
