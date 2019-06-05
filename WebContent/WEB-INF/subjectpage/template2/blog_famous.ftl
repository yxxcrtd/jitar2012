<div class='panel'>
  <div class='panel_head'>
    <div class='panel_head_right'><a href='${SubjectRootUrl}py/blogList.py?type=famous&subjectId=${subject.metaSubject.msubjId}&gradeId=${gradeId!}&unitId=${unitId!}' class='blog_more'>更多…</a></div>
    <div class='panel_head_left titlecolor'>名师工作室</div>
  </div>
  <div class='panel_content'>
	<#if famous_list?? >
	<div style='padding:6px;'>
	<table border='0' width='100%' cellpadding='0' cellspacing='0'>
	<tr valign='top'>
	<#list famous_list as u >	
	<#if u_index < 3>
	<td style='text-align:center;width:33%;'>
	  <div style='height:64px;'>	  
	  <table border='0' cellpadding='0' cellspacing='0' class='border_img_div'>
	  <tr><td><a href='${SiteUrl}go.action?loginName=${u.loginName}'><img src="${SSOServerUrl +'upload/'+ (u.userIcon!'')}" onerror="this.src='${ContextPath}images/default.gif'"  width='56' height='56' border='0' /></a></td></tr>
	  </table>
	  </div>
	  <div style='text-align:center'><a href='${SiteUrl}go.action?loginName=${u.loginName}'>${u.trueName!?html}</a></div>
	  </td>
	  </#if >
	</#list >
	</tr>
	</table>
	</div>
	</#if >
  </div>
  <div style='clear:both;font-size:0'></div>
</div>