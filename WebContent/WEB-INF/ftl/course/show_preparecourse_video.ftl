<table width='100%' border='0'>
<tr>
<#list pcv_list as pcv>
   
    <td align="center"> 
    <a href="${SiteUrl}manage/video.action?cmd=show&videoId=${pcv.videoId}" target="_blank"><img width="80" height="80" src="${pcv.flvThumbNailHref!}" /></a>
    <br/>${pcv.videoTitle}
    </td>
   
</#list>
 </tr>
</table>
<div style='text-align:right'><a href='${SiteUrl}p/${prepareCourse.prepareCourseId}/${prepareCourseStageId}/py/show_preparecourse_video_list.py'>全部视频</a></div>
