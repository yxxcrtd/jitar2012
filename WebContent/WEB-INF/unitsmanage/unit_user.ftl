<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
  <meta http-equiv="content-type" content="text/html; charset=UTF-8" />
  <title></title>
  <link href="${SiteUrl}css/manage.css" rel="stylesheet" type="text/css" />
  <script>
  function doPost(arg)
  {
    if("delete"==arg)
    {
        //机构管理员不能转为待删除，需要先把机构管理员转为 普通用户
        var vs = document.getElementsByName("guid");
        for(i=0;i<vs.length;i++){
            if(vs[i].checked){
                uid=vs[i].value;
                acs=document.getElementById("uac_"+uid).value;
                if(acs.indexOf(",4,")>=0 || acs.indexOf(",5,")>=0 || acs.indexOf(",6,")>=0){
                    alert("机构管理员不能转为待删除");
                    return false;
                }
            }
        }
    }
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
   window.location.href="unit_user.py?unitId=${unit.unitId}&userState=" + os.options[os.selectedIndex].value;
  }
  
  function ShowData(uid,xmlhttp)
  {
   if(xmlhttp.responseText != "")
   {
       acObj =  eval('(' +  xmlhttp.responseText + ')'); 
       for(i=0;i<acObj.length;i++)
       {     
         document.getElementById("ac"+uid).innerHTML +=  acObj[i].ObjectTitle + " " + showText(acObj[i].ObjectType) +"<br/>";
         document.getElementById("uac_"+uid).value +=  acObj[i].ObjectType +",";
       }
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
     	return "不确定";
     	break;
    }
  }
  </script>
  <script src='${SiteUrl}js/jitar/core.js'></script>
</head>
<body>
<h2><span style='color:red'>${unit.unitTitle!?html}</span> 机构用户管理
</h2>
<div style='text-align:right;padding:6px 0'>
<form method="GET" action="?">
<input name='unitId' type='hidden' value='${unit.unitId}' />
请输入用户的登录名、昵称或者真实姓名：
<input name='k' value="${k!?html}" />
<input name='f' type='hidden' value='name' />
<input type="submit" class="button" value=" 检  索 " />
</form>
</div>
<form method='post' style='padding-left:20px' id="fm" action="?">
<input name='cmd' type='hidden' value='' />
<input name='unitId' type='hidden' value='${unit.unitId}' />
<#if user_list??>
<table class="listTable" cellspacing="1">
  <thead>
    <tr style="text-align:left">
    <th><input type='checkbox' id='chk' onclick='CommonUtil.SelectAll(this,"guid");' /></th>
    <th style='width:48px;'>头像</th>
    <th>昵称,实名</th>  
    <th>学段/学科</th>
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
    <th>改密码</th>
    </tr>
  </thead>
<#list user_list as user >
<tr>
<td style='width:17px'>
<input type='checkbox' name='guid' value='${user.userId}' />
<input type='hidden' name='uac_${user.userId}' id='uac_${user.userId}' value=',' />
</td>
<td><img src="${SSOServerUrl +'upload/'+user.userIcon!"images/default.gif"}" onerror="this.src='${SiteUrl}images/default.gif';" width="48" height="48" border="0" title="${user.blogName!}" /></td>
<td>
	<#if user.nickName??><a href='${SiteUrl}go.action?loginName=${user.loginName}' target='_blank'>${user.nickName?html}</a><br/></#if>
	<a href='${SiteUrl}go.action?loginName=${user.loginName}' target='_blank'>${user.trueName?html}</a><br/>
	注册日期：${user.createDate?string('yyyy-MM-dd')}
</td>
<td>
<#assign usgList = Util.getSubjectGradeListByUserId(user.userId)>
<#if usgList?? && (usgList?size> 0) >
<#list usgList as usg>
<#if usg.gradeId??>${Util.gradeById(usg.gradeId).gradeName!?html}<#else>未设置</#if>/<#if usg.subjectId??>${Util.subjectById(usg.subjectId).msubjName!?html}<#else>未设置</#if>
<#if usg_has_next><br/></#if>
</#list>
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
    <#elseif user.userStatus == 2><font color='red'>待删除</font>
    <#else ><font color='red'>未知状态</font>
</#if>
</td>
<td>
<#if user.userType??>
<#assign showTypeName = Util.typeIdToName(user.userType) >
    <#if showTypeName??>
        <#list showTypeName?split("/") as x>
        <#if (x?length) &gt; 0 ><div>${x}</div></#if>
        </#list> 
    </#if>
</#if>
</td>
<td>
<a href='unit_modi_userinfo.py?unitId=${unit.unitId}&amp;userId=${user.userId}'>修改信息</a>
<a href='unit_modi_userpass.py?unitId=${unit.unitId}&amp;userId=${user.userId}'>修改密码</a>
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
<select id='manageLevel'>
<option value="">用户管理权限设置</option>
<optgroup label="设置管理权限">
<#if unitAdmin == "1">
<option value="unitSystemAdmin">设置为机构系统管理员</option>
<option value="unitUserAdmin">设置为机构用户管理员</option>
</#if>
<option value="contentAdmin">设置为机构内容管理员</option>
</optgroup>
<optgroup label="取消管理权限">
<#if unitAdmin == "1">
<option value="ununitSystemAdmin">取消机构系统管理员</option>
<option value="ununitUserAdmin">取消机构用户管理员</option>
</#if>
<option value="uncontentAdmin">取消机构内容管理员</option>
</optgroup>
</select>
<input class='button' type='button' value='执行设置' onclick="doPost('exe')" />
</div>
</form>
</body>
</html>