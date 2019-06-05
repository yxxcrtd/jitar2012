package cn.edustar.jitar.action;

import cn.edustar.data.Pager;
import cn.edustar.jitar.pojos.Subject;
import cn.edustar.jitar.service.GroupQuery;

/**
 * 协作组
 * 
 * @author renliang
 */
public class GroupListAction extends AbstractBasePageAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6168373478740439785L;
	private BaseSubject baseSubject = null;
	private String templateName = null;
	private Subject subject = null;

	@Override
	protected String execute(String cmd) throws Exception {
		baseSubject = new BaseSubject();
		subject = baseSubject.getSubject();
		if (subject == null) {
			addActionError("无法加载指定的学科。");
			return ERROR;
		}

		templateName = "template1";
		if (subject.getTemplateName() != null) {
			templateName = subject.getTemplateName();
		}
		String type = params.safeGetStringParam("type");
		GroupQuery qry = new GroupQuery(
				"subj.subjectId, g.createUserId, g.groupId, g.groupIcon, g.groupTitle, g.userCount,g.createDate, g.visitCount, g.articleCount, g.topicCount, g.resourceCount,g.actionCount,u.loginName, u.nickName");
		qry.setSubjectId(get_current_subjectId());
		qry.setGradeId(get_current_gradeId());
		String Page_Title = params.safeGetStringParam("title");
		request.setAttribute("Page_Title", Page_Title);
		if ("new".equals(type.trim())) {
			if (Page_Title.trim().equals("")) {
				request.setAttribute("Page_Title", "最新协作组");
			}
		} else if ("hot".equals(type.trim())) {
			qry.setOrderType(8);
			if (Page_Title.trim().equals("")) {
				request.setAttribute("Page_Title", "热门协作组");
			}
		} else if ("rcmd".equals(type.trim())) {
			qry.setIsRecommend(true);
			if (Page_Title.trim().equals("")) {
				request.setAttribute("Page_Title", "推荐协作组");
			}
		} else {
			if (Page_Title.trim().equals("")) {
				request.setAttribute("Page_Title", "协作组");
			}
		}
		Pager pager = params.createPager();
		pager.setPageSize(10);
		pager.setItemName("工作室");
		pager.setItemUnit("个");
		pager.setTotalRows(qry.count());
		request.setAttribute("group_list", qry.query_map(pager));
		request.setAttribute("pager", pager);
		request.setAttribute("subject", subject);

		return templateName;
	}

	private Integer get_current_gradeId() {
		Integer gradeId = subject.getMetaGrade().getGradeId();
		request.setAttribute("gradeId", gradeId);
		return gradeId;
	}

	private Integer get_current_subjectId() {
		subjectId = subject.getMetaSubject().getMsubjId();
		request.setAttribute("subjectId", subjectId);
		return subjectId;
	}

}
