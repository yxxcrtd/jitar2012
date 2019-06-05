<div class='c' style='float:left;width:453px;overflow:hidden;height:200px;'>
  <div class='c_head'>
    <div class='c_head_right' style='display:none'>更多…</div>
    <div class='c_head_left titlecolor'> 学科动态</div>
  </div>
  <div class='c_content'>
<#if news_list?? >
    <ul class='listitem_ul'>
    <#list news_list as news >
		  <li><span>${news.createDate?string('yyyy-MM-dd')}</span><a href='showNews.py?newsId=${news.newsId}' target='_blank'>${news.title!?html}</a></li>
		 
	</#list>
		
	  </ul>
</#if>
  </div>        
</div>

<#--
<#if resource.subjectId??>
${Util.subjectById(resource.subjectId).msubjName!?html}
</#if>
<#if resource.gradeId??>
${Util.gradeById(resource.gradeId).gradeName!?html}
</#if>
${news.newsId}${Util.gradeById(gradeId).gradeName}${Util.subjectById(subjectId).msubjName!?html}
-->
