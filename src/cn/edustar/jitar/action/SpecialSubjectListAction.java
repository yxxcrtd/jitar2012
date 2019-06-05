package cn.edustar.jitar.action;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.edustar.data.Pager;
import cn.edustar.jitar.pojos.SpecialSubject;
import cn.edustar.jitar.service.PhotoQuery;
import cn.edustar.jitar.service.PlugInTopicQuery;
import cn.edustar.jitar.service.SpecialSubjectArticleQuery;
import cn.edustar.jitar.service.SpecialSubjectQuery;
import cn.edustar.jitar.service.SpecialSubjectService;
import cn.edustar.jitar.service.VideoQuery;

//import cn.edustar.jitar.JitarConst;

/**
 * 专题
 * 
 * @author renliang
 */
public class SpecialSubjectListAction extends AbstractBasePageAction {

    /**
	 * 
	 */
    private static final long serialVersionUID = 1743444810468336926L;

    /** serialVersionUID */

    private transient static final Log log = LogFactory.getLog(SpecialSubjectListAction.class);

    private Integer specialSubjectId = 0;
    public SpecialSubjectService specialSubjectService;
    private SpecialSubject specialSubject = null;

    private void init() {
        request.setAttribute("k", params.getStringParam("k"));
    }

    @Override
    protected String execute(String cmd) throws Exception {
        init();
        response.setContentType("text/html; charset=UTF-8");

        String type = params.safeGetStringParam("type");
        specialSubjectId = params.safeGetIntParam("specialSubjectId");
        if (specialSubjectId < 1) {
            addActionError("无效的专题标识。");
            return "ERROR";
        }
        specialSubject = specialSubjectService.getSpecialSubject(specialSubjectId);
        if (specialSubject == null) {
            addActionError("无法加载专题对象");
            return "ERROR";
        }
        request.setAttribute("specialSubject", specialSubject);
        request.setAttribute("head_nav", "special_subject");

        if (type.trim().equals("photo")) {
            return photoList();
        } else if (type.trim().equals("article")) {
            return articleList();
        } else if (type.trim().equals("specialsubject")) {
            return specialsubjectList();
        } else if (type.trim().equals("topic")) {
            return topicList();
        } else if (type.trim().equals("video")) {
            return videoList();
        } else {
            addActionError("无效的操作命令。");
            return "ERROR";
        }
    }

    private String videoList() {
        Pager pager = params.createPager();
        pager.setItemName("视频");
        pager.setItemUnit("个");
        pager.setPageSize(24);
        VideoQuery qry = new VideoQuery("v.videoId, v.flvHref, v.flvThumbNailHref, v.title,v.userId,v.createDate");
        qry.setSpecialSubjectId(specialSubjectId);
        pager.setTotalRows(qry.count());
        request.setAttribute("video_list", qry.query_map(pager));
        request.setAttribute("pager", pager);
        return "specialsubject_video_list";
    }

    private String topicList() {
        Pager pager = params.createPager();
        pager.setItemName("讨论");
        pager.setItemUnit("个");
        pager.setPageSize(10);
        PlugInTopicQuery qry = new PlugInTopicQuery("pt.plugInTopicId,pt.title, pt.createUserId, pt.createUserName, pt.createDate");
        qry.setParentGuid(specialSubject.getObjectGuid());
        pager.setTotalRows(qry.count());
        request.setAttribute("topic_list", qry.query_map(pager));
        request.setAttribute("pager", pager);
        return "specialsubject_topic_list";
    }

    private String specialsubjectList() {
        Pager pager = params.createPager();
        pager.setItemName("专题");
        pager.setItemUnit("个");
        pager.setPageSize(10);
        SpecialSubjectQuery qry = new SpecialSubjectQuery("ss.specialSubjectId,ss.title,ss.logo,ss.description,ss.createDate,ss.expiresDate");
        qry.setObjectType("system");
        pager.setTotalRows(qry.count());
        request.setAttribute("specialsubject_list", qry.query_map(pager));
        request.setAttribute("pager", pager);
        return "specialsubject_specialsubject_list";
    }

    private String articleList() {
        Pager pager = params.createPager();
        pager.setItemName("文章");
        pager.setItemUnit("个");
        pager.setPageSize(20);
        SpecialSubjectArticleQuery qry = new SpecialSubjectArticleQuery(
                "ssa.articleId, ssa.title, ssa.userId, ssa.userTrueName,ssa.createDate,ssa.typeState");
        qry.setRequest(request);
        qry.setSpecialSubjectId(specialSubjectId);
        pager.setTotalRows(qry.count());
        request.setAttribute("article_list", qry.query_map(pager));
        request.setAttribute("pager", pager);
        return "specialsubject_article_list";
    }

    private String photoList() {
        Pager pager = params.createPager();
        pager.setItemName("图片");
        pager.setItemUnit("张");
        pager.setPageSize(48);
        PhotoQuery qry = new PhotoQuery("p.photoId, p.title, p.userId,p.href, p.userTrueName,u.loginName");
        qry.k = params.safeGetStringParam("k", null);
        qry.f = params.safeGetStringParam("f", "0");
        qry.sysCateId = params.getIntParamZeroAsNull("categoryId");
        request.setAttribute("f", qry.f);
        request.setAttribute("k", qry.k);
        qry.setSpecialSubjectId(specialSubjectId);
        pager.setTotalRows(qry.count());
        request.setAttribute("photo_list", qry.query_map(pager));
        request.setAttribute("pager", pager);
        return "specialsubject_photo_list";
    }

    public void setSpecialSubjectService(SpecialSubjectService specialSubjectService) {
        this.specialSubjectService = specialSubjectService;
    }
}
