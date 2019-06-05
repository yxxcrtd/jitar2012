package cn.edustar.jitar.service.impl;

import java.util.List;

import cn.edustar.jitar.dao.UGroupDao;
import cn.edustar.jitar.pojos.UGroup;
import cn.edustar.jitar.service.UGroupService;

public class UGroupServiceImpl implements UGroupService {
	private UGroupDao uGroupDao;
	public void setUGroupDao(UGroupDao uGroupDao){
		this.uGroupDao=uGroupDao;
	}
	
	public List<UGroup> getUGroups(){
		return this.uGroupDao.getUGroups();
	}
	public UGroup getUGroup(int groupId){
		return this.uGroupDao.getUGroup(groupId);
	}
	public UGroup getUGroup(String groupName){
		return this.uGroupDao.getUGroup(groupName);	
	}
	public void Save(UGroup uGroup){
		this.uGroupDao.Save(uGroup);
	}
	public void Delete(UGroup uGroup){
		this.Delete(uGroup);
	}
}
