from cn.edustar.jitar.data import *
from cn.edustar.jitar.util import ParamUtil
from placard_query import PlacardQuery

class showPlacardList:
  def execute(self):
    # 得到分页
    param = ParamUtil(request)
    self.pager = param.createPager()
    self.pager.itemName = u"公告"
    self.pager.itemUnit = u"条"
    self.pager.pageSize = 20

    qry = PlacardQuery("""  pld.id, pld.title, pld.createDate """)
    qry.objType = 100
    self.pager.totalRows = qry.count()
    card_list = qry.query_map(self.pager)
    request.setAttribute("card_list", card_list)
    request.setAttribute("pager", self.pager)
    
    return "/WEB-INF/ftl/showPlacardList.ftl"