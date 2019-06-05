<div class='gly'>
  <div class='gly_head'>    
    <div class='gly_head_left'>&nbsp;${webpart.displayName}</div>
  </div>
  <div style='padding:4px;'>
    <#if links??>
	<ul class='item_ul'>
		<#list links as link>
		<li><a href='${link.linkAddress}' target='_blank'>${link.linkName}</a></li> 
		</#list>
	</ul>    
	</#if>
  </div>
</div>