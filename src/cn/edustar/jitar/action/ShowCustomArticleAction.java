package cn.edustar.jitar.action;

import java.util.List;

import cn.edustar.data.Pager;
import cn.edustar.jitar.service.ArticleQuery;
import cn.edustar.jitar.service.ContentSpaceArticleQuery;

public class ShowCustomArticleAction extends AbstractBasePageAction {

    private static final long serialVersionUID = 1054702258153623545L;

    @SuppressWarnings("rawtypes")
    @Override
    protected String execute(String cmd) throws Exception {
        int type = this.params.safeGetIntParam("type");
        Pager pager = this.params.createPager();
        pager.setItemName("文章");
        pager.setItemUnit("篇");
        pager.setPageSize(20);

        String k = this.params.getStringParam("k");
        String f = this.params.getStringParam("f");
        if (f == null || f.length() == 0) {
            f = "title";
        }
        request.setAttribute("k", k);
        request.setAttribute("f", f);
        List article_list;
        if (type == 2) {
            ContentSpaceArticleQuery qry = new ContentSpaceArticleQuery(
                    " cs.spaceName, csa.title, csa.createDate, csa.viewCount, csa.contentSpaceArticleId ");
            qry.ownerType = 0;
            qry.ownerId = 0;
            qry.contentSpaceId = this.params.safeGetIntParam("categoryId");
            pager.setTotalRows(qry.count());
            article_list = (List) qry.query_map(pager);
            request.setAttribute("article_list", article_list);
            request.setAttribute("pager", pager);
            request.setAttribute("Page_Title", this.params.safeGetStringParam("title"));
            return "show_custom_contentspace_article";
        } else {
            ArticleQuery qry = new ArticleQuery(
                    " a.articleId, a.title, a.createDate, a.viewCount, a.commentCount, a.userId, a.recommendState, a.typeState, u.loginName, u.nickName, u.trueName ");
            pager.setTotalRows(qry.count());
            article_list = (List) qry.query_map(pager);
            request.setAttribute("article_list", article_list);
            request.setAttribute("pager", pager);
            request.setAttribute("Page_Title", this.params.safeGetStringParam("title"));
            return "show_custom_article";
        }
    }
}
