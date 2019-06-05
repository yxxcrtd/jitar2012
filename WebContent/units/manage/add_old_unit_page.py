from cn.edustar.jitar.pojos import UnitWebpart
from cn.edustar.jitar.util import ParamUtil

class add_old_unit_page:
    def execute(self):
        self.unitService = __spring__.getBean("unitService")
        unit_list = self.unitService.getAllUnitOrChildUnitList(None,[False])
        for unit in unit_list:
            unit_webpart_list = self.unitService.getUnitWebpartList(unit.unitId)
            if len(unit_webpart_list) == 0:                
                moduleName = [u"机构文章", u"机构图片", u"机构资源", u"机构视频", u"图片新闻",u"机构协作组", u"最新动态", u"最新公告", u"统计信息", u"调查投票", u"机构学科",u"机构集备", u"友情链接"]
                i = 0
                for m in moduleName:
                    i = i+ 1
                    unitWebpart = UnitWebpart()
                    unitWebpart.setModuleName(m)
                    unitWebpart.setRowIndex(i)
                    unitWebpart.setVisible(True)
                    unitWebpart.setSystemModule(True)
                    unitWebpart.setUnitId(unit.getUnitId())
                    if m == u"机构文章" or  m == u"机构资源" or m == u"机构视频" or m == u"机构集备":
                       unitWebpart.setWebpartZone(4)
                    elif m == u"图片新闻" or m == u"友情链接" or m == u"机构协作组":
                        unitWebpart.setWebpartZone(3)
                    elif m == u"图片模块":
                        unitWebpart.setWebpartZone(2)
                    else:
                        unitWebpart.setWebpartZone(5)
                    self.unitService.saveOrUpdateUnitWebpart(unitWebpart)
        self.params = ParamUtil(request)
        unitId = self.params.safeGetIntParam("unitId")
        if unitId > 0:
            response.sendRedirect("../index.py?unitId="+str(unitId))
            return
        else:
            return "/WEB-INF/ftl/success.ftl"