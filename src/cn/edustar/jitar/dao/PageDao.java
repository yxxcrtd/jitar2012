package cn.edustar.jitar.dao;

import java.util.List;

import cn.edustar.jitar.pojos.Page;
import cn.edustar.jitar.pojos.Widget;

/**
 * Page 数据库访问接口定义.
 * 
 * @author mengxianhui
 */
public interface PageDao {
	/**
	 * 获得指定标识的'Page'对象.
	 * 
	 * @param pageId
	 * @return
	 */
	public Page getPage(int pageId);
	
	/**
	 * 根据用户id得到该用户的首页面信息（在底层页面中显示内容用）.
	 * add by 孟宪会.
	 */
	public Page getUserIndexPage(int userId);
	
	/**
	 * 添加/创建一个页面.
	 * 
	 * @param page
	 */
	public void addPage(Page page);

	/**
	 * 得到指定标识的'widget'对象.
	 * 
	 * @param widgetId
	 * @return
	 */
	public Widget getWidget(int widgetId);

	/**
	 * 保存一个'Widget'对象.
	 * 
	 * @param widget
	 */
	public void saveWidget(Widget widget);
	
	/**
	 * 保存或修改.
	 *
	 * @param widget
	 */
	public void saveOrUpdate(Widget widget);

	/**
	 * 得到指定页面的所有内容块集合.
	 * 
	 * @param pageId
	 * @return
	 */
	public List<Widget> getPageWidgets(int pageId);

	/**
	 * @param userId
	 * @return
	 */
	public List<Page> getPageListByUser(int userId);

	/**
	 * 根据页面所属对象的类型、对象标识和页面名字获得页面.
	 * 
	 * @param objType - 对象类型.
	 * @param objId - 对象标识.
	 * @param page_name - 页面名字，特定的页面如主页名字为 'index'
	 * @return
	 */
	public Page getPageByObjectAndName(int objType, int objId, String page_name);

	/**
	 * 设置一个'Widget'的位置.
	 * 
	 * @param widget - 要放置的'widget'对象.
	 * @param columnIndex - 所在列索引.
	 * @param widgetBeforeId - 插入到这个'widget'之前, 如果不存在则附加到该列最后.
	 * @return 返回设置该 widget 新位置时候的更新记录数, 正常应该 == 1.
	 */
	public int setWidgetPosition(Widget widget, int columIndex, int widgetBeforeId);

	/**
	 * 设置一个页面的布局标识.
	 * 
	 * @param pageId - 页面标识.
	 * @param layoutId - 布局标识.
	 * @return 返回更新的记录数量.
	 */
	public int updatePageLayout(int pageId, int layoutId);

	/**
	 * 设置页面的主题.
	 * 
	 * @param pageId - 页面标识.
	 * @param skin
	 * @return 返回更新的记录数量.
	 */
	public int updatePageSkin(int pageId, String skin);
	
	/**
	 * 设置用户自定义样式
	 */
	public int updatePageCustomSkin(int pageId, String customSkin);
	
	/**
	 * 删除模块.
	 *
	 * @param widget
	 */
	public void removeWidget(Widget widget);
	
	/**
	 * 删除指定类型指定标识的对象的所有页面和内容块, 一般用于删除对象的时候调用.
	 * @param objectType
	 * @param objectId
	 */
	public void deletePageAndWidgetByObject(int objectType, int objectId);
	
	/**
	 * 得到一个模块的Widget对象定义
	 * @param moduleName
	 * @param pageId
	 * @return
	 */
	public Widget getWidgetByModuleNameAndPageId(String moduleName,int pageId);
	public Widget getWidgetByNameAndPageId(String name,int pageId);
}
