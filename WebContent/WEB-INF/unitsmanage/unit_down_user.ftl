<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
  <meta http-equiv="content-type" content="text/html; charset=UTF-8" />
  <title></title>
  <link href="${SiteUrl}css/manage.css" rel="stylesheet" type="text/css" />
  <link rel="stylesheet" type="text/css" href="${SiteUrl}css/msgbox.css" />
  <script>
  function doPost(arg)
  {
    if("exe"==arg)
    {
    	var vs = document.getElementById("manageLevel");
    	var v = vs.options[vs.selectedIndex].value;
    	if("" == v)
    	{
    	 alert("请选择一个执行选项。")
    	 return false;
    	}
    	else
    	{
    	 document.getElementById("fm").cmd.value=v;
    	}
    }
    else
    {
  		document.getElementById("fm").cmd.value=arg;
  	}
  	document.getElementById("fm").submit();
  }
  
  function doFilter(os)
  {
   window.location.href="unit_down_user.py?unitId=${unit.unitId}&userState=" + os.options[os.selectedIndex].value;
  }
  
  function ShowData(uid,xmlhttp)
  {
   if(xmlhttp.responseText.replace(/ /g,"").replace(/\r/g,"").replace(/\n/g,"")=="")
   {
    return;
   }
   acObj =  eval("(" +  xmlhttp.responseText + ")"); 
   for(i=0;i<acObj.length;i++)
   {     
     document.getElementById("ac"+uid).innerHTML +=  acObj[i].ObjectTitle + " " + showText(acObj[i].ObjectType) +" <a onclick='return confirm(\"你真的要删除吗？\")' href='${SiteUrl}units/manage/delete_unit_accesscontrol.py?accessControlId="+acObj[i].accessControlId+"&unitId=${unit.unitId}'><b>删除</b></a><br/>";
   }
  }
  function showText(nText)
  {
    switch(nText)
    {
     case "0":
     	return "未知的权限";
     	break;
     case "1":
     	return "系统超级管理员";
     	break;
     case "2":
     	return "系统用户管理员";
     	break;
     case "3":
     	return "系统内容管理员";
     	break;
     case "4":
     	return "机构系统管理员";
     	break;
     case "5":
     	return "机构用户管理员";
     	break;
     case "6":
     	return "机构内容管理员";
     	break;
     case "7":
     	return "学科系统管理员";
     	break;
     case "8":
     	return "学科用户管理员";
     	break;
     case "9":
     	return "学科内容管理员";
     	break;
     case "10":
     	return "元学科管理员";
     	break;
     case "11":
     	return "频道系统管理员";
     	break;
     case "12":
     	return "频道用户管理员";
     	break;
     case "13":
     	return "频道内容管理员";
     	break;
     case "14":
     	return "特定栏目管理员";
     	break;
     default:
     	return "未知的管理员";
     	break;
    }
  }	
  
  function ShowDiag() {
    	var guids = document.getElementsByName("guid");
    	var checkedCount = 0;
    	for(i=0;i<guids.length;i++)
    	{
    	 if(guids[i].checked) checkedCount++;
    	}
        if (checkedCount == 0) {
            window.alert("没有选择要重置新密码的用户！");
            return false;
        }
        MessageBox.Show('MessageTip');
    }    
  </script>
  <script src='${SiteUrl}js/jitar/core.js'></script>
  <script src="${SiteUrl}js/msgbox.js"></script>
</head>
<body>
<h2><span style='color:red'>${unit.unitTitle!?html}</span> 下级所有机构用户管理
</h2>
<div style='text-align:right;padding:6px 0'>
<form method="GET">
<input name='unitId' type='hidden' value='${unit.unitId}' />
关键字：<input name='k' value="${k!?html}" />
<select name='f'>
<option value='name'<#if f=="name"> selected='selected'</#if>>登录名、昵称或者真实姓名</option>
<option value='unit'<#if f=="unit"> selected='selected'</#if>>机构英文名称</option>
<option value='unitTitle'<#if f=="unitTitle"> selected='selected'</#if>>机构中文名称</option>
</select>
<input type="submit" class="button" value=" 检  索 " />
</form>
</div>
<form method='post' style='padding-left:20px' id="fm">
<input name='cmd' type='hidden' value='' />
<#if user_list??>
<table class="listTable" cellspacing="1">
  <thead>
    <tr style="text-align:left">
    <th><input type='checkbox' id='chk' onclick='CommonUtil.SelectAll(this,"guid");' /></th>
    <th style='width:48px;'>头像</th>
    <th>昵称,实名</th>  
    <th>学科(学段)</th>
    <th>管理权限</th>
    <th>
		<select onchange="doFilter(this)">
		 <option value="">用户状态</option>
		 <option value="0"<#if userState=="0"> selected='selected'</#if>>正常</option>
		 <option value="1"<#if userState=="1"> selected='selected'</#if>>待审核</option>
         <option value="2"<#if userState=="2"> selected='selected'</#if>>待删除</option>
		 <option value="3"<#if userState=="3"> selected='selected'</#if>>已锁定</option>
		</select>
		</th>
    <th>头衔</th>
    <th>所在机构</th>
    <th></th>
    </tr>
  </thead>
<#list user_list as user >
<tr>
<td style='width:17px'><input type='checkbox' name='guid' value='${user.userId}' /></td>
<td><img src="${SSOServerUrl +'upload/'+user.userIcon!"images/default.gif"}" onerror="this.src='${SiteUrl}images/default.gif';" width="48" height="48" border="0" title="${user.blogName!}" /></td>
<td>
	<a href='${SiteUrl}go.action?loginName=${user.loginName}' target='_blank'>${user.nickName?html}</a><br/>
	<a href='${SiteUrl}go.action?loginName=${user.loginName}' target='_blank'>${user.trueName?html}</a><br/>
	注册日期：${user.createDate?string('yyyy-MM-dd')}
</td>
<td>
	<#if user.subjectId??>
		${Util.subjectById(user.subjectId).msubjName!?html}
	</#if>
	<br />
	<#if user.gradeId??>
		(${Util.gradeById(user.gradeId).gradeName!?html})
	</#if>
</td>
<td id='ac${user.userId}'>
<script type='text/javascript'>
var xmlhttp${user.userId} = null;
if(window.XMLHttpRequest)
{
  xmlhttp${user.userId} = new window.XMLHttpRequest();
}
else
{
  var MX = ["MSXML2.XMLHTTP.6.0", "MSXML2.XMLHTTP.5.0",
		  "MSXML2.XMLHTTP.4.0", "MSXML2.XMLHTTP.3.0",
		  "MSXML2.XMLHTTP.2.6", "MSXML2.XMLHTTP",
		  "Microsoft.XMLHTTP"];
  for(k = 0;k<MX.length;k++)
  {
  	try
  	{
  		xmlhttp${user.userId} = new ActiveXObject(MX[k]); 
  		break;
  	}
  	catch(ex){}
  }
}

if(xmlhttp${user.userId} != null)
{
 xmlhttp${user.userId}.open("GET","${SiteUrl}manage/getAccessControl.py?userId=${user.userId}",true);
 xmlhttp${user.userId}.onreadystatechange = function()
 {
  if(xmlhttp${user.userId}.readyState == 4)
  {
   if(xmlhttp${user.userId}.status == 200)
   {
     ShowData(${user.userId},xmlhttp${user.userId});
   }
  }
 }
 xmlhttp${user.userId}.setRequestHeader("Connection", "close");
 xmlhttp${user.userId}.send(null);
}
</script>
</td>
<td>
<#if user.userStatus == 0>正常
    <#elseif user.userStatus == 1><font color='red'>待审核</font>
    <#elseif user.userStatus == 3><font color='red'>已锁定</font>
    <#elseif user.userStatus == 2><font color='red'>已删除</font>
    <#else ><font color='red'>未知状态</font>
</#if>
</td>
<td>
<#if user.userType??>
<#assign showTypeName = Util.typeIdToName(user.userType) >
    <#if showTypeName??>
        <#list showTypeName?split("/") as x>
        <#if (x?length) &gt; 0 >${x}</#if>
        </#list> 
    </#if>
</#if>
</td>
<td><a href='${SiteUrl}go.action?unitId=${user.unitId}' target='_blank'>${user.unitTitle!}</a></td>
<td>
<#if unit.unitId == user.unitId>
<a href='unit_modi_userinfo.py?unitId=${unit.unitId}&amp;userId=${user.userId}'>修改信息</a>
<a href='unit_modi_userpass.py?unitId=${unit.unitId}&amp;userId=${user.userId}'>修改密码</a>
</#if>
</td>
</tr>
</#list>
</table>
</#if>
<#if pager??>
<#include "/WEB-INF/ftl/pager.ftl">
</#if>
<div style='padding:6px'>
<input class='button' type='button' value='全 选' onclick='document.getElementById("chk").click();CommonUtil.SelectAll(document.getElementById("chk"),"guid");' />
<input class='button' type='button' value='转为待删除' onclick="doPost('delete')" />
<input class='button' type='button' value='审核' onclick="doPost('audit')" />
<input class='button' type='button' value='待审核' onclick="doPost('unaudit')" />
<input class='button' type='button' value='重设密码' onclick="ShowDiag()" />
<select id='manageLevel'>
<option value="">用户管理权限设置</option>
<option value="unitSystemAdmin">设置为机构系统管理员</option>
<option value="unitUserAdmin">设置为机构用户管理员</option>
<option value="contentAdmin">设置为机构内容管理员</option>
</select>
<span id="unitName"></span>
<input type='hidden' id='unit_id' name='unit_id' />
<input type='button' value='选择一个下级机构…' onclick="window.open('${SiteUrl}jython/get_unit_list.py?fromUnitId=${unit.unitId}','_blank','width=800,height=600,resizable=1,scrollbars=1')"/>
<input class='button' type='button' value='执行设置' onclick="doPost('exe')" />
</div>
<div id="blockUI" onClick="return false" onMousedown="return false" onMousemove="return false" onMouseup="return false" onDblclick="return false">&nbsp;</div>
<div id="MessageTip" class="hidden">
    <div class="boxHead">
        <div class="boxCloseButton" onclick="return MessageBox.Close();"><img src="${SiteUrl}images/dele.gif" /></div>
        <div class="boxTitle" onmousedown="MessageBox.dragStart(event)">为选择的用户重新设置新密码：</div>
    </div>
    <div style="padding: 10px; text-align: center;">
        <div style="line-height: 40px;"><br /></div>
          新密码：<input type="text" name="reset_password" />
        <input type="button" class="button" value=" 确  定 " onClick="doPost('resetpassword');" />
        <div style="line-height: 80px;"><br /></div>
    </div>
</div>
</form>
</body>
</html>