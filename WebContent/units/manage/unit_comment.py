from unit_page import UnitBasePage
from comment_query import CommentQuery
from cn.edustar.jitar.util import ParamUtil

class unit_comment(UnitBasePage):
    def __init__(self):
        UnitBasePage.__init__(self)
        self.comment_svc = __spring__.getBean("commentService")
        
    def execute(self):
        if self.loginUser == None:
            return self.LOGIN
        if self.comment_svc == None:
            self.addActionError(u"无法加载组织机构服务，请检查 applicationContext.xml 配置文件，是否缺少 commentService 节点。")
            return self.ERROR
        self.unit = self.getUnit()
        if self.unit == None:
            self.addActionError(u"您所访问的机构不存在！")
            return self.ERROR
        if self.isContentAdmin() == False:
            self.addActionError(u"你没有管理的权限。")
            return self.ERROR
        
        if request.getMethod() == "POST":
            self.save_post()
            
        self.get_list()
        
        return "/WEB-INF/unitsmanage/unit_comment.ftl"
    
    def get_list(self):
        qry = CommentQuery(""" cmt.id,cmt.content,cmt.audit,cmt.ip,cmt.createDate,cmt.star,cmt.title,cmt.objId,cmt.objType,cmt.userId,u.loginName """)
        qry.unitId = self.unit.unitId
        #qry.commentType = "aboutUser" 
        qry.audit = None
        qry.k= self.params.safeGetStringParam("k")
        qry.f= self.params.safeGetStringParam("f")
        pager = self.params.createPager()
        pager.setPageSize(15)
        pager.totalRows = qry.count()
        pager.itemName = u"评论"
        pager.itemUnit = u"条"
        
        comment_list = qry.query_map(pager)
        request.setAttribute("comment_list",comment_list)
        request.setAttribute("unit",self.unit)
        request.setAttribute("pager",pager)
        request.setAttribute("k",qry.k)
        request.setAttribute("f",qry.f)
        
    def save_post(self):
        cmd = self.params.safeGetStringParam("cmd")
        guids = self.params.safeGetIntValues("guid")
        if cmd == "delete":
            for g in guids:
                cmt = self.comment_svc.getComment(g)
                if cmt != None:
                    self.comment_svc.deleteComment(cmt)