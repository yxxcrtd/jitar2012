from cn.edustar.jitar.jython import BaseAdminAction
from cn.edustar.jitar.util import ParamUtil, CommonUtil
from base_action import ActionResult
from cn.edustar.jitar.data import Command
from java.util import ArrayList
from java.util import HashMap
from java.io import File, FileWriter, FileOutputStream, OutputStreamWriter
from cn.edustar.jitar import JitarRequestContext

class gen_site_html(BaseAdminAction, ActionResult):
    def __init__(self):
        self.params = ParamUtil(request)        
        
    def execute(self):
        if self.loginUser == None:
            return ActionResult.LOGIN
        accessControlService = __spring__.getBean("accessControlService")
        if accessControlService.isSystemAdmin(self.loginUser) == False:
            self.addActionError(u"没有管理站点配置的权限，只有超级管理员才能进行管理。")
            return ActionResult.ERROR
        
        html = self.params.safeGetStringParam("html")
        htmlGeneratorService = __spring__.getBean("htmlGeneratorService")
        msg = ""
        if html == "index":
            htmlGeneratorService.SiteIndex()
            msg = u"网站首页生成完毕!"
        elif html == "subject":
            self.GenSubjectNav()
            msg = u"学科导航文件生成完毕!"
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
    
    def GenSubjectNav(self):
        subjectService = __spring__.getBean("subjectService")      
        mGradeId = subjectService.getGradeIdList()
        MetaGrade = ArrayList()
        metaSubject = ArrayList()
        for grade in mGradeId:
            mGrade = subjectService.getGrade(int(grade))
            MetaGrade.add(mGrade)
            subj = subjectService.getSubjectByGradeId(int(grade))
            m = ArrayList()
            if subj != None:
                for sj in range(0, subj.size()):
                    m.add(subj[sj].metaSubject)
                metaSubject.add({"gradeName" : mGrade.gradeName, "gradeId" : grade, "metaSubject" : m })
        map = HashMap()
        map.put("metaGrade", MetaGrade)
        map.put("meta_Grade", MetaGrade)
        map.put("SubjectNav", metaSubject)
        map.put("SiteUrl", CommonUtil.getContextUrl(request))
        
        templateProcessor = __spring__.getBean("templateProcessor")
        str = templateProcessor.processTemplate(map, "/WEB-INF/ftl/site_subject_nav.ftl", "utf-8")
        rootPath = JitarRequestContext.getRequestContext().getServletContext().getRealPath("/")
        fileIndex = rootPath + "html" + File.separator + "subject_nav.html"
        try:
            file = File(fileIndex)
            fw = OutputStreamWriter(FileOutputStream(file), "utf-8")
            # fw = FileWriter(file, False)
            fw.flush()
            fw.write(str)
            fw.close()
        finally:
            file = None
            fw = None
