package cn.edustar.jitar.service;

import java.util.List;

import cn.edustar.jitar.model.ObjectType;
import cn.edustar.jitar.pojos.Page;
import cn.edustar.jitar.pojos.User;
import cn.edustar.jitar.pojos.Widget;

/**
 * Page, Widget 服务接口定义.
 * 
 * @author mengxianhui
 */
public interface PageService {
	/** 用户主页、协作组首页的页面名字 */
	public static final String INDEX_PAGE_NAME = "index";
	
		/**
	 * 添加一个页面.
	 * 
	 * @param page
	 */
	public void addPage(Page page);

	/**
	 * 得到指定用户的页面集合.
	 * 
	 * @param userId
	 * @return
	 */
	public List<Page> getPageListByUser(int userId);
	
	/**
	 * 得到指定标识的 Page 对象.
	 * 
	 * @param pageId
	 * @return
	 */
	public Page getPage(int pageId);

	/**
	 * 根据指定的键得到页面对象. (注意:此方法带有缓存).
	 * 
	 * @param key
	 * @return
	 */
	public Page getPageByKey(PageKey key);

	/**
	 * 得到指定用户的主页, 实际调用 getPageByKey 所以也带有缓存处理.
	 * @param user
	 * @return
	 */
	public Page getUserIndexPage(User user);
	
	/**
	 * 得到指定页面的所有内容块集合.
	 * 
	 * @param pageId
	 * @return
	 */
	public List<Widget> getPageWidgets(int pageId);

	/**
	 * 复制一个页面及其所有widgets到目标.
	 * 
	 * @param src_pk 源页面键.
	 * @param dest_pk 目标页面键.
	 * @param title 新的页面的标题.
	 */
	public void duplicatePage(PageKey src_pk, PageKey dest_pk, String title);


	/**
	 * 得到指定标识的 Widget 对象.
	 * 
	 * @param widgetId 对象标识.
	 * @return 返回该标识的 Widget 对象; 如果没有则返回 null.
	 */
	public Widget getWidget(int widgetId);

	/**
	 * 设置一个 Widget 的位置.
	 * 
	 * @param widget 内容块对象.
	 * @param colIndex 列索引.
	 * @param widgetBeforeId 插入到这个'widget'之前,如果不存在则放在该列最后.
	 */
	public void setWidgetPosition(Widget widget, int colIndex, int widgetBeforeId);

	/**
	 * 设置页面的布局属性.
	 * 
	 * @param page
	 * @param layoutId
	 */
	public void setPageLayout(Page page, int layoutId);

	/**
	 * 设置页面的主题.
	 * 
	 * @param page
	 * @param skin
	 */
	public void setPageSkin(Page page, String skin);
	
	public void setPageCustomSkin(Page page, String customSkin);
	/**
	 * 添加模块.
	 *
	 * @param widget
	 */
	public void saveWidget(Widget widget);
	
	/**
	 * 删除模块.
	 *
	 * @param widgetId
	 */
	public void removeWidget(int widgetId);
	
	/**
	 * 保存或修改.
	 *
	 * @param widget
	 */
	public void saveOrUpdate(Widget widget);

	/**
	 * 删除指定类型指定标识的对象的所有页面和内容块, 一般用于删除对象的时候调用.
	 * @param objectType
	 * @param objectId
	 */
	public void deletePageAndWidgetByObject(ObjectType objectType, int objectId);
	
	/**
	 * 得到一个模块的Widget对象定义
	 * @param moduleName
	 * @param pageId
	 * @return
	 */
	public Widget getWidgetByModuleNameAndPageId(String module,int pageId);
	public Widget getWidgetByNameAndPageId(String name,int pageId);
}
