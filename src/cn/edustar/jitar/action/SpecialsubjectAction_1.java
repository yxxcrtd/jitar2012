package cn.edustar.jitar.action;

import java.util.List;

import cn.edustar.jitar.pojos.SpecialSubject;
import cn.edustar.jitar.pojos.Subject;
import cn.edustar.jitar.service.PhotoQuery;
import cn.edustar.jitar.service.SpecialSubjectArticleQuery;
import cn.edustar.jitar.service.SpecialSubjectQuery;
import cn.edustar.jitar.service.SpecialSubjectService;
import cn.edustar.jitar.service.VideoQuery;

//import cn.edustar.jitar.JitarConst;

/**
 * 课程专题
 * 
 * @author renliang
 */
public class SpecialsubjectAction_1 extends AbstractBasePageAction {
	private static final long serialVersionUID = 281306702300166684L;
	/**
	 * 
	 */
    public SpecialSubjectService specialSubjectService;
	public Integer specialSubjectId = null;
	private SpecialSubject specialSubject = null;
	private BaseSubject baseSubject = null;
	private Subject subject = null;
	private String templateName = null;

	@Override
	protected String execute(String cmd) throws Exception {
		baseSubject = new BaseSubject();
		subject = baseSubject.getSubject();
		if (subject == null) {
			addActionError("无法加载指定的学科。");
			return "ERROR";
		}
		templateName = "template1";
		if (subject.getTemplateName() != null) {
			templateName = subject.getTemplateName();
		}
		Integer specialSubjectId = params.safeGetIntParam("specialSubjectId");
		if (specialSubjectId == 0) {
			specialSubject = specialSubjectService
					.getNewestSpecialSubjectByTypeId(subject.getSubjectId());
			if (specialSubject != null) {
				specialSubjectId = specialSubject.getSpecialSubjectId();
			}

		}
		if (specialSubjectId > 0) {
			if (specialSubject == null) {
				specialSubject = specialSubjectService
						.getSpecialSubject(specialSubjectId);
			}
			if (specialSubject == null) {
				addActionError("无法加载指定的专题。");
				return "ERROR";
			}
			return show_specialSubject();
		} else {
			request.setAttribute("subject", subject);
			request.setAttribute("head_nav", "specialsubject");
			return "error_" + templateName;
		}
	}

	private String show_specialSubject() {
		PhotoQuery qry = new PhotoQuery(
				"p.photoId, p.title, p.userId,p.href, p.userTrueName,u.loginName");
		
		
		qry.k = params.safeGetStringParam("k", null);
		qry.f = params.safeGetStringParam("f", "0");
		qry.sysCateId = params.getIntParamZeroAsNull("categoryId");
        request.setAttribute("f", qry.f);
        request.setAttribute("k", qry.k);
	        
		
		qry.setSpecialSubjectId(specialSubject.getSpecialSubjectId());
		qry.setExtName(".jpg");
		List ssp_list = qry.query_map(6);

		SpecialSubjectArticleQuery qry1 = new SpecialSubjectArticleQuery(
				"ssa.articleId, ssa.title, ssa.userId, ssa.userTrueName, ssa.createDate, ssa.loginName, ssa.typeState");
		qry1.setRequest(request);
		qry1.setSpecialSubjectId(specialSubject.getSpecialSubjectId());
		List ssa_list = qry1.query_map(10);

		SpecialSubjectQuery qry2 = new SpecialSubjectQuery(
				"ss.specialSubjectId, ss.objectGuid, ss.title,ss.logo,ss.description,ss.createDate,ss.expiresDate");
		qry2.setObjectId(subject.getSubjectId());
		qry2.setObjectType("subject");
		List ss_list = qry2.query_map(10);
		if (ssp_list != null && ssp_list.size() > 0) {
			request.setAttribute("specialSubjectPhotoList", ssp_list);
		}
		request.setAttribute("specialSubjectArticleList", ssa_list);
		request.setAttribute("specialSubjectList", ss_list);
		request.setAttribute("specialSubject", specialSubject);

		request.setAttribute("subject", subject);
		request.setAttribute("head_nav", "specialsubject");

		if (unitId != null && unitId != 0) {
			request.setAttribute("unitId", unitId);
		}
		video_list();
		return templateName;
	}

	private void video_list() {
		VideoQuery qry = new VideoQuery(
				" v.videoId, v.flvHref, v.flvThumbNailHref, v.title,v.userId,v.createDate");
		qry.setSpecialSubjectId(specialSubject.getSpecialSubjectId());
		List v_list = qry.query_map(5);
		if (v_list.size() > 0) {
			request.setAttribute("video_list", v_list);
		}
	}

	public void setSpecialSubjectService(
			SpecialSubjectService specialSubjectService) {
		this.specialSubjectService = specialSubjectService;
	}
}
