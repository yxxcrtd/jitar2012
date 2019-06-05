package cn.edustar.jitar.service.impl;

import java.util.List;

import cn.edustar.jitar.dao.StapleDao;
import cn.edustar.jitar.pojos.Staple;
import cn.edustar.jitar.service.StapleService;

/**
 * 用户博文分类服务实现类. * 
 * @author mengxianhui
 * @deprecated - 不使用了.
 */
public class StapleServiceImpl implements StapleService {
	private StapleDao stapleDao;

	public void addStaple(Staple staple) {
		stapleDao.addStaple(staple);
	}
	
	public Staple getStaple(Integer stapleId) {
		return (Staple) stapleDao.getStaple(stapleId);
	}

	public StapleDao getStapleDao() {
		return stapleDao;
	}
	public void setStapleDao(StapleDao stapleDao)
	{
		this.stapleDao = stapleDao;
	}

	public List<Staple> getAll() {
		return stapleDao.getAll();
	}
	
	public void updateStaple(Staple staple)
	{
		stapleDao.updateStaple(staple);
	}
	
	public void deleteStaple(Staple staple)
	{
		stapleDao.deleteStaple(staple);
	}
	
	public Boolean stapleNameIsExist(String stapleName)
	{
		return stapleDao.stapleNameIsExist(stapleName);
	}

	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.service.iface.IStapleService#getUserStapleList(int)
	 */
	public List<Staple> getUserStapleList(int userId) {
		return stapleDao.getUserStapleList(userId);
	}
}
