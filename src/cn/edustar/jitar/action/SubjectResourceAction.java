package cn.edustar.jitar.action;

import java.util.List;
import cn.edustar.data.Pager;
import cn.edustar.jitar.JitarContext;
import cn.edustar.jitar.model.CategoryTreeModel;
import cn.edustar.jitar.pojos.Grade;
import cn.edustar.jitar.pojos.Subject;
import cn.edustar.jitar.service.CacheService;
import cn.edustar.jitar.service.CategoryService;
import cn.edustar.jitar.service.ResourceQuery;
import cn.edustar.jitar.service.UserQuery;

/**
 * 课程资源
 * 
 * @author renliang
 */
@SuppressWarnings("serial")
public class SubjectResourceAction extends AbstractBasePageAction {
	private BaseSubject baseSubject = null;
	private CategoryService categoryService = null;
	private Subject subject = null;
	private CacheService cache = null;
	private Integer levelGradeId = null;
	private Integer unitId = null;
	@Override
	protected String execute(String cmd) throws Exception {
		baseSubject = new BaseSubject();
		cache = JitarContext.getCurrentJitarContext().getCacheProvider()
				.getCache("main");
		subject = baseSubject.getSubject();
		if (subject == null) {
			addActionError("无法加载指定的学科。");
			return "error";
		}
		String templateName = "template1";
		if (subject.getTemplateName() != null) {
			templateName = subject.getTemplateName();
		}

		levelGradeId = params.getIntParam("levelGradeId");

		if (levelGradeId == null || levelGradeId == 0) {
			levelGradeId = subject.getMetaGrade().getGradeId();
		}
		unitId = baseSubject.getUnitId();
		if (unitId == null) {
			unitId = 0;
		}
		Grade grade = subject.getMetaGrade();
		Integer msid = subject.getMetaSubject().getMsubjId();
		String type = params.getStringParam("type");
		if (type == null || type.trim().equals("")) {
			type = "new";
		}
		String cache_key1 = type + "_outHtmlSubject" + msid + levelGradeId;
		String outHtml = (String) cache.get(cache_key1);
		// #outHtml = None
		if (outHtml == null || outHtml.trim().equals("")) {
			outHtml = "";
			outHtml = outHtml + "d.add(" + msid + ",0,'" + grade.getGradeName()
					+ subject.getMetaSubject().getMsubjId()
					+ "','subjectResource.action?type=" + type + "&subjectId="
					+ msid + "&gradeId=" + grade.getGradeId()
					+ "&target=child&unitId=" + (0 != unitId ? unitId : "")
					+ "');";
			String cache_key = type + "_gradeLevelList" + grade.getGradeId();
			Object obj = cache.get(cache_key);
			@SuppressWarnings("unchecked")
			List<Grade> gradeLevelList = null != obj ? (List<Grade>) obj : null;
			// gradeLevelList = None
			if (gradeLevelList == null) {
				gradeLevelList = subjectService
						.getGradeLevelListByGradeId(grade.getGradeId());
				// #cache.put(cache_key, gradeLevelList)
				for (Grade glevel : gradeLevelList) {
					outHtml = outHtml + "d.add(" + msid + glevel.getGradeId()
							+ "," + msid + ",'" + glevel.getGradeName()
							+ "','subjectResource.action?type=" + type
							+ "&subjectId=" + msid + "&gradeId="
							+ grade.getGradeId() + "&level=1&levelGradeId="
							+ glevel.getGradeId() + "&unitId=" + unitId + "');";
					// #cache.put(cache_key1, outHtml)
				}

			}
		}

		request.setAttribute("outHtml", outHtml);

		// # 资源分类
		get_res_cates();

		// # 资源上载排行
		get_upload_sorter();

		// # 资源下载排行
		get_download_resource_list();

		request.setAttribute("subject", subject);
		request.setAttribute("head_nav", "resource");
		request.setAttribute("grade", grade);
		request.setAttribute("unitId", unitId);
		return templateName;
	}

	// 资源下载排行
	private void get_download_resource_list() {
		ResourceQuery qry = new ResourceQuery(
				"r.resourceId, r.href, r.title, r.downloadCount");
		qry.setSubjectId(subject.getMetaSubject().getMsubjId());
		qry.setGradeId(subject.getMetaGrade().getGradeId());
		qry.setFuzzyMatch(true);
		qry.setOrderType(4);
		request.setAttribute("download_resource_list", qry.query_map(20));

	}

	private void get_upload_sorter() {
		UserQuery qry = new UserQuery(
				"u.resourceCount, u.loginName, u.nickName");
		qry.setMetaSubjectId(subject.getMetaSubject().getMsubjId());
		qry.setMetaGradeId(subject.getMetaGrade().getGradeId());
		qry.setFuzzyMatch(true);
		qry.setOrderType(4); // #UserQuery.ORDER_TYPE_RESOURCE_COUNT_DESC
		qry.setUserStatus(0);
		request.setAttribute("upload_sorter", qry.query_map(20));

	}

	// 资源分类
	private void get_res_cates() {
		CategoryTreeModel res_cates = categoryService
				.getCategoryTree("resource"); // 这里获得资源分类树
		request.setAttribute("res_cates", res_cates);
		// # 资源主列表.
		query_resource();
	}

	// 查询资源主列表
	private void query_resource() {
		ResourceQuery qry = new ResourceQuery(
				"r.resourceId, r.title, r.href, r.fsize, r.createDate,u.loginName, u.nickName, grad.gradeName, sc.name as scName");
		Pager pager = params.createPager();
		if (unitId != null && unitId != 0) {
			qry.custormAndWhereClause = " r.approvedPathInfo LIKE '%/" + unitId
					+ "/%'";
		}
		// # 根据页面参数处理.
		String type = params.getStringParam("type");
		if (type == "rcmd") {
			qry.setRcmdState(true);
			if (unitId != null && unitId != 0) {
				// # 覆盖掉前面的设置
				qry.custormAndWhereClause = " r.rcmdPathInfo LIKE '%/" + unitId
						+ "/%'";
			}

		} else if (type.trim().equals("hot")) {
			qry.orderType = ResourceQuery.ORDER_TYPE_DOWNLOADCOUNT_DESC;
		} else if (type.trim().equals("cmt")) {
			qry.orderType = ResourceQuery.ORDER_TYPE_COMMENTCOUNT_DESC;
		} else {
			type = "new";
		}
		request.setAttribute("type", type);
		qry.subjectId = get_current_subjectId();

		// #qry.gradeId = get_current_gradeId();
		qry.setGradeId(levelGradeId);
		qry.gradelevel = params.getIntParamZeroAsNull("level");
		qry.setFuzzyMatch(true);

		qry.setK(params.getStringParam("k"));
		qry.sysCateId = params.getIntParamZeroAsNull("categoryId");

		// # 查询数据.
		pager.setTotalRows(qry.count());

		request.setAttribute("resource_list", qry.query_map(pager));
		request.setAttribute("pager", pager);
	}

	private Integer get_current_subjectId() {
		Integer subjectId = subject.getMetaSubject().getMsubjId();
		request.setAttribute("subjectId", subjectId);
		return subjectId;
	}

	public void setCategoryService(CategoryService categoryService) {
		this.categoryService = categoryService;
	}
	
}
