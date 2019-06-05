<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
 <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  <title>生成用户页面</title>
  <link rel="stylesheet" href="${SiteUrl}css/manage.css" type="text/css" />
  <script src="${SiteUrl}js/jitar/core.js"></script>
  <script>
  var minId = ${minId};
  var maxId = ${maxId};
  var currentId = minId;
  var ratio = (maxId - minId);
  var running = false;
  var xmlhttp = null;
  function createXMLHTTP()
  {
  	if(window.XMLHttpRequest)
  	xmlhttp = new window.XMLHttpRequest();
  	else if (window.ActiveXObject)
  	xmlhttp = new ActiveXObject("MSXML2.XMLHTTP")
	else
	xmlhttp = null;
  }
  
  createXMLHTTP();
  function ProcessTask()
  {
  	if(!running)
  	{
  	 document.getElementById("contaniner").innerHTML = "操作已暂停。";
  	 return;
  	 }
  	if(currentId>maxId) return;
    var url = "${SiteUrl}manage/admin_user_html.py?tmp=" + (new Date()).valueOf() + "&userId=" + currentId;
    
    if(xmlhttp == null)
    {
     document.getElementById("contaniner").innerHTML = "无法创建 XMLHttp 对象。";
     return
    }
    xmlhttp.open("GET",url,true);
    xmlhttp.setRequestHeader("Connection", "close")
    xmlhttp.onreadystatechange = function(){
    if(xmlhttp.readyState == 4)
    {
    	if(xmlhttp.status == 200)
    	{
	    	var p = Math.round((currentId-minId)*100*100/ratio)/100;
			document.getElementById("processBar").style.width = Math.ceil(p) + "%";				
			if(currentId == maxId) document.getElementById("contaniner").innerHTML = "全部用户静态首页文件生成完成!";
			else
			document.getElementById("contaniner").innerHTML = p + "% （" + currentId + "）";
			currentId++;
			//xmlhttp = null;
			ProcessTask();
		}
	 }
    };
    xmlhttp.send(null);  	
  }
  
  function StartTask(o)
  {
    o.value="继续执行";
  	running = true;
  	ProcessTask();
  }
  function StopTask()
  {
  	running = false;
  }
  

  </script>
</head>
<body style="padding:60px 0">
<table style="width:100%">
<tr>
<td style="padding:1px;border:1px solid green;">
<div id="processBar" style="height:18px;background:blue;width:0;"></div>
</td>
</tr>
<tr>
<td style="text-align:center;color:red;padding:10px 0;" id="contaniner">生成全部用户的静态首页页面，可能需要几个小时，请只在初始化时使用，平时无需执行。请点击“开始执行”按钮开始执行。</td>
</tr>
</table>
<div style="text-align:center"><input type="button" value="停止执行" onclick="StopTask()" />  <input type="button" value="开始执行" onclick="StartTask(this)" /></div>
</body>
</html>