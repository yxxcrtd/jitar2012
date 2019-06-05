package cn.edustar.jitar.query.sitefactory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletContext;

import org.hibernate.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import cn.edustar.jitar.JitarContext;
import cn.edustar.jitar.dao.hibernate.BaseDaoHibernate;
import cn.edustar.jitar.model.Configure;
import cn.edustar.jitar.model.ObjectType;
import cn.edustar.jitar.pojos.Article;
import cn.edustar.jitar.pojos.Grade;
import cn.edustar.jitar.pojos.SiteNav;
import cn.edustar.jitar.pojos.Subject;
import cn.edustar.jitar.pojos.TimerCount;
import cn.edustar.jitar.pojos.Unit;
import cn.edustar.jitar.pojos.UnitLinks;
import cn.edustar.jitar.pojos.UnitSubject;
import cn.edustar.jitar.pojos.UnitWebpart;
import cn.edustar.jitar.service.ArticleService;
import cn.edustar.jitar.service.ConfigService;
import cn.edustar.jitar.service.ProductConfigService;
import cn.edustar.jitar.service.SiteIndexPartService;
import cn.edustar.jitar.service.SiteNavService;
import cn.edustar.jitar.service.StatService;
import cn.edustar.jitar.service.SubjectService;
import cn.edustar.jitar.service.TemplateProcessor;
import cn.edustar.jitar.service.TimerCountService;
import cn.edustar.jitar.service.UnitService;
import cn.edustar.jitar.util.CommonUtil;
import cn.edustar.jitar.util.SubjectNav;

/**
 * <p>
 * 生成首页和机构的静态页面。
 * </p>
 * 
 * @author mxh
 * 
 */
@SuppressWarnings("rawtypes")
public class HtmlGeneratorServiceImpl extends BaseDaoHibernate implements HtmlGeneratorService {
    private Unit rootUnit = null;
    private JitarContext jitarContext;
    private ConfigService configService;
    private UnitService unitService;
    private ArticleService articleService;
    private SiteNavService siteNavService;
    private SubjectService subjectService;
    private StatService statService;
    private SiteIndexPartService siteIndexPartService;
    private ProductConfigService productConfigService;
    private TimerCountService timerCountService;
    private TemplateProcessor templateProcessor;
    private List pic_news;

    private HashMap<String, Object> root_map = new HashMap<String, Object>();
    // private static Logger log =
    // LoggerFactory.getLogger(HtmlGeneratorServiceImpl.class);
    private String template_text = "/WEB-INF/ftl/site_index.ftl";
    private String content = "";
    private boolean autoHtml = true;

    // 各个 ftl 可能使用得到的共享变量
    private String siteUrl = null, siteThemeUrl = null, unitRootUrl = null, templateName = null;

    public void SiteIndex() {
        //新版本不生成静态文件了。
        if(true) return;
        autoHtml = configService.getConfigure().getBoolValue(Configure.SITE_AUTO_HTML, true);
        if (!autoHtml)
            return;
        WebApplicationContext wac = ContextLoader.getCurrentWebApplicationContext();
        ServletContext sc = wac.getServletContext();
        rootUnit = unitService.getRootUnit();
        siteUrl = sc.getInitParameter("siteUrl");
        if (siteUrl == null || siteUrl.length() == 0) {
            siteUrl = sc.getContextPath();
        }
        if (siteUrl.endsWith("/") == false) {
            siteUrl += "/";
        }
        siteThemeUrl = configService.getConfigure().getStringValue("siteThemeUrl");
        String path = this.getClass().getResource("").getPath().toString();
        if (path == null) {
            System.out.println("无法得到网站路径信息，请与程序开发人员联系。");
            return;
        }
        File file = null;
        OutputStreamWriter fw = null;
        String root_dir = path.substring(0, path.indexOf("WEB-INF"));
        root_dir = root_dir.replace("\\", "/");
        root_dir = root_dir.replace("/", File.separator);
        // if (root_dir.startsWith(File.separator)) root_dir =
        // root_dir.substring(1);
        try {
            root_dir = URLDecoder.decode(root_dir, "utf-8");
        } catch (UnsupportedEncodingException e1) {
        }
        String root_path = root_dir + "index.htm";
        try {
            file = new File(root_path);
            fw = new OutputStreamWriter(new FileOutputStream(file), "utf-8");
            if (rootUnit == null) {
                fw.write("没有根单位。你看到的是缓存文件，可能根单位已经存在，你可以清空缓存再查看，或者<a href='checksetting.py'>查看配置文件进行配置</a>。");
                fw.flush();
                fw.close();
                CommonUtil.copyfile(root_path, root_dir + "index.html");
                return;
            }
            this.GenernateSubjectNavFile(root_dir);
            root_map.put("SiteThemeUrl", siteThemeUrl);
            root_map.put("SiteUrl", siteUrl);
            root_map.put("SiteConfig", this.configService.getConfigure());
            root_map.put("SiteNavList", this.siteNavService.getAllSiteNav(false, 0, 0));
            root_map.put("head_nav", "index");
            root_map.put("unit", rootUnit);

            if (this.getProductInfo() == false) {
                template_text = "/WEB-INF/ftl/site_err.ftl";
                content = templateProcessor.processTemplate(root_map, template_text, "utf-8");
                fw.write(content);
                fw.flush();
                fw.close();
                CommonUtil.copyfile(root_path, root_dir + "index.html");
                return;
            }

            root_map.put("newest_article_list", this.getNewestArticle());
            root_map.put("cmt_article_list", this.getCommentArticle());
            root_map.put("rcmd_article_list", this.getRcmdArticle());
            root_map.put("hot_article_list", this.getHotArticle());
            root_map.put("famous_article_list", this.getFamousArticle());
            root_map.put("digg_article_list", this.getDiggArticle());
            root_map.put("trample_article_list", this.getTrampleArticle());
            root_map.put("starcount_article_list", this.getStarCountArticle());
            root_map.put("new_comment_list", this.getNewComment());
            root_map.put("pic_news", this.getJitarPicNews());
            root_map.put("jitar_news", this.getJitarNews());
            root_map.put("new_resource_list", this.getNewResourceList());
            root_map.put("hot_resource_list", this.getHotResourceList());
            root_map.put("rcmd_resource_list", this.getRcmdResourceList());
            root_map.put("course_list", this.getPrepareCourseList());
            root_map.put("special_subject_list", this.getSpecialSubjectList());
            root_map.put("expert_list", this.getExpertList());
            root_map.put("famous_teachers", this.getFamousTeacherList());
            root_map.put("teacher_star", this.getTeacherStarList());
            root_map.put("best_group_list", this.getBestGroupList());
            root_map.put("rcmd_group_list", this.getRcmdGroupList());
            root_map.put("hot_group_list", this.getHotGroupList());
            root_map.put("new_group_list", this.getNewGroupList());
            root_map.put("new_topic_list", this.getGroupTopicList());

            root_map.put("new_wr_list", this.getNewBlogList());
            root_map.put("hot_wr_list", this.getHotBlogList());
            root_map.put("score_wr_list", this.getScoreBlogList());
            root_map.put("rcmd_wr_list", this.getRcmdBlogList());

            root_map.put("photo_list", this.getNewPhotoList());
            root_map.put("new_video_list", this.getNewVideoList());
            root_map.put("site_placard_list", this.getSitePlacardList());
            root_map.put("jitar_actions", this.getJitarActionList());
            root_map.put("school_link", this.getSchoolShowList());
            root_map.put("teacher_show", this.getTeacherShowList());
            root_map.put("site_stat", this.timerCountService.getTimerCountById(TimerCount.COUNT_TYPE_SITE));
            root_map.put("hot_tags", this.getHotTagList());
            root_map.put("siteIndexPartList", this.siteIndexPartService.getSiteIndexPartList(true));

            // 以下是浙大统一用户
            if (sc.getInitParameter("passportURL") != null) {
                // String passportURL =
                // PassportClient.getInstance().getPassportURL();
                // if (passportURL == null)
                // passportURL = "";
                // if (passportURL.toLowerCase().equals("http://"))
                // passportURL = "";
                // if (passportURL.length() > 0) {
                // if (!passportURL.endsWith("/"))
                // passportURL += "/";
                // }
                // int passportServerId =
                // PassportClient.getInstance().getServerId();
                // String passportVerifyKey =
                // PassportClient.getInstance().getVerifyKey();
                // root_map.put("passportServerId", passportServerId);
                // root_map.put("passportVerifyKey", passportVerifyKey);
            }
            // 浙大统一用户结束

            String pw = sc.getInitParameter("photo_width");
            String ph = sc.getInitParameter("photo_height");
            if (pw == null || pw.equals("") || !CommonUtil.isInteger(pw))
                pw = "150";
            if (ph == null || ph.equals("") || !CommonUtil.isInteger(ph))
                ph = "120";

            root_map.put("pw", pw);
            root_map.put("ph", ph);

            String configBlogSiteRoot = sc.getInitParameter("userUrlPattern");
            if (null != configBlogSiteRoot && !configBlogSiteRoot.equals("")) {
                root_map.put("UserUrlPattern", configBlogSiteRoot);
            }

            String platformType = sc.getInitParameter("platformType");
            if (null == platformType)
                platformType = "";
            root_map.put("platformType", platformType);
            if (platformType.equals("1")) {
                // MashupService mashupService =(MashupService)
                // wac.getBean("mashupService");
                // List<MashupPlatform> platfotm_list =
                // mashupService.getAllMashupPlatform(true);
                // root_map.put("platfotm_list", platfotm_list);
            }

            content = templateProcessor.processTemplate(root_map, template_text, "utf-8");
            fw.write(content);
            fw.flush();
            fw.close();
        } catch (Exception e) {
            e.printStackTrace();
            e.printStackTrace(System.out);
            System.out.println("生成页面时出现错误：" + e.getMessage());
        } finally {
            if (file != null) {
                file = null;
            }
            if (fw != null) {
                fw = null;
            }
            // root_map = null;
        }
        CommonUtil.copyfile(root_path, root_dir + "index.html");
    }

    public boolean getProductInfo() {
        boolean p = productConfigService.isValid();
        String errMessage = productConfigService.getErrMessage();
        this.root_map.put("errMessage", errMessage);
        String productName = productConfigService.getProductName();
        String productdays = productConfigService.getDays();
        String remainDays = productConfigService.getRemainDays();
        String guid = productConfigService.getProductGuid();
        this.root_map.put("guid", guid);
        if (!(productdays.equals("0") || productdays.equals("-1"))) {
            productName = productName + "  还可以继续试用" + remainDays + "天";
        }

        this.root_map.put("productName", productName);
        this.root_map.put("p", p);
        return p;
    }

    /* 最新文章 */
    public List getNewestArticle() {
        CommonQuery qa = new CommonQuery(this.getSession());
        qa.setMaxRow(11);
        qa.setStartRow(0);
        qa.setHql("SELECT new Map( a.articleId as articleId, a.title as title, a.createDate as createDate,a.userId as userId, a.articleAbstract as articleAbstract, a.typeState as typeState,a.userTrueName as trueName,a.loginName as loginName) FROM Article as a ");
        qa.setOrderByClause("a.articleId DESC");
        qa.setWhereClause("a.auditState = 0 " + // 审核
                " AND a.draftState = false " + // 非草稿.
                " AND a.delState = false " + // 非删除.
                " AND a.hideState = 0" + " And a.approvedPathInfo Like '%/" + rootUnit.getUnitId() + "/%'");
        List a = qa.getQueryList();
        qa = null;
        return a;
    }

    /**
     * 推荐文章
     * 
     * @return
     */
    public List getRcmdArticle() {
        CommonQuery qa = new CommonQuery(this.getSession());
        qa.setMaxRow(11);
        qa.setStartRow(0);
        qa.setHql("SELECT new Map( a.articleId as articleId, a.title as title, a.createDate as createDate,a.userId as userId, a.articleAbstract as articleAbstract, a.typeState as typeState,a.userTrueName as trueName,a.loginName as loginName) FROM Article as a ");
        qa.setOrderByClause("a.articleId DESC");
        qa.setWhereClause("a.auditState = 0 "
                + // 审核
                " AND a.draftState = false "
                + // 非草稿.
                " AND a.delState = false "
                + // 非删除.
                " AND a.hideState = 0" + " And a.approvedPathInfo Like '%/" + rootUnit.getUnitId() + "/%'" + " And a.rcmdPathInfo Like '%/"
                + rootUnit.getUnitId() + "/%'"

        );
        List cmt_article_list = qa.getQueryList();
        qa = null;
        return cmt_article_list;
    }

    /**
     * 文章评论
     * 
     * @return
     */
    public List getCommentArticle() {
        return this.articleService.getWeekCommentArticleList();
        // /return articleService.getArticleList_Order_By_CommentNums(7, 11);
    }

    /* 名师文章 */
    public List getFamousArticle() {
        CommonQuery qa = new CommonQuery(this.getSession());
        qa.setMaxRow(11);
        qa.setStartRow(0);
        qa.setHql("SELECT new Map( a.articleId as articleId, a.title as title, a.createDate as createDate,a.userId as userId, a.typeState as typeState,a.userTrueName as trueName,u.loginName as loginName) FROM Article as a,User as u ");
        qa.setOrderByClause("a.articleId DESC");
        qa.setWhereClause("a.auditState = 0 " + // 审核
                " AND a.draftState = false " + // 非草稿.
                " AND a.delState = false " + // 非删除.
                " AND a.hideState = 0" + " And a.approvedPathInfo Like '%/" + rootUnit.getUnitId() + "/%'" +
                // " And a.rcmdPathInfo Like '%/" + rootUnit.getUnitId() + "/%'"
                // +
                " AND u.userId = a.userId" + " AND u.userType LIKE '%/1/%' ");
        List fms_article_list = qa.getQueryList();
        qa = null;
        return fms_article_list;
    }

    public List getDiggArticle() {
        CommonQuery qa = new CommonQuery(this.getSession());
        qa.setMaxRow(11);
        qa.setStartRow(0);
        qa.setHql("SELECT new Map( a.articleId as articleId, a.title as title, a.createDate as createDate,a.userId as userId, a.articleAbstract as articleAbstract, a.typeState as typeState,a.userTrueName as trueName,a.loginName as loginName,a.digg as digg) FROM Article as a ");
        qa.setOrderByClause("a.digg DESC");
        qa.setWhereClause("a.auditState = 0 " + // 审核
                " AND a.draftState = false " + // 非草稿.
                " AND a.delState = false " + // 非删除.
                " AND a.hideState = 0" + " And a.approvedPathInfo Like '%/" + rootUnit.getUnitId() + "/%'");
        List a = qa.getQueryList();
        qa = null;
        return a;
    }

    public List getTrampleArticle() {
        CommonQuery qa = new CommonQuery(this.getSession());
        qa.setMaxRow(11);
        qa.setStartRow(0);
        qa.setHql("SELECT new Map( a.articleId as articleId, a.title as title, a.createDate as createDate,a.userId as userId, a.articleAbstract as articleAbstract, a.typeState as typeState,a.userTrueName as trueName,a.loginName as loginName,a.trample as trample) FROM Article as a ");
        qa.setOrderByClause("a.trample DESC");
        qa.setWhereClause("a.auditState = 0 " + // 审核
                " AND a.draftState = false " + // 非草稿.
                " AND a.delState = false " + // 非删除.
                " AND a.hideState = 0" + " And a.approvedPathInfo Like '%/" + rootUnit.getUnitId() + "/%'");
        List a = qa.getQueryList();
        qa = null;
        return a;
    }

    public List getStarCountArticle() {
        CommonQuery qa = new CommonQuery(this.getSession());
        qa.setMaxRow(11);
        qa.setStartRow(0);
        qa.setHql("SELECT new Map( a.articleId as articleId, a.title as title, a.createDate as createDate,a.userId as userId, a.articleAbstract as articleAbstract, a.typeState as typeState,a.userTrueName as trueName,a.loginName as loginName,a.commentCount as commentCount,a.starCount as starCount) FROM Article as a ");
        qa.setWhereClause("a.commentCount > 0 And a.auditState = 0 " + // 审核
                " AND a.draftState = false " + // 非草稿.
                " AND a.delState = false " + // 非删除.
                " AND a.hideState = 0" + " And a.approvedPathInfo Like '%/" + rootUnit.getUnitId() + "/%'");
        qa.setOrderByClause("a.starCount/a.commentCount DESC");
        List a = qa.getQueryList();
        qa = null;
        return a;
    }

    public List getNewComment() {
        CommonQuery qa = new CommonQuery(this.getSession());
        qa.setMaxRow(10);
        qa.setStartRow(0);
        qa.setHql("SELECT new Map( cmt.id as id,cmt.objId as objId, cmt.createDate as createDate, cmt.userId as userId, cmt.userName as userName, cmt.title as title) FROM Comment as cmt ");
        qa.setOrderByClause("cmt.id DESC");
        qa.setWhereClause("cmt.audit = 1 " + // 审核
                " AND cmt.objType = 3 " // 文章评论
        );
        List a = qa.getQueryList();
        qa = null;
        return a;
    }

    /* 热门文章：计算7周之内的点击数 */
    public List getHotArticle() {
        String hql = "SELECT new Map( a.articleId as articleId, a.title as title, a.createDate as createDate, a.typeState as typeState, u.userId as userId, u.userIcon as userIcon, u.trueName as trueName, u.loginName as loginName,Sum(vc.viewCount) as articleViewCount) FROM Article a ,User u,ViewCount vc Where a.userId = u.userId and a.articleId = vc.objId And vc.objType=" + ObjectType.OBJECT_TYPE_ARTICLE + " and DateDiff(day,viewDate,getdate())<=7 and vc.objType=3"
                + " AND a.auditState = "
                + Article.AUDIT_STATE_OK
                + // 审核通过.
                " AND a.draftState = false "
                + // 非草稿.
                " AND a.delState = false "
                + // 非删除.
                " AND a.hideState = 0 "
                + // 非隐藏的
                " GROUP BY a.articleId, a.title, a.createDate, a.typeState,  u.userId, u.userIcon, u.userIcon, u.trueName, u.loginName  Order By Sum(vc.viewCount) DESC";
        Query query = this.getSession().createQuery(hql);
        query.setFirstResult(0);
        query.setMaxResults(11);
        return query.list();
        // return viewCountService.getViewCountListEx(3, 7, 11);
    }

    public List getJitarPicNews() {
        CommonQuery qa = new CommonQuery(this.getSession());
        qa.setMaxRow(4);
        qa.setStartRow(0);
        qa.setHql("SELECT new Map(snews.newsId as newsId, snews.title as title, snews.picture as picture) FROM SiteNews as snews ");
        qa.setWhereClause("snews.picture IS NOT NULL And snews.picture <> '' And snews.status = 0 And snews.subjectId = 0");
        qa.setOrderByClause("snews.newsId DESC");
        List a = qa.getQueryList();
        for (int i = 0; i < a.size(); i++) {
            java.util.HashMap hm = (java.util.HashMap) a.get(i);
            String pic_url = hm.get("picture").toString();
            String pic_url_path = pic_url.substring(0, pic_url.lastIndexOf("/") + 1);
            String pic_url_fileName = pic_url.substring(pic_url.lastIndexOf("/") + 1, pic_url.lastIndexOf("."));
            String pic_url_fileExt = pic_url.substring(pic_url.lastIndexOf("."));
            pic_url_fileName = CommonUtil.escape(CommonUtil.urlGB2312Encode(pic_url_fileName));
            // System.out.println("pic_url = " + pic_url);
            // System.out.println("pic_url_path = " + pic_url_path);
            // System.out.println("pic_url_fileName = " + pic_url_fileName);
            // System.out.println("pic_url_fileExt = " + pic_url_fileExt);
            hm.put("picture", pic_url_path + pic_url_fileName + pic_url_fileExt);
        }
        pic_news = a;
        qa = null;
        return a;
    }

    public List getJitarNews() {
        String _s = "(";
        for (int kkk = 0; kkk < pic_news.size(); kkk++) {
            HashMap x = (HashMap) pic_news.get(kkk);
            _s += x.get("newsId") + ",";
        }
        if (_s.endsWith(","))
            _s = _s.substring(0, _s.length() - 1);
        _s += ")";

        CommonQuery qa = new CommonQuery(this.getSession());
        qa.setMaxRow(11);
        qa.setStartRow(0);
        qa.setHql("SELECT new Map( snews.newsId as newsId, snews.title as title,snews.createDate as createDate ) FROM SiteNews as snews ");
        if (_s.length() > 2) {
            qa.setWhereClause(" snews.status = 0 And snews.subjectId = 0 And snews.newsId not in " + _s);
        } else {
            qa.setWhereClause("(snews.picture IS NULL Or snews.picture = '') And snews.status = 0 And snews.subjectId = 0 ");
        }
        qa.setOrderByClause("snews.newsId DESC");
        List a = qa.getQueryList();

        qa = null;
        return a;
    }

    public List getNewResourceList() {
        CommonQuery qa = new CommonQuery(this.getSession());
        qa.setMaxRow(10);
        qa.setStartRow(0);
        qa.setHql("SELECT new Map( r.resourceId as resourceId, r.title as title, r.href as href, r.createDate as createDate, r.fsize as fsize, r.downloadCount as downloadCount,r.userId as userId,r.subjectId as subjectId,sc.name as scName,grade.gradeName as gradeName ) FROM Resource as r LEFT JOIN r.sysCate as sc LEFT JOIN r.grade as grade ");
        qa.setWhereClause("r.approvedPathInfo LIKE '%/" + this.rootUnit.getUnitId() + "/%'");
        qa.setOrderByClause("r.resourceId DESC");
        List a = qa.getQueryList();
        qa = null;
        return a;
    }

    public List getHotResourceList() {
        CommonQuery qa = new CommonQuery(this.getSession());
        qa.setMaxRow(10);
        qa.setStartRow(0);
        qa.setHql("SELECT new Map( r.resourceId as resourceId, r.title as title, r.href as href, r.createDate as createDate, r.fsize as fsize, r.downloadCount as downloadCount,r.userId as userId,r.subjectId as subjectId,sc.name as scName,grade.gradeName as gradeName ) FROM Resource as r LEFT JOIN r.sysCate as sc LEFT JOIN r.grade as grade ");
        qa.setWhereClause("r.approvedPathInfo LIKE '%/" + this.rootUnit.getUnitId() + "/%'");
        qa.setOrderByClause("r.downloadCount DESC");
        List a = qa.getQueryList();
        qa = null;
        return a;
    }

    public List getRcmdResourceList() {
        CommonQuery qa = new CommonQuery(this.getSession());
        qa.setMaxRow(10);
        qa.setStartRow(0);
        qa.setHql("SELECT new Map( r.resourceId as resourceId, r.title as title, r.href as href, r.createDate as createDate, r.fsize as fsize, r.downloadCount as downloadCount,r.userId as userId,r.subjectId as subjectId,sc.name as scName,grade.gradeName as gradeName ) FROM Resource as r LEFT JOIN r.sysCate as sc LEFT JOIN r.grade as grade ");
        qa.setWhereClause("r.approvedPathInfo LIKE '%/" + this.rootUnit.getUnitId() + "/%' And r.rcmdPathInfo LIKE '%/" + this.rootUnit.getUnitId()
                + "/%'");
        qa.setOrderByClause("r.resourceId DESC");
        List a = qa.getQueryList();
        qa = null;
        return a;
    }

    public List getPrepareCourseList() {
        CommonQuery qa = new CommonQuery(this.getSession());
        qa.setMaxRow(8);
        qa.setStartRow(0);
        qa.setHql("SELECT new Map( pc.createUserId as createUserId,pc.leaderId as leaderId,pc.memberCount as memberCount,pc.articleCount as articleCount,pc.resourceCount as resourceCount, "
                + " pc.status as status,pc.actionCount as actionCount,pc.topicCount as topicCount,pc.topicReplyCount as topicReplyCount,pc.viewCount as viewCount,pc.startDate as startDate,pc.endDate as endDate, "
                + " pc.title as title, pc.prepareCourseId as prepareCourseId, pc.createDate as createDate, pc.metaSubjectId as metaSubjectId, pc.gradeId as gradeId) FROM PrepareCourse as pc ");
        qa.setWhereClause("pc.status=0");
        qa.setOrderByClause("pc.prepareCourseId DESC");
        List a = qa.getQueryList();
        qa = null;
        return a;
    }

    // special_subject_list
    public List getSpecialSubjectList() {
        CommonQuery qa = new CommonQuery(this.getSession());
        qa.setMaxRow(8);
        qa.setStartRow(0);
        qa.setHql("SELECT new Map( ss.specialSubjectId as specialSubjectId, ss.title as title, ss.createDate as createDate) FROM SpecialSubject as ss ");
        qa.setOrderByClause("ss.specialSubjectId DESC");
        List a = qa.getQueryList();
        qa = null;
        return a;
    }

    // 学科带头人
    public List getExpertList() {
        CommonQuery qa = new CommonQuery(this.getSession());
        qa.setMaxRow(8);
        qa.setStartRow(0);
        qa.setHql("SELECT new Map( u.loginName as loginName, u.userIcon as userIcon, u.blogName as blogName, u.trueName as trueName, u.visitCount as visitCount) FROM User as u");
        qa.setWhereClause("u.userStatus = 0 And u.userType LIKE '%/3/%' ");
        qa.setOrderByClause("u.userId DESC");
        List a = qa.getQueryList();
        qa = null;
        return a;
    }

    // 名师
    public List getFamousTeacherList() {
        CommonQuery qa = new CommonQuery(this.getSession());
        qa.setMaxRow(12);
        qa.setStartRow(0);
        qa.setHql("SELECT new Map( u.loginName as loginName, u.userIcon as userIcon, u.blogName as blogName, u.trueName as trueName, u.visitCount as visitCount) FROM User as u");
        qa.setWhereClause("u.userStatus = 0 And u.userType LIKE '%/1/%'");
        qa.setOrderByClause("newid()");
        List a = qa.getQueryList();
        qa = null;
        return a;
    }

    // 研修之星
    public List getTeacherStarList() {
        CommonQuery qa = new CommonQuery(this.getSession());
        qa.setMaxRow(12);
        qa.setStartRow(0);
        qa.setHql("SELECT new Map( u.userId as userId, u.loginName as loginName, u.userIcon as userIcon, u.blogName as blogName, u.trueName as trueName, u.visitCount as visitCount) FROM User as u");
        qa.setWhereClause("u.userStatus = 0 And u.userType LIKE '%/5/%' ");
        // qa.setOrderByClause("u.articleScore + u.resourceScore + u.commentScore desc");
        qa.setOrderByClause("newid()"); // 随机排序
        List a = qa.getQueryList();
        qa = null;
        return a;
    }

    public List getBestGroupList() {
        CommonQuery qa = new CommonQuery(this.getSession());
        qa.setMaxRow(4);
        qa.setStartRow(0);
        qa.setHql("SELECT new Map( g.groupId as groupId, g.groupName as groupName, g.groupTitle as groupTitle, g.groupIcon as groupIcon,g.groupIntroduce as groupIntroduce) FROM Group as g");
        qa.setWhereClause("g.groupState = 0 And g.isBestGroup = true");
        qa.setOrderByClause("newid()");
        List a = qa.getQueryList();
        qa = null;
        return a;
    }

    public List getHotGroupList() {
        CommonQuery qa = new CommonQuery(this.getSession());
        qa.setMaxRow(7);
        qa.setStartRow(0);
        qa.setHql("SELECT new Map(g.groupId as groupId, g.groupName as groupName, g.groupTitle as groupTitle, g.groupIcon as groupIcon,g.groupIntroduce as groupIntroduce) FROM Group as g");
        qa.setWhereClause("g.groupState = 0 ");
        qa.setOrderByClause("g.visitCount DESC");
        List a = qa.getQueryList();
        qa = null;
        return a;
    }

    public List getRcmdGroupList() {
        CommonQuery qa = new CommonQuery(this.getSession());
        qa.setMaxRow(7);
        qa.setStartRow(0);
        qa.setHql("SELECT new Map( g.groupId as groupId, g.groupName as groupName, g.groupTitle as groupTitle, g.groupIcon as groupIcon,g.groupIntroduce as groupIntroduce) FROM Group as g");
        qa.setWhereClause("g.groupState = 0 And g.isRecommend = true");
        qa.setOrderByClause("g.groupId DESC");
        List a = qa.getQueryList();
        qa = null;
        return a;
    }

    public List getNewGroupList() {
        CommonQuery qa = new CommonQuery(this.getSession());
        qa.setMaxRow(7);
        qa.setStartRow(0);
        qa.setHql("SELECT new Map( g.groupId as groupId, g.groupName as groupName, g.groupTitle as groupTitle, g.groupIcon as groupIcon,g.groupIntroduce as groupIntroduce) FROM Group as g");
        qa.setWhereClause("g.groupState = 0");
        qa.setOrderByClause("g.groupId DESC");
        List a = qa.getQueryList();
        qa = null;
        return a;
    }

    public List getNewBlogList() {
        CommonQuery qa = new CommonQuery(this.getSession());
        qa.setMaxRow(7);
        qa.setStartRow(0);
        qa.setHql("SELECT new Map(u.userId as userId, u.loginName as loginName, u.userIcon as userIcon, u.blogName as blogName, u.nickName as nickName, u.blogIntroduce as blogIntroduce) FROM User as u");
        qa.setWhereClause("u.userStatus = 0");
        qa.setOrderByClause("u.userId DESC");
        List a = qa.getQueryList();
        qa = null;
        return a;
    }

    public List getHotBlogList() {
        CommonQuery qa = new CommonQuery(this.getSession());
        qa.setMaxRow(7);
        qa.setStartRow(0);
        qa.setHql("SELECT new Map(u.userId as userId, u.loginName as loginName, u.userIcon as userIcon, u.blogName as blogName, u.nickName as nickName, u.blogIntroduce as blogIntroduce) FROM User as u");
        qa.setWhereClause("u.userStatus = 0");
        qa.setOrderByClause("u.visitCount DESC");
        List a = qa.getQueryList();
        qa = null;
        return a;
    }

    public List getScoreBlogList() {
        CommonQuery qa = new CommonQuery(this.getSession());
        qa.setMaxRow(7);
        qa.setStartRow(0);
        qa.setHql("SELECT new Map(u.userId as userId, u.loginName as loginName, u.userIcon as userIcon, u.blogName as blogName, u.nickName as nickName, u.blogIntroduce as blogIntroduce,u.articleScore + u.resourceScore + u.commentScore as score) FROM User as u");
        qa.setWhereClause("u.userStatus = 0");
        qa.setOrderByClause("u.articleScore + u.resourceScore + u.commentScore DESC");
        List a = qa.getQueryList();
        qa = null;
        return a;
    }

    public List getRcmdBlogList() {
        CommonQuery qa = new CommonQuery(this.getSession());
        qa.setMaxRow(6);
        qa.setStartRow(0);
        qa.setHql("SELECT new Map(u.userId as userId, u.loginName as loginName, u.userIcon as userIcon, u.blogName as blogName, u.blogIntroduce as blogIntroduce) FROM User as u");
        qa.setWhereClause("u.userStatus = 0 And u.userType LIKE '%/2/%'");
        qa.setOrderByClause("u.userId DESC");
        List a = qa.getQueryList();
        qa = null;
        return a;
    }

    public List getGroupTopicList() {
        CommonQuery qa = new CommonQuery(this.getSession());
        qa.setMaxRow(5);
        qa.setStartRow(0);
        qa.setHql("SELECT new Map(t.createDate as createDate, t.title as title, t.groupId as groupId, t.topicId as topicId) FROM Topic as t, Group g");
        qa.setWhereClause("t.isDeleted = false And t.groupId = g.groupId");
        qa.setOrderByClause("t.topicId DESC");
        List a = qa.getQueryList();
        qa = null;
        return a;
    }

    public List getNewPhotoList() {
        CommonQuery qa = new CommonQuery(this.getSession());
        qa.setMaxRow(6);
        qa.setStartRow(0);
        qa.setHql("SELECT new Map(p.id as id, p.title as title, p.createDate as createDate, p.href as href, u.userId as userId, u.loginName as loginName, p.summary as summary) FROM Photo as p, User u");
        qa.setWhereClause("p.auditState = 0 And p.userId = u.userId And p.isPrivateShow = false");
        qa.setOrderByClause("p.id DESC");
        List a = qa.getQueryList();
        qa = null;
        return a;
    }

    public List getNewVideoList() {
        CommonQuery qa = new CommonQuery(this.getSession());
        qa.setMaxRow(6);
        qa.setStartRow(0);
        qa.setHql("SELECT new Map(v.videoId as videoId, v.title as title, v.createDate as createDate, v.href as href, v.flvHref as flvHref, v.userId as userId, u.loginName as loginName, v.summary as summary,v.flvThumbNailHref as flvThumbNailHref) FROM Video as v, User u");
        qa.setWhereClause("v.auditState = 0 And v.userId = u.userId");
        qa.setOrderByClause("v.videoId DESC");
        List a = qa.getQueryList();
        qa = null;
        return a;
    }

    public List getSitePlacardList() {
        CommonQuery qa = new CommonQuery(this.getSession());
        qa.setMaxRow(7);
        qa.setStartRow(0);
        qa.setHql("SELECT new Map(pld.id as id, pld.title as title, pld.createDate as createDate) FROM Placard pld");
        qa.setWhereClause("pld.hide = false And pld.objType = 100");
        qa.setOrderByClause("pld.id DESC");
        List a = qa.getQueryList();
        qa = null;
        return a;
    }

    public List getJitarActionList() {
        CommonQuery qa = new CommonQuery(this.getSession());
        qa.setMaxRow(7);
        qa.setStartRow(0);
        qa.setHql("SELECT new Map(act.title as title, act.createDate as createDate, act.actionId as actionId, act.ownerId as ownerId, act.ownerType as ownerType, act.createUserId as createUserId, act.actionType as actionType, "
                + " act.description as description, act.userLimit as userLimit, act.startDateTime as startDateTime,act.finishDateTime as finishDateTime, act.attendLimitDateTime as attendLimitDateTime, act.place as place, "
                + "act.status as status, act.visibility as visibility, act.attendCount as attendCount,u.loginName as loginName,u.trueName as trueName) FROM Action act,User u");
        qa.setWhereClause("act.createUserId = u.userId");
        qa.setOrderByClause("act.actionId DESC");
        List a = qa.getQueryList();
        qa = null;
        return a;
    }

    public List getSchoolShowList() {
        CommonQuery qa = new CommonQuery(this.getSession());
        qa.setMaxRow(10);
        qa.setStartRow(0);
        qa.setHql(" FROM Unit unit");
        qa.setWhereClause("unit.parentId <> 0 ");
        qa.setOrderByClause("unit.rank DESC");
        List a = qa.getQueryList();
        qa = null;
        return a;
    }

    public List getTeacherShowList() {
        CommonQuery qa = new CommonQuery(this.getSession());
        qa.setMaxRow(8);
        qa.setStartRow(0);
        qa.setHql("SELECT new Map( u.userId as userId, u.loginName as loginName, u.userIcon as userIcon, u.blogName as blogName, u.trueName as trueName, u.visitCount as visitCount) FROM User as u");
        qa.setWhereClause("u.userStatus = 0 And u.userType LIKE '%/6/%' ");
        qa.setOrderByClause("newid()");
        List a = qa.getQueryList();
        qa = null;
        return a;
    }

    public List getHotTagList() {
        CommonQuery qa = new CommonQuery(this.getSession());
        qa.setMaxRow(40);
        qa.setStartRow(0);
        qa.setHql("SELECT new Map(tag.tagId as tagId, tag.tagName as tagName) FROM Tag tag");
        qa.setWhereClause("tag.disabled = false");
        qa.setOrderByClause("tag.refCount DESC");
        List a = qa.getQueryList();
        qa = null;
        return a;
    }

    public void Articles() {

    }

    public void UnitIndex() {
        List<Unit> unitList = this.unitService.getAllUnitOrChildUnitList(null);
        for (int i = 0; i < unitList.size(); i++) {
            Unit unit = (Unit) unitList.get(i);
            this.UnitIndex(unit);
        }
        unitList.clear();
    }

    /**
     * 为了方便提供预览功能，增加了参数tempTemplateName(布局)和themeName(样式)
     * 
     * @param unit
     * @param tempTemplateName
     * @return
     */
    public String UnitIndex(Unit unit, String tempTemplateName, String themeName) {
        if (unit == null)
            return "";
        WebApplicationContext wac = ContextLoader.getCurrentWebApplicationContext();
        ServletContext sc = wac.getServletContext();
        siteUrl = sc.getInitParameter("siteUrl");
        if (siteUrl == null || siteUrl.length() == 0) {
            siteUrl = sc.getContextPath();
        }
        if (siteUrl.endsWith("/") == false) {
            siteUrl += "/";
        }
        siteThemeUrl = configService.getConfigure().getStringValue("siteThemeUrl");

        String configUnitSiteRoot = sc.getInitParameter("unitUrlPattern");

        if (configUnitSiteRoot == null || configUnitSiteRoot.equals("")) {
            this.unitRootUrl = this.siteUrl + "d/" + unit.getUnitName() + "/";
        } else {
            this.unitRootUrl = configUnitSiteRoot.replaceAll("\\{unitName\\}", unit.getUnitName());
        }

        if (null == tempTemplateName || tempTemplateName.length() == 0) {
            templateName = unit.getTemplateName();
        } else {
            templateName = tempTemplateName;
        }
        // log.info("templateName=" + unit.getUnitId() + templateName);
        if (templateName == null || templateName.trim().equals("")) {
            templateName = "template1";
        }

        HashMap<String, Object> map = new HashMap<String, Object>();

        if (null == themeName || themeName.length() == 0) {
        } else {
            map.put("theme", themeName);
        }

        List<UnitWebpart> webpartList = (List<UnitWebpart>) this.unitService.getUnitWebpartList(unit.getUnitId());
        for (int i = 0; i < webpartList.size(); i++) {

            UnitWebpart w = (UnitWebpart) webpartList.get(i);

            if (w.getWebpartZone() == UnitWebpart.WEBPART_TOP)
                map.put("hasTopWebpart", "1");
            else if (w.getWebpartZone() == UnitWebpart.WEBPART_BOTTOM)
                map.put("hasBottomWebpart", "1");
            else if (w.getWebpartZone() == UnitWebpart.WEBPART_LEFT)
                map.put("hasLeftWebpart", "1");
            else if (w.getWebpartZone() == UnitWebpart.WEBPART_MIDDLE)
                map.put("hasMiddleWebpart", "1");
            else if (w.getWebpartZone() == UnitWebpart.WEBPART_RIGHT)
                map.put("hasRightWebpart", "1");

            if (w.getModuleName().equals(UnitWebpart.WEBPART_MODULENAME_ARTICLE)) {
                map.put("webpart" + w.getUnitWebpartId(), GenernateUnitArticleContent(unit, w));
            } else if (w.getModuleName().equals(UnitWebpart.WEBPART_MODULENAME_LINKS)) {
                map.put("webpart" + w.getUnitWebpartId(), GenernateUnitLinksContent(unit, w));
            } else if (w.getModuleName().equals(UnitWebpart.WEBPART_MODULENAME_NEWESTNEWS)) {
                map.put("webpart" + w.getUnitWebpartId(), GenernateUnitNewsContent(unit, w));
            } else if (w.getModuleName().equals(UnitWebpart.WEBPART_MODULENAME_PHOTO)) {
                map.put("webpart" + w.getUnitWebpartId(), GenernateUnitPhotoContent(unit, w));
            } else if (w.getModuleName().equals(UnitWebpart.WEBPART_MODULENAME_PICNEWS)) {
                map.put("webpart" + w.getUnitWebpartId(), GenernateUnitPicNewsContent(unit, w));
            } else if (w.getModuleName().equals(UnitWebpart.WEBPART_MODULENAME_RESOURCE)) {
                map.put("webpart" + w.getUnitWebpartId(), GenernateUnitResourceContent(unit, w));
            } else if (w.getModuleName().equals(UnitWebpart.WEBPART_MODULENAME_STATISTICS)) {
                map.put("webpart" + w.getUnitWebpartId(), GenernateUnitStatsContent(unit, w));
            } else if (w.getModuleName().equals(UnitWebpart.WEBPART_MODULENAME_UNITNOTICE)) {
                map.put("webpart" + w.getUnitWebpartId(), GenernateUnitNoticeContent(unit, w));
            } else if (w.getModuleName().equals(UnitWebpart.WEBPART_MODULENAME_VIDEO)) {
                map.put("webpart" + w.getUnitWebpartId(), GenernateUnitVideoContent(unit, w));
            } else if (w.getModuleName().equals(UnitWebpart.WEBPART_MODULENAME_VOTE)) {
                map.put("webpart" + w.getUnitWebpartId(), GenernateUnitVoteContent(unit, w));
            } else if (w.getModuleName().equals(UnitWebpart.WEBPART_MODULENAME_UNITSUBJECT)) {
                map.put("webpart" + w.getUnitWebpartId(), GenernateUnitSubjectContent(unit, w));
            } else if (w.getModuleName().equals(UnitWebpart.WEBPART_MODULENAME_UNITGROUP)) {
                map.put("webpart" + w.getUnitWebpartId(), GenernateUnitGroupContent(unit, w));
            } else if (w.getModuleName().equals(UnitWebpart.WEBPART_MODULENAME_UNITPREPARECOURSE)) {
                map.put("webpart" + w.getUnitWebpartId(), GenernateUnitPrepareCourseContent(unit, w));
            } else {
                map.put("webpart" + w.getUnitWebpartId(), GenernateUnitCustormContent(unit, w));
            }
        }

        List<SiteNav> unitSiteNavList = this.siteNavService.getAllSiteNav(false, 1, unit.getUnitId());
        map.put("unit", unit);
        map.put("head_nav", "unit");
        map.put("webpartList", webpartList);
        map.put("SiteUrl", this.siteUrl);
        map.put("UnitRootUrl", this.unitRootUrl);
        map.put("UnitUrlPattern", configUnitSiteRoot);
        map.put("UnitSiteNavList", unitSiteNavList);

        String html = this.templateProcessor.processTemplate(map, "/WEB-INF/unitspage/" + this.templateName + "/html_index.ftl", "utf-8");

        String path = this.getClass().getResource("").getPath().toString();
        if (path == null) {
            System.out.println("无法得到网站路径信息，请与程序开发人员联系。");
            return "无法得到网站路径信息，请与程序开发人员联系。";
        }
        File file = null;
        OutputStreamWriter fw = null;
        String root_dir = path.substring(0, path.indexOf("WEB-INF"));
        String root_path = root_dir + "html/unit/" + unit.getUnitName() + "/";
        root_path = root_path.replace("\\", "/");
        root_path = root_path.replace("/", File.separator);
        try {
            root_path = URLDecoder.decode(root_path, "utf-8");
        } catch (UnsupportedEncodingException e) {
        }

        try {
            file = new File(root_path);
            if (!file.exists())
                file.mkdirs();
            file = new File(root_path + "index.html");
            fw = new OutputStreamWriter(new FileOutputStream(file), "UTF-8");

            fw.write(html);
            fw.flush();
            fw.close();
        } catch (Exception e) {
            e.printStackTrace();
            e.printStackTrace(System.out);
            html = "生成页面时出现错误：" + e.getMessage();
            System.out.println("生成页面时出现错误：" + e.getMessage());
        } finally {
            if (file != null) {
                file = null;
            }
            if (fw != null) {
                fw = null;
            }
        }
        return html;
    }

    public String UnitIndex(Unit unit) {
        return UnitIndex(unit, null, null);
    }

    private String GenernateUnitSubjectContent(Unit unit, UnitWebpart w) {
        List<UnitSubject> unitsubjectlist = this.unitService.getSubjectByUnitId(unit.getUnitId());
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("unit", unit);
        map.put("webpart", w);
        map.put("UnitRootUrl", unitRootUrl);
        map.put("unitsubjectlist", unitsubjectlist);
        content = this.templateProcessor.processTemplate(map, "/WEB-INF/unitspage/" + this.templateName + "/subject.ftl", "utf-8");
        return content;
    }

    private String GenernateUnitPhotoContent(Unit unit, UnitWebpart w) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        CommonQuery qa = new CommonQuery(this.getSession());
        qa.setMaxRow(6);
        qa.setStartRow(0);
        qa.setHql("SELECT new Map(p.id as photoId, p.title as title, p.createDate as createDate, p.href as href, p.userId as userId,  p.userTrueName as userTrueName) FROM Photo as p");
        qa.setWhereClause("p.auditState = 0 And p.unitId = " + unit.getUnitId() + " And p.isPrivateShow = false");
        qa.setOrderByClause("p.id DESC");
        List photo_list = qa.getQueryList();
        map.put("photo_list", photo_list);
        map.put("unit", unit);
        map.put("webpart", w);
        map.put("UnitRootUrl", unitRootUrl);
        content = this.templateProcessor.processTemplate(map, "/WEB-INF/unitspage/" + this.templateName + "/photo.ftl", "utf-8");
        map.clear();
        photo_list.clear();
        return content;
    }

    private String GenernateUnitNewsContent(Unit unit, UnitWebpart w) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        CommonQuery qa = new CommonQuery(this.getSession());
        qa.setMaxRow(6);
        qa.setStartRow(0);
        qa.setHql("SELECT new Map(un.unitNewsId as unitNewsId, un.title as title, un.createDate as createDate) FROM UnitNews as un");
        qa.setWhereClause("un.itemType = 2 And un.unitId = " + unit.getUnitId());
        qa.setOrderByClause("un.unitNewsId DESC");
        List newest_news = qa.getQueryList();
        map.put("newest_news", newest_news);
        map.put("unit", unit);
        map.put("webpart", w);
        map.put("UnitRootUrl", unitRootUrl);
        content = this.templateProcessor.processTemplate(map, "/WEB-INF/unitspage/" + this.templateName + "/newest_news.ftl", "utf-8");

        return content;
    }

    private String GenernateUnitPicNewsContent(Unit unit, UnitWebpart w) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        CommonQuery qa = new CommonQuery(this.getSession());
        qa.setMaxRow(6);
        qa.setStartRow(0);
        qa.setHql("SELECT new Map(un.unitNewsId as unitNewsId, un.title as title, un.picture as picture, un.createDate as createDate) FROM UnitNews as un");
        qa.setWhereClause("un.itemType = 0 And un.unitId = " + unit.getUnitId());
        qa.setOrderByClause("un.unitNewsId DESC");
        List pic_news = qa.getQueryList();
        map.put("pic_news", pic_news);
        map.put("unit", unit);
        map.put("webpart", w);
        map.put("UnitRootUrl", unitRootUrl);
        content = this.templateProcessor.processTemplate(map, "/WEB-INF/unitspage/" + this.templateName + "/pic_news.ftl", "utf-8");

        return content;
    }

    private String GenernateUnitResourceContent(Unit unit, UnitWebpart w) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        CommonQuery qa = new CommonQuery(this.getSession());
        qa.setMaxRow(10);
        qa.setStartRow(0);
        qa.setHql("SELECT new Map( r.resourceId as resourceId, r.title as title, r.href as href, r.createDate as createDate, r.fsize as fsize, r.downloadCount as downloadCount,r.userId as userId,r.subjectId as subjectId,sc.name as scName,grade.gradeName as gradeName ) FROM Resource as r LEFT JOIN r.sysCate as sc LEFT JOIN r.grade as grade ");
        qa.setWhereClause("r.approvedPathInfo LIKE '%/" + unit.getUnitId() + "/%'");
        qa.setOrderByClause("r.resourceId DESC");
        List new_resource_list = qa.getQueryList();
        map.put("new_resource_list", new_resource_list);

        qa = new CommonQuery(this.getSession());
        qa.setMaxRow(10);
        qa.setStartRow(0);
        qa.setHql("SELECT new Map( r.resourceId as resourceId, r.title as title, r.href as href, r.createDate as createDate, r.fsize as fsize, r.downloadCount as downloadCount,r.userId as userId,r.subjectId as subjectId,sc.name as scName,grade.gradeName as gradeName ) FROM Resource as r LEFT JOIN r.sysCate as sc LEFT JOIN r.grade as grade ");
        qa.setWhereClause("r.approvedPathInfo LIKE '%/" + unit.getUnitId() + "/%'");
        qa.setOrderByClause("r.downloadCount DESC");
        List hot_resource_list = qa.getQueryList();
        map.put("hot_resource_list", hot_resource_list);

        qa = new CommonQuery(this.getSession());
        qa.setMaxRow(10);
        qa.setStartRow(0);
        qa.setHql("SELECT new Map( r.resourceId as resourceId, r.title as title, r.href as href, r.createDate as createDate, r.fsize as fsize, r.downloadCount as downloadCount,r.userId as userId,r.subjectId as subjectId,sc.name as scName,grade.gradeName as gradeName ) FROM Resource as r LEFT JOIN r.sysCate as sc LEFT JOIN r.grade as grade ");
        qa.setWhereClause("r.approvedPathInfo LIKE '%/" + unit.getUnitId() + "/%' And r.rcmdPathInfo LIKE '%/" + unit.getUnitId() + "/%'");
        qa.setOrderByClause("r.resourceId DESC");
        List rcmd_resource_list = qa.getQueryList();
        map.put("rcmd_resource_list", rcmd_resource_list);

        map.put("unit", unit);
        map.put("webpart", w);
        map.put("SiteUrl", this.siteUrl);
        map.put("UnitRootUrl", unitRootUrl);
        content = this.templateProcessor.processTemplate(map, "/WEB-INF/unitspage/" + templateName + "/resource.ftl", "utf-8");
        qa = null;
        return content;
    }

    private String GenernateUnitStatsContent(Unit unit, UnitWebpart w) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("unit", unit);
        map.put("webpart", w);
        map.put("UnitRootUrl", unitRootUrl);
        map.put("unitAllUserCount", unitService.getAllUserCount(unit));
        map.put("unitAllArticleCount", unitService.getAllArticleCount(unit));
        map.put("unitAllResourceCount", unitService.getAllResourceCount(unit));
        map.put("unitAllPhotoCount", unitService.getAllPhotoCount(unit));
        map.put("unitAllVideoCount", unitService.getAllVideoCount(unit));
        content = this.templateProcessor.processTemplate(map, "/WEB-INF/unitspage/" + this.templateName + "/unit_count.ftl", "utf-8");
        return content;
    }

    private String GenernateUnitVoteContent(Unit unit, UnitWebpart w) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("unit", unit);
        map.put("webpart", w);
        map.put("UnitRootUrl", unitRootUrl);
        content = this.templateProcessor.processTemplate(map, "/WEB-INF/unitspage/" + this.templateName + "/unit_vote.ftl", "utf-8");

        return content;
    }

    private String GenernateUnitVideoContent(Unit unit, UnitWebpart w) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        CommonQuery qa = new CommonQuery(this.getSession());
        qa.setMaxRow(4);
        qa.setStartRow(0);
        qa.setHql("SELECT new Map(v.videoId as videoId, v.title as title, v.createDate as createDate, v.href as href, v.flvHref as flvHref, v.userId as userId, u.loginName as loginName, u.nickName as nickName, v.summary as summary,v.flvThumbNailHref as flvThumbNailHref) FROM Video as v, User u");
        qa.setWhereClause("v.auditState = 0 And v.userId = u.userId And v.unitId=" + unit.getUnitId());
        qa.setOrderByClause("v.videoId DESC");
        List new_video_list = qa.getQueryList();
        map.put("new_video_list", new_video_list);
        map.put("unit", unit);
        map.put("webpart", w);
        map.put("UnitRootUrl", unitRootUrl);
        content = this.templateProcessor.processTemplate(map, "/WEB-INF/unitspage/" + this.templateName + "/video.ftl", "utf-8");
        return content;
    }

    private String GenernateUnitNoticeContent(Unit unit, UnitWebpart w) {

        HashMap<String, Object> map = new HashMap<String, Object>();
        CommonQuery qa = new CommonQuery(this.getSession());
        qa.setMaxRow(6);
        qa.setStartRow(0);
        qa.setHql("SELECT new Map(un.unitNewsId as unitNewsId, un.title as title, un.createDate as createDate) FROM UnitNews as un");
        qa.setWhereClause("un.itemType = 1 And un.unitId = " + unit.getUnitId());
        qa.setOrderByClause("un.unitNewsId DESC");
        List unit_notice = qa.getQueryList();
        map.put("unit_notice", unit_notice);
        map.put("unit", unit);
        map.put("webpart", w);
        map.put("UnitRootUrl", unitRootUrl);
        content = this.templateProcessor.processTemplate(map, "/WEB-INF/unitspage/" + this.templateName + "/newest_notice.ftl", "utf-8");

        return content;
    }

    private String GenernateUnitArticleContent(Unit unit, UnitWebpart webpart) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        CommonQuery qa = new CommonQuery(this.getSession());
        qa.setMaxRow(10);
        qa.setStartRow(0);
        qa.setHql("SELECT new Map( a.articleId as articleId, a.title as title, a.createDate as createDate,a.userId as userId, a.typeState as typeState,a.userTrueName as userTrueName) FROM Article as a ");
        qa.setOrderByClause("a.articleId DESC");
        qa.setWhereClause("a.auditState = 0 " + // 审核
                " AND a.draftState = false " + // 非草稿.
                " AND a.delState = false " + // 非删除.
                " AND a.hideState = 0" + " And a.approvedPathInfo Like '%/" + webpart.getUnitId() + "/%'");
        List newest_article_list = qa.getQueryList();
        map.put("newest_article_list", newest_article_list);

        qa = new CommonQuery(this.getSession());
        qa.setMaxRow(10);
        qa.setStartRow(0);
        qa.setHql("SELECT new Map( a.articleId as articleId, a.title as title, a.createDate as createDate,a.userId as userId, a.typeState as typeState,a.userTrueName as userTrueName) FROM Article as a ");
        qa.setOrderByClause("a.viewCount DESC");
        qa.setWhereClause("a.auditState = 0 " + // 审核
                " AND a.draftState = false " + // 非草稿.
                " AND a.delState = false " + // 非删除.
                " AND a.hideState = 0" + " And a.approvedPathInfo Like '%/" + webpart.getUnitId() + "/%'");

        List hot_article_list = qa.getQueryList();
        map.put("hot_article_list", hot_article_list);

        qa = new CommonQuery(this.getSession());
        qa.setMaxRow(10);
        qa.setStartRow(0);
        qa.setHql("SELECT new Map( a.articleId as articleId, a.title as title, a.createDate as createDate,a.userId as userId, a.typeState as typeState,a.userTrueName as userTrueName) FROM Article as a ");

        qa.setOrderByClause("a.articleId DESC");
        qa.setWhereClause("a.auditState = 0 "
                + // 审核
                " AND a.draftState = false "
                + // 非草稿.
                " AND a.delState = false "
                + // 非删除.
                " AND a.hideState = 0" + " And a.approvedPathInfo Like '%/" + webpart.getUnitId() + "/%' And a.rcmdPathInfo Like '%/"
                + webpart.getUnitId() + "/%'");

        List rcmd_article_list = qa.getQueryList();
        map.put("rcmd_article_list", rcmd_article_list);

        map.put("unit", unit);
        map.put("webpart", webpart);
        map.put("SiteUrl", this.siteUrl);
        map.put("UnitRootUrl", unitRootUrl);
        content = this.templateProcessor.processTemplate(map, "/WEB-INF/unitspage/" + templateName + "/article.ftl", "utf-8");
        qa = null;
        return content;

    }

    private String GenernateUnitLinksContent(Unit unit, UnitWebpart webpart) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        List<UnitLinks> links = (List<UnitLinks>) this.unitService.getUnitLinksByUnitId(unit.getUnitId());
        if (links != null)
            map.put("links", links);
        map.put("unit", unit);
        map.put("UnitRootUrl", this.unitRootUrl);
        map.put("webpart", webpart);
        content = this.templateProcessor.processTemplate(map, "/WEB-INF/unitspage/" + this.templateName + "/unit_links.ftl", "utf-8");
        return content;
    }

    private String GenernateUnitGroupContent(Unit unit, UnitWebpart webpart) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        CommonQuery qa = new CommonQuery(this.getSession());
        qa.setMaxRow(10);
        qa.setStartRow(0);
        qa.setHql("SELECT new Map(ug.groupName as groupName, ug.groupTitle as groupTitle) FROM UnitGroup as ug  ");
        qa.setOrderByClause("ug.groupId DESC");
        qa.setWhereClause("ug.unitId = " + unit.getUnitId());
        List unitGroupList = qa.getQueryList();
        map.put("unitGroupList", unitGroupList);

        map.put("unit", unit);
        map.put("webpart", webpart);
        map.put("SiteUrl", this.siteUrl);
        map.put("UnitRootUrl", unitRootUrl);
        content = this.templateProcessor.processTemplate(map, "/WEB-INF/unitspage/" + templateName + "/unit_group.ftl", "utf-8");
        qa = null;
        return content;
    }

    private String GenernateUnitPrepareCourseContent(Unit unit, UnitWebpart webpart) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        CommonQuery qa = new CommonQuery(this.getSession());
        qa.setMaxRow(10);
        qa.setStartRow(0);
        qa.setHql("SELECT new Map(pc.prepareCourseId as prepareCourseId, pc.title as title, pc.startDate as startDate, pc.endDate as endDate, u.userId as v) FROM PrepareCourse as pc,User as u  ");
        qa.setOrderByClause("pc.prepareCourseId DESC");
        qa.setWhereClause("pc.leaderId = u.userId And pc.status = 0 And u.unitId = " + unit.getUnitId());
        List unitPrepareCourseList = qa.getQueryList();
        map.put("unitPrepareCourseList", unitPrepareCourseList);

        map.put("unit", unit);
        map.put("webpart", webpart);
        map.put("SiteUrl", this.siteUrl);
        map.put("UnitRootUrl", unitRootUrl);
        content = this.templateProcessor.processTemplate(map, "/WEB-INF/unitspage/" + templateName + "/unit_preparecourse.ftl", "utf-8");
        qa = null;
        return content;
    }

    private String GenernateUnitCustormContent(Unit unit, UnitWebpart webpart) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("unit", unit);
        map.put("UnitRootUrl", this.unitRootUrl);
        map.put("webpart", webpart);
        content = this.templateProcessor.processTemplate(map, "/WEB-INF/unitspage/" + this.templateName + "/custorm.ftl", "utf-8");
        map.clear();
        return content;
    }

    public void GenernateSubjectNavFile(String rootPath) {
        String subjectFilePath = rootPath + "html" + File.separator;
        File ff = new File(subjectFilePath);
        if (ff.exists() == false)
            ff.mkdir();
        subjectFilePath += "subject_nav.html";
        ff = new File(subjectFilePath);
        if (ff.exists()) {
            ff = null;
            return;
        }

        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("SiteThemeUrl", siteThemeUrl);
        map.put("SiteUrl", siteUrl);
        // 页头导航
        ArrayList<SubjectNav> metaSubject;
        List<Grade> MetaGrade = new ArrayList<Grade>();
        CommonQuery qa = new CommonQuery(this.getSession());
        qa.setMaxRow(-1);
        qa.setStartRow(0);
        qa.setHql("SELECT DISTINCT metaGrade.gradeId FROM Subject");
        qa.setOrderByClause("metaGrade.gradeId ASC");
        List<Integer> gradeIdList = qa.getQueryList();
        metaSubject = new ArrayList<SubjectNav>();
        for (int i = 0; i < gradeIdList.size(); i++) {
            Grade grade = (Grade) subjectService.getGrade(gradeIdList.get(i));
            MetaGrade.add(grade);
            List<Subject> subj = subjectService.getSubjectByGradeId(grade.getGradeId());
            List<Object> ms = new ArrayList<Object>();
            if (subj != null) {
                for (int j = 0; j < subj.size(); j++) {
                    ms.add(((Subject) subj.get(j)).getMetaSubject());
                }
                SubjectNav sn = new SubjectNav(grade.getGradeName(), grade.getGradeId(), ms);
                metaSubject.add(sn);
            }
        }

        map.put("metaGrade", MetaGrade);
        map.put("meta_Grade", MetaGrade);
        map.put("SubjectNav", metaSubject);
        String retContent = this.templateProcessor.processTemplate(map, "/WEB-INF/ftl/site_subject_nav.ftl", "utf-8");
        map.clear();
        qa = null;

        OutputStreamWriter fsw = null;
        try {
            fsw = new OutputStreamWriter(new FileOutputStream(ff), "utf-8");
            fsw.write(retContent);
            fsw.flush();
        } catch (Exception ex) {
        } finally {
            try {
                ff = null;
                if (fsw != null) {
                    fsw.close();
                }
            } catch (Exception x) {
                ff.deleteOnExit();
            }
        }
    }

    public void setTemplateProcessor(TemplateProcessor templateProcessor) {
        this.templateProcessor = templateProcessor;
    }

    public void setJitarContext(JitarContext jitarContext) {
        this.jitarContext = jitarContext;
    }

    public void setConfigService(ConfigService configService) {
        this.configService = configService;
    }

    public void setUnitService(UnitService unitService) {
        this.unitService = unitService;
    }

    public void setArticleService(ArticleService articleService) {
        this.articleService = articleService;
    }

    public void setSiteNavService(SiteNavService siteNavService) {
        this.siteNavService = siteNavService;
    }

    public void setSubjectService(SubjectService subjectService) {
        this.subjectService = subjectService;
    }

    public void setStatService(StatService statService) {
        this.statService = statService;
    }

    public void setSiteIndexPartService(SiteIndexPartService siteIndexPartService) {
        this.siteIndexPartService = siteIndexPartService;
    }

    public void setProductConfigService(ProductConfigService productConfigService) {
        this.productConfigService = productConfigService;
    }

    public void setTimerCountService(TimerCountService timerCountService) {
        this.timerCountService = timerCountService;
    }

}
