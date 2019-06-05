from unit_page import *
class reStatUnitIndex(UnitBasePage):
    def __init__(self):
        UnitBasePage.__init__(self)
        self.unitService = __jitar__.unitService
        
    def execute(self):
        if self.loginUser == None:
            return self.LOGIN        
        self.unit = self.getUnit()
        if self.unit == None:
            self.addActionError(u"您所访问的机构不存在！")
            return self.ERROR
        
        self.unitService.statUnitDayCount()
        self.unitService.statUnitRank(self.unit.unitId)
        htmlGeneratorService = __spring__.getBean("htmlGeneratorService")
        str = htmlGeneratorService.UnitIndex(self.unit)
        self.addActionMessage(u"单位首页生成完毕。")
        return self.SUCCESS
        