<div class='gly'>
  <div class='gly_head'>
    <div class='gly_head_right'><a href='${UnitRootUrl}py/show_unit_news_list.py?type=4'>查看全部</a></div>
    <div class='gly_head_left'>&nbsp;${webpart.displayName}</div>
  </div>
  <div style='padding:4px;'>
    <#if unitGroupList??>
	<ul class='item_ul'>
		<#list unitGroupList as g>
		<li><a href='${SiteUrl}g/${g.groupName}'>${g.groupTitle}</a></li> 
		</#list>
	</ul>    
	</#if>
  </div>
</div>