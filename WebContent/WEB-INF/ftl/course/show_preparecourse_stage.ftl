<#if pc_stage_list?? >
<div>
<#list pc_stage_list as s>
<#if currentStage == s_index >
    <#if prepareCourseStageId == s.prepareCourseStageId>
       <div style='border:1px solid red'>${s_index+1},<a href='${SiteUrl}p/${prepareCourseId}/${s.prepareCourseStageId}/' style='color:red;font-weight:bold;'>${s.title}</a></div>
    <#else>
       <div>${s_index+1},<a href='${SiteUrl}p/${prepareCourseId}/${s.prepareCourseStageId}/' style='color:red;font-weight:bold;'>${s.title}</a></div>
    </#if>
<#else>
    <#if prepareCourseStageId == s.prepareCourseStageId >
       <div style='border:1px solid red'>${s_index+1},<a href='${SiteUrl}p/${prepareCourseId}/${s.prepareCourseStageId}/'>${s.title}</a></div>
    <#else>
       <div>${s_index+1},<a href='${SiteUrl}p/${prepareCourseId}/${s.prepareCourseStageId}/' />${s.title}</a></div>
    </#if>    
</#if>
</#list>
</div>
<#else>
该备课没有定义流程。
</#if>
<#if prepareCourseStage??>
<br/>
<div style="border:1px solid #AAA;margin-top:6px">
<div style="font-weight:bold;background:#EEE;padding:4px 2px">当前流程描述</div>
<div style="padding:2px">
${prepareCourseStage.description!}
</div>
</div>
</#if>