<!--相关标签-->
<div id="contentInfo_label" class="contentInfo mt13">
   <div class="infoDetail clearfix">
    <#if SysCate?? && (SysCate?size &gt; 0)>
      <span class="infoTag">分类：</span>
        <p>
        	<a href='${ContextPath}showPhotoList.action?categoryId=${SysCate.categoryId}' target="_blank">${SysCate.name!?html}</a>
        </p>
     </#if>    
    </div>
    <#if photo.tags?? && photo.tags !="" >
	    <div class="infoDetail clearfix mt10">
			<span class="infoTag">标签：</span>
			<p class="infoTagCont1">
      		 <#assign tags = photo.tags.split(",")>
		        <#list tags as t>
			          <a href='${ContextPath}showTag.action?tagName=${t?url('UTF-8')}' class="infoTagA"<#if t?size &gt;5> title="${t!""}" </#if> >
			          <#if t?size &gt;5>${Util.getCountedWords(t,4)}<#else>${t}</#if></a>
		        </#list>
	         </p>
	        <#if tags?size &gt; 4>
	         <p><a href="javascript:;" class="unFold">展开</a><a href="javascript:;" class="unFold1">收起</a></p>
	        </#if>
	     </div>
    </#if>
</div>

