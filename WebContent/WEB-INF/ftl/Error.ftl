<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  <title>错误报告</title>
  <link rel="stylesheet" type="text/css" href="${SiteUrl}css/manage.css" />
  <style>
  .info_box {border:1px solid red; padding:10px 20px; width:75%; text-align:left;}
  .info_box ul {padding:20px;line-height:1.5em;} 
  </style>
</head>
<body>
<center>
  <div style='padding:4px;'>
    <font style="color: #FF0000; font-size: 26px; font-weight: bold;">操作未能成功</font>
  </div>
  <div class='info_box'>
  <ul>
  <#if actionErrors?? >
    <#list actionErrors as error>
      <li>${error!""}</li>
    </#list>
  </#if>
  <#-- 显示字段可能的错误 -->
  <#if fieldErrors?? >
  <#attempt>
    <#list fieldErrors?values as errors>
      <#list errors as err>
        <li>${err}</li>
      </#list>
    </#list>
  <#recover>
    <!-- error for fieldErrors -->
  </#attempt>
  </#if>
  </ul>
  
  <div style='padding:20px;'>
  <center>
  <#if actionLinks??>
    <#list actionLinks as link>
      [${link.html}] 
    </#list>
  <#else>
    [<a href='javascript:window.history.back();'>返 回</a>]
  </#if>
  </center>
  </div>
  
  </div>
</center>

  </body>
</html>
