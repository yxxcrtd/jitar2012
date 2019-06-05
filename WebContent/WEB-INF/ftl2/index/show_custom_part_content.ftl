<#macro showContent part,textLength>
<#if part.sysCateId??>
<#if part.partType == 1>
<div id='part_${part.siteIndexPartId}'> 正在加载内容，请稍候……</div>
<script type='text/javascript'>
$.get('${ContextPath}jython/get_cate_article.py?id=${part.sysCateId}&count=${part.showCount}&unitId=${rootUnit.unitId}&tmp=' + Date.parse(new Date()), 
  function(data){document.getElementById('part_${part.siteIndexPartId}').innerHTML = data;}
);
</script>
<#elseif part.partType == 2>
<div id='part_${part.siteIndexPartId}'> 正在加载内容，请稍候……</div>
<script type='text/javascript'>
$.get('${ContextPath}jython/get_index_contentspace_article.py?contentSpaceId=${part.sysCateId}&unitId=${rootUnit.unitId}&showType=${part.showType}&count=${part.showCount}&tmp=' + Date.parse(new Date()), 
  function(data){document.getElementById('part_${part.siteIndexPartId}').innerHTML = data;}
);
</script>
<#else>
${part.content!}
</#if>
<#else>
<div id="part_${part.siteIndexPartId}" myHeight="">
${part.content!}
</div>
</#if>
</#macro>