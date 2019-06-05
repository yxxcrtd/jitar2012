from common_data import CommonData
from java.util import Calendar
from java.util import Date
from java.text import SimpleDateFormat
class listview(CommonData):
    def __init__(self):        
        CommonData.__init__(self)  # some share variable can get
        
    def execute(self):
        if self.parentGuid == "" or self.parentType == "":
            self.addActionError(u"无效的标识。")
            return self.ERROR
        request.setAttribute("parentGuid",self.parentGuid)
        request.setAttribute("parentType",self.parentType)
        return "/WEB-INF/mod/calendarevent/listview.ftl" 
