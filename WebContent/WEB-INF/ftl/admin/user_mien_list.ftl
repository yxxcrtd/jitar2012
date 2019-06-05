<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
  <meta http-equiv="Content-Type" content="text/html;charset=UTF-8" />
  <title>用户管理</title>
  <link rel="stylesheet" type="text/css" href="../css/manage.css" />
  <script type="text/javascript" src='js/admin_user.js' ></script>
</head>

<body>
<h2>${typeName!'用户管理'}</h2>
  
<form name="list_form" action="?" method="post">
  <input type="hidden" name="cmd" value="" />
  <input type="hidden" name="set_to" value="" />
  <#include 'user_list_core.ftl' >
  
  <div class='funcButton'>
    <input class="button" id="selAll" name="sel_All" onClick="on_checkAll(list_form, 1)" type="button" value=" 全 选 ">&nbsp;&nbsp;
  <#if type == "star">
    <input type='button' class='button' onclick='unsetTo(list_form, "un_star")' value='取消研修之星' />
  <#elseif type == "show" > 
    <input type='button' class='button' onclick='unsetTo(list_form, "un_show")' value='取消教师风采' />
  </#if>
  </div>
</form>
    
</body>
</html>
