package cn.edustar.jitar.action;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.context.ContextLoader;

import cn.edustar.data.paging.PagingQuery;
import cn.edustar.jitar.JitarContext;
import cn.edustar.jitar.jython.JythonBaseAction;
import cn.edustar.jitar.pojos.Subject;
import cn.edustar.jitar.service.AccessControlService;
import cn.edustar.jitar.service.CacheService;
import cn.edustar.jitar.service.PagingService;
import cn.edustar.jitar.service.SiteNavService;
import cn.edustar.jitar.service.SubjectService;
import cn.edustar.jitar.service.UserSubjectGradeQuery;
import cn.edustar.jitar.util.CommonUtil;
import cn.edustar.jitar.util.ParamUtil;

import com.opensymphony.xwork2.ActionContext;

public class BaseSubject extends JythonBaseAction {

    public static final String ERROR = "/WEB-INF/ftl/Error.ftl";
    public static final String ACCESS_DENIED = "/WEB-INF/ftl/Error.ftl";
    public static final String ACCESS_ERROR = "/WEB-INF/ftl/mengv1/access_error.ftl";
    public static final String SUCCESS = "/WEB-INF/ftl/success.ftl";
    public static final String LOGIN = "/login.jsp";

    public ParamUtil params = null;
    public String userIds = "";
    public Integer unitId = null;
    public Integer metaGradeId = null;
    public Integer metaSubjectId = null;
    public Integer subjectId = null;
    public String subjectCode = null;
    public Subject subject = null;

    public SubjectService subjectService;
    public AccessControlService accessControlService;
    public PagingService pagingService;
    public SiteNavService siteNavService;

    public BaseSubject() {
        super();
        init();
        // TODO Auto-generated constructor stub
    }

    public Subject getSubject() {
        return subject;
    }

    public HttpServletRequest request = null;
    public String subjectRootUrl = null;

    public void init() {

        subjectService = ContextLoader.getCurrentWebApplicationContext().getBean("subjectService", SubjectService.class);
        accessControlService = ContextLoader.getCurrentWebApplicationContext().getBean("accessControlService", AccessControlService.class);
        pagingService = ContextLoader.getCurrentWebApplicationContext().getBean("pagingService", PagingService.class);
        siteNavService = ContextLoader.getCurrentWebApplicationContext().getBean("siteNavService", SiteNavService.class);

        request = super.getRequest();
        params = new ParamUtil(ActionContext.getContext().getParameters());
        subjectId = params.safeGetIntParam("id");
        subject = subjectService.getSubjectById(subjectId);

        unitId = params.getIntParamZeroAsNull("unitId");
        metaGradeId = params.safeGetIntParam("gradeId");
        metaSubjectId = params.safeGetIntParam("subjectId");
        if (subject == null) {
            subject = subjectService.getSubjectByMetaData(metaSubjectId, metaGradeId);
        }

        Object obj = request.getAttribute("subjectCode");
        subjectCode = null != obj ? (String) obj : null;

        if (subject == null) {
            subject = subjectService.getSubjectByCode(subjectCode);
        }
        if (subject == null) {
            try {
                write("Cann't load Subject Object !");
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return;
        }

        request.setAttribute("subjectCode",subject.getSubjectCode());

        subjectCode = (String) request.getAttribute("subjectCode");

        if (unitId != null && unitId != 0) {
            request.setAttribute("unitId", unitId);
        }

        if (subjectService == null) {
            try {
                write("Cann't load subjectService !");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        subject = subjectService.getSubjectByMetaData(metaSubjectId, metaGradeId);
        if (subject == null) {
            subject = subjectService.getSubjectById(subjectId);
        }
        if (subject == null) {
            subject = subjectService.getSubjectByCode(subjectCode);
        }
        // # self.write("Object not be found !")
        if (subject == null) {
            try {
                write("Cann't load Subject Object !");
            } catch (Exception e) {
                e.printStackTrace();
            }
            return;
        }

        if (isAdmin() || isContentAdmin() || isUserAdmin()) {
            request.setAttribute("isAdmin", "");
        }

        metaSubjectId = subject.getMetaSubject().getMsubjId();
        metaGradeId = subject.getMetaGrade().getGradeId();
        subjectCode = subject.getSubjectCode();

        subjectRootUrl = (String) request.getAttribute("SubjectRootUrl");
        if (subjectRootUrl == null) {
            String configSubjectSiteRoot = request.getSession().getServletContext().getInitParameter("subjectUrlPattern");
            if (configSubjectSiteRoot == null || "".equals(configSubjectSiteRoot.trim())) {
                subjectRootUrl = CommonUtil.getSiteUrl(request) + "k/" + subject.getSubjectCode() + "/";
            } else {
                configSubjectSiteRoot = String.valueOf(configSubjectSiteRoot);
                subjectRootUrl = configSubjectSiteRoot.replaceAll("\\{subjectCode\\}", subject.getSubjectCode());
            }

        }
        request.setAttribute("SubjectRootUrl", subjectRootUrl);
        CacheService cache = JitarContext.getCurrentJitarContext().getCacheProvider().getCache("sitenav");
        String cache_k = "subject_nav_" + subject.getSubjectId();
        Object subjectSiteNavList = cache.get(cache_k);
        if (subjectSiteNavList == null) {
            subjectSiteNavList = siteNavService.getAllSiteNav(false, 2, subject.getSubjectId());
            cache.put(cache_k, subjectSiteNavList);
        }
        request.setAttribute("SubjectSiteNavList", subjectSiteNavList);

        // #得到多学科用户
        UserSubjectGradeQuery qry2 = new UserSubjectGradeQuery("usg.userId");
        qry2.setSubjectId(get_current_subjectId());
        qry2.setGradeId(get_current_gradeId());
        // True #匹配学段，包括年级
        qry2.setFuzzyMatch(true);
        List<HashMap> usg_list = qry2.query_map(qry2.count());
        if (usg_list != null && usg_list.size() > 0) {
            /*
             * Iterator<JSONObject> iterator = usg_list.iterator();
             * while(iterator.hasNext()){ userIds +=
             * iterator.next().get("userId"); }
             */
            for (HashMap uu : usg_list) {
                userIds += uu.get("userId") + ",";
            }
        }
        if (!"".equals(userIds) && userIds.endsWith(".")) {
            userIds = userIds.substring(0, userIds.length() - 1);
        }
    }

    public Integer get_current_gradeId() {
        metaGradeId = subject.getMetaGrade().getGradeId();
        request.setAttribute("gradeId", metaGradeId);
        return metaGradeId;
    }

    public Integer get_current_subjectId() {
        subjectId = subject.getMetaSubject().getMsubjId();
        request.setAttribute("subjectId", subjectId);
        return subjectId;
    }

    public boolean isContentAdmin() {
        boolean flag = accessControlService.userIsSubjectContentAdmin(super.getLoginUser(), this.subject);
        if (flag) {
            request.setAttribute("isContentAdmin", "1");
        } else {
            request.setAttribute("isContentAdmin", "0");
        }
        return flag;
    }

    public boolean isAdmin() {
        boolean flag = accessControlService.userIsSubjectAdmin(super.getLoginUser(), this.subject);
        if (flag) {
            request.setAttribute("isSystemAdmin", "1");
            request.setAttribute("isAdmin", "1");
        } else {
            request.setAttribute("isSystemAdmin", "0");
        }
        return flag;
    }

    private boolean isUserAdmin() {
        boolean flag = accessControlService.userIsSubjectUserAdmin(super.getLoginUser(), this.subject);
        if (flag) {
            request.setAttribute("isUserAdmin", "1");
        } else {
            request.setAttribute("isUserAdmin", "0");
        }
        return flag;
    }

    public void clearSubjectCache() {
        if (subjectId != 0) {

        }
        CacheService cache = JitarContext.getCurrentJitarContext().getCacheProvider().getCache("subject");
        if (cache == null) {
            return;
        }
        List<String> cache_list = cache.getAllKeys();
        String cache_key_head = "sbj" + subjectId;
        for (String c : cache_list) {
            if (c.split("_")[0].trim().equals(cache_key_head.trim())) {
                cache.remove(c);
            }
        }
    }

    // 名师工作室
    public List getFamousList() {
        Integer minNum = CommonUtil.convertRoundMinNumber(get_current_gradeId());
        Integer maxNum = CommonUtil.convertRoundMaxNumber(get_current_gradeId());
        String strOrderBy = "UserId DESC";
        String strWhereClause = "(UserStatus=0 And UserType LIKE '%/1/%'";
        String strWhere1 = strWhereClause + " And GradeId >= " + minNum + " And GradeId<" + maxNum + " And SubjectId=" + get_current_subjectId();
        strWhere1 += ")";
        String strWhere2 = "";
        userIds = userIds.trim();
        if (!"".equals(userIds.trim())) {
            if(userIds.endsWith(",")){
                userIds = userIds.substring(0,userIds.length() - 1);
            }
            strWhere2 = " And UserId In(" + userIds + ")";
        }
        if (!"".equals(strWhere2)) {
            strWhere2 = strWhereClause + strWhere2 + ")";
        }
        if ("".equals(strWhere2.trim())) {
            strWhereClause = strWhere1;
        } else {
            strWhereClause = strWhere1 + " Or " + strWhere2;
        }
        return pagingService.getPagingList(new PagingQuery("Jitar_User", "UserId", "*", strOrderBy, strWhereClause, "findPagingUser", 6));
    }

    // # 学科带头人工作室.
    public List getExpertList() {
        Integer minNum = CommonUtil.convertRoundMinNumber(get_current_gradeId());
        Integer maxNum = CommonUtil.convertRoundMaxNumber(get_current_gradeId());
        String strOrderBy = "UserId DESC";
        String strWhereClause = "(UserStatus=0 And UserType LIKE '%/3/%'";
        String strWhere1 = strWhereClause + " And GradeId >= " + minNum + " And GradeId<" + maxNum + " And SubjectId=" + get_current_subjectId();
        strWhere1 += ")";
        String strWhere2 = "";
        if (!"".equals(userIds.trim())) {
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
        return pagingService.getPagingList(new PagingQuery("Jitar_User", "UserId", "*", strOrderBy, strWhereClause, "findPagingUser", 3));
    }

    // # 工作室访问排行
    public List getHotList(Integer topCount) {
        Integer minNum = CommonUtil.convertRoundMinNumber(get_current_gradeId());
        Integer maxNum = CommonUtil.convertRoundMaxNumber(get_current_gradeId());
        String strOrderBy = "VisitCount DESC";
        String strWhereClause = "(UserStatus=0 ";
        String strWhere1 = strWhereClause + " And GradeId >= " + minNum + " And GradeId<" + maxNum + " And SubjectId=" + get_current_subjectId();
        strWhere1 += ")";
        String strWhere2 = "";

        if (!"".equals(userIds.trim())) {
            if(userIds.endsWith(",")){
                userIds = userIds.substring(0,userIds.length() - 1);
            }
            strWhere2 = " And UserId In(" + userIds + ")";
        }
        if (!"".equals(strWhere2.trim())) {
            strWhere2 = strWhereClause + strWhere2 + ")";
        }
        if ("".equals(strWhere2.trim())) {
            strWhereClause = strWhere1;
        } else {
            strWhereClause = strWhere1 + " Or " + strWhere2;
        }
        return pagingService.getPagingList(new PagingQuery("Jitar_User", "UserId", "*", strOrderBy, strWhereClause, "findPagingUser", topCount));
    }

    // # 最新工作室排行.
    public List getNewList(Integer topCount) {
        Integer minNum = CommonUtil.convertRoundMinNumber(get_current_gradeId());
        Integer maxNum = CommonUtil.convertRoundMaxNumber(get_current_gradeId());
        String strOrderBy = "UserId DESC";
        String strWhereClause = "(UserStatus=0 ";
        String strWhere1 = strWhereClause + " And GradeId >= " + minNum + " And GradeId<" + maxNum + " And SubjectId=" + get_current_subjectId();
        strWhere1 += ")";
        String strWhere2 = "";

        if (!"".equals(userIds.trim())) {
            if(userIds.endsWith(",")){
                userIds = userIds.substring(0,userIds.length() - 1);
            }
            strWhere2 = " And UserId In(" + userIds + ")";
        }
        if (!"".equals(strWhere2.trim())) {
            strWhere2 = strWhereClause + strWhere2 + ")";
        }

        if ("".equals(strWhere2.trim())) {
            strWhereClause = strWhere1;
        } else {
            strWhereClause = strWhere1 + " Or " + strWhere2;
        }
        return pagingService.getPagingList(new PagingQuery("Jitar_User", "UserId", "*", strOrderBy, strWhereClause, "findPagingUser", topCount));
    }

    // # 推荐工作室.
    public List getRcmdList(Integer topCount) {
        Integer minNum = CommonUtil.convertRoundMinNumber(get_current_gradeId());
        Integer maxNum = CommonUtil.convertRoundMaxNumber(get_current_gradeId());
        String strOrderBy = "UserId DESC";
        String strWhereClause = "(UserStatus=0 And UserType LIKE '%/2/%'";
        String strWhere1 = strWhereClause + " And GradeId >= " + minNum + " And GradeId<" + maxNum + " And SubjectId=" + get_current_subjectId();
        strWhere1 += ")";
        String strWhere2 = "";

        if (!"".equals(userIds.trim())) {
            if(userIds.endsWith(",")){
                userIds = userIds.substring(0,userIds.length() - 1);
            }
            strWhere2 = " And UserId In(" + userIds + ")";
        }

        if (!"".equals(strWhere2.trim())) {
            strWhere2 = strWhereClause + strWhere2 + ")";
        }
        if ("".equals(strWhere2.trim())) {
            strWhereClause = strWhere1;
        } else {
            strWhereClause = strWhere1 + " Or " + strWhere2;
        }
        return pagingService.getPagingList(new PagingQuery("Jitar_User", "UserId", "*", strOrderBy, strWhereClause, "findPagingUser", topCount));
    }

    // # 得到学科教研员列表.
    public List getSubjectComissioner() {

        Integer minNum = CommonUtil.convertRoundMinNumber(get_current_gradeId());
        Integer maxNum = CommonUtil.convertRoundMaxNumber(get_current_gradeId());
        String strOrderBy = "VisitCount DESC";
        String strWhereClause = "(UserStatus=0 And UserType LIKE '%/4/%'";
        String strWhere1 = strWhereClause + " And GradeId >= " + minNum + " And GradeId<" + maxNum + " And SubjectId=" + get_current_subjectId();
        strWhere1 += ")";
        String strWhere2 = "";

        if (!"".equals(userIds)) {
            if(userIds.endsWith(",")){
                userIds = userIds.substring(0,userIds.length() - 1);
            }
            strWhere2 = " And UserId In(" + userIds + ")";
        }
        if (!"".equals(strWhere2.trim())) {
            strWhere2 = strWhereClause + strWhere2 + ")";
        }
        if ("".equals(strWhere2.trim())) {
            strWhereClause = strWhere1;
        } else {
            strWhereClause = strWhere1 + " Or " + strWhere2;
        }
        // #request.setAttribute("comissioner_list", comissioner_list)
        return pagingService.getPagingList(new PagingQuery("Jitar_User", "UserId", "*", strOrderBy, strWhereClause, "findPagingUser", 6));

    }

    public void setSiteNavService(SiteNavService siteNavService) {
        this.siteNavService = siteNavService;
    }

    public Integer getUnitId() {
        return unitId;
    }

    public String getSubjectRootUrl() {
        return subjectRootUrl;
    }

    public ParamUtil getParams() {
        return params;
    }

    public void setParams(ParamUtil params) {
        this.params = params;
    }

    public String getUserIds() {
        return userIds;
    }

    public void setUserIds(String userIds) {
        this.userIds = userIds;
    }

    public Integer getMetaGradeId() {
        return metaGradeId;
    }

    public void setMetaGradeId(Integer metaGradeId) {
        this.metaGradeId = metaGradeId;
    }

    public Integer getMetaSubjectId() {
        return metaSubjectId;
    }

    public void setMetaSubjectId(Integer metaSubjectId) {
        this.metaSubjectId = metaSubjectId;
    }

    public Integer getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(Integer subjectId) {
        this.subjectId = subjectId;
    }

    public String getSubjectCode() {
        return subjectCode;
    }

    public void setSubjectCode(String subjectCode) {
        this.subjectCode = subjectCode;
    }

    public SubjectService getSubjectService() {
        return subjectService;
    }

    public void setSubjectService(SubjectService subjectService) {
        this.subjectService = subjectService;
    }

    public AccessControlService getAccessControlService() {
        return accessControlService;
    }

    public void setAccessControlService(AccessControlService accessControlService) {
        this.accessControlService = accessControlService;
    }

    public PagingService getPagingService() {
        return pagingService;
    }

    public void setPagingService(PagingService pagingService) {
        this.pagingService = pagingService;
    }

    public HttpServletRequest getRequest() {
        return request;
    }

    public void setRequest(HttpServletRequest request) {
        this.request = request;
    }

    public static String getError() {
        return ERROR;
    }

    public static String getAccessDenied() {
        return ACCESS_DENIED;
    }

    public static String getAccessError() {
        return ACCESS_ERROR;
    }

    public static String getSuccess() {
        return SUCCESS;
    }

    public static String getLogin() {
        return LOGIN;
    }

    public SiteNavService getSiteNavService() {
        return siteNavService;
    }

    public void setUnitId(Integer unitId) {
        this.unitId = unitId;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public void setSubjectRootUrl(String subjectRootUrl) {
        this.subjectRootUrl = subjectRootUrl;
    }

};
