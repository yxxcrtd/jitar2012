package cn.edustar.jitar.dao.hibernate;

import java.util.List;

import cn.edustar.data.Pager;
import cn.edustar.data.hql.QueryHelper;
import cn.edustar.jitar.dao.LinkDao;
import cn.edustar.jitar.pojos.Link;
import cn.edustar.jitar.service.LinkQueryParam;

/**
 * 链接的数据库访问实现.
 * @author Yang XinXin
 * @version 1.0.0 Mar 17, 2008 9:06:49 AM
 * 
 */
public class LinkDaoHibernate extends BaseDaoHibernate implements LinkDao {
	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.dao.LinkDao#saveLink(cn.edustar.jitar.pojos.Link)
	 */
	public void saveLink(Link link) {
		this.getSession().saveOrUpdate(link);
	}

	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.dao.LinkDao#getLinkList(cn.edustar.jitar.service.LinkQueryParam, cn.edustar.data.Pager)
	 */
	@SuppressWarnings("unchecked")
	public List<Link> getLinkList(LinkQueryParam param, Pager pager) {
		QueryHelper query = param.createQuery();
		if (pager == null)
			return query.queryData(this.getSession());
		else
			return query.queryDataAndTotalCount(this.getSession(), pager);
	}
	
	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.dao.LinkDao#getLinkById(int)
	 */
	public Link getLinkById(int linkId) {
		return (Link)this.getSession().get(Link.class, linkId);
	}

	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.dao.LinkDao#deleteLink(cn.edustar.jitar.pojos.Link)
	 */
	public void deleteLink(Link link) {
		this.getSession().delete(link);
	}

	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.dao.LinkDao#deleteLinkByObject(int, int)
	 */
	public void deleteLinkByObject(int objectType, int objectId) {
		String hql = "DELETE FROM Link WHERE objectType = ? AND objectId = ?";
		int count = getSession().createQuery(hql).setInteger(0, objectType).setInteger(1,objectId ).executeUpdate();
		if (log.isDebugEnabled()) {
			log.debug("deleteLinkByObject objectType = " + objectType + 
					", objectId = " + objectId + ", DeleteCount = " + count);
		}
	}
}
