<form methos="GET" action="channel.action">
<table border="0" cellspacing="3">
<tr>
<td>关键字：</td><td><input name="k" value="${k!?html}"></td>
</tr>
<tr>
<td>类 别：</td>
<td>
<select name="f">
<option value="article">文章标题</option>
<option value="resource">资源标题</option>
<option value="video">视频标题</option>
<option value="photo">图片标题</option>
<option value="user">用户名称</option>
</select></td>
</tr>
<tr>
<td>&nbsp;</td><td>
<input type="submit" value="搜索频道" />
<input type="hidden" name="channelId" value="${channel.channelId}" />
<input type="hidden" name="cmd" value="search" />
</td>
</tr>
</table>
</form>