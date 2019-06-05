<div class="contentInfo mt13">
  <div class="infoDetail clearfix">
      <span class="infoTag">分类：</span>
        <p>
        <#if SysCate??>
        <a href='${ContextPath}articles.action?type=new&categoryId=${SysCate.categoryId}'>${SysCate.name!?html}</a>
        </#if>
        </p>
    </div>
   <#if article.subjectId??>
    <div class="infoDetail clearfix">
      <span class="infoTag">学科：</span>
      <#assign sub = Util.subjectById(article.subjectId)>
        <p>${sub.msubjName!}</p>
    </div>
   </#if> 
   <#if article.gradeId??>
    <div class="infoDetail clearfix">
      <span class="infoTag">年级：</span>
      <#assign grade = Util.gradeById(article.gradeId)>
        <p>${grade.gradeName!}</p>
    </div>
    </#if>
    <#if article.articleTags?? && article.articleTags !="" >
    <div class="infoDetail clearfix mt10">
      <span class="infoTag">标签：</span>      
      <#assign tags = article.articleTags.split(",")>
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