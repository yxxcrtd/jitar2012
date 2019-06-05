#encoding=utf-8
from java.text import SimpleDateFormat
from java.util import HashMap
from common_data import CommonData
from vote_query import VoteQuery

class morevote(CommonData):
    def __init__(self):        
        CommonData.__init__(self)  # some share variable can get
        self.vote_svc = __spring__.getBean("voteService")
        self.vote = None
        
    def execute(self):
        if self.parentGuid == "" or self.parentType == "":
            self.addActionError(u"无效的标识。")
            return self.ERROR
        
        pageIndex = self.params.safeGetStringParam("page")
        if pageIndex.isdigit() == False:
            pageIndex = "1"
        
        qry = VoteQuery(""" vote.voteId,vote.title,vote.createDate,vote.endDate """)
        qry.parentGuid = self.parentGuid
        qry.parentObjectType = self.parentType
        pager = self.params.createPager()
        pager.itemName = u"调查"
        pager.itemUnit = u"个"
        pager.pageSize = 20
        pager.setCurrentPage(int(pageIndex))
        pager.totalRows = qry.count()
        vote_list = qry.query_map(pager)
        
        map = HashMap()
        map.put("SiteUrl",self.pageFrameService.getSiteUrl())
        map.put("UserMgrUrl",self.pageFrameService.getUserMgrUrl())
        map.put("vote_list",vote_list)
        map.put("pager",pager)
        map.put("loginUser",self.loginUser)
        map.put("parentGuid",self.parentGuid)
        map.put("parentType",self.parentType)

        pagedata = self.pageFrameService.transformTemplate(map, "/WEB-INF/mod/vote/morevote.ftl")      
        
        page_frame = self.pageFrameService.getFramePage(self.parentGuid,self.parentType)
        page_frame = page_frame.replace("[placeholder_content]",pagedata)
        page_frame = page_frame.replace("[placeholder_title]",u"全部调查投票")        
        self.writeToResponse(page_frame)