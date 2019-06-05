package cn.edustar.jitar.action;

import java.util.List;

import cn.edustar.data.Pager;
import cn.edustar.data.paging.PagingQuery;
import cn.edustar.jitar.model.CategoryTreeModel;
import cn.edustar.jitar.pojos.BackYear;
import cn.edustar.jitar.pojos.Unit;
import cn.edustar.jitar.service.CategoryService;
import cn.edustar.jitar.service.PagingService;
import cn.edustar.jitar.service.UserService;
import cn.edustar.jitar.service.WebSiteManageService;
import cn.edustar.jitar.util.CommonUtil;
import cn.edustar.jitar.util.HtmlPager;
import cn.edustar.jitar.pojos.UserType;

/**
 * 文章栏目页面
 * 
 * @author mxh
 * 
 */
public class SiteArticlesAction extends AbstractBasePageAction {

    /**
     * 
     */
    private static final long serialVersionUID = 6810511322543287820L;

    private CategoryService categoryService;

    private PagingService pagingService;

    private WebSiteManageService webSiteManageService;

    private UserService userService;

    private List<BackYear> backYearList = null;

    private String type = "new";
    private String k = null;

    @Override
    public String execute(String cmd) throws Exception {
        String returnName = "list";
        //System.out.println("cmd=" + cmd);
        //this.getBackyearList();
        // # 文章分类
        this.getArticleCate();

        // # 学科分类
        this.putMetaSubjectList();

        // # 学段分类
        this.getGradeList();

        
        type = params.getStringParam("type");
        if (null == type || type.length() == 0) {
            type = "new";
        }
        
        k = this.params.getStringParam("k", null);        
        if (cmd.equals("ajax")) {
            this.getArticleList();
            returnName = "ajax";
        }
        request.setAttribute("head_nav", "articles");
        request.setAttribute("type", type);
        request.setAttribute("k", k);
        return returnName;
    }

    private void getArticleList() {
        Unit unit = this.getRootUnit();
        Integer year = params.getIntParamZeroAsNull("year");
        if (null == backYearList) {
            year = null;
        }
        Boolean IsValidYear = false;
        if (null != year && null != backYearList && backYearList.size() > 0) {
            for (BackYear y : backYearList) {
                if (year == y.getBackYear()) {
                    IsValidYear = true;
                    break;
                }
            }
            if (IsValidYear == false) {
                year = null;
            }
        }
        Integer blogUserId = params.getIntParamZeroAsNull("userId");
        String strWhereClause = "HideState=0 And AuditState=0 And DraftState=0 And DelState=0 And ApprovedPathInfo Like '%/" + unit.getUnitId()
                + "/%'";

        if (null != blogUserId) {
            strWhereClause += " And UserId=" + blogUserId;
        }
        String strOrderBy = "ArticleId DESC";
        String list_type = "最新文章";

        if (type.equals("hot")) {
            strOrderBy = "ViewCount DESC";
            list_type = "热门文章";
        } else if (type.equals("best")) {
            strWhereClause = strWhereClause + " And BestState = 1";
            list_type = "精华文章";
        } else if (type.equals("rcmd")) {
            strWhereClause = strWhereClause + "  And RcmdPathInfo Like '%/" + unit.getUnitId() + "/%'";
            list_type = "推荐文章";
        } else if (type.equals("cmt")) {
            strOrderBy = "CommentCount DESC";
            list_type = "评论最多文章";
        } else if (type.equals("famous")) {
            // # 为了不增加额外的表，现在先得到全部名师的 id，然后再去查询。
            Boolean nodata;
            String uid;
            List<Integer> user_list = userService.getAllUserIdByUserType(UserType.USERTYPE_FAMOUSE);
            if (null == user_list || user_list.size() == 0) {
                nodata = true;
            } else {
                uid = "";
                for (Integer u : user_list)
                    uid += u + ",";
                uid = uid.substring(0, uid.length() - 1);
                strWhereClause = strWhereClause + " And UserId IN (" + uid + ")";
                list_type = "名师文章";
            }
        } else if (type.equals("digg")) {
            strOrderBy = "Digg DESC";
            list_type = "按顶排序";
        } else if (type.equals("trample")) {
            strOrderBy = "Trample DESC";
            list_type = "按踩排序";
        } else if (type.equals("star")) {
            strOrderBy = "StarCount/CommentCount DESC";
            strWhereClause = strWhereClause + " And CommentCount > 0";
            list_type = "按星排序";
        } else {
            type = "new";
        }

        if (k != null && k.trim().length() != 0) {
            String newKey = CommonUtil.escapeSQLString(k);
            strWhereClause = strWhereClause + " And Title Like '%" + newKey + "%'";
        }

        Integer sysCateId = params.getIntParamZeroAsNull("categoryId");

        if (subjectId != null) {
            strWhereClause = strWhereClause + " And SubjectId = " + subjectId;
        }
        if (sysCateId != null) {
            // #只查询分类自己的
            // #strWhereClause = strWhereClause + " And SysCateId = " +
            // str(sysCateId)
            // #查询包含子孙分类的
            List<Integer> list = this.categoryService.getCategoryIds(sysCateId);
            String cateIds = "";
            for (Integer c : list) {
                if (cateIds.trim().length() == 0) {
                    cateIds = cateIds + c;
                }

                else {
                    cateIds = cateIds + "," + c;
                }
            }
            strWhereClause = strWhereClause + " And SysCateId IN (" + cateIds + ")";
        }
        if (gradeId != null) {
            strWhereClause = strWhereClause + " And GradeId = " + gradeId;
        }

        PagingQuery pagingQuery = new PagingQuery();
        pagingQuery.keyName = "ArticleId";
        pagingQuery.fetchFieldsName = "*";
        pagingQuery.orderByFieldName = strOrderBy;
        pagingQuery.spName = "findPagingArticle";
        if (year == null) {
            pagingQuery.tableName = "Jitar_Article";
        } else {
            pagingQuery.tableName = "HtmlArticle" + year;
        }
        pagingQuery.whereClause = strWhereClause;

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
        //System.out.println("article_list=" + article_list);
        request.setAttribute("list_type", list_type);
        request.setAttribute("article_list", article_list);
        request.setAttribute("categoryId", sysCateId);        
        request.setAttribute("pager", pager);
        request.setAttribute("year", year);
        String html = HtmlPager.render(pager.getTotalPages(), 3, pager.getCurrentPage());
        request.setAttribute("HtmlPager", html);
        
    }

    private void getGradeList() {
        this.putGradeList();
    }


    private void putMetaSubjectList() {
        request.setAttribute("subject_list", this.subjectService.getMetaSubjectList());
    }

    private void getArticleCate() {
        CategoryTreeModel article_cates = categoryService.getCategoryTree("default");
        request.setAttribute("article_cates", article_cates);
        request.setAttribute("trees", this.categoryService.showTree("default"));

    }

    private void getBackyearList() {
        backYearList = webSiteManageService.getBackYearList("article");
        if (null == backYearList || backYearList.size() == 0) {
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

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

}
