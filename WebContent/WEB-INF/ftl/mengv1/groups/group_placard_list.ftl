<div class='b1'>
  <div class='b1_head2'>
    <div class='b1_head_right'><a href='ktgroups.action?act=all&type=placard'>更多…</a></div>
    <div class='b1_head_left'>课题公告</div>
  </div>
  <div class='b1_content'>
      <div class='tc1'>
      <#if placard_list?? >
      <table border='0' cellpadding='1' cellpadding='1' width='246'>
      <#list placard_list as placard>
        <tr valign='top'>
        <td class="rank_left">${placard_index + 1}</td>
        <td class="rank_right">
            [${placard.createDate?string('yy-MM-dd')}] <a href='${SiteUrl}showPlacard.action?placardId=${placard.id!}' target='_blank' 
    title='发布时间：${placard.createDate!}'>${placard.title!?html}</a>
        </td>
        </tr>
      </#list>
       </table>
      <#else>
       暂无课题公告
      </#if>
    </div>
  </div>
</div>

