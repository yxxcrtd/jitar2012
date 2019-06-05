from unit_page import *
from java.lang import String

class unit_getinfo(UnitBasePage):
    def __init__(self):
        UnitBasePage.__init__(self)
        self.unitTypeService = __jitar__.unitTypeService
        
    def execute(self):
        self.unit = self.getUnit()
        request.setAttribute("unit", self.unit)
        return "/WEB-INF/unitsmanage/unit_into_ajax.ftl"
