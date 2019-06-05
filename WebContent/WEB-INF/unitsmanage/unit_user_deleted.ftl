<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
  <meta http-equiv="content-type" content="text/html; charset=UTF-8" />
  <title></title>
  <link href="${SiteUrl}css/manage.css" rel="stylesheet" type="text/css" />
  <script>
  function doPost(arg)
  {
  	document.getElementById("fm").cmd.value=arg;
  	document.getElementById("fm").submit();
  }
  </script>
  <script src='${SiteUrl}js/jitar/core.js'></script>
</head>
<body>
<h2>机构待删除用户管理</h2>
<div style='text-align:right;padding:6px 0'>
<form method="GET">
<input name='unitId' type='hidden' value='${unit.unitId}' />
请输入用户的登录名、昵称或者真实姓名：
<input name='k' value="${k!?html}" />
<input name='f' type='hidden' value='name' />
<input type="submit" class="button" value=" 检  索 " />
</form>
</div>
<form method='post' style='padding-left:20px' id="fm" action="?">
<input name='cmd' type='hidden' value='' />
<input name='unitId' type='hidden' value='${unit.unitId}' />
<#if user_list??>
<table class="listTable" cellspacing="1">
  <thead>
    <tr style="text-align:left">
    <th style="width:17px"><input type='checkbox' id='chk' onclick='CommonUtil.SelectAll(this,"guid");' /></th>
    <th style='width:48px;'>头像</th>
    <th>昵称,实名</th>  
    <th>学段/学科</th>
    <th>头衔</th>
    <th>状态</th>
    <th>操作</th>
    </tr>
  </thead>
<#list user_list as user >
<tr>
<td style='width:17px'><input type='checkbox' name='guid' value='${user.userId}' /></td>
<td><img src="${SSOServerUrl +'upload/'+user.userIcon!"images/default.gif"}" onerror="this.src='${SiteUrl}images/default.gif';" width="48" height="48" border="0" title="${user.blogName!}" /></td>
<td>
	<a href='${SiteUrl}go.action?loginName=${user.loginName}' target='_blank'>${user.nickName?html}</a><br/>
	<a href='${SiteUrl}go.action?loginName=${user.loginName}' target='_blank'>${user.trueName?html}</a><br/>
	注册日期：${user.createDate?string('yyyy-MM-dd')}
</td>
<td>
<#assign usgList = Util.getSubjectGradeListByUserId(user.userId)>
<#if usgList?? && (usgList?size> 0) >
<#list usgList as usg>
<#if usg.gradeId??>${Util.gradeById(usg.gradeId).gradeName!?html}<#else>未设置</#if>/<#if usg.subjectId??>${Util.subjectById(usg.subjectId).msubjName!?html}<#else>未设置</#if>
<#if usg_has_next><br/></#if>
</#list>
</#if>
</td>
<td>
<#if user.userType??>
<#assign showTypeName = Util.typeIdToName(user.userType) >
    <#if showTypeName??>
        <#list showTypeName?split("/") as x>
        <#if (x?length) &gt; 0 >${x}</#if>
        </#list> 
    </#if>
</#if>	
</td>
<td>
<#if user.userStatus == 0>正常
    <#elseif user.userStatus == 1><font color='red'>待审核</font>
    <#elseif user.userStatus == 3><font color='red'>已锁定</font>
    <#elseif user.userStatus == 2><font color='red'>已删除</font>
    <#else ><font color='red'>未知状态</font>
</#if>
</td>
<td><#if user.userStatus == 2><a href="crash_unit_user.py?unitId=${unit.unitId}&userId=${user.userId}" onclick="return confirm('你真的要删除 ${user.trueName!?js_string} 吗？\r\n\r\n删除用户将将删除该用户的全部内容，并且不可恢复。')">彻底删除</a></#if></td>
</tr>
</#list>
</table>
</#if>
<#if pager??>
<#include "/WEB-INF/ftl/pager.ftl">
</#if>
<div style='padding:6px'>
<input class='button' type='button' value=' 全  选 ' onclick='document.getElementById("chk").click();CommonUtil.SelectAll(document.getElementById("chk"),"guid");' />
<input class='button' type='button' value='恢复正常' onclick="doPost('audit')" />
</div>
</form>
</body>
</html>