<div class='m2' style="height:126px;">
  <div class='m1_head'>
    <div class='m1_head_right'><a href='blogList.action?type=3'>更多…</a></div>
    <div class='m1_head_left'>&nbsp;<img src='${ContextPath}css/index/j.gif' /> ${Util.typeIdToName(3)}</div>
  </div>
  <div class='m1_content'>
<#if expert_list?? >
    <div class='tdleft'><img src='${SiteThemeUrl}mimg1.jpg' /></div>
    <div class='tdright' style='padding-top:6px;text-align:center;'>
    <table cellspacing='0' cellpadding='3' border='0' align='center'>
    <tr align='left'>
    <td style='border-bottom:1px solid #d3d2d0'>
	<#if expert_list[0]?? >
		<#assign u = expert_list[0]>
		<a href='go.action?loginName=${u.loginName}' target='_blank'>${u.trueName}</a>
	<#else>
		&nbsp;
	</#if>
	</td><td style='border-bottom:1px solid #d3d2d0'><img src='${SiteThemeUrl}spacer1.gif' /></td>
    <td style='border-bottom:1px solid #d3d2d0'>
	<#if expert_list[1]?? >
		<#assign u = expert_list[1]>
		<a href='go.action?loginName=${u.loginName}' target='_blank'>${u.trueName}</a>
	<#else>
		&nbsp;
	</#if>
	</td><td style='border-bottom:1px solid #d3d2d0'><img src='${SiteThemeUrl}spacer1.gif' /></td>
    <td style='border-bottom:1px solid #d3d2d0'>
	<#if expert_list[2]?? >
		<#assign u = expert_list[2]>
		<a href='go.action?loginName=${u.loginName}' target='_blank'>${u.trueName}</a>
	<#else>
		&nbsp;
	</#if>
	</td>
    </tr>
    <tr align='left'>
    <td style='border-bottom:1px solid #d3d2d0'>	
	<#if expert_list[3]?? >
		<#assign u = expert_list[3]>
		<a href='go.action?loginName=${u.loginName}' target='_blank'>${u.trueName}</a>
	<#else>
		&nbsp;
	</#if>
	</td><td style='border-bottom:1px solid #d3d2d0'><img src='${SiteThemeUrl}spacer1.gif' /></td>
    <td style='border-bottom:1px solid #d3d2d0'>	
	<#if expert_list[4]?? >
		<#assign u = expert_list[4]>
		<a href='go.action?loginName=${u.loginName}' target='_blank'>${u.trueName}</a>
	<#else>
		&nbsp;
	</#if>
	</td><td style='border-bottom:1px solid #d3d2d0'><img src='${SiteThemeUrl}spacer1.gif' /></td>
    <td style='border-bottom:1px solid #d3d2d0'>
	<#if  expert_list[5]?? >
		<#assign u = expert_list[5]>
		<a href='go.action?loginName=${u.loginName}' target='_blank'>${u.trueName}</a>
	<#else>
		&nbsp;
	</#if>
	</td>
    </tr>
    <tr align='left'>
    <td style='border-bottom:1px solid #d3d2d0'>
	<#if expert_list[6]?? >
		<#assign u = expert_list[6]>
		<a href='go.action?loginName=${u.loginName}' target='_blank'>${u.trueName}</a>
	<#else>
		&nbsp;
	</#if>
	</td><td style='border-bottom:1px solid #d3d2d0'><img src='${SiteThemeUrl}spacer1.gif' /></td>
    <td style='border-bottom:1px solid #d3d2d0'>
	<#if expert_list[7]?? >
		<#assign u = expert_list[7]>
		<a href='go.action?loginName=${u.loginName}' target='_blank'>${u.trueName}</a>
	<#else>
		&nbsp;
	</#if>
	</td><td style='border-bottom:1px solid #d3d2d0'><img src='${SiteThemeUrl}spacer1.gif' /></td>
    <td style='border-bottom:1px solid #d3d2d0'>
	<#if expert_list[8]?? >
		<#assign u = expert_list[8]>
		<a href='go.action?loginName=${u.loginName}' target='_blank'>${u.trueName}</a>
	<#else>
		&nbsp;
	</#if>
	</td>
    </tr>
    <tr align='left'>
    <td>    
	<#if expert_list[9]?? >
		<#assign u = expert_list[9]>
		<a href='go.action?loginName=${u.loginName}' target='_blank'>${u.trueName}</a>
	<#else>
		&nbsp;
	</#if>
	</td><td><img src='${SiteThemeUrl}spacer1.gif' /></td>
    <td>    
	<#if expert_list[10]?? >
	<#assign u = expert_list[10]>
		<a href='go.action?loginName=${u.loginName}' target='_blank'>${u.trueName}</a>
	<#else>
	&nbsp;
	</#if>
	</td><td><img src='${SiteThemeUrl}spacer1.gif' /></td>
    <td>	
	<#if expert_list[11]?? >
		<#assign u = expert_list[11]>
		<a href='go.action?loginName=${u.loginName}' target='_blank'>${u.trueName}</a>
	<#else>
	 &nbsp;
	</#if>
	</td>
    </tr>
    </table>
    </div>
</#if>
<div style="font-size:0;height:0;clear:both"></div>
  </div>        
</div>     
