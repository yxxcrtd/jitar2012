<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html;charset=UTF-8" />
  <title>我加入的协作组</title>
  <link rel="stylesheet" type="text/css" href="../css/manage.css" />
</head>
<body>
<h2>我加入的协作组</h2>
<div class='pos'>
  您现在的位置：<a href='${SiteUrl}manage/' target='_top'>个人控制面板</a>
  &gt;&gt; <a href='group.py?cmd=list'>协作组管理</a>
  &gt;&gt; <span>我加入的协作组</span>
</div>
<br/>
<#import 'group_macro.ftl' as gmm >

<table class='listTable' cellspacing='1'>
  <thead>
    <tr>
      <th width='5%'>ID</th>
      <th width='50%'>协作组名称</th>
      <th width='12%'>加入时间</th>
      <th width='6%'>成员状态</th>
      <th width='6%'>成员身份</th>
      <th width='14%'>操作</th>
    </tr>
  </thead>
  <tbody>
  <#list group_list as group>
    <tr>
      <td align='right'><input type='checkbox' value='${group.groupId}' style='display:none' />${group.groupId}</td>
      <td>
        <table border='0' width='100%' style='border:0;' cellpadding='0' cellspacing='0'>
          <tr><td width='54' style='border:0;' valign='top'>
            <a href='${SiteUrl}go.action?groupId=${group.groupId}' target='_blank'><img 
              src='${Util.url(group.groupIcon!SiteUrl + "images/group_default.gif")}' width='48' height='48' border='0' /></a>
          </td><td style='border:0;' valign='top'>
            <div style='margin-bottom:3px;'><a href='${SiteUrl}go.action?groupId=${group.groupId}' target='_blank'>${group.groupTitle!}</a> (<@gmm.group_state group.groupState />)</div>
            <div style='margin-bottom:3px;'>标签：[<@gmm.list_tag group.groupTags!'' />]</div>
            <div>简介：${group.groupIntroduce!}</div>
          </td></tr>
        </table>
      </td>
      <td>${group.joinDate?string('yyyy-MM-dd')}</td>
      <td><@gmm.member_state group.status /></td>
      <td align='right'><@gmm.member_role group.groupRole /></td>
      <td>
      <#if (group.groupState == 0 || group.groupState == 4) >
        <a href='group.py?cmd=manage&groupId=${group.groupId}' target='_top'>进入后台管理</a>
      </#if>
      <#if (group.createUserId!=loginUser.userId)>
        <br/><br/><a href='group.py?cmd=quit&groupId=${group.groupId}'  onclick='return confirm_delete();' target='_self'>退出该协作组</a>
      </#if>
      </td>
    </tr>
  </#list>
  </tbody>
</table>
<script language='javascript'>
function confirm_delete() {
  return window.confirm('你是否确定退出群组?');
}
</script>
</body>
</html>
