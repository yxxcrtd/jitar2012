from unit_page import UnitBasePage
from cn.edustar.jitar.pojos import UnitSubject
from cn.edustar.jitar.util import FileCache

class unit_subject(UnitBasePage):
    def __init__(self):
        UnitBasePage.__init__(self)
        self.unitService = __spring__.getBean("unitService")
        self.subjectService = __spring__.getBean("subjectService")

    def execute(self):
        if self.loginUser == None:
            return self.LOGIN
        
        if self.unitService == None:
            self.addActionError(u"无法加载组织机构服务，请检查 applicationContext.xml 配置文件，是否缺少  unitService 节点。")
            return self.ERROR        
        
        self.unit = self.getUnit()
        if self.unit == None:
            self.addActionError(u"您所访问的机构不存在！")
            return self.ERROR
        
        if self.isUnitAdmin() == False:
            self.addActionError(u"你没有管理的权限。")
            return self.ERROR       
        
        if request.getMethod() == "POST":
            cmd = self.params.safeGetStringParam("cmd")            
            if cmd == "add":
                subId = self.params.safeGetIntValues("subId")
                for sid in subId:
                    subject = self.subjectService.getSubjectById(sid)
                    if subject != None:
                        # 检查是否已经存在
                        if False == self.unitService.checkUnitSubjectIsExists(subject, self.unit):                                
                            unitSubject = UnitSubject()
                            unitSubject.setUnitId(self.unit.unitId)
                            unitSubject.setSubjectId(subject.subjectId)
                            unitSubject.setMetaSubjectId(subject.metaSubject.msubjId)
                            unitSubject.setMetaGradeId(subject.metaGrade.gradeId)
                            unitSubject.setDisplayName(subject.subjectName)
                            self.unitService.saveOrUpdateUnitSubject(unitSubject)
            elif cmd == "delete":
                guids = self.params.safeGetIntValues("guid")                
                for guid in guids:
                    unitSubject = self.unitService.getUnitSubjectById(guid)
                    if unitSubject != None:                        
                        self.unitService.deleteUnitSubject(unitSubject)
            elif cmd == "setDisplayName":
                self.setDisplayName()
            else:
                self.addActionError(u"无效的命令.")
                return self.ERROR
        
        self.get_unit_subjectlist()
        request.setAttribute("unit", self.unit)
        request.setAttribute("subject_list", self.subjectService.getSubjectList())
        fc = FileCache()
        fc.deleteUnitCacheFile(self.unit.unitName)
        fc = None
        return "/WEB-INF/unitsmanage/unit_subject.ftl"
    
    def get_unit_subjectlist(self):
        unit_subject_list = self.unitService.getSubjectByUnitId(self.unit.unitId)
        request.setAttribute("unit_subject_list", unit_subject_list)
    
    def setDisplayName(self):
        unitSubjectId = self.params.safeGetIntValues("unitSubjectId")
        for usId in unitSubjectId:
            unitSubject = self.unitService.getUnitSubjectById(usId)
            if unitSubject != None:
                displayName = self.params.safeGetStringParam("displayName" + str(usId))
                if unitSubject.displayName != displayName:
                    unitSubject.setDisplayName(displayName)
                    self.unitService.saveOrUpdateUnitSubject(unitSubject)
