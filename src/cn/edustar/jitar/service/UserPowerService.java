package cn.edustar.jitar.service;

import java.util.List;

import cn.edustar.jitar.pojos.UCondition1;
import cn.edustar.jitar.pojos.UCondition2;
import cn.edustar.jitar.pojos.UGroup;
import cn.edustar.jitar.pojos.UGroupPower;
import cn.edustar.jitar.pojos.UGroupUser;

public interface UserPowerService {
	/**
	 * 用户每天上载文章的数量限制
	 * @param userId:用户ID
	 * @return:-1 表示无限制 0 表示不允许上载
	 */
	public int getUploadArticleNum(int userId);
	/**
	 * 用户每天上载资源的数量限制
	 * @param userId:用户ID
	 * @return:-1 表示无限制 0 表示不允许上载
	 */
	public int getUploadResourceNum(int userId);
	/**
	 * 用户上载空间的限制
	 * @param userId:用户ID
	 * @return
	 */
	public int getUploadDiskNum(int userId,boolean initgroupuser);
	
	/**
	 * 今天是否允许上载文章
	 * @param userId
	 * @return
	 */
	public boolean AllowUploadArticle(int userId);
	public boolean AllowUploadArticle(int userId,int maxNum);
	public boolean AllowUploadArticle(int userId,int maxNum,int todayNum);
	/**
	 * 今天是否允许上载资源
	 * @param userId
	 * @return
	 */
	public boolean AllowUploadResource(int userId);
	public boolean AllowUploadResource(int userId,int maxNum);
	public boolean AllowUploadResource(int userId,int maxNum,int todayNum);
	
	/**
	 * 是否允许创建视频会议
	 * @param userId
	 * @return
	 */
	public boolean AllowVideoConferenceCreate(int userId);
	/**
	 * 今天发表的文章数量
	 * @param userId
	 * @return
	 */
	public int getTodayArticles(int userId);
	/**
	 * 今天上载的资源数量
	 * @param userId
	 * @return
	 */
	public int getTodayResources(int userId);
	
	
	public List<UCondition1> getUCondition1();
	public List<UCondition1> getUCondition1(Integer score1,Integer score2,Integer conditionType);
	public void SaveUCondition1(List<UCondition1> list);
	
	public List<UCondition2> getUCondition2();
	public UCondition2 getUCondition2(String teacherTypeKeyword);
	public void SaveUCondition2(UCondition2 uCondition2);
	
	public UGroup getUGroup(int groupId);
	public List<UGroup> getUGroups();
	public void DeleteGroup(UGroup uGroup);
	
	public List<UGroupUser> getUGroupUser();
	public List<UGroupUser> getUGroupUser(int groupId,int managed);
	public List<UGroupUser> getUGroupUserByGroupId(int groupId);
	public List<UGroupUser> getUGroupUserByUserId(int userId);
	public Integer[] getGroupIdByUserId(int userId);
	public void DeleteUGroupUser(UGroupUser uGroupUser);
	public void DeleteUGroupUser(int managed);
	public void SaveUGroupUser(UGroupUser uGroupUser);
	public UGroupUser FinduGroupUser(int groupId,int userId);	
	
	public UGroupPower getUGroupPower(int groupId);
	public void SaveUGroupPower(UGroupPower uGroupPower);
	
}
