<table width='100%' border='0'>
<#list ugm_list as ugm>
  <#if (ugm.trueName??)><#assign userName = ugm.trueName >
  <#elseif (ugm.nickName??)><#assign userName = ugm.nickName >
  <#elseif (ugm.loginName??)><#assign userName = ugm.loginName >
  </#if>
  <tr>
    <td width='54'>
      <div class="friendImg">
        <a href="${SiteUrl}go.action?loginName=${ugm.loginName}" onmouseover="ToolTip.showUserCard(event,'${ugm.loginName}','${userName}', '${SSOServerUrl +"upload/"+ugm.userIcon!"images/default.gif"}');">
         <img class="friendIcon" src="${SSOServerUrl +'upload/'+ugm.userIcon!"images/default.gif"}" 
           alt='${ugm.nickName!}' width='48' onerror="this.src='${SiteUrl}images/default.gif'" />
        </a>
      </div>
		</td>
		<td>
			<div class="friendInfo">
       <div class="friend"><a href='${SiteUrl}go.action?loginName=${ugm.loginName}' target='_blank'>${ugm.nickName!?html}</a></div>
       <div class="friend">加入时间：<br />${ugm.joinDate?string("yyyy-MM-dd")}</div>
			</div>
		</td>
	</tr>
</#list>
</table>