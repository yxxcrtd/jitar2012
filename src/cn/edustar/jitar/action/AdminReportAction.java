package cn.edustar.jitar.action;

import java.util.List;

import org.springframework.web.context.ContextLoader;

import cn.edustar.data.Pager;
import cn.edustar.jitar.model.ObjectType;
import cn.edustar.jitar.pojos.Article;
import cn.edustar.jitar.pojos.Photo;
import cn.edustar.jitar.pojos.Report;
import cn.edustar.jitar.pojos.Resource;
import cn.edustar.jitar.pojos.Video;
import cn.edustar.jitar.service.ArticleService;
import cn.edustar.jitar.service.CacheService;
import cn.edustar.jitar.service.ConfigService;
import cn.edustar.jitar.service.PhotoService;
import cn.edustar.jitar.service.ReportQuery;
import cn.edustar.jitar.service.ReportService;
import cn.edustar.jitar.service.ResourceService;
import cn.edustar.jitar.service.VideoService;

/**
 * 举报内容管理。
 * 
 * @author mxh
 * 
 * 
 */
public class AdminReportAction extends ManageBaseAction {

    /** 举报服务 */
    private ReportService reportService;

    private ConfigService configService;

    private VideoService videoService;

    private ArticleService articleService;

    private PhotoService photoService;

    private ResourceService resourceService;
    
    private CacheService cacheService;
    /**
     * 
     */
    private static final long serialVersionUID = -5457916693522032535L;

    @Override
    protected String execute(String cmd) throws Exception {
        if (cmd.equals("list")) {
            return list();
        } else if (cmd.equals("delete")) {
            return delete();
        } else if (cmd.equals("unreport")) {
            return unreport();
        } else if (cmd.equals("deleteObject")) {
            return deleteObject();
        } else if (cmd.equals("unreport")) {
            return unreport();
        } else if (cmd.equals("deleteAll")) {
            return deleteAll();
        } else if (cmd.equals("processed")) {
            return processed();
        }
        return null;
    }

    private String deleteObject() {
        int reportId = this.param_util.safeGetIntParam("reportId");
        Report report = this.reportService.getReportById(reportId);
        if (null == report) {
            this.addActionError("无法加载对象。");
            this.addActionLink("返回列表", "adminReport.action?cmd=list");
            return ERROR;
        }
        
        cacheService = ContextLoader.getCurrentWebApplicationContext().getBean("cacheService",CacheService.class);

        if (report.getObjType() == ObjectType.OBJECT_TYPE_ARTICLE.getTypeId()) {
            Article article = this.articleService.getArticle(report.getObjId());
            if (article != null) {
                this.articleService.deleteArticle(article);
                cacheService.remove("article_" + report.getObjId());
            }            
        }
        else if(report.getObjType() == ObjectType.OBJECT_TYPE_RESOURCE.getTypeId()){
            Resource resource = this.resourceService.getResource(report.getObjId());
            if(resource != null){
                this.resourceService.deleteResource(resource);
            }            
        }else if(report.getObjType() == ObjectType.OBJECT_TYPE_PHOTO.getTypeId()){
            Photo photo = this.photoService.findById(report.getObjId());
            if(photo != null){
                this.photoService.delPhoto(photo);
            }            
        }else if(report.getObjType() == ObjectType.OBJECT_TYPE_VIDEO.getTypeId()){
            Video video = this.videoService.findById(report.getObjId());
            if(video != null){
                this.videoService.delVideo(video);
            }            
        }
        else{
            this.addActionError("未处理的类型。");
            this.addActionLink("返回列表", "adminReport.action?cmd=list");
        }
        
        //将举报设置为已处理
        report.setStatus(true);
        this.reportService.saveReport(report);
        this.addActionMessage("删除成功。");
        this.addActionLink("返回列表", "adminReport.action?cmd=list");
        return SUCCESS;
    }

    private String unreport() {
        int reportId = this.param_util.safeGetIntParam("reportId");
        Report report = this.reportService.getReportById(reportId);
        if (null == report) {
            this.addActionError("无法加载对象。");
            this.addActionLink("返回列表", "adminReport.action?cmd=list");
            return ERROR;
        }
        if (report.getObjType() == ObjectType.OBJECT_TYPE_ARTICLE.getTypeId()) {
            Article article = this.articleService.getArticle(report.getObjId());
            if (null != article) {
                this.articleService.updateArticle(article);
            }
        }

        this.addActionMessage("删除成功。");
        this.addActionLink("返回列表", "adminReport.action?cmd=list");
        return SUCCESS;
    }
    private String delete() {
        int reportId = this.param_util.safeGetIntParam("reportId");
        Report report = this.reportService.getReportById(reportId);
        if (null != report) {
            this.reportService.deleteReport(report);
        }
        this.addActionMessage("删除成功。");
        this.addActionLink("返回列表", "adminReport.action?cmd=list");
        return SUCCESS;
    }

    private String deleteAll() {
        List<Integer> ids = this.param_util.getIdList("id");
        Report report = null;
        for (Integer reportId : ids) {
            report = this.reportService.getReportById(reportId);
            if (null != report) {
                this.reportService.deleteReport(report);
            }
        }
        this.addActionMessage("删除成功。");
        this.addActionLink("返回列表", "adminReport.action?cmd=list");
        return SUCCESS;
    }

    private String processed() {
        List<Integer> ids = this.param_util.getIdList("id");
        Report report = null;
        for (Integer reportId : ids) {
            report = this.reportService.getReportById(reportId);
            if (null != report) {
                this.reportService.updateReportStatus(report, true);
            }
        }
        this.addActionMessage("标记处理成功。");
        this.addActionLink("返回列表", "adminReport.action?cmd=list");
        return SUCCESS;
    }

    @SuppressWarnings("rawtypes")
    private String list() {
        String reportType = null;
        String reportTypeConfig = configService.getConfigure().getConfigValue("reportType").getStringValue();
        if (reportTypeConfig == null || reportTypeConfig.length() == 0) {
            reportTypeConfig = "政治,色情,违法,人身攻击,抄袭,其它";
        } else {
            reportTypeConfig = reportTypeConfig.replaceAll("，", ",");
        }
        String[] reportTypeName = reportTypeConfig.split(",");
        Pager pager = this.param_util.createPager();
        pager.setPageSize(20);
        pager.setItemName("举报内容");
        pager.setItemUnit("条");

        reportType = this.param_util.getStringParam("type", null);
        if (reportType == null || reportType.length() == 0) {
            reportType = null;
        }
        String k = this.param_util.getStringParam("k", null);

        ReportQuery qry = new ReportQuery("r.reportId, r.objType, r.objId, r.createDate, r.objTitle, r.reportType, r.status, u.loginName, u.trueName");
        qry.reportType = reportType;
        qry.k = k;
        pager.setTotalRows(qry.count());
        List reports = (List) qry.query_map(pager);
        request.setAttribute("k", k);
        request.setAttribute("reportType", reportType);
        request.setAttribute("reports", reports);
        request.setAttribute("pager", pager);
        request.setAttribute("reportTypeName", reportTypeName);
        request.setAttribute("reportType", reportType);
        return "List_Success";
    }

    public void setReportService(ReportService reportService) {
        this.reportService = reportService;
    }

    public void setConfigService(ConfigService configService) {
        this.configService = configService;
    }

    public void setVideoService(VideoService videoService) {
        this.videoService = videoService;
    }

    public void setArticleService(ArticleService articleService) {
        this.articleService = articleService;
    }

    public void setPhotoService(PhotoService photoService) {
        this.photoService = photoService;
    }

    public void setResourceService(ResourceService resourceService) {
        this.resourceService = resourceService;
    }


}
