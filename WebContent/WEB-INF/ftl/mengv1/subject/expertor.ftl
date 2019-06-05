<div class='c'>
  <div class='c_head'>
    <div class='c_head_right'><a href='blogList.action?type=expert&subjectId=${subject.metaSubject.msubjId}&gradeId=${gradeId!}'>更多…</a></div>
    <div class='c_head_left titlecolor'> 学科带头人</div>
  </div>
  <div class='c_content'>
<#if expert_user_list?? >
    <div style='text-align:center'><img src = '${SiteThemeUrl}mimg1.jpg' hspace='4' vspace='2' /></div>
    <div style='text-align:center;padding:10px 0px;'>
    
    	<table cellspacing='0' cellpadding='3' border='0' width='216' >
			<#assign rowCount = 2>
			<#assign columnCount = 3>
			<#list 0..rowCount - 1 as row>
			
			<#if row == rowCount>
				<tr align='left'>
	    	       <td class='userbg' style='width:33%;'>
	    				<#if expert_user_list[row * columnCount]?? >
						<#assign user = expert_user_list[row * columnCount]>
						<a href='${SiteUrl}go.action?loginName=${user.loginName}' target='_blank'>${user.trueName}</a>
					<#else>
						&nbsp;
					</#if>
					</td><td><img src='${SiteThemeUrl}spacer1.gif' /></td>
				    <td class='userbg' style='width:33%;'>
					<#if expert_user_list[row * columnCount + 1]?? >
						<#assign user = expert_user_list[row * columnCount + 1]>
						<a href='${SiteUrl}go.action?loginName=${user.loginName}' target='_blank'>${user.trueName}</a>
					<#else>
						&nbsp;
					</#if>
					</td>
					<td><img src='${SiteThemeUrl}spacer1.gif' /></td>
				    <td class='userbg' style='width:33%;'>
					<#if expert_user_list[row * columnCount + 2]?? >
						<#assign user = expert_user_list[row * columnCount + 2]>
						<a href='${SiteUrl}go.action?loginName=${user.loginName}' target='_blank'>${user.trueName}</a>
					<#else>
						&nbsp;
					</#if>
					</td>
				</tr>
			<#else>
				<tr align='left'>
	    	       <td style='border-bottom:1px solid #d3d2d0;' class='userbg'>
					<#if expert_user_list[row * columnCount]?? >
						<#assign user = expert_user_list[row * columnCount]>
						<a href='${SiteUrl}go.action?loginName=${user.loginName}' target='_blank'>${user.trueName}</a>
					<#else>
						&nbsp;
					</#if>
					</td><td style='border-bottom:1px solid #d3d2d0'><img src='${SiteThemeUrl}spacer1.gif' /></td>
				    <td style='border-bottom:1px solid #d3d2d0;' class='userbg'>
					<#if expert_user_list[row * columnCount + 1]?? >
						<#assign user = expert_user_list[row * columnCount + 1]>
						<a href='${SiteUrl}go.action?loginName=${user.loginName}' target='_blank'>${user.trueName}</a>
					<#else>
						&nbsp;
					</#if>
					</td>
					<td style='border-bottom:1px solid #d3d2d0'><img src='${SiteThemeUrl}spacer1.gif' /></td>
				    <td style='border-bottom:1px solid #d3d2d0;' class='userbg'>
								<#if expert_user_list[row * columnCount + 2]?? >
						<#assign user = expert_user_list[row * columnCount + 2]>
						<a href='${SiteUrl}go.action?loginName=${user.loginName}' target='_blank'>${user.trueName}</a>
					<#else>
						&nbsp;
					</#if>
					</td>
				</tr>
			</#if>
			</#list>
			</table>
    </div>
</#if>
  </div>        
</div> 
