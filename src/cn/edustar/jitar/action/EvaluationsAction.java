package cn.edustar.jitar.action;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.web.context.ContextLoader;

import cn.edustar.data.Pager;
import cn.edustar.jitar.JitarContext;
import cn.edustar.jitar.pojos.EvaluationContent;
import cn.edustar.jitar.pojos.EvaluationPlan;
import cn.edustar.jitar.pojos.EvaluationPlanTemplate;
import cn.edustar.jitar.pojos.EvaluationResource;
import cn.edustar.jitar.pojos.EvaluationTemplate;
import cn.edustar.jitar.pojos.EvaluationTemplateFields;
import cn.edustar.jitar.pojos.EvaluationVideo;
import cn.edustar.jitar.pojos.Grade;
import cn.edustar.jitar.pojos.Resource;
import cn.edustar.jitar.pojos.Subject;
import cn.edustar.jitar.pojos.Unit;
import cn.edustar.jitar.pojos.User;
import cn.edustar.jitar.pojos.Video;
import cn.edustar.jitar.service.EvaluationContentQuery;
import cn.edustar.jitar.service.EvaluationPlanQuery;
import cn.edustar.jitar.service.EvaluationService;
import cn.edustar.jitar.service.ResourceService;
import cn.edustar.jitar.service.SubjectService;
import cn.edustar.jitar.service.UnitService;
import cn.edustar.jitar.service.UserService;
import cn.edustar.jitar.service.VideoService;
import cn.edustar.jitar.util.HtmlPager;

import com.alibaba.fastjson.JSONObject;

public class EvaluationsAction extends AbstractBasePageAction {

    private static final long serialVersionUID = -8773587023392363775L;
    private SubjectService subjectService;
    private EvaluationService evaluationService;
    private VideoService video_svc;
    private ResourceService res_svc;
    private JitarContext jitarContext;
    
    public String execute(String cmd) throws Exception {
        if (null == cmd || cmd.length() == 0) {
            cmd = "show";
        }
        if("ajax".equals(cmd)){
        	return ajax();
        }else if ("show".equals(cmd)) {
            return show();
        } else if ("list".equals(cmd)) {
            return list();
        } else if ("add".equals(cmd)) {
            return add();
        } else if ("edit".equals(cmd)) {
            return edit();
        } else if ("save".equals(cmd)) {
            return save();
        } else if ("content".equals(cmd)) {
            return content();
        } else if ("fieldcontent".equals(cmd)) {
            return fieldcontent();
        } else if ("savecontent".equals(cmd)) {
            if (getLoginUser() == null) {
                addActionError("请先登录，然后才能进行评课");
                addActionLink("登录", "/login2/login.jsp", "_top");
                return ERROR;
            }
            Integer evaluationPlanId = params.getIntParam("evaluationPlanId");
            if (evaluationPlanId == 0) {
                addActionError("缺少评课Id。");
                return ERROR;
            }
            savecontent();
            response.sendRedirect(request.getContextPath() + "/evaluations.action?cmd=content&evaluationPlanId=" + evaluationPlanId);
            return NONE;
            //return content();
        } else {
            return show();
        }
    }

    private String ajax() {
    	  String type = params.getStringParam("type");
          if (type == null || type.length() == 0) {
              type = "doing";
          }
          String k = params.getStringParam("k");
          String kperson = params.getStringParam("kperson");
          Integer subjectId = params.getIntParamZeroAsNull("subjectId");
          Integer gradeId = params.getIntParamZeroAsNull("gradeId");
          request.setAttribute("gradeId", gradeId);
          request.setAttribute("type", type);
          request.setAttribute("subjectId", subjectId);
          request.setAttribute("k", k);
          request.setAttribute("kperson", kperson);

          Pager pager = params.createPager();
          pager.setItemNameAndUnit("评课", "个");
          pager.setPageSize(15);

          EvaluationPlanQuery qry = new EvaluationPlanQuery(
                  "ev.evaluationPlanId, ev.evaluationCaption,ev.metaSubjectId, ev.metaGradeId, ev.startDate, ev.endDate, ev.teacherId, ev.teacherName, ev.teachDate, ev.createrId,ev.enabled,subj.msubjName, grad.gradeName");
          if (type.equals("doing")) {
              qry.userId = 0;
              qry.listType = 1;
          } else if (type.equals("finished")) {
              qry.userId = 0;
              qry.listType = 0;
          } else if (type.equals("mine")) {
              if (getLoginUser() == null) {
                  // String errDesc = "请先<a href='login.jsp'>登录</a>，然后才能操作";
                  // response.getWriter().write(errDesc);
                  // return NONE;
                  addActionError("请先登录，然后才能操作");
                  addActionLink(new ActionLink("登录", "login.jsp", "_top"));
                  return ERROR;
              }
              qry.userId = getLoginUser().getUserId();
              qry.listType = 2;
          } else if (type.equals("done")) {
              if (getLoginUser() == null) {
                  // String errDesc = "请先<a href='login.jsp'>登录</a>，然后才能操作";
                  // response.getWriter().write(errDesc);
                  // return NONE;
                  addActionError("请先登录，然后才能操作");
                  addActionLink(new ActionLink("登录", "login.jsp", "_top"));
                  return ERROR;

              }
              qry.userId = getLoginUser().getUserId();
              qry.listType = 3;
          } else {
              qry.listType = 1;
          }
          // #查询条件
          qry.enabled = true;
          qry.title = k;
          qry.subjectId = subjectId;
          qry.teacherName = kperson;
          qry.gradeId = gradeId;

          pager.setTotalRows(qry.count());
          List plan_list = (List) qry.query_map(pager);
          request.setAttribute("pager", pager);
          request.setAttribute("plan_list", plan_list);
          
          String html = HtmlPager.render(pager.getTotalPages(), 3, pager.getCurrentPage());
          request.setAttribute("HtmlPager", html);
          
          return "ajax";
	}

	private String fieldcontent() {
        Integer templateId = params.getIntParam("templateId");
        if (templateId > 0) {
            List<EvaluationTemplateFields> field_list = this.evaluationService.getEvaluationTemplateFields(templateId);
            request.setAttribute("field_list", field_list);
        }
        return "fieldcontent";
    }

    /**
     * 显示某个人的评课
     * 
     * @return
     */
    private String list() {
        String type = params.getStringParam("listType");
        String ispage = params.getStringParam("ispage");
        if (ispage == null) {
            ispage = "";
        }
        Integer userId = params.getIntParamZeroAsNull("userId");
        request.setAttribute("ispage", ispage);
        Pager pager = null;
        if (ispage.equals("1")) {
            pager = params.createPager();
            pager.setItemNameAndUnit("评课", "个");
            pager.setPageSize(15);
        }
        EvaluationPlanQuery qry = new EvaluationPlanQuery(
                "ev.evaluationPlanId, ev.evaluationCaption,ev.metaSubjectId, ev.metaGradeId, ev.startDate, ev.endDate, ev.teacherId, ev.teacherName,ev.teachDate,ev.createrId,subj.msubjName, grad.gradeName");
        qry.userId = userId;
        qry.enabled = true;
        if (type.equals("1")) {
            qry.listType = 1;
        } else if (type.equals("0")) {
            qry.listType = 0;
        } else {
            qry.listType = 1;
        }
        List plan_list = null;
        if (ispage.equals("1")) {
            pager.setTotalRows(qry.count());
            plan_list = (List) qry.query_map(pager);
            request.setAttribute("pager", pager);
        } else {
            plan_list = qry.query_map();
        }
        request.setAttribute("plan_list", plan_list);
        return "list";
    }
    /**
     * 对应 evaluation_content.py
     * 
     * @return
     */
    private String content() {
        Integer evaluationPlanId = params.getIntParam("evaluationPlanId");
        if (evaluationPlanId == 0) {
            addActionError("缺少评课Id。");
            return ERROR;
        }
        EvaluationPlan evaluationPlan = this.evaluationService.getEvaluationPlanById(evaluationPlanId);
        if (evaluationPlan == null) {
            addActionError("没有找到该评课。");
            return ERROR;
        }
        // #判断是否过期,过期的按照完成状态来显示
        int finish = 0;
        Date beginDate = evaluationPlan.getStartDate();
        Date endDate = evaluationPlan.getEndDate();
        Date nowDate = new Date();
        if (beginDate.compareTo(nowDate) > -1 || endDate.compareTo(nowDate) < 1) {
            finish = 1;
        }
        request.setAttribute("finish", finish);

        if (finish == 0) {
            if (getLoginUser() == null) {
                addActionError("请先登录，然后才能进行评课");
                addActionLink("登录", "login.jsp", "_top");
                return ERROR;
            }
        }
        String unitName = "";
        int teacherId =  evaluationPlan.getTeacherId();
        UserService userService = ContextLoader.getCurrentWebApplicationContext().getBean("userService", UserService.class);
        User user = userService.getUserById(teacherId);
        if(null!=user){
        	Unit unit = unitService.getUnitById(user.getUnitId());
        	if(null!=unit){
        		unitName = unit.getUnitTitle();
        	}
        }
        request.setAttribute("unitName", unitName);
        request.setAttribute("evaluationPlan", evaluationPlan);

        List<EvaluationTemplate> plantemplate_list = this.evaluationService.getEvaluationTemplates(evaluationPlanId);
        request.setAttribute("plantemplate_list", plantemplate_list);

        List<EvaluationVideo> video_list = this.evaluationService.getVideosAuditState(evaluationPlanId);
        List<EvaluationResource> resource_list = this.evaluationService.getResourcesAuditState(evaluationPlanId);
        request.setAttribute("video_list", video_list);
        request.setAttribute("resource_list", resource_list);

        String msubjName = "", gradeName = "";
        Integer metaSubjectId = evaluationPlan.getMetaSubjectId();
        Integer metaGradeId = evaluationPlan.getMetaGradeId();
        Subject subject = this.subjectService.getSubjectByMetaData(metaSubjectId, metaGradeId);
        Grade grade = this.subjectService.getGrade(metaGradeId);
        if (subject != null) {
            msubjName = subject.getSubjectName();
        }
        if (grade != null) {
            gradeName = grade.getGradeName();
        }
        request.setAttribute("msubjName", msubjName);
        request.setAttribute("gradeName", gradeName);

        List<EvaluationContent> content_list = this.evaluationService.getEvaluationContents(evaluationPlanId);
        request.setAttribute("content_list", content_list);
        return "content";
    }

    /**
     * 对应 evaluation_content.py中的POST处理部分
     */
    private void savecontent() {
        Integer evaluationPlanId = params.getIntParam("evaluationPlanId");
        Integer templateId = params.getIntParam("tempId");
        List<EvaluationTemplateFields> field_list = this.evaluationService.getEvaluationTemplateFields(templateId);
        String content = "";
        for (EvaluationTemplateFields f : field_list) {
            String fname = params.safeGetStringParam("fieldname" + f.getFieldsId());
            String fconntent = params.safeGetStringParam("fieldcontent" + f.getFieldsId());
            if (!fname.equals("")) {
                JSONObject json = new JSONObject();
                json.put(fname, fconntent);
                content += json.toJSONString() + ",";
                json = null;
            }
        }
        if (!content.equals("")) {
            if (content.endsWith(",")) {
                content = content.substring(0, content.length() - 1);
            }
            content = "[" + content + "]";
        }
        // #保存评课内容
        EvaluationContent evaluationContent = new EvaluationContent();
        evaluationContent.setPublishUserId(getLoginUser().getUserId());
        evaluationContent.setPublishUserName(getLoginUser().getTrueName());
        evaluationContent.setEvaluationPlanId(evaluationPlanId);
        evaluationContent.setEvaluationTemplateId(templateId);
        evaluationContent.setPublishContent(content);
        this.evaluationService.saveOrUpdateEvaluationContent(evaluationContent);
        
    }
    private String save() throws Exception {
        Integer evaluationPlanId = params.safeGetIntParam("evaluationPlanId");
        String teachDate = params.safeGetStringParam("teachDate");
        String startDate = params.safeGetStringParam("startDate");
        String endDate = params.safeGetStringParam("endDate");
        String title = params.safeGetStringParam("titleName");
        String teacherName = params.safeGetStringParam("teacherName");
        Integer teacherId = params.getIntParamZeroAsNull("teacherId");
        Integer metaGradeId = params.getIntParamZeroAsNull("gradeId");
        Integer metaSubjectId = params.getIntParamZeroAsNull("subjectId");
        String resIds = params.safeGetStringParam("resId");
        String videoIds = params.safeGetStringParam("videoId");
        String templateIds = params.safeGetStringParam("template");
        String fdTeacher = params.safeGetStringParam("fdTeacher");
        boolean isEdit = false;
        if (evaluationPlanId > 0) {
            isEdit = true;
        }
        if (evaluationPlanId == 0) {
            if ("".equals(title) || teacherId == null) {
                addActionError("请输入评课名称和授课人。");
                return ERROR;
            }
        } else {
            if ("".equals(title)) {
                addActionError("请输入评课名称。");
                return ERROR;
            }
        }

        Date td;
        try {
            td = new SimpleDateFormat("yyyy-MM-dd").parse(teachDate);
        } catch (ParseException e2) {
            addActionError("输入的授课日期格式不正确，应当是: '年年年年-月月-日日' 格式");
            return ERROR;
        }
        Date sd;
        try {
            sd = new SimpleDateFormat("yyyy-MM-dd").parse(startDate);
        } catch (ParseException e1) {
            addActionError("输入的开始日期格式不正确，应当是: '年年年年-月月-日日' 格式");
            return ERROR;
        }
        Date ed;
        try {
            ed = new SimpleDateFormat("yyyy-MM-dd").parse(endDate);
        } catch (ParseException e) {
            addActionError("输入的结束日期格式不正确，应当是: '年年年年-月月-日日' 格式");
            return ERROR;
        }
        // #保存评课
        EvaluationPlan evaluationPlan;
        if (evaluationPlanId > 0) {
            evaluationPlan = evaluationService.getEvaluationPlanById(evaluationPlanId);
        } else {
            evaluationPlan = new EvaluationPlan();
        }
        evaluationPlan.setFdTeacher(fdTeacher);
        evaluationPlan.setEvaluationCaption(title);
        evaluationPlan.setMetaSubjectId(metaSubjectId);
        evaluationPlan.setMetaGradeId(metaGradeId);
        evaluationPlan.setStartDate(sd);
        evaluationPlan.setEndDate(ed);
        evaluationPlan.setEnabled(true);
        if (evaluationPlanId == 0) {
            evaluationPlan.setTeacherId(teacherId);
            evaluationPlan.setTeacherName(teacherName);
        }
        evaluationPlan.setTeachDate(td);
        evaluationPlan.setCreaterId(getLoginUser().getUserId());
        evaluationPlan.setCreaterName(getLoginUser().getTrueName());

        evaluationPlanId = this.evaluationService.saveOrUpdateEvaluationPlanEx(evaluationPlan);

        // #保存模板
        if (isEdit) {
            evaluationService.removeEvaluationPlanTemplates(evaluationPlanId);
        }
        if (templateIds != null) {
            String[] artemplateIds = templateIds.split(",");
            for (int i = 0; i < artemplateIds.length; i++) {
                String templateId = artemplateIds[i];
                this.evaluationService.insertEvaluationPlanTemplates(evaluationPlanId, Integer.parseInt(templateId));
            }
        }
        // #保存视频
        if (isEdit) {
            evaluationService.removeVideosFromEvaluation(evaluationPlanId);
        }
        if (videoIds != null && videoIds.length() > 0) {
            String[] arrarvideoId = videoIds.split(",");
            for (int i = 0; i < arrarvideoId.length; i++) {
                String videoId = arrarvideoId[i];
                Video video = this.video_svc.findById(Integer.parseInt(videoId));
                this.evaluationService.insertVideoToEvaluation(evaluationPlanId, Integer.parseInt(videoId), video.getTitle(),
                        video.getFlvThumbNailHref());
            }
        }
        // #保存资源
        if (isEdit) {
            evaluationService.removeResourcesFromEvaluation(evaluationPlanId);
        }
        if (resIds != null && resIds.length() > 0) {
            String[] arrarResId = resIds.split(",");
            for (int i = 0; i < arrarResId.length; i++) {
                String resId = arrarResId[i];
                Resource resource = this.res_svc.getResource(Integer.parseInt(resId));
                if (resource != null) {
                    this.evaluationService.insertResourceToEvaluation(evaluationPlanId, Integer.parseInt(resId), resource.getTitle(),
                            resource.getHref());
                } else {
                    addActionError("没有找到资源 id= " + resId + "  resIds=" + resIds + "  videoIds=" + videoIds);
                    return ERROR;
                }
            }
        }
        // #成功返回
        response.sendRedirect("evaluations.action");
        return NONE;

    }
    /**
     * /jitar2/WebContent/evaluation_edit.py
     * 
     * @return
     */
    private String edit() {
        if (getLoginUser() == null) {
            addActionError("请先登录，然后才能操作");
            addActionLink(new ActionLink("登录", "login.jsp", "_top"));
            return ERROR;
        }
        // #要编辑的ID
        Integer evaluationPlanId = params.getIntParam("evaluationPlanId");
        if (evaluationPlanId == 0) {
            addActionError("缺少评课Id。");
            return ERROR;
        }
        EvaluationPlan evaluationPlan = evaluationService.getEvaluationPlanById(evaluationPlanId);
        if (evaluationPlan == null) {
            addActionError("没有找到该评课。");
            return ERROR;
        }
        request.setAttribute("evaluationPlan", evaluationPlan);

        List<EvaluationPlanTemplate> plantemplate_list = evaluationService.getEvaluationPlanTemplates(evaluationPlanId);
        request.setAttribute("plantemplate_list", plantemplate_list);

        List<Video> video_list = evaluationService.getVideos(evaluationPlanId);
        List<Resource> resource_list = evaluationService.getResources(evaluationPlanId);
        request.setAttribute("video_list", video_list);
        request.setAttribute("resource_list", resource_list);

        // # 学段分类.
        get_grade_list();
        // # 学科分类
        get_subject_list();
        List<EvaluationTemplate> template_list = evaluationService.getTemplates();
        if (template_list.size() < 1) {
            addActionError("当前没有模板可供选择，无法进行评课。");
            return ERROR;
        } else {
            request.setAttribute("template_list", template_list);
            return "edit";
        }
    }

    private String add() {
    	 String type = params.getStringParam("type");
         if (type == null || type.length() == 0) {
             type = "doing";
         }
         String k = params.getStringParam("k");
         String kperson = params.getStringParam("kperson");
         Integer subjectId = params.getIntParamZeroAsNull("subjectId");
         Integer gradeId = params.getIntParamZeroAsNull("gradeId");
         request.setAttribute("gradeId", gradeId);
         request.setAttribute("type", type);
         request.setAttribute("subjectId", subjectId);
         request.setAttribute("k", k);
         request.setAttribute("kperson", kperson);
         request.setAttribute("outHtml", super.getGradeSubjectTreeHtml(jitarContext.getCacheProvider().getCache("page")));
         String act = params.safeGetStringParam("act");
         if (act.equals("")) {
             act = "list";
         }
         if (act.equals("delete")) {
             delete();
         }
         Pager pager = params.createPager();
         pager.setItemNameAndUnit("评课", "个");
         pager.setPageSize(15);

         EvaluationPlanQuery qry = new EvaluationPlanQuery(
                 "ev.evaluationPlanId, ev.evaluationCaption,ev.metaSubjectId, ev.metaGradeId, ev.startDate, ev.endDate, ev.teacherId, ev.teacherName, ev.teachDate, ev.createrId,ev.enabled,subj.msubjName, grad.gradeName");
         if (type.equals("doing")) {
             qry.userId = 0;
             qry.listType = 1;
         } else if (type.equals("finished")) {
             qry.userId = 0;
             qry.listType = 0;
         } else if (type.equals("mine")) {
             if (getLoginUser() == null) {
                 // String errDesc = "请先<a href='login.jsp'>登录</a>，然后才能操作";
                 // response.getWriter().write(errDesc);
                 // return NONE;
                 addActionError("请先登录，然后才能操作");
                 addActionLink(new ActionLink("登录", "login.jsp", "_top"));
                 return ERROR;

             }
             qry.userId = getLoginUser().getUserId();
             qry.listType = 2;
         } else if (type.equals("done")) {
             if (getLoginUser() == null) {
                 // String errDesc = "请先<a href='login.jsp'>登录</a>，然后才能操作";
                 // response.getWriter().write(errDesc);
                 // return NONE;
                 addActionError("请先登录，然后才能操作");
                 addActionLink(new ActionLink("登录", "login.jsp", "_top"));
                 return ERROR;

             }
             qry.userId = getLoginUser().getUserId();
             qry.listType = 3;
         } else {
             qry.listType = 1;
         }
         // #查询条件
         qry.enabled = true;
         qry.title = k;
         qry.subjectId = subjectId;
         qry.teacherName = kperson;
         qry.gradeId = gradeId;

         pager.setTotalRows(qry.count());
         List plan_list = (List) qry.query_map(pager);
         request.setAttribute("pager", pager);
         request.setAttribute("plan_list", plan_list);
         
         // # 学段分类.
         get_grade_list();
         // # 学科分类
         get_subject_list();

         setRequestAttribute("head_nav", "evaluations");
    	
    	
        if (getLoginUser() == null) {
            addActionError("请先登录，然后才能操作");
            addActionLink(new ActionLink("登录", "login.jsp", "_top"));
            request.setAttribute("loginError", "请先登录.");
            return "show";
        }
        request.setAttribute("gradeId", params.getIntParamZeroAsNull("gradeId"));
        // # 学段分类.
        get_grade_list();
        // # 学科分类
        get_subject_list();

        List<EvaluationTemplate> template_list = this.evaluationService.getTemplates();
        if (template_list.size() < 1) {
            addActionError("当前没有模板可供选择，无法进行评课。");
            request.setAttribute("templateError", "当前没有模板可供选择，无法进行评课。");
            return "show";
        } else {
            request.setAttribute("template_list", template_list);
            return "add";
        }
        
    }

    private String show() {
        String type = params.getStringParam("type");
        if (type == null || type.length() == 0) {
            type = "doing";
        }
        String k = params.getStringParam("k");
        String kperson = params.getStringParam("kperson");
        Integer subjectId = params.getIntParamZeroAsNull("subjectId");
        Integer gradeId = params.getIntParamZeroAsNull("gradeId");
        request.setAttribute("gradeId", gradeId);
        request.setAttribute("type", type);
        request.setAttribute("subjectId", subjectId);
        request.setAttribute("k", k);
        request.setAttribute("kperson", kperson);
        
        /*
        List<MetaSubject> subject_list = subjectService.getMetaSubjectList();
        String outHtml = "";
        for (MetaSubject s : subject_list) {
            int msid = s.getMsubjId();
            outHtml = outHtml + "d.add(" + msid + ",0,'" + s.getMsubjName() + "','evaluations.action?subjectId=" + msid + "','','_middle');";
            List<Grade> gradeIdList = subjectService.getMetaGradeListByMetaSubjectId(msid);
            if (gradeIdList != null) {
                for (Grade gid : gradeIdList) {
                    outHtml = outHtml + "d.add(" + msid + gid.getGradeId() + "," + msid + ",'" + gid.getGradeName()
                            + "','evaluations.action?subjectId=" + msid + "&gradeId=" + gid.getGradeId() + "&target=child','','_self');";
                    List<Grade> gradeLevelList = subjectService.getGradeLevelListByGradeId(gid.getGradeId());
                    for (Grade glevel : gradeLevelList) {
                        outHtml = outHtml + "d.add(" + msid + gid.getGradeId() + glevel.getGradeId() + "," + msid + gid.getGradeId() + ",'"
                                + glevel.getGradeName() + "','evaluations.action?subjectId=" + msid + "&gradeId=" + glevel.getGradeId()
                                + "&level=1','','_self');";
                    }
                }
            }
        }*/
        request.setAttribute("outHtml", super.getGradeSubjectTreeHtml(jitarContext.getCacheProvider().getCache("page")));
        String act = params.safeGetStringParam("act");
        if (act.equals("")) {
            act = "list";
        }
        if (act.equals("delete")) {
            delete();
        }
        Pager pager = params.createPager();
        pager.setItemNameAndUnit("评课", "个");
        pager.setPageSize(15);

        EvaluationPlanQuery qry = new EvaluationPlanQuery(
                "ev.evaluationPlanId, ev.evaluationCaption,ev.metaSubjectId, ev.metaGradeId, ev.startDate, ev.endDate, ev.teacherId, ev.teacherName, ev.teachDate, ev.createrId,ev.enabled,subj.msubjName, grad.gradeName");
        if (type.equals("doing")) {
            qry.userId = 0;
            qry.listType = 1;
        } else if (type.equals("finished")) {
            qry.userId = 0;
            qry.listType = 0;
        } else if (type.equals("mine")) {
            if (getLoginUser() == null) {
                // String errDesc = "请先<a href='login.jsp'>登录</a>，然后才能操作";
                // response.getWriter().write(errDesc);
                // return NONE;
                addActionError("请先登录，然后才能操作");
                addActionLink(new ActionLink("登录", "login.jsp", "_top"));
                return ERROR;

            }
            qry.userId = getLoginUser().getUserId();
            qry.listType = 2;
        } else if (type.equals("done")) {
            if (getLoginUser() == null) {
                // String errDesc = "请先<a href='login.jsp'>登录</a>，然后才能操作";
                // response.getWriter().write(errDesc);
                // return NONE;
                addActionError("请先登录，然后才能操作");
                addActionLink(new ActionLink("登录", "login.jsp", "_top"));
                return ERROR;

            }
            qry.userId = getLoginUser().getUserId();
            qry.listType = 3;
        } else {
            qry.listType = 1;
        }
        // #查询条件
        qry.enabled = true;
        qry.title = k;
        qry.subjectId = subjectId;
        qry.teacherName = kperson;
        qry.gradeId = gradeId;

        pager.setTotalRows(qry.count());
        List plan_list = (List) qry.query_map(pager);
        request.setAttribute("pager", pager);
        request.setAttribute("plan_list", plan_list);
        
        // # 学段分类.
        get_grade_list();
        // # 学科分类
        get_subject_list();

        setRequestAttribute("head_nav", "evaluations");
        return "show";
    }
    private void delete() {
        List<Integer> actId = params.safeGetIntValues("actId");
        for (Integer g : actId) {
            evaluationService.deleteEvaluationContentByEvaluationPlanId(g);
            evaluationService.deleteEvaluationPlanById(g);
            evaluationService.removeResourcesFromEvaluation(g);
            evaluationService.removeVideosFromEvaluation(g);
            evaluationService.removeEvaluationPlanTemplates(g);
        }
    }

    // #进行中的评课
    private void get_doing_list() {
        EvaluationPlanQuery qry = new EvaluationPlanQuery(
                "ev.evaluationPlanId, ev.evaluationYear, ev.evaluationSemester, ev.evaluationTimes, ev.metaSubjectId, ev.metaGradeId, ev.startDate, ev.endDate, ev.userCount, ev.enabled");
        qry.ValidPlan = true;
        qry.enabled = true;
        List plan_list = qry.query_map(qry.count());
        if (plan_list.size() > 0) {
            request.setAttribute("plan_list", plan_list);
        }
    }

    // #我参与的评课
    private void get_done_list() {
        Pager pager = params.createPager();
        pager.setItemNameAndUnit("评课", "个");
        pager.setPageSize(15);
        EvaluationContentQuery qry = new EvaluationContentQuery(
                "ec.evaluationContentId, ec.title, ec.courseTeacherName, ec.publishUserName, ec.metaSubjectId, ec.metaGradeId, subj.msubjName, grad.gradeName, ec.createDate");
        qry.publishUserId = getLoginUser().getUserId();
        pager.setTotalRows(qry.count());
        List content_list = (List) qry.query_map(pager);
        request.setAttribute("pager", pager);
        request.setAttribute("content_list", content_list);
    }

    private void get_grade_list() {
        setRequestAttribute("grade_list", this.subjectService.getGradeList());
    }

    private void get_subject_list() {
        setRequestAttribute("subject_list", this.subjectService.getMetaSubjectList());
    }
    public SubjectService getSubjectService() {
        return subjectService;
    }
    public void setSubjectService(SubjectService subjectService) {
        this.subjectService = subjectService;
    }
    public EvaluationService getEvaluationService() {
        return evaluationService;
    }
    public void setEvaluationService(EvaluationService evaluationService) {
        this.evaluationService = evaluationService;
    }

    public VideoService getVideoService() {
        return video_svc;
    }

    public void setVideoService(VideoService videoService) {
        this.video_svc = videoService;
    }

    public ResourceService getResourceService() {
        return res_svc;
    }

    public void setResourceService(ResourceService resourceService) {
        this.res_svc = resourceService;
    }

	public void setJitarContext(JitarContext jitarContext) {
		this.jitarContext = jitarContext;
	}

}
