<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
  <meta http-equiv="content-type" content="text/html; charset=UTF-8" />
  <title></title>
  <link href="${SiteUrl}css/manage.css" rel="stylesheet" type="text/css" />  
    <script type="text/javascript">
    function checkData(frm)
    {
        if(frm.linkName.value=="")
        {
            alert("请输入链接名称");
            return false;
        }
        if(frm.linkAddress.value=="" || frm.linkAddress.value=="http://")
        {
            alert("请输入链接地址!");
            return false;
        }    
        return true;   
    }
  </script>
</head>
<body>
<h2>
<#if unitLinks??>
修改友情链接
<#else>
添加友情链接
</#if>
</h2>
<form method='post' style='padding-left:20px'  onsubmit='return checkData(this);'>
<table class='listTable' cellspacing='1'>
<tr>
<td style='width:100px'>链接名称(<font color='red'>*</font>)：</td><td>
<#if unitLinks??>
<input name='linkName' style='width:99%' value='${unitLinks.linkName?html}' />
<#else>
<input name='linkName' style='width:99%' value='' />
</#if>
</td>
</tr>
<tr>
<td>链接地址(<font color='red'>*</font>)：</td><td>
<#if unitLinks??>
<input name='linkAddress'  style='width:99%' value='${unitLinks.linkAddress?html}' />
<#else>
<input name='linkAddress'  style='width:99%' value='http://' />
</#if>
</td>
</tr>
</table>
<div style='padding:6px'>
<#if unitLinks??>
<input class='button' type='submit' value=' 修  改 ' />
<#else>
<input class='button' type='submit' value=' 保  存 ' />
</#if>
</div>
</form>
</body>
</html>
