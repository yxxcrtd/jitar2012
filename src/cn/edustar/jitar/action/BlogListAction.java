package cn.edustar.jitar.action;

import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import cn.edustar.data.Pager;
import cn.edustar.jitar.pojos.UserType;
import cn.edustar.jitar.service.UserQuery;
import cn.edustar.jitar.service.UserService;
import cn.edustar.jitar.util.ParamUtil;

//import cn.edustar.jitar.JitarConst;
/**
 * blogs
 * 
 * @author renliang
 */
public class BlogListAction extends AbstractBasePageAction {

    private static final long serialVersionUID = -7643048251598308774L;
    private transient static final Log log = LogFactory.getLog(BlogListAction.class);

    private String type = null;
    private UserService userService;
    private String f = null;
    private String k = null;
    private Integer role = null;
    private Integer sysCateId = null;
    private Integer metaSubjectId = null;
    private Integer metaGradeId = null;

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Override
    protected String execute(String cmd) throws Exception {

        metaSubjectId = params.getIntParamZeroAsNull("metaSubjectId");
        metaGradeId = params.getIntParamZeroAsNull("metaGradeId");
        sysCateId = (null!=request.getParameter("categoryId")&&request.getParameter("categoryId").trim().length()>0&&!"-1".equals(request.getParameter("categoryId")))?Integer.parseInt(request.getParameter("categoryId")):0;
        // # 工作室分类, == null 表示不限制.
        params.getIntParamZeroAsNull("categoryId");
        // # 简单关键字, loginName, nickName, blogName, tags 查询.
        params.getStringParam("k");
        // # 查找类型 name,email,tags,intro,unit
        params.getStringParam("f");

        request.setAttribute("f", f);
        request.setAttribute("k", k);
        role = params.getIntParamZeroAsNull("role");

        request.setAttribute("subjectId", metaSubjectId);
        request.setAttribute("gradeId", metaGradeId);
        request.setAttribute("unitId", unitId);
        request.setAttribute("categoryId", sysCateId);
        request.setAttribute("role", role);
        k = params.getStringParam("k");

        if (!"".equals(k) && k != null) {
            k = k.toLowerCase();
            if (k.length() > 25 || k.indexOf("'") > -1 || k.indexOf(("script")) > -1 || k.indexOf(">") > -1 || k.indexOf("<") > -1
                    || k.indexOf("\"") > -1 || k.indexOf("&gt;") > -1) {
                k = "";
                response.getWriter().write("请输入合法的文字，并且长度不大于25.");
            }

        }
        request.setAttribute("subject_list", subjectService.getMetaSubjectList());

        type = params.getStringParam("type");
        if (ParamUtil.isInteger(type)) {
            get_typeduser_list(Integer.parseInt(type));
        } else if (type.trim().equals("score")) {
            get_score_list();
        } else if (type.trim().equals("hot")) {
            get_hot_list();
        } else {
            get_new_blog_list();
        }
        return "success";
    }

    private void get_new_blog_list() {
        UserQuery qry = new UserQuery(
                "u.loginName, u.articleCount, u.userIcon, u.blogName, u.trueName, u.blogIntroduce,u.createDate, u.visitCount,u.myArticleCount, u.otherArticleCount, u.resourceCount, u.commentCount,u.userScore,u.userTags");
        qry.setOrderType(0);
        qry.setUserStatus(0);
        qry.setK(k);
        if(null!=request.getParameter("gradeId")&&0<request.getParameter("gradeId").trim().length()&&!"-1".equals(request.getParameter("gradeId").trim())){
        	qry.setGradeId(Integer.parseInt(request.getParameter("gradeId")));
        }
        
        if(null!=request.getParameter("subjectId")&&request.getParameter("subjectId").trim().length()>0&&!"-1".equals(request.getParameter("subjectId").trim())){
        	qry.setSubjectId(Integer.parseInt(request.getParameter("subjectId")));
        }
        
        if(sysCateId!=0){
        	qry.setSysCateId(sysCateId);
        }
        Pager pager = params.createPager();
        pager.setItemName("用户");
        pager.setItemUnit("个");
        pager.setPageSize(15);
        pager.setTotalRows(qry.count());
        request.setAttribute("pager", pager);
        request.setAttribute("user_list", qry.query_map(pager));
        if (type.trim().equals("search")) {
            request.setAttribute("list_type", "工作室搜索");
        } else {
            request.setAttribute("list_type", "最新工作室");
        }
    }

    private void get_hot_list() {
        UserQuery qry = new UserQuery(
                "u.loginName, u.userIcon, u.blogName, u.trueName,u.blogIntroduce,u.createDate, u.visitCount,u.articleCount, u.resourceCount, u.commentCount,u.userScore,u.userTags");
        qry.setOrderType(1);
        qry.setUserStatus(0);
        Pager pager = params.createPager();
        pager.setItemName("用户");
        pager.setItemUnit("个");
        pager.setPageSize(15);
        pager.setTotalRows(qry.count());
        Object obj = qry.query_map(pager);
        request.setAttribute("pager", pager);
        request.setAttribute("user_list", obj);
        request.setAttribute("list_type", "热门工作室");
    }

    private void get_score_list() {
        UserQuery qry = new UserQuery(
                "u.loginName, u.userIcon, u.blogName, u.trueName,u.blogIntroduce,u.createDate, u.visitCount,u.articleCount, u.resourceCount, u.commentCount,u.userScore,u.userTags");
        qry.setOrderType(6);
        qry.setUserStatus(0);
        Pager pager = params.createPager();
        pager.setItemName("用户");
        pager.setItemUnit("个");
        pager.setPageSize(15);
        pager.setTotalRows(qry.count());
        request.setAttribute("pager", pager);
        request.setAttribute("user_list", qry.query_map(pager));
        request.setAttribute("list_type", "工作室积分排行");
    }

    private void get_typeduser_list(Integer typdId) {
        UserQuery qry = new UserQuery(
                "u.loginName, u.userIcon, u.blogName, u.trueName, u.blogIntroduce, u.createDate, u.visitCount,u.articleCount, u.resourceCount, u.commentCount,u.userScore,u.userTags");
        qry.setOrderType(0);
        qry.setUserStatus(0);
        qry.setUserTypeId(typdId);
        Pager pager = params.createPager();
        pager.setItemName("用户");
        pager.setItemUnit("个");
        pager.setPageSize(15);
        pager.setTotalRows(qry.count());
        request.setAttribute("pager", pager);
        request.setAttribute("user_list", qry.query_map(pager));
        String user_typename = "";
        List<UserType> type_list = userService.getAllUserType();
        if (type_list != null && type_list.size() > 0) {
            for (UserType t : type_list) {
                if (t.getTypeId() == typdId) {
                    user_typename = t.getTypeName();
                    break;
                }
            }
        }
        request.setAttribute("list_type", user_typename);
    }
}
