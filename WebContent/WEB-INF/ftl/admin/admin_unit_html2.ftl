<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
 <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  <title>生成组织机构页面</title>
  <link rel="stylesheet" href="${SiteUrl}css/manage.css" type="text/css" />
  <script src="${SiteUrl}js/jitar/core.js"></script>
  <script>
  var title = [${title}];
  var unitId = ${id};
  var currentId = 0;
  var running = true;
  function ProcessTask()
  {
  	if(!running) return;
  
  	if(currentId >= unitId.length) return;
    var url = "${SiteUrl}manage/admin_unit_html.py?unitId=" + unitId[currentId];    
  	new Ajax.Request(
		url,
		{
			method : 'get',
			onSuccess : function(xport) {
				var p = Math.ceil((currentId+1)*100/(unitId.length));
				
				document.getElementById("processBar").style.width = p + "%";				
				if(currentId == (unitId.length-1)) document.getElementById("contaniner").innerHTML = "全部机构首页完成!";
				else
				document.getElementById("contaniner").innerHTML = p + "% " + title[currentId];
				currentId++;
				ProcessTask();
			},
			onException:function(xport){document.getElementById("contaniner").innerHTML = xport.responseText;},
			onFailure:function(){document.getElementById("contaniner").innerHTML = "错误";}
			});
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
<td style="text-align:center;" id="contaniner"></td>
</tr>
</table>
<div style="text-align:center;padding:6px;"><input type="button" value=" 暂  停 " onclick="running=false;" class="button" /> <input type="button" value=" 继  续 " onclick="if(!running){running=true;ProcessTask();}"  class="button" /></div>
<script>window.onload=ProcessTask;</script>
</body>
</html>