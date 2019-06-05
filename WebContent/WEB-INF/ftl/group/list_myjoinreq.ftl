<html>
<head>
  <meta http-equiv="Content-Type" content="text/html;charset=UTF-8" />
  <title></title>
  <link rel="stylesheet" type="text/css" href="../css/manage.css">
</head>
<body>
<h2>我发出的申请</h2>
<div class='pos'>
  您现在的位置：<a href='${SiteUrl}manage/' target='_top'>个人控制面板</a>
  &gt;&gt; <a href='group.py?cmd=list'>协作组管理</a>
  &gt;&gt; <span>我发出的申请</span>
</div>
<br/>
<#import 'group_macro.ftl' as gmm >

<table class='listTable' cellspacing='1'>
  <thead>
    <tr>
      <th width='5%'>gid</th>
      <th width='60%'>群组名</th>
      <th width='40%'>创建者</th>
      <th width='12%'>时间</th>
      <th width='8%'>操作</th>
    </tr>
  </thead>
  <tbody>
  <#foreach req in req_list>
    <tr>
      <td><input type='checkbox' name='id' value='${req.groupId}' style='display:none' />${req.groupId}</td>
      <td valign='top'>
        <table border='0' width='100%' style='border:0;' cellpadding='0' cellspacing='0'>
          <tr><td width='54' style='border:0;' valign='top'>
            <a href='${SiteUrl}go.action?groupId=${req.groupId}' target='_blank'><img 
              src='${Util.url(req.groupIcon!'images/group_default.gif')}' width='48' height='48' border='0' /></a>
          </td><td style='border:0;' valign='top'>
            <div style='margin-bottom:3px;'><a href='${SiteUrl}go.action?groupId=${req.groupId}' target='_blank'>${req.groupTitle!}</a></div>
            <div style='margin-bottom:3px;'>标签：[<@gmm.list_tag req.groupTags!'' />]</div>
            <div>简介：${req.groupIntroduce!}</div>
          </td></tr>
        </table>
      </td>
      <td valign='top'>
        <table border='0' width='100%' style='border:0;' cellpadding='0' cellspacing='0'>
          <tr><td width='54' style='border:0;' valign='top'>
            <a href='${SiteUrl}go.action?userId=${req.userId}' target='_blank'><img 
              src='${SSOServerUrl +'upload/'+req.userIcon!"images/default.gif"}' width='48' height='48' border='0' onerror="this.src='${ContextPath}images/default.gif'"/></a>
          </td><td style='border:0;' valign='top'>
            <div style='margin-bottom:3px;'><a href='${SiteUrl}go.action?userId=${req.userId}' target='_blank'>${req.nickName!?html}</a></div>
            <div style='margin-bottom:3px;'>${req.blogName!}</div>
          </td></tr>
        </table>
      </td>
      <td><nobr>${req.joinDate?string('yyyy-MM-dd hh:mm')}</nobr></td>
      <td><a href='?cmd=cancel_joinreq&amp;groupId=${req.groupId}' onclick='return confirm_cancel();'>取消</a></td>
    </tr>
  </#foreach>
  </tbody>
</table>
<script>
function confirm_cancel() {
  return confirm('您是否确定要取消加入此协作组的申请??');
}
</script>

</body>
</html>
