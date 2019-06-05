package cn.edustar.jitar.dao.hibernate;

import java.util.List;
import java.util.Map;

import cn.edustar.data.DataSchema;
import cn.edustar.data.DataTable;
import cn.edustar.data.Pager;
import cn.edustar.data.hql.QueryHelper;
import cn.edustar.jitar.dao.PhotoStapleDao;
import cn.edustar.jitar.pojos.Category;
import cn.edustar.jitar.pojos.PhotoStaple;
import cn.edustar.jitar.service.PhotoStapleQueryParam;
import cn.edustar.jitar.util.CommonUtil;

/**
 * @author Yang Xinxin
 */
public class PhotoStapleDaoHibernate extends BaseDaoHibernate implements PhotoStapleDao {

	/** 得到相册分类列表 */
	private final String GET_PHOTOSTAPLE_LIST = "FROM PhotoStaple PS WHERE (PS.userId = ?) ORDER BY PS.id DESC";
	
	/** 得到全部的相册分类列表 */
	private final String LOAD_PHOTOSTAPLE_LIST = "FROM PhotoStaple";
	
	/* 
	 * (non-Javadoc)
	 *
	 * @see cn.edustar.jitar.dao.PhotoStapleDao#findById(int)
	 */
	public PhotoStaple findById(int id) {
		return (PhotoStaple) this.getSession().get(PhotoStaple.class, id);
	}

	/* 
	 * (non-Javadoc)
	 *
	 * @see cn.edustar.jitar.dao.PhotoStapleDao#getPhotoStapleDataTable(cn.edustar.jitar.service.PhotoStapleQueryParam, cn.edustar.data.Pager)
	 */
	@SuppressWarnings("unchecked")
	public DataTable getPhotoStapleDataTable(PhotoStapleQueryParam param, Pager pager) {
		QueryHelper query = new QueryHelper();
		query.selectClause = "SELECT PS.id, PS.userId, PS.orderNum, PS.title, PS.stapleDescribe, PS.isHide";
		query.fromClause = "FROM PhotoStaple PS, User U";
		query.whereClause = "WHERE (PS.userId = U.userId) AND (PS.userId = " + param.userId + ")";
		query.orderClause = "ORDER BY id DESC";

		pager.setTotalRows(query.queryTotalCount(getSession()));
		List list = query.queryData(getSession(), pager);

		String schema_str = "id, userId, orderNum, title, stapleDescribe, isHide";
		DataTable dt = new DataTable(new DataSchema(schema_str), list);
		return dt;
	}

	/* 
	 * (non-Javadoc)
	 *
	 * @see cn.edustar.jitar.dao.PhotoStapleDao#savePhotoStaple(cn.edustar.jitar.pojos.PhotoStaple)
	 */
	public void savePhotoStaple(PhotoStaple photoStaple) {
		if(null == photoStaple){
			return;
		}
		Integer parentId =  photoStaple.getParentId();
		int csId = photoStaple.getId();
		if (csId > 0 ){
			//修改分类，需要检查其父分类不能是在子分类中，否则报告错误
			//如果父分类有变，检查:新的父分类中如果出现了原来的分类，说明新的分类被设置到了原来的分类下，不允许
			if(isInChildPath(csId,parentId)){
				throw new RuntimeException("不能把分类移动到自己的子分类下面.");
			}
		}
		
		/*计算设置parentPath*/
		if(null == parentId){
			photoStaple.setParentPath("/");
		}else{
			PhotoStaple parentphotoStaple =  findById(parentId);
			if( null == parentphotoStaple){
				photoStaple.setParentId(null);
				photoStaple.setParentPath("/");
			}else{
				photoStaple.setParentPath(calcParentCategoryPath(parentphotoStaple));
			}
		}
		
		this.getSession().saveOrUpdate(photoStaple);
	}
	
	/* 
	 * (non-Javadoc)
	 *
	 * @see cn.edustar.jitar.dao.PhotoStapleDao#delPhotoStaple(cn.edustar.jitar.pojos.PhotoStaple)
	 */
	public void delPhotoStaple (PhotoStaple photoStaple) {
		this.getSession().delete(photoStaple);
	}
	
	/* 
	 * (non-Javadoc)
	 *
	 * @see cn.edustar.jitar.dao.PhotoStapleDao#getPhotoStapleList(int)
	 */
	@SuppressWarnings("unchecked")
	public List getPhotoStapleList(int userId) {
		return this.getSession().createQuery(GET_PHOTOSTAPLE_LIST).setInteger(0, userId).list();
	}
	
	/* 
	 * (non-Javadoc)
	 *
	 * @see cn.edustar.jitar.dao.PhotoStapleDao#getPhotoAndStapleList(int)
	 */
	@SuppressWarnings("unchecked")
	public List<Map> getPhotoAndStapleList(int userId) {
		String hql = "SELECT new Map(p.userStaple as userStaple, ps.title as title, COUNT(*) as count) " +
				"FROM Photo p left join p.staple ps where p.userStaple = ps.id and p.userId = ? " +
				"GROUP BY p.userStaple, ps.title";
		return this.getSession().createQuery(hql).setInteger(0, userId).list();
	}
	
	/**
	 * 查询用户照片分类，转换为Category对象
	 * @param userId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Category> getPhotoStapleTreeList(int userId) {
		String hql = "select new cn.edustar.jitar.pojos.Category(ps.id,ps.title,ps.parentId,ps) FROM PhotoStaple ps WHERE (ps.userId = ?) ORDER BY ps.parentId,ps.orderNum,ps.id";
		return this.getSession().createQuery(hql).setInteger(0, userId).list();
	}	
	
	/**
	 * 得到用户照片子分类
	 * @param parentId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<PhotoStaple> getPhotoStapleChilds(int parentId){
		String hql = "FROM PhotoStaple ps WHERE (ps.parentId = ?) ORDER BY ps.orderNum,ps.id";
		return (List<PhotoStaple>)this.getSession().createQuery(hql).setInteger(0, parentId).list();
	}
	/* 
	 * (non-Javadoc)
	 *
	 * @see cn.edustar.jitar.dao.PhotoStapleDao#getPhotoStapleList()
	 */
	@SuppressWarnings("unchecked")
	public List<PhotoStaple> getPhotoStapleList() {
		return this.getSession().createQuery(LOAD_PHOTOSTAPLE_LIST).list(); 
	}

	/**
	 * newParentId 是 id 的子分类 ?? 
	 * @param id
	 * @param newParentId
	 * @return true 是子分类； false 不是子分类
	 */
	public boolean isInChildPath(int id,Integer newParentId)
	{
		if(null == newParentId){
			return false;
		}
		PhotoStaple origin_photoStaple = findById(id);
		if(null == origin_photoStaple){
			return false;
		}
		if (CommonUtil.equals(origin_photoStaple.getParentId(), newParentId)){
			//父分类没变
			return false;
		}else{
			PhotoStaple parent_photoStaple =  findById(newParentId);
			if(null == parent_photoStaple){
				return false;
			}else{
				String newParentPath = calcParentCategoryPath(parent_photoStaple);
				if(newParentPath.indexOf("/"+id+"/") >= 0){
					return true;
				}else{
					return false;
				}
			}
		}
	}
	
	/**
	 * 计算指定分类的分类路径. 分类路径 = 父分类路径 + c.id + "/" .
	 * @param c
	 * @return 
	 */
	public static final String calcCategoryPath(PhotoStaple c) {
		return c.getParentPath() + c.getId() + "/";
	}	
	
	/**
	 * 好像和上面的函数calcCategoryPath(ContentSpace c)没什么区别
	 * @param new_parent
	 * @return
	 */
	public static final String calcParentCategoryPath(PhotoStaple new_parent){
		if (new_parent == null) return "/";
		return new_parent.getCategoryPath();		
	}
		
}
