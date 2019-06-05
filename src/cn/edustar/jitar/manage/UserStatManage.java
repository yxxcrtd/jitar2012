package cn.edustar.jitar.manage;

import java.util.List;

import cn.edustar.data.Pager;
import cn.edustar.jitar.pojos.UserStat;
import cn.edustar.jitar.query.UserStatQueryParam;

/**
 * @author Yang Xinxin
 * @version 1.0.0 Sep 4, 2009 3:27:58 PM
 */
public interface UserStatManage {
	public List<UserStat> getUserStatList(UserStatQueryParam param, Pager pager);

	public List<UserStat> getUserListByLoginName(String loginName);

	public UserStat getUserStatById(int userId);

	public List<UserStat> getUserStatList();
	
	public void statAllUser();

	public void updateUserStat(String key, String beginDate, String endDate,int subjectId,int gradeId,int unitId,int teacherType, String...statGuid);
		
}
