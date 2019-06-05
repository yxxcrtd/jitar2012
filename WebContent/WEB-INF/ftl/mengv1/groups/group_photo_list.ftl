<div class='b1'>
  <div class='b1_head2'>
    <div class='b1_head_right'><a href='ktgroups.action?act=all&type=photo'>更多…</a></div>
    <div class='b1_head_left'>课题成果-图片</div>
  </div>
  <div class='b1_content'>
      <div class='tc1'>
      <#if photo_list?? >
      <table border='0' cellpadding='1' cellpadding='1' width='246'>
          <#list photo_list as photo>
            <tr>
              <td style="text-align:center">
                    <a href='../showPhoto.py?photoId=${photo.photoId}' target='_blank'>
                    <img onload="CommonUtil.reFixImg(this,120,100)" src="${Util.thumbNails(photo.href!'images/default.gif')}" vspace='4' border='0' /></a><br />
                    <a href='../showPhoto.py?photoId=${photo.photoId}' target='_blank'>${photo.title!?html}</a>
              </td>
            </tr>
          </#list>
       </table>
      <#else>
       暂无图片
      </#if>
    </div>
  </div>
</div>

