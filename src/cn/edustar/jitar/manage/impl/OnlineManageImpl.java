package cn.edustar.jitar.manage.impl;

import java.util.Date;
import java.util.List;

import cn.edustar.jitar.dao.hibernate.BaseDaoHibernate;
import cn.edustar.jitar.manage.OnlineManage;
import cn.edustar.jitar.pojos.UserOnLineStat;

/**
 * 在线管理的实现（没有事务）
 * 
 * @author Yang XinXin
 */
@SuppressWarnings("unchecked")
public class OnlineManageImpl extends BaseDaoHibernate implements OnlineManage {

//	/* (non-Javadoc)
//	 * 
//	 * @see cn.edustar.jitar.manage.OnlineManage#saveUserOnLine(cn.edustar.jitar.pojos.UserOnLine)
//	 */
//	@Override
//	public void saveUserOnLine(UserOnLine userOnLine) {
//		if (null != userOnLine) {
//			try {
//				UserOnLine uo = findUserOnLineByUserName(userOnLine.getUserName());
//				if (null == uo) {
//					this.getSession().save(userOnLine);
//				} else {
//					uo.setUserId(userOnLine.getUserId());
//					uo.setUserName(userOnLine.getUserName());
//					uo.setOnlineTime(userOnLine.getOnlineTime());
//					this.getSession().save(userOnLine);
//				}
//			} catch (Exception e) {
//				log.error("保存或更新在线用户出现异常：" + e);
//			}
//		}
//	}
//	
//	/* (non-Javadoc)
//	 * 
//	 * @see cn.edustar.jitar.manage.OnlineManage#findUserOnLineByUserName(java.lang.String)
//	 */
//	@Override
//	public UserOnLine findUserOnLineByUserName(String userName) {
//		List<UserOnLine> list = this.getSession().createQuery("FROM UserOnLine WHERE userName = ?").setString(0, userName).list();
//		return (null == list || list.isEmpty()) ? null : (UserOnLine) list.get(0);
//	}
	
	
	
	/* (non-Javadoc)
	 * 
	 * @see cn.edustar.jitar.manage.OnlineManage#getUserOnLineStat()
	 */
	@Override
	public UserOnLineStat getUserOnLineStat() {
		List<UserOnLineStat> list = this.getSession().createQuery("FROM UserOnLineStat").list();
		return (null == list || list.isEmpty()) ? null : (UserOnLineStat) list.get(0);
	}
	
	/* (non-Javadoc)
	 * 
	 * @see cn.edustar.jitar.manage.OnlineManage#updateOnLineStat(int)
	 */
	@Override
	public void updateOnLineStat(int count) {
		String hql = "UPDATE UserOnLineStat SET highest = ?, appearTime = '" + String.format("%1$tY-%1$tm-%1$td %1$tH:%1$tM:%1$tS", new Date()) + "' WHERE id = 1";
		this.getSession().createQuery(hql).setInteger(0, count).executeUpdate();	
		this.getSession().flush();
	}

    @Override
    public void saveUserOnLineStat(UserOnLineStat userOnLineStat) {
        this.getSession().save(userOnLineStat);
        this.getSession().flush();
    }

}
