<div class='b1'>
  <div class='b1_head'>
    <div class='b1_head_right'><a href='groups.action?type=rcmd'>更多…</a></div>
    <div class='b1_head_left'> 推荐协作组</div>
  </div>
<div class='b1_content'>
<#if (rcmd_group_list??) && (rcmd_group_list?size > 0) >
  <#assign g = rcmd_group_list[0] >
  <div class='tc1'>
    <table class='icontable' border='0' cellpadding='0' cellspacing='0'>
      <tr>
      <td class='iconleft'>
        <span class='img_container'><a href='${SiteUrl}go.action?groupId=${g.groupId}'><img src='${Util.url(g.groupIcon!'images/group_default.gif')}' width='64' height='64' border='0' /></a></span>
      </td>
      <td class='iconright'>
          <div><span><a href='${SiteUrl}go.action?groupId=${g.groupId}'>${g.groupTitle!?html}</a></span></div>
          <div>创建时间：${g.createDate?string('yyyy-MM-dd')}</div>
          <div>简介：${g.groupIntroduce!}</div>
      </td>
      </tr>
    </table>
    <#list rcmd_group_list as g >
      <#if (g_index > 0) >
      <div style="padding:2px 0">
      <span style='float:right'>
       ${g.createDate?string('yyyy-MM-dd')}
      </span>
      <span style='float:left'>
      <a href='${SiteUrl}go.action?groupId=${g.groupId}'>${g.groupTitle!?html}</a>
      </span>
      <div style='clear:both;font-size:0'></div>
      </div>
      </#if>
    </#list>
    <div style='clear:both;font-size:0'></div>
    </div>
   </#if>
  </div>
</div>
