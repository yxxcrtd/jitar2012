<#macro showCustomPart partList>
<#if partList?? && partList?size &gt; 0>
<div style="height:0;width:0;font-size:0;clear:both;"></div>
  <#assign showType = 0 />
  <#list partList as part>  
    <#assign showType = part.multiColumn />
    <#break>
  </#list>
  
  <div class="main">
  <#if showType == 0>
    <#-- 每个模块显示一行  -->
     <#list partList as part>  
     <div style="overflow:hidden;width:100%;">
      <#if part.showBorder == 1>
      <div class="border">
        <h3 class="h3Head textIn">
        <#if part.sysCateId??><a class="more" href='${ContextPath}showCustomArticle.action?categoryId=${part.sysCateId}&type=${part.partType}&unitId=${rootUnit.unitId}&title=${part.moduleName!?url}'>更多…</a>
        <#else><a class="more" onclick="ExpandCollapseCustomPart('part_${part.siteIndexPartId}');return false;" href="javascript:void(0)">V</a>
        </#if> 
        ${part.moduleName!?html}
        </h3>
        <div class="teach" style="<#if part.moduleHeight != 0>height:${part.moduleHeight}px;<#else>height:auto;</#if>">
            <#include 'site_index_part_content.ftl' />
        </div>
        <div class="imgShadow"><img src="${SiteThemeUrl}images/imgShadow.jpg" style="width:100%;height:8px;" /></div>
      </div>
      <#else>
        <div style="overflow:hidden;width:100%;<#if part.moduleHeight != 0>height:${part.moduleHeight}px;<#else>height:auto;</#if>">
        <#include 'site_index_part_content.ftl' />
        </div>
      </#if>
     </div>
     </#list>
  <#else>
    <#-- 所有模块在一行显示 -->
    <#list partList as part>
      <#if part.showBorder == 1>
      <div class="border" style="float:left;<#if part.moduleWidth != 0>width:${part.moduleWidth}px;</#if>">
        <h3 class="h3Head textIn">
        <#if part.sysCateId??><a class="more" href='${ContextPath}showCustomArticle.action?categoryId=${part.sysCateId}&type=${part.partType}&unitId=${rootUnit.unitId}&title=${part.moduleName!?url}'>更多…</a>
        <#else><a class="more" onclick="ExpandCollapseCustomPart('part_${part.siteIndexPartId}');return false;" href="javascript:void(0)">V</a>
        </#if> 
        ${part.moduleName!?html}
        </h3>
        <div class="teach" style="overflow:hidden;<#if part.moduleHeight != 0>height:${part.moduleHeight}px;<#else>height:auto;</#if>">
          <#include 'site_index_part_content.ftl' >
        </div>
        <div class="imgShadow"><img src="${SiteThemeUrl}images/imgShadow.jpg" style="width:100%;height:8px;" /></div>
      </div>
      <#else>
        <div style="overflow:hidden;<#if part.moduleHeight != 0>height:${part.moduleHeight}px;<#else>height:auto;</#if>">
        <#include 'site_index_part_content.ftl' />
        </div>
      </#if>
      <#if part_has_next><div style="float:left;height:20px;width:10px;"></div><#else><div style="clear:both;height:0px;width:0px;font-size:0"></div></#if>
     </#list>
  </#if>
 </div>  
</#if>

</#macro>