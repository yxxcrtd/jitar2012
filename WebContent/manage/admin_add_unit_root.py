from cn.edustar.jitar.pojos import Unit
from cn.edustar.jitar.util import ParamUtil
from cn.edustar.jitar.util import CommonUtil
from base_action import *

class admin_add_unit_root(ActionExecutor):
    def __init__(self):
        self.params = ParamUtil(request)
        self.unitService = __spring__.getBean("unitService")
        
    def execute(self):
        #检查是否 存在根节点
        unitList = self.unitService.getChildUnitListByParenId(0,[False])
        if len(unitList) > 0:
            self.addActionError(u"根节点已经存在，只能有一个根节点。")
            return ActionResult.ERROR
        
        if request.getMethod() == "POST":
            enname = self.params.safeGetStringParam("enname")
            title = self.params.safeGetStringParam("title")
            siteTitle = self.params.safeGetStringParam("siteTitle")
            if enname == "" or title == "":
                self.addActionError(u"机构名称和英文名称不能为空。")
                return ActionResult.ERROR
            if siteTitle == "":
                self.addActionError(u"机构网站名称不能为空。")
                return ActionResult.ERROR
            if CommonUtil.isValidName(enname) == False:
                self.addActionError(u"英文名称只能是英文字母、数字并且必须以英文字母开头。")
                return ActionResult.ERROR
            
            unit = Unit()
            unit.setUnitName(enname)
            unit.setUnitTitle(title)
            unit.setSiteTitle(siteTitle)
            unit.setParentId(0)
            unit.setUnitPathInfo("")
            unit.setHasChild(False)
            self.unitService.saveOrUpdateUnit(unit)
            unit.setUnitPathInfo("/" + str(unit.unitId) + "/")
            self.unitService.saveOrUpdateUnit(unit)
            
            # 将管理员添加到根机构中。
            userService = __spring__.getBean("userService")
            u = userService.getUserByLoginName("admin")
            if u != None:
                u.setUnitId(unit.unitId)
                u.setUnitPathInfo(unit.unitPathInfo)
                userService.updateUserUnit(u)
                
            return "/WEB-INF/ftl/admin/admin_add_unit_root_success.ftl"
            
        return "/WEB-INF/ftl/admin/admin_add_unit_root.ftl"
