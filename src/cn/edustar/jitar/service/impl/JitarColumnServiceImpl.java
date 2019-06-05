package cn.edustar.jitar.service.impl;

import java.util.List;

import cn.edustar.jitar.dao.JitarColumnDao;
import cn.edustar.jitar.pojos.JitarColumn;
import cn.edustar.jitar.pojos.JitarColumnNews;
import cn.edustar.jitar.service.JitarColumnService;

public class JitarColumnServiceImpl implements JitarColumnService {

	public JitarColumn getJitarColumnById(int columnId) {
		return this.jitarColumnDao.getJitarColumnById(columnId);
	}

	public List<JitarColumn> getJitarColumnList() {
		return this.jitarColumnDao.getJitarColumnList();
	}

	public void saveOrUpdateJitarColumnNews(JitarColumnNews jitarColumnNews)
	{
		this.jitarColumnDao.saveOrUpdateJitarColumnNews(jitarColumnNews);
	}
	
	public JitarColumnNews getJitarColumnNewsById(int columnNewsId)
	{
		return this.jitarColumnDao.getJitarColumnNewsById(columnNewsId);
	}
	
	public void deleteJitarColumnNews(JitarColumnNews jitarColumnNews)
	{
		this.jitarColumnDao.deleteJitarColumnNews(jitarColumnNews);
	}
	
	
	
	private JitarColumnDao jitarColumnDao;

	public void setJitarColumnDao(JitarColumnDao jitarColumnDao) {
		this.jitarColumnDao = jitarColumnDao;
	}
}
