<div style='text-align:center;padding:6px 0'>
<#-- 显示当前分页对象的分页 (需要pager变量) -->
共 ${pager.totalRows}&nbsp;&nbsp;${pager.itemUnit + pager.itemName}
 <#if pager.currentPage == 1>首页<#else><a href='${pager.firstPageUrl}'>首页</a></#if>
 <#if (pager.currentPage > 1)><a href='${pager.prevPageUrl}'>上一页</a><#else>上一页</#if>
 <#if (pager.currentPage < pager.totalPages)><a href='${pager.nextPageUrl}'>下一页</a><#else>下一页</#if>
 <#if (pager.currentPage != pager.totalPages) && (pager.totalPages != 0)><a href='${pager.lastPageUrl}'>尾页</a><#else>尾页</#if>
<#if (pager.currentPage != pager.totalPages)></#if>
 页次：<#if pager.currentPage == 0>1<#else>${pager.currentPage}</#if>/<#if pager.totalPages == 0>1<#else>${pager.totalPages}</#if> 页  #{pager.pageSize} ${pager.itemUnit + pager.itemName}/页 
<#if (pager.totalPages > 0)>转到: <select name='page' size='1' onchange="javascript:window.location='${pager.urlPattern}'.replace('{page}', this.options[this.selectedIndex].value);">
 <#list 1..pager.totalPages as i><option value='${i}' <#if i == pager.currentPage>selected</#if> >${i}</option></#list>
</#if>
</select>
</div>