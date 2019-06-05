<div class='m1' style='height:196px;overflow:hidden;'>
  <div class='m1_head'>
    <div class='m1_head_right'><a href='prepareCourse.action'>更多…</a></div>
    <div class='m1_head_left'>&nbsp;<img src='${ContextPath}css/index/j.gif' />&nbsp;集体备课</div>
  </div>
  <div class='m1_content' style='text-align:left;'>
  <#if course_list??>
  <ul class='news_new_item_ul'>
	<#list course_list as c>		
		<li><a href='p/${c.prepareCourseId}/0/'>${c.title}</a> [${c.createDate?string('MM/dd')}]</li>
	</#list>
	</ul>
	</#if>
  </div>        
</div>