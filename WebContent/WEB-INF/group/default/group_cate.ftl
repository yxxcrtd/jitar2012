<div>
	<div><a href='${SiteUrl}g/${group.groupName}/rescate/0.html'>所有资源</a></div>
<#list category_tree.all as c>
  <div>${c.treeFlag!} <a href='${SiteUrl}g/${group.groupName}/rescate/${c.categoryId}.html'>${c.name!?html}</a></div>
</#list>
</div>