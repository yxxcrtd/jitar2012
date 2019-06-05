<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  <title>board面板(主題及回复)</title>
  <link rel="stylesheet" href="../css/manage.css" type="text/css" />
</head>
<form>
<table border='1' width='100%'>
	<tr>
		<td width='30%'>
		  ${topic.userId!} <br/>
		  ${topic.loginName!}
		</td>
		<td width='70%'>
		  <div>${topic.title!}</div>
		  <div>${topic.content!} </div>
		</td>
	</tr>
 <#list reply_list as reply>
	<tr>
		<td>
		  ${reply.loginName!}
		</td>
		<td>
		  <div>${reply.title!}  (${reply.createDate!})  ${reply.isBest?string('精', '')}</div>
      <div>${reply.content!}</div>
      <div><a href="topic.action?cmd=view&amp;boardId=${reply.boardId}&amp;topicId=${reply.topicId}&amp;replyId=${reply.replyId}&amp;targetReply=${reply.targetReply}">编辑</a></div>
      
		</td>
	</tr>
 </#list>
</table>

</form>
<div>
  <li>topic_list = ${reply_list}
  <li>topic_list.schema = ${reply_list.schema}
</div>
</html>
	