package cn.edustar.jitar.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.edustar.data.DataRow;
import cn.edustar.jitar.pojos.Category;
import cn.edustar.jitar.service.CategoryService;

/**
 * 做为 ArticleServiecImpl, PhotoServiceImpl, ResourceServiceImpl 等的基类
 * 
 * @author Administrator
 */
public class DocumentServiceImpl {

	/** 日志 */
	protected static final Log log = LogFactory.getLog(ArticleServiceImpl.class);
	
	/**
	 * 定义从某种对象中能够提取分类信息的接口.
	 */
	public interface CategorySupport {
		/** 从指定对象中获取分类标识. */
		public Integer getCategoryId(Object o);
		
		/** 将分类对象设置给指定对象. */
		public void setCategory(Object o, Category category);
	}
	
	/** 可选的分类服务, 在查询合并分类信息的时候使用. */
	protected CategoryService cate_svc;
	
	/** 得到分类服务. */
	public CategoryService getCategoryService() {
		return this.cate_svc;
	}
	
	/** 可选的分类服务, 在查询合并分类信息的时候使用. */
	public void setCategoryService(CategoryService cate_svc) {
		this.cate_svc = cate_svc;
	}
	
	/**
	 * 合并分类信息到指定对象集合中.
	 * @param obj_list - 对象列表.
	 * @param cs - CategorySupport 回调.
	 * @param itemType - 限定的分类类型.
	 */
	@SuppressWarnings("unchecked")
	public void joinCategory(List obj_list, CategorySupport cs, String itemType) {
		// 验证.
		if (obj_list == null || obj_list.size() == 0) return;
		
		// 从列表中获取分类标识.
		Map<Integer, Category> cate_map = retrCategoryId(obj_list, cs);
		
		// 得到这些分类信息.
		cate_svc.getCategoryByMap(cate_map);
		
		// 合并.
		for (Object object : obj_list) {
			Integer cid = cs.getCategoryId(object);
			if (cid != null) {
				cs.setCategory(object, cate_map.get(cid));
			}
		}
	}
	
	/** 从 某个列表中 获取所有系统分类标识 */
	private static final Map<Integer, Category> retrCategoryId(
			List<Object> obj_list, CategorySupport cs) {
		HashMap<Integer, Category> map = new HashMap<Integer, Category>();
		for (Object object: obj_list) {
			Integer cid = cs.getCategoryId(object);
			if (cid != null)
				map.put(cid, null);
		}
		return map;
	}

	/** DataTable CategorySupport implement */
	protected static final class DtCateSupport implements CategorySupport {
		private String id_col;		// 分类标识所在的 DataTable 列.
		private String obj_col;		// 对象所在的 DataTable 列.
		public DtCateSupport(String id_col, String obj_col) {
			this.id_col = id_col;
			this.obj_col = obj_col;
		}
		public Integer getCategoryId(Object o) {
			return (Integer)((DataRow)o).get(id_col);
		}
		public void setCategory(Object o, Category category) {
			((DataRow)o).set(obj_col, category);
		}
	}

}
