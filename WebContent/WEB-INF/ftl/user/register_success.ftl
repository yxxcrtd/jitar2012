<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  <title>注册新用户成功完成</title>
  <link rel="stylesheet" type="text/css" href="css/manage.css" />   
</head>
<body>
  <h2>注册成功！欢迎您: ${user.nickName!?html}</h2>
  
  <h3>您可以:</h3>
  <ul>
    <li><a href='${SiteUrl}'>访问网站首页</a></li>
    <li><a href='${SiteUrl}go.action?loginName=${user.loginName}'>我的工作室</a></li>
    <li><a href='${SiteUrl}manage/'>进入工作室管理</a></li>
  </ul>

</body>
</html>

