package cn.edustar.jitar.service.impl;

import java.util.ArrayList;
import java.util.List;

import cn.edustar.data.Pager;
import cn.edustar.jitar.dao.PlacardDao;
import cn.edustar.jitar.model.ObjectType;
import cn.edustar.jitar.pojos.Placard;
import cn.edustar.jitar.service.PlacardQueryParam;
import cn.edustar.jitar.service.PlacardQueryParamEx;
import cn.edustar.jitar.service.PlacardService;

/**
 * 用户公告服务实现。
 * 
 *
 *
 */
public class PlacardServiceImpl implements PlacardService {
	/** 数据访问实现 */
	private PlacardDao pla_dao;

	/** 数据访问实现 */
	public void setPlacardDao(PlacardDao pla_dao) {
		this.pla_dao = pla_dao;
	}

	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.service.PlacardService#getPlacard(int)
	 */
	public Placard getPlacard(int placardId) {
		return pla_dao.getPlacard(placardId);
	}

	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.service.PlacardService#savePlacard(cn.edustar.jitar.pojos.Placard)
	 */
	public void savePlacard(Placard placard) {
		pla_dao.savePlacard(placard);
	}

	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.service.PlacardService#deletePlacard(cn.edustar.jitar.pojos.Placard)
	 */
	public void deletePlacard(Placard placard) {
		pla_dao.deletePlacard(placard);
	}

	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.service.PlacardService#deletePlacardForObject(cn.edustar.jitar.model.ObjectType, int)
	 */
	public void deletePlacardByObject(ObjectType objectType, int objectId) {
		pla_dao.deletePlacardByObject(objectType.getTypeId(), objectId);
	}

	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.service.PlacardService#hidePlacard(cn.edustar.jitar.pojos.Placard)
	 */
	public void hidePlacard(Placard placard) {
		pla_dao.updatePlacardHideState(placard, true);
	}

	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.service.PlacardService#showPlacard(cn.edustar.jitar.pojos.Placard)
	 */
	public void showPlacard(Placard placard) {
		pla_dao.updatePlacardHideState(placard, false);
	}

	
	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.service.PlacardService#getPlacardList(cn.edustar.jitar.model.ObjectType, int)
	 */
	public List<Placard> getPlacardList(ObjectType obj_type, int obj_id, boolean includeHide) {
		return pla_dao.getPlacardList(obj_type.getTypeId(), obj_id, includeHide);
	}

	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.service.PlacardService#getPlacardList(cn.edustar.jitar.service.PlacardQueryParam, cn.edustar.data.Pager)
	 */
	public List<Placard> getPlacardList(PlacardQueryParam param, Pager pager) {
		return this.pla_dao.getPlacardList(param, pager);
	}
	
	@SuppressWarnings("rawtypes")
	public List getPlacardList(PlacardQueryParamEx param, Pager pager) {
		return this.pla_dao.getPlacardList(param, pager);
	}	
	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.service.PlacardService#getPlacardCount(cn.edustar.jitar.model.ObjectType, int, boolean)
	 */
	public int getPlacardCount(ObjectType obj_type, int obj_id, boolean includeHide) {
		return pla_dao.getPlacardCount(obj_type.getTypeId(), obj_id, includeHide);
	}


	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.service.iface.PlacardService#getRecentPlacard(cn.edustar.jitar.model.ObjectType, int, int)
	 */
	public List<Placard> getRecentPlacard(ObjectType obj_type, int obj_id, int count) {
		return pla_dao.getRecentPlacard(obj_type.getTypeId(), obj_id, count);
	}
	
	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.service.iface.PlacardService#getMultiRecentPlacard(cn.edustar.jitar.model.ObjectType, java.util.List, int)
	 */
	public List<Placard> getMultiRecentPlacard(ObjectType obj_type, List<Integer> obj_ids, int count) {
		if (obj_type == null) 
			throw new java.lang.IllegalArgumentException("obj_type == null");
		if (obj_ids == null || obj_ids.size() == 0)
			return new ArrayList<Placard>();

		return pla_dao.getMultiRecentPlacard(obj_type.getTypeId(), obj_ids, count);
	}
}
