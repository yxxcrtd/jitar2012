<div class='b1'>
  <div class='b1_head'>
    <div class='b1_head_right'><a href='blogList.action?type=1'>更多…</a></div>
    <div class='b1_head_left'> ${Util.typeIdToName(1)}工作室</div>
  </div>
  <div class='b1_content'>
    <div>
      <div class='tc1'>
        <!-- 名师工作室 -->
        <#if famous_teachers??>
          <#list famous_teachers as user>
          <#if user_index < 3 >
          <table class='icontable' border='0' cellpadding='0' cellspacing='0'>
            <tr>
            <td class='iconleft'><span class='img_container'><a href='${SiteUrl}go.action?loginName=${user.loginName}'><img src="${SSOServerUrl +'upload/'+ (user.userIcon!'')}" onerror="this.src='${SiteUrl}images/default.gif'" width='64' height='64' onerror="this.src='${SiteUrl}images/default.gif'" border='0' /></a></span></td>
            <td class='iconright'>
            <div><strong><a href='${SiteUrl}go.action?loginName=${user.loginName}' target='_blank'>${user.blogName}</a></strong></div>
            <div><span>真实姓名：</span><a href='${SiteUrl}go.action?loginName=${user.loginName}' target='_blank'>${user.trueName}</a></div>
            <div><span>所在机构：</span>${user.unitName!?html}</div>
            <div><span>访问数：</span>${user.visitCount}</div>
            </td>
            </tr>
          </table>
          </#if>
          </#list>
        </#if>
      </div>
    </div>
  </div>
</div>