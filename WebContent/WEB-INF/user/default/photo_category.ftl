<#if (photocate)?? && (photocate?size) &gt; 20>
<div style="height:30px;overflow:hidden;">
<div><span style="float:right"><a href="#" style="" onclick="CommonUtil.ExpandCollapse(this);return false;">展开</a></span><span style="float:left"><a href='${UserSiteUrl}photocate/0.html'>所有分类</a></span></div>
<#list photocate as c>
<div style="clear:both"><a href='${UserSiteUrl}photocate/${c.id}.html'>${c.title?html}</a></div>
</#list>
</div>
<#else>
<div>
<div><a href='${UserSiteUrl}photocate/0.html'>所有分类</a></div>
<#list photocate as c>
  <div><a href='${UserSiteUrl}photocate/${c.id}.html'>${c.title?html}</a></div>
</#list>
</div>
</#if>