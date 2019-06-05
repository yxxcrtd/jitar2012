<#if webpart.partType == 1>
<div class='panel'>
  <div class='panel_head'>
  	<div class='panel_head_right'><a href='py/show_custorm_article.py?categoryId=${webpart.sysCateId}&unitId=${unitId!}&title=${webpart.moduleName?url}'>更多…</a></div>
    <div class='panel_head_left'>${webpart.moduleName!?html}</div>
  </div>
  <div class='panel_content' id='part_${webpart.subjectWebpartId}'>  
	<script type='text/javascript'>
	document.getElementById('part_${webpart.subjectWebpartId}').innerHTML ="	 正在加载内容，请稍候……"
	new Ajax.Request(SubjectUrl + 'jython/get_cate_article.py?id=${webpart.sysCateId}&unitId=${unitId!}&count=${webpart.showCount}&subject=${webpart.subjectId}&tmp=' + Date.parse(new Date()), { 
	  method: 'get',
	  onSuccess: function(xhr){document.getElementById('part_${webpart.subjectWebpartId}').innerHTML = xhr.responseText; },
	  onException: function(xhr){document.getElementById('part_${webpart.subjectWebpartId}').innerHTML = '加载数据出现错误：' + xhr.responseText;},
	  onFailure: function(){document.getElementById('part_${webpart.subjectWebpartId}').innerHTML = '加载数据出现错误：' + xhr.responseText;}
	}
	);
	</script>
	</div>
</div>
<#elseif webpart.partType == 2>
<div class='panel'>
  <div class='panel_head'>
  	<div class='panel_head_right'><a href='py/show_custorm_article.py?categoryId=${webpart.sysCateId}&partType=${webpart.partType}&title=${webpart.moduleName?url}'>更多…</a></div>
    <div class='panel_head_left'>${webpart.moduleName!?html}</div>
  </div>
  <div class='panel_content' id='part_${webpart.subjectWebpartId}'>  
	<script type='text/javascript'>
	document.getElementById('part_${webpart.subjectWebpartId}').innerHTML ="	 正在加载内容，请稍候……"
	new Ajax.Request(SubjectUrl + 'jython/get_subject_contentspace_article.py?subject=${webpart.subjectId}&contentSpaceId=${webpart.sysCateId}&showType=${webpart.showType}&count=${webpart.showCount}&id=${webpart.subjectId}&tmp=' + Date.parse(new Date()), { 
	  method: 'get',
	  onSuccess: function(xhr){document.getElementById('part_${webpart.subjectWebpartId}').innerHTML = xhr.responseText; },
	  onException: function(xhr){document.getElementById('part_${webpart.subjectWebpartId}').innerHTML = '加载数据出现错误：' + xhr.responseText;},
	  onFailure: function(){document.getElementById('part_${webpart.subjectWebpartId}').innerHTML = '加载数据出现错误：' + xhr.responseText;}
	}
	);
	</script>
	</div>
</div>
<#else>
<div class='panel'>
  <div class='panel_head'>
    <div class='panel_head_left'>${webpart.moduleName!?html}</div>
  </div>
  <div class='panel_content'>
   ${webpart.content!}   
  </div>
</div>
 </#if>