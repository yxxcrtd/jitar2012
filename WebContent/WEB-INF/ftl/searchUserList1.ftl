<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
 <head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  <title>人员</title>
  <link rel="stylesheet" type="text/css" href="${SiteUrl}css/index/article.css" />
<script src='${SiteUrl}js/jitar/core.js'></script>
  <style>
  html,body {width:100%;text-align:left;color:#000;}
  </style>
  <script language='javascript'>
function selectAll(obj)
{
	var i;
	for(i=0;i<document.getElementsByName("group").length;i++)
	{
		document.getElementsByName("group")[i].checked=obj.checked;

	}
}

function selectGroup()
{
	var i;
	var ids;
	ids="";
	gs = document.getElementsByName("group");
	for(i=0;i<gs.length;i++)
	{
		if(gs[i].checked)
			{
				if(ids=="")
				{
					ids=gs[i].value;
				}
				else
				{
					ids=ids+","+gs[i].value;
				}
			}
		}

	if(ids=="")
	{
		alert("请选择群组");
		return;
	}
	document.dataForm.groupids.value=ids;
	document.dataForm.submit();
}

function closeWindow()
{
	var ids,names,loginnames;
	ids="";
	names="";
	loginnames="";
	if (${closewindow!}==0)
	{
		return;
	}
	if (${closewindow!}==1)
	{
		<#if user_list??>
		<#list user_list! as user>
			if(ids==""){
				ids="${user.userId!}";
			}else{
				ids=ids+",${user.userId!}";
			}	
			if(names==""){
				names="${user.trueName!}";
			}else{
				names=names+",${user.trueName!}";
			}	
			if(loginnames==""){
				loginnames="${user.loginName!}";
			}else{
				loginnames=loginnames+",${user.loginName!}";
			}		
		</#list>
		</#if>
	}
	if(ids=="")
	{
		alert("没有找到用户");
		return;
	}
	self.parent.selectUsers(ids,names,loginnames);
}
  </script>
 </head>
 <body onload="closeWindow()">
<table border="0" cellspacing='0'>
<tr>
<form name="dataForm">
	<input type='hidden' name='groupids' value=''>
	<input type='hidden' name='closewindow' value=1>
</form>
<form name="searchForm">
<input type='hidden' name='cmd' value="glist">
<td>
<input type="text" size=6 Name="k" value="${k!?html}">&nbsp;&nbsp;<input type="submit" Name="btnSearch" value="查询">&nbsp;&nbsp;<input type="button" Name="btnOK" value="确定" onclick="selectGroup()">
</td>
</form>
</tr>
</table>
<table border="0" cellspacing='0' class='res_table'>
<thead>
<tr>
<td class='td_middle' style='width:10%'><input type='checkbox' onclick='selectAll(this)'></td>
<td class='td_left' ' style='width:60%'>群组名称</td>
<td class='td_middle' style='width:30%'>创建者</td>
</thead>
<tbody>
<form name="groupList">            
<tr>
<td colspan='4' style='padding:4px;'></td>
</tr>
  <#list group_list! as group> 
 <tr>
  <td style='padding-left:10px'>
  	<input type='checkbox' name='group' value='${group.groupId!?html}'>
  </td>
  <td>${group.groupTitle!?html}</td>
  <td>${group.trueName!?html}</td>
 </#list>
 </form>
</tbody>
</table>
<div class='pgr'><#include 'inc/pager.ftl' ></div>          
</body>
</html>