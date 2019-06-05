package cn.edustar.jitar.action;

import java.net.URLEncoder;

import cn.edustar.jitar.model.ObjectType;
import cn.edustar.jitar.pojos.Article;
import cn.edustar.jitar.pojos.Group;
import cn.edustar.jitar.pojos.Photo;
import cn.edustar.jitar.pojos.Subject;
import cn.edustar.jitar.pojos.UPunishScore;
import cn.edustar.jitar.pojos.Unit;
import cn.edustar.jitar.pojos.User;
import cn.edustar.jitar.service.ArticleService;
import cn.edustar.jitar.service.GroupService;
import cn.edustar.jitar.service.PhotoService;
import cn.edustar.jitar.service.SubjectService;
import cn.edustar.jitar.service.UPunishScoreService;
import cn.edustar.jitar.service.UnitService;
import cn.edustar.jitar.service.UserService;
import cn.edustar.jitar.util.EncryptDecrypt;
import cn.edustar.jitar.util.ParamUtil;

/**
 * 通用转向页面
 * 
 * @author mxh
 * 
 */
public class GoAction extends AbstractServletAction {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private UserService userService;
    private GroupService groupService;
    private ArticleService articleService;
    private PhotoService photoService;
    private UnitService unitService;
    private SubjectService subjectService;
    private UPunishScoreService uPunishScoreService;

    @Override
    public final String execute() throws Exception {
        request.setAttribute("ru", request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath());
        String contextPath = request.getContextPath() + "/";
        ParamUtil params = new ParamUtil(getActionContext().getParameters());
        int unitId = params.safeGetIntParam("unitId");
        if (params.existParam("unitId")) {
            unitId = params.safeGetIntParam("unitId");
        }
        String subjectUrlPattern = null;
        String unitUrlPattern = null;
        String userUrlPattern = request.getSession().getServletContext().getInitParameter("userUrlPattern");
        Object _o = request.getAttribute("UnitUrlPattern");
        if (_o != null) {
            unitUrlPattern = _o.toString();
        }
        _o = request.getAttribute("SubjectUrlPattern");
        if (_o != null) {
            subjectUrlPattern = _o.toString();
        }
        if (params.existParam("userId")) {
            int userId = params.getIntParam("userId");
            User user = userService.getUserById(userId);
            if (user == null) {
                this.addActionError("指定用户不存在");
                this.addActionLink("返回网站首页", contextPath);
                return ERROR;
            }
            if (userUrlPattern == null) {
                response.setStatus(301);
                response.setHeader("Location", URLEncoder.encode(user.getLoginName(),"utf-8"));
                response.setHeader("Connection", "close");
                return NONE;
            } else {
                userUrlPattern = userUrlPattern.replace("{loginName}", URLEncoder.encode(user.getLoginName(),"utf-8"));
                response.setStatus(301);
                response.setHeader("Location", userUrlPattern);
                response.setHeader("Connection", "close");
                return NONE;
            }
        } else if (params.existParam("loginName")) {
            String loginName = params.safeGetStringParam("loginName");
            if (loginName == null || loginName == "") {
                addActionError("指定用户不存在");
                addActionLink("返回网站首页", contextPath);
                return ERROR;
            }
            if (userUrlPattern == null || userUrlPattern.trim().length() < 1) {
                response.sendRedirect(URLEncoder.encode(loginName,"utf-8"));
                return NONE;
            } else {
                userUrlPattern = userUrlPattern.replace("{loginName}", URLEncoder.encode(loginName,"utf-8"));
                response.sendRedirect(userUrlPattern);
                return NONE;
            }
        } else if (params.existParam("photoId")) {
            Integer photoId = params.getIntParam("photoId");
            if (photoId == null || photoId == 0) {
                addActionError("缺少标识。");
                addActionLink("返回网站首页", contextPath);
                return ERROR;
            }

            Photo photo = photoService.findById(photoId);
            if (photo == null) {
                addActionError("指定的图像不存在");
                addActionLink("返回网站首页", contextPath);
                return ERROR;
            }
            int userId = photo.getUserId();
            User user = userService.getUserById(userId);
            if (user == null) {
                addActionError("指定用户不存在");
                addActionLink("返回网站首页", contextPath);
                return ERROR;
            }

            response.setStatus(301);
            response.setHeader("Location", contextPath + "photos.action?cmd=detail&photoId=" + photoId);
            response.setHeader("Connection", "close");
            return NONE;

        } else if (params.existParam("profile")) {
            String loginName = params.safeGetStringParam("profile");
            User user = userService.getUserByLoginName(loginName, false);
            if (user == null) {
                addActionError("指定用户不存在");
                addActionLink("返回网站首页", contextPath);
                return ERROR;
            }
            if (userUrlPattern == null || userUrlPattern.trim().length() < 1) {
                response.sendRedirect(URLEncoder.encode(user.getLoginName(),"utf-8") + "/profile");
                return NONE;
            } else {
                userUrlPattern = userUrlPattern.replace("{loginName}", URLEncoder.encode(loginName,"utf-8")) + "profile";
                response.sendRedirect(userUrlPattern);
                return NONE;
            }
        } else if (params.existParam("courseId")) {
            int courseId = params.getIntParam("courseId");
            response.sendRedirect("p/" + courseId + "/0/");
            return NONE;
        } else if (params.existParam("groupId")) {
            int groupId = params.getIntParam("groupId");
            Group group = groupService.getGroup(groupId);
            if (group == null) {
                addActionError("指定群组不存在");
                addActionLink("返回网站首页", contextPath);
                return ERROR;
            }

            response.sendRedirect("g/" + group.getGroupName());
            return NONE;
        } else if (params.existParam("groupName")) {
            String groupName = params.safeGetStringParam("groupName");
            if (groupName == null || groupName.trim().length() == 0) {
                addActionError("请指定协作组名称");
                addActionLink("返回网站首页", contextPath);
                return ERROR;
            }
            response.sendRedirect("g/" + groupName);
            return NONE;
        } else if (params.existParam("encPid")) {
            String pid = params.safeGetStringParam("encPid");
            if (pid == null || pid.trim().length() == 0) {
                addActionError("无效的集备标识。");
                addActionLink("返回网站首页", contextPath);
                return ERROR;
            }
            EncryptDecrypt ed = new EncryptDecrypt();
            pid = ed.decrypt(pid);
            ed = null;
            if (pid.indexOf("|") > -1) {
                pid = pid.substring(0, pid.indexOf("|"));
            }
            response.sendRedirect("p/" + pid + "/0/");
            return NONE;
        } else if (params.existParam("articleId")) {
            int articleId = params.getIntParam("articleId");
            Article article = articleService.getArticle(articleId);
            if (article == null) {
                addActionError("指定文章不存在");
                addActionLink("返回网站首页", contextPath);
                return ERROR;
            }

            // #检查本文是否加分
            UPunishScore punshScore = this.uPunishScoreService.getUPunishScore(ObjectType.OBJECT_TYPE_ARTICLE.getTypeId(), articleId);
            if (punshScore != null) {
                if (punshScore.getScore() < 0) {
                    request.setAttribute("scoreCreateUserId", punshScore.getCreateUserId());
                    request.setAttribute("scoreCreateUserName", punshScore.getCreateUserName());
                    request.setAttribute("score", -1 * punshScore.getScore());
                    request.setAttribute("scoreReason", punshScore.getReason());
                    request.setAttribute("scoreDate", punshScore.getPunishDate());
                    request.setAttribute("scoreObjId", punshScore.getObjId());
                    request.setAttribute("scoreObjTitle", punshScore.getObjTitle());
                }
            }

            response.setStatus(301);
            response.setHeader("Location", contextPath + "showArticle.action?articleId=" + articleId);
            response.setHeader("Connection", "close");
            
            return NONE;
        } else if (params.existParam("unitId")) {

            Unit unit = unitService.getUnitById(unitId);
            if (null == unit) {
                addActionError("指定的机构不存在。");
                addActionLink("返回网站首页", contextPath);
                return ERROR;
            }
            if(unit.getDelState()){
                addActionError("该机构已经被删除。");
                addActionLink("返回网站首页", contextPath);
                return ERROR;
            }
            
            String unitName = unit.getUnitName();
            if (null == unitName || unitName.trim().length() == 0) {
                addActionError("没有设置机构英文名称。");
                addActionLink("返回网站首页", contextPath);
                return ERROR;
            }
            if (unitUrlPattern != null && unitUrlPattern.trim().length() != 0) {
                unitUrlPattern = unitUrlPattern.replace("{unitName}", unitName);
                response.setStatus(301);
                response.setHeader("Location", unitUrlPattern);
                response.setHeader("Connection", "close");
                // #response.sendRedirect(unitUrlPattern);
                return NONE;
            } else {
                response.setStatus(301);
                response.setHeader("Location", request.getContextPath() + "/d/" + unitName + "/");
                response.setHeader("Connection", "close");
                // #response.sendRedirect(contextPath + "d/" + unitName + "/");
                return NONE;
            }
        } else if (params.existParam("unitName")) {
            String unitName = params.safeGetStringParam("unitName", null);
            if (unitName == null) {
                addActionError("没有指定机构名称。");
                addActionLink("返回网站首页", contextPath);
                return ERROR;
            }
            if (unitUrlPattern != null && unitUrlPattern.trim().length() != 0) {
                unitUrlPattern = unitUrlPattern.replace("{unitName}", unitName);
                response.setStatus(301);
                response.setHeader("Location", unitUrlPattern);
                response.setHeader("Connection", "close");
                return NONE;
            } else {
                Unit unit = unitService.getUnitByName(unitName);
                if (unit != null) {
                    response.setStatus(301);
                    response.setHeader("Location", contextPath + "d/" + unitName + "/");
                    response.setHeader("Connection", "close");
                    return NONE;
                } else {
                    addActionError("没有设置机构的英文名称。");
                    addActionLink("返回网站首页", contextPath);
                    return ERROR;
                }
            }
        } else if (params.existParam("id")) {
            // 学科id
            int id = params.safeGetIntParam("id");
            Subject subject = subjectService.getSubjectById(id);
            if (subject != null) {
                String subjectCode = subject.getSubjectCode();
                if (subjectUrlPattern != null && subjectUrlPattern.trim().length() != 0) {
                    subjectUrlPattern = subjectUrlPattern.replace("{subjectCode}", subjectCode);
                    if (unitId != 0) {
                        subjectUrlPattern = subjectUrlPattern + "?unitId=" + unitId;
                    }
                    response.setStatus(301);
                    response.setHeader("Location", subjectUrlPattern);
                    response.setHeader("Connection", "close");
                    return NONE;
                } else {
                    response.setStatus(301);
                    String subjectUrl = contextPath + "k/" + subjectCode + "/";
                    if (unitId != 0) {
                        subjectUrl = subjectUrl + "?unitId=" + unitId;
                    }
                    response.setHeader("Location", subjectUrl);
                    response.setHeader("Connection", "close");
                    return NONE;
                }
            } else {
                this.addActionError("无法加载学科对象。");
                this.addActionLink("返回网站首页", contextPath);
                return ERROR;
            }
        } else if (params.existParam("subjectId") && params.existParam("gradeId")) {
            int metaSubjectId = params.safeGetIntParam("subjectId");
            int metaGradeId = params.safeGetIntParam("gradeId");
            Subject subject = subjectService.getSubjectByMetaData(metaSubjectId, metaGradeId);
            if (subject != null) {
                String subjectCode = subject.getSubjectCode();
                if (subjectUrlPattern != null && subjectUrlPattern.trim().length() != 0) {
                    subjectUrlPattern = subjectUrlPattern.replace("{subjectCode}", subjectCode);
                    if (unitId != 0) {
                        subjectUrlPattern = subjectUrlPattern + "?unitId=" + unitId;
                    }
                    response.setStatus(301);
                    response.setHeader("Location", subjectUrlPattern);
                    response.setHeader("Connection", "close");
                    return NONE;
                } else {
                    response.setStatus(301);
                    String subjectUrl = contextPath + "k/" + subjectCode + "/";
                    if (unitId != 0) {
                        subjectUrl = subjectUrl + "?unitId=" + unitId;
                    }
                    response.setHeader("Location", subjectUrl);
                    response.setHeader("Connection", "close");
                    request.setAttribute("ru", contextPath + "k/" + subjectCode + "/");
                    return NONE;
                }
            } else {
                this.addActionError("无法加载学科对象。");
                this.addActionLink("返回网站首页", contextPath);
                return ERROR;
            }
        } else if (params.existParam("objType") && params.existParam("objId")) {
            int objType = params.safeGetIntParam("objType");
            int objId = params.safeGetIntParam("objId");
            if (objType == ObjectType.OBJECT_TYPE_ARTICLE.getTypeId()) {
                response.sendRedirect(request.getContextPath() + "/showArticle.action?articleId=" + objId);
            } else if (objType == ObjectType.OBJECT_TYPE_RESOURCE.getTypeId()) {
                response.sendRedirect(request.getContextPath() + "/showResource.action?resourceId=" + objId);
            } else if (objType == ObjectType.OBJECT_TYPE_PHOTO.getTypeId()) {
                response.sendRedirect(request.getContextPath() + "/photo.action?photoId=" + objId);
            } else if (objType == ObjectType.OBJECT_TYPE_VIDEO.getTypeId()) {
                response.sendRedirect(request.getContextPath() + "/manage/video.action?cmd=show&videoId=" + objId);
            } else {
                this.addActionError("暂不支持的类型");
                this.addActionLink("返回网站首页", contextPath);
                return ERROR;
            }
        }

        this.addActionError("无法识别指定参数");
        this.addActionLink("返回网站首页", contextPath);
        return ERROR;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public void setGroupService(GroupService groupService) {
        this.groupService = groupService;
    }

    public void setArticleService(ArticleService articleService) {
        this.articleService = articleService;
    }

    public void setPhotoService(PhotoService photoService) {
        this.photoService = photoService;
    }

    public void setUnitService(UnitService unitService) {
        this.unitService = unitService;
    }

    public void setSubjectService(SubjectService subjectService) {
        this.subjectService = subjectService;
    }

    public void setuPunishScoreService(UPunishScoreService uPunishScoreService) {
        this.uPunishScoreService = uPunishScoreService;
    }
}
