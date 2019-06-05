package cn.edustar.jitar.dao;

import java.util.List;

import cn.edustar.jitar.pojos.JitarColumn;
import cn.edustar.jitar.pojos.JitarColumnNews;

public interface JitarColumnDao {

	public JitarColumn getJitarColumnById(int columnId);

	public List<JitarColumn> getJitarColumnList();
	
	public void saveOrUpdateJitarColumnNews(JitarColumnNews jitarColumnNews);
	
	public JitarColumnNews getJitarColumnNewsById(int columnNewsId);
	
	public void deleteJitarColumnNews(JitarColumnNews jitarColumnNews);
}
