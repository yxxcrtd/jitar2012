#encoding=utf-8
from cn.edustar.jitar.util import CommonUtil
from cn.edustar.jitar.util import ParamUtil
from cn.edustar.jitar.pojos import SpecialSubjectArticle
from cn.edustar.jitar.pojos import ChannelArticle

class AdminArticleEdit:
    categoryService = __spring__.getBean("categoryService")
    subjectService = __spring__.getBean("subjectService")
    specialSubjectService = __spring__.getBean("specialSubjectService")
    channelPageService = __spring__.getBean("channelPageService")
    userService = __spring__.getBean("userService")
    
    ErrorMessage = ""
    
    def edit_get(self, article):
        self.putGradeList()
        self.putSubjectList()
        self.putUserArticleCategory(article.userId)
        self.putSpecialSubjectCategory(article)
        article_categories = self.categoryService.getCategoryTree('default')
        request.setAttribute("article_categories", article_categories)
        request.setAttribute("article", article)
        # 加载专题和自定义频道等等
        specialSubjectArticle = self.specialSubjectService.getSpecialSubjectArticleByArticleId(article.objectUuid)
        if specialSubjectArticle == None:
            request.setAttribute("specialSubjectId", 0)
        else:
            request.setAttribute("specialSubjectId", specialSubjectArticle.specialSubjectId)
        
        
    def edit_post(self, article):        
        self.params = ParamUtil(request)
        articleTitle = self.params.safeGetStringParam("articleTitle")
        gradeId = self.params.getIntParamZeroAsNull("gradeId")
        subjectId = self.params.getIntParamZeroAsNull("subjectId")
        sysCate = self.params.getIntParamZeroAsNull("sysCate")
        userCate = self.params.getIntParamZeroAsNull("userCate")
        specialSubjectId = self.params.safeGetIntParam("specialSubjectId")
        articleTags = self.params.getStringParam("articleTags", None)
        articleAbstract = self.params.getStringParam("articleAbstract", None)
        articleContent = self.params.safeGetStringParam("articleContent")
        commentState = self.params.safeGetIntParam("commentState")
        hideState = self.params.safeGetIntParam("hideState")      
        if articleTitle == "" or articleContent == "":
            # 无法解决编码问题，暂时不返回错误信息了。
            # self.ErrorMessage = "文章标题不能为空。"
            return False
        
        article.setTitle(articleTitle)
        article.setArticleContent(articleContent)
        article.setArticleAbstract(articleAbstract)
        article.setArticleTags(articleTags)
        article.setSubjectId(subjectId)
        article.setGradeId(gradeId)
        article.setUserCateId(userCate)
        article.setSysCateId(sysCate)
        article.setCommentState(commentState)
        article.setHideState(hideState)
        articleService = __spring__.getBean("articleService")
        articleService.updateArticle(article)        
      
        specialSubjectArticle = self.specialSubjectService.getSpecialSubjectArticleByArticleId(article.objectUuid)
        if specialSubjectId == 0:
            if specialSubjectArticle != None:
                self.specialSubjectService.deleteSpecialSubjectArticle(specialSubjectArticle)
        else:                
            if specialSubjectArticle == None:
                specialSubjectArticle = SpecialSubjectArticle()
                specialSubjectArticle.articleId = article.articleId
                specialSubjectArticle.articleGuid = article.objectUuid
                specialSubjectArticle.specialSubjectId = specialSubjectId
                specialSubjectArticle.createDate = article.createDate
                specialSubjectArticle.userId = article.userId
                specialSubjectArticle.loginName = article.loginName
                specialSubjectArticle.userTrueName = article.userTrueName
                specialSubjectArticle.articleState = 1
                
            specialSubjectArticle.setTitle(articleTitle)
            self.specialSubjectService.saveOrUpdateSpecialSubjectArticle(specialSubjectArticle)
        specialSubjectArticle = None
        return True
    
    def putUserArticleCategory(self, userId):
        uc_itemType = CommonUtil.toUserArticleCategoryItemType(userId)
        usercate_tree = self.categoryService.getCategoryTree(uc_itemType)
        request.setAttribute("usercate_tree", usercate_tree)
        
    def putSpecialSubjectCategory(self, article):
        ss = self.specialSubjectService.getValidSpecialSubjectList()
        if ss != None:
            request.setAttribute("specialsubject_list", ss)
            specialSubjectArticle = self.specialSubjectService.getSpecialSubjectArticleByArticleId(article.objectUuid)
            if specialSubjectArticle != None:
                request.setAttribute("specialSubjectId", specialSubjectArticle.specialSubjectId)
            else:
                request.setAttribute("specialSubjectId", 0)
        
    def putSubjectList(self):
        subject_list = self.subjectService.getMetaSubjectList()
        request.setAttribute("subject_list", subject_list)
    def putGradeList(self):
        grade_list = self.subjectService.getGradeList()
        request.setAttribute("grade_list", grade_list);
