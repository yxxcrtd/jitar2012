<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
 <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  <title>组织机构管理</title>
   <style>
   body {
	font-style: normal;
	line-height: normal;
	font-family: '宋体', arial, sans-serif, verdana;
	font-size: 12px;
	background-color: white;
	margin: 8px 8px 40px 8px;
	padding: 0;
	height: 100%;
}

table {
	font-family: '宋体', arial, sans-serif, verdana;
	font-size: 12px;
}

a {
	font-family: verdana, arial, '宋体', sans-serif;
	COLOR: #425b6f;
}

a:hover {
	COLOR: #ff6633;
}

/* 测试新表格样式和颜色 */
table.greenTable {
	border: 1px solid #A9F7A9;
	border-collapse: collapse;
	background-color: #fffffc;
	width: 100%;
	margin: 4px auto 4px;
}

table.greenTable thead tr {
	background: url(images/bg.gif) repeat center center;
}

table.greenTable th {
	border: 1px dotted;
	padding: 3px;
	font-weight: bold;
}

table.greenTable td {
	border: 1px dotted;
	padding: 3px;
}

/* 一般项目列表表格样式定义 */
.listTable {
	border: 1px solid #E6DBC0;
	border-collapse: collapse;
	font-family: verdana, arial, sans-serif, '宋体';
	font-size: 12px;
	width: 100%;
	/*background-color: #E6DBC0; margin: 4px auto 4px; */
}

.listTable thead tr {
	background: url(images/bg.gif) repeat center center;
}

.listTable thead th {
	border: 1px dotted #E6DBC0;
	padding: 3px 2px 3px 2px;
	/*background-color: #e5e5e5;*/
	white-space: nowrap;
}

.listTable tbody tr { /*background-color: white;*/
	
}

.listTable tbody td {
	border: 1px dotted #E6DBC0;
	padding: 3px;
	/*background-color: white;*/
}

.listTable tbody fieldset {
	width: 100%;
	height: 100%;
	border: #DDDDDD 1px dashed;
}

.listTable tbody legend {
	border: 0px;
	background-color: #FFFFFF;
}

.listTable tfoot td {
	padding: 3px;
	background-color: white;
}

.listTable .title {
	width: 30%;
	align: right;
	valign: top;
	font-weigth: bold;
}

/* 表单属性 */
form {
	border: 0;
	padding: 0;
	margin: 0;
}

/* 一般表单中 input, textarea 字段的字体和大小定义 */
input,textarea,select {
	font-family: '宋体', arial, sans-serif, verdana;
	font-size: 12px;
}
  </style>
  <link rel="stylesheet" href="${SiteUrl}css/manage.css" type="text/css" /> 
</head>
<body>
<h2>添加机构根名称：</h2>
<form method='post'>
<table class='listTable' cellspacing='1'>
<tr>
	<td align="right" style='width:60px'><b>机构名称:</b></td>
	<td>
		<input type='text' name='title' value='' size='80' maxLength="256" />
		<font color='red'>*</font>
	</td>
</tr>
<tr>
	<td align="right" style='width:60px'><b>英文名称:</b></td>
	<td>
		<input type='text' name='enname' value='' size='80' maxLength="256" />
		<font color='red'>必填且必须系统内唯一,只能输入数字或英文字母且以英文字母开头。</font>
	</td>
</tr>
<tr>
	<td align="right" style='width:60px'><b>网站名称:</b></td>
	<td>
		<input type='text' name='siteTitle' value='' size='80' maxLength="256" />
		<font color='red'>*</font>
	</td>
</tr>
<tr>
	<td></td>
	<td>
		<input class="button" type="submit" value=" 添  加 " />
		<input class="button" type="button" value=" 返  回 " onclick="window.history.back()" />
	</td>
</tr>
</table>
</form>
</body>
</html>