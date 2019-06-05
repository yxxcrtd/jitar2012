<#if user_list?? >
<#assign columnCount = 2 >
<#if user_list.size() % columnCount == 0>
<#assign rowCount = user_list.size() / columnCount >
  <#else>
<#assign rowCount = (user_list.size() / columnCount)?int + 1 >
  </#if>  
<table border='0' align='center' cellspacing='2' cellpadding='0' width='100%'>        
<#list 0..rowCount-1 as row>
 <tr valign='top' align='center'>
    <#list 0..columnCount-1 as cell>
    <td width='50%'>
    <#if user_list[(row ) * columnCount + cell]?? >
    <#assign u = user_list[(row) * columnCount + cell]>
      <div style='height:64px'>
      <table border='0' cellpadding='0' cellspacing='0' class='border_img_a'>
      <tr><td><a href='${SiteUrl}go.action?loginName=${u.loginName}'><img src="${SSOServerUrl +'upload/'+ (u.userIcon!'')}" onerror="this.src='${SiteUrl}images/default.gif'"  width='56' height='56' border='0' /></a></td></tr>
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
<#else>
没有数据
</#if>