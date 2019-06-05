#encoding=utf-8
from unit_page import *
from java.lang import String

class gen_index_html(UnitBasePage):
    def __init__(self):
        UnitBasePage.__init__(self)
        
    def execute(self):
        if self.loginUser == None:
            return self.LOGIN        
        self.unit = self.getUnit()
        if self.unit == None:
            self.addActionError(u"您所访问的机构不存在！")
            return self.ERROR
        
        if self.isContentAdmin() == False :
            self.addActionError(u"你没有管理的权限。")
            return self.ERROR
        
        htmlGeneratorService = __spring__.getBean("htmlGeneratorService")
        str = htmlGeneratorService.UnitIndex(self.unit)
        self.addActionMessage(u"单位首页生成完毕。")
        return self.SUCCESS