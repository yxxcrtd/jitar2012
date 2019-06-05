package cn.edustar.jitar.service.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.edustar.jitar.dao.OnLineDao;
import cn.edustar.jitar.pojos.UserOnLine;
import cn.edustar.jitar.service.OnLineService;

/**
 * 在线服务的实现
 *
 * @author Yang xinxin
 * @version 1.0.0 Apr 10, 2009 4:07:55 PM
 */
public class OnLineServiceImpl implements OnLineService {

	/** 日志 */
	private static final Log log = LogFactory.getLog(OnLineServiceImpl.class);
	
	/** 在线DAO */
	private OnLineDao onlineDao;
	
	/* (non-Javadoc)
	 *
	 * @see cn.edustar.jitar.service.OnLineService#findOnLineUser(long)
	 */
	public List<UserOnLine> findOnLineUser(long optTime) {
		return onlineDao.findOnLineUser(optTime);
	}

	/* (non-Javadoc)
	 *
	 * @see cn.edustar.jitar.service.OnLineService#getOnLineUesrNum(long)
	 */
	public long getOnLineUesrNum(long optTime) {
		return onlineDao.getOnLineUesrNum(optTime);
	}

	/* (non-Javadoc)
	 *
	 * @see cn.edustar.jitar.service.OnLineService#getOnLineGuestNum(long)
	 */
	public long getOnLineGuestNum(long optTime) {
		return onlineDao.getOnLineGuestNum(optTime);
	}
	
	/* (non-Javadoc)
	 *
	 * @see cn.edustar.jitar.service.OnLineService#saveUserOnLine(cn.edustar.jitar.pojos.UserOnLine)
	 */
	public void saveUserOnLine(UserOnLine userOnLine) {
		if (userOnLine != null) {
			try {
				UserOnLine uo = this.onlineDao.findUserOnLineByUserName(userOnLine.getUserName());
				if (uo == null) {
					onlineDao.saveUserOnLine(userOnLine);
				} else {
					uo.setUserId(userOnLine.getUserId());
					uo.setUserName(userOnLine.getUserName());
					uo.setOnlineTime(userOnLine.getOnlineTime());
					onlineDao.saveUserOnLine(uo);
				}
			} catch (Exception e) {
				log.error(e);
			}
		}
	}
	
	/* (non-Javadoc)
	 *
	 * @see cn.edustar.jitar.service.OnLineService#findUserOnLineByUserName(java.lang.String)
	 */
	public UserOnLine findUserOnLineByUserName(String userName) {
		return this.onlineDao.findUserOnLineByUserName(userName);
	}
	
	/* (non-Javadoc)
	 *
	 * @see cn.edustar.jitar.service.OnLineService#removeUserOnLine(cn.edustar.jitar.pojos.UserOnLine)
	 */
	public void removeUserOnLine(UserOnLine userOnLine) {
		try {
			if (userOnLine != null) {
				onlineDao.removeUserOnLine(userOnLine);
			}
		} catch (Exception e) {
			//log.error(e);
		}
	}
	
	/* (non-Javadoc)
	 *
	 * @see cn.edustar.jitar.service.OnLineService#removeUserOnLineOutTime(long)
	 */
	public void removeUserOnLineOutTime(long optTime) {
		try {
			this.onlineDao.removeUserOnLineOutTime(optTime);
		} catch (Exception e) {
			log.error(e);
		}
	}

	/**
	 * 在线DAO的set方法
	 * 
	 * @param onlineDao
	 */
	public void setOnlineDao(OnLineDao onlineDao) {
		this.onlineDao = onlineDao;
	}
	
}
