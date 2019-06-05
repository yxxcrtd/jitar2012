package cn.edustar.jitar.action;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.edustar.jitar.service.CategoryService;
import cn.edustar.jitar.service.SubjectService;
import cn.edustar.jitar.service.TimerCountService;
import cn.edustar.jitar.service.UserQuery;
import cn.edustar.jitar.util.ParamUtil;

//import cn.edustar.jitar.JitarConst;

/**
 * blogs
 * 
 * @author renliang
 */
public class BlogsAction extends AbstractBasePageAction {

    private static final long serialVersionUID = -7643048251598308774L;
    private transient static final Log log = LogFactory.getLog(BlogsAction.class);

    private CategoryService categoryService;

    private TimerCountService timerCountService;

    private SubjectService subjectService;

    public void setSubjectService(SubjectService subjectService) {
        this.subjectService = subjectService;
    }

    private ParamUtil params;

    public void setTimerCountService(TimerCountService timerCountService) {
        this.timerCountService = timerCountService;
    }

    public void setCategoryService(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @Override
    protected String execute(String cmd) throws Exception {
        get_famous_teacher();
        get_expert_list();
        get_comissioner_list();

        // 学段分类.
        get_grade_list();

        // 工作室分类
        putSysCategoryToRequest();

        // 最新工作室.
        get_new_blog_list();

        // 热门工作室.
        get_hot_blog_list();

        // 推荐工作室.
        get_rcmd_list();

        // 得到学科
        get_subject_list();

        // 文章搜索 - 文章分类.
        get_article_cate();

        // 研修之星.
        teacher_star();

        // 工作室活跃度排行(访问排行榜).
        get_blog_visit_charts();

        // 积分排行榜
        get_blog_score_charts();

        // 统计信息.
        get_site_stat();

        // 页面导航高亮为 'blogs'
        request.setAttribute("head_nav", "blogs");
        return "success";
    }

    /**
     * 将'系统分类树：syscate_tree'放到'request'中
     */
    private void putSysCategoryToRequest() {
        Object syscate_tree = categoryService.getCategoryTree(CategoryService.BLOG_CATEGORY_TYPE);
        setRequestAttribute("syscate_tree", syscate_tree);
    }

    private void get_site_stat() {
        request.setAttribute("site_stat", timerCountService.getTimerCountById(1));
    }

    private void get_blog_score_charts() {
        UserQuery userQuery = new UserQuery(" u.userScore, u.loginName, u.trueName,u.blogName, u.articleCount ");

        userQuery.orderType = 6;
        userQuery.userStatus = 0;
        request.setAttribute("blog_score_charts", userQuery.query_map(8));
    }

    private void get_blog_visit_charts() {
        UserQuery userQuery = new UserQuery(" u.visitCount, u.loginName, u.trueName,u.blogName, u.articleCount ");

        userQuery.orderType = 1;
        userQuery.userStatus = 0;
        request.setAttribute("blog_visit_charts", userQuery.query_map(8));

    }

    private void teacher_star() {
        UserQuery userQuery = new UserQuery(" u.userId, u.loginName, u.trueName, u.userIcon, u.blogName, u.blogIntroduce, u.articleCount,u.userTags");

        userQuery.userTypeId = 5;
        userQuery.orderType = 100;
        request.setAttribute("teacher_star", userQuery.query_map(2));
    }

    private void get_article_cate() {
        request.setAttribute("article_categories", categoryService.getCategoryTree("default"));

    }

    private void get_subject_list() {
        request.setAttribute("subject_list", subjectService.getMetaSubjectList());
    }

    private void get_rcmd_list() {
        UserQuery userQuery = new UserQuery(" u.loginName, u.userIcon, u.blogName, u.trueName, u.articleCount ");

        userQuery.userTypeId = 2;
        userQuery.userStatus = 0;
        userQuery.orderType = 100;
        request.setAttribute("rcmd_list", userQuery.query_map(12));
    }

    private void get_hot_blog_list() {
        UserQuery userQuery = new UserQuery(" u.loginName, u.userIcon, u.blogName, u.trueName,u.blogIntroduce, u.articleCount ");

        // # visitCount DESC
        userQuery.orderType = 1;
        userQuery.userStatus = 0;
        request.setAttribute("hot_blog_list", userQuery.query_map(7));
    }

    private void get_new_blog_list() {
        UserQuery userQuery = new UserQuery("u.loginName, u.userIcon, u.blogName, u.trueName, u.blogIntroduce, u.articleCount");

        userQuery.orderType = 0;
        userQuery.userStatus = 0;
        // qry.metaSubjectId=2
        request.setAttribute("new_blog_list", userQuery.query_map(6));
    }

    private void get_grade_list() {
        params = new ParamUtil(request);
        request.setAttribute("gradeId", params.getIntParamZeroAsNull("gradeId"));
        request.setAttribute("grade_list", subjectService.getGradeList());
    }

    private void get_comissioner_list() {
        UserQuery userQuery = new UserQuery(
                "u.loginName, u.trueName, u.userIcon, u.blogName, u.createDate, u.myArticleCount, u.otherArticleCount,u.visitCount, u.resourceCount, u.blogIntroduce, u.articleCount");

        userQuery.userTypeId = 4;
        userQuery.userStatus = 0;
        userQuery.orderType = 100;
        request.setAttribute("comissioner_list", userQuery.query_map(6));
    }

    private void get_expert_list() {
        UserQuery userQuery = new UserQuery(" u.loginName, u.userIcon, u.blogName, u.trueName, u.visitCount, unit.unitName, u.articleCount ");

        userQuery.userTypeId = 3;
        userQuery.userStatus = 0;
        userQuery.orderType = 100;
        request.setAttribute("expert_list", userQuery.query_map(13));
    }

    private void get_famous_teacher() {
        UserQuery userQuery = new UserQuery("u.loginName, u.userIcon, u.blogName, u.trueName, u.visitCount, u.articleCount");

        userQuery.userTypeId = 1;
        userQuery.userStatus = 0;
        userQuery.orderType = 100;
        request.setAttribute("famous_teachers", userQuery.query_map(3));
    }

}
