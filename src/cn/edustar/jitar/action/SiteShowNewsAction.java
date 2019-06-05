package cn.edustar.jitar.action;

import cn.edustar.jitar.pojos.SiteNews;
import cn.edustar.jitar.service.SubjectService;

/**
 * 网站新闻；
 * @author mxh
 *
 */
public class SiteShowNewsAction extends AbstractBasePageAction {

    /**
     * 
     */
    private static final long serialVersionUID = -736720613310471372L;

    private SubjectService subjectService;
    @Override
    protected String execute(String cmd) throws Exception {
        Integer newsId = params.getIntParam("newsId");
        if (newsId == null || newsId == 0) {
            this.addActionError("缺少标识。");
            this.addActionLink("返回首页", request.getContextPath() + "/");
            return ERROR;
        }
        SiteNews news = this.subjectService.getSiteNews(newsId);
        if (news == null) {
            this.addActionError("不能加载对象。");
            this.addActionLink("返回首页", request.getContextPath() + "/");
            return ERROR;
        }
        news.setViewCount(news.getViewCount() + 1);
        this.subjectService.saveOrUpateSiteNews(news);
        request.setAttribute("news", news);
        return SUCCESS;
    }
    public void setSubjectService(SubjectService subjectService) {
        this.subjectService = subjectService;
    }
}
