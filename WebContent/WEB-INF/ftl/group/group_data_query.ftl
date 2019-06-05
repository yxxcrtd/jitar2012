<#assign grpName="协作组">
<#assign grpShowName="小组">
<#if isKtGroup??>
    <#if isKtGroup=="1">
        <#assign grpName="课题组"> 
        <#assign grpShowName="课题">
    <#elseif isKtGroup=="2">
        <#assign grpName="备课组"> 
        <#assign grpShowName="小组">
    <#else>
        <#assign grpName="协作组">
        <#assign grpShowName="小组">
    </#if>
</#if>
<!doctype html>
<head>
  <meta http-equiv="Content-Type" content="text/html;charset=UTF-8" />
  <title>管理${grpName}</title>
  <link rel="stylesheet" type="text/css" href="${SiteUrl}css/manage.css" />
  <link rel="stylesheet" type="text/css" href="${SiteUrl}js/datepicker/calendar.css" />  
  <script src='${SiteUrl}js/datepicker/calendar.js' type="text/javascript"></script>
</head>
<body>
<#include 'group_title.ftl' >
<div class='pos'>
  您现在的位置： <a href='${SiteUrl}manage/group.py?cmd=home&amp;groupId=${group.groupId}'>${grpName}管理首页</a>
  &gt;&gt; <span>${grpName}统计查询</span> 
</div>
<br/>
<#if member_list??>
<form name='theForm' action='?groupId=${group.groupId}&guid=${guid}&cmd=query' method='POST'>
<strong>请输入要查询的起止日期：</strong>
开始日期：<input name="beginDate" id="beginDate" value="${beginDate!?html}" size="10" maxlength="10" readonly="readonly" />
结束日期：<input name="endDate" id="endDate" value="${endDate!?html}" size="10" maxlength="10" readonly="readonly" />
<input type="button" value=" 查  询 " onclick='exeQuery(this.form)' />
</form>
<div style="padding:20px;text-align:center;margin:auto;display:none" id="status">
  <div style='border:1px solid #8c8c8c;padding:1px;text-align:left;'>
   <div style="width:0%;height:10px;padding:5px 0;border:1px solid #8c8c8c;background:url(${SiteUrl}images/bar.gif) #3c6 top left;" id="statusColor">&nbsp;</div>
  </div>
  <div style="width:100%;padding:10px 0;position:relative;top:-30px;color:#0000FF;font-size:13px;" id="statusText">&nbsp;</div>
</div>
<script>
var xmlhttp = null;
var oForm = null;
var i = 0;
var postData = "";
var url = "queryData.py";
var taskId = "<#list member_list as m>${m.userId}<#if m_has_next>,</#if></#list>".split(",");
var taskTitle = "<#list member_list as m><#if m.trueName??>${m.trueName?js_string}<#else>${m.loginName?js_string}</#if><#if m_has_next>,</#if></#list>".split(",");
function exeQuery(oF)
{	
	oForm = oF;
	//检查日期是否在 1950-2049之间	
	d1= Date.parse(oForm.beginDate.value.replace(/-/g,"/"))
	d2= Date.parse(oForm.endDate.value.replace(/-/g,"/"))
	limitd1 = new Date(1950,1,1)
	limitd2 = new Date(2049,12,31)
	if(d1<limitd1 || d1>limitd2 )
	{
		alert("开始日期只能在 1950-01-10 到 2049-12-31 之间，这是数据库的设置限制的。");
		return;
	}
	if(d2<limitd1 || d2>limitd2 )
	{
		alert("结束日期只能在 1950-01-10 到 2049-12-31 之间，这是数据库的设置限制的。");
		return;
	}
	
	
	postData = "type=check&groupId=${group.groupId}&guid=${guid}&beginDate=" + oForm.beginDate.value + "&endDate=" + oForm.endDate.value;
	createXmlHttp();
	if(xmlhttp == null)
  {
   alert('不能创建 XMLHttpRequest 对象。\r\n\r\n请检查你的浏览器安全性设置，和管理加载项。');
   return;
  }
  document.getElementById("status").style.display="";
  document.getElementById("statusColor").style.width = "1%";
	document.getElementById("statusText").innerHTML = "正在验证输入的参数……";
  xmlhttp.open("POST",url,false);
  xmlhttp.setRequestHeader("Content-type","application/x-www-form-urlencoded");
  xmlhttp.setRequestHeader("Content-Length",postData.length);
  xmlhttp.send(postData);
  
  if(xmlhttp.readyState == 4)
  {
	  if(xmlhttp.status >= 200 && xmlhttp.status <=302)
	  {
	 		resultStatus = xmlhttp.getResponseHeader("result")
			if(resultStatus == "error")
			{
				document.getElementById("status").style.display="none";
			  document.getElementById("statusColor").style.width = "0%";
				document.getElementById("statusText").innerHTML = "";
				alert("验证查询出现错误，无法进行继续查询：\r\n\r\n" + xmlhttp.responseText);
				return;
			}
	  }
	}      

	//开始查询
	i = 0;
  setTimeout("exetask()",10);
}

function exetask()
{
  if( i < taskId.length)
  {
    var sw = "" + Math.floor(100*(i+1)/taskId.length) + "%";
  	document.getElementById("statusColor").style.width = sw;
		document.getElementById("statusText").innerHTML = "正在查询用户 " + taskTitle[i] + " ……，当前进度：" + sw;
		postData = "type=query&groupId=${group.groupId}&guid=${guid}&userId="+taskId[i]+"&beginDate=" + oForm.beginDate.value + "&endDate=" + oForm.endDate.value;
	  xmlhttp.open("POST",url,false);
	  xmlhttp.setRequestHeader("Content-type","application/x-www-form-urlencoded");
	  xmlhttp.setRequestHeader("Content-Length",postData.length);
	  xmlhttp.send(postData);
	  if(xmlhttp.readyState == 4 && xmlhttp.status == 200)
	  {
	  	i++;
	  	setTimeout("exetask()",10);
	  }
  }
  else
  {
  	document.getElementById("statusColor").style.width = "100%";  	
  	document.getElementById("statusText").innerHTML = "查询完毕！当前进度：100%。正在转向结果页面……";
  	window.setTimeout("transfer()",1000);
  }
}

function transfer()
{
  ret = "group_data_query.py?cmd=list&groupId=${group.groupId}&guid=${guid}&beginDate="+encodeURIComponent(oForm.beginDate.value)+"&endDate="+encodeURIComponent(oForm.endDate.value);
	window.location.href=ret;
}

function createXmlHttp()
{
 	if(window.XMLHttpRequest)
 	{   	
 	  xmlhttp = new window.XMLHttpRequest();
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
    		xmlhttp = new ActiveXObject(MX[k]); 
    		break;
    	}
    	catch(ex){}
    }
 	}
}

<#if beginDate?? && endDate??>
exeQuery(document.theForm)
</#if>
</script>
<#else>
<h4 style="text-align:center;color:red">该协作组没有成员，无需进行查询。</h4>
</#if>
<script>
calendar.set("beginDate");
calendar.set("endDate");
</script>
</body>
</html>