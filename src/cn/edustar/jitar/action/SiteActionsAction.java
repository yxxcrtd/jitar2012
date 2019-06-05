package cn.edustar.jitar.action;

import java.util.List;

import cn.edustar.data.Pager;
import cn.edustar.jitar.service.ActionQuery;
import cn.edustar.jitar.util.HtmlPager;

public class SiteActionsAction extends AbstractBasePageAction {

    /**
     * 
     */
    private static final long serialVersionUID = 549232540937944456L;

    @Override
    protected String execute(String cmd) throws Exception {
        request.setAttribute("head_nav", "actions");
        String ownerType = params.getStringParam("ownerType");
        String showtype = params.getStringParam("showType");
        int currentPage = params.safeGetIntParam("page");

        String k = params.getStringParam("k");
        String filter = params.getStringParam("filter");
        if (filter == null || filter.trim().length() == 0) {
            filter = "title";
        }
        if (showtype == null || showtype.length() == 0) {
            showtype = "all";
        }
        request.setAttribute("showType", showtype);
        request.setAttribute("ownerType", ownerType);
        request.setAttribute("k", k);
        request.setAttribute("filter", filter);

        if (cmd.equals("ajax")) {
            Pager pager = params.createPager();
            pager.setItemName("活动");
            pager.setItemUnit("个");
            pager.setPageSize(20);
            pager.setCurrentPage(currentPage);
            ActionQuery qry = new ActionQuery(
                    " act.title, act.createDate, act.actionId, act.ownerId, act.ownerType, act.createUserId, act.actionType, act.startDateTime, act.finishDateTime, act.attendLimitDateTime, u.loginName,u.trueName");

            if (!(ownerType == null || ownerType.length() < 1)) {
                qry.ownerType = ownerType;
            }

            if (showtype.equals("running")) {
                qry.qryDate = 1; // #正在报名的
            } else if (showtype.equals("finish")) {
                qry.qryDate = 2;// #已经完成的活动
            } else {
                qry.qryDate = 0;// #全部
            }
            qry.k = k;
            qry.status = 0;
            qry.filter = filter;
            pager.setTotalRows(qry.count());
            List action_list = (List) qry.query_map(pager);
            request.setAttribute("action_list", action_list);
            request.setAttribute("pager", pager);

            String html = HtmlPager.render(pager.getTotalPages(), 3, pager.getCurrentPage());
            request.setAttribute("HtmlPager", html);
            return "ajax";
        }
        return SUCCESS;
    }

}
