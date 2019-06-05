package cn.edustar.jitar.action;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import cn.edustar.data.Pager;
import cn.edustar.jitar.pojos.Action;
import cn.edustar.jitar.pojos.ActionReply;
import cn.edustar.jitar.pojos.ActionUser;
import cn.edustar.jitar.pojos.Group;
import cn.edustar.jitar.pojos.GroupMember;
import cn.edustar.jitar.pojos.Subject;
import cn.edustar.jitar.pojos.User;
import cn.edustar.jitar.service.ActionReplyQuery;
import cn.edustar.jitar.service.ActionService;
import cn.edustar.jitar.service.FriendService;
import cn.edustar.jitar.service.GroupService;
import cn.edustar.jitar.service.PrepareCourseService;
import cn.edustar.jitar.util.CommonUtil;
import cn.edustar.jitar.util.DateUtil;

public class SiteShowActionAction extends AbstractBasePageAction {

    class CommentState {
        public Boolean status;
        public String message;
        public CommentState() {
        }
    }
    /**
     * 
     */
    private static final long serialVersionUID = 7873915609462954432L;

    private ActionService actionService;
    private FriendService friendService;
    private GroupService groupService;
    private PrepareCourseService prepareCourseService;
    private String contextPath = "/";

    private Integer actionId = null;
    private Action action = null;
    private User loginUser = null;

    @Override
    protected String execute(String cmd) throws Exception {
        request.setAttribute("head_nav", "actions");
        contextPath = request.getContextPath() + "/";
        actionId = params.getIntParam("actionId");
        if (actionId == null || actionId == 0) {
            this.addActionError("缺少活动标识。");
            this.addActionLink("返回首页", contextPath);
            this.addActionLink("返回活动列表", contextPath + "actions.action");
            return ERROR;
        }

        this.action = this.actionService.getActionById(actionId);
        if (null == action) {
            this.addActionLink("返回活动列表", contextPath + "actions.action");
            this.addActionError("该活动不存在。");
            return ERROR;
        }

        if (this.action.getStatus() != 0 && this.action.getStatus() != 3) {
            this.addActionLink("返回活动列表", contextPath + "actions.action");
            this.addActionError("该活动的状态目前不正常。不能浏览活动信息。");
            return ERROR;
        }

        loginUser = this.getLoginUser();

        if (this.canViewAction() == false) {
            this.addActionLink("返回活动列表", contextPath + "actions.action");
            this.addActionError("该活动为非公开，你无权查看。");
            return ERROR;
        }

        // 更新统计
        this.actionService.updateActionUserStatById(actionId);
        this.getActionReplayList();
        String canAttend = this.canAttendAction();
             
       
        if (cmd.equals("deleteReplay")) {
            int actionReplyId = params.getIntParam("actionReplyId");
            if (this.actionService.canManageAction(this.action, loginUser)) {
                this.actionService.deleteActionReply(actionReplyId);
                response.sendRedirect(this.contextPath + "showAction.action?actionId=" + this.action.getActionId());
                return NONE;
            } else {
                this.addActionLink("返回活动", this.contextPath + "showAction.action?actionId=" + this.action.getActionId());
                this.addActionError("你无权删除活动的回复。需要管理员，协作组管理员，集备创建者，集备主备人才可以执行删除操作。");
                return ERROR;
            }
        } 
        
        if (request.getMethod().equals("POST")) {
            if (cmd.equals("comment")) {
                // #发表评论的条件：
                // # 登录用户，活动成员，或者组内成员
                if (loginUser == null) {
                    this.addActionLink("返回活动", this.contextPath + "showAction.action?actionId=" + this.action.getActionId());
                    this.addActionError("请先登录。");
                    return ERROR;
                }
                CommentState canPublishComment = this.canPublishComment();
                if (canPublishComment.status) {
                    return this.actionComment();
                } else {
                    this.addActionLink("返回活动", this.contextPath + "showAction.action?actionId=" + this.action.getActionId());
                    this.addActionError(canPublishComment.message);
                    return ERROR; //
                }
            } else if (cmd.equals("joinAction")) {
                if (canAttend.equals("1")) {
                    if (this.actionService.userIsInAction(loginUser.getUserId(), this.action.getActionId()) == false) {
                        // #检查参加的人数
                        if (this.action.getUserLimit() != 0) {
                            int uc = this.actionService.getUserCountByActionId(this.action.getActionId());
                            if (uc >= this.action.getUserLimit()) {
                                this.addActionLink("返回活动", this.contextPath + "showAction.action?actionId=" + this.action.getActionId());
                                this.addActionError("此活动人数已满。");
                                return ERROR;
                            }
                        }
                        return this.saveActionUser();
                    } else {
                        this.addActionLink("返回活动", this.contextPath + "showAction.action?actionId=" + this.action.getActionId());
                        this.addActionError("您已经参加了该活动，请勿重复申请。");
                        return ERROR;
                    }
                } else {
                    this.addActionLink("返回活动列表", this.contextPath + "actions.action");
                    this.addActionError("您暂时不能参加该活动。");
                    return ERROR;
                }
            } else {
                this.addActionLink("返回活动", this.contextPath + "showAction.action?actionId=" + this.action.getActionId());
                return this.unknownCommand(cmd);
            }

        }

        // List<ActionUserUnit> user_list =
        // this.actionService.getActionUserWithDistUnit();

        String can_manage = "0";
        if (loginUser == null) {
            can_manage = "0";
        } else {
            if (this.actionService.canManageAction(this.action, loginUser)) {
                can_manage = "1";
            } else {
                can_manage = "0";
            }
        }

        String redUrl = request.getRequestURI();

        if (request.getQueryString() != null) {
            redUrl += '?' + request.getQueryString();
        }
        if (loginUser != null) {
            ActionUser actionUser = this.actionService.getActionUserByUserIdAndActionId(loginUser.getUserId(), actionId);
            request.setAttribute("actionUser", actionUser);
        }
        request.setAttribute("can_manage", can_manage);
        request.setAttribute("can_comment", this.checkUserCanPostCommentAction());
        request.setAttribute("edustar_action", this.action);
        request.setAttribute("loginUser", loginUser);
        request.setAttribute("canAttend", canAttend);
        request.setAttribute("redUrl", redUrl);

        return SUCCESS;
    }

    private String saveActionUser() {
        String attenduserCount = request.getParameter("usernum");
        if (CommonUtil.isInteger(attenduserCount) == false) {
            this.addActionError("活动参加人数不能为非数字。");
            return ERROR;
        }
        ActionUser actionUser = new ActionUser();
        actionUser.setUserId(loginUser.getUserId());
        actionUser.setActionId(this.action.getActionId());
        actionUser.setAttendUserCount(1);
        actionUser.setIsApprove(1);
        actionUser.setStatus(1);
        actionUser.setDescription(request.getParameter("userdesc"));

        this.actionService.addActionUser(actionUser);
        return SUCCESS;
    }

    private String actionComment() {
        if (loginUser == null) {
            this.addActionLink("返回活动", this.contextPath + "showAction.action?actionId=" + this.action.getActionId());
            this.addActionError("请<a href='" + this.contextPath + "login.jsp' style='color:red'>登录</a>发表留言。");
            return ERROR;
        }
        String title = params.getStringParam("title").trim();
        String comment = params.getStringParam("actionComment").trim();
        if (title.length() == 0) {
            this.addActionLink("返回活动", this.contextPath + "showAction.action?actionId=" + this.action.getActionId());
            this.addActionError("请输入讨论标题。");
            return ERROR;
        }
        if (comment.length() == 0) {
            this.addActionLink("返回活动", this.contextPath + "showAction.action?actionId=" + this.action.getActionId());
            this.addActionError("请输入讨论内容。");
            return ERROR;
        }
        ActionReply actionReply = new ActionReply();
        actionReply.setTopic(title);
        actionReply.setContent(comment);
        actionReply.setAddIp(CommonUtil.getClientIP(request));
        actionReply.setUserId(loginUser.getUserId());
        actionReply.setActionId(this.action.getActionId());
        this.actionService.addComment(actionReply);

        // #计算转向页面地址
        int pageSize = params.getIntParam("pageSize");
        int totalRows = params.getIntParam("totalRows") + 1;
        if (pageSize <= 0) {
            pageSize = 10;
        }

        int pageCount = totalRows / pageSize;
        if (totalRows % pageSize != 0) {
            pageCount = pageCount + 1;
        }
        try {
            response.sendRedirect("showAction.action?actionId=" + this.action.getActionId() + "&page=" + pageCount);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return NONE;
    }

    private CommentState canPublishComment() {
        CommentState cs = new CommentState();

        if (loginUser == null) {
            cs.status = false;
            cs.message = "您没有登录。";
            return cs;
        }
        // #先检查是否在活动人员列表里面，列表优先
        ActionUser actionUser = this.actionService.getActionUserByUserIdAndActionId(loginUser.getUserId(), this.action.getActionId());
        if (actionUser != null) {
            if (actionUser.getStatus() == 1) {
                cs.status = true;
                cs.message = "";
            } else {
                String[] arr = new String[]{"未回复", "已参加", "已退出", "已请假"};
                cs.status = false;
                cs.message = "你不能进行评论，您的状态是：" + arr[actionUser.getStatus()];
            }
            return cs;
        }
        if (this.action.getOwnerType().equals("user")) {
            if(this.action.getVisibility() == 0 && this.action.getStatus() == 0){
                cs.status = true;
                cs.message = "";
                return cs;
            }
            // # 得到创建者的好友列表
            Boolean isUserFriend = this.friendService.isUserFriend(loginUser.getUserId(), this.action.getCreateUserId());
            if (isUserFriend) {
                cs.status = true;
                cs.message = "";
            } else {
                cs.status = false;
                cs.message = "该活动是个人活动，但您不是该活动创建者的好友。";
            }
            return cs;
        } else if (this.action.getOwnerType().equals("group")) {
            // # 得到群组信息
            Group group = this.groupService.getGroup(this.action.getOwnerId());
            if (group == null) {
                cs.status = false;
                cs.message = "该活动是协作组活动，但无法加载协作组信息。";
                return cs;
            }
            GroupMember group_member = this.groupService.getGroupMemberByGroupIdAndUserId(group.getGroupId(), loginUser.getUserId());
            if (group_member == null) {
                cs.status = false;
                cs.message = "该活动是协作组活动，但无您不是该协作组成员。";
                return cs;
            } else {
                if (group_member.getStatus() == 0) {
                    cs.status = true;
                    cs.message = "";
                } else {
                    String[] arr = new String[]{"正常可用状态", "申请后待审核", "待删除", "锁定", "邀请未回应"};
                    cs.status = false;
                    cs.message = "该活动是协作组活动，您是该协作组成员，但成员状态处于" + arr[group_member.getStatus()] + "。";

                }
                return cs;
            }
        }

        else if (this.action.getOwnerType().equals("preparecourse")) {
            // # 集体备课
            if (this.prepareCourseService.checkUserInPreCourse(this.action.getOwnerId(), loginUser.getUserId())) {
                cs.status = true;
                cs.message = "";
            } else {
                cs.status = false;
                cs.message = "该活动是集体备课活动，但您不在该集备成员里面或者状态未审核。";
            }
            return cs;
        }

        else if (this.action.getOwnerType().equals("subject")) {
            // # 学科
            Subject subject = this.subjectService.getSubjectById(this.action.getOwnerId());
            if (subject == null) {
                cs.status = false;
                cs.message = "该活动是学科活动，但无法加载该学科信息。";
                return cs;
            }
            if (this.subjectService.checkUserInSubject(loginUser, subject.getSubjectId())) {
                cs.status = true;
                cs.message = "";
            } else {
                cs.status = false;
                cs.message = "该活动是学科活动，但您不属于该学科。";

            }
            return cs;
        }
        cs.status = false;
        cs.message = "无法进行判断您在该活动中的状态。";

        return cs;
    }

    private String checkUserCanPostCommentAction() {
        if (loginUser == null) {
            return "0";
        }
        if (this.action.getStatus() != 0) {
            return "0";
        }
        if (loginUser.getUserId() == this.action.getCreateUserId()) {
            return "1";
        }
        if (this.checkActionUser()) {
            return "1";
        } else {
            return "0";
        }
    }

    private String canAttendAction() {
        int usercount = this.actionService.getUserCountByActionId(this.action.getActionId());
        request.setAttribute("usercount", usercount);

        // # 活动如果报名日期截止了，不能再参加,当前日期大于设置的报名截止日期
        if (DateUtil.compareDateTime(new Date(), this.action.getAttendLimitDateTime()) > -1) {
            request.setAttribute("isDeadLimit", "1");
            // #print "已经超过活动截止时间"
            return "0";
        }
        // # 活动状态不正常，不能参加
        if (this.action.getStatus() != 0) {
            return "0";
        }

        // # 当前用户没有登录，不能参加
        if (loginUser == null) {
            return "0";
        }

        // # 活动如果是不是公开的，不能参加，只能邀请
        if (this.action.getVisibility() != 0) {
            return "0";
        }

        // # 如果只能邀请，用户也不能参加
        if (this.action.getActionType() == 2) {
            return "0";
        } else if (this.action.getActionType() == 0) {// : #任意参加
            if (this.action.getUserLimit() == 0) {
                return "1";
            } else {
                if (usercount < this.action.getUserLimit()) {
                    return "1";
                } else {
                    return "0";
                }
            }
        } else if (this.action.getActionType() == 1) {// : #组内成员参加
            if (this.checkActionUser()) {
                return "1";
            } else {
                return "0";
            }
        } else {
            return "0";
        }

    }

    @SuppressWarnings("rawtypes")
    private void getActionReplayList() {
        Pager pager = this.params.createPager();
        ActionReplyQuery qry = new ActionReplyQuery(" actr.actionReplyId, actr.createDate, actr.topic, actr.content, actr.userId ");
        qry.actionId = this.action.getActionId();
        pager.setPageSize(6);
        pager.setItemNameAndUnit("回复", "个");
        pager.setTotalRows(qry.count());
        List action_reply_list = (List) qry.query_map(pager);
        if (action_reply_list.size() > 0) {
            request.setAttribute("action_reply_list", action_reply_list);
        }
        request.setAttribute("pager", pager);

    }

    private boolean canViewAction() {
        if (this.action.getVisibility() == 1) {
            return this.checkActionUser();
        } else {
            return true;
        }
    }

    private Boolean checkActionUser() {
        if (loginUser == null) {
            return false;
        }
        // #先检查是否在活动人员列表里面
        if (this.actionService.userIsInAction(loginUser.getUserId(), this.action.getActionId()) == true) {
            return true;
        }
        // 判断各种类型的活动

        if (this.action.getOwnerType().equals("user")) {
            if(this.action.getVisibility() == 0 && this.action.getStatus() == 0){
                return true;
            }
            // # 得到创建者的好友列表
            Boolean isUserFriend = this.friendService.isUserFriend(loginUser.getUserId(), this.action.getCreateUserId());
            if (isUserFriend) {
                return true;
            } else {
                return false;
            }

        } else if (this.action.getOwnerType().equals("group")) {
            // # 得到群组信息
            Group group = this.groupService.getGroup(action.getOwnerId());
            if (group == null) {
                return false;
            }
            GroupMember group_member = this.groupService.getGroupMemberByGroupIdAndUserId(group.getGroupId(), loginUser.getUserId());

            if (group_member == null) {
                return false;
            } else {
                return true;
            }
        }
        // # 集体备课
        else if (this.action.getOwnerType().equals("preparecourse")) {
            if (this.prepareCourseService.checkUserInPreCourse(this.action.getOwnerId(), loginUser.getUserId())) {
                return true;
            } else {
                return false;
            }

        }
        // # 学科
        else if (this.action.getOwnerType().equals("subject")) {
            Subject subject = this.subjectService.getSubjectById(this.action.getOwnerId());
            if (subject == null) {
                return false;
            }

            if (this.subjectService.checkUserInSubject(loginUser, subject.getSubjectId())) {
                return true;
            } else {
                return false;
            }
        }
        return false;
    }

    public void setActionService(ActionService actionService) {
        this.actionService = actionService;
    }

    public void setFriendService(FriendService friendService) {
        this.friendService = friendService;
    }

    public void setGroupService(GroupService groupService) {
        this.groupService = groupService;
    }

    public void setPrepareCourseService(PrepareCourseService prepareCourseService) {
        this.prepareCourseService = prepareCourseService;
    }

}
