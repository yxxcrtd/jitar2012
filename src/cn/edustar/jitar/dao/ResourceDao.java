package cn.edustar.jitar.dao;

import java.util.List;

import cn.edustar.data.Pager;
import cn.edustar.jitar.pojos.Resource;
import cn.edustar.jitar.service.ResourceQueryParam;
import cn.edustar.jitar.service.UserResourceQueryParam;

/**
 * 资源DAO
 * 
 * @author Yang Xinxin
 * @version 1.0.0 Mar 29, 2008 2:51:20 PM
 */
public interface ResourceDao extends Dao {
	
	/**
	 * 得到指定标识的资源对象
	 * 
	 * @param resourceId
	 * @return
	 */
	public Resource getResource(int resourceId);

	/**
	 * 得到今天上载的资源
	 * @param userId
	 * @return
	 */
	public List<Resource> getTodayResources(int userId);
	/**
	 * 创建资源
	 * 
	 * @param resource
	 */
	public void createResource(Resource resource);
	
	/**
	 * 修改资源
	 * 
	 * @param resource
	 */
	public void updateResource(Resource resource);

	/**
	 * 删除指定资源对象
	 * 
	 * @param resource
	 */
	public void deleteResource(Resource resource);
	
	/**
	 * 得到指定条件指定分页的资源列表
	 * 
	 * @param param - 查询条件
	 * @param pager - 分页设置
	 * @return 返回 List&lt;Resource&gt; 集合
	 */
	public List<Resource> getResourceList(ResourceQueryParam param, Pager pager);

	/**
	 * 得到指定条件的资源数据表
	 * 
	 * @param param
	 * @param pager
	 * @return
	 */
	public List<Object[]> getResourceDataTable(UserResourceQueryParam param, Pager pager);
	
	/**
	 * 修改指定资源的删除状态
	 * 
	 * @param resourceId
	 * @param delState
	 * @return
	 */
	public int updateResourceDeleteStatus(int resourceId, boolean delState);

	/**
	 * 修改指定资源的共享状态
	 * 
	 * @param resourceId
	 * @param shareMode
	 * @return
	 */
	public int updateResourceShareMode(int resourceId, int shareMode);

	/**
	 * 更新指定资源的分类属性 (包括 sysCate, userCate)
	 * 
	 * @param resource
	 */
	public void updateResourceCategory(Resource resource);

	/**
	 * 更新指定资源的评论数统计值
	 * 
	 * @param resourceId
	 * @param commentCount
	 */
	public int updateResourceCommentCount(int resourceId, int commentCount);

	/**
	 * 设置所有使用指定个人资源分类的资源的个人资源分类标识为 null, 该分类将被删除时候调用
	 * 
	 * @param userCateId
	 */
	public int batchClearResourceUserCategory(int userCateId);

	/**
	 * 更改资源的系统分类(资源类型)属性
	 * 
	 * @param resource
	 * @param sysCateId
	 */
	public void updateResourceSysCate(Resource resource, Integer sysCateId);
	
	/**
	 * @param resourceId
	 */
	public void incResourceCommentCount(int resourceId);
	
	/**
	 * 增加资源的访问量
	 *
	 * @param resourceId
	 */
	public void increaseViewCount(int resourceId);
	public void increaseViewCount(int resourceId, int count);
	
	/**
	 * 
	 *
	 * @param resourceId
	 */
	public void deleteResourceFromPrepareCourseStage(int resourceId);
	
	/**
	 * 计算当前用户已经上传的资源总和＋照片总数之和
	 *
	 * @param userId
	 * @return
	 */
	public int totalResourceSize(int userId);
	
	public void setPushState(Resource resource, int pushState);
	
	/** 得到用户的全部资源 */
	public List<Resource> getUserResourceOfAll(int userId);
	
}
