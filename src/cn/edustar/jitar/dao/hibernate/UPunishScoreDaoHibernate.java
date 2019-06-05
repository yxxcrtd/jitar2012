package cn.edustar.jitar.dao.hibernate;

import java.util.List;

import cn.edustar.data.Pager;
import cn.edustar.data.hql.QueryHelper;
import cn.edustar.jitar.dao.UPunishScoreDao;
import cn.edustar.jitar.pojos.UPunishScore;
import cn.edustar.jitar.query.PunishQueryParam;

public class UPunishScoreDaoHibernate  extends BaseDaoHibernate implements UPunishScoreDao{

	public UPunishScore getUPunishScore(int id)
	{
		String hql = "FROM UPunishScore WHERE id = ?";
		return (UPunishScore)getSession().createQuery(hql).setInteger(0, id).uniqueResult();
		
	}
	public UPunishScore getUPunishScore(int objType, int objId)
	{
		String hql = "FROM UPunishScore WHERE objType=? and objId = ?";
		return (UPunishScore)getSession().createQuery(hql).setInteger(0, objType).setInteger(1, objId).uniqueResult();
	}
	public UPunishScore createUPunishScore(int objType, int objId,int userId,float score,String title,int createUserId,String createUserName)
	{
		UPunishScore upun=getUPunishScore(objType,objId);
		if(upun==null)
		{
			upun=new UPunishScore();
			upun.setObjType(objType);
			upun.setObjId(objId);
			upun.setUserId(userId);
			upun.setScore(score);
			upun.setObjTitle(title);
			upun.setCreateUserId(createUserId);
			upun.setCreateUserName(createUserName);
		}else{
			upun.setUserId(userId);
			upun.setScore(score);
			upun.setObjTitle(title);
			upun.setCreateUserId(createUserId);
			upun.setCreateUserName(createUserName);
		}
		return upun;
	}
	public UPunishScore createUPunishScore(int objType, int objId,int userId,float score,String title,String reason,int createUserId,String createUserName)
	{
		UPunishScore upun=getUPunishScore(objType,objId);
		if(upun==null)
		{
			upun=new UPunishScore();
			upun.setObjType(objType);
			upun.setObjId(objId);
			upun.setUserId(userId);
			upun.setScore(score);
			upun.setObjTitle(title);
			upun.setReason(reason);
			upun.setCreateUserId(createUserId);
			upun.setCreateUserName(createUserName);
		}else{
			upun.setUserId(userId);
			upun.setScore(score);
			upun.setObjTitle(title);
			upun.setReason(reason);
			upun.setCreateUserId(createUserId);
			upun.setCreateUserName(createUserName);
		}
		return upun;
	}
	
	public void saveUPunishScore(UPunishScore uPunishScore)
	{
		getSession().save(uPunishScore);
		getSession().flush();
		
	}
	public void deleteUPunishScore(UPunishScore uPunishScore)
	{
		getSession().delete(uPunishScore);
		getSession().flush();
	}
	
	/**
	 * punitive=true 得到罚分   punitive=false 得到加分
	 */
	public float getScore(boolean punitive,int userId){
		String hql;
		if(punitive){
			hql = "SELECT SUM(score) FROM UPunishScore WHERE userId=? AND score>0";
		}else{
			hql = "SELECT SUM(score) FROM UPunishScore WHERE userId=? AND score<0";
		}
		Object result = getSession().createQuery(hql).setInteger(0,userId).uniqueResult();
		if (result == null){
			return 0f;
		}
		else{
			float f=Float.valueOf(result.toString()).floatValue();
			if(f<0){
				f=-1*f;
			}
			return f;
		}
	}
	/**
	 * punitive=true 得到罚分   punitive=false 得到加分
	 */	
	public float getScore(boolean punitive,int objType,int userId){
		String hql;
		if(punitive){
			hql = "SELECT SUM(score) FROM UPunishScore WHERE userId=? AND objType=? AND score>0";
		}else{
			hql = "SELECT SUM(score) FROM UPunishScore WHERE userId=? AND objType=? AND score<0";
		}
		Object result = getSession().createQuery(hql).setInteger(0,userId).setInteger(1,objType).uniqueResult();
		if (result == null){
			return 0f;
		}
		else{
			float f=Float.valueOf(result.toString()).floatValue();
			if(f<0){
				f=-1*f;
			}
			return f;
		}		
	}
	@SuppressWarnings("unchecked")
	public List<UPunishScore> getUPunishScoreList(int userId)
	{
		String hql = "FROM UPunishScore WHERE userId=?";
		List<UPunishScore> result = getSession().createQuery(hql).setInteger(0,userId).list();
		if (result == null || result.size() == 0)
			return null;
		else
			return result;
		
	}
	@SuppressWarnings("unchecked")
	public List<UPunishScore> getUPunishScoreList(PunishQueryParam param, Pager pager)
	{
		QueryHelper query = param.createQuery();
		if (pager == null) {
			return query.queryData(getSession(), -1, param.count);
		} else {
			return query.queryDataAndTotalCount(getSession(), pager);
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
