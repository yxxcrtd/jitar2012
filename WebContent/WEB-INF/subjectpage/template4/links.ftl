<div class='panel'>
  <div class='panel_head'>
    <div class='panel_head_left'>${webpart.displayName!?html}</div>
  </div>
  <div class='panel_content'>
    <#if links??>
	<ul class='item_ul'>
		<#list links as link>
		<li><a target='_blank' href='${link.linkHref}'>${link.linkTitle!?html}</a></li> 
		</#list>
	</ul>    
	</#if>
  </div>
</div>