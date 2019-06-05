<!doctype html>
<html>
<head>
 <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  <title>添加根机构</title>
<style>
html,body {
	font-style: normal;
	line-height: normal;
	font-family: '宋体', arial, sans-serif, verdana;
	font-size: 16px;
	background-color: white;
	margin: 8px;
	padding: 8px;
	height: 100%;
}
</style>
</head>
<body>
<h2>添加根机构：</h2>
<div><strong>说明</strong>：每个教研平台都需要一个唯一的根机构，如首次安装，请咨询软件开发商。机构代码是用户管理系统中，编辑机构中可以看到机构代码；Id 标识是用户统一平台中机构的 “t_org”表结构中的“unit_id”字段的值。</div>
<br/>
<form method='post'>
<input type="hidden" name="cmd" value="inputId" />
请输入根机构的 <strong>机构代码</strong> 标识:<input name="unitCode"/><br/><br/>
或者<br/>
请输入根机构的 <strong>Id</strong> 标识:<input name="unitId"/><br/><br/>
<input type="submit" value="添加根机构" />
</form>
</body>
</html>