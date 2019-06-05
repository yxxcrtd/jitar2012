<#assign grpName="协作组">
<#if isKtGroup??>
    <#if isKtGroup=="1">
        <#assign grpName="课题组"> 
    <#elseif isKtGroup=="2">
        <#assign grpName="备课组"> 
    <#else>
        <#assign grpName="协作组">
    </#if>
</#if>
<table width='100%' border='0'>
<#if ugm_list??>
<#list ugm_list as ugm>
  <#if (ugm.trueName??)><#assign userName = ugm.trueName >
  <#elseif (ugm.nickName??)><#assign userName = ugm.nickName >
  <#elseif (ugm.loginName??)><#assign userName = ugm.loginName >
  </#if>
  <#if isKtGroup=="1">
      <tr>
        <td width='56'>
    			<div class="friendImg">
    				<a href="${SiteUrl}go.action?loginName=${ugm.loginName}" onmouseover="ToolTip.showUserCard(event,'${ugm.loginName}','${userName}', '${SSOServerUrl +"upload/"+ugm.userIcon!'images/default.gif'}');">
    				 <img class="friendIcon" src="${SSOServerUrl +'upload/'+ugm.userIcon!'images/default.gif'}" 
    				   alt='${ugm.nickName!}' width='48' onerror="this.src='${SiteUrl}images/default.gif'" />
    				</a>
    				<br/><a href='${SiteUrl}go.action?loginName=${ugm.loginName}' target='_blank'>${ugm.nickName!?html}</a>
    				<!--加入时间：<br />${ugm.joinDate?string("yyyy-MM-dd")}-->
    			</div>
    		</td>
    		<td>
    			<div class="friendInfo">
                 <div class="friend"><b>单位</b>：${ugm.teacherUnit!?html}</div>
                 <div class="friend"><b>专业职务</b>：${ugm.teacherZYZW!?html}</div>
                 <div class="friend"><b>学历</b>：${ugm.teacherXL!?html}</div>
                 <div class="friend"><b>学位</b>：${ugm.teacherXW!?html}</div>
                 <div class="friend"><b>研究专长</b>：${ugm.teacherYJZC!?html}</div>
    			</div>
    		</td>
    	</tr>
        <tr>
            <td colspan='2' style="text-align:center"><hr style="width:90%";></td>
        </tr>	
    <#else>
      <tr>
        <td width='54'>
                <div class="friendImg">
                    <a href="${SiteUrl}go.action?loginName=${ugm.loginName}" onmouseover="ToolTip.showUserCard(event,'${ugm.loginName}','${userName}', '${SSOServerUrl +"upload/"+ugm.userIcon!'images/default.gif'}');">
                     <img class="friendIcon" src="${SSOServerUrl +'upload/'+ugm.userIcon!'images/default.gif'}" 
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
    </#if>
</#list>
</#if>
</table>
<div style='text-align:right'><a href='${SiteUrl}g/${group.groupName}/py/group_member_list.py'>全部成员</a></div>