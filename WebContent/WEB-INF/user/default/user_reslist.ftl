<div>
	<ul class='listul'>
	<#list resource_list as r>
	  <li><a href='${SiteUrl}showResource.action?resourceId=${r.resourceId}' 
	    target='_blank'><img src='${Util.iconImage(r.href!)}' border='0' align='absmiddle' />
      ${r.title?html}</a> (${r.createDate})</li>
	</#list>
	</ul>	
	<div class='pagingDiv' id='pager_list' currPage='${pager.currentPage}' pageSize='${pager.pageSize}' totalRows='${pager.totalRows}'></div>
</div>
