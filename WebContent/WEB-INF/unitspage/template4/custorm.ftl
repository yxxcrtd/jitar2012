<#if webpart??>
<#if webpart.partType == 1>
<div class='gly'>
  <div class='gly_head'>
    <div class='gly_head_right'><a href='py/show_unit_custorm_article.py?categoryId=${webpart.cateId}&title=${webpart.moduleName?url}'>更多…</a></div>
    <div class='gly_head_left'>&nbsp;${webpart.moduleName}</div>
  </div>
  <div style='padding:4px;' id='w_${webpart.unitWebpartId}'>
     正在加载内容……
  </div>
  <script type='text/javascript'>
	new Ajax.Request(UnitUrl + 'jython/get_unit_cate_article.py?unitId=${webpart.unitId}&cateId=${webpart.cateId}&count=${webpart.showCount}&tmp=' + Date.parse(new Date()), { 
	  method: 'get',
	  onSuccess: function(xhr){document.getElementById('w_${webpart.unitWebpartId}').innerHTML = xhr.responseText; },
	  onException: function(xhr){document.getElementById('w_${webpart.unitWebpartId}').innerHTML = '加载数据出现错误：' + xhr.responseText;},
	  onFailure: function(){document.getElementById('w_${webpart.unitWebpartId}').innerHTML = '加载数据出现错误：' + xhr.responseText;}
	}
	);
	</script>
</div>
<#elseif webpart.partType == 2>
<div class='gly'>
  <div class='gly_head'>
    <div class='gly_head_right'><a href='py/show_unit_custorm_article.py?categoryId=${webpart.cateId}&partType=${webpart.partType}&title=${webpart.moduleName?url}'>更多…</a></div>
    <div class='gly_head_left'>&nbsp;${webpart.moduleName}</div>
  </div>
  <div style='padding:4px;' id='w_${webpart.unitWebpartId}'>
   正在加载内容……
  </div>
    <script type='text/javascript'>
	new Ajax.Request(UnitUrl + 'jython/get_unit_contentspace_article.py?unitId=${webpart.unitId}&contentSpaceId=${webpart.cateId}&count=${webpart.showCount}&showType=${webpart.showType}&tmp=' + Date.parse(new Date()), { 
	  method: 'get',
	  onSuccess: function(xhr){document.getElementById('w_${webpart.unitWebpartId}').innerHTML = xhr.responseText; },
	  onException: function(xhr){document.getElementById('w_${webpart.unitWebpartId}').innerHTML = '加载数据出现错误：' + xhr.responseText;},
	  onFailure: function(){document.getElementById('w_${webpart.unitWebpartId}').innerHTML = '加载数据出现错误：' + xhr.responseText;}
	}
	);
	</script>
</div>
<#else>
<div class='gly'>
  <div class='gly_head'>
    <div class='gly_head_left'>&nbsp;${webpart.moduleName}</div>
  </div>
  <div style='padding:4px;'>
   ${webpart.content!}
  </div>
</div>
</#if>
<#else>
本模块没有输入内容
</#if>