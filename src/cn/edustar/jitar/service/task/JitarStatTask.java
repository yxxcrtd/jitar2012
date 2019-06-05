package cn.edustar.jitar.service.task;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import cn.edustar.jitar.manage.UserStatManage;
import cn.edustar.jitar.pojos.Group;
import cn.edustar.jitar.pojos.Unit;
import cn.edustar.jitar.service.GroupService;
import cn.edustar.jitar.service.UnitService;

/**
 * 系统定时器
 *
 * @author Yang XinXin
 * @version 2.0.0, 2011-02-21 10:02:12
 */
public class JitarStatTask {
	
	/** 用户服务 */
	private UserStatManage userStatManage;

	/** 区县、单位服务 */
	private UnitService unitService;
	
	/** 群组服务 */
	private GroupService groupService;
	
	/** 单位列表 */
	private List<Unit> unitList = new ArrayList<Unit>();
	
	/** 群组列表 */
	private List<Group> groupList = new ArrayList<Group>();
	
	/**
	 * 执行
	 */
	public void doAuth() {
		userStatManage.statAllUser();		
		Calendar c = Calendar.getInstance();
		String beginDate = "2008-01-01";
		String endDate = new StringBuffer(String.valueOf(c.get(Calendar.YEAR) + 1)).append("-12-31").toString();
		
		// 单位
		unitList = unitService.getAllUnitOrChildUnitList(null);
		if (unitList.size() > 0) {
			for (int i = 0; i < unitList.size(); i++) {
				int unitId = unitList.get(i).getUnitId();
				unitService.updateUnitStat(unitId, beginDate, endDate);
			}
		}
		
		// 群组
		groupList = groupService.getGroupList();
		if (groupList.size() > 0) {
			for (int i = 0; i < groupList.size(); i++) {
				int groupId = groupList.get(i).getGroupId();
				groupService.updateGroupStat(groupId, beginDate, endDate);
			}
		}
	}

	
	public void setUserStatManage(UserStatManage userStatManage) {
		this.userStatManage = userStatManage;
	}
	
	public void setGroupService(GroupService groupService) {
		this.groupService = groupService;
	}

	public void setUnitService(UnitService unitService) {
		this.unitService = unitService;
	}
}
