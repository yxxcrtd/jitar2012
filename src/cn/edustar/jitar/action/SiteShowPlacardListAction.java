package cn.edustar.jitar.action;

import java.util.List;

import cn.edustar.data.Pager;
import cn.edustar.jitar.service.PlacardQuery;

/**
 * 网站公告
 * @author mxh
 *
 */
public class SiteShowPlacardListAction extends AbstractBasePageAction {

    /**
     * 
     */
    private static final long serialVersionUID = 1961931079101158295L;

    @Override
    protected String execute(String cmd) throws Exception {
        Pager pager = params.createPager();
        pager.setItemName("公告");
        pager.setItemUnit("条");
        pager.setPageSize(20);

        PlacardQuery qry = new PlacardQuery("pld.id, pld.title, pld.createDate");
        qry.objType = 100;
        pager.setTotalRows(qry.count());
        @SuppressWarnings("rawtypes")
        List card_list = (List) qry.query_map(pager);
        request.setAttribute("card_list", card_list);
        request.setAttribute("pager", pager);
        return SUCCESS;
    }
}
