<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  <title>主題列表</title>
  <link rel="stylesheet" href="../css/manage.css" type="text/css" />
</head>

<form action="board.action" method="post">
<table border='1'>
	<input type='hidden' name='cmd' value='del'/>
	<#list topic_list as topics>
		<tr>
			<td><input type="checkbox" name="mytopic"
										  value="${topics.topicId!}"/>
			</td>
			<td>${topics.topicId!}</td>
			<td>${topics.title!}</td>
			<td>${topics.content!}</td>
			<td>${topics.createDate!}</td>
			<td>${topics.tags}</td>
			<td>${topics.replyCount!}</td>
			<td>${topics.viewCount!}</td>
			<td>${topics.loginName}</td>
			<td>${topics.nickName}</td> 
			<td>
			<a href="board.action?cmd=view&amp;topicId=${topics.topicId}&amp;boardId=${topics.boardId}">编辑</a>
			</td>
			<td>
				${topics.isTop?string('置顶', '')}
			</td>
		</tr>
	</#list>
	<tr>
		<td>
			<input type="submit" value="删除">
		</td>
	</tr>
</table>
</form>
</html>

<div>
  <li>topic_list = ${topic_list}
  <li>topic_list.schema = ${topic_list.schema}
</div>