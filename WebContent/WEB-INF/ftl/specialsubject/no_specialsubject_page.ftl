<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
 <head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  <link rel="icon" href="${SiteUrl}images/favicon.ico" />
  <link rel="shortcut icon" href="${SiteUrl}images/favicon.ico" />
  <title>专题 - <#include '/WEB-INF/ftl/webtitle.ftl' ></title> 
  <link rel="stylesheet" type="text/css" href="${SiteThemeUrl}special.css" />
  <script src='${SiteUrl}js/jitar/core.js'></script>
</head>
<body>
<#include '/WEB-INF/ftl/site_head.ftl'>
<div style='height:8px;font-size:0;'></div>
<div id='main' style='padding:20px 0'>

<h2 style='text-align:center;color:#f00'>当前没有任何专题，请<a style="font-size:1em" href='${SiteUrl}manage/admin.py?url=${(SiteUrl + "manage/specialsubject/admin_specialsubject_add.py")?url}'>创建一个专题</a>。</h2>

</div>
<div style='clear:both;'></div>
<#include '/WEB-INF/ftl/footer.ftl' >
</body>
</html>