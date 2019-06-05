<#if siteIndexPartList??>
<#list siteIndexPartList as part>
<#if part.moduleZone == 9>
<#if part.showBorder=1>
<div class='r1' style="height:auto">
  <div class='r1_head'>
    <div class='r1_head_right'><#if part.sysCateId??><a href='show_custorm_article.action?categoryId=${part.sysCateId}&type=${part.partType}&title=${part.moduleName!?url}'>更多…</a><#else><a onclick="CommonUtil.ExpandCollapseCustomPart('part_${part.siteIndexPartId}');return false;" href="javascript:void(0)">点击显示全部内容</a></#if></div>
    <div class='r1_head_left'> ${part.moduleName!?html}</div>
  </div>
  <div style='padding:4px;text-align:left;<#if part.moduleHeight!=0>;height:${part.moduleHeight}px;overflow:hidden;</#if>'> 
    <#include 'site_index_part_content.ftl' >
  </div>
</div>
<#else>
<#include 'site_index_part_content.ftl' >
</#if>
<div style='height:8px;font-size:0;'></div>
</#if>
</#list>
</#if>