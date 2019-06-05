from base_action import ActionExecutor,ActionResult
from cn.edustar.jitar.pojos import Page
from cn.edustar.jitar.util import ParamUtil

class gen_user_html(ActionExecutor):
    def __init__(self):
        self.params = ParamUtil(request)
        self.page_svc = __spring__.getBean("pageService")
        self.login_user = self.getLoginUser()

    def execute(self):
        if self.loginUser == None:
            response.sendRedirect("../login.jsp")
            return None
        page = self.page_svc.getUserIndexPage(self.login_user)
        if page == None:
            self.addActionError(u"没有找到您的首页。")
            return ActionResult.ERROR
        html = self.params.safeGetStringParam("html")        
        userHtmlService = __spring__.getBean("userHtmlService")
        msg = ""
        if html == "index":
            userHtmlService.GenUserIndex(self.loginUser)
            msg = u"个人空间首页生成完毕!"
        elif html == "article":
            userHtmlService.GenUserArticleList(self.loginUser)
            msg = u"文章列表页面生成完毕!"
        elif html == "profile":
            userHtmlService.GenUserProfile(self.loginUser)
            msg = u"个人档案页面生成完毕!"
        elif html == "resource":
            userHtmlService.GenUserResourceList(self.loginUser)
            msg = u"资源列表页面生成完毕!"
        elif html == "photo":
            userHtmlService.GenUserPhotoList(self.loginUser)
            msg = u"图片列表页面生成完毕!"
        elif html == "createaction":
            userHtmlService.GenUserCreateActionList(self.loginUser)
            msg = u"我创建的活动列表页面生成完毕!"
        elif html == "joinedaction":
            userHtmlService.GenUserJoinedActionList(self.loginUser)
            msg = u"我参与的活动列表页面生成完毕!"
        elif html == "createpreparecourse":
            userHtmlService.GenUserCreatePrepareCourseList(self.loginUser)
            msg = u"我发起的集备列表页面生成完毕!"
        elif html == "joinedparecourse":
            userHtmlService.GenUserJoinedPrepareCourseList(self.loginUser)
            msg = u"我参与的集备列表页面生成完毕!"            
        elif html == "question":
            userHtmlService.GenUserQuestionList(self.loginUser)
            msg = u"问题与解答列表页面生成完毕!"            
        elif html == "vote":
            userHtmlService.GenUserVoteList(self.loginUser)
            msg = u"调查投票列表页面生成完毕!"            
        elif html == "joinedgroup":
            userHtmlService.GenUserGroupList(self.loginUser)
            msg = u"我加入的协作组生成完毕!"            
        elif html == "leaveword":
            userHtmlService.GenUserLeaveWordList(self.loginUser)
            msg = u"用户留言页面生成完毕!"            
        elif html == "friendlink":
            userHtmlService.GenUserFriendList(self.loginUser)
            msg = u"我的好友列表页面生成完毕!"            
        elif html == "topic":
            userHtmlService.GenUserSpecialTopic(self.loginUser)
            msg = u"专题讨论页面生成完毕!"            
        else:
            self.addActionMessage(msg)            
        self.addActionMessage(msg)
        return ActionResult.SUCCESS