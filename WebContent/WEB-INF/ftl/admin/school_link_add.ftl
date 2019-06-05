<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  <title>添加修改组内链接</title>
  <link rel="stylesheet" href="../css/manage.css" type="text/css" />
</head>
<body>

<h2>${(link.linkId == 0)?string('添加', '修改')}机构风采链接</h2>

<form name="list_form" action="?" method="post" >
  <input type='hidden' name='cmd' value='save' />
  <input type='hidden' name='linkId' value='${link.linkId}' />
<#if __referer??>
	<input type='hidden' name='__referer' value='${__referer!}' />
</#if>
<table class="listTable" cellspacing="1">
	<tr>
		<td align="right" width='20%'><b>链接名称：</b></td>
		<td>
			<input type="text" name="title" value="${link.title!?html}" size="40" />
		</td>
	</tr>
	<tr>
		<td align="right" width='20%'><b>链接地址：</b></td>
		<td>
	 	  <input type="text" size="65" name="linkAddress" value="${link.linkAddress!?html}">
		</td>
	</tr>
	<tr>
		<td align="right"><b>链接类型：</b></td>
		<td>
			<select name="linkType">
				<option value="1" ${(link.linkType == 1)?string('selected', '')}>外部链接
				<option value="2" ${(link.linkType == 2)?string('selected', '')}>内部链接
			</select>
		</td>
	</tr>
	
	<tr>
		<td align="right" valign="top"><b>链接描述：</b></td>
		<td>
			<textarea type="text" name="description" rows='10' cols="68">${link.description!?html}</textarea>
		</td>
	</tr>
	<tr>
		<td align="right"><b>图片链接地址：</b></td> 			
		<td>
			<input type="text" name="linkIcon"  size="65" value="${link.linkIcon!?html}"> 						
		</td>
	</tr>
	<tr>
	  <td></td>
	  <td>
		  <input class="button" type="submit" value="${(link.linkId == 0)?string(' 添  加 ',' 修  改 ')} " />
		  <input class="button" type="button" value=" 返 回 " onclick="window.history.back()" />
	  </td>
	</tr>
</table>

</form>

</body>
</html>
