package cn.edustar.jitar.action;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.edustar.data.Pager;
import cn.edustar.jitar.pojos.Subject;
import cn.edustar.jitar.service.SiteNewsQuery;

//import cn.edustar.jitar.JitarConst;

/**
 * 视频
 * 
 * @author renliang
 */
public class NewListAction extends AbstractBasePageAction {

    /**
	 * 
	 */
    private static final long serialVersionUID = 1743444810468336926L;
    public Pager pager = null;

    /** serialVersionUID */
    private transient static final Log log = LogFactory.getLog(NewListAction.class);

    public BaseSubject baseSubject = null;

    private Subject subject = null;

    public String templateName = null;

    private void init() {
        pager = params.createPager();
        pager.setPageSize(10);
        pager.setItemName("新闻");
        pager.setItemUnit("条");
    }

    @Override
    protected String execute(String cmd) throws Exception {
        init();
        baseSubject = new BaseSubject();
        subject = baseSubject.getSubject();
        if (subject == null) {
            addActionError("无法加载指定的学科。");
            return "ERROR";
        }

        String templateName = "template1";
        if (subject.getTemplateName() != null) {
            templateName = subject.getTemplateName();
        }

        String type = params.safeGetStringParam("type");
        SiteNewsQuery qry = new SiteNewsQuery("snews.newsId, snews.title, snews.createDate, snews.picture");        
        qry.subjectId = subject.getSubjectId();
        String Page_Title = params.safeGetStringParam("title");
        if ("pic".equals(type.trim())) {
            if (Page_Title == "") {
                Page_Title = "图片新闻";
            }
            qry.hasPicture = true;
        } else if ("text".equals(type.trim())) {
            qry.hasPicture = false;
            if ("".equals(Page_Title.trim())) {
                Page_Title = "学科动态";
            }
        } else {
            if (Page_Title == "") {
                Page_Title = "学科新闻";
            }
        }
        pager.setTotalRows(qry.count());
        request.setCharacterEncoding("UTF-8");
        request.setAttribute("Page_Title", Page_Title);
        request.setAttribute("news_list", qry.query_map(pager));
        request.setAttribute("pager", pager);
        request.setAttribute("subject", subject);

        return templateName;
    }
}
