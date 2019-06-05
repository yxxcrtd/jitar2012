<div class='gly'> 
    <div class='gly_head'>
        <label class='gly_head_right'><a href='${UnitRootUrl}py/unit_video.py'>查看全部</a></label>
        <label class='gly_head_left'>&nbsp;${webpart.displayName}</label>
    </div>

	<div class='tab_content' style='overflow:hidden;'>
		<#if new_video_list??>
	    <table width='100%' border='0'>
	    <tr>
	    <td align='left' valign='top'>
	      <#list new_video_list as p>
	        <table border='0' align='left' width='25%'>
	        <tr align='center'>
	         <td style='height:110px;vertical-align:top;'>
	          <a href='${SiteUrl}manage/video.action?cmd=show&videoId=${p.videoId}'><img border='0' style='width:120px' src="${p.flvThumbNailHref!?html}" /></a>
	          </td>
	        </tr>
	        <tr align='center'>
	         <td><a href='${SiteUrl}manage/video.action?cmd=show&videoId=${p.videoId}'>${p.title!?html}</a></td>
	        </tr>
	        </table>
	      </#list>
	     </td>
	     </tr>
	     </table>
		</#if>
   </div>   
</div>