<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
  <meta http-equiv="content-type" content="text/html; charset=UTF-8" />
  <title></title>
  <link href="${SiteUrl}css/manage.css" rel="stylesheet" type="text/css" />
  <script>
  function doPost(arg)
  {
       	document.getElementById("oForm").cmd.value=arg;
      	document.getElementById("oForm").submit();
  }
function checkSelectedItem()
{
  gs = document.getElementsByName("guid");
  for(i=0;i<gs.length;i++)
  {
   if(gs[i].checked) return true;
  }
  
  alert("请选择一位用户。");
  return false;
}
  </script>
  <script src='${SiteUrl}js/jitar/core.js'></script>
</head>
<body>
<h2>频道用户管理</h2>
<#if admin_type != "">
<div style='float:right;padding:6px 0'>
<form method="GET">
<input name='channelId' type='hidden' value='${channel.channelId}' />
请输入关键字：
<input name='k' value="${k!?html}" />
<select name='f'>
 <option value='name'<#if f?? && f == 'name'> selected='selected'</#if>>登录名、昵称、真实姓名</option>
 <option value='unitTitle'<#if f?? && f == 'unitTitle'> selected='selected'</#if>>所属机构中文名称</option>
</select>
<input type="submit" class="button" value=" 检  索 " />
</form>
</div>
<div style="float:left;padding-left:10px">
<form method='post'>
<input type="hidden" name="cmd" value="add" />
<input type="hidden" name="guid" value="" id="uid" />
<input type="button" value="选择用户…" onclick='window.open("${SiteUrl}manage/common/user_select.action?type=multi&idTag=uid&titleTag=utitle","_blank","width=800,height=600,resizable=1,scrollbars=1")' />
<input type="submit" value="添加频道用户" onclick="if(document.getElementById('uid').value=='') {alert('请先选择用户。');return false;}" />
</form>
</div>
<div style="clear:both;padding-left:10px;color:red" id="utitle"></div>
<form method='post' id='oForm' style='padding-left:10px'>
<#if user_list??>
<table class="listTable" cellspacing="1">
  <thead>
    <tr>
    <th><input type='checkbox' id='chk' onclick='CommonUtil.SelectAll(this,"guid");' /></th>
    <th style='width:48px;'>头像</th>
    <th>登录名/真实姓名</th>  
    <th>机构</th>
    <th>学段/学科</th>
    <th>
 	<select name='userStatus' onchange='window.location.href="channeluser.action?channelId=${channel.channelId}&ustate=" + this.options[this.selectedIndex].value'>
 	<option value='-1'>用户状态</option>
 	<option value='0'<#if ustate == '0'> selected='selected'</#if>>正常</option>
 	<option value='1'<#if ustate == '1'> selected='selected'</#if>>待审核</option>
 	<option value='2'<#if ustate == '2'> selected='selected'</#if>>已锁定</option>
 	<option value='3'<#if ustate == '3'> selected='selected'</#if>>待删除</option>
 	</select>
 	</th>
    <th>本频道管理权限</th>
    <th>头衔</th>
    </tr>
  </thead>
<#list user_list as user >
<tr>
<td style='width:17px'><input type='checkbox' name='guid' value='${user.userId}' /></td>
<td><img src="${SSOServerUrl +'upload/'+ user.userIcon!''}" onerror="this.src='${ContextPath}images/default.gif'"  width="48" height="48" border="0" title="${user.blogName!}" /></td>
<td>
    <a href='${SiteUrl}go.action?loginName=${user.loginName!}' target='_blank'>${user.loginName!?html}</a><br/>
	<a href='${SiteUrl}go.action?profile=${user.loginName!}' target='_blank'>${user.trueName!?html}</a><br/>
	注册日期：${user.createDate?string('yyyy-MM-dd')}
</td>
<td>
<a href='${SiteUrl}go.action?unitId=${user.unitId}' target='_blank'>${user.unitTitle!?html}</a>
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
<#if user.userStatus == 0>正常
    <#elseif user.userStatus == 1><font color='red'>待审核</font>
    <#elseif user.userStatus == 2><font color='red'>待删除</font>
    <#elseif user.userStatus == 3><font color='red'>已锁定</font>
    <#else ><font color='red'>未知状态</font>
</#if>
</td>
<td>
${Util.getAccessControlListByUserAndObject(user.userId,11,channel.channelId)}<br/>
${Util.getAccessControlListByUserAndObject(user.userId,12,channel.channelId)}<br/>
${Util.getAccessControlListByUserAndObject(user.userId,13,channel.channelId)}
</td>
<td>
<#if user.userType??>
<#assign showTypeName = Util.typeIdToName(user.userType) >
    <#if showTypeName??>
        <#list showTypeName?split("/") as x>
        <#if (x?length) &gt; 0 ><div>${x}</div></#if>
        </#list> 
    </#if>
</#if>
</td>
</tr>
</#list>
</table>
</#if>
<#if pager??>
<#include "/WEB-INF/ftl/pager.ftl">
</#if>
<div style='padding:6px'>
<input class='button' type='button' value=' 全  选 ' onclick='document.getElementById("chk").click();CommonUtil.SelectAll(document.getElementById("chk"),"guid");' />
<select name='cmd'>
<option value=''>选择要执行的管理操作</option>
<option value='remove'<#if cmd == 'remove'> selected='selected'</#if>>从频道中删除所选用户</option>
<#if admin_type == "sys_admin">
<option value='user_admin'<#if cmd == 'user_admin'> selected='selected'</#if>>设置为本频道用户管理员权限</option>
<option value='unuser_admin'<#if cmd == 'unuser_admin'> selected='selected'</#if>>取消本频道用户管理员权限</option>
<option value='content_admin'<#if cmd == 'content_admin'> selected='selected'</#if>>设置为本频道内容管理员权限</option>
<option value='uncontent_admin'<#if cmd == 'uncontent_admin'> selected='selected'</#if>>取消本频道内容管理员权限</option>
</#if>
</select>
<input class='button' type='submit' value='执行选择的操作' onclick='if(checkSelectedItem()==false || this.form.cmd.value==""){alert("请选择一个操作。");return false;}' />

</div>
</#if>
</form>
<div>注：一个用户只能属于一个自定义频道。向频道添加用户，将覆盖该用户之前所加入的频道。</div>
</body>
</html>