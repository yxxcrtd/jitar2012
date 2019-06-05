package cn.edustar.jitar.dao;

import java.util.List;

import cn.edustar.data.Pager;
import cn.edustar.jitar.pojos.UPunishScore;
import cn.edustar.jitar.query.PunishQueryParam;

public interface UPunishScoreDao extends Dao {
	
	public UPunishScore getUPunishScore(int id);
	public UPunishScore getUPunishScore(int objType, int objId);
	public UPunishScore createUPunishScore(int objType, int objId,int userId,float s,String title,int createUserId,String createUserName);
	public UPunishScore createUPunishScore(int objType, int objId,int userId,float s,String title,String reason,int createUserId,String createUserName);
	public void saveUPunishScore(UPunishScore uPunishScore);
	public void deleteUPunishScore(UPunishScore uPunishScore);
	public List<UPunishScore> getUPunishScoreList(int userId);
	public List<UPunishScore> getUPunishScoreList(PunishQueryParam param, Pager pager);
	public float getScore(boolean punitive,int userId);
	public float getScore(boolean punitive,int objType,int userId);	
}