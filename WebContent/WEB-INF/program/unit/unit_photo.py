from unit_page import *
from photo_query import PhotoQuery

class unit_photo(UnitBasePage):
    def __init__(self):
        UnitBasePage.__init__(self)
        
    def execute(self):
        
        self.unit = self.getUnit()
        if self.unit == None:
            self.addActionError(u"您所访问的机构不存在！")
            return self.ERROR       
        if self.unit.delState == True:
            self.addActionError(u"您所访问的机构已经被删除！")
            return self.ERROR
                
        self.get_photo_root_category()
        self.get_unit_photo_list()
        
        request.setAttribute("head_nav", "unit_photo")
        request.setAttribute("unit", self.unit)
        request.setAttribute("type", "new")    
    
        templateName = "template1"
        if self.unit.templateName != None:
            templateName = self.unit.templateName
        return "/WEB-INF/unitspage/" + templateName + "/unit_photo.ftl"
    
    # 得到图片分类的根分类, 如果想显示所有分类, 把 for c in photo_categories.root 变成 for c in photo_categories 即可.
    def get_photo_root_category(self):
        photo_categories = __jitar__.categoryService.getCategoryTree('photo')
        #print "photo_categories = ", photo_categories
        root_cates = []
        for c in photo_categories.root:
            root_cates.append({'categoryId': c.categoryId, 'categoryName': c.name,'parentId':c.parentId })
        request.setAttribute("photo_cates", root_cates)
        return root_cates
    
    def get_unit_photo_list(self):        
        qry = PhotoQuery(""" p.photoId, p.title, p.createDate, p.href, u.userId, u.loginName, u.nickName, p.summary """)
        qry.unitId = self.unit.unitId
        pager = self.createPager()        
        pager.totalRows = qry.count()
        photo_list = qry.query_map(pager)
        request.setAttribute("photo_list", photo_list)
        request.setAttribute("pager", pager)
        
    def createPager(self):
        pager = self.params.createPager()
        pager.itemName = u"图片"
        pager.itemUnit = u"张"
        pager.pageSize = 20
        return pager