<#if siteIndexPartList??>
<#list siteIndexPartList as part>
<#if part.moduleZone == 7>
<#if part.showBorder=1>
<div class='cont1' style='height:auto;overflow:hidden;'> 
	<div class='tab2'>
		<span style='float:right;padding:2px 4px 0 0;'><#if part.sysCateId??><a href='show_custorm_article.action?categoryId=${part.sysCateId}&type=${part.partType}&title=${part.moduleName!?url}'>更多…</a><#else><a onclick="CommonUtil.ExpandCollapseCustomPart('part_${part.siteIndexPartId}');return false;" href="javascript:void(0)">点击显示全部内容</a></#if></span>
		<label class='tab2_label_1 titlecolor' style='width:auto;'><img src='${ContextPath}css/index/j.gif' />&nbsp;${part.moduleName!?html}</label>
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