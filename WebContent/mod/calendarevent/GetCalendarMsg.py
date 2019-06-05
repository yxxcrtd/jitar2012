from common_data import CommonData
from base_blog_page import *
from java.util import Calendar
from java.util import Date
from java.text import SimpleDateFormat
from base_action import BaseAction
from cn.edustar.jitar.data import Command

class GetCalendarMsg(CommonData, RequestMixiner, ResponseMixiner, PageCheckMixiner):
    def __init__(self):        
        CommonData.__init__(self)  # some share variable can get
        self.calendar_svc = __spring__.getBean("calendarService")
        self.AuthorityCheck_svc = __spring__.getBean("pluginAuthorityCheckService")
        self.user_svc = __jitar__.userService
        self.calendars = None
        
    def execute(self):
        self.parentGuid=self.params.safeGetStringParam("parentGuid")
        self.parentType=self.params.safeGetStringParam("parentType")
        self.year = self.params.safeGetIntParam("year")
        self.month = self.params.safeGetIntParam("month")
        self.day = self.params.safeGetIntParam("day")
        if self.year==0:
            now=Date()
            sdf = SimpleDateFormat("yyyy")
            self.year= sdf.format(now)
            sdf = SimpleDateFormat("M")
            self.month= sdf.format(now)
            sdf = SimpleDateFormat("d")
            self.day= sdf.format(now)
        
        self.calendars = self.calendar_svc.getCalendars(self.parentGuid,self.parentType,int(self.year),int(self.month))
        
        request.setAttribute("parentGuid",self.parentGuid)
        request.setAttribute("parentType",self.parentType)
        request.setAttribute("calendars",self.calendars)
        request.setAttribute("year",self.year)
        request.setAttribute("month",self.month)        
        request.setAttribute("day",self.day)
        return "/WEB-INF/mod/calendarevent/GetCalendarMsg.ftl"     