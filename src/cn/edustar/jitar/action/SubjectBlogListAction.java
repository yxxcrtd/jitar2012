package cn.edustar.jitar.action;

import java.util.List;

import cn.edustar.data.Pager;
import cn.edustar.data.paging.PagingQuery;
import cn.edustar.jitar.pojos.Subject;
import cn.edustar.jitar.pojos.UserSubjectGrade;
import cn.edustar.jitar.service.PagingService;
import cn.edustar.jitar.service.UserSubjectGradeQuery;
import cn.edustar.jitar.util.CommonUtil;

//import cn.edustar.jitar.JitarConst;
/**
 * subject blogs
 * 
 * @author renliang
 */
public class SubjectBlogListAction extends AbstractBasePageAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3466729284274546565L;
	public BaseSubject baseSubject = null;
	public Subject subject = null;
	public PagingService pagingService = null;

	@Override
	public String execute(String cmd) {
		baseSubject = new BaseSubject();
		subject = baseSubject.getSubject();
		
		if (subject == null) {
			addActionError("无法加载指定的学科。");
			return "error";
		}
		String templateName = "template1";
		if (subject.getTemplateName() != null) {
			templateName = subject.getSubjectName();
		}
		Integer minNum = CommonUtil
				.convertRoundMinNumber(get_current_gradeId());
		Integer maxNum = CommonUtil
				.convertRoundMaxNumber(get_current_gradeId());
		String strOrderBy = "UserId DESC";
		String strWhereClause = "(UserStatus=0";
		String type = params.safeGetStringParam("type");

		/*
		 * #qry = UserQuery(""" u.loginName, u.articleCount, u.userIcon,
		 * u.blogName, u.nickName, u.trueName, u.blogIntroduce,u.createDate,
		 * u.visitCount, # u.articleCount, u.resourceCount,
		 * u.commentCount,u.userScore """) #qry.userStatus = 0 #qry.FuzzyMatch =
		 * True #qry.metaSubjectId = get_current_subjectId() #qry.metaGradeId =
		 * get_current_gradeId()
		 */

		String Page_Title = params.safeGetStringParam("title");
		if ("comiss".equals(type.trim())) {
			// #qry.isComissioner = True
			strWhereClause += " And UserType LIKE '%/4/%' ";
			if (Page_Title.trim().equals("")) {
				Page_Title = "教研员工作室";
			}

		} else if ("famous".trim().trim().equals(type)) {
			// #qry.isFamous = True
			strWhereClause += " And UserType LIKE '%/1/%'";
			if (Page_Title.trim().equals("")) {
				Page_Title = "名师工作室";
			}

		}

		else if ("expert".equals(type.trim())) {
			// #qry.isFamous = True
			strWhereClause += " And UserType LIKE '%/3/%'";
			if (Page_Title.trim().equals("")) {
				Page_Title = "学科带头人";
			}

		}

		else if ("new".equals(type.trim())) {
			if (Page_Title.trim().equals("")) {
				Page_Title = "最新工作室";
			}

		}

		else if ("hot".equals(type.trim())) {
			strOrderBy = "VisitCount DESC";
			// #qry.orderType = UserQuery.ORDER_TYPE_VISITCOUNT_DESC
			if (Page_Title.trim().equals("")) {
				Page_Title = "热门工作室";
			}

		}

		else if ("rcmd".equals(type.trim())) {
			strWhereClause += " And UserType LIKE '%/2/%'";
			// #qry.isRecommend = True
			if (Page_Title.trim().equals("")) {
				Page_Title = "推荐工作室";
			}

		} else {
			if (Page_Title.trim().equals("")) {
				Page_Title = "工作室";
			}

		}

		String strWhere1 = strWhereClause + " And GradeId >= " + minNum
				+ " And GradeId<" + maxNum + " And SubjectId="
				+ get_current_subjectId();
		strWhere1 += ")";
		String strWhere2 = "";
		// #得到多学科用户
		UserSubjectGradeQuery qry2 = new UserSubjectGradeQuery("usg.userId");
		qry2.setSubjectId(get_current_subjectId());
		qry2.setGradeId(get_current_gradeId());
		qry2.setFuzzyMatch(true); // True #匹配学段，包括年级
		List<UserSubjectGrade> usg_list = qry2.query_map(qry2.count());
		String userIds = "";
		if (usg_list != null && usg_list.size() > 0) {
			for (UserSubjectGrade uu : usg_list) {
				userIds += uu.getUserId() + ",";
			}
		}
		if (!"".equals(userIds.trim()) ) {
		    if(userIds.endsWith(",")){
                userIds = userIds.substring(0,userIds.length() - 1);
            }
			strWhere2 = " And UserId In(" + userIds + ")";
		}

		if (!"".equals(strWhere2.trim())) {
			strWhere2 = strWhereClause + strWhere2 + ")";
		}
		if (strWhere2.trim().equals("")) {
			strWhereClause = strWhere1;
		} else {
			strWhereClause = strWhere1 + " Or " + strWhere2;
		}

		Integer totalCount = params.safeGetIntParam("totalCount");
		PagingQuery pagingQuery = new PagingQuery("Jitar_User", "UserId", "*",
				strOrderBy, strWhereClause, "findPagingUser", totalCount);

		Pager pager = new Pager();
		pager.setCurrentPage(params.safeGetIntParam("page", 1));
		pager.setPageSize(20);
		pager.setItemNameAndUnit("用户", "个");
		pager.setUrlPattern(params.generateUrlPattern());
		if (totalCount == 0) {
			pager.setTotalRows(pagingService.getRowsCount(pagingQuery));
		} else {
			pager.setTotalRows(totalCount);
		}

		List user_list = pagingService.getPagingList(pagingQuery, pager);
		request.setAttribute("Page_Title", Page_Title);
		request.setAttribute("user_list", user_list);
		request.setAttribute("pager", pager);
		request.setAttribute("subject", subject);
		return templateName;
	}

	public void setPagingService(PagingService pagingService) {
		this.pagingService = pagingService;
	}

	private Integer get_current_subjectId() {
		Integer subjectId = subject.getMetaSubject().getMsubjId();
		request.setAttribute("subjectId", subjectId);
		return subjectId;
	}

	private Integer get_current_gradeId() {
		Integer gradeId = subject.getMetaGrade().getGradeId();
		request.setAttribute("gradeId", gradeId);
		return gradeId;
	}
}
