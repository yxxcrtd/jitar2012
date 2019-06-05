<div class='panel'>
  <div class='panel_head'>
    <div class='panel_head_right'><a href='${SubjectRootUrl}py/blogList.py?type=expert&unitId=${unitId!}&title=${webpart.displayName!?url}'>更多…</a></div>
    <div class='panel_head_left titlecolor'>${webpart.displayName!?html}</div>
  </div>
  <div class='panel_content'>    
    <#if expert_user_list?? >          
    <table border='0' width='100%' cellpadding='0' cellspacing='0'>
    <#assign columnCount = 3>
    <#assign rowCount = 2>
    <#list 0..rowCount -1 as row>
        <tr valign='top'>
        <#list 0..columnCount -1 as cell >   
        <td style='text-align:center;width:33%;padding-bottom:4px;'>
            <#if expert_user_list[row * columnCount + cell]?? >
            <#assign u = expert_user_list[row * columnCount + cell] >
              <div style='height:64px;'>
              <table border='0' cellpadding='0' cellspacing='0' style='border:1px solid #bdcbd5;padding:1px;margin:auto'>
              <tr><td><a href='${SiteUrl}go.action?loginName=${u.loginName}'><img src="${SSOServerUrl +'upload/'+ (u.userIcon!'')}" onerror="this.src='${ContextPath}images/default.gif'"  width='56' height='56' border='0' /></a></td></tr>
              </table>
              </div>
              <div style='text-align:center'><a href='${SiteUrl}go.action?loginName=${u.loginName}'>${u.trueName!?html}</a></div>
              <#else>
              &nbsp;
              </#if>
          </td>
      </#list>
    </tr>
    </#list>
    </table>
    </#if>
  </div>
</div>