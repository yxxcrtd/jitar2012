package cn.edustar.jitar.action;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.edustar.data.Pager;
import cn.edustar.jitar.model.CategoryModel;
import cn.edustar.jitar.model.CategoryTreeModel;
import cn.edustar.jitar.service.CategoryService;
import cn.edustar.jitar.service.VideoQuery;

import com.alibaba.fastjson.JSONObject;

//import cn.edustar.jitar.JitarConst;

/**
 * 视频
 * 
 * @author renliang
 */
public class VideosAction extends AbstractBasePageAction {

    /**
	 * 
	 */
    private static final long serialVersionUID = 1743444810468336926L;

    /** serialVersionUID */

    private transient static final Log log = LogFactory.getLog(VideosAction.class);

    private CategoryService categoryService;

    public void setCategoryService(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    public String execute(String cmd) throws Exception {
        if (cmd.trim().equals("query")) {
            get_video_querylist();
            return "queryvideo";
        }
        
        String categoryId = request.getParameter("categoryId");
        request.setAttribute("categoryId", categoryId);
        
        // 最新视频
        get_new_video_list();
        // 视频点击排行
        get_hot_video_list();

        if ("ajax".equals(cmd)) {
            return "ajax";
        }
        
        // 得到视频分类,得到所有的视频分类
        //get_video_category_ex();
        request.setAttribute("trees", this.categoryService.showTree("video"));
        request.setAttribute("head_nav", "videos");
        return "site_videos";
    }

    private void get_hot_video_list() {
        VideoQuery videoQuery = new VideoQuery(" v.videoId, v.title ,u.trueName, u.loginName,v.flvThumbNailHref ");
        videoQuery.setOrderType(videoQuery.ORDER_TYPE_VIEWCOUNT_DESC);
        String categoryId = request.getParameter("categoryId");
        if(null!=categoryId){
        	videoQuery.setCategoryId(Integer.valueOf(categoryId));
        }
        request.setAttribute("hot_video_list", videoQuery.query_map(15));
    }

    private void get_video_querylist() {
        VideoQuery videoQuery = new VideoQuery("v.videoId, v.title, v.createDate, v.href, v.flvHref, v.userId, v.flvThumbNailHref,v.auditState");
        videoQuery.setAuditState(null);
        videoQuery.setOrderType(videoQuery.ORDER_TYPE_VIDEOID_DESC);
        videoQuery.setVideoIds(request.getParameter("id"));
        request.setAttribute("video_list", videoQuery.query_map());
    }

    // 得到视频分类,得到所有的视频分类
    private List<JSONObject> get_video_category_ex() {
        CategoryTreeModel video_categories = categoryService.getCategoryTree("video");
        List<JSONObject> parentUnitArray = new ArrayList<JSONObject>();
        for (CategoryModel c : video_categories.getAll()) {
            JSONObject obj = new JSONObject();
            obj.put("categoryId", c.getCategoryId());
            obj.put("categoryName", c.getName());
            obj.put("parentId", c.getParentId());
            parentUnitArray.add(obj);
        }
        request.setAttribute("root_cates", parentUnitArray);
        return parentUnitArray;
    }

    private void get_new_video_list() {
        VideoQuery videoQuery = new VideoQuery(" v.videoId, v.title, v.createDate, v.href, v.flvHref, v.userId, u.trueName, u.loginName, v.flvThumbNailHref ");
        videoQuery.setOrderType(videoQuery.ORDER_TYPE_VIDEOID_DESC);
        String categoryId = request.getParameter("categoryId");
        if(null!=categoryId){
        	videoQuery.setCategoryId(Integer.valueOf(categoryId));
        }
        request.setAttribute("new_video_list", videoQuery.query_map(10));
    }

    private void get_video_category() {
        List<JSONObject> video_cates = get_video_category_ex();
        // 得到每个分类的最新的 6 张视频
        VideoQuery videoQuery = new VideoQuery(" v.videoId, v.title, v.href,u.loginName,v.flvThumbNailHref");

        for (JSONObject c : video_cates) {
            // videoQuery.sysCateTitle = c.get("categoryName");
            video_cates = videoQuery.query_map(5);
        }

        if (video_cates.size() == 0) {
            video_cates = null;
            Pager pager = params.createPager();
            pager.setItemName("视频");
            pager.setItemUnit("个");
            pager.setPageSize(24);
            videoQuery = new VideoQuery(" v.videoId, v.title, v.createDate, v.href, v.userId, u.loginName, v.flvThumbNailHref ");
            videoQuery.setOrderType(0);
            pager.setTotalRows(videoQuery.count());
            request.setAttribute("video_list_all", videoQuery.query_map(pager));
            request.setAttribute("pager", pager);
        }
        request.setAttribute("video_cates", video_cates);
    }
}
