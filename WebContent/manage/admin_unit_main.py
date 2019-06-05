from cn.edustar.jitar.util import ParamUtil
from cn.edustar.jitar.jython import BaseAdminAction
from base_action import ActionResult

class admin_unit_main(BaseAdminAction, ActionResult):
    def __init__(self):
        self.params = ParamUtil(request)
        self.unitService = __spring__.getBean("unitService")
        
    def execute(self):
        if self.loginUser == None:
            return ActionResult.LOGIN
        accessControlService = __spring__.getBean("accessControlService")
        if accessControlService.isSystemAdmin(self.loginUser) == False:
            self.addActionError(u"没有管理的权限.")
            return ActionResult.ERROR        
        
        cmd = self.params.safeGetStringParam("cmd")
        
        if cmd == "Delete":
            return self.Delete()
        
        unitId = self.params.safeGetIntParam("unitId")
        if unitId == 0:
            #判断是否存在根节点，根节点必须为0
            unitList = self.unitService.getChildUnitListByParenId(0,[False])
            if len(unitList) < 1:
                # 还没有根节点，必须添加一个根节点.
                response.sendRedirect("admin_add_unit_root.py")
                return
        unit = self.unitService.getUnitById(unitId)
        unitLevel = self.unitService.getUnitDeepLevel(unit)
        configUnitLevel = self.unitService.getConfigUnitLevel()
        childunitlist = self.unitService.getAllUnitOrChildUnitList(unit,[False])
        request.setAttribute("unit", unit)
        request.setAttribute("unitLevel", unitLevel)
        request.setAttribute("configUnitLevel", configUnitLevel)
        #print "unitLevel=",unitLevel
        #print "configUnitLevel=",configUnitLevel
        if unitLevel > configUnitLevel:
            self.addActionError(u"不允许的级别。许可证级数不够或者在更新时没有执行 UPDATE替换单位信息的SQL。")
            return ActionResult.ERROR
        request.setAttribute("childunitlist", childunitlist)
        return "/WEB-INF/ftl/admin/admin_unit_main.ftl"
    
    def Delete(self):
        confirm = self.params.safeGetStringParam("confirm")
        parentUnitId = self.params.safeGetIntParam("unitId")
        toDeleteUnitId = self.params.safeGetIntParam("opunitId")
        if parentUnitId == 0 or toDeleteUnitId == 0:
            self.addActionError(u"请输入正确的标识。")
            return ActionResult.ERROR
        
        parentUnit = self.unitService.getUnitById(parentUnitId)
        toDeleteUnit = self.unitService.getUnitById(toDeleteUnitId)
        if parentUnit == None or toDeleteUnit == None:
            self.addActionError(u"不能加载的组织机构对象。")
            return ActionResult.ERROR
        
        if toDeleteUnit.parentId != parentUnit.unitId:
            self.addActionError(u"要删除的机构与父机构不相吻合。")
            return ActionResult.ERROR
        
        if confirm == "":           
            request.setAttribute("unit", toDeleteUnit)
            request.setAttribute("opunitId", toDeleteUnitId)
            request.setAttribute("unitId", parentUnitId)
            return "/WEB-INF/ftl/admin/admin_unit_delete.ftl"
        # 执行删除，下面的过程可能比较耗费比较长的时间。
        # 先替换文章、资源、用户的UnitPathInfo 信息？
        
        deleteComplexObjectService = __spring__.getBean("deleteComplexObjectService")
        deleteComplexObjectService.deleteUnit(toDeleteUnit)
        response.getWriter().println("<script>window.location.href='admin_unit_main.py?unitId=" + str(parentUnitId) + "';window.parent.frames[0].location.reload();</script>")
