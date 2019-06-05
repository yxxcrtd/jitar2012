package cn.edustar.jitar.action;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import cn.edustar.jitar.pojos.Action;
import cn.edustar.jitar.pojos.ActionUser;
import cn.edustar.jitar.pojos.User;
import cn.edustar.jitar.service.AccessControlService;
import cn.edustar.jitar.service.ActionService;
import cn.edustar.jitar.service.FriendService;
import cn.edustar.jitar.service.GroupService;
import cn.edustar.jitar.service.PrepareCourseService;
import cn.edustar.jitar.service.UserService;
import cn.edustar.jitar.util.DateUtil;

public class SiteActionCreateAction extends AbstractBasePageAction {

    /**
     * 
     */
    private static final long serialVersionUID = -6434956656544317163L;
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
    private int ownerId = 0;
    private String ownerType = null;

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

        ownerId = params.getIntParam("ownerId");
        ownerType = params.getStringParam("ownerType");

        if (ownerId == 0) {
            this.addActionError("缺少类别标识。");
            this.addActionLink("返回首页", contextPath);
            this.addActionLink("返回活动列表", contextPath + "actions.action");
            return ERROR;
        }
        if (ownerType == null || ownerType.trim().length() == 0) {
            this.addActionError("缺少类别名称。");
            this.addActionLink("返回首页", contextPath);
            this.addActionLink("返回活动列表", contextPath + "actions.action");
            return ERROR;
        }
        // #限定活动的范围，
        if (!ownerType.equals("user") && !ownerType.equals("group") && !ownerType.equals("preparecourse") && !ownerType.equals("subject")) {
            this.addActionError("活动类别必须是个人、协作组、集备、学科。");
            this.addActionLink("返回首页", contextPath);
            this.addActionLink("返回活动列表", contextPath + "actions.action");
            return ERROR;
        }
        request.setAttribute("ownerId", this.ownerId);
        request.setAttribute("ownerType", this.ownerType);

        if (request.getMethod().equals("POST")) {
            return this.actionPost();
        } else {
            return this.actionGet();
        }

    }

    private String actionGet() {
        return "input";
    }

    private String actionPost() {
        String title = params.getStringParam("actionName");
        if (title.trim().length() == 0) {
            this.addActionError("请输入标题。");
            this.addActionLink("返回", contextPath + "createAction.action?ownerId=" + this.ownerId + "&ownerType=" + this.ownerType);
            this.addActionLink("返回活动列表", contextPath + "actions.action");
            return ERROR;
        }
        String actionDescription = params.getStringParam("actionDescription");
        if (actionDescription.trim().length() == 0) {
            this.addActionError("请输入活动描述。");
            this.addActionLink("返回", contextPath + "createAction.action?ownerId=" + this.ownerId + "&ownerType=" + this.ownerType);
            this.addActionLink("返回活动列表", contextPath + "actions.action");
            return ERROR;
        }

        int actionType = params.getIntParam("actionType");
        int visibility = params.getIntParam("actionVisibility");
        int userLimit = params.getIntParam("actionUserLimit");
        Date actionStartDateTime = this.validateInputDatetime("actionStartDateTime");

        if (null == actionStartDateTime) {
            this.addActionLink("返回", contextPath + "createAction.action?ownerId=" + this.ownerId + "&ownerType=" + this.ownerType);
            this.addActionError(this.error_msg);
            return ERROR;
        }
        Date actionFinishDateTime = this.validateInputDatetime("actionFinishDateTime");
        if (null == actionFinishDateTime) {
            this.addActionLink("返回", contextPath + "createAction.action?ownerId=" + this.ownerId + "&ownerType=" + this.ownerType);
            this.addActionError(this.error_msg);
            return ERROR;
        }

        Date attendLimitDateTime = this.validateInputDatetime("attendLimitDateTime");
        if (null == attendLimitDateTime) {
            this.addActionLink("返回", contextPath + "createAction.action?ownerId=" + this.ownerId + "&ownerType=" + this.ownerType);
            this.addActionError(this.error_msg);
            return ERROR;
        }

        if (DateUtil.compareDateTime(actionStartDateTime, actionFinishDateTime) > 0) {
            this.addActionLink("返回", contextPath + "createAction.action?ownerId=" + this.ownerId + "&ownerType=" + this.ownerType);
            this.addActionError("活动开始日期不能大于结束日期。");
            return ERROR;
        }

        if (DateUtil.compareDateTime(attendLimitDateTime, actionFinishDateTime) > 0) {
            this.addActionLink("返回", contextPath + "createAction.action?ownerId=" + this.ownerId + "&ownerType=" + this.ownerType);
            this.addActionError("活动报名截止日期不能大于结束日期。");
            return ERROR;
        }

        if (DateUtil.compareDateTime(actionStartDateTime, attendLimitDateTime) > 0) {
            this.addActionLink("返回", contextPath + "createAction.action?ownerId=" + this.ownerId + "&ownerType=" + this.ownerType);
            this.addActionError("活动开始日期不能大于活动报名截止日期。");
            return ERROR;
        }

        action = new Action();
        action.setTitle(title);
        action.setOwnerId(ownerId);
        action.setOwnerType(ownerType);
        action.setCreateUserId(loginUser.getUserId());
        action.setActionType(actionType);
        action.setVisibility(visibility);
        action.setDescription(actionDescription);
        action.setPlace(params.getStringParam("actionPlace"));
        action.setUserLimit(userLimit);
        action.setStartDateTime(actionStartDateTime);
        action.setFinishDateTime(actionFinishDateTime);
        action.setAttendLimitDateTime(attendLimitDateTime);
        this.actionService.addAction(action);
        this.returnUrl = "showAction.action?actionId=" + action.getActionId();

        // # 将创建者插入到活动用户表内
        ActionUser actionUser = new ActionUser();
        actionUser.setActionId(action.getActionId());
        actionUser.setUserId(loginUser.getUserId());
        actionUser.setAttendUserCount(1);
        actionUser.setIsApprove(1);
        actionUser.setStatus(1);
        this.actionService.addActionUser(actionUser);

        // #插入活动的用户表
        return addInviteUser();
    }
    private String addInviteUser() {
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
            this.addActionLink("返回活动列表", contextPath + "actions.action");
            return SUCCESS;
        }

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
        if (actionStartDateTimeYMD == null || actionStartDateTimeYMD.length() == 0) {
            error_msg = "请输入" + error_msg + "日期。";
            return null;
        }
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
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd H:m:s");
        Date actionStartDateTime = null;
        try {
            actionStartDateTime = sdf.parse(strDate);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return actionStartDateTime;

    }

    public void setActionService(ActionService actionService) {
        this.actionService = actionService;
    }

    public void setFriendService(FriendService friendService) {
        this.friendService = friendService;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public void setGroupService(GroupService groupService) {
        this.groupService = groupService;
    }

    public void setPrepareCourseService(PrepareCourseService prepareCourseService) {
        this.prepareCourseService = prepareCourseService;
    }

    public void setAccessControlService(AccessControlService accessControlService) {
        this.accessControlService = accessControlService;
    }

}
