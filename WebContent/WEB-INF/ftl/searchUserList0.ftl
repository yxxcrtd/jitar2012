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
  
  // 用 AJAX 请求该区县下的机构, 并填充到 unitId select 中.
  url = "manage/admin_unit.py?cmd=unit_options&tmp=" + Math.random();
  new Ajax.Request(url, {
    method: "get",
    onSuccess: function(xport) { 
        var options = eval(xport.responseText);
        clear_options(unit_sel);
        add_option(unit_sel, "0", "全部机构");
        for (var i = 0; i < options.length; ++i)
          add_option(unit_sel, options[i][0], options[i][1]);
      }
  });


function clear_options(sel) {
  sel.options.length = 0;
}
function add_option(sel, val, text) {
  opt = document.createElement("OPTION");
  opt.value = val;
  opt.text = text;
  sel.options.add(opt);
}

function selectAll(obj)
{
	var i;
	for(i=0;i<document.userList.elements.length;i++)
	{
		if (document.userList.elements[i].name=="user")
		{
			document.userList.elements[i].checked=obj.checked;
		}
	}
}

function SelectUser()
{
	var i;
	var ids,names,loginnames,genders,unittitles;
	names="";
	ids="";
	loginnames="";
	genders="";
	unittitles="";
	userInput = document.getElementsByName("user")

	for(i=0;i<userInput.length;i++)
	{		
		if(userInput[i].checked)
		{
			if(ids==""){
				ids=userInput[i].value;
			}else{
				ids=ids+","+userInput[i].value;
			}
			if(names==""){
				names=userInput[i].getAttribute("value2");
			}else{
				names=names+","+ userInput[i].getAttribute("value2");
			}
			if(loginnames==""){
				loginnames=userInput[i].getAttribute("value3");
			}else{
				loginnames=loginnames+","+ userInput[i].getAttribute("value3");
			}
            if(genders==""){
                genders=userInput[i].getAttribute("value4");
            }else{
                genders=genders+","+ userInput[i].getAttribute("value4");
            }
            if(unittitles==""){
                unittitles=userInput[i].getAttribute("value5");
            }else{
                unittitles=unittitles+","+ userInput[i].getAttribute("value5");
            }            
		}
	}

	if(ids=="")
	{
		alert("请选择用户");
		return;
	}
	self.parent.selectUsers(ids,names,loginnames,genders,unittitles);
}
  </script>
 </head>
 <body>
<table border="0" cellspacing='0' >
<tr>
	<form name="searchForm">
	<input type='hidden' name='cmd' value="list">
	<input type='hidden' name='singleuser' value=${singleuser!}>
	<td>
	<select Name="unitId">
		<option value=0>全部机构</option>
		<#if unit_list?? >
			<#list unit_list as unit>
			<option value="${unit.unitId}" <#if unit.unitId==(unitId!0)>selected</#if>>
			${unit.unitTitle!?html}</option>
			</#list>
		</#if>
	</select>
	&nbsp;
	用户:<input type="text" size=6 Name="userName" value="${userName!?html}">
	&nbsp;
	<input type="submit" Name="btnSearch" value="查询">
	&nbsp;&nbsp;
	<input type="button" Name="btnOK" value="确定" onclick="SelectUser()">
	</td>
	</form>
</tr>
</table>

<table border="0" cellspacing='0' class='res_table'>
<thead>
<tr>

<td class='td_middle' style='width:10%'>
<#if singleuser==0>
<input type='checkbox' name='checkall' onclick='selectAll(this)'>
<#else>
选择	
</#if>
</td>

<td class='td_left' ' style='width:30%'>姓名</td>
<td class='td_left' ' style='width:30%'>性别</td>
<td class='td_middle' style='width:30%'>机构</td>
</thead>
<tbody>
<form name="userList">            
<tr>
<td colspan='4' style='padding:4px;'></td>
</tr>
  <#list user_list! as a> 
 <tr>
  <td style='padding-left:10px'>
<#if singleuser==0>
	<input type='checkbox' name='user' value2='${a.trueName!?html}' value3='${a.loginName!?html}' value4='<#if a.gender==1>男<#elseif a.gender==0>女</#if>' value5='${a.unitTitle!?html}' value='${a.userId!}'>
<#else>
	<input type='radio' name='user' value2='${a.trueName!?html}' value3='${a.loginName!?html}' value4='<#if a.gender==1>男<#elseif a.gender==0>女</#if>' value5='${a.unitTitle!?html}' value='${a.userId!}'>
</#if>
  </td>
  <td>${a.trueName!?html}</td>
  <td><#if a.gender==1>男<#elseif a.gender==0>女</#if></td>
  <td>${a.unitTitle!?html}</td>
 </#list>
 </form>
</tbody>
</table>
<div class='pgr'><#include 'inc/pager.ftl' ></div>          
</body>
</html>