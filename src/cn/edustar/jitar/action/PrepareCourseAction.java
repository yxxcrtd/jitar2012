package cn.edustar.jitar.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.edustar.data.Pager;
import cn.edustar.jitar.JitarContext;
import cn.edustar.jitar.service.CacheService;
import cn.edustar.jitar.service.PrepareCourseQuery;
import cn.edustar.jitar.service.PrepareCourseService;
import cn.edustar.jitar.service.SubjectService;
import cn.edustar.jitar.util.HtmlPager;
/**
 * 备课
 * 
 * @author renliang
 */
public class PrepareCourseAction extends AbstractBasePageAction {

    private static final long serialVersionUID = 1753263484640653826L;

    /** serialVersionUID */

    private transient static final Log log = LogFactory.getLog(PrepareCourseAction.class);

    private JitarContext jitarContext;
    private PrepareCourseService prepareCourseService;
    private CacheService cache = null;
    private String stage = null;
    private SubjectService subjectService;
    private Integer subjectId = null;
    private Integer gradeId = null;
    public String k = null;
    public String ktype = null;
    public String course_BeginDate = null;
    public String course_EndDate = null;

    public String execute(String cmd) throws Exception {
        jitarContext = JitarContext.getCurrentJitarContext();
        stage = this.params.safeGetStringParam("type");
        if ("".equals(stage.trim()))
            stage = "running";
        request.setAttribute("type", stage);
        if (cmd.equals("ajax")) {
            preparecourse_list();
            return "ajax";
        }
        
        // # 页面导航高亮'
        request.setAttribute("head_nav", "cocourses");
        
        cache = jitarContext.getCacheProvider().getCache("page");
        // 学科学段树的实现移到父类中，其他类也可以用
        String outHtml = getGradeSubjectTreeHtml(cache);
        request.setAttribute("outHtml", outHtml);
        get_subject_list();
        get_grade_list();

        
        return "success";
    }

    private void preparecourse_list() {
        params.getStringParam("k"); // #查询类型[关键字对应的类型]
        params.getStringParam("ktype");

        if (ktype == null) {
            ktype = "1";
        } // #主备人所属机构 unit =
        params.getStringParam("unit");
        course_BeginDate = params.getStringParam("course_BeginDate");
        course_EndDate = params.getStringParam("course_EndDate");
        subjectId = params.getIntParamZeroAsNull("subjectId");
        gradeId = params.getIntParamZeroAsNull("gradeId");

        request.setAttribute("subjectId", subjectId);
        request.setAttribute("gradeId", gradeId);
        request.setAttribute("k", k);
        request.setAttribute("ktype", ktype);
        request.setAttribute("course_BeginDate", course_BeginDate);
        request.setAttribute("course_EndDate", course_EndDate);

        Pager pager = params.createPager();
        pager.setItemName("备课");
        pager.setItemUnit("个");
        pager.setPageSize(20);
        PrepareCourseQuery qry = new PrepareCourseQuery(
                "pc.createUserId,pc.leaderId,pc.memberCount,pc.articleCount,pc.resourceCount,pc.status,pc.actionCount,pc.topicCount,pc.topicReplyCount,pc.viewCount,pc.startDate,pc.endDate,pc.title, pc.prepareCourseId, pc.createDate, pc.metaSubjectId, pc.gradeId,pc.recommendState,u.loginName,u.trueName");
        qry.gradeId = gradeId;
        qry.subjectId = subjectId;
        qry.k = k;
        qry.ktype = ktype;
        qry.course_BeginDate = course_BeginDate;
        qry.course_EndDate = course_EndDate;
        qry.status = 0;
        qry.stage = stage;
        String target = "";
        if (gradeId != null && gradeId % 1000 == 0) {
            target = "child";
        }
        if (target.equals("child")) {
            qry.containChild = "True";
        }
        else{
            qry.containChild = "False";
        }

        pager.setTotalRows(qry.count());
        List course_list = (List) qry.query_map(pager);
        Long sPrivateCount = (long) 0;
        Long sEditCount = (long) 0;
        List privateCountList = new ArrayList();
        List editCountList = new ArrayList();
        if (course_list.size() > 0) {
            Iterator<String> iterator = course_list.iterator();
            while (iterator.hasNext()) {
                Object obj = (Object) iterator.next();
                obj = (HashMap<String, String>) obj;
                if (obj instanceof HashMap) {
                    Map pc = (HashMap<String, String>) obj;
                    Integer pcId = (Integer) pc.get("prepareCourseId");
                    sPrivateCount = prepareCourseService.getPrepareCourseContentCount(pcId);
                    sEditCount = prepareCourseService.getPrepareCourseEditCount(pcId);
                    privateCountList.add(sPrivateCount);
                    editCountList.add(sEditCount);
                }
            }
        }
        request.setAttribute("privateCountList", privateCountList);
        request.setAttribute("editCountList", editCountList);
        request.setAttribute("course_list", course_list);
        // request.setAttribute("pager", pager);
        String html = HtmlPager.render(pager.getTotalPages(), 3, pager.getCurrentPage());
        request.setAttribute("HtmlPager", html);
    }

    private void get_grade_list() {
        request.setAttribute("gradeId", params.getIntParamZeroAsNull("gradeId"));
        request.setAttribute("grade_list", subjectService.getGradeList());
    }

    private void get_subject_list() {
        request.setAttribute("subject_list", subjectService.getMetaSubjectList());
    }

    public void setPrepareCourseService(PrepareCourseService prepareCourseService) {
        this.prepareCourseService = prepareCourseService;
    }

    public void setJitarContext(JitarContext jitarContext) {
        this.jitarContext = jitarContext;
    }

    public void setSubjectService(SubjectService subjectService) {
        this.subjectService = subjectService;
    }
}
