<div class='gly'>
  <div class='gly_head'>
    <div class='gly_head_right'><a href='${UnitRootUrl}py/show_unit_news_list.py?type=2'>查看全部</a></div>
    <div class='gly_head_left'>&nbsp;${webpart.displayName}</div>
  </div>
  <div style='padding:4px;'>
    <#if newest_news??>
	<ul class='item_ul'>
		<#list newest_news as news>
		<li><a href='${UnitRootUrl}py/show_news.py?unitNewsId=${news.unitNewsId}'>${news.title}</a></li> 
		</#list>
	</ul>    
	</#if>
  </div>
</div>