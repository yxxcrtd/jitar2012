<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html;charset=UTF-8" />
  <title>我创建的协作组</title>
  <link rel="stylesheet" type="text/css" href="../css/manage.css" />
</head>
<body>
<h2>我创建的协作组</h2>
<div class='pos'>
  您现在的位置：<a href='${SiteUrl}manage/' target='_top'>个人控制面板</a>
  &gt;&gt; <a href='group.py?cmd=list'>协作组管理</a>
  &gt;&gt; <span>我创建的协作组</span>
</div>
<br/>

<#import 'group_macro.ftl' as gmm >

<table class='listTable' cellspacing='1'>
  <thead>
    <tr>
      <th width='5%'>ID</th>
      <th width='60%'>协作组名称</th>
      <th width='12%'>创建时间</th>
      <th width='6%'>状态</th>
      <th width='6%'>成员</th>
      <th width='6%'>操作</th>
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
            <div style='margin-bottom:3px;'><a href='${SiteUrl}go.action?groupId=${group.groupId}' target='_blank'>${group.groupTitle!}</a></div>
            <div style='margin-bottom:3px;'>标签：[<@gmm.list_tag group.groupTags!'' />]</div>
            <div>简介：${group.groupIntroduce!}</div>
          </td></tr>
        </table>
      </td>
      <td>${group.createDate?string('yyyy-MM-dd')}</td>
      <td><@gmm.group_state group.groupState /></td>
      <td align='right'>${group.userCount}</td>
      <td>
      <#if (group.groupState == 0 || group.groupState == 4) >
        <a href='group.py?cmd=manage&groupId=${group.groupId}' target='_top'>进入</a>
      </#if>
      </td>
    </tr>
  </#list>
  </tbody>
</table>

</body>
</html>
