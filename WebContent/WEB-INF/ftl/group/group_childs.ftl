<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html;charset=UTF-8" />
  <title>子课题组</title>
  <link rel="stylesheet" type="text/css" href="../css/manage.css" />
  <script type='text/javascript' src='dialog/msg.js'></script>
<script type='text/javascript'>
function createKt(pid) 
{
    document.location.href="group.action?cmd=create&parentId="+pid;
}
function deletess(gid)
{
    if(confirm("确认删除吗?")==false){
        return false;
    }
    document.location.href="group_childs.py?cmd=delete&groupId="+gid+"&parentId=${parentId}";
}
</script>    
</head>
<body>
<#include 'group_title.ftl' >
<div class='pos'>
  您现在的位置： <a href='group.py?cmd=home&amp;groupId=${parentId}'>课题组管理首页</a>
  &gt;&gt; <span>子课题管理</span> 
</div>
<br/>
<#import 'group_macro.ftl' as gmm >
<table class='listTable' cellspacing='1'>
  <thead>
    <tr>
      <th width='5%'>ID</th>
      <th width='50%'>子课题名称</th>
      <th width='20%'>课题负责人</th>
      <th width='7%'>成员数</th>
      <th width='10%'>创建时间</th>
      <th width='8%'>管理</th>
    </tr>
  </thead>
  <tbody>
  <#list group_list as group>
    <tr>
      <td align='right'>${group.groupId}</td>
      <td>
        <table border='0' width='100%' style='border:0;' cellpadding='0' cellspacing='0'>
          <tr><td width='54' style='border:0;' valign='top'>
            <a href='${SiteUrl}go.action?groupId=${group.groupId}' target='_blank'><img 
              src='${SiteUrl}${group.groupIcon!}' width='48' height='48' border='0' /></a>
          </td><td style='border:0;' valign='top'>
            <div style='margin-bottom:3px;'><a href='${SiteUrl}go.action?groupId=${group.groupId}' target='_blank'>${group.groupTitle!}</a></div>
            <div style='margin-bottom:3px;'>状态：[<@gmm.group_state group.groupState />]</div>
            <div>简介：${group.groupIntroduce!}</div>
          </td></tr>
        </table>
      </td>
      <td>
              <#assign userlist = Util.getKtUserById(group.groupId)>
              <#if userlist??>
                <#list userlist as ktuser>
                    <li>${ktuser.teacherName}[${ktuser.teacherUnit}]</li>
                </#list>
              </#if>       
      </td>
      <td align='right'>${group.userCount}</td>
      <td>${group.createDate?string('yyyy-MM-dd')}</td>
      <td>
      <#if (group.groupState == 0 || group.groupState == 4) >
        <a href='group.py?cmd=manage&groupId=${group.groupId}' target='_top'>进入子课题</a><br/>
      </#if>
      <a href='javascript:void(0);' onclick="deletess(${group.groupId});return false;">删除子课题</a>
      </td>
    </tr>
  </#list>
  </tbody>
</table>
<div class='funcButton'>
  <input type='hidden' name='cmd' value='' />
  <input type='hidden' name='parentId' value='${parentId}' />
  <input type="button" class="button" value=" 创建新课题 " onclick="createKt('${parentId}');" />
</div>  

</body>
</html>
