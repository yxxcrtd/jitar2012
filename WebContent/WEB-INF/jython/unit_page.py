from cn.edustar.jitar.jython import JythonBaseAction
from cn.edustar.jitar.util import ParamUtil
from cn.edustar.jitar.data import BaseQuery
from cn.edustar.jitar.pojos import User, AccessControl
from java.lang import String

class UnitBasePage(JythonBaseAction):
    ERROR = "/WEB-INF/ftl/Error.ftl"
    ACCESS_DENIED = "/WEB-INF/ftl/Error.ftl"
    ACCESS_ERROR = "/WEB-INF/ftl/mengv1/access_error.ftl"
    SUCCESS = "/WEB-INF/ftl/success.ftl"
    LOGIN = "/login.jsp"
    
    def __init__(self):
        self.params = ParamUtil(request)
        self.unitId = self.params.safeGetIntParam("unitId")
        self.unitService = __spring__.getBean("unitService")
        self.accessControlService = __spring__.getBean("accessControlService")
        self.unit = None
        self.unitName = request.getAttribute("unitName")
        self.unitRootUrl = None
        self.subjectService = __jitar__.subjectService       
        self.categoryService = __jitar__.categoryService
        
    def getUnit(self):
        if self.unitName != None and self.unitName != "":
            self.unit = self.unitService.getUnitByName(self.unitName)
        if self.unitId != 0:
            self.unit = self.unitService.getUnitById(self.unitId)
        if self.unit == None:
            return None
        self.unitId = self.unit.unitId
        self.unitName = self.unit.unitName
        
        if self.unit != None:
            self.unitRootUrl = request.getAttribute("UnitRootUrl")
            if self.unitRootUrl == None:
                configUnitSiteRoot = request.getSession().getServletContext().getInitParameter("unitUrlPattern")
                if configUnitSiteRoot == None or configUnitSiteRoot == "":
                    self.unitRootUrl = self.getCurrentSiteUrl(request) + "d/" + self.unit.unitName + "/"
                else:
                    configUnitSiteRoot = String(configUnitSiteRoot)
                    self.unitRootUrl = configUnitSiteRoot.replaceAll("\\{unitName\\}", self.unit.unitName)
            request.setAttribute("UnitRootUrl", self.unitRootUrl)
            cache = __jitar__.cacheProvider.getCache('sitenav')
            cache_k = "unit_nav_" + str(self.unit.unitId)
            unitSiteNavList = cache.get(cache_k)
            if unitSiteNavList == None:
                unitSiteNavList = __spring__.getBean("siteNavService").getAllSiteNav(False, 1, self.unit.unitId)
                cache.put(cache_k, unitSiteNavList)
            request.setAttribute("UnitSiteNavList", unitSiteNavList)
            request.setAttribute("canManege", self.canManege())
        return self.unit
    
    def canManege(self):
        if self.loginUser == None:
            return False
        return (self.isUnitAdmin() or self.isContentAdmin() or self.isUserAdmin())
        
    def isUnitAdmin(self):
        if self.unitId == 0:
            return False
        if self.loginUser == None:return False
        #accessControlService = __spring__.getBean("accessControlService")
        if self.accessControlService.isSystemAdmin(self.loginUser) == True:
            return True
        unitSystemAdmin = self.accessControlService.userIsUnitSystemAdmin(self.loginUser, self.unit)        
        return unitSystemAdmin
    
    def isContentAdmin(self):
        if self.unitId == 0:
            return False
        if self.loginUser == None:return False
        #accessControlService = __spring__.getBean("accessControlService")
        if self.accessControlService.isSystemAdmin(self.loginUser) == True:
            return True
        if self.isUnitAdmin() == True:
            return True        
        unitContentAdmin = self.accessControlService.userIsUnitContentAdmin(self.loginUser, self.unit)
        return unitContentAdmin
    
    def isUserAdmin(self):
        if self.unitId == 0:
            return False
        if self.loginUser == None:return False
        #accessControlService = __spring__.getBean("accessControlService")
        if self.accessControlService.isSystemAdmin(self.loginUser) == True:
            return True
        if self.isUnitAdmin() == True:
            return True        
        unitUserAdmin = self.accessControlService.userIsUnitUserAdmin(self.loginUser, self.unit)
        return unitUserAdmin        
    
    def clear_cache(self):
        if self.unit != None:
            cache = __jitar__.cacheProvider.getCache('unit')
            if cache != None:                    
                cache_list = cache.getAllKeys()
                cache_key_head = "unit" + str(self.unit.unitId)
                for c in cache_list:
                    if c.split("_")[0] == cache_key_head:
                        cache.remove(c)
                        
    def getCurrentSiteUrl(self, request):
        root = request.getScheme() + "://" + request.getServerName()
        if request.getServerPort() != 80:
            root = root + ":" + str(request.getServerPort())
        root = root + request.getContextPath() + "/"
        return root
    def putGradeList(self):
        grade_list = self.subjectService.getGradeList()
        request.setAttribute("grade_list", grade_list);
        
    def putSubjectList(self):
        subject_list = self.subjectService.getMetaSubjectList()
        request.setAttribute("subject_list", subject_list)
    def putResouceCateList(self):
        res_cate = self.categoryService.getCategoryTree("resource")
        request.setAttribute("res_cate", res_cate)
    
class UnitNewsNoticeQuery(BaseQuery):
    def __init__(self, selectFields):
        BaseQuery.__init__(self, selectFields)
        
        self.itemType = None
        self.unitId = None
        self.orderType = 0        
        
    def initFromEntities(self, qctx):
        qctx.addEntity("UnitNews", "un", "")
    
    def resolveEntity(self, qctx, entity):
        BaseQuery.resolveEntity(self, qctx, entity)
        
    def applyWhereCondition(self, qctx):
        if self.itemType != None:
            qctx.addAndWhere("un.itemType = :itemType")
            qctx.setInteger("itemType", self.itemType)
            
        if self.unitId != None:
            qctx.addAndWhere("un.unitId = :unitId")
            qctx.setInteger("unitId", self.unitId)
    
    def applyOrderCondition(self, qctx):
        if self.orderType == 0:
            qctx.addOrder("un.unitNewsId DESC")
            
class NoCache:
    def get(self, key):
        return None
    def put(self, key):
        pass
    def put(self, key, ttl):
        pass
    def remove(self, key):
        pass
    def clear(self):
        pass
