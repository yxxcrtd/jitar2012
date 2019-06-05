<#if (usercate_tree.all)?? && ((usercate_tree.all)?size) &gt; 20>
<div style="height:30px;overflow:hidden;">
<div><span style="float:right"><a href="#" style="" onclick="CommonUtil.ExpandCollapse(this);return false;">展开</a></span><span style="float:left"><a href='${UserSiteUrl}'>个人首页</a> <a href='${UserSiteUrl}category/0.html'>所有文章</a></span></div>
<#list usercate_tree.all as c>
<div style="clear:both">${c.treeFlag2!} <a href='${UserSiteUrl}category/${c.id}.html'>${c.name?html}</a></div>
</#list>
</div>
<#else>
<div>
<a href='${UserSiteUrl}'>个人首页</a>
<a href='${UserSiteUrl}category/0.html'>所有文章</a>
<#list usercate_tree.all as c>
<div>${c.treeFlag2!} <a href='${UserSiteUrl}category/${c.id}.html'>${c.name?html}</a></div>
</#list>
</div>
</#if>