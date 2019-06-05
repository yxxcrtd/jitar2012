from cn.edustar.jitar.jython import BaseAdminAction
from cn.edustar.jitar.util import ParamUtil
from cn.edustar.jitar.pojos import Config, User
from base_action import ActionResult
from java.lang import String

# 站点配置管理
class site_screen(BaseAdminAction, ActionResult):
    # 得到配置服务
    def __init__(self):
        self.cfg_svc = __spring__.getBean('configService')


    # 执行主入口
    def execute(self):
        if self.loginUser == None: 
            return ActionResult.LOGIN
        
        accessControlService = __spring__.getBean("accessControlService")
        if accessControlService.isSystemAdmin(self.loginUser) == False:
            self.addActionError(u"只有超级管理员才能进行管理。")
            return ActionResult.ERROR
        
        self.params = ParamUtil(request)
        cmd = self.params.getStringParam("cmd")
        
        if cmd == "save":
            return self.save()
        else:
            return self.list()
  
    # 列出当前配置
    def list(self):
        config = self.cfg_svc.getConfigure()
        request.setAttribute("config", config)
        return "/WEB-INF/ftl/admin/Site_Screen_Option.ftl"


    def get_site_url(self):
        return request.getScheme() + "://" + request.getServerName() + ":" + str(request.getServerPort()) + request.getContextPath() + "/"
  

        # 保存对配置的修改
    def save(self):
        accessControlService = __spring__.getBean("accessControlService")
        if accessControlService.isSystemAdmin(self.loginUser) == False:
            self.addActionError(u"没有修改系统配置的权限, 只有系统管理员才能修改配置！")
            return ActionResult.ERROR
        
        # 屏蔽系统中出现的非法词汇
        self.update_config_item("site.screen.enalbed", self.params.getStringParam("site.screen.enalbed"))
        self.update_config_item("site.screen.replace", self.params.getStringParam("site.screen.replace"))
        self.update_config_item("site.screen.keyword", self.params.getStringParam("site.screen.keyword"))
    
        # 最后再重新加载一下配置.
        self.cfg_svc.reloadConfig()
        self.addActionMessage(u"配置修改成功完成！")
        self.addActionLink(u"返回站点配置", "site_screen.py")
        return ActionResult.SUCCESS
  
  
        # 更新一个配置项
    def update_config_item(self, name, value):
        # 得到原配置项.
        config = self.cfg_svc.getConfigByItemTypeAndName("jitar", name)
        if config == None:
            self.addActionError(u"没有找到名字为 " + name + u" 的 jitar 配置项, 请确定您的数据库没有不正确的修改?")
            return
        SiteUrl = self.get_site_url()
        config.value = value.replace("${SiteUrl}", SiteUrl)
        self.cfg_svc.updateConfig(config)
        return

