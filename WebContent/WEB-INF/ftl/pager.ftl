<div style='text-align:center;padding:6px 0'>
<#-- 显示当前分页对象的分页 (需要pager变量) -->
共 ${pager.totalRows}&nbsp;&nbsp;${pager.itemUnit + pager.itemName}
 <#if pager.currentPage == 1>首页<#else><a href='${pager.firstPageUrl}'>首页</a></#if>
 <#if (pager.currentPage > 1)><a href='${pager.prevPageUrl}'>上一页</a><#else>上一页</#if>
 <#if (pager.currentPage < pager.totalPages)><a href='${pager.nextPageUrl}'>下一页</a><#else>下一页</#if>
 <#if (pager.currentPage != pager.totalPages) && (pager.totalPages != 0)><a href='${pager.lastPageUrl}'>尾页</a><#else>尾页</#if>
 页次：${pager.currentPage}/${pager.totalPages} 页  #{pager.pageSize} ${pager.itemUnit + pager.itemName}/页 
<#if (pager.totalPages > 0)>转到页面:<input id='__pg' style='width:60px' value='${pager.currentPage}' /> <input type='button' value='查看' onclick="window.location='${pager.urlPattern}'.replace('{page}', document.getElementById('__pg').value);" /></#if>
</div>