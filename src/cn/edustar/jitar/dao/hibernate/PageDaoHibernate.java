package cn.edustar.jitar.dao.hibernate;

import java.util.List;

import cn.edustar.jitar.dao.PageDao;
import cn.edustar.jitar.pojos.Page;
import cn.edustar.jitar.pojos.Widget;
import cn.edustar.jitar.util.CommonUtil;

/**
 * 获取'Page'的数据库实现.
 * 
 * @author mengxianhui
 */
public class PageDaoHibernate extends BaseDaoHibernate implements PageDao {

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.edustar.jitar.dao.iface.PageDao#getPage(int)
	 */
	public Page getPage(int pageId) {
		return (Page) this.getSession().get(Page.class, pageId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.edustar.jitar.dao.iface.PageDao#addPage(cn.edustar.jitar.pojos.Page)
	 */
	public void addPage(Page page) {
		this.getSession().save(page);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.edustar.jitar.dao.iface.PageDao#saveWidget(cn.edustar.jitar.pojos.Widget)
	 */
	public void saveWidget(Widget widget) {
		this.getSession().save(widget);
	}
	
	/*
	 * (non-Javadoc)
	 *
	 * @see cn.edustar.jitar.dao.PageDao#saveOrUpdate(cn.edustar.jitar.pojos.Widget)
	 */
	public void saveOrUpdate(Widget widget) {
		this.getSession().saveOrUpdate(widget);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.edustar.jitar.dao.PageDao#getPageWidgets(int)
	 */
	@SuppressWarnings("unchecked")
	public List<Widget> getPageWidgets(int pageId) {
		String hql = "FROM Widget WHERE pageId = ? ORDER BY columnIndex, rowIndex, id";
		return (List<Widget>)  this.getSession().createQuery(hql).setInteger(0, pageId).list();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.edustar.jitar.dao.PageDao#getPageListByUser(int)
	 */
	@SuppressWarnings("unchecked")
	public List<Page> getPageListByUser(int userId) {
		String hql = "FROM Page WHERE userId = ?";
		return (List<Page>)  this.getSession().createQuery(hql).setInteger(0, userId).list();
	}
	
	@SuppressWarnings("unchecked")
	public Page getUserIndexPage(int userId) {
		String hql = "FROM Page WHERE ObjId = ? AND name = ?";
		List<Page> page_list = this.getSession().createQuery(hql).setInteger(0, userId).setString(1, "index").list();//this.getSession().find(hql, new Object[]{userId, "index"});
		if(page_list == null || page_list.size() == 0)
			return null;
		return page_list.get(0);
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.edustar.jitar.dao.PageDao#getPageByObjectAndName(int, int,
	 *      java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	public Page getPageByObjectAndName(int objType, int objId, String page_name) {
		String query_hql = "FROM Page WHERE objType = ? AND objId = ? AND name = ?";
		List<Page> page_list = this.getSession().createQuery(query_hql).setInteger(0, objType).setInteger(1, objId).setString(2, page_name).list();

		if (page_list == null || page_list.size() == 0)
			return null;
		return page_list.get(0);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.edustar.jitar.dao.PageDao#getWidget(int)
	 */
	public Widget getWidget(int widgetId) {
		return (Widget) this.getSession().get(Widget.class, widgetId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.edustar.jitar.dao.PageDao#setWidgetPosition(cn.edustar.jitar.pojos.Widget,
	 *      int, int)
	 */
	@SuppressWarnings("unchecked")
	public int setWidgetPosition(Widget widget, int columnIndex, int widgetBeforeId) {
		String find_hql = "FROM Widget WHERE pageId = ? AND columnIndex = ? ORDER BY rowIndex ASC, id ASC";
		List<Widget> widget_list = this.getSession().createQuery(find_hql).setInteger(0, widget.getPageId()).setInteger(1, columnIndex).list(); //getSession().find(find_hql, new Object[]{widget.getPageId(), columnIndex});

		int this_row_index = 1;
		int update_count = 0;
		for (Widget witer : widget_list) {
			// 如果找到了插入位置, 则插入 widget 到这个 this_row_index.
			if (witer.getId() == widgetBeforeId) {
				String ins_hql = "UPDATE Widget SET columnIndex = ?, rowIndex = ? WHERE id = ?";
				update_count = this.getSession().createQuery(ins_hql).setInteger(0, columnIndex).setInteger(1, this_row_index).setInteger(2, widget.getId()).executeUpdate();
				//getSession().bulkUpdate(ins_hql, new Object[]{columnIndex, this_row_index, widget.getId()});
				++this_row_index;
			} else if (witer.getId() == widget.getId())
				// 碰到自己忽略.
				continue;

			// 如果应该的位置 和 witer 的位置不符合, 则现在更新它.
			if (this_row_index != witer.getRowIndex()) {
				String move_hql = "UPDATE Widget SET rowIndex = ? WHERE id = ?";
				this.getSession().createQuery(move_hql).setInteger(0, this_row_index).setInteger(1,  witer.getId()).executeUpdate();
				//getSession().bulkUpdate(move_hql, new Object[]{this_row_index, witer.getId()});
			}
			++this_row_index;
		}

		// 可能没找到插入位置, 则插入到末尾.
		if (update_count == 0) {
			String append_hql = "UPDATE Widget SET columnIndex = ?, rowIndex = ? WHERE id = ?";
			update_count = this.getSession().createQuery(append_hql).setInteger(0, columnIndex).setInteger(1, this_row_index).setInteger(2, widget.getId()).executeUpdate();
					
					//getSession().bulkUpdate(append_hql, new Object[]{columnIndex, this_row_index, widget.getId()});
		}
		return update_count;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.edustar.jitar.dao.PageDao#updatePageLayout(int, int)
	 */
	public int updatePageLayout(int pageId, int layoutId) {
		String update_hql = "UPDATE Page SET layoutId = ? WHERE pageId = ? ";
		return this.getSession().createQuery(update_hql).setInteger(0, layoutId).setInteger(1, pageId).executeUpdate();//getSession().bulkUpdate(update_hql, new Object[] {layoutId, pageId});
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.edustar.jitar.dao.PageDao#setPageSkin(int, java.lang.String)
	 */
	public int updatePageSkin(int pageId, String skin) {
		String update_hql = "UPDATE Page SET skin = ? WHERE pageId = ? ";
		return this.getSession().createQuery(update_hql).setString(0, skin).setInteger(1, pageId).executeUpdate(); //getSession().bulkUpdate(update_hql, new Object[] {skin, pageId});
	}
	
	public int updatePageCustomSkin(int pageId, String customSkin)
	{
		String update_hql = "UPDATE Page SET customSkin = ? WHERE pageId = ? ";
		return this.getSession().createQuery(update_hql).setString(0, customSkin).setInteger(1, pageId).executeUpdate(); //(update_hql, new Object[] {customSkin, pageId});
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.edustar.jitar.dao.PageDao#removeWidget(cn.edustar.jitar.pojos.Widget)
	 */
	public void removeWidget(Widget widget) {
		this.getSession().delete(widget);
		this.getSession().flush();
	}

	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.dao.PageDao#deletePageAndWidgetByObject(int, int)
	 */
	@SuppressWarnings("unchecked")
	public void deletePageAndWidgetByObject(int objectType, int objectId) {
		boolean debug = log.isDebugEnabled();
		// 得到该对象的所有页面.
		String hql = "SELECT pageId FROM Page WHERE objType = ? AND objId = ?";
		List<Integer> list = this.getSession().createQuery(hql).setInteger(0, objectType).setInteger(1, objectId).list();//getSession().find(hql, new Object[]{objectType, objectId});
		if (list == null || list.size() == 0) return;
		
		String ids = CommonUtil.toSqlInString(list);
		hql = "DELETE FROM Widget WHERE pageId IN " + ids;
		int count = this.getSession().createQuery(hql).executeUpdate();
		
		hql = "DELETE FROM Page WHERE objType = ? AND objId = ?";
		int count2 = this.getSession().createQuery(hql).setInteger(0, objectType).setInteger(1, objectId).executeUpdate();//getSession().bulkUpdate(hql, new Object[]{objectType, objectId});
		if (debug) {
			log.debug("deletePageAndWidgetByObject objectType = " + objectType +
					", objectId = " + objectId + 
					", DeleteCount(page,widget) = (" + count2 + "," + count + ")");
		}
	}
	
	/**
	 * 得到一个模块的Widget对象定义
	 * @param moduleName
	 * @param pageId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Widget getWidgetByModuleNameAndPageId(String moduleName,int pageId)
	{
		String queryString = "FROM Widget WHERE module = ? And pageId = ?";
		List<Widget> list = (List<Widget>)this.getSession().createQuery(queryString).setString(0, moduleName).setInteger(1, pageId).list();//getSession().find(queryString, new Object[]{moduleName, pageId});
		if (list == null || list.size() == 0) return null;
		return (Widget)list.get(0);
	}
	
	@SuppressWarnings("unchecked")
	public Widget getWidgetByNameAndPageId(String name,int pageId){
		String queryString = "FROM Widget WHERE name = ? And pageId = ?";
		List<Widget> list = (List<Widget>)this.getSession().createQuery(queryString).setString(0, name).setInteger(1, pageId).list();
		if (list == null || list.size() == 0) return null;
		return (Widget)list.get(0);
	}
}
