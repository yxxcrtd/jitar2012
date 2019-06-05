<#-- 用户列表核心部分 -->
<#if !(cfg_showEmail)??><#assign cfg_showEmail = 'false'></#if>
<table class="listTable" cellspacing="1">
  <thead>
    <tr>
    <th width="6%">&nbsp;</th>
    <th width="6%">ID</th>
    <th width="7%">头像</th>
  <#if cfg_showEmail == 'true'>
    <th width="30%">登录名(昵称,实名,邮件,注册时间)</th>
  <#else>
    <th width="30%">登录名(昵称,实名)</th>
  </#if>
    <th width="16%">学科(学段)</th>
    <th width="20%">所属机构</th>
    <th width="7%">状态</th>
    <th width="9%">操作</th>
    </tr>
  </thead>  
  <tbody>
  <#if userList?size == 0>
    <tr>
      <td colspan='8' style='padding:12px;' align='center' valign='center'>没有找到符合条件的用户</td>
    </tr>
  </#if>
  <#list userList as user>
    <tr>
      <td align="center"><input type="checkbox" id="userId" name="userId" value="${user.userId}" /></td>
      <td align="center">${user.userId}</td>
      <td align="center">
        <a href="${SiteUrl}go.action?loginName=${user.loginName!}" target="_blank"><span style='border:1px solid #ececec;'>
        <img src="${SSOServerUrl +'upload/'+ user.userIcon!''}" onerror="this.src='${ContextPath}images/default.gif'" width="48" height="48" border="0" title="${user.blogName!}" /></span></a>
      </td>                         
      <td>
        <a href="${SiteUrl}go.action?loginName=${user.loginName!}" title="点击访问该工作室" target="_blank">${user.loginName!}</a>
        (${user.nickName!?html})
        <br/> (${user.trueName!?html})
      <#if cfg_showEmail == 'true'>
        <#if user.email??><br/> <a href='mailto:${user.email?html}'>${user.email!?html}</a></#if>
        <#if user.idCard??><br/> 身份证:${user.idCard!?html}</#if>
        <#if user.qq??><br/> QQ:<a href="tencent://Message/?Uin=${user.qq!?html}&websitName=ss&Menu=yes">${user.qq!?html}</a></#if>
        <#if user.createDate??><br/> (${user.createDate?string('yyyy-MM-dd HH:mm:ss')})</#if>
      </#if>
      </td>
      <td>
	   	<#if user.subjectId??>
			${Util.subjectById(user.subjectId).msubjName!?html}
		</#if>
		<br />
      	<#if user.gradeId??>
        	(${Util.gradeById(user.gradeId).gradeName!?html})
      	</#if>
      </td>
      <td align="center">
        <nobr>
        <#if user.userStatus == 0>正常
        <#elseif user.userStatus == 1><font color='red'>待审核</font>
        <#elseif user.userStatus == 3><font color='red'>已锁定</font>
        <#elseif user.userStatus == 2><font color='red'>已删除</font>
        <#else ><font color='red'>未知状态</font>
        </#if>
        </nobr>
      </td>
      <td style="text-align: center">
        <a href="?cmd=edit&userId=${user.userId}">修改</a>
        <br/><a href="?cmd=delete&userId=${user.userId}" onclick="return confirm('确定要删除当前用户吗？');">删除</a>
      </td>
    </tr>
  </#list>
  </tbody>
</table>      
<div class='pager'>
<#if pager?? >
  <#include "../inc/pager.ftl">
</#if>
</div>