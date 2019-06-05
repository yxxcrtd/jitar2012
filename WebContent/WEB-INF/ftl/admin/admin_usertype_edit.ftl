<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
 <head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  <link rel="stylesheet" type="text/css" href="../css/manage.css" />
  <script type="text/javascript">
  function AddTypeName()
  {
    var f = document.theForm;
    if(f.typeName.value == "")
    {
     window.alert("请输入用户类型定义名称。");
     return false;
    }
    f.cmd.value="add";
    f.submit();
    return true;
  }
  </script>
 </head>
<body>
<h2>用户头衔名称维护</h2>
<#if userType??>
<form name="theForm" method="post" action="admin_usertype_manage.py">
<input type="hidden" name="cmd" value="add" />
<input type="hidden" name="typeId" value="${userType.typeId}" />
用户类型名称：<input name="typeName" value="${userType.typeName!?html}" /> <input type="button" class="button" value="修改用户类型名称定义" onclick="return AddTypeName()" />
</form>
<#else>
没有得到对象，无法进行修改。
</#if>
</body>
</html>
