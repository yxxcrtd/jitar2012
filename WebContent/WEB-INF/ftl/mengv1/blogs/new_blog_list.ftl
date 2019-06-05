<div class='b2'>    
  <div class='b1_head_a'>
    <div class='b1_head_right'><a href='blogList.action?type=new'>更多…</a></div>
    <div class='b1_head_left'> 最新工作室</div>
  </div>
  <div class='b1_content'>
<#if new_blog_list?? >

      <div class='tc1' style='text-align:center'>
           <#list new_blog_list as user>
              <#if user_index == 0>
              <table class='micontable1' border='0' cellpadding='0' cellspacing='0' width='99%'>
              <tr>
              <td class='miconleft'><span class='mimg_container'><a href='${SiteUrl}go.action?loginName=${user.loginName}'><img src="${SSOServerUrl +'upload/'+ (user.userIcon!'')}" onerror="this.src='${SiteUrl}images/default.gif'" onerror="this.src='${SiteUrl}images/default.gif'" width='64' height='64' border='0' /></a></span></td>
              <td class='miconright'>
              <div><span><a href='${SiteUrl}go.action?loginName=${user.loginName}'>${user.blogName}</a></span></div>
              <div><span>真实姓名：</span><a href='${SiteUrl}go.action?loginName=${user.loginName}' target='_blank'>${user.trueName}</a></div>
              <div>简介: ${user.blogIntroduce!}</div>
              <div style='display:none'>
                <div><span>最新文章：</span></div>
	              <div>[文章类别]<a href=''>文章标题文章标题</a></div>
	              <div>[文章类别]<a href=''>文章标题文章标题</a></div>
	              <div>[文章类别]<a href=''>文章标题文章标题</a></div>
	              <div>[文章类别]<a href=''>文章标题文章标题</a></div>
	            </div>
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
