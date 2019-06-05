#encoding=utf-8
from subject_page import *
from util import Util
from cn.edustar.jitar.util import CommonUtil
from admin_edit_content import AdminArticleEdit

class admin_article_edit(BaseSubject, Util, AdminArticleEdit):
    def __init__(self):
        BaseSubject.__init__(self)
        self.articleService = __spring__.getBean("articleService")
        self.userService = __spring__.getBean("userService")        
        
    def execute(self):        
        response.setContentType("text/html; charset=utf-8")
        if self.loginUser == None:
            return self.LOGIN
        
        if self.isAdmin == False and self.isContentAdmin() == False:
            self.addActionError(u"您你没有管理的权限！")
            return self.ERROR
        
        articleId = self.params.safeGetIntParam("articleId")
        article = self.articleService.getArticle(articleId)
        if article == None:
            self.addActionError(u"无法加载该文章。")
            return self.ERROR
        #检查是否可以管理文章
        if article.subjectId == None or article.gradeId == None:
            self.addActionError(u"该文章没有设置学科、学段属性，不属于本学科，无法进行管理。")
            return self.ERROR

        if self.checkIsSameGrade(article.gradeId, self.metaGradeId) == False:
            self.addActionError(u"该文章不属于本学科，无法进行管理。")
            return self.ERROR 
        
        if request.getMethod() == "POST":
            if self.edit_post(article) == False:
                self.addActionError(u"您输入的信息不全，请检查输入。")
                return self.ERROR
            
            self.addActionMessage(u"修改成功。")
            self.addActionLink(u"返回前页", "article.py?id=" + str(self.subject.subjectId))
            return self.SUCCESS            
                
        self.edit_get(article)
        
        return "/WEB-INF/subjectmanage/admin_article_edit.ftl"
