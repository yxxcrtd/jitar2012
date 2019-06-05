<#if part.sysCateId??>
<#if part.partType == 1>
<div id='part_${part.siteIndexPartId}'> 正在加载内容，请稍候……</div>
<script type='text/javascript'>
new Ajax.Request('${SiteUrl}jython/get_cate_article.py?id=${part.sysCateId}&count=${part.showCount}&unitId=${unit.unitId}&tmp=' + Date.parse(new Date()), { 
  method: 'get',
  onSuccess: function(xhr){document.getElementById('part_${part.siteIndexPartId}').innerHTML = xhr.responseText;},
  onException: function(xhr){document.getElementById('part_${part.siteIndexPartId}').innerHTML = '加载数据出现错误：' + xhr.responseText;},
  onFailure: function(){document.getElementById('part_${part.siteIndexPartId}').innerHTML = '加载数据出现错误：' + xhr.responseText;}
}
);
</script>
<#elseif part.partType == 2>
<div id='part_${part.siteIndexPartId}'> 正在加载内容，请稍候……</div>
<script type='text/javascript'>
new Ajax.Request('${SiteUrl}jython/get_index_contentspace_article.py?contentSpaceId=${part.sysCateId}&unitId=${unit.unitId}&showType=${part.showType}&count=${part.showCount}&tmp=' + Date.parse(new Date()), { 
  method: 'get',
  onSuccess: function(xhr){document.getElementById('part_${part.siteIndexPartId}').innerHTML = xhr.responseText;},
  onException: function(xhr){document.getElementById('part_${part.siteIndexPartId}').innerHTML = '加载数据出现错误：' + xhr.responseText;},
  onFailure: function(){document.getElementById('part_${part.siteIndexPartId}').innerHTML = '加载数据出现错误：' + xhr.responseText;}
}
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