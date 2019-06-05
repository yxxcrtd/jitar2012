package cn.edustar.jitar.action;

import java.util.List;
import cn.edustar.data.Pager;
import cn.edustar.data.paging.PagingQuery;
import cn.edustar.jitar.pojos.BackYear;
import cn.edustar.jitar.pojos.Subject;
import cn.edustar.jitar.service.CategoryService;
import cn.edustar.jitar.service.PagingService;
import cn.edustar.jitar.service.WebSiteManageService;
import cn.edustar.jitar.util.CommonUtil;

/**
 * 课程文章
 * 查询出文章分类树
 * 阅读数,评论对文章进行排序,根据年代进行查询
 * @author renliang
 */
public class SubjectArticleAction extends AbstractBasePageAction {

	private static final long serialVersionUID = 6168373478740439785L;
	private CategoryService categoryService = null;
	private PagingService pagingService = null;
	private BaseSubject baseSubject = null;
	private WebSiteManageService webSiteManageService = null;
	private Subject subject = null;
	private List<BackYear> backYearList = null;

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
		get_backyear_list();
		get_blog_cates();
		get_article_list();
		if (unitId != null && unitId != 0) {
			request.setAttribute("unitId", unitId);
		}
		request.setAttribute("subject", subject);
		request.setAttribute("head_nav", "article");
		return templateName;
	}

	// # 文章主查询列表, 带分页.
	private void get_article_list() {
		Integer year = params.getIntParamZeroAsNull("year");
		if (backYearList == null) {
			year = null;
		}
		if (backYearList != null) {
			boolean IsValidYear = false;
			for (BackYear b : backYearList) {
				if (null!=year && year == b.getBackYear()) {
					IsValidYear = true;
					break;
				}
			}
			if (!IsValidYear) {
				year = null;
			}
		}

		String strWhereClause = "";
		Integer subjectId = subject.getMetaSubject().getMsubjId();
		request.setAttribute("subjectId", subjectId);

		if (subjectId != null) {
			strWhereClause = strWhereClause + "SubjectId =" + subjectId
					+ " And ";
		}

		Integer gradeId = subject.getMetaGrade().getGradeId();
		request.setAttribute("gradeId", gradeId);

		if (gradeId != null) {
			Integer minId = CommonUtil.convertRoundMinNumber(gradeId);
			Integer maxId = CommonUtil.convertRoundMaxNumber(gradeId);
			// #print "minId = %s ,maxId = %s" % (minId,maxId)
			strWhereClause = strWhereClause + " GradeId >=" + minId
					+ " And  GradeId < " + maxId + " And ";
		}

		if (unitId != null && unitId != 0) {
			strWhereClause = strWhereClause + "  ApprovedPathInfo LIKE '%/"
					+ unitId + "/%' And ";
		}

		String strOrderBy = "ArticleId DESC";

		String type = params.getStringParam("type");

		if (type.trim().equals("rcmd")) {
			strWhereClause = strWhereClause
					+ " RcmdPathInfo IS NOT NULL And ";
		} else if (type.trim().equals("hot")) {
			strOrderBy = "ViewCount DESC";
		}

		else if (type.trim().equals("cmt")) {
			strOrderBy = "CommentCount DESC";
		}

		else {
			type = "new";
		}

		request.setAttribute("type", type);

		String k = params.getStringParam("k");
		Integer sysCateId = params.getIntParamZeroAsNull("categoryId");

		if (k != null && k != "") {
			strWhereClause = strWhereClause + " Title Like '%" + k + "%' And ";
		}
		if (sysCateId != null) {
			// #只查询分类自己的
			// #strWhereClause = strWhereClause + " SysCateId = " +
			// str(sysCateId) + " And "
			// #查询包含子孙分类的
			List<Integer> list = categoryService.getCategoryIds(sysCateId);
			String cateIds = "";
			for (Integer d : list) {
				if (cateIds == "") {
					cateIds = cateIds + d;
				} else {
					cateIds = cateIds + "," + d;
				}
			}
			strWhereClause = strWhereClause + " SysCateId IN (" + cateIds + ")"
					+ " And ";
		}

		if (strWhereClause.trim()
				.substring(strWhereClause.trim().length() - 4, strWhereClause.trim().length())
				.trim().equals("And")) {
			strWhereClause = strWhereClause.substring(0,
					strWhereClause.length() - 4);
		}
		PagingQuery pagingQuery = new PagingQuery();
		pagingQuery.setKeyName("ArticleId");
		pagingQuery.setFetchFieldsName("*");
		pagingQuery.setOrderByFieldName(strOrderBy);
		pagingQuery.setSpName("findPagingArticle");
		if (year == null) {
			pagingQuery.setTableName("Jitar_Article");
		} else {
			pagingQuery.setTableName("HtmlArticle" + year);
		}
		pagingQuery.setWhereClause(strWhereClause);

		Integer totalCount = params.safeGetIntParam("totalCount");
		Pager pager = new Pager();
		pager.setCurrentPage(params.safeGetIntParam("page", 1));
		pager.setPageSize(20);
		pager.setItemNameAndUnit("文章", "篇");
		pager.setUrlPattern(params.generateUrlPattern());
		if (totalCount == 0) {
			pager.setTotalRows(pagingService.getRowsCount(pagingQuery));
		} else {
			pager.setTotalRows(totalCount);
		}
		List article_list = pagingService.getPagingList(pagingQuery, pager);

		request.setAttribute("article_list", article_list);
		request.setAttribute("pager", pager);
		request.setAttribute("k", k);
		request.setAttribute("sysCateId", sysCateId);
		request.setAttribute("year", year);
	}

	// 文章分类
	private void get_blog_cates() {
		request.setAttribute("blog_cates",
				categoryService.getCategoryTree("default"));
	}

	private void get_backyear_list() {
		this.backYearList = webSiteManageService.getBackYearList("article");
		if (backYearList == null || backYearList.size() < 1) {
			return;
		}
		request.setAttribute("backYearList", backYearList);
	}

	
	public void setCategoryService(CategoryService categoryService) {
		this.categoryService = categoryService;
	}

	public void setPagingService(PagingService pagingService) {
		this.pagingService = pagingService;
	}

	public void setWebSiteManageService(WebSiteManageService webSiteManageService) {
		this.webSiteManageService = webSiteManageService;
	}
	
}
