from common_data import CommonData
from calendar_query import CalendarQuery

class my_created_object(CommonData):
    def execute(self):
        if self.loginUser == None:
            self.addActionError(u"请先登录。")
            return self.ERROR
        
        # 列出当前用户的所有用户提醒
        cal_svc = __spring__.getBean("calendarService")
        
        # 自己可以删除自己创建的？
        ids = self.params.safeGetIntValues("id")
        for id in ids:
            cal_svc.DelCalendar(id)

        userGuid=self.loginUser.userGuid;
        
        qry = CalendarQuery(""" c.id,c.objectGuid,c.objectType,c.eventTimeBegin,c.eventTimeEnd,c.title,c.url,c.createTime """)
        qry.objectGuid=userGuid
        qry.objectType="user"
        pager = self.params.createPager()
        pager.itemName = u"提醒"
        pager.itemUnit = u"个"
        pager.pageSize = 20
        pager.totalRows = qry.count()
        calendars = qry.query_map(pager)
        request.setAttribute("calendars", calendars)
        request.setAttribute("pager", pager)
        
        return "/WEB-INF/mod/calendarevent/my_created_object.ftl"        