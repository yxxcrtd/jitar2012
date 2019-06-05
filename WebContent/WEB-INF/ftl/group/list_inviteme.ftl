<html>
<head>
  <meta http-equiv="Content-Type" content="text/html;charset=UTF-8" />
  <title></title>
  <link rel="stylesheet" type="text/css" href="../css/manage.css">
</head>
<body>
<h2>我收到的邀请</h2>
<div class='pos'>
  您现在的位置：<a href='${SiteUrl}manage/' target='_top'>个人控制面板</a>
  &gt;&gt; <a href='group.py?cmd=list'>协作组管理</a>
  &gt;&gt; <span>我收到的邀请</span>
</div>
<br/>
<#import 'group_macro.ftl' as gmm >

<table class='listTable' cellspacing='1'>
  <thead>
    <tr>
      <th width='5%'>gid</th>
      <th width='60%'>群组名</th>
      <th width='40%'>邀请人</th>
      <th width='12%'>邀请时间</th>
      <th width='10%'>操作</th>
    </tr>
  </thead>
  <tbody>
  <#foreach invite in invite_list>
    <tr>
      <td><input type='checkbox' name='id' value='${invite.groupId}' style='display:none;' />${invite.groupId}</td>
      <td valign='top'>
        <table border='0' width='100%' style='border:0;' cellpadding='0' cellspacing='0'>
          <tr><td width='54' style='border:0;' valign='top'>
            <a href='${SiteUrl}go.action?groupId=${invite.groupId}' target='_blank'><img 
              src='${Util.url(invite.groupIcon!'images/group_default.gif')}' width='48' height='48' border='0' /></a>
          </td><td style='border:0;' valign='top'>
            <div style='margin-bottom:3px;'><a href='${SiteUrl}go.action?groupId=${invite.groupId}' target='_blank'>${invite.groupTitle!}</a></div>
            <div style='margin-bottom:3px;'>标签：[<@gmm.list_tag invite.groupTags!'' />]</div>
            <div>简介：${invite.groupIntroduce!}</div>
          </td></tr>
        </table>
      </td>
      <td valign='top'>
        <table border='0' width='100%' style='border:0;' cellpadding='0' cellspacing='0'>
          <tr><td width='54' style='border:0;' valign='top'>
            <a href='${SiteUrl}go.action?userId=${invite.userId}' target='_blank'><img 
              src='${SSOServerUrl +"upload/"+ invite.userIcon!"images/default.gif"}' onerror="this.src='${ContextPath}images/default.gif'" width='48' height='48' border='0' /></a>
          </td><td style='border:0;' valign='top'>
            <div style='margin-bottom:3px;'><a href='${SiteUrl}go.action?userId=${invite.userId}' target='_blank'>${invite.nickName!?html}</a></div>
            <div style='margin-bottom:3px;'>${invite.blogName!}</div>
          </td></tr>
        </table>
      </td>
      <td width='100'><nobr>${invite.joinDate?string('MM-dd hh:mm')}</nobr></td>
      <td>
        <a href='?cmd=accept_invite&amp;groupId=${invite.groupId}'>同意</a> 
        <br/><br/>
        <a href='?cmd=reject_invite&amp;groupId=${invite.groupId}' onclick='return confirm_reject();'>婉拒</a>
      </td>
    </tr>
  </#foreach>
  </tbody>
</table>
<script>
function confirm_reject() {
  return confirm('您是否确定要拒绝此加入协作组的邀请??');
}
</script>

</body>
</html>
