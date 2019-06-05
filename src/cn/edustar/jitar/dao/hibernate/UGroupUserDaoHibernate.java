package cn.edustar.jitar.dao.hibernate;
import java.util.List;

import cn.edustar.jitar.pojos.UGroup;
import cn.edustar.jitar.pojos.UGroupUser;
import cn.edustar.jitar.pojos.User;
import cn.edustar.jitar.dao.UGroupUserDao;

public class UGroupUserDaoHibernate  extends BaseDaoHibernate implements UGroupUserDao{
	
	@SuppressWarnings("unchecked")
	public List<UGroupUser> getUGroupUser(){
		List<UGroupUser> list = this.getSession().createQuery("FROM UGroupUser Order By groupUserId").list();
		return list;
		
	}

	@SuppressWarnings("unchecked")
	public List<UGroupUser> getUGroupUser(int groupId,int managed){
		List<UGroupUser> list = this.getSession().createQuery("FROM UGroupUser Where groupId=? and managed=1 Order By groupUserId").setInteger(0,groupId).list();
		return list;
		
	}
	
	@SuppressWarnings("unchecked")
	public List<UGroupUser> getUGroupUserByGroupId(int groupId){
		List<UGroupUser> list = this.getSession().createQuery("FROM UGroupUser Where groupId = ? Order By groupUserId").setInteger(0,groupId).list();;
		return list;
		
	}
	
	@SuppressWarnings("unchecked")
	public List<UGroupUser> getUGroupUserByUserId(int userId,int managed){
		List<UGroupUser> list = this.getSession().createQuery("FROM UGroupUser Where userId = ? and managed = ? Order By groupUserId").setInteger(0, userId).setInteger(1, managed).list();
		return list;
	}
	
	@SuppressWarnings("unchecked")
	public List<UGroupUser> getUGroupUserByUserId(int userId){
		List<UGroupUser> list = this.getSession().createQuery("FROM UGroupUser Where userId = ? Order By groupUserId").setInteger(0, userId).list();
		return list;
	}
	
	@SuppressWarnings("unchecked")
	public UGroupUser Find(int groupId,int userId){
		List<UGroupUser> list = this.getSession().createQuery("FROM UGroupUser Where groupId = ? and userId = ?").setInteger(0, groupId).setInteger(1, userId).list();
		if(list.size()==0 || list==null){
			return null;
		}
		else{
			return list.get(0);
		}
	}
	
	@SuppressWarnings("unchecked")
	public UGroupUser Find(int groupId,int userId,int managed){
		List<UGroupUser> list = this.getSession().createQuery("FROM UGroupUser Where groupId = ? and userId = ? and managed = ?").setInteger(0, groupId).setInteger(1, userId).setInteger(2, managed).list();
		if(list.size()==0 || list==null){
			return null;
		}
		else{
			return list.get(0);
		}
	}
	
	
	public Integer[] getGroupIdByUserId(int userId){
		List<UGroupUser> groupUsers = getUGroupUserByUserId(userId);
		if(groupUsers.size()==0 || groupUsers==null){
			return null;
		}
		Integer[] groupId=new Integer [groupUsers.size()];
		for(int i=0;i<groupUsers.size();i++){
			int gId=groupUsers.get(i).getGroupId();
			groupId[i]=gId;
		}
		return groupId;	
	}
	
	@SuppressWarnings("unchecked")
	public void InSertUser1(UGroup group,int score1,int score2,int conditionType){
		List<User> list=null;
		int score=0;
		if(conditionType==0){
			if(score1>score2){
				score=score1;
				score1=score2;
				score2=score;
			}
		}
		if(conditionType==0){
			list = this.getSession().createQuery("FROM User Where UserScore >= ? and UserScore < ?").setInteger(0,score1).setInteger(1,score2).list();
		}
		else if(conditionType==1){
			list = this.getSession().createQuery("FROM User Where UserScore >= ?").setInteger(0,score1).list();
		}
		else if(conditionType==-1){
			list = this.getSession().createQuery("FROM User Where UserScore < ?").setInteger(0,score2).list();
		}
		if(list!=null){
			for(int i=0;i<list.size();i++){
				User user=list.get(i);
				int userId=user.getUserId();
				UGroupUser ogu=Find(group.getGroupId(),userId);
				if(ogu==null){
					UGroupUser ugu=new UGroupUser(); 
					ugu.setUserId(userId);	
					ugu.setGroupId(group.getGroupId());
					ugu.setManaged(0);
					Save(ugu);
				}
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	public boolean isExistUser1(int userId,UGroup group,int score1,int score2,int conditionType){
		List<User> list=null;
		int score=0;
		if(conditionType==0){
			if(score1>score2){
				score=score1;
				score1=score2;
				score2=score;
			}
		}
		if(conditionType==0){
			list = this.getSession().createQuery("FROM User Where userId=? and UserScore >= ? and UserScore < ?").setInteger(0, userId).setInteger(1, score1).setInteger(2, score2).list();
		}
		else if(conditionType==1){
			list = this.getSession().createQuery("FROM User Where userId=? and UserScore >= ?").setInteger(0, userId).setInteger(1, score1).list();
		}
		else if(conditionType==-1){
			list = this.getSession().createQuery("FROM User Where userId=? and UserScore < ?").setInteger(0, userId).setInteger(1, score2).list();
		}
		if(list!=null){
			if(list.size()>0)
				return true;
			else
				return false;
		}
		else{
			return false;
		}
	}	
	@SuppressWarnings("unchecked")
	public void DeleteUser1(UGroup group,int score1,int score2,int conditionType){
		List<User> list=null;
		int score=0;
		if(conditionType==0){
			if(score1>score2){
				score=score1;
				score1=score2;
				score2=score;
			}
		}
		if(conditionType==0){
			list = this.getSession().createQuery("FROM User Where UserScore >= ? and UserScore <= ?").setInteger(0,score1).setInteger(1,score2).list();
		}
		else if(conditionType==1){
			list = this.getSession().createQuery("FROM User Where UserScore > ?").setInteger(0,score1).list();
		}
		else if(conditionType==-1){
			list = this.getSession().createQuery("FROM User Where UserScore < ?").setInteger(0,score2).list();
		}
		if(list!=null){
			for(int i=0;i<list.size();i++){
				User user=list.get(i);
				int userId=user.getUserId();
				UGroupUser ugu=new UGroupUser(); 
				ugu.setUserId(userId);	
				ugu.setGroupId(group.getGroupId());
				Delete(ugu);
			}
		}		
	}
	
	@SuppressWarnings("unchecked")
	public List<User> getUser(String hql){
		List<User> list= this.getSession().createQuery(hql).list();
		return list;
	}
	
		
	@SuppressWarnings("unchecked")
	public UGroupUser getGroupUserManaged(int groupId,int userId){
		String hql = "FROM UGroupUser WHERE groupId=? and userId = ? and managed=1";
		List<UGroupUser> list=this.getSession().createQuery(hql).setInteger(0, groupId).setInteger(1, userId).list();
		if(list==null){
			return null;
		}
		else{
			return list.get(0);
		}
	}
	public void Delete(UGroupUser uGroupUser){
		String hql = "DELETE FROM UGroupUser WHERE groupId=? and userId = ?";
		this.getSession().createQuery(hql).setInteger(0, uGroupUser.getGroupId()).setInteger(1, uGroupUser.getUserId()).executeUpdate();
	}
	
	public void DeleteGroupUser(int managed){
		String hql = "DELETE FROM UGroupUser WHERE managed=?";
		this.getSession().createQuery(hql).setInteger(0, managed).executeUpdate();
	}
	public void Delete(int groupId){
		String hql = "DELETE FROM UGroupUser WHERE groupId=?";
		this.getSession().createQuery(hql).setInteger(0, groupId).executeUpdate();
	}
	public void DeleteUser2(int userId){
		String hql = "DELETE FROM UGroupUser WHERE managed=0 and userId=?";
		this.getSession().createQuery(hql).setInteger(0, userId).executeUpdate();
	}
	
	public void Delete(int groupId,int userId){
		String hql = "DELETE FROM UGroupUser WHERE groupId=? and userId = ?";
		this.getSession().createQuery(hql).setInteger(0, groupId).setInteger(1, userId).executeUpdate();
	}
	
	public void Save(UGroupUser uGroupUser){
		//System.out.println("uGroupUser.getGroupId()="+uGroupUser.getGroupId());
		//System.out.println("uGroupUser.getUserId()="+uGroupUser.getUserId());
		//System.out.println("uGroupUser.getManaged()="+uGroupUser.getManaged());
		//System.out.println("uGroupUser.getGroupUserId()="+uGroupUser.getGroupUserId());
		
		//this.getSession().setFlushMode(getSession().FLUSH_EAGER);
		this.getSession().saveOrUpdate(uGroupUser);
		//this.getSession().flush();
		//this.getSession().saveOrUpdate(uGroupUser);
	}
}
