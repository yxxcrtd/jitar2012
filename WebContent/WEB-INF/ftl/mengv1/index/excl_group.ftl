<#-- 优秀团队, best_group_list -->
<div class='m2'>
<div class='m1_head'>
  <div class='m1_head_right'><a href='groups.action?type=best'>更多…</a></div>
  <div class='m1_head_left'>&nbsp;<img src='${ContextPath}css/index/j.gif' />&nbsp;优秀团队</div>
</div>
<div class='m1_content'>
<#if best_group_list?? >
  <div class='tdleft'><img src='${SiteThemeUrl}yxtd.gif' /></div>
  <div class='tdright' style='padding:6px 0 0 4px;'>
  <ul class='news_new_item_ul'>
  <#list best_group_list as g >
   <li><a href='go.action?groupId=${g.groupId}' target='_blank' title="g.groupTitle!?html">${Util.getCountedWords(g.groupTitle!?html,14)}</a></li> 
  </#list>
  </ul>   
  </div>
</#if>
<div style="font-size:0;height:0;clear:both"></div>
 </div>
</div>