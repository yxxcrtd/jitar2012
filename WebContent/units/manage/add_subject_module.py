from cn.edustar.jitar.pojos import UnitWebpart

class add_subject_module:
    def execute(self):
        unitService = __spring__.getBean("unitService")
        us = unitService.getAllUnitOrChildUnitList(None,[False])
        for u in us:                
            unitWebpart = UnitWebpart()
            unitWebpart.setModuleName(u"机构学科")
            unitWebpart.setDisplayName(u"机构学科")
            unitWebpart.setSystemModule(True)
            unitWebpart.setUnitId(u.unitId)
            unitWebpart.setWebpartZone(1)
            unitWebpart.setRowIndex(1)
            unitWebpart.setContent(None)
            unitWebpart.setVisible(True)
            unitWebpart.setPartType(0)
            unitWebpart.setShowType(0)
            #print u.unitId
            unitService.saveOrUpdateUnitWebpart(unitWebpart)
        response.getWriter().println(u"<h1>ok，请勿重复执行。</h1>")
