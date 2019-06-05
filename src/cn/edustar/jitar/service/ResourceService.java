package cn.edustar.jitar.service;

import java.util.List;

import cn.edustar.data.DataTable;
import cn.edustar.data.Pager;
import cn.edustar.jitar.model.ResourceModelEx;
import cn.edustar.jitar.pojos.Resource;
import cn.edustar.jitar.pojos.User;

/**
 * 资源服务接口
 * 
 * @author Yang Xinxin
 * @version 1.0.0 Mar 29, 2008 2:51:20 PM
 */
public interface ResourceService {
	
	/** 事件: 资源已经被创建, 事件对象为 Resource 对象 */
	public static final String EVENT_RESOURCE_CREATED = "jitar.resource.created";

	/** 事件: 资源即将被销毁(彻底删除, crash), 事件对象为 Resource 对象 */
	public static final String EVENT_RESOURCE_CRASH = "jitar.resource.crash";

	/**
	 * 得到指定标识的资源.
	 * 
	 * @param resourceId
	 */
	public Resource getResource(int resourceId);

	/**
	 * 得到指定条件指定分页的资源列表.
	 * 
	 * @param param 查询条件.
	 * @param pager 分页设置.
	 * @return 返回 List&lt;ResourceModelEx&gt; 集合, 里面的对象属性可能未设置.
	 */
	public List<ResourceModelEx> getResourceList(ResourceQueryParam param, Pager pager);

	/**
	 * 得到指定条件的资源数据表.
	 * 
	 * @param param
	 * @param pager
	 * @return
	 */
	public DataTable getResourceDataTable(UserResourceQueryParam param, Pager pager);

	/**
	 * 创建一个资源.
	 * 
	 * @param resource
	 */
	public void createResource(Resource resource);

	/**
	 * 更新资源属性.
	 * 
	 * @param resource
	 */
	public void updateResource(Resource resource);

	/**
	 * 逻辑删除资源(放到回收站中).
	 * 
	 * @param resource
	 */
	public void deleteResource(Resource resource);

	/**
	 * 恢复一个被删除的资源(从回收站中).
	 * 
	 * @param resource
	 */
	public void recoverResource(Resource resource);

	/**
	 * 彻底删除一个资源(从回收站中删除).
	 * 
	 * @param resource
	 */
	public void crashResource(Resource resource);

	/**
	 * 更新指定资源的共享模式.
	 * 
	 * @param resource
	 * @param shareMode
	 */
	public void updateResourceShareMode(Resource resource, int shareMode);

	/**
	 * 更新指定资源的分类属性 (包括 sysCate, userCate).
	 * 
	 * @param resource
	 */
	public void moveResourceCategory(Resource resource);

	/**
	 * 重新统计指定用户的资源评论统计数量.
	 * 
	 * @param user
	 * @return
	 */
	public Object statResourceComment(User user);

	/**
	 * 设置所有使用指定个人资源分类的资源的个人资源分类标识为 null, 该分类将被删除时候调用.
	 * 
	 * @param userCateId
	 */
	public void batchClearResourceUserCategory(int userCateId);

	/**
	 * 审核通过资源, 设置其审核状态 = OK, 并更新该分类,用户等资源统计信息.
	 * 
	 * @param Resource
	 */
	public void auditResource(Resource resource);

	/**
	 * 取消一个资源的审核, 设置其审核状态 = WAIT_AUDIT
	 * 
	 * @param Resource
	 */
	public void unauditResource(Resource resource);

	/**
	 * 推荐一个资源.
	 * 
	 * @param Resource
	 */
	public void rcmdResource(Resource resource);

	/**
	 * 取消资源的推荐.
	 * 
	 * @param Resource
	 */
	public void unrcmdResource(Resource resource);

	/**
	 * 更改资源的系统分类(资源类型)属性.
	 * 
	 * @param resource
	 * @param sysCateId
	 */
	public void updateResourceSysCate(Resource resource, Integer sysCateId);

	/**
	 * 
	 *
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
	 * 计算当前用户已经上传的资源总和＋照片总数之和
	 * 
	 * @param userId
	 * @return
	 */
	public int totalResourceSize(int userId);
	public void setPushState(Resource resource, int pushState);
	
	public List<Resource> getTodayResources(int userId);
	
	public List<Resource> getUserResourceOfAll(int userId);
}
