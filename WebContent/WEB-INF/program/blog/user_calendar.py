from cn.edustar.jitar.util import Lunar
from java.lang import System

class user_calendar:
    def execute(self):
        LunarCalendar = Lunar(System.currentTimeMillis())
        request.setAttribute("today",LunarCalendar.getCurrentDate())
        request.setAttribute("nongli",LunarCalendar.getLunarDateString())
        request.setAttribute("weekday",LunarCalendar.getDayOfWeek())
        if LunarCalendar.getTermString() != "" :
            request.setAttribute("jieqi",LunarCalendar.getTermString())
        return "/WEB-INF/user/default/user_calendar.ftl"