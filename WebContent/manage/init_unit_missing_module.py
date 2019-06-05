from cn.edustar.jitar.pojos import UnitWebpart
from cn.edustar.jitar.jython import JythonBaseAction
from base_action import ActionResult
from unit_query import UnitQuery

class init_unit_missing_module(JythonBaseAction):
    def __init__(self):
        self.unitService = __spring__.getBean("unitService")
        
    def execute(self):
        if self.loginUser == None:
            return ActionResult.LOGIN
        accessControlService = __spring__.getBean("accessControlService")
        if accessControlService.isSystemAdmin(self.loginUser) == False:
            self.addActionError(u"没有管理权限.")
            return ActionResult.ERROR
        
        if accessControlService.isSystemAdmin(self.loginUser) == False:
            self.addActionError(u"只有 admin 用户才可进行配置.")
            return ActionResult.ERROR
        
        
        #得到全部的缺少导航的单位
        qry = UnitQuery("unit.unitId, unit.unitTitle")
        unitlist = qry.query_map(qry.count())
        for unit in unitlist:
            webpartList = self.unitService.getSystemUnitWebpart(int(unit["unitId"]))
            if len(webpartList) < 11: # 11对应下面的 moduleName数量
                for w in webpartList:
                    self.unitService.deleteUnitWebpart(w)                
                self.genWebparts(int(unit["unitId"]))            
        response.getWriter().println(u"设置机构丢失的模块执行完毕。")
        
    def genWebparts(self, unitId):              
        moduleName = [u"机构文章", u"机构图片", u"机构资源", u"机构视频", u"图片新闻",u"机构协作组", u"最新动态", u"最新公告", u"统计信息", u"调查投票", u"机构学科",u"机构集备", u"友情链接"]           
        i = 0
        for m in moduleName:
            i = i + 1
            unitWebpart = UnitWebpart()
            unitWebpart.setModuleName(m)
            unitWebpart.setDisplayName(m)
            unitWebpart.setRowIndex(i)
            unitWebpart.setVisible(True)
            unitWebpart.setSystemModule(True)
            unitWebpart.setUnitId(unitId)
            if m == u"机构文章" or  m == u"机构资源" or m == u"机构视频" or m == u"机构集备":
               unitWebpart.setWebpartZone(4)
            elif m == u"图片新闻" or m == u"友情链接" or m == u"机构协作组":
                unitWebpart.setWebpartZone(3)
            elif m == u"图片模块":
                unitWebpart.setWebpartZone(2)
            elif m == u"机构学科":
                unitWebpart.setWebpartZone(1)
            else:
                unitWebpart.setWebpartZone(5)
            self.unitService.saveOrUpdateUnitWebpart(unitWebpart)