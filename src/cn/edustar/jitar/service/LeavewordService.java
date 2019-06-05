package cn.edustar.jitar.service;

import java.util.List;

import cn.edustar.data.Pager;
import cn.edustar.jitar.model.ObjectType;
import cn.edustar.jitar.pojos.LeaveWord;

/**
 * 留言服务接口定义.
 * 
 * @author Yang Xinxin
 * @version 1.0.0 Mar 7, 2008 3:00:33 PM
 */
public interface LeavewordService {
	/**
	 * 根据留言 id 得到对应记录.
	 * 
	 * @param id
	 * @return
	 */
	public LeaveWord getLeaveWord(int id);

	/***
	 * 保存一个留言.
	 * @param lw
	 */
	public void saveLeaveWord(LeaveWord lw); 

	/**
	 * 更新一个留言的信息.
	 * @param lw
	 */
	public void updateLeaveWord(LeaveWord lw);
	
	/**
	 * 删除指定留言.
	 * @param lw
	 */
	public void deleteLeaveWord(LeaveWord lw);
	
	/**
	 * 删除指定对象类型和标识的对象的所有留言, 一般用于将要删除该对象.
	 * @param objectType
	 * @param objectId
	 */
	public void deleteLeavewordByObject(ObjectType objectType, int objectId);

	/**
	 * 留言列表.
	 * 
	 * @param param - 查询条件.
	 * @param pager - 分页设置.
	 * @param type - 列表类型.
	 * @return
	 */
	public List<LeaveWord> getLeaveWordList(LeavewordQueryParam param, Pager pager);
	
	/***
	 * 得到个人的留言.
	 * @param objId
	 * @return
	 */
	public List<LeaveWord> getPersonalLeaveWordList(int userId, Pager pager);
	
	/**
	 * 更新留言回复次数.
	 * @param leavewordId
	 */
	public void updateReplyTimes(int leavewordId);
	
}