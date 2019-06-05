package cn.edustar.jitar.action;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import cn.edustar.jitar.pojos.Action;
import cn.edustar.jitar.pojos.ActionUserUnit;

import cn.edustar.jitar.pojos.Subject;
import cn.edustar.jitar.pojos.User;
import cn.edustar.jitar.service.AccessControlService;
import cn.edustar.jitar.service.ActionService;
import cn.edustar.jitar.service.FriendService;
import cn.edustar.jitar.service.GroupService;
import cn.edustar.jitar.service.PrepareCourseService;
import cn.edustar.jitar.service.UserService;
import cn.edustar.jitar.util.DateUtil;

public class SiteActionEditAction extends AbstractBasePageAction {

    /**
     * 
     */
    private static final long serialVersionUID = 99837030994856314L;
    private ActionService actionService;
    private FriendService friendService;
    private UserService userService;
    private GroupService groupService;
    private PrepareCourseService prepareCourseService;
    private AccessControlService accessControlService;

    private String contextPath = "/";

    private int actionId = 0;
    private Action action = null;
    private User loginUser = null;

    // #学科内容管理员权限
    private Boolean contentAdmin = false;
    // #学科管理员权限
    private Boolean manageAdmin = false;

    private String error_msg = "";
    private String returnUrl = "";

    @Override
    protected String execute(String cmd) throws Exception {
        contextPath = request.getContextPath() + "/";
        loginUser = this.getLoginUser();
        if (null == loginUser) {
            this.addActionError("<a href='" + contextPath + "login.jsp'>请先登录</a>。");
            this.addActionLink("返回首页", contextPath);
            this.addActionLink("返回活动列表", contextPath + "actions.action");
            return ERROR;
        }
        actionId = params.getIntParam("actionId");

        if (actionId == 0) {
            this.addActionLink("返回活动列表", contextPath + "actions.action");
            this.addActionError("缺少标识。");
            return ERROR;
        }

        action = this.actionService.getActionById(actionId);
        if (null == action) {
            this.addActionLink("返回活动列表", contextPath + "actions.action");
            this.addActionError("加载活动信息失败。");
            return ERROR;
        }

        this.returnUrl = this.contextPath + "showAction.action?actionId=" + this.action.getActionId();
        if (this.action.getOwnerType().equals("subject")) {
            int subjectId = this.action.getOwnerId();
            Subject subject = this.subjectService.getSubjectById(subjectId);
            this.contentAdmin = this.accessControlService.userIsSubjectContentAdmin(this.loginUser, subject);
            this.manageAdmin = this.accessControlService.userIsSubjectAdmin(this.loginUser, subject);
        }
        if (!(loginUser.getUserId() == this.action.getCreateUserId() || contentAdmin || manageAdmin)) {
            this.addActionLink("返回活动列表", contextPath + "actions.action");
            this.addActionLink("查看本活动", this.returnUrl);
            this.addActionError("只有活动的创建者,学科系统管理员,或者学科内容管理员才可以修改活动。");
            return ERROR;
        }
        if (request.getMethod().equals("POST")) {
            if (cmd.equals("edit")) {
                return this.actionPost();
            } else if (cmd.equals("deluser")) {
                return this.delUser();
            } else if (cmd.equals("inviteuser")) {
                return this.inviteUser();
            } else if (cmd.equals("printuser")) {
                return this.printUser();
            } else {
                this.addActionError("无效的命令。");
                return ERROR;
            }
        } else {
            List<ActionUserUnit> action_user_list = this.actionService.getActionUserWithDistUnit(this.action.getActionId());
            request.setAttribute("action_user_list", action_user_list);
            request.setAttribute("edustarAction", this.action);
            return this.actionGet();
        }
    }
    private String actionGet() {
        this.getDatetimePart("startDateTime", this.action.getStartDateTime());
        this.getDatetimePart("finishDateTime", this.action.getFinishDateTime());
        this.getDatetimePart("attendLimitDateTime", this.action.getAttendLimitDateTime());
        return "edit";
    }
    private void getDatetimePart(String varType, Date varDateTime) {
        Calendar c = Calendar.getInstance();
        c.setTime(varDateTime);
        if (c.get(Calendar.AM_PM) == Calendar.PM) {
            request.setAttribute(varType + "H", c.get(Calendar.HOUR) + 12);
        } else {
            request.setAttribute(varType + "H", c.get(Calendar.HOUR));
        }
        request.setAttribute(varType + "MM", c.get(Calendar.MINUTE));
    }
    private String printUser() throws UnsupportedEncodingException {
        // request.setCharacterEncoding("utf-8");
        List<ActionUserUnit> action_user_list = this.actionService.getActionUserWithDistUnit(this.action.getActionId());
        request.setAttribute("action_user_list", action_user_list);
        request.setAttribute("action", this.action);
        response.reset();
        response.setContentType("application/vnd.ms-excel");
        response.setHeader("Content-Type", "application/vnd.ms-excel; charset=GB2312");
        response.setCharacterEncoding("GB2312");
        response.setLocale(Locale.SIMPLIFIED_CHINESE);
        response.addHeader("Content-Disposition", "attachment;filename=ActionUser.xls");
        return "print";
    }
    private String inviteUser() {
        this.error_msg = "";
        List<Integer> invite_users = params.safeGetIntValues("inviteUserId");
        for (int i = 0; i < invite_users.size(); i++) {
            User user = this.userService.getUserById(invite_users.get(i));
            if (user == null) {
                this.error_msg = this.error_msg + "<li>用户" + invite_users.get(i) + "不是本系统存在的用户。</li>";
            } else {
                if (this.actionService.userIsInAction(user.getUserId(), this.action.getActionId())) {
                    this.error_msg = this.error_msg + "<li>用户" + user.getTrueName() + "已经是该活动的成员。</li>";
                } else {
                    this.actionService.addActionUser(this.action.getActionId(), user, loginUser.getUserId());
                }
            }
        }
        // #更新统计数据
        this.actionService.updateActionUserStatById(this.action.getActionId());
        if (this.error_msg.length() != 0) {

            this.addActionError("操作结果：" + this.error_msg);
            this.addActionLink("查看本活动", this.returnUrl);
            this.addActionLink("编辑本活动", this.contextPath + "actionEdit.action?actionId=" + this.action.getActionId());
            this.addActionLink("返回活动列表", contextPath + "actions.action");
            return ERROR;
        } else {
            this.addActionError("用户添加完毕。");
            this.addActionLink("查看本活动", this.returnUrl);
            this.addActionLink("编辑本活动", this.contextPath + "actionEdit.action?actionId=" + this.action.getActionId());
            this.addActionLink("返回活动列表", contextPath + "actions.action");
            return SUCCESS;
        }

    }

    private String delUser() {
        List<Integer> action_user_users = params.safeGetIntValues("guid");
        if (action_user_users == null || action_user_users.size() == 0) {
            this.addActionError("请选择一个用户。");
            this.addActionLink("查看本活动", this.returnUrl);
            this.addActionLink("编辑本活动", this.contextPath + "actionEdit.action?actionId=" + this.action.getActionId());
            this.addActionLink("返回活动列表", contextPath + "actions.action");
            return ERROR;
        }
        for (int i = 0; i < action_user_users.size(); i++) {
            this.actionService.delActionUserById(action_user_users.get(i));
        }

        this.actionService.updateActionUserStatById(this.action.getActionId());
        this.addActionMessage("用户删除操作完成。");
        this.addActionLink("查看本活动", this.returnUrl);
        this.addActionLink("编辑本活动", this.contextPath + "actionEdit.action?actionId=" + this.action.getActionId());
        this.addActionLink("编辑本活动", this.contextPath + "actionEdit.action?actionId=" + this.action.getActionId());
        return SUCCESS;
    }
    private String actionPost() {
        request.setAttribute("RedUrl", returnUrl);
        String title = params.getStringParam("actionName");
        if (title.trim().length() == 0) {
            this.addActionLink("返回活动", returnUrl);
            this.addActionError("请输入标题。");
            return ERROR;

        }
        int actionType = params.getIntParam("actionType");
        if (0 != actionType && actionType != 1 && actionType != 2) {
            this.addActionLink("返回活动", returnUrl);
            this.addActionError("请选项活动类型。");
            return ERROR;
        }
        int visibility = params.getIntParam("actionVisibility");
        if (0 != visibility && 1 != visibility) {
            this.addActionLink("返回活动", returnUrl);
            this.addActionError("请选项活动方式。");
            return ERROR;
        }

        Date actionStartDateTime = this.validateInputDatetime("actionStartDateTime");
        Date actionFinishDateTime = this.validateInputDatetime("actionFinishDateTime");
        Date attendLimitDateTime = this.validateInputDatetime("attendLimitDateTime");
        if (null == actionStartDateTime || null == actionFinishDateTime || null == attendLimitDateTime) {
            this.addActionLink("返回活动", returnUrl);
            this.addActionError(this.error_msg);
            return ERROR;
        }

        if (DateUtil.compareDateTime(actionStartDateTime, actionFinishDateTime) > 0) {
            this.addActionLink("返回活动", returnUrl);
            this.addActionError("活动开始日期不能大于结束日期。");
            return ERROR;
        }

        if (DateUtil.compareDateTime(attendLimitDateTime, actionFinishDateTime) > 0) {
            this.addActionLink("返回活动", returnUrl);
            this.addActionError("活动报名截止日期不能大于结束日期。");
            return ERROR;
        }

        if (DateUtil.compareDateTime(actionStartDateTime, attendLimitDateTime) > 0) {
            this.addActionLink("返回活动", returnUrl);
            this.addActionError("活动开始日期不能大于活动报名截止日期。");
            return ERROR;
        }

        this.action.setTitle(title);
        this.action.setActionType(actionType);
        this.action.setVisibility(visibility);
        this.action.setDescription(params.getStringParam("actionDescription"));
        this.action.setPlace(params.getStringParam("actionPlace"));
        this.action.setUserLimit(params.safeGetIntParam("userLimit"));
        this.action.setStartDateTime(actionStartDateTime);
        this.action.setFinishDateTime(actionFinishDateTime);
        this.action.setAttendLimitDateTime(attendLimitDateTime);
        this.actionService.saveAction(this.action);
        this.addActionMessage("编辑成功。");
        this.addActionLink("查看本活动", this.returnUrl);
        return SUCCESS;
    }
    private Date validateInputDatetime(String varType) {
        if (varType.equals("actionStartDateTime")) {
            error_msg = "活动开始";
        } else if (varType.equals("actionFinishDateTime")) {
            error_msg = "活动结束";
        } else if (varType.equals("attendLimitDateTime")) {
            error_msg = "活动报名截止";
        } else {
            return null;
        }

        String actionStartDateTimeYMD = params.getStringParam(varType + "YMD");
        int actionStartDateTimeH = params.safeGetIntParam(varType + "H");
        int actionStartDateTimeMM = params.safeGetIntParam(varType + "MM");

        if (actionStartDateTimeH > 23 || actionStartDateTimeH < 0) {
            error_msg = error_msg + "日期的小时数应当在0-23之间。";
            return null;
        }

        if (actionStartDateTimeMM > 59 || actionStartDateTimeMM < 0) {
            error_msg = error_msg + "日期的分钟数应当在0-59之间。";
            return null;
        }

        String strDate = actionStartDateTimeYMD + " " + actionStartDateTimeH + ":" + actionStartDateTimeMM + ":0";
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-M-d H:m:s");
        Date actionStartDateTime = null;
        try {
            actionStartDateTime = sdf.parse(strDate);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return actionStartDateTime;

    }

    public ActionService getActionService() {
        return actionService;
    }
    public void setActionService(ActionService actionService) {
        this.actionService = actionService;
    }
    public FriendService getFriendService() {
        return friendService;
    }
    public void setFriendService(FriendService friendService) {
        this.friendService = friendService;
    }
    public void setUserService(UserService userService) {
        this.userService = userService;
    }
    public GroupService getGroupService() {
        return groupService;
    }
    public void setGroupService(GroupService groupService) {
        this.groupService = groupService;
    }
    public PrepareCourseService getPrepareCourseService() {
        return prepareCourseService;
    }
    public void setPrepareCourseService(PrepareCourseService prepareCourseService) {
        this.prepareCourseService = prepareCourseService;
    }
    public void setAccessControlService(AccessControlService accessControlService) {
        this.accessControlService = accessControlService;
    }

}
