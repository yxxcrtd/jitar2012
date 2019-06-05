<#assign grpName="协作组">
<#assign grpShowName="小组">
<#if isKtGroup??>
    <#if isKtGroup=="1">
        <#assign grpName="课题组"> 
        <#assign grpShowName="课题">
    <#elseif isKtGroup=="2">
        <#assign grpName="备课组"> 
        <#assign grpShowName="小组">
    <#else>
        <#assign grpName="协作组">
        <#assign grpShowName="小组">
    </#if>
</#if>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html;charset=UTF-8" />
  <title>管理${grpName}</title>
  <link rel="stylesheet" type="text/css" href="../css/manage.css" />
</head>
<body>
<#include 'group_title.ftl' >
<div class='pos'>
  您现在的位置： <a href='group.py?cmd=home&amp;groupId=${group.groupId}'>${grpName}管理首页</a>
  &gt;&gt; <span>${grpName}成员管理</span>
</div>
<br/>

<form name='theForm' action='?' method='post'>
  <input type='hidden' name='cmd' value='list' />  
  <input type='hidden' name='groupId' value='${group.groupId}' />
  <table class='listTable' cellspacing='1'>
    <thead>
      <tr>
        <th width='48'>选择</th>
        <th width='460'>成员</th>
        <th width='100'>是否在线</th>
        <th width='120'>加入时间</th>
        <th width='64'>状态/角色</th>
        <th width='64'>操作</th>
      </tr>
    </thead>
    <tbody>
   <#if member_list??>
	<#list member_list as member>
		<tr>
			<td>
				<input type='checkbox' name='userId' value='${member.userId}' />${member.userId}
			</td>
	        <td>
				<table width='100%' style='border:0' border='0' cellspacing='0' cellpadding='0'>
					<tr>
						<td width='50' style='border:0'>
							<img src="${SSOServerUrl +'upload/'+ member.userIcon!''}" width='48' border='0' onerror="this.src='${ContextPath}images/default.gif'"/>
						</td>
						<td align='left' valign='top' style='border:0'>
							<a href='${SiteUrl}go.action?loginName=${member.loginName}' target='_blank'>${member.nickName!}</a>(${member.loginName!})
							<br/>组内文章: ${member.articleCount!}, 资源: ${member.resourceCount!}, 主题: ${member.topicCount!}
						</td>
					</tr>
				</table>
	        </td>
	        <td>${Util.isOnline(member.userId)?string("<font style='color:red'>在线</font>","不在线")}</td>
        <td>${member.joinDate}</td>
        <td>
         <input type='hidden' name='memberstatus${member.userId}' id='memberstatus${member.userId}' value='${member.status}' />
         <#if member.status == 0>正常
         <#elseif member.status == 1><font color='red'>待审核</font>
         <#elseif member.status == 2><font color='red'>删除</font>
         <#elseif member.status == 3><font color='red'>锁定</font>
         <#elseif member.status == 4><font color='red'>已邀请</font>
         </#if>
         <br/>
         <#if member.groupRole == 0>普通成员
         <#elseif member.groupRole == 1000><font color='blue'>管理员</font>
         <#elseif member.groupRole == 800><font color='blue'>副管理员</font>
         <#else>普通成员
         </#if>
        </td>
        <#-- 管理操作 -->
        <td>
    <#if (group_member.groupRole >= 800) >
      <#if member.status == 0>
        <#if member.groupRole == 0>
          <a href='?cmd=delete_member&amp;groupId=${group.groupId}&amp;userId=${member.userId}' onclick='return confirm_delete();'>删除</a>
          <a href='?cmd=lock_member&amp;groupId=${group.groupId}&amp;userId=${member.userId}'>锁定</a>
        </#if>
      </#if>
      <#if member.status == 1>
          <a href='?cmd=audit_member&amp;groupId=${group.groupId}&amp;userId=${member.userId}'>审核通过</a>
          <a href='?cmd=delete_member&amp;groupId=${group.groupId}&amp;userId=${member.userId}' onclick='return confirm_delete();'>删除申请</a>
      </#if>
      <#if member.status == 2>
          <a href='?cmd=recover_member&amp;groupId=${group.groupId}&amp;userId=${member.userId}'>恢复</a>
      </#if>
      <#if member.status == 3>
          <a href='?cmd=unlock_member&amp;groupId=${group.groupId}&amp;userId=${member.userId}'>解锁</a>
      </#if>
      <#if member.status == 4>
          <a href='?cmd=uninvite_member&amp;groupId=${group.groupId}&amp;userId=${member.userId}'>取消邀请</a>
      </#if>
      <#if isKtGroup??>
      <#if isKtGroup=="1">
        <a href='?cmd=edit_member&amp;groupId=${group.groupId}&amp;userId=${member.userId}'>成员信息</a>
      </#if>
      </#if>
    </#if>
    <#if loginUser.userId == group.createUserId && group.createUserId != member.userId && member.status == 0>
      <a href='?cmd=set_creator&amp;groupId=${group.groupId}&amp;userId=${member.userId}' onclick='return confirm_set_creator();'>转让</a>
    </#if>
        </td>
      </tr>
    </#list>
    </#if>
    </tbody>
  </table>
  
  <div class='pager'>
    <#include '../inc/pager.ftl' >
  </div>
	<div class='funcButton'>
	<#if (group_member.groupRole >= 800) >
	  <input type='button' class='button' value=' 审核通过 ' onclick='javascript:audit_m();' /> 
    <input type='button' class='button' value=' 锁 定 ' onclick='javascript:lock_m();' />
    <input type='button' class='button' value=' 解 锁 ' onclick='javascript:unlock_m();' />
	  <input type='button' class='button' value=' 删 除 ' onclick='javascript:delete_m();' />
	  <input type='button' class='button' value=' 恢 复 ' onclick='javascript:recover_m();' />
	</#if>
	  <input type='button' class='button' value=' 邀请成员 ' onclick='document.inviteMember.submit();' />
	<#if (group_member.groupRole >= 800) >
	  <input type='button' class='button' value=' 取消邀请 ' onclick='javascript:uninvite_m();' />
	</#if>
	<#if (group_member.groupRole >= 1000) >
	  <select onchange='execute_select(this)'>
	    <option value=''>执行操作</option>
	    <option value='set_vice'>设置为副管理员</option>
	    <option value='unset_vice'>取消副管理员</option>
	  <#if loginUser.userId == group.createUserId >
	    <option value='set_creator'>转让协作组给...</option>
	  </#if>
	  </select>
	</#if>
	</div>
</form>

<div class='funcButton help'>

</div>

<form name='inviteMember' action='groupMember.action' method='get' style='display:none'>
  <input type='hidden' name='cmd' value='invite' />
  <input type='hidden' name='groupId' value='${group.groupId}' />
</form>
<script language='javascript'>
function confirm_delete() {
  return window.confirm('你是否确定删除指定的群组成员?');
}
function getTheForm() {
  return document.forms['theForm'];
}
function audit_m() {
  submitCommand('audit_member');
}
function delete_m() {
  if (has_item_selected()) {
    if (confirm('您是否确定从协作组中删除选中的成员用户??') == false)
      return;
  }
  submitCommand('delete_member');
}
function recover_m() {
  submitCommand('recover_member');
}
function lock_m() {
  submitCommand('lock_member');
}
function unlock_m() {
  submitCommand('unlock_member');
}
function uninvite_m() {
  submitCommand('uninvite_member');
}
function confirm_set_creator() {
  return confirm('您是否确定将协作组转让给指定用户??\r\n\r\n被转让的用户将成为协作组创建者和管理员, 而您自己将转为为副管理员.');
}
function execute_select(sel) {
  var cmd = sel.options[sel.selectedIndex].value;
  if (cmd == '' || cmd == null) return;
  var uid=0;
  if (cmd == 'set_creator') {
    if (has_one_selected() == false) {
      alert('为转让协作组, 您必须选择一个且只能选择一个转让对象.');
      sel.selectedIndex = 0;
      return;
    }
    uid = user_one_selectedId();
    if(document.getElementById("memberstatus"+uid).value != 0)
    {
        alert('只能转让给状态正常的成员');
        sel.selectedIndex = 0;
        return ;
    } 
    if (confirm_set_creator() == false){
    sel.selectedIndex = 0;
    return;
    }
  }
  if (cmd == 'set_vice')
  {
      var oids = document.getElementsByName('userId');
      if (oids != null){
          for (var i = 0; i < oids.length; ++i) {
            if (oids[i].checked){
                uid = oids[i].value; 
                if(document.getElementById("memberstatus"+uid).value != 0)
                {
                    alert('只有状态正常的成员才能设置为副管理员');
                    sel.selectedIndex = 0;
                    return ;
                }                
            }
          }    
      }
  }
  submitCommand(cmd);
}
function submitCommand(cmd) {
  if (has_item_selected() == false) {
    alert('没有选择要操作的协作组成员.');
    return;
  }
  var form = getTheForm();
  form.cmd.value = cmd;
  form.submit();
}
function has_one_selected() {
  var ids = document.getElementsByName('userId');
  if (ids == null) return false;
  var count = 0;
  for (var i = 0; i < ids.length; ++i) {
    if (ids[i].checked) ++count;
  }
  return (count == 1);
}

function user_one_selectedId() {
  var ids = document.getElementsByName('userId');
  if (ids == null) return 0;
  var id = 0;
  for (var i = 0; i < ids.length; ++i) {
    if (ids[i].checked){
        id = ids[i].value; 
    }
  }
  return id;
}

function has_item_selected() {
  var ids = document.getElementsByName('userId');
  if (ids == null) return false;
  for (var i = 0; i < ids.length; ++i) {
    if (ids[i].checked) return true;
  }
  return false;
}
</script>

<#--
<h2>DEBUG</h2>
<li>group = ${group}
<li>member_list = ${member_list}
<li>member_list.schema = ${member_list.schema}
-->

</body>
</html>
