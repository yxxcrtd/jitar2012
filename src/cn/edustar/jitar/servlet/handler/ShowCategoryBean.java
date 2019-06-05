package cn.edustar.jitar.servlet.handler;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;

import cn.edustar.jitar.pojos.Category;
import cn.edustar.jitar.pojos.Page;
import cn.edustar.jitar.pojos.User;
import cn.edustar.jitar.pojos.Widget;
import cn.edustar.jitar.service.CategoryService;
import cn.edustar.jitar.service.PageKey;
import cn.edustar.jitar.util.CommonUtil;
import cn.edustar.jitar.util.ParamUtil;

/**
 * 显示某个用户的文章分类.
 *
 * 
 * 访问分类页 URL:    '/u/$username/category/$cid.html'
 *   PathInfo:  '/$username/category/$cid.html'
 */
public class ShowCategoryBean extends BaseShowUser {
	/** 分类服务. */
	private CategoryService cat_svc;

	/** 分类服务. */
	public void setCategoryService(CategoryService cat_svc) {
		this.cat_svc = cat_svc;
	}
	
	/** 要访问的用户登录名 */
	private String loginName;
	
	/** 要访问的分类标识 */
	private int cid;
	
	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.servlet.handler.ServletBeanBase#handleRequst()
	 */
	@Override
	protected void handleRequst() throws IOException, ServletException {
		// 解析要访问的用户和分类标识.
		if (parsePathInfo() == false) {
			response.sendError(SC_NOT_FOUND);
			return;
		}
		
		// 得到要访问的用户对象.
		User user_model = getUserService().getUserByLoginName(loginName);
		if (user_model == null) {
			response.sendError(SC_NOT_FOUND);
			return;
		}

		// 得到要访问的分类, 要验证分类的确属于该用户.
		String itemType = CommonUtil.toUserArticleCategoryItemType(user_model.getUserId());
		Category category;
		if (cid == 0) {
			category = new Category();
			category.setItemType(itemType);
			category.setName("所有文章");
		} else
			category = cat_svc.getCategory(cid);
		if (category == null || itemType.equals(category.getItemType()) == false) {
			response.sendError(SC_NOT_FOUND);
			return;
		}
		
		// 得到系统缺省分类页.
		Page page = super.getSystemPageWithUserSkin(
				PageKey.SYSTEM_USER_CATEGORY, user_model._getUserObject()); 
		if (page == null) {
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "PageModel unexist");
			return;
		}

		// 得到该页面的所有内容块.
		List<Widget> widget_list = getPageService().getPageWidgets(page.getPageId());

		// 合成模板.
		HashMap<String, Object> root_map = new HashMap<String, Object>();
		root_map.put("user", user_model);
		root_map.put("category", category);
		root_map.put("page", page);
		root_map.put("widgets", widget_list);

		// 使用位于 WEB-INF 下面的用户缺省分类模板.
		String template_name = "/WEB-INF/user/default/category_index.ftl";

		response.setContentType("text/html; charset=utf-8");
		processTemplate(getTemplateProcessor(), root_map, template_name);
	}
	
	// 解析要访问的用户和分类标识.
	private boolean parsePathInfo() {
		// '/$username/category/$cid.html'
		String path_info = request.getPathInfo();
		if (path_info == null) return false;
		
		// ['', '$username', 'category', '$cid.html' ]
		String[] parts = path_info.split("/");
		if (parts.length < 3) return false;
		this.loginName = parts[1];
		if (ParamUtil.isEmptyString(loginName)) return false;
		if ("category".equals(parts[2]) == false) return false;
		if (parts[3].endsWith(".html") == false) return false;
		parts[3] = parts[3].substring(0, parts[3].length() - ".html".length());
		if (ParamUtil.isInteger(parts[3]) == false) return false;
		this.cid = Integer.parseInt(parts[3]);
		
		return true;
	}
}
