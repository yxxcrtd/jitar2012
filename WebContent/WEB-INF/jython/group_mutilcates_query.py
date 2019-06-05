# -*- coding: utf-8 -*-
from cn.edustar.jitar.pojos import Group
from cn.edustar.jitar.service import PageKey
from cn.edustar.jitar.pojos import User
from cn.edustar.jitar.pojos import GroupMutil
from cn.edustar.jitar.pojos import Article, Resource, Photo, Video
from cn.edustar.jitar.util import CommonUtil
from cn.edustar.jitar.data import BaseQuery
from cn.edustar.jitar.util import ParamUtil

class GroupMutilcatesQuery(BaseQuery):   
    def __init__(self, selectFields):
        BaseQuery.__init__(self, selectFields)
        self.params = ParamUtil(request)
        self.cate_svc = __jitar__.categoryService
        
        #widgetId是指页面中的某个模块标示，支持同一页面多个同类模块下可以显示不同的内容
        self.widgetId = 0
        
        #群组的文章资源图片视频分类的Id,这4个群组分类的Id是绑定在群组的Id
        self.articleCateId=None
        self.resourceCateId=None
        self.videoCateId=None
        self.photoCateId=None
        
        #支持按照群组分类的的分类名来查询
        self.articleCateName=None
        self.resourceCateName=None
        self.videoCateName=None
        self.photoCateName=None
        #某一类群组 例如 课题组的类型Id [主要用于总站首页的 课题组的汇总]，需要和上面4个分类名绑定一起使用
        self.groupCateId=None
        
        #群组的父一级的组Id [主要用于某个课题组的资源聚合]
        self.groupPrentId=None
        
        
        self.orderType=0
        # 查询关键字. 
        self.k = self.params.safeGetStringParam("k", None)
        
    def initFromEntities(self, qctx):
        qctx.addEntity("Group", "g", "")
        if self.videoCateId!=None or self.videoCateName!=None:
            qctx.addEntity("Video", "v", "")
            qctx.addEntity("GroupVideo", "gv", "gv.videoId=v.videoId and g.groupId=gv.groupId")
            if self.widgetId != 0:
                qctx.addEntity("GroupMutil", "gm", "gm.videoCateId = gv.groupCateId")
        if self.photoCateId!=None or self.photoCateName!=None:
            qctx.addEntity("Photo", "p", "")
            qctx.addEntity("GroupPhoto", "gp", "gp.photoId=p.photoId and g.groupId=gp.groupId")
            if self.widgetId != 0:
                qctx.addEntity("GroupMutil", "gm", "gm.photoCateId = gp.groupCateId")
        if self.articleCateId!=None or self.articleCateName!=None:
            qctx.addEntity("Article", "a", "")
            qctx.addEntity("GroupArticle", "ga", "ga.articleId=a.articleId and g.groupId=ga.groupId")
            if self.widgetId != 0:
                qctx.addEntity("GroupMutil", "gm", "gm.articleCateId = ga.groupCateId")
        if self.resourceCateId!=None or self.resourceCateName!=None:
            qctx.addEntity("Resource", "r", "")
            qctx.addEntity("GroupResource", "gr", "gr.resourceId=r.resourceId and g.groupId=gr.groupId")
            if self.widgetId != 0:
                qctx.addEntity("GroupMutil", "gm", "gm.resourceCateId = gr.groupCateId")
        
    # 提供 where 条件.
    def applyWhereCondition(self, qctx):
        if self.k != None and self.k != "":
            newKey = self.k.replace("'","''").replace("%","[%]").replace("_","[_]").replace("[","[[]")
            if self.articleCateId!=None:
                qctx.addAndWhere("a.title LIKE :likeKey")
                qctx.setString("likeKey", "%" + newKey + "%")
            elif self.resourceCateId!=None:
                qctx.addAndWhere("r.title LIKE :likeKey")
                qctx.setString("likeKey", "%" + newKey + "%")
            elif self.videoCateId!=None:
                qctx.addAndWhere("v.title LIKE :likeKey")
                qctx.setString("likeKey", "%" + newKey + "%")
            elif self.photoCateId!=None:
                qctx.addAndWhere("p.title LIKE :likeKey")
                qctx.setString("likeKey", "%" + newKey + "%")
        if self.articleCateId!=None:
            #只查询分类自己的
            #qctx.addAndWhere("ga.groupCateId = :cateId")
            #qctx.setInteger("cateId", self.articleCateId)
            #查询包含子孙分类的
            #print "self.articleCateId="+str(self.articleCateId)
            list=self.cate_svc.getCategoryIds(self.articleCateId)
            cateIds=""
            for c in list:
                if cateIds=="":
                    cateIds=cateIds+str(c)
                else:
                    cateIds=cateIds+","+str(c)
            qctx.addAndWhere("ga.groupCateId IN (" + cateIds +")")
            
        elif self.resourceCateId!=None:
            #只查询分类自己的
            #qctx.addAndWhere("gr.groupCateId = :cateId")
            #qctx.setInteger("cateId", self.resourceCateId)
            #查询包含子孙分类的
            #print "self.resourceCateId="+str(self.resourceCateId)
            list=self.cate_svc.getCategoryIds(self.resourceCateId)
            cateIds=""
            for c in list:
                if cateIds=="":
                    cateIds=cateIds+str(c)
                else:
                    cateIds=cateIds+","+str(c)
            qctx.addAndWhere("gr.groupCateId IN (" + cateIds +")")
            
        elif self.videoCateId!=None:
            #只查询分类自己的
            #qctx.addAndWhere("gv.groupCateId = :cateId")
            #qctx.setInteger("cateId", self.videoCateId)
            #查询包含子孙分类的
            #print "self.videoCateId="+str(self.videoCateId)
            list=self.cate_svc.getCategoryIds(self.videoCateId)
            cateIds=""
            for c in list:
                if cateIds=="":
                    cateIds=cateIds+str(c)
                else:
                    cateIds=cateIds+","+str(c)
            qctx.addAndWhere("gv.groupCateId IN (" + cateIds +")")
            
        elif self.photoCateId!=None:
            #只查询分类自己的
            #qctx.addAndWhere("gp.groupCateId = :cateId")
            #qctx.setInteger("cateId", self.photoCateId)
            #查询包含子孙分类的
            #print "self.photoCateId="+str(self.photoCateId)
            list=self.cate_svc.getCategoryIds(self.photoCateId)
            cateIds=""
            for c in list:
                if cateIds=="":
                    cateIds=cateIds+str(c)
                else:
                    cateIds=cateIds+","+str(c)
            qctx.addAndWhere("gp.groupCateId IN (" + cateIds +")")
            
        elif self.articleCateName!=None:
            #qctx.addAndWhere("gm.articleCateId In(SELECT categoryId FROM cn.edustar.jitar.pojos.Category WHERE name=:catename)")
            qctx.addAndWhere("ga.groupCateId In(SELECT categoryId FROM cn.edustar.jitar.pojos.Category WHERE name=:catename)")
            qctx.setString("catename", self.articleCateName)
            if self.groupCateId!=None:
                qctx.addAndWhere("ga.groupId IN (SELECT groupId FROM cn.edustar.jitar.pojos.Group WHERE categoryId=:categoryId )")
                qctx.setInteger("categoryId", self.groupCateId)
        elif self.resourceCateName!=None:
            #qctx.addAndWhere("gm.resourceCateId In(SELECT categoryId FROM cn.edustar.jitar.pojos.Category WHERE name=:catename)")
            qctx.addAndWhere("gr.groupCateId In(SELECT categoryId FROM cn.edustar.jitar.pojos.Category WHERE name=:catename)")
            qctx.setString("catename", self.resourceCateName)
            if self.groupCateId!=None:
                qctx.addAndWhere("gr.groupId IN (SELECT groupId FROM cn.edustar.jitar.pojos.Group WHERE categoryId=:categoryId )")
                qctx.setInteger("categoryId", self.groupCateId)
        elif self.videoCateName!=None:
            #qctx.addAndWhere("gm.videoCateId In(SELECT categoryId FROM cn.edustar.jitar.pojos.Category WHERE name=:catename)")
            qctx.addAndWhere("gv.groupCateId In(SELECT categoryId FROM cn.edustar.jitar.pojos.Category WHERE name=:catename)")
            qctx.setString("catename", self.videoCateName)
            if self.groupCateId!=None:
                qctx.addAndWhere("gv.groupId IN (SELECT groupId FROM cn.edustar.jitar.pojos.Group WHERE categoryId=:categoryId )")
                qctx.setInteger("categoryId", self.groupCateId)
        elif self.photoCateName!=None:
            #qctx.addAndWhere("gm.photoCateId In(SELECT categoryId FROM cn.edustar.jitar.pojos.Category WHERE name=:catename)")
            qctx.addAndWhere("gp.groupCateId In(SELECT categoryId FROM cn.edustar.jitar.pojos.Category WHERE name=:catename)")
            qctx.setString("catename", self.photoCateName)
            if self.groupCateId!=None:
                qctx.addAndWhere("gp.groupId IN (SELECT groupId FROM cn.edustar.jitar.pojos.Group WHERE categoryId=:categoryId )")
                qctx.setInteger("categoryId", self.groupCateId)
        if self.groupPrentId!=None:
            qctx.addAndWhere("g.groupId=:grp_Id Or g.groupId IN (SELECT groupId FROM cn.edustar.jitar.pojos.Group WHERE parentId=:parentId )")
            qctx.setInteger("grp_Id", self.groupPrentId)
            qctx.setInteger("parentId", self.groupPrentId)
        elif self.widgetId != 0:
            qctx.addAndWhere("gm.widgetId = :widgetId")
            qctx.setInteger("widgetId", self.widgetId)
            
    # 提供排序 order 条件.
    def applyOrderCondition(self, qctx):
        if self.orderType == 0:
            if self.articleCateId!=None or self.articleCateName!=None:
                qctx.addOrder("g.groupId DESC,a.articleId DESC")
            elif self.resourceCateId!=None or self.resourceCateName!=None:
                qctx.addOrder("g.groupId DESC,r.resourceId DESC")
            elif self.videoCateId!=None or self.videoCateName!=None:
                qctx.addOrder("g.groupId DESC,v.videoId DESC")
            elif self.photoCateId!=None or self.photoCateName!=None:
                qctx.addOrder("g.groupId DESC,p.photoId DESC")
            else:
                qctx.addOrder("g.groupId DESC")
