package cn.edustar.jitar.service.task;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.FileFileFilter;
import org.apache.commons.io.filefilter.FileFilterUtils;
import org.apache.commons.io.filefilter.IOFileFilter;
import org.apache.commons.io.filefilter.TrueFileFilter;
import org.springframework.web.context.ContextLoader;

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
public class JitarStatTaskService {
	
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
	    
	    //deleteRegisterNotUsedHeaderImage();
	    
		userStatManage.statAllUser();		
		Calendar c = Calendar.getInstance();
		String beginDate = "2008-01-01";
		String endDate = new StringBuffer(String.valueOf(c.get(Calendar.YEAR) + 1)).append("-12-31").toString();
		
		//统计所有机构
		unitService.statUnitDayCount();
		
		// 单位排名统计更新
       unitList = unitService.getAllUnitOrChildUnitList(null);
        if (unitList.size() > 0) {
            for (int i = 0; i < unitList.size(); i++) {
                int unitId = unitList.get(i).getUnitId();
                unitService.statUnitRank(unitId);
            }
        }
		
		// 单位
		/*
		 * unitList = unitService.getAllUnitOrChildUnitList(null);
		if (unitList.size() > 0) {
			for (int i = 0; i < unitList.size(); i++) {
				int unitId = unitList.get(i).getUnitId();
				unitService.updateUnitStat(unitId, beginDate, endDate);
			}
		}*/
		
		
		
		// 群组
		groupList = groupService.getGroupList();
		if (groupList.size() > 0) {
			for (int i = 0; i < groupList.size(); i++) {
				int groupId = groupList.get(i).getGroupId();
				groupService.updateGroupStat(groupId, beginDate, endDate);
			}
		}
	}

	
	@SuppressWarnings({"rawtypes", "unused"})
    private void deleteRegisterNotUsedHeaderImage() {
        try {
            String rootPath = ContextLoader.getCurrentWebApplicationContext().getServletContext().getRealPath("/");
            if (!rootPath.endsWith(File.separator)) {
                rootPath += File.separator;
            }
            String headerPath = rootPath + "images" + File.separator + "headImg";
            Date pointDate = new Date();  
            long timeInterval = pointDate.getTime() - (60 * 60 * 1000); //超过1小时的文件
            pointDate.setTime(timeInterval);  
            // 设置文件过滤条件  
            IOFileFilter timeFileFilter = FileFilterUtils.ageFileFilter(pointDate, true);  
            IOFileFilter fileFiles = FileFilterUtils.and(FileFileFilter.FILE, timeFileFilter);  
            // 删除符合条件的文件  
            File deleteRootFolder = new File(headerPath);  
            Iterator itFile = FileUtils.iterateFilesAndDirs(deleteRootFolder, fileFiles, TrueFileFilter.INSTANCE);  
            while (itFile.hasNext()) {  
                File file = (File) itFile.next();
                if(!headerPath.equalsIgnoreCase(file.getCanonicalPath())){
                FileUtils.deleteQuietly(file);
                }
            }  
            
        } catch (Exception ex) {
            System.out.println("不删除头像文件夹下的文件：" + ex.getLocalizedMessage());
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
