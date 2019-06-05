<div class='c'>
  <div class='c_head'>
    <div class='c_head_right'><a href='blogList.action?type=comiss&subjectId=${subject.metaSubject.msubjId}&gradeId=${gradeId!}'>更多…</a></div>
    <div class='c_head_left titlecolor'> 教研员工作室</div>
  </div>
  <div class='c_content'>
  
        <#if comissioner_list?? >          
        <table border='0' width='100%' cellpadding='0' cellspacing='0'>
        <#assign columnCount = 3>
        <#assign rowCount = 2>
        <#list 0..rowCount -1 as row>
            <tr valign='top'>
            <#list 0..columnCount -1 as cell >   
                <td style='text-align:center;width:33%;padding-bottom:4px;'>
                    <#if comissioner_list[row * columnCount + cell]?? >
                    <#assign u = comissioner_list[row * columnCount + cell] >
                      <div style='height:64px;'>
                      <table border='0' cellpadding='0' cellspacing='0' style='border:1px solid #bdcbd5;padding:1px;margin:auto'>
                      <tr><td><a href='${SiteUrl}go.action?loginName=${u.loginName}'><img src="${SSOServerUrl +'upload/'+ (u.userIcon!'')}" onerror="this.src='${SiteUrl}images/default.gif'" 
                            width='56' height='56' border='0' /></a></td></tr>
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
<#--
        
<#if comissioner_list?? >
  <#if comissioner_list[0]??>
    <#assign u = comissioner_list[0]>
    <div>
      <span class='border_img'><a href='${SiteUrl}go.action?loginName=${u.loginName}' target='_blank'><img src='${u.userIcon!}' width='64' height='64' border='0' /></a></span>
      <div style='font-weight:bold;'><a href='${SiteUrl}go.action?loginName=${u.loginName} target='_blank'>${u.blogName!?html}</a></div>
      <div>创建时间：${u.createDate?string('yyyy-MM-dd')}</div>
      <div>文章数：${u.articleCount!}, 资源数：${u.resourceCount!}</div>
      <div>${u.blogIntroduce!}</div>
		</div>
	</#if>
      <div style='text-align:center;padding:10px 0px;'>
    	<table cellspacing='0' cellpadding='3' border='0' width='216' >
			<#assign rowCount = 2>
			<#assign columnCount = 3>
			<#list 1..rowCount as row>
			<#if row == rowCount>
			<tr align='left'>
	        <td class='userbg' style='width:33%;'>
					<#if comissioner_list[(row-1) * columnCount + 1]?? >
						<#assign user = comissioner_list[(row-1) * columnCount + 1]>
						<a href='${SiteUrl}go.action?loginName=${user.loginName}' target='_blank'>${user.nickName}</a>
					<#else>
						&nbsp;
					</#if>
					</td><td><img src='${SiteThemeUrl}spacer1.gif' /></td>
				    <td class='userbg' style='width:33%;'>
					<#if comissioner_list[(row-1) * columnCount + 2]?? >
						<#assign user = comissioner_list[(row-1) * columnCount + 2]>
						<a href='${SiteUrl}go.action?loginName=${user.loginName}' target='_blank'>${user.nickName}</a>
					<#else>
						&nbsp;
					</#if>
					</td>
					<td><img src='${SiteThemeUrl}spacer1.gif' /></td>
				    <td class='userbg' style='width:33%;'>
					<#if comissioner_list[(row-1) * columnCount + 3]?? >
						<#assign user = comissioner_list[(row-1) * columnCount + 3]>
						<a href='${SiteUrl}go.action?loginName=${user.loginName}' target='_blank'>${user.nickName}</a>
					<#else>
						&nbsp;
					</#if>
					</td>
				</tr>
			<#else>
			<tr align='left'>
	        <td style='border-bottom:1px solid #d3d2d0;' class='userbg'>
					<#if comissioner_list[(row-1) * columnCount + 1]?? >
						<#assign user = comissioner_list[(row-1) * columnCount + 1]>
						<a href='${SiteUrl}go.action?loginName=${user.loginName}' target='_blank'>${user.nickName}</a>
					<#else>
						&nbsp;
					</#if>
					</td><td style='border-bottom:1px solid #d3d2d0'><img src='${SiteThemeUrl}spacer1.gif' /></td>
				    <td style='border-bottom:1px solid #d3d2d0;' class='userbg'>
					<#if comissioner_list[(row-1) * columnCount + 2]?? >
						<#assign user = comissioner_list[(row-1) * columnCount + 2]>
						<a href='${SiteUrl}go.action?loginName=${user.loginName}' target='_blank'>${user.nickName}</a>
					<#else>
						&nbsp;
					</#if>
					</td>
					<td style='border-bottom:1px solid #d3d2d0'><img src='${SiteThemeUrl}spacer1.gif' /></td>
				    <td style='border-bottom:1px solid #d3d2d0;' class='userbg'>
					<#if comissioner_list[(row-1) * columnCount + 3]?? >
						<#assign user = comissioner_list[(row-1) * columnCount + 3]>
						<a href='${SiteUrl}go.action?loginName=${user.loginName}' target='_blank'>${user.nickName}</a>
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
-->
  </div>        
</div> 
