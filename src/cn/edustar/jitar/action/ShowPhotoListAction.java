package cn.edustar.jitar.action;

import cn.edustar.data.Pager;
import cn.edustar.jitar.pojos.Category;
import cn.edustar.jitar.service.CategoryService;
import cn.edustar.jitar.service.PhotoQuery;

/**
 * 图片列表
 * 
 * @author renliang
 */
public class ShowPhotoListAction extends AbstractBasePageAction {

    /**
	 * 
	 */
    private static final long serialVersionUID = 3882295287077409697L;
    private CategoryService cate_svc = null;
    public void setCate_svc(CategoryService cate_svc) {
        this.cate_svc = cate_svc;
    }
    private Category category = null;
    private Integer categoryId = null;
    private String tagName = null;
    @Override
    protected String execute(String cmd) throws Exception {
        if (cmd.trim().equalsIgnoreCase("tag")) {
            tagName = params.safeGetStringParam("tagName");
            request.setAttribute("tagName", tagName);
        } else {
            categoryId = params.safeGetIntParam("categoryId");
            if (categoryId == 0) {
                response.getWriter().write("没有该分类。");
                return null;
            }
            category = cate_svc.getCategory(categoryId);
            if (category == null) {
                response.getWriter().write("没有该分类记录。");
                return null;
            }
            request.setAttribute("category", category);
        }
        // # 最新图片.
        // get_new_photo_list();
        // # 图片点击排行.
        // get_hot_photo_list();
        get_photo_with_pager();
        // # 页面导航高亮为 'gallery'
        request.setAttribute("head_nav", "gallery");
        return "success";
    }

    private void get_photo_with_pager() {
        Pager pager = params.createPager();
        pager.setItemName("图片");
        pager.setItemUnit("张");
        pager.setPageSize(24);
        PhotoQuery qry = new PhotoQuery("p.photoId, p.title, p.createDate, p.href, u.userId, u.loginName, u.nickName, p.summary");
        if (null == categoryId) {
            qry.setTags(tagName.trim());
        } else {
            qry.setSysCateId(categoryId);
        }
        qry.setOrderType(0);
        pager.setTotalRows(qry.count());
        request.setAttribute("pager", pager);
        request.setAttribute("photo_list", qry.query_map(pager));
    }

    @SuppressWarnings("unused")
    private void get_hot_photo_list() {
        PhotoQuery qry = new PhotoQuery("p.photoId, p.title,u.loginName");
        qry.setSysCateId(categoryId);
        qry.setOrderType(2); // # viewCount DESC
        request.setAttribute("hot_photo_list", qry.query_map(20));
    }

    // # 获得最新图片列表.
    @SuppressWarnings("unused")
    private void get_new_photo_list() {
        PhotoQuery qry = new PhotoQuery("p.photoId, p.title, p.createDate, p.href, u.userId, u.loginName, u.nickName, p.summary");
        qry.setSysCateId(categoryId);
        qry.setOrderType(0); // # photoId DESC
        request.setAttribute("new_photo_list", qry.query_map(10));
        // #DEBUG: print "new_photo_list = ", new_photo_list
    }
}
