from common_data import CommonData
from base_blog_page import *
from java.util import Calendar
from java.util import Date
from java.text import SimpleDateFormat
from base_action import BaseAction
from cn.edustar.jitar.data import Command

class default(CommonData, RequestMixiner, ResponseMixiner, PageCheckMixiner):
    def __init__(self):        
        CommonData.__init__(self)  # some share variable can get
        self.calendar_svc = __spring__.getBean("calendarService")
        self.AuthorityCheck_svc = __spring__.getBean("pluginAuthorityCheckService")
        self.user_svc = __jitar__.userService
        self.calendars = None
        
    def execute(self):
        self.parentGuid=self.params.safeGetStringParam("parentGuid")
        self.parentType=self.params.safeGetStringParam("parentType")
        if self.parentGuid == "" or self.parentType == "":
            self.addActionError(u"无效的标识。")
            return self.ERROR
        self.page=None
        if self.parentType == "user" :
            self.user = self.user_svc.getUserByGuid(self.parentGuid)
            self.page = self.getUserIndexPage(self.user)
        elif self.parentType == "group" :
            self.group = group_svc.getGroupByGuid(self.parentGuid)
            self.page = self.getGroupIndexPage(self.group)

        if self.page == None:
            return self.sendNotFound()
            
        request.setAttribute("page", self.page)
        
        self.year = self.params.safeGetIntParam("year")
        self.month = self.params.safeGetIntParam("month")
        self.day = self.params.safeGetIntParam("day")
        
        if self.year==0:
            now=Date()
            sdf = SimpleDateFormat("yyyy")
            self.year= int(sdf.format(now))
            sdf = SimpleDateFormat("M")
            self.month= int(sdf.format(now))
            sdf = SimpleDateFormat("d")
            self.day= int(sdf.format(now))
        if self.day==0:
            self.day=1
               
        self.calendars = self.calendar_svc.getCalendars(self.parentGuid,self.parentType,int(self.year),int(self.month))
        
        request.setAttribute("parentGuid",self.parentGuid)
        request.setAttribute("parentType",self.parentType)
        request.setAttribute("calendars",self.calendars)
        request.setAttribute("year",int(self.year))
        request.setAttribute("month",int(self.month))        
        request.setAttribute("day",int(self.day))
        return "/WEB-INF/mod/calendarevent/default.ftl"     