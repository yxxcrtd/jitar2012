<div class='gly'>
  <div class='gly_head'>
    <div class='gly_head_right'><a href='${UnitRootUrl}py/show_unit_news_list.py?type=3'>查看全部</a></div>
    <div class='gly_head_left'>&nbsp;${webpart.displayName}</div>
  </div>
  <div style='padding:4px;'>
    <#if unitPrepareCourseList??>
	<ul class='item_ul'>
		<#list unitPrepareCourseList as pc>
		<li><span style="float:rightl">[${pc.startDate?string("MM-dd")} ~ ${pc.endDate?string("MM-dd")}]</span><a href='${SiteUrl}p/${pc.prepareCourseId}/0/'>${pc.title}</a></li> 
		</#list>
	</ul>    
	</#if>
  </div>
</div>