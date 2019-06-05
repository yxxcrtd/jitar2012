from base_action import *
from base_action import ActionResult
from cn.edustar.jitar.pojos import User
from cn.edustar.jitar.jython import BaseAdminAction
from cn.edustar.jitar.util import CommonUtil
from cn.edustar.jitar.util import ParamUtil
from cn.edustar.jitar.pojos import ContentSpace
from java.util import Date
 
class usercate(BaseAdminAction):
    # 定义要返回的页面常量.
    ADMIN_MAIN = "/WEB-INF/ftl/admin/main.ftl"
    ERROR = "/WEB-INF/ftl/Error.ftl"    
    SUCCESS = "/WEB-INF/ftl/success.ftl"
    
    def __init__(self):
        self.params = ParamUtil(request)
        self.contentSpaceService = __spring__.getBean("contentSpaceService")
        self.categoryService = __spring__.getBean("categoryService")
    def execute(self):
        user = self.loginUser
        if user == None:
            return ActionResult.LOGIN
        accessControlService = __spring__.getBean("accessControlService")
        if accessControlService.isSystemAdmin(user) == False:
            self.addActionError(u"没有管理权限，只有超级管理员才可以使用此功能。")
            return ActionResult.ERROR

        if self.contentSpaceService == None:
            self.addActionError(u"无法获得 contentSpaceService 服务，检查配置文件。")
            return ActionResult.ERROR
        if request.getMethod() == "POST":
            cmd = self.params.safeGetStringParam("cmd")
            if cmd == "add":
                spaceName = self.params.safeGetStringParam("space_name")
                if spaceName == "":
                    self.addActionError(u"请输入自定义分类的名称。")
                    return ActionResult.ERROR
                contentSpace = ContentSpace()
                contentSpace.setSpaceName(spaceName)
                contentSpace.setCreateDate(Date())
                contentSpace.setCreateUserId(self.loginUser.userId)
                contentSpace.setOwnerType(ContentSpace.CONTENTSPACE_OWNERTYPE_DEFAULT)
                contentSpace.setOwnerId(0)
                self.contentSpaceService.saveOrUpdateContentSpace(contentSpace)
                self.addActionMessage(u"您成功创建了一个自定义文章分类： " + spaceName + u"。")
                self.addActionLink(u"返回", "usercate.py")
                return ActionResult.SUCCESS
            elif cmd == "delete":
                guids = self.params.safeGetIntValues("guid")
                for g in guids:
                    childList = self.contentSpaceService.getContentSpaceList(g)
                    if childList != None :
                        if childList.size() > 0 :
                            self.addActionError(u"分类下存在子分类，不允许删除该分类。")
                            return ActionResult.ERROR
                    contentSpace = self.contentSpaceService.getContentSpaceById(g)
                    if contentSpace != None:
                        # 删除文章
                        self.contentSpaceService.deleteContentSpaceArticleByContentSpaceId(contentSpace.contentSpaceId)
                        self.contentSpaceService.deleteContentSpace(contentSpace)
                self.addActionMessage(u"删除成功。")
                self.addActionLink(u"返回", "usercate.py")
                return ActionResult.SUCCESS
            else:
                self.addActionError(u"无效的命令。")
                return ActionResult.ERROR
        
        #contentSpaceList = self.contentSpaceService.getAllContentSpaceList(ContentSpace.CONTENTSPACE_OWNERTYPE_DEFAULT, 0)
        #request.setAttribute("contentSpaceList", contentSpaceList)
        #return "/WEB-INF/ftl/admin/usercate.ftl"
        
        
        #得到了categoryList
        categoryList = self.contentSpaceService.getContentSpaceTreeList(ContentSpace.CONTENTSPACE_OWNERTYPE_DEFAULT, 0)
        #需要把categoryList转换为CategoryTreeModel
        category_tree = self.categoryService.getCategoryTree(categoryList);
        request.setAttribute("category_tree", category_tree)
        return "/WEB-INF/ftl/admin/usercatetree.ftl"
