<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
  <link rel="stylesheet" type="text/css" href="../../css/manage.css" />
</head>
<body>
  <table class='listTable' cellspacing='1' style="width:90%;margin:0 auto;">
    <thead>
      <tr>
        <th width='17'></th>
        <th>成员</th> 
        <th>状态/角色</th>
      </tr>
    </thead>
    <tbody>
    <#list user_list as member>
     <#if member.userId != prepareCourse.createUserId >
      <tr>
        <td><input type='radio' name='userId' onclick="window.parent.document.getElementById('u_id').value='${member.userId}'" /></td>
        <td>
          <a href='${SiteUrl}go.action?loginName=${member.loginName}' target='_blank'>${member.nickName!}</a>(${member.loginName!})
        </td>
        <td>
         <#if member.status == 0>正常
         <#elseif member.status == 1>
         	<font color='red'>待审核</font>
         <#else>
         	未知
         </#if>
         [<#if member.userId == prepareCourse.createUserId || member.userId == prepareCourse.leaderId >
         <#if member.userId == prepareCourse.leaderId ><font color='blue'>主备人</font></#if>
         <#if member.userId == prepareCourse.createUserId ><font color='red'>发起人</font></#if>       
         <#else>普通成员</#if>]
        </td>
        </tr>
   </#if>
  </#list>
  </tbody>
</table>
</body>
</html>
