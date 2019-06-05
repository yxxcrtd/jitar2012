<div class="contentInfo mt13">
    <#if resource??>
   <#if resource.scName??> <#if resource.scName?length &gt; 0 > 
    <div class="infoDetail clearfix">
      <span class="infoTag">分类：</span>
        <p>
        ${resource.scName!?html}
        </p>
    </div>
    </#if></#if>
    <#if (resource.subjectId!0) !=0>
    <div class="infoDetail clearfix">
      <span class="infoTag">学科：</span>
        <p>${Util.subjectById(resource.subjectId!0).msubjName!?html}</p>
    </div>
    </#if>
    <#if resource.gradeName??><#if resource.gradeName?length &gt; 0 > 
    <div class="infoDetail clearfix">
      <span class="infoTag">年级：</span>
        <p>${resource.gradeName!?html}</p>
    </div>
    </#if></#if>
    <#if resource.tags?? && resource.tags !="" >
    <div class="infoDetail clearfix mt10">
      <span class="infoTag">标签：</span>
      <#assign tags = resource.tags.split(",")>
        <p class="infoTagCont1">
        <#list tags  as t>
          <a href='${ContextPath}showTag.action?tagName=${t?url('UTF-8')}' class="infoTagA"<#if t?size &gt;5> title="${t!""}"</#if>>
          <#if t?size &gt;5>${Util.getCountedWords(t,4)}<#else>${t}</#if></a>
        </#list>
        </p>
        <#if tags?size &gt; 4>
        <p><a href="javascript:;" class="unFold">展开</a><a href="javascript:;" class="unFold1">收起</a></p>
        </#if>
    </div>
    </#if>
    </#if>
</div>