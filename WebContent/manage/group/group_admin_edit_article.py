# coding=utf-8
from cn.edustar.jitar.jython import JythonBaseAction
from cn.edustar.jitar.util import ParamUtil
from cn.edustar.jitar.pojos import GroupMember
from util import Util
from cn.edustar.jitar.util import CommonUtil
from admin_edit_content import AdminArticleEdit
from base_action import *

class group_admin_edit_article(JythonBaseAction, ActionResult, Util, AdminArticleEdit):    
    def __init__(self):
        self.params = ParamUtil(request)
        self.groupService = __jitar__.groupService
        self.group = None
        self.articleService = __spring__.getBean("articleService")
        
    def execute(self):
        if self.loginUser == None:
            return self.LOGIN
        groupId = self.params.safeGetIntParam("groupId")
        self.group = self.groupService.getGroup(groupId)
        if self.group == None:
            self.addActionError(u"无法加载协作组。")
            return self.ERROR
        
        # 当前用户是否可以管理协作组
        if self.canManageGroup() == False:
            self.addActionError(u"您没有管理协作组的权限。")
            return self.ACCESS_DENIED
        
        articleId = self.params.safeGetIntParam("articleId")
        article = self.articleService.getArticle(articleId)
        if article == None:
            self.addActionError(u"无法加载该文章。")
            return self.ERROR
        #检查是否可以管理文章
        groupArticle = self.groupService.getGroupArticleByGroupAndArticle(self.group.groupId, articleId)
        if groupArticle == None:
            self.addActionError(u"该文章不属于该协作组。")
            return self.ERROR
        
        if request.getMethod() == "POST":
            if self.edit_post(article) == False:
                self.addActionError(u"您输入的信息不全，请检查输入。")
                return self.ERROR
            
            self.addActionMessage(u"修改成功。")
            self.addActionLink(u"返回前页", "../groupArticle.action?cmd=list&groupId=" + str(self.group.groupId))
            return self.SUCCESS
        
        self.edit_get(article)        
        return "/WEB-INF/ftl/group/group_admin_edit_article.ftl"
        
    def canManageGroup(self):
        if self.loginUser == None:
            return False
        accessControlService = __spring__.getBean("accessControlService")
        if accessControlService.isSystemAdmin(self.loginUser) == True:
            return True
        
        groupMember = self.groupService.getGroupMemberByGroupIdAndUserId(self.group.groupId, self.loginUser.userId)
        if groupMember == None:
            return False
        role = groupMember.getGroupRole()
        grole = GroupMember.GROUP_ROLE_VICE_MANAGER  
        if role >= grole:
            return True
        return False
