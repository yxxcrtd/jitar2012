from cn.edustar.jitar.pojos import Unit, Config
from cn.edustar.jitar.model import Configure, UserMgrModel
from cn.edustar.jitar.util import ParamUtil, CommonUtil
from cn.edustar.jitar.data import Command
from java.util import ArrayList, HashMap
from java.io import File, FileOutputStream, OutputStreamWriter
from base_action import *
from java.net import URLDecoder

class add_root_unit(ActionExecutor):
    def __init__(self):
        self.params = ParamUtil(request)
        self.unitService = __jitar__.unitService
        self.configService = __jitar__.configService
    
    def getSiteUrl(self):
        return CommonUtil.getSiteUrl(request)
    
    def getSiteRootUrl(self):
        return CommonUtil.getSiteServer(request)
    
    def execute(self):
        #检查是否 存在根节点，本程序将进行整个平台的初始化工作，
        rootUnit = self.unitService.getRootUnit()
        if rootUnit != None:
            self.addActionError(u"根机构已经存在，只能有一个根机构。")
            return ActionResult.ERROR
        
        unitList = self.unitService.getChildUnitListByParenId(0)
        if len(unitList) > 0:
            self.addActionError(u"组织机构表中已经存在单位，不能再添加，请检查组织机构表。")
            return ActionResult.ERROR        
        
        if request.getMethod() == "POST":
            enname = self.params.safeGetStringParam("enname")
            title = self.params.safeGetStringParam("title")
            siteTitle = self.params.safeGetStringParam("siteTitle")
            if enname == "" or title == "":
                self.addActionError(u"机构名称、英文名称不能为空。")
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
            self.save_or_update_item("site.name",title)
            self.save_or_update_item("site.title",siteTitle)
            self.save_or_update_item("siteThemeUrl",request.getContextPath() + "/css/index/")
                
            self.configService.reloadConfig()
            
            # 将管理员添加到根机构中。
            userService = __spring__.getBean("userService")
            u = userService.getUserByLoginName("admin")
            if u != None:
                u.setUnitId(unit.unitId)
                u.setUnitPathInfo(unit.unitPathInfo)
                userService.updateUserUnit(u)        
                        
            #初始化一些内容,重新生成导航文件等
            self.GenSubjectNav()
            
            self.addActionMessage(u"设置成功。")
            self.addActionLink(u"返回首页", "index.py")
            self.addActionLink(u"返回其他配置页面", "checksetting.py")
            
            return ActionResult.SUCCESS
        else:
            try:
                self.clearAllStaticCacheFile()
            except:
                print u"系统初始化错误：删除静态文件时出现错误。"
        return "/WEB-INF/ftl/admin/admin_add_unit_root.ftl"
    
    def save_or_update_item(self,name, value):
        config = self.configService.getConfigByItemTypeAndName("jitar", name)
        if config == None:
            config = Config()
            config.setItemType("jitar")
            config.setName(name)
            config.setValue(value)
            config.setType("string")
            config.setDefval("")
            config.setTitle(name)
            config.setDescription(value)
            self.configService.createConfig(config)
        else:
            config.value = value
        self.configService.updateConfig(config)
        
    def clearAllStaticCacheFile(self):
        #删除可能存在的静态文件
        strRootDir = request.getServletContext().getRealPath("/")
        strRootDir = URLDecoder.decode(strRootDir,"utf-8")
        #删除首页
        file = File(strRootDir + "index.html")
        if file.exists() and file.isFile():
            file.delete()        
        file = File(strRootDir + "index.htm")
        if file.exists() and file.isFile():
            file.delete()
                    
        strFile = strRootDir + "html" + File.separator + "subject_nav.html"
        file = File(strFile)
        if file.exists() and file.isFile():
            file.delete()
        
        strFile = strRootDir + "html" + File.separator + "user" + File.separator
        file = File(strFile)
        if file.exists():
            self.deleteDirectory(strFile)
        
        strFile = strRootDir + "html" + File.separator + "unit" + File.separator
        file = File(strFile)
        if file.exists():
            self.deleteDirectory(strFile)
        file = None
        
    def deleteDirectory(self, sPath):
        # 如果sPath不以文件分隔符结尾，自动添加文件分隔符  
        dirFile = File(sPath)
        # 如果dir对应的文件不存在，或者不是一个目录，则退出  
        if dirFile.exists() == False or dirFile.isDirectory() == False:
            return
        # 删除文件夹下的所有文件(包括子目录)
        files = dirFile.listFiles()
        for f in files:
            # 删除子文件
            if f.isFile():
                f.delete()
            else:
                self.deleteDirectory(f.getAbsolutePath())
        #/删除当前目录  
        dirFile.delete()
        dirFile = None
        
    def GenSubjectNav(self):
        strFile = request.getServletContext().getRealPath("/")
        strFile = URLDecoder.decode(strFile,"utf-8")
        strSubjectFile = strFile + "html" + File.separator
        file = File(strSubjectFile)
        if file.exists() == False:
            file.mkdirs()
        #创建学科导航
        strSubjectFile = strSubjectFile + "subject_nav.html"
        file = File(strSubjectFile)
        #先得到年级
        subjectService = __spring__.getBean("subjectService")
        qry = Command(" SELECT DISTINCT metaGrade.gradeId FROM Subject Order By metaGrade.gradeId ASC") 
        mGradeId = qry.open()
        MetaGrade = ArrayList()
        metaSubject = ArrayList()
        for grade in mGradeId:
            mGrade = subjectService.getGrade(int(grade))
            MetaGrade.add(mGrade)
            subj = subjectService.getSubjectByGradeId(int(grade))
            m = ArrayList()
            if subj != None:
                for sj in range(0, subj.size()):
                    m.add(subj[sj].metaSubject)
                metaSubject.add({"gradeName" : mGrade.gradeName, "gradeId" : grade, "metaSubject" : m })
        map = HashMap()
        map.put("metaGrade", MetaGrade)
        map.put("meta_Grade", MetaGrade)
        map.put("SubjectNav", metaSubject)
        templateProcessor = __spring__.getBean("templateProcessor")
        str = templateProcessor.processTemplate(map, "/WEB-INF/ftl/site_subject_nav.ftl", "utf-8")
        try:            
            fw = OutputStreamWriter(FileOutputStream(file), "utf-8")
            fw.flush()
            fw.write(str)
            fw.close()
        finally:
            file = None
            fw = None