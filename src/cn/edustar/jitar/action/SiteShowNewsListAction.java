package cn.edustar.jitar.action;

import cn.edustar.data.Pager;
import cn.edustar.jitar.service.SiteNewsQueryParam;
import cn.edustar.jitar.service.SubjectService;

/**
 * 网站新闻
 * @author mxh
 *
 */
public class SiteShowNewsListAction extends AbstractBasePageAction {

    
    private static final long serialVersionUID = -6693050406111313223L;

    /**
     * 学科服务
     */
    private SubjectService subjectService;
    @Override
    protected String execute(String cmd) throws Exception {
        Pager pager = params.createPager();
        pager.setItemName("新闻");
        pager.setItemUnit("条");
        pager.setPageSize(20);
        SiteNewsQueryParam qryparam = new SiteNewsQueryParam();
        qryparam.subjectId = 0;    
        request.setAttribute("news_list", this.subjectService.getSiteNewsDataTable(qryparam,pager));
        request.setAttribute("pager", pager);
        return SUCCESS;
    }
    public void setSubjectService(SubjectService subjectService) {
        this.subjectService = subjectService;
    }

}
