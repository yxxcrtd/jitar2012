<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
 <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  <title>生成组织机构页面</title>
  <link rel="stylesheet" href="${SiteUrl}css/manage.css" type="text/css" />
  <script src="${SiteUrl}js/jitar/core.js"></script>
  <script>
  var minId = ${minId};
  var maxId = ${maxId};
  var currentId = minId;
  var ratio = (maxId - minId);
  function ProcessTask()
  {
  	if(currentId>maxId) return;
    var url = "${SiteUrl}manage/admin_unit_html.py?unitId=" + currentId;
  	new Ajax.Request(
		url,
		{
			method : 'get',
			onSuccess : function(xport) {
				var p = Math.ceil((currentId-minId)*100/ratio);
				document.getElementById("processBar").style.width = p + "%";				
				if(currentId == maxId) document.getElementById("contaniner").innerHTML = "全部机构首页完成!";
				else
				document.getElementById("contaniner").innerHTML = p + "%";
				currentId++;
				ProcessTask();
				}
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
<script>window.onload=ProcessTask;</script>
</body>
</html>