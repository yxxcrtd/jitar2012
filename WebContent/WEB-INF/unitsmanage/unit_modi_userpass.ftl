<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
  <meta http-equiv="content-type" content="text/html; charset=UTF-8" />
  <title></title>
  <link href="${SiteUrl}css/manage.css" rel="stylesheet" type="text/css" />
</head>
<body>
<h2>
修改机构用户密码
</h2>
<form method='post' style='padding-left:20px'>
<div style='padding:10px 0;font-size:1.5em'>你正在修改的是 <strong>${user.loginName}(${user.trueName})</strong> 的密码</div>
<div><strong>请输入用户的新密码：</strong><input type='password' name='pwd1' style='width:200px' /></div>
<div><strong>请确认用户的新密码：</strong><input type='password' name='pwd2' style='width:200px' /></div>
<input class='button' type='submit' value='保存密码' />
</form>
</body>
</html>
