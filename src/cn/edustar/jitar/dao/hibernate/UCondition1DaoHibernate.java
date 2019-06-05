package cn.edustar.jitar.dao.hibernate;

import java.util.List;
import cn.edustar.jitar.pojos.UCondition1;
import cn.edustar.jitar.pojos.User;
import cn.edustar.jitar.dao.UCondition1Dao;
public class UCondition1DaoHibernate  extends BaseDaoHibernate implements UCondition1Dao {
	
	@SuppressWarnings("unchecked")
	public List<UCondition1> getUCondition1(){
		List<UCondition1> list = this.getSession().createQuery("FROM UCondition1 Order By conditiontype,score1").list();
		return list;
	}
	
	@SuppressWarnings("unchecked")
	public List<UCondition1> getUCondition1(Integer score1,Integer score2,Integer conditionType){
		List<UCondition1> list;
		int score=0;
		if(conditionType==0){
			if(score1>score2){
				score=score1;
				score1=score2;
				score2=score;
			}
		}
		if(conditionType==0)
			list = this.getSession().createQuery("FROM UCondition1 WHERE score1 >= ? AND score2 <= ? and conditionType=0 Order by score1").setInteger(0,score1).setInteger(1, score2).list();	
		else if(conditionType==1)
			list = this.getSession().createQuery("FROM UCondition1 WHERE score1 > and conditionType=1 ? Order by score1").setInteger(0,score1).list();	
		else if(conditionType==-1)
			list = this.getSession().createQuery("FROM UCondition1 WHERE score2 < ? and conditionType=-1 Order by score1").setInteger(0, score2).list();	
		else
			list = this.getSession().createQuery("FROM UCondition1 WHERE score1 >= ? AND score2 <= ? Order by score1").setInteger(0,score1).setInteger(1, score2).list();	
		return list;
	}
	
	@SuppressWarnings("unchecked")
	public boolean FindUser(int groupId,int userId){
		List<UCondition1> list= this.getSession().createQuery("FROM UCondition1 WHERE groupId = ?").setInteger(0, groupId).list();
		for(int i=0;i<list.size();i++){
			UCondition1 condition1=list.get(i);
			int score1=condition1.getScore1();
			int score2=condition1.getScore2();
			int conditionType=condition1.getConditionType();
			int score=0;
			if(conditionType==0){
				if(score1>score2){
					score=score1;
					score1=score2;
					score2=score;
				}
			}
			List<User> listuser=null;
			if(conditionType==0){
				listuser = this.getSession().createQuery("FROM User Where userId=? and UserScore >= ? and UserScore <= ?").setInteger(0, userId).setInteger(1, score1).setInteger(2, score2).list();
			}
			else if(conditionType==1){
				listuser = this.getSession().createQuery("FROM User Where userId=? and UserScore > ?").setInteger(0, userId).setInteger(1, score1).list();
			}
			else if(conditionType==-1){
				listuser = this.getSession().createQuery("FROM User Where userId=? and UserScore < ?").setInteger(0, userId).setInteger(1, score2).list();
			}
			if(listuser!=null){
				return true;
			}
		}
		return false;
	}
	public void Save(List<UCondition1> list){
		String queryString="DELETE FROM UCondition1";
		this.getSession().createQuery(queryString).executeUpdate();
		for(int i=0;i<list.size();i++){
			UCondition1 uCondition1=list.get(i);
			this.getSession().saveOrUpdate(uCondition1);
		}
	}
}
