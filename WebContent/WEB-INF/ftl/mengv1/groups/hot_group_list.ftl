<div class='b1'>    
  <div class='b1_head b1_bg1'>
    <div class='b1_head_right'><a href='groups.action?type=hot'>更多…</a></div>
    <div class='b1_head_left'> 热门协作组</div>
  </div>
  <div class='b1_content'>
<#if (hot_group_list??) && (hot_group_list?size > 0) >
  <#assign g = hot_group_list[0] >
      <div class='tc1'>
        <table class='icontable' border='0' cellpadding='0' cellspacing='0'>
          <tr>
          <td class='iconleft'><span class='img_container'><a href='${SiteUrl}go.action?groupId=${g.groupId}'><img src='${Util.url(g.groupIcon!'images/group_default.gif')}' width='64' height='64' border='0' /></a></span></td>
          <td class='iconright'>
	          <div><span><a href='${SiteUrl}go.action?groupId=${g.groupId}'>${g.groupTitle!?html}</a></span></div>
	          <div>创建时间：${g.createDate?string('yyyy-MM-dd')}</div>
	        <#assign u = Util.userById(g.createUserId) >
	          <div>创建者：<a href='${SiteUrl}go.action?loginName=${u.loginName}' target='_blank'>${u.nickName}</a></div>
          </td>
          </tr>
          <tr>
          <td colspan='2' class='iconarticle'>
          <div>${g.groupIntroduce!}</div>
          </td>
          </tr>
        </table>
      </div>
      </#if>
    </div>
</div>
