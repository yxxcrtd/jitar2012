<div class='b2'>    
  <div class='b1_head_a'>
    <div class='b1_head_right'><a href='blogList.action?type=2'>更多…</a></div>
    <div class='b1_head_left'> ${Util.typeIdToName(2)}工作室</div>
  </div>
  <div>
<#if rcmd_list?? >
	<#assign column = 4>
	<#assign row = 3>
       <table border='0' cellspacing='6' class='m_2'>
       <#list 0..row-1 as r>
        <tr>
       <#list 0..column-1 as c>
       <#if rcmd_list[r*column + c]??>
       	<#assign wr = rcmd_list[r*column + c] >      
         <td valign='top' style='width:25%'>
          <div style='margin:auto;'>
            <div style='text-align:center;'>
            <span class='img_container2'><a href='${SiteUrl}go.action?loginName=${wr.loginName}'><img src='${SSOServerUrl +"upload/"+wr.userIcon!"images/default.gif"}' onerror="this.src='${SiteUrl}images/default.gif'" width='48' height='48' border='0' /></a></span>
            </div>
            <div style='text-align:center;padding-top:6px;'><a href='${SiteUrl}go.action?loginName=${wr.loginName}'>${wr.blogName!?html}</a></div>
          </div>       
         </td>
         <#else>
         <td style='width:25%'>&nbsp;</td>
         </#if>
       	</#list>
        </tr>
        </#list>
       </table>
</#if>
  </div>
</div>
