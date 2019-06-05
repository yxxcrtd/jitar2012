package cn.edustar.jitar.service;

import java.util.List;
import java.util.Map;

import cn.edustar.data.DataTable;
import cn.edustar.data.Pager;
import cn.edustar.jitar.pojos.Category;
import cn.edustar.jitar.pojos.PhotoStaple;

/**
 * 相册分类接口.
 * 
 * @author Yang Xinxin
 * @version 1.0.0 Apr 28, 2008 11:02:45 AM
 */
public interface PhotoStapleService {
	
	/**
	 * 根据'PhotoStapleId'得到'相册分类对象'
	 *
	 * @param id
	 * @return
	 */
	public PhotoStaple findById(int id);

	/**
	 * 得到'相册分类'的'DataTable'
	 *
	 * @param param
	 * @param pager
	 * @return
	 */
	public DataTable getPhotoStapleDataTable(PhotoStapleQueryParam param, Pager pager);

	/**
	 * 保存一个'相册分类对象'
	 *
	 * @param photoStaple
	 */
	public void savePhotoStaple(PhotoStaple photoStaple);
	
	/**
	 * 根据'PhotoStapleId'删除'相册分类对象'
	 *
	 * @param id
	 */
	public void delPhotoStaple(int id);
	
	/**
	 * 根据'用户Id'得到'相册分类列表'
	 *
	 * @param userId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getPhotoStapleList(int userId);
	
	/**
	 * 个人分类的统计.
	 *
	 * @param userId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Map> getPhotoAndStapleList(int userId);
	
	public List<PhotoStaple> getPhotoStapleList();
	
	/**
	 * 得到用户照片子分类
	 * @param parentId
	 * @return
	 */
	public List<PhotoStaple> getPhotoStapleChilds(int parentId);
	/**
	 * 查询用户照片分类，转换为Category对象
	 * @param userId
	 * @return
	 */
	public List<Category> getPhotoStapleTreeList(int userId);	
	
	/**
	 *  newParentId 是否是 id 的子分类 
	 * @param id
	 * @param newParentId
	 * @return true 是子分类； false 不是子分类
	 * @return
	 */
	public boolean isInChildPath(int id,Integer newParentId);
	
}
