<div class='panel'>
  <div class='panel_head'>
    <div class='panel_head_right'></div>
    <div class='panel_head_left titlecolor'> 工作室访问排行</div>
  </div>
  <div class='panel_content'>
	<#if hot_list?? >
	<table  cellpadding='1' cellpadding='1' width='220'>
	  <#list hot_list as u>
	  	<tr valign='top'>
	  	<td class="rank_left">${u_index + 1}</td>
	  	<td class="rank_right"><a href='${SiteUrl}go.action?loginName=${u.loginName}'>${u.blogName}</a></td>
	  	<td style='text-align:right;'>${u.visitCount}</td>
	  	</tr>
	  </#list>
	</table>
	</#if >
  </div>
</div>