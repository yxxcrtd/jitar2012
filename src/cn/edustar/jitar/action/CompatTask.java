package cn.edustar.jitar.action;

import cn.edustar.jitar.pojos.Group;
import cn.edustar.jitar.pojos.GroupMember;
import cn.edustar.jitar.pojos.User;
import cn.edustar.jitar.service.AccessControlService;
import cn.edustar.jitar.service.impl.JitarContextImpl;

/**
 * 提供给 Struts, Python action 一些公共任务
 */
public class CompatTask {

    /**
     * 判断能否访问指定用户的工作室及其子页面
     * 
     * @param user
     * @return 返回'true'表示能够显示，返回'false'表示由于种种原因不能显示
     */
    public static boolean canVisitUser(User user, ActionInterface ai) {
        // 用户必须存在
        if (user == null) {
            ai.addActionError("指定的用户不存在！");
            return false;
        }

        // 根据用户状态决定
        switch (user.getUserStatus()) {
            case User.USER_STATUS_NORMAL : // 状态正常，可以访问其页面
                return true;
            case User.USER_STATUS_WAIT_AUTID : // 待审核的用户不能访问
                ai.addActionError("用户：" + user.getNickName() + "，尚未审核！");
                return false;
            case User.USER_STATUS_DELETED : // 被删除的用户不能访问
                ai.addActionError("用户：" + user.getNickName() + "，已被删除！");
                return false;
            case User.USER_STATUS_LOCKED : // 被锁定的用户可以访问
                return true;
            default : // 其它状态非法或暂不支持
                ai.addActionError("用户：" + user.getNickName() + "，状态非法！");
                return false;
        }
    }

    /**
     * 判断用户能否在个人面板中更改信息，被锁定的用户不能更改自己的任何信息
     * 
     * @param user
     * @param ai
     * @return
     */
    public static boolean canManageBlog(User user, ActionInterface ai) {
        // 断言。(如果为假，则抛出：java.lang.AssertionError)
        assert canVisitUser(user, ai) == true;

        switch (user.getUserStatus()) { // 状态正常的用户才能管理自己的工作室，被锁定的用户不能管理自己的工作室，只能浏览
            case User.USER_STATUS_NORMAL :
                return true;
            case User.USER_STATUS_WAIT_AUTID : // 待审核的不能管理内容
                ai.addActionError("用户：" + user.getNickName() + "，尚未审核！");
                return false;
            case User.USER_STATUS_DELETED : // 被删除的不能管理内容
                ai.addActionError("用户：" + user.getNickName() + "，已被删除！");
                return false;
            case User.USER_STATUS_LOCKED : // 被锁定的不能管理内容
                ai.addActionError("用户：" + user.getNickName() + "，已被锁定！");
                return false;
            default : // 状态非法，不能管理内容
                ai.addActionError("用户：" + user.getNickName() + "，状态非法！");
                return false;
        }
    }

    /**
     * 判断能否访问指定的协作组
     * 
     * @param group
     * @param ai
     * @return
     */
    public static boolean canVisitGroup(Group group, ActionInterface ai) {
        // 协作组必须存在
        if (group == null) {
            ai.addActionError("您访问的协作组不存在！");
            return false;
        }

        // 状态判断
        switch (group.getGroupState()) {
            case Group.GROUP_STATE_NORMAL : // 状态正常，则能访问
                return true;
            case Group.GROUP_STATE_WAIT_AUDIT : // 待审核的不能访问
                ai.addActionError("协作组：" + group.getGroupTitle() + "，尚未审核！");
                return false;
            case Group.GROUP_STATE_LOCKED : // 被锁定的能够访问
                return true;
            case Group.GROUP_STATE_DELETED : // 被删除的不能访问
                ai.addActionError("协作组：" + group.getGroupTitle() + "，已被删除！");
                return false;
            case Group.GROUP_STATE_HIDED : // 被隐藏的协作组则只有成员才能访问
                return true;
            default : // 状态非法，不能访问
                ai.addActionError("协作组：" + group.getGroupTitle() + "，状态非法！");
                return false;
        }
    }

    /**
     * 判断指定用户能否进入指定的协作组
     * 
     * @param group
     * @param user
     * @param gm
     * @param ai
     * @return
     */
    public static boolean canEnterGroup(Group group, User user, GroupMember gm, ActionInterface ai) {
        if (canVisitGroup(group, ai) == false) {
            return false;
        }

        // 用户和协作组成员身份必须存在
        if (user == null || gm == null) {
            ai.addActionError("只有协作组成员才能进入协作组！");
            return false;
        }

        // 用户状态必须合法
        if (isUserStatusValid(user, ai) == false) {
            return false;
        }

        // 协作组成员状态必须合法
        switch (gm.getStatus()) {
            case GroupMember.STATUS_NORMAL : // 状态正常
                return true;
            case GroupMember.STATUS_WAIT_AUDIT : // 等待审核不能进入
                ai.addActionError("用户在协作组：" + group.getGroupTitle() + "，尚未审核，不能进入协作组！");
                return false;
            case GroupMember.STATUS_DELETING : // 被删除不能进入
                ai.addActionError("该用户已经从协作组 " + group.getGroupTitle() + " 中删除，不能进入协作组！");
                return false;
            case GroupMember.STATUS_LOCKED : // 被锁定可以进入
                return true;
            case GroupMember.STATUS_INVITING : // 没有被审核的不能进入
                ai.addActionError("用户被邀请加入协作组：" + group.getGroupTitle() + "，但尚未审核，不能进入协作组！");
                return false;
            default : // 状态非法，不能访问
                ai.addActionError("用户在协作组：" + group.getGroupTitle() + "，中的状态非法，不能进入协作组！");
                return false;
        }
    }

    /**
     * 判断指定用户能否给指定协作组中发表内容，必须在此之前调用过：canEnterGroup()
     * 
     * @param group
     * @param user
     * @param gm
     * @param ai
     * @return
     */
    public static boolean canPubToGroup(Group group, User user, GroupMember gm, ActionInterface ai) {
        assert canEnterGroup(group, user, gm, ai);

        // 只有成员状态正常的才能发布内容到协作组
        switch (gm.getStatus()) {
            case GroupMember.STATUS_NORMAL :
                return true;
            default :
                return false;
        }
    }

    /**
     * 判断用户能否进行管理操作，
     * 
     * @param user
     * @param ai
     * @return
     */
    public static boolean canAdmin(User user, ActionInterface ai) {
        // 必须是登录用户
        if (user == null) {
            ai.addActionError("没有登录！");
            return false;
        }
        // 网站级内容管理员
        AccessControlService acs = (AccessControlService) JitarContextImpl.getCurrentJitarContext().getSpringContext().getBean("accessControlService");

      

        if (acs == null) {
            ai.addActionError("没有管理的权限！");
            return false;
        }
        if (acs.isSystemAdmin(user) || acs.isSystemContentAdmin(user)) {
            return true;
        }

        // 其它人不能管理
        ai.addActionError("没有管理的权限！");
        return false;
    }

    /**
     * 判断用户的状态是否合法
     * 
     * @param user
     * @param ai
     * @return
     */
    private static boolean isUserStatusValid(User user, ActionInterface ai) {
        switch (user.getUserStatus()) {
            case User.USER_STATUS_NORMAL : // 状态正常
                return true;
            case User.USER_STATUS_WAIT_AUTID : // 待审核的用户不能访问
                ai.addActionError("用户：" + user.getNickName() + "，尚未审核！");
                return false;
            case User.USER_STATUS_DELETED : // 被删除的用户不能访问
                ai.addActionError("用户：" + user.getNickName() + "，已被删除！");
                return false;
            case User.USER_STATUS_LOCKED : // 被锁定的用户可以访问
                return true;
            default : // 其它状态非法
                ai.addActionError("用户：" + user.getNickName() + "，状态非法！");
                return false;
        }
    }

}
