from common_data import CommonData
from java.util import Calendar
from java.util import Date
from java.text import SimpleDateFormat

class showMsg(CommonData):
    def __init__(self):        
        CommonData.__init__(self)  # some share variable can get
        self.calendar_svc = __spring__.getBean("calendarService")
        
    def execute(self):
        self.parentGuid=self.params.safeGetStringParam("parentGuid")
        self.parentType=self.params.safeGetStringParam("parentType")
        self.year = self.params.safeGetIntParam("year")
        self.month = self.params.safeGetIntParam("month")
        self.day = self.params.safeGetIntParam("day")
        
        self.calendars = self.calendar_svc.getCalendars(self.parentGuid,self.parentType,int(self.year),int(self.month),int(self.day))
        
        request.setAttribute("parentGuid",self.parentGuid)
        request.setAttribute("parentType",self.parentType)
        request.setAttribute("year",self.year)
        request.setAttribute("month",self.month)
        request.setAttribute("day",self.day)
        request.setAttribute("calendars",self.calendars)
        return "/WEB-INF/mod/calendarevent/showMsg.ftl" 
