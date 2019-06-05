from cn.edustar.jitar.util import ParamUtil
from tag_query import TagQuery

# 获取数据脚本.
class tags:
  def execute(self):
    tag_list = self.get_tag_list()
    request.setAttribute("tag_list", tag_list)
        
    # 页面导航高亮为 'tags'
    request.setAttribute("head_nav", "tags")
     
    return "/WEB-INF/ftl/site_tags_v2.ftl"

  def get_tag_list(self):
    # 投影查询字段.
    qry = TagQuery(""" tag.tagId, tag.tagName, tag.refCount, tag.refCount, tag.viewCount """)
    qry.disabled = False
    param = ParamUtil(request)
    pager = param.createPager()
    pager.itemName = u"标签"
    pager.itemUnit = u"个"
    pager.totalRows = qry.count()
    pager.pageSize = 20
    tag_list = qry.query_map(pager)
    request.setAttribute("pager", pager)
    return tag_list

    #最大和最小refCount.
    MaxRefCount = 1
    MinRefCount = 1
    
    # 产生随机标签大小, 用于构造出标签云, 页面上写 style='font-size:${fontSize} px'.
    for tag in tag_list:
      if MaxRefCount < tag["refCount"] : MaxRefCount = tag["refCount"] 
      if MinRefCount > tag["refCount"] : MinRefCount = tag["refCount"]
    
    #print "MinRefCount = " ,MinRefCount
    #print "MaxRefCount = " ,MaxRefCount
    for tag in tag_list:
        if MaxRefCount == MinRefCount :
            tag["fontSize"] = 0
        else :
            tag["fontSize"] = ((tag["refCount"] - MinRefCount) * 6) / (MaxRefCount - MinRefCount)        
        if tag["fontSize"] < 0 : tag["fontSize"] = 0
        if tag["fontSize"] > 6 : tag["fontSize"] = 6

    # DEBUG: print "tag_list = ", tag_list
    return tag_list