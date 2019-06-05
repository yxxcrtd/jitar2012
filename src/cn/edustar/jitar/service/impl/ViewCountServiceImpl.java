package cn.edustar.jitar.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.edustar.jitar.dao.ViewCountDao;
import cn.edustar.jitar.pojos.ViewCount;
import cn.edustar.jitar.service.ViewCountService;

public class ViewCountServiceImpl implements ViewCountService {
	
	private static final Log log = LogFactory.getLog(ViewCountServiceImpl.class);

	/** 博文的数据访问对象 */
	private ViewCountDao viewcount_dao;

	/** 数据访问对象. */
	public void setViewCountDao(ViewCountDao viewcountdao) {
		this.viewcount_dao = viewcountdao;
	}

	/**
	 * 缺省构造函数.
	 */
	public ViewCountServiceImpl() {
	}

	/**
	 * 根据ID获得记录.
	 * 
	 * @param Id
	 * @return
	 */
	public ViewCount getViewCount(int id) {
		return viewcount_dao.getViewCount(id);
	}

	/**
	 * 根据日期获得记录.
	 * 
	 * @param date
	 * @return
	 */
	public ViewCount getViewCount(int objType, int objId, Date date){
		return viewcount_dao.getViewCount(objType, objId, date);
	}

	/**
	 * 创建对象.
	 * 
	 * @param viewCount
	 */
	public void createViewCount(ViewCount viewCount) {
		viewcount_dao.createViewCount(viewCount);
	}

	/**
	 * 修改对象.
	 * 
	 * @param viewCount
	 */
	public void updateViewCount(ViewCount viewCount) {
		viewcount_dao.updateViewCount(viewCount);
	}

	/**
	 * 更改删除状态
	 */
	public void changeViewCountdDelStatus(int objType, int objId, int del) {
		viewcount_dao.changeViewCountdDelStatus(objType, objId, del);
	}

	public void deleteViewCount(int objType, int objId) {
		// System.out.println("getViewCount(objType, objGUID) objType="+objType+"
		// objGUID="+objGUID);
		ViewCount viewCount = viewcount_dao.getViewCount(objType, objId);
		// System.out.println("viewCount IS NULL ="+(viewCount==null));
		if (viewCount != null)
			viewcount_dao.crashViewCount(viewCount);
	}

	/**
	 * 删除,当该删除原始记录的时候,删除对应的记录
	 * 
	 * 
	 * @param viewCount
	 */
	public void deleteViewCount(ViewCount viewCount) {
		viewcount_dao.crashViewCount(viewCount);
	}

	/**
	 * 统计数量(返回前多少天的数量)
	 * 
	 * @param days
	 * @return
	 */
	public List<ViewCount> getViewCountList(int objType, int days) {
		return viewcount_dao.getViewCountList(objType, days);
	}

	@SuppressWarnings("unchecked")
	public List getViewCountListEx(int objType, int days, int topNum) {
		return viewcount_dao.getViewCountListEx(objType, days, topNum);
	}

	/**
	 * 相对增加点击率.
	 * 
	 * @param viewCount 文章的评论对象.
	 * @param count 为正表示增加数量, 为 0 不操作.
	 */
	public void incViewCount(ViewCount viewCount, int count) {
		viewcount_dao.incViewCount(viewCount, count);
	}

	/**
	 * 增加点击率
	 */
	public void incViewCount(int objType, int objId, int count) {
		ViewCount viewCount = getViewCount(objType, objId, new Date());
		if (viewCount == null) {
			viewCount = new ViewCount();
			viewCount.setObjType(objType);
			viewCount.setObjId(objId);
			viewCount.setViewCount(count);
			createViewCount(viewCount);
		} else {
			incViewCount(viewCount, 1);
		}
	}
	
	/**
	 * 按区域获取数据
	 * @param objType
	 * @param days
	 * @param topNum
	 * @param unitPath
	 * @param sharedDepth
	 * @return
	 */
	/*@SuppressWarnings({"unchecked","unused"})	
	public List getViewCountListShared(int objType, int days, int topNum, String unitPath, int sharedDepth)
	{
		return this.viewcount_dao.getViewCountListShared(objType, days, topNum, unitPath, sharedDepth);
	}
*/
}
