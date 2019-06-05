from cn.edustar.jitar.jython import BaseAdminAction
from cn.edustar.jitar.model import Configure, UserMgrModel
from cn.edustar.jitar.util import ParamUtil, CommonUtil
from cn.edustar.jitar.pojos import Config, User
from base_action import ActionResult
from java.lang import String

# 站点配置管理
class admin_site(BaseAdminAction, ActionResult):
    def __init__(self):
        self.cfg_svc = __jitar__.configService
        self.unitService = __jitar__.unitService
        self.accessControlService = __spring__.getBean("accessControlService")
        
    def execute(self):
        if self.loginUser == None:
            return ActionResult.LOGIN
    
        if self.accessControlService.isSystemAdmin(self.loginUser) == False:
            self.addActionError(u"没有管理站点配置的权限，只有超级管理员才能进行管理。")
            return ActionResult.ERROR
        
        self.cfg_svc.reloadConfig()
        self.params = ParamUtil(request)
        cmd = self.params.getStringParam("cmd")
        
        if cmd == "save":
            return self.save()
        elif cmd == "dump":
            return self.dump()
        else:
            return self.list()
        
    def list(self):
        request.setAttribute("RootUnit", self.unitService.getRootUnit())
        config = self.cfg_svc.getConfigure()
        request.setAttribute("config", config)
        logo = ""
        logowidth = ""
        logoheight = ""
        sitelogo = config["site.logo"]
        if sitelogo != None and sitelogo != "":
            sitelogo = str(sitelogo)
            arr = sitelogo.split("|")
            if len(arr) > 2:
                logowidth = arr[0]
                logoheight = arr[1]
                logo = arr[2]
            
        request.setAttribute("logowidth", logowidth)
        request.setAttribute("logoheight", logoheight)
        request.setAttribute("logo", logo)
        request.setAttribute("SiteUrlCurrent", self.get_site_url())
        
        return "/WEB-INF/ftl/admin/admin_site.ftl"
    def get_site_url(self):
        return CommonUtil.getSiteUrl(request)

    def save(self):        
        unitTitle = self.params.getStringParam("unitTitle")
        siteTitle = self.params.getStringParam("siteTitle")
        if unitTitle == "" or siteTitle == "":
            self.addActionError(u"请输入根单位名称、根单位平台名称")
            return self.ERROR
        
        rootUnit = self.unitService.getRootUnit()
        if rootUnit == None:
            self.addActionError(u"没没有根单位。")
            return ActionResult.ERROR
        else:
            if rootUnit.unitTitle != unitTitle or rootUnit.siteTitle != siteTitle:
                rootUnit.unitTitle = unitTitle
                rootUnit.siteTitle = siteTitle
                self.unitService.saveOrUpdateUnit(rootUnit)
        
        
        self.save_or_update_item("site.name",unitTitle)
        self.save_or_update_item("site.title",siteTitle)
        self.update_config_item("site.keyword", self.params.getStringParam("site.keyword"))
        self.update_config_item("site.copyright", self.params.getStringParam("site.copyright"))
        self.update_config_item("site.webmaster.email", self.params.getStringParam("site.webmaster.email"))
        self.update_config_item("site.stop.info", self.params.getStringParam("site.stop.info"))
        self.update_config_item("site.resourceUrl", self.params.getStringParam("site.resourceUrl"))        
        self.update_config_item("score.my.article.add", self.params.getStringParam("score.my.article.add"))
        self.update_config_item("score.other.article.add", self.params.getStringParam("score.other.article.add"))
        self.update_config_item("score.article.rcmd", self.params.getStringParam("score.article.rcmd"))
        self.update_config_item("score.resource.add", self.params.getStringParam("score.resource.add"))
        self.update_config_item("score.resource.rcmd", self.params.getStringParam("score.resource.rcmd"))        
        self.update_config_item("score.photo.upload", self.params.getStringParam("score.photo.upload"))
        self.update_config_item("score.video.upload", self.params.getStringParam("score.video.upload"))        
        self.update_config_item("score.comment.add", self.params.getStringParam("score.comment.add"))        
        self.update_config_item("score.article.adminDel", self.params.getStringParam("score.article.adminDel"))
        self.update_config_item("score.resource.adminDel", self.params.getStringParam("score.resource.adminDel"))
        self.update_config_item("score.comment.adminDel", self.params.getStringParam("score.comment.adminDel"))
        self.update_config_item("score.photo.adminDel", self.params.getStringParam("score.photo.adminDel"))
        self.update_config_item("score.video.adminDel", self.params.getStringParam("score.video.adminDel"))        
        self.update_config_item("site.user.online.time", self.params.getStringParam("site.user.online.time"))
        reportType = self.params.getStringParam("reportType")
        if reportType == None or reportType == "":
            reportType = u"政治,色情,违法,人身攻击,抄袭,其它"
        else:
            reportType = reportType.replace(u"，",",")
        self.update_config_item("reportType", reportType)
        
        
        platformTypeSetting = request.getAttribute("platformType")
        if platformTypeSetting != None and platformTypeSetting == "2":  
            topsite = self.params.getStringParam("topsiteurl")
            if topsite == None:
                topsite = ""
            if topsite != "":
                if len(topsite) > 1 and topsite[len(topsite) - 1] != "/":
                    topsite = topsite + "/"
                if topsite.startswith("http") == False:
                    topsite = "http://" + topsite
            
            config = self.cfg_svc.getConfigByItemTypeAndName("jitar", "topsite_url")
            if config == None:
                config = Config()
                config.setItemType("jitar")
                config.setName("topsite_url")
                config.setValue(topsite)
                config.setType("string")
                config.setDefval("")
                config.setTitle(u"市平台地址")
                config.setDescription(u"市平台地址")
                self.cfg_svc.createConfig(config)
            else:
                self.update_config_item("topsite_url", topsite)
        
        logo = self.params.getStringParam("logo")
        logowidth = self.params.getStringParam("logowidth")
        logoheight = self.params.getStringParam("logoheight")    
        if logo == None:logo = ""
        if logowidth == None:logowidth = ""
        if logoheight == None:logoheight = ""
        sitelog = logowidth + "|" + logoheight + "|" + logo
        config = self.cfg_svc.getConfigByItemTypeAndName("jitar", "site.logo")
        if config == None:
            config = Config()
            config.setItemType("jitar")
            config.setName("site.logo")
            config.setValue(sitelog)
            config.setType("string")
            config.setDefval("")
            config.setTitle(u"网站Logo")
            config.setDescription(u"网站Logo，图片或者Flash。")
            self.cfg_svc.createConfig(config)
        else:
            self.update_config_item("site.logo", sitelog)
            
        # 最后再重新加载一下配置
        self.cfg_svc.reloadConfig()
        
        self.addActionMessage(u"配置修改成功完成.")
        self.addActionLink(u"返回站点配置", "admin_site.py")
        self.addActionLink(u"返回管理首页", "admin.py?cmd=main")
        #删除静态页面
        htmlGeneratorService = __spring__.getBean("htmlGeneratorService")
        htmlGeneratorService.SiteIndex()
        return ActionResult.SUCCESS

    def update_config_item(self, name, value):
        # 得到原配置项.
        config = self.cfg_svc.getConfigByItemTypeAndName("jitar", name)
        #print "config = ", config
        if config == None:
          self.addActionError(u"没有找到名字为 " + name + u" 的 jitar 配置项, 请确定您的数据库没有不正确的修改?")
          return
        
        SiteUrl = self.get_site_url()
        config.value = config.defval = value.replace("${SiteUrl}", SiteUrl)        
        self.cfg_svc.updateConfig(config)
        return
    
    def save_or_update_item(self,name, value):
        config = self.cfg_svc.getConfigByItemTypeAndName("jitar", name)
        if config == None:
            config = Config()
            config.setItemType("jitar")
            config.setName(name)
            config.setValue(value)
            config.setType("string")
            config.setDefval(value)
            config.setTitle(name)
            config.setDescription(value)
            self.cfg_svc.createConfig(config)
        else:
            self.update_config_item(name, value)      
        self.cfg_svc.reloadConfig()
    
    # 打印出当前所有配置
    def dump(self):
        out = response.writer
        print >> out, "<style>body{font-family:arial, '宋体';}</style>"
        print >> out, "<h2>Config dump (for debug)</h2>"
        cfg = self.cfg_svc.getConfigure()
        
        # 调试用途
        #print >>out, "<li>cfg = ", cfg
        
        for e in cfg:
            print >> out, "<li>cfg[%s] = %s" % (e, cfg[e])
        
        return None