package cn.edustar.jitar.action;

import cn.edustar.data.Pager;
import cn.edustar.data.paging.PagingQuery;
import cn.edustar.jitar.pojos.Subject;
import cn.edustar.jitar.service.PagingService;
import cn.edustar.jitar.util.CommonUtil;

/**
 * 
 * 
 * @author renliang
 */
public class BlogAction extends AbstractBasePageAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6168373478740439785L;
	private BaseSubject baseSubject = null;
	private Subject subject = null;
	private PagingService pagingService = null;

	@Override
	protected String execute(String cmd) throws Exception {
		baseSubject = new BaseSubject();
		subject = baseSubject.getSubject();
		if (subject == null) {
			addActionError("无法加载指定的学科。");
			return "error";
		}
		String templateName = "template1";
		if (subject.getTemplateName() != null) {
			templateName = subject.getTemplateName();
		}
		// # 工作室分类
		// #get_blog_cates()
		// # 名师工作室
		get_famous_user_list();
		// # 学科带头人工作室
		get_expert_user_list();
		// # 工作室访问排行
		// #教研员
		get_subject_comissioner_user_list();

		get_hot_user_list();

		query_blog();

		request.setAttribute("subject", subject);
		request.setAttribute("head_nav", "blog");
		return templateName;
	}

	private void query_blog() {
		Integer minNum = CommonUtil.convertRoundMinNumber(baseSubject
				.get_current_gradeId());
		Integer maxNum = CommonUtil.convertRoundMaxNumber(baseSubject
				.get_current_gradeId());
		String strOrderBy = "UserId DESC";
		String strWhereClause = "(UserStatus=0 ";
		String strWhere1 = strWhereClause + " And GradeId >= " + minNum
				+ " And GradeId<" + maxNum + " And SubjectId="
				+ baseSubject.get_current_subjectId();
		strWhere1 += ")";
		String strWhere2 = "";
		/*String userIds = ""; // TODO 这里的userIds不知哪里来的
		if (userIds == null) {
			strWhere2 = " And UserId In(" + userIds + ")";
		}*/
		if (!strWhere2.trim().equals("")) {
			strWhere2 = strWhereClause + strWhere2 + ")";
		}
		if (strWhere2.trim().equals("")) {
			strWhereClause = strWhere1;
		} else {
			strWhereClause = strWhere1 + " Or " + strWhere2;
		}
		PagingQuery pagingQuery = new PagingQuery();
		pagingQuery.setKeyName("UserId");
		pagingQuery.setFetchFieldsName("*");
		pagingQuery.setSpName("findPagingUser");
		pagingQuery.setTableName("Jitar_User");
		pagingQuery.setWhereClause(strWhereClause);
		pagingQuery.orderByFieldName = strOrderBy;
		Integer totalCount = params.safeGetIntParam("totalCount");
		Pager pager = params.createPager();
		pager.setPageSize(10);
		pager.setItemName("工作室");
		pager.setItemUnit("个");
		pager.setCurrentPage(params.safeGetIntParam("page", 1));
		pager.setPageSize(10);
		pager.setItemNameAndUnit("用户", "个");
		pager.setUrlPattern(params.generateUrlPattern());
		if (totalCount == 0) {
			pager.setTotalRows(pagingService.getRowsCount(pagingQuery));
		} else {
			pager.setTotalRows(totalCount);
		}
		request.setAttribute("blog_list",
				pagingService.getPagingList(pagingQuery, pager));
		request.setAttribute("pager", pager);
	}

	private void get_hot_user_list() {
		request.setAttribute("hot_list", baseSubject.getHotList(10));
	}

	private void get_subject_comissioner_user_list() {
		request.setAttribute("comissioner_list", baseSubject.getSubjectComissioner());
	}

	private void get_expert_user_list() {
		request.setAttribute("expert_list", baseSubject.getExpertList());
	}

	private void get_famous_user_list() {
		request.setAttribute("famous_list", baseSubject.getFamousList());
	}

	public void setPagingService(PagingService pagingService) {
		this.pagingService = pagingService;
	}
	
}
