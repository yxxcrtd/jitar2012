<html>
<head>
  <meta http-equiv="Content-Type" content="text/html;charset=UTF-8" />
  <title>我发出的邀请</title>
  <link rel="stylesheet" type="text/css" href="../css/manage.css">
</head>
<body>
<h2>我发出的邀请</h2>
<div class='pos'>
  您现在的位置：<a href='${SiteUrl}manage/' target='_top'>个人控制面板</a>
  &gt;&gt; <a href='group.py?cmd=list'>协作组管理</a>
  &gt;&gt; <span>我发出的邀请</span>
</div>
<br/>
<#import 'group_macro.ftl' as gmm >

<table class='listTable' cellspacing='1'>
  <thead>
    <tr>
      <th width='5%'>uid</th>
      <th width='40%'>邀请加入的协作组</th>
      <th width='30%'>被邀请人</th>
      <th width='10%'>邀请时间</th>
      <th width='8%'>操作</th>
    </tr>
  </thead>
  <tbody>
  <#foreach invite in invite_list>
    <tr>
      <td><input type='checkbox' name='id' value='${invite.userId}' style='display:none' />${invite.userId}</td>
      <td>
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
      <td>
        <table border='0' width='100%' style='border:0;' cellpadding='0' cellspacing='0'>
          <tr><td width='54' style='border:0;' valign='top'>
		        <a href='${SiteUrl}go.action?userId=${invite.userId}' target='_blank'><img 
		          src='${SSOServerUrl +"upload/"+invite.userIcon!"images/default.gif"}' width='48' height='48' border='0' onerror="this.src='${ContextPath}images/default.gif'"/></a>
		      </td><td style='border:0;' valign='top'>
            <div style='margin-bottom:3px;'><a href='${SiteUrl}go.action?userId=${invite.userId}' target='_blank'>${invite.nickName!?html}</a></div>
            <div style='margin-bottom:3px;'>${invite.blogName!}</div>
          </td></tr>
        </table>
      </td>
      <td width='100'><nobr>${invite.joinDate?string('yyyy-MM-dd hh:mm')}</nobr></td>
      <td>
        <nobr><a href='?cmd=uninvite&amp;groupId=${invite.groupId}&amp;userId=${invite.userId}' onclick='return confirm_uninvite();'>取消邀请</a></nobr>
      </td>
    </tr>
  </#foreach>
  </tbody>
</table>
<script>
function confirm_uninvite() {
  return confirm('您是否确定要取消对该用户的邀请??');
}
</script>

</body>
</html>
