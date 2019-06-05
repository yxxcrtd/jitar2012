<div class='b1'>
  <div class='b1_head2'>
    <div class='b1_head_right'><a href='ktgroups.action?act=all&type=video'>更多…</a></div>
    <div class='b1_head_left'>课题成果-视频</div>
  </div>
  <div class='b1_content'>
      <div class='tc1'>
      <#if video_list?? >
      <table border='0' cellpadding='1' cellpadding='1' width='246'>
          <#list video_list as video>
            <tr>
              <td style="text-align:center">
                <a href='${SiteUrl}manage/video.action?cmd=show&videoId=${video.videoId}'><img border=0 src="${video.flvThumbNailHref!?html}"/></a><br />
                <a href='video.action?cmd=show&videoId=${video.videoId}' target='_blank'>${video.title!?html}</a>
              </td>
            </tr>
          </#list>
       </table>
      <#else>
       暂无视频
      </#if>
    </div>
  </div>
</div>

