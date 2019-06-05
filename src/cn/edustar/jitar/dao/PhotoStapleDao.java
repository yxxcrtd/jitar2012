package cn.edustar.jitar.dao;

import java.util.List;
import java.util.Map;

import cn.edustar.data.DataTable;
import cn.edustar.data.Pager;
import cn.edustar.jitar.pojos.Category;
import cn.edustar.jitar.pojos.PhotoStaple;
import cn.edustar.jitar.service.PhotoStapleQueryParam;

/**
 * 照片分类的DAO接口
 * 
 * @author Yang Xinxin
 * @version 1.0.0 Mar 3, 2008 2:30:44 PM
 */
public interface PhotoStapleDao {
	
	/**
	 * 根据'照片分类'的Id得到一个对象.
	 *
	 * @param id
	 * @return
	 */
	public PhotoStaple findById(int id);

	/**
	 * 得到一个'照片分类'DataTable
	 *
	 * @param param
	 * @param pager
	 * @return
	 */
	public DataTable getPhotoStapleDataTable(PhotoStapleQueryParam param, Pager pager);

	/**
	 * 保存或修改一个'照片分类'对象.
	 *
	 * @param photoStaple
	 */
	public void savePhotoStaple(PhotoStaple photoStaple);

	/**
	 * 删除一个'照片分类'对象.
	 *
	 * @param photoStaple
	 */
	public void delPhotoStaple(PhotoStaple photoStaple);

	/**
	 * 得到'照片分类'的列表.
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
	
	/**
	 * 得到所有的相册分类.
	 *
	 * @return
	 */
	public List<PhotoStaple> getPhotoStapleList();

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
	/**
	 * 得到用户照片子分类
	 * @param parentId
	 * @return
	 */
	public List<PhotoStaple> getPhotoStapleChilds(int parentId);	
}
