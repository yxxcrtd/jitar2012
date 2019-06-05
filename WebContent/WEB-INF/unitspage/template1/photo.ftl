<div class='gly'>
  <div class='gly_head'>
    <div class='gly_head_right'><a href='${UnitRootUrl}py/unit_photo.py'>查看全部</a></div>
    <div class='gly_head_left'>&nbsp;${webpart.displayName}</div>
  </div>
  <div style='text-align:center;margin:auto;width:auto'>  
  <#if photo_list??>
  <table border='0' align="center">
  <tr>
  <td>
  <#list photo_list as photo >
  	<div class='img_panel'>

    <div class='img_div'><a href='${SiteUrl}go.action?photoId=${photo.photoId}'><img onload="CommonUtil.reFixImg(this,120,100)" style='width:120px' src="${Util.thumbNails(photo.href!'../images/default.gif')}" vspace='4' border='0' /></a></div>
    <div class='img_title' title='${photo.title!?html}'><a href='${SiteUrl}go.action?photoId=${photo.photoId}'>${photo.title!?html}</a></div>

  	</div>
  	<#if (photo_index + 1) == (photo_list?size) ><div style="clear:both"></div></#if>
  </#list>
  </td>
  </tr>
  </table>
 </#if>
</div>
</div>