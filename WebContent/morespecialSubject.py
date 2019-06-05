from site_config import SiteConfig
from cn.edustar.jitar.util import ParamUtil
from base_specialsubject_page import *
from base_action import *

class morespecialSubject(ActionExecutor):
    def __init__(self):
        self.params = ParamUtil(request)
    
    def execute(self):

        response.contentType = "text/html; charset=UTF-8"
        
        pager = self.params.createPager()
        pager.itemName = u"专题"
        pager.itemUnit = u"个"
        pager.pageSize = 20
        qry = SpecialSubjectQuery(""" ss.specialSubjectId, ss.title, ss.logo, ss.description, ss.createDate, ss.expiresDate, ss.objectType, ss.objectId """)
        pager.totalRows = qry.count()
        specialsubject_list = qry.query_map(pager)
        request.setAttribute("specialsubject_list", specialsubject_list)
        request.setAttribute("pager", pager)
        return "/WEB-INF/ftl/morespecialSubject.ftl"
