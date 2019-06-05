package cn.edustar.jitar.service.impl;

import java.util.List;

import cn.edustar.jitar.dao.WebSiteManageDao;
import cn.edustar.jitar.pojos.BackYear;
import cn.edustar.jitar.service.WebSiteManageService;

public class WebSiteManageServiceImpl implements WebSiteManageService {

	private WebSiteManageDao webSiteManageDao;

	public void saveOrUpdateBackYear(BackYear backYear) {
		this.webSiteManageDao.saveOrUpdateBackYear(backYear);
	}

	@SuppressWarnings("rawtypes")
	public List getArticleYearList() {
		return this.webSiteManageDao.getArticleYearList();
	}

	public Long getYearArticleCount(int year) {
		return this.webSiteManageDao.getYearArticleCount(year);
	}

	public Long getSubejctYearArticleCount(int year, int subjectId, int gradeId) {
		return this.webSiteManageDao.getSubejctYearArticleCount(year,
				subjectId, gradeId);
	}

	public Long getUnitYearArticleCount(int year, int unitId) {
		return this.webSiteManageDao.getUnitYearArticleCount(year, unitId);
	}

	public void statYearArticleCount(int year) {
		this.webSiteManageDao.statYearArticleCount(year);
	}

	public List<BackYear> getBackYearList(String backYearType) {
		return this.webSiteManageDao.getBackYearList(backYearType);
	}

	public void setWebSiteManageDao(WebSiteManageDao webSiteManageDao) {
		this.webSiteManageDao = webSiteManageDao;
	}

	public void slpitArticleTable(int year) {
		this.webSiteManageDao.slpitArticleTable(year);
	}

	public void updateUnitInfo(int userId, int unitId)
	{
		this.webSiteManageDao.updateUnitInfo(userId, unitId);
	}
}
