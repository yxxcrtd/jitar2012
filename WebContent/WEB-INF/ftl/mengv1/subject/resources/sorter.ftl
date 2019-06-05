<div class='res_b1'>
  <div id="rank_" class='tab2'>
    <label style='width:46px;'>排行榜</label>            
    <div class="cur" onmouseover="TabUtil.changeTab('rank_',0)" style='padding-left:4px;padding-right:3px;'><a href='#'>资源上载排行</a></div>
    <div class="" onmouseover="TabUtil.changeTab('rank_',1)" style='padding-left:4px;padding-right:3px;'><a href='#'>资源下载排行</a></div>
  </div>    

  <div class='tab_content' style='padding:10px;'>
    <!-- 资源上载排行 -->
    <div id='rank_0' style='display:block'>
<#if upload_sorter?? >
	<table  cellpadding='1' cellpadding='1' width='210'>
	  <#list upload_sorter as user>
	  	<tr>
	  	<td class="rank_left">${user_index + 1}</td>
	  	<td class="rank_right">
	  	<span style='float:right'>${user.resourceCount!}</span>
	  	<span style='float:leftt'>
	  	<a href="${SiteUrl}go.action?loginName=${user.loginName}" target="_blank">${user.nickName}</a>
	  	</span>
	  	</td>
	  	</tr>
	  </#list>
	</table>
</#if>
    </div>
    
    <!-- 资源下载排行 -->
    <div id='rank_1' style='display:none'>
    <!-- 资源下载排行 -->
      <#if download_resource_list??>
      <table border='0' cellpadding='1' cellpadding='1' width='210'>
      <#list download_resource_list as r>
        <tr valign='top'>
        <td class="rank_left">${r_index + 1}</td>
        <td class="rank_right">
        <a href='showResource.action?resourceId=${r.resourceId}'>${r.title!?html}</a>
        </td>
        <td align='right'>${r.downloadCount!}</td>
        </tr>
      </#list>
       </table>
      <#else>
       暂无排行
      </#if>
    </div>
  </div>
</div>
