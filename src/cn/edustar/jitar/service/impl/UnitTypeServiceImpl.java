package cn.edustar.jitar.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.context.ContextLoader;

import com.alibaba.fastjson.JSON;
import com.caucho.hessian.client.HessianProxyFactory;
import com.octopus.system.model.Dictionary;
import com.octopus.system.service.DicManageService;

import cn.edustar.jitar.pojos.UnitType;
import cn.edustar.jitar.service.UnitTypeService;
import cn.edustar.jitar.dao.UnitTypeDao;
/**
 * 单位类型的服务
 * @author baimindong 
 * @version 
 *
 */
public class UnitTypeServiceImpl implements UnitTypeService{
	
	private UnitTypeDao unitTypeDao;
	
	/***
	 * 保存一个单位类型.
	 * @param type
	 */
	public void saveUnitType(UnitType type){
		this.unitTypeDao.saveUnitType(type);
	}

	/**
	 *得到全部的单位分类 
	 * @return
	 */
	public List<UnitType> getUnitTypeList(){
		return this.unitTypeDao.getUnitTypeList();
	}
	/**
	 *得到全部的单位分类名称
	 * @return
	 */
	public List<String> getUnitTypeNameList(){
	    //  注意：这里需要统一用户系统进行更新到最新版本 
	    // 2013年9月3号
	    
	    //通用调用
	    HessianProxyFactory factory = new HessianProxyFactory();
        factory.setConnectTimeout(5000L); // 毫秒
        String ssoLoginUrl = ContextLoader.getCurrentWebApplicationContext().getServletContext().getInitParameter("SSOServerURL");
        String SSOServerURL1 = ssoLoginUrl;
        String SSOServerURL2 = ssoLoginUrl;
        if(ssoLoginUrl.indexOf(";")>-1){
        	String[] arrayUrl = ssoLoginUrl.split("\\;");
        	SSOServerURL1 = arrayUrl[0];
        	SSOServerURL2 = arrayUrl[1];
        }                 
        if (SSOServerURL2.endsWith("/")) {
        	SSOServerURL2 = SSOServerURL2.substring(0, SSOServerURL2.length() - 1);
        }
        String dicUrl = SSOServerURL2 + ContextLoader.getCurrentWebApplicationContext().getServletContext().getFilterRegistration("ssoUserFilter").getInitParameter("dicManageServiceUrl");
        if(dicUrl == null || dicUrl.length() == 0) return null;
        try {
            DicManageService dicManageService = (DicManageService) factory.create(DicManageService.class, dicUrl);
            String ret = dicManageService.showDics("2"); //2机构类型
            List<Dictionary> ls = JSON.parseArray(ret, Dictionary.class);
            if(ls == null || ls.size() == 0){
                return null;
            }
            else
            {
                List<String> ls2 = new ArrayList<String>();
                for(Dictionary d : ls){
                    ls2.add(d.getTypeId() + "_" + d.getTypeName());
                }
                return ls2;
            }
            
        } catch (Exception ex) {            
            System.out.println("传输错误" + dicUrl + " : " + ex.getLocalizedMessage());
            return null;
        }
	   
	    
		//return this.unitTypeDao.getUnitTypeNameList();
	}
	
	/**
	 * 通过标识Id得到单位分类.
	 * @param Id
	 * @return
	 */
	public UnitType getUnitTypeById(int Id){
		return this.unitTypeDao.getUnitTypeById(Id);
	}
	
	/**
	 * 通过标识guid得到单位分类.
	 * @param guid
	 * @return
	 */
	public UnitType getUnitTypeByGuid(String guid){
		return this.unitTypeDao.getUnitTypeByGuid(guid);
	}
	
	/***
	 * 删除单位分类.
	 * @param type
	 */
	public void deleteUnitType(UnitType type){
		this.unitTypeDao.deleteUnitType(type);
	}

	
	public UnitTypeDao getUnitTypeDao() {
		return unitTypeDao;
	}

	public void setUnitTypeDao(UnitTypeDao unitTypeDao) {
		this.unitTypeDao = unitTypeDao;
	}	
}
