<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
 <head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  <link rel="stylesheet" type="text/css" href="${SiteUrl}css/manage.css" />
  <script type='text/javascript'>
  function getUser()
  {
    var ids = [];
    var titles = [];
    var checkedList = document.getElementsByName("userId")
    for(var i=0;i<checkedList.length;i++)
    {
     if(checkedList[i].checked)
     {
       ids.push(checkedList[i].value)
       titles.push(checkedList[i].getAttribute("title"))
     }
    }
    
    <#if back == "ModalDialogReturn">
        if(ids.length == 0)
        window.parent.returnValue = "";
        else
        window.parent.returnValue = ids + "|||" + titles;    
    <#else>
        window.parent.opener.document.getElementById("${idTag}").value=ids;
        if(window.parent.opener.document.getElementById("${titleTag}").tagName == "INPUT")
        {
            window.parent.opener.document.getElementById("${titleTag}").value=titles;
        }
        else
        {
            window.parent.opener.document.getElementById("${titleTag}").innerHTML=titles;
        }
    </#if>
    window.parent.close();
  } 
  </script>
  <style>
  a{text-decoration:none}
  td{padding:2px 6px;}
  </style>
 </head>
 <body>
<#if user_list??>
<table cellspacing="1" cellpadding="2" style="background:#a1c794">
<tr style="background:#36909b;color:#fff">
<th></th>
<th>登录名</th>
<th>真实姓名</th>
<th>机构名称</th>
<th>注册时间</th>
</tr>
<#list user_list as u>
<#if u_index%2 == 0 >
<tr style="background:#fff">
<#else>
<tr style="background:#f7f7f7">
</#if>
<td>
<#if type== '1'>
<input name='userId' type='radio' value='${u.userId}' title="${u.trueName!?html}" />
<#else>
<input name='userId' type='checkbox' value='${u.userId}' title="${u.trueName!?html}" />
</#if>
</td>
<td><a href='${SiteUrl}go.action?userId=${u.userId}' target="_blank">${u.loginName!?html}</a></td>
<td><a href='${SiteUrl}go.action?userId=${u.userId}' target="_blank">${u.trueName!?html}</a></td>
<td><a href='${SiteUrl}go.action?unitName=${u.unitName!}' target="_blank">${u.unitTitle!?html}</a></td>
<td>${u.createDate?string("yyyy-MM-dd HH:mm:ss")}</td>
</tr>
</#list>
</table>
<#include "/WEB-INF/ftl/inc/pager.ftl">
</#if>
</body>
</html>