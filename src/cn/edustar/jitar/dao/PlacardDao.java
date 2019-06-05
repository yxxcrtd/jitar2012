package cn.edustar.jitar.dao;

import java.util.List;

import cn.edustar.data.Pager;
import cn.edustar.jitar.pojos.Placard;
import cn.edustar.jitar.service.PlacardQueryParam;
import cn.edustar.jitar.service.PlacardQueryParamEx;

/**
 * 用户公告数据库访问接口定义 * 
 *
 */
public interface PlacardDao {
	/**
	 * 得到指定标识的公告.
	 * @param placardId
	 * @return
	 */
	public Placard getPlacard(int placardId);
	
	/**
	 * 保存(创建/更新)公告.
	 * @param placard
	 */
	public void savePlacard(Placard placard);
	
	/**
	 * 删除公告.
	 * @param placard
	 */
	public void deletePlacard(Placard placard);
	
	/**
	 * 删除指定对象的所有公告, 一般用于要删除该对象的时候.
	 * @param objectType
	 * @param objectId
	 */
	public void deletePlacardByObject(int objectType, int objectId);

	/**
	 * 更新指定公告的隐藏状态.
	 * @param placard
	 * @param hide
	 */
	public void updatePlacardHideState(Placard placard, boolean hide);
	
	/**
	 * 得到指定对象类型、指定对象标识的所有公告.
	 * @param obj_type
	 * @param obj_id
	 * @param includeHide - 是否包括隐藏的, true 表示包括隐藏的; false 表示不包括隐藏的.
	 * @return 返回 List&lt;Placard&gt; 集合.
	 */
	public List<Placard> getPlacardList(int obj_type, int obj_id, boolean includeHide);

	/**
	 * 得到指定条件下的公告列表.
	 * @param param
	 * @param pager
	 * @return
	 */
	public List<Placard> getPlacardList(PlacardQueryParam param, Pager pager);

	/**
	 * 得到指定条件下的课题组公告列表
	 * @param param
	 * @param pager
	 * @return
	 */
	public List getPlacardList(PlacardQueryParamEx param, Pager pager);
	/**
	 * 统计指定对象类型、指定对象标识的公告数量.
	 * @param obj_type
	 * @param obj_id
	 * @param includeHide - 是否包括隐藏的, true 表示包括隐藏的; false 表示不包括隐藏的.
	 * @return
	 */
	public int getPlacardCount(int obj_type, int obj_id, boolean includeHide);

	/**
	 * 获得指定对象的指定数量的最新的公告列表.	 * @param obj_type - 对象类型，参见 ObjectType 说明.
	 * @param obj_id - 对象标识.
	 * @param count - 数量.
	 * @return
	 */
	public List<Placard> getRecentPlacard(int obj_type, int obj_id, int count);

	/**
	 * 获得指定的多个对象、指定数量的最新公告列表，用于 GroupPlacardModule
	 * @param obj_type - 对象类型, 参见 ObjectType 类定义.
	 * @param obj_ids - 一组对象标识
	 * @param count - 取出最新的多少条
	 * @return
	 * @remark 此方法其实也覆盖了 getRecentPlacard() 方法。
	 */
	public List<Placard> getMultiRecentPlacard(int obj_type, List<Integer> obj_ids, int count);
}
