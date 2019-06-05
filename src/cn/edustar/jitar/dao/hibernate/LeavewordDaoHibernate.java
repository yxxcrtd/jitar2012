package cn.edustar.jitar.dao.hibernate;

import java.util.List;

import cn.edustar.data.Pager;
import cn.edustar.data.hql.QueryHelper;
import cn.edustar.jitar.dao.LeavewordDao;
import cn.edustar.jitar.pojos.LeaveWord;
import cn.edustar.jitar.service.LeavewordQueryParam;

/**
 * 留言数据库实现
 * 
 * @author Yang Xinxin
 * @version 1.0.0 Mar 7, 2008 2:57:54 PM
 */
public class LeavewordDaoHibernate extends BaseDaoHibernate implements LeavewordDao {
	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.edustar.jitar.dao.LeavewordDao#getById(int)
	 */
	public LeaveWord getById(int id) {
		return (LeaveWord) this.getSession().get(LeaveWord.class, id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.edustar.jitar.dao.LeavewordDao#deleteLeaveWord(cn.edustar.jitar.pojos.LeaveWord)
	 */
	public void deleteLeaveWord(LeaveWord leaveWord) {
		this.getSession().delete(leaveWord);
	}

	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.dao.LeavewordDao#deleteLeaveWordByObject(cn.edustar.jitar.model.ObjectType, int)
	 */
	public void deleteLeaveWordByObject(int objectType, int objectId) {
		String hql = "DELETE FROM LeaveWord WHERE objType = ? AND objId = ?";
		int count = this.getSession().createQuery(hql).setInteger(0, objectType).setInteger(1, objectId).executeUpdate();
		if (log.isDebugEnabled()) {
			log.debug("deleteLeaveWordByObject objectType = " + objectType + ", objectId = " + objectId + ", DeleteCount = " + count);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.edustar.jitar.dao.LeavewordDao#updateLeaveWord(cn.edustar.jitar.pojos.LeaveWord)
	 */
	public void updateLeaveWord(LeaveWord leaveWord) {
		getSession().update(leaveWord);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.edustar.jitar.dao.LeavewordDao#getLeavewordList(cn.edustar.jitar.service.LeavewordQueryParam,
	 *      cn.edustar.data.Pager)
	 */
	@SuppressWarnings("unchecked")
	public List<LeaveWord> getLeaveWordList(LeavewordQueryParam param, Pager pager) {
		// 构造查询器.
		QueryHelper query = new QueryHelper();
		query.fromClause = "FROM LeaveWord L ";

		// 限定条件
		if (param.userId != null) {
			query.addAndWhere("L.userId = :userId");
			query.setInteger("userId", param.userId);
		}
		if (param.objType != null) {
			query.addAndWhere("L.objType = :objType");
			query.setInteger("objType", param.objType);
		}
		if (param.objId != null) {
			query.addAndWhere("L.objId = :objId");
			query.setInteger("objId", param.objId);
		}

		// 排序
		query.orderClause = "ORDER BY L.id DESC";

		// 进行查询
		if (pager == null) {
			return query.queryData(getSession(), -1, param.count);
		} else {
			pager.setTotalRows(query.queryTotalCount(getSession()));
			return query.queryData(getSession(), pager);
		}
		
	} 
	
	@SuppressWarnings("unchecked")
	public List<LeaveWord> getPersonalLeaveWordList(int userId, Pager pager) {
		QueryHelper query = new QueryHelper();
		query.fromClause = "FROM LeaveWord L";
		query.whereClause = "WHERE L.objId=" + userId +"AND L.objType=1";
		query.orderClause = "ORDER BY L.id DESC";
		return query.queryDataAndTotalCount(getSession(), pager);
	}

	//保存留言
	public void saveLeaveWord(LeaveWord word) {
		this.getSession().save(word);
		
	}
	
	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.dao.LeavewordDao#updateReplyTimes(int)
	 */
	public void updateReplyTimes(int leavewordId) {
		int count = 1;
		String hql = " UPDATE Leaveword " +
				" SET replyTimes=replyTimes + ? " +
				" WHERE id = ?" ;
			this.getSession().createQuery(hql).setInteger(0, count).setInteger(1, leavewordId).executeUpdate();
	}
	
}
