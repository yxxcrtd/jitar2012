<#-- 研修之星 -->
<div class='m1' style="height:126px;">
  <div class='m1_head'>
    <div class='m1_head_right'><a href='blogList.action?type=5'>更多…</a></div>
    <div class='m1_head_left'>&nbsp;<img src='${ContextPath}css/index/j.gif' /> ${Util.typeIdToName(5)}</div>
  </div>
  <div class='m1_content'>
<#if teacher_star??>
    <div class='tdleft'><img src='${SiteThemeUrl}mimg2.jpg' /></div>
    <div class='tdright' style='padding-top:6px;text-align:center;'>
    <table cellspacing='0' cellpadding='3' border='0' align='center'>
    <tr align='left'>
    <td style='border-bottom:1px solid #d3d2d0'>
	<#if teacher_star[0]?? >
		<#assign u = teacher_star[0]>
		<a href='go.action?loginName=${u.loginName}' target='_blank'>${u.trueName}</a>
	<#else>
		&nbsp;
	</#if>
	</td><td style='border-bottom:1px solid #d3d2d0'><img src='${SiteThemeUrl}spacer1.gif' /></td>
    <td style='border-bottom:1px solid #d3d2d0'>
	<#if teacher_star[1]?? >
		<#assign u = teacher_star[1]>
		<a href='go.action?loginName=${u.loginName}' target='_blank'>${u.trueName}</a>
	<#else>
		&nbsp;
	</#if>
	</td><td style='border-bottom:1px solid #d3d2d0'><img src='${SiteThemeUrl}spacer1.gif' /></td>
    <td style='border-bottom:1px solid #d3d2d0'>
	<#if teacher_star[2]?? >
		<#assign u = teacher_star[2]>
		<a href='go.action?loginName=${u.loginName}' target='_blank'>${u.trueName}</a>
	<#else>
		&nbsp;
	</#if>
	</td>
    </tr>
    <tr align='left'>
    <td style='border-bottom:1px solid #d3d2d0'>	
	<#if teacher_star[3]?? >
		<#assign u = teacher_star[3]>
		<a href='go.action?loginName=${u.loginName}' target='_blank'>${u.trueName}</a>
	<#else>
		&nbsp;
	</#if>
	</td><td style='border-bottom:1px solid #d3d2d0'><img src='${SiteThemeUrl}spacer1.gif' /></td>
    <td style='border-bottom:1px solid #d3d2d0'>	
	<#if teacher_star[4]?? >
		<#assign u = teacher_star[4]>
		<a href='go.action?loginName=${u.loginName}' target='_blank'>${u.trueName}</a>
	<#else>
		&nbsp;
	</#if>
	</td><td style='border-bottom:1px solid #d3d2d0'><img src='${SiteThemeUrl}spacer1.gif' /></td>
    <td style='border-bottom:1px solid #d3d2d0'>
	<#if  teacher_star[5]?? >
		<#assign u = teacher_star[5]>
		<a href='go.action?loginName=${u.loginName}' target='_blank'>${u.trueName}</a>
	<#else>
		&nbsp;
	</#if>
	</td>
    </tr>
    <tr align='left'>
    <td style='border-bottom:1px solid #d3d2d0'>
	<#if teacher_star[6]?? >
		<#assign u = teacher_star[6]>
		<a href='go.action?loginName=${u.loginName}' target='_blank'>${u.trueName}</a>
	<#else>
		&nbsp;
	</#if>
	</td><td style='border-bottom:1px solid #d3d2d0'><img src='${SiteThemeUrl}spacer1.gif' /></td>
    <td style='border-bottom:1px solid #d3d2d0'>
	<#if teacher_star[7]?? >
		<#assign u = teacher_star[7]>
		<a href='go.action?loginName=${u.loginName}' target='_blank'>${u.trueName}</a>
	<#else>
		&nbsp;
	</#if>
	</td><td style='border-bottom:1px solid #d3d2d0'><img src='${SiteThemeUrl}spacer1.gif' /></td>
    <td style='border-bottom:1px solid #d3d2d0'>
	<#if teacher_star[8]?? >
		<#assign u = teacher_star[8]>
		<a href='go.action?loginName=${u.loginName}' target='_blank'>${u.trueName}</a>
	<#else>
		&nbsp;
	</#if>
	</td>
    </tr>
    <tr align='left'>
    <td>    
	<#if teacher_star[9]?? >
		<#assign u = teacher_star[9]>
		<a href='go.action?loginName=${u.loginName}' target='_blank'>${u.trueName}</a>
	<#else>
		&nbsp;
	</#if>
	</td><td><img src='${SiteThemeUrl}spacer1.gif' /></td>
    <td>    
	<#if teacher_star[10]?? >
	<#assign u = teacher_star[10]>
		<a href='go.action?loginName=${u.loginName}' target='_blank'>${u.trueName}</a>
	<#else>
	&nbsp;
	</#if>
	</td><td><img src='${SiteThemeUrl}spacer1.gif' /></td>
    <td>	
	<#if teacher_star[11]?? >
		<#assign u = teacher_star[11]>
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