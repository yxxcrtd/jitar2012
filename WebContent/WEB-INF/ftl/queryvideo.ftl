  <#if video_list??>
      <#list video_list as p>
        <table border="0" cellspacing="1" cellpadding="1" style="width:100%;" id="tv${p.videoId}">
        <tr>
            <td>
            <a href='${SiteUrl}manage/video.action?cmd=show&videoId=${p.videoId}' target='_blank'><img border=0 src="${p.flvThumbNailHref!?html}"/>
            </a>
            <a href='${SiteUrl}manage/video.action?cmd=show&videoId=${p.videoId}' target='_blank'>${p.title!?html}</a>    
            <input type="hidden" name="videoId" value="${p.videoId}">        
            </td>
            <td style="width:100px;text-align:center">
            <#if p.auditState!=0>
                                待审核
            </#if>
            </td>
            <td style="width:60px;text-align:center"><a href="#" onclick="removeVideo(${p.videoId});">删除</a></td>
        </tr>   
        </table> 
      </#list>
  </#if>
   