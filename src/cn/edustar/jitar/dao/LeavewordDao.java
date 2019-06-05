package cn.edustar.jitar.dao;

import java.util.List;

import cn.edustar.data.Pager;
import cn.edustar.jitar.pojos.LeaveWord;
import cn.edustar.jitar.service.LeavewordQueryParam;

/**
 * 留言接口
 * 
 * @author Yang Xinxin
 * @version 1.0.0 Mar 7, 2008 2:57:54 PM
 */
public interface LeavewordDao {
	/** 投影查询字段 */
	public static final String GET_MESSAGE_LIST = "U.loginName, U.nickName, U.userIcon, L.content, L.createDate, L.ip";

	/**
	 * 根据 id 得到对应记录.
	 * 
	 * @param id
	 * @return
	 */
	public LeaveWord getById(int id);

	/**
	 * 保存一个留言.
	 * @param word
	 */
	public void saveLeaveWord(LeaveWord word);
	
	/**
	 * 更新留言.
	 * @param leaveWord
	 */
	public void updateLeaveWord(LeaveWord leaveWord);

	/**
	 * 删除留言.
	 * @param leaveWord
	 */
	public void deleteLeaveWord(LeaveWord leaveWord);

	/**
	 * 删除指定对象类型和标识的对象的所有留言, 一般用于将要删除该对象.
	 * @param objectType
	 * @param objectId
	 */
	public void deleteLeaveWordByObject(int objectType, int objectId);
	
	/**
	 * 全部留言列表.
	 * 
	 * @param param 查询条件.
	 * @param pager 分页设置.
	 * @param type 列表类型.
	 */
	public List<LeaveWord> getLeaveWordList(LeavewordQueryParam param, Pager pager);
	
	/***
	 * 得到个人留言.
	 * @param ObjId
	 * @return
	 */
	public List<LeaveWord> getPersonalLeaveWordList(int userId, Pager pager);
	
	/**
	 * 更新留言的回复次数.
	 * @param leavewordId
	 */
	public void updateReplyTimes(int leavewordId);
}
