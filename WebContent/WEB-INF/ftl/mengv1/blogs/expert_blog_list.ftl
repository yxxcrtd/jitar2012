<div class='b1' style='height:260px;'>    
  <div class='b1_head b1_bg1'>
    <div class='b1_head_right'><a href='blogList.action?type=3'>更多…</a></div>
    <div class='b1_head_left'> ${Util.typeIdToName(3)}工作室</div>
  </div>
  <div class='b1_content'>
      <div class='tc1'>
        <!-- 学科带头人工作室 expert_list  -->
        <#if expert_list??>
	        <#if expert_list[0]??>
	          	<#assign user=expert_list[0]>
	              <table class='icontable' border='0' cellpadding='0' cellspacing='0'>
	              <tr>
	              <td class='iconleft'><span class='img_container'><a href='${SiteUrl}go.action?loginName=${user.loginName}'><img src='${SSOServerUrl +"upload/"+user.userIcon!""}' onerror="this.src='${SiteUrl}images/default.gif'" width='64' height='64' border='0' /></a></span></td>
	              <td class='iconright'>
	              <div><strong><a href='${SiteUrl}go.action?loginName=${user.loginName}' target='_blank'>${user.blogName}</a></strong></div>
	              <div><span>真实姓名：</span><a href='${SiteUrl}go.action?loginName=${user.loginName}' target='_blank'>${user.trueName}</a></div>
	              <div><span>所在机构：</span>${user.unitName!?html}</div>
	              <div><span>访问数：</span>${user.visitCount}</div>
	              </td>
	              </tr>
	              <tr>
	              <td colspan='2' class='iconarticle' style='display:none'>
	              <div><span>个人文章：</span></div>
	              <div><a href=''>文章标题文章标题</a></div>
	              <div><a href=''>文章标题文章标题</a></div>
	              <div><a href=''>文章标题文章标题</a></div>
	              <div><a href=''>文章标题文章标题</a></div>
	              </td>
	              </tr>
	              </table>
	              <div style='height:12px;'></div>
			</#if>
	        
			<table cellspacing='0' cellpadding='3' border='0' width='240'>
			<#assign rowCount  = 6>
			<#list 1..rowCount as row>
			<#if row == rowCount>
				<tr align='left'>
	    	       <td class='userbg'>
					<#if expert_list[(row-1) * 2 + 1]?? >
						<#assign user = expert_list[(row-1) * 2 + 1]>
						<a href='${SiteUrl}go.action?loginName=${user.loginName}' target='_blank'>${user.trueName}</a>
					<#else>
						&nbsp;
					</#if>
					</td><td><img src='${SiteThemeUrl}spacer1.gif' /></td>
				    <td class='userbg'>
					<#if expert_list[(row-1) * 2 + 2]?? >
						<#assign user = expert_list[(row-1) * 2 + 2]>
						<a href='${SiteUrl}go.action?loginName=${user.loginName}' target='_blank'>${user.trueName}</a>
					<#else>
						&nbsp;
					</#if>
					</td>
				</tr>
			<#else>
				<tr align='left'>
	    	       <td style='border-bottom:1px solid #d3d2d0;width:50%;' class='userbg'>
					<#if expert_list[(row-1) * 2 + 1]?? >
						<#assign user = expert_list[(row-1) * 2 + 1]>
						<a href='${SiteUrl}go.action?loginName=${user.loginName}' target='_blank'>${user.trueName}</a>
					<#else>
						&nbsp;
					</#if>
					</td><td style='border-bottom:1px solid #d3d2d0'><img src='${SiteThemeUrl}spacer1.gif' /></td>
				    <td style='border-bottom:1px solid #d3d2d0;width:50%' class='userbg'>
					<#if expert_list[(row-1) * 2 + 2]?? >
						<#assign user = expert_list[(row-1) * 2 + 2]>
						<a href='${SiteUrl}go.action?loginName=${user.loginName}' target='_blank'>${user.trueName}</a>
					<#else>
						&nbsp;
					</#if>
					</td>
				</tr>
			</#if>
			</#list>
			</table>
        </#if>
      </div>
      <div style='clear:both;font-size:0'></div>
  </div>
</div>
