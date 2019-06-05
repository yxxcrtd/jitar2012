<div class='b2'>    
  <div class='b1_head_a'>
    <div class='b1_head_right'><a href='blogList.action?type=hot'>更多…</a></div>
    <div class='b1_head_left'> 热门工作室</div>
  </div>
  <div class='b1_content'>
<#if (hot_blog_list??) && (hot_blog_list?size > 0) >
      <div class='tc1' style='text-align:center'>
      <#list hot_blog_list as user>
         <#if user_index == 0>
           <table class='micontable1' border='0' cellpadding='0' cellspacing='0' width='99%'>
            <tr>
             <td class='miconleft'><span class='mimg_container'><a href='${SiteUrl}go.action?loginName=${user.loginName}'><img src='${SSOServerUrl +"upload/"+user.userIcon!""}' onerror="this.src='${SiteUrl}images/default.gif'" width='64' height='64' border='0' /></a></span></td>
             <td class='miconright'>
               <div><span><a href='${SiteUrl}go.action?loginName=${user.loginName}'>${user.blogName}</a></span></div>
		           <div>真实姓名：<a href='${SiteUrl}go.action?loginName=${user.loginName}' target='_blank'>${user.trueName!?html}</a></div>
		           <div>&nbsp;&nbsp;${user.blogIntroduce!}</div>
             </td>
            </tr>
           </table>
         <div style="height:4px;font-size:0"></div>
         <#else>
           <a href='${SiteUrl}go.action?loginName=${user.loginName}' target='_blank'>${user.trueName!?html}</a>
           <#if user_has_next> | </#if>
         </#if>
      </#list>  
      </div>
</#if>
  </div>
</div>
