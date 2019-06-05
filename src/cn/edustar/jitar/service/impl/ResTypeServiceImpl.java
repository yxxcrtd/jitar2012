package cn.edustar.jitar.service.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.edustar.jitar.dao.ResTypeDao;
import cn.edustar.jitar.pojos.ResType;
import cn.edustar.jitar.service.ResTypeService;

/**
 * 资源类型服务实现.
 * @author gusheng
 *
 */
public class ResTypeServiceImpl implements ResTypeService {
	/** 文章记录器 */
	private static final Log logger = LogFactory.getLog(ResTypeServiceImpl.class);
	
	/** JtrResTypeDao DAO */
	private ResTypeDao resTypeDao;

	
	
	public void delResType(ResType resType) {
		resTypeDao.delResType(resType);
	}
	
	/* (non-Javadoc)
	 * @see cn.edustar.jitar.service.impl.ResTypeService#getResType(int)
	 */
	public ResType getResType(int resTypeId) {
		return resTypeDao.getResType(resTypeId);
	}
	
	/* (non-Javadoc)
	 * @see cn.edustar.jitar.service.impl.ResTypeService#createResType(cn.edustar.jitar.pojos.JtrResType)
	 */
	public void createResType(ResType resType) {
		if (resType == null) 
			throw new IllegalArgumentException("resType == null");
		boolean success = false;
		try {
			resTypeDao.createResType(resType);
			success = true;
		} finally {
			if (logger.isDebugEnabled()) {
				logger.debug("创建资源类型 JtrResType=" + resType + ", success=" + success);
			}
		}
	}
	/* (non-Javadoc)
	 * @see cn.edustar.jitar.service.impl.ResTypeService#getChildJtrResTypes(java.lang.Integer)
	 */
	public List<ResType> getChildResTypes(Integer parentId) {
		return resTypeDao.getChildResTypes(parentId);
	}

	public List<ResType> getResTypes() {
		return resTypeDao.getResTypes();
	}
	/* (non-Javadoc)
	 * @see cn.edustar.jitar.service.impl.ResTypeService#getResTypeDao()
	 */
	public ResTypeDao getResTypeDao() {
		return resTypeDao;
	}

	/* (non-Javadoc)
	 * @see cn.edustar.jitar.service.impl.ResTypeService#setResTypeDao(cn.edustar.jitar.dao.JtrResTypeDao)
	 */
	public void setResTypeDao(ResTypeDao resTypeDao) {
		this.resTypeDao = resTypeDao;
	}
	/**
	 * 用来检查相同级别是否有相同的分类
	 */ 
	public ResType getResTypeByNameAndParentId(String resName, Integer parentId)
	{
		return this.resTypeDao.getResTypeByNameAndParentId(resName, parentId);
	}
}
