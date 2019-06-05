<div class="contentInfo mt13">
  <#if (video.categoryId!0) != 0>
  <div class="infoDetail clearfix">
      <span class="infoTag">分类：</span>
        <p>
        ${Util.getCategory(video.categoryId!0).name!?html}
        </p>
    </div>
  </#if>
  <#if (video.subjectId!0) !=0 >  
    <div class="infoDetail clearfix">
      <span class="infoTag">学科：</span>
        <p>${Util.subjectById(video.subjectId!).msubjName!?html}</p>
    </div>
  </#if> 
  <#if (video.gradeId!0) !=0> 
    <div class="infoDetail clearfix">
      <span class="infoTag">年级：</span>
        <p>${Util.gradeById(video.gradeId!0).gradeName!?html}</p>
    </div>
  </#if>
    <#if  video.tags?? &&  video.tags !="" >
    <div class="infoDetail clearfix mt10">
      <span class="infoTag">标签：</span>
      <#assign tags =  video.tags.split(",")>
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
</div>