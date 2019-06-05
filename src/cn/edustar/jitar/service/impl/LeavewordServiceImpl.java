package cn.edustar.jitar.service.impl;

import java.util.List;

import cn.edustar.data.Pager;
import cn.edustar.jitar.dao.LeavewordDao;
import cn.edustar.jitar.model.ObjectType;
import cn.edustar.jitar.pojos.LeaveWord;
import cn.edustar.jitar.service.LeavewordQueryParam;
import cn.edustar.jitar.service.LeavewordService;

/**
 * 留言服务.
 * 
 * @author Yang Xinxin
 * @version 1.0.0 Mar 7, 2008 2:37:54 PM
 */
public class LeavewordServiceImpl implements LeavewordService {

	/** 留言对象. */
	private LeavewordDao leavewordDao;

	/** 留言对象的set方法 */
	public void setLeavewordDao(LeavewordDao leavewordDao) {
		this.leavewordDao = leavewordDao;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.edustar.jitar.service.LeavewordService#getById(int)
	 */
	public LeaveWord getLeaveWord(int id) {
		return leavewordDao.getById(id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.edustar.jitar.service.LeavewordService#getLeavewordDataTable(cn.edustar.jitar.service.LeavewordQueryParam,
	 *      cn.edustar.data.Pager, java.lang.String)
	 */
	public List<LeaveWord> getLeaveWordList(LeavewordQueryParam param, Pager pager) {
		List<LeaveWord> list = leavewordDao.getLeaveWordList(param, pager);
		return list;
	}
	
	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.service.LeavewordService#getPersonalLeavewordList(int)
	 */
	public List<LeaveWord> getPersonalLeaveWordList(int userId, Pager pager) {
		return leavewordDao.getPersonalLeaveWordList(userId, pager);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.edustar.jitar.service.LeavewordService#updateLeaveWord(cn.edustar.jitar.pojos.LeaveWord)
	 */
	public void updateLeaveWord(LeaveWord lw) {
		leavewordDao.updateLeaveWord(lw);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.edustar.jitar.service.LeavewordService#deleteLeaveWord(cn.edustar.jitar.pojos.LeaveWord)
	 */
	public void deleteLeaveWord(LeaveWord lw) {
		leavewordDao.deleteLeaveWord(lw);
	}

	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.service.LeavewordService#deleteLeavewordByObject(cn.edustar.jitar.model.ObjectType, int)
	 */
	public void deleteLeavewordByObject(ObjectType objectType, int objectId) {
		leavewordDao.deleteLeaveWordByObject(objectType.getTypeId(), objectId);
	}

	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.service.LeavewordService#saveLeaveWord(cn.edustar.jitar.pojos.LeaveWord)
	 */
	public void saveLeaveWord(LeaveWord lw) {
		leavewordDao.saveLeaveWord(lw);
	}

	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.service.LeavewordService#updateReplyTimes(int)
	 */
	public void updateReplyTimes(int leavewordId) {
		leavewordDao.updateReplyTimes(leavewordId);
	}
}
