from cn.edustar.jitar.pojos import Resource
from cn.edustar.jitar.util import ParamUtil, EncryptDecrypt
from cn.edustar.jitar.data import Command
from cn.edustar.jitar import JitarContext
from resource_query import ResourceQuery
from cn.edustar.jitar.model import ObjectType
from base_action import BaseAction, ActionResult, SubjectMixiner
from comment_query import CommentQuery

class showResource(BaseAction, SubjectMixiner):
    def __init__(self):
        self.params = ParamUtil(request)
        self.cmt_svc = __jitar__.commentService
        self.pun_svc = __jitar__.UPunishScoreService
        return
    
    def execute(self):
        q = request.getQueryString()
        response.sendRedirect(request.getContextPath() + "/showResource.action?" + q)
        
        param = ParamUtil(request)
        resourceId = param.getIntParam("resourceId")
        shareMode = param.getIntParam("shareMode")
        if resourceId == None or resourceId == "":
            request.setAttribute("error", u"无效的标识。")
            return "/WEB-INF/ftl/show_resource.ftl"
        self.res_svc = __jitar__.getResourceService()
        
        # 得到的信息不全啊
        resource = self.res_svc.getResource(resourceId)
        if resource == None:
            request.setAttribute("error", u"无法加载该资源。")
            return "/WEB-INF/ftl/show_resource.ftl"
        
        punshScore= self.pun_svc.getUPunishScore(ObjectType.OBJECT_TYPE_RESOURCE.getTypeId() , resourceId)
        if punshScore!=None:
            if punshScore.getScore()<0 :
                request.setAttribute("scoreCreateUserId", punshScore.getCreateUserId())
                request.setAttribute("scoreCreateUserName", punshScore.getCreateUserName())
                request.setAttribute("score", -1*punshScore.getScore())
                request.setAttribute("scoreReason", punshScore.getReason())
                request.setAttribute("scoreDate", punshScore.getPunishDate())
                request.setAttribute("scoreObjId", punshScore.getObjId())
                request.setAttribute("scoreObjTitle", punshScore.getObjTitle())
                    
        qry = ResourceQuery(""" r.resourceId, r.title, r.href, r.createDate, r.fsize, r.downloadCount, 
                                r.author, r.publisher, r.summary, u.loginName, r.subjectId, 
                                grad.gradeName, sc.name as scName, r.shareMode,r.userId,r.recommendState """)
        
        # 如果是管理者或者作者自己，则可以浏览        
        if self.loginUser != None:
            accessControlService = __spring__.getBean("accessControlService")
            isAdmin = False
            adminList = accessControlService.getAllAccessControlByUser(self.loginUser)
            if len(adminList) > 0:
                isAdmin = True
            
            if self.loginUser.userId == resource.userId or accessControlService.isSystemAdmin(self.loginUser) or isAdmin == True:
                qry.auditState = None
                qry.delState = None
            else:
                if resource.auditState == True or resource.delState == True:
                    request.setAttribute("error", u"该资源未审核或者待删除。")
                    return "/WEB-INF/ftl/show_resource.ftl"
        else:
            if resource.auditState == True or resource.delState == True:
                request.setAttribute("error", u"该资源未审核或者待删除。")
                return "/WEB-INF/ftl/show_resource.ftl"            
        
        qry.resourceId = resource.resourceId
        qry.shareMode = resource.shareMode
        new_resource_list = qry.query_map(1)
        # print "====", new_resource_list
        
        if(len(new_resource_list) > 0):            
            request.setAttribute("resource", new_resource_list[0])
        else:
            return
        res = new_resource_list[0]
        query = AdminCommentQuery(" cmt.id, cmt.title, cmt.content, cmt.audit, cmt.createDate, cmt.userId, cmt.userName, cmt.star, cmt.objType, cmt.objId, u.userIcon, u.loginName, u.trueName ")
        query.objType = 12
        query.objId = resource.resourceId
        query.audit = 1
        # 调用分页函数.
        pager = self.createPager()
        pager.totalRows = query.count()
        commentList = query.query_map(pager)
        request.setAttribute("comment_list", commentList)
        request.setAttribute("pager", pager)        
        
        conf = JitarContext.getCurrentJitarContext().getConfigService().getConfigure() 
        largefileupload = conf.getValue("resource.download")
        if(largefileupload == 1):
            request.setAttribute("resourcedowload", "true")
        else:            
            if self.loginUser == None:
                if self.getMashupUserCookie() == "":
                    request.setAttribute("resourcedowload", "false")
                else:
                    request.setAttribute("resourcedowload", "true")
            else:
                request.setAttribute("resourcedowload", "true")
            
        response.setContentType("text/html; charset=UTF-8")
        
        # 增加资源的访问量
        self.res_svc.increaseViewCount(resourceId) 
        if self.getImpersonateUser() != None:
            request.setAttribute("resourcedowload", "true")
        filename = res["href"]
        if filename != None and filename != "":
            filename = filename.lower()
            canPreview = False
            if filename.endswith(".jpg") or filename.endswith(".gif") or filename.endswith(".png") or filename.endswith(".txt"):
                canPreview = True
            elif filename.endswith(".doc") or filename.endswith(".docx") or filename.endswith(".swf") or filename.endswith(".pdf"):# or filename.endswith(".xls") or filename.endswith(".xlsx") or filename.endswith(".ppt") or filename.endswith(".pptx"):
                PDF2SWFPath = request.getSession().getServletContext().getInitParameter("pdf2swfPath")
                if PDF2SWFPath != None and PDF2SWFPath != "":
                    canPreview = True
                prepareCourseFileServer = request.getSession().getServletContext().getInitParameter("PrepareCourseFileServer")
                if prepareCourseFileServer != None and prepareCourseFileServer != "": canPreview = False
            if canPreview:
                request.setAttribute("resourceId", resource.resourceId)
                request.setAttribute("canPreview", "true")
             
        return "/WEB-INF/ftl/show_resource.ftl"
      
      
    # 创建并返回一个分页对象
    def createPager(self):
        # 调用Java的函数
        pager = self.params.createPager()
        pager.itemName = u"评论"
        pager.itemUnit = u"个"
        pager.pageSize = 20
        return pager
    
    def getMashupUserCookie(self):
        cookie = ""
        cookies = request.getCookies()
        if cookies == None:
            return cookie
        for c in cookies:
            if c.getName() == "mashup":
                 cookie = c.getValue()
        return cookie
# 资源带高级过滤条件的搜索.
class AdminCommentQuery (CommentQuery):
    def __init__(self, selectFields):
        CommentQuery.__init__(self, selectFields)
        self.kk = None  # 查询关键字.
        self.f = 0  # kk 查询哪个字段.
    
    def applyWhereCondition(self, qctx):
        CommentQuery.applyWhereCondition(self, qctx)
        if self.kk != None and self.kk != '':
           self._applyKeywordFilter(qctx)
  
    def _applyKeywordFilter(self, qctx):
        newKey = self.kk.replace("'", "''").replace("%", "[%]").replace("_", "[_]").replace("[", "[[]")
        if self.f == 'title':  # 用资源标题、标签过滤.
            qctx.addAndWhere('cmt.title LIKE :kk')
            qctx.setString('kk', '%' + newKey + '%')
        elif self.f == 'content': 
            qctx.addAndWhere('cmt.content LIKE :kk')
            qctx.setString('kk', '%' + newKey + '%')
        elif self.f == 'uname':  # 用户名 (maybe id).
            try:
                userId = int(newKey)
                qctx.addAndWhere('cmt.userId = :kk')
                qctx.setInteger('kk', userId)
            except:
                qctx.addAndWhere('u.loginName = :kk OR u.trueName = :kk')
                qctx.setString('kk', newKey)
