from cn.edustar.jitar.util import ParamUtil
from base_action import BaseAction
from java.util import HashMap
from java.util import ArrayList
from base_channel_page import ChannelPage

class channel_unitlist(ChannelPage):
    def __init__(self):
        ChannelPage.__init__(self)
        self.unitTypeService = __spring__.getBean("unitTypeService")
        self.unitService = __spring__.getBean("unitService")
        self.params = ParamUtil(request)
        
    def execute(self):
        channelId = self.params.safeGetIntParam("channelId")
        if channelId == 0:
            self.addActionError(u"您所访问的页面不存在！")
            return ActionResult.ACCESS_ERROR
        self.channel = self.channelPageService.getChannel(channelId)
        if self.channel == None:
            self.addActionError(u"无法加载指定的页面！")
            return ActionResult.ACCESS_ERROR
        head_nav = "channel"

        map = HashMap()
        map.put("channel", self.channel)
        unitLists = []
        unittypeIds = self.params.safeGetStringParam("unittypeId")
        if unittypeIds.find(',') > 0 :
            aunittypeIds = unittypeIds.split(',')
            unitType = ""
            for tId in aunittypeIds:
                unittypeId = int(tId)
                oUnitType = self.unitTypeService.getUnitTypeById(unittypeId)
                if oUnitType != None:
                    unitType1 = oUnitType.unitTypeName
                    unlitList = self.unitService.getChildUnitListByUnitType(unitType1)
                    unitType = unitType + unitType1
                    if unlitList != None :
                        unitLists.append(unlitList)
            map.put("unlitList", unitLists)
            map.put("unitType", unitType)
        else:    
            unittypeId = int(unittypeIds)
            oUnitType = self.unitTypeService.getUnitTypeById(unittypeId)
            unitType=""
            if oUnitType != None:
                unitType = oUnitType.unitTypeName
                unlitList = self.unitService.getChildUnitListByUnitType(unitType)
                if unlitList != None:
                    unitLists.append(unlitList)
            map.put("unlitList", unitLists)
            map.put("unitType", unitType)
        if self.channel.skin == None :
            skin = "template1"
        elif self.channel.skin == "" :
            skin = "template1"
        else:
            skin = self.channel.skin

        mainContent = self.templateProcessor.processTemplate(map, "/WEB-INF/channel/" + skin + "/channel_unitlist.ftl", "utf-8")
        headerContent = self.GenerateContentFromTemplateString(self.channel.headerTemplate, head_nav)
        footerContent = self.GenerateContentFromTemplateString(self.channel.footerTemplate)
        indexPageTemplate = self.GenerateContentFromTemplateString(self.channel.indexPageTemplate)

        out = response.getWriter()
        if headerContent == "" and footerContent == "" and mainContent == "":
            out.println(u"该频道没有指定模板，无法显示页面内容。")
            return
        out.println(headerContent)
        out.println(mainContent)
        out.println(footerContent)