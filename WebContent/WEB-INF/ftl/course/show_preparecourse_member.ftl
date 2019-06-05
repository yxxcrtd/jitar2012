<table width='100%' border='0'>
<#list member_list as ugm>
  <#if (ugm.trueName??)><#assign userName = ugm.trueName >
  <#elseif (ugm.nickName??)><#assign userName = ugm.nickName >
  <#elseif (ugm.loginName??)><#assign userName = ugm.loginName >
  </#if>
  <tr>
    <td width='54'>
        <div class="friendImg">
            <a href="${SiteUrl}go.action?loginName=${ugm.loginName}" onmouseover="ToolTip.showUserCard(event,'${ugm.loginName}','${userName}', '${SSOServerUrl +"upload/"+ ugm.userIcon!"images/default.gif"}');">
             <img class="friendIcon" src="${SSOServerUrl +'upload/'+ ugm.userIcon!"images/default.gif"}" 
               alt='${ugm.nickName!}' width='48' onerror="this.src='${SiteUrl}images/default.gif'" />
            </a>
        </div>
        </td>
        <td>
            <div class="friendInfo">
             <div class="friend"><a href='${SiteUrl}go.action?loginName=${ugm.loginName}' target='_blank'>${ugm.nickName!?html}</a></div>
             <div class="friend">加入时间：<br />${ugm.joinDate?string("yyyy-MM-dd")}</div>
             <div class="friend"><a href='${SiteUrl}p/${prepareCourse.prepareCourseId}/0/py/show_preparecourse_user_content.py?userId=${ugm.userId}' target='_blank'>查看个案</a></div>
            </div>
        </td>
    </tr>
</#list>
</table>
<div style='text-align:right'><a href='${SiteUrl}p/${prepareCourse.prepareCourseId}/${prepareCourseStageId}/py/show_preparecourse_member_list.py'>全部人员</a></div>
<#--
 <a href='${SiteUrl}manage/course/createPreCourse2.py?prepareCourseId=${prepareCourse.prepareCourseId}&amp;prepareCourseStageId=${prepareCourseStageId}'>人员管理</a>
-->
