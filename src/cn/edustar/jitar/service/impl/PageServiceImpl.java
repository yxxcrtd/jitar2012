package cn.edustar.jitar.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.edustar.jitar.JitarException;
import cn.edustar.jitar.dao.PageDao;
import cn.edustar.jitar.model.ObjectType;
import cn.edustar.jitar.pojos.Page;
import cn.edustar.jitar.pojos.User;
import cn.edustar.jitar.pojos.Widget;
import cn.edustar.jitar.service.CacheService;
import cn.edustar.jitar.service.PageKey;
import cn.edustar.jitar.service.PageService;

/**
 * Page 服务实现
 * 
 * @author mengxianhui
 */
public class PageServiceImpl implements PageService {

	/** 文章记录器 */
	private static final Log logger = LogFactory.getLog(PageServiceImpl.class);

	/** 数据访问实现对象。 */
	private PageDao page_dao;

	/** 缓存服务 */
	private CacheService cache_svc = NoCacheServiceImpl.INSTANCE;

	/** 数据访问实现对象。 */
	public void setPageDao(PageDao pp_dao) {
		this.page_dao = pp_dao;
	}

	/** 缓存服务 */
	public void setCacheService(CacheService cache_svc) {
		this.cache_svc = cache_svc;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.edustar.jitar.service.PageService#getPage(int)
	 */
	public Page getPage(int pageId) {
		Page page = page_dao.getPage(pageId);
		return page;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.edustar.jitar.service.PageService#getPageByKey(cn.edustar.jitar.service.PageKey)
	 */
	public Page getPageByKey(PageKey key) {
		if (key == null)
			throw new IllegalArgumentException("key == null");

		// 1. 从缓存中找, cacheKey = 'p_100_3_index'
		String cacheKey = calcCacheKey(key);
		Page page = (Page) cache_svc.get(cacheKey);
		if (page != null)
			return page;

		// 没找到则现在去获取.
		page = page_dao.getPageByObjectAndName(key.getObjectType().getTypeId(), key.getObjectId(), key.getPageName());
		if (page == null)
			return null;

		// 放到缓存中, 并返回.
		putPageToCache(page);
		return page;
	}

	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.service.PageService#getUserIndexPage(cn.edustar.jitar.pojos.User)
	 */
	public Page getUserIndexPage(User user) {
		PageKey pk = new PageKey(ObjectType.OBJECT_TYPE_USER, user.getUserId(), INDEX_PAGE_NAME);
		return getPageByKey(pk);
	}
	
	
	

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.edustar.jitar.service.PageManageService#addPage(cn.edustar.jitar.pojos.Page)
	 */
	public void addPage(Page page) {
		page_dao.addPage(page);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.edustar.jitar.service.iface.PortalPageService#getPortalPageWidgets(int)
	 */
	public List<Widget> getPageWidgets(int pageId) {
		return page_dao.getPageWidgets(pageId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.edustar.jitar.service.PageManageService#getPageListByUser(int)
	 */
	public List<Page> getPageListByUser(int userId) {
		return page_dao.getPageListByUser(userId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.edustar.jitar.service.PageService#duplicatePage(cn.edustar.jitar.service.PageKey, cn.edustar.jitar.service.PageKey)
	 */
	public void duplicatePage(PageKey src_pk, PageKey dest_pk, String title) {
		Page src_page = null;
		if (logger.isDebugEnabled()) {
			logger.debug("复制页面: src_pk=" + src_pk + ",dest_pk=" + dest_pk + ",title=" + title);
		}
		
		Object obj = cache_svc.get("p_" +src_pk.getObjectId()+ "_" + src_pk.getObjectType().getTypeId()  + "_" + src_pk.getPageName());
		if(null!=obj){  //这里更改了一下,先从cache里面拿,没有在查询
			src_page = (Page)obj;
		}else{
			// 得到源页面.
			src_page = this.page_dao.getPageByObjectAndName(src_pk.getObjectType().getTypeId(), src_pk.getObjectId(), src_pk.getPageName());
		}
		
		if (src_page == null)
			throw new JitarException("无法找到源页面：src_page_key = " + src_pk);
		putPageToCache(src_page);

		// 得到源页面的所有 widgets.
		List<Widget> widget_list = this.page_dao.getPageWidgets(src_page.getPageId());

		//复制源页面到目标页面.
		Page dest_page = new Page();
		dest_page.setName(dest_pk.getPageName());
		dest_page.setTitle(title);
		dest_page.setObjType(dest_pk.getObjectType().getTypeId());
		dest_page.setObjId(dest_pk.getObjectId());
		dest_page.setDescription("Copy from: " + src_page.getDescription());
		dest_page.setCreateDate(new Date());
		dest_page.setItemOrder(src_page.getItemOrder());
		dest_page.setLayoutId(src_page.getLayoutId());
		dest_page.setSkin(src_page.getSkin());
		dest_page.setCustomSkin(src_page.getCustomSkin());
		page_dao.addPage(dest_page);

		// 复制所属 widgets.
		for (int i = 0; i < widget_list.size(); ++i) {
			Widget src_w = widget_list.get(i);
			Widget dest_w = new Widget();
			dest_w.setName(src_w.getName());
			dest_w.setTitle(src_w.getTitle());
			dest_w.setCreateDate(new Date());
			dest_w.setPageId(dest_page.getPageId());
			dest_w.setData(src_w.getData());
			dest_w.setIsHidden(src_w.getIsHidden());
			dest_w.setItemOrder(src_w.getItemOrder());
			dest_w.setColumnIndex(src_w.getColumnIndex());
			dest_w.setRowIndex(src_w.getRowIndex());
			dest_w.setCustomTemplate(src_w.getCustomTemplate());
			dest_w.setModule(src_w.getModule());
			page_dao.saveWidget(dest_w);
		}
		if (logger.isDebugEnabled()) {
			logger.debug("复制成功: src_pk=" + src_pk + ",dest_pk=" + dest_pk);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.edustar.jitar.service.PageService#getWidget(int)
	 */
	public Widget getWidget(int widgetId) {
		return page_dao.getWidget(widgetId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.edustar.jitar.service.PageService#setWidgetPosition(cn.edustar.jitar.pojos.Widget, int, int)
	 */
	public void setWidgetPosition(Widget widget, int colIndex, int widgetBeforeId) {
		page_dao.setWidgetPosition(widget, colIndex, widgetBeforeId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.edustar.jitar.service.PageService#setPageLayout(cn.edustar.jitar.pojos.Page, int)
	 */
	public void setPageLayout(Page page, int layoutId) {
		page_dao.updatePageLayout(page.getPageId(), layoutId);
		removePageCache(page);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.edustar.jitar.service.PageService#setPageSkin(cn.edustar.jitar.pojos.Page, java.lang.String)
	 */
	public void setPageSkin(Page page, String skin) {
		if (skin == null || skin.length() == 0)
			skin = "default";
		page_dao.updatePageSkin(page.getPageId(), skin);
		removePageCache(page);
	}
  
	public void setPageCustomSkin(Page page, String customSkin) {
		if (customSkin == null || customSkin.length() == 0)
			return;
		page_dao.updatePageCustomSkin(page.getPageId(), customSkin);
		removePageCache(page);
	}
	
	/**
	 * 将指定页面放到缓存中.
	 * 
	 * @param page
	 */
	private void putPageToCache(Page page) {
		if (page == null)
			return;
		String key = calcCacheKey(page);
		this.cache_svc.put(key, page);
	}

	/**
	 * 删除指定页面的缓存
	 * 
	 * @param page
	 */
	private void removePageCache(Page page) {
		this.cache_svc.remove(calcCacheKey(page));
	}

	// 通过 page, pagekey 计算放在缓存中的键, 两个函数必须保证完全一致.
	private static final String calcCacheKey(PageKey key) {
		return "p_" + key.getObjectId() + "_" + key.getObjectType().getTypeId() + "_" + key.getPageName();
	}

	/**
	 *
	 * @param page
	 * @return
	 */
	private static final String calcCacheKey(Page page) {
		return "p_" + page.getObjId() + "_" + page.getObjType() + "_" + page.getName();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see cn.edustar.jitar.service.PageService#saveWidget(cn.edustar.jitar.pojos.Widget)
	 */
	public void saveWidget(Widget widget) {
		this.page_dao.saveWidget(widget);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see cn.edustar.jitar.service.PageService#removeWidget(int)
	 */
	public void removeWidget(int widgetId) {
		Widget widget = this.page_dao.getWidget(widgetId);
		if(widget != null){
			this.page_dao.removeWidget(widget);
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see cn.edustar.jitar.service.PageService#saveOrUpdate(cn.edustar.jitar.pojos.Widget)
	 */
	public void saveOrUpdate(Widget widget) {
		this.page_dao.saveOrUpdate(widget);
	}

	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.service.PageService#deletePageAndWidgetByObject(cn.edustar.jitar.model.ObjectType, int)
	 */
	public void deletePageAndWidgetByObject(ObjectType objectType, int objectId) {
		this.page_dao.deletePageAndWidgetByObject(objectType.getTypeId(), objectId);
	}
	
	/**
	 * 得到一个模块的Widget对象定义
	 * @param moduleName
	 * @param pageId
	 * @return
	 */
	public Widget getWidgetByModuleNameAndPageId(String module,int pageId)
	{
		return this.page_dao.getWidgetByModuleNameAndPageId(module, pageId);
	}
	public Widget getWidgetByNameAndPageId(String name,int pageId){
		return this.page_dao.getWidgetByNameAndPageId(name, pageId);
	}
}
