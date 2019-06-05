package cn.edustar.jitar.util;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.springframework.web.context.ContextLoader;

import com.alibaba.fastjson.JSONObject;

import cn.edustar.jitar.JitarContext;
import cn.edustar.jitar.model.ObjectType;
import cn.edustar.jitar.pojos.Category;
import cn.edustar.jitar.pojos.Group;
import cn.edustar.jitar.pojos.Page;
import cn.edustar.jitar.pojos.PrepareCourse;
import cn.edustar.jitar.pojos.User;
import cn.edustar.jitar.pojos.Widget;
import cn.edustar.jitar.service.CacheService;
import cn.edustar.jitar.service.CategoryService;
import cn.edustar.jitar.service.PageKey;
import cn.edustar.jitar.service.PageService;

//page 检测辅助类, 可帮助检测用户页, 协作组页中对象状态并返回合适的信息.
public class PageCheckMixiner {
    private static PageService pageService = null;
    private static HttpServletRequest request = null;
    /** 缓存服务 */
    private static CacheService cache_svc = null;
    static {
        pageService = ContextLoader.getCurrentWebApplicationContext().getBean("pageService", PageService.class);
    }

    // 得到指定页面 page 的所有 widget 放到 request 环境中, 然后返回指定的 ftl模板名.
    public static String getWidgetsAndReturn(Page page, String ftl) {
        String key =String.valueOf(page.getId());
        request = ServletActionContext.getRequest();
        cache_svc = JitarContext.getCurrentJitarContext().getCacheProvider().getCache(key);
        List<Widget> widget_list = null;
        Object obj = cache_svc.get(key);
        if (null != obj) {
            widget_list = (List<Widget>) obj;
        } else {
            widget_list = getPageWidgets(page);
            cache_svc.put(key, widget_list);
        }
        // # print "self.widget_list = ", self.widget_list
        request.setAttribute("widget_list", widget_list);
        request.setAttribute("widgets", widget_list);
        return ftl;
    }

    // 得到指定用户的首页, 如果不存在则从母版复制一份.
    public static Page getUserIndexPage(User user) {
        PageKey index_pk = new PageKey(ObjectType.OBJECT_TYPE_USER, user.getUserId(), "index");
        Page page = pageService.getPageByKey(index_pk);
        if (page != null) {
            return page;
        }
        return createUserIndexPage(user);
    }

    // 创建/复制协作组首页, 并返回复制的页面.
    public Page createGroupIndexPage(Group group) {
        PageKey src_pk = PageKey.SYSTEM_GROUP_INDEX;
        PageKey dest_pk = new PageKey(ObjectType.OBJECT_TYPE_GROUP, group.getGroupId(), "index");
        String title = group.getGroupTitle();
        pageService.duplicatePage(src_pk, dest_pk, title);
        return pageService.getPageByKey(dest_pk);
    }

    // 得到指定用户文章显示所用的系统页, 其中 skin 被设置为该用户的.
    public Page getUserEntryPage(User user) {
        return getSystemPageWithUserSkin(PageKey.SYSTEM_USER_ENTRY, user);
    }

    // 得到指定用户文章分类显示所用的系统页, 其中 skin 被设置为该用户的.
    public Page getUserArticleCategoryPage(User user) {
        return getSystemPageWithUserSkin(PageKey.SYSTEM_USER_CATEGORY, user);
    }

    // 得到指定用户资源分类显示所用的系统页, 其中 skin 被设置为该用户的.
    public Page getUserResourceCategoryPage(User user) {
        return getSystemPageWithUserSkin(PageKey.SYSTEM_USER_RESCATE, user);
    }

    // 得到指定用户视频分类页面分类显示所用的系统页, 其中 skin 被设置为该用户的.
    public Page getUserVideoCategoryPage(User user) {
        return getSystemPageWithUserSkin(PageKey.SYSTEM_USER_VIDEOCATE, user);
    }

    // 得到指定用户的用户档案显示系统页, 其中 skin 被设置为该用户的.
    public Page getUserProfilePage(User user) {
        return getSystemPageWithUserSkin(PageKey.SYSTEM_USER_PROFILE, user);
    }

    // 得到指定协作组的首页, 如果不存在, 则立刻从母版复制一份.
    public Page getGroupIndexPage(Group group) {
        // # 协作组类型 + 协作组标识 + 'index'
        PageKey index_pk = new PageKey(ObjectType.OBJECT_TYPE_GROUP, group.getGroupId(), "index");
        Page page = pageService.getPageByKey(index_pk);
        if (page != null) {
            return page;
        }
        // 创建出来, 并返回.
        return createGroupIndexPage(group);
    }

    // 得到集体备课的首页
    public Page getPrepareCourseIndexPage(PrepareCourse prepareCourse) {
        // 集体备课类型 + 集体备课标识+ 'index'
        PageKey index_pk = new PageKey(ObjectType.OBJECT_TYPE_PREPARECOURSE, prepareCourse.getPrepareCourseId(), "index");
        Page page = pageService.getPageByKey(index_pk);
        if (null != page) {
            return page;
        }
        // 创建出来, 并返回.
        return createPrepareCourseIndexPage(prepareCourse);
    }

    // 得到指定协作组文章分类显示所用的系统页, 其中 skin 被设置为该协作组的.
    public Page getGroupArticleCategoryPage(Group group) {
        return getSystemPageWithGroupSkin(PageKey.SYSTEM_GROUP_ARTICLE_CATEGORY, group);
    }

    // 得到指定协作组资源分类显示所用的系统页, 其中 skin 被设置为该协作组的.
    public Page getGroupResourceCategoryPage(Group group) {
        return getSystemPageWithGroupSkin(PageKey.SYSTEM_GROUP_RESOURCE_CATEGORY, group);
    }

    // 得到指定的系统页, 并附加上指定协作组的首页的皮肤属性.
    public Page getSystemPageWithGroupSkin(PageKey pk, Group group) {
        // 得到此系统页面.
        Page sys_page = pageService.getPageByKey(pk);
        if (sys_page == null) {
            return null;
        }

        // 得到协作组主页.
        Page index_page = getGroupIndexPage(group);

        // 创建新页面.
        Page new_page = sys_page._getPageObject().clone();
        if (index_page != null) {
            new_page.setSkin(index_page.getSkin());
        }
        return new_page;
    }

    // // 创建/复制集体备课首页, 并返回复制的页面.
    private Page createPrepareCourseIndexPage(PrepareCourse prepareCourse) {
        PageKey src_pk = PageKey.SYSTEM_GROUP_INDEX;
        PageKey dest_pk = new PageKey(ObjectType.OBJECT_TYPE_PREPARECOURSE, prepareCourse.getPrepareCourseId(), "index");
        String title = prepareCourse.getTitle();
        pageService.duplicatePage(src_pk, dest_pk, title);
        return pageService.getPageByKey(dest_pk);
    }

    private Page getSystemPageWithUserSkin(PageKey pk, User user) {
        // 得到此系统页面.
        Page sys_page = pageService.getPageByKey(pk);
        if (sys_page == null) {
            return null;
        }
        // 得到用户个人主页.
        Page index_page = pageService.getUserIndexPage(user._getUserObject());

        // 创建新页面.
        Page new_page = sys_page._getPageObject().clone();
        if (index_page != null) {
            new_page.setSkin(index_page.getSkin());
            new_page.setCustomSkin(index_page.getCustomSkin());
            if (index_page.getCustomSkin() != null) {
                Object customSkin = JSONObject.parse(index_page.getCustomSkin());
                request.setAttribute("customSkin", customSkin);
            }
        }
        return new_page;
    }

    // 创建/复制用户首页, 并返回复制的页面.
    public static Page createUserIndexPage(User user) {
        PageKey src_pk = PageKey.SYSTEM_USER_INDEX;
        PageKey dest_pk = new PageKey(ObjectType.OBJECT_TYPE_USER, user.getUserId(), "index");
        String title = user.getBlogName();
        if (title == null || title.trim().equals("")) {
            title = user.getNickName() + " 的工作室";
        }
        pageService.duplicatePage(src_pk, dest_pk, title);
        Page p = pageService.getPageByKey(dest_pk);
        //焦作的个人页面需要增加 专题研讨、互动教学、教师培训 这三个 自定义文章分类模块模式
        AddUserCategoryWidget(user , p);
        return p;
    }
    
    /**
     * 焦作的个人页面需要增加 专题研讨、互动教学、教师培训 这三个 自定义文章分类模块模式
     * @param user
     * @param page
     */
    private static void AddUserCategoryWidget(User user,Page page){
		String itemType = CommonUtil.toUserArticleCategoryItemType(user.getUserId());
		CategoryService cate_svc = ContextLoader.getCurrentWebApplicationContext().getBean("categoryService", CategoryService.class);
		Category cate =  cate_svc.getCategory("教师培训", itemType, true);
		if(null != cate){
			Widget dest_w = getUserCategoryWidget(cate,page.getPageId(),0,0);
	    	pageService.saveWidget(dest_w);
		}
		cate =  cate_svc.getCategory("互动教学", itemType, true);
		if(null != cate){
			Widget dest_w = getUserCategoryWidget(cate,page.getPageId(),0,1);
	    	pageService.saveWidget(dest_w);
		}
		cate =  cate_svc.getCategory("专题研讨", itemType, true);
		if(null != cate){
			Widget dest_w = getUserCategoryWidget(cate,page.getPageId(),0,2);
	    	pageService.saveWidget(dest_w);
		}
    }
    
    private static Widget getUserCategoryWidget(Category cate,int pageId,int ColumnIndex,int RowIndex){
    	Widget dest_w = new Widget();
		dest_w.setName("category_article");
		dest_w.setTitle("特定分类文章");
		dest_w.setCreateDate(new Date());
		dest_w.setPageId(pageId);
		dest_w.setData("\"categoryId\":\""+ cate.getCategoryId() +"\",\"count\":\"10\",\"title\":\""+cate.getName()+"\"");
		dest_w.setIsHidden(false);
		dest_w.setItemOrder(0);
		dest_w.setColumnIndex(ColumnIndex);
		dest_w.setRowIndex(RowIndex);
		dest_w.setCustomTemplate(null);
		dest_w.setModule("category_article");
		return dest_w;
    }
    
    // # 得到指定页面的所有功能块.
    private static List<Widget> getPageWidgets(Page page) {
        return pageService.getPageWidgets(page.getPageId());
    }

    public static void setPageService(PageService pageService) {
        PageCheckMixiner.pageService = pageService;
    }
}
