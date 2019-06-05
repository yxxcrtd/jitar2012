package cn.edustar.jitar.service.impl;

import java.util.List;

import cn.edustar.data.Pager;
import cn.edustar.jitar.dao.LinkDao;
import cn.edustar.jitar.model.ObjectType;
import cn.edustar.jitar.pojos.Link;
import cn.edustar.jitar.service.LinkQueryParam;
import cn.edustar.jitar.service.LinkService;

/**
 * 
 *
 *
 */
public class LinkServiceImpl implements LinkService {
	/** 链接对象 */
	private LinkDao linkDao;

	/** 链接对象的set方法 */
	public void setLinkDao(LinkDao linkDao) {
		this.linkDao = linkDao;
	}

	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.service.LinkService#saveLink(cn.edustar.jitar.pojos.Link)
	 */
	public void saveLink(Link link) {
		linkDao.saveLink(link);
		
	}
	
	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.service.LinkService#getLinkList(cn.edustar.data.Pager)
	 */
	public List<Link> getLinkList(LinkQueryParam param, Pager pager) {
		return linkDao.getLinkList(param, pager);
	}

	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.service.LinkService#getLinkById(int)
	 */
	public Link getLinkById(int linkId) {
		return linkDao.getLinkById(linkId);
	}

	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.service.LinkService#deleteLink(cn.edustar.jitar.pojos.Link)
	 */
	public void deleteLink(Link link) {
		linkDao.deleteLink(link);
	}

	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.service.LinkService#deleteLinkByObject(cn.edustar.jitar.model.ObjectType, int)
	 */
	public void deleteLinkByObject(ObjectType objectType, int objectId) {
		linkDao.deleteLinkByObject(objectType.getTypeId(), objectId);
	}
}
