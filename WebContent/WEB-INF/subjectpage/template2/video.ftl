<div class='panel'>
  <div class='panel_head'>
    <div class='panel_head_right'><a href='${SubjectRootUrl}video/?unitId=${unitId!}&title=${webpart.displayName!?url}'>更多…</a></div>
    <div class='panel_head_left'><img src='${SiteUrl}theme/subject/${subject.themeName?default('theme1')}/j.gif' />&nbsp;${webpart.displayName!?html}</div>
  </div>
  <div class='panel_content'>
	<#if new_video_list??>
	<table border='0' width='100%' cellpadding='0' cellspacing='0' style='table-layout:fixed'>
	<tr valign='top'>
	<#list new_video_list as p>
		<td style='text-align:center;width:25%;padding-bottom:4px;'>	            
          <div style='height:104px;'>
          <table border='0' cellpadding='0' cellspacing='0' style='border:1px solid #bdcbd5;padding:1px;margin:auto'>
          <tr><td><a title='${p.title!?html}' href='${SiteUrl}manage/video.action?cmd=show&videoId=${p.videoId}'><img border=0 width='96' height='96' src="${p.flvThumbNailHref!?html}"/></td></tr>
          </table>
          </div>
          <div style='text-align:center;overflow:hidden;'><a onmouseover='this.title=this.innerHTML' href='${SiteUrl}manage/video.action?cmd=show&videoId=${p.videoId}'>${p.title!?html}</a></div>	            
          </td>
      </#list>
     </tr>
     </table>
	</#if>
  </div>
</div>