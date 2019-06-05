<div class='c' style='float:right;width:300px;overflow:hidden;height:200px;'>
  <div class='c_head'>
    <div class='c_head_right' style='display:none'>更多…</div>
    <div class='c_head_left titlecolor'> 学科公告</div>
  </div>
  <div class='c_content'>
<#if placard_list?? >
	   <ul class='listitem_ul'>
  <#list placard_list as p >
		  <li><a href='showPlacard.action?placardId=${p.id}' target='_blank'>${p.title!?html}</a></li>
  </#list>
		</ul>
</#if>
  </div>        
</div> 
