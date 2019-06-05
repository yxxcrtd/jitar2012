<div>
	<div><a href='${SiteUrl}g/${group.groupName}/artcate/0.html'>所有文章</a></div>
	<#if category_tree??>
	<#list category_tree.all as c>
	<div>${c.treeFlag!} <a href='${SiteUrl}g/${group.groupName}/artcate/${c.categoryId}.html'>${c.name!?html}</a></div>
	</#list>
	</#if>
</div>