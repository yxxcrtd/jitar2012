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
<form name="theForm" method="post">
<input type="hidden" name="cmd" value="" />
<#if user_type_list??>
<table class="listTable" cellspacing="1" style="width:auto;">
<thead>
<tr>
<th>类型ID</th>
<th>用户类型名称</th>
<th>操作</th>
</tr>
</thead>
<tbody>
<#list user_type_list as ut>
<tr>
<td style="padding:2px 10px;">${ut.typeId}</td>
<td style="padding:2px 10px;">${ut.typeName}</td>
<td style="padding:2px 10px;"><a href="admin_usertype_manage.py?cmd=edit&typeId=${ut.typeId}">修改</a><#if !ut.isSystem> | <a href="admin_usertype_manage.py?cmd=delete&typeId=${ut.typeId}" onclick="return confirm('删除之后将不可恢复。\r\n你真的要删除这个用户分类名称吗？')">删除</a></#if></td>
</tr>
</#list>
</tbody>
</table>
<div style="padding:4px 0">注：系统自带的用户头衔不能删除。</div>
<#else>
暂无用户类型的定义。
</#if>
<hr/>
用户类型名称：<input name="typeName" /> <input type="button" class="button" value="添加用户类型名称定义" onclick="return AddTypeName()" />
</form>
</body>
</html>
