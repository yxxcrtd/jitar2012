package cn.edustar.jitar.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import cn.edustar.data.Pager;
import cn.edustar.jitar.JitarContext;
import cn.edustar.jitar.pojos.Grade;
import cn.edustar.jitar.pojos.Subject;
import cn.edustar.jitar.service.CacheService;
import cn.edustar.jitar.service.PrepareCourseQuery;
import cn.edustar.jitar.service.PrepareCourseService;
import cn.edustar.jitar.util.CommonUtil;

/**
 * 学科集体备课
 * 
 * @author renliang
 */
@SuppressWarnings("serial")
public class SubjectPrepareCourseAction extends AbstractBasePageAction {

	private BaseSubject baseSubject = null;
	private PrepareCourseService prepareCourseService = null;
	private Subject subject = null;
	private CacheService cache = null;
	private String stage = null;
	private Integer levelGradeId = null;

	@Override
	protected String execute(String cmd) throws Exception {
		baseSubject = new BaseSubject();
		subject = baseSubject.getSubject();
		cache = JitarContext.getCurrentJitarContext().getCacheProvider()
				.getCache("page");
		if (subject == null) {
			addActionError("无法加载指定的学科。");
			return "error";
		}
		stage = params.safeGetStringParam("showtype");
		if (stage.trim().equals("")) {
			stage = "running";
		}
		String strUnitId = "";
		if (unitId != null && unitId != 0) {
			strUnitId = "&unitId=" + unitId;
		}
		if (request.getAttribute("SubjectRootUrl") == null) {
			String configSubjectSiteRoot = request.getSession()
					.getServletContext().getInitParameter("subjectUrlPattern");
			String subjectRootUrl = null;
			if (configSubjectSiteRoot == null || configSubjectSiteRoot == "") {
				subjectRootUrl = getCurrentSiteUrl(request)+ "k/"
						+ subject.getSubjectCode() + "/";
			} else {
				subjectRootUrl = configSubjectSiteRoot.replaceAll(
						"\\{subjectCode\\}", subject.getSubjectCode());
			}
			request.setAttribute("SubjectRootUrl", subjectRootUrl);
		}
		String templateName = "template1";
		if (subject.getTemplateName() != null) {
			templateName = subject.getTemplateName();
		}
		levelGradeId = params.getIntParamZeroAsNull("levelGradeId");

		Grade grade = subject.getMetaGrade();

		Integer msid = subject.getMetaSubject().getMsubjId();
		String cache_key1 = "outHtmlSubject" + msid + levelGradeId;
		String outHtml = (String) cache.get(cache_key1);
		// # 考虑到添加了单位信息，不再进行缓存
		if (outHtml == null || outHtml == "") {
			outHtml = "";
			outHtml = outHtml + "d.add(" + msid + ",0,'" + grade.getGradeName()
					+ subject.getMetaSubject().getMsubjName()
					+ "','subjectPrepareCourse.action?subjectId=" + msid
					+ "&gradeId=" + grade.getGradeId() + strUnitId + "');";
			String cache_key = "gradeLevelList" + grade.getGradeId();
			Object obj = cache.get(cache_key);
			@SuppressWarnings("unchecked")
			List<Grade> gradeLevelList = null != obj ? (List<Grade>) cache
					.get(cache_key) : null;
			if (gradeLevelList == null) {
				gradeLevelList = subjectService
						.getGradeLevelListByGradeId(grade.getGradeId());
				cache.put(cache_key, gradeLevelList);
			}
			for (Grade glevel : gradeLevelList) {
				outHtml = outHtml + "d.add(" + msid + glevel.getGradeId() + ","
						+ msid + ",'" + glevel.getGradeName()
						+ "','subjectPrepareCourse.action?subjectId=" + msid + "&gradeId="
						+ grade.getGradeId() + "&levelGradeId="
						+ glevel.getGradeId() + strUnitId + "');";
				cache.put(cache_key1, outHtml);
			}
		}

		request.setAttribute("outHtml", outHtml);

		// # 查询列表
		get_course_list();

		request.setAttribute("type", params.getStringParam("type"));
		request.setAttribute("levelGradeId", levelGradeId);
		request.setAttribute("grade", grade);
		request.setAttribute("subject", subject);
		request.setAttribute("head_nav", "preparecourse");
		return templateName;
	}

	private void get_course_list() {
		Pager pager = params.createPager();
		pager.setItemName("备课");
		pager.setItemUnit("个");
		pager.setPageSize(30);
		PrepareCourseQuery qry = new PrepareCourseQuery(
				"pc.createUserId,pc.leaderId,pc.memberCount,pc.articleCount,pc.resourceCount,pc.status,pc.actionCount,pc.topicCount,pc.topicReplyCount,pc.viewCount,pc.startDate,pc.endDate,pc.title, pc.prepareCourseId, pc.createDate, pc.metaSubjectId, pc.gradeId,pc.recommendState,u.loginName,u.trueName");
		qry.setStatus(0);
		qry.setStage(stage);
		// #print "get_current_subjectId()=",get_current_subjectId()
		qry.setSubjectId(get_current_subjectId());
		if (levelGradeId == null) {
			// # 得到全学段的内容
			qry.setContainChild(null);
			qry.setGradeId(subject.getMetaGrade().getGradeId());
			// #print "qry.gradeId=",qry.gradeId
		} else {
			qry.setContainChild("True");
			qry.setGradeId(levelGradeId);
			// #print "qry.gradeId=",qry.gradeId
		}
		pager.setTotalRows(qry.count());
		Object obj = qry.query_map(pager);
		@SuppressWarnings("unchecked")
		List<HashMap> course_list = null != obj ? (List<HashMap>) qry
				.query_map(pager) : null;

		Long sPrivateCount = (long) 0;
		Long sEditCount = (long) 0;
		List<Long> privateCountList = new ArrayList<Long>();
		List<Long> editCountList = new ArrayList<Long>();
		if (course_list != null && course_list.size() > 0) {
			for (Map pc : course_list) {
				Integer pcId = (Integer) pc.get("prepareCourseId");
				sPrivateCount = prepareCourseService
						.getPrepareCourseContentCount(pcId);
				sEditCount = prepareCourseService
						.getPrepareCourseEditCount(pcId);
				privateCountList.add(sPrivateCount);
				editCountList.add(sEditCount);
			}

		}

		request.setAttribute("privateCountList", privateCountList);
		request.setAttribute("editCountList", editCountList);
		request.setAttribute("course_list", course_list);
		request.setAttribute("pager", pager);
		request.setAttribute("showtype", stage);
		request.setAttribute("levelGradeId", levelGradeId);

	}

	private Integer get_current_subjectId() {
		Integer subjectId = subject.getMetaSubject().getMsubjId();
		request.setAttribute("subjectId", subjectId);
		return subjectId;
	}

	private String getCurrentSiteUrl(HttpServletRequest request) {
		return CommonUtil.getSiteUrl(request);
	}

	public void setPrepareCourseService(PrepareCourseService prepareCourseService) {
		this.prepareCourseService = prepareCourseService;
	}

}
