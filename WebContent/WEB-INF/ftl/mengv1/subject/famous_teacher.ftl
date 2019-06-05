<div class='c'>
  <div class='c_head'>
    <div class='c_head_right'><a href='blogList.action?subjectId=${subject.metaSubject.msubjId}&gradeId=${gradeId!}&type=famous'>更多…</a></div>
    <div class='c_head_left titlecolor'> 名师工作室</div>
  </div>
  <div class='c_content'>
  
  <#if famous_user_list?? >
    <div style='padding:6px;'>
    <table border='0' width='100%' cellpadding='0' cellspacing='0'>
    <tr valign='top'>
    <#list famous_user_list as u >
    <#if u_index < 3>
    <td style='text-align:center;width:33%;'>
      <div style='height:64px;'>
      <table border='0' cellpadding='0' cellspacing='0' style='border:1px solid #bdcbd5;padding:1px;margin:auto'>
      <tr><td><a href='${SiteUrl}go.action?loginName=${u.loginName}'><img src="${SSOServerUrl +'upload/'+ (u.userIcon!'')}" onerror="this.src='${SiteUrl}images/default.gif'" 
            width='56' height='56' border='0' /></a></td></tr>
      </table>
      </div>
      <div style='text-align:center'><a href='${SiteUrl}go.action?loginName=${u.loginName}'>${u.trueName!?html}</a></div>
      </td>
     </#if>
    </#list>
    </tr>
    </table>
    </div>
    </#if>
  </div>
</div>
