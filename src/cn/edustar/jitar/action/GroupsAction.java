package cn.edustar.jitar.action;

import cn.edustar.data.Pager;
import cn.edustar.jitar.data.Command;
import cn.edustar.jitar.service.CategoryService;
import cn.edustar.jitar.service.GroupQuery;
import cn.edustar.jitar.service.SubjectService;
import cn.edustar.jitar.util.ParamUtil;


/**
 * 视频
 * 
 * @author renliang
 */
public class GroupsAction extends AbstractBasePageAction {

    private static final long serialVersionUID = 1753263484640653826L;

    private CategoryService categoryService;

    private ParamUtil params = null;
    String qrytype = null;

    private SubjectService subjectService;

    public void setSubjectService(SubjectService subjectService) {
        this.subjectService = subjectService;
    }

    public void setCategoryService(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    public String execute(String cmd) throws Exception {
        params = new ParamUtil(request);
        qrytype = params.getStringParam("type");
        // # 协作组分类, 学科, 区县.
        get_group_cate();
        get_subject_list();
        // # 学段分类.
        get_grade_list();
        // # 推荐, 热门协作组, (最新不使用).
        get_rcmd_group_list();
        get_hot_group_list();
        // # self.get_new_group_list();
        // 查询协作组.
        query_group_list();
        // # 页面导航高亮为 'groups'
        request.setAttribute("head_nav", "groups");
        query_group_activity_list();
        response.setContentType("text/html; charset=UTF-8");
        return "success";
    }

    private void query_group_activity_list() {
        // # 活跃度算法 (g.userCount + g.articleCount + g.topicCount +
        // g.resourceCount) / 10
        String hql = "select new Map( g.groupId as groupId, g.groupTitle as groupTitle, g.parentId, (g.userCount + g.articleCount + g.topicCount + g.resourceCount) / 10 as totalCount) FROM Group g where g.groupState = 0 ORDER BY (g.userCount + g.articleCount + g.topicCount + g.resourceCount) DESC";
        Command cmd = new Command(hql);
        request.setAttribute("group_activity_list", cmd.open(25));
    }

    private void query_group_list() {
        GroupQuery groupQuery = new GroupQuery(
                "g.groupId, g.groupIcon, g.groupTitle, g.createDate, g.userCount,g.parentId,g.visitCount, g.articleCount, g.topicCount, g.resourceCount, g.groupIntroduce,g.XKXDName,g.XKXDId,g.groupTags, subj.subjectName, grad.gradeName, sc.name as scName");
        groupQuery.subjectId = params.getIntParamZeroAsNull("subjectId");
        groupQuery.categoryId = params.getIntParamZeroAsNull("categoryId");
        groupQuery.gradeId = params.getIntParamZeroAsNull("gradeId");
        groupQuery.k = params.getStringParam("k");
        if (qrytype != null) {
            if (qrytype.trim().equals("best"))
                groupQuery.isBestGroup = true;
            if (qrytype.trim().equals("rcmd"))
                groupQuery.isRecommend = true;
            if (qrytype.trim().equals("hot"))
                groupQuery.orderType = groupQuery.ORDER_BY_VISITCOUNT_DESC;
            if (qrytype.trim().equals("new"))
                groupQuery.orderType = groupQuery.ORDER_BY_ID_DESC;
        }
        Pager pager = params.createPager();
        pager.setItemName("协作组");
        pager.setItemUnit("个");
        pager.setPageSize(10);
        pager.setTotalRows(groupQuery.count());
        request.setAttribute("group_list", groupQuery.query_map(pager));
        // #print "group_list = ", group_list
        request.setAttribute("pager", pager);
        request.setAttribute("subjectId", groupQuery.subjectId);
        request.setAttribute("categoryId", groupQuery.categoryId);
        request.setAttribute("gradeId", groupQuery.gradeId);
        request.setAttribute("k", groupQuery.k);
        request.setAttribute("type", qrytype);
    }

    private void get_hot_group_list() {
        GroupQuery groupQuery = new GroupQuery(
                "g.groupId, g.groupIcon, g.groupTitle, g.createDate, g.groupIntroduce, g.createUserId,g.parentId,g.XKXDName,g.XKXDId,u.loginName, u.nickName");
        // # visitCount DESC
        groupQuery.orderType = groupQuery.ORDER_BY_VISITCOUNT_DESC;
        request.setAttribute("hot_group_list", groupQuery.query_map());
    }

    private void get_rcmd_group_list() {
        GroupQuery groupQuery = new GroupQuery("g.groupId, g.groupIcon, g.groupTitle, g.createDate, g.groupIntroduce,g.parentId,g.XKXDName,g.XKXDId");
        groupQuery.isRecommend = true;
        request.setAttribute("rcmd_group_list", groupQuery.query_map(5));
    }

    private void get_grade_list() {
        params = new ParamUtil(request);
        request.setAttribute("gradeId", params.getIntParamZeroAsNull("gradeId"));
        request.setAttribute("grade_list", subjectService.getGradeList());
    }

    private void get_subject_list() {
        request.setAttribute("subject_list", subjectService.getMetaSubjectList());
    }

    private void get_group_cate() {
        request.setAttribute("group_cate", categoryService.getCategoryTree("group"));
    }
}

