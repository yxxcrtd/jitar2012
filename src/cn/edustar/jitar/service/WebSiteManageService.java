package cn.edustar.jitar.service;

import java.util.List;

import cn.edustar.jitar.pojos.BackYear;

public interface WebSiteManageService {
	public void saveOrUpdateBackYear(BackYear backYear);
	public List<BackYear> getBackYearList(String backYearType);
	@SuppressWarnings("rawtypes")
	public List getArticleYearList();
	public void statYearArticleCount(int year);
	public void slpitArticleTable(int year);
	public Long getYearArticleCount(int year);
	public Long getSubejctYearArticleCount(int year,int subjectId,int gradeId);
	public Long getUnitYearArticleCount(int year,int unitId);
	public void updateUnitInfo(int userId, int unitId);
}
