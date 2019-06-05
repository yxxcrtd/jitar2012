package cn.edustar.jitar.action;

import cn.edustar.data.Pager;
import cn.edustar.jitar.data.Command;
import cn.edustar.jitar.model.CategoryTreeModel;
import cn.edustar.jitar.pojos.Subject;
import cn.edustar.jitar.service.CategoryService;
import cn.edustar.jitar.service.GroupQuery;

/**
 * 活动 课程
 * 
 * @author renliang
 */
public class SubjectGroupAction extends AbstractBasePageAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6168373478740439785L;
	private BaseSubject baseSubject = null;
	private CategoryService categoryService = null;
	private Subject subject = null;

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
		// # 协作组分类
		get_group_cates();

		// # 推荐协作组
		get_rcmd_list();

		// # 协作组搜索.未完成
		get_group_list();

		// # 热门协作组
		get_hot_list();

		// # 最新协作组
		get_new_group_list();

		// # 活跃度排行
		query_group_activity_list();

		request.setAttribute("subject", subject);
		request.setAttribute("head_nav", "groups");
		return templateName;
	}
	//活跃协作组排行
	private void query_group_activity_list() {
		 //# 活跃度算法 (g.userCount + g.articleCount + g.topicCount + g.resourceCount) / 10 
	        String hql = "select new Map( g.groupId as groupId, g.groupTitle as groupTitle, (g.userCount + g.articleCount + g.topicCount + g.resourceCount) / 10 as totalCount) FROM Group g where g.groupState = 0 and subjectId = :subjectId and gradeId > :start_gradeId and gradeId < :end_gradeId ORDER BY (g.userCount + g.articleCount + g.topicCount + g.resourceCount) DESC";
	        Command cmd =new Command(hql);
	        //# 整数化学段
	        int round_gradeId = (get_current_gradeId() / 1000) * 1000;
	        int start_gradeId = round_gradeId - 1;
	        int end_gradeId = round_gradeId + 1000;
	        
	        cmd.setInteger("subjectId", get_current_subjectId());
	        cmd.setInteger("start_gradeId", start_gradeId);
	        cmd.setInteger("end_gradeId", end_gradeId);
	        
	        request.setAttribute("group_activity_list", cmd.open(25));

	}

	// 最新协作组
	private void get_new_group_list() {
		GroupQuery qry = new GroupQuery(
				"subj.subjectId, g.createUserId, g.groupId, g.groupIcon, g.groupTitle, g.createDate,u.loginName, u.nickName");
		qry.setOrderType(1);
		qry.subjectId = get_current_subjectId();
		qry.gradeId = get_current_gradeId();
		request.setAttribute("new_group_list", qry.query_map(4));
	}

	// 热门协作组
	private void get_hot_list() {
		GroupQuery qry = new GroupQuery(
				"subj.subjectId, g.createUserId, g.groupIcon, g.groupId, g.groupTitle, g.createDate, g.groupIntroduce,u.loginName, u.nickName");
		qry.setOrderType(2);
		qry.setSubjectId(get_current_subjectId());
		qry.setGradeId(get_current_gradeId());
		request.setAttribute("hot_list", qry.query_map());

	}

	//协作组搜索
	private void get_group_list() {
		GroupQuery qry = new GroupQuery(
				"subj.subjectId, g.createUserId, g.groupId, g.groupIcon, g.groupTitle, g.createDate,u.loginName, u.nickName, g.userCount, g.visitCount, g.articleCount, g.topicCount, g.resourceCount, g.groupTitle , g.groupIntroduce");

		Pager pager = createPager();

		// # 根据页面参数处理.
		String type = params.getStringParam("type");
		if (type.trim().equals("rcmd")) {
			qry.setIsRecommend(true);
		}

		else if (type.trim().equals("hot")) {
			qry.orderType = qry.ORDER_BY_VISITCOUNT_DESC;
		}

		else {
			type = "new";
		}

		request.setAttribute("type", type);

		qry.setSubjectId(get_current_subjectId());
		qry.setGradeId(get_current_gradeId());

		qry.setK(params.getStringParam("k"));
		// #qry.sysCateId = self.params.getIntParamZeroAsNull("categoryId")

		pager.setTotalRows(qry.count());
		request.setAttribute("group_list", qry.query_map(pager));
		request.setAttribute("pager", pager);
	}

	// 推荐协作组
	private void get_rcmd_list() {
		GroupQuery qry = new GroupQuery(
				"subj.subjectId, g.groupId, g.groupIcon,  g.groupTitle, g.createDate, g.groupIntroduce");
		qry.setIsRecommend(true);
		qry.setSubjectId(get_current_subjectId());
		qry.gradeId = get_current_gradeId();
		request.setAttribute("rcmd_list", qry.query_map(4));

	}

	private Integer get_current_subjectId() {
		Integer subjectId = subject.getMetaSubject().getMsubjId();
		request.setAttribute("subjectId", subjectId);
		return subjectId;
	}

	// # 协作组分类.
	private void get_group_cates() {
		CategoryTreeModel group_cates = categoryService
				.getCategoryTree("group");
		request.setAttribute("group_cates", group_cates);
		get_current_gradeId();
	}

	private Integer get_current_gradeId() {
		Integer gradeId = subject.getMetaGrade().getGradeId();
		request.setAttribute("gradeId", gradeId);
		return gradeId;
	}

	private Pager createPager() {
		Pager pager = params.createPager();
		pager.setItemName("协作组");
		pager.setItemUnit("个");
		pager.setPageSize(10);
		return pager;
	}
	
	public void setCategoryService(CategoryService categoryService) {
		this.categoryService = categoryService;
	}
}
