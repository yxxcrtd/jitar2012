<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  <title>操作成功</title>
  <link rel="stylesheet" type="text/css" href="../css/manage.css" />   
</head>
    
<body>
<center>
  <font style="color: green; font-size: 26px; font-weight: bold;">分类创建/修改成功</font>
  
  <div style='border:1px solid blue; width:75%; text-align:left; margin-top:12px;'>
  <ul>
    <li>创建/修改的分类为: ${category.name!?html}</li>
  <#list actionMessages as message>
    <li>${message}</li>
  </#list>
  </ul>
<#if (actionErrors?size > 0)>
  <h4>附加信息为</h4>
  <ul>
  <#list actionErrors as error>
    <li>${error}</li>
  </#list>
  </ul>
</#if>
  </div>
</center>

 </body>
</html>