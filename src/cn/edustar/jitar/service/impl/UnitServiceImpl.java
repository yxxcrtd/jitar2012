package cn.edustar.jitar.service.impl;

import java.util.List;

import javax.servlet.ServletRequest;

import com.alibaba.fastjson.JSON;
import com.caucho.hessian.client.HessianProxyFactory;

import cn.edustar.data.Pager;
import cn.edustar.jitar.JitarRequestContext;
import cn.edustar.jitar.dao.UnitDao;
import cn.edustar.jitar.pojos.Subject;
import cn.edustar.jitar.pojos.Unit;
import cn.edustar.jitar.pojos.UnitDayCount;
import cn.edustar.jitar.pojos.UnitLinks;
import cn.edustar.jitar.pojos.UnitNews;
import cn.edustar.jitar.pojos.UnitSubject;
import cn.edustar.jitar.pojos.UnitWebpart;
import cn.edustar.jitar.service.CacheService;
import cn.edustar.jitar.service.ProductConfigService;
import cn.edustar.jitar.service.UnitQueryParam;
import cn.edustar.jitar.service.UnitService;
import cn.edustar.jitar.util.CommonUtil;

public class UnitServiceImpl implements UnitService {

    /** 支持缓存的服务 */
    private CacheService cacheService;
    private ProductConfigService productConfigService;
    private String rootUnitKey = "rootUnit";
    public UnitDao unitDao;

    public boolean checkUnitSubjectIsExists(Subject subject, Unit unit) {
        return this.unitDao.checkUnitSubjectIsExists(subject, unit);
    }

    public String convertUnitStringToUnitTitle(String unitPathInfo) {
        if (unitPathInfo == null || unitPathInfo.length() == 0) {
            return unitPathInfo;
        }

        String[] a = unitPathInfo.split("/");
        String outString = "/";
        List<Unit> allUnits = this.getAllUnitOrChildUnitList(null);
        for (int i = 0; i < a.length; i++) {
            if (a[i].equals("")) {
                continue;
            }
            if (a[i].equals("0")) {
                outString += "根目录/";
            } else {
                Unit u = this.getUnitFromList(allUnits, a[i]);
                if (u == null) {
                    outString += "无效机构/";
                } else {
                    outString += u.getUnitTitle() + "/";
                }
            }
        }
        return outString;
    }

    /** 删除所有单位的友情链接 */
    public void deleteAllUnitLinks(Unit unit) {
        this.unitDao.deleteAllUnitLinks(unit);
    }

    /** 删除机构的所有新闻 */
    public void deleteAllUnitNews(Unit unit) {
        this.unitDao.deleteAllUnitNews(unit);
    }
    
    // 逻辑删除单位
    public void deleteUnit(Unit unit) {
        // 删除新闻、公告、动态、调查等等
//        this.deleteAllUnitLinks(unit);

        // 删除所有新闻
//        this.unitDao.deleteUnit(unit);
    	
    	// 1，修改SSO
    	ServletRequest request = null;
		if(null != JitarRequestContext.getRequestContext()) {
			request = JitarRequestContext.getRequestContext().getRequest();
		}
        String SSOServerURL = request.getServletContext().getInitParameter("SSOServerURL");
        if(null != SSOServerURL){
            String SSOServerURL1 = SSOServerURL;
            String SSOServerURL2 = SSOServerURL;
            if(SSOServerURL.indexOf(";")>-1){
            	String[] arrayUrl = SSOServerURL.split("\\;");
            	SSOServerURL1 = arrayUrl[0];
            	SSOServerURL2 = arrayUrl[1];
            }         	
	        if(SSOServerURL2.endsWith("/")) {
	        	SSOServerURL2 = SSOServerURL2.substring(0, SSOServerURL2.length() - 1);
	        }		
			String unitManageServiceUrl = SSOServerURL2 + request.getServletContext().getFilterRegistration("ssoUserFilter").getInitParameter("unitManageServiceUrl");
			
			// 得到远程用户服务对象
			com.octopus.system.service.UnitManageService unitManageService = null;
	        HessianProxyFactory factory = new HessianProxyFactory();
	        factory.setConnectTimeout(5000L);
	        String result = "";
	        
	        String unitJson = getSSOUnit(unit);
	        try {
	        	unitManageService = (com.octopus.system.service.UnitManageService) factory.create(com.octopus.system.service.UnitManageService.class, unitManageServiceUrl);
	        	if (null != unitManageService) {
	        		result = unitManageService.delUnit(unitJson);
	        	}
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
        }
        // 2，修改教研
    	unitDao.deleteUnit(unit);
    	unitDao.resetParentUnitCount(unit.getParentId());    	
    }

    public int getMaxId(){
    	return this.unitDao.getMaxId();
    }
    
    public void deleteUnitLinksByLinkId(int linkId) {
        this.unitDao.deleteUnitLinksByLinkId(linkId);
    }

    /**
     * 删除机构新闻
     * 
     * @param unitNews
     */
    public void deleteUnitNews(UnitNews unitNews) {
        this.unitDao.deleteUnitNews(unitNews);
    }

    public void deleteUnitSubject(UnitSubject unitSubject) {
        this.unitDao.deleteUnitSubject(unitSubject);
    }

    public void deleteUnitWebpart(Unit unit) {
        this.unitDao.deleteUnitWebpart(unit);
    }
    public void deleteUnitWebpart(UnitWebpart unitWebpart) {
        this.unitDao.deleteUnitWebpart(unitWebpart);
    }
    public Long getAllArticleCount(Unit unit) {
        return this.unitDao.getAllArticleCount(unit);
    }

    public Long getAllPhotoCount(Unit unit) {
        return this.unitDao.getAllPhotoCount(unit);
    }

    public Long getAllResourceCount(Unit unit) {
        return this.unitDao.getAllResourceCount(unit);
    }

    public List<Unit> getAllUnitByUnitId(int unitId) {
        return this.unitDao.getAllUnitByUnitId(unitId);
    }
    public Long getOnlyChildUserCount(Unit unit){
        return this.unitDao.getOnlyChildUserCount(unit);
    }
    public Long getOnlyChildArticleCount(Unit unit){
        return this.unitDao.getOnlyChildArticleCount(unit);
    }
    public Long getOnlyChildResourceCount(Unit unit){
        return this.unitDao.getOnlyChildResourceCount(unit);
    }
    public Long getOnlyChildPhotoCount(Unit unit){
        return this.unitDao.getOnlyChildPhotoCount(unit);
    }
    public Long getOnlyChildVideoCount(Unit unit){
        return this.unitDao.getOnlyChildVideoCount(unit);
    }

    public List<Unit> getAllUnitOrChildUnitList(Unit unit, Boolean...showDeleted) {
        return this.unitDao.getAllUnitOrChildUnitList(unit,showDeleted);
    }

    /**
     * 统计包含下级的所有信息
     */
    public Long getAllUserCount(Unit unit) {
        return this.unitDao.getAllUserCount(unit);
    }

    public Long getAllVideoCount(Unit unit) {
        return this.unitDao.getAllVideoCount(unit);
    }

    public List<Unit> getChildUnitListByParenId(int parentId, Boolean...showDeleted) {
        return this.unitDao.getChildUnitListByParenId(parentId,showDeleted);
    }

    public List<Unit> getChildUnitListByUnitType(String unitType) {
        return this.unitDao.getChildUnitListByUnitType(unitType);
    }

    public int getConfigUnitLevel() {
        String unitLevel = productConfigService.getUnitLevel();
        // System.out.println("unitLevel = "+unitLevel);
        if (unitLevel == null || unitLevel.equals("")) {
            return 100;
        }
        try {
            return Integer.parseInt(unitLevel, 10);
        } catch (Exception e) {
            return 100;
        }

    }

    /**
     * 得到自定义的块内容
     * 
     * @param unitId
     * @return
     */
    public List<UnitWebpart> getCustormUnitWebpart(int unitId) {
        return this.unitDao.getCustormUnitWebpart(unitId);
    }

    public List<Unit> getDownUnitList(Unit unit) {
        return this.unitDao.getDownUnitList(unit);
    }

    // 得到根单位信息。此处加了缓存服务
    public Unit getRootUnit() {
        Unit rootUnit = (Unit) this.cacheService.get(this.rootUnitKey);
        if (null == rootUnit) {
            // 根机构的parentId必须为 0
            List<Unit> u_l = this.getChildUnitListByParenId(0);
            if (u_l != null && u_l.size() > 0) {
                rootUnit = (Unit) u_l.get(0);
                this.cacheService.put(this.rootUnitKey, rootUnit);
            }
        }
        return rootUnit;
    }

    public List<UnitSubject> getSubjectByUnitId(int unitId) {
        return this.unitDao.getSubjectByUnitId(unitId);
    }

    public List<UnitWebpart> getSystemUnitWebpart(int unitId) {
        return this.unitDao.getSystemUnitWebpart(unitId);
    }

    /**
     * 文章数
     * 
     * @param unitPath
     * @return
     */
    public int getUnitArtilceCount(int unitId) {
        return this.unitDao.getUnitArtilceCount(unitId);
    }

    public Unit getUnitByGuid(String unitGuid) {

        return this.unitDao.getUnitByGuid(unitGuid);
    }

    public Unit getUnitById(int unitId) {

        return this.unitDao.getUnitById(unitId);
    }

    public Unit getUnitByName(String unitName) {

        return this.unitDao.getUnitByName(unitName);
    }

    public Unit getUnitByTitle(String unitTitle) {
        return this.unitDao.getUnitByTitle(unitTitle);
    }

    public int getUnitDeepLevel(Unit unit) {
        int count = 0;
        if (unit == null)
            return count;
        if (unit.getUnitPathInfo() == null || unit.getUnitPathInfo().trim().equals(""))
            return count;
        String unitPathInfo = unit.getUnitPathInfo();
        String[] unitPathInfoArray = unitPathInfo.split("/");
        count = unitPathInfoArray.length - 1;
        if (count < 0)
            count = 0;
        return count;
    }

    private Unit getUnitFromList(List<Unit> au, String unitId) {
        for (int i = 0; i < au.size(); i++) {
            Unit ut = (Unit) au.get(i);
            if (String.valueOf(ut.getUnitId()).equals(unitId)) {
                return ut;
            }
        }
        return null;
    }

    public UnitLinks getUnitLinksById(int unitLinksId) {
        return this.unitDao.getUnitLinksById(unitLinksId);
    }

    public List<UnitLinks> getUnitLinksByUnitId(int unitId) {
        return this.unitDao.getUnitLinksByUnitId(unitId);
    }

    public List<Unit> getUnitList(UnitQueryParam param, Pager pager) {
        return this.unitDao.getUnitList(param, pager);
    }

    /**
     * 根据标识加载机构新闻
     * 
     * @param unitNewsId
     * @return
     */
    public UnitNews getUnitNewsById(int unitNewsId) {
        return this.unitDao.getUnitNewsById(unitNewsId);
    }

    /** 得到2个机构之间的路径。2个单位必须是上下级机构 :u1 是上级机构 */
    public String getUnitPathBetweenUnits(Unit u1, Unit u2) {
        String startUnitPath = "/" + String.valueOf(u1.getUnitId()) + "/";
        if (u1.getUnitId() == u2.getUnitId())
            return startUnitPath;
        String s2 = u2.getUnitPathInfo();
        if (s2.indexOf(startUnitPath) == -1)
            return "";
        String s3 = s2.substring(s2.indexOf(startUnitPath));
        if (s3.equals("/"))
            s3 = "";
        return s3;
    }

    /**
     * 相册数
     * 
     * @param unitPath
     * @return
     */
    public int getUnitPhotoCount(int unitId) {
        return this.unitDao.getUnitPhotoCount(unitId);
    }

    public int getUnitResourceCount(int unitId) {
        return this.unitDao.getUnitResourceCount(unitId);
    }

    public UnitSubject getUnitSubjectById(int unitSubjectId) {
        return this.unitDao.getUnitSubjectById(unitSubjectId);
    }

    public int getUnitUserCount(int unitId) {
        return this.unitDao.getUnitUserCount(unitId);
    }

    public int getUnitVideoCount(int unitId) {
        return this.unitDao.getUnitVideoCount(unitId);
    }

    public int getUnitVoteCount(String unitGuid) {
        return this.unitDao.getUnitVoteCount(unitGuid);
    }

    /**
     * 根据标识加载机构内容块
     * 
     * @param unitWebpartId
     * @return
     */
    public UnitWebpart getUnitWebpartById(int unitWebpartId) {
        return this.unitDao.getUnitWebpartById(unitWebpartId);
    }

    public List<UnitWebpart> getUnitWebpartList(int unitId) {
        return this.unitDao.getUnitWebpartList(unitId);
    }

    public void saveOrUpdateUnit(Unit unit) {
    	// 1，先保存统一用户中的单位
    	ServletRequest request = null;
		if(null != JitarRequestContext.getRequestContext()) {
			request = JitarRequestContext.getRequestContext().getRequest();
		}
        String SSOServerURL = request.getServletContext().getInitParameter("SSOServerURL");
        if(null != SSOServerURL){
            String SSOServerURL1 = SSOServerURL;
            String SSOServerURL2 = SSOServerURL;
            if(SSOServerURL.indexOf(";")>-1){
            	String[] arrayUrl = SSOServerURL.split("\\;");
            	SSOServerURL1 = arrayUrl[0];
            	SSOServerURL2 = arrayUrl[1];
            }         	
	        if(SSOServerURL2.endsWith("/")) {
	        	SSOServerURL2 = SSOServerURL2.substring(0, SSOServerURL2.length() - 1);
	        }		
			String unitManageServiceUrl = SSOServerURL2 + request.getServletContext().getFilterRegistration("ssoUserFilter").getInitParameter("unitManageServiceUrl");
			
			
			// 得到远程用户服务对象
			com.octopus.system.service.UnitManageService unitManageService = null;
	        HessianProxyFactory factory = new HessianProxyFactory();
	        factory.setConnectTimeout(5000L);
	        String result = "";
	        
	        String unitJson = getSSOUnit(unit);
	        //System.out.println("unitJson=" + unitJson);
	        //System.out.println("unit.getUnitId()=" + unit.getUnitId());
	        // if(true) return;
	        boolean isNewCreate = false;
	        try {
	        	unitManageService = (com.octopus.system.service.UnitManageService) factory.create(com.octopus.system.service.UnitManageService.class, unitManageServiceUrl);
	        	if (null != unitManageService) {
	        	    if(unit.getUnitId() == 0){
	        	        //新建机构，返回 的是 机构的id
	        	        result = unitManageService.getSaveUnitId(unitJson);
	        	        isNewCreate = true;
	        	    }
	        	    else{
	        	        //更新机构，返回的是1、2、3等标志
	        	        result = unitManageService.updateUnitRemote(unitJson);
	        	        if(result.endsWith("1")){
	        	            String unitTypeName = unit.getUnitType();
	                        if(unitTypeName.contains("_")){
	                            unitTypeName = unitTypeName.substring(unitTypeName.indexOf("_") + 1);
	                        }
	                        unit.setUnitType(unitTypeName);
	                        this.unitDao.saveOrUpdateUnit(unit);
	                        //System.out.println("result" + result);
	                        return;
	        	        }
	        	        else{
	        	            throw new Exception("更新失败！");
	        	        }
	        	    }        		
	        	}
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        String unitTypeName = unit.getUnitType();
	        if(unitTypeName.contains("_")){
	            unitTypeName = unitTypeName.substring(unitTypeName.indexOf("_") + 1);
	        }
	        unit.setUnitType(unitTypeName);
	    	// 2，保存教研
	        if (isNewCreate) {
	        	unit.setUnitId(Integer.valueOf(result));
	            this.unitDao.saveOrUpdateUnit(unit);
	            if (0 == unit.getParentId()) {
	            	this.cacheService.put(this.rootUnitKey, unit);   
	            }
	        }
	        if(unit.getParentId() > 0){
    	        Unit parent = this.unitDao.getUnitById(unit.getParentId());
    	        unit.setUnitPathInfo(parent.getUnitPathInfo() + unit.getUnitId() + "/");
    	        this.unitDao.saveOrUpdateUnit(unit);
	        }
        }else{
            this.unitDao.saveOrUpdateUnit(unit);
            if (0 == unit.getParentId()) {
            	this.cacheService.put(this.rootUnitKey, unit);   
            }
        }
    }
    
    private String getSSOUnit(Unit unit) {
        String unitTypeName = unit.getUnitType();
        String unitTypeNameId = "1000";
        if(unitTypeName.contains("_")){
            unitTypeNameId = unitTypeName.substring(0, unitTypeName.indexOf("_"));
        }
        if(CommonUtil.isInteger(unitTypeNameId) == false){
            unitTypeNameId = "1000";
        }
        
        //System.out.println("Integer.valueOf(unitTypeNameId).intValue()=" + Integer.valueOf(unitTypeNameId).intValue());
    	com.octopus.system.model.Unit ssoUnit = new com.octopus.system.model.Unit();
    	ssoUnit.setUnitId(unit.getUnitId());
    	ssoUnit.setChildCount(0);
    	ssoUnit.setParentUnitCode(unit.getParentId());
    	ssoUnit.setUnitCode(unit.getUnitName());
    	ssoUnit.setUnitEName(unit.getUnitName());
    	ssoUnit.setUnitName(unit.getUnitTitle());
    	ssoUnit.setUnitStatus(1);
    	/*if(unit.getUnitId()==0){
    	    ssoUnit.setUnitType(0);
    	}else
    	{
    	    ssoUnit.setUnitType(Integer.valueOf(unitTypeNameId).intValue());
    	}*/
    	ssoUnit.setUnitType(Integer.valueOf(unitTypeNameId).intValue());
    	ssoUnit.setVersionNo(0);
        ssoUnit.setWebSiteName(unit.getSiteTitle());
    	return JSON.toJSONString(ssoUnit);
    }

    
    public void resetParentUnitCount(int parentId){
        this.unitDao.resetParentUnitCount(parentId);
    }
    public void saveOrUpdateUnitLinks(UnitLinks unitLinks) {
        this.unitDao.saveOrUpdateUnitLinks(unitLinks);
    }

    /**
     * 添加机构新闻
     * 
     * @param unitNews
     */
    public void saveOrUpdateUnitNews(UnitNews unitNews) {
        this.unitDao.saveOrUpdateUnitNews(unitNews);
    }

    public void saveOrUpdateUnitSubject(UnitSubject unitSubject) {
        this.unitDao.saveOrUpdateUnitSubject(unitSubject);
    }

    public void saveOrUpdateUnitWebpart(UnitWebpart unitWebpart) {
        this.unitDao.saveOrUpdateUnitWebpart(unitWebpart);
    }

    public void setCacheService(CacheService cacheService) {
        this.cacheService = cacheService;
    }

    public void setProductConfigService(ProductConfigService productConfigService) {
        this.productConfigService = productConfigService;
    }

    public void setUnitDao(UnitDao unitDao) {
        this.unitDao = unitDao;
    }

    public int setUnitWebpartPosition(UnitWebpart unitWebpart, int columnIndex, int widgetBeforeId) {
        return this.unitDao.setUnitWebpartPosition(unitWebpart, columnIndex, widgetBeforeId);
    }

    /** 更新对象的显示名 */
    public void updateAccessControlUnitTitle(Unit unit) {
        this.unitDao.updateAccessControlUnitTitle(unit);
    }

    public void updateUnitStat(int unitId, String beginDate, String endDate) {
        this.unitDao.updateUnitStat(unitId, beginDate, endDate);
    }

    /**
     * 每天的统计
     */
    public void statUnitDayCount(){
        this.unitDao.statUnitDayCount();
    }
    public UnitDayCount queryUnitDayCount(int unitId){
        return this.unitDao.queryUnitDayCount(unitId);
    }
    
    public void statUnitRank(int unitId){
        this.unitDao.statUnitRank(unitId);
    }
    public void saveOrUpdateUnitDayCount(UnitDayCount unitDayCount){
        this.unitDao.saveOrUpdateUnitDayCount(unitDayCount);
    }
}
