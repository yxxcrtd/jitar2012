#encoding=utf-8
from unit_page import UnitBasePage
from util import Util
from cn.edustar.jitar.util import CommonUtil
from admin_edit_content import AdminArticleEdit

class admin_article_edit(UnitBasePage, Util, AdminArticleEdit):
    def __init__(self):
        UnitBasePage.__init__(self)
        self.articleService = __spring__.getBean("articleService")
    
    def execute(self):
        if self.loginUser == None:
            return self.LOGIN
        
        self.unit = self.getUnit()
        if self.unit == None:
            self.addActionError(u"您所访问的机构不存在！")
            return self.ERROR
        
        if self.isContentAdmin() == False:
            self.addActionError(u"你没有管理的权限。")
            return self.ERROR
        
        articleId = self.params.safeGetIntParam("articleId")
        article = self.articleService.getArticle(articleId)
        if article == None:
            self.addActionError(u"无法加载该文章。")
            return self.ERROR
        
        refer = self.params.safeGetStringParam("from")
        #检查是否可以管理文章
        if article.unitId == None or article.orginPath == None:
            self.addActionError(u"该文章没有设置单位属性，无法进行管理。")
            return self.ERROR
            
        if refer == "owner":
            #自己单位的文章    
            if article.unitId != self.unit.unitId and article.orginPath.find("/" + str(self.unit.unitId) + "/") == -1:
                self.addActionError(u"该文章不属于本单位的管辖范围，无法进行管理。")
                return self.ERROR
        elif refer == "push":
            #下级推送上来的文章
            if article.unitPathInfo == None or article.unitPathInfo.find("/" + str(self.unit.unitId) + "/") == -1:
                self.addActionError(u"该文章不属于下级推送的文章，无法进行管理。")
                return self.ERROR
        elif refer == "down":
            if article.orginPath.find("/" + str(self.unit.unitId) + "/") == -1:
                self.addActionError(u"该文章不属于下级的文章，无法进行管理。")
                return self.ERROR
        else:
            self.addActionError(u"无效的管理来源。")
            return self.ERROR
        
        if request.getMethod() == "POST":
            if self.edit_post(article) == False:
                self.addActionError(u"您输入的信息不全，请检查输入。")
                return self.ERROR
            
            self.addActionMessage(u"修改成功。")
            
            redUrl = ""
            if refer == "owner":
                redUrl = "unit_owner_article.py?unitId=" + str(self.unit.unitId)
            elif refer == "push":
                redUrl = "unit_push_article.py?unitId=" + str(self.unit.unitId)
            elif refer == "down":
                redUrl = "unit_down_article.py?unitId=" + str(self.unit.unitId)
            else:
                self.addActionError(u"无效的管理来源，无法进行返回定位。")
                return self.ERROR
            
            self.addActionLink(u"返回前页", redUrl)
            return self.SUCCESS

        
        self.edit_get(article)
        return "/WEB-INF/unitsmanage/admin_article_edit.ftl"
