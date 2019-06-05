package cn.edustar.jitar.action;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.http.HttpServletResponse;

import cn.edustar.jitar.model.ObjectType;
import cn.edustar.jitar.module.Module;
import cn.edustar.jitar.module.ModuleIcon;
import cn.edustar.jitar.pojos.GroupMember;
import cn.edustar.jitar.pojos.Page;
import cn.edustar.jitar.pojos.PrepareCourse;
import cn.edustar.jitar.pojos.Widget;
import cn.edustar.jitar.service.AccessControlService;
import cn.edustar.jitar.service.GroupService;
import cn.edustar.jitar.service.ModuleContainer;
import cn.edustar.jitar.service.PageService;
import cn.edustar.jitar.service.PrepareCourseService;
import cn.edustar.jitar.util.CommonUtil;
import cn.edustar.jitar.util.FileCache;

/**
 * 页面管理.
 * 
 * @author mengxianhui
 * @author Yang Xinxin
 * @version 1.0.0 Jun 24, 2008 3:04:28 PM
 * 支持的操作:
 *   1. 编辑通用类型的widget的data数据 - update_data:
 *     page.action?cmd=update_data&widgetId=$widget的id
 *     post的内容为json对象：表单变量为：data
 *     内容是json格式的字符串.
 *   2. 删除 widget.
 *     page.action?cmd=delete_widget&widgetId=$10
 *     widgetId：要删除的widget的id
 *   3. 添加模块.
 *     page.action?cmd=add_widget&pageId=100&module=rss
 *     pageId: 页面id .
 *     module: 模块英文名称.
 *     也可以识别提交上来的 data 字段, 其存放在 widget data 属性中.
 *   4. 移动功能块.
 *      page.action?cmd=move_widget&widgetId=$widgetId&col=所在列标识&wbi=插入到此块前面.
 *   5. 设置主题.
 *      page.action?cmd=set_skin&pageId=$pageId&skin=$skin
 *      pageId:页面id .
 *      skin:主题名称.
 *   6. 设置布局.
 *      page.action?cmd=set_layout&pageId=$pageId&layoutId=1
 *      pageId: 页面id.
 *      layoutId:代表布局的数字.
 *     
 */
public class PageAction extends ManageBaseAction {
	/** serialVersionUID */
	private static final long serialVersionUID = 661814828085103764L;

	/** 页面服务 */
	private PageService page_svc;
	
	/** 协作组服务 */
	private GroupService group_svc;
	
	/** 集备服务 */	
	private PrepareCourseService prepareCourseService;
	
	private AccessControlService accessControlService;
	
	public void setAccessControlService(AccessControlService accessControlService) {
		this.accessControlService = accessControlService;
	}

	/** 模块容器服务 */
	private ModuleContainer mod_cont;

	/** execute() 的返回值. */
	private String returnResult = NONE;
	
	
	
	/**
	 * 构造.
	 */
	public PageAction() {
		
	}

	/** 页面服务 */
	public void setPageService(PageService pageService) {
		this.page_svc = pageService;
	}
	
	/** 协作组服务 */
	public void setGroupService(GroupService group_svc) {
		this.group_svc = group_svc;
	}

	/** 模块容器服务 */
	public void setModuleContainer(ModuleContainer mod_cont) {
		this.mod_cont = mod_cont;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.edustar.jitar.action.ManageBaseAction#execute(java.lang.String)
	 */
	@Override
	public String execute(String cmd) throws Exception {
		
		// 新做的.
		if ("update_data".equals(cmd))
			return update_data();
		else if ("add_widget".equals(cmd))
			return add_widget();
		else if ("delete_widget".equals(cmd))
			return delete_widget();
		else if ("move_widget".equals(cmd))
			return move_widget();
		else if ("set_skin".equals(cmd))
			return set_skin();
		else if ("set_layout".equals(cmd))
			return set_layout();

		return ajax_response(HttpServletResponse.SC_BAD_REQUEST, "未知请求");
	}

	/**
	 * 更新指定功能块的数据 data 字段, 该字段用 json 格式保存.
	 * @return
	 * @throws IOException
	 */
	private String update_data() throws IOException {
		if (isUserLogined() == false) 
			return ajax_response(HttpServletResponse.SC_FORBIDDEN, "用户未登录");

		// 得到参数.
		int widgetId = param_util.getIntParam("widgetId");
		String title = param_util.safeGetStringParam("title");
		String data = param_util.safeGetStringParam("data");
		
		// 得到内容块对象.
		Widget widget = page_svc.getWidget(widgetId);
		if (widget == null) 
			return ajax_response(HttpServletResponse.SC_NOT_FOUND, "未找到指定标识的功能块");
		
		// 得到其所属页面, == null 是不应该发生的, 因为在 Page, Widget 表上面有约束.
		Page page = getPageAndCheckRight(widget.getPageId());
		if (page == null) return this.returnResult;
		
		// 修改该功能块.
		if (title != null && title.length() > 0)
			widget.setTitle(title);
		widget.setData(data);
		page_svc.saveOrUpdate(widget);
		super.removePageId(widget.getPageId());
		try
		{
			FileCache fc = new FileCache();
			fc.deleteUserAllCache(getLoginUser().getLoginName());
			fc = null;
		}
		catch(Exception ex){}
		return ajax_success();
	}
	
	/**
	 * 在指定页面添加模块.
	 * @return
	 * @throws IOException
	 */
	private String add_widget() throws IOException {
		if (isUserLogined() == false) 
			return ajax_response(HttpServletResponse.SC_FORBIDDEN, "用户未登录");
		
		// 得到参数.
		int pageId = param_util.getIntParam("pageId");
		String moduleName = param_util.safeGetStringParam("module");
		String moduleTitle = CommonUtil.unescape(param_util.safeGetStringParam("title"));
		String data = param_util.safeGetStringParam("data");
		
		// 得到页面.
		Page page = getPageAndCheckRight(pageId);
		if (page == null) return this.returnResult;
	
		// 创建 widget 并保存.
		if (moduleTitle == null || moduleTitle.length() == 0)
			moduleTitle = getModuleTitleByName(moduleName);
		Widget widget = new Widget();
		widget.setName(moduleName);
		widget.setTitle(moduleTitle);
		widget.setModule(moduleName);
		widget.setPageId(pageId);
		widget.setData(data);
		widget.setRowIndex(0);
		widget.setColumnIndex(0);
		widget.setItemOrder(0);
		
		ModuleIcon mi = new ModuleIcon();
		String icon = mi.getModuleIcon(moduleName);
		mi = null;
		if(icon != null) widget.setIcon(icon);
		page_svc.saveWidget(widget);
		super.removePageId(widget.getPageId());
		return ajax_success();
	}
	
	/**
	 * 删除指定标识的 widget.
	 * @return
	 * @throws IOException
	 */
	private String delete_widget() throws IOException {
		if (isUserLogined() == false) 
			return ajax_response(HttpServletResponse.SC_FORBIDDEN, "用户未登录");

		// 得到参数.
		int widgetId = param_util.getIntParam("widgetId");
		
		// 得到内容块对象.
		Widget widget = page_svc.getWidget(widgetId);
		if (widget == null) 
			return ajax_response(HttpServletResponse.SC_NOT_FOUND, "未找到指定标识的功能块");
		
		// 得到其所属页面, == null 是不应该发生的, 因为在 Page, Widget 表上面有约束.
		Page page = getPageAndCheckRight(widget.getPageId());
		if (page == null) return this.returnResult;

		// 实际删除该功能块.
		page_svc.removeWidget(widgetId);
		super.removePageId(widget.getPageId());
		return ajax_success();
	}
	
	/**
	 * 移动模块.
	 * URL: 'page.action?cmd=movewidget&pageId=$pageId&widgetId=$widgetId&col=$column&wbi=$widgetBeforeId'
	 * @return
	 */
	private String move_widget() throws IOException {
		if (isUserLogined() == false) 
			return ajax_response(HttpServletResponse.SC_FORBIDDEN, "用户未登录");

		// 得到参数并验证.
		int widgetId = param_util.getIntParam("widgetId");
		int colIndex = param_util.getIntParam("col");
		int widgetBeforeId = param_util.getIntParam("wbi");
		
		// 找到 widget.
		Widget widget = page_svc.getWidget(widgetId);
		if (widget == null) 
			return ajax_response(HttpServletResponse.SC_NOT_FOUND, "未找到指定标识的功能块");
		
		// 得到其所属页面, == null 是不应该发生的, 因为在 Page, Widget 表上面有约束.
		Page page = getPageAndCheckRight(widget.getPageId());
		if (page == null) return this.returnResult;
		
		// 实际进行 'set' 位置操作
		page_svc.setWidgetPosition(widget, colIndex, widgetBeforeId);
		super.removePageId(widget.getPageId());
		// 返回操作成功.
		return ajax_success();
	}
	
	/**
	 * 设置指定页面的皮肤.
	 * @return
	 * @throws IOException
	 */
	private String set_skin() throws IOException {
		if (isUserLogined() == false) 
			return ajax_response(HttpServletResponse.SC_FORBIDDEN, "用户未登录");

		// 得到参数并验证.
		int pageId = param_util.getIntParam("pageId");
		String skin = param_util.safeGetStringParam("skin", "");
		if (CommonUtil.isEmptyString(skin)) skin = "skin1";
		
		// 得到页面.
		Page page = getPageAndCheckRight(pageId);
		if (page == null) return this.returnResult;

		// 实际执行.
		page_svc.setPageSkin(page, skin);
		super.removePageId(page.getPageId());
		return ajax_success();
	}
	
	/**
	 * 设置指定页面的布局标识.
	 * @return
	 * @throws IOException
	 */
	private String set_layout() throws IOException {
		if (isUserLogined() == false) 
			return ajax_response(HttpServletResponse.SC_FORBIDDEN, "用户未登录");

		// 得到参数并验证.
		int pageId = param_util.getIntParam("pageId");
		int layoutId = param_util.getIntParam("layoutId");
		
		// 得到页面.
		Page page = getPageAndCheckRight(pageId);
		if (page == null) return this.returnResult;

		// 实际执行.
		page_svc.setPageLayout(page, layoutId);
		super.removePageId(page.getPageId());

		return ajax_success();
	}
	
	/**
	 * 向 client 返回指定的信息.
	 * @param errCode
	 * @param msg
	 * @return
	 * @throws IOException
	 */
	private String ajax_response(int errCode, String msg) throws IOException {
		response.setContentType("text/html;charset=UTF-8");
		response.getWriter().write("" + errCode + " " + msg);
		return NONE;
	}

	/**
	 * 向 client 返回操作成功信息 SC_OK, "OK" .
	 * @return
	 * @throws IOException
	 */
	private String ajax_success() throws IOException {
		// '200 OK'
		return ajax_response(HttpServletResponse.SC_OK, "OK");
	}
	
	/** 协作组服务 */
	/**
	 * 得到指定标识的页面并验证是否有权限.
	 */
	private Page getPageAndCheckRight(int pageId) throws IOException {
		// 判断页面参数是否正确.
		if (pageId == 0) {
			returnResult = ajax_response(HttpServletResponse.SC_BAD_REQUEST, "未给出页面标识");
			return null;
		}
		
		// 得到页面.
		Page page = page_svc.getPage(pageId);
		if (page == null) {
			returnResult = ajax_response(HttpServletResponse.SC_NOT_FOUND, "未找到指定标识的页面");
			return null;
		}
		
		// 验证是否有权限修改此页面.
		if (checkRight(page) == false) {
			returnResult = ajax_response(HttpServletResponse.SC_FORBIDDEN, "没有修改此页面/功能块的权限");
			return null;
		}
		
		return page;
	}
	/** 协作组服务 */

	/**
	 * 检查当前登录用户是否对指定页面具有操作权限.
	 * @param oper
	 * @return 返回 true 表示有权限; 返回 false 表示没有权限.
	 * @remark 当前业务设计
	 *   1. 如果是系统管理员, 则对任何页面均有权限, 包括系统页面.
	 *   2. 普通用户能够修改自己的页面.
	 *   3. 协作组管理员能够修改协作组页面.
	 *   4. 其它形态页面, 当前没有权限.
	 *   5，对于集备，只有创建者、主备人有权限
	 */
	private boolean checkRight(Page page) {
		if (isUserLogined() == false) return false;
		if (page == null) return false;
		
		// 如果是系统管理员, 则对任何页面均有权限, 包括系统页面.
		if (this.accessControlService.isSystemAdmin(getLoginUser()))
			return true;
		
		// 普通用户能够修改自己的页面.
		if (page.getObjType() == ObjectType.OBJECT_TYPE_USER.getTypeId() 
				&& page.getObjId() == getLoginUser().getUserId()) {
			return true;
		}
		
		// 协作组管理员能够修改协作组页面.
		if (page.getObjType() == ObjectType.OBJECT_TYPE_GROUP.getTypeId()) {
			GroupMember gm = group_svc.getGroupMemberByGroupIdAndUserId(page.getObjId(), 
					getLoginUser().getUserId());
			if (gm == null) return false;
			if (gm.getGroupRole() == GroupMember.GROUP_ROLE_MANAGER || gm.getGroupRole() == GroupMember.GROUP_ROLE_VICE_MANAGER) return true;
			return false;
		}
		
		// 集体备课权限，主备人、创建人可以操作。 
		if(page.getObjType() == ObjectType.OBJECT_TYPE_PREPARECOURSE.getTypeId())
		{
			PrepareCourse prepareCourse = this.prepareCourseService.getPrepareCourse(page.getObjId());
			if(prepareCourse == null) return false;
			if(prepareCourse.getCreateUserId() == getLoginUser().getUserId() || prepareCourse.getLeaderId() == getLoginUser().getUserId())
			{
				return true;
			}
		}
		// 其它形态页面, 当前没有权限.
		
		return false;
	}

	/**
	 * 通过模块名字获取其缺省中文标题.
	 * @param module_name
	 * @return
	 */
	private String getModuleTitleByName(String module_name) {
		Module module = mod_cont.getModule(module_name);
		if (module == null) return module_name;
		return module.getModuleTitle();
	}

	public PrepareCourseService getPrepareCourseService() {
		return prepareCourseService;
	}

	public void setPrepareCourseService(PrepareCourseService prepareCourseService) {
		this.prepareCourseService = prepareCourseService;
	}
}
