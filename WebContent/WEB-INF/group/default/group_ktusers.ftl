<table width='100%' border='0'>
<#list manager as ktuser>
<#if users[ktuser_index]??>
  <#assign user=users[ktuser_index]>  
  <tr>
        <td width='54'>
            <div class="friendImg">
                <a href="${SiteUrl}go.action?userId=${ktuser.teacherId}" onmouseover="ToolTip.showUserCard(event,'${user.loginName}','${ktuser.teacherName!?html}', '${SSOServerUrl +"upload/"+user.userIcon!"images/default.gif"}');">
                 <img class="friendIcon" src="${SSOServerUrl +'upload/'+user.userIcon!"images/default.gif"}" 
                   alt='${user.nickName!}' width='48' onerror="this.src='${SiteUrl}images/default.gif'" />
                </a>
                <br/><a href='${SiteUrl}go.action?userId=${ktuser.teacherId}' target='_blank'>${ktuser.teacherName!?html}</a>
            </div>
        </td>
        <td>
            <div class="friendInfo">
             <div class="friend"><b>单位</b>：${ktuser.teacherUnit!?html}</div>
             <div class="friend"><b>行政职务</b>：${ktuser.teacherXZZW!?html}</div>
             <div class="friend"><b>专业职务</b>：${ktuser.teacherZYZW!?html}</div>
             <div class="friend"><b>学历</b>：${ktuser.teacherXL!?html}</div>
             <div class="friend"><b>学位</b>：${ktuser.teacherXW!?html}</div>
             <div class="friend"><b>研究专长</b>：${ktuser.teacherYJZC!?html}</div>
            </div>
        </td>
    </tr>
    <tr>
        <td colspan='2' style="text-align:center"><hr style="width:90%";></td>
    </tr>
</#if>    
</#list>
</table>

