package cn.edustar.jitar.action;

import cn.edustar.jitar.pojos.ContentSpaceArticle;
import cn.edustar.jitar.service.ContentSpaceService;

/**
 * 显示自定义分类文章
 * 
 * @author admin
 * 
 */
public class ShowContentAction extends AbstractBasePageAction {
   
    private static final long serialVersionUID = 2129504909333791150L;

    private ContentSpaceService contentSpaceService;

    @Override
    protected String execute(String cmd) throws Exception {
        int contentSpaceArticleId = this.params.safeGetIntParam("articleId");
        ContentSpaceArticle contentSpaceArticle = this.contentSpaceService.getContentSpaceArticleById(contentSpaceArticleId);
        if (contentSpaceArticle == null) {
            this.addActionError("无法加载所请求的文章。");
            return ERROR;
        }
        contentSpaceArticle.setViewCount(contentSpaceArticle.getViewCount() + 1);
        this.contentSpaceService.saveOrUpdateArticle(contentSpaceArticle);

        request.setAttribute("contentSpaceArticle", contentSpaceArticle);

        return SUCCESS;
    }

    public void setContentSpaceService(ContentSpaceService contentSpaceService) {
        this.contentSpaceService = contentSpaceService;
    }

}
