package cn.edustar.jitar.dao;

import java.util.List;

import cn.edustar.data.Pager;
import cn.edustar.jitar.pojos.Subject;
import cn.edustar.jitar.pojos.Unit;
import cn.edustar.jitar.pojos.UnitDayCount;
import cn.edustar.jitar.pojos.UnitLinks;
import cn.edustar.jitar.pojos.UnitNews;
import cn.edustar.jitar.pojos.UnitSubject;
import cn.edustar.jitar.pojos.UnitWebpart;
import cn.edustar.jitar.service.UnitQueryParam;

public interface UnitDao {

	public void saveOrUpdateUnit(Unit unit);

	public Unit getUnitById(int unitId);

	public Unit getUnitByGuid(String unitGuid);

	public Unit getUnitByTitle(String unitTitle);
	
	public Unit getUnitByName(String unitName);

	public void deleteUnit(Unit unit);
	
	public List<Unit> getAllUnitOrChildUnitList(Unit unit,Boolean...showDeleted);

	public List<Unit> getChildUnitListByParenId(int parentId, Boolean...showDeleted);
	
	public List<Unit> getChildUnitListByUnitType(String unitType);
	
	public List<UnitWebpart> getUnitWebpartList(int unitId);

	public List<UnitLinks> getUnitLinksByUnitId(int unitId);

	public void saveOrUpdateUnitWebpart(UnitWebpart unitWebpart);

	public void saveOrUpdateUnitSubject(UnitSubject unitSubject);

	public UnitSubject getUnitSubjectById(int unitSubjectId);

	public void deleteUnitSubject(UnitSubject unitSubject);

	public List<UnitSubject> getSubjectByUnitId(int unitId);
	
	public boolean checkUnitSubjectIsExists(Subject subject,Unit unit);
	
	public List<Unit> getDownUnitList(Unit unit);
	
	public void resetParentUnitCount(int parentId);
	
	/**
	 * 得到自定义的块内容
	 * @param unitId
	 * @return
	 */
	public List<UnitWebpart> getCustormUnitWebpart(int unitId);
	
	/**
	 * 根据标识加载机构内容块
	 * @param unitWebpartId
	 * @return
	 */
	public UnitWebpart getUnitWebpartById(int unitWebpartId);	
	
	
	/**
	 * 文章数
	 * @param unitPath
	 * @return
	 */
	public int getUnitArtilceCount(int unitId);
	
	/**
	 * 相册数
	 * @param unitPath
	 * @return
	 */
	public int getUnitPhotoCount(int unitId);
	
	/**
	 * 视频数
	 * @param unitPath
	 * @return
	 */
	public int getUnitVideoCount(int unitId);
	
	/**
	 * 资源数
	 * @param unitPath
	 * @return
	 */
	public int getUnitResourceCount(int unitId);
	
	/**
	 * 投票数
	 * @param unitPath
	 * @return
	 */
	public int getUnitVoteCount(String unitGuid);
	
	/**
	 * 用户数
	 * @param unitId
	 * @return
	 */
	public int getUnitUserCount(int unitId);
	
	
	
	/**
	 * 设置机构内容块位置
	 * @param unitWebpart
	 * @param columnIndex
	 * @param widgetBeforeId
	 * @return
	 */
	public int setUnitWebpartPosition(UnitWebpart unitWebpart,int columnIndex, int widgetBeforeId);
	
	/**
	 * 更新 UnitLinks
	 * @param unitLinks
	 */
	public void saveOrUpdateUnitLinks(UnitLinks unitLinks);
	
	/**
	 * 加载 UnitLinks
	 * @param unitLinksId
	 * @return
	 */
	public UnitLinks getUnitLinksById(int unitLinksId);
	
		
	/**
	 * 删除一个 UnitWebpart
	 * @param unitId
	 */
	public void deleteUnitWebpart(UnitWebpart unitWebpart);
	
	/**
	 * 得到系统的块内容
	 * @param unitId
	 * @return
	 */
	public List<UnitWebpart> getSystemUnitWebpart(int unitId);
	
	/**
	 * 根据 id 删除友情链接
	 * @param linkId
	 */
	public void deleteUnitLinksByLinkId(int linkId);
	
	public int getMaxId();
	/**
	 * 添加机构新闻
	 * @param unitNews
	 */
	public void saveOrUpdateUnitNews(UnitNews unitNews);
	
	/**
	 * 删除机构新闻
	 * @param unitNews
	 */
	public void deleteUnitNews(UnitNews unitNews);
	
	/**
	 * 根据标识加载机构新闻
	 * @param unitNewsId
	 * @return
	 */
	public UnitNews getUnitNewsById(int unitNewsId);
	
	/** 更新对象的显示名 */
	public void updateAccessControlUnitTitle(Unit unit);
	
	/** 删除机构的所有链接 */
	public void deleteAllUnitLinks(Unit unit);
	
	/** 删除机构的所有新闻 */
	public void deleteAllUnitNews(Unit unit);
	
	/** 删除单位的内容块  */
	public void deleteUnitWebpart(Unit unit);
	
	public void updateUnitStat(int unitId, String beginDate, String endDate);
	
	/** 统计使用的 */
	public List<Unit> getAllUnitByUnitId(int unitId);
	
	public List<Unit> getUnitList(UnitQueryParam param, Pager pager);
	
	/**
	 * 以下4个统计含下级机构和本级机构的总和
	 * @param unit
	 * @return
	 */
	public Long getAllUserCount(Unit unit);	
	public Long getAllArticleCount(Unit unit);	
	public Long getAllResourceCount(Unit unit);	
	public Long getAllPhotoCount(Unit unit);	
	public Long getAllVideoCount(Unit unit);
	
	/**
	 * 以下4个统计只包含子级所有的数量
	 * @param unit
	 * @return
	 */
	public Long getOnlyChildUserCount(Unit unit);    
    public Long getOnlyChildArticleCount(Unit unit);  
    public Long getOnlyChildResourceCount(Unit unit); 
    public Long getOnlyChildPhotoCount(Unit unit);    
    public Long getOnlyChildVideoCount(Unit unit);
	
	
	public void statUnitDayCount();	
	public UnitDayCount queryUnitDayCount(int unitId);	
	public void statUnitRank(int unitId);
	public void saveOrUpdateUnitDayCount(UnitDayCount unitDayCount);
}
