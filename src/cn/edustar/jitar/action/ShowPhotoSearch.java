package cn.edustar.jitar.action;

import cn.edustar.data.Pager;
import cn.edustar.jitar.service.PhotoQuery;

public class ShowPhotoSearch extends AbstractBasePageAction{

    private static final long serialVersionUID = 1L;

    @Override
    protected String execute(String cmd) throws Exception {
        String k = params.safeGetStringParam("k");
        Pager pager = params.createPager();
        pager.setItemName("图片");
        pager.setItemUnit("张");
        pager.setPageSize(8);
        PhotoQuery qry = new PhotoQuery("p.photoId, p.href");
        qry.setOrderType(0);
        qry.f = "3";
        qry.k = k;
        pager.setTotalRows(qry.count());
        request.setAttribute("k", k);
        request.setAttribute("pager", pager);
        request.setAttribute("photo_list", qry.query_map(pager));
        request.setAttribute("head_nav", "gallery");
        return SUCCESS;
    }
}
