from cn.edustar.jitar.util import ParamUtil
from cn.edustar.jitar.pojos import Group
from cn.edustar.jitar.util import CommonUtil

group_svc = __jitar__.groupService
cat_svc = __jitar__.categoryService

class group_cate_article:
    def execute(self):
        response.setContentType("text/html; charset=UTF-8")
        groupName = request.getAttribute("groupName")
        if groupName == None or groupName == "":
            return ""
        
        group = group_svc.getGroupByName(groupName)
        if group == None:
            return self.notFound()
        
        itemType = CommonUtil.toGroupArticleCategoryItemType(group.getGroupId())
        category_tree = cat_svc.getCategoryTree(itemType)
        request.setAttribute("category_tree", category_tree)
        request.setAttribute("group", group)
        return "/WEB-INF/group/default/group_cate_article.ftl"