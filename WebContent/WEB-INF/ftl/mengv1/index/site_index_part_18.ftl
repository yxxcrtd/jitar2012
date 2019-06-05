<#if siteIndexPartList??>
<#list siteIndexPartList as part>
<#if part.moduleZone == 18>
<div style='height:8px;font-size:0;'></div>
<#if part.showBorder=1>
<div class='b1' style='height:auto'>
  <div class='b1_head'>
    <div class='b1_head_right'><#if part.sysCateId??><a href='show_custorm_article.action?categoryId=${part.sysCateId}&type=${part.partType}&title=${part.moduleName!?url}'>更多…</a><#else><a onclick="CommonUtil.ExpandCollapseCustomPart('part_${part.siteIndexPartId}');return false;" href="javascript:void(0)">点击显示全部内容</a></#if></div>
    <div class='b1_head_left' style='width:auto;'>&nbsp;<img src='${ContextPath}css/index/j.gif' />&nbsp;${part.moduleName!?html}</div>
  </div>
  <div style='padding:4px;text-align:left;<#if part.moduleHeight!=0>;height:${part.moduleHeight}px;overflow:hidden;</#if>'> 
    <#include 'site_index_part_content.ftl' >
  </div>
</div>
<#else>
<#include 'site_index_part_content.ftl' >
</#if>
</#if>
</#list>
</#if>