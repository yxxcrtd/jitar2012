package cn.edustar.jitar.service;

import cn.edustar.jitar.pojos.ProductConfig;

/**
 * DAO.
 * 
 * @author bai
 */
public interface ProductConfigService {

	public boolean isValid();
	
	public ProductConfig findById(int id);

	public void createProductConfig(ProductConfig cfg);

	public void updateProdcutConfig(ProductConfig cfg);

	public String getErrMessage();
	public String getProductName();
	public String getProductId();
	public String getDays();
	public String getRemainDays();
	public String getProductGuid();
	public String getUnitLevel();
	
	public String getUsercount();
	
}
