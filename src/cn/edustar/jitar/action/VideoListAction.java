package cn.edustar.jitar.action;

import cn.edustar.data.Pager;
import cn.edustar.jitar.service.VideoQuery;
import cn.edustar.jitar.util.HtmlPager;

/**
 * 视频
 */
public class VideoListAction extends AbstractBasePageAction {

	/** serialVersionUID */
    private static final long serialVersionUID = 1743444810468336926L;

    public String execute(String cmd) throws Exception {
        String type = params.safeGetStringParam("type");
        VideoQuery qry = new VideoQuery("v.videoId, v.title, v.createDate, v.href, v.flvHref, v.userId, u.loginName, u.nickName, v.summary,v.flvThumbNailHref");
        qry.setCategoryId(this.params.safeGetIntParam("categoryId", null));
        if (type.trim().equals("hot")) {
            qry.setOrderType(VideoQuery.ORDER_TYPE_VIEWCOUNT_DESC);
        } else {
            qry.setOrderType(VideoQuery.ORDER_TYPE_VIDEOID_DESC);
        }
        String t = "最新上传";
        if (type.trim().equals("hot")) {
            t = "热门排行";
        } else if (type.trim().equals("search")) {
            qry.setK(request.getParameter("k"));
            if (qry.k.length() == 0) {
                t = "全部视频";
            } else {
                t = "关键字:" + qry.k;
            }
        }
        Pager pager = params.createPager();
        pager.setTotalRows(qry.count());
        pager.setPageSize(24);
        request.setAttribute("video_list", qry.query_map(pager));

        request.setAttribute("t", t);
        request.setAttribute("type", type);
        request.setAttribute("pager", pager);
        request.setAttribute("head_nav", "videos");

        String html = HtmlPager.render(pager.getTotalPages(), 3, pager.getCurrentPage());
        request.setAttribute("HtmlPager", html);

        return "success";
    }

}
